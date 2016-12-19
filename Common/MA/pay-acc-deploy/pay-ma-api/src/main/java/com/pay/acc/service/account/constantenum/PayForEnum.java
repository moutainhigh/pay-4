/**
 * 
 */
package com.pay.acc.service.account.constantenum;


/**
 * @author Administrator
 * 
 */
public enum PayForEnum {
	
	B2C_DEAL(100, "渠道交易",null),
	
	EXCHANGE1(101,"货币转换",null),
	
	EXCHANGE2(102,"货币转换",null),
	
	CHANNEL_SETTLEMENT(103,"渠道销账",null),
	
	MERCHANT_SETTLEMENT1(200,"商户清算,货币转换",null),
	MERCHANT_SETTLEMENT2(201,"商户清算,货币转换",null),
	
	MERCHANT_BASIC_ACCT_SETTLEMENT(202,"清算基本账户",null),
	MERCHANT_GUARANTEE_ACCT_SETTLEMENT(203,"清算保证金",null),
	MERCHANT_RISK_FEE(204,"风控手续费",null),
	MERCHANT_LIQUDATE_FEE(205,"清算手续费",null),
	//交易单笔手续费  add by nico.shao 2016年07月06日10:55:46
	MERCHANT_TRASCATION_FIXED_FEE(206,"交易单笔处理手续费",null),


	MERCHANT_WITHDRAW_APPLY(300,"提现申请",null),
	MERCHANT_WITHDRAW_SUCCESS(301,"提现成功",null),
	MERCHANT_WITHDRAW_FAIL(302,"提现失败",null),
	//MERCHANT_WITHDRAW_FEE(303,"提现手续费",null),	
	
	MERCHANT_WITHDRAW_BACK(400,"提现退票",null),
	
	MERCHANT_CANCEL_APPLY(500,"交易撤消",null),
	MERCHANT_CANCEL_EXCHANGE1(501,"交易撤消,货币转换",null),
	MERCHANT_CANCEL_EXCHANGE2(502,"交易撤消,货转换",null),
	
	MERCHANT_REFUND_APPLY(503,"交易退款",null),
	MERCHANT_REFUND_BOND(504,"交易退款，退还保证金",null),
	MERCHANT_REFUND_EXCHANGE1(505,"交易退款申请，货币兑换",null),
	MERCHANT_REFUND_EXCHANGE2(506,"交易退款申请，货币兑换",null),
	
	MERCHANT_REFUND_SUCCESS(508,"交易退款成功",null),
	MERCHANT_REFUND_SUCCESS_EXCHANGE1(509,"交易退款成功，货币兑换",null),
	MERCHANT_REFUND_SUCCESS_EXCHANGE2(510,"交易退款成功，货币兑换",null),
	
	MERCHANT_REFUND_FAIL_EXCHANGE1(512,"交易退款失败，货币兑换",null),
	MERCHANT_REFUND_FAIL_EXCHANGE2(513,"交易退款失败，货币兑换",null),
	
	MERCHANT_REFUND_FAIL_SETTLEMENT(514,"退款失败，账户入账",null),
	MERCHANT_REFUND_FAIL__GUARANTEE_ACCT_SETTLEMENT(515,"退款失败，保证金入账",null),	
	
	ASSURE_SETTLEMENT(700,"归还保证金",null),
	
	//INNER_POSTING(30,"手工账",null),
	INNER_POSTING(30,"记账",null),
	
	FREEZE_BALANCE(32,"金额冻结",null),
	UNFREEZE_BALANCE(33,"金额解冻",null),
	
	//添加冻结解冻类型与记账deal_code一致 by tom.wang 2016年5月7日14:28:46
	FREEZE_BASIC_BALANCE(900,"基本户金额冻结",null),
	UNFREEZE_BASIC_BALANCE(902,"基本户金额解冻",null),
	
	FREEZE_GUARANTEE_BALANCE(901,"保证金金额冻结",null),
	UNFREEZE_GUARANTEE_BALANCE(903,"保证金金额解冻",null),
	
	//添加退款手续费 add by Mack 2016年5月18日10:55:46
	REFUND_FEE(517,"退款手续费",null),
	
