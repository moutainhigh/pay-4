package com.pay.app.controller.base.receivables;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.util.MD5BaseAlgorithms;
import com.pay.app.common.util.WebUrlUtil;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dto.ApiOrder;
import com.pay.base.dto.ApiOrderResponse;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.external.ExternalLogService;
import com.pay.util.FormatDate;


/**
 * 我要收款
 * @author jinhaha
 * 
 */
public class ReceivablesController extends MultiActionController{
	
	private static final Log logger = LogFactory.getLog(ReceivablesController.class);
 	
 	private String receivIndex;
	private String receivConfirm;
	private String receivResult;

	
	private final static String pix="pay";
	private MemberQueryService  memberQueryService;
	private EnterpriseBaseService  enterpriseBaseService; 
	//private InnerAcquireContextService acquireContext;
	//private InnerAcquireService innerAcquireService; 
	private ExternalLogService externalLogService;

	 public ModelAndView index(HttpServletRequest request, HttpServletResponse response
			 ,ApiOrder apiOrder) {
		 Map<String,Object> paraMap = new HashMap<String,Object>();
		 paraMap.put("apiOrder", apiOrder);
		 return new ModelAndView(receivIndex,paraMap);
	 }
	 
	 
	 public ModelAndView confirm(HttpServletRequest request, HttpServletResponse response
			 ,ApiOrder apiOrder) {
		 Map<String,Object> paraMap = new HashMap<String,Object>();
		 paraMap.put("apiOrder", apiOrder);
		 ResultDto rDto=this.validateApiOrder(apiOrder);
		 if(rDto.isResultStatus()){
			 Map<String,String> map =this.createPayMap(apiOrder,request);
			//paraMap.put("apiOrder", this.generateApiOrder(apiOrder, request));
			 paraMap.put("paramMap", map);
			
			 return new ModelAndView(receivConfirm,paraMap);
		 }else{
			 paraMap.put("errMsg", rDto.getErrorMsg());
		 }
		 return new ModelAndView(receivIndex,paraMap);
		
	 }
	 
//	 public ModelAndView submit(HttpServletRequest request, HttpServletResponse response
//			 ,InnerAcquireRequestDTO reqDto) {
//		 Map<String,Object> paraMap = new HashMap<String,Object>();
//		 paraMap.put("reqDto", reqDto);
//		 InnerAcquireResponseDTO responseDto=null;
//		 try {
//			 acquireContext.setAcquireService(innerAcquireService);
//			 responseDto =(InnerAcquireResponseDTO) acquireContext.doAcquire(reqDto);
//		} catch (Exception e) {
//			logger.error("acquireContext.doAcquir throws error ",e);
//		}
//		 paraMap.put("responseDto", responseDto);
//		 return new ModelAndView(receivResult,paraMap);
//	 }
	 
