/**
 *  File: PaymentQueryMessageHandle.java
 *  Description:
 *  Copyright Corporation. All rights reserved.
 *  Date      Author      Changes
 *  Dec 27, 2011   ch-ma     Create
 *
 */
package com.pay.api.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.dto.http.PaymentTotalRequest;
import com.pay.api.dto.http.PaymentTotalResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.api.service.BaseMessageHandle;
import com.pay.api.service.IMessageHandleService;
import com.pay.api.util.FreeMarkerUtil;
import com.pay.api.util.ParameterXmlParserUtil;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dao.base.FundoutOrderDAO;
import com.pay.fo.order.dao.base.PayToAcctOrderDAO;
import com.pay.fo.order.dao.batchpayment.BatchPaymentOrderDAO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.FundoutOrderExample;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.MD5Util;

/**
 * 
 */
public class PaymentTotalQueryMessageHandle extends BaseMessageHandle implements IMessageHandleService {

	private Log logger = LogFactory.getLog(PaymentTotalQueryMessageHandle.class);
	static String datePattern = "^[0-9]{8,8}$";
	static String pattern = "^[A-Za-z0-9]+$";
	private BaseDAO<MerchantConfigure> merchantConfigureDao;
	private BatchPaymentOrderDAO batchPaymentOrderDAO;
	private PayToAcctOrderDAO payToAcctOrderDAO;
	private FundoutOrderDAO fundoutOrderDAO;
	
	public String process(final String merchantCode, final String bizNo, String requestInfo) {
		String resultXml = null;
		PaymentTotalRequest request = transferRequestTotal(requestInfo);
		try {
			String errorCode = checkRequestTotal(requestInfo, request);
			if (null != errorCode) {
				resultXml = generateResultTotalXml(request, request.getResult());
				return resultXml;
			}

			// do payment
			PaymentTotalResult result = handleTotal(request);
			resultXml = generateResultTotalXml(request, result);
		} catch (Exception e) {
			logger.error("payment error:", e);
			PaymentTotalResult result = new PaymentTotalResult();
			result.setResponseCode(Integer.valueOf(ErrorCode.FAIL));
			result.setResponseDesc(ErrorCode.FAIL_DESC);
			result.setTotalMoney(0l);
			result.setTotalCount(0);
			resultXml = generateResultTotalXml(request, result);
		}

		return resultXml;
	}

	@Override
	protected PaymentRequest transferRequest(String requestXml) {
		return null;
	}
	
	/**
	 * 为出款总额查询提供的方法
	 * @param requestXml
	 * @return
	 */
	protected PaymentTotalRequest transferRequestTotal(String requestXml) {
		PaymentTotalRequest request = ParameterXmlParserUtil.parser(PaymentTotalRequest.class, requestTransferMap, requestXml, "REQUEST_HEADER");
		return request;
	}

	@Override
	protected String checkRequest(String requestXml, PaymentRequest request) throws Exception {
		return null;
	}
	
