/**
 * 
 */
package com.pay.fi.helper;

/**
 * @author chaoyue
 *
 */
public enum ChannelItemOrgCodeEnum {

	MOCK("00000000", "MOCK"), 
	BOCS("10076001", "中银卡司"),
	BOCM("10079001", "中银MOTO"),
	BOCI("10080001", "中银MIGS"),
	CYBSCTV("10078001", "农行CTV"),
	BOC("10003001", "中国银行"),
	ABC("10002001", "农业银行"),
	CREDORAX("10075001","Credorax"),
	ADYEN("10077001","Adyen"),
	BELTO("10077002","boleto"),
	CASHU("10077003","Cashu"),
	HNAPAY("10077004","新生支付"),//新生支付
	CT_BOLETO("10081001","CT_BOLETO"),
	CT_SAFETYPAY("10081002","CT_SAFETYPAY"),
	CT_DirectDebitsSSL("10081003","CT_DirectDebitsSSL"),
	CT_SofortBanking("10081004","CT_SofortBanking"),
	CT_EPS("10081005","CT_EPS"),
	CT_Giropay("10081006","CT_Giropay"),
	CT_PagBrasilDebitCard("10081007","CT_Debit Card"),
	CT_PagBrasilOTF("10081008","CT_Online Bank Transfer"),
	CT_Poli("10081009","CT_Poli"),
	CT_Przelewy24("10081010","CT_Przelewy24"),
	CT_Qiwi("10081011","CT_Qiwi"),
	CT_SEPA("10081012","CT_SEPA"),
	CT_Teleingreso("10081013","CT_Teleingreso"),
	CT_TrustPay("10081014","CT_TrustPay"),
	CT_iDeal("10081015","CT_iDeal"),
	QHWR("10081016","前海万融"),
	WFT("10081017","威富通【微信｜支付宝】")
	;

	private String code;
	private String desc;

	private ChannelItemOrgCodeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static boolean isExists(String code) {

		for (ChannelItemOrgCodeEnum item : ChannelItemOrgCodeEnum.values()) {
			if (item.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public static ChannelItemOrgCodeEnum getChannelItemByCode(String code){
		for(ChannelItemOrgCodeEnum item:ChannelItemOrgCodeEnum.values()){
			if(item.getCode().equals(code)){
				return item;
			}
		}
		return null;
	}

}
