package com.ncsu.wolfwr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.BillingInfo;
import com.ncsu.wolfwr.entity.Staff;
import com.ncsu.wolfwr.repository.BillingInfoRepository;
import com.ncsu.wolfwr.repository.StaffRepository;

import models.ShipmentProductDetails;

@Service
public class BillingService {
	BillingInfoRepository billingRepo;
	
	StaffRepository staffRepo;
	
	@Autowired
	BillingService(BillingInfoRepository billingRepo, StaffRepository staffRepo) {
		this.billingRepo = billingRepo;
		this.staffRepo = staffRepo;
	}
	
	public BillingInfo getBillById(int billId) {
		return billingRepo.findById(billId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
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