	/**
	 * 为出款总额查询提供的检验
	 * @param requestXml
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String checkRequestTotal(String requestXml, PaymentTotalRequest request) throws Exception {
		String merchantCode = request.getMerchantCode();
		String signvalue = request.getSignvalue();
		PaymentTotalResult result = new PaymentTotalResult();
		
		// 验证商户
		if(StringUtils.isBlank(merchantCode)){
			result.setResponseCode(Integer.valueOf(ErrorCode.MERCHANT_INVALID));
			result.setResponseDesc(ErrorCode.MERCHANT_INVALID_DESC);
			result.setTotalMoney(0l);
			result.setTotalCount(0);
			result.setPrivateField(request.getPrivateField());
			request.setResult(result);
			return result.getResponseCode().toString();
		}
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);
		if (null == merchantConfigure) {
			result.setResponseCode(Integer.valueOf(ErrorCode.MERCHANT_INVALID));
			result.setResponseDesc(ErrorCode.MERCHANT_INVALID_DESC);
			result.setTotalMoney(0l);
			result.setTotalCount(0);
			result.setPrivateField(request.getPrivateField());
			request.setResult(result);
			return result.getResponseCode().toString();
		}
		
		// 对签名域进行判断
		String srcData = buildRequestSignMsg(request);
		String mac = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
		boolean signFlag = mac.equalsIgnoreCase(signvalue);
		if (!signFlag) {
			result.setResponseCode(Integer.valueOf(ErrorCode.SIGN_INVALID));
			result.setResponseDesc(ErrorCode.SIGN_INVALID_DESC);
			result.setPrivateField(request.getPrivateField());
			result.setTotalMoney(0l);
			result.setTotalCount(0);
			request.setResult(result);
			return result.getResponseCode().toString();
		}
		
		// 验证开始和结束时间
		if(StringUtils.isBlank(request.getStartDate()) || StringUtils.isBlank(request.getEndDate()) || request.getStartDate().length() != 8 || request.getEndDate().length() != 8 || !(Pattern.matches(datePattern, request.getStartDate())) || !(Pattern.matches(datePattern, request.getEndDate()))){
			result.setResponseCode(Integer.valueOf(ErrorCode.QUERY_TIME_INVALID));
			result.setResponseDesc(ErrorCode.QUERY_TIME_INVALID_DESC);
			result.setTotalMoney(0l);
			result.setTotalCount(0);
			result.setPrivateField(request.getPrivateField());
			request.setResult(result);
			return result.getResponseCode().toString();
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date startDate = sdf.parse(request.getStartDate());
			Date endDate = sdf.parse(request.getEndDate());
			if(endDate.before(startDate) || (endDate.getTime()-startDate.getTime())/86400000 > 7){
				result.setResponseCode(Integer.valueOf(ErrorCode.QUERY_TIME_LIMIT));
				result.setResponseDesc(ErrorCode.QUERY_TIME_LIMIT_DESC);
				result.setTotalMoney(0l);
				result.setTotalCount(0);
				result.setPrivateField(request.getPrivateField());
				request.setResult(result);
				return result.getResponseCode().toString();
			}
		}

		String errorCode = result.getResponseCode() == null ? null : result.getResponseCode().toString();

		return errorCode;
	}

	@Override
	protected String generateResultXml(PaymentRequest request, PaymentResult result) {
		return null;
	}
	
	/**
	 * 为出款总额查询提供的方法
	 * @param request
	 * @param result
	 * @return
	 */
	protected String generateResultTotalXml(PaymentTotalRequest request, PaymentTotalResult result) {
		Map parameterMap = new HashMap();

		parameterMap.put("RESPONSE_CODE", String.valueOf(result.getResponseCode()));
		parameterMap.put("RESPONSE_DESC", result.getResponseDesc());
		parameterMap.put("PRIVATE_FIELD", result.getPrivateField());
		parameterMap.put("TOTAL_MONEY", String.valueOf(result.getTotalMoney()));
		parameterMap.put("TOTAL_COUNT", String.valueOf(result.getTotalCount()));
		
		// 加密签名域
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(request.getMerchantCode());
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);
		if(merchantConfigure != null){
			String srcData = request.getMerchantCode() + result.getResponseCode();
			String signvalue = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
			parameterMap.put("SIGNVALUE", signvalue);
		}
		
		String resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(), parameterMap);
		
		return resultXml;
	}

	@Override
	protected PaymentResult handle(PaymentRequest request) throws Exception {
		return null;
	}
	
	/**
	 * 为出款总额查询提供的方法
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected PaymentTotalResult handleTotal(PaymentTotalRequest request) throws Exception {
		String merchantCode = request.getMerchantCode();
		String startDate = request.getStartDate();
		String endDate = request.getEndDate();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		PaymentTotalResult result = new PaymentTotalResult();
		FundoutOrderExample fundoutOrderExample = new FundoutOrderExample();
		fundoutOrderExample.createCriteria().andPayerMemberCodeEqualTo(Long.valueOf(merchantCode)).andCreateDateBetween(sdf.parse(startDate), sdf.parse(endDate));
		List<FundoutOrder> list = fundoutOrderDAO.selectByExample(fundoutOrderExample);
		
		Long totalMoney = 0l;
		Integer totalCount = 0;
		// 符合条件的累计金额和笔数
		for(FundoutOrder order : list){
			if(order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()){
				totalMoney += order.getRealoutAmount();
				totalCount += 1;
			}
		}
		result.setResponseCode(Integer.valueOf(ErrorCode.SUCCESS));
		result.setResponseDesc(ErrorCode.SUCCESS_DESC);
		result.setTotalMoney(totalMoney/10);
		result.setTotalCount(totalCount);
		result.setPrivateField(request.getPrivateField());
		return result;
	}

	public void setMerchantConfigureDao(BaseDAO<MerchantConfigure> merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}

	public void setBatchPaymentOrderDAO(BatchPaymentOrderDAO batchPaymentOrderDAO) {
		this.batchPaymentOrderDAO = batchPaymentOrderDAO;
	}

	public void setPayToAcctOrderDAO(PayToAcctOrderDAO payToAcctOrderDAO) {
		this.payToAcctOrderDAO = payToAcctOrderDAO;
	}

	public void setFundoutOrderDAO(FundoutOrderDAO fundoutOrderDAO) {
		this.fundoutOrderDAO = fundoutOrderDAO;
	}
	
	/**
	 * 构造签名域，此处仅仅是商户号
	 * @param request
	 * @return
	 */
	private String buildRequestSignMsg(PaymentTotalRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getMerchantCode());
		return sb.toString();
	}
	
	public static void main(String[] args){
		String original = "白痴abcd1234";
		byte[] b = original.getBytes();
		String target1 = new String(b, 0 , 6);
		System.out.println(target1);
		String target2 = new String(b, 6 , 1);
		System.out.println(target2);
	}

}
