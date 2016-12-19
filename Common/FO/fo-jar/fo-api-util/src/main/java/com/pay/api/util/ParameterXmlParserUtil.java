/**
 *  File: ParameterXmlParserUtil.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 15, 2011   ch-ma     Create
 *
 */
package com.pay.api.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.api.helper.AuditFlag;
import com.pay.api.helper.CurrencyCode;
import com.pay.api.helper.FeeType;
import com.pay.api.helper.PayType;
import com.pay.api.helper.PayeeType;

/**
 * payement api parser.
 */
public class ParameterXmlParserUtil {

	private static Log logger = LogFactory.getLog(ParameterXmlParserUtil.class);

	public static String getNodeText(String parameterXml, String nodeName) {

		if (null == parameterXml) {
			return parameterXml;
		}

		StringBuilder startNode = new StringBuilder("<");
		startNode.append(nodeName);
		startNode.append(">");

		StringBuilder endNode = new StringBuilder("</");
		endNode.append(nodeName);
		endNode.append(">");

		int start = parameterXml.indexOf(startNode.toString());

		int end = parameterXml.indexOf(endNode.toString());

		String xml = null;
		if (-1 != start && -1 != end) {
			xml = parameterXml.substring(start, end + endNode.length());
		}
		
		xml = xml.replaceAll("\\s*", "");

		return xml;
	}

