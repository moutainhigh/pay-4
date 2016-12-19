/**
*
* jack.liu_liu  20100926
*
**/
package com.pay.fundout.withdraw.model.paytoaccount;
/**
* MASSPAY_ORDER
*/

public class PayRequest  implements java.io.Serializable {

	
	
    private Long sequenceid;
    private String errorTips;
    private Integer payerAcctType;
    private Integer status;
    private Long payerMember;
    private String callSeq;
    private String batchNum;
    private String remarks;
    private String busiCode;
    private Long payeeMember;
    private java.util.Date updateDate;
    private Long amount;
    private Integer payeeAcctType;
    private java.util.Date createDate;
	private String payerAccount;
	private String payeeAccount;
    public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getPayeeAccount() {
		return payeeAccount;
	}

	public void setPayeeAccount(String payeeAccount) {
		this.payeeAccount = payeeAccount;
	}

	public Long getSequenceid (){
        return sequenceid;
    }
    
    public void setSequenceid (Long sequenceid){
        this.sequenceid = sequenceid;
    }
    public String getErrorTips (){
        return errorTips;
    }
    
    public void setErrorTips (String errorTips){
        this.errorTips = errorTips;
    }
    public Integer getPayerAcctType (){
        return payerAcctType;
    }
    
    public void setPayerAcctType (Integer payerAcctType){
        this.payerAcctType = payerAcctType;
    }
    public Integer getStatus (){
        return status;
    }
    
    public void setStatus (Integer status){
        this.status = status;
    }
    public Long getPayerMember (){
        return payerMember;
    }
    
    public void setPayerMember (Long payerMember){
        this.payerMember = payerMember;
    }
    public String getCallSeq (){
        return callSeq;
    }
    
    public void setCallSeq (String callSeq){
        this.callSeq = callSeq;
    }
    public String getBatchNum (){
        return batchNum;
    }
    
    public void setBatchNum (String batchNum){
        this.batchNum = batchNum;
    }
    public String getRemarks (){
        return remarks;
    }
    
    public void setRemarks (String remarks){
        this.remarks = remarks;
    }
    public String getBusiCode (){
        return busiCode;
    }
    
    public void setBusiCode (String busiCode){
        this.busiCode = busiCode;
    }
    public Long getPayeeMember (){
        return payeeMember;
    }
    
    public void setPayeeMember (Long payeeMember){
        this.payeeMember = payeeMember;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }
    public Long getAmount (){
        return amount;
    }
    
    public void setAmount (Long amount){
        this.amount = amount;
    }
    public Integer getPayeeAcctType (){
        return payeeAcctType;
    }
    
    public void setPayeeAcctType (Integer payeeAcctType){
        this.payeeAcctType = payeeAcctType;
    }
    public java.util.Date getCreateDate (){
        return createDate;
    }
    
    public void setCreateDate (java.util.Date createDate){
        this.createDate = createDate;
    }

}