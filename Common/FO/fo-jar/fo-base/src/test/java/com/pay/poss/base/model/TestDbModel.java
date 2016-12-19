package com.pay.poss.base.model;


public class TestDbModel {

	private static final long serialVersionUID = 1L;

	private Long roleKy;
	private String roleCode;
	private String roleName;
	private String roleRemarks;
	private int status;

	public Long getRoleKy() {
		return roleKy;
	}

	public void setRoleKy(Long roleKy) {
		this.roleKy = roleKy;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRoleRemarks() {
		return roleRemarks;
	}

	public void setRoleRemarks(String roleRemarks) {
		this.roleRemarks = roleRemarks;
	}

}