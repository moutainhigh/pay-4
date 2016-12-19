package com.pay.txncore.dto;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class TradeDataDTO {
    private TradeOrderDTO TradeOrderDTO;
    private Map<String, String> data;

    public com.pay.txncore.dto.TradeOrderDTO getTradeOrderDTO() {
        return TradeOrderDTO;
    }

    public void setTradeOrderDTO(com.pay.txncore.dto.TradeOrderDTO tradeOrderDTO) {
        TradeOrderDTO = tradeOrderDTO;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
