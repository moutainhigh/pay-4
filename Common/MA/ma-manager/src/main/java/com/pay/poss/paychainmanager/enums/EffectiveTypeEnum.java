/**
 * 
 */
package com.pay.poss.paychainmanager.enums;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		EffectiveDateEnum.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-3		fjl			Create
 */
public enum EffectiveTypeEnum {
	
	DAY_180(1,180,"180天"),
    ONE_YEAR(2,1,"1年"),
    TWO_YEAR(3,2,"2年"),
    THREE_YEAR(4,3,"3年"),
    TEN_YEAR(5,10,"10年");
    
    
    private int type;
    private int value;
    private String memo;
    
    EffectiveTypeEnum(int type,int value,String  memo){
        this.type = type;
        this.value = value;
        this.memo = memo;
    }
    
    public int getType(){
    	return type;
    }
    
    public int getValue(){
        return value;
    }
    
    public String getMemo(){
    	return memo;
    }
    
    
    public static EffectiveTypeEnum getEffectiveEnum(int type){
        for (EffectiveTypeEnum nameEnum : values()) {
            if(nameEnum.getType()==type){
                return nameEnum;
            }
        }
        return DAY_180;
    }   
  
}
