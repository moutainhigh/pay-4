/**
 *  File: XmlUtils.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Author   Changes     Date               
 *  Sandy    Create      2012-3-31         
 *
 */
package com.pay.rm.blacklist.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Sandy
 * @Date 2012-3-31下午2:09:26
 * @Description
 */
public class XmlUtils {

	private static Log logger = LogFactory.getLog(XmlUtils.class);

	public static Map<String, String> xml2Map(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			logger.error(e);
		}
		getElementList(doc.getRootElement(), map);
		return map;
	}

	@SuppressWarnings("rawtypes")
	private static void getElementList(Element element, Map<String, String> map) {
		List elements = element.elements();
		if (elements.size() == 0) {
			String xpath = ""; //
			if (map.get(getNames(element.getName())) != null) {
				xpath = getNames(element.getParent().getName()) + "-"
						+ getNames(element.getName());
			} else {
				xpath = getNames(element.getName());
			}
			String value = element.getTextTrim();
			map.put(xpath, value);
		} else {
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				getElementList(elem, map);
			}
		}
	}

	private static String getNames(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}

	public static String processXml(String xml) {
		return xml.substring(7, xml.length());
	}

}
