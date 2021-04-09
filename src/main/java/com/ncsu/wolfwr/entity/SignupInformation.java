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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class SignupInformation implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "signup_id")
	private Integer signupId;
	
	@Column(name = "store_id")
	private Integer storeId;
	
	@Column(name = "customer_id")
	private Integer customerId;
	
	@Column(name = "staff_id")
	private Integer staffId;
	
	@Column(name = "tier_id")
	private Integer tierId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "signup_date")
	private Date signupDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;
}
