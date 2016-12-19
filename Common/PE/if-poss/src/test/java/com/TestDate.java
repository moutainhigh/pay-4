package com;

import java.text.SimpleDateFormat;

public class TestDate {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		System.out.println(bartDateFormat.format(new java.util.Date()));
	}

}
