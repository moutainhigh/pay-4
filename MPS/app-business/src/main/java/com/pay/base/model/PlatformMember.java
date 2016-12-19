package com.pay.base.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.httpclient.util.DateUtil;

/**
 * 
 * @author yangjian
 *
 *         下午7:13:55
 */
public class PlatformMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6779271011009636980L;
	// 映射关键字段
	private Long id;
	// 平台会员号
	private Long father_member_code;
	// 平台会员操作号
	private Long father_operator_id;
	// 普通会员号
	private Long son_member_code;
	// 普通会员号操作号
	private Long son_operator_id;
	// 会员号的状态 1、待审核 2、已审核通过 3、拒绝 4、解除绑定
	private Integer status;
	// 创建时间
	private Date create_date;
	// 更新时间
	private Date update_date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFather_member_code() {
		return father_member_code;
	}
	public void setFather_member_code(Long father_member_code) {
		this.father_member_code = father_member_code;
	}
	public Long getFather_operator_id() {
		return father_operator_id;
	}
	public void setFather_operator_id(Long father_operator_id) {
		this.father_operator_id = father_operator_id;
	}
	public Long getSon_member_code() {
		return son_member_code;
	}
	public void setSon_member_code(Long son_member_code) {
		this.son_member_code = son_member_code;
	}
	public Long getSon_operator_id() {
		return son_operator_id;
	}
	public void setSon_operator_id(Long son_operator_id) {
		this.son_operator_id = son_operator_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	public String toString() {
		return "PlatformMember[id=" + id 
				+ ", father_member_code=" + this.father_member_code
				+ ", father_operator_id=" + this.father_operator_id
				+ ", son_member_code=" + this.son_member_code
				+ ", son_operator_id=" + this.son_operator_id
				+ ", status=" + this.status
				+ "]";
	}
}
