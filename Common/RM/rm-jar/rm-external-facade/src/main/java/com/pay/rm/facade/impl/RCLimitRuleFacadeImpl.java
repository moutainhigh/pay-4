/**
 *  <p>File: AbstractRCLimitProvider.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.facade.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.facade.dao.RCLimitRuleFacadeDao;
import com.pay.rm.facade.dto.RCLimitParamDTO;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.model.RCLimitParam;
import com.pay.rm.facade.model.RCLimitResult;
import com.pay.rm.facade.util.RCMEMBERTYPE;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.rm.service.service.facade.RCLimitService;
import com.pay.rm.service.service.rmlimit.risklevel.RmRiskLevelService;

/**
 * <p>风控限额限次Facade</p>
 * @author zengli
 * @since 2011-5-11
 * @see 
 */
public class RCLimitRuleFacadeImpl implements RcLimitRuleFacade {
	
	private Log logger = LogFactory.getLog(RCLimitRuleFacadeImpl.class);
	
	private RCLimitRuleFacadeDao rcLimitRuleFacadeDao;
	
	private RCLimitService rcLimitService;
	
	private RmRiskLevelService riskLevelService;
	
	/**
	 * @param riskLevelService the riskLevelService to set
	 */
	public void setRiskLevelService(RmRiskLevelService riskLevelService) {
		this.riskLevelService = riskLevelService;
	}
	/**
	 * @param rcLimitService the rcLimitService to set
	 */
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}
	/**
	 * 获取规则
	 */
	public RCLimitResultDTO getRule(RCLimitParamDTO rcLimitParamDTO)throws RMFacadeException {
		
		Assert.notNull(rcLimitParamDTO, "rcLimitParamDTO must be not null...");
		RCLimitResult rcLimitResult = null;
		try{
			RCLimitParam rcLimitParam = dtoToModelByParam(rcLimitParamDTO);
			//判断会员是企业会员还是个人会员
			if(RCMEMBERTYPE.ENTERPRISE.getValue() == rcLimitParam.getMemberType() ){
				//查询企业会员是否定制
				rcLimitResult = rcLimitRuleFacadeDao.queryCustomBusinessRcRule(rcLimitParam);
				//为空则为未定制
				if(rcLimitResult == null){
					//未查找到定制的,查找基本商户限额的
					rcLimitResult = rcLimitRuleFacadeDao.queryBusinessRcRule(rcLimitParam);
					if(rcLimitResult != null){
						//与行业相关
						if( null != rcLimitParam.getMccCode() && 0 != rcLimitParam.getMccCode()){
							coventResultByMcc(rcLimitResult, rcLimitParam.getMccCode());
						}
					}
				}
			}else{
				rcLimitResult = rcLimitRuleFacadeDao.queryCustomUserRcRule(rcLimitParam);
				if(rcLimitResult == null){
					rcLimitResult = rcLimitRuleFacadeDao.queryUserRcRule(rcLimitParam);
				}
			}
		}catch (Exception e) {
			logger.error("获取风控规则出错"+e.getMessage()+"["+rcLimitParamDTO+"]",e);
			throw new RMFacadeException("获取风控规则出错"+e.getMessage()+"["+rcLimitParamDTO+"]", ExceptionCodeEnum.EXPORT_ERROR);
		}
		
		return dtoToModelByResult(rcLimitResult);
	}
	/**
	 * @param rcLimitRuleFacadeDao the rcLimitRuleFacadeDao to set
	 */
	public void setRcLimitRuleFacadeDao(RCLimitRuleFacadeDao rcLimitRuleFacadeDao) {
		this.rcLimitRuleFacadeDao = rcLimitRuleFacadeDao;
	}
	
	
	private RCLimitResult coventResultByMcc(RCLimitResult limitResult,Long mccCode){
		RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(mccCode); 
		BigDecimal bSingleLimit = new BigDecimal(limitResult.getSingleLimit()); //单笔限额倍数
		BigDecimal bDayLimit = new BigDecimal(limitResult.getDayLimit());//每日限额倍数  
		BigDecimal mSingleLimit = new BigDecimal(rcMccLimit.getSingleLimit());//行业单笔限额
		limitResult.setBatchAccounts(limitResult.getBatchAccounts());
		limitResult.setDayLimit(bDayLimit.multiply(mSingleLimit).longValue());  //行业限额乘以每日限额倍数
		limitResult.setDayTimes(limitResult.getDayTimes());
		limitResult.setMonthLimit(limitResult.getMonthLimit());
		limitResult.setMonthTimes(limitResult.getMonthTimes());
		limitResult.setSingleLimit(bSingleLimit.multiply(mSingleLimit).longValue());//行业限额乘以单笔限额倍数
		return limitResult;
	}
	
	/**
	 * rcParam DTO转换Model
	 * @param rcLimitParamDTO
	 * @return
	 */
	private RCLimitParam dtoToModelByParam(RCLimitParamDTO rcLimitParamDTO ){
		RCLimitParam rcLimitParam = new RCLimitParam();
		if(rcLimitParamDTO != null) {
			BeanUtils.copyProperties(rcLimitParamDTO, rcLimitParam);
		}
		return rcLimitParam;
	}
	/**
	 * Result DTO转换Model
	 * @param rcLimitParamDTO
	 * @return
	 */
	private RCLimitResultDTO dtoToModelByResult(RCLimitResult limitResult ){
		RCLimitResultDTO rcLimitResultDTO = new RCLimitResultDTO();
		if(limitResult != null) {
			BeanUtils.copyProperties(limitResult, rcLimitResultDTO);
		}
		return rcLimitResultDTO;
	}
	/* (non-Javadoc)
	 * @see com.pay.rm.facade.RcLimitRuleFacade#isExistMcc(java.lang.Long)
	 */
	@Override
	public boolean isExistMcc(Long mccCode) throws RMFacadeException {
		RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(mccCode);
		if(rcMccLimit==null){
			return false;
		}else{
			return true;
		}
	}
	/* (non-Javadoc)
	 * @see com.pay.rm.facade.RcLimitRuleFacade#getRiskLevelList()
	 */
	@Override
	public List<RcRiskLevel> getRiskLevelList() {
		return riskLevelService.getRiskLevelList();
	}
	
	
	
	
}
