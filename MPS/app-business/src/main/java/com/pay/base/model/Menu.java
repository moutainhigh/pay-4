package com.pay.base.model;

import java.util.Date;

public class Menu implements Model{

    private Long menuId;
    private Long type;
    private Long hierarchy;
    private Long parentId;
    private int orderId;
    private String name;
    private String url;
    private String description;
    private int status;
    private Date createDate;
    private Date updateDate;
    private int displayFlag;
    private String menuCode;
    private String securityLvl;
    
    
    public int getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}
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
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
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
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
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
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	public String getSecurityLvl() {
		return securityLvl;
	}
	public void setSecurityLvl(String securityLvl) {
		this.securityLvl = securityLvl;
	}
	@Override
    public void setPrimaryKey(Long id) {
        setMenuId(id);
    }
}
