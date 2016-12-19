package com.pay.test;

import java.util.HashMap;
import java.util.Map;

import com.caucho.hessian.client.HessianProxyFactory;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

public class Test {
	
public static void main(String[] args) {
		
		Map<String,Object> paraMap = new HashMap<String, Object>();
		
		
		/*   登录
		  测试10602*/
		
//		  paraMap.put("loginName", "2397031958@qq.com");
//		  paraMap.put("loginPwd", "12345678");
//		  paraMap.put("identity", "adminA");
//		  paraMap.put("type", "2");
		
//		  paraMap.put("loginName", "17092101985@163.com");
//		  paraMap.put("loginPwd", "asdfasdf");
//		  paraMap.put("identity", "admin");
//		  paraMap.put("type", "2");
		
	 /*企业注册 10601
	    paraMap.put("loginName", "138183138520.com");
		paraMap.put("loginPwd", "xiaodai1234");
		paraMap.put("legalName", "Hacker");
		paraMap.put("type", "2");
		paraMap.put("zhName", "上海师范大学");
		paraMap.put("region", "安徽英语培训公司"); 
		paraMap.put("city", "安徽发展有限公司");
		paraMap.put("regionBank", "2900");
		paraMap.put("tel", "13988888888");
		paraMap.put("legalLink", "18817505157");
		paraMap.put("cityBank", "2900");
		paraMap.put("email", "wenru@qq.com");
		paraMap.put("address", "上海市徐汇区桂林路100号");
		paraMap.put("branchBankId", "10009001");
		paraMap.put("bigBankName", "dhdasasdhdsaaa");*/
		
		
		//个人会员注册10601
		/*paraMap.put("loginName","13919566255@qq.com");
		paraMap.put("loginpwd","admin1314");
		paraMap.put("type","1");*/
		
		
		
		 	
		
		/*paraMap.put("memberCode", "10000003671");
		  paraMap.put("frozen", "1");*/
		
		
		
		
		
		//账户金额更新
		//paraMap.put("loginName", "17092101985@163.com");
		//paraMap.put("type", "1");
		//paraMap.put("amount", "10000");
		
		
		// 测试添加账户币种10630
		/* paraMap.put("memberCode", "10000003780");
		 paraMap.put("acctType","103" );
		*/
		
		

		//测试账户状态10629 */
		/* paraMap.put("acctCode", "2243010220110000003593201");
		 paraMap.put("memberCode", "10000003593");
		 paraMap.put("status", "1");*/

		
		
		// 测试冻结会员状态10631
		//paraMap.put("memberCode", "10000003593");
		// 测试解冻会员状态10632
		paraMap.put("memberCode", "");
		

		
		
		
		// 测试账户币种查询10628
		// paraMap.put("memberCode", "10000003617");
		
		
		// 查询账户所有币种账户10633
	   //paraMap.put("memberCode", "10000003617");
			
		//账户信息查询（memberCode=10000003617）10633
		//paraMap.put("memberCode", "10003617");
		
		
		
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.TXNCORE.getCode());
		try{
			
			HessianProxyFactory factory = new HessianProxyFactory();

			HessianInvokeService hessianInvokeService = null;
			
			hessianInvokeService = (HessianInvokeService) factory.create(
						HessianInvokeService.class, "http://localhost:8080/account/service/invoke");

			String result = hessianInvokeService.invoke("10632", sysTraceNo,
					SystemCodeEnum.ACCOUNT.getCode(), SystemCodeEnum.ACCOUNT.getCode(),
					SystemCodeEnum.ACCOUNT.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());
			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();
			
			System.out.println("result: "+result);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
