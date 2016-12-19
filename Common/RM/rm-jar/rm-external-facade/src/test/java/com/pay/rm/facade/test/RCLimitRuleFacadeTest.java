/**
 *  <p>File: AbstractRCLimitProvider.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */

package com.pay.rm.facade.test;
import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.facade.dto.RCLimitParamDTO;

/**
 * <p></p>
 * @author zengli
 * @since 2011-5-11
 * @see 
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
"classpath*:context/*.xml","classpath*:context/**/*.xml"})
public class RCLimitRuleFacadeTest extends AbstractTestNGSpringContextTests {
	
	@Resource(name="rm-rclimitrulefacade")
	private RcLimitRuleFacade facade;

	/**
	 * @param facade the facade to set
	 */
	public void setFacade(RcLimitRuleFacade facade) {
		this.facade = facade;
	}
	
	@Test
	public void testCustomBusinessRule(){
		try {
			System.err.println("----"+facade.getRiskLevelList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	@Test
	public void testBusinessRule(){
		RCLimitParamDTO rcLimitParamDTO = new RCLimitParamDTO();
//		rcLimitParamDTO.setBusiType(RCLIMITCODE.FO_WITHDRAW.getValue());
//		rcLimitParamDTO.setMemberType(RCLIMITCODE.ENTERPRISE.getValue());
		rcLimitParamDTO.setMemberCode(10000000001l);
		rcLimitParamDTO.setLevel(202);
		try {
			System.out.println(facade.getRule(rcLimitParamDTO));
		} catch (RMFacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@Test
	public void testUserRule(){
		RCLimitParamDTO rcLimitParamDTO = new RCLimitParamDTO();
//		rcLimitParamDTO.setBusiType(RCLIMITCODE.FO_WITHDRAW.getValue());
//		rcLimitParamDTO.setMemberType(RCLIMITCODE.PERSONAL.getValue());
		rcLimitParamDTO.setMemberCode(10000000001l);
		rcLimitParamDTO.setLevel(202);
		try {
			System.out.println(facade.getRule(rcLimitParamDTO));
		} catch (RMFacadeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
