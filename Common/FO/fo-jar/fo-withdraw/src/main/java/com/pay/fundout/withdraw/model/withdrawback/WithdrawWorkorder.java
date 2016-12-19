/**
 * 
 * @author jack.liu_liu
 *2010.9.17
 */
package com.pay.fundout.withdraw.model.withdrawback;
/**
* withdraw_workorder
*/

public class WithdrawWorkorder  implements java.io.Serializable {

    private Long orderSeq;
    private Long workorderKy;
    private Integer batchStatus;
    private Long status;
    private String batchNum;
    private String workflowKy;

    public Long getOrderSeq (){
        return orderSeq;
    }
    
    public void setOrderSeq (Long orderSeq){
        this.orderSeq = orderSeq;
    }
    public Long getWorkorderKy (){
        return workorderKy;
    }
    
    public void setWorkorderKy (Long workorderKy){
        this.workorderKy = workorderKy;
    }
    public Integer getBatchStatus (){
        return batchStatus;
    }
    
    public void setBatchStatus (Integer batchStatus){
        this.batchStatus = batchStatus;
    }
    public Long getStatus (){
        return status;
    }
    
    public void setStatus (Long status){
        this.status = status;
    }
    public String getBatchNum (){
        return batchNum;
    }
    
    public void setBatchNum (String batchNum){
        this.batchNum = batchNum;
    }
    public String getWorkflowKy (){
        return workflowKy;
    }
    
    public void setWorkflowKy (String workflowKy){
        this.workflowKy = workflowKy;
    }

}