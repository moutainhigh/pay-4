package com.pay.poss.refund.main;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pay.poss.base.common.Constants;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BigDecimal sumAmount = new BigDecimal(0);
//		System.out.println(sumAmount.divide(new BigDecimal(1000),2,BigDecimal.ROUND_HALF_UP));
//		sumAmount= sumAmount.add(new BigDecimal(1000));
//		System.out.println(sumAmount);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = sdf.format(new Date(System.currentTimeMillis()));
		String manualSeq = String.valueOf(System.currentTimeMillis());
		String taskMsg = manualSeq + ":" + Constants.TASK_TYPE_REFUND  +":" + Constants.BATCH_BUILD_AUTO + ":1:1:" + currentTime;
		System.out.println(taskMsg);
	}
	
	public static void testBigDecimal(){
		//BigDecimal a = new BigDecimal(0);
		double b = 0;
		b+=((new BigDecimal(3)).doubleValue());
		System.out.println(new BigDecimal(b));
	}

}
