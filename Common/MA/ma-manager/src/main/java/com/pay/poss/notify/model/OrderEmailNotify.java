package com.pay.poss.notify.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 商户下单邮件通知实体类
 * @author davis.guo at 2016-09-02
 */
public class OrderEmailNotify implements Serializable {

	private static final long serialVersionUID = 6333485103792012541L;
	
	private Long id ;
	private Long memberCode;//会员号
	private Long merchantCode;//商户号
	private String merchantName;//商户名称
	private String loginName;//登录名
	
	private String emailCompany ;//公司email
	private String emailNotify ;//通知email
	private Integer openFlag ;//0-未开通, 1-已开通
	private Date createDate ;//创建时间
	private Date updateDate ;//修改时间
	private String operator ;//操作员
	private Long maxTradeOrderNo ;//当前最大网关订单号
	
	private String createTime ;//页面显示时间
	private String updateTime ;//页面显示时间
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public String getCreateTime() {
		if(createDate != null)
		{
			sdf.format(createDate);
		}
		return sdf.format(new Date());
	}
	public String getUpdateTime() {
		if(updateDate != null)
		{
			sdf.format(updateDate);
		}
		return sdf.format(new Date());
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Long getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(Long merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmailCompany() {
		return emailCompany;
	}

	public void setEmailCompany(String emailCompany) {
		this.emailCompany = emailCompany;
	}

	public String getEmailNotify() {
		return emailNotify;
	}

	public void setEmailNotify(String emailNotify) {
		this.emailNotify = emailNotify;
	}

	public Integer getOpenFlag() {
		return openFlag;
	}

	public void setOpenFlag(Integer openFlag) {
		this.openFlag = openFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Long getMaxTradeOrderNo() {
		return maxTradeOrderNo;
	}

	public void setMaxTradeOrderNo(Long maxTradeOrderNo) {
		this.maxTradeOrderNo = maxTradeOrderNo;
	}
	
    
}