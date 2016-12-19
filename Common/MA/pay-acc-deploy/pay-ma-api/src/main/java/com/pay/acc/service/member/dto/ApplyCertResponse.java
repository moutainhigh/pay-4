package com.pay.acc.service.member.dto;

import java.io.Serializable;

public class ApplyCertResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5292966599064036807L;
	
	
	
	public ApplyCertResponse(String dn,String authCode,String refNo){
		this.authCode=authCode;
		this.dn=dn;
		this.refNo=refNo;
	}
	
	public ApplyCertResponse(String dn,String authCode,String refNo,Integer status,String signCertPem){
		this.authCode=authCode;
		this.dn=dn;
		this.refNo=refNo;
		this.status=status;
		this.signCertPem=signCertPem;
	}
	/**
	 * 证书DN
	 */
	private String dn;
	/**
	 * 证书授权码
	 */
	private String authCode;
	/**
	 * 证书参考号
	 */
	private String refNo;
	
	private String encCertPem;

    private String encPriKeyPem;
	
    private String signCertPem;
    
    
    private Integer status;
    
	
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getRefNo() {
		return refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	public String getEncCertPem() {
		return encCertPem;
	}
	public void setEncCertPem(String encCertPem) {
		this.encCertPem = encCertPem;
	}
	public String getEncPriKeyPem() {
		return encPriKeyPem;
	}
	public void setEncPriKeyPem(String encPriKeyPem) {
		this.encPriKeyPem = encPriKeyPem;
	}
	public String getSignCertPem() {
		return signCertPem;
	}
	public void setSignCertPem(String signCertPem) {
		this.signCertPem = signCertPem;
	}
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCn() {
		if(dn!=null && dn.split(",").length>0){
			 return dn.split(",")[0];
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "ApplyCertResponse [dn=" + dn + ", authCode=" + authCode
				+ ", refNo=" + refNo + ", encCertPem=" + encCertPem
				+ ", encPriKeyPem=" + encPriKeyPem + ", signCertPem="
				+ signCertPem + "]";
	}
}