	//添加拒付相关
	CHARGE_BACK_REFUND1(601,"拒付录入",null),	//拒付退款 
	BOUNCE_602(602,"欺诈-全额拒付扣保证金(按比例)",null),
	BOUNCE_603(603,"欺诈-全额拒付扣基本户(按比例)",null),
	BOUNCE_604(604,"欺诈-部分拒付扣基本户",null),
	BOUNCE_605(605,"非欺诈-全额拒付扣保证金(按比例)",null),
	BOUNCE_606(606,"非欺诈-全额拒付扣基本户(按比例)",null),
	BOUNCE_607(607,"非欺诈-部分拒付扣基本户",null),
	//BOUNCE_610(610,"拒付罚款",null),
	//add delin 拒付罚款
	BOUNCE_610(610,"拒付确认应收罚款",null),
	BOUNCE_618(618,"拒付罚款货币兑换",null),
	BOUNCE_619(619,"拒付罚款货币兑换",null),
	BOUNCE_620(620,"拒付罚款",null),

	//add delin 2016年8月12日10:53:16 资金池头寸调拨
	CAPITAL_POOL_1201 (1201,"资金池头寸调拨",null),
	CAPITAL_POOL_1202 (1202,"资金池头寸审核拒绝",null),
	CAPITAL_POOL_1203(1203,"资金池头寸货币兑换",null),
	CAPITAL_POOL_1204 (1204,"资金池头寸货币兑换",null),
	CAPITAL_POOL_1205 (1205,"资金池头寸调拨",null),
	
	//add wenzhe 2016年8月15日11:00:16 结汇
	Settle_Parities_1001(1001,"结汇转出",null),
	Settle_Parities_1002(1002,"结汇手续费",null),
	Settle_Parities_1003(1003,"货币转换",null),
	Settle_Parities_1004(1004,"结汇转入",null),
	Settle_Parities_1005(1005,"确认结汇收入",null),
	Settle_Parities_1006(1006,"结汇失败,账户入账",null),
	Settle_Parities_1007(1007,"结汇失败,手续费退回",null),
	
	//add wenzhe 2016年8月15日11:00:16 购汇
	Buy_Parities_1001(1101,"购汇转出",null),
	Buy_Parities_1002(1102,"购汇手续费",null),
	Buy_Parities_1003(1103,"货币转换",null),
	Buy_Parities_1004(1104,"购汇转入",null),
	Buy_Parities_1005(1105,"购汇货币兑换确认",null),
	Buy_Parities_1006(1106,"购汇失败,账户入账",null),
	Buy_Parities_1007(1107,"购汇失败,手续费退回",null),
	
	//add wenzhe 2016年9月6日11:00:16 跨境付款
	CROSS_BORERPAY_2001(2001,"跨境付款",null),
	CROSS_BORERPAY_2105(2105,"跨境付款失败，入账",null),
	CROSS_BORERPAY_2002(2002,"跨境付款手续费",null),
	CROSS_BORERPAY_2003(2003,"跨境付款，小额服务费",null),
	CROSS_BORERPAY_2104(2104,"跨境付款失败，手续费入账",null),
	CROSS_BORERPAY_2103(2103,"跨境付款失败，小额服务费入账",null),
	CROSS_BORERPAY_2307(2307,"跨境付款出款失败，手续费入账",null),
	CROSS_BORERPAY_2308(2308,"跨境付款出款失败，小额服务费入账",null),
	CROSS_BORERPAY_2309(2309,"跨境付款出款失败，入账",null),
	
	
	CHARGE_BACK_REFUND(600,"拒付录入",null),	//拒付退款
	
	//对于拒付dealType为603, 604, 606, 607, 608给于一个固定的code:6034678
	BOUNCE_603_604_606_607_608(6034678 , "拒付扣款", null), //基本户
	//对于dealType为602，605，609给于一个固定的code:60259
	BOUNCE_602_605_609(60259, "拒付扣款", null), //保证金户
	
	FIXFEE_BACK_AMOUNT(518,"固定手续费退还",null),
	
	FEE_AMOUNT(34,"交易手续费",null),
	ORDER_CREDIT_FEE(207,"订单授信手续费",null),
	
	ALL_SEARCH_TYPE(999,"全部",new PayForEnum[]{
		MERCHANT_BASIC_ACCT_SETTLEMENT,
		MERCHANT_GUARANTEE_ACCT_SETTLEMENT,
		MERCHANT_RISK_FEE,
		MERCHANT_WITHDRAW_APPLY,
		MERCHANT_WITHDRAW_SUCCESS,
		MERCHANT_WITHDRAW_FAIL,
		MERCHANT_WITHDRAW_BACK,
	}),
	
	;
	public static final PayForEnum[] MARGIN_SEARCH_TYPES= { MERCHANT_REFUND_BOND, ASSURE_SETTLEMENT,INNER_POSTING,MERCHANT_GUARANTEE_ACCT_SETTLEMENT,MERCHANT_REFUND_FAIL__GUARANTEE_ACCT_SETTLEMENT,
		FREEZE_GUARANTEE_BALANCE,
		UNFREEZE_GUARANTEE_BALANCE,
//		BOUNCE_603,
//		BOUNCE_605,
//		BOUNCE_609
		BOUNCE_602_605_609
		};

