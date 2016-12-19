package com.pay.poss.authenticmanager.service;

import java.util.List;

import com.pay.inf.exception.AppException;
import com.pay.poss.authenticmanager.dto.VerifyLogDto;
import com.pay.poss.authenticmanager.dto.VerifySearchDto;
import com.pay.poss.authenticmanager.dto.VerifySearchListDto;
import com.pay.poss.base.exception.PossException;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file IAuthenticService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-11-10 gungun_zhang Create
 */

public interface IAuthenticService {
	public List<VerifySearchListDto> queryVerifyLog(
			VerifySearchDto verifySearchDto);

	public Integer queryVerifyLogCount(VerifySearchDto verifySearchDto);

	public VerifyLogDto getVerifyLogById(String verifyId);

	public Boolean updateVerifyLogStatusTrans(VerifyLogDto verifyLogDto)
			throws PossException, AppException;

	public Boolean updateVerifyLogStatusFreeTrans(VerifyLogDto verifyLogDto)
			throws PossException, AppException;

}
