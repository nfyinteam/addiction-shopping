package edu.nf.shopping.util;

import java.util.UUID;

public class UUIDUtils {

	public static String createUUID() {
		String uuid=UUID.randomUUID().toString();
		
		uuid = uuid.replace("-", "").toUpperCase();
		return uuid.substring(0,20);
		
	}
	
	public static String createExtractionCode() {
		String uuid=UUID.randomUUID().toString();
		
		uuid = uuid.replace("-", "").toLowerCase();
		return uuid.substring(0,4);
		
	}
}
