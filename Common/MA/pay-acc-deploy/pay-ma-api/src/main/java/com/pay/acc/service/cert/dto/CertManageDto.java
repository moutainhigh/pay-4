package com.pay.acc.service.cert.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 证书使用地点管理
 * 
 * @author fjl
 * @date 2011-11-15
 */
public class CertManageDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id; // ID主键
	private Long memberCode; // 会员号
	private Long operatorId; // 操作员ID
	private String userDn; // 用户DN
	private String machineId; // 用户机器唯一标识
	private String usePlace; // 使用地点
	private Integer status; // 0 为删除 1为有效
	private Date createDate; // 创建时间
	private Date updateDate; // 更新时间
	
	public static enum StatusEnum{
		DELETED(0,"删除"),
		VALID(1,"有效");
		
		private int value;
		private String des;
		private StatusEnum(int value,String des){
			this.value = value;
			this.des = des;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getDes() {
			return des;
		}
		
		public static StatusEnum get(int value){
			for(StatusEnum item : values()){
				if(value == item.getValue()){
					return item;
				}
			}
			return null;
		}
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param memberCode
	 *            the memberCode to set
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	/**
	 * @return the operatorId
	 */
	public Long getOperatorId() {
		return operatorId;
	}

	/**
	 * @param operatorId
	 *            the operatorId to set
	 */
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * @return the userDd
	 */
	public String getUserDn() {
		return userDn;
	}

	/**
	 * @param userDd
	 *            the userDd to set
	 */
	public void setUserDn(String userDn) {
		this.userDn = userDn;
	}

	/**
	 * @return the machineId
	 */
	public String getMachineId() {
		return machineId;
	}

	/**
	 * @param machineId
	 *            the machineId to set
	 */
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}

	/**
	 * @return the usePlace
	 */
	public String getUsePlace() {
		return usePlace;
	}

	/**
	 * @param usePlace
	 *            the usePlace to set
	 */
	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getCN(){
		if(userDn != null){
			String[] tmp = userDn.split("[,]");
			if(tmp.length >0){
				return tmp[0];
			}
		}
		return userDn;
	}

	@Override
	public String toString() {
		return "CertManage [id=" + id + ", memberCode=" + memberCode
				+ ", operatorId=" + operatorId + ", userDn=" + userDn
				+ ", machineId=" + machineId + ", usePlace=" + usePlace
				+ ", status=" + status + ", createDate=" + createDate
				+ ", updateDate=" + updateDate + "]";
	}
	
	

}
