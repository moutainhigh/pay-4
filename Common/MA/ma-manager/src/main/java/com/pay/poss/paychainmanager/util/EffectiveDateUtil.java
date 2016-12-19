package com.pay.poss.paychainmanager.util;

import java.util.Calendar;
import java.util.Date;

import com.pay.poss.paychainmanager.enums.EffectiveTypeEnum;
import com.pay.util.DateUtil;

public class EffectiveDateUtil {
	
	
	/**
	 * 根据 开始时间和时间步长类型得到结果时间
	 * @param typeEnum
	 * @param startDate
	 * @return
	 */
	public static Date getEndDate(EffectiveTypeEnum typeEnum,Date startDate){
		if(startDate == null){
			startDate = new Date();
		}
		
		if(typeEnum == null){
			typeEnum = EffectiveTypeEnum.DAY_180;
		}
		Date endDate = null;
		switch (typeEnum) {
		case DAY_180:
			endDate = DateUtil.skipDateTime(startDate, EffectiveTypeEnum.DAY_180.getValue());
			break;
		case ONE_YEAR:
			endDate = skipYear(startDate, EffectiveTypeEnum.ONE_YEAR.getValue());
			break;
		case TWO_YEAR:
			endDate = skipYear(startDate, EffectiveTypeEnum.TWO_YEAR.getValue());
			break;
		case THREE_YEAR:
			endDate = skipYear(startDate, EffectiveTypeEnum.THREE_YEAR.getValue());
			break;
		case TEN_YEAR:
			endDate = skipYear(startDate, EffectiveTypeEnum.TEN_YEAR.getValue());		
			break;

		default:
			break;
		}

		return endDate;
		
	}
	
	/**
	 * 根据开始时间和步长类型，判断步长是否过期
	 * @param typeEnum
	 * @param startDate
	 * @return
	 */
	public static boolean isEffectiveTypeExpired(EffectiveTypeEnum typeEnum,Date startDate){
		
		Date endDate = getEndDate(typeEnum,startDate);
		Date nowDate = new Date();
		if(nowDate.after(endDate)){
			return true;
		}

		return false;
		
	}
	
	public static Date skipYear(Date d, int year) {
        if (d == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

	public static void main(String[] args) {
		
		Date d = new Date();
		d = DateUtil.skipDateTime(d,-365);
		
		for(EffectiveTypeEnum item :EffectiveTypeEnum.values()){
			System.out.println(DateUtil.formatDateTime("yyyy-MM-dd hh:mm:ss",
					EffectiveDateUtil.getEndDate(item, d)));
		}
		
		for(EffectiveTypeEnum item :EffectiveTypeEnum.values()){
			System.out.println(isEffectiveTypeExpired(item,d));
		}
		
	}
}
