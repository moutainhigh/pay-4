package com.pay.base.common.enums;



public enum EffectiveTypeEnum {
	
	DAY_180(1,180,"180天"),
    ONE_YEAR(2,365,"1年"),
    TWO_YEAR(3,730,"2年"),
    THREE_YEAR(4,1095,"3年"),
    TEN_YEAR(5,3650,"10年");
    
    
    private int effectiveType;
    private int days;
    private String memo;
    
    EffectiveTypeEnum(int effectiveType,int day,String  memo){
        this.effectiveType=effectiveType;
        this.days=day;
        this.memo=memo;
    }
    
    public int getValue(){
        return effectiveType;
    }
    
    public int getDays(){
        return days;
    }
    
    public String getMemo(){
    	return memo;
    }
    
    public static final EffectiveTypeEnum[] SEARCH_TYPES=new EffectiveTypeEnum[]{DAY_180,ONE_YEAR,TWO_YEAR
    	,THREE_YEAR,TEN_YEAR
    };
    
    public static EffectiveTypeEnum getEffectiveEnum(int effectiveType){
        for (EffectiveTypeEnum nameEnum : values()) {
            if(nameEnum.getValue()==effectiveType){
                return nameEnum;
            }
        }
        return null;
    }
    
    
    public static int  getDatsByValue(int effectiveType){
        for (EffectiveTypeEnum nameEnum : values()) {
            if(nameEnum.getValue()==effectiveType){
                return nameEnum.getDays();
            }
        }
        return 0;
    }
    
    public static String  getMemoByValue(int effectiveType){
        for (EffectiveTypeEnum nameEnum : values()) {
            if(nameEnum.getValue()==effectiveType){
                return nameEnum.getMemo();
            }
        }
        return "";
    }
    /*
    public static void main(String[] args) {
		System.out.println(EffectiveTypeEnum.getEffectiveEnum(2).getDays());
	}*/
    
    public static void main(String[] args) {
    	EffectiveTypeEnum [] ee=EffectiveTypeEnum.SEARCH_TYPES;
    	for(EffectiveTypeEnum ef :ee){
    		System.out.println(ef.getValue());
    	}
	}
}
