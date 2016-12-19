package com.pay.base.dao.paychain.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.common.enums.EffectiveTypeEnum;
import com.pay.base.dao.paychain.PayChainDAO;
import com.pay.base.dto.PayChainDetailDto;
import com.pay.base.dto.PayChainPaymentDto;
import com.pay.base.dto.PayChainPaymentParamDto;
import com.pay.base.dto.PayChainStatDto;
import com.pay.base.dto.PayChainStatParamDto;
import com.pay.base.dto.PayChainStatRecordDto;
import com.pay.base.model.PayChain;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class PayChainDAOImpl extends BaseDAOImpl implements PayChainDAO {

	@Override
	public Long create(PayChain payChain) {
		return (Long) this.getSqlMapClientTemplate().insert(
				getNamespace().concat("create"), payChain);
	}

	@Override
	public Long generateChainNum() {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getChainNum"));
	}

	public Date getOverDate(int day) {
		return (Date) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getOverDate"), day);
	}

	@Override
	public PayChain getPayChin(String payChainNumber) {

		return (PayChain) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getPayChainByCode"), payChainNumber);
	}

	@Override
	public PayChainStatDto queryPayChainRecords(
			PayChainStatParamDto payChainStatParamDto) {

		int count = queryPayChainRecordsCount(payChainStatParamDto);
		if (count != 0) {// 如果有记录再去查询结果集
			PayChainStatDto payChainStatDto = queryPayChainCountSum(payChainStatParamDto);
			List<PayChainStatRecordDto> list = getSqlMapClientTemplate()
					.queryForList(
							getNamespace().concat("queryPayChainRecords"),
							payChainStatParamDto);
			payChainStatDto.setPayChainStatRecordDtos(list);
			payChainStatDto.setRecordsCount(count);
			return payChainStatDto;
		}
		return new PayChainStatDto();
	}

	@Override
	public PayChainStatDto queryPayChainCountSum(
			PayChainStatParamDto payChainStatParamDto) {
		return (PayChainStatDto) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("queryPayChainCountSum"),
				payChainStatParamDto);
	}

	@Override
	public int queryPayChainRecordsCount(
			PayChainStatParamDto payChainStatParamDto) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("queryPayChainRecords_count"),
				payChainStatParamDto);
	}

	@Override
	public String getMaxChainNum() {
		return (String) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMaxChainNum"));
	}

	@Override
	public int queryPayChainPaymentCount(String payChainNum) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("queryPayChainPayment_count"),
				payChainNum);
	}

	@Override
	public PayChainDetailDto queryPayChainDetail(
			PayChainPaymentParamDto payChainPaymentParamDto) {
		PayChainDetailDto payChainDetailDto = new PayChainDetailDto();
		int count = queryPayChainPaymentCount(payChainPaymentParamDto
				.getPayChainNumber());
		if (count != 0) {
			List<PayChainPaymentDto> list = getSqlMapClientTemplate()
					.queryForList(
							getNamespace().concat("queryPayChainPayment"),
							payChainPaymentParamDto);
			payChainDetailDto.setPayChainPaymentDtos(list);
			payChainDetailDto.setRecordsCount(count);
			// 查询总支付数和总金额
			PayChainStatParamDto payChainStatParamDto = new PayChainStatParamDto();
			payChainStatParamDto.setPayChainNumber(payChainPaymentParamDto
					.getPayChainNumber());
			PayChainStatDto payChainStatDto = queryPayChainCountSum(payChainStatParamDto);
			payChainDetailDto.setRecordsPaySum(payChainStatDto
					.getRecordsPaySum());
			payChainDetailDto.setRecordsAmountSum(payChainStatDto
					.getRecordsAmountSum());
		}
		return payChainDetailDto;
	}

	@Override
	public int updatePayChainStatus(String payChainNumber, int status) {
		PayChain pc = new PayChain();
		pc.setStatus(status);
		pc.setPayChainNumber(payChainNumber);
		return getSqlMapClientTemplate().update(
				this.getNamespace().concat("updatePayChainStatus"), pc);
	}

	@Override
	/**
	 * update t_pay_chain  set EFFECTIVE_DATE = #effectiveDate#,OVERDUE_DATE = CREATE_DATE+#effDay# WHERE pay_chain_number = #payChainNumber#
	 */
	public int updateEffDate(String payNum, int effectiveType) {
		Map<String, Object> pc = new HashMap<String, Object>();
		int days = EffectiveTypeEnum.getDatsByValue(effectiveType);
		pc.put("effectiveDate", effectiveType);
		pc.put("effDay", days);
		pc.put("payChainNumber", payNum);
		return getSqlMapClientTemplate().update(
				this.getNamespace().concat("updateEffectiveDate"), pc);
	}
}
