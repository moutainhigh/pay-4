package com.pay.pe.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.inf.dao.impl.DefaultDAO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.DealDto;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.CalFeeService;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;
import com.pay.pe.service.accounting.AccountEntryService;
import com.pay.pe.service.order.DealService;
import com.pay.pe.service.order.PaymentOrderService;

public class PEServiceImpl implements PEService {

	/** Logger. */
	private Log logger = LogFactory.getLog(getClass());
	
	// TODO 是否要加上更新时间
	private String orderUniqueSql = "UPDATE PE.DEAL D  SET D.DEAL_STATUS = 1 , DEAL_END_DATE=sysdate WHERE D.DEAL_STATUS = '0' AND DEAL_ID = ?";

	private CalFeeService calFeeService;

	/** Deal Service. */
	private DealService dealService;

	/** 查询分录(充正/取消用). */
	private AccountEntryService entryService;

	/**
	 * Payment Order Service.
	 */
	private PaymentOrderService paymentOrderService;

	private DefaultDAO defaultDao;

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setCalFeeService(CalFeeService calFeeService) {
		this.calFeeService = calFeeService;
	}

	public void setEntryService(AccountEntryService entryService) {
		this.entryService = entryService;
	}

	public void setDealService(DealService dealService) {
		this.dealService = dealService;
	}

	public void setDefaultDao(DefaultDAO defaultDao) {
		this.defaultDao = defaultDao;
	}

	@Override
	public PaymentResponseDto processPayment(PaymentReqDto paymentReqDto) {

		if (paymentReqDto == null)
			return null;

		CalFeeRequest calFeeRequest = new CalFeeRequest();

		if (paymentReqDto.isHasCaculatedPrice()) {
			paymentReqDto.setHasCaculatedPayeePrice(paymentReqDto
					.isHasCaculatedPrice());
			paymentReqDto.setHasCaculatedPayerPrice(paymentReqDto
					.isHasCaculatedPrice());
		}
		BeanUtils.copyProperties(paymentReqDto, calFeeRequest);
		PaymentResponseDto calFeeReponseDto = new PaymentResponseDto();

		CalFeeReponse fee = calFeeService.calculateFeeDetail(calFeeRequest);
		if (fee == null)
			return null;

		BeanUtils.copyProperties(fee, calFeeReponseDto);

		calFeeReponseDto.setPaymentReq(paymentReqDto);
		if (fee.getCalFeeDetails() != null) {
			List<PaymentDetailDto> res = new ArrayList<PaymentDetailDto>();
			calFeeReponseDto.setPaymentDetails(res);
			for (CalFeeDetail tmp : fee.getCalFeeDetails()) {
				PaymentDetailDto paymentDetail = new PaymentDetailDto();
				BeanUtils.copyProperties(tmp, paymentDetail);
				res.add(paymentDetail);
			}
		}
		return calFeeReponseDto;
	}

	@Override
	public PaymentResponseDto processVoucherCode(
			PaymentResponseDto paymentResponseDto) {
		PaymentReqDto paymentReqDto = paymentResponseDto.getPaymentReq();
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		BeanUtils.copyProperties(paymentReqDto, calFeeRequest);
		List<PaymentDetailDto> paymentResponseList = paymentResponseDto
				.getPaymentDetails();

		// 生成dealDto对象
		DealDto deal = dealService.generateDealDto(calFeeRequest);

		// 设置费用信息
		deal.setHasCaculatedPayeePrice(false);
		deal.setHasCaculatedPayerPrice(false);

		// 保存deal信息
		try {
			deal = dealService.saveDealInIsolatedTx(deal);
		} catch (Exception e) {
			this.logger.error("订单重复提交......");
			// TODO 如果是重复的异常，直接返回，否则抛出异常
			// 保存失败
			// throw new
			// PEBisnessRuntimeException(ErrorCodeType.SAVE_DEAL_ERROR);
		}
		// 设置分录的信息
		for (PaymentDetailDto detail : paymentResponseList) {
			detail.setVouchercode(deal.getVoucherCode());
		}
		paymentResponseDto.setPaymentDetails(paymentResponseList);
		paymentResponseDto.setVoucherCode(deal.getVoucherCode());
		return paymentResponseDto;
	}

