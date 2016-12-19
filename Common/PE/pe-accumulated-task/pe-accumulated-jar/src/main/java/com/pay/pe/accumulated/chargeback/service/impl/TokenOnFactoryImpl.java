package com.pay.pe.accumulated.chargeback.service.impl;

import java.util.Iterator;
import java.util.List;

import com.pay.pe.accumulated.chargeback.service.ChargeBackService;
import com.pay.pe.accumulated.chargeback.service.TokenOnFactory;

public class TokenOnFactoryImpl implements TokenOnFactory {

	List tokenOnList;

	@Override
	public ChargeBackService getChargeBackService(int takenOn) {
        for (Iterator ite = tokenOnList.iterator();ite.hasNext();) {
        	ChargeBackService chB = (ChargeBackService) ite.next();
            if (chB.getChargeBackmethod() == takenOn) {
                return chB;
            }
        }
        throw new RuntimeException(takenOn + " updateBalancemethod is not  definite");
	}

	public void setTokenOnList(List tokenOnList) {
		this.tokenOnList = tokenOnList;
	}
}
