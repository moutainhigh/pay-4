package com.pay.poss.common;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath*:context/*.xml","classpath*:context/conf/*.xml" })
public class PossPropertiesUtilTest extends AbstractTestNGSpringContextTests {

	/**
	 * 从缓存中读取配置获取website 上下文路径
	 */
	@Test
	public void f() {
		
		
	}

}