	@Override
	public PaymentResponseDto caculateFee(PaymentReqDto paymentReqDto) {
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		BeanUtils.copyProperties(paymentReqDto, calFeeRequest);
		PaymentResponseDto calFeeReponseDto = new PaymentResponseDto();

		CalFeeReponse fee = calFeeService.caculateFee(calFeeRequest);

		fee.setCalFeeRequest(calFeeRequest);
		BeanUtils.copyProperties(fee, calFeeReponseDto);
		calFeeReponseDto.setPaymentReq(paymentReqDto);
		return calFeeReponseDto;
	}

	@Override
	public boolean accounting(PaymentResponseDto paymentResponse)
			throws Exception {
		// CalFeeReponse calFeeReponse = new CalFeeReponse();
		// BeanUtils.copyProperties(paymentResponse, calFeeReponse);
		if (paymentResponse == null) {
			logger.error("calFeeReponse == null");
			return false;
		}
		PaymentReqDto paymentReq = paymentResponse.getPaymentReq();
		if (null == paymentReq) {
			logger.error("calFeeReponse.getCalFeeRequest() == null  "
					+ paymentResponse.toString());
			return false;
		}
		if (null == paymentResponse.getVoucherCode()) {
			logger.error("paymentResponse.getVoucherCode() == null :  "
					+ paymentResponse.toString());
			return false;
		}
		List<PaymentDetailDto> calFeeDetails = paymentResponse
				.getPaymentDetails();
		if (calFeeDetails == null || calFeeDetails.size() == 0) {
			logger.error("calFeeReponse.getCalFeeDetails() == null");
			return false;
		}

		long crTotal = 0L;
		long drTotal = 0L;
		for (PaymentDetailDto calFeeDetail : calFeeDetails) {
			if (calFeeDetail.getCrdr().intValue() == 1) {
				drTotal = drTotal + calFeeDetail.getValue();
			}
			if (calFeeDetail.getCrdr().intValue() == 2) {
				crTotal = crTotal + calFeeDetail.getValue();
			}
		}
		if (crTotal != drTotal) {
			logger.error("paymentResponse.getVoucherCode "
					+ paymentResponse.getVoucherCode()
					+ "accountingCalFeeReponse crTotal = " + crTotal
					+ "  drTotal=  " + drTotal + " [crTotal != drTotal]  "
					+ paymentResponse.toString());
			return false;
		}

		DealDto dealDto = dealService.getDealByVoucherNo(paymentResponse
				.getVoucherCode());
		if (dealDto == null) {
			logger.error("paymentResponse.getVoucherCode is "
					+ paymentResponse.getVoucherCode() + " get dealDto is null");
			return false;
		} else if (dealDto.getDealStatus() != null
				&& dealDto.getDealStatus() == 1) {
			return true;
		}

		int hasUpdateDeal = defaultDao.getJdbcTemplate().update(orderUniqueSql,
				new Object[] { dealDto.getDealId() });
		// 判断是否已经记账，记账则返回成功
		if (hasUpdateDeal == 0) {
			// 查询一下状态,如果成功返回true,没有或者失败都返回false
			DealDto dealTwice = dealService.getDealByVoucherNo(paymentResponse
					.getVoucherCode());
			if (dealTwice == null)
				return false;
			if (dealTwice.getDealStatus() != null
					&& dealDto.getDealStatus() == 1)
				return true;
			logger.info("calFeeRequest.getOrderId()=" + paymentReq.getOrderId()
					+ "calFeeRequest.getDealCode()=" + paymentReq.getDealCode()
					+ " exist!");
			return false;
		}
		logger.info("accountingCalFeeReponse save paymentOrderDto ="
				+ paymentReq.getOrderId());

		String dealId = dealDto.getDealId();
		Date createdate = new Date();
		List<AccountEntryDTO> entries = new ArrayList<AccountEntryDTO>();
		for (PaymentDetailDto calFeeDetail : calFeeDetails) {
			AccountEntryDTO entry = new AccountEntryDTO();
			BeanUtils.copyProperties(calFeeDetail, entry);
			entry.setDealId(dealId);
			// 这里一般是不会为null 这里的值也不应该为null
			if (entry.getCreatedate() == null) {
				entry.setCreatedate(createdate);
			}
			entries.add(entry);
		}
		entryService.insertAccountEntrys(entries,
				paymentResponse.getVoucherCode());
		return true;
	}

	@Override
	public List<AccountEntryDTO> getAccountEntryByOrderId(String orderId) {
		return entryService.getAccountEntryByOrderId(orderId);
	}

	@Override
	public int getMaBalanceBy(String acctCode, Integer crdr) {
		return this.calFeeService.getMaBalanceBy(acctCode, crdr);
	}

}
