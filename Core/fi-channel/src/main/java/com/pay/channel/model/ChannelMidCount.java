package com.pay.channel.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 二级商户号交易次数统计
 * 
 * @author Bobby Guo
 * @date 2015年10月12日
 */
public class ChannelMidCount implements Serializable {

	private static final long serialVersionUID = -803128215035564042L;
	
	/** 主键ID **/
	private Long id;
	/** 商户号 **/
	private Long memberCode;
	/** 渠道编码 **/
	private String channelCode;
	/** 二级商户号 **/
	private String mid;
	/** 交易次数 **/
	private Long txnCount;
	/** 统计日期 **/
	private String countDate;
	/** 创建时间 **/
	private Date createDate;
	/** 修改时间 **/
	private Date updateDate;

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

	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public Long getTxnCount() {
		return txnCount;
	}

	public void setTxnCount(Long txnCount) {
		this.txnCount = txnCount;
	}

	public String getCountDate() {
		return countDate;
	}

	public void setCountDate(String countDate) {
		this.countDate = countDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public ChannelMidCount() {
		super();
	}

	public ChannelMidCount(Long id, Long memberCode, String channelCode,
			String mid, Long txnCount, String countDate, Date createDate,
			Date updateDate) {
		super();
		this.id = id;
		this.memberCode = memberCode;
		this.channelCode = channelCode;
		this.mid = mid;
		this.txnCount = txnCount;
		this.countDate = countDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
	
	public ChannelMidCount(Long memberCode, String channelCode,
			String mid, Long txnCount) {
		super();
		this.memberCode = memberCode;
		this.channelCode = channelCode;
		this.mid = mid;
		this.txnCount = txnCount;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
