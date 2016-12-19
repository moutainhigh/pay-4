package com.pay.txncore.dto;

/**
 * @author Fred
 * @date 20110430
 */
public class ResponseNotify {
	
	private String noticeContent;
	
	private String noticeUrl;
	
	private String responseNo;

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeUrl() {
		return noticeUrl;
	}

	public void setNoticeUrl(String noticeUrl) {
		this.noticeUrl = noticeUrl;
	}

	public String getResponseNo() {
		return responseNo;
	}

	public void setResponseNo(String responseNo) {
		this.responseNo = responseNo;
	}

	@Override
	public String toString() {
		return "ResponseNotify [noticeContent=" + noticeContent
				+ ", noticeUrl=" + noticeUrl + ", responseNo=" + responseNo
				+ "]";
	}

}