	/**
	 * parser a XML form root node is PayPlatRequestParameter.
	 * 
	 * @param <T>
	 * @param targetClass
	 * @param transferMap
	 *            , if node & property need transfer,you can set elements in
	 *            <code>transferMap</code>.
	 * @param parameterXml
	 * @return
	 */
	public static <T> T parser(Class<T> targetClass,
			Map<String, String> transferMap, String parameterXml) {

		// parameterXml = xmlFormat(parameterXml);

		logger.info("xml data:" + parameterXml);

		if (null == targetClass) {
			return null;
		}

		Document document;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new ByteArrayInputStream(parameterXml
					.getBytes()));
			return parser(targetClass, transferMap, document.getRootElement());
		} catch (Exception e) {
			logger.error("xml parser error:", e);
		}
		return null;
	}

	/**
	 * 
	 * @param <T>
	 * @param targetClass
	 * @param transferMap
	 * @param parameterXml
	 * @param rootNode
	 * @return
	 */
	public static <T> T parser(Class<T> targetClass,
			Map<String, String> transferMap, String parameterXml,
			String rootNode) {

		// logger.info("xml data:" + parameterXml);

		if (null == targetClass) {
			return null;
		}

		Document document;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new ByteArrayInputStream(parameterXml
					.getBytes()));
			Element element = document.getRootElement().element(rootNode);
			return parser(targetClass, transferMap, element);
		} catch (Exception e) {
			logger.error("xml parser error:", e);
		}
		return null;
	}

	/**
	 * 
	 * @param <T>
	 * @param targetClass
	 * @param transferMap
	 * @param parameterXml
	 * @param rootNode
	 * @return
	 */
	public static <T> List<T> parserList(Class<T> targetClass,
			Map<String, String> transferMap, String parameterXml,
			String rootNode) {

		// logger.info("xml data:" + parameterXml);

		if (null == targetClass) {
			return null;
		}

		Document document;
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(new ByteArrayInputStream(parameterXml
					.getBytes()));
			Element rootEle = document.getRootElement().element(rootNode);
			List<Element> elements = rootEle.elements();
			List<T> targets = null;
			if (null != elements && !elements.isEmpty()) {
				targets = new ArrayList<T>();
				for (Element element : elements) {
					targets.add(parser(targetClass, transferMap, element));
				}
			}
			return targets;
		} catch (Exception e) {
			logger.error("xml parser error:", e);
		}
		return null;
	}

	private static <T> T parser(Class<T> targetClass,
			Map<String, String> transferMap, Element rootElement) {
		try {
			List<Element> para = rootElement.elements();
			Iterator<Element> it = para.iterator();
			T targetObject = targetClass.newInstance();
			BeanWrapper sourceBw = new BeanWrapperImpl(targetObject);
			while (it.hasNext()) {
				Element element = (Element) it.next();
				String property = element.getName();
				if (null != transferMap && !transferMap.isEmpty()) {
					if (null != transferMap.get(property)) {
						property = transferMap.get(property);
					}
				}
				if (isExistsProperty(sourceBw, property)) {
					sourceBw.setPropertyValue(property, element.getText());
				}
			}
			return targetObject;
		} catch (Exception e) {
			logger.error("xml parser error:", e);
		}
		return null;
	}

	private static boolean isExistsProperty(BeanWrapper sourceBw,
			String property) {
		try {
			PropertyDescriptor propertyDescriptor = sourceBw
					.getPropertyDescriptor(property);
			if (null != propertyDescriptor) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	public static String xmlFormat(String xml) {

		String outStr = xml;
		try {
			SAXReader reader = new SAXReader();

			StringReader in = new StringReader(xml);
			Document doc = reader.read(in);
			OutputFormat formater = OutputFormat.createPrettyPrint();
			formater.setEncoding("utf-8");
			StringWriter out = new StringWriter();
			XMLWriter writer = new XMLWriter(out, formater);
			writer.write(doc);
			outStr = out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outStr;
	}

	public static void main(String[] args) {

		String xml = generateXml("100001", "BX22333");

		String result = getNodeText(xml, "REQUEST_BODY");

		System.out.print(result);

		// parser(Pay2AcctRequest.class, null, xml.toString());
	}

	public static String generateXml(String merchantCode, String bizNo) {

		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<PayPlatRequestParameter>");
		xml.append("<REQUEST_HEADER>");
		xml.append("<MERCHANT_CODE>" + merchantCode + "</MERCHANT_CODE>");
		xml.append("<BIZ_NO>" + bizNo + "</BIZ_NO>");
		xml.append("<CURRENCY_CODE>" + CurrencyCode.RMB.getValue()
				+ "</CURRENCY_CODE>");
		xml.append("<TOTAL_AMOUNT>" + 2000 + "</TOTAL_AMOUNT>");
		xml.append("<TOTAL_COUNT>" + 2 + "</TOTAL_COUNT>");
		xml.append("<AUDIT_FLAG>" + AuditFlag.NO.getValue() + "</AUDIT_FLAG>");
		xml.append("<FEE_TYPE>" + FeeType.PAYER.getValue() + "</FEE_TYPE>");
		xml.append("<PAY_TYPE>" + PayType.BANK.getValue() + "</PAY_TYPE>");
		xml.append("<REQUEST_TIME>" + "20111223111450" + "</REQUEST_TIME>");
		xml.append("<VERSION>" + "V1.0" + "</VERSION>");
		xml.append("<SIGNVALUE>" + "V1.0" + "</SIGNVALUE>");
		xml.append("</REQUEST_HEADER>");

		xml.append("<REQUEST_BODY>");

		for (int i = 0; i < 5; i++) {
			xml.append("<PAY_ITEM>");
			xml.append("<ORDER_ID1></ORDER_ID1>");
			xml.append("<ORDER_ID>");
			xml.append(System.currentTimeMillis());
			xml.append("</ORDER_ID>");
			xml.append("<PAYEE_NAME>" + "马超湖" + "</PAYEE_NAME>");
			xml.append("<PAYEE_ACCOUNT>" + "356889110911712" + i
					+ "</PAYEE_ACCOUNT>");
			xml.append("<AMOUNT>1000</AMOUNT>");
			xml.append("<PAYEE_MOBILE>15921167187</PAYEE_MOBILE>");
			xml.append("<NOTE>15921167187</NOTE>");
			xml.append("<REMARK>test</REMARK>");
			xml.append("<BANK_NAME>中国工商银行</BANK_NAME>");
			xml.append("<PROVINCE>上海市</PROVINCE>");
			xml.append("<CITY>上海市</CITY>");
			xml.append("<BRANCHE>中国工商银行上海市三林支行</BRANCHE>");
			xml.append("<PAYEE_TYPE>" + PayeeType.INDIVIDUAL.getValue()
					+ "</PAYEE_TYPE>");
			xml.append("</PAY_ITEM>");
		}
		xml.append("</REQUEST_BODY>");
		xml.append("</PayPlatRequestParameter>");

		return ParameterXmlParserUtil.xmlFormat(xml.toString());
	}
}
