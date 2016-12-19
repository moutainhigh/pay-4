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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pay.api.dto.http.PaymentItemResult;
import com.pay.api.dto.http.PaymentRequest;
import com.pay.api.dto.http.PaymentResult;
import com.pay.api.helper.ErrorCode;
import com.pay.api.helper.QueryType;
import com.pay.api.helper.RequestStatus;
import com.pay.api.helper.SignType;
import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.api.service.BaseMessageHandle;
import com.pay.api.service.IMessageHandleService;
import com.pay.api.util.FreeMarkerUtil;
import com.pay.api.util.ParameterXmlParserUtil;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.dao.base.FundoutOrderDAO;
import com.pay.fo.order.dao.base.PayToAcctOrderDAO;
import com.pay.fo.order.dao.batchpayment.BatchPaymentOrderDAO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.base.FundoutOrderExample;
import com.pay.fo.order.model.base.PayToAcctOrder;
import com.pay.fo.order.model.base.PayToAcctOrderExample;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrderExample;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.MD5Util;

/**
 * 
 */
public class PaymentQueryMessageHandle extends BaseMessageHandle implements IMessageHandleService {

	static String pattern = "^[A-Za-z0-9]+$";
	static String datePattern = "^[0-9]{8,8}$";
	private BaseDAO<MerchantConfigure> merchantConfigureDao;
	private BatchPaymentOrderDAO batchPaymentOrderDAO;
	private PayToAcctOrderDAO payToAcctOrderDAO;
	private FundoutOrderDAO fundoutOrderDAO;

	@Override
	protected PaymentRequest transferRequest(String requestXml) {
		PaymentRequest request = ParameterXmlParserUtil.parser(PaymentRequest.class, requestTransferMap, requestXml, "REQUEST_HEADER");
		return request;
	}

	@Override
	protected String checkRequest(String requestXml, PaymentRequest request) throws Exception {
		String merchantCode = request.getMerchantCode();
		String orderId = request.getOrderId();
		Integer queryType = Integer.valueOf(request.getQueryType());
		String bizNo = request.getBizNo();
		String signValue = request.getSignvalue();
//		String signValue = "16a6dc14c86d2859fb864fdf3b85b009";
		PaymentResult result = new PaymentResult();
		
		if (null == QueryType.getQueryType(Integer.valueOf(queryType))) {
			result.setBizNo(request.getBizNo());
			result.setMerchantCode(request.getMerchantCode());
			result.setErrorCode(ErrorCode.QUERY_TYPE_INVALID);
			result.setErrorMsg(ErrorCode.QUERY_TYPE_INVALID_DESC);
			request.setResult(result);
			return result.getErrorCode();
		}

		// 验证商户
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);
		if (null == merchantConfigure) {
			result.setBizNo(request.getBizNo());
			result.setMerchantCode(request.getMerchantCode());
			result.setErrorCode(ErrorCode.MERCHANT_INVALID);
			result.setErrorMsg(ErrorCode.MERCHANT_INVALID_DESC);
			request.setResult(result);
			return result.getErrorCode();
		}
		
		String signType = request.getSignType();
		boolean signFlag = false;
		String srcData = buildRequestSignMsg(request);
		
		if (SignType.MD5.getValue().equals(signType)) {
			String mac = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
			signFlag = mac.equalsIgnoreCase(signValue);
		} else if (SignType.RSA.getValue().equals(signType)) {
			signFlag = securityProvider.verifySignature(srcData, merchantConfigure.getPublicKey(), signValue);
		}

		if (!signFlag) {
			result.setBizNo(request.getBizNo());
			result.setMerchantCode(request.getMerchantCode());
			result.setErrorCode(ErrorCode.SIGN_INVALID);
			result.setErrorMsg(ErrorCode.SIGN_INVALID_DESC);
			request.setResult(result);
			return result.getErrorCode();
		}
		
		// 查询类型为1的时候要对订单号进行校验
		if(queryType == QueryType.ORDER.getValue()){
			if(StringUtils.isBlank(request.getOrderId())){
				result.setBizNo(request.getBizNo());
				result.setMerchantCode(request.getMerchantCode());
				result.setErrorCode(ErrorCode.ORDER_INVALID);
				result.setErrorMsg(ErrorCode.ORDER_INVALID_DESC);
				request.setResult(result);
				return result.getErrorCode();
			}
		}
		
		// 查询类型为2的时候要对批次号进行验证
		if (queryType == QueryType.BIZNO.getValue()) {
			boolean flag = Pattern.matches(pattern, bizNo);
			if (!flag) {
				result.setBizNo(request.getBizNo());
				result.setMerchantCode(request.getMerchantCode());
				result.setErrorCode(ErrorCode.BUSINESSNO_INVALID);
				result.setErrorMsg(ErrorCode.BUSINESSNO_INVALID_DESC);
				request.setResult(result);
				return result.getErrorCode();
			}
		}

