package com.pay.base.service.matrixcard.request.rules.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.app.common.helper.AppConf;
import com.pay.base.common.helper.matrixcard.ErrorCodeMatrixExceptionEnum;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.request.rules.IMatrixCardReqRuleService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;

/**
 * @author jim_chen
 * @version 
 * 2010-9-16 
 */
public class MatrixCardReqRuleServiceImpl implements IMatrixCardReqRuleService
{

	private MatrixCardTransMgrService matrixCardTransMgrService;

	@Override
	public void validate(MatrixCardTransInfoDto transInfoDto) throws MatrixCardException
	{
		String ip = transInfoDto.getIp();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.DATE, -1);
		Date dateFm = from.getTime();
		Date dateTo = Calendar.getInstance().getTime();
		paramMap.put("ip", ip);
		paramMap.put("creationDateBegin", dateFm);
		paramMap.put("creationDateEnd", dateTo);
		int count = this.matrixCardTransMgrService.countMartrixCardTransMgrByParamMap(paramMap);
		// 同一ip一 天申请的口令卡不能超过 100张
		String reqIpLimit=AppConf.get(AppConf.reqIpLimit);
		int ipLimit=0;
		if(StringUtils.isNotBlank(reqIpLimit)){
			ipLimit=Integer.parseInt(reqIpLimit);
		}
		if (count > ipLimit)
		{
			throw new MatrixCardException(ErrorCodeMatrixExceptionEnum.REQ_IP_LIMIT_ERROR, "Can only apply not more than 100 matrixCards in one day");
		}
	}



	public MatrixCardTransMgrService getMatrixCardTransMgrService()
	{
		return matrixCardTransMgrService;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService)
	{
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

}
