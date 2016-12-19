package com.pay.txncore.dao.impl;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.AfterChannel2CashierDAO;
import com.pay.txncore.dto.AfterChannel2CashierDTO;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public class AfterChannel2CashierDAOImpl extends BaseDAOImpl<AfterChannel2CashierDTO> implements AfterChannel2CashierDAO {

    @Override
    public List<AfterChannel2CashierDTO> queryChannel2CashierDTO(AfterChannel2CashierDTO dto) {
        return super.findByCriteria("queryChannel2CashierDTO",dto);
    }
}
