package com.pay.poss.base.enums;
/**
 * @Description 各个包所用到的公用枚举
 * @project 	poss-memberanager
 * @file 		CommonEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-11		tianqing_wang			Create
 */
public enum CommonEnum {

	MEEMBER_FROZEN("MEEMBER_FROZEN", "企业会员冻结"),
	MEEMBER_UNFROZEN("MEEMBER_UNFROZEN", "企业会员解冻"),
	ACCT_FROZEN("MEEMBER_FROZEN", "企业账户冻结"),
	ACCT_UNFROZEN("MEEMBER_UNFROZEN", "企业账户解冻"),
	PERSONAL_MEMBER_FROZEN("MEEMBER_FROZEN", "个人会员冻结"),
	PERSONAL_MEMBER_UNFROZEN("MEEMBER_UNFROZEN", "个人会员解冻"),
	PERSONAL_ACCT_FROZEN("PERSONAL_ACCT_FROZEN", "个人账户冻结"),
	PERSONAL_ACCT_UNFROZEN("PERSONAL_ACCT_UNFROZEN", "个人账户解冻"),
	STATUS_ZERO("0","正常"),
	STATUS_ONE("1","冻结"),
	STATUS_TWO("2","解冻");
	private final String code;
    private final String description;
    
    /**
     * 私有构造方法
     * @param code
     * @param description
     */
    private CommonEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return Returns the code.
     */
    public String getCode() {
        return code;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>code</code>获得枚举
     * @param code
     * @return
     */
    public static CommonEnum valueof(String code) {
        for (CommonEnum commonEnum : values()) {
            if (commonEnum.getCode().equals(code)) {
                return commonEnum;
            }
        }
        return null;
    }
}
