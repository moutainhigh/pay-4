package com.pay.txncore.service.impl;

import com.pay.txncore.dao.PreControllerDAO;
import com.pay.txncore.model.PreController;
import com.pay.txncore.service.PreControllerService;

/**
 * Created by cuber on 2016/8/31.
 */
public class PreControllerServiceImpl  implements PreControllerService{

    private PreControllerDAO preControllerDAO;

    public PreControllerDAO getPreControllerDAO() {
        return preControllerDAO;
    }

    public void setPreControllerDAO(PreControllerDAO preControllerDAO) {
        this.preControllerDAO = preControllerDAO;
    }

    @Override
    public PreController saveAuthControllerRnTx(String orderId, long memberCode, long preAuthorAmount, String preCurrencyCode) {
        PreController preController = new PreController();
        preController.setOrderId(orderId);
        preController.setPreAuthorAmount(preAuthorAmount);
        preController.setPreCurrencyCode(preCurrencyCode);
        preController.setMemberCode(memberCode);
        long id = (Long)preControllerDAO.create(preController);
        preController.setId(id);
        return preController;
    }

    @Override
    public boolean updateAuthControllerRnTx(PreController PreController) {
        return preControllerDAO.update(PreController);
    }

    @Override
    public PreController getAuthControllerByMemberCodeAndOrderId(String orderId, long memberCode) {
        return preControllerDAO.findByMemberAndOrderId(memberCode,orderId);
    }

    @Override
    public PreController getAuthControllerByPreAuthTradeNo(long preAuthTradeNo) {
        return preControllerDAO.findObjectByCriteria("findByPreAuthTraderNo",preAuthTradeNo);
    }

    @Override
    public PreController getAuthControllerById(long id) {
        return preControllerDAO.findById(id);
    }
}
