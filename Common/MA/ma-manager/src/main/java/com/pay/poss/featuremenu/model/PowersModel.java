package com.pay.poss.featuremenu.model;

import java.util.List;

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
	private List<PowersModel> childlist;
	private boolean checked;
	private int status;
	private Integer displayFlag;
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

	/**
	 * @return the displayFlog
	 */
	public Integer getDisplayFlag() {
		return displayFlag;
	}

	/**
	 * @param displayFlog the displayFlog to set
	 */
	public void setDisplayFlag(Integer displayFlag) {
		this.displayFlag = displayFlag;
	}
	
}
