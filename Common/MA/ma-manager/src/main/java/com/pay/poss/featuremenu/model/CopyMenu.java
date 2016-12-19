package com.pay.poss.featuremenu.model;


public class CopyMenu{

	private Long srcMenuId;
    private Long menuId;
    private Long type;
    private Long parentId;
	/**
	 * @return the srcMenuId
	 */
	public Long getSrcMenuId() {
		return srcMenuId;
	}
	/**
	 * @param srcMenuId the srcMenuId to set
	 */
	public void setSrcMenuId(Long srcMenuId) {
		this.srcMenuId = srcMenuId;
	}
	/**
	 * @return the menuId
	 */
	public Long getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	/**
	 * @return the type
	 */
	public Long getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Long type) {
		this.type = type;
	}
	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}
	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
    
    public String toString(){
    	return this.getMenuId()+"\t"+this.getParentId()+"\t"+this.getType()+"\t"+this.getSrcMenuId();
    }
    
	
}
