package com.pay.base.service.specialmerchant;

import java.util.List;

import com.pay.base.model.CardType;
import com.pay.base.model.SpEnumInfo;
import com.pay.base.model.SpecialMerchant;
import com.pay.base.model.SpecialMerchantCity;

public interface SpecialmerchantService {

	public List<SpEnumInfo> getSpEnumInfoList(int enumType);
	
	public List<SpecialMerchantCity> getSpecialMerchantCityCodeList();
	
	public int querySpecialmerchantSum(SpecialMerchant specialMerchant);
	
	public List<SpecialMerchant> querySpecialMerchantList(int pageNo, int pageSize,SpecialMerchant dto);
	 
	public List<CardType> queryCardTypeList(CardType cardType);
	
	public SpecialMerchant getSpecialMerchantdetail(Long sp_merchant_id);
}
