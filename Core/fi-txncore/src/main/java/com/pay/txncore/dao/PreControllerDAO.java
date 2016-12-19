package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.model.PreController;

/**
 * Created by cuber on 2016/8/31.
 */
public interface PreControllerDAO  extends BaseDAO<PreController>{
    PreController findByMemberAndOrderId(long memberCode, String orderId);
}
