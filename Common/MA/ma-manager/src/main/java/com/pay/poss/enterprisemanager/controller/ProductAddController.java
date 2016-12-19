package com.pay.poss.enterprisemanager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.enterprisemanager.dto.ProductDto;
import com.pay.poss.enterprisemanager.enums.ActivationModeEnum;
import com.pay.poss.enterprisemanager.enums.ProductAllowObjectEnum;
import com.pay.poss.enterprisemanager.enums.ProductStatusEnum;
import com.pay.poss.enterprisemanager.enums.ProductTypeEnum;
import com.pay.poss.enterprisemanager.enums.ProductTypeOrderEnum;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.membermanager.service.ProductConfigService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * Date				Author			Changes
 * 2012-7-16			DDR				Create
 */
public class ProductAddController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(ProductAddController.class);
	private IEnterpriseService enterpriseService ;	
	private ProductConfigService productConfigService;
	
	
	
	public ProductAddController(){
		setCommandClass(Product.class);
	}
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("ProductAddController.referenceData() is running...");
    	Map<String,Object> dataMap = this.initData();
    	Long proid = ServletRequestUtils.getLongParameter(request,"productId",-1L);
    	if(proid!=-1L){
    		Product pd =  productConfigService.getByProductId(proid);
    		dataMap.put("product", pd);
    	}
     	return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("ProductAddController.onSubmit() is running...");
    	
		Product product = (Product)command;
		String sign = this.enterpriseService.saveOrUpdateProduct(product);
    	Map<String,Object> dataMap = this.initData();	    	    
    	dataMap.put("sign", sign);
    	log.info(sign);
    	int addAgain = ServletRequestUtils.getIntParameter(request, "addAgain",0);
    	if(addAgain==1){
    		if(sign!=null && !sign.contains("成功")){
    			dataMap.put("product", product);
    		}
    		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
    	}
    	response.setCharacterEncoding("utf-8");
    	response.getWriter().write(sign);
    	return null;
		
		          
   }
    
   private Map<String,Object> initData(){
	   Map<String,Object> dataMap = new HashMap<String,Object>();
	   ProductAllowObjectEnum[] allowObjectEnum = ProductAllowObjectEnum.values();
	   ActivationModeEnum[] activationModeEnum = ActivationModeEnum.values();
	   ProductStatusEnum[] productStatusEnum = ProductStatusEnum.values();
	   dataMap.put("productStatusEnum", productStatusEnum);
	   dataMap.put("allowObjectEnum", allowObjectEnum);
	   dataMap.put("productTypeOrderEnum", ProductTypeOrderEnum.values());
	   dataMap.put("activationModeEnum", activationModeEnum);
	   return dataMap;
    }

public void setEnterpriseService(IEnterpriseService enterpriseService) {
	this.enterpriseService = enterpriseService;
}

public void setProductConfigService(ProductConfigService productConfigService) {
	this.productConfigService = productConfigService;
}
    

	
	
	
	
}
