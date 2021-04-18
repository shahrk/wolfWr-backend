package com.ncsu.wolfwr.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Staff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "staff_id")
	private Integer staffId;
	
	@Column(name = "store_id")
	private Integer storeId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	private String address;
	
	private String email;
	
	@Column(name = "job_title")
	private String jobTitle;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	private Integer age;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "employment_start_date")
	@JsonFormat(pattern="MM/dd/yyyy")
	private Date employmentStartDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "employment_end_date")
	@JsonFormat(pattern="MM/dd/yyyy")
	private Date employmentEndDate;
	
	
	
	
}
