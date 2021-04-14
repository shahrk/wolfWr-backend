package models;

import java.util.Map;

import com.ncsu.wolfwr.entity.Return;

import lombok.Data;

@Data
public class ReturnPOJO {

	private Return returns;
	private Map<Integer, Integer> merchCountMap;
}
