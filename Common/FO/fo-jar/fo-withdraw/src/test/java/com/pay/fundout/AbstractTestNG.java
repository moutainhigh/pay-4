package com.pay.fundout;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 测试抽象超类
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml","classpath*:config/**/*.xml" })
public abstract class AbstractTestNG extends AbstractTestNGSpringContextTests {
}
