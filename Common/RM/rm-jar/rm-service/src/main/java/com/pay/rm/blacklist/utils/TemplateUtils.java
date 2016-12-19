package com.pay.rm.blacklist.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateUtils {

	private static Log logger = LogFactory.getLog(TemplateUtils.class);

	/**
	 * 装配XML报文
	 * 
	 * @param filePath
	 * @param param
	 * @return
	 */
	public static String getTemplate(String filePath, Map<String, String> param) {
		Configuration cfg = new Configuration();
		Template template = null;
		String xmlValue = null;
		cfg.setClassForTemplateLoading(TemplateUtils.class, "/");
		cfg.setDefaultEncoding("UTF-8");
		try {
			template = cfg.getTemplate(filePath);
			xmlValue = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, param);
		} catch (IOException e) {
			logger.error("FreeMarker解析装配发生异常", e);
		} catch (TemplateException e) {
			logger.error("FreeMarker解析装配发生异常", e);
		}
		return xmlValue;
	}
}
