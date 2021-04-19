package com.ncsu.wolfwr.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.BillingInfo;
import com.ncsu.wolfwr.entity.Merchandise;
import com.ncsu.wolfwr.entity.Staff;
import com.ncsu.wolfwr.repository.BillingInfoRepository;
import com.ncsu.wolfwr.repository.MerchandiseRepository;
import com.ncsu.wolfwr.repository.StaffRepository;

import models.ShipmentMerchDetails;
import models.ShipmentProductDetails;

@Service
public class BillingService {
	BillingInfoRepository billingRepo;
	
	StaffRepository staffRepo;
	
	MerchandiseRepository merchRepo;
	
	@Autowired
	BillingService(BillingInfoRepository billingRepo, StaffRepository staffRepo, MerchandiseRepository merchRepo) {
		this.billingRepo = billingRepo;
		this.staffRepo = staffRepo;
		this.merchRepo = merchRepo;
	}
	
	/**
	 * @param billId
	 * @return billing info object.
	 * throws not found exception if bill id is not found.
	 */
	public BillingInfo getBillById(int billId) {
		return billingRepo.findById(billId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	/**
	 * @param receivingStoreId
	 * @param shipmentId
	 * @param products as a list
	 * generate a bill in billing_info once shipment is received to a store. 
	 */ 
	public void generateBill(int receivingStoreId, int shipmentId, List<ShipmentProductDetails> products) {
		BillingInfo bill = new BillingInfo();
		bill.setPaymentStatus(false);
		bill.setShipmentId(shipmentId);
		List<Staff> billingOps = staffRepo.getBillingOperatorByStore(receivingStoreId);
		if (billingOps.isEmpty()) {
			bill.setStaffId(null);			
		} else {
			bill.setStaffId(billingOps.get(0).getStaffId());
		}
		float amount = (float) 0.0;
		for (ShipmentProductDetails product: products) {
			amount += product.getBuyPrice()*product.getQuantity();
		}
		bill.setAmount(amount);
		billingRepo.save(bill);
	}
	
	public void generateBillStore(int receivingStoreId, int shipmentId, List<ShipmentMerchDetails> merchandiseList) {
		BillingInfo bill = new BillingInfo();
		bill.setPaymentStatus(false);
		bill.setShipmentId(shipmentId);
		List<Staff> billingOps = staffRepo.getBillingOperatorByStore(receivingStoreId);
		if (billingOps.isEmpty()) {
			bill.setStaffId(null);			
		} else {
			bill.setStaffId(billingOps.get(0).getStaffId());
		}
		float amount = (float) 0.0;
		for (ShipmentMerchDetails merch: merchandiseList) {
			Optional<Merchandise> merchandise = merchRepo.findById(merch.getMerchandiseId());
			
			amount += merchandise.orElseThrow().getBuyPrice()*merch.getQuantity();
		}
		bill.setAmount(amount);
		billingRepo.save(bill);
	}
	
	public void updateBill(int shipmentId, List<ShipmentProductDetails> products) {
		BillingInfo bill = billingRepo.findByShipmentId(shipmentId).orElseThrow();
		float amount = (float) 0.0;
		for (ShipmentProductDetails product: products) {
			amount += product.getBuyPrice()*product.getQuantity();
		}
		bill.setAmount(amount);
		billingRepo.save(bill);
	}
	
	public void updateBill(int billId, BillingInfo billInfo) {
		if(billInfo.getBillId() != billId) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		billingRepo.save(billInfo);
	}
	
	public void deleteBill(int id) {
		this.billingRepo.deleteById(id);
	}
	
	/**
	 * @param supplierId
	 * @param storeId
	 * @return total sum of pending amount for a given supplier for a particular store.
	 */
	public Float getSupplierPendingPayments(int supplierId, Integer storeId) {
		float pendingPayment = (float) 0.0;
		if (storeId == null) {
			pendingPayment = billingRepo.getSupplierPendingPayment(supplierId).orElse((float)0.0);
			
		} else {
			pendingPayment = billingRepo.getSupplierPendingPaymentForStore(supplierId, storeId).orElse((float)0.0);
		}
		return pendingPayment;
	}
}
