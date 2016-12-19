///**
// * 
// */
//package com.pay.acc.service.perf;
//
//
//
//import junit.framework.Test;
//
//
//
///**
// * @author Administrator
// *
// */
//public class AccountExecutePerfTest {
//	
//	/**
//	 * 进行多用户性能测试
//	 * 
//	 * 注意：10个用户，循环运行1次，20秒钟完成，
//	 * @return
//	 */
////	protected static Test executeAccountPerfWithMutiUser(){
////		int users=10000;
////		int iterations = 10;
////		long maxElapsedTimeInMillis=100000;
////		Timer timer = new ConstantTimer(1000);
////		Test testFactory = new TestFactory(AccountBalancePerfTest.class);
//////		Test loadTest = new LoadTest(testFactory, users, iterations);
////		Test loadTest = new LoadTest(testFactory, users, iterations,timer);
////		Test timedTest = new TimedTest(loadTest, maxElapsedTimeInMillis);
////		return timedTest;
////	}
////	public static void main(String[] args) {
////		junit.textui.TestRunner.run(executeAccountPerfWithMutiUser());
////		
////	}
//	
//	
//}
