package com.pay.acc.member.dto;

import java.util.Date;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		EnterpriseContact.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */
public class EnterpriseContactDto {
    private Long contactId;//主键
    private Long memberCode;//会员号，关联到t_member表主键
    private String address;//企业会员地址
    private String fax;//企业传真
    private String tel;//企业联系电话
    private String zip;//邮编
    private String legalName;//企业法人姓名
    private String legalLink;//手机或者电话
    private String email;//邮件地址
    private String financeName;//财务联系人
    private String financeLink;//财务联系手机或者电话
    private String compayLinkerName;//企业联系人
    private String compayLinkerTel;//企业联系人电话
    private String techName;//公司技术人姓名
    private String techLink;//公司技术联系手机或者电话
    private String webName1;//网站名称1
    private String webAddr1;//网站地址1
    private String webName2;//网站名称2
    private String webAddr2;//网站地址2
    private String webName3;//网站名称3
    private String webAddr3;//网站地址3
    private Date createDate;//数据创建数据
    private Date updateDate;//数据更新时间

    public String getCompayLinkerName() {
		return compayLinkerName;
	}

	public void setCompayLinkerName(String compayLinkerName) {
		this.compayLinkerName = compayLinkerName;
	}

	public String getCompayLinkerTel() {
		return compayLinkerTel;
	}

	public void setCompayLinkerTel(String compayLinkerTel) {
		this.compayLinkerTel = compayLinkerTel;
	}

	public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getLegalLink() {
        return legalLink;
    }

    public void setLegalLink(String legalLink) {
        this.legalLink = legalLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFinanceName() {
        return financeName;
    }

    public void setFinanceName(String financeName) {
        this.financeName = financeName;
    }

    public String getFinanceLink() {
        return financeLink;
    }

    public void setFinanceLink(String financeLink) {
        this.financeLink = financeLink;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getTechLink() {
        return techLink;
    }

    public void setTechLink(String techLink) {
        this.techLink = techLink;
    }

    public String getWebName1() {
        return webName1;
    }

    public void setWebName1(String webName1) {
        this.webName1 = webName1;
    }

    public String getWebAddr1() {
        return webAddr1;
    }

    public void setWebAddr1(String webAddr1) {
        this.webAddr1 = webAddr1;
    }

    public String getWebName2() {
        return webName2;
    }

    public void setWebName2(String webName2) {
        this.webName2 = webName2;
    }

    public String getWebAddr2() {
        return webAddr2;
    }

    public void setWebAddr2(String webAddr2) {
        this.webAddr2 = webAddr2;
    }

    public String getWebName3() {
        return webName3;
    }

    public void setWebName3(String webName3) {
        this.webName3 = webName3;
    }

    public String getWebAddr3() {
        return webAddr3;
    }

    public void setWebAddr3(String webAddr3) {
        this.webAddr3 = webAddr3;
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
}