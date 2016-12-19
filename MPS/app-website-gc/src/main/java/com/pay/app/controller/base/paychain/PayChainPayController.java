/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.controller.base.paychain;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ExternalTypeEnum;
import com.pay.base.common.enums.PayChainValidateMsgEnum;
import com.pay.base.common.enums.ProcessStatus;
import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.model.ContextPicture;
import com.pay.base.model.ExternalLog;
import com.pay.base.model.PayChain;
import com.pay.base.service.contextPic.ContextPicService;
import com.pay.base.service.external.ExternalLogService;
import com.pay.base.service.external.ExternalNoticeService;
import com.pay.base.service.paychain.PayChainPayInfoService;
import com.pay.base.service.paychain.PayChainPayService;
import com.pay.base.service.paychain.PayChainService;
import com.pay.util.CheckUtil;
import com.pay.util.FormatDate;

/**
 * 支付链支付
 * @author fjl
 * @date 2011-9-19
 */

public class PayChainPayController extends MultiActionController {
	
	//支付链基础服务
	private PayChainService  payChainService;
	
	//支付链付款信息服务
	private PayChainPayInfoService payChainPayInfoService;
	
	//支付链付款服务
	private PayChainPayService payChainPayService;
	
	//外部交易日志
	private ExternalLogService externalLogService;
	
	//外部交易完成后通知服务
	private ExternalNoticeService externalNoticeService;
	
	private ContextPicService  contextPicService;
	
	private String indexPage;
	
	private String errorPage;
	
	private String payPage;
	
	private String payResultPage;
	
	private String payExcelPage;
	
	private static final String MIX_AMOUNT = "0.01";
	private static final String MAX_AMOUNT = "999999999";
	
