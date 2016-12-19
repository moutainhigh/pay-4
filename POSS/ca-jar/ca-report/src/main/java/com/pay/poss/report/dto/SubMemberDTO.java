package com.pay.poss.report.dto;
/**
* t_sub_member
*/

public class SubMemberDTO  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long parentId;
    private java.util.Date createTime;
    private java.util.Date updateTime;
    private Long sequenceId;
    private String memberName;
    private String operator;
    private String memberCode;

    public Long getParentId (){
        return parentId;
    }
    
    public void setParentId (Long parentId){
        this.parentId = parentId;
    }
    public java.util.Date getCreateTime (){
        return createTime;
    }
    
    public void setCreateTime (java.util.Date createTime){
        this.createTime = createTime;
    }
    public java.util.Date getUpdateTime (){
        return updateTime;
    }
    
    public void setUpdateTime (java.util.Date updateTime){
        this.updateTime = updateTime;
    }
    public Long getSequenceId (){
        return sequenceId;
    }
    
    public void setSequenceId (Long sequenceId){
        this.sequenceId = sequenceId;
    }
    public String getMemberName (){
        return memberName;
    }
    
    public void setMemberName (String memberName){
        this.memberName = memberName;
    }
    public String getOperator (){
        return operator;
    }
    
    public void setOperator (String operator){
        this.operator = operator;
    }
    public String getMemberCode (){
        return memberCode;
    }
    
    public void setMemberCode (String memberCode){
        this.memberCode = memberCode;
    }

}