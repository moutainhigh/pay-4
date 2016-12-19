/**
 * 
 */
package com.pay.acc.service.member.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.member.dto.LinkerDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.LinkerService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.LinkerQueryService;

/**
 * @author Administrator
 * 
 */
public class LinkerQueryServiceImpl implements LinkerQueryService {

	private LinkerService linkerService;

	private Log log = LogFactory.getLog(LinkerQueryServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.member.LinkerQueryService#
	 * queryIsLinkerWithMemberCode(java.lang.Long, java.lang.Long)
	 */
	@Override
	public boolean queryIsLinkerWithMemberCode(Long orgMemberCode, Long linkMemberCode) throws MaMemberQueryException {
		try {
			LinkerDto linkerDto = this.linkerService.queryCheckLinkerWithMemberCode(orgMemberCode, linkMemberCode);
			if (linkerDto != null) {
				return true;
			}
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "输入参数有误", e);
		} catch (MemberUnknowException e) {
			log.error("未知异常", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "未知异常", e);
		}
		return false;
	}

	/**
	 * @param linkerService
	 *            the linkerService to set
	 */
	public void setLinkerService(LinkerService linkerService) {
		this.linkerService = linkerService;
	}

}
