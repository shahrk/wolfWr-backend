package com.ncsu.wolfwr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ncsu.wolfwr.entity.Staff;
import com.ncsu.wolfwr.repository.StaffRepository;

@Service
public class StaffService {
	
	StaffRepository staffRepo;
	
	@Autowired
	StaffService(StaffRepository staffRepo) {
		this.staffRepo = staffRepo;
	}
	
	public Staff getStaffById(Integer staffId) {
		return staffRepo.findById(staffId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	public Integer createStaff(Staff staff) {
		if (staff.getStaffId() != null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		staff = this.staffRepo.save(staff);
		return staff.getStaffId();
	}
	
	public void updateStaff(Integer id, Staff staff) {
		if (staff.getStaffId() != null && staff.getStaffId() != id) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		if (!this.staffRepo.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		this.staffRepo.save(staff);
	}
	
	public void deleteStaff(Integer id) {
		this.staffRepo.deleteById(id);
	}
}
