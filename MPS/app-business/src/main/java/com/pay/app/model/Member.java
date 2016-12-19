package com.pay.app.model;

import java.util.Date;

public class Member {
	
	private int MEMBER_CODE;
	 
	 private int SERVICE_LEVEL_CODE;
	 
	 private int MEMBER_TYPE;
	 
	 private String NAME;
	 
	 private String GREETING;
	 
	 private int STATUS;
	 
	 private String SECURITY_QUESTION;
	 
	 private String SECURITY_ANSWER;
	 
	 private Date CREATION_DATE;
	 
	 private Date UPDATE_DATE;
	 
	 private String PARNTER_USERID;

	public int getMEMBER_CODE() {
		return MEMBER_CODE;
	}

	public void setMEMBER_CODE(int mEMBERCODE) {
		MEMBER_CODE = mEMBERCODE;
	}

	public int getSERVICE_LEVEL_CODE() {
		return SERVICE_LEVEL_CODE;
	}

	public void setSERVICE_LEVEL_CODE(int sERVICELEVELCODE) {
		SERVICE_LEVEL_CODE = sERVICELEVELCODE;
	}

	public int getMEMBER_TYPE() {
		return MEMBER_TYPE;
	}

	public void setMEMBER_TYPE(int mEMBERTYPE) {
		MEMBER_TYPE = mEMBERTYPE;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getGREETING() {
		return GREETING;
	}

	public void setGREETING(String gREETING) {
		GREETING = gREETING;
	}

	public int getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}

	public String getSECURITY_QUESTION() {
		return SECURITY_QUESTION;
	}

	public void setSECURITY_QUESTION(String sECURITYQUESTION) {
		SECURITY_QUESTION = sECURITYQUESTION;
	}

	public String getSECURITY_ANSWER() {
		return SECURITY_ANSWER;
	}

	public void setSECURITY_ANSWER(String sECURITYANSWER) {
		SECURITY_ANSWER = sECURITYANSWER;
	}

	public Date getCREATION_DATE() {
		return CREATION_DATE;
	}

	public void setCREATION_DATE(Date cREATIONDATE) {
		CREATION_DATE = cREATIONDATE;
	}

	public Date getUPDATE_DATE() {
		return UPDATE_DATE;
	}

	public void setUPDATE_DATE(Date uPDATEDATE) {
		UPDATE_DATE = uPDATEDATE;
	}

	public String getPARNTER_USERID() {
		return PARNTER_USERID;
	}

	public void setPARNTER_USERID(String pARNTERUSERID) {
		PARNTER_USERID = pARNTERUSERID;
	}

}
