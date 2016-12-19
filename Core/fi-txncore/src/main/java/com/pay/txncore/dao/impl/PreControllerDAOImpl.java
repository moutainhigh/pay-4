package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.PreControllerDAO;
import com.pay.txncore.model.PreController;

/**
 * Created by cuber on 2016/8/31.
 */
public class PreControllerDAOImpl extends BaseDAOImpl<PreController> implements
        PreControllerDAO{
    @Override
    public PreController findByMemberAndOrderId(long memberCode, String orderId) {
        PreController controller =  new PreController();
        controller.setMemberCode(memberCode);
        controller.setOrderId(orderId);
        return super.findObjectByCriteria("findByMemberAndOrderId",controller);
    }
}
