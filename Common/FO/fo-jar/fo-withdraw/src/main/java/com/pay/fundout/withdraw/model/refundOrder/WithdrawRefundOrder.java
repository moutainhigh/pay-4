/**
*jack.liu_liu
*2010.9.18
**/
package com.pay.fundout.withdraw.model.refundOrder;
/**
* withdraw_refund_order
*/

public class WithdrawRefundOrder  implements java.io.Serializable {

    private String payerAcctCode;
    private java.util.Date creationDate;
    private Integer status;
    private String payeeAcctCode;
    private Long withdrawOrderId;
    private Long sequenceId;
    private String comments;
    private java.util.Date updateDate;

    public String getPayerAcctCode (){
        return payerAcctCode;
    }
    
    public void setPayerAcctCode (String payerAcctCode){
        this.payerAcctCode = payerAcctCode;
    }
    public java.util.Date getCreationDate (){
        return creationDate;
    }
    
    public void setCreationDate (java.util.Date creationDate){
        this.creationDate = creationDate;
    }
    public Integer getStatus (){
        return status;
    }
    
    public void setStatus (Integer status){
        this.status = status;
    }
    public String getPayeeAcctCode (){
        return payeeAcctCode;
    }
    
    public void setPayeeAcctCode (String payeeAcctCode){
        this.payeeAcctCode = payeeAcctCode;
    }
    public Long getWithdrawOrderId (){
        return withdrawOrderId;
    }
    
    public void setWithdrawOrderId (Long withdrawOrderId){
        this.withdrawOrderId = withdrawOrderId;
    }
    public Long getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Long sequenceId){
        this.sequenceId = sequenceId;
    }
    public String getComments (){
        return comments;
    }
    
    public void setComments (String comments){
        this.comments = comments;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }

}