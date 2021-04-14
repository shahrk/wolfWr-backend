package com.ncsu.wolfwr.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReturnMerchandiseId.class)
public class ReturnContainsMerchandise implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "return_id")
	private Integer returnId;
	
	@Id
	@Column(name = "merchandise_id")
	private Integer merchandiseId;
	
	private Integer quantity;
	
}
