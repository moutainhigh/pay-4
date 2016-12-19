package com.pay.poss.authenticmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.authenticmanager.dao.IAuthenticDao;
import com.pay.poss.authenticmanager.dto.VerifyLogDto;
import com.pay.poss.authenticmanager.dto.VerifySearchDto;
import com.pay.poss.authenticmanager.dto.VerifySearchListDto;
import com.pay.poss.authenticmanager.model.IdcVerifyBase;
import com.pay.poss.authenticmanager.model.PossOperate;
import com.pay.poss.authenticmanager.model.TransLog;
import com.pay.poss.base.exception.PossException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

public class AuthenticDaoImpl extends BaseDAOImpl<IdcVerifyBase> implements
		IAuthenticDao {
	private Log log = LogFactory.getLog(AuthenticDaoImpl.class);

	@Override
	public List<VerifySearchListDto> queryVerifyLog(
			VerifySearchDto verifySearchDto) {
		log.debug("AuthenticDaoImpl.queryVerifyLog is running...");
		List<VerifySearchListDto> resultList = this.getSqlMapClientTemplate()
				.queryForList("authentic.queryVerifyLog", verifySearchDto);
		return resultList;
	}

	@Override
	public Integer queryVerifyLogCount(VerifySearchDto verifySearchDto) {
		log.debug("AuthenticDaoImpl.queryVerifyLogCount is running...");
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("authentic.queryVerifyLogCount",
						verifySearchDto);
		return count;
	}

	@Override
	public VerifyLogDto getVerifyLogById(Long verifyId) {
		log.debug("AuthenticDaoImpl.queryVerifyLogCount is running...");
		VerifyLogDto verifyLogDto = (VerifyLogDto) this
				.getSqlMapClientTemplate().queryForObject(
						"authentic.getVerifyLogById", verifyId);
		return verifyLogDto;
	}

	@Override
	public void updateVerifyLogStatus(Map<String, Object> dataMap)
			throws PossException {
		log.debug("AuthenticDaoImpl.updateVerifyLogStatus is running...");
		int i = this.getSqlMapClientTemplate().update(
				"authentic.updateVerifyLogStatus", dataMap);
		if (i != 1) {
			throw new PossException("补单时,更新状态失败",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}

	}

	@Override
	public void updatePoliceMessage(Map<String, Object> dataMap)
			throws PossException {
		log.debug("AuthenticDaoImpl.updatePoliceMessage is running...");
		int i = this.getSqlMapClientTemplate().update(
				"authentic.updatePoliceMessage", dataMap);
		if (i != 1) {
			throw new PossException("补单时,更新补单原因失败",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	@Override
	public void insertOpLog(PossOperate possOperate) {
		this.getSqlMapClientTemplate().insert("authentic.insertOperateLog",
				possOperate);

	}

	@Override
	public String getAccountCode(Long memberCode) {
		log.debug("AuthenticDaoImpl.getAccountCode is running...");
		String accountCode = (String) this.getSqlMapClientTemplate()
				.queryForObject("authentic.getAccountCodeOfRmb", memberCode);
		return accountCode;
	}

	@Override
	public String getOrderId() {
		log.debug("AuthenticDaoImpl.getOrderId is running...");
		String orderId = (String) this.getSqlMapClientTemplate()
				.queryForObject("authentic.getOrderId");
		return orderId;
	}

	@Override
	public String getOldOrderId(String verifyId) {
		log.debug("AuthenticDaoImpl.getOldOrderId is running...");
		String orderId = (String) this.getSqlMapClientTemplate()
				.queryForObject("authentic.getOldOrderId", verifyId);
		return orderId;
	}

	@Override
	public void insertTransLog(TransLog transLog) {
		this.getSqlMapClientTemplate().insert("authentic.insertTransLog",
				transLog);

	}

	@Override
	public void updateTransLog(String orderId, String relatxOrderId) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("orderId", orderId);
		dataMap.put("relatxOrderId", relatxOrderId);
		this.getSqlMapClientTemplate().update("authentic.updateTransLog",
				dataMap);

	}

}
