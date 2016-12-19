package com.pay.base.service.matrixcard.vfy.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.common.helper.AppConf;
import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyStatus;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyType;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.common.util.matrixcard.MatrixCardUtil;
import com.pay.base.dao.matrixcard.vfy.IMatrixCardVfyDao;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDtoUtil;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.vfy.MatrixCardVfyTxService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class MatrixCardVfyTxServiceImpl implements MatrixCardVfyTxService {
	private IMatrixCardVfyDao matrixCardVfyDao;
	private IMessageDigest iMessageDigest;
	private IMatrixCardService matrixCardService;

	public void setMatrixCardVfyDao(IMatrixCardVfyDao matrixCardVfyDao) {
		this.matrixCardVfyDao = matrixCardVfyDao;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}

	private Log log = LogFactory.getLog(MatrixCardVfyServiceImpl.class);

	@Override
	public MatrixCardVfy getMatrixCardVfyRnTx(MatrixCardVfy maxtrixVfy,
			String serialNo, MatrixCardToken matrixCardToken,
			OperatorInfo operatorInfo) throws MatrixCardException {

		if (null == maxtrixVfy) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR,
					"token is null");
		}
		int status = maxtrixVfy.getStatus();
		// int vfyType = maxtrixVfy.getVfyType();
		// 判断是否重复验证
		if (status != MatrixCardVfyStatus.REQUEST.getValue()) {
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.VFY_TOKEN_REUSE,
					"token status has validate");
		}
		maxtrixVfy.setVfyDate(new Date());
		maxtrixVfy.setVfyIp(operatorInfo.getIp());
		maxtrixVfy.setVfyMemberCode(operatorInfo.getMemberCode());
		maxtrixVfy.setVfyUid(operatorInfo.getU_id());
		maxtrixVfy.setSerialNo(serialNo);
		maxtrixVfy.setValue0(DESSecurityUtil.encrypt(matrixCardToken
				.getValue0()));
		maxtrixVfy.setValue1(DESSecurityUtil.encrypt(matrixCardToken
				.getValue1()));
		maxtrixVfy.setValue2(DESSecurityUtil.encrypt(matrixCardToken
				.getValue2()));
		int vfyType = maxtrixVfy.getVfyType();
		MatrixCardDto matrixCardDto = null;
		try {
			if (!operatorInfo.getMemberCode().equals(
					maxtrixVfy.getRequestMemberCode())) {
				log.debug("没有找到验证数据！");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.VFY_TOKEN_ERROR,
						"vfy token error ");
			}
			// 判断token是否超出有效时间
			long requestTime = maxtrixVfy.getRequestDate().getTime() + 20 * 60 * 1000;
			long nowTime = System.currentTimeMillis();

			if (nowTime > requestTime) {
				log.debug("验证超时！");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.VFY_TOKEN_TIMEOUT,
						"validation token timeout ");
			}
			// 对提交数据完整性做初步验证
			validateTokenData(maxtrixVfy, matrixCardToken);

			// 验证给定的serialNo是否存在
			matrixCardDto = matrixCardService.findBySerialNo(serialNo);

			if (null == matrixCardDto) {
				log.debug("请求的卡[" + serialNo + "]不存在!");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.COMMON_SERIALNO_NOTEXISTS,
						"matrixcard serialno is not exists ");
			}
			int matrixCardUserTime = 0;
			String matrixCardUserLimit = AppConf
					.get(AppConf.matrixCardUserLimit);
			if (StringUtils.isNotEmpty(matrixCardUserLimit)) {
				matrixCardUserTime = Integer.parseInt(matrixCardUserLimit);
			}
			// 卡的使用次数不能大于设定的
			if (matrixCardDto.getUserTime() >= matrixCardUserTime) {
				log.debug("使用卡的次数已经超过了最高限制");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.MATRIX_CARD_USER_LIMIT_ERROR,
						"matrixcard vfy  status error");
			}
			int cardStatus = matrixCardDto.getStatus();
			// 卡状态是否为解绑状态
			if (MatrixCardStatus.UNBIND.getValue() == cardStatus) {
				log.debug("请求的卡[" + serialNo + "]处于解绑状态!");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.VFY_CARD_STATUS_ERROR,
						"matrixcard vfy card statys error ");
			}
			// 新卡绑定验证申请
			if (vfyType == MatrixCardVfyType.NEWCARD_VFY.getValue()) {
				// 判断申请的口令卡是否使用
				if (cardStatus != MatrixCardStatus.CREATE.getValue()) {
					log.debug("请求的卡[" + serialNo + "]已被使用!");
					throw new MatrixCardException(
							ErrorCodeMatrixExceptionEnum.TB_ALREADY_BIND,
							"matrixcard serial has used ");
				}
				Date lastValidDate = matrixCardDto.getCreationDate();
				lastValidDate = MatrixCardUtil
						.getBindLastValidDate(lastValidDate);
				// 判断申请的口令卡是否超过了最后有效期(判断最后有效期在当前日期之前)
				if (lastValidDate.before(new Date())) {
					log.debug("超过新卡绑定的最后有效期!");
					throw new MatrixCardException(
							ErrorCodeMatrixExceptionEnum.VFY_BIND_DATE_LINE_ERROR,
							"matrixcard serial has used ");
				}

			}
			// 已绑定卡验证申请, 验证卡是否和membercode匹配
			if (vfyType == MatrixCardVfyType.NORMALCARD_VFY.getValue()) {
				if (cardStatus != MatrixCardStatus.BIND.getValue()) {
					log.debug("请求的卡[" + serialNo + "]未绑定!");
					throw new MatrixCardException(
							ErrorCodeMatrixExceptionEnum.VFY_CARD_STATUS_ERROR,
							"matrixcard serialNo unbind ");
				}
				Long memberCode = maxtrixVfy.getRequestMemberCode();
				if (memberCode == null
						|| !memberCode
								.equals(matrixCardDto.getBindMemberCode())) {
					log.debug("请求验证的卡[" + serialNo + "]非该操作员所有!");
					throw new MatrixCardException(
							ErrorCodeMatrixExceptionEnum.VFY_TOKEN_NOTCARDOWNER,
							"matrixcard vfy token not card owner ");
				}
			}
			// 判断输入坐标值是否正确
			if (!verifyCoordinate(serialNo, matrixCardToken)) {
				log.debug("坐标值不正确！");
				throw new MatrixCardException(
						ErrorCodeMatrixExceptionEnum.VFY_PWD_ERROR,
						"matrixcard vfy pwd errror ");
			}
		} catch (MatrixCardException e) {
			throw e;
		}
		return maxtrixVfy;
	}

	@Override
	public ErrorCodeMatrixExceptionEnum verifyRnTx(String serialNo,
			MatrixCardToken matrixCardToken, OperatorInfo operatorInfo)
			throws MatrixCardException {
		String tokenStr = matrixCardToken.getToken();
		MatrixCardVfy maxtrixVfy = matrixCardVfyDao
				.getMatrixCardVfyByToken(tokenStr);// 根据token找出验证数据对象
		MatrixCardDto matrixCardDto = null;
		matrixCardDto = matrixCardService.findBySerialNo(serialNo);
		try {
			this.getMatrixCardVfyRnTx(maxtrixVfy, serialNo, matrixCardToken,
					operatorInfo);
			if (null != maxtrixVfy) {
				maxtrixVfy.setStatus(MatrixCardVfyStatus.SUCCESS.getValue()); // 更新验证状态为“验证成功”
			}
			// 验证给定的serialNo是否存在

			return null;
		} catch (MatrixCardException e) {
			if (null != maxtrixVfy) {
				maxtrixVfy.setStatus(MatrixCardVfyStatus.FAIL.getValue());// 更新验证状态为“验证失败”
			}
			return e.getErrorEnum();

		} finally {
			if (null != maxtrixVfy) {
				matrixCardVfyDao.update(maxtrixVfy);
			}
			// 将口令卡的使用次数加1
			if (null != matrixCardDto) {
				int userTime = matrixCardDto.getUserTime() + 1;
				matrixCardDto.setUserTime(userTime);
				matrixCardService.updateMatrixCard(matrixCardDto);
			}
		}
	}

	/*
	 * 验证坐标值
	 */
	private boolean verifyCoordinate(String serialNo,
			MatrixCardToken matrixCardToken) {

		String[] coordinates = new String[] { matrixCardToken.getXy0(),
				matrixCardToken.getXy1(), matrixCardToken.getXy2() };

		String[] values = matrixCardService.getValue(serialNo, coordinates);

		if (values[0].equals(matrixCardToken.getValue0())
				&& values[1].equals(matrixCardToken.getValue1())
				&& values[2].equals(matrixCardToken.getValue2())) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * token的验证
	 */
	private void validateTokenData(MatrixCardVfy maxtrixVfy,
			MatrixCardToken matrixCardToken) throws MatrixCardException {
		if (StringUtil.isEmpty(matrixCardToken.getToken())
				|| StringUtil.isEmpty(matrixCardToken.getValue0())
				|| StringUtil.isEmpty(matrixCardToken.getValue1())
				|| StringUtil.isEmpty(matrixCardToken.getValue2())) {
			log.debug("提交的验证数据不完整!");
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.VFY_TOKENDATA_ERROR,
					"matrixcard vfy data error ");
		}
		String token = matrixCardToken.getToken();
		token = token.substring(0, token.length() - 13);

		if (maxtrixVfy.getRequestId() != null) {
			matrixCardToken.setRequestId(maxtrixVfy.getRequestId().toString());
		}
		matrixCardToken.setRequestDate(maxtrixVfy.getRequestDate());
		matrixCardToken.setXy0(maxtrixVfy.getXy0());
		matrixCardToken.setXy1(maxtrixVfy.getXy1());
		matrixCardToken.setXy2(maxtrixVfy.getXy2());

		String requestToken = generateEncryptStr(matrixCardToken);
		if (!requestToken.equals(token)) {
			log.debug("提交的token数据错误!");
			throw new MatrixCardException(
					ErrorCodeMatrixExceptionEnum.VFY_TOKENDATA_ERROR,
					"matrixcard vfy token data error ");
		}
	}

	/*
	 * 生成坐标token
	 */
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

	public MatrixCardVfy save(MatrixCardVfy model) {
		Long id = (Long) matrixCardVfyDao.create(model);
		model.setId(id);
		return model;
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

}
