package com.pay.txncore.service;

import com.pay.txncore.model.AuthChangeOrder;

/**
 * Created by cuber on 2016/9/1.
 */
public interface AuthChaneOrderService {
    AuthChangeOrder saveAuthControllerRnTx(String requestId,long preControllerId);

    boolean updateAuthChangeOrderRnTx(AuthChangeOrder AuthChangeOrder);
}
