package com.pay.poss.appealmanager.dto;

public class BaseDataRelationDto {
	private String appealRelationId;
	private String fatherDataCode;
	private String sonDataCode;
	private String sonName;
	private String relationType;
	
	private String pageEndRow;
	private String pageStartRow;
	
	public String getAppealRelationId() {
		return appealRelationId;
	}
	public void setAppealRelationId(String appealRelationId) {
		this.appealRelationId = appealRelationId;
	}
	public String getFatherDataCode() {
		return fatherDataCode;
	}
	public void setFatherDataCode(String fatherDataCode) {
		this.fatherDataCode = fatherDataCode;
	}
	public String getSonDataCode() {
		return sonDataCode;
	}
	public void setSonDataCode(String sonDataCode) {
		this.sonDataCode = sonDataCode;
	}
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	public String getPageEndRow() {
		return pageEndRow;
	}
	public void setPageEndRow(String pageEndRow) {
		this.pageEndRow = pageEndRow;
	}
	public String getPageStartRow() {
		return pageStartRow;
	}
	public void setPageStartRow(String pageStartRow) {
		this.pageStartRow = pageStartRow;
	}
	
	
	

}
