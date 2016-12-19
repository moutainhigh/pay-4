package com.pay.poss.param;

import java.util.List;

/**
 * Created by songyilin on 2016/10/14 0014.
 */
public class BankOrderParam extends BaseTradeParam{

    private List<String> bankOrderList;

    public List<String> getBankOrderList() {
        return bankOrderList;
    }

    public void setBankOrderList(List<String> bankOrderList) {
        this.bankOrderList = bankOrderList;
    }
}
