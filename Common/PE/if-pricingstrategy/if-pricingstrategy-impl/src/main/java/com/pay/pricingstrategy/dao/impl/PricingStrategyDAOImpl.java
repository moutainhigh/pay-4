package com.pay.pricingstrategy.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.helper.EFFECTIVEON;
import com.pay.pricingstrategy.model.PricingStrategy;
import com.pay.pricingstrategy.model.PricingStrategyDetail;
import com.pay.pricingstrategy.model.PricingStrategyDetailReport;
import com.pay.pricingstrategy.util.PricingStrategyUtil;
import com.pay.util.DateUtil;

public class PricingStrategyDAOImpl extends BaseDAOImpl implements
		PricingStrategyDAO {

	@Override
	public PricingStrategy addPricingStrategy(PricingStrategy pricingStrategy) {
		long id = (Long) super.create(pricingStrategy);
		if (id != 0L) {
			PricingStrategy ps = this.getPricingStrategy(id);
			return ps;
		}
		return pricingStrategy;
	}

	@Override
	public PricingStrategyDetail addPricingStrategyDetail(
			PricingStrategyDetail pricingDetail) {
		Long id = (Long) this.getSqlMapClientTemplate().insert(
				this.getNamespace() + "createPricingStrategyDetail",
				pricingDetail);
		if (id != null && id != 0L) {
			PricingStrategyDetail detail = this.getPricingStrategyDetail(id);
			return detail;
		}
		return pricingDetail;
	}

	@Override
	public PricingStrategy changePricingStrategy(PricingStrategy pricingStrategy) {
		this.update(pricingStrategy);
		PricingStrategy ps = this.getPricingStrategy(pricingStrategy
				.getPriceStrategyCode());
		return ps;
	}

	@Override
	public PricingStrategyDetail changePricingStrategyDetail(
			PricingStrategyDetail pricingDetail) {
		this.getSqlMapClientTemplate().update(
				this.getNamespace() + "updatePricingStrategyDetail",
				pricingDetail);
		return this.getPricingStrategyDetail(pricingDetail
				.getPriceStrategyDetailCode());
	}

	@Override
	public List<PricingStrategy> getAllGlobalPricingStrategy(
			Integer paymentServiceCode, Date date) {
		return getPricingStrategyByParam(paymentServiceCode, null, null, date);
	}

	@Override
	public List<PricingStrategy> getAllPricingStrategyByDTO(Map querymap) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<PricingStrategy> getAllPricingStrategyByDTO(
			PricingStrategy pricingStrategy, Date date) {
		List<PricingStrategy> list = this.findByTemplate(
				"getAllGlobalPricingStrategyByExample", pricingStrategy);
		List<PricingStrategy> result = new ArrayList<PricingStrategy>();
		if (date != null) {
			for (PricingStrategy ps : list) {
				if (PricingStrategyUtil.isNullDate(ps.getValidDate())) {
					continue;
				}

				if (PricingStrategyUtil.isNullDate(ps.getInvalidDate())
						|| (ps.getInvalidDate().getTime() == getDate(1900, 1, 1)
								.getTime())
						&& (ps.getValidDate().equals(date) || ps.getValidDate()
								.before(date))) {
					result.add(ps);
					continue;
				}

				if ((ps.getValidDate().before(date))
						&& ps.getInvalidDate().after(date)) {
					result.add(ps);
				}
			}
		} else {
			result = list;
		}
		return result;
	}

	@Override
	public List<PricingStrategy> getAllPricingStrategyByPSC(
			Integer paymentServiceCode) {
		// return getPricingStrategyByParam(paymentServiceCode, null, null,
		// null);
		PricingStrategy pricingStrategy = new PricingStrategy();
		pricingStrategy.setPaymentServiceCode(paymentServiceCode);
		return getAllPricingStrategyByDTO(pricingStrategy, null);
	}

	@Override
	public List<PricingStrategyDetail> getAllPricingStrategyDetail(
			Long pricingStrategyCode, Long transAmount,
			Integer terminalTypeCode, String reservedCode) {
		return this.getAllPricingStrategyDetailByParam(pricingStrategyCode,
				transAmount, terminalTypeCode, reservedCode);
	}

	@Override
	public List<PricingStrategyDetail> getAllPricingStrategyDetailByPricingStrategyCode(
			Long pricingStrategyCode) {
		return this.getAllPricingStrategyDetailByParam(pricingStrategyCode,
				null, null, null);
	}

	@Override
	public List<PricingStrategyDetail> getAllPricingStrategyDetailByParam(
			Long pricingStrategyCode, Long transAmount,
			Integer terminalTypeCode, String reservedCode) {
		PricingStrategyDetail para = new PricingStrategyDetail();
		para.setPriceStrategyCode(pricingStrategyCode);
		para.setTerminalTypeCode(terminalTypeCode);
		para.setReservedCode(reservedCode);
		List<PricingStrategyDetail> list = this.findByTemplate(
				"getAllPricingStrategyDetailByExample", para);
		List<PricingStrategyDetail> result = new ArrayList<PricingStrategyDetail>();
		if (transAmount != null) {
			for (PricingStrategyDetail detail : list) {

				// if ((detail.getRangFrom() == null || detail.getRangFrom() <=
				// transAmount)
				// && (detail.getRangTo() == null || detail.getRangTo() >
				// transAmount)) {
				// result.add(detail);
				// }
				if (PricingStrategyUtil.isBetweenEquals(detail.getRangFrom(),
						transAmount, detail.getRangTo())) {
					result.add(detail);
				}

			}
		} else {
			result = list;
		}
		return result;
	}

	@Override
	public PricingStrategy getPricingStrategy(Long priceStrategyCode) {

		return (PricingStrategy) this.findObjectByTemplate("selectById",
				priceStrategyCode);
	}

	@Override
	public List<PricingStrategy> getPricingStrategyByMember(
			Integer paymentServiceCode, Long memberCode, Date date) {
		return getPricingStrategyByParam(paymentServiceCode, null, memberCode,
				date);
	}

	@Override
	public List<PricingStrategy> getPricingStrategyByParam(
			Integer paymentServiceCode, Integer serviceLevelCode,
			Long memberCode, Date date) {

		PricingStrategy para = new PricingStrategy();
		para.setPaymentServiceCode(paymentServiceCode);
		para.setServiceLevelCode(serviceLevelCode);
		para.setMemberCode(memberCode);
		if (null == serviceLevelCode && null == memberCode) {
			para.setEffectiveOn(EFFECTIVEON.GLOBLE.getValue());
		}
		List<PricingStrategy> list = this.findByTemplate(
				"getAllGlobalPricingStrategyByExample", para);
		List<PricingStrategy> result = new ArrayList<PricingStrategy>();
		if (date != null) {
			for (PricingStrategy ps : list) {

				// if ((isNullDate(ps.getValidDate()) || ps.getValidDate()
				// .before(date))
				// && (isNullDate(ps.getInvalidDate()) || ps.getInvalidDate()
				// .after(date))) {
				// result.add(ps);
				// }
				// 有效开始日期 为空 忽略
				if (PricingStrategyUtil.isNullDate(ps.getValidDate())) {
					continue;
				}
				// 无效日期为空 或为1900, 1, 1 或 data<=
				if (PricingStrategyUtil.isNullDate(ps.getInvalidDate())
						|| (ps.getInvalidDate().getTime() == getDate(1900, 1, 1)
								.getTime())
						&& (ps.getValidDate().equals(date) || ps.getValidDate()
								.before(date))) {
					result.add(ps);
					continue;
				}

				// Add By scott.ling 如果是月累计的话，则根据yyyyMM来判断区间
				// 如果时间为1900年，则默认为9999年
				if (CACULATEMETHOD.ACCUMULATED.getValue() == ps
						.getCaculateMethod()) {
					date = convertDateByOffset(date, 1, 1);
					String dateStr = DateUtil.formatDateTime("yyyyMM", date);
					String validDateStr = DateUtil.formatDateTime("yyyyMM",
							ps.getValidDate());
					String invalidDateStr = "999912";

					if (ps.getInvalidDate().getTime() != getDate(1900, 1, 1)
							.getTime()) {
						invalidDateStr = DateUtil.formatDateTime("yyyyMM",
								convertDateByOffset(ps.getInvalidDate(), 1, 0));
					}
					if ((dateStr.compareTo(validDateStr)) >= 0
							&& dateStr.compareTo(invalidDateStr) <= 0) {
						result.add(ps);
					}
				} else {
					if ((ps.getValidDate().before(date))
							&& ps.getInvalidDate().after(date)) {
						result.add(ps);
					}
				}
			}
		} else {
			result = list;
		}
		return result;
	}

	private Date convertDateByOffset(Date date, int moffset, int doffset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (moffset > 0) {
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - moffset);
		}
		if (doffset > 0) {
			calendar.set(Calendar.DAY_OF_MONTH, doffset);
		}
		return calendar.getTime();
	}

	@Override
	public List<PricingStrategy> getPricingStrategyByServiceLevel(
			Integer paymentServiceCode, Integer serviceLevelCode, Date date) {
		return getPricingStrategyByParam(paymentServiceCode, serviceLevelCode,
				null, date);
	}

	@Override
	public List<PricingStrategy> getPricingStrategyByServiceLevel(
			Integer servicelevel) {
		return getPricingStrategyByParam(null, servicelevel, null, null);
	}

	@Override
	public PricingStrategyDetail getPricingStrategyDetail(
			Long pricingStrategyDetailCode) {

		return (PricingStrategyDetail) this.findObjectByTemplate(
				"selectPriceStrategyDetailById", pricingStrategyDetailCode);
	}

	@Override
	public void removePricingStrategy(Long pricingStrategyCode) {
		this.delete(pricingStrategyCode);

	}

	@Override
	public List<PricingStrategyDetail> getPricingStrategyDetailByReservedCode(
			Long pricingStrategyCode, Long transAmount,
			Integer terminalTypeCode, String reservedCode) {
		PricingStrategyDetail para = new PricingStrategyDetail();
		para.setPriceStrategyCode(pricingStrategyCode);
		para.setTerminalTypeCode(terminalTypeCode);
		para.setReservedCode(reservedCode);
		List<PricingStrategyDetail> list = this.findByTemplate(
				"getPricingStrategyDetailByReservedCode", para);
		List<PricingStrategyDetail> result = new ArrayList<PricingStrategyDetail>();
		if (transAmount != null) {
			for (PricingStrategyDetail detail : list) {

				if (PricingStrategyUtil.isBetweenEquals(detail.getRangFrom(),
						transAmount, detail.getRangTo())) {
					result.add(detail);
				}

			}
		} else {
			result = list;
		}
		return result;
	}

	@Override
	public void removePricingStrategyDetail(Long priceStrategyDetailCode) {
		this.getSqlMapClientTemplate().delete(
				this.getNamespace() + "deletePriceStrategyDetail",
				priceStrategyDetailCode);

	}

	// private boolean isNullDate(Timestamp ts){
	// if(ts == null || ts.getNanos() == 0) return true;
	// return false;
	// }

	private static Date getDate(final int year, final int month, final int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	@Override
	public List<PricingStrategyDetailReport> getAllPricingStrategyDetailByPSC(
			Integer paymentServiceCode) {
		HashMap para= new HashMap();
		para.put("paymentServiceCode", paymentServiceCode);
		List<PricingStrategyDetailReport> list=this.findByTemplate(
				"getAllPricingStrategyDetailByPSC", para);
		return list;
	}
}
