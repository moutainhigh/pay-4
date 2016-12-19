/**
 * 
 */
package com.pay.wechat.model;

import java.util.Date;

/**
 * 系统商户与公众号绑定
 * @author PengJiangbo
 *	
 */
public class SysUserMapper {

	/**
	 * 主键
	 */
	private Long id;
	
	/**
	 * 登录名
	 */
	private String login_name;
	
	/**
	 * 微信用户唯一标识
	 */
	private String openID;
	
	/**
	 * 绑定时间
	 */
	private Date bindTime;
	
	/**
	 * 登录密码
	 */
	private String login_dpwd;
	
	/**
	 * 绑定状态
	 */
	private Long statu;
	
	/**
	 * 商户号
	 */
	private Long member_code;
	
	/**
	 * 会员操作类型
	 */
	private int scale_type ;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public Date getBindTime() {
		return bindTime;
	}

	public void setBindTime(Date bindTime) {
		this.bindTime = bindTime;
	}

	public String getLogin_dpwd() {
		return login_dpwd;
	}

	public void setLogin_dpwd(String login_dpwd) {
		this.login_dpwd = login_dpwd;
	}

	public Long getStatu() {
		return statu;
	}

	public void setStatu(Long statu) {
		this.statu = statu;
	}

	public Long getMember_code() {
		return member_code;
	}

	public void setMember_code(Long member_code) {
		this.member_code = member_code;
	}

	public int getScale_type() {
		return scale_type;
	}

	public void setScale_type(int scale_type) {
		this.scale_type = scale_type;
	}

	@Override
	public String toString() {
		return "SysUserMapper [id=" + id + ", login_name=" + login_name
				+ ", openID=" + openID + ", bindTime=" + bindTime
				+ ", login_dpwd=" + login_dpwd + ", statu=" + statu
				+ ", member_code=" + member_code + ", scale_type=" + scale_type
				+ "]";
	}

}
