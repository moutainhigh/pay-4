/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.acct.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.base.common.enums.AcctStatusEnum;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.dao.acct.AcctDAO;
import com.pay.base.dto.AcctDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.AcctInfo;
import com.pay.base.model.Operator;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.CheckUtil;
import com.pay.util.CurrencyCodeEnum;

/**
 *
 * @author wangzhi
 * @version $Id: AcctServiceImpl.java, v 0.1 2010-10-2 上午11:28:21 wangzhi Exp $
 */
public class AcctServiceImpl implements AcctService {

	private AcctDAO acctDAO;
	private OperatorServiceFacade operatorService;
	private IMessageDigest iMessageDigest;
	private AcctAttribService acctAttribService;
	private final Log logger = LogFactory.getLog(AcctServiceImpl.class);

	/**
	 * @param acct
	 * @return
	 * @see com.pay.base.service.acct.AcctService#createAcct(com.pay.base.model.Acct)
	 */
	@Override
	public Long createAcct(final Acct acct) {
		return acctDAO.createAcct(acct);
	}

	/**
	 * @param memberCode
	 * @return
	 * @see com.pay.base.service.acct.AcctService#createAcctAttrib(java.lang.Long)
	 */
	@Override
	public Long createAcctAttrib(final Long memberCode) {
		return acctDAO.createAcctAttrib(memberCode);
	}

	/**
	 * @param acctAttrId
	 * @return
	 * @see com.pay.base.service.acct.AcctService#getAccCodeById(java.lang.Long)
	 */
	@Override
	public String getAccCodeById(final Long acctAttrId) {
		return acctDAO.getAccCodeById(acctAttrId);
	}

	/**
	 * @param memberCode
	 * @param acctTypeId
	 * @return
	 * @see com.pay.base.service.acct.AcctService#getByMemberCode(long, int)
	 */
	@Override
	public Acct getByMemberCode(final long memberCode, final int acctTypeId) {
		return acctDAO.getByMemberCode(memberCode, acctTypeId);
	}

	@Override
	public Acct getSonAcctByParentMember(final long fatherMemberCode, final int acctTypeId,
			final String sonMemberName) {
		List<Acct> accts = acctDAO.getSonAcctByParentMember(fatherMemberCode,
				acctTypeId, sonMemberName);
		if (accts != null && accts.size() > 0) {
			return accts.get(0);
		}
		return null;
	}

	public void setAcctDAO(final AcctDAO acctDAO) {
		this.acctDAO = acctDAO;
	}

