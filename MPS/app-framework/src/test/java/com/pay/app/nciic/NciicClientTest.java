/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.nciic;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pay.app.cidverify.BaseCid2GovParameterModel;
import com.pay.app.cidverify.INciicClient;
import com.pay.app.cidverify.cid2gov.XmlModelManager;



/**
 * @author fjl
 * @date 2011-4-29
 */

public class NciicClientTest  extends TestCase {
	
	
	/**
	 * application context configuration file
	 */
	private static  ApplicationContext ac = null;

	protected void setUp() throws Exception {

		ac = new ClassPathXmlApplicationContext(new String[] { 
				"classpath*:context/ma/context-cidverify.xml"
		});
	}

	protected void tearDown() throws Exception {
		
		((ClassPathXmlApplicationContext)ac).close();
	}
	
	public void testNciicGetCondition(){
    	System.out.println("testNciicGetCondition");
    	
    	try {
			INciicClient client =(INciicClient) ac.getBean("app-cidgov");
			XmlModelManager manager =( XmlModelManager ) ac.getBean("app-xmlModelManager");
			
			BaseCid2GovParameterModel bpm = new BaseCid2GovParameterModel();
			bpm.setNo("340824198208107413");
			bpm.setName("宋增辉");
			
			String con = manager.getCondition(bpm);
			System.out.println(con);
			
//			String ret =client.executeCidVerify(con);
//			System.out.println(ret);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
