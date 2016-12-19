package com.pay.base.service.matrixcard.transmgr.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.app.model.Model;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardTransStatus;
import com.pay.base.dao.matrixcard.transmgr.MatrixCardTransMgrDAO;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardTransMgr;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.util.BeanConvertUtil;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardTransMgrServiceImpl implements MatrixCardTransMgrService {

	private MatrixCardTransMgrDAO baseMatrixCardTransMgrDAO;

	private IMatrixCardTransInfoService matrixCardTransInfoService;
	private Log log = LogFactory.getLog(MatrixCardTransMgrServiceImpl.class);

	// @Override
	// public MatrixCardTransMgrDto findValidTransByUserInfo(Long uId, int
	// transType) throws MatrixCardException {
	// Map<String,Object> paramMap=new HashMap<String,Object>();
	// paramMap.put("u_id", uId);
	// paramMap.put("transType", transType);
	// paramMap.put("status", MatrixCardTransStatus.ACTIVE.getValue());
	// List<MatrixCardTransMgr>
	// list=this.baseMatrixCardTransMgrDAO.selectMatrixCardTransMgrList(paramMap);
	// if(list.size()>0){
	// MatrixCardTransMgr matrixCardTransMgr=list.get(0);
	// return BeanConvertUtil.convert(MatrixCardTransMgrDto.class,
	// matrixCardTransMgr);
	// }else{
	// return null;
	// }
	// }

	@Override
	public MatrixCardTransMgrDto findValidTransByMemberCode(Long memberCode,
			int transType) throws MatrixCardException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("transType", transType);
		paramMap.put("status", MatrixCardTransStatus.ACTIVE.getValue());
		List<MatrixCardTransMgr> list = this.baseMatrixCardTransMgrDAO
				.selectMatrixCardTransMgrList(paramMap);
		if (list.size() > 0) {
			MatrixCardTransMgr matrixCardTransMgr = list.get(0);
			return BeanConvertUtil.convert(MatrixCardTransMgrDto.class,
					matrixCardTransMgr);
		} else {
			return null;
		}

	}

	@Override
	public MatrixCardTransInfoDto beginTransInIsolatedRdTx(
			MatrixCardTransInfoDto transInfoDto) throws MatrixCardException {
		try {
			// 记录trans
			MatrixCardTransMgrDto transMgrDto = parseTransData(transInfoDto);
			transMgrDto = save(transMgrDto);// 保存口令卡事务
			transInfoDto.setTransId(transMgrDto.getId());
			transInfoDto = matrixCardTransInfoService.save(transInfoDto);// 口令卡操作信息
		} catch (Exception e) {
			log.error(e);
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.SAVE_DATA_ERROR,
					"insert into MatrixCardTransMgr error");
		}
		return transInfoDto;
	}

	public void finishTransInIsolatedRnTx(Long transId)
			throws MatrixCardException {
		try {
			MatrixCardTransMgr matrixCardTransMgr = (MatrixCardTransMgr) baseMatrixCardTransMgrDAO
					.findById(transId);
			matrixCardTransMgr.setStatus(MatrixCardTransStatus.FINISH
					.getValue());
			baseMatrixCardTransMgrDAO.update(matrixCardTransMgr);
		} catch (Exception e) {
			log.error(e);
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.UPDATE_DATA_ERROR,
					"update  MatrixCardTransMgr error");
		}

	}

	public void finishTransInIsolatedRnTx(Long transId,
			MatrixCardTransInfoDto transInfo) throws MatrixCardException {
		try {
			matrixCardTransInfoService.updateMatrixCardTransInfo(transInfo);
			MatrixCardTransMgr matrixCardTransMgr = (MatrixCardTransMgr) baseMatrixCardTransMgrDAO
					.findById(transId);
			matrixCardTransMgr.setStatus(MatrixCardTransStatus.FINISH
					.getValue());
			baseMatrixCardTransMgrDAO.update(matrixCardTransMgr);
		} catch (Exception e) {
			log.error(e);
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.UPDATE_DATA_ERROR,
					"update  MatrixCardTransMgr error");
		}
	}

	@Override
	public void finishTransInIsolatedRdTx(Long transId,
			MatrixCardTransInfoDto transInfo) throws MatrixCardException {
		try {
			matrixCardTransInfoService.updateMatrixCardTransInfo(transInfo);
			MatrixCardTransMgr matrixCardTransMgr = (MatrixCardTransMgr) baseMatrixCardTransMgrDAO
					.findById(transId);
			matrixCardTransMgr.setStatus(MatrixCardTransStatus.FINISH
					.getValue());
			baseMatrixCardTransMgrDAO.update(matrixCardTransMgr);
		} catch (Exception e) {
			log.error(e);
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.UPDATE_DATA_ERROR,
					"update  MatrixCardTransMgr error");
		}
	}

	private MatrixCardTransMgrDto parseTransData(
			MatrixCardTransInfoDto transInfoDto) {

		MatrixCardTransMgrDto transMgrDto = new MatrixCardTransMgrDto();
		transMgrDto.setCreationDate(transInfoDto.getCreationDate());
		transMgrDto.setIp(transInfoDto.getIp());
		transMgrDto.setMemberCode(transInfoDto.getMemberCode());
		transMgrDto.setSessionId(transInfoDto.getSessionId());
		transMgrDto.setStatus(MatrixCardTransStatus.ACTIVE.getValue());
		transMgrDto.setTransType(transInfoDto.getTransType());
		transMgrDto.setU_id(transInfoDto.getU_id());

		return transMgrDto;
	}

	@Override
	public long countMartrixCardTransMgr() {
		return baseMatrixCardTransMgrDAO.countMatrixCardTransMgr(null);
	}

	@Override
	public int countMartrixCardTransMgrByParamMap(Map<String, Object> paramMap) {

		return this.baseMatrixCardTransMgrDAO.countMatrixCardTransMgr(paramMap);
	}

	public boolean beShowValidateCode(String sessionId, int minuts, int total) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.MINUTE, (-1) * minuts);
		Date dateFm = from.getTime();
		Date dateTo = Calendar.getInstance().getTime();
		paramMap.put("sessionId", sessionId);
		paramMap.put("creationDateBegin", dateFm);
		paramMap.put("creationDateEnd", dateTo);
		int requestTimes = countMartrixCardTransMgrByParamMap(paramMap);
		if (requestTimes > total) {
			return true;
		}
		return false;
	}

	public MatrixCardTransMgrDto save(MatrixCardTransMgrDto dto) {
		MatrixCardTransMgr model = (MatrixCardTransMgr) BeanConvertUtil
				.convert(baseMatrixCardTransMgrDAO.getModelClass(), dto);
		Long id = (Long) baseMatrixCardTransMgrDAO.create(model);
		model.setId(id);
		MatrixCardTransMgrDto result = (MatrixCardTransMgrDto) BeanConvertUtil
				.convert(dto.getClass(), model);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	public MatrixCardTransMgrDAO getBaseMatrixCardTransMgrDAO() {
		return baseMatrixCardTransMgrDAO;
	}

	public void setBaseMatrixCardTransMgrDAO(
			MatrixCardTransMgrDAO baseMatrixCardTransMgrDAO) {
		this.baseMatrixCardTransMgrDAO = baseMatrixCardTransMgrDAO;
	}

	public void setMatrixCardTransInfoService(
			IMatrixCardTransInfoService matrixCardTransInfoService) {
		this.matrixCardTransInfoService = matrixCardTransInfoService;
	}

}
