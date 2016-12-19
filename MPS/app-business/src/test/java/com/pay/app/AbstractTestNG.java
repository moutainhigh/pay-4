/**
 *  File: AbstractTestNG.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-14   terry_ma     Create
 *
 */
package com.pay.app;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 测试抽象超类
 */
 @ContextConfiguration(locations = { "classpath*:context/*.xml" })
public abstract class AbstractTestNG extends AbstractTestNGSpringContextTests {

	abstract public void testSave();

	abstract public void testUpdate();

	abstract public void testDeleteById();

	abstract public void testFindById();

	abstract public void testLoadAll();
}
