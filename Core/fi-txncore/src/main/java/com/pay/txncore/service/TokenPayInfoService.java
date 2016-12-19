package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.TokenPayInfo;

public interface TokenPayInfoService {
	 List<TokenPayInfo> getTokenPayInfos(Map<String,Object> parmas);
	 List<TokenPayInfo> getTokenPayInfos(Map<String,Object> parmas,Page<TokenPayInfo> page);
	 boolean saveTokenPayInfo(TokenPayInfo payInfo);
	 boolean update(TokenPayInfo payInfo);
	 boolean delete(TokenPayInfo payInfo);
	 TokenPayInfo getTokenPayInfo(Long id);
	 int findByValidate(Map<String,Object> parmas);
}
