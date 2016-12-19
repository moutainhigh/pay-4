package com.pay.poss.enterprisemanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.util.StringUtil;


public class ProductForMenuController extends MultiActionController {
	private Log log = LogFactory.getLog(ProductForMenuController.class);
	private IEnterpriseService enterpriseService ;
	private String delPassword = "ddr";
	
	public String getDelPassword() {
		return delPassword;
	}
	public void setDelPassword(String delPassword) {
		this.delPassword = delPassword;
	}
	//删除
	public ModelAndView delProduct(HttpServletRequest request,
		            HttpServletResponse response)throws Exception{
		String checkPassword = ServletRequestUtils.getStringParameter(request, "checkPassword"); 
		String id = ServletRequestUtils.getStringParameter(request, "id");
		String result = "";
		response.setContentType("text/plain;charset=UTF-8");
		if(StringUtil.isEmpty(id)){
			result = "删除的产品ID号为空，无法定位删除的记录";
			response.getWriter().write(result);
			 return null;
		}
		if(!delPassword.equals(checkPassword)){
			result = "密码不正确，输入正确的密码才能删除";
			response.getWriter().write(result);
			return null;
		}else{
			if(enterpriseService.productDeleteFinal(id)){
				result = "S";
				response.getWriter().write(result);
				return null;
			}else{
				result = "删除产品失败,不存在对应的数据或已被删除了";
				response.getWriter().write(result);
				return null;
			}
		}

	}
	public IEnterpriseService getEnterpriseService() {
		return enterpriseService;
	}
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
}
