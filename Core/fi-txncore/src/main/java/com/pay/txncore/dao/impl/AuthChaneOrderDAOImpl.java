package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.AuthChaneOrderDAO;
import com.pay.txncore.dto.AuthChangeOrderDTO;
import com.pay.txncore.model.AuthChangeOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by cuber on 2016/9/1.
 */
public class AuthChaneOrderDAOImpl  extends BaseDAOImpl implements AuthChaneOrderDAO {


    @Override
    public List<AuthChangeOrder> findList(long preController) {
        return null;
    }

    @Override
    public List<AuthChangeOrderDTO> findList(Map map) {
        return super.findByCriteria("findListByCondition",map);
    }

    @Override
    public int findByValidate(Map map) {
        return (Integer) super.findObjectByCriteria("findByValidate",map);
    }
}
