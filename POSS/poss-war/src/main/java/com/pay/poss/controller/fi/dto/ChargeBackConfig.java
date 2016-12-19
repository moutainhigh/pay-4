package com.pay.poss.controller.fi.dto;

import java.util.Date;

public class ChargeBackConfig {
    /**
     * null
     */
    private Integer id;

    /**
     * 小于等于百分比
     */
    private String firstPercent;

    /**
     * 收取_____美元／笔
     */
    private String firstCost;

    /**
     * 大于百分比
     */
    private String secondPercent;

    /**
     * 小于百分比
     */
    private String thirdPercent;

    /**
     * 收取费用
     */
    private String secondCost;

    /**
     * 大于百分比
     */
    private String fourPercent;

    /**
     * null
     */
    private String fourCost;

    /**
     * null
     */
    private String operator;

    /**
     * null
     */
    private Date createDate;

    /**
     * null
     */
    private Date updateDate;
    
    private Long memberCode;

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.ID
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.FIRST_PERCENT
     */
    public String getFirstPercent() {
        return firstPercent;
    }

    /**
     *
     */
    public void setFirstPercent(String firstPercent) {
        this.firstPercent = firstPercent;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.FIRST_COST
     */
    public String getFirstCost() {
        return firstCost;
    }

    /**
     *
     */
    public void setFirstCost(String firstCost) {
        this.firstCost = firstCost;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.SECOND_PERCENT
     */
    public String getSecondPercent() {
        return secondPercent;
    }

    /**
     *
     */
    public void setSecondPercent(String secondPercent) {
        this.secondPercent = secondPercent;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.THIRD_PERCENT
     */
    public String getThirdPercent() {
        return thirdPercent;
    }

    /**
     *
     */
    public void setThirdPercent(String thirdPercent) {
        this.thirdPercent = thirdPercent;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.SECOND_COST
     */
    public String getSecondCost() {
        return secondCost;
    }

    /**
     *
     */
    public void setSecondCost(String secondCost) {
        this.secondCost = secondCost;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.FOUR_PERCENT
     */
    public String getFourPercent() {
        return fourPercent;
    }

    /**
     *
     */
    public void setFourPercent(String fourPercent) {
        this.fourPercent = fourPercent;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.FOUR_COST
     */
    public String getFourCost() {
        return fourCost;
    }

    /**
     *
     */
    public void setFourCost(String fourCost) {
        this.fourCost = fourCost;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.OPERATOR
     */
    public String getOperator() {
        return operator;
    }

    /**
     *
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.CREATE_DATE
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     *
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *
     * @return the value of CHARGE_BACK_CONFIG.UPDATE_DATE
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     *
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
}