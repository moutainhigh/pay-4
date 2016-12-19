package com.pay.base.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-23 下午04:14:37 menu&URL model
 */
public class PowersModel implements java.io.Serializable {
	private String id;
	private String menuName;
	private String menuUrl;
	private String parentId;
	/** 默认菜单权限ID*/
    private String defaultPermId;
	private List<PowersModel> childlist;
	private boolean checked;
	private int status=0;
    private int displayFlag=0;
    private Long menuType;
	private String menuCode;
    private String securityLvl;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private String desc;
	

	public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<PowersModel> getChildlist() {
		return childlist;
	}

	public void setChildlist(List<PowersModel> childlist) {
		this.childlist = childlist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

    public String getDefaultPermId() {
        return defaultPermId;
    }

    public void setDefaultPermId(String defaultPermId) {
        this.defaultPermId = defaultPermId;
    }
    
    
    public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	
	

	public int getDisplayFlag() {
		return displayFlag;
	}

	public void setDisplayFlag(int displayFlag) {
		this.displayFlag = displayFlag;
	}
	
	

	public String getSecurityLvl() {
		return securityLvl;
	}

	public void setSecurityLvl(String securityLvl) {
		this.securityLvl = securityLvl;
	}
	public Long getMenuType() {
		return menuType;
	}

	public void setMenuType(Long menuType) {
		this.menuType = menuType;
	}
	@Override
    public boolean equals(Object object){
    	if(object != null && object instanceof PowersModel){
    		PowersModel powersModel = (PowersModel)object;
    		if(StringUtils.equals(this.getId(), powersModel.getId())){
    			return true;
    		}
    	}
    	return false;
	}

	@Override
	public String toString() {
		return "PowersModel [id=" + id + ", menuName=" + menuName
				+ ", menuUrl=" + menuUrl + ", parentId=" + parentId
				+ ", defaultPermId=" + defaultPermId + ", childlist="
				+ childlist + ", checked=" + checked + ", status=" + status
				+ ", displayFlag=" + displayFlag + ", menuType=" + menuType
				+ ", menuCode=" + menuCode + ", securityLvl=" + securityLvl
				+ ", desc=" + desc + "]";
	}
	
	
	
}
