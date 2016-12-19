package com.pay.poss.persistence;


import com.pay.poss.domain.*;
import com.pay.poss.param.BaseTradeParam;
import com.pay.poss.param.RefundOrderTradeParam;
import com.pay.poss.param.RefuseOrderTradeParam;
import com.pay.poss.param.SettlementTradeParam;

import java.util.List;

public interface ClearingMapper {


    public List<ClearingOrderSettle> selectSettleOrder(SettlementTradeParam param);

    public Integer countSettleOrder(SettlementTradeParam param);

    public List<ClearingRefuseOrder> selectRefuseOrder(RefuseOrderTradeParam param);

    public Integer countRefuseOrder(RefuseOrderTradeParam param);

    public List<ClearingRefundOrder> selectRefundOrder(RefundOrderTradeParam param);

    public Integer countRefundOrder(RefundOrderTradeParam param);

    public List<RiskOrderDetail> selectOrderDetail(SettlementTradeParam param);

    public Integer countOrderDetail(SettlementTradeParam param);

    public List<ClearingMigsRefundOrder> selectMigsRefundOrder(BaseTradeParam param);

    public Integer countMigsRefundOrder(BaseTradeParam param);
}