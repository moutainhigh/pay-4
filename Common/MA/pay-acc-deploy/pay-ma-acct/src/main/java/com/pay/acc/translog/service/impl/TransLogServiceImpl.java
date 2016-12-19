/**
 * 
 */
package com.pay.acc.translog.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.translog.dao.TransLogDAO;
import com.pay.acc.translog.dto.TransLogDto;
import com.pay.acc.translog.exception.TransLogException;
import com.pay.acc.translog.exception.TransLogUnknowException;
import com.pay.acc.translog.model.TransLog;
import com.pay.acc.translog.service.TransLogService;
import com.pay.util.BeanConvertUtil;

public class TransLogServiceImpl implements TransLogService {

	private TransLogDAO transLogDAO;

	public List<TransLog> queryBalanceHistoryByAcctCodeAndDate(String acctCode,
			Date fromDate, Date toDate) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("acctCode", acctCode);
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		return transLogDAO.findByTemplate(
				"queryWithdrawBalanceHistoryByAcctCodeAndDate", paraMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.translog.service.TransLogService#createTransLog(com.pay.acc
	 * .translog.dto.TransLogDto)
	 */
	@Override
	public Long createTransLog(TransLogDto transLogDto)
			throws TransLogException, TransLogUnknowException {
		if (transLogDto == null) {
			throw new TransLogException("输入的参数不能为空");
		}
		try {
			TransLog transLog = BeanConvertUtil.convert(TransLog.class,
					transLogDto);
			return (Long) this.transLogDAO.create(transLog);
		} catch (Exception e) {
			throw new TransLogUnknowException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.translog.service.TransLogService#queryTransLogDtoWithSerialNo
	 * (java.lang.Long)
	 */
	@Override
	public TransLogDto queryTransLogDtoWithSerialNo(Long serialNo)
			throws TransLogException, TransLogUnknowException {
		if (serialNo == null || serialNo.longValue() <= 0) {
			throw new TransLogException("输入的参数不正确");
		}
		TransLog transLog = this.transLogDAO
				.queryTransLogWithSerialNo(serialNo);
		try {
			return BeanConvertUtil.convert(TransLogDto.class, transLog);
		} catch (Exception e) {
			throw new TransLogUnknowException(e);
		}
	}

	public List<TransLog> queryTransLogBySerialNo(String serialNo)
			throws NumberFormatException, SQLException {
		if (serialNo != null && !"".equals(serialNo)) {
			return transLogDAO
					.queryTransLogBySerialNo(Long.parseLong(serialNo));
		}
		return null;
	}

	public int editTransLogForLinkId(List<TransLog> transLogList, Long baseId) {
		if (transLogList != null && baseId != null) {
			for (TransLog transLog : transLogList) {
				transLog.setLinkId(baseId);
				return transLogDAO.editLinkId(transLog);
			}
		}
		return 0;
	}

	public TransLogDAO getTransLogDAO() {
		return transLogDAO;
	}

	public void setTransLogDAO(TransLogDAO transLogDAO) {
		this.transLogDAO = transLogDAO;
	}

}
