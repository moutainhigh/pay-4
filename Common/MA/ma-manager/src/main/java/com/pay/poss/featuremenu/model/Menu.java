package com.pay.poss.featuremenu.model;

import java.util.Date;

import com.pay.inf.model.Model;

public class Menu implements Model{

    private Long menuId;
    private Long type;
    private Long hiddenType; 
    
	private Long hierarchy;
    private Long parentId;
    private Integer orderId;
    private String name;
    private String url;
    private String description;
    private Integer status;
    private Date createDate;
    private Date updateDate;
    private Integer displayFlag;
    
    private String securityLevel;
    
    private String menuCode;
    
    //树形菜单用的level
    private Integer level;

    public Long getMenuId() {
		return menuId;
	}


	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}


	public Long getType() {
		return type;
	}


	public void setType(Long type) {
		this.type = type;
	}


	public Long getHiddenType() {
		return hiddenType;
	}


	public void setHiddenType(Long hiddenType) {
		this.hiddenType = hiddenType;
	}


	public Long getHierarchy() {
		return hierarchy;
	}


	public void setHierarchy(Long hierarchy) {
		this.hierarchy = hierarchy;
	}


	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public Integer getOrderId() {
		return orderId;
	}


	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
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


	public String getMenuCode() {
		return menuCode;
	}


	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}


	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	public Integer getDisplayFlag() {
		return displayFlag;
	}


	public void setDisplayFlag(Integer displayFlag) {
		this.displayFlag = displayFlag;
	}

	/**
	 * @return the level
	 */
	public Integer getLevel() {
		return level;
	}


	/**
	 * @param level the level to set
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}


	/**
	 * @return the securityLevel
	 */
	public String getSecurityLevel() {
		return securityLevel;
	}


	/**
	 * @param securityLevel the securityLevel to set
	 */
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	
	
	
	
}
