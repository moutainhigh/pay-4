package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.AuthChaneOrderDAO;
import com.pay.txncore.dao.TokenPayInfoDAO;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.service.TokenPayInfoService;

public class TokenPayInfoServiceImpl implements TokenPayInfoService {
	
	private TokenPayInfoDAO tokenPayInfoDAO;

	private AuthChaneOrderDAO authChaneOrderDAO;

	@Override
	public List<TokenPayInfo> getTokenPayInfos(Map<String, Object> parmas) {
		return tokenPayInfoDAO.findByCriteria(parmas);
	}

	@Override
	public List<TokenPayInfo> getTokenPayInfos(Map<String, Object> parmas,
			Page<TokenPayInfo> page) {
		return tokenPayInfoDAO.findByCriteria(parmas, page);
	}

	@Override
	public boolean saveTokenPayInfo(TokenPayInfo payInfo) {
        Long id= (Long) tokenPayInfoDAO.create("createToken", payInfo);
		return id!=null;
	}

	@Override
	public boolean update(TokenPayInfo payInfo) {
		return tokenPayInfoDAO.update(payInfo);
	}

	@Override
	public boolean delete(TokenPayInfo payInfo) {
		return tokenPayInfoDAO.delete(payInfo);
	}

	@Override
	public TokenPayInfo getTokenPayInfo(Long id) {
		return tokenPayInfoDAO.findById(id);
	}

	public void setTokenPayInfoDAO(TokenPayInfoDAO tokenPayInfoDAO) {
		this.tokenPayInfoDAO = tokenPayInfoDAO;
	}

	@Override
	public int findByValidate(Map<String, Object> parmas) {
		return authChaneOrderDAO.findByValidate(parmas);
	}

	public void setAuthChaneOrderDAO(AuthChaneOrderDAO authChaneOrderDAO) {
		this.authChaneOrderDAO = authChaneOrderDAO;
	}
}
