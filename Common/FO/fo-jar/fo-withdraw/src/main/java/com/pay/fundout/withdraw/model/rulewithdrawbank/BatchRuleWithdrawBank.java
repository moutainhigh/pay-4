/**
 *  File: BatchRuleWithdrqwBank.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.model.rulewithdrawbank;
/**
* BATCH_RULE_WITHDRAW_BANK
*/

public class BatchRuleWithdrawBank  implements java.io.Serializable {

    private java.util.Date creationDate;
    private Long maxCounts;
    private Integer status;
    private Long sequenceId;
    private Long batchRuleId;
    private Long withdrawBankId;
    private java.util.Date updateDate;

    public java.util.Date getCreationDate (){
        return creationDate;
    }
    
    public void setCreationDate (java.util.Date creationDate){
        this.creationDate = creationDate;
    }
    public Long getMaxCounts (){
        return maxCounts;
    }
    
    public void setMaxCounts (Long maxCounts){
        this.maxCounts = maxCounts;
    }
    public Integer getStatus (){
        return status;
    }
    
    public void setStatus (Integer status){
        this.status = status;
    }
    public Long getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Long sequenceId){
        this.sequenceId = sequenceId;
    }
    public Long getBatchRuleId (){
        return batchRuleId;
    }
    
    public void setBatchRuleId (Long batchRuleId){
        this.batchRuleId = batchRuleId;
    }
    public Long getWithdrawBankId (){
        return withdrawBankId;
    }
    
    public void setWithdrawBankId (Long withdrawBankId){
        this.withdrawBankId = withdrawBankId;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }

}