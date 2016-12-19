package com.pay.app.base.api.common.constans;

/**
 * 操作类型参数值
 *
 */
public abstract class CutsConstants {
	// 日志类型
	/** 登录 **/
    public static final int USER_LOG_LOGIN=1; 
    /** 支付  **/
    public static final int USER_LOG_PAY=2;  
    /** 付款  **/
    public static final int USER_LOG_PAYMENT=3;
    /** 充值  **/
    public static final int USER_LOG_COST=4;
    /** 确认付款  **/
    public static final int USER_LOG_PAYCONFIRM=5;
    /** 余额查询  **/
    public static final int USER_LOG_QUERYBALANCE=6;
    /** 交易查询 **/
    public static final int USER_LOG_QUERYTRADE=7;
    /** 设置安全问题  **/
    public static final int USER_LOG_SAFEQUESTION=8;
    /** 修改支付密码 **/
    public static final int USER_LOG_UPDATEPAYPWD=9;
    /** 订阅通知  **/
    public static final int USER_LOG_NOTICE=10;
    /** 添加联系人 **/
    public static final int USER_LOG_ADDLINKER=11;
    /** 找回支付密码 **/
    public static final int USER_LOG_FINDPAYPWD=12;
    /** 修改问候语  **/
    public static final int USER_LOG_UPDATEGREETING=13;
    /** 提现 **/
    public static final int USER_LOG_CASH=14;
    /** 补全资料  **/
    public static final int USER_LOG_COMPLETEUSERINFO=15;
    /** 注册成功**/
    public static final int USER_LOG_REGISTERSUCCESS=16;
    /** 安全登录  **/
    public static final int USER_LOG_SECURE_LOGIN=17;
    /** 口令卡登录  **/
    public static final int USER_LOG_CARD_LOGIN=18;
    /** 登录失败  **/
    public static final int USER_LOG_LOGIN_FAILED=19;
    
    /***************登录级别**************************/
    /** 锁定 */
    public static final int FEATURE_LOCK=0;
    /** 口令卡用户非口令卡登录  */
    public static final int FEATURE_UNMAXTRIX=1;
    /** 普通登录 */
    public static final int FEATURE_NORMAL=2;
    /** 口令卡登录 */
    public static final int FEATURE_MAXTRIX=3;
    /** 数字证书 */
    public static final int FEATURE_CERTIFICATE=4;
    /** U盾 */
    public static final int FEATURE_U_SHIELD=5;
    
    /** 控件存放路径 */
    public static final String WOYOEDIT_SETUP_PATH="woyoedit.setup.path";
}
