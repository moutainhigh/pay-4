/**
 * 
 */
package com.pay.wechat.model;

/**
 * 安全问题绑定
 * @author PengJiangbo
 *
 */
public class QuestionBind {
	//主键
	private Long id ;
	//问题1
	private String question1 ;
	//问题2
	private String question2 ;
	//问题3
	private String question3 ;
	//答案1
	private String qa1 ;
	//答案2
	private String qa2 ;
	//答案3
	private String qa3 ;
	//商户号
	private Long memberCode ;
	//微信用户唯一标识
	private String openID;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion1() {
		return question1;
	}
	public void setQuestion1(String question1) {
		this.question1 = question1;
	}
	public String getQuestion2() {
		return question2;
	}
	public void setQuestion2(String question2) {
		this.question2 = question2;
	}
	public String getQuestion3() {
		return question3;
	}
	public void setQuestion3(String question3) {
		this.question3 = question3;
	}
	public String getQa1() {
		return qa1;
	}
	public void setQa1(String qa1) {
		this.qa1 = qa1;
	}
	public String getQa2() {
		return qa2;
	}
	public void setQa2(String qa2) {
		this.qa2 = qa2;
	}
	public String getQa3() {
		return qa3;
	}
	public void setQa3(String qa3) {
		this.qa3 = qa3;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	@Override
	public String toString() {
		return "QuestionBind [id=" + id + ", question1=" + question1
				+ ", question2=" + question2 + ", question3=" + question3
				+ ", qa1=" + qa1 + ", qa2=" + qa2 + ", qa3=" + qa3
				+ ", memberCode=" + memberCode + ", openID=" + openID + "]";
	}
}