	@Override
	public ResultDto doResetPayPwdRnTx(final Long memberCode, final Integer acctType,
			String newPayPwd, final Long operatorId) {
		ResultDto result = new ResultDto();

		Acct acct = acctDAO.getByMemberCode(memberCode, acctType);
		AcctDto acctDto = null;
		acctDto = BeanConvertUtil.convert(AcctDto.class, acct);
		// 验证密码格式是否正确
		if (!CheckUtil.checkPayPwd(newPayPwd)) {
			logger.error("newPayPwd is not valide ");
			result.setErrorNum(ErrorCodeEnum.ACCT_VERIFY_PASSWORD_ERROR);
			return result;
		}
		// 验证帐号
		// 需要判断用户是否有效，如果该用户无效了，就不能进行密码的设置
		if (null == acctDto) {
			logger.error("acct is not exist ");
			result.setErrorNum(ErrorCodeEnum.ACCT_NON_EXIST_ERROR);
			return result;
		}
		// 需要判断用户是否有效，如果该用户无效了，就不能进行密码的设置
		if (null != acctDto
				&& acctDto.getStatus() == AcctStatusEnum.INVALID.getCode()) {
			logger.error("acct invalid error ");
			result.setErrorNum(ErrorCodeEnum.ACCT_INVALID_ERROR);
			return result;
		}
		// 需要判断用户是否冻结，如果该用户冻结了，就不能进行密码的设置
		if (null != acctDto
				&& acctDto.getStatus() == AcctStatusEnum.FROZEN.getCode()) {
			logger.error("acct frozen error ");
			result.setErrorNum(ErrorCodeEnum.ACCT_FROZEN_ERROR);
			return result;
		}
		boolean updateResult = false;// 默认失败
		// 密码加密
		try {
			newPayPwd = iMessageDigest.genMessageDigest(newPayPwd.getBytes());
			// acctAttribService.queryAcctAttribByAcctCode(acctDto.getAcctCode());
			if (operatorId != null && operatorId > 0) {
				updateResult = operatorService.updateOperatorPayPWD(memberCode,
						operatorId, newPayPwd);
			} else {
				/*updateResult = acctAttribService.resetAcctAttribPwd(
						acctDto.getAcctCode(), newPayPwd);*///add by peiyu.yang 这个为了在更新所有币种的支付密码
				updateResult = acctAttribService.resetAcctAttribPwd(null,String.valueOf(memberCode),newPayPwd);
			}
		} catch (Exception e) {
			logger.error("unknow error", e);
			result.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
			return result;
		}

		// 设置不成功
		if (!updateResult) {
			logger.error("memeber update password error");
			result.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_PASSWORD);
			return result;
		}
		result.setObject(SET_PASSWORD_SUCCEED);
		return result;
	}

	@Override
	public ResultDto doVerifyPayPasswordRnTx(final Long memberCode, final Integer acctType,
			String payPwd, final Long operatorId) {
		ResultDto result = new ResultDto();

		Acct acct = acctDAO.getByMemberCode(memberCode, acctType);
		AcctDto acctDto = null;
		acctDto = BeanConvertUtil.convert(AcctDto.class, acct);
		// 验证帐号
		if (null == acctDto) {
			logger.error("acct is not exist ");
			result.setErrorNum(ErrorCodeEnum.ACCT_NON_EXIST_ERROR);
			return result;
		}
		// 需要判断用户是否有效，如果该用户无效了，就不能进行密码的设置
		if (null != acctDto
				&& acctDto.getStatus() == AcctStatusEnum.INVALID.getCode()) {
			logger.error("acct invalid error ");
			result.setErrorNum(ErrorCodeEnum.ACCT_INVALID_ERROR);
			return result;
		}
		// 需要判断用户是否冻结，如果该用户冻结了，就不能进行密码的设置
		if (null != acctDto
				&& acctDto.getStatus() == AcctStatusEnum.FROZEN.getCode()) {
			logger.error("acct frozen error ");
			result.setErrorNum(ErrorCodeEnum.ACCT_FROZEN_ERROR);
			return result;
		}
		boolean verifyResult = false;// 默认失败
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = acctAttribService.queryAcctAttribByAcctCode(acctDto
					.getAcctCode());
			payPwd = iMessageDigest.genMessageDigest(payPwd.getBytes());
			if (operatorId != null && operatorId > 0) {
				Operator operator = operatorService.getOperatorById(operatorId);
				if (operator != null)
					verifyResult = payPwd.equals(operator.getPayPassWord());
			} else {
				verifyResult = payPwd.equals(acctAttribDto.getPayPwd()); // 匹配
			}

		} catch (Exception e) {
			logger.error("unknow error", e);
			result.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
			return result;
		}
		// 账户属性不存在
		if (null == acctAttribDto) {
			logger.error("acct attrib no exist ");
			result.setErrorNum(ErrorCodeEnum.ACCT_ATTRI_NO_EXIST);
			return result;
		}

		// 验证不成功
		if (!verifyResult) {
			logger.error("member verify password error");
			result.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
			return result;
		}
		result.setObject(VERIFY_PASSWORD_SUCCEED);
		return result;
	}

	@Override
	public boolean isHavePayPwd(final Long memberCode, final int acctType) {
		Acct acct = acctDAO.getByMemberCode(memberCode, acctType);
		if (acct != null) {
			AcctAttribDto attrib = acctAttribService
					.queryAcctAttribByAcctCode(acct.getAcctCode());
			if (attrib != null && attrib.getPayPwd() != null) {
				return true;
			}
		}
		return false;
	}

	public void setiMessageDigest(final IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setAcctAttribService(final AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public OperatorServiceFacade getOperatorService() {
		return operatorService;
	}

	public void setOperatorService(final OperatorServiceFacade operatorService) {
		this.operatorService = operatorService;
	}

	@Override
	public List<Acct> getAcctByMemberCode(final Long memberCode) {
		return acctDAO.getByMemberCode(memberCode);
	}

	@Override
	public Map<String,List<AcctInfo>> getAcctInfByMemberCode(final Long memberCode) {
		List<AcctInfo> list = acctDAO.getAcctInfoByMemberCode(memberCode);		
		Map<String,List<AcctInfo>> map = null;
		//List<AcctInfo> list_ = null;
		if(list!=null&&list.size()>0){
			map = new HashMap<String, List<AcctInfo>>();
			//list_= new ArrayList<AcctInfo>();
             for(int i=0;i<list.size();i++){
            	 AcctInfo acctInfo = list.get(i);
            	 acctInfo.setAcctName(AcctTypeEnum.getAcctNameByCode(acctInfo.getAcctType()));
            	 acctInfo.setCurName(CurrencyCodeEnum.getCurrencyNameByCode(acctInfo.getCurCode()));
                 acctInfo.setIsBasicAcct(AcctTypeEnum.isBasicCode(acctInfo.getAcctType())?1:0);
                 acctInfo.setIsFrozenAcct(acctInfo.getAcctType()==null?0:acctInfo.getAcctType()>200?1:0);
                 BigDecimal balance = acctInfo.getBalance();
                 
                 if(balance!=null)
                	 acctInfo.setBalance(balance.multiply(new BigDecimal(0.001)));
                 
                 BigDecimal frozenAmount = acctInfo.getFrozenAmount();
                 
                 if(frozenAmount!=null)
                        acctInfo.setFrozenAmount(frozenAmount.multiply(new BigDecimal(0.001)));
                 
            	 //list_.set(i, acctInfo);
            	 
            	 //对返回数据进行处理
            	 if(map.containsKey(acctInfo.getCurCode())){
            		  List<AcctInfo> listp = map.get(acctInfo.getCurCode());
            		  AcctInfo info = listp.get(0);
            		  //info是基本账户类型，acctInfo 是对应的保证金账户
            		  if(acctInfo.getAcctType()<200){
            			  info.setDebitBalance(acctInfo.getBalance());
            			  info.setDepFrozenAmount(acctInfo.getFrozenAmount());            			  
            		  }else if(acctInfo.getAcctType()>=200){
            			  info.setDepBalance(acctInfo.getBalance());
            			  info.setDepFrozenAmount(info.getFrozenAmount());
            			  info.setFrozenAmount(acctInfo.getFrozenAmount());
            			  info.setAcctName(acctInfo.getAcctName());
            		  }
            		  listp.set(0, info);
            		  map.put(info.getCurCode(),listp);
            	 }else{
            		 List<AcctInfo> tlist = new ArrayList<AcctInfo>();
            		 AcctInfo info= new AcctInfo();
            		 BeanUtils.copyProperties(acctInfo,info);
            		 tlist.add(info);
            		 map.put(info.getCurCode(),tlist);
            	 }
             }
            map.put("all",list);
            return map;
		}
		return null;
	}

	@Override
	public AcctInfo getAcctInfByAcctCode(final String acctCode) {
		AcctInfo info = acctDAO.getAcctInfoByAcctCode(acctCode);
	   	info.setAcctName(AcctTypeEnum.getAcctNameByCode(info.getAcctType()));
	   	info.setCurName(CurrencyCodeEnum.getCurrencyNameByCode(info.getCurCode()));
	    info.setIsBasicAcct(AcctTypeEnum.isBasicCode(info.getAcctType())?1:0);
	    info.setIsFrozenAcct(info.getAcctType()==null?0:info.getAcctType()>200?1:0);
		return info;
	}

	/* (non-Javadoc)
	 * @see com.pay.base.service.acct.AcctService#queryAcctByMemberCodeAndCurrency(java.lang.Long, java.lang.String)
	 */
	@Override
	public List<Acct> queryAcctByMemberCodeAndCurrency(Long memberCode,
			String currency) {
		return this.acctDAO.queryAcctByMemberCodeAndCurrency(memberCode, currency) ;
	}

}