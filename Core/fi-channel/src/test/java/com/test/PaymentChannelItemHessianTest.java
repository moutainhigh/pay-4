/**
 * 
 */
package com.test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * @author chaoyue
 *
 */
@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class PaymentChannelItemHessianTest extends AbstractTestNGSpringContextTests{

	@Resource(name="client-channelService")
	private HessianInvokeService hessianInvokeService;

	//@Test
	public void testCategoryAdd() {

		Map<String, String> paraMap = new HashMap<String, String>();
		
		paraMap.put("code", "ACCT_RMB");
		
		paraMap.put("name", "人民币账户支付");
		paraMap.put("operator", "system");
		paraMap.put("description", "人民币账户支付");
		
		
		String requestJSonString = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper
				.processRequest(requestJSonString);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());

		String result = hessianInvokeService.invoke("20105", sysTraceNo,
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,String> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		System.out.println(resultMap);
	}
	
	//@Test
	public void testCategoryDel() {

		Map<String, String> paraMap = new HashMap<String, String>();
		
		paraMap.put("id", "26");
		
		String requestJSonString = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper
				.processRequest(requestJSonString);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());

		String result = hessianInvokeService.invoke("20106", sysTraceNo,
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,String> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		System.out.println(resultMap);
	}
	
	@Test
	public void testCategoryQuery() {

		Map<String, String> paraMap = new HashMap<String, String>();
		
		String requestJSonString = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper
				.processRequest(requestJSonString);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.CHANNEL.getCode());

		String result = hessianInvokeService.invoke("20107", sysTraceNo,
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getCode(),
				SystemCodeEnum.CHANNEL.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String,String> resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		System.out.println(resultMap);
	}
}
