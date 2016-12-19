package com.pay.base.dao.specialmerchant;

import java.util.List;
import java.util.Map;

import com.pay.base.model.CardType;
import com.pay.base.model.SpEnumInfo;
import com.pay.base.model.SpecialMerchant;
import com.pay.base.model.SpecialMerchantCity;

public interface SpecialmerchantDAO {

	 public List<SpEnumInfo> getSpEnumInfoList(int enumType);
	 
	 public List<SpecialMerchantCity> getSpecialMerchantCityCodeList(); 
	 
	 public int querySpecialmerchantSum(SpecialMerchant specialMerchant);
	 
	 public List<SpecialMerchant> querySpecialMerchantList(Map<String,Object> paramMap);
	 
	 public List<CardType> queryCardTypeList(Map<String,Object> paramMap);
	 
     public SpecialMerchant getSpecialMerchantdetail(Map<String,Object> paramMap);
     
     
}
