package models;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerSignupPOJO {
	private Integer tierId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	private String homeAddress;
	private Boolean membershipStatus;
	private Float rewardPoints;
	private Boolean membershipAmountPaid;
	private Integer storeId;
	private Integer staffId;
	private Date signupDate = new Date();
}
