/**
 *  File: SynonymReader.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-20      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.synonym;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

/**
 * @author Jason_wang
 * 
 */
public class SynonymReader {

	private String fileName;

	public SynonymReader(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 读取关键字配置
	 * 
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public Map<String, String[]> readConfig() throws IOException,
			FileNotFoundException {

		List<String> lines = IOUtils.readLines(new FileInputStream(fileName),
				"UTF-8");
		Map<String, String[]> map = new HashMap<String, String[]>();
		String[] names = null;
		List<String> temps = null;
		for (String line : lines) {
			names = line.trim().split(",");
			for (String name : names) {
				if (name == null || name.trim().length() == 0) {
					continue;
				}
				temps = new ArrayList<String>();
				for (String temp : names) {
					if (!name.equals(temp)) {
						temps.add(temp);
					}
				}
				map.put(name, temps.toArray(new String[temps.size()]));
			}
		}
		return map;
	}
}
