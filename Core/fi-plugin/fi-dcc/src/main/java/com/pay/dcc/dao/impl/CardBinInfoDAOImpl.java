package com.pay.dcc.dao.impl;

import com.pay.dcc.dao.CardBinInfoDAO;
import com.pay.dcc.model.CardBinInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CardBinInfoDAOImpl extends BaseDAOImpl<CardBinInfo> implements CardBinInfoDAO {

	@Override
	public CardBinInfo getCardBinInfo(String cardBin) {
		return super.findById("findCardBinById",cardBin);
	}

}
