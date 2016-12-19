package com.pay.txncore.service.impl;

import com.pay.txncore.dao.AuthChaneOrderDAO;
import com.pay.txncore.model.AuthChangeOrder;
import com.pay.txncore.service.AuthChaneOrderService;

/**
 * Created by cuber on 2016/9/1.
 */
public class AuthChaneOrderServiceImpl implements AuthChaneOrderService {

    private AuthChaneOrderDAO authChaneOrderDAO;

    public void setAuthChaneOrderDAO(AuthChaneOrderDAO authChaneOrderDAO) {
        this.authChaneOrderDAO = authChaneOrderDAO;
    }

    @Override
    public AuthChangeOrder saveAuthControllerRnTx(String requestId, long preControllerId) {
        AuthChangeOrder authChangeOrder =  new AuthChangeOrder();
        authChangeOrder.setRequestId(requestId);
        authChangeOrder.setPreControllerId(preControllerId);
        long id = (Long)authChaneOrderDAO.create(authChangeOrder);
        authChangeOrder.setId(id);
        return authChangeOrder;
    }

    @Override
    public boolean updateAuthChangeOrderRnTx(AuthChangeOrder AuthChangeOrder) {
        return authChaneOrderDAO.update(AuthChangeOrder);
    }
}