	 public ModelAndView notice(HttpServletRequest request, HttpServletResponse response
			 ,ApiOrderResponse apiOrderResp) {
		 Map<String,Object> paraMap = new HashMap<String,Object>();
		 paraMap.put("apiOrderResp", apiOrderResp);
		 return new ModelAndView(receivResult,paraMap);
	 }
	 
//	 private ApiOrder generateApiOrder(ApiOrder apiOrder,HttpServletRequest request){
//		 apiOrder.setBuyerMarked(apiOrder.getMobile());
//		 String randomDate=FormatDate.getRandomDate();
//		 apiOrder.setSerialID(pix+randomDate); 
//		 apiOrder.setSubmitTime(randomDate);
//		 apiOrder.setCustomerIP(WebUtil.getClientIP(request));
//		 apiOrder.setType("1000");
//		 apiOrder.setRemark(apiOrder.getProductDesc());
//		 BigDecimal orderAmount=new BigDecimal(apiOrder.getAmount()).multiply(new BigDecimal(100));
//		 apiOrder.setTotalAmount(orderAmount.toString());
//		 String memeberCode=SessionHelper.getMemeberCodeBySession();
//		 apiOrder.setPartnerID(memeberCode);
//		 ApiOrderDetail orderDetail=new ApiOrderDetail();
//		 EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memeberCode));
//		 orderDetail.setDisplayName(enterpriseBase.getZhName());
//		 orderDetail.setGoodsName(apiOrder.getProductName());
//		 orderDetail.setGoodsCount("1");
//		 orderDetail.setOrderID(randomDate);
//		 orderDetail.setOrderAmount(orderAmount.toString());
//		 apiOrder.setOrderDetail(orderDetail);
//		 String orderDetails="{"+randomDate+"},{"+orderAmount.toString()+"},{"+enterpriseBase.getZhName()+"},{"
//				 +orderDetail.getGoodsName()+"},{"+orderDetail.getGoodsCount()+"}";
//		 apiOrder.setOrderDetails(orderDetails);
//		 apiOrder.setNoticeUrl(receivNoticeUrl);
//	
//		 return apiOrder;
//	 }
	 
	 
	 public LinkedHashMap<String, String> createPayMap(ApiOrder apiOrder,HttpServletRequest request){
		 	String randomDate=FormatDate.getRandomDate();
		 	String customerIP = "pay.com[127.0.0.1]";
		 	BigDecimal orderAmount=new BigDecimal(apiOrder.getAmount()).multiply(new BigDecimal(100));
		 	String totalAmount=orderAmount.longValue()+"";
		 	String memeberCode=SessionHelper.getMemeberCodeBySession();
		 	String productName=apiOrder.getProductName();
		 	EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(Long.valueOf(memeberCode));
		 	String displayName=enterpriseBase.getZhName();
		 	String type="1000";
		 	String buyerMarked=apiOrder.getMobile();
		 	LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>();
		 	String orderId=externalLogService.getOrderNo("301");
		 	String orderDetails = orderId+","+orderAmount+","+displayName+","+productName+",1";
			map2.put("version", "2.6");//版本号
			map2.put("serialID", pix+randomDate);
			map2.put("submitTime",randomDate);
			map2.put("failureTime","");//失效时间s
			map2.put("customerIP",customerIP);
			map2.put("orderDetails",orderDetails);
			map2.put("totalAmount",totalAmount);
			map2.put("type",type);
			map2.put("buyerMarked", buyerMarked);
			
			map2.put("payType","ALL");
			map2.put("orgCode", "");
			map2.put("currencyCode", "1");
			map2.put("directFlag", "");
			
			map2.put("borrowingMarked", "");
			map2.put("platformID", "");
			String receivNoticeUrl=WebUrlUtil.getFullConextPath(request)+"/corp/receiv/receiv.htm?method=notice";
			
			map2.put("returnUrl","");
			map2.put("noticeUrl",receivNoticeUrl);
			map2.put("partnerID",memeberCode);//必填
			
			map2.put("remark",apiOrder.getProductDesc());
			map2.put("charset","1");
			map2.put("signType","2");
			//map2.put("pkey", fiPkey);
			map2.put("orderAmount",totalAmount);
			map2.put("orderID", orderId);
			map2.put("displayName", displayName);
			map2.put("goodsName", productName);
			map2.put("goodsCount", 1+""); 
			String signMsgUrl = WebUrlUtil.mapToUrl(map2);
			
			String signMsg = "";
			try {
				signMsg = MD5BaseAlgorithms.getMD5Str(signMsgUrl);
				map2.put("signMsg", signMsg);
				map2.remove("pkey");//不传key到客户端
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("设置付款签名异常", e);
			}
			
			return map2;
	 }
	 
	 private ResultDto validateApiOrder(ApiOrder apiOrder){
		 ResultDto rd=new ResultDto();
		 rd.setResultStatus(false);
		 //ErrorCodeEnum
		 if(apiOrder==null){
			 rd.setErrorMsg(ErrorCodeEnum.INVAILD_PARAMETER.getMessage());
		 }else if(StringUtils.isBlank(apiOrder.getProductName())){
			 rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PRODUCT_NAME_EMPEY.getMessage());
		 }else if(StringUtils.isBlank(apiOrder.getProductDesc())){
			 rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PRODUCT_REMARK_EMPEY.getMessage());
		 }else if(StringUtils.isBlank(apiOrder.getAmount())){
			 rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PRODUCT_REMARK_EMPEY.getMessage());
		 }else if(StringUtils.isBlank(apiOrder.getMobile())){
			 rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PAY_MOBILE_EMPEY.getMessage());
		 }else{
			 try {
				MemberInfoDto mDto=memberQueryService.doQueryMemberInfoNsTx(apiOrder.getMobile(), null, null, null);
				if(mDto!=null){
					rd.setResultStatus(true);
					rd.setObject(mDto.getLoginName());
				}else{
					rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PAY_MEMBER_NOT_EXSITS.getMessage());
				}
			} catch (MaMemberQueryException e) {
				logger.error("ReceivablesController.memberQueryService.doQueryMemberInfoNsTx throws error:",e);
				rd.setErrorMsg(ErrorCodeEnum.API_ORDER_PAY_MEMBER_STATUS_ERROR.getMessage());
			}
		 }
		 return rd;
	 }


	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}


	public void setReceivIndex(String receivIndex) {
		this.receivIndex = receivIndex;
	}


	public void setReceivConfirm(String receivConfirm) {
		this.receivConfirm = receivConfirm;
	}


	public void setReceivResult(String receivResult) {
		this.receivResult = receivResult;
	}


	


	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}


	


//	public void setAcquireContext(InnerAcquireContextService acquireContext) {
//		this.acquireContext = acquireContext;
//	}
//
//
//	public void setInnerAcquireService(InnerAcquireService innerAcquireService) {
//		this.innerAcquireService = innerAcquireService;
//	}


	public void setExternalLogService(ExternalLogService externalLogService) {
		this.externalLogService = externalLogService;
	}
	 
	
	
	 
}
