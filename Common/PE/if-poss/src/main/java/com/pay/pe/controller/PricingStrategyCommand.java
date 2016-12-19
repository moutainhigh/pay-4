package com.pay.pe.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.helper.EFFECTIVEON;
import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.helper.SERVICELEVEL;
import com.pay.util.StringUtil;

public class PricingStrategyCommand implements Serializable {
	/**
	 * 序列id.
	 */
	private final long serialVersionUID = -3107728909381591875L;

	private Integer paymentservicecode;// 支付服务代码
	private String paymentservicename;

	private int paymentServiceType;// 交易支付服务

	private Long pricestrategycode;// 价格策略代码
	private String pricestrategyname;// 价格策略名称
	private String pricestrategytype;// 价格策略类型 1-固定费用;2-费率;3-费率及下限;
										// 4-费率及上限;5-费率及上下限;6-固定费用,费率及下限;7-固定费用,费率及上限;
										// 8-固定费用,费率及上下限

	private Integer effectiveon;// 生效范围 1-全局; 2-特定服务等级; 3-特定会员
	private String effectiveonString;
	private Long membercode;// 会员
	private Integer servicelevelcode;// 服务等级

	private Integer status;// 状态

	private String validdate;// 有效日期
	private String invaliddate;// 失效时间

	private Integer caculatemethod;// 计算方式 1-单笔交易; 2-每月固定; 3-特定时间;4-每月累计
	private String pricestrategyseq;// 同一paymentservicecode的价格策略序号

	private final PRICESTRATEGYTYPE pricestrategytypeEnum[] = PRICESTRATEGYTYPE
			.values();
	private final EFFECTIVEON effectiveonEnum[] = EFFECTIVEON.values();
	private final CACULATEMETHOD caculatemethodEnum[] = CACULATEMETHOD.values();

	private Map pricestrategytypeMap = new HashMap();
	private Map effectiveonMap = new HashMap();
	private Map caculatemethodMap = new HashMap();
	private Map servicelevelcodeMap = new HashMap();

	public PricingStrategyCommand() {
		for (PRICESTRATEGYTYPE aaa : PRICESTRATEGYTYPE.values()) {
			pricestrategytypeMap.put(aaa.getValue(),
					aaa.getPRICESTRATEGYTYPEDesc(aaa.getValue()));
		}

		for (EFFECTIVEON aaa : EFFECTIVEON.values()) {
			effectiveonMap.put(aaa.getValue(),
					aaa.getEFFECTIVEONDesc(aaa.getValue()));
		}

		caculatemethodMap.put(CACULATEMETHOD.SINGLETRANSACTION.getValue(),
				CACULATEMETHOD
						.getCACULATEMETHODDesc(CACULATEMETHOD.SINGLETRANSACTION
								.getValue()));
		caculatemethodMap.put(CACULATEMETHOD.ACCUMULATED.getValue(),
				CACULATEMETHOD.getCACULATEMETHODDesc(CACULATEMETHOD.ACCUMULATED
						.getValue()));
		// servicelevelcodeMap.put(200, "企业普通会员");
		// servicelevelcodeMap.put(100, "个人普通会员");
		// servicelevelcodeMap.put(101,"个人卖家");
		// servicelevelcodeMap.put(201, "中小企业客户");
		// servicelevelcodeMap.put(202,"大企业客户");
		for (SERVICELEVEL aaa : SERVICELEVEL.values()) {
			servicelevelcodeMap.put(aaa.getValue(),
					aaa.getSERVICELEVELDesc(aaa.getValue()));
		}

	}

	private Long pricestrategydetailcode;// 价格策略明细代码

	private Integer terminltype;// 终端
	private String effectivefrom;// 时间(起始)
	private String effectiveto;// 时间(中止)

	private Long chargerate;// 费率
	private String fixedcharge;// 固定费用
	private String maxcharge;// 上限
	private String mincharge;// 下限

	private Long rangby;// 费率基数
	private String rangbystring;
	private String rangfrom;// 交易额(起始)
	private String rangto;// 交易额(中止)

	private String reservedCode;// 扩展代码
	private String reservedName;// 扩展名称

	public String getReservedCode() {
		return reservedCode;
	}

	public void setReservedCode(String reservedCode) {
		this.reservedCode = reservedCode;
	}

	public String getReservedName() {
		return reservedName;
	}

	public void setReservedName(String reservedName) {
		this.reservedName = reservedName;
	}

	public Integer getTerminltype() {
		return terminltype;
	}

	public void setTerminltype(Integer terminltype) {
		this.terminltype = terminltype;
	}

	public Integer getCaculatemethod() {
		return caculatemethod;
	}

	public void setCaculatemethod(Integer caculatemethod) {
		this.caculatemethod = caculatemethod;
	}

	public Integer getEffectiveon() {
		return effectiveon;
	}

	public void setEffectiveon(Integer effectiveon) {
		this.effectiveon = effectiveon;
	}

	public Long getMembercode() {
		return membercode;
	}

	public void setMembercode(Long membercode) {
		this.membercode = membercode;
	}

	public Integer getPaymentservicecode() {
		return paymentservicecode;
	}

	public void setPaymentservicecode(Integer paymentservicecode) {
		this.paymentservicecode = paymentservicecode;
	}

