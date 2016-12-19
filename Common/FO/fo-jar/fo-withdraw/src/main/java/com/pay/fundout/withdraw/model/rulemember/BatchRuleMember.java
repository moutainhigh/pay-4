/**
 *  File: BatchRuleMember.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.model.rulemember;
/**
* BATCH_RULE_MEMBER
*/

public class BatchRuleMember  implements java.io.Serializable {

    private java.util.Date creationDate;
    private Integer status;
    private Integer withdrawMemberId;
    private Integer sequenceId;
    private Integer batchRuleId;
    private java.util.Date updateDate;

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
    public Integer getWithdrawMemberId (){
        return withdrawMemberId;
    }
    
    public void setWithdrawMemberId (Integer withdrawMemberId){
        this.withdrawMemberId = withdrawMemberId;
    }
    public Integer getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Integer sequenceId){
        this.sequenceId = sequenceId;
    }
    public Integer getBatchRuleId (){
        return batchRuleId;
    }
    
    public void setBatchRuleId (Integer batchRuleId){
        this.batchRuleId = batchRuleId;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }

}