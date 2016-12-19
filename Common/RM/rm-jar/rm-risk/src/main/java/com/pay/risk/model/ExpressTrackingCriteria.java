package com.pay.risk.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressTrackingCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected Object record;

    protected List<Criteria> oredCriteria;

    private Integer oracleStart;

    private Integer oracleEnd;

    public ExpressTrackingCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected ExpressTrackingCriteria(ExpressTrackingCriteria example) {
        this.orderByClause = example.orderByClause;
        this.oredCriteria = example.oredCriteria;
        this.distinct = example.distinct;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setRecord(Object record) {
        this.record = record;
    }

    public Object getRecord() {
        return record;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setOracleStart(Integer oracleStart) {
        this.oracleStart = oracleStart;
    }

    public Integer getOracleStart() {
        return oracleStart;
    }

    public void setOracleEnd(Integer oracleEnd) {
        this.oracleEnd = oracleEnd;
    }

    public Integer getOracleEnd() {
        return oracleEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<String> criteriaWithoutValue;

        protected List<Map<String, Object>> criteriaWithSingleValue;

        protected List<Map<String, Object>> criteriaWithListValue;

        protected List<Map<String, Object>> criteriaWithBetweenValue;

        protected GeneratedCriteria() {
            super();
            criteriaWithoutValue = new ArrayList<String>();
            criteriaWithSingleValue = new ArrayList<Map<String, Object>>();
            criteriaWithListValue = new ArrayList<Map<String, Object>>();
            criteriaWithBetweenValue = new ArrayList<Map<String, Object>>();
        }

        public boolean isValid() {
            return criteriaWithoutValue.size() > 0
                || criteriaWithSingleValue.size() > 0
                || criteriaWithListValue.size() > 0
                || criteriaWithBetweenValue.size() > 0;
        }

        public List<String> getCriteriaWithoutValue() {
            return criteriaWithoutValue;
        }

        public void setCriteriaWithoutValue(List<String> criteriaWithoutValue) {
            this.criteriaWithoutValue = criteriaWithoutValue;
        }

        public List<Map<String, Object>> getCriteriaWithSingleValue() {
            return criteriaWithSingleValue;
        }

        public void setCriteriaWithSingleValue(List<Map<String, Object>> criteriaWithSingleValue) {
            this.criteriaWithSingleValue = criteriaWithSingleValue;
        }

        public List<Map<String, Object>> getCriteriaWithListValue() {
            return criteriaWithListValue;
        }

        public void setCriteriaWithListValue(List<Map<String, Object>> criteriaWithListValue) {
            this.criteriaWithListValue = criteriaWithListValue;
        }

        public List<Map<String, Object>> getCriteriaWithBetweenValue() {
            return criteriaWithBetweenValue;
        }

        public void setCriteriaWithBetweenValue(List<Map<String, Object>> criteriaWithBetweenValue) {
            this.criteriaWithBetweenValue = criteriaWithBetweenValue;
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
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("value", value);
            criteriaWithSingleValue.add(map);
        }

        protected void addCriterion(String condition, List<? extends Object> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", values);
            criteriaWithListValue.add(map);
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            List<Object> list = new ArrayList<Object>();
            list.add(value1);
            list.add(value2);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("condition", condition);
            map.put("values", list);
            criteriaWithBetweenValue.add(map);
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIsNull() {
            addCriterion("PARTNER_ID is null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIsNotNull() {
            addCriterion("PARTNER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerIdEqualTo(String value) {
            addCriterion("PARTNER_ID =", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotEqualTo(String value) {
            addCriterion("PARTNER_ID <>", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThan(String value) {
            addCriterion("PARTNER_ID >", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdGreaterThanOrEqualTo(String value) {
            addCriterion("PARTNER_ID >=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThan(String value) {
            addCriterion("PARTNER_ID <", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLessThanOrEqualTo(String value) {
            addCriterion("PARTNER_ID <=", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdLike(String value) {
            addCriterion("PARTNER_ID like", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotLike(String value) {
            addCriterion("PARTNER_ID not like", value, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdIn(List<String> values) {
            addCriterion("PARTNER_ID in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotIn(List<String> values) {
            addCriterion("PARTNER_ID not in", values, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdBetween(String value1, String value2) {
            addCriterion("PARTNER_ID between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andPartnerIdNotBetween(String value1, String value2) {
            addCriterion("PARTNER_ID not between", value1, value2, "partnerId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(BigDecimal value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(BigDecimal value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(BigDecimal value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(BigDecimal value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<BigDecimal> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<BigDecimal> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoIsNull() {
            addCriterion("TRADE_ORDER_NO is null");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoIsNotNull() {
            addCriterion("TRADE_ORDER_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoEqualTo(String value) {
            addCriterion("TRADE_ORDER_NO =", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotEqualTo(String value) {
            addCriterion("TRADE_ORDER_NO <>", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoGreaterThan(String value) {
            addCriterion("TRADE_ORDER_NO >", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_ORDER_NO >=", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoLessThan(String value) {
            addCriterion("TRADE_ORDER_NO <", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoLessThanOrEqualTo(String value) {
            addCriterion("TRADE_ORDER_NO <=", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoLike(String value) {
            addCriterion("TRADE_ORDER_NO like", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotLike(String value) {
            addCriterion("TRADE_ORDER_NO not like", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoIn(List<String> values) {
            addCriterion("TRADE_ORDER_NO in", values, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotIn(List<String> values) {
            addCriterion("TRADE_ORDER_NO not in", values, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoBetween(String value1, String value2) {
            addCriterion("TRADE_ORDER_NO between", value1, value2, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotBetween(String value1, String value2) {
            addCriterion("TRADE_ORDER_NO not between", value1, value2, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeUrlIsNull() {
            addCriterion("TRADE_URL is null");
            return (Criteria) this;
        }

        public Criteria andTradeUrlIsNotNull() {
            addCriterion("TRADE_URL is not null");
            return (Criteria) this;
        }

        public Criteria andTradeUrlEqualTo(Integer value) {
            addCriterion("TRADE_URL =", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlNotEqualTo(Integer value) {
            addCriterion("TRADE_URL <>", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlGreaterThan(Integer value) {
            addCriterion("TRADE_URL >", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlGreaterThanOrEqualTo(Integer value) {
            addCriterion("TRADE_URL >=", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlLessThan(Integer value) {
            addCriterion("TRADE_URL <", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlLessThanOrEqualTo(Integer value) {
            addCriterion("TRADE_URL <=", value, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlIn(List<Integer> values) {
            addCriterion("TRADE_URL in", values, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlNotIn(List<Integer> values) {
            addCriterion("TRADE_URL not in", values, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlBetween(Integer value1, Integer value2) {
            addCriterion("TRADE_URL between", value1, value2, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andTradeUrlNotBetween(Integer value1, Integer value2) {
            addCriterion("TRADE_URL not between", value1, value2, "tradeUrl");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNull() {
            addCriterion("LANGUAGE is null");
            return (Criteria) this;
        }

        public Criteria andLanguageIsNotNull() {
            addCriterion("LANGUAGE is not null");
            return (Criteria) this;
        }

        public Criteria andLanguageEqualTo(String value) {
            addCriterion("LANGUAGE =", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotEqualTo(String value) {
            addCriterion("LANGUAGE <>", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThan(String value) {
            addCriterion("LANGUAGE >", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageGreaterThanOrEqualTo(String value) {
            addCriterion("LANGUAGE >=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThan(String value) {
            addCriterion("LANGUAGE <", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLessThanOrEqualTo(String value) {
            addCriterion("LANGUAGE <=", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageLike(String value) {
            addCriterion("LANGUAGE like", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotLike(String value) {
            addCriterion("LANGUAGE not like", value, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageIn(List<String> values) {
            addCriterion("LANGUAGE in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotIn(List<String> values) {
            addCriterion("LANGUAGE not in", values, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageBetween(String value1, String value2) {
            addCriterion("LANGUAGE between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andLanguageNotBetween(String value1, String value2) {
            addCriterion("LANGUAGE not between", value1, value2, "language");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNull() {
            addCriterion("FIRST_NAME is null");
            return (Criteria) this;
        }

        public Criteria andFirstNameIsNotNull() {
            addCriterion("FIRST_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andFirstNameEqualTo(String value) {
            addCriterion("FIRST_NAME =", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotEqualTo(String value) {
            addCriterion("FIRST_NAME <>", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThan(String value) {
            addCriterion("FIRST_NAME >", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameGreaterThanOrEqualTo(String value) {
            addCriterion("FIRST_NAME >=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThan(String value) {
            addCriterion("FIRST_NAME <", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLessThanOrEqualTo(String value) {
            addCriterion("FIRST_NAME <=", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameLike(String value) {
            addCriterion("FIRST_NAME like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotLike(String value) {
            addCriterion("FIRST_NAME not like", value, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameIn(List<String> values) {
            addCriterion("FIRST_NAME in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotIn(List<String> values) {
            addCriterion("FIRST_NAME not in", values, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameBetween(String value1, String value2) {
            addCriterion("FIRST_NAME between", value1, value2, "firstName");
            return (Criteria) this;
        }

        public Criteria andFirstNameNotBetween(String value1, String value2) {
            addCriterion("FIRST_NAME not between", value1, value2, "firstName");
            return (Criteria) this;
        }

        public Criteria andSurNameIsNull() {
            addCriterion("SUR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andSurNameIsNotNull() {
            addCriterion("SUR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andSurNameEqualTo(String value) {
            addCriterion("SUR_NAME =", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameNotEqualTo(String value) {
            addCriterion("SUR_NAME <>", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameGreaterThan(String value) {
            addCriterion("SUR_NAME >", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameGreaterThanOrEqualTo(String value) {
            addCriterion("SUR_NAME >=", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameLessThan(String value) {
            addCriterion("SUR_NAME <", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameLessThanOrEqualTo(String value) {
            addCriterion("SUR_NAME <=", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameLike(String value) {
            addCriterion("SUR_NAME like", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameNotLike(String value) {
            addCriterion("SUR_NAME not like", value, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameIn(List<String> values) {
            addCriterion("SUR_NAME in", values, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameNotIn(List<String> values) {
            addCriterion("SUR_NAME not in", values, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameBetween(String value1, String value2) {
            addCriterion("SUR_NAME between", value1, value2, "surName");
            return (Criteria) this;
        }

        public Criteria andSurNameNotBetween(String value1, String value2) {
            addCriterion("SUR_NAME not between", value1, value2, "surName");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("EMAIL is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("EMAIL is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("EMAIL =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("EMAIL <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("EMAIL >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("EMAIL >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("EMAIL <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("EMAIL <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("EMAIL like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("EMAIL not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("EMAIL in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("EMAIL not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("EMAIL between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("EMAIL not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNull() {
            addCriterion("PHONE_NUMBER is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNotNull() {
            addCriterion("PHONE_NUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberEqualTo(String value) {
            addCriterion("PHONE_NUMBER =", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotEqualTo(String value) {
            addCriterion("PHONE_NUMBER <>", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThan(String value) {
            addCriterion("PHONE_NUMBER >", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("PHONE_NUMBER >=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThan(String value) {
            addCriterion("PHONE_NUMBER <", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("PHONE_NUMBER <=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLike(String value) {
            addCriterion("PHONE_NUMBER like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotLike(String value) {
            addCriterion("PHONE_NUMBER not like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIn(List<String> values) {
            addCriterion("PHONE_NUMBER in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotIn(List<String> values) {
            addCriterion("PHONE_NUMBER not in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberBetween(String value1, String value2) {
            addCriterion("PHONE_NUMBER between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotBetween(String value1, String value2) {
            addCriterion("PHONE_NUMBER not between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andZipIsNull() {
            addCriterion("ZIP is null");
            return (Criteria) this;
        }

        public Criteria andZipIsNotNull() {
            addCriterion("ZIP is not null");
            return (Criteria) this;
        }

        public Criteria andZipEqualTo(String value) {
            addCriterion("ZIP =", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotEqualTo(String value) {
            addCriterion("ZIP <>", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThan(String value) {
            addCriterion("ZIP >", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipGreaterThanOrEqualTo(String value) {
            addCriterion("ZIP >=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThan(String value) {
            addCriterion("ZIP <", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLessThanOrEqualTo(String value) {
            addCriterion("ZIP <=", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipLike(String value) {
            addCriterion("ZIP like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotLike(String value) {
            addCriterion("ZIP not like", value, "zip");
            return (Criteria) this;
        }

        public Criteria andZipIn(List<String> values) {
            addCriterion("ZIP in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotIn(List<String> values) {
            addCriterion("ZIP not in", values, "zip");
            return (Criteria) this;
        }

        public Criteria andZipBetween(String value1, String value2) {
            addCriterion("ZIP between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andZipNotBetween(String value1, String value2) {
            addCriterion("ZIP not between", value1, value2, "zip");
            return (Criteria) this;
        }

        public Criteria andStreetIsNull() {
            addCriterion("STREET is null");
            return (Criteria) this;
        }

        public Criteria andStreetIsNotNull() {
            addCriterion("STREET is not null");
            return (Criteria) this;
        }

        public Criteria andStreetEqualTo(String value) {
            addCriterion("STREET =", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotEqualTo(String value) {
            addCriterion("STREET <>", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThan(String value) {
            addCriterion("STREET >", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetGreaterThanOrEqualTo(String value) {
            addCriterion("STREET >=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThan(String value) {
            addCriterion("STREET <", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLessThanOrEqualTo(String value) {
            addCriterion("STREET <=", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetLike(String value) {
            addCriterion("STREET like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotLike(String value) {
            addCriterion("STREET not like", value, "street");
            return (Criteria) this;
        }

        public Criteria andStreetIn(List<String> values) {
            addCriterion("STREET in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotIn(List<String> values) {
            addCriterion("STREET not in", values, "street");
            return (Criteria) this;
        }

        public Criteria andStreetBetween(String value1, String value2) {
            addCriterion("STREET between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andStreetNotBetween(String value1, String value2) {
            addCriterion("STREET not between", value1, value2, "street");
            return (Criteria) this;
        }

        public Criteria andCityIsNull() {
            addCriterion("CITY is null");
            return (Criteria) this;
        }

        public Criteria andCityIsNotNull() {
            addCriterion("CITY is not null");
            return (Criteria) this;
        }

        public Criteria andCityEqualTo(String value) {
            addCriterion("CITY =", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotEqualTo(String value) {
            addCriterion("CITY <>", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThan(String value) {
            addCriterion("CITY >", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityGreaterThanOrEqualTo(String value) {
            addCriterion("CITY >=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThan(String value) {
            addCriterion("CITY <", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLessThanOrEqualTo(String value) {
            addCriterion("CITY <=", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityLike(String value) {
            addCriterion("CITY like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotLike(String value) {
            addCriterion("CITY not like", value, "city");
            return (Criteria) this;
        }

        public Criteria andCityIn(List<String> values) {
            addCriterion("CITY in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotIn(List<String> values) {
            addCriterion("CITY not in", values, "city");
            return (Criteria) this;
        }

        public Criteria andCityBetween(String value1, String value2) {
            addCriterion("CITY between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andCityNotBetween(String value1, String value2) {
            addCriterion("CITY not between", value1, value2, "city");
            return (Criteria) this;
        }

        public Criteria andStateIsNull() {
            addCriterion("STATE is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("STATE is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(String value) {
            addCriterion("STATE =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(String value) {
            addCriterion("STATE <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(String value) {
            addCriterion("STATE >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(String value) {
            addCriterion("STATE >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(String value) {
            addCriterion("STATE <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(String value) {
            addCriterion("STATE <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLike(String value) {
            addCriterion("STATE like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotLike(String value) {
            addCriterion("STATE not like", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<String> values) {
            addCriterion("STATE in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<String> values) {
            addCriterion("STATE not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(String value1, String value2) {
            addCriterion("STATE between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(String value1, String value2) {
            addCriterion("STATE not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIsNull() {
            addCriterion("COUNTRY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIsNotNull() {
            addCriterion("COUNTRY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCountryCodeEqualTo(String value) {
            addCriterion("COUNTRY_CODE =", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotEqualTo(String value) {
            addCriterion("COUNTRY_CODE <>", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeGreaterThan(String value) {
            addCriterion("COUNTRY_CODE >", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("COUNTRY_CODE >=", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLessThan(String value) {
            addCriterion("COUNTRY_CODE <", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLessThanOrEqualTo(String value) {
            addCriterion("COUNTRY_CODE <=", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeLike(String value) {
            addCriterion("COUNTRY_CODE like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotLike(String value) {
            addCriterion("COUNTRY_CODE not like", value, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeIn(List<String> values) {
            addCriterion("COUNTRY_CODE in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotIn(List<String> values) {
            addCriterion("COUNTRY_CODE not in", values, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeBetween(String value1, String value2) {
            addCriterion("COUNTRY_CODE between", value1, value2, "countryCode");
            return (Criteria) this;
        }

        public Criteria andCountryCodeNotBetween(String value1, String value2) {
            addCriterion("COUNTRY_CODE not between", value1, value2, "countryCode");
            return (Criteria) this;
        }

        public Criteria andFromAddressIsNull() {
            addCriterion("FROM_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andFromAddressIsNotNull() {
            addCriterion("FROM_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andFromAddressEqualTo(String value) {
            addCriterion("FROM_ADDRESS =", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotEqualTo(String value) {
            addCriterion("FROM_ADDRESS <>", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressGreaterThan(String value) {
            addCriterion("FROM_ADDRESS >", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressGreaterThanOrEqualTo(String value) {
            addCriterion("FROM_ADDRESS >=", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLessThan(String value) {
            addCriterion("FROM_ADDRESS <", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLessThanOrEqualTo(String value) {
            addCriterion("FROM_ADDRESS <=", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressLike(String value) {
            addCriterion("FROM_ADDRESS like", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotLike(String value) {
            addCriterion("FROM_ADDRESS not like", value, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressIn(List<String> values) {
            addCriterion("FROM_ADDRESS in", values, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotIn(List<String> values) {
            addCriterion("FROM_ADDRESS not in", values, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressBetween(String value1, String value2) {
            addCriterion("FROM_ADDRESS between", value1, value2, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andFromAddressNotBetween(String value1, String value2) {
            addCriterion("FROM_ADDRESS not between", value1, value2, "fromAddress");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameIsNull() {
            addCriterion("SHIPPING_FULLNAME is null");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameIsNotNull() {
            addCriterion("SHIPPING_FULLNAME is not null");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameEqualTo(String value) {
            addCriterion("SHIPPING_FULLNAME =", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameNotEqualTo(String value) {
            addCriterion("SHIPPING_FULLNAME <>", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameGreaterThan(String value) {
            addCriterion("SHIPPING_FULLNAME >", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_FULLNAME >=", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameLessThan(String value) {
            addCriterion("SHIPPING_FULLNAME <", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_FULLNAME <=", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameLike(String value) {
            addCriterion("SHIPPING_FULLNAME like", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameNotLike(String value) {
            addCriterion("SHIPPING_FULLNAME not like", value, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameIn(List<String> values) {
            addCriterion("SHIPPING_FULLNAME in", values, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameNotIn(List<String> values) {
            addCriterion("SHIPPING_FULLNAME not in", values, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameBetween(String value1, String value2) {
            addCriterion("SHIPPING_FULLNAME between", value1, value2, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingFullnameNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_FULLNAME not between", value1, value2, "shippingFullname");
            return (Criteria) this;
        }

        public Criteria andShippingMailIsNull() {
            addCriterion("SHIPPING_MAIL is null");
            return (Criteria) this;
        }

        public Criteria andShippingMailIsNotNull() {
            addCriterion("SHIPPING_MAIL is not null");
            return (Criteria) this;
        }

        public Criteria andShippingMailEqualTo(String value) {
            addCriterion("SHIPPING_MAIL =", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailNotEqualTo(String value) {
            addCriterion("SHIPPING_MAIL <>", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailGreaterThan(String value) {
            addCriterion("SHIPPING_MAIL >", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_MAIL >=", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailLessThan(String value) {
            addCriterion("SHIPPING_MAIL <", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_MAIL <=", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailLike(String value) {
            addCriterion("SHIPPING_MAIL like", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailNotLike(String value) {
            addCriterion("SHIPPING_MAIL not like", value, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailIn(List<String> values) {
            addCriterion("SHIPPING_MAIL in", values, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailNotIn(List<String> values) {
            addCriterion("SHIPPING_MAIL not in", values, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailBetween(String value1, String value2) {
            addCriterion("SHIPPING_MAIL between", value1, value2, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingMailNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_MAIL not between", value1, value2, "shippingMail");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneIsNull() {
            addCriterion("SHIPPING_PHONE is null");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneIsNotNull() {
            addCriterion("SHIPPING_PHONE is not null");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneEqualTo(String value) {
            addCriterion("SHIPPING_PHONE =", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneNotEqualTo(String value) {
            addCriterion("SHIPPING_PHONE <>", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneGreaterThan(String value) {
            addCriterion("SHIPPING_PHONE >", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_PHONE >=", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneLessThan(String value) {
            addCriterion("SHIPPING_PHONE <", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_PHONE <=", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneLike(String value) {
            addCriterion("SHIPPING_PHONE like", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneNotLike(String value) {
            addCriterion("SHIPPING_PHONE not like", value, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneIn(List<String> values) {
            addCriterion("SHIPPING_PHONE in", values, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneNotIn(List<String> values) {
            addCriterion("SHIPPING_PHONE not in", values, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneBetween(String value1, String value2) {
            addCriterion("SHIPPING_PHONE between", value1, value2, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingPhoneNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_PHONE not between", value1, value2, "shippingPhone");
            return (Criteria) this;
        }

        public Criteria andShippingZipIsNull() {
            addCriterion("SHIPPING_ZIP is null");
            return (Criteria) this;
        }

        public Criteria andShippingZipIsNotNull() {
            addCriterion("SHIPPING_ZIP is not null");
            return (Criteria) this;
        }

        public Criteria andShippingZipEqualTo(String value) {
            addCriterion("SHIPPING_ZIP =", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipNotEqualTo(String value) {
            addCriterion("SHIPPING_ZIP <>", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipGreaterThan(String value) {
            addCriterion("SHIPPING_ZIP >", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_ZIP >=", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipLessThan(String value) {
            addCriterion("SHIPPING_ZIP <", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_ZIP <=", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipLike(String value) {
            addCriterion("SHIPPING_ZIP like", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipNotLike(String value) {
            addCriterion("SHIPPING_ZIP not like", value, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipIn(List<String> values) {
            addCriterion("SHIPPING_ZIP in", values, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipNotIn(List<String> values) {
            addCriterion("SHIPPING_ZIP not in", values, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipBetween(String value1, String value2) {
            addCriterion("SHIPPING_ZIP between", value1, value2, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingZipNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_ZIP not between", value1, value2, "shippingZip");
            return (Criteria) this;
        }

        public Criteria andShippingStreetIsNull() {
            addCriterion("SHIPPING_STREET is null");
            return (Criteria) this;
        }

        public Criteria andShippingStreetIsNotNull() {
            addCriterion("SHIPPING_STREET is not null");
            return (Criteria) this;
        }

        public Criteria andShippingStreetEqualTo(String value) {
            addCriterion("SHIPPING_STREET =", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetNotEqualTo(String value) {
            addCriterion("SHIPPING_STREET <>", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetGreaterThan(String value) {
            addCriterion("SHIPPING_STREET >", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_STREET >=", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetLessThan(String value) {
            addCriterion("SHIPPING_STREET <", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_STREET <=", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetLike(String value) {
            addCriterion("SHIPPING_STREET like", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetNotLike(String value) {
            addCriterion("SHIPPING_STREET not like", value, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetIn(List<String> values) {
            addCriterion("SHIPPING_STREET in", values, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetNotIn(List<String> values) {
            addCriterion("SHIPPING_STREET not in", values, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetBetween(String value1, String value2) {
            addCriterion("SHIPPING_STREET between", value1, value2, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingStreetNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_STREET not between", value1, value2, "shippingStreet");
            return (Criteria) this;
        }

        public Criteria andShippingCityIsNull() {
            addCriterion("SHIPPING_CITY is null");
            return (Criteria) this;
        }

        public Criteria andShippingCityIsNotNull() {
            addCriterion("SHIPPING_CITY is not null");
            return (Criteria) this;
        }

        public Criteria andShippingCityEqualTo(String value) {
            addCriterion("SHIPPING_CITY =", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityNotEqualTo(String value) {
            addCriterion("SHIPPING_CITY <>", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityGreaterThan(String value) {
            addCriterion("SHIPPING_CITY >", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_CITY >=", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityLessThan(String value) {
            addCriterion("SHIPPING_CITY <", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_CITY <=", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityLike(String value) {
            addCriterion("SHIPPING_CITY like", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityNotLike(String value) {
            addCriterion("SHIPPING_CITY not like", value, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityIn(List<String> values) {
            addCriterion("SHIPPING_CITY in", values, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityNotIn(List<String> values) {
            addCriterion("SHIPPING_CITY not in", values, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityBetween(String value1, String value2) {
            addCriterion("SHIPPING_CITY between", value1, value2, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingCityNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_CITY not between", value1, value2, "shippingCity");
            return (Criteria) this;
        }

        public Criteria andShippingStateIsNull() {
            addCriterion("SHIPPING_STATE is null");
            return (Criteria) this;
        }

        public Criteria andShippingStateIsNotNull() {
            addCriterion("SHIPPING_STATE is not null");
            return (Criteria) this;
        }

        public Criteria andShippingStateEqualTo(String value) {
            addCriterion("SHIPPING_STATE =", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateNotEqualTo(String value) {
            addCriterion("SHIPPING_STATE <>", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateGreaterThan(String value) {
            addCriterion("SHIPPING_STATE >", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_STATE >=", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateLessThan(String value) {
            addCriterion("SHIPPING_STATE <", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_STATE <=", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateLike(String value) {
            addCriterion("SHIPPING_STATE like", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateNotLike(String value) {
            addCriterion("SHIPPING_STATE not like", value, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateIn(List<String> values) {
            addCriterion("SHIPPING_STATE in", values, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateNotIn(List<String> values) {
            addCriterion("SHIPPING_STATE not in", values, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateBetween(String value1, String value2) {
            addCriterion("SHIPPING_STATE between", value1, value2, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingStateNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_STATE not between", value1, value2, "shippingState");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeIsNull() {
            addCriterion("SHIPPING_COUNTRY_CODE is null");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeIsNotNull() {
            addCriterion("SHIPPING_COUNTRY_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeEqualTo(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE =", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeNotEqualTo(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE <>", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeGreaterThan(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE >", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeGreaterThanOrEqualTo(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE >=", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeLessThan(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE <", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeLessThanOrEqualTo(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE <=", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeLike(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE like", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeNotLike(String value) {
            addCriterion("SHIPPING_COUNTRY_CODE not like", value, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeIn(List<String> values) {
            addCriterion("SHIPPING_COUNTRY_CODE in", values, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeNotIn(List<String> values) {
            addCriterion("SHIPPING_COUNTRY_CODE not in", values, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeBetween(String value1, String value2) {
            addCriterion("SHIPPING_COUNTRY_CODE between", value1, value2, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andShippingCountryCodeNotBetween(String value1, String value2) {
            addCriterion("SHIPPING_COUNTRY_CODE not between", value1, value2, "shippingCountryCode");
            return (Criteria) this;
        }

        public Criteria andPayerNameIsNull() {
            addCriterion("PAYER_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPayerNameIsNotNull() {
            addCriterion("PAYER_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPayerNameEqualTo(String value) {
            addCriterion("PAYER_NAME =", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameNotEqualTo(String value) {
            addCriterion("PAYER_NAME <>", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameGreaterThan(String value) {
            addCriterion("PAYER_NAME >", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_NAME >=", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameLessThan(String value) {
            addCriterion("PAYER_NAME <", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameLessThanOrEqualTo(String value) {
            addCriterion("PAYER_NAME <=", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameLike(String value) {
            addCriterion("PAYER_NAME like", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameNotLike(String value) {
            addCriterion("PAYER_NAME not like", value, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameIn(List<String> values) {
            addCriterion("PAYER_NAME in", values, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameNotIn(List<String> values) {
            addCriterion("PAYER_NAME not in", values, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameBetween(String value1, String value2) {
            addCriterion("PAYER_NAME between", value1, value2, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerNameNotBetween(String value1, String value2) {
            addCriterion("PAYER_NAME not between", value1, value2, "payerName");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeIsNull() {
            addCriterion("PAYER_CONTACT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeIsNotNull() {
            addCriterion("PAYER_CONTACT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeEqualTo(String value) {
            addCriterion("PAYER_CONTACT_TYPE =", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeNotEqualTo(String value) {
            addCriterion("PAYER_CONTACT_TYPE <>", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeGreaterThan(String value) {
            addCriterion("PAYER_CONTACT_TYPE >", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_CONTACT_TYPE >=", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeLessThan(String value) {
            addCriterion("PAYER_CONTACT_TYPE <", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeLessThanOrEqualTo(String value) {
            addCriterion("PAYER_CONTACT_TYPE <=", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeLike(String value) {
            addCriterion("PAYER_CONTACT_TYPE like", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeNotLike(String value) {
            addCriterion("PAYER_CONTACT_TYPE not like", value, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeIn(List<String> values) {
            addCriterion("PAYER_CONTACT_TYPE in", values, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeNotIn(List<String> values) {
            addCriterion("PAYER_CONTACT_TYPE not in", values, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeBetween(String value1, String value2) {
            addCriterion("PAYER_CONTACT_TYPE between", value1, value2, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactTypeNotBetween(String value1, String value2) {
            addCriterion("PAYER_CONTACT_TYPE not between", value1, value2, "payerContactType");
            return (Criteria) this;
        }

        public Criteria andPayerContactIsNull() {
            addCriterion("PAYER_CONTACT is null");
            return (Criteria) this;
        }

        public Criteria andPayerContactIsNotNull() {
            addCriterion("PAYER_CONTACT is not null");
            return (Criteria) this;
        }

        public Criteria andPayerContactEqualTo(String value) {
            addCriterion("PAYER_CONTACT =", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactNotEqualTo(String value) {
            addCriterion("PAYER_CONTACT <>", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactGreaterThan(String value) {
            addCriterion("PAYER_CONTACT >", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_CONTACT >=", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactLessThan(String value) {
            addCriterion("PAYER_CONTACT <", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactLessThanOrEqualTo(String value) {
            addCriterion("PAYER_CONTACT <=", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactLike(String value) {
            addCriterion("PAYER_CONTACT like", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactNotLike(String value) {
            addCriterion("PAYER_CONTACT not like", value, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactIn(List<String> values) {
            addCriterion("PAYER_CONTACT in", values, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactNotIn(List<String> values) {
            addCriterion("PAYER_CONTACT not in", values, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactBetween(String value1, String value2) {
            addCriterion("PAYER_CONTACT between", value1, value2, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerContactNotBetween(String value1, String value2) {
            addCriterion("PAYER_CONTACT not between", value1, value2, "payerContact");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardIsNull() {
            addCriterion("PAYER_IDENTITY_CARD is null");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardIsNotNull() {
            addCriterion("PAYER_IDENTITY_CARD is not null");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardEqualTo(String value) {
            addCriterion("PAYER_IDENTITY_CARD =", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardNotEqualTo(String value) {
            addCriterion("PAYER_IDENTITY_CARD <>", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardGreaterThan(String value) {
            addCriterion("PAYER_IDENTITY_CARD >", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_IDENTITY_CARD >=", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardLessThan(String value) {
            addCriterion("PAYER_IDENTITY_CARD <", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardLessThanOrEqualTo(String value) {
            addCriterion("PAYER_IDENTITY_CARD <=", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardLike(String value) {
            addCriterion("PAYER_IDENTITY_CARD like", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardNotLike(String value) {
            addCriterion("PAYER_IDENTITY_CARD not like", value, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardIn(List<String> values) {
            addCriterion("PAYER_IDENTITY_CARD in", values, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardNotIn(List<String> values) {
            addCriterion("PAYER_IDENTITY_CARD not in", values, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardBetween(String value1, String value2) {
            addCriterion("PAYER_IDENTITY_CARD between", value1, value2, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdentityCardNotBetween(String value1, String value2) {
            addCriterion("PAYER_IDENTITY_CARD not between", value1, value2, "payerIdentityCard");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeIsNull() {
            addCriterion("PAYER_ID_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeIsNotNull() {
            addCriterion("PAYER_ID_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeEqualTo(String value) {
            addCriterion("PAYER_ID_TYPE =", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeNotEqualTo(String value) {
            addCriterion("PAYER_ID_TYPE <>", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeGreaterThan(String value) {
            addCriterion("PAYER_ID_TYPE >", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_ID_TYPE >=", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeLessThan(String value) {
            addCriterion("PAYER_ID_TYPE <", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeLessThanOrEqualTo(String value) {
            addCriterion("PAYER_ID_TYPE <=", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeLike(String value) {
            addCriterion("PAYER_ID_TYPE like", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeNotLike(String value) {
            addCriterion("PAYER_ID_TYPE not like", value, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeIn(List<String> values) {
            addCriterion("PAYER_ID_TYPE in", values, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeNotIn(List<String> values) {
            addCriterion("PAYER_ID_TYPE not in", values, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeBetween(String value1, String value2) {
            addCriterion("PAYER_ID_TYPE between", value1, value2, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andPayerIdTypeNotBetween(String value1, String value2) {
            addCriterion("PAYER_ID_TYPE not between", value1, value2, "payerIdType");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNull() {
            addCriterion("CARD_NO is null");
            return (Criteria) this;
        }

        public Criteria andCardNoIsNotNull() {
            addCriterion("CARD_NO is not null");
            return (Criteria) this;
        }

        public Criteria andCardNoEqualTo(String value) {
            addCriterion("CARD_NO =", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotEqualTo(String value) {
            addCriterion("CARD_NO <>", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThan(String value) {
            addCriterion("CARD_NO >", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoGreaterThanOrEqualTo(String value) {
            addCriterion("CARD_NO >=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThan(String value) {
            addCriterion("CARD_NO <", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLessThanOrEqualTo(String value) {
            addCriterion("CARD_NO <=", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoLike(String value) {
            addCriterion("CARD_NO like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotLike(String value) {
            addCriterion("CARD_NO not like", value, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoIn(List<String> values) {
            addCriterion("CARD_NO in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotIn(List<String> values) {
            addCriterion("CARD_NO not in", values, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoBetween(String value1, String value2) {
            addCriterion("CARD_NO between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andCardNoNotBetween(String value1, String value2) {
            addCriterion("CARD_NO not between", value1, value2, "cardNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoIsNull() {
            addCriterion("TRACKING_NO is null");
            return (Criteria) this;
        }

        public Criteria andTrackingNoIsNotNull() {
            addCriterion("TRACKING_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingNoEqualTo(String value) {
            addCriterion("TRACKING_NO =", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoNotEqualTo(String value) {
            addCriterion("TRACKING_NO <>", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoGreaterThan(String value) {
            addCriterion("TRACKING_NO >", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoGreaterThanOrEqualTo(String value) {
            addCriterion("TRACKING_NO >=", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoLessThan(String value) {
            addCriterion("TRACKING_NO <", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoLessThanOrEqualTo(String value) {
            addCriterion("TRACKING_NO <=", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoLike(String value) {
            addCriterion("TRACKING_NO like", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoNotLike(String value) {
            addCriterion("TRACKING_NO not like", value, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoIn(List<String> values) {
            addCriterion("TRACKING_NO in", values, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoNotIn(List<String> values) {
            addCriterion("TRACKING_NO not in", values, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoBetween(String value1, String value2) {
            addCriterion("TRACKING_NO between", value1, value2, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andTrackingNoNotBetween(String value1, String value2) {
            addCriterion("TRACKING_NO not between", value1, value2, "trackingNo");
            return (Criteria) this;
        }

        public Criteria andUploadeDateIsNull() {
            addCriterion("UPLOADE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUploadeDateIsNotNull() {
            addCriterion("UPLOADE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUploadeDateEqualTo(Date value) {
            addCriterion("UPLOADE_DATE =", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateNotEqualTo(Date value) {
            addCriterion("UPLOADE_DATE <>", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateGreaterThan(Date value) {
            addCriterion("UPLOADE_DATE >", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPLOADE_DATE >=", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateLessThan(Date value) {
            addCriterion("UPLOADE_DATE <", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateLessThanOrEqualTo(Date value) {
            addCriterion("UPLOADE_DATE <=", value, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateIn(List<Date> values) {
            addCriterion("UPLOADE_DATE in", values, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateNotIn(List<Date> values) {
            addCriterion("UPLOADE_DATE not in", values, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateBetween(Date value1, Date value2) {
            addCriterion("UPLOADE_DATE between", value1, value2, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andUploadeDateNotBetween(Date value1, Date value2) {
            addCriterion("UPLOADE_DATE not between", value1, value2, "uploadeDate");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgIsNull() {
            addCriterion("COMPLETE_FLG is null");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgIsNotNull() {
            addCriterion("COMPLETE_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgEqualTo(String value) {
            addCriterion("COMPLETE_FLG =", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgNotEqualTo(String value) {
            addCriterion("COMPLETE_FLG <>", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgGreaterThan(String value) {
            addCriterion("COMPLETE_FLG >", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgGreaterThanOrEqualTo(String value) {
            addCriterion("COMPLETE_FLG >=", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgLessThan(String value) {
            addCriterion("COMPLETE_FLG <", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgLessThanOrEqualTo(String value) {
            addCriterion("COMPLETE_FLG <=", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgLike(String value) {
            addCriterion("COMPLETE_FLG like", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgNotLike(String value) {
            addCriterion("COMPLETE_FLG not like", value, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgIn(List<String> values) {
            addCriterion("COMPLETE_FLG in", values, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgNotIn(List<String> values) {
            addCriterion("COMPLETE_FLG not in", values, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgBetween(String value1, String value2) {
            addCriterion("COMPLETE_FLG between", value1, value2, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andCompleteFlgNotBetween(String value1, String value2) {
            addCriterion("COMPLETE_FLG not between", value1, value2, "completeFlg");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("STATUS like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("STATUS not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andExpressComIsNull() {
            addCriterion("EXPRESS_COM is null");
            return (Criteria) this;
        }

        public Criteria andExpressComIsNotNull() {
            addCriterion("EXPRESS_COM is not null");
            return (Criteria) this;
        }

        public Criteria andExpressComEqualTo(String value) {
            addCriterion("EXPRESS_COM =", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComNotEqualTo(String value) {
            addCriterion("EXPRESS_COM <>", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComGreaterThan(String value) {
            addCriterion("EXPRESS_COM >", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComGreaterThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COM >=", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComLessThan(String value) {
            addCriterion("EXPRESS_COM <", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComLessThanOrEqualTo(String value) {
            addCriterion("EXPRESS_COM <=", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComLike(String value) {
            addCriterion("EXPRESS_COM like", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComNotLike(String value) {
            addCriterion("EXPRESS_COM not like", value, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComIn(List<String> values) {
            addCriterion("EXPRESS_COM in", values, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComNotIn(List<String> values) {
            addCriterion("EXPRESS_COM not in", values, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComBetween(String value1, String value2) {
            addCriterion("EXPRESS_COM between", value1, value2, "expressCom");
            return (Criteria) this;
        }

        public Criteria andExpressComNotBetween(String value1, String value2) {
            addCriterion("EXPRESS_COM not between", value1, value2, "expressCom");
            return (Criteria) this;
        }

        public Criteria andQueryUrlIsNull() {
            addCriterion("QUERY_URL is null");
            return (Criteria) this;
        }

        public Criteria andQueryUrlIsNotNull() {
            addCriterion("QUERY_URL is not null");
            return (Criteria) this;
        }

        public Criteria andQueryUrlEqualTo(String value) {
            addCriterion("QUERY_URL =", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlNotEqualTo(String value) {
            addCriterion("QUERY_URL <>", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlGreaterThan(String value) {
            addCriterion("QUERY_URL >", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlGreaterThanOrEqualTo(String value) {
            addCriterion("QUERY_URL >=", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlLessThan(String value) {
            addCriterion("QUERY_URL <", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlLessThanOrEqualTo(String value) {
            addCriterion("QUERY_URL <=", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlLike(String value) {
            addCriterion("QUERY_URL like", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlNotLike(String value) {
            addCriterion("QUERY_URL not like", value, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlIn(List<String> values) {
            addCriterion("QUERY_URL in", values, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlNotIn(List<String> values) {
            addCriterion("QUERY_URL not in", values, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlBetween(String value1, String value2) {
            addCriterion("QUERY_URL between", value1, value2, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andQueryUrlNotBetween(String value1, String value2) {
            addCriterion("QUERY_URL not between", value1, value2, "queryUrl");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateIn(List<Date> values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotIn(List<Date> values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("OPERATOR is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("OPERATOR is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("OPERATOR =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("OPERATOR <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("OPERATOR >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("OPERATOR >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("OPERATOR <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("OPERATOR <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("OPERATOR like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("OPERATOR not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("OPERATOR in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("OPERATOR not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("OPERATOR between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("OPERATOR not between", value1, value2, "operator");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andPartnerIdLikeInsensitive(String value) {
            addCriterion("upper(PARTNER_ID) like", value.toUpperCase(), "partnerId");
            return this;
        }

        public Criteria andTradeOrderNoLikeInsensitive(String value) {
            addCriterion("upper(TRADE_ORDER_NO) like", value.toUpperCase(), "tradeOrderNo");
            return this;
        }

        public Criteria andLanguageLikeInsensitive(String value) {
            addCriterion("upper(LANGUAGE) like", value.toUpperCase(), "language");
            return this;
        }

        public Criteria andFirstNameLikeInsensitive(String value) {
            addCriterion("upper(FIRST_NAME) like", value.toUpperCase(), "firstName");
            return this;
        }

        public Criteria andSurNameLikeInsensitive(String value) {
            addCriterion("upper(SUR_NAME) like", value.toUpperCase(), "surName");
            return this;
        }

        public Criteria andEmailLikeInsensitive(String value) {
            addCriterion("upper(EMAIL) like", value.toUpperCase(), "email");
            return this;
        }

        public Criteria andPhoneNumberLikeInsensitive(String value) {
            addCriterion("upper(PHONE_NUMBER) like", value.toUpperCase(), "phoneNumber");
            return this;
        }

        public Criteria andZipLikeInsensitive(String value) {
            addCriterion("upper(ZIP) like", value.toUpperCase(), "zip");
            return this;
        }

        public Criteria andStreetLikeInsensitive(String value) {
            addCriterion("upper(STREET) like", value.toUpperCase(), "street");
            return this;
        }

        public Criteria andCityLikeInsensitive(String value) {
            addCriterion("upper(CITY) like", value.toUpperCase(), "city");
            return this;
        }

        public Criteria andStateLikeInsensitive(String value) {
            addCriterion("upper(STATE) like", value.toUpperCase(), "state");
            return this;
        }

        public Criteria andCountryCodeLikeInsensitive(String value) {
            addCriterion("upper(COUNTRY_CODE) like", value.toUpperCase(), "countryCode");
            return this;
        }

        public Criteria andFromAddressLikeInsensitive(String value) {
            addCriterion("upper(FROM_ADDRESS) like", value.toUpperCase(), "fromAddress");
            return this;
        }

        public Criteria andShippingFullnameLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_FULLNAME) like", value.toUpperCase(), "shippingFullname");
            return this;
        }

        public Criteria andShippingMailLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_MAIL) like", value.toUpperCase(), "shippingMail");
            return this;
        }

        public Criteria andShippingPhoneLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_PHONE) like", value.toUpperCase(), "shippingPhone");
            return this;
        }

        public Criteria andShippingZipLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_ZIP) like", value.toUpperCase(), "shippingZip");
            return this;
        }

        public Criteria andShippingStreetLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_STREET) like", value.toUpperCase(), "shippingStreet");
            return this;
        }

        public Criteria andShippingCityLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_CITY) like", value.toUpperCase(), "shippingCity");
            return this;
        }

        public Criteria andShippingStateLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_STATE) like", value.toUpperCase(), "shippingState");
            return this;
        }

        public Criteria andShippingCountryCodeLikeInsensitive(String value) {
            addCriterion("upper(SHIPPING_COUNTRY_CODE) like", value.toUpperCase(), "shippingCountryCode");
            return this;
        }

        public Criteria andPayerNameLikeInsensitive(String value) {
            addCriterion("upper(PAYER_NAME) like", value.toUpperCase(), "payerName");
            return this;
        }

        public Criteria andPayerContactTypeLikeInsensitive(String value) {
            addCriterion("upper(PAYER_CONTACT_TYPE) like", value.toUpperCase(), "payerContactType");
            return this;
        }

        public Criteria andPayerContactLikeInsensitive(String value) {
            addCriterion("upper(PAYER_CONTACT) like", value.toUpperCase(), "payerContact");
            return this;
        }

        public Criteria andPayerIdentityCardLikeInsensitive(String value) {
            addCriterion("upper(PAYER_IDENTITY_CARD) like", value.toUpperCase(), "payerIdentityCard");
            return this;
        }

        public Criteria andPayerIdTypeLikeInsensitive(String value) {
            addCriterion("upper(PAYER_ID_TYPE) like", value.toUpperCase(), "payerIdType");
            return this;
        }

        public Criteria andCardNoLikeInsensitive(String value) {
            addCriterion("upper(CARD_NO) like", value.toUpperCase(), "cardNo");
            return this;
        }

        public Criteria andTrackingNoLikeInsensitive(String value) {
            addCriterion("upper(TRACKING_NO) like", value.toUpperCase(), "trackingNo");
            return this;
        }

        public Criteria andCompleteFlgLikeInsensitive(String value) {
            addCriterion("upper(COMPLETE_FLG) like", value.toUpperCase(), "completeFlg");
            return this;
        }

        public Criteria andStatusLikeInsensitive(String value) {
            addCriterion("upper(STATUS) like", value.toUpperCase(), "status");
            return this;
        }

        public Criteria andExpressComLikeInsensitive(String value) {
            addCriterion("upper(EXPRESS_COM) like", value.toUpperCase(), "expressCom");
            return this;
        }

        public Criteria andQueryUrlLikeInsensitive(String value) {
            addCriterion("upper(QUERY_URL) like", value.toUpperCase(), "queryUrl");
            return this;
        }

        public Criteria andRemarkLikeInsensitive(String value) {
            addCriterion("upper(REMARK) like", value.toUpperCase(), "remark");
            return this;
        }

        public Criteria andOperatorLikeInsensitive(String value) {
            addCriterion("upper(OPERATOR) like", value.toUpperCase(), "operator");
            return this;
        }
    }
}