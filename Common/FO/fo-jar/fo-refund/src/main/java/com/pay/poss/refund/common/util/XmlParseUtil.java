/**
 *  File: XmlParseUtil.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-23    jason_wang      Changes
 *  
 *
 */
package com.pay.poss.refund.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author Jason_wang
 *
 */
public class XmlParseUtil {

	/**
	 * 解析工作流流程定义文件
	 * @param is
	 * @return
	 * @throws DocumentException 
	 */
	public static Map<String,Object> parseWorkflowFile(InputStream is) throws DocumentException{
		//实例化解析器
		SAXReader reader = new SAXReader();
		//读取文件
		Document doc = reader.read(is);
		//获取根节点
		Element element = doc.getRootElement();
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Attribute attr = element.attribute("key");
		resultMap.put("workflowKey",attr.getValue());
		
		List<String> nodeList = new ArrayList<String>();
		Element temp = null;
		for(Iterator<Element> list = element.elementIterator("task");list.hasNext();){
			temp = list.next();
			attr = temp.attribute("name");
			nodeList.add(attr.getValue());
		}
		resultMap.put("nodeList",nodeList);
		return resultMap;
	}
}
