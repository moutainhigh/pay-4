/**
 * 
 */
package com.pay.txncore.service.chargeback.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.amountma.dao.IFrozenDao;
import com.pay.poss.amountma.dto.FrozenLogDto;
import com.pay.txncore.commons.BouncedEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.BouncedOrderVO;
import com.pay.txncore.model.ChargeBackConfig;
import com.pay.txncore.model.ChargeBackOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChargeBackOrderServiceImpl implements ChargeBackOrderService {

	private final Log logger = LogFactory.getLog(getClass());
	private BaseDAO chargeBackOrderDAO;
	private BaseDAO chargeBackConfigDAO;
	private TradeOrderService tradeOrderService;
	private MerchantRateService merchantRateService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private CurrencyRateService currencyRateService;
	private AcctService acctService;
	private IFrozenDao frozenLogDao;
	private AccountQueryService accountQueryService;
	private AccountingService accountingService600;
	private AccountingService accountingService601;
	private AccountingService accountingService602;
	private AccountingService accounting_600_601;
	private AccountingService accounting_600_602;
	private AccountingService accounting_600_603;
	private AccountingService accounting_600_604;
	private AccountingService accounting_600_605;
	private AccountingService accounting_600_606;
	private AccountingService accounting_600_607;
	private AccountingService accounting_600_608;
	private AccountingService accounting_600_609;
	private AccountingService accounting_700_700;
	private AccountingService accounting_900_903;
	private AccountingService accounting_900_901;
	private AccountingService accounting_900_902;
	private AccountingService accounting_900_900;
	private AccountingService accounting_600_610;
	private AccountingService accounting_600_618;
	private AccountingService accounting_600_619;
	private AccountingService accounting_600_620;
	

	public void setChargeBackOrderDAO(BaseDAO chargeBackOrderDAO) {
		this.chargeBackOrderDAO = chargeBackOrderDAO;
	}

	public void setChargeBackConfigDAO(BaseDAO chargeBackConfigDAO) {
		this.chargeBackConfigDAO = chargeBackConfigDAO;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}


	public void setAccounting_700_700(AccountingService accounting_700_700) {
		this.accounting_700_700 = accounting_700_700;
	}

	@Override
	public Long addChargeBackOrder(ChargeBackOrder chargeBackOrder) {

		return (Long) chargeBackOrderDAO.create(chargeBackOrder);
	}

	@Override
	public boolean updateChargeBackOrder(ChargeBackOrder chargeBackOrder) {
		return chargeBackOrderDAO.update(chargeBackOrder);
	}

	@Override
	public List<ChargeBackOrder> queryChargeBackOrders(
			ChargeBackOrder chargeBackCondition) {
		return chargeBackOrderDAO.findByCriteria(chargeBackCondition);
	}

	@Override
	public List<ChargeBackOrder> queryChargeBackOrderByAuthorAndTradeDate(
			String author, String tradeDate) {

		ChargeBackOrder chargeBackCondition = new ChargeBackOrder();

		chargeBackCondition.setAuthorisation(author);

		return chargeBackOrderDAO.findByCriteria(chargeBackCondition);
	}

	@Override
	public List<ChargeBackOrder> queryChargeBackOrders(
			ChargeBackOrder chargeBackCondition, Page page) {
		return chargeBackOrderDAO.findByCriteria(chargeBackCondition, page);
	}

	@Override
	public void batchAddChargeBackOrder(List<ChargeBackOrder> chargeBackOrders) {
		List<Object> ids = chargeBackOrderDAO.batchCreate(chargeBackOrders);

		Assert.isTrue(chargeBackOrders.size() == ids.size());
	}
	@Override
	public void batchAddBouncedOrder(List<BouncedOrderVO> chargeBackOrders) {
		List<Object> ids = chargeBackOrderDAO.batchCreate("bouncedcreate",chargeBackOrders);
		
		for (BouncedOrderVO bouncedOrderVO:chargeBackOrders) {
			liquidationRnTx(bouncedOrderVO,true);
		}
		
		Assert.isTrue(chargeBackOrders.size() == ids.size());
	}

	@Override
	public void doChargeProcess(Long orderId, Integer cpType, Integer status,
			String auditOperator, String auditBackMsg, 
			String appealDbRelativePath,String amountType,Integer accountingFlg) {
		//审批状态：0未处理 1已上传资料2已递交银行3申诉失败待复核4申诉失败待复核5申诉失败6申诉成功7不申诉
		//资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
		/*ChargeBackOrder order = new ChargeBackOrder();
		order.setAuditDate(new Date());
		order.setAuditMsg(auditBackMsg);
		order.setAuditOperator(auditOperator);
		order.setCpType(cpType);
		order.setStatus(status);
		order.setOrderId(orderId);
		order.setAccountingFlg(accountingFlg);
		order.setAmountType(amountType);
		order.setAppealDbRelativePath(appealDbRelativePath);
		boolean b = updateChargeBackOrder(order);*/
		List<BouncedOrderVO> bouncedOrderVOs=queryBouncedOrdersByOrderId(orderId.toString());
		if(bouncedOrderVOs!=null && bouncedOrderVOs.size()>0)
		{
			BouncedOrderVO bouncevo=bouncedOrderVOs.get(0);
			
			//已清算的订单
			if(StringUtils.isNotEmpty(bouncevo.getSettlementRate())){
				liquidationRnTx(bouncedOrderVOs.get(0),true);
			}
		
		}

	}

	@Override
	public ChargeBackOrder queryByOrderId(Long orderId) {

		return (ChargeBackOrder) chargeBackOrderDAO.findById(orderId);
	}

	private void doAccounting600(Long orderId, Long memberCode,
			Integer acctType, Long refundAmount, String currencyCode) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(refundAmount);
		accountingDto.setOrderAmount(refundAmount);

		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);

		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingService600.doAccounting(accountingDto);
	}
	

	private void doAccounting601(String bankCode, Long orderId,
			Long refundAmount, String currencyCode) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(refundAmount);
		accountingDto.setOrderAmount(refundAmount);

		accountingDto.setBankCode(bankCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingService601.doAccounting(accountingDto);
	}
	public void accounting_600_601( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_600_601.doAccounting(accountingDto);
	}
	private void accounting_600_602(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_602.doAccounting(accountingDto);
	}
	private void accounting_600_609(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_609.doAccounting(accountingDto);
	}
	private void accounting_600_605(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_600_605.doAccounting(accountingDto);
	}
	private void accounting_600_603(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_603.doAccounting(accountingDto);
	}
	private void accounting_600_604(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_604.doAccounting(accountingDto);
	}
	private void accounting_600_606(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_606.doAccounting(accountingDto);
	}
	private void accounting_600_607(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_607.doAccounting(accountingDto);
	}
	private void accounting_600_608(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		
		accounting_600_608.doAccounting(accountingDto);
	}
	private void accounting_900_900(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_900_900.doAccounting(accountingDto);
	}
	private void accounting_900_901(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_900_901.doAccounting(accountingDto);
	}
	private void accounting_900_902(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_900_902.doAccounting(accountingDto);
	}
	private void accounting_900_903(Long orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_900_903.doAccounting(accountingDto);
	}
	private void doFineAccounting(Long orderId, Long memberCode,
			Integer acctType, BigDecimal fineAmount, String fineExchangeRate,
			String currencyCode) {

		BigDecimal vAmount = fineAmount.multiply(new BigDecimal(
				fineExchangeRate));

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(vAmount.longValue());
		accountingDto.setOrderAmount(vAmount.longValue());

		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);

		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingService602.doAccounting(accountingDto);
	}
	public void accounting_600_610( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_600_610.doAccounting(accountingDto);
	}
	public void accounting_600_618( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_618.doAccounting(accountingDto);
	}
	public void accounting_600_619( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_619.doAccounting(accountingDto);
	}
	private void accounting_600_620(String orderId, Long memberCode,
			Integer acctType, Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(acctType);
		
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_600_620.doAccounting(accountingDto);
	}
	@Override
	public void checkBasicAccounting() {

		// 查询拒付扣款未扣成功记录
		List<ChargeBackOrder> list = new ArrayList<ChargeBackOrder>();

		for (ChargeBackOrder order : list) {

			Long partnerId = Long.valueOf(order.getMemberCode());

			// 查询清算订单，获取结算币种
			PartnerSettlementOrder partnerSettlementOrder = partnerSettlementOrderService
					.querySettlementOrderByTradeOrderNo(partnerId,
							order.getTradeOrderNo());

			String partnerSettlementCurrencyCode = partnerSettlementOrder
					.getSettlementCurrencyCode();

			BigDecimal refundAmount = new BigDecimal(
					order.getChargeBackAmount()).multiply(new BigDecimal(
					partnerSettlementOrder.getSettlementRate()));
			// 拒付记账
			doAccounting600(
					order.getOrderId(),
					partnerId,
					AcctTypeEnum
							.getBasicAcctTypeByCurrency(partnerSettlementCurrencyCode),
					refundAmount.longValue(), partnerSettlementCurrencyCode);
			doAccounting601(order.getOrgCode(), order.getOrderId(),
					refundAmount.longValue(), partnerSettlementCurrencyCode);
		}

	}

	@Override
	public void doFineAccounting() {

		// 查询统计商户拒付率
		Map paraMap = new HashMap();
		List<Map> chargeBackInfos = chargeBackOrderDAO.findByCriteria(
				"queryMerchantDealInfo", paraMap);
		if (null != chargeBackInfos && !chargeBackInfos.isEmpty()) {
			for (Map map : chargeBackInfos) {
				Long memberCode = Long.valueOf(String.valueOf(map
						.get("MEMBERCODE")));
				String month = String.valueOf(map.get("MONTH"));
				Integer chargeBackOrderCnt = Integer.valueOf(String.valueOf(map
						.get("CNT")));
				Long chargeBackOrderTotalCnt = Long.valueOf(String.valueOf(map
						.get("TOTALCNT")));
				BigDecimal percentage = new BigDecimal(String.valueOf(map
						.get("PERCENTAGE")));
				logger.info("charge back summary:");
				logger.info("memberCode:" + memberCode + ",month:" + month
						+ ",chargeBackOrderCnt:" + chargeBackOrderCnt
						+ ",chargeBackOrderTotalCnt:" + chargeBackOrderTotalCnt
						+ ",percentage:" + percentage);

				ChargeBackConfig chargeBackConfig = new ChargeBackConfig();
				chargeBackConfig.setMemberCode(memberCode);
				chargeBackConfig = (ChargeBackConfig) chargeBackConfigDAO
						.findObjectByCriteria("findByCriteria",
								chargeBackConfig);
				// 拒付分析
				if (null != chargeBackConfig) {

					String firstPercent = chargeBackConfig.getFirstPercent();
					String firstCost = chargeBackConfig.getFirstCost();

					// 第一区间
					if (!StringUtil.isEmpty(firstPercent)
							&& percentage
									.compareTo(new BigDecimal(firstPercent)) < 0) {
						if (!StringUtil.isEmpty(firstCost)) {
							paraMap = new HashMap();
							paraMap.put("memberCode", memberCode);
							paraMap.put("month", month);
							List<ChargeBackOrder> list = chargeBackOrderDAO
									.findByCriteria("queryChargebackOrder",
											paraMap);

						}
					}

					String secondPercent = chargeBackConfig.getSecondPercent();
					String thirdPercent = chargeBackConfig.getThirdPercent();
					String fourPercent = chargeBackConfig.getFourPercent();

				}

			}
		}

	}

	public void doAccounting_700_700(String orderId, Long partnerId,
			Long amount, String currencyCode) throws Exception {

		Integer payerAcctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		Integer payeeAcctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);

		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingDto.setPayerAcctType(payerAcctType);
		accountingDto.setPayeeAcctType(payeeAcctType);
		accountingDto.setPayer(partnerId);
		accountingDto.setPayee(partnerId);

		accounting_700_700.doAccounting(accountingDto);
	}
	/**
	 * 新拒付，调单查询
	 */
	@Override
	public List<BouncedOrderVO> queryBouncedOrders(
			BouncedOrderVO bouncedOrderVO, Page page) {
		List<BouncedOrderVO> bouncedOrderVOs=chargeBackOrderDAO.findByCriteria("queryBouncedOrders",bouncedOrderVO, page);
		return bouncedOrderVOs;
	}
	@Override
	public List<BouncedOrderVO> queryBouncedOrdersByOrderId(String orderId) {
		Map<String,Object> map = new HashMap<String,Object>();
				map.put("orderId", orderId);
		return chargeBackOrderDAO.findByCriteria("queryBouncedOrdersTask", map);
	}
	@Override
	public List<BouncedOrderVO> queryBouncedOrders(Date createDate, String[] amountTypes,Integer accountingFlg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createDate", createDate);
		map.put("amountTypes", amountTypes);
		map.put("accountingFlg", accountingFlg);
		return chargeBackOrderDAO.findByCriteria("queryBouncedOrdersTask", map);
	}
	
	@Override
	public List<BouncedOrderVO> queryBouncedOrders(Map map) {
		return chargeBackOrderDAO.findByCriteria("queryBouncedOrdersTask", map);
	}

	@Override
	public void alertStatus(BouncedOrderVO bouncedOrderVO)
	{
		ChargeBackOrder chargeBackOrder =new ChargeBackOrder();
		chargeBackOrder.setOrderId(bouncedOrderVO.getOrderId());
		chargeBackOrder.setStatus(7);
		updateChargeBackOrder(chargeBackOrder);
		if((""+bouncedOrderVO.getCpType()).equals(BouncedEnum.bouncedType0.getType()) 
				|| ((""+bouncedOrderVO.getCpType()).equals(BouncedEnum.bouncedType1.getType()) && (BouncedEnum.gcflag8
						.getType()).equals(bouncedOrderVO.getMerchantCode()) ))
		{
			logger.info("alertStatus：更新超过最晚回复时间的为不申诉,拒付和gc银行调单解冻并扣款");
			chargeBackOrder.setAmountType(BouncedEnum.amountType7.getType());
			bouncedOrderVO.setAmountStatus(BouncedEnum.amountType7.getType());
			liquidationRnTx(bouncedOrderVO,true);
		}
		

		
	}
	@Override
	public void freeFrozenRnTx(BouncedOrderVO bouncedOrderVO)
	{
		// 资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
		// 2,5,8都是一笔拒付的结束,才更新已拒付金额，和可拒付金额，2欺诈交易直接扣款结束 5为申诉成功 8为申诉失败
		Long tradeOrderNo = bouncedOrderVO.getTradeOrderNo();
		TradeOrderDTO tradeOrder = tradeOrderService.queryTradeOrderById(tradeOrderNo);
		BigDecimal tradeAmount = new BigDecimal(tradeOrder.getOrderAmount());
		BigDecimal refundAmount = new BigDecimal(tradeOrder.getRefundAmount());
		BigDecimal overBouncedAmount = new BigDecimal(tradeOrder.getOverBouncedAmount());
		BigDecimal doingBouncedAmount = new BigDecimal(tradeOrder.getDoingBouncedAmount());

		String rate = bouncedOrderVO.getSettlementRate();
		BigDecimal baseAmount1000 =bouncedOrderVO.getBaseAmount().multiply(new BigDecimal(rate)).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal baseAmount =bouncedOrderVO.getBaseAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal("1000"));
		BigDecimal assureAmount1000=bouncedOrderVO.getAssureAmount().multiply(new BigDecimal(rate)).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal assureAmount=bouncedOrderVO.getAssureAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal("1000"));
		BigDecimal amount1000 = baseAmount1000.add(assureAmount1000);

		String amountStatus = bouncedOrderVO.getAmountStatus();
		String cpFlg = String.valueOf(bouncedOrderVO.getCpFlg());
		Long partnerId = Long.valueOf(bouncedOrderVO.getMemberCode());
		String partnerIds = bouncedOrderVO.getMemberCode();
		Long orderId = bouncedOrderVO.getOrderId();
		String sorderId = bouncedOrderVO.getOrderId().toString();
		String settlementCurrencyCode = bouncedOrderVO.getSettlementCurrencyCode();
		String orgCode = bouncedOrderVO.getOrgCode();
		String cpCurrencyCode = bouncedOrderVO.getCpCurrencyCode();

		PartnerSettlementOrder partnerSettlementOrder = partnerSettlementOrderService
				.querySettlementOrderByTradeOrderNo(partnerId, tradeOrderNo);
		Long partnerSettlementOrderNo = partnerSettlementOrder.getId();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		Integer assureSettlementFlg=partnerSettlementOrder.getAssureSettlementFlg();
		
		PartnerSettlementOrder settlementOrderQuery = new PartnerSettlementOrder();
		settlementOrderQuery.setId(partnerSettlementOrderNo);

		ChargeBackOrder chargeBackOrder = new ChargeBackOrder();
		chargeBackOrder.setOrderId(orderId);

		Boolean baseFlag = true;
		Boolean assureFlag = true;
		// 查看基本户和保证金是否够扣
		assureFlag = assureCheckBalance(assureAmount1000, partnerId, settlementCurrencyCode,
				assureFlag);
		baseFlag = baseCheckBalance(baseAmount1000, partnerId, settlementCurrencyCode, baseFlag);

		if (BouncedEnum.amountType4.getType().equals(amountStatus)
				&& BouncedEnum.cpFalg1.getType().equals(cpFlg)) {
			logger.info("全额拒付解冻记账开始：orderId=" + orderId + ",baseAmount=" + baseAmount
					+ ",assureAmount=" + assureAmount + ",amountStatus=" + amountStatus + ",cpFlg="
					+ cpFlg);
			accounting_900_902(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, baseAmount1000,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
			accounting_900_903(orderId, partnerId,
					AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
					assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, assureAmount1000,
					AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode));
			chargeBackOrder.setAmountType(BouncedEnum.amountType6.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("全额拒付解冻记账结束");
			logger.info("全额拒付解冻记账，更新网关订单开始；orderId=" + orderId + ",tradeOrderNo=" + tradeOrderNo);
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			tradeOrderDTO.setRefundAmount(tradeAmount.longValue());
			tradeOrderDTO.setOverBouncedAmount(0L);
			tradeOrderDTO.setDoingBouncedAmount(0L);
			tradeOrderDTO.setUpdateDate(new Date());
			tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			logger.info("全额拒付解冻记账，更新网关订单结束；orderId=" + orderId + ",tradeOrderNo=" + tradeOrderNo);

			logger.info("全额拒付解冻记账，更新清算订单保证金清算状态开始,保证金清算状态更改为0；orderId=" + orderId
					+ ",partnerSettlementOrderNo=" + partnerSettlementOrderNo);
			if(assureSettlementFlg==5){
			settlementOrderQuery.setAssureSettlementFlg(0);
			partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrderQuery);
			}
			logger.info("全额拒付解冻记账，更新清算订单保证金清算状态结束；orderId=" + orderId
					+ ",partnerSettlementOrderNo=" + partnerSettlementOrderNo);
		} else if (BouncedEnum.amountType4.getType().equals(amountStatus)
				&& BouncedEnum.cpFalg2.getType().equals(cpFlg)) {
			logger.info("部分拒付解冻记账开始：orderId=" + orderId + ",baseAmount=" + baseAmount
					+ ",assureAmount=" + assureAmount + ",amountStatus=" + amountStatus + ",cpFlg="
					+ cpFlg);
			accounting_900_902(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, baseAmount1000,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
			chargeBackOrder.setAmountType(BouncedEnum.amountType6.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("部分拒付解冻记账结束");
			logger.info("部分拒付解冻记账，更新网关订单开始；orderId=" + orderId + ",tradeOrderNo=" + tradeOrderNo);
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			tradeOrderDTO.setRefundAmount(refundAmount.add(baseAmount1000).longValue());
			tradeOrderDTO.setOverBouncedAmount(0L);
			tradeOrderDTO.setDoingBouncedAmount(doingBouncedAmount.subtract(baseAmount1000)
					.longValue());
			tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
			tradeOrderDTO.setUpdateDate(new Date());
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			logger.info("部分拒付解冻记账，更新网关订单结束；orderId=" + orderId + ",tradeOrderNo=" + tradeOrderNo);
		}
	}
	@Override
	public List<BouncedOrderVO> queryBouncedOrders(BouncedOrderVO bouncedOrderVO) {
		List<BouncedOrderVO> bouncedOrderVOs=chargeBackOrderDAO.findByCriteria("queryBouncedOrders",bouncedOrderVO);
		return bouncedOrderVOs;
	}
	@Override
	public void liquidationRnTx(BouncedOrderVO bouncedOrderVO,Boolean flag) {
		//资金状态：1直接扣款，2已扣款，3冻结，4已冻结，5解冻，6已解冻，7申诉失败扣款，8申诉失败扣款成功
		//2,5,8都是一笔拒付的结束,才更新已拒付金额，和可拒付金额，2欺诈交易直接扣款结束 5为申诉成功 8为申诉失败
		Long tradeOrderNo=bouncedOrderVO.getTradeOrderNo();
		TradeOrderDTO tradeOrder =tradeOrderService.queryTradeOrderById(tradeOrderNo);
		BigDecimal tradeAmount=new BigDecimal(tradeOrder.getOrderAmount());
		BigDecimal refundAmount=new BigDecimal(tradeOrder.getRefundAmount());
		BigDecimal overBouncedAmount =new BigDecimal(tradeOrder.getOverBouncedAmount());
		BigDecimal doingBouncedAmount =new BigDecimal(tradeOrder.getDoingBouncedAmount());
		
		String rate=bouncedOrderVO.getSettlementRate();
		BigDecimal baseAmount1000 =bouncedOrderVO.getBaseAmount().multiply(new BigDecimal(rate)).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal baseAmount =bouncedOrderVO.getBaseAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal("1000"));
		BigDecimal baseAmountTran1000 =bouncedOrderVO.getBaseAmount();
		BigDecimal assureAmount1000=bouncedOrderVO.getAssureAmount().multiply(new BigDecimal(rate)).setScale(0, BigDecimal.ROUND_HALF_UP);
		BigDecimal assureAmount=bouncedOrderVO.getAssureAmount().multiply(new BigDecimal(rate)).divide(new BigDecimal("1000"));
		BigDecimal assureTran1000=bouncedOrderVO.getAssureAmount();
		BigDecimal amount1000=baseAmount1000.add(assureAmount1000);
	
		String amountStatus=bouncedOrderVO.getAmountStatus();
		String cpFlg=String.valueOf(bouncedOrderVO.getCpFlg());
		Long partnerId=Long.valueOf(bouncedOrderVO.getMemberCode());
		String partnerIds=bouncedOrderVO.getMemberCode();
		Long orderId=bouncedOrderVO.getOrderId();
		String sorderId=bouncedOrderVO.getOrderId().toString();
		String settlementCurrencyCode=bouncedOrderVO.getSettlementCurrencyCode();
		String orgCode=bouncedOrderVO.getOrgCode();
		String cpCurrencyCode=bouncedOrderVO.getCpCurrencyCode();
		
		PartnerSettlementOrder partnerSettlementOrder = partnerSettlementOrderService
				.querySettlementOrderByTradeOrderNo(partnerId,
						tradeOrderNo);
		Long partnerSettlementOrderNo=partnerSettlementOrder.getId();
		Integer assureSettlementFlg=partnerSettlementOrder.getAssureSettlementFlg();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		PartnerSettlementOrder settlementOrderQuery=new  PartnerSettlementOrder();
		settlementOrderQuery.setId(partnerSettlementOrderNo);
		
		
		ChargeBackOrder chargeBackOrder =new ChargeBackOrder();
		chargeBackOrder.setOrderId(orderId);
		
		
		Boolean baseFlag=true;
		Boolean assureFlag=true;
		//查看基本户和保证金是否够扣
		assureFlag = assureCheckBalance(assureAmount1000, partnerId, settlementCurrencyCode,
				assureFlag);
		baseFlag = baseCheckBalance(baseAmount1000, partnerId, settlementCurrencyCode, baseFlag);
		
		
		//欺诈交易，全额拒付直接扣款记账开始
		if( BouncedEnum.amountType1.getType().equals(amountStatus) && BouncedEnum.cpFalg1.getType().equals(cpFlg))
		{
			if(baseFlag && assureFlag)
			{
			logger.info("欺诈交易，全额拒付直接扣款记账开始：orderId="+orderId+",baseAmount="+baseAmount+",assureAmount="+assureAmount+",amountStatus="+amountStatus+",cpFlg="+cpFlg);
			accounting_600_601( sorderId,
					amount1000.longValue(),settlementCurrencyCode,merchantOrderId);
			accounting_600_602(orderId, partnerId,
					AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
					assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			accounting_600_603(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			chargeBackOrder.setAmountType(BouncedEnum.amountType2.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("欺诈交易，全额拒付直接扣款记账结束");
			logger.info("欺诈交易，全额拒付直接扣款记账，更新网关订单开始；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			tradeOrderDTO.setRefundAmount(0L);
			tradeOrderDTO.setDoingBouncedAmount(0L);
			tradeOrderDTO.setOverBouncedAmount(tradeAmount.longValue());
			tradeOrderDTO.setDoingBouncedAmount(0L);
			tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
			tradeOrderDTO.setUpdateDate(new Date());
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			logger.info("欺诈交易，全额拒付直接扣款记账，更新网关订单结束；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
			
			logger.info("欺诈交易，全额拒付直接扣款记账，更新清算订单保证金清算状态开始;保证金清算状态更改为5;orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
			if(assureSettlementFlg==0){
				settlementOrderQuery.setAssureSettlementFlg(5);
				partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrderQuery);
			}
			logger.info("欺诈交易，全额拒付直接扣款记账，更新清算订单保证金清算状态结束；orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
			ChargeBackOrder chargeBackOrder4 = new ChargeBackOrder();
			chargeBackOrder4.setStatus(Integer.valueOf(BouncedEnum.statusAudit5.getType()));;
			chargeBackOrder4.setOrderId(orderId);
			this.updateChargeBackOrder(chargeBackOrder4);
			}else{
				logger.info("拒付登记审批时，金额不够，记账失败，更改状态，转为每日任务循环扣款！");
				if (flag) {
					ChargeBackOrder chargeBackOrder3 = new ChargeBackOrder();
					chargeBackOrder3.setAccountingFlg(1);
					chargeBackOrder3.setOrderId(orderId);
					this.updateChargeBackOrder(chargeBackOrder3);
				}
			}
		}else if(BouncedEnum.amountType1.getType().equals(amountStatus) && BouncedEnum.cpFalg2.getType().equals(cpFlg))
		{
			if (baseFlag) {
				logger.info("欺诈交易，部分拒付直接扣款记账开始：orderId=" + orderId + ",baseAmount=" + baseAmount
						+ ",assureAmount=" + assureAmount + ",amountStatus=" + amountStatus
						+ ",cpFlg=" + cpFlg);
				accounting_600_601(sorderId, amount1000.longValue(), settlementCurrencyCode,
						sorderId);
				accounting_600_604(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				chargeBackOrder.setAmountType(BouncedEnum.amountType2.getType());
				updateChargeBackOrder(chargeBackOrder);
				logger.info("欺诈交易，部分拒付直接扣款记账结束");
				logger.info("欺诈交易，部分拒付直接扣款记账，更新网关订单开始；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
				TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
				tradeOrderDTO.setRefundAmount(refundAmount.subtract(baseAmountTran1000).longValue());
				tradeOrderDTO.setOverBouncedAmount(overBouncedAmount.add(baseAmountTran1000)
						.longValue());
				tradeOrderDTO.setDoingBouncedAmount(doingBouncedAmount.subtract(baseAmountTran1000)
						.longValue());
				
				tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
				tradeOrderDTO.setUpdateDate(new Date());
				tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
				logger.info("欺诈交易，部分拒付直接扣款记账，更新网关订单结束；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
				logger.info("欺诈交易，全额拒付直接扣款记账，更新清算订单保证金清算状态结束；orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
				ChargeBackOrder chargeBackOrder4 = new ChargeBackOrder();
				chargeBackOrder4.setStatus(Integer.valueOf(BouncedEnum.statusAudit5.getType()));;
				chargeBackOrder4.setOrderId(orderId);
				this.updateChargeBackOrder(chargeBackOrder4);
			} else {
				logger.info("拒付登记审批时，金额不够，记账失败，更改状态，转为每日任务循环扣款！");
				if (flag) {
					ChargeBackOrder chargeBackOrder3 = new ChargeBackOrder();
					chargeBackOrder3.setAccountingFlg(1);
					chargeBackOrder3.setOrderId(orderId);
					this.updateChargeBackOrder(chargeBackOrder3);
				}
			}
		}else if(BouncedEnum.amountType3.getType().equals(amountStatus) && BouncedEnum.cpFalg1.getType().equals(cpFlg))
		{
			if(baseFlag && assureFlag)
			{
			logger.info("非欺诈交易，全额拒付冻结记账开始：orderId="+orderId+",baseAmount="+baseAmount+",assureAmount="+assureAmount+",amountStatus="+amountStatus+",cpFlg="+cpFlg);
			accounting_600_601( sorderId,
					amount1000.longValue(),settlementCurrencyCode,sorderId);
			accounting_900_900(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.addFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
			accounting_900_901(orderId, partnerId,
					AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
					assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.addFrozenLogRnTx(partnerIds, assureAmount1000, AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode));
			chargeBackOrder.setAmountType(BouncedEnum.amountType4.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("非欺诈交易，全额拒付冻结记账结束");
			
			logger.info("非欺诈交易，全额拒付冻结记账记账，更新清算订单保证金清算状态开始;保证金清算状态更改为5;orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
			if(assureSettlementFlg==0){
				settlementOrderQuery.setAssureSettlementFlg(5);
				partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrderQuery);
			}
			logger.info("非欺诈交易，全额拒付冻结记账，更新清算订单保证金清算状态结束；orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
			}else{
				logger.info("拒付登记审批时，金额不够，记账失败，更改状态，转为每日任务循环扣款！");
				if (flag) {
					ChargeBackOrder chargeBackOrder3 = new ChargeBackOrder();
					chargeBackOrder3.setAccountingFlg(1);
					chargeBackOrder3.setOrderId(orderId);
					this.updateChargeBackOrder(chargeBackOrder3);
				}
			}
		}else if( BouncedEnum.amountType3.getType().equals(amountStatus) && BouncedEnum.cpFalg2.getType().equals(cpFlg))
		{
			if(baseFlag)
			{	
				logger.info("非欺诈交易，部分拒付冻结记账开始：orderId="+orderId+",baseAmount="+baseAmount+",assureAmount="+assureAmount+",amountStatus="+amountStatus+",cpFlg="+cpFlg);
				accounting_600_601( sorderId,
						amount1000.longValue(),settlementCurrencyCode,sorderId);
				accounting_900_900(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				this.addFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
				chargeBackOrder.setAmountType(BouncedEnum.amountType4.getType());
				updateChargeBackOrder(chargeBackOrder);
				logger.info("非欺诈交易，部分拒付冻结记账结束");
			}
		}else if(BouncedEnum.amountType5.getType().equals(amountStatus) && BouncedEnum.cpFalg1.getType().equals(cpFlg))
		{
			logger.info("全额拒付解冻记账开始：orderId="+orderId+",baseAmount="+baseAmount+",assureAmount="+assureAmount+",amountStatus="+amountStatus+",cpFlg="+cpFlg);
			accounting_900_902(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
			accounting_900_903(orderId, partnerId,
					AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
					assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, assureAmount1000, AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode));
			chargeBackOrder.setAmountType(BouncedEnum.amountType6.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("全额拒付解冻记账结束");
			logger.info("全额拒付解冻记账，更新网关订单开始；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			tradeOrderDTO.setRefundAmount(tradeAmount.longValue());
			tradeOrderDTO.setOverBouncedAmount(0L);
			tradeOrderDTO.setDoingBouncedAmount(0L);
			tradeOrderDTO.setUpdateDate(new Date());
			tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			logger.info("全额拒付解冻记账，更新网关订单结束；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
			
			logger.info("全额拒付解冻记账，更新清算订单保证金清算状态开始,保证金清算状态更改为0；orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
			if(assureSettlementFlg==5){
				settlementOrderQuery.setAssureSettlementFlg(0);
				partnerSettlementOrderService.updatePartnerSettlementOrder(settlementOrderQuery);
			}
			logger.info("全额拒付解冻记账，更新清算订单保证金清算状态结束；orderId="+orderId+",partnerSettlementOrderNo="+partnerSettlementOrderNo);
		}else if(BouncedEnum.amountType5.getType().equals(amountStatus) && BouncedEnum.cpFalg2.getType().equals(cpFlg))
		{
			logger.info("部分拒付解冻记账开始：orderId="+orderId+",baseAmount="+baseAmount+",assureAmount="+assureAmount+",amountStatus="+amountStatus+",cpFlg="+cpFlg);
			accounting_900_902(orderId, partnerId,
					AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
					baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
			this.freeFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
			chargeBackOrder.setAmountType(BouncedEnum.amountType6.getType());
			updateChargeBackOrder(chargeBackOrder);
			logger.info("部分拒付解冻记账结束");
			logger.info("部分拒付解冻记账，更新网关订单开始；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
			TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
			tradeOrderDTO.setRefundAmount(refundAmount.add(baseAmountTran1000).longValue());
			tradeOrderDTO.setOverBouncedAmount(0L);
			tradeOrderDTO.setDoingBouncedAmount(doingBouncedAmount.subtract(baseAmountTran1000).longValue());
			tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
			tradeOrderDTO.setUpdateDate(new Date());
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
			logger.info("部分拒付解冻记账，更新网关订单结束；orderId="+orderId+",tradeOrderNo="+tradeOrderNo);
		}else if(BouncedEnum.amountType7.getType().equals(amountStatus) && BouncedEnum.cpFalg1.getType().equals(cpFlg))
		{
				logger.info("非欺诈交易全额拒付失败时，全额拒付先解冻再扣款，记账开始：orderId=" + orderId + ",baseAmount="
						+ baseAmount + ",assureAmount=" + assureAmount + ",amountStatus="
						+ amountStatus + ",cpFlg=" + cpFlg);
				accounting_900_902(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				this.freeFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
				accounting_900_903(orderId, partnerId,
						AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
						assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				this.freeFrozenLogRnTx(partnerIds, assureAmount1000, AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode));
				accounting_600_605(orderId, partnerId,
						AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode),
						assureAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				accounting_600_606(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				chargeBackOrder.setAmountType(BouncedEnum.amountType8.getType());
				updateChargeBackOrder(chargeBackOrder);
				logger.info("非欺诈交易全额拒付失败时，全额拒付先解冻再扣款，记账结束");
				logger.info("非欺诈交易全额拒付失败记账，更新网关订单开始；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
				TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
				tradeOrderDTO.setRefundAmount(0L);
				tradeOrderDTO.setOverBouncedAmount(tradeAmount.longValue());
				tradeOrderDTO.setDoingBouncedAmount(0L);
				tradeOrderDTO.setUpdateDate(new Date());
				tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
				tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
				logger.info("非欺诈交易全额拒付失败记账，更新网关订单结束；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
		}else if( BouncedEnum.amountType7.getType().equals(amountStatus) && BouncedEnum.cpFalg2.getType().equals(cpFlg))
		{
				logger.info("非欺诈交易部分拒付失败时，部分拒付先解冻再扣款，记账开始：orderId=" + orderId + ",baseAmount="
						+ baseAmount + ",assureAmount=" + assureAmount + ",amountStatus="
						+ amountStatus + ",cpFlg=" + cpFlg);
				accounting_900_902(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				this.freeFrozenLogRnTx(partnerIds, baseAmount1000, AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode));
				accounting_600_607(orderId, partnerId,
						AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode),
						baseAmount1000.longValue(), settlementCurrencyCode,merchantOrderId);
				chargeBackOrder.setAmountType(BouncedEnum.amountType8.getType());
				updateChargeBackOrder(chargeBackOrder);
				logger.info("非欺诈交易部分拒付失败时，部分拒付先解冻再扣款，记账结束");
				logger.info("非欺诈交易部分拒付失败记账，更新网关订单开始；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
				TradeOrderDTO tradeOrderDTO = new TradeOrderDTO();
				tradeOrderDTO.setRefundAmount(refundAmount.subtract(baseAmountTran1000).longValue());
				tradeOrderDTO.setOverBouncedAmount(overBouncedAmount.add(baseAmountTran1000).longValue());
				tradeOrderDTO.setDoingBouncedAmount(doingBouncedAmount.subtract(baseAmountTran1000)
						.longValue());
				tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
				tradeOrderDTO.setUpdateDate(new Date());
				tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
				logger.info("非欺诈交易部分拒付失败记账，更新网关订单结束；orderId=" + orderId + ",tradeOrderNo="
						+ tradeOrderNo);
		}
		
	}
	/**
	 * 冻结过程
	 * @param memberCode
	 * @param amount
	 * @param desc
	 * @param acctType
	 * @return
	 * 2016年5月31日   mmzhang     add
	 */
	public synchronized boolean addFrozenLogRnTx(String memberCode, BigDecimal amount,
			Integer acctType) {
		// 得到最新的数据库的金额
		BigDecimal blance = null;
		String acctCode=null;
		try {
			BalancesDto bo = accountQueryService
					.doQueryBalancesNsTx(Long.valueOf(memberCode),acctType);
			
			blance = BigDecimal.valueOf(bo.getBalance());
			acctCode = bo.getAcctCode();
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询memberCode时报错：" + memberCode, e);
			return false;
		}

		BigDecimal amountLong = amount.divide(new BigDecimal("1000"));
		// 通过验证以后做更新操作
		logger.info("冻结操作开始----------------------");
		// 添加冻结操作addFrozenAmount与记账操作doAccounting_900_900都更新t_acct账户冻结金额和账户余额造成死锁，这里直接用记账
		boolean isUpdateOk=false;
		try {
			logger.info("冻结操作更新冻结金额----------------------");
			isUpdateOk =frozenLogDao.addFrozenAmount(Long.valueOf(memberCode), amountLong,acctCode)==1;
			logger.info("冻结金额：" + (isUpdateOk ? "成功" : "失败") + "，但是未提交");
		} catch (Exception e) {
			logger.info("冻结记账失败----------------------");
			e.printStackTrace();
		}
		return isUpdateOk;

	}
	public synchronized boolean freeFrozenLogRnTx(String memberCode, BigDecimal amount,Integer acctType) {

		// 得到最新的数据库的金额
		BigDecimal blance = null;
		String acctCode=null;
		try {
			BalancesDto bo = accountQueryService
					.doQueryBalancesNsTx(Long.valueOf(memberCode),acctType);
			
			acctCode = bo.getAcctCode();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询memberCode时报错：" + memberCode, e);
			return false;
		}
		// 得到最新的blance
		logger.info("解冻操作开始----------------------");
		boolean isUpdateOk=false;
		try {
			logger.info("解冻开始记账成功更新冻结金额----------------------");
			isUpdateOk = frozenLogDao.freeFrozenAmount(Long.valueOf(memberCode),amount.divide(new BigDecimal("1000")),acctCode) == 1;
		} catch (Exception e) {
			logger.info("解冻记账失败----------------------");
			e.printStackTrace();
		}
		return isUpdateOk;

	}
	
	public Boolean baseCheckBalance(BigDecimal baseAmount1000, Long partnerId,
			String settlementCurrencyCode, Boolean baseFlag) {
		Integer acctTypeBase = AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto1 = accountQueryService.doQueryAcctAttribNsTx(partnerId, acctTypeBase);
		if(acctAttribDto1!=null && acctAttribDto1.getAcctCode()!=null){	
			AcctDto acctDto;
			try {
				acctDto = acctService.queryAcctByAcctCode(acctAttribDto1.getAcctCode());
				if(baseAmount1000.longValue()>acctDto.getBalance()){
					baseFlag=false;
					logger.info("拒付，基本户余额不够 " + baseAmount1000.longValue() + acctDto.getBalance());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		return baseFlag;
	}

	private Boolean assureCheckBalance(BigDecimal assureAmount1000, Long partnerId,
			String settlementCurrencyCode, Boolean assureFlag) {
		Integer acctTypeAssure = AcctTypeEnum.getGuaranteeAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(partnerId, acctTypeAssure);
		if(acctAttribDto!=null && acctAttribDto.getAcctCode()!=null){	
			AcctDto acctDto;
			try {
				acctDto = acctService.queryAcctByAcctCode(acctAttribDto.getAcctCode());
				if(assureAmount1000.longValue()>acctDto.getBalance()){
					assureFlag=false;
					logger.info("拒付，保证余户余额不够 " + assureAmount1000.longValue() + acctDto.getBalance());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		return assureFlag;
	}
	
	/**
	 * 拒付罚款 
	 * delin
	 * 2016年7月26日14:09:53
	 */
	@Override
	public void doFineAccounting(Map<String, Object> map) {
		String bouncedFineNo= map.get("bouncedFineNo").toString();
		Long fineAmount=Long.valueOf(map.get("fineAmount").toString());
		Long partnerId=Long.valueOf(map.get("partnerId").toString());
		String currencyCode=map.get("currencyCode").toString();//USD 配置规则的时候计算出来的币种和 金额
	
		Long settlementAmount=Long.valueOf(map.get("settlementAmount").toString());//转换成实际扣款的金额，根据 商户基本信息哪里配置的拒付罚款金额币种进行金额的转换 取清算汇率
		String bouncedCurrencyCode=map.get("bouncedCurrencyCode").toString();
		
		this.accounting_600_610(bouncedFineNo, fineAmount, currencyCode, "--");
		this.accounting_600_618(bouncedFineNo, fineAmount, currencyCode, "--");
		this.accounting_600_619(bouncedFineNo, settlementAmount, bouncedCurrencyCode, "--");
		this.accounting_600_620(bouncedFineNo, partnerId,
				AcctTypeEnum.getBasicAcctTypeByCurrency(bouncedCurrencyCode),
				settlementAmount, bouncedCurrencyCode,"--");
	}
	
	
	/**
	 * 通过状态统计数量
	 */
	@Override
	public int countChargeBackByStatus(Map paraMap) {
		return chargeBackOrderDAO.countByCriteria("countChargeBackByStatus", paraMap);
	}

	public void setAccounting_600_601(AccountingService accounting_600_601) {
		this.accounting_600_601 = accounting_600_601;
	}

	public void setAccounting_600_602(AccountingService accounting_600_602) {
		this.accounting_600_602 = accounting_600_602;
	}

	public void setAccounting_600_603(AccountingService accounting_600_603) {
		this.accounting_600_603 = accounting_600_603;
	}

	public void setAccounting_600_604(AccountingService accounting_600_604) {
		this.accounting_600_604 = accounting_600_604;
	}

	public void setAccounting_600_605(AccountingService accounting_600_605) {
		this.accounting_600_605 = accounting_600_605;
	}

	public void setAccounting_600_606(AccountingService accounting_600_606) {
		this.accounting_600_606 = accounting_600_606;
	}

	public void setAccounting_600_607(AccountingService accounting_600_607) {
		this.accounting_600_607 = accounting_600_607;
	}

	public void setAccounting_600_608(AccountingService accounting_600_608) {
		this.accounting_600_608 = accounting_600_608;
	}

	public void setAccounting_600_609(AccountingService accounting_600_609) {
		this.accounting_600_609 = accounting_600_609;
	}

	public void setAccountingService600(AccountingService accountingService600) {
		this.accountingService600 = accountingService600;
	}

	public void setAccountingService601(AccountingService accountingService601) {
		this.accountingService601 = accountingService601;
	}

	public void setAccountingService602(AccountingService accountingService602) {
		this.accountingService602 = accountingService602;
	}

	public void setAccounting_900_903(AccountingService accounting_900_903) {
		this.accounting_900_903 = accounting_900_903;
	}

	public void setAccounting_900_901(AccountingService accounting_900_901) {
		this.accounting_900_901 = accounting_900_901;
	}

	public void setAccounting_900_902(AccountingService accounting_900_902) {
		this.accounting_900_902 = accounting_900_902;
	}

	public void setAccounting_900_900(AccountingService accounting_900_900) {
		this.accounting_900_900 = accounting_900_900;
	}

	public AccountQueryService getAccountQueryService() {
		return accountQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public IFrozenDao getFrozenLogDao() {
		return frozenLogDao;
	}

	public void setFrozenLogDao(IFrozenDao frozenLogDao) {
		this.frozenLogDao = frozenLogDao;
	}
	public void setAccounting_600_610(AccountingService accounting_600_610) {
		this.accounting_600_610 = accounting_600_610;
	}


	public void setAccounting_600_618(AccountingService accounting_600_618) {
		this.accounting_600_618 = accounting_600_618;
	}

	public void setAccounting_600_619(AccountingService accounting_600_619) {
		this.accounting_600_619 = accounting_600_619;
	}

	public void setAccounting_600_620(AccountingService accounting_600_620) {
		this.accounting_600_620 = accounting_600_620;
	}
@Override
	public Long bouncedKeyQuery() {
		return (Long)chargeBackOrderDAO.findObjectByCriteria("bouncedKeyQuery", null);
	}
	
}
