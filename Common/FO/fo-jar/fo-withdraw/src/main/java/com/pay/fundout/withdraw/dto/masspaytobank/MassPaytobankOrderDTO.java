
package com.pay.fundout.withdraw.dto.masspaytobank;

import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

/**
 * 
 * @author Sean_yi
 * @createtime 2010-12-21
 * @filename MassPaytobankOrderDTO.java
 * @version 1.0
 */
public class MassPaytobankOrderDTO  extends  BaseOrderDTO {

	private static final long serialVersionUID = 3049547002814825919L;
	
	private String payerAcctCode;
	
    private Integer totalNum;
    
    private Long totalFee;
    
    private Integer payerAcctType;
    
    private Long massOrderSeq;
    
    private Integer status;
    
    private String remark;
    
    private String businessNo;
    
    private Long totalAmount;
    
    private Long payerMemberCode;
    
    private String payerOperator;
    
    private Date createDate;
    
    private Date updateDate;
    
    private Integer isPayerPayFee;
    
    private Long realPayAmount;
    
    private Long realOutAmount;
    
    private Integer oldStatus;

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

    public String getPayerAcctCode (){
        return payerAcctCode;
    }
    
    public void setPayerAcctCode (String payerAcctCode){
        this.payerAcctCode = payerAcctCode;
    }
    public Integer getTotalNum (){
        return totalNum;
    }
    
    public void setTotalNum (Integer totalNum){
        this.totalNum = totalNum;
    }
    public Long getTotalFee (){
        return totalFee;
    }
    
    public void setTotalFee (Long totalFee){
        this.totalFee = totalFee;
    }
    public Integer getPayerAcctType (){
        return payerAcctType;
    }
    
    public void setPayerAcctType (Integer payerAcctType){
        this.payerAcctType = payerAcctType;
    }
    public Long getMassOrderSeq (){
        return massOrderSeq;
    }
    
    public void setMassOrderSeq (Long massOrderSeq){
        this.massOrderSeq = massOrderSeq;
    }
    public Integer getStatus (){
        return status;
    }
    
    public void setStatus (Integer status){
        this.status = status;
    }
    public String getRemark (){
        return remark;
    }
    
    public void setRemark (String remark){
        this.remark = remark;
    }
    public String getBusinessNo (){
        return businessNo;
    }
    
    public void setBusinessNo (String businessNo){
        this.businessNo = businessNo;
    }
    public Long getTotalAmount (){
        return totalAmount;
    }
    
    public void setTotalAmount (Long totalAmount){
        this.totalAmount = totalAmount;
    }
    public Long getPayerMemberCode (){
        return payerMemberCode;
    }
    
    public void setPayerMemberCode (Long payerMemberCode){
        this.payerMemberCode = payerMemberCode;
    }
    public String getPayerOperator (){
        return payerOperator;
    }
    
    public void setPayerOperator (String payerOperator){
        this.payerOperator = payerOperator;
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

	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
	}

	public Long getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(Long realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public Long getRealOutAmount() {
		return realOutAmount;
	}

	public void setRealOutAmount(Long realOutAmount) {
		this.realOutAmount = realOutAmount;
	}
    
    

}