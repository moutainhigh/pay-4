package com.pay.task;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class PropertiesMap {
	public static Map<String, String> maps = new HashMap<String, String>();

	public static Map<String, String> getMaps() {
		return maps;
	}

	public static void setMaps(Map<String, String> maps) {
		PropertiesMap.maps = maps;
	}
	

	

}
