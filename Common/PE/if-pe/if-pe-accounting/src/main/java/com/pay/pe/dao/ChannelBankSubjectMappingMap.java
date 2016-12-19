package com.pay.pe.dao;

import java.util.HashMap;
import java.util.Map;

public class ChannelBankSubjectMappingMap extends HashMap{

	private ChannelBankSubjectMappingDAO channelBankSubjectMappingDAO;
	
	/*
	 * 1-出款,2-充退
	 */
	private Integer type;
	
	/*
	 * 出款map
	 */
	private static Map<String, String> payerBankSubjectMap;
	
	/*
	 * 充退map
	 */
	private static Map<String, String> payeeBankSubjectMap;
	
	public void setType(Integer type) {
		this.type = type;
	}

	public void setChannelBankSubjectMappingDAO(
			ChannelBankSubjectMappingDAO channelBankSubjectMappingDAO) {
		this.channelBankSubjectMappingDAO = channelBankSubjectMappingDAO;
	}
	
	public Object get(Object key) {
		if(type == 1){
			if(payerBankSubjectMap == null){
				payerBankSubjectMap = channelBankSubjectMappingDAO.queryChannelBankSubjectByType(type);
			}
			return payerBankSubjectMap.get(key);
		}else if(type == 2){
			if(payeeBankSubjectMap == null){
				payeeBankSubjectMap = channelBankSubjectMappingDAO.queryChannelBankSubjectByType(type);
			}
			return payeeBankSubjectMap.get(key);
		}
		return null;
	}
}
