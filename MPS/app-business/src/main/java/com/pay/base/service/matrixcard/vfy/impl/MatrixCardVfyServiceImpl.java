package com.pay.base.service.matrixcard.vfy.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.model.Model;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyStatus;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.common.util.matrixcard.MatrixCardUtil;
import com.pay.base.dao.matrixcard.vfy.IMatrixCardVfyDao;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDtoUtil;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;
import com.pay.base.service.matrixcard.vfy.MatrixCardVfyTxService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author jim_chen
 * @version 2010-9-19
 */
public class MatrixCardVfyServiceImpl implements IMatrixCardVfyService {

	private IMatrixCardVfyDao matrixCardVfyDao;
	private IMessageDigest iMessageDigest;
	private MatrixCardVfyTxService matrixCardVfyTxService;

	private Log log = LogFactory.getLog(MatrixCardVfyServiceImpl.class);

	@Override
	public boolean verify(String serialNo, MatrixCardToken matrixCardToken,
			OperatorInfo operatorInfo) throws MatrixCardException {
		try {
			ErrorCodeMatrixExceptionEnum errorCode = matrixCardVfyTxService
					.verifyRnTx(serialNo, matrixCardToken, operatorInfo);
			if (null != errorCode) {
				throw new MatrixCardException(errorCode,
						"validate verify error");
			}
			return true;
		} catch (MatrixCardException e) {
			throw e;
		}

	}

	@Override
	public int countMatrixCardVfy() {
		return this.matrixCardVfyDao.countMatrixCardVfy();
	}

	@Override
	public MatrixCardVfy save(MatrixCardVfy model) {
		Long id= (Long) matrixCardVfyDao.create(model);
		model.setId(id);
		return model;
	}

	@Override
	public MatrixCardVfyDto selectMatrixCardVfyById(Long id) {
		MatrixCardVfy info = (MatrixCardVfy) this.matrixCardVfyDao.findById(id);
		MatrixCardVfyDto dto = (MatrixCardVfyDto) BeanConvertUtil.convert(
				MatrixCardVfyDto.class, info);
		return dto;
	}

	@Override
	public void updateMatrixCardVfy(MatrixCardVfyDto dto) {
		Model model = (Model) BeanConvertUtil.convert(
				matrixCardVfyDao.getModelClass(), dto);
		this.matrixCardVfyDao.update(model);
	}

	public IMatrixCardVfyDao getMatrixCardVfyDao() {
		return matrixCardVfyDao;
	}

	public void setMatrixCardVfyDao(IMatrixCardVfyDao matrixCardVfyDao) {
		this.matrixCardVfyDao = matrixCardVfyDao;
	}

	@Override
	public MatrixCardToken requestToken(MatrixCardVfyRequest mcVfyRequest)
			throws MatrixCardException {
		validateRequest(mcVfyRequest);
		return generateToken(mcVfyRequest);
	}

	/*
	 * 请求数据校验
	 */
	private void validateRequest(MatrixCardVfyRequest mcVfyRequest)
			throws MatrixCardException {
		// 验证请求数据完整性
		if (mcVfyRequest.getRequestDate() == null) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.VFY_REQUESTDATA_ERROR,
					"validate matrixcard request data error");
		}
		// if(MatrixCardVfyRequestType.LOGIN.getValue() !=
		// mcVfyRequest.getRequestType()){
		//
		// }

	}

	/*
	 * 生成坐标token
	 */
	@Override
	public MatrixCardToken generateToken(MatrixCardVfyRequest mcVfyRequest)
			throws MatrixCardException {
		try {
			MatrixCardVfy matrixVfy = MatrixCardVfyDtoUtil
					.createModel(mcVfyRequest);

			// 生成坐标，取得各坐标的值
			String[] coords = MatrixCardUtil.getRandomCoord();
			matrixVfy.setXy0(coords[0]);
			matrixVfy.setXy1(coords[1]);
			matrixVfy.setXy2(coords[2]);
			matrixVfy.setStatus(MatrixCardVfyStatus.REQUEST.getValue());// 初始状态为“请求验证”
			matrixVfy = this.save(matrixVfy); // 保存对象得到id
			matrixVfy = (MatrixCardVfy) matrixCardVfyDao.findById(matrixVfy
					.getId());
			// 加密串中的后13位是MatrixCardVfy的id，总长度为45位
			String encryptStr = generateEncryptStr(MatrixCardVfyDtoUtil
					.createMatrixCardTokenDto(matrixVfy));
			encryptStr = encryptStr
					+ StringUtil.fillZero(matrixVfy.getId().toString(), 13);
			matrixVfy.setToken(encryptStr);
			matrixCardVfyDao.update(matrixVfy); // 更新加密串到数据库
			MatrixCardToken token = MatrixCardVfyDtoUtil
					.createMatrixCardTokenDto(matrixVfy);
			return token;
		} catch (Exception e) {
			log.debug(e);
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.GENERATE_TOKEN_ERROR,
					"generate token failture");
		}

	}

	/*
	 * token由requestId,xy0,xy1,xy2,creationDate联合起来MessageDigest+
	 * MatrixCardVfy的ID补足15位得到
	 */
	private String generateEncryptStr(MatrixCardToken maxtrix)
			throws MatrixCardException {
		try {
			StringBuffer signMsg = new StringBuffer();
			if (maxtrix.getRequestId() != null) {
				signMsg.append(maxtrix.getRequestId()).append(",");
			}
			signMsg.append(maxtrix.getXy0()).append(",");
			signMsg.append(maxtrix.getXy1()).append(",");
			signMsg.append(maxtrix.getXy2()).append(",");
			signMsg.append(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",
					maxtrix.getRequestDate()));
			StringBuffer digest = new StringBuffer();
			digest.append(iMessageDigest.genMessageDigest(signMsg.toString()
					.getBytes()));
			return digest.toString();
		} catch (Exception e) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.GENERATE_TOKEN_ERROR,
					"generate token failture");
		}
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMatrixCardVfyTxService(
			MatrixCardVfyTxService matrixCardVfyTxService) {
		this.matrixCardVfyTxService = matrixCardVfyTxService;
	}

}
