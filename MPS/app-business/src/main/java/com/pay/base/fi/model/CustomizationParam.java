/**
 * 
 */
package com.pay.base.fi.model;

import java.sql.Timestamp;

/**
 * 定制化参数
 * @author PengJiangbo
 *
 */
public class CustomizationParam {

	/**
	 * 定制化参数id
	 */
	private Long id ;
	/**
	 * 商户会员号
	 */
	private Long memberCode ;
	/**
	 * 创建时间
	 */
	private Timestamp createTime ;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime ;
	/**
	 * 订单有效时间
	 */
	private Long effectiveTime ;
	/**
	 * 有效时间单位
	 */
	private String effectiveUnit ;
	/**
	 * 修改人
	 */
	private Long updator ;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Long getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(Long effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	
	public String getEffectiveUnit() {
		return effectiveUnit;
	}
	public void setEffectiveUnit(String effectiveUnit) {
		this.effectiveUnit = effectiveUnit;
	}
	
	public Long getUpdator() {
		return updator;
	}
	public void setUpdator(Long updator) {
		this.updator = updator;
	}
	@Override
	public String toString() {
		return "CustomizationParam [id=" + id + ", memberCode=" + memberCode
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", effectiveTime=" + effectiveTime + ", effectiveUnit="
				+ effectiveUnit + ", updator=" + updator + "]";
	}
}
