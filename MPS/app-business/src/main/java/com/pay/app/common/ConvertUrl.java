package com.pay.app.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.common.template.UrlTemplate;

/**
 * @author jim_chen
 * @version 2010-10-26
 */
public class ConvertUrl {

	private static String urlSeparator = "/";
	private static String postfix = ".htm";
	private static String paramSeparator = "-_";
	private static String valueSeparator = "---";
	private static String addpostfix = "/common";
	private static String containMethod = "method";
	private static String patternString = "[a-zA-Z][a-zA-Z_]+---[\\u4E00-\\u9FA5\\uF900-\\uFA2D|a-zA-Z0-9_!@.#%()]*";
	static Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
	private static final Log logger = LogFactory.getLog(UrlTemplate.class);

	public static StringBuffer conversionUrl(String url){
		if(StringUtils.isEmpty(url)){
			return null;
		}
		StringBuffer urlTmp = new StringBuffer(url);
		String param = null;
		if (null != url && url.length() > 0 && url.indexOf("?") != -1) {
			param = url.substring(url.indexOf("?") + 1, url.length());
			urlTmp.delete(url.indexOf("?"), url.length());
		}
		return conversionUrl( urlTmp,  param);
	}
	
	public static StringBuffer conversionUrl(StringBuffer url, String param) {

		if (StringUtils.isNotBlank(param)) {
			String tmp = url.substring(url.lastIndexOf(urlSeparator), url.length());
			url.delete(url.lastIndexOf(urlSeparator), url.length());
			if (url.indexOf("app") > 0 || url.indexOf("corp") > 0 || url.indexOf("validatecode") > 0 || url.indexOf("base")>0) {
				url.append(tmp);
				url.delete(url.lastIndexOf("."), url.length());
			}
			else {
				url.append(addpostfix);
				url.append(tmp);
				url.delete(url.lastIndexOf("."), url.length());
			}
			if (param.indexOf(containMethod) != -1) {
				url.append(includeMethod(param));
			}
			else {
				String del = url.substring(url.lastIndexOf(urlSeparator), url.length());
				url.delete(url.lastIndexOf(urlSeparator), url.length());
				url.append(replaceParam(del, param));
			}
			url.append(postfix);
			logger.debug("UrlTemplate the log  [url :" + url + "]");
			return url;
		}
		else {
			return url;
		}

	}

	public static void compareParam(String del, String param, StringBuffer sb) {
		String[] urlTmps = param.split(valueSeparator);
		if (del.indexOf(urlTmps[0]) != -1) {// 参数存在相同的
			Matcher m = pattern.matcher(del);
			boolean result = m.find();
			while (result) {
				String tmp = m.group();
				if (tmp.indexOf(urlTmps[0]) != -1) {
					m.appendReplacement(sb, param);
					m.appendTail(sb);
				}
				result = m.find();
			}
			if (sb.length() == 0) {
				sb.append(del);
				sb.append(paramSeparator).append(param);
			}
		}
		else {
			if (sb.length() == 0) {
				sb.append(del);
			}
			sb.append(paramSeparator).append(param);
		}
	}

	public static String replaceParam(String del, String params) {
		params = params.replaceAll("=", valueSeparator);
		Matcher m = pattern.matcher(params);
		StringBuffer sb = new StringBuffer();
		boolean result = m.find();
		while (result) {
			String param = m.group();
			if (sb.length() > 0) {
				compareParam(sb.toString(), param, sb.delete(0, sb.length()));
			}
			else {
				compareParam(del, param, sb);
			}
			result = m.find();
		}
		return sb.toString();
	}

	public static String includeMethod(String param) {
		String pater = "method=";// 如果是方法,则去掉method=
		Pattern p = Pattern.compile(pater, Pattern.CASE_INSENSITIVE);
		// 用Pattern类的matcher()方法生成一个Matcher对象
		Matcher m = p.matcher(param);
		StringBuffer sb = new StringBuffer();
		// 使用find()方法查找第一个匹配的对象
		boolean result = m.find();
		// 使用循环找出模式匹配的内容替换之,再将内容加到sb里
		if (result) {
			m.appendReplacement(sb, urlSeparator);
		}
		else {
			sb.append(paramSeparator);
		}
		m.appendTail(sb);		
		return sb.toString().replaceAll("&", paramSeparator).replaceAll("=", valueSeparator);
	}

	public static void main(String[] args) {
		 StringBuffer url = new StringBuffer("http://localhost:8080/website/logout.htm?method=out&mtype=1");

		 System.out.println(ConvertUrl.conversionUrl(url.toString()));
//		String tmp="/website/common/featuremenu/menuNew-_pid---0-_pname---根目录.htm";
//		String tmp = "http://localhost:8080/website/common/featuremenu/menuView-_pid---62-_pname---"a>>é 3/4 aeuè"i 1/4 aa°"i 1/4 -_type---2.htm";
//		tmp="/common/featuremenu/menuNew-_pid---2-_pname---根目录.htm";
//		String sss = "[a-zA-Z][a-zA-Z_]*---[\\u4E00-\\u9FA5\\uF900-\\uFA2D|a-zA-Z0-9_.!@#%]*";
////		String sss = "[a-zA-Z][a-zA-Z_]*---[\\W\\w]*";
//		
//
//		Pattern pattern1 = Pattern.compile(sss, Pattern.CASE_INSENSITIVE);
//		Matcher m = pattern1.matcher(tmp);
//		boolean flag = m.find();
//		while (flag) {
//			System.out.println(m.group());
//			flag = m.find();
//		}
//		System.out.println("over ");


	}
}
