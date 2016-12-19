//package com.pay.poss.security.authentication;
//
//import javax.servlet.FilterChain;
//
//import org.easymock.MockControl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.Test;
//
//import com.pay.poss.security.authentication.AuthenticationProcessingFilterExt;
//
//@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml", "classpath:config/spring/security/*.xml" })
//public class AuthenticationProcessingFilterExtTest extends AbstractTestNGSpringContextTests {
//
//	@Autowired
//	private AuthenticationProcessingFilterExt authentication;
//
//	@Test
//	public void testDoFilter() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		MockHttpServletResponse response = new MockHttpServletResponse();
//		
//		request.setMethod("POST");
//		request.setRequestURI("/j_spring_security_check");
//		request.addParameter("j_username", "tester1");
//		request.addParameter("j_password", "123456");
//
//		MockControl mockFilter = MockControl.createControl(FilterChain.class);
//		FilterChain filterChain = (FilterChain) mockFilter.getMock();
//		
////		try {
////			authentication.doFilter(request, response, filterChain);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		} catch (ServletException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//	}
//}
