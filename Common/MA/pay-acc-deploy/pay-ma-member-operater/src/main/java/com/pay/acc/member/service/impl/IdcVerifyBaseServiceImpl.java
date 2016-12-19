package com.pay.acc.member.service.impl;

import java.util.Map;

import com.pay.acc.member.dao.IdcVerifyBaseDAO;
import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.MemberVerifyResultDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.IdcVerifyBase;
import com.pay.acc.member.service.IdcVerifyBaseService;

public class IdcVerifyBaseServiceImpl implements IdcVerifyBaseService {

	
	private IdcVerifyBaseDAO idcVerifyBaseDAO;
	private final int STATUS = 1;
	private final int MEMBERLEVEL = 0;
	
	/**
	 * 根据memberCode查询实名认证信息
	 * @param memberCode
	 * @return MemberVerifyDto
	 * @exception Exception
	 */
	@Override
	public MemberVerifyResultDto QueryMemberVerifyByMemberCode(Long memberCode) throws Exception {
		MemberVerifyResultDto dto = new MemberVerifyResultDto();
		IdcVerifyBase  idcVerifyBase = idcVerifyBaseDAO.QueryMemberVerifyByMemberCode(memberCode);
		if(null!=idcVerifyBase){
			if(STATUS==idcVerifyBase.getStatus()){//如果最近一条记录实名认证成功
				dto.setVerify(true);//设置已认证
				dto.setMemberLevel(idcVerifyBase.getIsPaperFile());//取得真实状态
			}else{
				dto.setVerify(false);//设置未认证
				dto.setMemberLevel(MEMBERLEVEL);//设置状态为普通即0
			}
		}else{
			dto.setVerify(false);
			dto.setMemberLevel(MEMBERLEVEL);
		}
		return dto;
	}

	/* (non-Javadoc)
	 * @see com.pay.ma.service.idcverifybase.IdcVerifyBaseService#queryVerifySuccess(java.util.Map)
	 */
	public boolean queryVerifySuccess(Map<String, Object> paraMap)  throws MemberException, MemberUnknowException{
		//return (List<IdcVerifyDto>)idcVerifyBaseDAO.findIdcVerifyDtoList("queryVerifySuccessList",paraMap);
		if(null==paraMap){
			throw new MemberException("参数输入 "+paraMap+" 不正确");
		}
		
		boolean bool = false;
		try {
			bool =idcVerifyBaseDAO.findIdcVerifyDtoList("queryVerifySuccessCount",paraMap);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return bool;
	}


	
	/* (non-Javadoc)
	 * @see com.pay.acc.member.service.IdcVerifyBaseService#queryVerifyInfo(java.util.Map)
	 */
	public IdcVerifyDto queryVerifyInfo(Map<String, Object> paraMap)throws MemberException, MemberUnknowException {
		if(null==paraMap){
			throw new MemberException("paraMap参数输入 "+paraMap+" 不正确");
		}
		
		IdcVerifyDto idcVerifyDto = null;
		try {
			idcVerifyDto =(IdcVerifyDto) idcVerifyBaseDAO.findObjectByTemplate("queryVerifyInfo",paraMap);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}
		return idcVerifyDto;
	}
	
	
	public IdcVerifyBaseDAO getIdcVerifyBaseDAO() {
		return idcVerifyBaseDAO;
	}

	public void setIdcVerifyBaseDAO(IdcVerifyBaseDAO idcVerifyBaseDAO) {
		this.idcVerifyBaseDAO = idcVerifyBaseDAO;
	}

}
