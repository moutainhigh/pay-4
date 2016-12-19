package com.pay.poss.specialmerchant.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.specialmerchant.dao.ISpEnumInfoDao;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;
import com.pay.poss.specialmerchant.service.ISpEnumInfoService;

public class SpEnumInfoServiceImpl implements ISpEnumInfoService{

	private ISpEnumInfoDao spEnumInfoDao;

	public ISpEnumInfoDao getSpEnumInfoDao() {
		return spEnumInfoDao;
	}

	public void setSpEnumInfoDao(ISpEnumInfoDao spEnumInfoDao) {
		this.spEnumInfoDao = spEnumInfoDao;
	}
	
	@Override
	public Page<SpEnumInfoDto> search(Page<SpEnumInfoDto> paramPage,
			SpEnumInfoDto dto) {
		return spEnumInfoDao.findPage(paramPage, dto);
	}
	
	@Override
	public boolean addSpEnumInfoRnTx(String enumName, String enumCode,Long enumType,String value1,String value2) {
		
		
		SpEnumInfoDto dto = new SpEnumInfoDto();
		dto.setEnumName(enumName);
		dto.setEnumCode(enumCode);
		dto.setEnumType(enumType);
		dto.setValue1(value1);
		dto.setValue2(value2);
		boolean isUpdateOk = true;
		long enumId = (Long) spEnumInfoDao.create(dto); 
		if(enumId==0){
			isUpdateOk = false;
		}
	
		return isUpdateOk;
		
	}
	
	public boolean updateSpEnumInfoRnTx(Long id,String enumName,String value1,String value2){
		
		boolean isUpdateOk = spEnumInfoDao.updateSpEnumInfo(id,enumName,value1,value2)==1; 
		return isUpdateOk;
	}
	public List<SpEnumInfoDto> queryRangIdOrCardType(SpEnumInfoDto spEnumInfo){
		List<SpEnumInfoDto> spEnumInfoList = spEnumInfoDao.queryRangIdOrCardType(spEnumInfo);
		return spEnumInfoList;
	}
	@Override
	public SpEnumInfoDto getById(Long id) {
		return spEnumInfoDao.findById(id);
	}
	
	public boolean delSpEnumInfoRnTx(Long id){
		return spEnumInfoDao.delete(id);
	}
	
	public int findMaxEnumCode(int enumType){
		
		return spEnumInfoDao.findMaxEnumCode(enumType);
	}
}
