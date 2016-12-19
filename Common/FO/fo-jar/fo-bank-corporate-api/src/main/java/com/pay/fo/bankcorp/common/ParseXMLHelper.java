/**
 *  File: GParseDoc.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2012-2-15   Sandy       Create
 *
 */
package com.pay.fo.bankcorp.common;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;
import com.pay.fo.bankcorp.dto.RespDTO;
import com.pay.util.DateUtil;

/**
 * doc2map
 */
public class ParseXMLHelper {
	
	@SuppressWarnings("rawtypes")
	public static Object doc2Model(String xml,Class clazz) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		BeanWrapper obj = new BeanWrapperImpl(clazz);
		parseElement(doc.getRootElement(), clazz, obj);
		
		return  obj.getWrappedInstance();
	}
	
	@SuppressWarnings("rawtypes")
	private  static void parseElement(Element element,Class clazz,BeanWrapper obj) {
		
		
		
		List elements = element.elements();
		if (elements.size() == 0) {
			String name = getName(element.getName()); //
			String value = element.getTextTrim();
			
			obj.setPropertyValue(name, parseValue(obj, name, value));
		}else{
			for (Iterator it = elements.iterator(); it.hasNext();) {
				Element elem = (Element) it.next();
				parseElement(elem, clazz, obj);
			}
		}
	}
	
	private static  Object parseValue(BeanWrapper obj,String name,String value){
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getPropertyType(name);
		if(Integer.class.equals(clazz)){
			return StringUtils.isEmpty(value)?null:Integer.valueOf(value);
		}
		if(Long.class.equals(clazz)){
			return StringUtils.isEmpty(value)?null:Long.valueOf(value);
		}
		if(Date.class.equals(clazz)){
			return StringUtils.isEmpty(value)?null:DateUtil.parse("yyyy-MM-dd HH:mm:ss", value);
		}
		return value;
	}

	private static   String getName(String name) {
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Msg><TradeOrderId>123</TradeOrderId><UpdateDate>2012-03-31 09:20:00</UpdateDate><OrderStatus>112</OrderStatus><BankOrderId>456</BankOrderId><FailedReason>测试</FailedReason><TransCode>0001</TransCode><RtnCode>0000</RtnCode><RtnInfo>测试</RtnInfo></Msg>";
		RespDTO dto = (BankCorpPaymentRespDTO) ParseXMLHelper.doc2Model(xml, BankCorpPaymentRespDTO.class);
		//dto.setOrderStatus(111);
		//dto.setFailedReason(null);
		System.out.println(TemplateHelper.generateRespDoc("0001", dto));
	}
}
