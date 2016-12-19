package com.pay.txncore.service;

import com.pay.txncore.model.PreController;

/**
 * Created by cuber on 2016/8/31.
 */
public interface PreControllerService {

    PreController saveAuthControllerRnTx(String orderId,long memberCode, long preAuthorAmount, String preCurrencyCode);

    boolean updateAuthControllerRnTx(PreController PreController);

    PreController getAuthControllerByMemberCodeAndOrderId(String orderId, long memberCode);

    PreController getAuthControllerByPreAuthTradeNo(long preAuthTradeNo);

    PreController getAuthControllerById(long id);


}
