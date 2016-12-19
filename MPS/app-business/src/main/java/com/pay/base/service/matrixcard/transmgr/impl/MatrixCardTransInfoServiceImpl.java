package com.pay.base.service.matrixcard.transmgr.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.app.model.Model;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.dao.matrixcard.transmgr.IMatrixCardTransInfoDao;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardTransInfo;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;
import com.pay.util.BeanConvertUtil;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardTransInfoServiceImpl implements
		IMatrixCardTransInfoService {

	private IMatrixCardTransInfoDao matrixCardTransInfoDAO;

	@Override
	public MatrixCardTransInfoDto save(MatrixCardTransInfoDto dto)
			throws MatrixCardException {
		try {
			MatrixCardTransInfo model = (MatrixCardTransInfo) BeanConvertUtil
					.convert(matrixCardTransInfoDAO.getModelClass(), dto);
			Long id = (Long) matrixCardTransInfoDAO.create(model);
			model.setId(id);
			MatrixCardTransInfoDto result = (MatrixCardTransInfoDto) BeanConvertUtil
					.convert(dto.getClass(), model);
			BeanUtils.copyProperties(result, dto);
			return dto;
		} catch (Exception e) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.SAVE_DATA_ERROR,
					"insert into MatrixCardTransInfo failture");
		}

	}

	@Override
	public void updateMatrixCardTransInfo(MatrixCardTransInfoDto dto)
			throws MatrixCardException {
		try {
			Model model = (Model) BeanConvertUtil.convert(
					matrixCardTransInfoDAO.getModelClass(), dto);
			this.matrixCardTransInfoDAO.update(model);
		} catch (Exception e) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.UPDATE_DATA_ERROR,
					"update MatrixCardTransInfo failture");
		}
	}

	@Override
	public int countMatrixCardTransInfo() {
		return this.matrixCardTransInfoDAO.countMatrixCardTransInfo();
	}

	@Override
	public MatrixCardTransInfoDto selectMatrixCardTransInfoById(Long id) {
		MatrixCardTransInfo info = (MatrixCardTransInfo) this.matrixCardTransInfoDAO
				.findById(id);
		MatrixCardTransInfoDto dto = (MatrixCardTransInfoDto) BeanConvertUtil
				.convert(MatrixCardTransInfoDto.class, info);
		return dto;
	}

	@Override
	public MatrixCardTransInfoDto selectMatrixCardTransInfoByTransId(
			Long trandsId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("transId", trandsId);
		MatrixCardTransInfo info = (MatrixCardTransInfo) this.matrixCardTransInfoDAO
				.selectMatrixCardTransInfoByParamMap(paramMap);
		MatrixCardTransInfoDto dto = (MatrixCardTransInfoDto) BeanConvertUtil
				.convert(MatrixCardTransInfoDto.class, info);
		return dto;
	}

	@Override
	public MatrixCardTransInfoDto selectRequestMcIdByMemberCode(Long memberCode)
			throws MatrixCardException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("transType", MatrixCardTransType.REQUEST.getValue());
		List<MatrixCardTransInfo> infoList = this.matrixCardTransInfoDAO
				.queryListByParamMap(paramMap);
		MatrixCardTransInfo info = null;
		if (null != infoList && infoList.size() > 0) {
			info = infoList.get(0);
		} else {
			return null;
		}
		MatrixCardTransInfoDto dto = (MatrixCardTransInfoDto) BeanConvertUtil
				.convert(MatrixCardTransInfoDto.class, info);
		return dto;
	}

	public IMatrixCardTransInfoDao getMatrixCardTransInfoDAO() {
		return matrixCardTransInfoDAO;
	}

	public void setMatrixCardTransInfoDAO(
			IMatrixCardTransInfoDao matrixCardTransInfoDAO) {
		this.matrixCardTransInfoDAO = matrixCardTransInfoDAO;
	}

}
