package com.pay.base.service.matrixcard.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.app.model.Model;
import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardCfg;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.common.util.matrixcard.MatrixCardUtil;
import com.pay.base.dao.matrixcard.IMatrixCardDAO;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCard;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.RandomUtil;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardServiceImpl implements IMatrixCardService {

	private IMatrixCardDAO matrixCardDAO;

	@Override
	public boolean isBindByMemberCode(Long memberCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bindMemberCode", memberCode);
		paramMap.put("status", MatrixCardStatus.BIND.getValue());
		int count = this.matrixCardDAO.countMatrixCardByParamMap(paramMap);
		if (count > 0) {
			return true;
		}
		return false;
	}

	@Override
	public int countMatrixCard() {
		return matrixCardDAO.countMatrixCard();
	}

	public MatrixCardDto findById(Long id) {
		MatrixCard matrixCard = (MatrixCard) matrixCardDAO.findById(id);
		if (matrixCard == null) {
			return null;
		}
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixCard);
		dto.setMatrixData(DESSecurityUtil.decrypt(dto.getMatrixData()));
		return dto;
	}

	public MatrixCardDto findBySerialNo(String serialNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serialNo", serialNo);
		MatrixCard matrixCard = this.matrixCardDAO
				.selectMatrixCardBySerialNo(paramMap);
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixCard);
		return dto;

	}

	@Override
	public MatrixCardDto findByBindUid(Long bindUid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bindUid", bindUid);
		paramMap.put("status", MatrixCardStatus.BIND.getValue());
		MatrixCard matrixCard = this.matrixCardDAO
				.selectMatrixCardBySerialNo(paramMap);
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixCard);
		return dto;
	}

	@Override
	public MatrixCardDto findByBindMemberCode(Long memberCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bindMemberCode", memberCode);
		paramMap.put("status", MatrixCardStatus.BIND.getValue());
		MatrixCard matrixCard = this.matrixCardDAO
				.selectMatrixCardBySerialNo(paramMap);
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixCard);
		return dto;
	}

	@Override
	public MatrixCardDto create() throws MatrixCardException {
		try {
			MatrixCardDto matrixCardDto = new MatrixCardDto();
			matrixCardDto.setCreationDate(new Date(System.currentTimeMillis()));
			matrixCardDto.setMatrixData(DESSecurityUtil.encrypt(MatrixCardUtil
					.createMatrixCardData()));
			// 生成随机的15位序列号
			String serialNo = RandomUtil.randomDegital(15);
			matrixCardDto.setSerialNo(serialNo);
			matrixCardDto.setStatus(MatrixCardStatus.CREATE.getValue());
			return this.save(matrixCardDto);
		} catch (Exception e) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.CREATE_MATRIXCARD_ERROR,
					"create matrixcard failture");
		}
	}

	@Override
	public MatrixCardDto save(MatrixCardDto dto) {
		Model model = (Model) BeanConvertUtil.convert(
				matrixCardDAO.getModelClass(), dto);
		Long id = (Long) matrixCardDAO.create(model);
		model.setPrimaryKey(id);
		MatrixCardDto result = (MatrixCardDto) BeanConvertUtil.convert(
				dto.getClass(), model);
		BeanUtils.copyProperties(result, dto);
		return dto;
	}

	@Override
	public MatrixCardDto selectMatrixCardById(Long id) {
		MatrixCard matrixcard = (MatrixCard) this.matrixCardDAO.findById(id);
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixcard);
		return dto;
	}

	@Override
	public void updateMatrixCard(MatrixCardDto dto) {

		Model model = (Model) BeanConvertUtil.convert(
				matrixCardDAO.getModelClass(), dto);
		this.matrixCardDAO.update(model);
	}

	public IMatrixCardDAO getMatrixCardDAO() {
		return matrixCardDAO;
	}

	public void setMatrixCardDAO(IMatrixCardDAO matrixCardDAO) {
		this.matrixCardDAO = matrixCardDAO;
	}

	@Override
	public String[] getValue(String serialNo, String[] coordinates) {
		String[] values = new String[coordinates.length];

		MatrixCardDto matrixCardDto = findBySerialNo(serialNo);
		if (matrixCardDto == null) {
			return null;
		}

		String data = matrixCardDto.getMatrixData();
		data = DESSecurityUtil.decrypt(data);
		for (int i = 0; i < coordinates.length; i++) {

			int positions[] = MatrixCardUtil.convertPosToNumber(coordinates[i]);
			int x0 = positions[0];
			int y0 = positions[1];

			String[] setValue = data.split(",");

			int pos = (x0 - 1) * MatrixCardCfg.COLS + (y0 - 1);
			values[i] = setValue[pos].trim();

		}

		return values;
	}

	@Override
	public MatrixCardDto selectmatrixcardByTransInfoMemberCode(Long memberCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		List<MatrixCard> list = this.matrixCardDAO
				.selectmatrixcardByTransInfoMemberCode(paramMap);
		MatrixCard matrixcard = null;
		if (null != list && list.size() > 0) {
			matrixcard = list.get(0);
		} else {
			return null;
		}
		MatrixCardDto dto = (MatrixCardDto) BeanConvertUtil.convert(
				MatrixCardDto.class, matrixcard);
		return dto;
	}

}