	/**
	 * 接收支付链
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ModelAndView view = new ModelAndView(errorPage);
		String q= request.getParameter("n");
		if(StringUtils.isNotBlank(q)){
			String code = null;
			try {
				code = payChainService.decryptChainNum(q);
			} catch (Exception e) {
				//非法链接
				return view;
			}
			//(1)根据code 判断是否合法：未关闭，未过期
			PayChainValidateMsgEnum vme = payChainService.validate(code);
			if(vme.getCode() != PayChainValidateMsgEnum.LEGAL.getCode()){
				//连接不合法
				return view;
			}			
			//(2)根据code 查询支付链
			PayChain payChain = payChainService.getPayChainByChainNum(code);
			//(3)根据code 查询收款方信息
			PayChainPayInfo payChainPayInfo = payChainPayInfoService.get(payChain);
			if(payChainPayInfo == null){
				//支付链不存在。
				return view;
			}
			//(4)跳转到支付页面
			//request.getSession(true).setAttribute("_payChainNum_", payChain.getPayChainNumber());
			//支付链编号加密后放入页面
			
			view = new ModelAndView(indexPage);
			view.addObject("payInfo", payChainPayInfo);
			view.addObject("payChainNum", payChainService.encryptChainNum(payChain.getPayChainNumber()));
			List<ContextPicture>  picList=contextPicService.queryPicListByOwnerId(payChain.getPayChainId());
			view.addObject("picList", picList);
			view.addObject("isNeedLogin",payChainPayInfoService.isNeedLogin(payChainPayInfo.getMcc()));
			return view;
		}
		
		return view;
	}
	
	
	/**
	 * 提交支付信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView submitPay(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		ModelAndView errorview = new ModelAndView(errorPage);
		
		//(1)付款方前台数据校验
	//	Object payChainNum = request.getSession(true).getAttribute("_payChainNum_");
		String method = request.getMethod();
		if(method == null || !"POST".equalsIgnoreCase(method)){
			//用户直接提交数据
			return  errorview;
		}
		
		String payChainNum = "";
		String payChainNumDesc = request.getParameter("payChainNum");
		try {
			payChainNum = payChainService.decryptChainNum(payChainNumDesc);
		} catch (Exception e) {
			//非法链接
			return errorview;
		}
		//(2)根据code 判断是否合法：未关闭，未过期
		PayChainValidateMsgEnum vme = payChainService.validate(payChainNum);
		if(vme.getCode() != PayChainValidateMsgEnum.LEGAL.getCode()){
			//连接不合法
			return errorview;
		}
		
		ModelAndView view = new ModelAndView(indexPage);
		String amountTxt = request.getParameter("amount");
		String payerNameTxt = request.getParameter("payerName");
		String payerEmailTxt = request.getParameter("payerEmail");
		String payerMemoTxt = request.getParameter("payerMemo");
		
		Map<String,String> msg = validate(request);
		PayChain payChain = payChainService.getPayChainByChainNum((String)payChainNum);
		PayChainPayInfo payChainPayInfo = payChainPayInfoService.get(payChain);
		boolean isNeedLogin = payChainPayInfoService.isNeedLogin(payChainPayInfo.getMcc());
		if(msg != null && !msg.isEmpty()){
			msg.put("amount", amountTxt);
			msg.put("payerName", payerNameTxt);
			msg.put("payerEmail", payerEmailTxt);
			msg.put("payerMemo", payerMemoTxt);
			msg.put("payChainNum", payChainNumDesc);
			
			view.addAllObjects(msg);
			view.addObject("payInfo", payChainPayInfo);
			view.addObject("isNeedLogin",isNeedLogin);
			return view;
		}
		
		//非4511 必须登录
		if(isNeedLogin && SessionHelper.getLoginSession() == null){
			//需要登录，如果没有登录跳转到
			return errorview;
		}
		
		//(2)数据验证成功后，插入日志表
		ExternalLog externalLog = new ExternalLog();
		externalLog.setAmount(new BigDecimal(amountTxt));
		externalLog.setCardNo(payChain.getPayChainNumber());
		externalLog.setCreateDate(new Date());
		externalLog.setExternalProcessStatus(null);
		externalLog.setExternalType(ExternalTypeEnum.PAY_CHAIN.getType());
		externalLog.setPayerContact(payerEmailTxt);
		externalLog.setPayerName(payerNameTxt);
		externalLog.setProcessStatus(ProcessStatus.PROCESS_INIT.getValue());
		externalLog.setRemark(payerMemoTxt);
		externalLog.setCurrencyCode(1);
		externalLog.setOrderNo(externalLogService.getOrderNo());
		
		//(3)跳转到收银台
		payChainPayInfo.setOrderNo(externalLog.getOrderNo());
		payChainPayInfo.setAmount(amountTxt);
		payChainPayInfo.setPayerEmail(payerEmailTxt);
		
		if(isNeedLogin && SessionHelper.getLoginSession() != null){
			payChainPayInfo.setPayerEmail(SessionHelper.getLoginSession().getLoginName());
		}
	
		Map<String,String> map =payChainPayService.createPayMap(payChainPayInfo);
		
		externalLogService.createExternalLogRdTx(externalLog);
		
		HashMap<String, String> model =  new HashMap<String, String>() ;
		model.putAll(map);
		String payUrl = model.get("payUrl");
		model.remove("payUrl");
	
		return new ModelAndView(payPage).addObject("payMap",model).addObject("payUrl", payUrl);
	}
	
	private Map<String,String> validate(HttpServletRequest request){
		String amountTxt = request.getParameter("amount");
		String payerNameTxt = request.getParameter("payerName");
		String payerEmailTxt = request.getParameter("payerEmail");
		String payerMemoTxt = request.getParameter("payerMemo");
		
		//初步验证
		Map<String,String> msg = new HashMap<String,String>();
		if(StringUtils.isBlank(amountTxt)){
			
			msg.put("amountTip", MessageConvertFactory.getMessage("paychain.payAmount.notEmpty"));
		}
		if(StringUtils.isBlank(payerNameTxt)){
			
			msg.put("payerNameTip", MessageConvertFactory.getMessage("paychain.payerName.notEmpty"));
		}
		if(StringUtils.isBlank(payerEmailTxt)){
			
			msg.put("payerEmailTip", MessageConvertFactory.getMessage("paychain.payerEmail.notEmpty"));
		}
		
		String reg = "^\\d+(\\.\\d{0,2})?$";
		if(StringUtils.isNotBlank(amountTxt) &&  !amountTxt.matches(reg)){
			if(msg.get("amountTip") == null){
				
				msg.put("amountTip", MessageConvertFactory.getMessage("paychain.payAmount.formaterror"));
			}
		}
		
		BigDecimal rechargeAmount = null;
		if(StringUtils.isNotBlank(amountTxt) &&  amountTxt.matches(reg)){
			rechargeAmount = new BigDecimal(amountTxt);
			if( rechargeAmount.compareTo(new BigDecimal(MIX_AMOUNT)) == -1  || rechargeAmount.compareTo(new BigDecimal(MAX_AMOUNT)) == 1){
				if(msg.get("amountTip") == null){
					msg.put("amountTip", MessageConvertFactory.getMessage("paychain.payAmount.minmaxerror"));
					
				}
			}
		}
		
		if( StringUtils.isNotBlank(payerEmailTxt) && ! CheckUtil.checkEmail(payerEmailTxt) ){
			if(msg.get("payerEmailTip") == null){
				msg.put("payerEmailTip", MessageConvertFactory.getMessage("paychain.payerEmail.error"));
			}
		}
		
		if(StringUtils.isNotBlank(payerNameTxt) && (CheckUtil.checkStringLength(payerNameTxt,1) || ! CheckUtil.checkStringLength(payerNameTxt,100)) ){
			if(msg.get("payerNameTip") == null){
				msg.put("payerNameTip", MessageConvertFactory.getMessage("paychain.payerName.mixmaxerror"));
			}
		}
		
		if( StringUtils.isNotBlank(payerMemoTxt) && ! CheckUtil.checkStringLength(payerMemoTxt, 100) ){
			if(msg.get("payerMemoTip") == null){
				msg.put("payerMemoTip", MessageConvertFactory.getMessage("paychain.payMemo.mixmaxerror"));
			}
		}
		return msg;
	}
	
	
	/**
	 * 支付后台通知
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView payNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		response.setStatus(200);
		
		String msg = payNoticeUpdate(request,response);
		//(3)给网关响应
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
		out.close();
		return null;
	}
	
	
	private String payNoticeUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		logger.info("支付链支付，后台通知:开始");
		String msg = "";//调试信息
		
		String method = request.getMethod();
		if( !"POST".equalsIgnoreCase(method)){
			return  msg = "{'error':'request method not post'}";
		}
		
		//(1)验签
		if( !payChainPayService.validateNoticeMap(request)){
			//签名失败了
			logger.info("支付链支付，后台通知:验签失败");
			msg = "{'error':'sign error'}";
			
			return msg;
		}
		//(2)判断是否支付成功
		//支付成功（2.1）查询外部交易表更新状态，状态为非成功下去更新
		//支付失败（2.2）查询外部交易表更新状态，状态为初始情况下去更新
		
		String transNo = ServletRequestUtils.getStringParameter(request, "orderID");
		String stateCode = ServletRequestUtils.getStringParameter(request, "stateCode");
		String remark = ServletRequestUtils.getStringParameter(request, "remark","");
		String getWayOrderNo = ServletRequestUtils.getStringParameter(request, "orderNo");
		
		ExternalLog externalLog = externalLogService.findByOrderNo(transNo);
		
		if(externalLog ==  null){
			msg = "{'error':'orderID not exists'}";
		}else if(stateCode.equals ("2")){
			
			if(externalLog.getProcessStatus()!=null ){
				boolean issuccess = false;			
				if(externalLog.getProcessStatus()== ProcessStatus.PROCESS_INIT.getValue() && externalLog.getExternalProcessStatus()==null){
						issuccess = true;
				}else if(externalLog.getProcessStatus()== ProcessStatus.PROCESS_FAILED.getValue() 
						&& externalLog.getExternalProcessStatus()== ExternalProcessStatus.EXTERNAL_PROCESS_FAILED.getValue()){
					logger.info("外部跟踪编号：" + externalLog.getGatewayNo() +"=" + getWayOrderNo);
					issuccess = true;
				}
				
				if(issuccess)
				{
					//externalLog.setRemark(externalLog.getRemark().concat("|").concat(remark));
					externalLog.setExternalProcessStatus(ExternalProcessStatus.EXTERNAL_PROCESS_SUCCED.getValue());
					externalLog.setProcessStatus(ProcessStatus.PROCESS_SUCCED.getValue());
					externalLog.setGatewayNo(getWayOrderNo);
					externalLog.setUpdateDate(new Date());
					
					boolean bol = externalLogService.updateStatusRdTx(externalLog);
					if(!bol){
						msg = "{'orderID':'"+transNo+"','msg':'update failed','getway':'true'}";
					}else{
						msg = "{'orderID':'"+transNo+"','msg':'update success','getway':'true'}";
						externalNoticeService.sendNotice(externalLog.getId());
					}
				}
				logger.info(msg);
			}
		}else if(stateCode.equals("3")){//网关处理失败,网关会处理只会提交成功数据即状态为2。
			
			if(externalLog.getProcessStatus()!=null && externalLog.getProcessStatus()== ProcessStatus.PROCESS_INIT.getValue() && externalLog.getExternalProcessStatus()==null){

				//externalLog.setRemark(externalLog.getRemark().concat("|").concat(remark));
				externalLog.setExternalProcessStatus(ExternalProcessStatus.EXTERNAL_PROCESS_FAILED.getValue());
				externalLog.setProcessStatus(ProcessStatus.PROCESS_FAILED.getValue());
				externalLog.setGatewayNo(getWayOrderNo);
				externalLog.setUpdateDate(new Date());
				boolean bol = externalLogService.updateStatusRdTx(externalLog);
				if(!bol){
					msg = "{'orderID':'"+transNo+"','msg':'update failed','getway':'false'}";
				}else{
					msg = "{'orderID':'"+transNo+"','msg':'update success','getway':'false'}";
				}
			}
			logger.info("订单号:"+transNo+"网关付款未成功！付款不能继续");
		}
		
		return msg;
	}
	
	
	
	/**
	 * 支付前台通知
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView payCallback(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		//(1)查询外部交易状态如果为0初始化状态。调后台通知进行更新。
		
		//(2)查询外部交易状态
		
		//(3)根据外部交易状态返回信息
		Map requestQuery = request.getParameterMap();
		logger.info(requestQuery);
		//自己的orderNo,自己提交给网关，自己传的参数见PayChainPayServiceImpl 77行,与网关传过来的orderID 应该是一致的（需要网关支持前台通知）
		String transNo = ServletRequestUtils.getStringParameter(request, "orderID");
		transNo = StringUtils.isNotBlank(transNo) ? transNo : ServletRequestUtils.getStringParameter(request, "order_no");

		ExternalLog externalLog = externalLogService.findByOrderNo(transNo);
		String msg = "";
		boolean status = false;
		HashMap<String, Object> model = new HashMap<String, Object>();
		
		if(externalLog!=null){
			
			Integer processStatusEx = externalLog.getProcessStatus();
			if(processStatusEx.intValue() == ProcessStatus.PROCESS_INIT.getValue()){
				try{
					payNoticeUpdate(request, response);
				}catch (Exception e) {
					logger.info("payCallback调用notice异常：本地订单号："+externalLog.getOrderNo());
					logger.info(e);
				}
			}
			
			externalLog = externalLogService.findByOrderNo(transNo);
			Integer processStatus = externalLog.getProcessStatus();
			Integer exProcessStatus = externalLog.getExternalProcessStatus();
			
			if(processStatus.intValue() ==ProcessStatus.PROCESS_SUCCED.getValue()){
				msg =externalLog.getPayerName()+"付款成功,金额是"+externalLog.getAmount()+"元！";
				status = true;
			}else if(processStatus.intValue() == ProcessStatus.PROCESS_FAILED.getValue()){
				msg =externalLog.getPayerName()+"付款失败。 ";
			}
			
			
			PayChain payChain = payChainService.getPayChainByChainNum(externalLog.getCardNo());
			if(status){
							
				PayChainPayInfo payChainPayInfo = payChainPayInfoService.get(payChain);
				payChainPayInfo.setPayerEmail(externalLog.getPayerContact());
				payChainPayInfo.setOrderNo(externalLog.getOrderNo());
				List<ContextPicture>  picList=contextPicService.queryPicListByOwnerId(payChain.getPayChainId());
				model.put("picList", picList);
				model.put("payChainPayInfo", payChainPayInfo);
				model.put("amount", externalLog.getAmount());
			}else{
				model.put("payChainUrl", payChain.getPayChainUrl());
			}
		}else{
			msg = "对应的订单号不存在，请确认订单号是否有效！";
		}

		model.put("status", status);
		model.put("msg", msg);
		
		return  new ModelAndView(payResultPage,model);
	}
	
	public ModelAndView excel(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		ModelAndView view =  new ModelAndView(payExcelPage);
		
		String transNo = ServletRequestUtils.getStringParameter(request, "orderID");
		ExternalLog externalLog = externalLogService.findByOrderNo(transNo);
		if(externalLog == null){
			return null;
		}else{
			PayChain payChain = payChainService.getPayChainByChainNum(externalLog.getCardNo());
			
			PayChainPayInfo payChainPayInfo = payChainPayInfoService.get(payChain);
			payChainPayInfo.setPayerEmail(externalLog.getPayerContact());
			payChainPayInfo.setOrderNo(externalLog.getOrderNo());
			
			List<ContextPicture>  picList=contextPicService.queryPicListByOwnerId(payChain.getPayChainId());
			view.addObject("picList", picList);
			view.addObject("payChainPayInfo", payChainPayInfo);
			view.addObject("amount", externalLog.getAmount());
		}
		
		Date nowDate =  FormatDate.formatStr(FormatDate.getDay());
		view.addObject("nowDate",nowDate);
		
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("payChain"+FormatDate.getDay()+".xls").getBytes("UTF-8"),"ISO8859_1"));
		
		return view;
	}
	
	public void setPayChainService(PayChainService payChainService) {
		this.payChainService = payChainService;
	}

	public void setPayChainPayInfoService(
			PayChainPayInfoService payChainPayInfoService) {
		this.payChainPayInfoService = payChainPayInfoService;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public void setPayPage(String payPage) {
		this.payPage = payPage;
	}


	public void setPayChainPayService(PayChainPayService payChainPayService) {
		this.payChainPayService = payChainPayService;
	}


	public void setExternalLogService(ExternalLogService externalLogService) {
		this.externalLogService = externalLogService;
	}


	public void setPayResultPage(String payResultPage) {
		this.payResultPage = payResultPage;
	}

	public void setExternalNoticeService(ExternalNoticeService externalNoticeService) {
		this.externalNoticeService = externalNoticeService;
	}

	public void setPayExcelPage(String payExcelPage) {
		this.payExcelPage = payExcelPage;
	}


	public void setContextPicService(ContextPicService contextPicService) {
		this.contextPicService = contextPicService;
	}
	
}
