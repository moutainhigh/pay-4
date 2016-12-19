/**
 *  File: FreeMarkParseUtil.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-8-27   terry_ma     Create
 *
 */
package com.pay.fundout.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemark转换类.
 */
public class FreeMarkParseUtil {

	public static String parseTemplate(String template, Map dataMap) {

		Configuration conf = new Configuration();
		StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
		stringTemplateLoader.putTemplate("_default_http_param_template_key",
				template);
		conf.setTemplateLoader(stringTemplateLoader);
		conf.setDefaultEncoding("UTF-8");
		Template t = null;
		try {
			t = conf.getTemplate("_default_http_param_template_key");
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringWriter stringWriter = new StringWriter();
		try {
			t.process(dataMap, stringWriter);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}
}
