package com.pay.pe.service.order.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.inf.dto.DtoUtil;
import com.pay.inf.dto.MutableDto;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.pe.dao.order.DealDAO;
import com.pay.pe.dao.order.PaymentOrderDAO;
import com.pay.pe.dto.DealDto;
import com.pay.pe.dto.PaymentOrderDto;
import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.dto.PaymentServiceType;
import com.pay.pe.helper.COMMONORDERSTATUS;
import com.pay.pe.helper.DealStatus;
import com.pay.pe.helper.DealType;
import com.pay.pe.model.Deal;
import com.pay.pe.model.PaymentOrder;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.order.DealService;
import com.pay.pe.service.order.PaymentOrderService;
import com.pay.pe.service.payment.common.LogUtil;
import com.pay.pe.service.paymentservice.PaymentServiceService;

/**
 * 
 */
public class DealServiceImpl extends BaseServiceImpl implements DealService {

	private static final long serialVersionUID = -1490484286412351922L;

	private final Log logger = LogFactory.getLog(getClass());

	private PaymentOrderDAO orderDao;

	private PaymentOrderService paymentOrderService;
	/**
	 * 支付服务服务.
	 */
	private PaymentServiceService paymentService;

	private DtoUtil mainDtoUtil;

	public void setMainDtoUtil(DtoUtil mainDtoUtil) {
		this.mainDtoUtil = mainDtoUtil;
	}

