package com;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class ORGCODETest {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
//		DateFormat   df   =   new   SimpleDateFormat("yy-mm-dd");
//		System.out.println((df.parse( "2007-07-01 ")).compareTo(df.parse( "2007-07-01 ")) );
		
//		System.out.println(DateUtils.truncate(new Date(), Calendar.DAY_OF_YEAR));
		System.err.println("Truncated Date: " +  DateUtils.truncate(new Date(), Calendar.DATE));   
		
		
//		System.out.println(ORGCODE.MOCK001);
//		System.out.println(ORGCODE.getOrganizationDesc(ORGCODE.MOCK001.getValue()));
//		System.out.println(ORGCODE.getOrganizationIdentify(ORGCODE.MOCK001.getValue()));		
//		System.out.println(ORGCODE.getORGCODE("000000001"));
//		System.out.println(ORGCODE.getORGCODEValue("MOCK001"));

	}

}
