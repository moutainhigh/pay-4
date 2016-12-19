/**
 * 
 */
package com.pay.rm.service.service.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.service.dao.rmnamelist.RcNameListDAO;
import com.pay.rm.service.dto.rmnamelist.RcNameListDTO;
import com.pay.rm.service.model.rmnamelist.RcNameList;
import com.pay.rm.service.service.facade.RCNameListService;

/**
 * @author Administrator
 *
 */
public class RCNameListServiceImpl implements RCNameListService {
	
	private RcNameListDAO<RcNameList> rcNameListDAO;
	@Override
	public boolean isBlack(String memberId, int memberType, int businessId)
			throws RMFacadeException {
		
		int nameType = typeOfNameList(memberId, memberType, businessId);
		if(nameType==2) 
			return true;
		else 
			return false;
	}

	@Override
	public boolean isWhite(String memberId, int memberType, int businessId)
			throws RMFacadeException {
		int nameType = typeOfNameList(memberId, memberType, businessId);
		if(nameType==1) 
			return true;
		else 
			return false;
	}

	/* (non-Javadoc)
	 * @see com.pay.rm.service.service.facade.RCNameListService#typeOfNameList(java.lang.String, int, int)
	 */
	@Override
	public int typeOfNameList(String memberId, int memberType, int businessId)
			throws RMFacadeException {
		if(memberId==null||(memberId!=null&&memberId.trim().length()==0))  
			throw new RMFacadeException("查询黑白名单异常-缺少用户ID",ExceptionCodeEnum.NAMELIST_EXCEPTION);
		Map<String,String> map = new HashMap<String,String>();
		map.put("status", String.valueOf(1));
		map.put("memberType", String.valueOf(memberType));
		map.put("memberId", memberId);
		map.put("businessId", String.valueOf(businessId));
		List<RcNameList> list = rcNameListDAO.findBySelective(map);
		if(list==null) throw new RMFacadeException("查询黑白名单异常-返回列表为空",ExceptionCodeEnum.NAMELIST_EXCEPTION);
		if(list.size()>0){
			RcNameList rcNameList= list.get(0);
			return rcNameList.getNameType();
		}else{
			return 0;
		}
	}

	@Override
	public long createNameList(RcNameListDTO rcNameListDTO) throws RMFacadeException {
		// TODO Auto-generated method stub
		RcNameList rcNameList = new RcNameList();
		BeanUtils.copyProperties(rcNameListDTO, rcNameList);
		return (Long) rcNameListDAO.create(rcNameList);
	}

	public void setRcNameListDAO(RcNameListDAO<RcNameList> rcNameListDAO) {
		this.rcNameListDAO = rcNameListDAO;
	}

	
	
}
