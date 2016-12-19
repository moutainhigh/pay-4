/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.common.enums;

/**
 * 服务级别枚举
 * @author zhi.wang
 * @version $Id: ServiceLevelEnum.java, v 0.1 2010-12-2 下午04:10:54 zhi.wang Exp $
 */
public enum ServiceLevelEnum {
    
    /** 个人普通会员*/
    INDIVIDUAL_GENERAL(100,"个人普通会员"),
    /** 个人卖家 */
    INDIVIDUAL_SELLER(101,"个人卖家"),
    /** 支付 */
    INDIVIDUAL_pay(102,"支付账户"),
    /** 支付 */
    INDIVIDUAL_paySELLER(103,"支付个人卖家"),
    /** 企业普通会员  */
    ENTERPRICE_GENERAL(200,"企业普通会员"),
    /** 中小企业客户  */
    ENTERPRICE_SMALL(201,"中小企业客户"),
    /** 大企业客户  */
    ENTERPRICE_LARGE(202,"大企业客户"),
    
    /** 集团企业客户  */
    ENTERPRICE_GROUP_SMALL(203,"集团企业客户"),
    /** 超大型企业客户  */
    ENTERPRICE_BIG_SMALL(204,"超大型企业客户"),
    /** 交易中心  */
    TRADING_CENTER(300,"交易中心"),
    /** 测试会员  */
    TEST_GENERAL(999,"测试会员");
    
    // 服务级别代码
    private Integer code;
    // 服务级别名称
    private String name;
    
    ServiceLevelEnum(Integer code,String name){
        this.code = code;
        this.name = name;
    }
    
    public Integer getValue(){
        return code;
    }
    
    public String getName(){
        return name;
    }
    
    /**
     * 根据服务级别代码获取对应的服务级别枚举
     *
     * @param code 
     *        根据服务级别代码
     * @return
     */
    public static ServiceLevelEnum getServiceLevelEnum(Integer code){
        if(code != null){
            for (ServiceLevelEnum nameEnum : values()) {
                if(nameEnum.getValue() == code){
                    return nameEnum;
                }
            }
        }
        return null;
    }
}
