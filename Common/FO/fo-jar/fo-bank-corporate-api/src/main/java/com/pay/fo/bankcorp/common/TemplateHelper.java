package com.pay.fo.bankcorp.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXValidator;
import org.dom4j.util.XMLErrorHandler;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateHelper {
	
	private final static String txnCodeTemplate = "\\{txnCode\\}";
	
	private final static String ReqNamespaceNode = "<Msg xmlns=\"http://www.pay.com/bank-corp/{txnCode}-Req\">";
	
	private final static String RespNamespaceNode = "<Msg xmlns=\"http://www.pay.com/bank-corp/{txnCode}-Rsp\">";
	
	private final static String schemaLanguage = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
	
	private final static String XMLSchema = "http://www.w3.org/2001/XMLSchema";
	
	private final static String schemaSource = "http://java.sun.com/xml/jaxp/properties/schemaSource";
	
	private final static String REQXSDLocation = "/opt/pay/config/fo/bank-corp/schema/{txnCode}-Req.xsd";
	
	private final static String RESPXSDLocation = "/opt/pay/config/fo/bank-corp/schema/{txnCode}-Rsp.xsd";
	
	private final static String RESPTemplateLocation = "/template/{txnCode}-Rsp.xml";
	
	private final static String REQtemplateLocation = "/template/{txnCode}-Req.xml";
	
	
	
	/**
	 * 验证请求报文
	 * @param xmlValue
	 * @param txnCode
	 * @return
	 * @throws Exception
	 */
	public static boolean validateRequest(String xmlValue,String txnCode) throws Exception {

		return validate(xmlValue, txnCode, REQXSDLocation,ReqNamespaceNode);
	}
	
	/**
	 * 验证响应报文
	 * @param xmlValue
	 * @param txnCode
	 * @return
	 * @throws Exception
	 */
	public static boolean validateResponse(String xmlValue,String txnCode) throws Exception {

		return validate(xmlValue, txnCode, RESPXSDLocation,RespNamespaceNode);
	}
	
	private static boolean validate(String xmlValue,String txnCode,String xsdLocation,String namespaceNode) throws Exception {

		xmlValue = xmlValue.replaceAll("<Msg>",namespaceNode.replaceAll(txnCodeTemplate, txnCode));

		Document doc = DocumentHelper.parseText(xmlValue);
		XMLErrorHandler errorHandler = new XMLErrorHandler();

		// 获取基于 SAX 的解析器的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 解析器在解析时验证 XML 内容。
		factory.setValidating(true);

		
		factory.setNamespaceAware(true);

		// 使用当前配置的工厂参数创建 SAXParser 的一个新实例。
		SAXParser parser = factory.newSAXParser();

		parser.setProperty(schemaLanguage,XMLSchema);
		String filePath =xsdLocation.replaceAll(txnCodeTemplate, txnCode);
		System.out.println("xsd:" + filePath);
		parser.setProperty(
				schemaSource, "file:"
						+ filePath);

		// 创建一个SAXValidator校验工具，并设置校验工具的属性
		SAXValidator validator = new SAXValidator(parser.getXMLReader());
		// 设置校验工具的错误处理器，当发生错误时，可以从处理器对象中得到错误信息。
		validator.setErrorHandler(errorHandler);
		// 校验
		validator.validate(doc);

		if (errorHandler.getErrors().hasContent()) {
			System.out.println("XML文件通过XSD文件校验失败.");
			System.out.println(errorHandler.getErrors().asXML());
			return false;
		} else {
			System.out.println("XML文件通过XSD文件校验成功.");
			return true;
		}
	}
	
	/**
	 * 生成业务响应报文
	 * @param txnCode  交易代码
	 * @param obj      参数对象
	 * @return
	 */
	public static String generateRespDoc(String txnCode,Object obj){
		
		return generateDoc(txnCode,obj,RESPTemplateLocation);
	}
	
	
	/**
	 * 生成业务请求报文
	 * @param txnCode  交易代码
	 * @param obj      参数对象
	 * @return
	 */
	public static String generateReqDoc(String txnCode,Object obj){
		return generateDoc(txnCode,obj, REQtemplateLocation);
	}
	
	
	/**
	 * 生成错误参数Document
	 * @param code
	 * @param desc
	 * @return
	 */
	public static String generateErrorDoc(String code,String desc) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Msg");
		root.addElement("TransCode").setText("9999");
		root.addElement("RtnCode").setText(code);
		root.addElement("RtnInfo").setText(desc);
		return doc.asXML();
	}

	/**
	 * 
	 * @param txnCode
	 * @param obj
	 * @param templateLocation
	 * @return
	 */
	private static String generateDoc(String txnCode,Object obj,String templateLocation){
		Configuration cfg = new Configuration();
		Template template = null;
		String xmlValue = null;
		cfg.setClassForTemplateLoading(TemplateHelper.class, "/");
		cfg.setDefaultEncoding("UTF-8");
		try {
			template = cfg.getTemplate(templateLocation.replaceAll(txnCodeTemplate, txnCode));
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("param", obj);
			//xmlValue = FreeMarkerTemplateUtils.processTemplateIntoString(template, param);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FreeMarker解析装配发生异常");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FreeMarker解析装配发生异常");
		}
		return xmlValue;
	}

}