		// 查询类型为4的时候要对查询起始日期和结束日期进行验证
		if (queryType == QueryType.DATE_INTEVAL.getValue()) {
			if(StringUtils.isBlank(request.getStartDate()) || StringUtils.isBlank(request.getEndDate()) || request.getStartDate().length() != 8 || request.getEndDate().length() != 8 || !(Pattern.matches(datePattern, request.getStartDate())) || !(Pattern.matches(datePattern, request.getEndDate()))){
				result.setBizNo(request.getBizNo());
				result.setMerchantCode(request.getMerchantCode());
				result.setErrorCode(ErrorCode.QUERY_TIME_INVALID);
				result.setErrorMsg(ErrorCode.QUERY_TIME_INVALID_DESC);
				request.setResult(result);
				return result.getErrorCode();
			}else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				Date startDate = sdf.parse(request.getStartDate());
				Date endDate = sdf.parse(request.getEndDate());
				if(endDate.before(startDate) || (endDate.getTime()-startDate.getTime())/86400000 > 7){
					result.setBizNo(request.getBizNo());
					result.setMerchantCode(request.getMerchantCode());
					result.setErrorCode(ErrorCode.QUERY_TIME_LIMIT);
					result.setErrorMsg(ErrorCode.QUERY_TIME_LIMIT_DESC);
					request.setResult(result);
					return result.getErrorCode();
				}
			}
		}

		String errorCode = result.getErrorCode();

		return errorCode;
	}

	@Override
	protected String generateResultXml(PaymentRequest request, PaymentResult result) {
		Map parameterMap = new HashMap();

		parameterMap.put("MERCHANT_CODE", request.getMerchantCode());
		parameterMap.put("BIZ_NO", request.getBizNo());
		parameterMap.put("TOTAL_AMOUNT", String.valueOf(result.getTotalAmount()));
		parameterMap.put("TOTAL_COUNT", String.valueOf(result.getTotalCount()));
		parameterMap.put("SUCCESS_AMOUNT", String.valueOf(result.getSuccessAmount()));
		parameterMap.put("SUCCESS_COUNT", String.valueOf(result.getSuccessCount()));
		parameterMap.put("ERROR_CODE", result.getErrorCode());
		parameterMap.put("ERROR_MSG", result.getErrorMsg());
		// 

		List<PaymentItemResult> itemList = result.getItemList();
		parameterMap.put("itemList", itemList);

		String resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(), parameterMap);
		
		String signType = request.getSignType();
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(request.getMerchantCode());
		MerchantConfigure merchantConfigure = (MerchantConfigure) merchantConfigureDao.findObjectByCriteria(criteria);