	public PaymentOrderService getPaymentOrderService() {
		return paymentOrderService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	/**
	 * @return Returns the orderDao.
	 */
	public PaymentOrderDAO getOrderDao() {
		return orderDao;
	}

	/**
	 * @param orderDao
	 *            The orderDao to set.
	 */
	public void setOrderDao(PaymentOrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public PaymentServiceService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentServiceService paymentService) {
		this.paymentService = paymentService;
	}

	/**
	 * 判断付款方/收款方是否为个人或者企业会员.
	 * 
	 * @param partyOrgType
	 *            付款方/收款方的机构类型代码
	 * @return boolean 是否为个人或者企业会员
	 */
	private boolean isMember(final Integer partyOrgType) {
		if (null == partyOrgType) {
			return false;
		}
		int partyOrgTypeIntValue = partyOrgType.intValue();
		if (MemberTypeEnum.INDIVIDUL.getCode() == partyOrgTypeIntValue) {
			return true;
		}
		if (MemberTypeEnum.MERCHANT.getCode() == partyOrgTypeIntValue) {
			return true;
		}
		return false;
	}

	/**
	 * 根据交易的支付服务组号取得相应的计费支付服务
	 * 
	 * @param deal
	 *            交易
	 * @return 计费支付服务
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private PaymentServiceDTO initPaymentServiceDto(final DealDto deal)
			throws Exception {
		Map mainIden = new HashMap();
		Map addition = new HashMap();
		addition.put("PaymentServicePkgCode", deal.getPaymentServicePkgCode());

		logger.info(LogUtil.wrapLog("initPaymentServiceDto", "Start", null,
				mainIden, addition, null, null, null));

		// 获取同一组的所有服务列表
		List<PaymentServiceDTO> result = paymentService.getPaymentServiceDtos(
				deal.getPaymentServicePkgCode(),
				PaymentServiceType.BILLING.getValue());
		if (null == result || result.size() == 0) {
			logger.info(LogUtil.wrapLog("initPaymentServiceDto", "Fail", null,
					mainIden, addition, "No payment service found", null,
					"No payment service found"));
			return null;
		}

		PaymentServiceDTO paymentServiceDTO = result.get(0);
		logger.info(LogUtil.wrapLog("initPaymentServiceDto", "Success", null,
				mainIden, addition, null, null, null));
		mainIden = null;
		addition = null;

		return paymentServiceDTO;
	}

	/*
	 * (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	public List<DealDto> findByOrderSeqId(Long orderSeqId) {
		Map mainIden = new HashMap();
		Map addition = new HashMap();
		addition.put("orderSeqId", orderSeqId);

		logger.info(LogUtil.wrapLog("findByOrderSeqId", "Start", null,
				mainIden, addition, null, null, null));

		PaymentOrder order = (PaymentOrder) getOrderDao().findById(orderSeqId);
		List<Deal> result = ((DealDAO) getMainDao()).findByOrderSeqId(order);
		List<DealDto> deals = mainDtoUtil.convert2Dtos(result);

		addition.put("Deals", deals);
		logger.info(LogUtil.wrapLog("findByOrderSeqId", "Success", null,
				mainIden, addition, null, null, null));
		mainIden = null;
		addition = null;

		return deals;
	}

	/*
	 * (non-Javadoc)
	 */
	public DealDto create(DealDto deal) {

		Deal model = (Deal) mainDtoUtil.convert2Model(deal);

		String id = (String) getMainDao().create(model);
		deal.setDealId(id);
		return deal;

	}

	/*
	 * (non-Javadoc)
	 */
	public DealDto modify(DealDto deal) {
		getMainDao().update(deal);
		return deal;
	}

	/*
	 * (non-Javadoc)
	 */
	public int getDealStatus(final String dealId) {
		return ((Deal) getMainDao().findById(dealId)).getDealStatus();
	}

	/*
	 * dev need
	 */
	public DealDto findDealInSameTx(final String dealId) {
		// Deal deal = (Deal)
		// ((DealDao)getMainDao()).findModelInSameTxById(dealId);
		// return (DealDto) mainDtoUtil.convert2Dto(deal);
		return null;
	}

	/*
	 * dev need
	 */
	@SuppressWarnings("unchecked")
	public DealDto saveDealInIsolatedTx(final DealDto dealdto) throws Exception {
		PaymentOrderDto paymentOrderDto = dealdto.getOrder();
		try {
			Long sequenceId = (Long) this.paymentOrderService
					.create(paymentOrderDto);
			paymentOrderDto.setSequenceId(sequenceId);
			dealdto.setOrder(paymentOrderDto);
			dealdto.setOrderSeqId(paymentOrderDto.getSequenceId());
		} catch (Exception e) {
			logger.error("method saveDealInIsolatedTx  save paymentOrder had error:", e);
			if (e.getCause().getMessage().indexOf("ORA-00001") > 0) {
				// 查询交易信息
				paymentOrderDto = paymentOrderService
						.findByOrderIdAndOrderCode(
								paymentOrderDto.getOrderType(),
								paymentOrderDto.getOrderId());
				dealdto.setOrder(paymentOrderDto);
				dealdto.setOrderSeqId(paymentOrderDto.getSequenceId());
			} else {
				throw e;
			}
		}

		String voucherCode = generateVoucherCode(
				paymentOrderDto.getSequenceId(), dealdto.getDealCode(),
				paymentOrderDto.getIsReversed());
		// 如果违反唯一约束，数据库抛出异常，回滚
		dealdto.setVoucherCode(Long.parseLong(voucherCode));
		DealDto result = this.create(dealdto);

		return result;
	}

	/**
	 * 生成凭证号po_id+dealCode+冲正标志
	 * 
	 * @param sequenceId
	 * @param dealType
	 * @param isReversed
	 * @return
	 */
	private String generateVoucherCode(Long sequenceId, Integer dealType,
			boolean isReversed) {
		String voucherCode = sequenceId + "" + dealType + "001";
		return voucherCode;
	}

	/*
	 * (non-Javadoc)
	 */
	// public long countDealAmount(
	// final int dealStatus,
	// final int orderCode,
	// final String memberAcctCode,
	// final Date from,
	// final Date end) {
	// return ((DealDao)getMainDao()).countDealAmount(dealStatus, orderCode,
	// memberAcctCode, from, end);
	// }

	/*
	 * (non-Javadoc)
	 */
	public long generateDealAccountingToken(final String dealId) {
		return ((DealDAO) getMainDao()).getDealAccountingToken(dealId);
	}

	/**
	 * 根据传入的查询条件查找交易列表
	 * 
	 * @param criterias
	 *            查询条件
	 * @return 交易列表
	 * @throws Exception
	 */
	// @SuppressWarnings("unchecked")
	// public List<DealDto> queryDeal(List<Criteria> criterias) throws Exception
	// {
	// List<Deal> deals = getMainDao().findModelsByCriteria(criterias);
	//
	// return mainDtoUtil.convert2Dtos(deals);
	// }

	@SuppressWarnings("unchecked")
	public List<DealDto> queryDeal(List criterias) throws Exception {
		// List<Deal> deals = getMainDao().findModelsByCriteria(criterias);
		// List<Deal> deals = getMainDao().findModelsByCriteria(criterias);
		// return mainDtoUtil.convert2Dtos(deals);
		return null;
	}

	/**
	 * 根据凭证好查找DEAL
	 */
	public DealDto getDealByVoucherNo(Long voucherCode) {

		Deal deal = ((DealDAO) getMainDao()).getDealByVoucherNo(voucherCode);
		return (DealDto) mainDtoUtil.convert2Dto(deal);
	}

	/*
	 * (non-Javadoc)
	 */
	public DealDto getDealAndLocked(String dealId) {
		return (DealDto) mainDtoUtil.convert2Dto(((DealDAO) getMainDao())
				.getDealAndLocked(dealId));
	}

	public DealDto getDealAndLocked(String dealId, DealStatus status) {
		return (DealDto) mainDtoUtil.convert2Dto(((DealDAO) getMainDao())
				.getDealAndLocked(dealId, status));
	}

	/*
	 * (non-Javadoc)
	 */
	public DealDto getDealAndLocked(String dealId, long waitSeconds) {
		return (DealDto) mainDtoUtil.convert2Dto(((DealDAO) getMainDao())
				.getDealAndLocked(dealId, waitSeconds));
	}

	/*
	 * (non-Javadoc)
	 */
	public DealDto getDealAndLocked(String dealId, long waitSeconds,
			DealStatus status) {
		return (DealDto) mainDtoUtil.convert2Dto(((DealDAO) getMainDao())
				.getDealAndLocked(dealId, waitSeconds, status));
	}

	/*
	 * (non-Javadoc)
	 */
	public void changeDealStatus(String dealId, DealStatus oldStatus,
			DealStatus newStatus) {
		changeDealStatus(dealId, oldStatus.getValue(), newStatus.getValue());
	}

	/*
	 * (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	public void changeDealStatus(String dealId, int oldStatus, int newStatus) {
		Map mainIden = new HashMap();
		Map addition = new HashMap();
		addition.put("dealId", dealId);
		addition.put("oldStatus", oldStatus);
		addition.put("newStatus", newStatus);

		logger.info(LogUtil.wrapLog("changeDealStatus", "Start", dealId,
				mainIden, addition, null, null, null));

		DealDto deal = this.findDealInSameTx(dealId);
		deal.setDealStatus(newStatus);
		deal.setDealEndDate(new Date());
		this.update(deal);

		addition.put("deal", deal);
		logger.info(LogUtil.wrapLog("changeDealStatus", "Success", dealId,
				mainIden, addition, null, null, null));
		mainIden = null;
		addition = null;
	}

	/*
	 * (non-Javadoc)
	 */
	@SuppressWarnings("unchecked")
	public MutableDto update(final MutableDto dto) {
		Map mainIden = new HashMap();
		super.update(dto);

		return dto;
	}

	public List<DealDto> findDealByOrgOrderId(final String orgOrderId)
			throws Exception {
		List<Deal> result = ((DealDAO) getMainDao())
				.findDealByOrgOrderId(orgOrderId);
		List<DealDto> deals = mainDtoUtil.convert2Dtos(result);
		return deals;
	}

	@Override
	public DealDto generateDealDto(CalFeeRequest calFeeRequest) {
		PaymentOrderDto paymentOrderDto = new PaymentOrderDto();

		paymentOrderDto.setOrderType(calFeeRequest.getOrderCode());
		paymentOrderDto.setPayMethod(calFeeRequest.getPayMethod());

		paymentOrderDto.setOrderId(calFeeRequest.getOrderId());
		paymentOrderDto.setOrderAmount(calFeeRequest.getOrderAmount());// not
																		// null
		paymentOrderDto.setOrderTime(new Timestamp(System.currentTimeMillis()));// not
																				// null

		paymentOrderDto.setSubmitAcctCode(calFeeRequest.getSubmitAcctCode());

		paymentOrderDto
				.setPayerAcctType(calFeeRequest.getPayerAcctType() == null ? null
						: calFeeRequest.getPayerAcctType());
		paymentOrderDto
				.setPayerAcctCode(calFeeRequest.getPayerMemberAcctCode());

		paymentOrderDto.setPayerOrgType(calFeeRequest.getPayerOrgType());
		paymentOrderDto
				.setPayerOrgCode(calFeeRequest.getPayerOrgCode() == null ? null
						: calFeeRequest.getPayerOrgCode());

		paymentOrderDto.setPayeeAcctType(calFeeRequest.getPayeeAcctType());
		paymentOrderDto
				.setPayeeAcctCode(calFeeRequest.getPayeeMemberAcctCode());

		paymentOrderDto.setPayeeOrgType(calFeeRequest.getPayeeOrgType());
		paymentOrderDto
				.setPayeeOrgCode(calFeeRequest.getPayeeOrgCode() == null ? null
						: calFeeRequest.getPayeeOrgCode());

		paymentOrderDto
				.setOrderStatus(COMMONORDERSTATUS.DealSuccess.getValue());// not
																			// null
		paymentOrderDto.setProductName("");
		paymentOrderDto.setProductNum(1);
		paymentOrderDto.setVersion("1");// not null
		paymentOrderDto.setMemo("");

		DealDto dealDto = new DealDto();
		dealDto.setOrder(paymentOrderDto);// 必填
		// 如果已经计费,可以设置 也可以不要设置,重新算
		// dealDto.setHasCaculatedPrice(true);
		// dealDto.setPayerFee(calFeeReponse.getPayerFee());
		// dealDto.setPayeeFee(calFeeReponse.getPayeeFee());

		dealDto.setOrderTime(paymentOrderDto.getOrderTime());
		dealDto.setDealAmount(paymentOrderDto.getOrderAmount());// 必填 //not null
		dealDto.setDealBeginDate(new Timestamp(System.currentTimeMillis()));// not
																			// null
		dealDto.setDealStatus(DealStatus.created.getValue());// not null
		dealDto.setDealType(DealType.AcctPay.getValue());

		dealDto.setDealCode(calFeeRequest.getDealCode());// 必填
		dealDto.setPaymentServicePkgCode(calFeeRequest
				.getPaymentServicePkgCode());// 可以由 dealcode ,order.orderCode
		// ,order.payMethod 得到

		dealDto.setPayeeFullMemberAccCode(calFeeRequest
				.getPayeeFullMemberAcctCode());// 必填
		dealDto.setPayeeAcctType(paymentOrderDto.getPayeeAcctType());// 必填
		dealDto.setPayeeAcctCode(paymentOrderDto.getPayeeAcctCode()); // 必填
		dealDto.setPayeeOrgCode(paymentOrderDto.getPayeeOrgCode());// 必填
		dealDto.setPayeeOrgType(paymentOrderDto.getPayeeOrgType());// 必填

		dealDto.setPayerFullMemberAccCode(calFeeRequest
				.getPayerFullMemberAcctCode());// //必填 acct表中的acct_code
		dealDto.setPayerAcctType(paymentOrderDto.getPayerAcctType());// 必填
		dealDto.setPayerAcctCode(paymentOrderDto.getPayerAcctCode()); // 必填
		dealDto.setPayerOrgCode(paymentOrderDto.getPayerOrgCode());// 必填
		dealDto.setPayerOrgType(paymentOrderDto.getPayerOrgType());// 必填

		dealDto.setDealBeginDate(new Date());
		dealDto.setTransactionDate(dealDto.getDealBeginDate());

		// dealDto.setPriceStrategyCode(calFeeReponse.getPriceStrategyCode());
		return dealDto;
	}

	@Override
	protected Class getModelClass() {
		return Deal.class;
	}

	@Override
	protected Class getDtoClass() {
		return DealDto.class;
	}

}