	public Long getPricestrategycode() {
		return pricestrategycode;
	}

	public void setPricestrategycode(Long pricestrategycode) {
		this.pricestrategycode = pricestrategycode;
	}

	public String getPricestrategyname() {
		return pricestrategyname;
	}

	public void setPricestrategyname(String pricestrategyname) {
		this.pricestrategyname = pricestrategyname;
	}

	public String getPricestrategytype() {
		return pricestrategytype;
	}

	public void setPricestrategytype(String pricestrategytype) {
		this.pricestrategytype = pricestrategytype;
	}

	public Integer getServicelevelcode() {
		return servicelevelcode;
	}

	public void setServicelevelcode(Integer servicelevelcode) {
		this.servicelevelcode = servicelevelcode;
	}

	public String getInvaliddate() {
		return invaliddate;
	}

	public void setInvaliddate(String invaliddate) {
		this.invaliddate = invaliddate;
	}

	public String getValiddate() {
		return validdate;
	}

	public void setValiddate(String validdate) {
		this.validdate = validdate;
	}

	public Long getChargerate() {
		return StringUtil.isNull(chargerate) ? new Long(0) : chargerate;
	}

	public void setChargerate(Long chargerate) {
		this.chargerate = chargerate;
	}

	public String getEffectivefrom() {
		return effectivefrom;
	}

	public void setEffectivefrom(String effectivefrom) {
		this.effectivefrom = effectivefrom;
	}

	public String getEffectiveto() {
		return effectiveto;
	}

	public void setEffectiveto(String effectiveto) {
		this.effectiveto = effectiveto;
	}

	public String getFixedcharge() {
		return StringUtil.isNull(fixedcharge) ? "0" : fixedcharge;
	}

	public void setFixedcharge(String fixedcharge) {
		this.fixedcharge = fixedcharge;
	}

	public String getMaxcharge() {
		return StringUtil.isNull(maxcharge) ? "0" : maxcharge;
	}

	public void setMaxcharge(String maxcharge) {
		this.maxcharge = maxcharge;
	}

	public String getMincharge() {
		return StringUtil.isNull(mincharge) ? "0" : mincharge;
	}

	public void setMincharge(String mincharge) {
		this.mincharge = mincharge;
	}

	public Long getPricestrategydetailcode() {
		return pricestrategydetailcode;
	}

	public void setPricestrategydetailcode(Long pricestrategydetailcode) {
		this.pricestrategydetailcode = pricestrategydetailcode;
	}

	public Long getRangby() {
		return StringUtil.isNull(rangby) ? new Long(0) : rangby;
	}

	public void setRangby(Long rangby) {
		this.rangby = rangby;
	}

	public String getRangfrom() {
		return StringUtil.isNull(rangfrom) ? "0" : rangfrom;
	}

	public void setRangfrom(String rangfrom) {
		this.rangfrom = rangfrom;
	}

	public String getRangto() {
		return StringUtil.isNull(rangto) ? "0" : rangto;
	}

	public void setRangto(String rangto) {
		this.rangto = rangto;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEffectiveonString() {
		return effectiveonString;
	}

	public void setEffectiveonString(String effectiveonString) {
		this.effectiveonString = effectiveonString;
	}

	public String getRangbystring() {
		return rangbystring;
	}

	public void setRangbystring(String rangbystring) {
		this.rangbystring = rangbystring;
	}

	public String getPaymentservicename() {
		return paymentservicename;
	}

	public void setPaymentservicename(String paymentservicename) {
		this.paymentservicename = paymentservicename;
	}

	public int getPaymentServiceType() {
		return paymentServiceType;
	}

	public void setPaymentServiceType(int paymentServiceType) {
		this.paymentServiceType = paymentServiceType;
	}

	public String getPricestrategyseq() {
		return pricestrategyseq;
	}

	public void setPricestrategyseq(String pricestrategyseq) {
		this.pricestrategyseq = pricestrategyseq;
	}

	public PRICESTRATEGYTYPE[] getPricestrategytypeEnum() {
		return pricestrategytypeEnum;
	}

	public EFFECTIVEON[] getEffectiveonEnum() {
		return effectiveonEnum;
	}

	public CACULATEMETHOD[] getCaculatemethodEnum() {
		return caculatemethodEnum;
	}

	public Map getPricestrategytypeMap() {
		return pricestrategytypeMap;
	}

	public void setPricestrategytypeMap(Map pricestrategytypeMap) {
		this.pricestrategytypeMap = pricestrategytypeMap;
	}

	public Map getEffectiveonMap() {
		return effectiveonMap;
	}

	public void setEffectiveonMap(Map effectiveonMap) {
		this.effectiveonMap = effectiveonMap;
	}

	public Map getCaculatemethodMap() {
		return caculatemethodMap;
	}

	public void setCaculatemethodMap(Map caculatemethodMap) {
		this.caculatemethodMap = caculatemethodMap;
	}

	public Map getServicelevelcodeMap() {
		return servicelevelcodeMap;
	}

	public void setServicelevelcodeMap(Map servicelevelcodeMap) {
		this.servicelevelcodeMap = servicelevelcodeMap;
	}

}
