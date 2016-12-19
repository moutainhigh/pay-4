package com.pay.txncore.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

/**
 * 旅客信息
 * @author peiyu.yang
 *
 */
public class PreauthTravlDetailDTO {
	/**
	 * 旅客姓名
	 */
	private String travelName;
	/**
	 * 会员号
	 */
	private String travelCustomID;
	
	/**
	 * 注册时间
	 */
	private String travelRegTime;
	
	/**
	 * 护照编号
	 */
	private String travelportNo;
	
	/**
	 * 电子邮箱
	 */
	private String travelRegEmail;
	
	/**
	 * 前往时间
	 */
	private String travelDateTime;
	
	/**
	 * 入住/前往国家(代码)
	 */
	private String travelCountry;
	
	/**
	 * 入住/前往城市（代码）
	 */
	private String travelCity;
	
	/**
	 * 旅程
	 */
	private String travelPros;
	
	private String orderId;
	
	
	public String getTravelName() {
		return travelName;
	}
	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
	public String getTravelCustomID() {
		return travelCustomID;
	}
	public void setTravelCustomID(String travelCustomID) {
		this.travelCustomID = travelCustomID;
	}
	public String getTravelRegTime() {
		return travelRegTime;
	}
	public void setTravelRegTime(String travelRegTime) {
		this.travelRegTime = travelRegTime;
	}
	public String getTravelportNo() {
		return travelportNo;
	}
	public void setTravelportNo(String travelportNo) {
		this.travelportNo = travelportNo;
	}
	public String getTravelRegEmail() {
		return travelRegEmail;
	}
	public void setTravelRegEmail(String travelRegEmail) {
		this.travelRegEmail = travelRegEmail;
	}
	public String getTravelDateTime() {
		return travelDateTime;
	}
	public void setTravelDateTime(String travelDateTime) {
		this.travelDateTime = travelDateTime;
	}
	public String getTravelCountry() {
		return travelCountry;
	}
	public void setTravelCountry(String travelCountry) {
		this.travelCountry = travelCountry;
	}
	public String getTravelCity() {
		return travelCity;
	}
	public void setTravelCity(String travelCity) {
		this.travelCity = travelCity;
	}
	public String getTravelPros() {
		return travelPros;
	}
	public void setTravelPros(String travelPros) {
		this.travelPros = travelPros;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			BeanWrapper bean = new BeanWrapperImpl(this);
			PropertyDescriptor[] properties = bean.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : properties) {
				String key = propertyDescriptor.getDisplayName();
				Object value = bean.getPropertyValue(key);
				if (!"class".equals(key)) {
					if (!StringUtil.isEmpty(value+"")) {
						if(sb.length()<1){
							sb.append(key);
							sb.append("=");
							sb.append(value);
						}else{
							sb.append(",");
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
}
