package com.pay.dcc.service.impl;

import com.pay.dcc.dao.CardBinInfoDAO;
import com.pay.dcc.model.CardBinInfo;
import com.pay.dcc.service.CardBinInfoService;

public class CardBinInfoServiceImpl implements CardBinInfoService {
	
	private CardBinInfoDAO cardBinInfoDAO;

	public void setCardBinInfoDAO(CardBinInfoDAO cardBinInfoDAO) {
		this.cardBinInfoDAO = cardBinInfoDAO;
	}
	
	@Override
	public CardBinInfo getCardBinInfo(String cardBin) {
		return cardBinInfoDAO.getCardBinInfo(cardBin);
	}
}
