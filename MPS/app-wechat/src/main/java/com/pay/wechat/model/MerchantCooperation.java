/**
 * 
 */
package com.pay.wechat.model;

/**
 * 了解合作实体
 * @author PengJiangbo
 *
 */
public class MerchantCooperation {

	/**
	 * 姓名
	 */
	private String memberName ;
	/**
	 * 公司名称
	 */
	private String companyName ;
	/**
	 * 联系电话
	 */
	private String tel ;
	/**
	 * 经营范围
	 */
	private String operateScope ;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOperateScope() {
		return operateScope;
	}
	public void setOperateScope(String operateScope) {
		this.operateScope = operateScope;
	}
	@Override
	public String toString() {
		return "Cooperation [memberName=" + memberName + ", companyName="
				+ companyName + ", tel=" + tel + ", operateScope="
				+ operateScope + "]";
	}
	
}
