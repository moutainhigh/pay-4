package com;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.pe.helper.OrgCode;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;
import com.pay.util.StringUtil;


@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = false)
@Transactional
public class WithDrawCalFeeServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "peService")
	private PEService peService;
	
	private String  getorderId(){
		String result ="201008888888"; 
		int temp = (int)(1+Math.random()*10000000);
		return result+temp;
	}
	
	private  String getOrderCode(int pkgCode){
		int a = pkgCode ;
		int b = a/10 ;
		int c = b*10 ;
		return c+"" ;
	}
	
	
	
	private PaymentReqDto setPaymentReqTemplate(PaymentReqDto calFeeReqDto,Integer payer,Integer payee){
		if(payer==1){
			calFeeReqDto.setPayerOrgType(OrgType.MEMBER.getValue());
			calFeeReqDto.setPayerAcctType(10);          			//科目无需设置账户类型
			calFeeReqDto.setPayer("10000000005");                   //科目无需设置payee
			
			calFeeReqDto.setPayerMemberAcctCode("1000000000510");    //科目无需设置账户账号全称
			calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000000510");  //科目无需设置账户全称
		}else if(payer == 2){
			calFeeReqDto.setPayerOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayerOrgCode(OrgCode.MOCK001.getValue());    //设置ORGCODE  银行科目需要设置
		}else{//HNA
			calFeeReqDto.setPayerOrgType(OrgType.INNER.getValue());
		}
		
		if(payee==1){
			calFeeReqDto.setPayeeOrgType(1);
			calFeeReqDto.setPayeeAcctType(10);
			calFeeReqDto.setPayee("10000000200");
			calFeeReqDto.setPayeeMemberAcctCode("1000000020010");
			calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000020010");
		}else if(payee ==2){
			calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayeeOrgCode(OrgCode.MOCK001.getValue());
		}else{
			calFeeReqDto.setPayeeOrgType(OrgType.INNER.getValue());
		}
		return calFeeReqDto;
	
	}
	/**
	 * 退款将付款方和收款方反之
	 * @param calFeeReqDto
	 * @param payer
	 * @param payee
	 * @return
	 */
	private PaymentReqDto setPaymentReqTemplateRefund(PaymentReqDto calFeeReqDto,Integer payer,Integer payee){
		if(payer==1){
			calFeeReqDto.setPayerOrgType(OrgType.MEMBER.getValue());
			calFeeReqDto.setPayerAcctType(10);          			//科目无需设置账户类型
			calFeeReqDto.setPayer("10000000200");                   //科目无需设置payee
			
			calFeeReqDto.setPayerMemberAcctCode("1000000020010");    //科目无需设置账户账号全称
			calFeeReqDto.setPayerFullMemberAcctCode("20010200100011000000020010");  //科目无需设置账户全称
		}else if(payer == 2){
			calFeeReqDto.setPayerOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayerOrgCode(OrgCode.MOCK001.getValue());    //设置ORGCODE  银行科目需要设置
		}else{//HNA
			calFeeReqDto.setPayerOrgType(OrgType.INNER.getValue());
		}
		
		if(payee==1){
			calFeeReqDto.setPayeeOrgType(1);
			calFeeReqDto.setPayeeAcctType(10);
			calFeeReqDto.setPayee("10000000005");
			calFeeReqDto.setPayeeMemberAcctCode("1000000000510");
			
			calFeeReqDto.setPayeeFullMemberAcctCode("20010200100011000000000510");
		}else if(payee ==2){
			calFeeReqDto.setPayeeOrgType(OrgType.BANK.getValue());
			calFeeReqDto.setPayeeOrgCode(OrgCode.MOCK001.getValue());
		}else{
			calFeeReqDto.setPayeeOrgType(OrgType.INNER.getValue());
		}
		return calFeeReqDto;
	}
		
	/**
	 * 提现第二部测试
	 * @param acctCode
	 * @param peObj
	 * @param amount
	 * @return
	 */
	private PaymentReqDto initCalFeeRequest(int dealCode,int payer,int payee,Long amount,String orderId,boolean isRefund){
		System.out.println("========================================" + dealCode);
		PaymentReqDto  calFeeReqDto = new PaymentReqDto();
		
//		calFeeReqDto.setPayerFee(5000L);
//		calFeeReqDto.setHasCaculatedPrice(true);
		if(StringUtil.isEmpty(orderId)){
			orderId =this.getorderId();
			calFeeReqDto.setOrderId(orderId);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>> orderId <<<<<<<<<<<<<<<<<<<<<<<<<<<< "+ orderId);
		}else{
			calFeeReqDto.setOrderId(orderId);
		}		
		calFeeReqDto.setOrderAmount(amount);
		calFeeReqDto.setAmount(amount);
		calFeeReqDto.setDealCode(dealCode);                //枚举类由PE统一提供
		calFeeReqDto.setOrderCode(Integer.parseInt(getOrderCode(dealCode)));    //枚举类由PE统一提供  
		
//		calFeeReqDto.setOrderCode(Integer.parseInt("120"));    //枚举类由PE统一提供  
		calFeeReqDto.setOrderCode(Integer.parseInt(getOrderCode(dealCode)));    //枚举类由PE统一提供  


		calFeeReqDto.setPayMethod(1);                 //枚举类由PE统一提供
		calFeeReqDto.setTerminalType(TerminalType.WEB.getValue());
		calFeeReqDto.setRequestDate(new Date());
		if(isRefund){
			this.setPaymentReqTemplateRefund(calFeeReqDto, payer, payee);
		}else{
			this.setPaymentReqTemplate(calFeeReqDto, payer, payee);
		}
		return calFeeReqDto ;
	}
	
	/**
	 * 
	 */
	/**
	 * 
	 */
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	/**
	* @Title: testCalculate
	* @Description: TODO(这里用一句话描述这个方法的作用)
	* @param     设定文件
	* @return void    返回类型
	* @throws
	*/ 
	@Test
	public void testCalculate(){
		
		/**
		 * 银行卡在线充值（个人）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(101,2,1,100*1000L,"",false);
		
		/**
		 * 银行卡在线充值（B2B）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(102,2,1,100*1000L,"",false);
		
		/**
		 * 银行卡在线充值（大额）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(103,2,1,100*1000L,"",false);
		
		
		//信用卡大额充值（包含手续费）
//		PaymentReqDto calFeeRequest = initCalFeeRequest(105,2,1,100*1000L,"",false);

		//信用卡大额充值（无手续费）
//		PaymentReqDto calFeeRequest = initCalFeeRequest(106,2,1,100*1000L,"",false);
		/**
		 * 银行卡支付（B2C）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(111,1,1,100*1000L,"",false);
		
		/**
		 * 银行卡支付（B2B）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(112,1,1,100*1000L,"",false);

		/**
		 * 银行卡支付（大额）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(113,1,1,100*1000L,"",false);
		
		/**
		 * 账户支付
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(114,1,1,100*1000L,"",false);
		
		//信用卡大额支付（付款方付费）

//    	PaymentReqDto calFeeRequest = initCalFeeRequest(116,1,1,100*1000L,"",false);

		//信用卡大额支付（收款方付费）

//    	PaymentReqDto calFeeRequest = initCalFeeRequest(181,1,1,100*1000L,"",true);
    	
		/**
		 * 网关银行卡支付退手续费(第一步)
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(121,1,1,100*1000L,"",true);
		
		/**
		 * 网关银行卡支付退款不退手续费(第一步)
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(122,1,1,100*1000L,"true");
		
		/**
		 * 网关银行卡支付退款(第二步)
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(123,1,3,100*1000L,"",true);
		
		/**
		 * 网关银行卡支付退款(第三步)
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(124,3,2,100*1000L,"",true);
		
		/**
		 * 网关银行卡支付退款(第三步)(不成功)(不成功)
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(125,3,1,100*1000L,"",true);
		
		/**
		 * 网关账户支付退手续费
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(126,1,1,100*1000L,"",true);
		/**
		 * 网关账户支付不退手续费
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(127,1,1,100*1000L,"",true);
		

		
		/**
		 * 网关对账 1133 1134
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(131,2,2,100*1000L,"",false);
		/**
		 * 网关对账 1134 1002
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(132,2,2,100*1000L,"",false);
		
		/**
		 * 提现申请
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(301,1,3,100*1000L,null,false);
		
		
		/**
		 * 银行代扣
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(401,2,1,100*1000L,"",false);
		
		/**
		 * 银行代扣退款第一步
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(411,1,2,100*1000L,"",false);

		
		
		/**
		 * 银行代扣退款第二步（成功）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(412,2,2,100*1000L,"",false);
		
		
		/**
		 * 银行代扣退款失败
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(413,1,2,100*1000L,"",false);
		
		
		/**
		 * 银行代扣退票
		 */
		PaymentReqDto calFeeRequest = initCalFeeRequest(421,2,1,100*1000L,"",false);
		
		/**
		 * 提现第三步
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(303,3,2,100*1000L,"2010088888886727105",false);
		
		/**
		 * 提现退款
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(304,3,1,100*1000L,"2010088888889971151",false);
		
		/**
		 * 提现退票
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(311,2,1,100*1000L,"",true);
		
		/**
		 * 付款到银行第一步
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(321,1,3,100*1000L,"",false);
		
		/**
		 * 付款到银行第二步
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(322,3,2,100*1000L,"",false);
		/**
		 * 付款到银行退款
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(323,3,1,100*1000L,"",false);
		
		/**
		 * 直付到银行的退票
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(331,2,1,100*1000L,"",true);
		
		/**
		 * 批量付款到银行账户
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(341,1,3,100*1000L,"",false);
		
		/**
		 * 批量直付第二步
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(342,3,2,100*1000L,"",false);
		
		/**
		 * 风控拒绝、网银支付失败
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(343,3,1,100*1000L,"",false);
		
		/**
		 * 批量直付到银行退票
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(351,2,1,100*1000L,"",true);
		
		/**
		 * 单笔直付到账户
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(361,1,1,100*1000L,"",false);
		
		/**
		 * 单笔直付到账户（收款方付费）
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(362,1,1,100*1000L,"",false);
		
		/**
		 * 批量内转-总订单扣款
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(371,1,3,100*1000L,null);
		
		/**
		 * 批量内转-单笔付到收款方
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(372,3,1,100*1000L,"",false);
		
		/**
		 * 批量内转-单笔失败退款
		 */
//		PaymentReqDto calFeeRequest = initCalFeeRequest(373,3,1,100*1000L,"",false);
		
		PaymentResponseDto calFeeRespone = peService.processPayment(calFeeRequest);
		try {
			System.out.println("voucherCode   =====  "+calFeeRespone.getVoucherCode());

//			boolean flage = peService.accounting(calFeeRespone);
//			System.out.println("accounting reuset ==== " + flage);
//			assert flage == true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("========================================");
		System.out.println("result = " + calFeeRespone.toString());
	}

}