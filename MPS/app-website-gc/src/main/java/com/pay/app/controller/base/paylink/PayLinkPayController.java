///**
// * 
// */
//package com.pay.app.controller.base.paylink;
//
//import java.sql.Timestamp;
//import java.util.Date;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
//
//import com.pay.app.common.helper.AppConf;
//import com.pay.fi.chain.model.PayLink;
//import com.pay.fi.chain.model.PayLinkAttrib;
//import com.pay.fi.chain.service.PayLinkService;
//import com.pay.fi.chain.util.O2oEnum;
//import com.pay.util.StringUtil;
//
///**
// * 根据支付链接支付Controller
// * @author PengJiangbo
// *
// */
//public class PayLinkPayController extends MultiActionController {
//
//	private static final Log logger = LogFactory.getLog(PayLinkPayController.class) ;
//	private String indexView ;
//	//private String noExistView ;
//	private String invalidView ;
//	
//	private PayLinkService payLinkService ;
//	/**
//	 * 支付链支付默认页面
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
//		
//		String nPara = StringUtil.null2String(request.getParameter("n")) ;
//		//无效请求
//		if(null == nPara || "".equals(nPara)){
//			logger.info("无效的支付链接请求...");
//			return new ModelAndView(invalidView) ;
//		}
//		String payLinkName = AppConf.get(AppConf.payLinkUrl) + "?n=" + nPara ;
//		PayLink payLink = this.payLinkService.findPayLinkByName(payLinkName) ;
//		//支付链接不存在
//		if(null == payLink){
//			logger.info("...支付链接不存在！");
//			return new ModelAndView(invalidView) ;
//		}
//		int status = payLink.getStatus() ;
//		Timestamp invalidTime = payLink.getInvalidTime() ;
//		//失效时间
//		long longInvalidTime = invalidTime.getTime() ;
//		long longNowTime = new Date().getTime() ;
//		//状态无效或当前时间超过支付链接的失效时间为无效支付链接
//		if((status != O2oEnum.payLinkEffective.getStatus()) || (longNowTime > longInvalidTime)){
//			logger.info("...支付链接已[失效]！");
//			return new ModelAndView(invalidView) ;
//		}
//		Long payLinkNo = payLink.getPaylinkNo() ;
//		List<PayLinkAttrib> payLinkAttribs = this.payLinkService.findPayLinkAttribsByPayLinkNo(payLinkNo) ;
//		return new ModelAndView(indexView).addObject("payLink", payLink)
//				.addObject("payLinkAttribs", payLinkAttribs);
//		
//	}
//	
//	public void setPayLinkService(PayLinkService payLinkService) {
//		this.payLinkService = payLinkService;
//	}
//
//	public void setIndexView(String indexView) {
//		this.indexView = indexView;
//	}
//
//	public void setInvalidView(String invalidView) {
//		this.invalidView = invalidView;
//	}
//	
//}
