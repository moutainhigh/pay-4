/**
 * 
 */
package com.pay.rm.exceptioncard.dto;

/**
 * 前七日异常卡笔数， 失败订单笔数统计
 * @author Jiangbo.peng
 *
 */
public class AweekCount {

	/**
	 * 前七日异常卡笔数
	 */
	private Long aweekExceptionCardCount ;
	
	/**
	 * 前七日失败订单笔数
	 */
	private Long aweekFailOrderCount ;
	
	//比例
	private String percent ;

	/**
	 * @return the aweekExceptionCardCount
	 */
	public Long getAweekExceptionCardCount() {
		return aweekExceptionCardCount;
	}

	/**
	 * @param aweekExceptionCardCount the aweekExceptionCardCount to set
	 */
	public void setAweekExceptionCardCount(Long aweekExceptionCardCount) {
		this.aweekExceptionCardCount = aweekExceptionCardCount;
	}

	/**
	 * @return the aweekFailOrderCount
	 */
	public Long getAweekFailOrderCount() {
		return aweekFailOrderCount;
	}

	/**
	 * @param aweekFailOrderCount the aweekFailOrderCount to set
	 */
	public void setAweekFailOrderCount(Long aweekFailOrderCount) {
		this.aweekFailOrderCount = aweekFailOrderCount;
	}
	

	/**
	 * @return the percent
	 */
	public String getPercent() {
		return percent;
	}

	/**
	 * @param percent the percent to set
	 */
	public void setPercent(String percent) {
		this.percent = percent;
	}

	@Override
	public String toString() {
		return "AweekCount [aweekExceptionCardCount=" + aweekExceptionCardCount
				+ ", aweekFailOrderCount=" + aweekFailOrderCount + "]";
	}
	
	
	
}
