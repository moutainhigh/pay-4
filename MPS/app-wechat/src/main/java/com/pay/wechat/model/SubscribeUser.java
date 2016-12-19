/**
 * 
 */
package com.pay.wechat.model;

/**
 * 关注用户
 * @author PengJiangbo
 *
 */
public class SubscribeUser {

	private String openID;
	private String subscribe;
	private String nickName;
	private String sex;
	private String language;
	private String city;
	private String province;
	private String country;
	private String headImgUrl;
	private String subscribeTime;
	private String unionID;
	public String getOpenID() {
		return openID;
	}
	public void setOpenID(String openID) {
		this.openID = openID;
	}
	public String getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUnionID() {
		return unionID;
	}
	public void setUnionID(String unionID) {
		this.unionID = unionID;
	}
	@Override
	public String toString() {
		return "SubscribeUser [openID=" + openID + ", subscribe=" + subscribe
				+ ", nickName=" + nickName + ", sex=" + sex + ", language="
				+ language + ", city=" + city + ", province=" + province
				+ ", country=" + country + ", headImgUrl=" + headImgUrl
				+ ", subscribeTime=" + subscribeTime + ", unionID=" + unionID
				+ "]";
	}
	
}
