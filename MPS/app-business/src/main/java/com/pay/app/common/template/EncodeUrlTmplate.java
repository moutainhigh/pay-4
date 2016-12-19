package com.pay.app.common.template;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.pay.app.base.session.RequestLocal;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class EncodeUrlTmplate implements TemplateMethodModel {

	
	public Object exec(List arguments) throws TemplateModelException {
		HttpServletResponse response = (HttpServletResponse) RequestLocal.getResponse();
		if (arguments.size() != 1) // 限定方法中必须且只能传递一个参数
		{
			return null;
		}
		return response.encodeURL((String) arguments.get(0));
	}

}
