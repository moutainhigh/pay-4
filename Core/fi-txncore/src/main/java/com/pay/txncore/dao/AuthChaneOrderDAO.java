package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.AuthChangeOrderDTO;
import com.pay.txncore.model.AuthChangeOrder;

import java.util.List;
import java.util.Map;

/**
 * Created by cuber on 2016/9/1.
 */
public interface AuthChaneOrderDAO extends BaseDAO {

    List<AuthChangeOrder> findList(long preController);


    List<AuthChangeOrderDTO> findList(Map map);

    int findByValidate(Map map);
}
