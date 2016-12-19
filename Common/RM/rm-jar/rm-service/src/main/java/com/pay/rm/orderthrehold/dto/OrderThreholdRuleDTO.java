package com.pay.rm.orderthrehold.dto;

import java.util.Date;

/**
 * 
 * @author Timothy
 *
 */
public class OrderThreholdRuleDTO {
	/**
	 * 阈值类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 时间间隔
	 */
	private int duration;
	/**
	 * 次数阈值
	 */
	private int threhold;
	/**
	 * 更新日期
	 */
	private Date updateDate;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getThrehold() {
		return threhold;
	}
	public void setThrehold(int threhold) {
		this.threhold = threhold;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Override
	public String toString() {
		return "OrderThreholdRuleDTO [type=" + type + ", description=" + description
				+ ", duration=" + duration + ", threhold=" + threhold
				+ ", updateDate=" + updateDate + "]";
	}
}
