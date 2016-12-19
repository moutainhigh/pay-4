/**
 * modify history
 * 2016-04-12 zhangmm: 修正了分录记录错误，以及503-504 的一些错误
 * 2016-04-22 sch: 增加了一些日志 
 * 2016-04-25 sch: 对于保证金已经清算的订单，我们把扣保证金 ==> 扣除基本户 ,我们把保证金扣除的标志，从3==》4 refunding 状态
 * 2016-05-07 sch: 增加了退款手续费，采用类似于风险订单的方式 
 * 2016-05-07 sch: 两次部分退款 金额总和超过了  退款金额总和:建议在网关定单 可退金额=0的时候，计算一下总金额的数字 
 * 2016-06-22 sch: 针对农行ctv渠道，把ReturnNO 保存到 RequestSerialId 中去,代码暂时去掉了
 * 2016-06-25 sch: 渠道超时，暂时不做任何处理，只记录状态。 等待后续变成人工处理 
 * 2016-07-05 sch: updateSettlemeOrder_afterRefund, 针对退款后，金额为0，是否要清算标志 
 * 2016-07-11 sch: 针对小金额退款订单，额外还一笔固定手续费 ，触发条件是 清算订单的 fixed_fee >0, fixed_settlemnt_feeamount >0 
 *                 这种订单，要还给商户一笔钱，然后记录在RefundFee_order中，作为RefundFee 记录中去
 * 2016-07-22 sch: RefundOrder表中增加两个字段，ChannelReturnNO ,  orgCode   
 * 2016-07-28 sch: 拒付调单中，不能退款       
 * 2016-08-17 sch: setChannelReturnNo() 修改了 ChannelReturnNo 字段，migs 从参考号获取
 * 命名规范: 
 */
package com.pay.txncore.service.refund.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.model.RefundFeeConf;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.fi.exception.refund.RefundException;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.exception.AppException;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.redis.RedisClientTemplate;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.commons.RefundConfirmStatusEnum;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.commons.RefundTypeEnum;
import com.pay.txncore.commons.ResultCode;
import com.pay.txncore.commons.SettlementFlagEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderConfirmDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.dto.refund.RefundResultNoticeDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.dto.refund.RefundOrderExtendDTO;
import com.pay.txncore.dto.refund.RefundOrderStatusChangeLogDTO;
import com.pay.txncore.model.PartnerConfig;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.RefundFeeOrder;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.PartnerConfigService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.refund.RefundOrderConfirmService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.txncore.service.refund.RefundTransactionValidateService;
import com.pay.txncore.service.refund.RefundOrderExtendService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringFormatUtil;
import com.pay.util.StringUtil;

import java.util.Calendar;


/**
 * @author chaoyue
 *
 */
public class RefundServiceImpl implements RefundService {

