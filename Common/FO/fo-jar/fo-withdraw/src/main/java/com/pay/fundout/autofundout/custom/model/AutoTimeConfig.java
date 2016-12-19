
package  com.pay.fundout.autofundout.custom.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
*  jack.liu_liu
*  2010/12/10
*/
public class AutoTimeConfig extends BaseObject   {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long sequenceid;
    private Date createDate;
    private Integer timeType;
    private String timeSource;
    private Long configId;
    private Integer settleFlag;

    public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Long getSequenceid (){
        return sequenceid;
    }
    
    public void setSequenceid (Long sequenceid){
        this.sequenceid = sequenceid;
    }
    public java.util.Date getCreateDate (){
        return createDate;
    }
    
    public void setCreateDate (java.util.Date createDate){
        this.createDate = createDate;
    }

    public String getTimeSource (){
        return timeSource;
    }
    
    public void setTimeSource (String timeSource){
        this.timeSource = timeSource;
    }
    public Long getConfigId (){
        return configId;
    }
    
    public void setConfigId (Long configId){
        this.configId = configId;
    }

	public Integer getSettleFlag() {
		return settleFlag;
	}

	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}

}