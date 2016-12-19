package com.pay.poss.param;

import java.util.List;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class TradeDataParam extends BaseTradeParam{

    private List<String> grantCodeList;

    private List<String> tradeOrderNoList;

    private String tradeOrderLike;

    private List<String> tradeDateList;

    private List<String> cardNoList;

    public List<String> getTradeDateList() {
        return tradeDateList;
    }

    public void setTradeDateList(List<String> tradeDateList) {
        this.tradeDateList = tradeDateList;
    }

    public List<String> getCardNoList() {
        return cardNoList;
    }

    public void setCardNoList(List<String> cardNoList) {
        this.cardNoList = cardNoList;
    }

    public String getTradeOrderLike() {
        return tradeOrderLike;
    }

    public void setTradeOrderLike(String tradeOrderLike) {
        this.tradeOrderLike = tradeOrderLike;
    }

    public List<String> getTradeOrderNoList() {
        return tradeOrderNoList;
    }

    public void setTradeOrderNoList(List<String> tradeOrderNoList) {
        this.tradeOrderNoList = tradeOrderNoList;
    }

    public List<String> getGrantCodeList() {
        return grantCodeList;
    }

    public void setGrantCodeList(List<String> grantCodeList) {
        this.grantCodeList = grantCodeList;
    }


}