	private final Log logger = LogFactory.getLog(getClass());
	private RefundTransactionValidateService validateService;
	private TradeDataSingnatureService tradeDataSingnatureService;
	private TradeOrderService tradeOrderService;
	private JmsSender jmsSender;
	private PartnerConfigService partnerConfigService;
	private PaymentOrderService paymentOrderService;
	private ChannelOrderService channelOrderService;
	private AccountQueryService accountQueryService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private RefundOrderService refundOrderService;
	private RefundOrderExtendService refundOrderExtendService;		//2016-04-27
	private EnterpriseBaseService enterpriseBaseService;			//2016-05-07
	//private ChannelService channelService;							//2016-05-07
	private CurrencyRateService currencyRateService;
	private RefundOrderConfirmService refundOrderConfirmService;
	private RedisClientTemplate redisClient;
	
	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}
	
	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	private AccountingService accounting_500_500;
	private AccountingService accounting_500_501;
	private AccountingService accounting_500_502;
	private AccountingService accounting_500_503;
	private AccountingService accounting_500_504;
	private AccountingService accounting_500_505;
	private AccountingService accounting_500_506;
	private AccountingService accounting_500_507;
	private AccountingService accounting_500_508;
	private AccountingService accounting_500_509;
	private AccountingService accounting_500_510;

	private AccountingService accounting_500_511;	
	private AccountingService accounting_500_512;
	private AccountingService accounting_500_513;

	private AccountingService accounting_500_514;
	private AccountingService accounting_500_515;
	private AccountingService accounting_500_518;
	
	private AcctService acctService;
	
	//这些是常量 
	final int REFUND_FLG_UNSTTLEMENT_ALL   =1;		//未清算全额退款 
	final int REFUND_FLG_UNSTTLEMENT_PART  =2;		//未清算部分退款
	final int REFUND_FLG_SETTLEMENTED_ALL  =3;		//已清算全部退款  
	final int REFUND_FLG_SETTLEMENTED_PART =4;		//已清算部分退款

	//手续费都是按比例还给用户，不挂是固定的还是百分比的。    要求清算的时候，也按照百分比 来计算么？   （100块的单子，清算之前，退款50块，清算之后，退款50块。） 
	final int FEE_FLG_UNSTTLEMENT    	 		 = 0;		//未清算订单，不还手续费
	final int FEE_FLG_RETURN_PERPART			 = 1;       //百分比手续费要还
	final int FEE_FLG_RETURN_FIXED				 = 2;		//固定手续费要还
	final int FEE_FLG_RETURN_PERPART_FIXED  	 = 3;		//所有的手续费，都按照比例还给用户
	 
	
	final int POLICY_VERSION_FIRST = 1;				//第一个版本版本策略，也就是 2016-04-29 之前的策略代码
	final int POLICY_VERSION_SECOND = 2;			//(1)手续费如何还,使用配置。  (2)如果该条目已经清算，则不扣除保证金 
	//final int POLICY_VERSION_THIRD  = 3;			//收支两条线
	
	final int SETTLEMENTORDER_MDY_FLG = 1;
	final int SETTLEMENTORDER_MDY_AMOUNT = 2;
	final int SETTLEMENTORDER_MDY_ASSUREFLG = 4;
	final int SETTLEMENTORDER_MDY_DATE = 8;
	
	//当前使用哪个版本的扣款策略 
	int refundPolicyVersion = POLICY_VERSION_SECOND;  //这个数字在构建refund_Order_extend的时候，会用到
	boolean bSetUnsettlement_RefundAll_Success = true;	//清算前，全额退款成功时，是否设置清算订单为未清算状态 
	
	//退款手续费，退款失败，是否要还给商户	
	//final int POLICY_REFUNDFEE_FAILED_RETURN =1;		 
	//final int POLICY_REFUNDFEE_FAILED_NOTRETURN =2;		
	//final int POLICY_REFUNDFEE_FAILED_UNDEF = POLICY_REFUNDFEE_FAILED_NOTRETURN;		//没有定义，其实也就是没有收 
	
	//int refundFeeFailedPolicy = POLICY_REFUNDFEE_FAILED_RETURN;							
	
	//这个函数需要修改：  去读一张关于这个账户的配置表 ，从而来获得 是否要扣 百分比手续费的配置
	private int getMerchantFeeFlg(Long parnerId){
		int feeFlg = FEE_FLG_RETURN_PERPART_FIXED;
		
		RefundFeeConf refundFeeConf = enterpriseBaseService.queryRefundFeeConfByCode(parnerId);
		if(refundFeeConf==null){
			logger.info("找不到对应的parnerid 的退款标志");
			return feeFlg;
		}
		logger.info("refundFeeConf " + refundFeeConf.toString());
		
		feeFlg = 0;
		if(refundFeeConf.getRefundFixedFeeConf()==1){
			feeFlg |= FEE_FLG_RETURN_FIXED;
		}
		if(refundFeeConf.getRefundPerFeeConf()==1){
			feeFlg |= FEE_FLG_RETURN_PERPART;
		}
		
		logger.info("parnerId=["+parnerId+"] MerchantFeeFlg=[" + feeFlg +"]");
		return feeFlg;
	}
	
	// 判断是否是全额退款，这个判断在代码中多次出现，所以写一个函数来统一各处的逻辑
	private boolean checkIsFullRefund(long refundAmount,TradeOrderDTO tradeOrderDTO){
		if(refundAmount == tradeOrderDTO.getOrderAmount())
			return true;
		return false;
	}
	
	
	//退款失败的时候，获得需要返回给保证金帐户的保证金数量　
	//输入参数：
	//      policyVersion   :      策略版本号
	//		assureAmount_SO :        清算订单中的保证金数量
	//      settlementFlg_SO :        清算订单中的清算标志，是否已经清算
	//      settlementAssureFlg_SO    清算订单中的保证金清算标志，是否已经清算
	//      orderAmount_TO            tradeOrder.orderAmount
	//      refundAmount_RO			  refundAmount_RO
	
	private long getAssureAmount_refundFailed(int policyVersion,String refundOrderNo,long assureAmount_SO,
			boolean settlementFlg_SO, 
			boolean settlementAssureFlg_SO,
			long orderAmount_TO,long refundAmount_RO){
				
		//部分退款，返回０
		if(policyVersion <= POLICY_VERSION_FIRST){
			if(refundAmount_RO < orderAmount_TO)
				return 0;
			return assureAmount_SO;
		}
		
		if(refundAmount_RO < orderAmount_TO)
			return 0;
		
		//如果退款订单已经清算完毕，也返回０
		if(settlementAssureFlg_SO)
			return 0;
		
		return assureAmount_SO;
	}
	
	//退款申请的时候, 获取需要从保证金户 扣的款。 这个函数，和getAssureAmount_refundFailed ，可以相同，也可以不同。 如果有保存数据的话，上面的函数，可以直接取出来
	//输入参数：
	//      policyVersion:           策略版本号
	//		assureAmount_SO :        清算订单中的保证金数量
	//      settlementFlg_SO :        清算订单中的清算标志，是否已经清算
	//      settlementAssureFlg_SO    清算订单中的保证金清算标志，是否已经清算
	//      orderAmount_TO            tradeOrder.orderAmount
	//      refundAmount_RO			  refundAmount_RO
	private long getAssureAmount_refundRequest(int policyVersion, String refundOrderNo,long assureAmount_SO,
			Integer settlementAssureFlg_SO,
			long orderAmount_TO,long refundAmount_RO){
		
		if(policyVersion <= POLICY_VERSION_FIRST){			
			//部分退款，肯定返回0 
			if(refundAmount_RO != orderAmount_TO)  
				return 0;
			
			return assureAmount_SO;
		}
	
		//下面是策略版本2 的地方 
		boolean  bSettlementAssureFlg_SO = false;
		if ( settlementAssureFlg_SO == SettlementFlagEnum.SETTLEMENTED.getCode()) { 
			bSettlementAssureFlg_SO = true;
		}
		
		if(bSettlementAssureFlg_SO)
			return 0;
		
		//部分退款，也返回0
		if(refundAmount_RO != orderAmount_TO)  //也可以计算出一个比例来
			return 0;
		
		
		return assureAmount_SO;
	}
	
	//按比例计算手续费 ，这个是清算后退款  才会使用到
	//注意：这个函数生效之前，必须把数据库里的PerFee 字段 清理干净 
	//输入参数 
	//   fee_flg_policy : 收续费的计算策略
	//   orderAmount:     订单的金额
	//   settlementAmount:清算的金额, 我们使用清算金额来计算比例 
	//   refundAmount:    退款金额 
	//   fee:             退款手续费： 清算币种
	//   fee_fixed:       固定手续费   settlementOrder.getFixedFeeSettlementAmount()  这是对应的清算币种的
	//   fee_per:         百分比手续费 （由于数据库里字段不明，所以暂时，不用这个字段) ： 这个字段有可能是空的 
	
	//这里的逻辑有点问题： 100块的订单，清算前，退掉了 80, 清算后退款了10块，那么所有手续费都按照  50% 来计算返还 
	private Long calcRefundFee2(int fee_flg_policy,
			Long orderAmount,Long settlementAmount,Long refundAmount,
			Long fee,Long fee_fixed, Long fee_per) {

		BigDecimal bPaymentAmount = new BigDecimal(settlementAmount);
		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		
		//判断一下极端异常情况 
		if( (settlementAmount <=0)||(refundAmount > settlementAmount)) {
			logger.info("ERROR settlementAmount =["+settlementAmount 
					+ "]refundAmount=[" + bRefundAmount +"] set to 1.0");
			bPaymentAmount = new BigDecimal(1.0);
			bRefundAmount = new BigDecimal(1.0);
		}
		
		if(fee_flg_policy == FEE_FLG_RETURN_PERPART_FIXED){			
			if(fee !=null) {
				BigDecimal bFee = new BigDecimal(fee);
				Long fee_calc = bRefundAmount
						.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN)
						.multiply(bFee).longValue();

				logger.info("FEE_FLG_RETURN_PERPART_FIXED settlementAmount=[" + settlementAmount + "],refundAmount=[" + refundAmount + "],fee=["+ bFee + "],calcfee=["+ fee_calc +"]");
				return fee_calc;
			}
			
			return Long.valueOf(0);
		}
		
		//只退比例手续费,这里假设 百分比手续费为 是 * 10000 的
		if(fee_flg_policy == FEE_FLG_RETURN_PERPART){
			if(fee_per != null){
				BigDecimal bFee = new BigDecimal(fee_per/10);		//这里要注意,是1000还是1万 
				
				Long fee_calc =  bRefundAmount.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN).multiply(bFee).longValue();
				
				logger.info("FEE_FLG_RETURN_PART 1 settlementAmount=[" + settlementAmount + "],refundAmount=[" + refundAmount + "],fee=["+ bFee + "],calcfee=["+ fee_calc +"]");
				return fee_calc;
			}
			else if( (fee_fixed !=null ) && (fee !=null) ){
				BigDecimal bFee = new BigDecimal(fee - fee_fixed);		//这两个值得货币比重是一样的么
				Long fee_calc =  bRefundAmount.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN).multiply(bFee).longValue();
				
				logger.info("FEE_FLG_RETURN_PART 2 settlementAmount=[" + settlementAmount + "],refundAmount=[" + refundAmount + "],fee=["+fee_fixed + "] fixed_fee="
				+ fee_fixed +"],bfee=["+ bFee + "],calcfee=["+ fee_calc +"]");
				return fee_calc;
			}
			
			return Long.valueOf(0);
		}		
		
		//只退固定手续费 
		if(fee_flg_policy == FEE_FLG_RETURN_FIXED){
			if(fee_fixed != null){
				BigDecimal bFee = new BigDecimal(fee_fixed);
				
				Long fee_calc =  bRefundAmount.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN).multiply(bFee).longValue();
				
				logger.info("FEE_FLG_RETURN_FIXED settlementAmount=[" + settlementAmount + "],refundAmount=[" + refundAmount + "],fee=["+ bFee + "],calcfee=["+ fee_calc +"]");
				return fee_calc;
			}
			
			return Long.valueOf(0);
		}		
		return Long.valueOf(0);
	}
	// 小金额订单的退款，如果当时收了这206分录的手续费，则重新计算一下返回手续费，是否正确 
		// 处理逻辑： 如果清算订单的 fixed_fee 有值， settlementOrder.getFixedFeeSettlementAmount() =0 ,才会有效率 
			//注意：
			//输入参数 
			//   fee_flg_policy : 收续费的计算策略
			//   orderAmount:     订单的金额
			//   settlementAmount:清算的金额, 我们使用清算金额来计算比例 
			//   refundAmount:    退款金额 
			//   fee:             手续费 
			//   str_fee_fixed:       固定手续费   settlementOrder.getFixedFeeSettlementAmount()  这是对应的清算币种的
			//   str_fee_fixed_settlemnetAmount:   settlementOrder.getFixedFeeSettlementAmount()  这是对应的清算币种的
			//   fixed_fee_206:    
			
			
			//这里的逻辑有点问题： 100块的订单，清算前，退掉了 80, 清算后退款了10块，那么所有手续费都按照  50% 来计算返还 
	private Long calcRefundFee_206(int fee_flg_policy, Long orderAmount,
			Long settlementAmount, Long refundAmount, String  str_fee_fixed,
			String str_fee_fixed_settlemnetAmount, Long fixed_fee_206) {

		if(StringUtil.isEmpty(str_fee_fixed_settlemnetAmount) ||
		   StringUtil.isEmpty(str_fee_fixed)) {
			
			return Long.valueOf(0);
		}
		
		// 如何判断他有没有做206分录呢 ,保险一点，用一个字段来表达吧
		Long fee_fixed_settlemnetAmount = Long.valueOf(str_fee_fixed_settlemnetAmount);
		Long fee_fixed = Long.valueOf(str_fee_fixed);
		
		if ((fee_fixed_settlemnetAmount == null)
				|| (fee_fixed_settlemnetAmount > 0)) {
			return Long.valueOf(0);
		}

		if ((fee_fixed == null) || (fee_fixed == 0)) {
			return Long.valueOf(0);
		}

		if ((fixed_fee_206 == null) || (fixed_fee_206 == 0)) {
			return Long.valueOf(0);
		}

		BigDecimal bPaymentAmount = new BigDecimal(settlementAmount);
		BigDecimal bRefundAmount = new BigDecimal(refundAmount);

		// 判断一下极端异常情况
		if ((settlementAmount <= 0) || (refundAmount > settlementAmount)) {
			logger.info("ERROR settlementAmount =[" + settlementAmount
					+ "]refundAmount=[" + bRefundAmount + "] set to 1.0");
			bPaymentAmount = new BigDecimal(1.0);
			bRefundAmount = new BigDecimal(1.0);
		}

		if ((fee_flg_policy == FEE_FLG_RETURN_PERPART_FIXED)
				|| (fee_flg_policy == FEE_FLG_RETURN_FIXED)) {

			if (fixed_fee_206 != null) {
				BigDecimal bFee = new BigDecimal(fixed_fee_206);
				Long fee_calc = bRefundAmount
						.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN)
						.multiply(bFee).longValue();

				logger.info("fixed fee 206 settlementAmount=["
						+ settlementAmount + "],refundAmount=[" + refundAmount
						+ "],fee=[" + bFee + "],calcfee=[" + fee_calc + "]");
				return fee_calc;
			}

			return Long.valueOf(0);
		}
		return Long.valueOf(0);
	}
		
	//====
	//处理来自MPS的退款请求
	//流程说明：  这个版本,只允许有一张退款订单, 简化问题
	//====
	public RefundTransactionServiceResultDTO refundRdTx(
			RefundTransactionServiceParamDTO paramDTO) throws Exception {
		
		//step1: 安全性检查
		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
		validateService.validate(paramDTO);
		if (!ResponseCodeEnum.SUCCESS.getCode().equals(paramDTO.getErrorCode())) {
			resultDTO.setResponseCode(paramDTO.getErrorCode());
			resultDTO.setResponseDesc(paramDTO.getErrorMsg());
			return resultDTO;
		}
		
		//step2: 获取各种订单
		PaymentOrderDTO paymentOrderDTO = paramDTO.getPaymentOrderDTO();
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(paymentOrderDTO.getPaymentOrderNo());
		TradeOrderDTO tradeOrderDTO = paramDTO.getTradeOrderDTO();
		
		if((tradeOrderDTO == null) || (channelOrderDTO == null ) || (paymentOrderDTO==null)){			
			logger.error("退款-找不到网关订单or渠道订单 or支付订单"  + paramDTO.getTradeOrderDTO().getTradeOrderNo() );			
			resultDTO.setResponseCode(ResultCode.REFUND_ORDER_CREATE_ERROR.getCode());
			resultDTO.setResponseDesc(ResultCode.REFUND_ORDER_CREATE_ERROR.getDescription());
			return resultDTO;
		}

		if(ChannelItemOrgCodeEnum.CT_Poli.getCode().equals(channelOrderDTO.getOrgCode()) ||
				ChannelItemOrgCodeEnum.CT_Teleingreso.getCode().equals(channelOrderDTO.getOrgCode()) ||
				ChannelItemOrgCodeEnum.CT_TrustPay.getCode().equals(channelOrderDTO.getOrgCode())){
			logger.error("退款-不能退款渠道:"  + channelOrderDTO.getOrgCode() );
			resultDTO.setResponseCode(ResultCode.CAN_NOT_REFUND_CHANNEL.getCode());
			resultDTO.setResponseDesc(ResultCode.CAN_NOT_REFUND_CHANNEL.getDescription());
			return resultDTO;
		}
		
		/*begin: add by LIBO for refund concurrent*/
		HashMap unComplicateparam = new HashMap<String, String>();
		String tradeOrderNo = tradeOrderDTO.getTradeOrderNo().toString();
		unComplicateparam.put("key", tradeOrderNo);
		unComplicateparam.put("value", tradeOrderNo);
		//5秒内不得重复提交订单
		unComplicateparam.put("seconds", "5");
		boolean complicateflag =redisClient.unComplicate(unComplicateparam);
		if(complicateflag==false){
			resultDTO.setResponseCode("0049");
			resultDTO.setResponseDesc("Repeated transaction number:订单号重复");
		}
		/*end: add by LIBO for refund concurrent*/

		//add 2016-05-26 拒付订单，不允许存在
		{
			Long overBouncedAmount = tradeOrderDTO.getOverBouncedAmount();
			Long doingBouncedAmount = tradeOrderDTO.getDoingBouncedAmount();
			String checkFlag = tradeOrderDTO.getCheckFlag();
			if ( ((overBouncedAmount !=null) && (overBouncedAmount >0) )
				||((doingBouncedAmount !=null) && (doingBouncedAmount >0))
				||((checkFlag !=null) && checkFlag.equals("1"))){
				
				logger.error("退款-已拒付订单，不允许退款"  + paramDTO.getTradeOrderDTO().getTradeOrderNo() );			
				resultDTO.setResponseCode(ResultCode.CHARGEBACK_ORDER_NOT_ALLOWREFUND.getCode());
				resultDTO.setResponseDesc(ResultCode.CHARGEBACK_ORDER_NOT_ALLOWREFUND.getDescription());
				return resultDTO;				
			}		
			
			
		}
		
		String transferRate = channelOrderDTO.getTransferRate();
		//step 2.1 获取清算订单 
		List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
				.querySettlementOrder(tradeOrderDTO.getPartnerId(),
						paymentOrderDTO.getPaymentOrderNo());
				
		int settlementSize = settlementOrders.size();	//清算订单数量
		if(settlementOrders.size() !=1){
			logger.info("退款-清算订单的数量不对,无法执行退款 PaymentOrderNo=[" + paymentOrderDTO.getPaymentOrderNo()+"] 清算订单数量=" +settlementSize + "]"); 		
			resultDTO.setResponseCode(ResultCode.CONSTRUCT_REFUND_FAIL.getCode());
			resultDTO.setResponseDesc(ResultCode.CONSTRUCT_REFUND_FAIL.getDescription());
			return resultDTO;
		}
		
		{
			//对于清算失败的订单，我们暂时也不能处理退款，只有0,1,4的订单状态才能执行本操作 
			PartnerSettlementOrder settlementOrder = settlementOrders.get(0);	
			if(   (settlementOrder.getSettlementFlg() != 0)
			    &&(settlementOrder.getSettlementFlg() != 1)
			    &&(settlementOrder.getSettlementFlg() != 4)) {
				
				logger.info("退款-清算订单状态不合法,不能执行退款动作 SettlementOrderNo=[" + settlementOrder.getId() 
						+"SettlementFlg=" + settlementOrder.getSettlementFlg() + "]"); 		
				
				resultDTO.setResponseCode(ResultCode.CONSTRUCT_REFUND_FAIL.getCode());
				resultDTO.setResponseDesc(ResultCode.CONSTRUCT_REFUND_FAIL.getDescription());
				return resultDTO;
			}
			
			//add by nico.shao 2016-07-11 有的时候，下面这句能够防止一部分并发问题
			//有的时候，很奇怪，2个同样的退款订单差一秒，第1个订单已经修改了 assureSettlementFlg,但是tradeOrderAmount 挡不住 
			if( settlementOrder.getAssureSettlementFlg()==4){
				logger.info("退款-清算订单状态不合法,不能执行退款动作 SettlementOrderNo=[" + settlementOrder.getId() 
						+"AssureSettlementFlg=" + settlementOrder.getAssureSettlementFlg() + "]"); 		
				
				resultDTO.setResponseCode(ResultCode.CONSTRUCT_REFUND_FAIL.getCode());
				resultDTO.setResponseDesc(ResultCode.CONSTRUCT_REFUND_FAIL.getDescription());
				return resultDTO;
			}
		}
		//step3: 构造退款订单
		RefundOrderDTO refundOrderDTO = buildRefundOrderInfo2(paramDTO,transferRate,tradeOrderDTO);
		
		Long partnerId = tradeOrderDTO.getPartnerId();
		Long refundAmount = refundOrderDTO.getRefundAmount();

		if (refundAmount <= 0 || refundAmount > tradeOrderDTO.getRefundAmount() || refundAmount > tradeOrderDTO.getOrderAmount()) {
			logger.error("退款-构建退款对象计算退款金额为0或者大于可退金额或者大于订单金额-TRADE_ORDER_NO=["
					+ paramDTO.getTradeOrderDTO().getTradeOrderNo() + "]refundAmount=[" + refundAmount 
					+ "] t.refundAmount=[" + tradeOrderDTO.getRefundAmount() 
					+ "] t.orderAmount=[" + tradeOrderDTO.getOrderAmount());
			
			resultDTO.setResponseCode(ResultCode.REFUND_AMOUNT_EQUAL_TO_ZERO.getCode());
			resultDTO.setResponseDesc(ResultCode.REFUND_AMOUNT_EQUAL_TO_ZERO.getDescription());
			return resultDTO;
		}
		
		//从这里开始，所有的错误，都建议使用异常，让事务回滚。 否则的话，将在系统中留下退款订单,从而导致不一致的情况
		
		//step3.1   保存退款订单
		logger.info("refundOrderDTO" + refundOrderDTO.toString());
		Long refundOrderNo = refundOrderService.createRefundOrderRnTx(refundOrderDTO);  //这是一个事务？ 
		logger.info("ordercenter中 30104 退款 PolicyVersion=[" + refundPolicyVersion + "],退款订单号=["+refundOrderNo+"]支付订单号=["+paymentOrderDTO.getPaymentOrderNo()+"]");
		
		refundOrderDTO.setRefundOrderNo(refundOrderNo);
		resultDTO.setRefundOrderId(refundOrderNo+"");
		
		//step 3  更新网关订单可退余额 
		// 减少可退金额
		boolean bRet = tradeOrderService.updateRefundAmount(paramDTO
				.getTradeOrderDTO().getTradeOrderNo(), -refundAmount,
				TradeOrderStatusEnum.SUCCESS.getCode() + "");
		
		if (!bRet) {
			//need change to exception
			logger.info("退款-更新网关订单的退款金额出错[" + paramDTO.getTradeOrderDTO().getTradeOrderNo() +"]"); 		//add by sch 2016-04-22
			resultDTO.setResponseCode(ResultCode.REFUND_ORDER_CREATE_ERROR.getCode());
			resultDTO.setResponseDesc(ResultCode.REFUND_ORDER_CREATE_ERROR.getDescription());
			
			throw new AppException("退款异常！");
			//return resultDTO;
		}

		//这是记录日志的条目 
		RefundOrderStatusChangeLogDTO refundOrderStatusChangeLogDTO = this.buildRefundOrederStatusChangeLogDTO(refundOrderNo, 
				RefundStatusEnum.INIT.getCode(), RefundStatusEnum.INIT.getCode(), "init", 
				-refundAmount);
		
		//logTradeOrderModify(refundOrderNo,tradeOrderDTO.getTradeOrderNo(),refundAmount,"refund init"); //add by sch 2016-04-22
				
		//step 4    查询结算订单。 根据清算订单的状态，来进行不同的处理   
		//如果是未清算状态,则更新清算订单的清算金额和清算标志。      如果是已经清算状态, 则从保证金和基本户 上进行扣款处理 .
		//从step 4.1 -- step 4.4 应该写成一个函数，用来表明 退款的核心处理流程。 如果是  收支两条线，则改这个函数即可  
		
		/*
		//step 4.1   获取清算订单的各项值
		long assureAmount = 0;			//保证金
		long fee = 0;					//清算订单中的手续费
		String settlementCurrencyCode = "";			//清算币种
		long settlementAmount = 0;				//清算金额
		int  settlementFlg = 0;					//清算标志
		String settlementRate = "";
		String merchantOrderId = null;			//很老的订单，这个字段可能为空？
		
		PartnerSettlementOrder settlementOrder = settlementOrders.get(0);	
		//处理清算订单
		{			
			assureAmount     = settlementOrder.getAssureAmount();
			settlementAmount = settlementOrder.getAmount(); 
			if (null != settlementOrder.getFee()) {
				fee += settlementOrder.getFee();
			}
			settlementCurrencyCode = settlementOrder.getSettlementCurrencyCode();
			
			// 得到第一次结算金额和第二次结算金额
			settlementFlg = settlementOrder.getSettlementFlg();
				
			merchantOrderId = settlementOrder.getOrderId();
			settlementRate = settlementOrder.getSettlementRate();
			
			logger.info("退款-退款查询清算订单settlementOrder:清算订单号=["+settlementOrder.getId()+"],SettlementFlg=["+settlementFlg
					+"],settlementAmount=["       + settlementAmount       + "],assureAmount=["+ assureAmount 				
					+"],settlementCurrencyCode=[" + settlementCurrencyCode + "],settlementRate=["+ settlementRate 
					+"]");
		}
		
		//step 4.2  构造退款订单的可退金额
		RefundOrderExtendDTO roeDTO = buildRefundOrederExtendDTO(refundOrderNo, tradeOrderDTO, settlementOrder);  
		
		//step 4.3  
		if(settlementFlg == SettlementFlagEnum.UNSETTLEMENT.getCode() 
				|| settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode())
		{
			//清算前订单
			if(refundAmount > settlementAmount ){
				//need change to exception 
				logger.info("退款-退款订单金额超出清算金额  " + refundAmount + " > " + settlementAmount );
				resultDTO.setResponseCode(ResultCode.MORE_THAN_REFUND_AMOUNT.getCode());
				resultDTO.setResponseDesc(ResultCode.MORE_THAN_REFUND_AMOUNT.getDescription());
				throw new AppException("退款异常！");
				//return resultDTO;
			}
				
			logger.info("退款-未清算订单处理开始；退款订单号=["+refundOrderDTO.getRefundOrderNo()+"],settlementFlg=[" + settlementFlg +"]");
			
		
			settlementOrder.setSettlementFlg(SettlementFlagEnum.REFUND.getCode());
			settlementOrder.setAmount(settlementOrder.getAmount() - refundAmount);				  
			partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
			
			//logSettlementOrderModify(refundOrderNo,settlementOrder.getId(),refundAmount,
			///		settlementFlg,settlementOrder.getSettlementFlg(),"refund init");   		
			
			//修改 日志中的  时间和金额
			java.util.Date date = new Date();
			refundOrderStatusChangeSetValue(refundOrderStatusChangeLogDTO, SETTLEMENTORDER_MDY_FLG |  SETTLEMENTORDER_MDY_AMOUNT
					, settlementFlg, SettlementFlagEnum.REFUND.getCode(), 0, 0, -refundAmount, date, date);
			
			//记录退款订单的扩充信息信息
			int policyVersion = POLICY_VERSION_FIRST;
			int refund_flg = REFUND_FLG_UNSTTLEMENT_PART;
			if( checkIsFullRefund(refundAmount , tradeOrderDTO)) {
				refund_flg = REFUND_FLG_UNSTTLEMENT_ALL;
			}
						
			refundOrderExtendSetValue(roeDTO,0,0,0, 
					FEE_FLG_UNSTTLEMENT, policyVersion, refund_flg,0);
		}
		else 
		{
			logger.info("退款-已清算进行清算处理开始；退款订单号=["
					+ refundOrderDTO.getRefundOrderNo() + "],settlementFlg=["+ settlementFlg + "]");

			switch (refundPolicyVersion) {
			case POLICY_VERSION_SECOND: {
				doMerchantRefundApplyAccounting_VERSION2(channelOrderDTO,
						tradeOrderDTO, refundOrderDTO, settlementOrder,
						assureAmount, settlementCurrencyCode, settlementAmount,
						settlementRate, fee, merchantOrderId, roeDTO);

			}
				break;
			case POLICY_VERSION_FIRST:
			default: {
				doMerchantRefundApplyAccounting_VERSION1(channelOrderDTO,
						tradeOrderDTO, refundOrderDTO, settlementOrder,
						assureAmount, settlementCurrencyCode, settlementAmount,
						settlementRate, fee, merchantOrderId, roeDTO);
			}
				break;
			}

			refundOrderDTO.setPayerFee(fee);
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);

			logger.info("退款-已清算进行清算处理结束！");
		}

		//step 4.4 在数据库中记录退款订单的扩充金额   下面这几个动作,最好放在事务的外面 
		logRefundOrderExtends(roeDTO);		
		*/
		
		doRefundApply_VERSION_1_2(settlementOrders,refundOrderNo,refundAmount,refundOrderDTO,
			tradeOrderDTO,channelOrderDTO,refundOrderStatusChangeLogDTO,resultDTO);		
		
	
		//记录Log清算订单的Log 日志
		logRefundOrderStatusChangeLog(refundOrderStatusChangeLogDTO);
		
		
		//step5   通知FO后台审核
		sendToFo(paymentOrderDTO, channelOrderDTO, transferRate, partnerId,
				refundAmount, refundOrderNo);
		
		resultDTO.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
		resultDTO.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		resultDTO.setRefundAmount(new BigDecimal(refundAmount).divide(
				new BigDecimal(10)).toString());
		return resultDTO;
	}
		
	//==========
	//处理来自退款的申请,这是版本1和版本2的退款申请 
	//输出参数： refundOrderStatusChangeLogDTO: 这个是需要记录的日志
	//       resultDTO: 如果发生错误，则需要记录错误码
	//==========
	private void doRefundApply_VERSION_1_2(List<PartnerSettlementOrder> settlementOrders,
			Long refundOrderNo,Long refundAmount,RefundOrderDTO refundOrderDTO,
			TradeOrderDTO tradeOrderDTO , ChannelOrderDTO channelOrderDTO,
			RefundOrderStatusChangeLogDTO refundOrderStatusChangeLogDTO,
			RefundTransactionServiceResultDTO resultDTO
			) throws Exception {

		//step 4.1   获取清算订单的各项值
		long assureAmount = 0;			//保证金
		long fee = 0;					//清算订单中的手续费
		String settlementCurrencyCode = "";			//清算币种
		long settlementAmount = 0;				//清算金额
		int  settlementFlg = 0;					//清算标志
		String settlementRate = "";
		String merchantOrderId = null;			//很老的订单，这个字段可能为空？
		
		PartnerSettlementOrder settlementOrder = settlementOrders.get(0);	
		//处理清算订单
		{			
			assureAmount     = settlementOrder.getAssureAmount();
			settlementAmount = settlementOrder.getAmount(); 
			if (null != settlementOrder.getFee()) {
				fee += settlementOrder.getFee();
			}
			settlementCurrencyCode = settlementOrder.getSettlementCurrencyCode();
			
			// 得到第一次结算金额和第二次结算金额
			settlementFlg = settlementOrder.getSettlementFlg();
				
			merchantOrderId = settlementOrder.getOrderId();
			settlementRate = settlementOrder.getSettlementRate();
			
			logger.info("退款-退款查询清算订单settlementOrder:清算订单号=["+settlementOrder.getId()+"],SettlementFlg=["+settlementFlg
					+"],settlementAmount=["       + settlementAmount       + "],assureAmount=["+ assureAmount 				
					+"],settlementCurrencyCode=[" + settlementCurrencyCode + "],settlementRate=["+ settlementRate 
					+"]");
		}
		
		//step 4.2  构造 扩充退款订单信息 
		RefundOrderExtendDTO roeDTO = buildRefundOrederExtendDTO(refundOrderNo, tradeOrderDTO, settlementOrder);  
		
		//step 4.3  
		if(settlementFlg == SettlementFlagEnum.UNSETTLEMENT.getCode() 
				|| settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode())
		{
			//清算前订单
			if(refundAmount > settlementAmount ){
				//need change to exception 
				logger.info("退款-退款订单金额超出清算金额  " + refundAmount + " > " + settlementAmount );
				resultDTO.setResponseCode(ResultCode.MORE_THAN_REFUND_AMOUNT.getCode());
				resultDTO.setResponseDesc(ResultCode.MORE_THAN_REFUND_AMOUNT.getDescription());
				throw new AppException("退款异常！");
				//return resultDTO;
			}
				
			logger.info("退款-未清算订单处理开始；退款订单号=["+refundOrderDTO.getRefundOrderNo()+"],settlementFlg=[" + settlementFlg +"]");
			
		
			settlementOrder.setSettlementFlg(SettlementFlagEnum.REFUND.getCode());
			settlementOrder.setAmount(settlementOrder.getAmount() - refundAmount);				  
			partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
			
			//logSettlementOrderModify(refundOrderNo,settlementOrder.getId(),refundAmount,
			///		settlementFlg,settlementOrder.getSettlementFlg(),"refund init");   		
			
			//修改 日志中的  时间和金额
			java.util.Date date = new Date();
			refundOrderStatusChangeSetValue(refundOrderStatusChangeLogDTO, SETTLEMENTORDER_MDY_FLG |  SETTLEMENTORDER_MDY_AMOUNT
					, settlementFlg, SettlementFlagEnum.REFUND.getCode(), 0, 0, -refundAmount, date, date);
			
			//记录退款订单的扩充信息信息
			int policyVersion = POLICY_VERSION_FIRST;
			int refund_flg = REFUND_FLG_UNSTTLEMENT_PART;
			if( checkIsFullRefund(refundAmount , tradeOrderDTO)) {
				refund_flg = REFUND_FLG_UNSTTLEMENT_ALL;
			}
						
			refundOrderExtendSetValue(roeDTO,0,0,0, 
					FEE_FLG_UNSTTLEMENT, policyVersion, refund_flg,0);
		}
		else 
		{
			logger.info("退款-已清算进行清算处理开始；退款订单号=["
					+ refundOrderDTO.getRefundOrderNo() + "],settlementFlg=["+ settlementFlg + "]");

			switch (refundPolicyVersion) {
			case POLICY_VERSION_SECOND: {
				doMerchantRefundApplyAccounting_VERSION2(channelOrderDTO,
						tradeOrderDTO, refundOrderDTO, settlementOrder,
						assureAmount, settlementCurrencyCode, settlementAmount,
						settlementRate, fee, merchantOrderId, roeDTO);

			}
				break;
			case POLICY_VERSION_FIRST:
			default: {
				doMerchantRefundApplyAccounting_VERSION1(channelOrderDTO,
						tradeOrderDTO, refundOrderDTO, settlementOrder,
						assureAmount, settlementCurrencyCode, settlementAmount,
						settlementRate, fee, merchantOrderId, roeDTO);
			}
				break;
			}

			refundOrderDTO.setPayerFee(fee);
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);

			logger.info("退款-已清算进行清算处理结束！");
		}

		//step 4.4 在数据库中记录退款订单的扩充金额   下面这几个动作,最好放在事务的外面 
		try
		{
			logRefundOrderExtends(roeDTO);			
		}catch(Exception e) {
			logger.error("refund erro:" + refundOrderNo, e);
		}
	}
	
	//========
	//设置退款订单的refundOrderDTO.ChannelReturnNo 
	//输出参数：refundOrderDTO
	//========
	private void setChannelReturnNo(
			RefundOrderDTO refundOrderDTO,
			final ChannelPaymentResult refundResult){
		
		//针对农行ctv渠道/MIGS/中银moto，把ReturnNO 保存到 setChannelReturnNo 中去
		String strOrgCode = refundResult.getOrgCode();
		if(!StringUtil.isEmpty(strOrgCode)){
			if((ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(strOrgCode)))
			{
				String channleReturnNo = refundResult.getChannelReturnNo();
				if(!StringUtil.isEmpty(channleReturnNo)){
					refundOrderDTO.setChannelReturnNo(channleReturnNo); 
				}
			}
			else if((ChannelItemOrgCodeEnum.BOCM.getCode().equals(strOrgCode))
			   ||(ChannelItemOrgCodeEnum.BOCI.getCode().equals(strOrgCode))){
				String channleReturnNo = refundResult.getReferenceNo();   //取的是参考号 
				if(!StringUtil.isEmpty(channleReturnNo)){
					refundOrderDTO.setChannelReturnNo(channleReturnNo); 
				}
			}
		}
	}
	//=========
	//这个代码是退款成功或者失败之后的流程
	//输入参数：refundOrderNo: 退款订单号
	//      refundResult: 退款成功 / 失败的结果 . 
	//重构的规则：原来的函数，太长了，200多行。  很多变量的作用域搞不清楚了，重构的原则，是把它变得短一些 
	//=========
	
	@Override
	public void refundHandle(String refundOrderNo,
			ChannelPaymentResult refundResult) throws Exception {
		
		String responseCode = refundResult.getErrorCode();
		String responseMsg = refundResult.getErrorMsg();
		
		logger.info("退款结果处理:refundOrderNo=[" + refundOrderNo + "], process result:" + responseCode);
		
		//step1:   从数据库里获得各种订单
		RefundOrderDTO refundOrderDTO = refundOrderService.queryByPk(Long
				.valueOf(refundOrderNo));
				
		TradeOrderDTO tradeOrderDTO = tradeOrderService
				.queryTradeOrderById(refundOrderDTO.getTradeOrderNo());
		// 判断是否对账
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(refundOrderDTO.getPaymentOrderNo());		//这个名字,看起来是 QueryTradeOrderNo ,实际上是根据支付订单号来查询
		
		List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
				.querySettlementOrder(
						Long.valueOf(refundOrderDTO.getPartnerId()),
						refundOrderDTO.getPaymentOrderNo());
		
		//构造一个Log日志
		String logAction = "handle_success";
		int logNewRefundStatus = RefundStatusEnum.SUCCESS.getCode();	
		Long logRefundAmount = Long.valueOf(0);
		if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			 logNewRefundStatus = RefundStatusEnum.FAIL.getCode();			//这里代表了3种失败 ，没有细分
			 logRefundAmount = refundOrderDTO.getRefundAmount();
			 logAction = "handle_failed";
		}
		
		RefundOrderStatusChangeLogDTO refundOrderStatusChangeLogDTO = this.buildRefundOrederStatusChangeLogDTO(Long.valueOf(refundOrderNo), 
				Long.valueOf(refundOrderDTO.getStatus()), logNewRefundStatus, logAction, 
				logRefundAmount);
		
		//2016-06-25 如果是机构超时，则直接返回 , 有机构返回参数么？ 
		if(ResponseCodeEnum.UNDEFINED_ERROR.getCode().equals(responseCode)){
			logger.info("机构超时，不做处理");
			refundOrderDTO.setStatus(RefundStatusEnum.TIMEOUT.getCode() + "");		
			refundOrderStatusChangeLogDTO.setNewStatus(Long.valueOf(RefundStatusEnum.TIMEOUT.getCode()));
			
			//更新网关订单的值: note by sch 2016-05-26 这个函数最好改掉，因为只需要修改  几个字段的值，没有必要全部字段更新
			refundOrderService.updateRefundOrderByPkRnTx(refundOrderDTO);	
			//记录日志
			logRefundOrderStatusChangeLog(refundOrderStatusChangeLogDTO);
			
			//start: add by LIBO for 退款状态查询 refundOrderNo
			RefundOrderConfirmDTO refundOrderConfirmDTO = new RefundOrderConfirmDTO();
			Date createDate = new Date(); 
			refundOrderConfirmDTO.setConNum(0);
			refundOrderConfirmDTO.setRefundOrderNo(Long.parseLong(refundOrderNo)); //退款订单号
			refundOrderConfirmDTO.setCreateDate(createDate); //创建日期
			refundOrderConfirmDTO.setStatus(RefundConfirmStatusEnum.INIT.getCode()+""); //确认状态 0 初始
			refundOrderConfirmDTO.setPreStartTime(new Date(createDate.getTime() + 30*1000)); // 预启动时间 30s后发起查询
			refundOrderConfirmService.create(refundOrderConfirmDTO);
			//end: add by LIBO for 退款状态查询 refundOrderNo
			return; 
		}
		
		if((refundOrderDTO ==null) || (refundOrderDTO.getStatus() == null)){
			logger.info("退款订单找不到 或者状态为空，不做处理 ");
			return ;
		}
		
		//订单的状态， 失败、成功、最终失败这三种状态，不允许进行下一步的处理，因为可能会导致分录重复 
		{
			Integer refundOrderStatus = Integer.valueOf(refundOrderDTO.getStatus());
			
			if(  ( RefundStatusEnum.FAIL.getCode() == refundOrderStatus)
			  || ( RefundStatusEnum.SUCCESS.getCode() == refundOrderStatus)
			  || ( RefundStatusEnum.FINAL_FAIL.getCode() == refundOrderStatus)){
				
				logger.info("退款订单状态不对， 不允许处理为成功或者失败  现有状态=" + refundOrderStatus) ;			
				return ;
			}
		}
		//end 2016-06-25
		
		//step2: 从各种订单中获得各个需要的变量
		/*
		String orderId = refundOrderDTO.getRefundOrderNo() + "";
		//Long amount = refundOrderDTO.getRefundAmount();  //这个变量有歧义，在成功和失败的情况下，币种是不一样的，所以，去掉这个变量 
		String orgCode = channelOrderDTO.getOrgCode();
		//String channelSettlementCurrencyCode = channelOrderDTO.getSettlementCurrencyCode();

		String settlementCurrencyCode = "";
		boolean settlementFlg = false;
		String partnerSettlementRate = "1";
		long assureAmount = 0;
		long settlementAmount = 0;
		String merchantOrderId=tradeOrderDTO.getOrderId();
		boolean settlementAssureFlg= false;		//2016-04-25

		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			settlementCurrencyCode = settlementOrder
					.getSettlementCurrencyCode();
			if (settlementOrder.getSettlementFlg() == 1) {
				settlementFlg = true;
				partnerSettlementRate = settlementOrder.getSettlementRate();
			}
			assureAmount += settlementOrder.getAssureAmount();
			//if (null != settlementOrder.getFee()) {
			//	fee += settlementOrder.getFee();
			//}
			settlementAmount += settlementOrder.getAmount();
		
			if(settlementOrder.getAssureSettlementFlg()==1){		//0,4 都认为是false 
				settlementAssureFlg = true;
			}
		}
		*/
		
		Long orderAmount = tradeOrderDTO.getOrderAmount();
		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		
	
		RefundOrderExtendDTO roeDTO = readRefundOrderExtend(Long.valueOf(refundOrderNo));
		
		//step3 产生各种分录
		if( doAccounting_afterRefund(responseCode,roeDTO,channelOrderDTO,
				tradeOrderDTO, refundOrderDTO, settlementOrders,
				refundOrderNo,refundAmount,orderAmount, partnerId)==false){
			logger.info("doAccounting_afterRefund failed");
			return ;
		}
								
		//step 4: 更新清算订单的值
		updateSettlemeOrder_afterRefund(refundOrderStatusChangeLogDTO,roeDTO,refundOrderNo,settlementOrders,responseCode,refundAmount);
		
		//step5: 更新退款订单和网关订单的状态 这个代码是从上面的代码中挪出来的， 主要是让业务逻辑清晰一点
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.COMPLETED.getCode());
			//add by sch 2016-05-26
			if(tradeOrderDTO.getOverRefundAmount() != null){
				tradeOrderDTO.setOverRefundAmount(tradeOrderDTO.getOverRefundAmount() + refundAmount );
			}
			if(tradeOrderDTO.getDoingRefundAmount()!=null){
				tradeOrderDTO.setDoingRefundAmount(tradeOrderDTO.getDoingRefundAmount() - refundAmount);
			}
			//end 2016-05-26
			refundOrderDTO.setStatus(RefundStatusEnum.SUCCESS.getCode() + "");
			
		
		}
		else{
			// 增加可退金额
			tradeOrderDTO.setRefundAmount(tradeOrderDTO.getRefundAmount()+refundAmount);		
			//add by sch 2016-05-26
			if(tradeOrderDTO.getDoingRefundAmount() != null){
				tradeOrderDTO.setDoingRefundAmount(tradeOrderDTO.getDoingRefundAmount() - refundAmount);
			}
			

			//logTradeOrderModify(Long.valueOf(refundOrderNo),tradeOrderDTO.getTradeOrderNo(),
			//		refundAmount,"refund failed");		//add by sch 2016-04-22

			//这里要区分是正常的失败，还是被Excption捕获的非正常失败
			if(ResponseCodeEnum.UNDEFINED_ERROR.getCode().equals(responseCode)){
				refundOrderDTO.setStatus(RefundStatusEnum.TIMEOUT.getCode() + "");
				
				refundOrderStatusChangeLogDTO.setNewStatus(Long.valueOf(RefundStatusEnum.TIMEOUT.getCode()));
				
			}else{
				refundOrderDTO.setStatus(RefundStatusEnum.FAIL.getCode() + "");				
			}
		}
		setChannelReturnNo(refundOrderDTO,refundResult); //2016-07-22
		refundOrderDTO.setUpdateDate(new Date());
		refundOrderDTO.setComplateDate(new Date());
		
		//更新网关订单的值: note by sch 2016-05-26 这个函数最好改掉，因为只需要修改  几个字段的值，没有必要全部字段更新
		refundOrderService.updateRefundOrderByPkRnTx(refundOrderDTO);

		//更新网关订单的值: note by sch 2016-05-26 这个函数最好改掉，因为只需要修改  几个字段的值，没有必要全部字段更新
		tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
		
		//记录日志
		logRefundOrderStatusChangeLog(refundOrderStatusChangeLogDTO);
		
		//构造一个退款手续费订单
		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			String settlementCurrencyCode = settlementOrder.getSettlementCurrencyCode();
			createRefundFeeOrder(refundOrderDTO, tradeOrderDTO, settlementCurrencyCode);
			break;		//最多只做一次
		}
		
		//step 6:  发送退款通知
		logger.info("发送商户退款通知！--------------------------------------------------------------------------------"
				+ refundOrderDTO.getRefundOrderNo());

		notifyMerchant(refundOrderDTO, responseMsg);
	}
	
	//===
	// 记帐
	// 输入参数： roeDTO 可能是空的
	// 返回值，如果false，建议外面的流程，就不要继续走下去了。 
	//==
	
	private boolean doAccounting_afterRefund(String responseCode,RefundOrderExtendDTO roeDTO,ChannelOrderDTO channelOrderDTO,
			TradeOrderDTO tradeOrderDTO,
			RefundOrderDTO refundOrderDTO, List<PartnerSettlementOrder> settlementOrders,
			String refundOrderNo,long refundAmount,long orderAmount,Long partnerId){
				
		
		String orderId = refundOrderDTO.getRefundOrderNo() + "";
		//Long amount = refundOrderDTO.getRefundAmount();  //这个变量有歧义，在成功和失败的情况下，币种是不一样的，所以，去掉这个变量 
		String orgCode = channelOrderDTO.getOrgCode();
		//String channelSettlementCurrencyCode = channelOrderDTO.getSettlementCurrencyCode();

		String settlementCurrencyCode = "";
		boolean settlementFlg = false;
		String partnerSettlementRate = "1";
		long assureAmount = 0;
		long settlementAmount = 0;
		String merchantOrderId=tradeOrderDTO.getOrderId();
		boolean settlementAssureFlg= false;		//2016-04-25

		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			settlementCurrencyCode = settlementOrder
					.getSettlementCurrencyCode();
			if (settlementOrder.getSettlementFlg() == 1) {
				settlementFlg = true;
				partnerSettlementRate = settlementOrder.getSettlementRate();
			}
			assureAmount += settlementOrder.getAssureAmount();
			//if (null != settlementOrder.getFee()) {
			//	fee += settlementOrder.getFee();
			//}
			settlementAmount += settlementOrder.getAmount();
		
			if(settlementOrder.getAssureSettlementFlg()==1){		//0,4 都认为是false 
				settlementAssureFlg = true;
			}
		}
		
		logger.info("退款结果处理:退款订单号=["+refundOrderNo+"],settlementFlg=["+settlementFlg +"]");
		
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
			try {
				//这里最好也比较一下 标记位
				if(roeDTO != null){
					
				}
				// 未清算
				if (!settlementFlg) {	
					logger.info("退款结果处理: 退款成功 未清算订单: 开始 do500_501_503;refundAmount=["+refundAmount+"]refundOrderId=["+
								orderId+"]channelOrderDTO.getTransferRate()=["+channelOrderDTO.getTransferRate()+"]" + "orgCode=[" 
							    + orgCode + "]");							    
					
					doAccounting500_500(refundOrderDTO, channelOrderDTO,merchantOrderId);
					doAccounting_500_501(orgCode, orderId, refundAmount,
							channelOrderDTO.getCurrencyCode(),merchantOrderId);
					doAccounting_500_502(orgCode, orderId, refundAmount,
							channelOrderDTO.getTransferCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);
					
					logger.info("退款结果处理: 退款成功 未清算订单 end do500_501_503");
					
				} else{// 商户已清算					
					//这个时候，最好和退款订单中的状态作一次比较，这样，比较容易处理	
					logger.info("退款结果处理: 退款成功 已清算订单: 开始 do508_509_510;refundAmount=["+refundAmount+"]refundOrderId=["+
							orderId+"]channelOrderDTO.getTransferRate()=["+channelOrderDTO.getTransferRate()+"]" + "orgCode=[" 
						    + orgCode + "]");	
									
					doAccounting_500_508(orgCode, orderId,
							refundOrderDTO.getRefundAmount(),
							channelOrderDTO.getCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);
					
					//前面可能修改了  这个 渠道支付金额，（减少了一分钱，所以，这里这样记账，是否会有问题？ note by sch 2016-05-18) 
					doAccounting_500_509(orgCode, orderId, refundAmount,
							channelOrderDTO.getCurrencyCode(),merchantOrderId);
					doAccounting_500_510(orgCode, orderId, refundAmount,
							channelOrderDTO.getTransferCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);
					
					logger.info("退款结果处理:退款成功已清算订单 end do508_509_510");		
					
					//如果成功的话，做一笔511的记账 
					if(roeDTO !=null){
						Long smallOrderFixedfeeAmount = roeDTO.getSmallOrderFixedfeeAmount();
						Long smallOrderFixedfeeDelta = roeDTO.getSmallOrderFixedfeeDelta();
						if( (smallOrderFixedfeeAmount!= null ) &&
							(smallOrderFixedfeeAmount >0) && 
							(smallOrderFixedfeeDelta != null) &&
							(smallOrderFixedfeeDelta >0) )
						{
							
							logger.info("518 记账 分录  smallOrderFixedFeeDelta=[" + smallOrderFixedfeeDelta +"]");
							doAccounting_500_518(partnerId, orderId,	settlementCurrencyCode,
									 smallOrderFixedfeeDelta, merchantOrderId) ;
							
						}
						
						//更新RefundOrderExtend
						updateRefundOrderExtendCount(roeDTO);
					}	
				}
			} catch (Exception e) {
				logger.error("refund erro:" + refundOrderNo, e);
			}
			
		} else {
			// 退款失败
			if(roeDTO == null)
			{
				//系统中老的订单,老的处理方式  
				//if (channelOrderDTO.getReconciliationFlg() == 1) {// 渠道已对账
								
				return doAccounting_failed_afterRefund_Version1(											
							channelOrderDTO, refundOrderDTO,
							refundOrderNo,  orderId,  settlementFlg,
							settlementAssureFlg,  assureAmount,
							settlementAmount,  partnerSettlementRate,
							settlementCurrencyCode,  merchantOrderId,
							partnerId, refundAmount,orderAmount);
								
				
			}
			else{
				
				return doAccounting_failed_afterRefund_Version2(roeDTO,channelOrderDTO, refundOrderDTO,
						refundOrderNo,  orderId,  settlementFlg,
						settlementAssureFlg,  assureAmount,
						settlementAmount,  partnerSettlementRate,
						settlementCurrencyCode,  merchantOrderId,
						partnerId, refundAmount,orderAmount);
				
			}
		}
		return true;
	}
	
	//====
	//处理失败时的情况,这是最老的版本的情况
	//refundOrerNo,orderId 实际上是一样的 
	//====
	private boolean doAccounting_failed_afterRefund_Version1(
			ChannelOrderDTO channelOrderDTO, RefundOrderDTO refundOrderDTO,
			String refundOrderNo, String orderId, boolean settlementFlg,
			boolean settlementAssureFlg, long assureAmount,
			long settlementAmount, String partnerSettlementRate,
			String settlementCurrencyCode, String merchantOrderId,
			Long partnerId, long refundAmount, long orderAmount) {

		try {
			if (settlementFlg) {// 商户已清算
				// 这个时候，因为要还清回去，所以最好和退款订单中的状态作一次
				logger.info("退款结果处理:退款失败 已清算订单，refundAmount=[" + refundAmount
						+ "],orderAmount=[" + orderAmount + "],assureAmount=["
						+ assureAmount + "],partnerSettlementRate=["
						+ partnerSettlementRate + "],settlementAmount=["
						+ settlementAmount + "]");

				doAccounting_500_512(orderId, refundAmount,
						channelOrderDTO.getCurrencyCode(),
						partnerSettlementRate, merchantOrderId);
				doAccounting_500_513(orderId, refundAmount,
						channelOrderDTO.getCurrencyCode(), merchantOrderId);

				// 获取应该归还到保证金户头的保证金数，单位：交易币种
				long assureAmount_515 = getAssureAmount_refundFailed(
						POLICY_VERSION_FIRST, refundOrderNo, assureAmount,
						settlementFlg, settlementAssureFlg, orderAmount,
						refundAmount);

				if (refundAmount < assureAmount_515) {
					// 可能会产生一个很大的值, 所以,这里需要拒绝 这个报文
					logger.error("退款结果处理:退款失败 已清算订单 refundAmount=[ "
							+ refundAmount + "]< assureAmount=[" + assureAmount
							+ "]");
					return false;
				}

				// 先退还保证金，只有全额退款时才扣了保证金 。 上面的assureAmount重新计算会对这个流程产生影响
				if (assureAmount_515 > 0) {
					doAccounting_500_515(partnerId, orderId, assureAmount_515,
							settlementCurrencyCode, partnerSettlementRate,
							merchantOrderId);
				}

				long amount_514 = refundAmount - assureAmount_515; // 给基本户退的钱
																	// ，币种为交易币种
				long fee = refundOrderDTO.getPayerFee(); // 手续费，比种为　清算币种
				long amountfee = calcRefundFee(settlementAmount, refundAmount,
						fee); // 计算手续费的比例

				logger.info("退款结果处理:退款失败 已清算订单 开始退基本账户！ refundOrderNo=["
						+ refundOrderNo + "]," + "refundAmount=["
						+ refundAmount + "],assureAmount_515=["
						+ assureAmount_515 + "]+ amountFee=[" + amountfee + "]");

				// 这个函数和doAccount_500_512 有所不同
				doAccounting_500_514_2(partnerId, orderId, amount_514,
						settlementCurrencyCode, partnerSettlementRate,
						amountfee, merchantOrderId);

				logger.info("退款结果处理:退款失败 已清算订单结束，doAccounting 512_513_515_514");
			}
		}

		catch (Exception e) {
			logger.error("refund erro:" + refundOrderNo, e);
		}

		return true;
	}
	//========
	//
	//========
		
	private boolean doAccounting_failed_afterRefund_Version2(
			RefundOrderExtendDTO roeDTO, ChannelOrderDTO channelOrderDTO,
			RefundOrderDTO refundOrderDTO, String refundOrderNo,
			String orderId, boolean settlementFlg, boolean settlementAssureFlg,
			long assureAmount, long settlementAmount,
			String partnerSettlementRate, String settlementCurrencyCode,
			String merchantOrderId, Long partnerId, long refundAmount,
			long orderAmount) {

		try {
			//直接根据 roeDTO 里面的值，来进行恢复 
			if(settlementFlg && (roeDTO.getSettlementFlg() !=1))
			{
				logger.info("settlemngFlg but roeDTO.getSettlementFlg ()!=1 ");
				
				return true;
			}
			
			if (settlementFlg) {// 商户已清算
	
				logger.info("退款结果处理:退款失败 已清算订单，refundAmount=[" + refundAmount
						+ "],orderAmount=[" + orderAmount + "],assureAmount=["
						+ assureAmount + "],partnerSettlementRate=["
						+ partnerSettlementRate + "],settlementAmount=["
						+ settlementAmount + "]");

				doAccounting_500_512(orderId, refundAmount,
						channelOrderDTO.getCurrencyCode(),
						partnerSettlementRate, merchantOrderId);
				doAccounting_500_513(orderId, refundAmount,
						channelOrderDTO.getCurrencyCode(), merchantOrderId);

				// 获取应该归还到保证金户头的保证金数，单位：交易币种
				long assureAmount_515 = roeDTO.getAssureAcctDelta();	
				long amount_514 = roeDTO.getBaseAcctDelta(); // 给基本户退的钱
				
				if ((refundAmount < assureAmount_515) || (refundAmount < amount_514)) {
					// 可能会产生一个很大的值, 所以,这里需要拒绝 这个报文
					logger.error("退款结果处理:退款失败 已清算订单 refundAmount=[ "
							+ refundAmount + "]< assureAmount=[" + assureAmount
							+ "]");
					return false;
				}
				
				/*
				if(amount_514 + assureAmount_515 != refundAmount){
					//可能会出错
					logger.error("退款结果处理:退款失败 已清算订单 refundAmount=[ "
							+ refundAmount + "] < assureAmount=[" + assureAmount
							+ "]");
					return false;					
				}
				*/
				
				// 先退还保证金，只有全额退款时才扣了保证金 。 上面的assureAmount重新计算会对这个流程产生影响
				if (assureAmount_515 > 0) {
					doAccounting_500_515(partnerId, orderId, assureAmount_515,
							settlementCurrencyCode, partnerSettlementRate,
							merchantOrderId);
				}
				
				long amountfee = roeDTO.getFeeDelta(); // 计算手续费的比例
				
				logger.info("退款结果处理:退款失败 已清算订单 开始退基本账户！ refundOrderNo=["
						+ refundOrderNo + "]," + "refundAmount=["
						+ refundAmount + "],assureAmount_515=["
						+ assureAmount_515 + "]+ amountFee=[" + amountfee + "]");

				// 这个函数和doAccount_500_512 有所不同
				doAccounting_500_514_2(partnerId, orderId, amount_514,
						settlementCurrencyCode, partnerSettlementRate,
						amountfee, merchantOrderId);

				//end 2016-05-03				
				logger.info("退款结果处理:退款失败 已清算订单结束，doAccounting 512_513_515)514");
				
			}
		}

		catch (Exception e) {
			logger.error("refund erro:" + refundOrderNo, e);
		}

		return true;
	}


	//========
	//在处理退款订单的成功失败之后，更新清算订单的一些状态
	// 输入参数：
	//    roeDTO :      暂时还用不到
	//    refundAmount: 退款金额（以 refundOrder中的退款金额）
	// 输出参数：
	//    refundOrderStatusChangeLogDTO
	//清算订单中有三个地方，需要更新
	//(1)清算标志
	//(2)保证金归还标志
	//(3)清算余额

	//========
	private void updateSettlemeOrder_afterRefund(
			RefundOrderStatusChangeLogDTO outRoscDTO,
			RefundOrderExtendDTO roeDTO,String refundOrderNo,
			List<PartnerSettlementOrder> settlementOrders,String responseCode,long refundAmount){
		
		logger.info("updateSettlemeOrder_afterRefund begin");
		
			
		//根据业务逻辑，我们先根据返回码，分为成功和失败, 分别进行更新
		if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){				
			//成功 结果，只需要 看情况更新 清算标志 
			
			for(PartnerSettlementOrder settlementOrder : settlementOrders){
				
				int modifyFlg = 0;
				
				//如果是全额退款的订单，还需要把状态改成 未清算状态么？ 2016-07-05 根据 配置来
				if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()){
					
					//除了这一张订单外，没有其他的未完成状态的订单
					boolean hasNoUncompleteRefundOrder = checkNoUncompleteRefundOrder(settlementOrder.getTradeOrderNo(),Long.valueOf(refundOrderNo));				
					if (hasNoUncompleteRefundOrder){
	
						//金额大于0 的 
						if(settlementOrder.getAmount().longValue()>0){
					
							// 记录日志信息
							outRoscDTO.setOldSettlementFlg(Long.valueOf(settlementOrder.getSettlementFlg()));
							outRoscDTO.setNewSettlementFlg(Long.valueOf(SettlementFlagEnum.UNSETTLEMENT.getCode()));
							modifyFlg |= SETTLEMENTORDER_MDY_FLG;

							settlementOrder.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());

							// 如果订单状态是未清算状态，则修改它的清算时间 2016-04-28
							java.util.Date settleDate = settlementOrder.getSettlementDate();
							if (settleDate.before(Calendar.getInstance().getTime())) // 如果还没到清算时间，则不管它
							{
								// 记录日志信息
								outRoscDTO.setOldSettlementDate(settleDate);
								outRoscDTO.setNewSettlementDate(Calendar.getInstance().getTime());
								modifyFlg |= SETTLEMENTORDER_MDY_DATE;

								settlementOrder.setSettlementDate(Calendar.getInstance().getTime());
								logger.info("修改订单的清算时间 old=" + settleDate	+ "new date=" + settlementOrder.getSettlementDate());
							}
						}
						else {		 
							//如果是全额退款的订单，还需要把状态改成 未清算状态么？ 2016-07-05 根据 配置来						
							if(bSetUnsettlement_RefundAll_Success)
							{
								logger.info("清算金额为0的清算订单，修改状态为未清算");
								outRoscDTO.setOldSettlementFlg(Long.valueOf(settlementOrder.getSettlementFlg()));
								outRoscDTO.setNewSettlementFlg(Long.valueOf(SettlementFlagEnum.UNSETTLEMENT.getCode()));
								modifyFlg |= SETTLEMENTORDER_MDY_FLG;

								settlementOrder.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());

								// 如果订单状态是未清算状态，则修改它的清算时间 
								java.util.Date settleDate = settlementOrder.getSettlementDate();
								if (settleDate.before(Calendar.getInstance().getTime())) // 如果还没到清算时间，则不管它
								{
									//记录日志信息
									outRoscDTO.setOldSettlementDate(settleDate);
									outRoscDTO.setNewSettlementDate(Calendar.getInstance().getTime());
									modifyFlg |= SETTLEMENTORDER_MDY_DATE;

									settlementOrder.setSettlementDate(Calendar.getInstance().getTime());
									logger.info("修改订单的清算时间 old=" + settleDate	+ "new date=" + settlementOrder.getSettlementDate());
								}
							}
						}
					}	
				
				}	
				
			
				if(modifyFlg > 0 )
				{
					partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);	
					outRoscDTO.setSettlementModifyFlg(Long.valueOf(modifyFlg));
				}		
			}
		}
		else
		{
			//退款失败，以上三个业务，可能都需要更新
			for(PartnerSettlementOrder settlementOrder : settlementOrders){
				
				int modifyFlg = 0;
				
				final int SETTLEMENTORDER_MDY_FLG = 1;
				final int SETTLEMENTORDER_MDY_AMOUNT = 2;
				final int SETTLEMENTORDER_MDY_ASSUREFLG = 4;
				final int SETTLEMENTORDER_MDY_DATE = 8;
				
				if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()){					
					outRoscDTO.setSettlementAmountDelta(refundAmount);
					modifyFlg |= SETTLEMENTORDER_MDY_AMOUNT;
					
					settlementOrder.setAmount(settlementOrder.getAmount()+refundAmount);		
				}
				
				//2015年11月7日11:41:12 peiyu.yang 未清算退款的，清算订单状态仍未清算退款,如果是部分退款原来的订单还是要清算的
				if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()&&
						settlementOrder.getAmount().longValue()>0){
					
					//除了这一张订单外，没有其他的未完成状态的订单
					boolean hasNoUncompleteRefundOrder = checkNoUncompleteRefundOrder(settlementOrder.getTradeOrderNo(),Long.valueOf(refundOrderNo));
					
					if (hasNoUncompleteRefundOrder) {
						outRoscDTO.setOldSettlementFlg(Long.valueOf(settlementOrder.getSettlementFlg()));
						outRoscDTO.setNewSettlementFlg(Long.valueOf(SettlementFlagEnum.UNSETTLEMENT.getCode()));
						modifyFlg |= SETTLEMENTORDER_MDY_FLG;
					
						settlementOrder.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());  
					
					
						//如果订单状态是未清算状态，则修改它的清算时间 2016-04-28
						java.util.Date settleDate = settlementOrder.getSettlementDate();
						if(settleDate.before(Calendar.getInstance().getTime()))    //如果还没到清算时间，则不管它
						{
						//记录日志信息
							outRoscDTO.setOldSettlementDate(settleDate);
							outRoscDTO.setNewSettlementDate(Calendar.getInstance().getTime());
							modifyFlg |= SETTLEMENTORDER_MDY_DATE;
						
							settlementOrder.setSettlementDate(Calendar.getInstance().getTime());
							logger.info("修改订单的清算时间 old=" + settleDate + "new date=" + settlementOrder.getSettlementDate());
						}
					}
				}
						
				//全额退款才会发生这个事情  2016-04-25
				if( (settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.REFUND.getCode() 
						|| settlementOrder.getAssureSettlementFlg()==3)				//因为原来是=3的原因 	 	
						&& refundAmount == settlementOrder.getOrderAmount() ){
					logger.info("把 AssureSettlementFlag from REFUND==>UNSETTLEMENT");
					
					//记录日志信息
					outRoscDTO.setOldSettlementFlg(Long.valueOf(settlementOrder.getAssureSettlementFlg()));
					outRoscDTO.setNewSettlementFlg(Long.valueOf(SettlementFlagEnum.UNSETTLEMENT.getCode()));
					modifyFlg |= SETTLEMENTORDER_MDY_ASSUREFLG;
					
					settlementOrder.setAssureSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());					
				}
												
				
				if(modifyFlg > 0 ){
					//失败 情况下  ,只有在原来是退款状态的情况下，才需要增加日志 
					partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);

					outRoscDTO.setSettlementModifyFlg(Long.valueOf(modifyFlg));
					//logSettlementOrderModify(Long.valueOf(refundOrderNo),settlementOrder.getId(),
					//		refundAmount,oldSettlementFlag,settlementOrder.getSettlementFlg(),"refund failed");					
				}
			}
		}
		
		logger.info("updateSettlemeOrder_afterRefund end");
	}
	
	
	private void sendToFo(PaymentOrderDTO paymentOrderDTO,
			ChannelOrderDTO channelOrderDTO, String transferRate,
			Long partnerId, Long refundAmount, Long refundOrderNo) {
		Map<String, String> refundMap = new HashMap();
		refundMap.put("depositOrderNo",
				String.valueOf(paymentOrderDTO.getPaymentOrderNo()));
		refundMap.put("refundOrderNo", String.valueOf(refundOrderNo));

		// 转换
		if (!StringUtil.isEmpty(transferRate)) {
			BigDecimal rAmount = new BigDecimal(refundAmount);
			rAmount.multiply(new BigDecimal(transferRate));
			refundMap.put("applyAmount", rAmount.toString());
		} else {
			refundMap.put("applyAmount", refundAmount.toString());
		}

		refundMap.put("memberCode", String.valueOf(partnerId));
		refundMap.put("memberAccType",
				String.valueOf(paymentOrderDTO.getPayeeType()));
		refundMap.put("memberType",
				String.valueOf(MemberTypeEnum.MERCHANT.getCode()));
		refundMap.put("bankSerialNo",
				String.valueOf(channelOrderDTO.getChannelOrderNo()));
		refundMap.put("bankOrderId",
				String.valueOf(channelOrderDTO.getChannelOrderNo()));
		refundMap.put("depositAmount",
				String.valueOf(channelOrderDTO.getAmount()));
		refundMap.put("applyMax", String.valueOf(channelOrderDTO.getAmount()));
		refundMap.put("depositDate", DateUtil.formatDate(
				channelOrderDTO.getCompleteDate(), "yyyyMMddHHmmss"));
		refundMap.put("bankChannel", channelOrderDTO.getOrgCode());

		jmsSender.send("fo.customRefund.fiTofo.req",
				JSonUtil.toJSonString(refundMap));
	}

	//====
	//计算退款金额,全额退款肯定没有问题
	//=====
	private long calcTransAmount(String transferRate,Long refundAmount){
		long transferAmount = new BigDecimal(refundAmount).multiply(new BigDecimal(transferRate)).longValue();
		int flg=0;
		if(!String.valueOf(transferAmount).endsWith("0")){
			flg=1;
		}
		transferAmount = transferAmount/10;
		if(flg==1){
			return (transferAmount * 10+10);
		}
		
		return (transferAmount * 10);		
	}
	
	
	private RefundOrderDTO buildRefundOrderInfo(
			RefundTransactionServiceParamDTO paramDTO,String transferRate) {
		RefundOrderDTO refundOrderDTO = new RefundOrderDTO();

		refundOrderDTO.setCreateDate(new Date());
		refundOrderDTO.setUpdateDate(new Date());
		refundOrderDTO.setBgUrl(paramDTO.getNoticeUrl().trim());
		refundOrderDTO.setPartnerId(paramDTO.getPartnerId().trim());
		refundOrderDTO.setPartnerRefundOrderId(paramDTO.getRefundOrderId()
				.trim());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			refundOrderDTO.setPartnerRefundTime(fmt.parse(paramDTO
					.getRefundTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		refundOrderDTO.setRefundObjType(paramDTO.getDestType().trim());
		refundOrderDTO.setRefundType(paramDTO.getRefundType().trim());
		refundOrderDTO.setTradeOrderNo(paramDTO.getTradeOrderDTO()
				.getTradeOrderNo());
		refundOrderDTO.setPaymentOrderNo(paramDTO.getPaymentOrderDTO()
				.getPaymentOrderNo());

		Long refundAmount = calcRefundAmount(paramDTO.getRefundType(),
				Long.valueOf(paramDTO.getRefundAmount()), paramDTO
						.getPaymentOrderDTO().getPaymentAmount());
		refundOrderDTO.setRefundAmount(refundAmount);
		
		long transferAmount = calcTransAmount(transferRate,refundAmount);
		refundOrderDTO.setTransferAmount(transferAmount);
		
		refundOrderDTO
				.setStatus(String.valueOf(RefundStatusEnum.INIT.getCode()));
		refundOrderDTO.setErrorCode(paramDTO.getErrorCode());
		refundOrderDTO.setRemark(paramDTO.getRemark());
		refundOrderDTO.setDepositBackNo(0L);

		if (paramDTO.getRefundType().equals(
				RefundTypeEnum.RATE_REFUND.getStringCode())) {
			refundOrderDTO.setRefundValue(Long.valueOf(paramDTO
					.getRefundAmount()));
		}

		//获取orgCode 2016-07-22
		refundOrderDTO.setRefundOrgCode(paramDTO.getPaymentOrderDTO().getOrgCode()); //获取orgCode;
		
		return refundOrderDTO;
	}
	
	//===
	//2016-04-25 在原来的 buildRefundOrderInfo 基础上,增加了两个小判断
	//===
	private RefundOrderDTO buildRefundOrderInfo2(
			RefundTransactionServiceParamDTO paramDTO,String transferRate,TradeOrderDTO tradeOrderDTO) {
		
		RefundOrderDTO refundOrderDTO = buildRefundOrderInfo(paramDTO,transferRate);
		
		Long refundAmount = refundOrderDTO.getRefundAmount(); 
		
		String refundType = paramDTO.getRefundType();
		if (Integer.valueOf(refundType) == RefundTypeEnum.FULL_REFUND.getCode()
				&& refundAmount < tradeOrderDTO.getRefundAmount()) {
			logger.info("全额退款，金额 < 网关订单的金额，所以改为部分退款"); 
			refundOrderDTO.setRefundType(RefundTypeEnum.PART_REFUND.getCode() + "");
		}

		//add by sch 2016-04-25 如果是部分退款，但是金额=全额退款，则设置为全额退款 
		//注意：这里使用 getOrderAmount，和上面的是不一样的。 如果是第2个部分退款，依然是部分退款。 
		if (Integer.valueOf(refundType) == RefundTypeEnum.PART_REFUND.getCode()
				&& (refundAmount == tradeOrderDTO.getOrderAmount())) {
			logger.info("部分退款，金额=网关订单的金额，所以改为全额退款"); 
			refundOrderDTO.setRefundType(RefundTypeEnum.FULL_REFUND.getCode() +"");
		}
		//sch 2016-04-25
		logger.info("build return refundAmount=" + refundAmount + ",orderAmount=" + tradeOrderDTO.getOrderAmount() + 
				",trade.refundAmount=" + tradeOrderDTO.getRefundAmount());
		
		//2016-05-07 对于最后一次非全额退款的订单，进行一个金额的计算，因为有的时候，总计的退款订单的金额，加起来，会大于原订单金额 
		if( (refundAmount < tradeOrderDTO.getOrderAmount()) && (refundAmount.equals(tradeOrderDTO.getRefundAmount()))){
			
			long totalTransAmount  =  calcTransAmount(transferRate,tradeOrderDTO.getOrderAmount());		//总的退款金额,应该和渠道订单中是一致的			
			long successTransAmount = calcRefundOrderTransAmount(tradeOrderDTO.getTradeOrderNo());		//已经退款成功或者退款中的金额
			long thisTransAmount = refundOrderDTO.getTransferAmount();
			
			long delta = thisTransAmount + successTransAmount - totalTransAmount;
			if((delta >0) && (delta <100) && (thisTransAmount > delta )) { 	//0 - 100（10分钱） 之间,我们允许作一个调整 
				logger.info("NeedProcessDelta totalTransAmount = [" + totalTransAmount +"] successTransAmount=["
						+ successTransAmount + "] thisTransAmount=[" + thisTransAmount + "]delta=" + delta);
				
				refundOrderDTO.setTransferAmount(thisTransAmount - delta );
			}
			else {
				logger.info(" totalTransAmount = [" + totalTransAmount +"] successTransAmount=["
					+ successTransAmount + "] thisTransAmount=[" + thisTransAmount + "]delta=" + delta);
			}
		}
		else
		{
			//logger.info("build return not process");
			//logger.info("build return refundAmount=" + refundAmount + ",orderAmount=" + tradeOrderDTO.getOrderAmount() + 
			//		",trade.refundAmount=" + tradeOrderDTO.getRefundAmount());
		}
		
		return refundOrderDTO;
	}

	//对于按比例 扣款，计算扣款金额
	private Long calcRefundAmount(String refundType, Long refundAmount,
			Long paymnetAmount) {
		BigDecimal bPaymentAmount = new BigDecimal(paymnetAmount);
		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		BigDecimal rate = new BigDecimal(100000);

		if (RefundTypeEnum.RATE_REFUND.getStringCode().equals(refundType)) {
			// 付金支额 * 退款比例 / 10000[比例位数]
			return bPaymentAmount.multiply(bRefundAmount)
					.divide(rate, 0, BigDecimal.ROUND_DOWN)
					.multiply(new BigDecimal(10)).longValue();
		} else if (RefundTypeEnum.PART_REFUND.getStringCode()
				.equals(refundType)) {
			// *10到厘处理
			return bRefundAmount.multiply(new BigDecimal(10)).longValue();
		} else {
			// *10到厘处理
			return bRefundAmount.multiply(new BigDecimal(10)).longValue();
		}
	}

	//按比例计算 手续费
	private Long calcRefundFee(Long orderAmount, Long refundAmount, Long fee) {
		BigDecimal bPaymentAmount = new BigDecimal(orderAmount);
		BigDecimal bRefundAmount = new BigDecimal(refundAmount);
		BigDecimal bFee = new BigDecimal(fee);

		return bRefundAmount.divide(bPaymentAmount, 6, BigDecimal.ROUND_DOWN)
				.multiply(bFee).longValue();
	}

	public static void main(String[] args) {
		RefundServiceImpl impl = new RefundServiceImpl();

		Long orderAmount = 545000L;
		Long refundAmount = 345000L;
		Long fee = 19080L;

		int feeFlg = impl.getMerchantFeeFlg(Long.valueOf("10000003593"));
		System.out.println(feeFlg);
		
		System.out.println(impl.calcRefundFee(orderAmount, refundAmount, fee)*6.1966);
	}


	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}
	
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setRefundOrderExtendService(RefundOrderExtendService refundOrderExtendService){
		this.refundOrderExtendService = refundOrderExtendService;
	}
	
	public void setValidateService(
			RefundTransactionValidateService validateService) {
		this.validateService = validateService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public void setAccounting_500_500(AccountingService accounting_500_500) {
		this.accounting_500_500 = accounting_500_500;
	}

	public void setAccounting_500_501(AccountingService accounting_500_501) {
		this.accounting_500_501 = accounting_500_501;
	}

	public void setAccounting_500_502(AccountingService accounting_500_502) {
		this.accounting_500_502 = accounting_500_502;
	}

	public void setAccounting_500_503(AccountingService accounting_500_503) {
		this.accounting_500_503 = accounting_500_503;
	}

	public void setAccounting_500_504(AccountingService accounting_500_504) {
		this.accounting_500_504 = accounting_500_504;
	}

	public void setAccounting_500_505(AccountingService accounting_500_505) {
		this.accounting_500_505 = accounting_500_505;
	}

	public void setAccounting_500_506(AccountingService accounting_500_506) {
		this.accounting_500_506 = accounting_500_506;
	}

	public void setAccounting_500_507(AccountingService accounting_500_507) {
		this.accounting_500_507 = accounting_500_507;
	}

	public void setAccounting_500_508(AccountingService accounting_500_508) {
		this.accounting_500_508 = accounting_500_508;
	}

	public void setAccounting_500_509(AccountingService accounting_500_509) {
		this.accounting_500_509 = accounting_500_509;
	}

	public void setAccounting_500_510(AccountingService accounting_500_510) {
		this.accounting_500_510 = accounting_500_510;
	}

	public void setAccounting_500_511(AccountingService accounting_500_511) {
		this.accounting_500_511 = accounting_500_511;
	}

	public void setAccounting_500_512(AccountingService accounting_500_512) {
		this.accounting_500_512 = accounting_500_512;
	}

	public void setAccounting_500_513(AccountingService accounting_500_513) {
		this.accounting_500_513 = accounting_500_513;
	}

	public void setAccounting_500_514(AccountingService accounting_500_514) {
		this.accounting_500_514 = accounting_500_514;
	}

	public void setAccounting_500_515(AccountingService accounting_500_515) {
		this.accounting_500_515 = accounting_500_515;
	}

	public void setAccounting_500_518(AccountingService accounting_500_518) {
		this.accounting_500_518 = accounting_500_518;
	}
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setTradeDataSingnatureService(
			TradeDataSingnatureService tradeDataSingnatureService) {
		this.tradeDataSingnatureService = tradeDataSingnatureService;
	}

	public void setPartnerConfigService(
			PartnerConfigService partnerConfigService) {
		this.partnerConfigService = partnerConfigService;
	}
	
	public void setRefundOrderConfirmService(
			RefundOrderConfirmService refundOrderConfirmService) {
		this.refundOrderConfirmService = refundOrderConfirmService;
	}

	private void doAccounting500_500(RefundOrderDTO refundOrderDTO,
			ChannelOrderDTO channelOrderDTO,String merchantOrderId) {

		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(refundOrderDTO.getRefundAmount());
		accountingDto.setOrderId(refundOrderDTO.getRefundOrderNo());
		accountingDto.setPayerCurrencyCode(channelOrderDTO.getCurrencyCode());
		accountingDto.setPayeeCurrencyCode(channelOrderDTO.getCurrencyCode());
		accountingDto.setOrderAmount(refundOrderDTO.getRefundAmount());
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_500_500.doAccounting(accountingDto);
	}

	public void doAccounting_500_501(String orgCode, String orderId,
			Long amount, String currencyCode,String merchantOrderId) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_500_501.doAccounting(accountingDto);
	}

	public void doAccounting_500_502(String orgCode, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accounting_500_502.doAccounting(accountingDto);
	}

	public int doAccounting_500_503(Long partnerId, String orderId,
			Long refundAmount, String settlementCurrencyCode,
			String partnerSettlementRate, Long fee,String merchantOrderId) throws Exception {

		long aAmount = new BigDecimal(refundAmount).multiply(
				new BigDecimal(partnerSettlementRate)).longValue();

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());

		long amount = aAmount;
		if (null != fee && fee > 0) {
			accountingDto.setHasCaculatedPrice(true);
			accountingDto.setPayeeFee(fee);
			amount = aAmount - fee;
		}
		else {
			//add by sch 2016-05-18 : 如果不设置为true的话，底层代码，会使用 价格策略来产生一个 手续费分录.
			accountingDto.setHasCaculatedPrice(true);  
			accountingDto.setPayeeFee(Long.valueOf(0));	
		}

		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);

		return accounting_500_503.doAccountingReturn(accountingDto);
	}

	//扣款收续费,从基本户扣钱
	//输入参数： RefundFee : 退款手续费
	public int doAccounting_500_516(Long partnerId, String refundOrderId,
			Long refundAmount, String settlementCurrencyCode,
			Long efundFee,String merchantOrderId) throws Exception {

		logger.info("doAccounting_500_516" + partnerId + refundOrderId + refundAmount + settlementCurrencyCode + efundFee + merchantOrderId);
		return 1;
	}

	//扣款手续费，给基本户还钱 
	
	public int doAccounting_500_517(Long partnerId,String refundOrderId, Long refundFee,String settlementCurrencyCode,String merchantOrderId){
		logger.info("doAccounting_500_517" + partnerId + refundOrderId +  settlementCurrencyCode + refundFee + merchantOrderId);
		return 1;
	}
	

	
	public int doAccounting_500_504(Long partnerId, String orderId,
			Long amount, String currencyCode, String partnerSettlementRate,String merchantOrderId)
			throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				partnerSettlementRate));

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		Integer acctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_500_504.doAccountingReturn(accountingDto);
	}
	
	/*
	 * 新增加的518 记账 ，给账户加钱，返回手续费. 这个应该是和514,515 比较像 
	 */
	public int doAccounting_500_518(Long partnerId, String orderId,
			 String settlementCurrencyCode,
			 Long feeDelta,String merchantOrderId) throws Exception {

		long aAmount = feeDelta.longValue();

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayee(partnerId);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());

		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);

		return accounting_500_518.doAccountingReturn(accountingDto);
	}

	public void doAccounting_500_505(String orderId, Long amount,
			String currencyCode, String partnerSettlementRate,String merchantOrderId) throws Exception {

		long aAmount = new BigDecimal(amount).multiply(
				new BigDecimal(partnerSettlementRate)).longValue();

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		// accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_500_505.doAccounting(accountingDto);
	}

	public void doAccounting_500_506(String orderId, Long amount,
			String currencyCode, String partnerSettlementRate,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_506.doAccounting(accountingDto);
	}

	public void doAccounting_500_508(String orgCode, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();

		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);

		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_508.doAccounting(accountingDto);
	}

	public void doAccounting_500_509(String orgCode, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_509.doAccounting(accountingDto);
	}

	public void doAccounting_500_510(String orgCode, String orderId,
			Long amount, String currencyCode, String transferRate,String merchantOrderId)
			throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				transferRate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setBankCode(orgCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_510.doAccounting(accountingDto);
	}

	public void doAccounting_500_512(String orderId, Long amount,
			String currencyCode,String partnerSettlementRate,String merchantOrderId) throws Exception {
//add by zhangmingmig 退款失败货币转换记账要乘以汇率
		long aAmount = new BigDecimal(amount).multiply(
				new BigDecimal(partnerSettlementRate)).longValue();
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_512.doAccounting(accountingDto);
	}

	public void doAccounting_500_513(String orderId, Long amount,
			String currencyCode,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_500_513.doAccounting(accountingDto);
	}

	public int doAccounting_500_515(Long partnerId, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		Integer acctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctType);
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_500_515.doAccountingReturn(accountingDto);
	}

	public int doAccounting_500_514(Long partnerId, String orderId,Long orderAmount,Long settlementAmount,Long assureAmount,
			Long refundAmount, String settlementCurrencyCode,
			String rate, Long fee,String merchantOrderId) throws Exception {
		
		long aRefundAmount = refundAmount;
		//全额退款时，退回清算账户钱要减去保证金
		if(orderAmount.longValue() == refundAmount){
			refundAmount = refundAmount - assureAmount;
		}
		long aAmount = new BigDecimal(refundAmount).multiply(
				new BigDecimal(rate)).longValue();

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
		
		long amountFee = calcRefundFee(settlementAmount, aRefundAmount, fee);		//这个还有必要进行这种计算么　？
		accountingDto.setHasCaculatedPrice(true);
		// 第一次结算全部收取手续费用
		long amount = aAmount;
		if (amountFee > 0) {
			accountingDto.setPayeeFee(amountFee);
			amount = aAmount - amountFee;
		}

		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);

		return accounting_500_514.doAccountingReturn(accountingDto);
	}
	
	//=============
	//和上面的函数相似,但是函数里面不再进行业务计算,只进行汇率计算 
	//输入参数: 
	// partnerId:     商户Id
	// refundOrderId: 退款OrderId
	// orderAmount:	  traderOrder.Amount　　交易币种
	// baseAcctAmount:    需要规划到基本户的金额(可能已经减掉保证金数量了)　交易币种
	// Long amountFee:　　　手续费，已经是清算币种　
	//=============
	public int doAccounting_500_514_2(Long partnerId, String refundOrderId,Long baseAcctAmount,
			String settlementCurrencyCode,
			String rate, Long amountFee,String merchantOrderId) throws Exception {
		
		//long aRefundAmount = refundAmount;
		long aAmount = new BigDecimal(baseAcctAmount).multiply(
				new BigDecimal(rate)).longValue();

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(refundOrderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
				
		accountingDto.setHasCaculatedPrice(true);
		
		long amount = aAmount;
		if (amountFee > 0) {
			accountingDto.setPayeeFee(amountFee);
			amount = aAmount - amountFee;
		}
		else{
			//这里最好也加上 手续费  add by sch 2016-05-30
			accountingDto.setPayeeFee(Long.valueOf(0));		//否则的话	
		}
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);

		return accounting_500_514.doAccountingReturn(accountingDto);
	}
	

	private void notifyMerchant(RefundOrderDTO refundOrder, String responseMsg) {

		TradeOrderDTO tradeOrderDTO = tradeOrderService
				.queryTradeOrderById(refundOrder.getTradeOrderNo());
		RefundResultNoticeDTO noticeDTO = new RefundResultNoticeDTO();
		try {
			String charsetType = "1";
			String signType = "2";
			noticeDTO.setCharset(charsetType);
			noticeDTO.setCompleteTime(DateUtil.formatDate(
					refundOrder.getComplateDate(), "yyyyMMddHHmmss"));
			noticeDTO.setDealId(refundOrder.getRefundOrderNo().toString());
			noticeDTO.setOrderId(tradeOrderDTO.getOrderId());
			noticeDTO.setPartnerId(refundOrder.getPartnerId());
			noticeDTO.setRefundAmount(String.valueOf(refundOrder
					.getRefundAmount() / 10));
			noticeDTO.setRefundOrderId(refundOrder.getPartnerRefundOrderId());
			noticeDTO.setRefundTime(DateUtil.formatDate(
					refundOrder.getPartnerRefundTime(), "yyyyMMddHHmmss"));
			noticeDTO.setRemark(refundOrder.getRemark());
			noticeDTO.setResultCode(refundOrder.getStatus());
			noticeDTO.setResultMsg(responseMsg);
			noticeDTO.setSignType(signType);

			PartnerConfig partnerConfig = partnerConfigService.findConfig(
					tradeOrderDTO.getPartnerId(), "code1");
			String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
					noticeDTO.generateSign(), signType, charsetType,
					partnerConfig.getValue());

			noticeDTO.setSignMsg(signMsg);

			HttpNotifyRequest refundNotify = new HttpNotifyRequest();

			Map data = MapUtil.bean2map(noticeDTO);
			data.put("url", refundOrder.getBgUrl());

			logger.info("notify parater:" + data);

			refundNotify.setData(data);
			refundNotify.setUrl(refundOrder.getBgUrl());
			refundNotify.setTemplateId(1103);
			refundNotify.setType(RequestType.HTTP);
			jmsSender.send(refundNotify);
		} catch (Exception e) {
			logger.error("refund notify error:", e);
		}
	}

	
	
	//检查帐户余额是否足够扣款
	//会抛出异常
	//输入参数： refundAmount*settlementRate 属于 清算币种
	//       fee 	:   清算时产生的手续费
	//       refundFee: 退款手续费,已经是清算币种
	private void checkAcctBalanceEnough(String settlementCurrencyCode,Long partnerId,long refundAmount,String settlementRate, long fee,long refundFee) 
			throws Exception{
		
		long aAmount = new BigDecimal(refundAmount).multiply(
				new BigDecimal(settlementRate)).longValue();
		long amount = aAmount;
		if (fee > 0) {
			amount = aAmount - fee;
		}
		
		//add by sch 2016-05-03
		if(refundFee>0){
			amount += refundFee;
		}
		//end 2016-05-03
		
		Integer acctType = AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(partnerId, acctType);
		if(acctAttribDto!=null && acctAttribDto.getAcctCode()!=null){	
			AcctDto acctDto=acctService.queryAcctByAcctCode(acctAttribDto.getAcctCode());
			if(amount>acctDto.getBalance()){
				logger.info("退款，账户余额不够 " + amount + acctDto.getBalance());
				
				throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户["
							+ acctAttribDto.getAcctCode() + "]余额不足");
			}
		}
	}
	
	//========
	//商户退款申请,该订单已经被结算的状态,版本1  这是2016-04-29 改造之前的版本 
	//特点：手续费 （固定手续费+百分比手续费） 按照比例退给商户。    全额退款  已归还保证金的清算订单，也继续扣除保证金户.  
	//只处理单张退款订单的状态
	//输入参数  :
	//      settlementOrder        退款订单  肯定不是空，调用者已经检查过了
	//      assureAmount_settlement_order 清算订单中的保证金数量
	//      fee_settlement_order:  清算订单中的手续费,这个是清算币种
	//输出参数： outRoeDTO
	//========
	private void doMerchantRefundApplyAccounting_VERSION1(ChannelOrderDTO channelOrder,
			TradeOrderDTO tradeOrderDTO, RefundOrderDTO refundOrderDTO,
			PartnerSettlementOrder settlementOrder,
			long assureAmount_settlement_order, String settlementCurrencyCode,
			long settlementAmount, String settlementRate, long fee_settlement_order,
			String merchantOrderId,RefundOrderExtendDTO outRoeDTO) throws Exception {

		Long orderAmount = tradeOrderDTO.getOrderAmount();
		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		String refundOrderId = refundOrderDTO.getRefundOrderNo().toString();

		int policyVersion = POLICY_VERSION_FIRST;
		int feeFlg = FEE_FLG_RETURN_PERPART_FIXED;
		
		// 如果是全额退款
		if (refundAmount == orderAmount) {
			logger.info("退款-已清算全额退款处理开始: 退款订单号=[" + refundOrderId +"]");

			//(1)计算需要的扣除的保证金 ：　 如果保证金已经归还，则全部改为扣除基本户
			long assureAmount = getAssureAmount_refundRequest(policyVersion,refundOrderId,assureAmount_settlement_order,
					settlementOrder.getAssureSettlementFlg(),orderAmount, refundAmount);	
			
			
			//(2) 判断基本本户余额是否够扣款，如果不够就不扣款
			checkAcctBalanceEnough(settlementCurrencyCode, partnerId, refundAmount,settlementRate,fee_settlement_order,0);

			// 设置 如果assureAmount ==0 ,下面的语句也没啥影响
			if (assureAmount > 0) { //=0 的话，下面的语句根本也没有必要去执行了
				int assureAccountingFlg = doAccounting_500_504(partnerId,
						refundOrderId, assureAmount, settlementCurrencyCode,
						settlementRate, merchantOrderId);

				// 置保证金已退款
				if (assureAccountingFlg == 1) {
					if (settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.UNSETTLEMENT.getCode()) { // =0 才会把它变到4
						settlementOrder.setAssureSettlementFlg(SettlementFlagEnum.REFUND.getCode());
						partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
					}
				}
			}
			// 扣基本账户
			// note by sch : (refundAmount - assureAmount). 如果assureAmount
			// 被重新设置为0，则下面的字段也是对的
			int basicAccountingFlg = doAccounting_500_503(partnerId, refundOrderId,
					refundAmount - assureAmount, settlementCurrencyCode,
					settlementRate, fee_settlement_order, merchantOrderId);
			
			logger.info("退款-已清算全额退款处理结束  退款订单号=[" + refundOrderId +"]" + ",refundAmount=[" + refundAmount 
					+ "],assureAmount=[" + assureAmount + "]");
			
			refundOrderExtendSetValue(outRoeDTO,refundAmount - assureAmount , assureAmount, fee_settlement_order, 
					feeFlg, policyVersion, REFUND_FLG_SETTLEMENTED_ALL,0);
			
		} else { // 部分退款，不退保证金，全部由基本户出

			logger.info("退款-已清算部分退款处理,退款订单号=["
					+ refundOrderId + "],settlementAmount=[" + settlementAmount + "],refundAmount=["
					+ refundAmount +"]");

			if (refundAmount <= settlementAmount) {
				//如果这里也采取 
				
				// 计算手续费比例
				long fee = calcRefundFee(settlementOrder.getAmount(), refundAmount,fee_settlement_order);

				// 检查一些帐户余额，如果余额不够，返回的错误码，会更加合适
				checkAcctBalanceEnough(settlementCurrencyCode, partnerId,refundAmount,settlementRate,fee,0);

				doAccounting_500_503(partnerId, refundOrderId, refundAmount,
						settlementCurrencyCode, settlementRate, fee,
						merchantOrderId);
				
				logger.info("退款-已清算部分退款处理结束　fee=["+ fee+"]"); 
								
				refundOrderExtendSetValue(outRoeDTO,refundAmount , 0,  fee, 
						feeFlg, policyVersion, REFUND_FLG_SETTLEMENTED_PART,0);
				
			} else {
				logger.info("退款-已清算部分退款处理异常,退款金额大于清算清算金额");
				throw new AppException("退款异常！");
			}
		}

		logger.info("货币兑换记账处理 开始；doAccounting_500_505；doAccounting_500_506开始");

		// 货币兑换，取商户结算币种
		doAccounting_500_505(refundOrderId, refundAmount, settlementCurrencyCode,
				settlementRate, merchantOrderId);

		// 货币兑换，取渠道结算币种
		doAccounting_500_506(refundOrderId, refundAmount,
				channelOrder.getCurrencyCode(), settlementRate, merchantOrderId);

		logger.info("货币兑换记账处理 结束；doAccounting_500_505；doAccounting_500_506结束");
	}

	
	
	// ========
	// 商户退款申请,该订单已经被结算的状态,版本2
	// 特点：手续费 （固定手续费+百分比手续费） 将会根据商户的标志，区分出来。   已归还保证金的已经清算的订单，将不再扣除保证金.
	// 
	// 只处理单张退款订单的状态
	// 输入参数 :
	// settlementOrder 退款订单 肯定不是空，调用者已经检查过了
	// assureAmount_settlement_order 清算订单中的保证金数量
	// fee_settlement_order: 清算订单中的手续费,这个是清算币种
	// 输出参数： outRoeDTO
	// ========
	private void doMerchantRefundApplyAccounting_VERSION2(
			ChannelOrderDTO channelOrder, TradeOrderDTO tradeOrderDTO,
			RefundOrderDTO refundOrderDTO,
			PartnerSettlementOrder settlementOrder,
			long assureAmount_settlement_order, String settlementCurrencyCode,
			long settlementAmount, String settlementRate,
			long fee_settlement_order, String merchantOrderId,
			RefundOrderExtendDTO outRoeDTO) throws Exception {

		Long orderAmount = tradeOrderDTO.getOrderAmount();
		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		String refundOrderId = refundOrderDTO.getRefundOrderNo().toString();

		int policyVersion = POLICY_VERSION_SECOND;
		int feeFlg = getMerchantFeeFlg(partnerId);

		// 下面的全额退款的逻辑和部分退款的逻辑,其实是完全一样的.  因为部分退款 计算出来assureAmount会等于0  
		// 如果是全额退款
		if (refundAmount == orderAmount) {  //这里有一个是long 型，所以能够判断，全是Long 型，就错了。 
			logger.info("退款-已清算全额退款处理开始: 退款订单号=[" + refundOrderId + "]");
			
			// (1)计算需要的扣除的保证金 ：　 如果保证金已经归还，则全部改为扣除基本户
			long assureAmount = getAssureAmount_refundRequest(policyVersion,refundOrderId,
					assureAmount_settlement_order,settlementOrder.getAssureSettlementFlg(),
					orderAmount, refundAmount);

			// (2) 判断基本本户余额是否够扣款，如果不够就不扣款
			checkAcctBalanceEnough(settlementCurrencyCode, partnerId,
					refundAmount, settlementRate, fee_settlement_order,0);

			// 设置 如果assureAmount ==0 ,下面的语句也没啥影响
			if (assureAmount > 0) { // =0 的话，下面的语句根本也没有必要去执行了
				int assureAccountingFlg = doAccounting_500_504(partnerId,
						refundOrderId, assureAmount, settlementCurrencyCode,
						settlementRate, merchantOrderId);

				// 置保证金已退款
				if (assureAccountingFlg == 1) {
					if (settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.UNSETTLEMENT
							.getCode()) { // =0 才会把它变到4
						settlementOrder
								.setAssureSettlementFlg(SettlementFlagEnum.REFUND
										.getCode());
						partnerSettlementOrderService
								.updatePartnerSettlementOrder(settlementOrder);
					}
				}
			}
						
			Long feeLong = calcRefundFee2(feeFlg ,settlementOrder.getOrderAmount(),settlementOrder.getAmount(),
					refundAmount,   fee_settlement_order,Long.valueOf(settlementOrder.getFixedFeeSettlementAmount()),  
					settlementOrder.getPerFee());

			// 扣基本账户
			// note by sch : (refundAmount - assureAmount). 如果assureAmount
			int basicAccountingFlg = doAccounting_500_503(partnerId,
					refundOrderId, refundAmount - assureAmount,
					settlementCurrencyCode, settlementRate,
					feeLong, merchantOrderId);

			
			logger.info("退款-已清算全额退款处理结束  退款订单号=[" + refundOrderId + "]"
					+ ",refundAmount=[" + refundAmount + "],assureAmount=["
					+ assureAmount + "]" + "feeLong = [" + feeLong + "]");
			
			long fee=0;
			if(feeLong!=null){
				fee = feeLong.longValue();
			}
			
			Long fixedfee_206_Long = calcRefundFee_206(feeFlg, settlementOrder.getOrderAmount(),
					settlementOrder.getAmount(),  refundAmount, 
					settlementOrder.getFixedFee(), settlementOrder.getFixedFeeSettlementAmount(), 
					settlementOrder.getSmallOrderFixedFeeAmount());
					
			logger.info("退款-已清算全额退款处理结束　feeLong=[" + feeLong + "] " + 
					   "fixedFee_206=[" + fixedfee_206_Long + "]");
			
			refundOrderExtendSetValue(outRoeDTO, refundAmount - assureAmount,
					assureAmount, fee, feeFlg, policyVersion,
					REFUND_FLG_SETTLEMENTED_ALL,fixedfee_206_Long.longValue());
		} else { // 部分退款，不退保证金，全部由基本户出

			logger.info("退款-已清算部分退款处理,退款订单号=[" + refundOrderId
					+ "],settlementAmount=[" + settlementAmount
					+ "],refundAmount=[" + refundAmount + "]");

			if (refundAmount <= settlementAmount) {
				// 计算退款手续费 2016-04-30
				
				
				// 计算手续费比例
				Long feeLong = calcRefundFee2(feeFlg ,settlementOrder.getOrderAmount(),settlementOrder.getAmount(),
						refundAmount,   fee_settlement_order,Long.valueOf(settlementOrder.getFixedFeeSettlementAmount()),
						settlementOrder.getPerFee());
				long fee=0;
				if(feeLong!=null){
					fee = feeLong.longValue();
				}
				
				// 检查一些帐户余额，如果余额不够，返回的错误码，会更加合适
				checkAcctBalanceEnough(settlementCurrencyCode, partnerId,
						refundAmount, settlementRate, fee,0);

				doAccounting_500_503(partnerId, refundOrderId, refundAmount,
						settlementCurrencyCode, settlementRate, feeLong,
						merchantOrderId);

				Long fixedfee_206_Long = calcRefundFee_206(feeFlg, settlementOrder.getOrderAmount(),
						settlementOrder.getAmount(),  refundAmount, 
						settlementOrder.getFixedFee(), settlementOrder.getFixedFeeSettlementAmount(), 
						settlementOrder.getSmallOrderFixedFeeAmount());
				
				logger.info("退款-已清算部分退款处理结束　feeLong=[" + feeLong + "] " + 
						   "fixedFee_206=[" + fixedfee_206_Long + "]");
				
				refundOrderExtendSetValue(outRoeDTO, refundAmount, 0, fee,
						feeFlg, policyVersion, REFUND_FLG_SETTLEMENTED_PART,fixedfee_206_Long.longValue());

			} else {
				logger.info("退款-已清算部分退款处理异常,退款金额大于清算清算金额");
				throw new AppException("退款异常！");
			}
		}

		logger.info("货币兑换记账处理 开始；doAccounting_500_505；doAccounting_500_506开始");

		// 货币兑换，取商户结算币种
		doAccounting_500_505(refundOrderId, refundAmount,
				settlementCurrencyCode, settlementRate, merchantOrderId);

		// 货币兑换，取渠道结算币种
		doAccounting_500_506(refundOrderId, refundAmount,
				channelOrder.getCurrencyCode(), settlementRate, merchantOrderId);

		logger.info("货币兑换记账处理 结束；doAccounting_500_505；doAccounting_500_506结束");
	}

	@Override
	public RefundOrderDTO queryRefundOrderDTO(String parnterID,String partnerRefundOrderId) {
			return refundOrderService.queryByPartnerAndPartnerRefundOrderId(parnterID, partnerRefundOrderId);
	}
	

	
	/**
	 * 农行渠道对账回调退款销账
	 * 
	 * @param orgCode
	 * @param refundOrderNo
	 * @param refundAmount
	 * @param currencyCode 交易币种
	 * @param settlementCurrencyCode 渠道对账币种
	 * @param settlementRate 渠道对账汇率
	 * @throws Exception
	 */
	public void doAbcAccounting(String orgCode, Long refundOrderNo,
			Long refundAmount, String currencyCode,
			String settlementCurrencyCode, String settlementRate,String merchantOrderId)
			throws Exception {
		doAccounting_500_509(orgCode, refundOrderNo.toString(), refundAmount,
				currencyCode,merchantOrderId);
		doAccounting_500_510(orgCode, refundOrderNo.toString(), refundAmount,
				settlementCurrencyCode, settlementRate,merchantOrderId);
	}

		/* 
	 * 针对网关订单的更新，需要记录日志，否则很难跟踪问题,先记录日志，以后记录在数据库中
	 * add by sch 2016-04-23
	 * 输入参数：desc 为更新原因
	 */

