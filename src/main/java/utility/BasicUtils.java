package utility;

import java.util.Collection;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class BasicUtils {

	public static boolean isEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty() || collection.size() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method returns true of the map is null or is empty.
	 * 
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		if (map == null || map.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * 
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * 
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() == 0 || string.trim().equalsIgnoreCase("null")) {
			return true;
		}
		return false;
	}
}
