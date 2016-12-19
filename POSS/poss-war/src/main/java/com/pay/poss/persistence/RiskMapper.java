package com.pay.poss.persistence;

import com.pay.poss.domain.*;
import com.pay.poss.param.BankOrderParam;
import com.pay.poss.param.BaseTradeParam;
import com.pay.poss.param.TradeDataParam;

import java.util.List;

import static org.bouncycastle.asn1.x509.X509ObjectIdentifiers.id;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public interface RiskMapper {

    public List<RiskTradeData> selectOneZeroStarter(TradeDataParam param);

    public Integer countOneZeroStarter(TradeDataParam param);
    
    public List<RiskTradeChange> selectRiskTradeChange(BaseTradeParam param);

    public List<RiskMerchantMonitor> selectRiskMerchantMonitor(BaseTradeParam param);

    public List<RiskTradeData> selectRiskTradeData(TradeDataParam param);

    public Integer countRiskTradeData(TradeDataParam param);

    public List<RiskTradeDetail> selectRiskTradeDetail(BaseTradeParam param);

    public Integer countRiskTradeDetail(BaseTradeParam param);

    public List<RiskMerchantTrade> selectRiskMerchantTrade(BaseTradeParam param);

    public List<RiskChannelDailyReport> selectRiskChannelDailyReport(BaseTradeParam param);

    public List<RiskBankOrder> selectBankOrder(BankOrderParam param);

    public Integer countBankOrder(BankOrderParam param);
}
