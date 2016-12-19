
package com.pay.fundout.withdraw.model.withdrawback;
/**
 * 
 * @author jack.liu_liu
 *2010.9.17
 */

public class WithdrawOrder  implements java.io.Serializable {

    private String errorMessage;
    private java.util.Date createTime;
    private Long memberType;
    private Long memberAcc;
    private Long type;
    private Integer bankProvince;
    private Long memberAccType;
    private Long amount;
    private Long busiType;
    private String bankRemarks;
    private String bankKy;
    private String orderSeqId;
    private Integer prioritys;
    private Long memberCode;
    private String orderRemarks;
    private Long bankAcctType;
    private java.util.Date updateTime;
    private Long status;
    private Integer bankCity;
    private String fundorigin;
    private String withdrawBankCode;
    private String bankPurpose;
    private String bankAcct;
    private Integer withdrawType;
    private String accountName;
    private Long sequenceId;
    private String moneyType;
    private String bankBranch;

    public String getErrorMessage (){
        return errorMessage;
    }
    
    public void setErrorMessage (String errorMessage){
        this.errorMessage = errorMessage;
    }
    public java.util.Date getCreateTime (){
        return createTime;
    }
    
    public void setCreateTime (java.util.Date createTime){
        this.createTime = createTime;
    }
    public Long getMemberType (){
        return memberType;
    }
    
    public void setMemberType (Long memberType){
        this.memberType = memberType;
    }
    public Long getMemberAcc (){
        return memberAcc;
    }
    
    public void setMemberAcc (Long memberAcc){
        this.memberAcc = memberAcc;
    }
    public Long getType (){
        return type;
    }
    
    public void setType (Long type){
        this.type = type;
    }
    public Integer getBankProvince (){
        return bankProvince;
    }
    
    public void setBankProvince (Integer bankProvince){
        this.bankProvince = bankProvince;
    }
    public Long getMemberAccType (){
        return memberAccType;
    }
    
    public void setMemberAccType (Long memberAccType){
        this.memberAccType = memberAccType;
    }
    public Long getAmount (){
        return amount;
    }
    
    public void setAmount (Long amount){
        this.amount = amount;
    }
    public Long getBusiType (){
        return busiType;
    }
    
    public void setBusiType (Long busiType){
        this.busiType = busiType;
    }
    public String getBankRemarks (){
        return bankRemarks;
    }
    
    public void setBankRemarks (String bankRemarks){
        this.bankRemarks = bankRemarks;
    }
    public String getBankKy (){
        return bankKy;
    }
    
    public void setBankKy (String bankKy){
        this.bankKy = bankKy;
    }
    public String getOrderSeqId (){
        return orderSeqId;
    }
    
    public void setOrderSeqId (String orderSeqId){
        this.orderSeqId = orderSeqId;
    }
    public Integer getPrioritys (){
        return prioritys;
    }
    
    public void setPrioritys (Integer prioritys){
        this.prioritys = prioritys;
    }
    public Long getMemberCode (){
        return memberCode;
    }
    
    public void setMemberCode (Long memberCode){
        this.memberCode = memberCode;
    }
    public String getOrderRemarks (){
        return orderRemarks;
    }
    
    public void setOrderRemarks (String orderRemarks){
        this.orderRemarks = orderRemarks;
    }
    public Long getBankAcctType (){
        return bankAcctType;
    }
    
    public void setBankAcctType (Long bankAcctType){
        this.bankAcctType = bankAcctType;
    }
    public java.util.Date getUpdateTime (){
        return updateTime;
    }
    
    public void setUpdateTime (java.util.Date updateTime){
        this.updateTime = updateTime;
    }
    public Long getStatus (){
        return status;
    }
    
    public void setStatus (Long status){
        this.status = status;
    }
    public Integer getBankCity (){
        return bankCity;
    }
    
    public void setBankCity (Integer bankCity){
        this.bankCity = bankCity;
    }
    public String getFundorigin (){
        return fundorigin;
    }
    
    public void setFundorigin (String fundorigin){
        this.fundorigin = fundorigin;
    }
    public String getWithdrawBankCode (){
        return withdrawBankCode;
    }
    
    public void setWithdrawBankCode (String withdrawBankCode){
        this.withdrawBankCode = withdrawBankCode;
    }
    public String getBankPurpose (){
        return bankPurpose;
    }
    
    public void setBankPurpose (String bankPurpose){
        this.bankPurpose = bankPurpose;
    }
    public String getBankAcct (){
        return bankAcct;
    }
    
    public void setBankAcct (String bankAcct){
        this.bankAcct = bankAcct;
    }
    public Integer getWithdrawType (){
        return withdrawType;
    }
    
    public void setWithdrawType (Integer withdrawType){
        this.withdrawType = withdrawType;
    }
    public String getAccountName (){
        return accountName;
    }
    
    public void setAccountName (String accountName){
        this.accountName = accountName;
    }
    public Long getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Long sequenceId){
        this.sequenceId = sequenceId;
    }
    public String getMoneyType (){
        return moneyType;
    }
    
    public void setMoneyType (String moneyType){
        this.moneyType = moneyType;
    }
    public String getBankBranch (){
        return bankBranch;
    }
    
    public void setBankBranch (String bankBranch){
        this.bankBranch = bankBranch;
    }

}