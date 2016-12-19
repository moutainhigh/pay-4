/**
 * 
 */
package com.pay.pe.account.dto;

import java.util.Date;
import java.util.List;

import com.pay.util.DateUtil;

/**
 * @Description  累计收费失败查询参数dto
 * Date			Author			Changes
 * 2012-3-8		DDR				Create
 */
public class AccountingFeeFailedParamDto {
	
	private String beginDate;
	private String endDate;
	private String datePattern = "yyyy-MM-dd HH:mm:ss";
	private Long memberCode;   		//会员号
	private List memberCodes;
	private String acctName;			//账户 名
	private String serviceName;			//服务名
	
	public Date getBegin() {
		if(beginDate!=null && beginDate.trim().length()>0 ){
			try{
				return DateUtil.parse(datePattern,beginDate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}
	/**
	 * @return the createEnd
	 */
	public Date getEnd() {
		if(endDate!=null && endDate.trim().length()>0){
			try{
				return DateUtil.parse(datePattern,endDate);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the datePattern
	 */
	public String getDatePattern() {
		return datePattern;
	}
	/**
	 * @param datePattern the datePattern to set
	 */
	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public List<Long> getMemberCodes() {
		return memberCodes;
	}
	public void setMemberCodes(List<Long> memberCodes) {
		this.memberCodes = memberCodes;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	
	public String getMemberCodesToStr(){
		if(memberCodes==null || memberCodes.size()==0){
			return null;
		}
		int lenMax = memberCodes.size() - 1;
		StringBuffer str = new StringBuffer();
		for(int i=0;i<=lenMax;i++){
			str.append(memberCodes.get(i)+(lenMax==i?"":","));
		}
		return str.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
