package com.pay.poss.authenticmanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.authenticmanager.dto.VerifyLogDto;
import com.pay.poss.authenticmanager.dto.VerifySearchDto;
import com.pay.poss.authenticmanager.dto.VerifySearchListDto;
import com.pay.poss.authenticmanager.model.IdcVerifyBase;
import com.pay.poss.authenticmanager.model.PossOperate;
import com.pay.poss.authenticmanager.model.TransLog;
import com.pay.poss.base.exception.PossException;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file IMerchantDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-9-19 gungun_zhang Create
 */

public interface IAuthenticDao extends BaseDAO<IdcVerifyBase> {
	public List<VerifySearchListDto> queryVerifyLog(
			VerifySearchDto verifySearchDto);

	public Integer queryVerifyLogCount(VerifySearchDto verifySearchDto);

	public VerifyLogDto getVerifyLogById(Long verifyId);

	public void updateVerifyLogStatus(Map<String, Object> dataMap)
			throws PossException;

	public void updatePoliceMessage(Map<String, Object> dataMap)
			throws PossException;

	public void insertOpLog(PossOperate possOperate);

	public String getAccountCode(Long memberCode);

	public String getOrderId();

	public String getOldOrderId(String verifyId);

	public void insertTransLog(TransLog transLog);

	public void updateTransLog(String orderId, String relatxOrderId);

}