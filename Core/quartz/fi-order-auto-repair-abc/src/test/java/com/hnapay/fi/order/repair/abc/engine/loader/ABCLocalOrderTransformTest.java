/**
 * ABCLocalOrderTransformTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.abc.engine.loader;

import java.util.Map;

import org.junit.Test;

import com.hnapay.fi.dto.batchrepair.api.LocalOrderDTO;

/**
 * latest modified time : 2011-8-26 上午11:54:14
 * @author bigknife
 */
public class ABCLocalOrderTransformTest {
	@Test
	public  void testTransform() {
		ABCLocalOrderTransform t = new ABCLocalOrderTransform();
		LocalOrderDTO order = new LocalOrderDTO();
		order.setDepositProtocolNo("1111");
		Map<String, String> map = t.toQueryParam(order);
		System.out.println(map);
	}
}
