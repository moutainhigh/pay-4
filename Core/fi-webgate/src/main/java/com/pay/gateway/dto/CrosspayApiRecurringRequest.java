/**
 * 
 */
package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class CrosspayApiRecurringRequest  extends CrosspayApiRequest{

	
	/**
	 * 循环扣款
	 */
	//扣款频率M/Y
	private String frequency;
	//剩余期数
	private String period;
	//最大失败次数
	private String maxFailedTimes;
	//添加扣款状态验证状态
	private String recurringFlag;
	// 1为创建循环退款 2为取消循环退款
	private String recurringStatus;
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getMaxFailedTimes() {
		return maxFailedTimes;
	}
	public void setMaxFailedTimes(String maxFailedTimes) {
		this.maxFailedTimes = maxFailedTimes;
	}
	public String getRecurringFlag() {
		return recurringFlag;
	}
	public void setRecurringFlag(String recurringFlag) {
		this.recurringFlag = recurringFlag;
	}
	public String getRecurringStatus() {
		return recurringStatus;
	}
	public void setRecurringStatus(String recurringStatus) {
		this.recurringStatus = recurringStatus;
	}
//	@Override
//	public String toString() {
//		return "CrosspayApiRecurringRequest [frequency=" + frequency
//				+ ", period=" + period + ", maxFailedTimes=" + maxFailedTimes
//				+ ", recurringFlag=" + recurringFlag + ", recurringStatus="
//				+ recurringStatus + "]";
//	}
	public String generateSign() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			int i = 0;
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				i++;
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key) && !"signMsg".equals(key)
						&& !"crosspayApiResponse".equals(key)
						&& !"crosspayApiRecurringResponse".equals(key) 
						&& !"recurringFlag".equals(key)) {
					
					if (!StringUtil.isEmpty(value+"")) {
						if(sb.length()<1){
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}else{
							sb.append("&");
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
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
		
		System.out.println("sign: "+sb.toString());
		return sb.toString();
	}

	/*public static void main(String[] args) throws Exception {
		CrosspayApiRequest request = new CrosspayApiRequest();
		request.setBillAddress("helloda");
		request.setBillCity("nidaye");
		request.setBillCountryCode("helloiddd");
		System.out.println(request.generateSign());
	}*/
}
