package com.ncsu.wolfwr.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Shipment;
import com.ncsu.wolfwr.entity.ShipmentContainsProduct;
import com.ncsu.wolfwr.entity.StoreShipment;
import com.ncsu.wolfwr.entity.SupplierShipment;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.ShipmentContainsProductRepository;
import com.ncsu.wolfwr.repository.ShipmentRepository;
import com.ncsu.wolfwr.repository.StoreShipmentRepository;
import com.ncsu.wolfwr.repository.SupplierShipmentRepository;

import models.ProductsDetailsJSON;
import models.StoreShipmentPOJO;
import models.SupplierShipmentPOJO;

@Service
public class ShipmentService {
	ShipmentRepository shipmentRepo;
	
	StoreShipmentRepository storeShipmentRepo;
	
	SupplierShipmentRepository supplierShipmentRepo;
	
	MerchandiseRepository merchandiseRepo;
	
	ShipmentContainsProductRepository shipmentContainsProductRepo;
	
	@Autowired
	ShipmentService(ShipmentRepository shipmentRepo, StoreShipmentRepository storeShipmentRepo, SupplierShipmentRepository supplierShipmentRepo, MerchandiseRepository merchandiseRepo, ShipmentContainsProductRepository shipmentContainsProductRepo) {
		this.shipmentRepo = shipmentRepo;
		this.storeShipmentRepo = storeShipmentRepo;
		this.supplierShipmentRepo = supplierShipmentRepo;
		this.merchandiseRepo = merchandiseRepo;
		this.shipmentContainsProductRepo = shipmentContainsProductRepo; 
	}
	
	public Shipment getShipmentById(Integer shipmentId) {
		return shipmentRepo.findById(shipmentId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createStoreShipment(StoreShipmentPOJO storeShipmentPojo) {
		Shipment shipment = storeShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		StoreShipment storeShipment = new StoreShipment(storeShipmentPojo);
		storeShipment = this.storeShipmentRepo.save(storeShipment);
		saveShipmentProducts(shipment.getShipmentId(), storeShipmentPojo.getProductsList());
		return storeShipment.getShipmentId();
	}
	
	public Integer createSupplierShipment(SupplierShipmentPOJO supplierShipmentPojo) {
		Shipment shipment = supplierShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		SupplierShipment supplierShipment = new SupplierShipment(supplierShipmentPojo);
		supplierShipment = this.supplierShipmentRepo.save(supplierShipment);
		saveShipmentProducts(shipment.getShipmentId(), supplierShipmentPojo.getProductsList());
		return supplierShipment.getShipmentId();
	}
	
	private void saveShipmentProducts(Integer shipmentId, List<ProductsDetailsJSON> products) {
		List<ShipmentContainsProduct> shipmentProducts = new ArrayList<ShipmentContainsProduct>();
		products.stream().forEach((product) -> {
			shipmentProducts.add(new ShipmentContainsProduct(shipmentId, product));
			updateOrCreateMerchandise(product);
		});
		this.shipmentContainsProductRepo.saveAll(shipmentProducts);
	}
	
	private void updateOrCreateMerchandise(ProductsDetailsJSON product) {
		// TODO Add logic to create merchandise if product details don't match existing merchandise 
		// or update stock if it does
	}
	
	public void updateStoreShipment(Integer id, StoreShipmentPOJO storeShipmentPojo) {
		Shipment shipment = storeShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		StoreShipment storeShipment = new StoreShipment(storeShipmentPojo);
		storeShipment = this.storeShipmentRepo.save(storeShipment);
		saveShipmentProducts(shipment.getShipmentId(), storeShipmentPojo.getProductsList());
	}
	
	public void updateSupplierShipment(Integer id, SupplierShipmentPOJO supplierShipmentPojo) {
		Shipment shipment = supplierShipmentPojo.getShipmentDetails();
		shipment = this.shipmentRepo.save(shipment);
		SupplierShipment supplierShipment = new SupplierShipment(supplierShipmentPojo);
		supplierShipment = this.supplierShipmentRepo.save(supplierShipment);
		saveShipmentProducts(shipment.getShipmentId(), supplierShipmentPojo.getProductsList());
	}
	
	public void deleteShipment(Integer id) {
		this.shipmentRepo.deleteById(id);
	}
}