//		String srcData = ParameterXmlParserUtil.getNodeText(resultXml, "RESPONSE_BODY");
		if(merchantConfigure != null){
			String srcData = buildRequestSignMsg(request);
			
			if(SignType.MD5.getValue().equals(signType)){
				String signvalue = MD5Util.md5Hex(srcData + merchantConfigure.getPublicKey());
				parameterMap.put("SIGNVALUE", signvalue);
			} else if (SignType.RSA.getValue().equals(signType)) {
				String signvalue = securityProvider.generateSignature(srcData);
				parameterMap.put("SIGNVALUE", signvalue);
			}
		}

		resultXml = FreeMarkerUtil.parser(responseXmlTemplate.trim(), parameterMap);

		return resultXml;
	}

	@Override
	protected PaymentResult handle(PaymentRequest request) throws Exception {

		String merchantCode = request.getMerchantCode();
		String orderId = request.getOrderId();
		Integer queryType = Integer.valueOf(request.getQueryType());
		String bizNo = request.getBizNo();
		String startDate = request.getStartDate();
		String endDate = request.getEndDate();

		PaymentResult result = new PaymentResult();
		result.setMerchantCode(merchantCode);
		result.setBizNo(bizNo);
		
		// 查询类型为1，订单号作为唯一查询条件
		if(queryType == QueryType.ORDER.getValue()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			FundoutOrderExample fundoutOrderExample = new FundoutOrderExample();
			fundoutOrderExample.createCriteria().andPayerMemberCodeEqualTo(Long.valueOf(merchantCode)).andForeignOrderIdEqualTo(orderId);
			List<FundoutOrder> orders = fundoutOrderDAO.selectByExample(fundoutOrderExample);
			// 查不到订单
			if(orders == null || orders.size() <= 0){
				result.setMerchantCode(merchantCode);
				result.setErrorCode(ErrorCode.ORDER_INVALID);
				result.setErrorMsg(ErrorCode.ORDER_INVALID_DESC);
			}else{
				FundoutOrder fundoutOrder = (FundoutOrder)orders.get(0);
				result.setMerchantCode(merchantCode);
				result.setTotalAmount(fundoutOrder.getRealoutAmount() == null ? 0l : fundoutOrder.getRealoutAmount()/10);
				result.setTotalCount(1);
				if(fundoutOrder.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()){
					result.setSuccessAmount(fundoutOrder.getRealoutAmount() == null ? 0l : fundoutOrder.getRealoutAmount()/10);
					result.setSuccessCount(1);
				}else{
					result.setSuccessAmount(0l);
					result.setSuccessCount(0);
				}
				List<PaymentItemResult> itemList = new ArrayList<PaymentItemResult>();
				PaymentItemResult paymentItemResult = new PaymentItemResult();
				paymentItemResult.setpaySeqNo(fundoutOrder.getOrderId());
				paymentItemResult.setOrderAmount(String.valueOf((fundoutOrder.getRealoutAmount() == null ? 0l : fundoutOrder.getRealoutAmount()/10)));
				paymentItemResult.setOrderId(fundoutOrder.getForeignOrderId().toString());
				if (fundoutOrder.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
					paymentItemResult.setStatus(RequestStatus.SUCCESS.getValue());
				} else if (fundoutOrder.getOrderStatus() == OrderStatus.PROCESSED_FAILURE.getValue()) {
					paymentItemResult.setStatus(RequestStatus.FAIL.getValue());
				}else{
					paymentItemResult.setStatus(RequestStatus.FAIL.getValue());
				}
				paymentItemResult.setSuccessTime(new SimpleDateFormat("yyyyMMddHHmmss").format(fundoutOrder.getUpdateDate()));
				itemList.add(paymentItemResult);
				result.setItemList(itemList);
			}
		}
		
		if (queryType == QueryType.BIZNO.getValue()) {
			BatchPaymentOrderExample example = new BatchPaymentOrderExample();
			example.createCriteria().andPayerMemberCodeEqualTo(Long.valueOf(merchantCode)).andBusinessBatchNoEqualTo(bizNo);
			List<BatchPaymentOrder> list = batchPaymentOrderDAO.selectByExample(example);
			if (null == list || list.isEmpty()) {
				result.setErrorCode(ErrorCode.ORDER_INVALID);
				result.setErrorCode(ErrorCode.ORDER_INVALID_DESC);
				return result;
			} else {
				BatchPaymentOrder batchOrder = list.get(0);

				if (batchOrder.getOrderStatus() != OrderStatus.PROCESSED_SUCCESS.getValue()) {
					result.setErrorCode(ErrorCode.DOING);
					result.setErrorCode(ErrorCode.DOING_DESC);
					return result;
				}

				if (batchOrder.getOrderSmallType().equals(OrderSmallType.API_BATCHPAY2ACCT.getValue())) {

					result.setTotalAmount(batchOrder.getOrderAmount() == null ? 0l : batchOrder.getOrderAmount()/10);
					result.setTotalCount(batchOrder.getPaymentCount());
					if (OrderStatus.PROCESSED_SUCCESS.getValue() == batchOrder.getOrderStatus()) {
						result.setSuccessAmount(batchOrder.getSuccessAmount() == null ? 0l : batchOrder.getSuccessAmount()/10);
						result.setSuccessCount(batchOrder.getSuccessCount());
					}

					PayToAcctOrderExample acctExample = new PayToAcctOrderExample();
					acctExample.createCriteria().andParentOrderIdEqualTo(batchOrder.getOrderId());
					List<PayToAcctOrder> orders = payToAcctOrderDAO.selectByExample(acctExample);

					List<PaymentItemResult> itemList = buildAcctItemResultList(orders);

					result.setItemList(itemList);

				} else if (batchOrder.getOrderSmallType().equals(OrderSmallType.API_BATCHPAY2BANK.getValue())) {
					result.setTotalAmount(batchOrder.getOrderAmount() == null ? 0l : batchOrder.getOrderAmount()/10);
					result.setTotalCount(batchOrder.getPaymentCount());
					if (OrderStatus.PROCESSED_SUCCESS.getValue() == batchOrder.getOrderStatus()) {
						result.setSuccessAmount(batchOrder.getSuccessAmount() == null ? 0l : batchOrder.getSuccessAmount()/10);
						result.setSuccessCount(batchOrder.getSuccessCount());
					}
					FundoutOrderExample bankExample = new FundoutOrderExample();
					bankExample.createCriteria().andParentOrderIdEqualTo(batchOrder.getOrderId());
					List<FundoutOrder> orders = fundoutOrderDAO.selectByExample(bankExample);
					List<PaymentItemResult> itemList = buildBankItemResultList(orders);
					result.setItemList(itemList);
				} else {
					result.setErrorCode(ErrorCode.ORDER_INVALID);
					result.setErrorCode(ErrorCode.ORDER_INVALID_DESC);
				}

			}
		}
		
		// 查询类型为4，起始时间和结束时间是需要输入的查询条件
		if (queryType == QueryType.DATE_INTEVAL.getValue()) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			FundoutOrderExample fundoutOrderExample = new FundoutOrderExample();
			fundoutOrderExample.createCriteria().andPayerMemberCodeEqualTo(Long.valueOf(merchantCode)).andCreateDateBetween(sdf.parse(startDate), sdf.parse(endDate));
			List<FundoutOrder> orders = fundoutOrderDAO.selectByExample(fundoutOrderExample);
			Long totalAmount = 0l;
			Integer totalCount = 0;
			Long successAmount = 0l;
			Integer successCount = 0;
			if(orders == null || orders.size() <= 0){
				result.setMerchantCode(merchantCode);
			}else{
				result.setMerchantCode(merchantCode);
				for(FundoutOrder order : orders){
					totalAmount += order.getRealoutAmount();
					totalCount += 1;
					if(order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()){
						successAmount += order.getRealoutAmount();
						successCount += 1;
					}
				}
				result.setTotalAmount(totalAmount/10);
				result.setTotalCount(totalCount);
				result.setSuccessAmount(successAmount/10);
				result.setSuccessCount(successCount);
				List<PaymentItemResult> itemList = new ArrayList<PaymentItemResult>();
				for(FundoutOrder order : orders){
					PaymentItemResult item = new PaymentItemResult();
					item.setpaySeqNo(order.getOrderId());
					item.setOrderAmount(String.valueOf(order.getRealoutAmount() == null ? 0l : order.getRealoutAmount()/10));
					item.setOrderId(order.getOrderId().toString());
					if (order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
						item.setStatus(RequestStatus.SUCCESS.getValue());
					} else if (order.getOrderStatus() == OrderStatus.PROCESSED_FAILURE.getValue()) {
						item.setStatus(RequestStatus.FAIL.getValue());
					}else{
						item.setStatus(RequestStatus.FAIL.getValue());
					}
					item.setSuccessTime(new SimpleDateFormat("yyyyMMddHHmmss").format(order.getUpdateDate()));
					itemList.add(item);
				}
				result.setItemList(itemList);
			}
		}
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
	
	private List<PaymentItemResult> buildAcctItemResultList(List<PayToAcctOrder> orders) {
		List<PaymentItemResult> itemList = new ArrayList<PaymentItemResult>();
		for (PayToAcctOrder order : orders) {
			PaymentItemResult item = new PaymentItemResult();
			item.setOrderAmount(String.valueOf(order.getOrderAmount() == null ? 0l : order.getOrderAmount() / 10));
			item.setpaySeqNo(order.getOrderId());
			item.setOrderId(order.getForeignOrderId());
			if (order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
				item.setStatus(RequestStatus.SUCCESS.getValue());
			} else if (order.getOrderStatus() == OrderStatus.PROCESSED_FAILURE.getValue()) {
				item.setStatus(RequestStatus.FAIL.getValue());
			}
			item.setSuccessTime(new SimpleDateFormat("yyyyMMddHHmmss").format(order.getUpdateDate()));
			itemList.add(item);
		}
		return itemList;
	}

	private List<PaymentItemResult> buildBankItemResultList(List<FundoutOrder> orders) {
		List<PaymentItemResult> itemList = new ArrayList<PaymentItemResult>();
		for (FundoutOrder order : orders) {
			PaymentItemResult item = new PaymentItemResult();
			item.setOrderAmount(String.valueOf(order.getOrderAmount() == null ? 0l : order.getOrderAmount() / 10));
			item.setpaySeqNo(order.getOrderId());
			item.setOrderId(order.getForeignOrderId());
			if (order.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
				item.setStatus(RequestStatus.SUCCESS.getValue());
			} else if (order.getOrderStatus() == OrderStatus.PROCESSED_FAILURE.getValue()) {
				item.setStatus(RequestStatus.FAIL.getValue());
			}
			item.setSuccessTime(new SimpleDateFormat("yyyyMMddHHmmss").format(order.getUpdateDate()));
			itemList.add(item);
		}
		return itemList;
	}

	/**
	 * 签名域，商户号和查询类型
	 * @param request
	 * @return
	 */
	private String buildRequestSignMsg(PaymentRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append(request.getMerchantCode());
		sb.append(request.getQueryType());
		return sb.toString();
	}

}
