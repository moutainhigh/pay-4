 /** @Description 
 * @project 	poss-reconcile
 * @file 		WebReconcileUploadDTO.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-15		sunsea.li		Create 
*/
package com.pay.poss.refund.model;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.inf.model.BaseObject;

/**
 * <p>用于文件上传的model大对象</p>
 * @author sunsea.li
 * @since 2010-9-15
 * @see 
 */
public class WebRefundUploadDTO extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RefundImportFile refundImportFile = null;
	
	private CommonsMultipartFile orginalFile = null;

	public CommonsMultipartFile getOrginalFile() {
		return orginalFile;
	}

	public void setOrginalFile(CommonsMultipartFile orginalFile) {
		this.orginalFile = orginalFile;
	}

	public RefundImportFile getRefundImportFile() {
		return refundImportFile;
	}

	public void setRefundImportFile(RefundImportFile refundImportFile) {
		this.refundImportFile = refundImportFile;
	}

}
