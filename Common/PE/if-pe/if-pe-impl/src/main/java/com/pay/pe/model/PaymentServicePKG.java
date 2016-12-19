package com.pay.pe.model;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import com.pay.inf.model.Model;



public class PaymentServicePKG implements Model{

	private Integer autoMergeEntries;
	private String description;
	private Integer paymentServicePackageCode;
	private String paymentServicePackageName;
	private Collection paymentSrvPkgAssignList;

	public PaymentServicePKG() {
		super();
		this.paymentSrvPkgAssignList = new Vector();
	}

	public void addToPaymentSrvPkgAssignList(Object anObject) {
		// Fill in method body here.
	}

	public void addToPaymentSrvPkgAssignList(
			PaymentSrvPkgAssignment aPaymentSrvPkgAssignment) {
		// Fill in method body here.
	}

	public Integer getAutoMergeEntries() {
		return this.autoMergeEntries;
	}

	public String getDescription() {
		return this.description;
	}

	public Collection getPaymentSrvPkgAssignList() {
		return this.paymentSrvPkgAssignList;
	}

	public Integer getPaymentServicePackageCode() {
		return paymentServicePackageCode;
	}

	public void setPaymentServicePackageCode(Integer paymentServicePackageCode) {
		this.paymentServicePackageCode = paymentServicePackageCode;
	}

	public String getPaymentServicePackageName() {
		return paymentServicePackageName;
	}

	public void setPaymentServicePackageName(String paymentServicePackageName) {
		this.paymentServicePackageName = paymentServicePackageName;
	}

	public void removeFromPaymentSrvPkgAssignList(
			PaymentSrvPkgAssignment aPaymentSrvPkgAssignment) {
		// Fill in method body here.
	}

	public void removeFromPaymentSrvPkgAssignList(Object anObject) {
		// Fill in method body here.
	}

	public void setAutoMergeEntries(Integer autoMergeEntries) {
		this.autoMergeEntries = autoMergeEntries;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPaymentSrvPkgAssignList(Collection paymentSrvPkgAssignList) {
		this.paymentSrvPkgAssignList = paymentSrvPkgAssignList;
	}

	// public String toString() {
	// StringBuffer sb = new StringBuffer();
	// sb.append("autoMergeEntries->" + autoMergeEntries + ";");
	// sb.append("description->" + description + ";");
	// sb.append("paymentservicepackagecode->" + paymentservicepackagecode +
	// ";");
	// sb.append("paymentservicepackagename->" + paymentservicepackagename +
	// ";");
	// return sb.toString();
	// }

}
