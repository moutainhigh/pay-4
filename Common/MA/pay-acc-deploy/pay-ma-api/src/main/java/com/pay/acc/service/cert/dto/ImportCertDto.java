/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.acc.service.cert.dto;

/**
 * @author fjl
 * @date 2011-11-28
 */
public class ImportCertDto {
	private String token;
	private String certFilePath;
	private String backupPwd;
	private String usePlace;
	private String dn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCertFilePath() {
		return certFilePath;
	}

	public void setCertFilePath(String certFilePath) {
		this.certFilePath = certFilePath;
	}

	public String getBackupPwd() {
		return backupPwd;
	}

	public void setBackupPwd(String backupPwd) {
		this.backupPwd = backupPwd;
	}

	public String getUsePlace() {
		return usePlace;
	}

	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	@Override
	public String toString() {
		return "token=" + token + ", certFilePath=" + certFilePath
				+ ", backupPwd=" + backupPwd + ", usePlace=" + usePlace
				+ ", dn=" + dn ;
	}

}
