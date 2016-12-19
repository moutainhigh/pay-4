/**
 * 
 */
package com.pay.batchpay.dto;

/**
 * @author PengJiangbo
 *
 */
public class OrderTemplateFileDbUnion {
	
	private BulkPaymentTemplate fileTemplate ;
	
	private BulkPaymentTemplate dbTemplate ;

	public BulkPaymentTemplate getFileTemplate() {
		return fileTemplate;
	}

	public void setFileTemplate(BulkPaymentTemplate fileTemplate) {
		this.fileTemplate = fileTemplate;
	}

	public BulkPaymentTemplate getDbTemplate() {
		return dbTemplate;
	}

	public void setDbTemplate(BulkPaymentTemplate dbTemplate) {
		this.dbTemplate = dbTemplate;
	}

	@Override
	public String toString() {
		return "OrderTemplateFileDbUnion [fileTemplate=" + fileTemplate
				+ ", dbTemplate=" + dbTemplate + "]";
	}
	
}
