/**
 * 
 */
package com.pay.app.common.template;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.RequestLocal;
import com.pay.app.common.util.ImgUrlUtil;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * @author fjl
 *
 */
public class UploadFileContextTemplate implements TemplateMethodModel {
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		
		HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
		
		return ImgUrlUtil.getContextPath(request);
	}

}
