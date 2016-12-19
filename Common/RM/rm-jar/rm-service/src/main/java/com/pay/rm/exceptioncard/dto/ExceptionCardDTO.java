/**
 * 
 */
package com.pay.rm.exceptioncard.dto;

import java.sql.Timestamp;

/**
 * @author Jiangbo.peng
 *ID
MEMBER_CODE
TIME
TIME_ZONE
EXCEPTION_CARD_COUNT
FAIL_ORDER_COUNT
EXCEPTION_CARD_PERCENT
AWEEK_AGO_TIME_ZONE_PERCENT
STATUS
UPDATE_TIME
EXTEND2
 */
public class ExceptionCardDTO {

	/** 主键ID */
	private Long id ;
	
	/** 商户会员号 */
	private Long memberCode ;
	
	/** 创建时间 */
	private Timestamp time ;
	
	/** 时间区间 */
	private String timeZone ;
	
	/** 异常卡笔数 */
	private Long exceptionCardCount ;
	
	/** 失败订单笔数 */
	private Long failOrderCount ;
	
	/** 异常卡比例 */
	private String exceptionCardPercent ;
	
	/** 前七日分时比例 */
	private String aweekAgoTimeZonePercent ;
	
	/** 状态 */
	private String status ;
	
	/** 最近一次修改时间 */
	private Timestamp updateTime ;
	
	/** 是否为异常卡 */
	private boolean exceptionCardFlag ;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the memberCode
	 */
	public Long getMemberCode() {
		return memberCode;
	}

	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	/**
	 * @return the exceptionCardCount
	 */
	public Long getExceptionCardCount() {
		return exceptionCardCount;
	}

	/**
	 * @param exceptionCardCount the exceptionCardCount to set
	 */
	public void setExceptionCardCount(Long exceptionCardCount) {
		this.exceptionCardCount = exceptionCardCount;
	}

	/**
	 * @return the failOrderCount
	 */
	public Long getFailOrderCount() {
		return failOrderCount;
	}

	/**
	 * @param failOrderCount the failOrderCount to set
	 */
	public void setFailOrderCount(Long failOrderCount) {
		this.failOrderCount = failOrderCount;
	}

	/**
	 * @return the exceptionCardPercent
	 */
	public String getExceptionCardPercent() {
		return exceptionCardPercent;
	}

	/**
	 * @param exceptionCardPercent the exceptionCardPercent to set
	 */
	public void setExceptionCardPercent(String exceptionCardPercent) {
		this.exceptionCardPercent = exceptionCardPercent;
	}

	/**
	 * @return the aweekAgoTimeZonePercent
	 */
	public String getAweekAgoTimeZonePercent() {
		return aweekAgoTimeZonePercent;
	}

	/**
	 * @param aweekAgoTimeZonePercent the aweekAgoTimeZonePercent to set
	 */
	public void setAweekAgoTimeZonePercent(String aweekAgoTimeZonePercent) {
		this.aweekAgoTimeZonePercent = aweekAgoTimeZonePercent;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the updateTime
	 */
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	/**
	 * @return the exceptionCardFlag
	 */
	public boolean isExceptionCardFlag() {
		return exceptionCardFlag;
	}

	/**
	 * @param exceptionCardFlag the exceptionCardFlag to set
	 */
	public void setExceptionCardFlag(boolean exceptionCardFlag) {
		this.exceptionCardFlag = exceptionCardFlag;
	}

	@Override
	public String toString() {
		return "ExceptionCardDTO [id=" + id + ", memberCode=" + memberCode
				+ ", time=" + time + ", timeZone=" + timeZone
				+ ", exceptionCardCount=" + exceptionCardCount
				+ ", failOrderCount=" + failOrderCount
				+ ", exceptionCardPercent=" + exceptionCardPercent
				+ ", aweekAgoTimeZonePercent=" + aweekAgoTimeZonePercent
				+ ", status=" + status + ", updateTime=" + updateTime
				+ ", exceptionCardFlag=" + exceptionCardFlag + "]";
	}

	
}