/*	private void logTradeOrderModify(Long refundOrderNo,Long tradeOrderNo,Long refundAmount,String desc){
		logger.info("TradeOrder RefundAmount Modify refundOrderNO:=[" + refundOrderNo + "]" 
	            + " traderOrderNo=[" + 	tradeOrderNo + "]"
	            + " refundAmount=[" + refundAmount + "]"  
	            + " desc=" + desc);
	}
*/
	
	/* 
	 * 针对清算订单的更新，需要记录日志，否则很难跟踪问题,先记录日志，以后记录在数据库中
	 * add by sch 2016-04-23
	 */
/*	private void logSettlementOrderModify(Long refundOrderNo,Long settlementOrderNo,Long refundAmount,
			int oldSettlementFlag,int newSettlementFlag,String desc){
		logger.info("TradeOrder SettlementOrder Modify refundOrderNO:=[" + refundOrderNo + "]" 
	            + " traderOrderNo=[" + 	settlementOrderNo + "]"
	            + " refundAmount=[" + refundAmount + "]" 
	            + " oldFlag =[" + oldSettlementFlag + "]"
	            + " newFlag =[" + newSettlementFlag + "]"
	            + " desc=" + desc);
	}*/
	
	
	
	/*
	 * 创建一个RefundOrderExtendDTO
	 */
	private RefundOrderExtendDTO buildRefundOrederExtendDTO(long refundOrderNo,TradeOrderDTO traderOrderDTO,PartnerSettlementOrder settlementOrder){
		
		RefundOrderExtendDTO  roeDTO = new RefundOrderExtendDTO();
		
		roeDTO.setRefundOrderNo(refundOrderNo);
		roeDTO.setSettlementOrderNo(settlementOrder.getId());
		roeDTO.setSettlementFlg(Long.valueOf(settlementOrder.getSettlementFlg()));
		roeDTO.setTradeRefundAmount(traderOrderDTO.getRefundAmount());
		roeDTO.setSettlementAmount(settlementOrder.getAmount());
		roeDTO.setAssureSettlementAmount(settlementOrder.getAssureAmount());
		
		//这几个手续费字段，可能是空的，所以需要特别判断
		Long AssureSettlementFlg = Long.valueOf(0);
		if(settlementOrder.getAssureSettlementFlg() != null){
			AssureSettlementFlg = Long.valueOf(settlementOrder.getAssureSettlementFlg());
		}
		roeDTO.setAssureSettlementFlg(AssureSettlementFlg);
		
		Long PerFee = Long.valueOf(0);
		if( settlementOrder.getPerFee() != null){
			PerFee = settlementOrder.getPerFee();		//这些判断放在 setPerFeeAmount里面是否更好？
		}
		roeDTO.setPerFeeAmount(PerFee);		
		
		Long Fee = Long.valueOf(0);
		if( settlementOrder.getFee() != null){
			Fee = settlementOrder.getFee();
		}			
		roeDTO.setFeeAmount(Fee);
		
		Long FixedFeeAmount = Long.valueOf(0);
		if( settlementOrder.getFixedFeeSettlementAmount() != null){
			FixedFeeAmount =  Long.valueOf(settlementOrder.getFixedFeeSettlementAmount());
		}			
		roeDTO.setFixedFeeAmount( FixedFeeAmount );
		
		roeDTO.setSettlementCurrency( settlementOrder.getSettlementCurrencyCode());
		roeDTO.setBaseAcctDelta( Long.valueOf(0));
		roeDTO.setAssureAcctDelta( Long.valueOf(0));
		roeDTO.setFeeFlg(Long.valueOf(0));
		roeDTO.setFeeDelta(Long.valueOf(0));
		roeDTO.setPolicyVersion(Long.valueOf(refundPolicyVersion));		
		roeDTO.setRefundFlg(Long.valueOf(0));
		roeDTO.setSettlementRate(settlementOrder.getSettlementRate());
		roeDTO.setRefundSettlementFlg(Long.valueOf(0));			//先设置为0
		
		//add by nico.shao 2016-07-11
		if(settlementOrder.getSmallOrderFixedFeeAmount() != null){
			roeDTO.setSmallOrderFixedfeeAmount(settlementOrder.getSmallOrderFixedFeeAmount());
		}
		else 
			roeDTO.setSmallOrderFixedfeeAmount(Long.valueOf(0));
		
		roeDTO.setSmallOrderFixedfeeDelta(Long.valueOf(0));
		roeDTO.setSmallOrderFixedfeeRCount(Long.valueOf(0));
		
		java.util.Date  date=new Date();
		roeDTO.setCreateDate(date);
				
		logger.info("logRefundOrderExtends " + roeDTO.toString());
		
		return roeDTO;
	}
	
	/*
	 * 设置退款订单的一些扩充信息
	 */
	private void refundOrderExtendSetValue(RefundOrderExtendDTO roeDTO,long baseDelta,long assureDelta,
			long feeDelta,long feeFlg, long policyVersion,long refundFlg,
			long smallOrderFixedFeeDelta){
		
		//logger.info("feeFlg" + feeFlg );
		roeDTO.setBaseAcctDelta(baseDelta);
		roeDTO.setAssureAcctDelta(assureDelta);
		roeDTO.setFeeDelta(feeDelta);
		roeDTO.setFeeFlg(feeFlg);
		roeDTO.setPolicyVersion(policyVersion);
		roeDTO.setRefundFlg(refundFlg);
		
		roeDTO.setSmallOrderFixedfeeDelta(smallOrderFixedFeeDelta); //2016-07-11
		//roeDTO.setRefundFeeAmount(Long.valueOf(0));
		//roeDTO.setRefundFeeRate("0");
		//roeDTO.setRefundFeePolicy(Long.valueOf(0));
	}
	
	/*
	 * 记录退款订单的扩充信息
	 */
	private void logRefundOrderExtends(RefundOrderExtendDTO roeDTO) {
			
		//logger.info("logRefundOrderExtends " + roeDTO.toString());
		
		refundOrderExtendService.createRefundOrderExtend(roeDTO);		
		
		//RefundOrderExtendDTO data1DTO = readRefundOrderExtend(roeDTO.getRefundOrderNo());
		
		//if(data1DTO !=null){
		//	logger.info("read dataDTO " + data1DTO.toString());
		//}
	}

	//如果没有这条记录，会返回为空 
	private RefundOrderExtendDTO readRefundOrderExtend(long refundOrderNo){
		return refundOrderExtendService.findByRefundOrderNo(refundOrderNo);
	}
	
	
	private void updateRefundOrderExtendCount(RefundOrderExtendDTO roeDTO){
		boolean bResult =  refundOrderExtendService.updateRefundOrderExtendCount(roeDTO);
		logger.info("updateRefundOrderExtendCount " + bResult);
	}
	
	//构造退款手续费订单
	private void createRefundFeeOrder(RefundOrderDTO refundOrderDTO, TradeOrderDTO tradeOrderDTO,
			String settlementCurrencyCode) {
		try{
			//获取手续费
			//EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
			//		.queryEnterpriseBaseByMemberCode(tradeOrderDTO.getPartnerId());
			
			RefundFeeConf refundFeeConf = enterpriseBaseService.queryRefundFeeConfByCode(tradeOrderDTO.getPartnerId());
			if(refundFeeConf==null){
				logger.info("商户退款手续费配置：找不到");
				return ;
			}
			
			logger.info("refundFeeConf =" + refundFeeConf.toString());
			
			String refundFeeStr = refundFeeConf.getRefundFee();
	
			// 如果没有配置退款手续费，就不再产生退款手续费订单
			if(StringUtil.isEmpty(refundFeeStr)||Double.valueOf(refundFeeStr)<=0.0){
				logger.info("退款手续费配置为0，无需收取退款手续费");
				return;
			}
			
			long refundFee = 0;
			SettlementRate settlementRete = currencyRateService.getSettlementRate(
					refundFeeConf.getRefundFeeCurrency(),settlementCurrencyCode,"1"
							,String.valueOf(tradeOrderDTO.getPartnerId()),null);
			
			if(!StringUtil.isEmpty(refundFeeStr)){
				refundFee = new BigDecimal(refundFeeStr).multiply(new BigDecimal(settlementRete.getExchangeRate())).multiply(new BigDecimal("1000")).longValue();
			}
			
			RefundFeeOrder refundfeeOrder = new RefundFeeOrder();
			refundfeeOrder.setCreateDate(new Date());
			refundfeeOrder.setRefundOrderNo(refundOrderDTO.getRefundOrderNo());
				
			refundfeeOrder.setMerchantRefundOrderId(refundOrderDTO.getPartnerRefundOrderId());
			refundfeeOrder.setMerchantOrderId(tradeOrderDTO.getOrderId());
			refundfeeOrder.setExchangeRate(settlementRete.getExchangeRate());
			refundfeeOrder.setFeeAmount(refundFeeStr);
			refundfeeOrder.setFeeCurrencyCode(refundFeeConf.getRefundFeeCurrency());
			refundfeeOrder.setFeeFlg(0);
			refundfeeOrder.setPartnerId(tradeOrderDTO.getPartnerId() );
			refundfeeOrder.setSettlementAmount(refundFee);
			refundfeeOrder.setSettlementCurrencyCode(settlementCurrencyCode);
			refundfeeOrder.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
			refundfeeOrder.setUpdateDate(new Date());
			
			refundOrderExtendService.createRefundFeeOrder(refundfeeOrder);
			
			logger.info("创建退款手续费订单结束");
		}catch(Exception e){
			logger.error("save refundFeeOrder error:" + refundOrderDTO.getRefundOrderNo(),e);
		}
	}

	
	

	
	//构造一个RefundOrderStautsChagneLogDTO
	private RefundOrderStatusChangeLogDTO buildRefundOrederStatusChangeLogDTO(long refundOrderNo,long oldStatus,
			long newStatus,String action,long tradeAmountDelta){
		RefundOrderStatusChangeLogDTO roscDTO= new RefundOrderStatusChangeLogDTO();
		
		roscDTO.setRefundOrderNo(refundOrderNo);
		roscDTO.setAction(action);
		roscDTO.setNewStatus(newStatus);
		roscDTO.setOldStatus(oldStatus);
		roscDTO.setTradeRefundDelta(tradeAmountDelta);
		
		java.util.Date  date=new Date();		
		roscDTO.setCreateDate(date);
		
		refundOrderStatusChangeSetValue(roscDTO,0,0,0,0,0,0,date,date);
				
		return roscDTO;
	}
	
	private Long logRefundOrderStatusChangeLog(RefundOrderStatusChangeLogDTO roscDTO){
		logger.info("refundOrderStatusChangeDTO "+ roscDTO.toString());
		return refundOrderExtendService.createRefundOrderStatusChangeLog(roscDTO);
	}
	
	//主要是和清算订单相关的修改 
	private void refundOrderStatusChangeSetValue(RefundOrderStatusChangeLogDTO roscDTO,long settlementModifyFlg,
			long oldSettlementFlg,long newSettlementFlg,
			long oldAssureSettlementFlg,long newAssureSettlementFlg,long settlementAmountDelta,
			java.util.Date oldSettlementDate ,java.util.Date newSettlementDate){
	
		roscDTO.setSettlementModifyFlg(settlementModifyFlg);
		roscDTO.setOldSettlementFlg(oldSettlementFlg);
		roscDTO.setNewSettlementFlg(newSettlementFlg);
		roscDTO.setOldAssureSettlementFlg(oldAssureSettlementFlg);
		roscDTO.setNewAssureSettlementFlg(newAssureSettlementFlg);
		roscDTO.setSettlementAmountDelta(settlementAmountDelta);
		roscDTO.setOldSettlementDate(oldSettlementDate);
		roscDTO.setNewSettlementDate(newSettlementDate);		
	}
	
	//=========
	//检查是否 没有进行中的退款订单,在设置 清算订单标志的时候，会用到 
	//返回值： true: 表示没有进行中的退款订单
	//     false: 表示有进行中的订单
	//=========
	private boolean checkNoUncompleteRefundOrder(Long tradeOrderNo,Long refundOrderNo){
		
		List<RefundOrderDTO> refundOrderList = this.refundOrderService.findByTradeOrderNo(tradeOrderNo);
		
		if((refundOrderList == null) || (refundOrderList.isEmpty()) ){
			logger.info("检查退款订单列表，是否为空，这里不应该为空");
			return true;
		}
					
		for (RefundOrderDTO refundOrderDTO : refundOrderList) {
			
			if(refundOrderNo.equals(refundOrderDTO.getRefundOrderNo())){		//不能使用==来判断 
				continue;
			}
			
			//logger.info("refundOrderDTO.getRefundOrderNo()=["+ refundOrderDTO.getRefundOrderNo() 	+ "] refundOrderNo=[" + refundOrderNo + "]");
			
			Integer iRefundStatus =Integer.valueOf(refundOrderDTO.getStatus());
			if((RefundStatusEnum.INIT.getCode()==iRefundStatus) || (RefundStatusEnum.MANUAL.getCode()==iRefundStatus)
				|| (RefundStatusEnum.REFUNDING.getCode()==iRefundStatus)
				||  (RefundStatusEnum.TIMEOUT.getCode()==iRefundStatus)){
				logger.info("检查退款订单列表  退款订单id=[" + refundOrderDTO.getRefundOrderNo() + "]" + "status=[" + refundOrderDTO.getStatus() + "]");
				return false;
			}
		}
		
		logger.info("检查退款订单列表 return true 退款订单id=[" + refundOrderNo + "]" + "number=["+ refundOrderList.size() + "]");		
	
		return true;
	}
	
	//=========
	//获取所有退款成功\进行中\人工处理的退款订单金额 
	//=========
	private long calcRefundOrderTransAmount(Long tradeOrderNo){
		
		long transAmount = 0;
		List<RefundOrderDTO> refundOrderList = this.refundOrderService.findByTradeOrderNo(tradeOrderNo);
		
		if((refundOrderList == null) || (refundOrderList.isEmpty()) ){
			logger.info("检查退款订单列表，是否为空，这里不应该为空");
			return transAmount;
		}
					
		for (RefundOrderDTO refundOrderDTO : refundOrderList) {
				
			Integer iRefundStatus =Integer.valueOf(refundOrderDTO.getStatus());
			if((RefundStatusEnum.INIT.getCode()==iRefundStatus) || (RefundStatusEnum.MANUAL.getCode()==iRefundStatus) 
				|| (RefundStatusEnum.SUCCESS.getCode()==iRefundStatus))
			{
				transAmount+= refundOrderDTO.getTransferAmount();
			}
		}
		
		logger.info("检查退款订单列表    已经退款金额=[" + transAmount + "]" + "number=["+ refundOrderList.size() + "]");			
		return transAmount;
	}
	//====
	//处理来自退款Task的退款请求
	//====
	public RefundTransactionServiceResultDTO refundTaskRdTx(
			RefundTransactionServiceParamDTO paramDTO) throws Exception {
		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
		Long refundAmount = 0L;
		
		Map<String, String> refundMap = new HashMap();
//		refundMap.put("depositOrderNo",
//				String.valueOf(paymentOrderDTO.getPaymentOrderNo()));
//		refundMap.put("refundOrderNo", String.valueOf(refundOrderNo));
//
//		// 转换
//		if (!StringUtil.isEmpty(transferRate)) {
//			BigDecimal rAmount = new BigDecimal(refundAmount);
//			rAmount.multiply(new BigDecimal(transferRate));
//			refundMap.put("applyAmount", rAmount.toString());
//		} else {
//			refundMap.put("applyAmount", refundAmount.toString());
//		}
//
//		refundMap.put("memberCode", String.valueOf(partnerId));
//		refundMap.put("memberAccType",
//				String.valueOf(paymentOrderDTO.getPayeeType()));
//		refundMap.put("memberType",
//				String.valueOf(MemberTypeEnum.MERCHANT.getCode()));
//		refundMap.put("bankSerialNo",
//				String.valueOf(channelOrderDTO.getChannelOrderNo()));
//		refundMap.put("bankOrderId",
//				String.valueOf(channelOrderDTO.getChannelOrderNo()));
//		refundMap.put("depositAmount",
//				String.valueOf(channelOrderDTO.getAmount()));
//		refundMap.put("applyMax", String.valueOf(channelOrderDTO.getAmount()));
//		refundMap.put("depositDate", DateUtil.formatDate(
//				channelOrderDTO.getCompleteDate(), "yyyyMMddHHmmss"));
//		refundMap.put("bankChannel", channelOrderDTO.getOrgCode());

		jmsSender.send("fo.autoRefund.fiTofo.req", JSonUtil.toJSonString(refundMap));
		
		resultDTO.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
		resultDTO.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		resultDTO.setRefundAmount(new BigDecimal(refundAmount).divide(
				new BigDecimal(10)).toString());
		return resultDTO;
	}
	
	
	
	
	/* 原来的代码
	@Override
	public RefundTransactionServiceResultDTO refundRdTx(
			RefundTransactionServiceParamDTO paramDTO) throws Exception {
		
		//step1: 安全性检查
		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
		validateService.validate(paramDTO);
		if (!ResponseCodeEnum.SUCCESS.getCode().equals(paramDTO.getErrorCode())) {
			resultDTO.setResponseCode(paramDTO.getErrorCode());
			resultDTO.setResponseDesc(paramDTO.getErrorMsg());
			return resultDTO;
		}
		PaymentOrderDTO paymentOrderDTO = paramDTO.getPaymentOrderDTO();
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(paymentOrderDTO.getPaymentOrderNo());
		String transferRate = channelOrderDTO.getTransferRate();
		TradeOrderDTO tradeOrderDTO = paramDTO.getTradeOrderDTO();
		String merchantOrderId = null;
		
		//step2: 构造退款订单
		RefundOrderDTO refundOrderDTO = buildRefundOrderInfo(paramDTO,transferRate);
		Long partnerId = tradeOrderDTO.getPartnerId();
		Long refundAmount = refundOrderDTO.getRefundAmount();

		if (refundAmount <= 0 || refundAmount > tradeOrderDTO.getRefundAmount()) {
			logger.error("退款-构建退款对象计算退款金额为0-TRADE_ORDER_NO: "
					+ paramDTO.getTradeOrderDTO().getTradeOrderNo());
			resultDTO.setResponseCode(ResultCode.REFUND_AMOUNT_EQUAL_TO_ZERO
					.getCode());
			resultDTO.setResponseDesc(ResultCode.REFUND_AMOUNT_EQUAL_TO_ZERO
					.getDescription());
			return resultDTO;
		}

		String refundType = paramDTO.getRefundType();
		if (Integer.valueOf(refundType) == RefundTypeEnum.FULL_REFUND.getCode()
				&& refundAmount < tradeOrderDTO.getRefundAmount()) {
			logger.info("全额退款，金额 < 网关订单的金额，所以改为部分退款"); 
			refundOrderDTO.setRefundType(RefundTypeEnum.PART_REFUND.getCode()
					+ "");
		}

		//add by sch 2016-04-25 如果是部分退款，但是金额=全额退款，则设置为全额退款 
		//注意：这里使用 getOrderAmount，和上面的是不一样的。 如果是第2个部分退款，依然是部分退款。 
		if (Integer.valueOf(refundType) == RefundTypeEnum.PART_REFUND.getCode()
				&& (refundAmount == tradeOrderDTO.getOrderAmount())) {
			logger.info("部分退款，金额=网关订单的金额，所以改为全额退款"); 
			refundOrderDTO.setRefundType(RefundTypeEnum.FULL_REFUND.getCode() +"");
		}
		//sch 2016-04-25
		
		Long refundOrderNo = refundOrderService
				.createRefundOrderRnTx(refundOrderDTO);
		logger.info("ordercenter中退款30104RefundServiceImpl.refundRdTx():退款订单号"+refundOrderNo+"支付订单号"+paymentOrderDTO.getPaymentOrderNo());
		
		refundOrderDTO.setRefundOrderNo(refundOrderNo);
		resultDTO.setRefundOrderId(refundOrderNo+"");
		
		//step 3  更新网关订单可退余额 
		// 减少可退金额
		boolean bRet = tradeOrderService.updateRefundAmount(paramDTO
				.getTradeOrderDTO().getTradeOrderNo(), -refundAmount,
				TradeOrderStatusEnum.SUCCESS.getCode() + "");
		if (!bRet) {
			logger.info("update trade order refundAmount failed"); 		//add by sch 2016-04-22
			resultDTO.setResponseCode(ResultCode.REFUND_ORDER_CREATE_ERROR
					.getCode());
			resultDTO.setResponseDesc(ResultCode.REFUND_ORDER_CREATE_ERROR
					.getDescription());
			return resultDTO;
		}

		logTradeOrderModify(refundOrderNo,tradeOrderDTO.getTradeOrderNo(),refundAmount,"refund init"); //add by sch 2016-04-22
		
		
		//step4     查询结算订单,如果是未清算状态,则更新清算订单的清算金额.  如果是已经清算状态, 则更新清算标志
		List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
				.querySettlementOrder(tradeOrderDTO.getPartnerId(),
						paymentOrderDTO.getPaymentOrderNo());
		
		// 获取保证金,只有全额退才退保证金
		long assureAmount = 0;			//保证金
		long fee = 0;					//手续费
		String settlementCurrencyCode = "";			//清算币种
		long firstSettlementAmount = 0;				//清算金额
		long secondSettlementAmount = 0;
		int firstSettlementFlg = 0;					//清算标志
		int secondSettlementFlg = 0;
		int settlementSize = settlementOrders.size();	//清算订单数量
		String firstPartnerSettlementRate = "";			
		String secondPartnerSettlementRate = "";

		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			logger.info("ordercenter中退款查询清算订单settlementOrder:清算订单号:"+settlementOrder.getId()+"，SettlementFlg:"+settlementOrder.getSettlementFlg()+"，settlementSize:"+settlementSize);
			
			assureAmount += settlementOrder.getAssureAmount();
			if (null != settlementOrder.getFee()) {
				fee += settlementOrder.getFee();
			}
			settlementCurrencyCode = settlementOrder
					.getSettlementCurrencyCode();
			// 得到第一次结算金额和第二次结算金额
			if (settlementOrder.getOrderCode() == 1) {
				firstSettlementAmount = settlementOrder.getAmount();
				if (settlementOrder.getSettlementFlg() == 1) {
					firstSettlementFlg = 1;
					firstPartnerSettlementRate = settlementOrder
							.getSettlementRate();
				}
			} else {
				secondSettlementAmount = settlementOrder.getAmount();
				if (settlementOrder.getSettlementFlg() == 1) {
					secondSettlementFlg = 1;
					secondPartnerSettlementRate = settlementOrder
							.getSettlementRate();
				}
			}
			
			//判断是否需要修改状态，防止退款后再清算
			if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.UNSETTLEMENT.getCode() 
					|| settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()){
				
				int oldSettlementFlg = settlementOrder.getSettlementFlg();  //add by sch 2016-04-13 用于下面的日志 
				
				settlementOrder.setSettlementFlg(SettlementFlagEnum.REFUND.getCode());
				//settlementOrder.setAssureSettlementFlg(SettlementFlagEnum.REFUND.getCode());
				settlementOrder.setAmount(settlementOrder.getAmount() - refundAmount);				//这个逻辑对么? 如果有两张清算订单,每张都减去 退款数量?  
				partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
				
				logSettlementOrderModify(refundOrderNo,settlementOrder.getId(),refundAmount,
						oldSettlementFlg,settlementOrder.getSettlementFlg(),"refund init");   		//add by sch 2016-04-22
			}
			
			merchantOrderId = settlementOrder.getOrderId();
		}
		
		//step 4.1 已经清算的订单, 则进行保证金扣款 
		if (firstSettlementFlg == 1) {
			logger.info("已清算进行清算处理开始；退款订单号"+refundOrderDTO.getRefundOrderNo()+"，firstSettlementFlg"+firstSettlementFlg);
			doMerchantRefundApplyAccounting(channelOrderDTO, tradeOrderDTO,
					refundOrderDTO, settlementOrders, settlementSize,
					firstSettlementFlg, assureAmount, settlementCurrencyCode,
					firstPartnerSettlementRate, secondSettlementFlg,
					secondPartnerSettlementRate, firstSettlementAmount, fee,merchantOrderId);
			refundOrderDTO.setPayerFee(fee);
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);

			logger.info("已清算进行清算处理结束！");
		}

		//step5   通知FO后台审核
		sendToFo(paymentOrderDTO, channelOrderDTO, transferRate, partnerId,
				refundAmount, refundOrderNo);
		
		resultDTO.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
		resultDTO.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		resultDTO.setRefundAmount(new BigDecimal(refundAmount).divide(
				new BigDecimal(10)).toString());
		return resultDTO;
	}
*/
	
	/*
	@Override
	public void refundHandle(String refundOrderNo,
			ChannelPaymentResult refundResult) throws Exception {
		String responseCode = refundResult.getErrorCode();
		String responseMsg = refundResult.getErrorMsg();
		if (logger.isInfoEnabled()) {
			logger.info("refundOrderNo:" + refundOrderNo
					+ ",muanual process result:" + responseCode);
		}

		RefundOrderDTO refundOrderDTO = refundOrderService.queryByPk(Long
				.valueOf(refundOrderNo));
		
		
		TradeOrderDTO tradeOrderDTO = tradeOrderService
				.queryTradeOrderById(refundOrderDTO.getTradeOrderNo());
		// 判断是否对账
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(refundOrderDTO.getPaymentOrderNo());
		List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
				.querySettlementOrder(
						Long.valueOf(refundOrderDTO.getPartnerId()),
						refundOrderDTO.getPaymentOrderNo());

		String orderId = refundOrderDTO.getRefundOrderNo() + "";
		Long amount = refundOrderDTO.getRefundAmount();
		String orgCode = channelOrderDTO.getOrgCode();
		String channelSettlementCurrencyCode = channelOrderDTO
				.getSettlementCurrencyCode();

		String settlementCurrencyCode = "";
		boolean settlementFlg = false;
		String partnerSettlementRate = "1";
		long assureAmount = 0;
		long fee = 0;
		long settlementAmount = 0;
		String merchantOrderId=tradeOrderDTO.getOrderId();
		boolean settlementAssureFlg= false;		//2016-04-25

		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			settlementCurrencyCode = settlementOrder
					.getSettlementCurrencyCode();
			if (settlementOrder.getSettlementFlg() == 1) {
				settlementFlg = true;
				partnerSettlementRate = settlementOrder.getSettlementRate();
			}
			assureAmount += settlementOrder.getAssureAmount();
			if (null != settlementOrder.getFee()) {
				fee += settlementOrder.getFee();
			}
			settlementAmount += settlementOrder.getAmount();
			
			//add by sch 2016-04-25
			if(settlementOrder.getAssureSettlementFlg()==1){		//0,4 都认为是false 
				settlementAssureFlg = true;
			}
			//sch 2016-04-25
		}

		Long orderAmount = tradeOrderDTO.getOrderAmount();
		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		
		logger.info("ordercenter中退款30104RefundServiceImpl.refundHandle():退款订单号"+refundOrderNo+",settlementFlg"+settlementFlg);
		// 退款成功
		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

			try {
				// 未清算
				if (!settlementFlg) {
					logger.info("退款成功未清算开始doAccounting500_500;doAccounting_500_501;doAccounting_500_502;amount:"+amount+"orderId:"+orderId+"channelOrderDTO.getTransferRate():"+channelOrderDTO.getTransferRate()+"refundOrderDTO.getRefundAmount():"+refundOrderDTO.getRefundAmount());
					doAccounting500_500(refundOrderDTO, channelOrderDTO,merchantOrderId);
					doAccounting_500_501(orgCode, orderId, amount,
							channelOrderDTO.getCurrencyCode(),merchantOrderId);
					doAccounting_500_502(orgCode, orderId, amount,
							channelOrderDTO.getTransferCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);
					logger.info("退款成功未清算结束！");
					
				} else{// 商户已清算
					logger.info("退款成功已清算开始doAccounting500_508;doAccounting_500_509;doAccounting_500_510;refundOrderDTO.getRefundAmount():"+refundOrderDTO.getRefundAmount()+"refundAmount:"+refundAmount+"channelOrderDTO.getTransferRate():"+channelOrderDTO.getTransferRate()+"orderId:"+orderId);
					amount = new BigDecimal(refundAmount)
							.multiply(
									new BigDecimal(channelOrderDTO
											.getTransferRate())).longValue();
					String rate = channelOrderDTO.getSettlementRate();
					doAccounting_500_508(orgCode, orderId,
							refundOrderDTO.getRefundAmount(),
							channelOrderDTO.getCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);

//					logger.info("refundOrderNo:" + refundOrderNo + ",orgCode:" + orgCode);
//					if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode) && channelOrderDTO.getReconciliationFlg() == 1){
//						logger.info("refundOrderNo:" + refundOrderNo + ",orgCode:" + orgCode + ",do abc was recon");
//						doAccounting_500_509(orgCode, orderId, refundAmount,
//								channelOrderDTO.getCurrencyCode());
//						doAccounting_500_510(orgCode, orderId, refundAmount,
//								channelOrderDTO.getSettlementCurrencyCode(),
//								channelOrderDTO.getSettlementRate());
//					}else if(!ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
//						logger.info("refundOrderNo:" + refundOrderNo + ",orgCode:" + orgCode + ",do other logic");
//						doAccounting_500_509(orgCode, orderId, refundAmount,
//								channelOrderDTO.getCurrencyCode());
//						doAccounting_500_510(orgCode, orderId, refundAmount,
//								channelOrderDTO.getTransferCurrencyCode(),
//								channelOrderDTO.getTransferRate());
//					}
					
					logger.info("refundOrderNo:" + refundOrderNo + ",orgCode:" + orgCode + ",do other logic");
					doAccounting_500_509(orgCode, orderId, refundAmount,
							channelOrderDTO.getCurrencyCode(),merchantOrderId);
					doAccounting_500_510(orgCode, orderId, refundAmount,
							channelOrderDTO.getTransferCurrencyCode(),
							channelOrderDTO.getTransferRate(),merchantOrderId);
					logger.info("退款成功已清算结束");
					
				}
			} catch (Exception e) {
				logger.error("refund erro:" + refundOrderNo, e);
			}

			refundOrderDTO.setStatus(RefundStatusEnum.SUCCESS.getCode() + "");
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.COMPLETED.getCode());
			
		} else {
			// 退款失败
			// 渠道已对账才处理
			//if (channelOrderDTO.getReconciliationFlg() == 1) {// 渠道已对账

				if (settlementFlg) {// 商户已清算
					logger.info("退款失败已清算开始，refundAmount："+refundAmount+"orderAmount:"+orderAmount+"assureAmount:"+assureAmount+"partnerSettlementRate:"+partnerSettlementRate+"settlementAmount:"+settlementAmount);
					doAccounting_500_512(orderId, refundAmount,
							channelOrderDTO.getCurrencyCode(),partnerSettlementRate,merchantOrderId);
					doAccounting_500_513(orderId, refundAmount,
							channelOrderDTO.getCurrencyCode(),merchantOrderId);

					
					//add by sch 2016-04-25
					if(settlementAssureFlg)
						assureAmount = 0;		//全部把钱还给基本户 
					//end 2016-04-25
					
					// 先退还保证金，只有全额退款时才扣了保证金
					if(orderAmount == refundAmount){
						doAccounting_500_515(partnerId, orderId, assureAmount,
								settlementCurrencyCode, partnerSettlementRate,merchantOrderId);
					}else{
						logger.info("部分退款，退款失败时，不做保证金退还动作.." + refundOrderNo);
					}
					
					//计算手续费比例
					fee = refundOrderDTO.getPayerFee();//calcRefundFee(settlementAmount, refundAmount, fee);
					logger.info("refundOrderNo:" + refundOrderNo+ ", orderAmount:" + orderAmount + ", refundAmount:" + refundAmount + ", fee:" + fee +", 开始退基本账户！");
					doAccounting_500_514(partnerId, orderId,orderAmount,settlementAmount, assureAmount,refundAmount,
							settlementCurrencyCode,
							partnerSettlementRate, fee,merchantOrderId);
					logger.info("退款失败已清算结束，doAccounting_500_512；doAccounting_500_513；doAccounting_500_515；doAccounting_500_514");
				}
			//}

			
			// 增加可退金额
			tradeOrderDTO.setRefundAmount(tradeOrderDTO.getRefundAmount()
					+ amount);

			logTradeOrderModify(Long.valueOf(refundOrderNo),tradeOrderDTO.getTradeOrderNo(),
					amount,"refund failed");		//add by sch 2016-04-22

			//这里要区分是正常的失败，还是被Excption捕获的非正常失败
			if(ResponseCodeEnum.UNDEFINED_ERROR.getCode().equals(responseCode)){
				refundOrderDTO.setStatus(RefundStatusEnum.TIMEOUT.getCode() + "");
			}else{
				refundOrderDTO.setStatus(RefundStatusEnum.FAIL.getCode() + "");				
			}
		}

		//更新不能清算
		for(PartnerSettlementOrder settlementOrder : settlementOrders){
			int oldSettlementFlag = settlementOrder.getSettlementFlg(); //下面记录日志用 
			
			if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode() 
					&& !ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
				settlementOrder.setAmount(settlementOrder.getAmount()+refundAmount);
			}
			
			//2015年11月7日11:41:12 peiyu.yang 未清算退款的，清算订单状态仍未清算退款,如果是部分退款原来的订单还是要清算的
			if(settlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()&&
					settlementOrder.getAmount().longValue()>0){
				settlementOrder.setSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());
			}
			
			// 原来的代码  2016-04-25 ，因为没有人设置这个 settlementFlagEnum标志==>REFUND ，所以，这段代码，从来就没有启用过 
			//if(settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.REFUND.getCode() 
			//		&& refundAmount != settlementOrder.getOrderAmount() 
			//&& !ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
			//	settlementOrder.setAssureSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());
			//}
			
			
			//全额退款才会发生这个事情  2016-04-25
			if(settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.REFUND.getCode() 
					&& refundAmount == settlementOrder.getOrderAmount() 
			&& !ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
				logger.info("把 AssureSettlementFlag from REFUND==>UNSETTLEMENT");
				settlementOrder.setAssureSettlementFlg(SettlementFlagEnum.UNSETTLEMENT.getCode());
			}
			
			partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);

			//记录日志 add by sch 2016-04-23
			if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
				logSettlementOrderModify(Long.valueOf(refundOrderNo),settlementOrder.getId(),
						Long.valueOf(0),oldSettlementFlag,settlementOrder.getSettlementFlg(),"refund success");
			}
			else{
				//失败 情况下  ,只有在原来是退款状态的情况下，才需要增加日志 
				if( oldSettlementFlag == SettlementFlagEnum.REFUND.getCode()) {
				logSettlementOrderModify(Long.valueOf(refundOrderNo),settlementOrder.getId(),
						refundAmount,oldSettlementFlag,settlementOrder.getSettlementFlg(),"refund failed");
				}
			}
			//end 2016-04-23
		}
		refundOrderDTO.setUpdateDate(new Date());
		refundOrderDTO.setComplateDate(new Date());
		refundOrderService.updateRefundOrderByPkRnTx(refundOrderDTO);

		tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
		// 发送退款通知
		logger.info("发送商户退款通知！--------------------------------------------------------------------------------"
				+ refundOrderDTO.getRefundOrderNo());

		notifyMerchant(refundOrderDTO, responseMsg);
	}
	
	private void doMerchantRefundApplyAccounting(ChannelOrderDTO channelOrder,
			TradeOrderDTO tradeOrderDTO, RefundOrderDTO refundOrderDTO,
			List<PartnerSettlementOrder> settlementOrders,
			Integer settlementSize, Integer firstSettlementFlg,
			long assureAmount, String settlementCurrencyCode,
			String firstPartnerSettlementRate, Integer secondSettlementFlg,
			String secondPartnerSettlementRate, long firstSettlementAmount,
			long fee,String merchantOrderId) throws Exception {

		Long orderAmount = tradeOrderDTO.getOrderAmount();
		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		String orderId = refundOrderDTO.getRefundOrderNo().toString();

		// 如果是全额退款
		if (refundAmount == tradeOrderDTO.getOrderAmount()) {
			logger.info("全额退款处理doMerchantRefundApplyAccounting(),退款订单号" + orderId);
			// 只有一笔结算订单
			if (settlementSize == 1) {

				if (firstSettlementFlg == 1) {// 已清算才扣款
					
					//add by sch 2016-04-25  如果保证金已经归还，则全部改为扣除基本户
					if(!settlementOrders.isEmpty()){
						PartnerSettlementOrder settlementOrder = settlementOrders.get(0); 
						if(settlementOrder.getAssureSettlementFlg() == SettlementFlagEnum.SETTLEMENTED.getCode()){   //已经清算状态，则修改数据
							 assureAmount =0;			
							 logger.info("保证金已经被归还到基本户，我们就不再扣除保证金了 ");  //类似于有些清算订单的的保证金为0
						}
					}
					//end 2016-04-25
					
					//判断基本本户余额是否够扣款，如果不够就不扣款
					long aAmount = new BigDecimal(refundAmount).multiply(
							new BigDecimal(firstPartnerSettlementRate)).longValue();
					long amount = aAmount;
					if ( fee > 0) {
						amount = aAmount - fee;
					}
					Integer acctType = AcctTypeEnum
							.getBasicAcctTypeByCurrency(settlementCurrencyCode);
					AcctAttribDto acctAttribDto = accountQueryService
							.doQueryAcctAttribNsTx(partnerId, acctType);
					if(acctAttribDto!=null && acctAttribDto.getAcctCode()!=null){	
						AcctDto acctDto=acctService.queryAcctByAcctCode(acctAttribDto.getAcctCode());
						if(amount>acctDto.getBalance()){
							throw new MaAcctBalanceException(
								ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户["
										+ acctAttribDto.getAcctCode() + "]余额不足");
						}
					}
					
					// 退保证金  note by sch : 如果assureAmount ==0 ,下面的语句也没啥影响 
					int assureAccountingFlg = doAccounting_500_504(partnerId,
							orderId, assureAmount, settlementCurrencyCode,
							firstPartnerSettlementRate,merchantOrderId);
					if (assureAccountingFlg == 1) {
						// 置保证金已退款
						for (PartnerSettlementOrder settlementOrder : settlementOrders) {
							
							//settlementOrder.setAssureSettlementFlg(3);			//这是原来的代码
							//partnerSettlementOrderService
							//		.updatePartnerSettlementOrder(settlementOrder);							
							
							//add by sch 2016-04-25
							if(settlementOrder.getAssureSettlementFlg()== SettlementFlagEnum.UNSETTLEMENT.getCode()){	//=0 才会把它变到4
								settlementOrder.setAssureSettlementFlg( SettlementFlagEnum.REFUND.getCode());
								partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
							}
							//sch 2016-04-25
						}
					}

					// 扣基本账户
					// note by sch : (refundAmount - assureAmount).  如果assureAmount 被重新设置为0，则下面的字段也是对的
					int basicAccountingFlg = doAccounting_500_503(partnerId,
							orderId, refundAmount - assureAmount,
							settlementCurrencyCode, firstPartnerSettlementRate,
							fee,merchantOrderId);
					
					
					
				}
			} else {// 2笔清算订单
				if (firstSettlementFlg == 1 
						&& secondSettlementFlg == 1
						&& firstPartnerSettlementRate
								.equals(secondPartnerSettlementRate)) {// 两笔都清算，且汇率相同
					// 退保证金
					int assureAccountingFlg = doAccounting_500_504(partnerId,
							orderId, assureAmount, settlementCurrencyCode,
							firstPartnerSettlementRate,merchantOrderId);
					if (assureAccountingFlg == 1) {
						// 置保证金已退款
						for (PartnerSettlementOrder settlementOrder : settlementOrders) {	
							settlementOrder.setAssureSettlementFlg(3);
							partnerSettlementOrderService
									.updatePartnerSettlementOrder(settlementOrder);					
						}
					}

					// 扣基本账户
					int basicAccountingFlg = doAccounting_500_503(partnerId,
							orderId, refundAmount - assureAmount,
							settlementCurrencyCode, firstPartnerSettlementRate,
							fee,merchantOrderId);
				} else if (firstSettlementFlg == 1) {// 第一笔已清算
					// 退保证金
					int assureAccountingFlg = doAccounting_500_504(partnerId,
							orderId, assureAmount, settlementCurrencyCode,
							firstPartnerSettlementRate,merchantOrderId);
					if (assureAccountingFlg == 1) {
						// 置保证金已退款
						for (PartnerSettlementOrder settlementOrder : settlementOrders) {
							settlementOrder.setAssureSettlementFlg(3);
							partnerSettlementOrderService
									.updatePartnerSettlementOrder(settlementOrder);
						}
					}

					// 扣基本账户
					int basicAccountingFlg = doAccounting_500_503(partnerId,
							orderId, refundAmount - assureAmount,
							settlementCurrencyCode, firstPartnerSettlementRate,
							fee,merchantOrderId);
				}

			}

		} else {// 部分退款，不退保证金，全部由基本户出
			logger.info("部分退款处理doMerchantRefundApplyAccounting(),退款订单号" + orderId+",firstSettlementFlg"+firstSettlementFlg+",firstSettlementAmount"+firstSettlementAmount+",refundAmount"+refundAmount);
			if (firstSettlementFlg == 1) {// 第一笔已清算

				if (refundAmount <= firstSettlementAmount) {
					logger.info("部分退款处理doAccounting_500_503,退款订单号" + orderId);	
					//计算手续费比例
					fee = calcRefundFee(settlementOrders.get(0).getAmount(), refundAmount, fee);
					doAccounting_500_503(partnerId, orderId, refundAmount,
							settlementCurrencyCode, firstPartnerSettlementRate,
							fee,merchantOrderId);
				} else {
					throw new AppException("退款异常！");
				}

			}

		}
		logger.info("货币兑换记账处理doMerchantRefundApplyAccounting()开始；doAccounting_500_505；doAccounting_500_506");
		// 货币兑换，取商户结算币种
		doAccounting_500_505(orderId, refundAmount, settlementOrders.get(0)
				.getSettlementCurrencyCode(), firstPartnerSettlementRate,merchantOrderId);

		// 货币兑换，取渠道结算币种
		doAccounting_500_506(orderId, refundAmount,
				channelOrder.getCurrencyCode(), firstPartnerSettlementRate,merchantOrderId);
		logger.info("货币兑换记账处理doMerchantRefundApplyAccounting()开始；doAccounting_500_505；doAccounting_500_506结束");
	}
	
	private void doMerchantRefundHandlerAccounting(
			ChannelOrderDTO channelOrder, TradeOrderDTO tradeOrderDTO,
			RefundOrderDTO refundOrderDTO,
			List<PartnerSettlementOrder> settlementOrders, String responseCode,
			boolean settlementFlg) throws Exception {

		long refundAmount = refundOrderDTO.getRefundAmount();
		Long partnerId = Long.valueOf(refundOrderDTO.getPartnerId());
		String orderId = refundOrderDTO.getRefundOrderNo().toString();
		String settlementCurrencyCode = "";
		String partnerSettlementRate = "";
		String orgCode = channelOrder.getOrgCode();

		// 获取保证金,只有全额退才退保证金
		long assureAmount = 0;
		long fee = 0;
		for (PartnerSettlementOrder settlementOrder : settlementOrders) {
			assureAmount += settlementOrder.getAssureAmount();
			if (null != settlementOrder.getFee()) {
				fee += settlementOrder.getFee();
			}
			settlementCurrencyCode = settlementOrder
					.getSettlementCurrencyCode();
			partnerSettlementRate = settlementOrder.getSettlementRate();
		}

		if (ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {

		} else {

		}

	}

	private void doUpdateSettAmount(long refundAmount,List<PartnerSettlementOrder> settlementOrders,Integer status){
		
		//更新不能清算
		for(PartnerSettlementOrder settlementOrder : settlementOrders){
			settlementOrder.setSettlementFlg(SettlementFlagEnum.REFUND.getCode());
			
			partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrder);
		}
		
	}
	*/
}

/*
 * 测试案例
 * 1)  记录日志失败，爆出异常，实际上 fi.trade_order 并不会增加订单，说明这个定单 
 * 2)  4种手续费组合已经测试过了，正常通过 
 * 3)  保证金已经归还的单子，不扣除手续费，该测试已经通过了。 
 * 
 * 
 * 
 */

