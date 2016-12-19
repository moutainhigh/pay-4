package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.util.JSonUtil;

/*
 * 根据用户创建新的币种账户
 * @author xiaodai.Rainy
 * @param memberCode、acctType（账户类型是101、102、103......）
 * @time 2015-11-25
 * 
 */
public class AddAcctCurrencyHandle implements EventHandler{
	
	private IEnterpriseService enterpriseService;

	
	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public final Log logger = LogFactory.getLog(AccountQueryHandler.class);

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		
		@SuppressWarnings("unchecked")
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,new HashMap<String, String>().getClass());

			Map<String, Object> result = new HashMap<String, Object>();
			 //获取参数
			String memberCode = paraMap.get("memberCode");
			String acctType = paraMap.get("acctType");
			
			String [] accountOfEnterprise = acctType.split(",");
			//参数校验
		if (StringUtils.isEmpty(memberCode)||StringUtils.isEmpty(acctType)) {
			
			result.put("responseCode", ResponseCodeEnum.INVALID_PARAM.getCode());
			result.put("responseCode", ResponseCodeEnum.INVALID_PARAM.getDesc());
		}
		try {
			//会员账户开通
			Boolean isSuccess = enterpriseService.accountOfEnterpriseEditTrans(
					memberCode, accountOfEnterprise);
			String sign = "";
			if (isSuccess) {
				sign = "开通成功!";
			} else {
				sign = "开通失败,请与管理员联系...";
				
			}
			result.put("sign", sign);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
			return JSonUtil.toJSonString(result);

		} catch (Exception e) {
			
			logger.error("addAcctCurrency error:", e);
			result.put("responseCode",ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			result.put("responseDesc",ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(result);
	}

}

