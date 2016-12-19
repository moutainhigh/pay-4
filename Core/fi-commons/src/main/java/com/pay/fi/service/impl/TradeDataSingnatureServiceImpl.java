/**
 * 
 */
package com.pay.fi.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.exception.ParamValidateException;
import com.pay.fi.helper.SecuritySubstance;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.util.CharsetTypeEnum;
import com.pay.util.SignTypeEnum;
import com.pay.util.StringUtil;

public class TradeDataSingnatureServiceImpl implements
		TradeDataSingnatureService {

	private final Log log = LogFactory
			.getLog(TradeDataSingnatureServiceImpl.class);

	@Override
	public boolean verifyBySignType(String src, String signMsg,
			String signType, String key, String charsetType) throws Exception {
		int charset = Integer.valueOf(charsetType).intValue();
		CharsetTypeEnum charsetin = CharsetTypeEnum.getByCode(charset);
		if (SignTypeEnum.getByCode(signType) == null) {
			log.error("@FI-加签类型不正确");
			throw new ParamValidateException("FI-加签类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		if (charsetin == null) {
			log.error("@FI-字符集类型不正确");
			throw new ParamValidateException("FI-字符集类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}

		if (null != signType && signType.equals(SignTypeEnum.RSA.getCode())) {// RSA
			try {
				return SecuritySubstance.verifySignatureByRSA(src, signMsg,
						charsetin, key);
			} catch (Exception e) {
				log.error("RSA验签：验签过程异常" + e);
				throw new Exception(e);
			}
		} else if (null != signType
				&& signType.equals(SignTypeEnum.MD5.getCode())) {
			try {
				return SecuritySubstance.verifySignatureByMD5(src, signMsg,
						charsetin, key);
			} catch (Exception e) {
				log.error("MD5验签：验签过程异常" , e);
				throw new Exception(e);
			}
		}
		return false;
	}

	@Override
	public String genSignMsgBySignType(String src, String signType,
			String charsetType, String key) throws Exception {
		String signMsg = "";
		int charset = Integer.valueOf(StringUtil.isEmpty(charsetType)?"1":charsetType).intValue();
		CharsetTypeEnum charsetin = CharsetTypeEnum.getByCode(charset);
		/*if (SignTypeEnum.getByCode(signType) == null) {
			log.error("@FI-加签类型不正确");
			throw new ParamValidateException("FI-加签类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}*/
		signType = StringUtil.isEmpty(signType)?"2":signType;
		if (charsetin == null) {
			log.error("@FI-字符集类型不正确");
			throw new ParamValidateException("FI-字符集类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		if (null != signType && signType.equals(SignTypeEnum.RSA.getCode())) {// RSA
			return SecuritySubstance.genSignByRSA(src, charsetin);
		} else if (null != signType
				&& signType.equals(SignTypeEnum.MD5.getCode())) {
			return SecuritySubstance.genSignByMD5(src, charsetin, key);
		}else if(null != signType && signType.equals(SignTypeEnum.SHA512.getCode())){
			return SecuritySubstance.genSignBySHA512(src, charsetin,key);
		}
		return signMsg;
	}

	@Override
	public String decryptData(String encryptedData, String signType,
			String charsetType, String key) throws Exception {
		int charset = Integer.valueOf(charsetType).intValue();
		CharsetTypeEnum charsetin = CharsetTypeEnum.getByCode(charset);
		String srcData = null;
		if (SignTypeEnum.getByCode(signType) == null) {
			log.error("@FI-加签类型不正确");
			throw new ParamValidateException("FI-加签类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		if (charsetin == null) {
			log.error("@FI-字符集类型不正确");
			throw new ParamValidateException("FI-字符集类型不正确",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		if (null != signType && signType.equals(SignTypeEnum.RSA.getCode())) {// RSA
			try {
				srcData = SecuritySubstance.decryptData(encryptedData, key);
			} catch (Exception e) {
				log.error("RSA验签：验签过程异常" + e);
				throw new Exception(e);
			}
		} else {
			log.error("验签类型为：" + signType);
			throw new Exception();
		}
		return srcData;
	}
}
