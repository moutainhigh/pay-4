package com.pay.txncore.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.AfterChannel2CashierDTO;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public interface AfterChannel2CashierDAO extends BaseDAO<AfterChannel2CashierDTO> {
    List<AfterChannel2CashierDTO> queryChannel2CashierDTO(AfterChannel2CashierDTO dto);
}
