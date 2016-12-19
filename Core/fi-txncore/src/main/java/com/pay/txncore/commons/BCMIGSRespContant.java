package com.pay.txncore.commons;


/**
 * 中行MIGS返回结果码
 * @author user07
 *
 */
public enum BCMIGSRespContant {
    
	BC0("0","交易成功"),
	BC1("1","交易无法处理"),
	BC2("2","请联系发卡银行"),
	BC3("3","交易拒绝,银行无响应"),
	BC4("4","交易拒绝,此卡已过期"),
	BC5("5","卡余额不足"),
	BC6("6","交易拒绝,银行系统错误"),
	BC7("7","支付处理错误"),
	BC8("8","无效交易类型"),
	BC9("9","银行拒绝交易"),
	BCA("A","交易中止"),
	BCB("B","交易受限"),
	BCC("C","交易已取消"),
	BCD("D","延期交易"),
	BCE("E","交易拒绝联系发卡行"),
	BCF("F","3D安全校验失败"),
	BCI("I","校验码错误"),
	BCL("L","购物交易被锁定"),
	BCN("N","持卡人未做3D校验"),
	BCP("P","交易待定"),
	BCR("R","重试超限，交易不处理"),
	BCT("T","地址校验失败"),
	BCU("U","信用卡安全码错误"),
	BCV("V","地址校验和信用卡安全码错误"),
	BC("?","交易状态无法识别")
	;
	
	private String respCode;
	private String respDesc;
	
	BCMIGSRespContant(String respCode,String respDesc){
		this.respCode = respCode;
		this.respDesc = respDesc;
	}
	
	public static BCMIGSRespContant getRespCodeEnum(String value) {
		if (value != null) {
			for (BCMIGSRespContant nameEnum : values()) {
				if (nameEnum.getRespCode().equals(value)) {
					return nameEnum;
				}
			}
		}
		return null;
	}

	public String getRespCode() {
		return respCode;
	}
	
	public String getRespDesc() {
		return respDesc;
	}
}
