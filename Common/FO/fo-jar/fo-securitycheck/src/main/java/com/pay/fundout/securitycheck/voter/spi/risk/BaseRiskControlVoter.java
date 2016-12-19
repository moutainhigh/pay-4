package com.pay.fundout.securitycheck.voter.spi.risk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.fundout.securitycheck.common.Constants;
import com.pay.fundout.securitycheck.model.DenyVoteMsg;
import com.pay.fundout.securitycheck.model.Principal;
import com.pay.fundout.securitycheck.model.RiskData;
import com.pay.fundout.securitycheck.riskdata.RiskDataStatistDispatcher;
import com.pay.fundout.securitycheck.voter.AbstractVoter;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.rm.service.service.facade.RCNameListService;

/**
 * <p>
 * 风控参数基础投票器
 * </p>
 * 
 * @author darv
 * 
 */
public abstract class BaseRiskControlVoter extends AbstractVoter {
	protected RiskDataStatistDispatcher riskDataStatService;
	private FoRcLimitFacade foRcLimitFacade;
	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	private MassPayService massPayService;
	protected boolean cacheRiskRule;
	protected Map<String, Integer> rcType;
	protected Map<String, Integer> bwList;
	protected RCNameListService rcNameListService;

	@Override
	protected int doVote(int busiType, Principal principal, DenyVoteMsg denyVoteMsg) {
		// 白名单判断
		try {
			// if (principal.getPayerBWFlag() == -2) {
			// boolean isWhiteList =
			// rcNameListService.isWhite(principal.getPayerMemberCode(),
			// Integer.valueOf(principal.getPayerAcctType()),
			// bwList.get(principal.getBusiType()
			// + principal.getPayerMemberType() +
			// principal.getPayeeMemberType()));
			// if (isWhiteList) {
			// principal.setPayerBWFlag(1);
			// }
			// }
			// if (principal.getPayerBWFlag() == 1) {
			// return GRANTED;
			// }
		} catch (Exception e) {
			denyVoteMsg.setCode(Constants.DENY_CODE_SYSTEM_ERROR);
			denyVoteMsg.setTips(new String[] { principal.toString(), this.toString() });
			return DENIED;
		}

		String rcParam = principal.getBusiType() + principal.getPayerMemberType() + principal.getPayeeMemberType();
		Integer mapParam = rcType.get(rcParam);
		if (mapParam == null) {
			logger.warn("没有找到对应的风控规则映射参数 [" + principal + "]");
			return DENIED;
		}

		return doVoteIfInWhiteList(busiType, principal, denyVoteMsg);
	}

	public void setRcNameListService(RCNameListService rcNameListService) {
		this.rcNameListService = rcNameListService;
	}

	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}


	protected final RiskData statRiskData(Principal principal) {
		return riskDataStatService.dispatch(principal);
	}

	public void setCacheRiskRule(boolean cacheRiskRule) {
		this.cacheRiskRule = cacheRiskRule;
	}

	public void setRiskDataStatService(RiskDataStatistDispatcher riskDataStatService) {
		this.riskDataStatService = riskDataStatService;
	}

	public void setRcType(Map<String, Integer> rcType) {
		this.rcType = rcType;
	}

	public void setBwList(Map<String, Integer> bwList) {
		this.bwList = bwList;
	}

	protected RCLimitResultDTO cacheRiskRuleIfNecessary(Principal principal) {
		if (cacheRiskRule == false) {
			return getBaseRule(principal);
		}

		RCLimitResultDTO baseRule = principal.getRiskRule();
		if (baseRule == null) {
			baseRule = getBaseRule(principal);
			principal.setRiskRule(baseRule);
		}
		return baseRule;
	}

	private RCLimitResultDTO getBaseRule(Principal principal) {
		RCLimitResultDTO rcLimitResultDTO = null;
		try {
			MemberVerifyResult verifyResult = massPayService.getMemberVerifyResult(Long.valueOf(principal.getPayerMemberCode()));
			if (verifyResult != null) {
				principal.setPayerAuthLevel(verifyResult.getMemberLevel());
			}
			if(2 == principal.getPayerMemberType()){
				if(2==principal.getPayeeMemberType()){
					rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2E.getKey(), null, Long.valueOf(principal.getPayerMemberCode()));
				}else{
					rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2P.getKey(), null, Long.valueOf(principal.getPayerMemberCode()));
				}
			}else{
				rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PAY_PERSONAL_ACC.getKey(), null, Long.valueOf(principal.getPayerMemberCode()));
			}
			
			if (rcLimitResultDTO == null) {
				logger.error("无法找到风控规则 [rcMap=" + RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2E.getKey() + ",principal=" + principal + "]");
				return null;
			}
			return rcLimitResultDTO;
		} catch (Exception e) {
			logger.error("无法找到风控规则 [ principal=" + principal + "]", e);
			return null;
		}
	}

	protected abstract int doVoteIfInWhiteList(int busiType, Principal principal, DenyVoteMsg denyVoteMsg);
}
