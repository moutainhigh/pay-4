package com.pay.base.service.specialmerchant.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.dao.specialmerchant.SpecialmerchantDAO;
import com.pay.base.model.CardType;
import com.pay.base.model.SpEnumInfo;
import com.pay.base.model.SpecialMerchant;
import com.pay.base.model.SpecialMerchantCity;
import com.pay.base.service.specialmerchant.SpecialmerchantService;

public class SpecialmerchantServiceImpl implements SpecialmerchantService{

	private SpecialmerchantDAO specialmerchantDAO;
	
	public void setSpecialmerchantDAO(SpecialmerchantDAO specialmerchantDAO) {
		this.specialmerchantDAO = specialmerchantDAO;
	}

	@Override
	public List<SpEnumInfo> getSpEnumInfoList(int enumType) {
			List<SpEnumInfo> SpEnumInfos = specialmerchantDAO.getSpEnumInfoList(enumType);
	
			return SpEnumInfos;
	}
	
	public List<SpecialMerchantCity> getSpecialMerchantCityCodeList() {
		List<SpecialMerchantCity> SpecialMerchants = specialmerchantDAO.getSpecialMerchantCityCodeList();
		return SpecialMerchants;
     }
	
	public int querySpecialmerchantSum(SpecialMerchant specialMerchant) {
        return  (Integer)this.specialmerchantDAO.querySpecialmerchantSum(specialMerchant);
	}
	
	 public List<SpecialMerchant> querySpecialMerchantList(int pageNo, int pageSize,SpecialMerchant dto){
		 
		 Map<String,Object> paramMap = new HashMap<String,Object>();
		 paramMap.put("start", (pageNo-1)*pageSize+1);
		 paramMap.put("end", pageNo*pageSize);
		 paramMap.put("sp_merchant_name", dto.getSp_merchant_name());
		 paramMap.put("province_code", dto.getProvince_code());
		 paramMap.put("range_id", dto.getRange_id());	
		 paramMap.put("card_type_id", dto.getCard_type_id());
		 List<SpecialMerchant> SpecialMerchants = specialmerchantDAO.querySpecialMerchantList(paramMap);
		 return SpecialMerchants;
	 }
	 
    public List<CardType> queryCardTypeList(CardType cardType){
	 
		 Map<String,Object> paramMap = new HashMap<String,Object>();	
		 paramMap.put("sp_merchant_id", cardType.getSp_merchant_id());
		 List<CardType> CardTypes = specialmerchantDAO.queryCardTypeList(paramMap);
		 return CardTypes;
	 }
    
    public SpecialMerchant getSpecialMerchantdetail(Long sp_merchant_id){
    	 Map<String,Object> paramMap = new HashMap<String,Object>();
    	 paramMap.put("sp_merchant_id", sp_merchant_id);
    	 return specialmerchantDAO.getSpecialMerchantdetail(paramMap);
    }
}
