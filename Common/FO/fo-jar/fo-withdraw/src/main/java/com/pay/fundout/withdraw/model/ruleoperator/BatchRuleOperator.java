/**
 *  File: BatchRuleOperator.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.model.ruleoperator;
/**
* BATCH_RULE_OPERATOR
*/

public class BatchRuleOperator  implements java.io.Serializable {

    private String identity;
    private java.util.Date creationDate;
    private Integer status;
    private Long sequenceId;
    private Long batchRuleId;
    private java.util.Date updateDate;

    public String getIdentity (){
        return identity;
    }
    
    public void setIdentity (String identity){
        this.identity = identity;
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
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }

}