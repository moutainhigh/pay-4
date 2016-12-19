
package  com.pay.fundout.autofundout.custom.model;

import java.util.Date;

import com.pay.inf.model.BaseObject;

/**
*  jack.liu_liu
*  2010/12/10
*/
public class AutoQuotaConfig  extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long sequenceid;
    private Long baseAmount;
    private java.util.Date createDate;
    private Long configId;

    public Long getSequenceid (){
        return sequenceid;
    }
    
    public void setSequenceid (Long sequenceid){
        this.sequenceid = sequenceid;
    }
    public Long getBaseAmount (){
        return baseAmount;
    }
    
    public void setBaseAmount (Long baseAmount){
        this.baseAmount = baseAmount;
    }
    public java.util.Date getCreateDate (){
        return createDate;
    }
    
    public void setCreateDate (Date createDate){
        this.createDate = createDate;

    }
    public Long getConfigId (){
        return configId;
    }
    
    public void setConfigId (Long configId){
        this.configId = configId;
    }

}