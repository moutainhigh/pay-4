package com.pay.app.controller.base.buySettleParities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acct.buySettleParities.dict.ApplyCheckDict;
import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.acct.buySettleParities.service.AccApplyCheckService;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.base.vo.MapVO;

/**
 * @author 蒋文哲 
 * 购结汇账户开通
 */
@Controller
public class BuySettleAccountController{
	
	@Autowired
	@Qualifier(value="acc-acctAttribService")
	private AcctAttribService acctAttribService;
	
	@Autowired
	private AccApplyCheckService accApplyCheckServiceImpl;
	Log logger = LogFactory.getLog(BuySettleAccountController.class);
	/**
	 * 账户开通申请验证
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/BuySettleAccountController/checkBuySettleAccount.do")
	@ResponseBody
	public Map<String, Object> checkBuySettleAccount(MapVO mapVo,HttpServletRequest request){
		Map<String, Object> result=new HashMap<String, Object>();
		List<AcctAttribDto> accattribDtoList=acctAttribService.QueryAcctAttribNsTx(Long.valueOf(mapVo.getData().get("memberCode")));
		int op=-1;
		try {
			logger.debug("前台参数:"+mapVo+"查询结果:"+accattribDtoList);
			op=checkAccountExist(mapVo, accattribDtoList,op);
			op=checkAccountApply(mapVo.getData(),op);
			op=saveAccApplyCheck(mapVo.getData(),op,request);
		} catch (Exception e) {
			op=3;
			logger.error("账户开通申请验证异常",e);
		}
		resultOp(result, op);
		logger.info("状态码:"+op+"返回结果:"+result);
		return result;
	}
	
	/**
	 * 验证账户是否开户
	 * @param mapVo
	 * @param accattribDtoList
	 * @param op
	 * @return
	 */
	private int checkAccountExist(MapVO mapVo, List<AcctAttribDto> accattribDtoList,int op) {
		if(op==-1){
			for (AcctAttribDto acctAttribDto : accattribDtoList) {
				if (acctAttribDto.getCurCode().equals(mapVo.getData().get("accountName"))) {
					op=1;
					break;
				}
			}
		}
		return op;
	}
	/**
	 * 验证重复提交
	 * @param data
	 * @return
	 */
	private int checkAccountApply(Map<String, String> data,int op){
		if(op==-1){
			AccApplyCheck accApplyCheck=new AccApplyCheck();
			accApplyCheck.setAccCurrencyCode(data.get("accountName"));
			accApplyCheck.setPartnerId(Long.valueOf(data.get("memberCode")));
			List<AccApplyCheck> resultList=accApplyCheckServiceImpl.queryConditions(accApplyCheck);
			if(resultList.size()>0){
				for (AccApplyCheck item : resultList) {
					if(ApplyCheckDict.WAIT_CHECK.getStatus().equals(item.getStatus())){
						op=2;
					}
				}
			}
		}
		return op;
	}
	/**
	 * 保存账户开通申请 
	 * @param data
	 */
	private int saveAccApplyCheck(Map<String, String> data,int op,HttpServletRequest request){
		if(op==-1){
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			AccApplyCheck accApplyCheck=new AccApplyCheck();
			accApplyCheck.setPartnerId(Long.valueOf(data.get("memberCode")));
			accApplyCheck.setPartnerName(data.get("partnerName"));
			accApplyCheck.setAccCurrencyCode(data.get("accountName"));
			accApplyCheck.setReason(data.get("accountUse"));
			accApplyCheck.setOperator(loginSession.getOperatorIdentity());
			accApplyCheckServiceImpl.createAccApplyCheck(accApplyCheck);
			op=0;
		}
		return op;
	}
	/**
	 * 返回码
	 * @param result
	 * @param op
	 */
	private void resultOp(Map<String, Object> result,int op){
		switch (op) {
		case 0:
			result.put("repCode", 0);
			result.put("message", "开通账户申请提交");
			logger.info("开通账户申请提交");
			break;
		case 1:
			result.put("repCode", 1);
			result.put("message", "账户已开户");
			logger.info("账户已开户");
			break;
		case 2:
			result.put("repCode", 2);
			result.put("message", "申请已提交，不能重复申请！");
			logger.info("申请已提交，不能重复申请！");
			break;
		case 3:
			result.put("repCode", 3);
			result.put("message", "系统异常");
			break;
		default:
			break;
		}
	}
}
