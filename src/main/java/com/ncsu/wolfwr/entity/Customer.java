package com.ncsu.wolfwr.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Customer implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Integer customerId;
	
	@Column(name = "tier_id")
	private Integer tierId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email_address")
	private String emailAddress;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "home_address")
	private String homeAddress;
	
	@Column(name = "membership_status",columnDefinition = "bool")
	private Boolean membershipStatus;
	
	@Column(name = "rewards_points",columnDefinition = "float4")
	private Float rewardsPoints;
	
	@Column(name = "membership_amount_paid",columnDefinition = "bool")
	private Boolean membershipAmountPaid;
	
	@Transient
	private String membershipLevel;
	
	
}
