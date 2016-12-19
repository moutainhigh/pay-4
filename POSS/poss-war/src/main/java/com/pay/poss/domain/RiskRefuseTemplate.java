package com.pay.poss.domain;

/**
 * Created by songyilin on 2016/10/17 0017.
 */
public class RiskRefuseTemplate {


    private String tradeDateTemplate;

    private String cardNo;

    private String authorisation;

    public String getTradeDateTemplate() {
        return tradeDateTemplate;
    }

    public void setTradeDateTemplate(String tradeDateTemplate) {
        this.tradeDateTemplate = tradeDateTemplate;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getAuthorisation() {
        return authorisation;
    }

    public void setAuthorisation(String authorisation) {
        this.authorisation = authorisation;
    }
}
