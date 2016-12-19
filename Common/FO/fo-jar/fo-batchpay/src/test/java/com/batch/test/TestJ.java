///**
// * 
// */
//package com.batch.test;
//
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.pay.batchpay.dto.StudentTest;
//
//
///**
// * @author PengJiangbo
// *
// */
//public class TestJ {
//
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testSub() {
//		System.out.println(Integer.MAX_VALUE);
//		System.out.println(Long.MAX_VALUE);
//		System.out.println(Long.valueOf("2015091810400175932"));
//	}
//	
//	@Test
//	public void testRa(){
//		for(int i= 0 ; i<1000; i++){
//			String random = random(1) ;
//			System.out.println("random:" + random);
//		}
//	}
//	public String random(int n) {
//        if (n < 1 || n > 10) {
//            throw new IllegalArgumentException("cannot random " + n + " bit number");
//        }
//        Random ran = new Random();
//        if (n == 1) {
//            return String.valueOf(ran.nextInt(10));
//        }
//        int bitField = 0;
//        char[] chs = new char[n];
//        for (int i = 0; i < n; i++) {
//            while(true) {
//                int k = ran.nextInt(10);
//                if( (bitField & (1 << k)) == 0) {
//                    bitField |= 1 << k;
//                    chs[i] = (char)(k + '0');
//                    break;
//                }
//            }
//        }
//        return new String(chs);
//    }
//	
//	@Test
//	public void testBd(){
//		BigDecimal val = new BigDecimal(0.02).multiply(new BigDecimal(1000)) ;
//		System.out.println(val);
//		String str = "Hello" ;
//		String strArr[] = str.split(",") ;
//		System.out.println(strArr.length) ;
//	}
//	
//	@Test
//	public void testListSome(){
//		
//		StudentTest st1 = new StudentTest(1L, "a1", "3_0") ;
//		StudentTest st2 = new StudentTest(2L, "a2", "2_0,1_1") ;
//		StudentTest st3 = new StudentTest(3L, "a3", "1_0,1_1,1_2") ;
//		List<StudentTest> lists = new ArrayList<StudentTest>() ;
//		lists.add(st1) ;
//		lists.add(st2) ;
//		lists.add(st3) ;
//		int listsLen = lists.size() ;
//		List<StudentTest> lt = new ArrayList<StudentTest>() ;
//		
//		for(int i=0; i<listsLen; i++){
//			String pwd = lists.get(i).getPassword() ;
//			String pwdArr[] = pwd.split(",") ;
//			StringBuilder sb = new StringBuilder() ;
//			for(int j=0; j<pwdArr.length; j++){
//				String pwdArrEv = pwdArr[j] ;
//				if(pwdArr[j].contains("_0")){
//					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("笔付款成功").append("\\n") ;
//				}else if(pwdArr[j].contains("_1")){
//					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("笔付款中").append("\\n") ;
//				}else if(pwdArr[j].contains("_2")){
//					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("付款失败").append("\\n") ;
//				}
//			}
//			lists.get(i).setPassword(sb.toString());
//			lt.add(lists.get(i)) ;
//		}
//		System.out.println(lt);
//	}
//	
//	
//}
