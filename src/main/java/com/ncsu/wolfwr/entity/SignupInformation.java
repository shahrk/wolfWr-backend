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
import models.CustomerSignupPOJO;

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
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="EST")
	private Date signupDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="EST")
	private Date endDate;
	
	public SignupInformation (CustomerSignupPOJO c) {
		this.storeId = c.getStoreId();
		this.staffId = c.getStaffId();
		this.tierId = c.getTierId();
		this.signupDate = c.getSignupDate();
	}
}
