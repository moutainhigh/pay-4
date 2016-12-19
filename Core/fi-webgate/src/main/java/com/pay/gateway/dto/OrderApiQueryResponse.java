/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class OrderApiQueryResponse {

	private String queryOrderId;
	private String mode;
	private String dataSetPath;
	private String type;
	private String resultCode;
	private String resultMsg;
	private String detailsSize;
	private String partnerId;
	private String registerUserId;
	private String remark;
	private String charset;
	private String signType;
	private String signMsg;
	private List<OrderApiQueryDetailResponse> details;
	private List<OrderApiQueryRefundDetailResponse> refundDetails;
	private List<OrderApiQuerySettlementDetailResponse> settlementDetails;
	private List<OrderCardBindQueryDetailResponse> cardBindDetails;

	private List<OrderApiPreAuthDetailResponse> preAuthDetails;
	private List<OrderApiPreAuthVoidDetailResponse> preAuthVoidDetails;
	private List<OrderApiPreAuthCapDetailResponse> preAuthCapDetails;
    
	public String getRegisterUserId() {
		return registerUserId;
	}

	public void setRegisterUserId(String registerUserId) {
		this.registerUserId = registerUserId;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetailsSize() {
		return detailsSize;
	}

	public void setDetailsSize(String detailsSize) {
		this.detailsSize = detailsSize;
	}

	public String getQueryOrderId() {
		return queryOrderId;
	}

	public void setQueryOrderId(String queryOrderId) {
		this.queryOrderId = queryOrderId;
	}

	public List<OrderApiQueryDetailResponse> getDetails() {
		return details;
	}

	public void setDetails(List<OrderApiQueryDetailResponse> details) {
		this.details = details;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getRemark() {
		return remark;
	}

	public String getDataSetPath() {
		return dataSetPath;
	}

	public void setDataSetPath(String dataSetPath) {
		this.dataSetPath = dataSetPath;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSignMsg() {

		return signMsg;
	}

	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}

	public List<OrderApiQueryRefundDetailResponse> getRefundDetails() {
		return refundDetails;
	}

	public void setRefundDetails(
			List<OrderApiQueryRefundDetailResponse> refundDetails) {
		this.refundDetails = refundDetails;
	}

	public List<OrderApiQuerySettlementDetailResponse> getSettlementDetails() {
		return settlementDetails;
	}

	public void setSettlementDetails(
			List<OrderApiQuerySettlementDetailResponse> settlementDetails) {
		this.settlementDetails = settlementDetails;
	}

	public List<OrderCardBindQueryDetailResponse> getCardBindDetails() {
		return cardBindDetails;
	}

	public void setCardBindDetails(
			List<OrderCardBindQueryDetailResponse> cardBindDetails) {
		this.cardBindDetails = cardBindDetails;
	}

	public List<OrderApiPreAuthDetailResponse> getPreAuthDetails() {
		return preAuthDetails;
	}

	public void setPreAuthDetails(List<OrderApiPreAuthDetailResponse> preAuthDetails) {
		this.preAuthDetails = preAuthDetails;
	}

	public List<OrderApiPreAuthVoidDetailResponse> getPreAuthVoidDetails() {
		return preAuthVoidDetails;
	}

	public void setPreAuthVoidDetails(List<OrderApiPreAuthVoidDetailResponse> preAuthVoidDetails) {
		this.preAuthVoidDetails = preAuthVoidDetails;
	}

	public List<OrderApiPreAuthCapDetailResponse> getPreAuthCapDetails() {
		return preAuthCapDetails;
	}

	public void setPreAuthCapDetails(List<OrderApiPreAuthCapDetailResponse> preAuthCapDetails) {
		this.preAuthCapDetails = preAuthCapDetails;
	}

	public String generateSign() {
		String returnStr = null;
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						if(value instanceof List) {//如果是list list只能有一组
							sb.append(key);
							sb.append("=");
							sb.append("[");
							StringBuilder insideBuilder = new StringBuilder();
							if(null != value){
								List listObjs = (List)value;
								for (Object obj:listObjs
										) {
									insideBuilder.append("{").append(obj).append("}").append(",");
								}
								if(insideBuilder.length() > 0){
									sb.append(insideBuilder.substring(0, insideBuilder.length() -1));
								}
							}
							sb.append("]");
						}else{
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}
						sb.append("&");
					}
				}
			}
			if(sb.length() > 0){
				returnStr = sb.substring(0, sb.length() -1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			int i = 0;
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				i++;
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						sb.append(key);
						sb.append("=");
						sb.append(value);
					}
					if (i < properties.length) {
						sb.append("&");
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		OrderApiQueryResponse response = new OrderApiQueryResponse();
		response.setCharset("1");
		response.setDetailsSize("1");
		response.setMode("1");
		response.setPartnerId("10000003593");
		response.setQueryOrderId("1435031404718");
		response.setRemark("11111");
		response.setResultCode("0000");
		response.setResultMsg("成功");
		response.setSignMsg("JJJJJJ");
		response.setSignType("1");
		response.setType("1");

		OrderApiQueryRefundDetailResponse detail = new OrderApiQueryRefundDetailResponse();

		detail.setDealId("106787987979878787");
		detail.setOrderId("1435031404718");
		detail.setRefundAmount("1000");
		detail.setRefundOrderId("1435031404718");
		detail.setStateCode("1");
		List<OrderApiQueryRefundDetailResponse> details = new ArrayList<OrderApiQueryRefundDetailResponse>();

		details.add(detail);
		response.setRefundDetails(details);
		System.out.println(response.generateSign());
	}

}
