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

import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.inf.enums.SerCode;
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
@ContextConfiguration(locations = { "classpath*:context/client-service.xml" })
public class AccountTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "client-accountService")
	private HessianInvokeService invokeService;

	@Test
	public void test() {

		test10606();
	}

	public void test10601() {

		Map paraMap = new HashMap();
		paraMap.put("loginName", "machaohu100@gmail.com");
		paraMap.put("loginPwd", "asdfasdf");
		paraMap.put("name", "超越");
		paraMap.put("nickname", "超越");
		paraMap.put("type", "1");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10619() {

		Map paraMap = new HashMap();
		
		paraMap.put("loginName", "18621758498");
		paraMap.put("type", "1");
		paraMap.put("amount", "10000");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void testBindingQuickPayment() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "18621758498");
		paraMap.put("type", "1");
		paraMap.put("amount", "10000");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10603() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "501374997@qq.com");
		paraMap.put("type", "2");
		paraMap.put("flag", "1");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10614() {

		Map paraMap = new HashMap();

		// paraMap.put("loginName", "18621758498");
		paraMap.put("mobile", "18621758498");
		paraMap.put("businessType",
				CheckCodeOriginEnum.SMS_FINDLOGINPWD.getValue());

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10624() {

		Map paraMap = new HashMap();

		// paraMap.put("loginName", "18621758498");
		paraMap.put("mobile", "18621758498");
		paraMap.put("checkCode", "024401");
		paraMap.put("businessType",
				CheckCodeOriginEnum.SMS_FINDLOGINPWD.getValue());

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}
	
	public void test10606() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "18621758498");
		paraMap.put("idType", "2");
		paraMap.put("identity", "501374997@qq.com");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10607() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "18621758498");
		paraMap.put("name", "瞿益侠");
		paraMap.put("idType", "1");
		paraMap.put("idNo", "431224198802142713");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}

	public void test10608() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "18621758498");
		paraMap.put("bankAcct", "6201230000000000");
		paraMap.put("bankCode", "10004001");
		paraMap.put("bankBranchId", "105581020022");
		paraMap.put("bankBranch", "中国建设银行佛山汾江支行");
		paraMap.put("isDefault", "0");
		paraMap.put("province", "5900");
		paraMap.put("city", "5880");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}
	
	public void test10626() {

		Map paraMap = new HashMap();

		paraMap.put("loginName", "18621758498");
		paraMap.put("bankAcct", "6201230000000000");

		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());

		System.out.println(dataMap);
	}
	
	public void test10627() {
		
		Map paraMap = new HashMap();
		
		paraMap.put("orderId", System.currentTimeMillis()+"");
		paraMap.put("payer", "10000003364");
		paraMap.put("acctType", "10");
		paraMap.put("payee", "10000003477");
		paraMap.put("password", "asdfasdf1");
		paraMap.put("amount", "100");
		
		String dataMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(dataMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo("999");
		String result = invokeService.invoke(
				"", sysTraceNo, "999",
				SystemCodeEnum.ACCOUNT.getCode(),
				SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map<String, Object> dataMap = JSonUtil.toObject(result,
				new HashMap<String, Object>().getClass());
		
		System.out.println(dataMap);
	}

}
