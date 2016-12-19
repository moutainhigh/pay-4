package com.pay.poss.base.exception.enums;

import junit.framework.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pay.poss.base.exception.enums.ExceptionCodeEnum;


public class ExceptionCodeEnumTest {
	private ExceptionCodeEnum exceptionCode;
	
	@BeforeMethod
	public void init(){
		exceptionCode=ExceptionCodeEnum.NOT_FOUND_EXCEPTION;
	}
	
	@Test
	public void testGetCode(){
		Assert.assertEquals(ExceptionCodeEnum.UN_KNOWN_EXCEPTION, ExceptionCodeEnum.valueof("0001"));
		Assert.assertEquals(ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, ExceptionCodeEnum.valueof("un_define"));
	}
	
	public void testGetEnumCode(){
		Assert.assertEquals("9001", exceptionCode.getCode());
		Assert.assertEquals("未定义异常编码", exceptionCode.getDescription());
	}
}
