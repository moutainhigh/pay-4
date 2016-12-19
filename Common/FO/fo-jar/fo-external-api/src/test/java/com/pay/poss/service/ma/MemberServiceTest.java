/**
 *  File: a.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-17   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.service.ma;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 
 * @author Sandy_Yang
 */
@ContextConfiguration(locations = { "classpath*:config/**/external-api-bean.xml","classpath*:context/*.xml" })
public class MemberServiceTest extends AbstractTestNGSpringContextTests {
	// 该功能无需提供   2010-10-06 zliner修改 
//	@Autowired
//	private AccountQueryService accountQueryService ;
//	
//	@Test
//	public void testAccountQueryService() {
//		QueryBalanceBO q = new QueryBalanceBO();
//		q.setMemberCode(1L);
//		q.setAccountType(1);
//		try {
//			accountQueryService.doQueryBalanceInfoByDateNsTx(q);
//		} catch (MaAccountQueryUntxException e) {
//			e.printStackTrace();
//		}
//	}
}
