package com.pay.poss.enterprisemanager.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.enterprisemanager.enums.ProductTypeOrderEnum;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.membermanager.service.ProductConfigService;
/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create
 *          Author Changes 2012-5-13 ddr update
 */

public class ProductOfEnterpriseEditController extends SimpleFormController {

	private Log log = LogFactory.getLog(ProductOfEnterpriseEditController.class);
	private MemberQueryService memberQueryService;
	private ProductConfigService productConfigService;
	
	
	
	
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setProductConfigService(ProductConfigService productConfigService) {
		this.productConfigService = productConfigService;
	}

	/**
	 * 用户配置产品页面
	 * @author ddr 
	 * 2012-5-29
	 */
	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("AccountOfEnterpriseEditController.referenceData is running...");
		
		String memberCode = request.getParameter("memberCode");
		String type = request.getParameter("type");
		
		MemberInfoDto midto =  memberQueryService.doQueryMemberInfoNsTx(null, new Long(memberCode), null, null);
		List<Product> curProducts = productConfigService.getMemberProducts(new Long(memberCode));
		List<List<Menu>> baseTrees =   productConfigService.getBaseProductTrees(midto.getMemberType());
//		List<Product> defaultProducts =   productConfigService.getDefaultProducts(midto.getMemberType());
		List<Product> extraProducts =   productConfigService.getExtraProducts(midto.getMemberType());
		
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		
		dataMap.put("memberInfo",midto);
		dataMap.put("baseTrees",baseTrees);
		dataMap.put("curProducts",curProducts);
//		dataMap.put("defaultProducts",defaultProducts);
		dataMap.put("extraProducts",extraProducts);	
		dataMap.put("type",type);
		dataMap.put("productTypeOrderEnum", ProductTypeOrderEnum.values());
		log.debug("AccountOfEnterpriseEditController.referenceData is end");
		return dataMap;
	}
	
	//ddr 修改 2012 -5- 29
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		
		//add  by ddr
		log.debug("AccountOfEnterpriseEditController.onSubmit is running...");
		String memberCode = request.getParameter("memberCode");
		String productIds[] = ServletRequestUtils.getStringParameters(request, "product");
		String sign ="";
		Boolean isSuccess = false;
		if(memberCode==null||memberCode.trim().length()==0||productIds==null ||productIds.length==0){
			sign = "无用户或产品列表为空";
		}else{
			try{
				isSuccess = productConfigService.createMemberProductRdTx(new Long(memberCode), productIds);
				sign=isSuccess?"S":"修改失败";
			}catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				sign = "后台操作出现异常..";
			}
		}
		log.info("配置用户产品信息...状态："+sign);
		
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(sign);
		return null;
		//end ddr;
		
		
//		List<BaseData> acctTempOfEnterpriseList = enterpriseService.queryAcctTempByMemberCode(memberCode);
//		List<Relation> allProductOfAcctTempList = new ArrayList<Relation>();
//		for(int i=0;i<acctTempOfEnterpriseList.size();i++){
//			BaseData base= acctTempOfEnterpriseList.get(i);
//			List<Relation> productOfAcctTempList =  enterpriseService.queryProductOfAcctTemp(base.getCode());
//			allProductOfAcctTempList.addAll(productOfAcctTempList);
//		}
//		List<BaseData> productOfEnterpriseList = enterpriseService.queryProductByMemberCode(memberCode);
//		EnterpriseSearchListDto enterprise = enterpriseService.queryEnterpriseByMemberCode(memberCode);
//		
//		Map<String,Object> dataMap = new Hashtable<String,Object>();
//		dataMap.put("enterprise",enterprise);
//		dataMap.put("sign",sign);
//		dataMap.put("acctTempOfEnterpriseList",acctTempOfEnterpriseList);
//		dataMap.put("allProductOfAcctTempList",allProductOfAcctTempList);
//		dataMap.put("productOfEnterpriseList",productOfEnterpriseList);
//		dataMap.put("type",request.getParameter("type"));
//		
//		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
		
	}

	

	
}
