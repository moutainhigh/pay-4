
package com.pay.pe.helper;

import java.util.EnumMap;

public enum DealType {
	/**
	 * 缺省的DealType,只保存订单.
	 */
	DefaultDealType(0),
	
	/**
	 * 银行卡支付BankCardPay.
	 */
	BankCardPay(10),
	
	/**
	 * 退款支付WireOutPay.
	 */
	WireOutPay(20),
	
	/**
	 * 账户支付AcctPay.
	 */
	AcctPay(30),
	
	/**
	 * 中间账户支付SpecAcctPay.
	 */
	SpecAcctPay(40),
	
	/**
	 * 线下支付Offline_EBankPay.
	 */
	Offline_EBankPay(50),
	
	/**
	 * 线下退款Offline_PostPay.
	 */
	Offline_PostPay(60),
	
	/**
	 * 神州行卡支付SZXCardPay.
	 */
	SZXCardPay(70),
	
	/**
	 * 电话银行支付TelBankPay.
	 */
	TelBankPay(80),
	
	/**
	 * Visa卡支付VisaPay.
	 */
	VisaPay(90),
	
	/**
	 * 企业银行直联.
	 */
	BankBizApi(11),
	/**
     * 批量直付子订单. 
	 */
    MassPay2BankAcct(12),
	
	/**
	 * 信用卡支付
	 */
	CreditCardPay(13),
	  /**
     * 代扣.
     */
	Debit(14), 
	
	/**
	 * 网关预付费卡支付.
	 */
	PrepayCardPay(17);

    private int value;

    public int getValue() {
        return value;
    }

    DealType(int value) {
        this.value = value;
    }
    
    public final static EnumMap<DealType, String> DEALTYPEMAP;

    static {
    	DEALTYPEMAP = new EnumMap<DealType, String>(DealType.class);
    	DEALTYPEMAP.put(DealType.BankCardPay, "银行卡支付");
    	DEALTYPEMAP.put(DealType.WireOutPay, "退款支付");
    	DEALTYPEMAP.put(DealType.AcctPay, "账户支付");
    	DEALTYPEMAP.put(DealType.SpecAcctPay, "中间账户支付");
    	DEALTYPEMAP.put(DealType.Offline_EBankPay, "线下支付");
    	DEALTYPEMAP.put(DealType.Offline_PostPay, "线下退款");
    	DEALTYPEMAP.put(DealType.SZXCardPay, "神州行卡支付");
    	DEALTYPEMAP.put(DealType.TelBankPay, "电话银行支付");
    	DEALTYPEMAP.put(DealType.VisaPay, "Visa卡支付");
    	DEALTYPEMAP.put(DealType.BankBizApi, "企业银行直联");
    	DEALTYPEMAP.put(DealType.MassPay2BankAcct, "批量直付子订单");
    	DEALTYPEMAP.put(DealType.CreditCardPay, "信用卡支付");
    	DEALTYPEMAP.put(DealType.PrepayCardPay, "网关预付费卡支付");
    }
    /**
     * 跟据value返回枚举对应的key
     * 
     * @param value
     * @return ACCTTYPE
     */
    public static DealType getDealTypeMapKey(int value) {
    	DealType tmpKey = null;
        for (DealType tmpEnum : DealType.values()) {
            if (tmpEnum.value == value) {
                tmpKey = tmpEnum;
                break;
            }
        }
        return tmpKey;
    }
    /**
     * 返回DealType对应的描述.
     * @param value int.
     * @return String
     */
    public static String getDealTypeDesc(final int value) {
        return DealType.DEALTYPEMAP.get(
            DealType.getDealTypeMapKey(value));
    }
    
    
}
