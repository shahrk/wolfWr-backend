package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.BillingOperator;
import com.ncsu.wolfwr.entity.Cashier;
import com.ncsu.wolfwr.entity.Manager;
import com.ncsu.wolfwr.entity.RegistrationOperator;
import com.ncsu.wolfwr.entity.Staff;
import com.ncsu.wolfwr.entity.WarehouseOperator;
import com.ncsu.wolfwr.repository.BillingOperatorRepository;
import com.ncsu.wolfwr.repository.CashierRepository;
import com.ncsu.wolfwr.repository.ManagerRepository;
import com.ncsu.wolfwr.repository.RegistrationOperatorRepository;
import com.ncsu.wolfwr.repository.StaffRepository;
import com.ncsu.wolfwr.repository.WarehouseOperatorRepository;

@Service
public class StaffService {
	
	StaffRepository staffRepo;
	CashierRepository cashierRepo;
	ManagerRepository managerRepo;
	BillingOperatorRepository billingRepo;
	RegistrationOperatorRepository registrationRepo;
	WarehouseOperatorRepository warehouseRepo;
	
	@Autowired
	StaffService(StaffRepository staffRepo, CashierRepository cashierRepo, ManagerRepository managerRepo, 
			BillingOperatorRepository billingRepo, RegistrationOperatorRepository registrationRepo, WarehouseOperatorRepository warehouseRepo) {
		this.staffRepo = staffRepo;
		this.cashierRepo = cashierRepo;
		this.managerRepo = managerRepo;
		this.billingRepo = billingRepo;
		this.registrationRepo = registrationRepo;
		this.warehouseRepo = warehouseRepo;
	}
	
	public Staff getStaffById(int staffId) {
		return staffRepo.findById(staffId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createStaff(Staff staff) {
		if (staff.getStaffId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		staff = this.staffRepo.save(staff);
		if(staff.getJobTitle().equalsIgnoreCase("cashier")) {
			Cashier cashier = new Cashier();
			cashier.setStaffId(staff.getStaffId());
			this.cashierRepo.save(cashier);
		}else if(staff.getJobTitle().equalsIgnoreCase("Manager")) {
			Manager manager = new Manager();
			manager.setStaffId(staff.getStaffId());
			this.managerRepo.save(manager);
		}else if(staff.getJobTitle().equalsIgnoreCase("Warehouse")) {
			WarehouseOperator warehouse = new WarehouseOperator();
			warehouse.setStaffId(staff.getStaffId());
			this.warehouseRepo.save(warehouse);
		}else if(staff.getJobTitle().equalsIgnoreCase("Biller")) {
			BillingOperator billing = new BillingOperator();
			billing.setStaffId(staff.getStaffId());
			this.billingRepo.save(billing);
		}else if(staff.getJobTitle().equalsIgnoreCase("Registrar")) {
			RegistrationOperator registrar = new RegistrationOperator();
			registrar.setStaffId(staff.getStaffId());
			this.registrationRepo.save(registrar);
		}
		return staff.getStaffId();
	}
	
	public void updateStaff(int id, Staff staff) {
		if (staff.getStaffId() != null && staff.getStaffId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.staffRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.staffRepo.save(staff);
	}
	
	public void deleteStaff(int id) {
		this.staffRepo.deleteById(id);
	}
}
