package com.pay.poss.featuremenu.dto;

import com.pay.inf.dto.MutableDto;


public class AnnouncementDto implements MutableDto {
    private Long announcementId;
    private String startTime;
   
	private String endTime;
    private String author; 
    private String subject;
    private int displaysort;
    private String message;
    private Integer pageEndRow;// 结束行
	private Integer pageStartRow;// 起始行

	public Long getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public int getDisplaysort() {
		return displaysort;
	}
	public void setDisplaysort(int displaysort) {
		this.displaysort = displaysort;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	 public String getStartTime() {
			return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
}
