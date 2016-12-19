package com.pay.poss.controller.fi.query;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.poss.client.CurrencyRateService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.userrelation.dto.UserRelationDto;
import com.pay.poss.userrelation.service.IUserRelationService;
import com.pay.util.DateUtil;
import com.pay.util.IPv4Util;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class TradeOrderQueryController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private String tradeOrderDetails;
	private EnterpriseBaseService enterpriseBaseService;
	private IUserRelationService userRelationService;
    private IpCountryInfoService ipCountryInfoService;
    public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
		this.ipCountryInfoService = ipCountryInfoService;
	}
	
	public void setUserRelationService(IUserRelationService userRelationService) {
		this.userRelationService = userRelationService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setTradeOrderDetails(String tradeOrderDetails) {
		this.tradeOrderDetails = tradeOrderDetails;
	}

	private GatewayOrderQueryService gatewayOrderQueryService;
	
	private CurrencyRateService currencyRateService;

	
	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,	
			HttpServletResponse response) throws Exception {
		String startTime = DateUtil.skipDateTimeStr(new Date(), -3);
		String endTime = DateUtil.skipDateTimeStr(new Date(), 1);
		
		return new ModelAndView(initView).addObject("startTime", startTime)
				.addObject("endTime", endTime);
	}
	
	
	private  List<UserRelationDto> allUserRelationDto = new ArrayList<UserRelationDto>();
	
	public void getrelationDto(List<UserRelationDto> data){
		for(UserRelationDto item : data){  
			List<UserRelationDto> subData = userRelationService.findByLayer(item.getId());
			if(subData!=null && !subData.isEmpty()){
				allUserRelationDto.addAll(subData);
				getrelationDto(subData);
			}else{
				allUserRelationDto.add(item);
			}
		}
	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Page page = PageUtils.getPage(request);
		Map paraMap = this.request(request,page);
		Map resultMap = gatewayOrderQueryService.queryTradeOrder(paraMap);
		Map countAmount = gatewayOrderQueryService.queryTradeOrderSumAmount(paraMap);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		if(tradeOrders!=null){
			for (Map map : tradeOrders) {
				Integer overBouncedAmount=(Integer)map.get("overBouncedAmount");
				Integer doingBouncedAmount=(Integer)map.get("doingBouncedAmount");
				if(overBouncedAmount>0||doingBouncedAmount>0){
					map.put("chargeBack", "1");
				}else{
					map.put("chargeBack", "2");
				}
			}
		}
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", tradeOrders)
				.addObject("page", page).addObject("countAmount", countAmount);

	}
	
	public Map request(HttpServletRequest request,Page page){
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String status = StringUtil.null2String(request.getParameter("statusList"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));
		String chargeBack = StringUtil
				.null2String(request.getParameter("chargeBack"));
		String channelOrderNo = StringUtil
				.null2String(request.getParameter("channelOrderNo"));
		String authCode = StringUtil
				.null2String(request.getParameter("authCode"));
		//获取登入的用户
		String loginId = SessionUserHolderUtil.getLoginId();
		UserRelationDto relationDto = userRelationService
				.findUserRelatoinByLoginId(loginId);
		allUserRelationDto.clear();
		if (relationDto != null) {
			allUserRelationDto.add(relationDto);
			long id = relationDto.getId();
			List<UserRelationDto> findById = userRelationService
					.findByLayer(id);
			if (findById != null || !findById.isEmpty()) {
				allUserRelationDto.addAll(findById);
				getrelationDto(findById);
			}
		}
		
		List<UserRelationDto> tempList= new ArrayList<UserRelationDto>();  
	    for(UserRelationDto userRelationDto:allUserRelationDto){  
	    	if(!tempList.contains(userRelationDto)){  
	            tempList.add(userRelationDto);  
	        } 
	    } 
	    String signLoginIdS="";
	    for (UserRelationDto userRelation : tempList) {
	    	signLoginIdS+="'"+userRelation.getLoginId()+"',";
	    }
	    if(StringUtils.isNotBlank(signLoginIdS)){
	    	signLoginIdS=signLoginIdS.substring(0,signLoginIdS.length()-1);
	    }
		
		Map paraMap = new HashMap();

		paraMap.put("partnerId", partnerId);
		paraMap.put("orderId", orderId);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("statusList", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("chargeBack", chargeBack);
		paraMap.put("channelOrderNo", channelOrderNo);
		paraMap.put("authCode", authCode);

		if(StringUtils.isNotEmpty(signLoginIdS)){
			paraMap.put("signLoginIdS", signLoginIdS);
		}
		if(page!=null){
			paraMap.put("page", page);
		}
		return paraMap;
	}
	
	
	
	
	public ModelAndView downloadTrade(HttpServletRequest request,HttpServletResponse response){
		Map paraMap = request(request,null);
		Map resultMap = gatewayOrderQueryService.downloadTradeOrder(paraMap);
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		for (Map map : tradeOrders) {
	     String status=String.valueOf(map.get("status"));
	     if(status.equals("0")){
	    	 map.put("status", "未付款");
	     }else if(status.equals("1")){
	    	 map.put("status", "支付失败");
	     }else if(status.equals("2")){
	    	 map.put("status", "交易已付款");
	     }else if(status.equals("3")||status.equals("4")){
	    	 map.put("status", "支付成功");
	     }else if(status.equals("5")){
	    	 map.put("status", "支付失败");
	     }
	     	Long orderAmount= Long.valueOf(map.get("orderAmount")+"");
	     	Long refundAmount=0L;
	     	if(map.get("refundAmount")!=null){
	     		refundAmount = Long.valueOf(map.get("refundAmount")+"") ;
	     	}
	       map.put("orderAmount", new BigDecimal(orderAmount).divide(new BigDecimal(1000)));
	       if(refundAmount==null){
	    	   map.put("refundAmount", "0.0");
	       }else{
	    	   map.put("refundAmount", new BigDecimal(refundAmount).divide(new BigDecimal(1000)));
	       }
	       boolean flag=map.get("cardType")!=null;
	    	  String str= String.valueOf(map.get("cardType"));
	    	   if(str.equals("CREDIT")){
	    		   map.put("cardType", "贷记卡");
	    	   }else if(str.equals("DEBIT")){
	    		   map.put("cardType", "借记卡");
	    	   }
	     if(status.equals("3")){
	    	 int compareTo = new BigDecimal(refundAmount).compareTo(new BigDecimal(0).ZERO);
	    	 if(compareTo==0){
	    		 map.put("partialRefund", "全额退款");
	    	 }else if(compareTo==1){
	    		 map.put("partialRefund", "部分退款");
	    	 }
	     }else{
	    	 map.put("partialRefund", "否");
	     }
	       Long creDate= (Long) map.get("createDate");
		   Long comDate= (Long) map.get("completeDate");
		   if(creDate!=null){
			   Date date = new Date(creDate);
			   SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   map.put("createDate", sd.format(date));
		   }
		   if(comDate!=null){
			   Date dates = new Date(comDate);
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			   map.put("completeDate", sdf.format(dates));
		   }
		   String ip=String.valueOf(map.get("ip"));
		   if(!StringUtil.isEmpty(ip)){
				long ipInt=0;
				int ipFlg=0;
				try{
				   ipInt = IPv4Util.ipToLong(ip);
				   ipFlg=1;
				}catch(Exception e){
					ipFlg=-1;
				}
				if(ipFlg>0){
					Map<String,Object> params_ = new HashMap<String, Object>();
					params_.put("ip",ipInt);
					IpCountryInfo info = ipCountryInfoService.getCountryInfo(params_);
					logger.info("ipCountryInfo: "+info);
					if(info!=null&&!StringUtil.isEmpty(info.getCountryCode3())){
						map.put("ipCountry",info.getCountryCode3());
					}
				}
			}
		   
		}
		
	try {
		String[] headers = new String[] { "系统交易号", "会员号", "商户订单号", "订单金额",
				"可退金额","交易币种","状态","是否退款","创建时间","完成时间","商户返回码","返回消息","卡bin","卡bin国家",
				"卡bin地区","借记卡/贷记卡","卡种","卡组织","币种代码","ip地址","ip国家","ip地区"};
		String[] fields = new String[] { "tradeOrderNo", "partnerId", "orderId",
				"orderAmount", "refundAmount","currencyCode","status","partialRefund","createDate","completeDate","respCode","respMsg",
				"cardBin","cardCountry","cardBinRegion","cardType","cardClass","cardOrg","currencyNumber","ip","ipCountry","ipBinRegion"};
		Workbook grid = SimpleExcelGenerator.generateGrid("网 关 订 单 管 理",
				headers, fields, tradeOrders);
		Date myDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String dd = sdf.format(myDate);
		response.setContentType("application/force-download");
		response.setContentType("applicationnd.ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ dd + ".xls");
		grid.write(response.getOutputStream());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
	
	/***
	 * 网关订单详情
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView tradeOrderDetails(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));//会员号
		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));//商户订单号
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));//系统交易号 渠道订单号
		
		EnterpriseBaseDto code = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerId));

		Map<String,String> paraMap=new HashMap<String, String>();
		  paraMap.put("tradeOrderNo", tradeOrderNo);
		  Map resultMap = gatewayOrderQueryService.queryTradeOrderDetails(paraMap); //基本信息
		  resultMap.put("enName", code.getZhName());
		  resultMap.put("orderId", orderId);
		  resultMap.put("tradeOrderNo", tradeOrderNo);
		  BigDecimal payA=new BigDecimal("0");
		  BigDecimal	refA= new BigDecimal("0");
		  BigDecimal canRefA=new BigDecimal("0");
		  Map RefundOrder=gatewayOrderQueryService.queryRefundOrder(paraMap);//退款gatewayOrderQueryService.queryTradeOrder
		  if(resultMap.get("paymentAmount")!=null){
			  double orderAmount=Double.valueOf(resultMap.get("paymentAmount").toString());
			  payA = new BigDecimal(resultMap.get("paymentAmount").toString());
			  resultMap.put("paymentAmount", orderAmount/1000);
		  }
		  if(resultMap.get("payAmount")!=null){
			  double payAmount=Double.valueOf(resultMap.get("payAmount").toString());
			  resultMap.put("payAmount", payAmount/1000);
		  }
		  if(resultMap.get("canRefundAmount")!=null){ //可退金额
				double  canRefundAmount=Double.valueOf(resultMap.get("canRefundAmount").toString());
				  canRefA=new BigDecimal( resultMap.get("canRefundAmount").toString());
				  resultMap.put("canRefundAmount", canRefundAmount/1000);
		 }
		 List<Map> tradeOrders = (List<Map>) RefundOrder.get("result");
		 double  refundAmount=0;
		 double  refundAmountCount=0;
		 double  transferRate=0;
		 if(tradeOrders!=null && !tradeOrders.isEmpty()){
		 for (Map map : tradeOrders) {
		  	if(map.get("refundAmount")!=null){
				 double refundAmount1=Double.valueOf(map.get("refundAmount").toString());
				 map.put("refundAmount", refundAmount1/1000);
				 if(map.get("status").equals("2")){
					 refundAmountCount+=refundAmount1; //计算退款金额总额
				  }
		  	}
		}
	}
		  resultMap.put("refundAmount", refundAmountCount/1000);
		  refA=new BigDecimal(refundAmountCount);
		  float refundingAmount = payA.subtract(refA).subtract(canRefA).floatValue();
		  resultMap.put("refundingAmount", refundingAmount/1000);
		 
		 
		 Map channlResult = gatewayOrderQueryService.queryChannelOrder(paraMap);
		 List<Map> channlResults = (List<Map>) channlResult.get("result");
		  Map temp=new HashMap();
		  if(channlResults!=null && !channlResults.isEmpty()){
		 for (Map map : channlResults) {
			temp.putAll(map);
		}
		 }
		 
		 //更改退款金额逻辑  2016年4月29日16:44:21 delin.dong 
		 if(channlResults!=null && !channlResults.isEmpty()){
			 for (Map map : channlResults) {
				 if(map.get("transferRate")!=null){
					 transferRate =	Double.valueOf(map.get("transferRate").toString());
				 }
				  double payAmount1=transferRate*refundAmount;
				  BigDecimal bg = new BigDecimal(payAmount1);
				  double payAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
				  map.put("payAmount", payAmount);
			 }
		 }
		 for (Map map : tradeOrders) {
			  refundAmount= Double.valueOf(map.get("refundAmount").toString());
			  double payAmount1=transferRate*refundAmount;
			  BigDecimal bg = new BigDecimal(payAmount1);
			  double payAmount = bg.setScale(2, BigDecimal.ROUND_HALF_UP)
					.doubleValue();
			  map.put("payAmount",(int) payAmount);
		 }
		 return new ModelAndView(tradeOrderDetails).addObject("map", resultMap).addObject("ClearingRefundOrder",tradeOrders).addObject("channlResults", temp);
	}

	public void setInitView(String initView) {
		this.initView = initView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}
}
