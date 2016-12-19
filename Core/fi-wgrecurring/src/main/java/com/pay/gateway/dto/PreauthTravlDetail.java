package com.pay.gateway.dto;

import java.beans.PropertyDescriptor;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

public class PreauthTravlDetail {
	private String travelName;
	private String travelCustomID;
	private String travelRegTime;
	private String travelportNo;
	private String travelRegEmail;
	private String travelDateTime;
	private String travelCountry;
	private String travelCity;
	private String travelPros;
	
	
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
