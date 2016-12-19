/**
 * 
 */
package com.pay.gateway.controller.paylink;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.chain.model.PayLink;
import com.pay.fi.chain.model.PayLinkAttrib;
import com.pay.fi.chain.service.PayLinkService;
import com.pay.fi.chain.util.PaylinkCountryEnum;
import com.pay.util.StringUtil;

/**
 * 支付链webgate重定向Controller
 * @author PengJiangbo
 *
 */
public class PayLinkPayToViewController extends MultiActionController {
	
	private String indexView ;
	private String invalidView ;
	
	private PayLinkService payLinkService ;
	
	/**
	 * 支付链有效首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(final HttpServletRequest request, final HttpServletResponse response){
		//String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String pathTail = StringUtil.null2String(request.getParameter("pathTail"));
		String shoptermsName = StringUtil.null2String(request.getParameter("shoptermsName")) ;
		String payLinkName = StringUtil.null2String(request.getParameter("payLinkName")) ;
		String orderId = StringUtil.null2String(request.getParameter("orderId")) ;
		PayLink payLink = this.payLinkService.findPayLinkByName(payLinkName) ;
		if(null == payLink){
			payLink = new PayLink() ;
		}
		Long payLinkNo = payLink.getPaylinkNo();
		List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(payLinkNo) ;
		String currencyCode = "" ;
		if(CollectionUtils.isNotEmpty(payLinkAttribs)){
			currencyCode = payLinkAttribs.get(0).getCurrencyCode() ; 
		}
		PaylinkCountryEnum[] countries = PaylinkCountryEnum.values() ;
		return new ModelAndView(indexView).addObject("pathTail", pathTail).addObject("shoptermsName", shoptermsName)
				.addObject("payLink", payLink).addObject("payLinkAttribs", payLinkAttribs)
				.addObject("orderId", orderId)
				.addObject("currencyCode", currencyCode)
				.addObject("countries", countries);
	}	
	/**
	 * 支付链无效首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView invalid(final HttpServletRequest request, final HttpServletResponse response){
		
		return new ModelAndView(invalidView) ;
	}
	
	//加载基本信息
//	public PayLinkBaseInfo loadBaseInfo(final Long memberCode){
//		PayLinkBaseInfo payLinkBaseInfo = null ;
//		payLinkBaseInfo = this.payLinkService.payLinkBaseInfoQuery(memberCode) ;
//		if(null == payLinkBaseInfo)
//			payLinkBaseInfo = new PayLinkBaseInfo() ;
//		return payLinkBaseInfo ;
//	}
	//setter--------------------------------------------
	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}
	public void setInvalidView(final String invalidView) {
		this.invalidView = invalidView;
	}
	public void setPayLinkService(final PayLinkService payLinkService) {
		this.payLinkService = payLinkService;
	}
	
}
