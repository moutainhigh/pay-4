package  com.pay.fundout.autofundout.custom.model;

import com.pay.inf.model.BaseObject;

/**
*  jack.liu_liu
*  2010/12/10
*/


public class AutoFundoutConfig extends BaseObject  {

    private Long sequenceid;
    private Long retainedAmount;
    private String bankAccCode;
    private String remark;
    private Integer status;
    private Integer memberType;
    private String bankName;
    private java.util.Date updateDate;
    private String bankCode;
    private String createUser;
    private Integer busiType;
    private java.util.Date createDate;
    private Integer autoType;
    private Long memberCode;
    private String updateUser;
    private Integer accType;

    public Integer getAccType() {
		return accType;
	}

	public void setAccType(Integer accType) {
		this.accType = accType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMemberType() {
		return memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	public Integer getBusiType() {
		return busiType;
	}

	public void setBusiType(Integer busiType) {
		this.busiType = busiType;
	}

	public Integer getAutoType() {
		return autoType;
	}

	public void setAutoType(Integer autoType) {
		this.autoType = autoType;
	}

	public Long getSequenceid (){
        return sequenceid;
    }
    
    public void setSequenceid (Long sequenceid){
        this.sequenceid = sequenceid;
    }
    public Long getRetainedAmount (){
        return retainedAmount;
    }
    
    public void setRetainedAmount (Long retainedAmount){
        this.retainedAmount = retainedAmount;
    }
    public String getBankAccCode (){
        return bankAccCode;
    }
    
    public void setBankAccCode (String bankAccCode){
        this.bankAccCode = bankAccCode;
    }
    public String getRemark (){
        return remark;
    }
    
    public void setRemark (String remark){
        this.remark = remark;
    }
   
    public String getBankName (){
        return bankName;
    }
    
    public void setBankName (String bankName){
        this.bankName = bankName;
    }
    public java.util.Date getUpdateDate (){
        return updateDate;
    }
    
    public void setUpdateDate (java.util.Date updateDate){
        this.updateDate = updateDate;
    }
    public String getBankCode (){
        return bankCode;
    }
    
    public void setBankCode (String bankCode){
        this.bankCode = bankCode;
    }
    public String getCreateUser (){
        return createUser;
    }
    
    public void setCreateUser (String createUser){
        this.createUser = createUser;
    }
   
    public java.util.Date getCreateDate (){
        return createDate;
    }
    
    public void setCreateDate (java.util.Date createDate){
        this.createDate = createDate;
    }
   
    public Long getMemberCode (){
        return memberCode;
    }
    
    public void setMemberCode (Long memberCode){
        this.memberCode = memberCode;
    }
    public String getUpdateUser (){
        return updateUser;
    }
    
    public void setUpdateUser (String updateUser){
        this.updateUser = updateUser;
    }

}