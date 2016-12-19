package com.pay.poss.memberrelation.dto;

import java.io.Serializable;
/**   
* @Title: RelationDataDto.java 
* @Package com.pay.poss.memberrelation.dto 
* @Description: 关联名单数据传输对象
* @author cf
* @date 2011-9-22 下午12:50:03 
* @version V1.0   
*/

public class RelationDataDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7158642451824060563L;

	private String  fatherMemberCode;			//总店会员号
	private String  relationStoreNumbers;		//门店编号
	private String  relationStoreAddress;		//门店地址
	private String  relationStoreName;			//店主姓名
	private String  relationMobileNo;			//手机号码
	private String  amountEmail;				//账户（Email）
	private Long    sunMemberCode;				//门店会员号
	private boolean insertFlag=false;			//是否新增系统标记

	public boolean isInsertFlag() {
		return insertFlag;
	}

	public void setInsertFlag(boolean insertFlag) {
		this.insertFlag = insertFlag;
	}

	public RelationDataDto(String fatherMemberCode,String relationStoreNumbers,String relationStoreAddress,String relationStoreName,String relationMobileNo,String amountEmail){
		super();
		this.fatherMemberCode=fatherMemberCode;
		this.relationMobileNo=relationMobileNo;
		this.relationStoreAddress=relationStoreAddress;
		this.relationStoreName=relationStoreName;
		this.relationStoreNumbers=relationStoreNumbers;
		this.amountEmail=amountEmail;
	}
	
	public Long getSunMemberCode() {
		return sunMemberCode;
	}

	public void setSunMemberCode(Long sunMemberCode) {
		this.sunMemberCode = sunMemberCode;
	}
	
	public String getFatherMemberCode() {
		return fatherMemberCode;
	}


	public void setFatherMemberCode(String fatherMemberCode) {
		this.fatherMemberCode = fatherMemberCode;
	}


	public String getRelationStoreNumbers() {
		return relationStoreNumbers;
	}


	public void setRelationStoreNumbers(String relationStoreNumbers) {
		this.relationStoreNumbers = relationStoreNumbers;
	}


	public String getRelationStoreAddress() {
		return relationStoreAddress;
	}


	public void setRelationStoreAddress(String relationStoreAddress) {
		this.relationStoreAddress = relationStoreAddress;
	}


	public String getRelationStoreName() {
		return relationStoreName;
	}


	public void setRelationStoreName(String relationStoreName) {
		this.relationStoreName = relationStoreName;
	}


	public String getRelationMobileNo() {
		return relationMobileNo;
	}


	public void setRelationMobileNo(String relationMobileNo) {
		this.relationMobileNo = relationMobileNo;
	}


	public String getAmountEmail() {
		return amountEmail;
	}


	public void setAmountEmail(String amountEmail) {
		this.amountEmail = amountEmail;
	}


	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("fatherMemberCode= "+fatherMemberCode);
		buffer.append("relationStoreNumbers= "+relationStoreNumbers);
		buffer.append("relationStoreAddress= "+relationStoreAddress);
		buffer.append("relationStoreName= "+relationStoreName);
		buffer.append("relationMobileNo= "+relationMobileNo);
		buffer.append("amountEmail= "+amountEmail);
		return buffer.toString() ;
	}
	
	
}
