package com.ncsu.wolfwr.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Merchandise;
import com.ncsu.wolfwr.entity.Shipment;
import com.ncsu.wolfwr.entity.ShipmentContainsProduct;
import com.ncsu.wolfwr.entity.ShipmentProductId;
import com.ncsu.wolfwr.entity.StoreShipment;
import com.ncsu.wolfwr.entity.SupplierShipment;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.ShipmentContainsProductRepository;
import com.ncsu.wolfwr.repository.ShipmentRepository;
import com.ncsu.wolfwr.repository.StaffRepository;
import com.ncsu.wolfwr.repository.StoreShipmentRepository;
import com.ncsu.wolfwr.repository.SupplierShipmentRepository;

import models.ShipmentMerchDetails;
import models.ShipmentProductDetails;
import models.StoreShipmentPOJO;
import models.SupplierShipmentPOJO;

@Service
public class ShipmentService {
	ShipmentRepository shipmentRepo;
	
	StoreShipmentRepository storeShipmentRepo;
	
	BillingService billingService;
	
	SupplierShipmentRepository supplierShipmentRepo;
	
	MerchandiseRepository merchandiseRepo;
	
	ShipmentContainsProductRepository shipmentContainsProductRepo;
	
	StaffRepository staffRepo;
	
	MerchandiseService merchService;
	
	@Autowired
	ShipmentService(ShipmentRepository shipmentRepo, StoreShipmentRepository storeShipmentRepo, SupplierShipmentRepository supplierShipmentRepo, MerchandiseRepository merchandiseRepo, ShipmentContainsProductRepository shipmentContainsProductRepo, BillingService billingService, MerchandiseService merchService, StaffRepository staffRepo) {
		this.shipmentRepo = shipmentRepo;
		this.storeShipmentRepo = storeShipmentRepo;
		this.supplierShipmentRepo = supplierShipmentRepo;
		this.merchandiseRepo = merchandiseRepo;
		this.shipmentContainsProductRepo = shipmentContainsProductRepo;
		this.billingService =  billingService;
		this.merchService = merchService;
		this.staffRepo = staffRepo;
	}
	
