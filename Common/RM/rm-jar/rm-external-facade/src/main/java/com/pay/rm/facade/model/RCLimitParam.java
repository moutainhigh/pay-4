/**
 *  <p>File: RCLimitParamDTO.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.model;

import com.pay.inf.model.BaseObject;


/**
 * <p>风控Facade入参DTO</p>
 * @author zengli
 * @since 2011-5-11
 * @see 
 */
public class RCLimitParam extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 对公对私类型
	 */
	private int memberType;
	/**
	 * @return the memberType
	 */
	public int getMemberType() {
		return memberType;
	}
	/**
	 * @param memberType the memberType to set
	 */
	public void setMemberType(int memberType) {
		this.memberType = memberType;
	}
	/**
	 * 会员号
	 */
	private long memberCode;
	/**
	 * 风控等级
	 */
	private int level;
	/**
	 * 行业编码
	 */
	private Long mccCode;
	/**
	 * 业务类型
	 */
	private int busiType;
	/**
	 * @return the busiType
	 */
	public int getBusiType() {
		return busiType;
	}
	/**
	 * @param busiType the busiType to set
	 */
	public void setBusiType(int busiType) {
		this.busiType = busiType;
	}
	/**
	 * @return the memberCode
	 */
	public long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode the memberCode to set
	 */
	public void setMemberCode(long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/** 
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	} 
	/** 
	 * @return the mccCode
	 */
	public Long getMccCode() {
		return mccCode;
	}
	/**
	 * @param mccCode the mccCode to set
	 */
	public void setMccCode(Long mccCode) {
		this.mccCode = mccCode;
	}
}
