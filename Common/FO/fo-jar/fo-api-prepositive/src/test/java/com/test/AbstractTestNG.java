package com.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 测试抽象超类
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml",
		"classpath*:test/**/*.xml" })
public abstract class AbstractTestNG extends AbstractTestNGSpringContextTests {
}