	public Shipment getShipmentById(int shipmentId) {
		return shipmentRepo.findById(shipmentId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	/**
	 * @param senderStoreId, senderWarehouseCheckerId, shipmentDate, shipmentDetails, shipmentMerchDetails
	 * @return shipment_id.
	 * 1. Create a new entry in shipment, store_shipment and shipment_contains_product entities.
	 * 2. Decrease quantity in stock from sender store and increase the quantity in stock in recipient store
	 * 	  for the shipment_contains_merchandise entity.
	 * 3. Generate a bill with total sum to be paid to sender store.  
	 */ 
	public Integer createStoreShipment(StoreShipmentPOJO storeShipmentPojo) {
		Shipment shipment = storeShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		StoreShipment storeShipment = new StoreShipment(storeShipmentPojo);
		storeShipment.setShipmentId(shipment.getShipmentId());
		storeShipment = this.storeShipmentRepo.save(storeShipment);
		saveStoreShipmentMerchandise(shipment.getRecepientStoreId(), shipment.getShipmentId(), storeShipmentPojo.getShipmentMerchDetails());
		this.billingService.generateBillStore(shipment.getRecepientStoreId(), shipment.getShipmentId(), storeShipmentPojo.getShipmentMerchDetails());
		return storeShipment.getShipmentId();
	}
	
	/**
	 * @param supplierId, shipmentDetails, productsList
	 * @return shipment_id.
	 * 1. Create a new entry in shipment, supplier_shipment and shipment_contains_product entities.
	 * 2. Increase the quantity in stock in recipient store for the shipment_contains_merchandise entity.
	 * 3. Generate a bill with total sum to be paid to the supplier. 
	 */
	@Transactional
	public Integer createSupplierShipment(SupplierShipmentPOJO supplierShipmentPojo) {
		Shipment shipment = supplierShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		SupplierShipment supplierShipment = new SupplierShipment(supplierShipmentPojo);
		supplierShipment.setShipmentId(shipment.getShipmentId());
		supplierShipment = this.supplierShipmentRepo.save(supplierShipment);
		saveSupplierShipmentProducts(supplierShipmentPojo.getSupplierId(), shipment, supplierShipmentPojo.getProductsList());
		this.billingService.generateBill(shipment.getRecepientStoreId(), shipment.getShipmentId(), supplierShipmentPojo.getProductsList());
		return supplierShipment.getShipmentId();
	}
	
	private void saveSupplierShipmentProducts(int supplierId, Shipment shipment, List<ShipmentProductDetails> products) {
		List<ShipmentContainsProduct> shipmentProducts = new ArrayList<ShipmentContainsProduct>();
		products.stream().forEach((product) -> {
			shipmentProducts.add(new ShipmentContainsProduct(shipment.getShipmentId(), product));
			createOrUpdateMerchandiseOnSupplierShipment(shipment.getRecepientStoreId(), supplierId, product);
		});
		this.shipmentContainsProductRepo.saveAll(shipmentProducts);
	}
	
	private void saveSupplierShipmentProductsOnUpdate(int supplierId, Shipment shipment, List<ShipmentProductDetails> products) {
		List<ShipmentContainsProduct> shipmentProducts = new ArrayList<ShipmentContainsProduct>();
		products.stream().forEach((product) -> {
			ShipmentProductId spId = new ShipmentProductId(shipment.getShipmentId(), product.getProductId());
			shipmentProducts.add(new ShipmentContainsProduct(shipment.getShipmentId(), product));
			this.shipmentContainsProductRepo.findById(spId).ifPresent((shipmentProduct)->product.setQuantity(product.getQuantity() - shipmentProduct.getQuantity()));
			createOrUpdateMerchandiseOnSupplierShipment(shipment.getRecepientStoreId(), supplierId, product);
		});
		this.shipmentContainsProductRepo.saveAll(shipmentProducts);
	}
	
	private void saveStoreShipmentMerchandise(int receivingStoreId, int shipmentId, List<ShipmentMerchDetails> merchList) {
		List<ShipmentContainsProduct> shipmentProducts = new ArrayList<ShipmentContainsProduct>();
		merchList.stream().forEach((merchDetails) -> {
			Merchandise merch = merchandiseRepo.findById(merchDetails.getMerchandiseId()).orElseThrow();
			shipmentProducts.add(new ShipmentContainsProduct(shipmentId, merch.getProductId(), merchDetails.getQuantity()));
			createOrUpdateMerchandiseOnStoreShipment(receivingStoreId, merch, merchDetails.getQuantity());
		});
		this.shipmentContainsProductRepo.saveAll(shipmentProducts);
	}
	
	private void createOrUpdateMerchandiseOnSupplierShipment(Integer receivingStoreId, Integer supplierId, ShipmentProductDetails product) {
		Merchandise merch = merchandiseRepo.getMatchingMerchandise(product.getProductId(), receivingStoreId, product.getBuyPrice(), product.getMarketPrice(), product.getProductionDate(), product.getExpirationDate(), supplierId);
		if (merch != null) {			
			merchandiseRepo.addMerchandiseStock(merch.getMerchandiseId(), product.getQuantity());
		} else {
			merch = new Merchandise();
			merch.setBuyPrice(product.getBuyPrice());
			merch.setExpirationDate(product.getExpirationDate());
			merch.setMarketPrice(product.getMarketPrice());
			merch.setProductId(product.getProductId());
			merch.setProductionDate(product.getProductionDate());
			merch.setQuantityInStock(product.getQuantity());
			merch.setStoreId(receivingStoreId);
			merch.setSupplierId(supplierId);
			merchService.createMerchandise(merch);
		}
	}
	
	private void createOrUpdateMerchandiseOnStoreShipment(Integer receivingStoreId, Merchandise senderMerch, Integer quantity) {
		//updating merchandise for sending store
		merchandiseRepo.addMerchandiseStock(senderMerch.getMerchandiseId(), -quantity);
		//creating or updating merchandise for receiving store
		Merchandise merch = merchandiseRepo.getMatchingMerchandise(senderMerch.getProductId(), receivingStoreId, senderMerch.getBuyPrice(), senderMerch.getMarketPrice(), senderMerch.getProductionDate(), senderMerch.getExpirationDate(), senderMerch.getSupplierId());
		if (merch != null) {			
			merchandiseRepo.addMerchandiseStock(merch.getMerchandiseId(), quantity);
		} else {
			merch = senderMerch;
			senderMerch.setStoreId(receivingStoreId);
			senderMerch.setQuantityInStock(quantity);
			senderMerch.setMerchandiseId(null);	
			merchService.createMerchandise(merch);
		}
	}
	
	public void updateStoreShipment(int id, StoreShipmentPOJO storeShipmentPojo) {
		Shipment shipment = storeShipmentPojo.getShipmentDetails();
		if(shipment.getShipmentId() != null && shipment.getShipmentId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mismatching Shipment Id");
		}
		shipment.setShipmentId(id);
		shipment = this.shipmentRepo.save(shipment);
		StoreShipment storeShipment = new StoreShipment(storeShipmentPojo);
		storeShipment = this.storeShipmentRepo.save(storeShipment);
		saveStoreShipmentMerchandise(shipment.getRecepientStoreId(), shipment.getShipmentId(), storeShipmentPojo.getShipmentMerchDetails());
	}
	
	@Transactional
	public void updateSupplierShipment(int id, SupplierShipmentPOJO supplierShipmentPojo) {
		Shipment shipment = supplierShipmentPojo.getShipmentDetails();
		if(shipment.getShipmentId() != null && shipment.getShipmentId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "mismatching Shipment Id");
		}
		shipment.setShipmentId(id);
		shipment = this.shipmentRepo.save(shipment);
		SupplierShipment supplierShipment = new SupplierShipment(supplierShipmentPojo);
		supplierShipment = this.supplierShipmentRepo.save(supplierShipment);
		this.billingService.updateBill(shipment.getShipmentId(), supplierShipmentPojo.getProductsList());
		saveSupplierShipmentProductsOnUpdate(supplierShipmentPojo.getSupplierId(), shipment, supplierShipmentPojo.getProductsList());
	}
	
	public void deleteShipment(int id) {
		this.shipmentRepo.deleteById(id);
	}
}
