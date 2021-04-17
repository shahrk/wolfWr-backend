package models;

import java.util.List;

import com.ncsu.wolfwr.entity.Transaction;

import lombok.Data;

@Data
public class TransactionPOJO {
	
	private Transaction transactionDetails;
	private List<TransactionMerchandiseDetails> merchList;
	
}
