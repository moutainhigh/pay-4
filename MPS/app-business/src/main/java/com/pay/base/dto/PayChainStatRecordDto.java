package com.pay.base.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import com.pay.util.DateUtil;
import com.pay.base.common.enums.EffectiveTypeEnum;

/**
 * 用于显示支付链统计列表的dto
 * @author DDR
 * Date 2011-09-21
 */
public class PayChainStatRecordDto {
	
	private Date createDate;//创建时间
	private Date overdueDate; //结束时间
    private String chainNumber; //支付链号
    private String description;   //支付链描述
    private String status;		//支付链状态
    private String countAndSum; //支付链统计结果 格式$count-$sum，如果为空必须为0,
    private String count;   //$count
    private String sum;		//$sum 格式是0.00
    private Integer effectiveDate; //有效期类型
    private String payChainName; //支付链名称
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the overdueDate
	 */
	public Date getOverdueDate() {
		return overdueDate;
	}
	/**
	 * @param overdueDate the overdueDate to set
	 */
	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}
	/**
	 * @return the chainNumber
	 */
	public String getChainNumber() {
		return chainNumber;
	}
	/**
	 * @param chainNumber the chainNumber to set
	 */
	public void setChainNumber(String chainNumber) {
		this.chainNumber = chainNumber;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @return the status
	 */
	public String getStatusMsg() {
		return status.equals("1") ? "生效" : "已关闭";
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the countAndSum
	 */
	public String getCountAndSum() {
		return countAndSum;
	}
	/**
	 * @param countAndSum the countAndSum to set
	 */
	public void setCountAndSum(String countAndSum) {
		this.countAndSum = countAndSum;
		if(countAndSum!=null && countAndSum.indexOf("-")>0 ){
			String[] cs = countAndSum.split("-");
			count = cs[0];
			sum = new DecimalFormat("0.00").format(new BigDecimal(cs[1]));;
		}
	}
	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}
	/**
	 * @return the sum
	 */
	public String getSum() {
		return sum;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(String sum) {
		this.sum = sum;
	}
    
	public String getDescriptionTitle(){
		if(payChainName!=null && payChainName.length()>5){
			return payChainName.substring(0,5)+"...";
		}
		return payChainName;
	}
	/**
	 * @return the effectiveDate
	 */
	public Integer getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Integer effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getEffectiveMsg(){
		EffectiveTypeEnum em =  EffectiveTypeEnum.getEffectiveEnum(effectiveDate);
		if(em!=null){
			return em.getMemo();
		}
		
		return null;
	}
	
	public int getEffDays(){
		Long day =  (new Date().getTime() - createDate.getTime())/(24*3600*1000);
		return day.intValue();
	}
	
	public String getPayChainName() {
		return payChainName;
	}
	
	public void setPayChainName(String payChainName) {
		this.payChainName = payChainName;
	}
	
}
