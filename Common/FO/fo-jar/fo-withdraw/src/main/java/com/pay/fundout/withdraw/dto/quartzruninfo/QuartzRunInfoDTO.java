package com.pay.fundout.withdraw.dto.quartzruninfo;

/**
 * 
 * @author Sean_yi
 *
 */
public class QuartzRunInfoDTO  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 业务类型
	 */
    private Integer busiType;
    /**
     * 外键
     */
    private Long fkId;
    /**
     * 主标识
     */
    private Long sequenceId;
    /**
     * 创建时间
     */
    private java.util.Date createDate;
    /**
     * 更新时间
     */
    private java.util.Date updateDate;
    /**
     * 上次运行时间
     */
    private java.util.Date lastRunTime;

    public Integer getStatus (){
        return status;
    }
    
    public void setStatus (Integer status){
        this.status = status;
    }
    public Integer getBusiType (){
        return busiType;
    }
    
    public void setBusiType (Integer busiType){
        this.busiType = busiType;
    }
    public Long getFkId (){
        return fkId;
    }
    
    public void setFkId (Long fkId){
        this.fkId = fkId;
    }
    public Long getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Long sequenceId){
        this.sequenceId = sequenceId;
    }
    public java.util.Date getCreateDate (){
        return createDate;
    }
    
    public void setCreateDate (java.util.Date createDate){
        this.createDate = createDate;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }
    public java.util.Date getLastRunTime (){
        return lastRunTime;
    }
    
    public void setLastRunTime (java.util.Date lastRunTime){
        this.lastRunTime = lastRunTime;
    }

}