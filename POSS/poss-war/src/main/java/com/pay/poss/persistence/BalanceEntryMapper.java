package com.pay.poss.persistence;


import com.pay.acc.deal.model.BalanceEntry;
import com.pay.poss.domain.ClearingBalanceEntry;
import com.pay.poss.domain.ClearingEntry;
import com.pay.poss.param.BalanceEntryQueryTradeParam;

import java.util.List;

public interface BalanceEntryMapper {
    int deleteByPrimaryKey(Long bentryId);

    int insert(ClearingBalanceEntry record);

    int insertSelective(ClearingBalanceEntry record);

    ClearingBalanceEntry selectByPrimaryKey(Long bentryId);

    int updateByPrimaryKeySelective(ClearingBalanceEntry record);

    int updateByPrimaryKey(ClearingBalanceEntry record);

    List<ClearingEntry> selectSelective(BalanceEntryQueryTradeParam param);

    public Integer countSelective(BalanceEntryQueryTradeParam param);



}