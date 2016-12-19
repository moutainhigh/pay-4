package com.pay.gateway.validate.crosspay.api;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.facade.BusinessTradeCountService;
import com.pay.rm.facade.dto.BusinessTradeCountDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.rm.service.dto.rmlimit.businesslimit.RcBusinessLimitDTO;
import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.service.rmlimit.businesslimit.RmBusinesslimitService;
import com.pay.rm.service.service.rmlimit.businesslimitcustom.RmBusinessLimitCustomService;


/**
 * 商户交易额度及交易次数检查
 * 限额下次查询
 * @author peiyu.yang
 * @since 2015年6月21日15:25:34
 */
public class BusinessTradeCheckRule extends MessageRule{
	
	private static Logger logger = LoggerFactory.getLogger(BusinessTradeCheckRule.class);
	
	private BusinessTradeCountService businessTradeCountService;
	
	private RmBusinesslimitService rmBusinesslimitService;
	
	private EnterpriseBaseService enterpriseBaseService;
	
	private RmBusinessLimitCustomService rmBusinessLimitCustomService;
	
	private String singleLimitMsg;//单笔订单金额超过最大值
	private String monthLimitMsg;//月交易金额已超过最大值
	private String dayLimitMsg;//日交易金额已超过最大值
	private String monthLCMsg;//月交易次数已超过最大值
	private String dayLCMsg;// 日交易次数已超过最大值

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		CrosspayApiRequest crosspayApiRequest = (CrosspayApiRequest) validateBean;
		CrosspayApiResponse crosspayApiResponse = crosspayApiRequest
				.getCrosspayApiResponse();
		
		String memberCode = crosspayApiRequest.getPartnerId();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day =c.get(Calendar.DAY_OF_MONTH);
		//String orderAmount = crosspayApiRequest.getOrderAmount();
		
		//Long orderAmountNum = Long.valueOf(orderAmount);
		
		
		BusinessTradeCountDTO tradeCount = new BusinessTradeCountDTO();
		tradeCount.setDay(day);
		tradeCount.setMonth(month);
		tradeCount.setYear(year);
		tradeCount.setMemberCode(memberCode);
		
		//只查找当月所有记录，tradeCountList的size最大为31，即月最大天数
		List<BusinessTradeCountDTO> tradeCountList = businessTradeCountService.getBusinessTradeCountDTO(tradeCount);
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("businessId",memberCode);
		params.put("status","1");
		params.put("businessType",RCLIMITCODE.FO_ENTERPRISE_RECORDER.getValue());
        
		//查找个人定制限额限次
		RcBusinessLimitCustomDTO rblcustomDTO= rmBusinessLimitCustomService.getRcBusinessLimitCustomDTO(params);
        
		BusinessTradeCountDTO countDTO = new BusinessTradeCountDTO();
		
		Long singleLimit=0L;
		Long monthLimit=0L;
		Long monthTimes=0L;
		Long dayLimit=0L;
		Long dayTimes=0L;
		//商户定制限制存在
		if(rblcustomDTO!=null){
			singleLimit = rblcustomDTO.getSingleLimit();
			monthLimit = rblcustomDTO.getMonthLimit();
			monthTimes = rblcustomDTO.getMonthTimes();
			dayLimit = rblcustomDTO.getDayLimit();
			dayTimes = rblcustomDTO.getDayTimes();
		}else{
			long riskLevel = enterpriseBaseService.queryEnterpriseRiskLeveCode(Long.valueOf(memberCode));
			params.put("riskLevel",riskLevel);
			RcBusinessLimitDTO rblDTO = rmBusinesslimitService.getRcBusinessLimitDTO(params);
			
			if(rblDTO==null)
				return true;
			singleLimit = rblDTO.getSingleLimit();
			monthLimit = rblDTO.getMonthLimit();
			monthTimes = rblDTO.getMonthTimes();
			dayLimit = rblDTO.getDayLimit();
			dayTimes = rblDTO.getDayTimes();
		}
		
		
		/*if(singleLimit<orderAmountNum){//单笔金额限制没有通过
			crosspayApiResponse.setResultCode(getMessageId());
			crosspayApiResponse.setResultMsg(getSingleLimitMsg());
			return false;
		}*/
		
		//表示当月没有交易记录
		if(tradeCountList==null||tradeCountList.size()<1)
			return true;
		
		int index= tradeCountList.size()-1;
		countDTO = tradeCountList.get(index);
		
		logger.info("[dayCount: "+countDTO.getDayCount()
					          +",dayLimitCount: "+dayTimes
					    +"monthTime: "+countDTO.getMonthCount()
					    +" ,monthLimitTime: "+monthTimes+"]");
			
			//超过了设置月限制金额,或是这个月的交一次数超过了限制
			/*/if(monthLimit<(countDTO.getMonthAmount()+orderAmountNum)){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMonthLimitMsg());
				return false;//限额的先不处理
			}else */
		
		    logger.info("dayTimes: "+dayTimes+" ,monthTime: "+monthTimes);
		    
		    
			if(monthTimes<(countDTO.getMonthCount()+1)){
				crosspayApiResponse.setResultCode(getMessageId());
				crosspayApiResponse.setResultMsg(getMonthLCMsg());
				return false;
			}
			
			int countDay = countDTO.getDay();
			
			//表示当填有交易记录
			if(day==countDay){//限额的先不处理
				/*if(dayLimit<(countDTO.getDayAmount()+orderAmountNum)){
					crosspayApiResponse.setResultCode(getMessageId());
					crosspayApiResponse.setResultMsg(getDayLimitMsg());
					return false;
				}else */
				if(dayTimes<(countDTO.getDayCount()+1)){
					crosspayApiResponse.setResultCode(getMessageId());
					crosspayApiResponse.setResultMsg(getDayLCMsg());
					return false;
				}
			}

		return true;
	}

	public void setBusinessTradeCountService(
			BusinessTradeCountService businessTradeCountService) {
		this.businessTradeCountService = businessTradeCountService;
	}

	public void setRmBusinesslimitService(
			RmBusinesslimitService rmBusinesslimitService) {
		this.rmBusinesslimitService = rmBusinesslimitService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setRmBusinessLimitCustomService(
			RmBusinessLimitCustomService rmBusinessLimitCustomService) {
		this.rmBusinessLimitCustomService = rmBusinessLimitCustomService;
	}

	public String getSingleLimitMsg() {
		return singleLimitMsg;
	}

	public void setSingleLimitMsg(String singleLimitMsg) {
		this.singleLimitMsg = singleLimitMsg;
	}

	public String getMonthLimitMsg() {
		return monthLimitMsg;
	}

	public void setMonthLimitMsg(String monthLimitMsg) {
		this.monthLimitMsg = monthLimitMsg;
	}

	public String getDayLimitMsg() {
		return dayLimitMsg;
	}

	public void setDayLimitMsg(String dayLimitMsg) {
		this.dayLimitMsg = dayLimitMsg;
	}

	public String getMonthLCMsg() {
		return monthLCMsg;
	}

	public void setMonthLCMsg(String monthLCMsg) {
		this.monthLCMsg = monthLCMsg;
	}

	public String getDayLCMsg() {
		return dayLCMsg;
	}

	public void setDayLCMsg(String dayLCMsg) {
		this.dayLCMsg = dayLCMsg;
	}
}
