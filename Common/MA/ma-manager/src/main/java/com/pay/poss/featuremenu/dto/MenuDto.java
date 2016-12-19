package com.pay.poss.featuremenu.dto;

import java.util.Date;

import com.pay.inf.dto.MutableDto;


public class MenuDto implements MutableDto {
    private Long menuId;
    private Long type;
    private Long hiddenType; 
   
	private Long hierarchy;
    private Long parentId ;
    private Integer orderId;
    private String name;
    private String url;
    private String description;
    private Integer status;
    private Date createDate;
    private Date updateDate;
    private Integer displayFlag;
    private Integer pageEndRow;// 结束行
	private Integer pageStartRow;// 起始行
	
	private String securityLevel;
    
	private String menuCode;
    
    public String getName() {
    	if(name!=null && !name.equals("")){
			String str = name.trim();
			return str;
		}else{
			return name;
		}
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
    	if(url!=null && !url.equals("")){
			String str = url.trim();
			return str;
		}else{
			return url;
		}
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
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getDisplayFlag() {
		return displayFlag;
	}
	public void setDisplayFlag(Integer displayFlag) {
		this.displayFlag = displayFlag;
	}
	public Integer getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(Integer pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public Integer getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(Integer pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public void setUrl(String url) {
		this.url = url;
	}
   
	
	
}
