package com.pay.fo.bankcorp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankChannelConfigCriteria {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected List oredCriteria;

    /**
     */
    public BankChannelConfigCriteria() {
        oredCriteria = new ArrayList();
    }

    /**
     */
    protected BankChannelConfigCriteria(BankChannelConfigCriteria example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
    }

    /**
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     */
    public List getOredCriteria() {
        return oredCriteria;
    }

    /**
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     */
    public void clear() {
        oredCriteria.clear();
    }

    /**
     */
    public static class Criteria {
        protected List criteriaWithoutValue;

        protected List criteriaWithSingleValue;

        protected List criteriaWithListValue;

        protected List criteriaWithBetweenValue;

        protected Criteria() {
            super();
            criteriaWithoutValue = new ArrayList();
            criteriaWithSingleValue = new ArrayList();
            criteriaWithListValue = new ArrayList();
            criteriaWithBetweenValue = new ArrayList();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public List getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public List getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public List getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteriaWithoutValue.add(condition);
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List list = new ArrayList();
            list.add(value1);
            list.add(value2);
            Map map = new HashMap();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andChannelCodeIsNull() {
            addCriterion("CHANNEL_CODE is null");
            return this;
        }

        public Criteria andChannelCodeIsNotNull() {
            addCriterion("CHANNEL_CODE is not null");
            return this;
        }

        public Criteria andChannelCodeEqualTo(String value) {
            addCriterion("CHANNEL_CODE =", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeNotEqualTo(String value) {
            addCriterion("CHANNEL_CODE <>", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeGreaterThan(String value) {
            addCriterion("CHANNEL_CODE >", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CHANNEL_CODE >=", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeLessThan(String value) {
            addCriterion("CHANNEL_CODE <", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeLessThanOrEqualTo(String value) {
            addCriterion("CHANNEL_CODE <=", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeLike(String value) {
            addCriterion("CHANNEL_CODE like", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeNotLike(String value) {
            addCriterion("CHANNEL_CODE not like", value, "channelCode");
            return this;
        }

        public Criteria andChannelCodeIn(List values) {
            addCriterion("CHANNEL_CODE in", values, "channelCode");
            return this;
        }

        public Criteria andChannelCodeNotIn(List values) {
            addCriterion("CHANNEL_CODE not in", values, "channelCode");
            return this;
        }

        public Criteria andChannelCodeBetween(String value1, String value2) {
            addCriterion("CHANNEL_CODE between", value1, value2, "channelCode");
            return this;
        }

        public Criteria andChannelCodeNotBetween(String value1, String value2) {
            addCriterion("CHANNEL_CODE not between", value1, value2, "channelCode");
            return this;
        }

        public Criteria andBankNameIsNull() {
            addCriterion("BANK_NAME is null");
            return this;
        }

        public Criteria andBankNameIsNotNull() {
            addCriterion("BANK_NAME is not null");
            return this;
        }

        public Criteria andBankNameEqualTo(String value) {
            addCriterion("BANK_NAME =", value, "bankName");
            return this;
        }

        public Criteria andBankNameNotEqualTo(String value) {
            addCriterion("BANK_NAME <>", value, "bankName");
            return this;
        }

        public Criteria andBankNameGreaterThan(String value) {
            addCriterion("BANK_NAME >", value, "bankName");
            return this;
        }

        public Criteria andBankNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_NAME >=", value, "bankName");
            return this;
        }

        public Criteria andBankNameLessThan(String value) {
            addCriterion("BANK_NAME <", value, "bankName");
            return this;
        }

        public Criteria andBankNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_NAME <=", value, "bankName");
            return this;
        }

        public Criteria andBankNameLike(String value) {
            addCriterion("BANK_NAME like", value, "bankName");
            return this;
        }

        public Criteria andBankNameNotLike(String value) {
            addCriterion("BANK_NAME not like", value, "bankName");
            return this;
        }

        public Criteria andBankNameIn(List values) {
            addCriterion("BANK_NAME in", values, "bankName");
            return this;
        }

        public Criteria andBankNameNotIn(List values) {
            addCriterion("BANK_NAME not in", values, "bankName");
            return this;
        }

        public Criteria andBankNameBetween(String value1, String value2) {
            addCriterion("BANK_NAME between", value1, value2, "bankName");
            return this;
        }

        public Criteria andBankNameNotBetween(String value1, String value2) {
            addCriterion("BANK_NAME not between", value1, value2, "bankName");
            return this;
        }

        public Criteria andBankAccIsNull() {
            addCriterion("BANK_ACC is null");
            return this;
        }

        public Criteria andBankAccIsNotNull() {
            addCriterion("BANK_ACC is not null");
            return this;
        }

        public Criteria andBankAccEqualTo(String value) {
            addCriterion("BANK_ACC =", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccNotEqualTo(String value) {
            addCriterion("BANK_ACC <>", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccGreaterThan(String value) {
            addCriterion("BANK_ACC >", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ACC >=", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccLessThan(String value) {
            addCriterion("BANK_ACC <", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccLessThanOrEqualTo(String value) {
            addCriterion("BANK_ACC <=", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccLike(String value) {
            addCriterion("BANK_ACC like", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccNotLike(String value) {
            addCriterion("BANK_ACC not like", value, "bankAcc");
            return this;
        }

        public Criteria andBankAccIn(List values) {
            addCriterion("BANK_ACC in", values, "bankAcc");
            return this;
        }

        public Criteria andBankAccNotIn(List values) {
            addCriterion("BANK_ACC not in", values, "bankAcc");
            return this;
        }

        public Criteria andBankAccBetween(String value1, String value2) {
            addCriterion("BANK_ACC between", value1, value2, "bankAcc");
            return this;
        }

        public Criteria andBankAccNotBetween(String value1, String value2) {
            addCriterion("BANK_ACC not between", value1, value2, "bankAcc");
            return this;
        }

        public Criteria andBankAccNameIsNull() {
            addCriterion("BANK_ACC_NAME is null");
            return this;
        }

        public Criteria andBankAccNameIsNotNull() {
            addCriterion("BANK_ACC_NAME is not null");
            return this;
        }

        public Criteria andBankAccNameEqualTo(String value) {
            addCriterion("BANK_ACC_NAME =", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameNotEqualTo(String value) {
            addCriterion("BANK_ACC_NAME <>", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameGreaterThan(String value) {
            addCriterion("BANK_ACC_NAME >", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_ACC_NAME >=", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameLessThan(String value) {
            addCriterion("BANK_ACC_NAME <", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameLessThanOrEqualTo(String value) {
            addCriterion("BANK_ACC_NAME <=", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameLike(String value) {
            addCriterion("BANK_ACC_NAME like", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameNotLike(String value) {
            addCriterion("BANK_ACC_NAME not like", value, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameIn(List values) {
            addCriterion("BANK_ACC_NAME in", values, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameNotIn(List values) {
            addCriterion("BANK_ACC_NAME not in", values, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameBetween(String value1, String value2) {
            addCriterion("BANK_ACC_NAME between", value1, value2, "bankAccName");
            return this;
        }

        public Criteria andBankAccNameNotBetween(String value1, String value2) {
            addCriterion("BANK_ACC_NAME not between", value1, value2, "bankAccName");
            return this;
        }

        public Criteria andMinRemindedBalanceIsNull() {
            addCriterion("MIN_REMINDED_BALANCE is null");
            return this;
        }

        public Criteria andMinRemindedBalanceIsNotNull() {
            addCriterion("MIN_REMINDED_BALANCE is not null");
            return this;
        }

        public Criteria andMinRemindedBalanceEqualTo(Long value) {
            addCriterion("MIN_REMINDED_BALANCE =", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceNotEqualTo(Long value) {
            addCriterion("MIN_REMINDED_BALANCE <>", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceGreaterThan(Long value) {
            addCriterion("MIN_REMINDED_BALANCE >", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceGreaterThanOrEqualTo(Long value) {
            addCriterion("MIN_REMINDED_BALANCE >=", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceLessThan(Long value) {
            addCriterion("MIN_REMINDED_BALANCE <", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceLessThanOrEqualTo(Long value) {
            addCriterion("MIN_REMINDED_BALANCE <=", value, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceIn(List values) {
            addCriterion("MIN_REMINDED_BALANCE in", values, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceNotIn(List values) {
            addCriterion("MIN_REMINDED_BALANCE not in", values, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceBetween(Long value1, Long value2) {
            addCriterion("MIN_REMINDED_BALANCE between", value1, value2, "minRemindedBalance");
            return this;
        }

        public Criteria andMinRemindedBalanceNotBetween(Long value1, Long value2) {
            addCriterion("MIN_REMINDED_BALANCE not between", value1, value2, "minRemindedBalance");
            return this;
        }

        public Criteria andIsSupportMultipleIsNull() {
            addCriterion("IS_SUPPORT_MULTIPLE is null");
            return this;
        }

        public Criteria andIsSupportMultipleIsNotNull() {
            addCriterion("IS_SUPPORT_MULTIPLE is not null");
            return this;
        }

        public Criteria andIsSupportMultipleEqualTo(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE =", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleNotEqualTo(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE <>", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleGreaterThan(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE >", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE >=", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleLessThan(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE <", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleLessThanOrEqualTo(Integer value) {
            addCriterion("IS_SUPPORT_MULTIPLE <=", value, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleIn(List values) {
            addCriterion("IS_SUPPORT_MULTIPLE in", values, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleNotIn(List values) {
            addCriterion("IS_SUPPORT_MULTIPLE not in", values, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleBetween(Integer value1, Integer value2) {
            addCriterion("IS_SUPPORT_MULTIPLE between", value1, value2, "isSupportMultiple");
            return this;
        }

        public Criteria andIsSupportMultipleNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_SUPPORT_MULTIPLE not between", value1, value2, "isSupportMultiple");
            return this;
        }

        public Criteria andUpperLimitIsNull() {
            addCriterion("UPPER_LIMIT is null");
            return this;
        }

        public Criteria andUpperLimitIsNotNull() {
            addCriterion("UPPER_LIMIT is not null");
            return this;
        }

        public Criteria andUpperLimitEqualTo(Long value) {
            addCriterion("UPPER_LIMIT =", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitNotEqualTo(Long value) {
            addCriterion("UPPER_LIMIT <>", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitGreaterThan(Long value) {
            addCriterion("UPPER_LIMIT >", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitGreaterThanOrEqualTo(Long value) {
            addCriterion("UPPER_LIMIT >=", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitLessThan(Long value) {
            addCriterion("UPPER_LIMIT <", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitLessThanOrEqualTo(Long value) {
            addCriterion("UPPER_LIMIT <=", value, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitIn(List values) {
            addCriterion("UPPER_LIMIT in", values, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitNotIn(List values) {
            addCriterion("UPPER_LIMIT not in", values, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitBetween(Long value1, Long value2) {
            addCriterion("UPPER_LIMIT between", value1, value2, "upperLimit");
            return this;
        }

        public Criteria andUpperLimitNotBetween(Long value1, Long value2) {
            addCriterion("UPPER_LIMIT not between", value1, value2, "upperLimit");
            return this;
        }

        public Criteria andLowerLimitIsNull() {
            addCriterion("LOWER_LIMIT is null");
            return this;
        }

        public Criteria andLowerLimitIsNotNull() {
            addCriterion("LOWER_LIMIT is not null");
            return this;
        }

        public Criteria andLowerLimitEqualTo(Long value) {
            addCriterion("LOWER_LIMIT =", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitNotEqualTo(Long value) {
            addCriterion("LOWER_LIMIT <>", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitGreaterThan(Long value) {
            addCriterion("LOWER_LIMIT >", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitGreaterThanOrEqualTo(Long value) {
            addCriterion("LOWER_LIMIT >=", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitLessThan(Long value) {
            addCriterion("LOWER_LIMIT <", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitLessThanOrEqualTo(Long value) {
            addCriterion("LOWER_LIMIT <=", value, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitIn(List values) {
            addCriterion("LOWER_LIMIT in", values, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitNotIn(List values) {
            addCriterion("LOWER_LIMIT not in", values, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitBetween(Long value1, Long value2) {
            addCriterion("LOWER_LIMIT between", value1, value2, "lowerLimit");
            return this;
        }

        public Criteria andLowerLimitNotBetween(Long value1, Long value2) {
            addCriterion("LOWER_LIMIT not between", value1, value2, "lowerLimit");
            return this;
        }

        public Criteria andMaxSupportItemsIsNull() {
            addCriterion("MAX_SUPPORT_ITEMS is null");
            return this;
        }

        public Criteria andMaxSupportItemsIsNotNull() {
            addCriterion("MAX_SUPPORT_ITEMS is not null");
            return this;
        }

        public Criteria andMaxSupportItemsEqualTo(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS =", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsNotEqualTo(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS <>", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsGreaterThan(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS >", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsGreaterThanOrEqualTo(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS >=", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsLessThan(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS <", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsLessThanOrEqualTo(Integer value) {
            addCriterion("MAX_SUPPORT_ITEMS <=", value, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsIn(List values) {
            addCriterion("MAX_SUPPORT_ITEMS in", values, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsNotIn(List values) {
            addCriterion("MAX_SUPPORT_ITEMS not in", values, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsBetween(Integer value1, Integer value2) {
            addCriterion("MAX_SUPPORT_ITEMS between", value1, value2, "maxSupportItems");
            return this;
        }

        public Criteria andMaxSupportItemsNotBetween(Integer value1, Integer value2) {
            addCriterion("MAX_SUPPORT_ITEMS not between", value1, value2, "maxSupportItems");
            return this;
        }

        public Criteria andServerAddressIsNull() {
            addCriterion("SERVER_ADDRESS is null");
            return this;
        }

        public Criteria andServerAddressIsNotNull() {
            addCriterion("SERVER_ADDRESS is not null");
            return this;
        }

        public Criteria andServerAddressEqualTo(String value) {
            addCriterion("SERVER_ADDRESS =", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressNotEqualTo(String value) {
            addCriterion("SERVER_ADDRESS <>", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressGreaterThan(String value) {
            addCriterion("SERVER_ADDRESS >", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_ADDRESS >=", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressLessThan(String value) {
            addCriterion("SERVER_ADDRESS <", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressLessThanOrEqualTo(String value) {
            addCriterion("SERVER_ADDRESS <=", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressLike(String value) {
            addCriterion("SERVER_ADDRESS like", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressNotLike(String value) {
            addCriterion("SERVER_ADDRESS not like", value, "serverAddress");
            return this;
        }

        public Criteria andServerAddressIn(List values) {
            addCriterion("SERVER_ADDRESS in", values, "serverAddress");
            return this;
        }

        public Criteria andServerAddressNotIn(List values) {
            addCriterion("SERVER_ADDRESS not in", values, "serverAddress");
            return this;
        }

        public Criteria andServerAddressBetween(String value1, String value2) {
            addCriterion("SERVER_ADDRESS between", value1, value2, "serverAddress");
            return this;
        }

        public Criteria andServerAddressNotBetween(String value1, String value2) {
            addCriterion("SERVER_ADDRESS not between", value1, value2, "serverAddress");
            return this;
        }

        public Criteria andServerPortIsNull() {
            addCriterion("SERVER_PORT is null");
            return this;
        }

        public Criteria andServerPortIsNotNull() {
            addCriterion("SERVER_PORT is not null");
            return this;
        }

        public Criteria andServerPortEqualTo(Integer value) {
            addCriterion("SERVER_PORT =", value, "serverPort");
            return this;
        }

        public Criteria andServerPortNotEqualTo(Integer value) {
            addCriterion("SERVER_PORT <>", value, "serverPort");
            return this;
        }

        public Criteria andServerPortGreaterThan(Integer value) {
            addCriterion("SERVER_PORT >", value, "serverPort");
            return this;
        }

        public Criteria andServerPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("SERVER_PORT >=", value, "serverPort");
            return this;
        }

        public Criteria andServerPortLessThan(Integer value) {
            addCriterion("SERVER_PORT <", value, "serverPort");
            return this;
        }

        public Criteria andServerPortLessThanOrEqualTo(Integer value) {
            addCriterion("SERVER_PORT <=", value, "serverPort");
            return this;
        }

        public Criteria andServerPortIn(List values) {
            addCriterion("SERVER_PORT in", values, "serverPort");
            return this;
        }

        public Criteria andServerPortNotIn(List values) {
            addCriterion("SERVER_PORT not in", values, "serverPort");
            return this;
        }

        public Criteria andServerPortBetween(Integer value1, Integer value2) {
            addCriterion("SERVER_PORT between", value1, value2, "serverPort");
            return this;
        }

        public Criteria andServerPortNotBetween(Integer value1, Integer value2) {
            addCriterion("SERVER_PORT not between", value1, value2, "serverPort");
            return this;
        }

        public Criteria andMacKeyIsNull() {
            addCriterion("MAC_KEY is null");
            return this;
        }

        public Criteria andMacKeyIsNotNull() {
            addCriterion("MAC_KEY is not null");
            return this;
        }

        public Criteria andMacKeyEqualTo(String value) {
            addCriterion("MAC_KEY =", value, "macKey");
            return this;
        }

        public Criteria andMacKeyNotEqualTo(String value) {
            addCriterion("MAC_KEY <>", value, "macKey");
            return this;
        }

        public Criteria andMacKeyGreaterThan(String value) {
            addCriterion("MAC_KEY >", value, "macKey");
            return this;
        }

        public Criteria andMacKeyGreaterThanOrEqualTo(String value) {
            addCriterion("MAC_KEY >=", value, "macKey");
            return this;
        }

        public Criteria andMacKeyLessThan(String value) {
            addCriterion("MAC_KEY <", value, "macKey");
            return this;
        }

        public Criteria andMacKeyLessThanOrEqualTo(String value) {
            addCriterion("MAC_KEY <=", value, "macKey");
            return this;
        }

        public Criteria andMacKeyLike(String value) {
            addCriterion("MAC_KEY like", value, "macKey");
            return this;
        }

        public Criteria andMacKeyNotLike(String value) {
            addCriterion("MAC_KEY not like", value, "macKey");
            return this;
        }

        public Criteria andMacKeyIn(List values) {
            addCriterion("MAC_KEY in", values, "macKey");
            return this;
        }

        public Criteria andMacKeyNotIn(List values) {
            addCriterion("MAC_KEY not in", values, "macKey");
            return this;
        }

        public Criteria andMacKeyBetween(String value1, String value2) {
            addCriterion("MAC_KEY between", value1, value2, "macKey");
            return this;
        }

        public Criteria andMacKeyNotBetween(String value1, String value2) {
            addCriterion("MAC_KEY not between", value1, value2, "macKey");
            return this;
        }
    }
}