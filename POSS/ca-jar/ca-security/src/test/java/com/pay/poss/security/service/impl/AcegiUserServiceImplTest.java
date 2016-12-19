//package com.pay.poss.security.service.impl;
//
//import java.util.Map;
//
//import junit.framework.Assert;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//
//import com.pay.poss.security.service.IUserService;
//
//@ContextConfiguration(locations = {
//		"classpath*:config/env/test-dataAccessContext.xml",
//		"classpath*:config/spring/platform/*.xml",
//		"classpath:config/spring/security/*.xml" })
//public class AcegiUserServiceImplTest extends AbstractTestNGSpringContextTests {
//	@Autowired
//	private IUserService userService;
//
//	// @Test
//	public void testGetUserByLoginCode() throws Exception {
//		Map result = userService.getUserByLoginCode("adminA");
//		Assert.assertEquals("adminA", result.get("LOGIN_ID") + "");
//	}
//
//	// //@Test
//	// public void testQueryRolesByLoginCode() throws PossUntxException {
//	// List<String> result = userService.queryRolesByLoginCode("adminA");
//	// Assert.assertNotNull(result);
//	// }
//	// @Test
//	// public void testQueryResesByLoginCode() throws PossUntxException {
//	// List<String> result = userService.queryRolesByLoginCode("adminA");
//	// Assert.assertNotNull(result);
//	// System.out.println(result);
//	// }
//}
