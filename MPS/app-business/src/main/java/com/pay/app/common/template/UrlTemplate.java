package com.pay.app.common.template;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.RequestLocal;
import com.pay.app.common.ConvertUrl;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**将URL转换成伪静态的URL
 * @author jim_chen
 * @version 
 * 2010-10-26 
 */
public class UrlTemplate implements TemplateMethodModel {


	@Override
	public Object exec(List arguments) throws TemplateModelException {
		HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
		String urlTmp = "";
		if (null != arguments && arguments.size() > 0) {
			urlTmp = (String) arguments.get(0);
		}
		StringBuffer url = new StringBuffer(urlTmp);
		String param = null;
		if (null != url && url.length() > 0 && url.indexOf("?") != -1) {
			param = url.substring(url.indexOf("?") + 1, url.length());
			url.delete(url.indexOf("?"), url.length());
		}
		url = ConvertUrl.conversionUrl(url, param);
		return url;
	}

	

	public static void main(String[] args) {		

		StringBuffer url = new StringBuffer("http://localhost:8080/website/logout.htm?method=out&mtype=1");
		String param = null;
		if (null != url && url.length() > 0 && url.indexOf("?") != -1) {
			param = url.substring(url.indexOf("?") + 1, url.length());
			url.delete(url.indexOf("?"), url.length());
		}
		System.out.println(ConvertUrl.conversionUrl(url, param));

	}

}
