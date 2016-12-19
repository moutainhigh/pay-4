/**
 * 
 */
package com.pay.acc.member.service.impl;

import com.pay.acc.member.dao.LinkerDAO;
import com.pay.acc.member.dto.LinkerDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.Linker;
import com.pay.acc.member.service.LinkerService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 *
 */
public class LinkerServiceImpl implements LinkerService {
	
	private LinkerDAO linkerDAO;

	/* (non-Javadoc)
	 * @see com.pay.acc.member.service.LinkerService#queryCheckLinkerWithMemberCode(java.lang.Long, java.lang.Long)
	 */
	@Override
	public LinkerDto queryCheckLinkerWithMemberCode(Long orgMemberCode, Long linkMemberCode) throws MemberException, MemberUnknowException {
		
		if(orgMemberCode==null||linkMemberCode==null||orgMemberCode.longValue()<=0||linkMemberCode.longValue()<=0){
			throw new MemberException("输入的参数有误");
		}
		Linker linker=null;
		try {
			linker = this.linkerDAO.queryMyLinkerWithMemberCode(orgMemberCode, linkMemberCode);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return BeanConvertUtil.convert(LinkerDto.class, linker);
	}

	/**
	 * @param linkerDAO the linkerDAO to set
	 */
	public void setLinkerDAO(LinkerDAO linkerDAO) {
		this.linkerDAO = linkerDAO;
	}
	

}
