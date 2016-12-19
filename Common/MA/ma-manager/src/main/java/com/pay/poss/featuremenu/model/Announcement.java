package com.pay.poss.featuremenu.model;

import java.util.Date;

import com.pay.inf.model.Model;

public class Announcement implements Model{

    private Long announcementId;
	private Date startTime;
    private Date endTime;
    private String author; 
    private String subject;
    private int displaysort;
    private String message;
    
    
    public Long getAnnouncementId() {
		return announcementId;
	}
	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
}