	private int code;
	
	//private String strCode ;

	private String message;
	
	private PayForEnum[] subType;
	
	private PayForEnum(final int code, final String message,final PayForEnum[] subType) {
		this.code = code;
		this.message = message;
		this.subType = subType;
	}
	
	//查询类型
	public static final PayForEnum[] SEARCH_TYPES = new PayForEnum[]{
		MERCHANT_BASIC_ACCT_SETTLEMENT,
		//MERCHANT_GUARANTEE_ACCT_SETTLEMENT,
		MERCHANT_RISK_FEE,
		MERCHANT_WITHDRAW_APPLY,
		MERCHANT_WITHDRAW_SUCCESS,
		MERCHANT_WITHDRAW_FAIL,
		MERCHANT_WITHDRAW_BACK,
		FEE_AMOUNT,
		ASSURE_SETTLEMENT,//余额查询添加归还保证金类型 by tom.wang2016年4月21日16:40:16
		INNER_POSTING,
		MERCHANT_REFUND_APPLY,
		MERCHANT_REFUND_FAIL_SETTLEMENT,
		//MERCHANT_REFUND_BOND,
		//CHARGE_BACK_REFUND,
		//交易类型添加基本户的冻结解冻 by tom.wang 2016年5月11日13:02:44 
		FREEZE_BASIC_BALANCE,
		UNFREEZE_BASIC_BALANCE,
		/*BOUNCE_602,
		BOUNCE_604,
		BOUNCE_606,
		BOUNCE_607,
		BOUNCE_608,*/
		BOUNCE_603_604_606_607_608,
		BOUNCE_610,
		//添加退款手续费 add by Mack 2016年5月18日10:57:19
		REFUND_FEE,
		MERCHANT_TRASCATION_FIXED_FEE,
		//add by Mack 
		FIXFEE_BACK_AMOUNT,
		BOUNCE_620,
		//add by davis.guo at 2016-08-10
		FREEZE_GUARANTEE_BALANCE,//保证金金额冻结
		UNFREEZE_GUARANTEE_BALANCE,//保证金金额解冻
		Settle_Parities_1001,
		Settle_Parities_1002,
		Settle_Parities_1006,
		Settle_Parities_1007,
		Buy_Parities_1001,
		Buy_Parities_1002,
		Buy_Parities_1006,
		Buy_Parities_1007,
		CROSS_BORERPAY_2001,
		CROSS_BORERPAY_2105,
		CROSS_BORERPAY_2002,
		CROSS_BORERPAY_2003,
		CROSS_BORERPAY_2104,
		CROSS_BORERPAY_2103,
		CROSS_BORERPAY_2307,
		CROSS_BORERPAY_2308,
		CROSS_BORERPAY_2309
	};
	
	
	public String getGroupValue(){
		
		StringBuffer result = new StringBuffer("");
		
		if(getSubType()!=null){
			for(PayForEnum f:getSubType()){
				if(f.getSubType()!=null && f.getSubType().length>0){
					for(PayForEnum p:f.getSubType()){
						result.append(p.getCode()).append(",");
					}
				}else
					result.append(f.getCode()).append(",");
			}
		}else{
			result.append(getCode());
		}
		if(result.indexOf(",")!=-1){
			result = result.deleteCharAt(result.lastIndexOf(","));
		}
		
		return result.toString();
	}

	public static PayForEnum get(final int value){
		for(PayForEnum ft:values()){
			if(ft.getCode() == value){
				return ft;
			}
		}
		return null;
	}
	//
	static String BOUNCE_603_604_606_607_608_basie_Arr [] = {"603", "604", "606", "607", "608"}; 
	public static boolean isExists_BOUNCE_603_604_606_607_608_basic(String code) {
		for (String str : BOUNCE_603_604_606_607_608_basie_Arr) {
			if (str.equals(code)) {
				return true;
			}
		}
		return false;
	}
	//
	static String BOUNCE_602_605_609_guaratee_Arr [] = {"602", "605", "609"}; 
	public static boolean isExists_BOUNCE_602_605_609_guarantee(String code) {
		for (String str : BOUNCE_602_605_609_guaratee_Arr) {
			if (str.equals(code)) {
				return true;
			}
		}
		return false;
	}
	
	public int getCode() {
		return code;
	}

	
	public String getMessage() {
		return message;
	}

	public PayForEnum[] getSubType() {
		return subType;
	}

	

}

