/**
 *  File: BatchTimeConfig.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-16     darv      Changes
 *  
 */
package com.pay.fundout.withdraw.model.timeconfig;
/**
* BATCH_TIME_CONFIG
*/

public class BatchTimeConfig  implements java.io.Serializable {

    private String startTimePoint;
    private java.util.Date creationDate;
    private String specialPoint;
    private Integer timeSpan;
    private Integer status;
    private Long sequenceId;
    private String endTimePoint;
    private Integer timeType;
    private String weekDayList;
    private java.util.Date updateDate;

    public String getStartTimePoint (){
        return startTimePoint;
    }
    
    public void setStartTimePoint (String startTimePoint){
        this.startTimePoint = startTimePoint;
    }
    public java.util.Date getCreationDate (){
        return creationDate;
    }
    
    public void setCreationDate (java.util.Date creationDate){
        this.creationDate = creationDate;
    }
    public String getSpecialPoint (){
        return specialPoint;
    }
    
    public void setSpecialPoint (String specialPoint){
        this.specialPoint = specialPoint;
    }
    public Integer getTimeSpan (){
        return timeSpan;
    }
    
    public void setTimeSpan (Integer timeSpan){
        this.timeSpan = timeSpan;
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
    public String getEndTimePoint (){
        return endTimePoint;
    }
    
    public void setEndTimePoint (String endTimePoint){
        this.endTimePoint = endTimePoint;
    }
    public Integer getTimeType (){
        return timeType;
    }
    
    public void setTimeType (Integer timeType){
        this.timeType = timeType;
    }
    public String getWeekDayList (){
        return weekDayList;
    }
    
    public void setWeekDayList (String weekDayList){
        this.weekDayList = weekDayList;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }

}