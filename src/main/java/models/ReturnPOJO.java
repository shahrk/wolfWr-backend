package models;

import java.util.Map;

import com.ncsu.wolfwr.entity.Returns;

import lombok.Data;

@Data
public class ReturnPOJO {

	private Returns returns;
	private Map<Integer, Integer> merchCountMap;
}
