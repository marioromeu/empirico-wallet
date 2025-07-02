package br.com.itads.empirico.util;

import java.util.HashMap;
import java.util.Map;

public class FixUtils {

	private static Map<String, String> fixMap = fillMap();
	
	public static String fix(String key) {
		if (fixMap.containsKey(key)) {
			return fixMap.get(key);
		}
		String fixedKey = key.replaceAll("[^a-zA-Z0-9]", "_");
		fixMap.put(key, fixedKey);
		return fixedKey;
	}

	private static Map<String, String> fillMap() {

		Map<String, String> fixMap = new HashMap<>();
		
		fixMap.put("BRB3SAACNOR6", "B3SA3");
		fixMap.put("BRAMERACNOR6", "AMER3");
		fixMap.put("BRVPPRCTF004", "VPPR11");
		fixMap.put("BRBRAVACNOR3", "BRAV3");
		fixMap.put("BRTVRICTF001", "TVRI11");
		fixMap.put("BRISAEACNPR9", "ISAE4");
		fixMap.put("XPBR31", "XPBR31");
		
		return fixMap;
	}

}
