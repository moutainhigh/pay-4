package com.pay.txncore.crosspay.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefusePaymentOrderCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected Object record;

    protected List<Criteria> oredCriteria;

    private Integer oracleStart;

    private Integer oracleEnd;

    public RefusePaymentOrderCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected RefusePaymentOrderCriteria(RefusePaymentOrderCriteria example) {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoIsNull() {
            addCriterion("TRADE_BASE_NO is null");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoIsNotNull() {
            addCriterion("TRADE_BASE_NO is not null");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoEqualTo(Long value) {
            addCriterion("TRADE_BASE_NO =", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoNotEqualTo(Long value) {
            addCriterion("TRADE_BASE_NO <>", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoGreaterThan(Long value) {
            addCriterion("TRADE_BASE_NO >", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoGreaterThanOrEqualTo(Long value) {
            addCriterion("TRADE_BASE_NO >=", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoLessThan(Long value) {
            addCriterion("TRADE_BASE_NO <", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoLessThanOrEqualTo(Long value) {
            addCriterion("TRADE_BASE_NO <=", value, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoIn(List<Long> values) {
            addCriterion("TRADE_BASE_NO in", values, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoNotIn(List<Long> values) {
            addCriterion("TRADE_BASE_NO not in", values, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoBetween(Long value1, Long value2) {
            addCriterion("TRADE_BASE_NO between", value1, value2, "tradeBaseNo");
            return (Criteria) this;
        }

        public Criteria andTradeBaseNoNotBetween(Long value1, Long value2) {
            addCriterion("TRADE_BASE_NO not between", value1, value2, "tradeBaseNo");
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

        public Criteria andTradeOrderNoEqualTo(Long value) {
            addCriterion("TRADE_ORDER_NO =", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotEqualTo(Long value) {
            addCriterion("TRADE_ORDER_NO <>", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoGreaterThan(Long value) {
            addCriterion("TRADE_ORDER_NO >", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoGreaterThanOrEqualTo(Long value) {
            addCriterion("TRADE_ORDER_NO >=", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoLessThan(Long value) {
            addCriterion("TRADE_ORDER_NO <", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoLessThanOrEqualTo(Long value) {
            addCriterion("TRADE_ORDER_NO <=", value, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoIn(List<Long> values) {
            addCriterion("TRADE_ORDER_NO in", values, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotIn(List<Long> values) {
            addCriterion("TRADE_ORDER_NO not in", values, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoBetween(Long value1, Long value2) {
            addCriterion("TRADE_ORDER_NO between", value1, value2, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andTradeOrderNoNotBetween(Long value1, Long value2) {
            addCriterion("TRADE_ORDER_NO not between", value1, value2, "tradeOrderNo");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdIsNull() {
            addCriterion("REQUEST_SERIAL_ID is null");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdIsNotNull() {
            addCriterion("REQUEST_SERIAL_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdEqualTo(String value) {
            addCriterion("REQUEST_SERIAL_ID =", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdNotEqualTo(String value) {
            addCriterion("REQUEST_SERIAL_ID <>", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdGreaterThan(String value) {
            addCriterion("REQUEST_SERIAL_ID >", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdGreaterThanOrEqualTo(String value) {
            addCriterion("REQUEST_SERIAL_ID >=", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdLessThan(String value) {
            addCriterion("REQUEST_SERIAL_ID <", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdLessThanOrEqualTo(String value) {
            addCriterion("REQUEST_SERIAL_ID <=", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdLike(String value) {
            addCriterion("REQUEST_SERIAL_ID like", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdNotLike(String value) {
            addCriterion("REQUEST_SERIAL_ID not like", value, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdIn(List<String> values) {
            addCriterion("REQUEST_SERIAL_ID in", values, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdNotIn(List<String> values) {
            addCriterion("REQUEST_SERIAL_ID not in", values, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdBetween(String value1, String value2) {
            addCriterion("REQUEST_SERIAL_ID between", value1, value2, "requestSerialId");
            return (Criteria) this;
        }

        public Criteria andRequestSerialIdNotBetween(String value1, String value2) {
            addCriterion("REQUEST_SERIAL_ID not between", value1, value2, "requestSerialId");
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

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<String> values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<String> values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("CURRENCY is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("CURRENCY is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("CURRENCY =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("CURRENCY <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("CURRENCY >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("CURRENCY >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("CURRENCY <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("CURRENCY <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("CURRENCY like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("CURRENCY not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("CURRENCY in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("CURRENCY not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("CURRENCY between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("CURRENCY not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNull() {
            addCriterion("ORDER_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNotNull() {
            addCriterion("ORDER_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountEqualTo(Long value) {
            addCriterion("ORDER_AMOUNT =", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotEqualTo(Long value) {
            addCriterion("ORDER_AMOUNT <>", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThan(Long value) {
            addCriterion("ORDER_AMOUNT >", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("ORDER_AMOUNT >=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThan(Long value) {
            addCriterion("ORDER_AMOUNT <", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThanOrEqualTo(Long value) {
            addCriterion("ORDER_AMOUNT <=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIn(List<Long> values) {
            addCriterion("ORDER_AMOUNT in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotIn(List<Long> values) {
            addCriterion("ORDER_AMOUNT not in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountBetween(Long value1, Long value2) {
            addCriterion("ORDER_AMOUNT between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotBetween(Long value1, Long value2) {
            addCriterion("ORDER_AMOUNT not between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameIsNull() {
            addCriterion("PARTNER_DISPLAY_NAME is null");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameIsNotNull() {
            addCriterion("PARTNER_DISPLAY_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameEqualTo(String value) {
            addCriterion("PARTNER_DISPLAY_NAME =", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameNotEqualTo(String value) {
            addCriterion("PARTNER_DISPLAY_NAME <>", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameGreaterThan(String value) {
            addCriterion("PARTNER_DISPLAY_NAME >", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameGreaterThanOrEqualTo(String value) {
            addCriterion("PARTNER_DISPLAY_NAME >=", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameLessThan(String value) {
            addCriterion("PARTNER_DISPLAY_NAME <", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameLessThanOrEqualTo(String value) {
            addCriterion("PARTNER_DISPLAY_NAME <=", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameLike(String value) {
            addCriterion("PARTNER_DISPLAY_NAME like", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameNotLike(String value) {
            addCriterion("PARTNER_DISPLAY_NAME not like", value, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameIn(List<String> values) {
            addCriterion("PARTNER_DISPLAY_NAME in", values, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameNotIn(List<String> values) {
            addCriterion("PARTNER_DISPLAY_NAME not in", values, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameBetween(String value1, String value2) {
            addCriterion("PARTNER_DISPLAY_NAME between", value1, value2, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andPartnerDisplayNameNotBetween(String value1, String value2) {
            addCriterion("PARTNER_DISPLAY_NAME not between", value1, value2, "partnerDisplayName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNull() {
            addCriterion("GOODS_NAME is null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIsNotNull() {
            addCriterion("GOODS_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsNameEqualTo(String value) {
            addCriterion("GOODS_NAME =", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotEqualTo(String value) {
            addCriterion("GOODS_NAME <>", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThan(String value) {
            addCriterion("GOODS_NAME >", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameGreaterThanOrEqualTo(String value) {
            addCriterion("GOODS_NAME >=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThan(String value) {
            addCriterion("GOODS_NAME <", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLessThanOrEqualTo(String value) {
            addCriterion("GOODS_NAME <=", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameLike(String value) {
            addCriterion("GOODS_NAME like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotLike(String value) {
            addCriterion("GOODS_NAME not like", value, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameIn(List<String> values) {
            addCriterion("GOODS_NAME in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotIn(List<String> values) {
            addCriterion("GOODS_NAME not in", values, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameBetween(String value1, String value2) {
            addCriterion("GOODS_NAME between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsNameNotBetween(String value1, String value2) {
            addCriterion("GOODS_NAME not between", value1, value2, "goodsName");
            return (Criteria) this;
        }

        public Criteria andGoodsCountIsNull() {
            addCriterion("GOODS_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andGoodsCountIsNotNull() {
            addCriterion("GOODS_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsCountEqualTo(Long value) {
            addCriterion("GOODS_COUNT =", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountNotEqualTo(Long value) {
            addCriterion("GOODS_COUNT <>", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountGreaterThan(Long value) {
            addCriterion("GOODS_COUNT >", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountGreaterThanOrEqualTo(Long value) {
            addCriterion("GOODS_COUNT >=", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountLessThan(Long value) {
            addCriterion("GOODS_COUNT <", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountLessThanOrEqualTo(Long value) {
            addCriterion("GOODS_COUNT <=", value, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountIn(List<Long> values) {
            addCriterion("GOODS_COUNT in", values, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountNotIn(List<Long> values) {
            addCriterion("GOODS_COUNT not in", values, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountBetween(Long value1, Long value2) {
            addCriterion("GOODS_COUNT between", value1, value2, "goodsCount");
            return (Criteria) this;
        }

        public Criteria andGoodsCountNotBetween(Long value1, Long value2) {
            addCriterion("GOODS_COUNT not between", value1, value2, "goodsCount");
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

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNull() {
            addCriterion("REFUND_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIsNotNull() {
            addCriterion("REFUND_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRefundAmountEqualTo(Long value) {
            addCriterion("REFUND_AMOUNT =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(Long value) {
            addCriterion("REFUND_AMOUNT <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(Long value) {
            addCriterion("REFUND_AMOUNT >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("REFUND_AMOUNT >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(Long value) {
            addCriterion("REFUND_AMOUNT <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(Long value) {
            addCriterion("REFUND_AMOUNT <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<Long> values) {
            addCriterion("REFUND_AMOUNT in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<Long> values) {
            addCriterion("REFUND_AMOUNT not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(Long value1, Long value2) {
            addCriterion("REFUND_AMOUNT between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(Long value1, Long value2) {
            addCriterion("REFUND_AMOUNT not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIsNull() {
            addCriterion("TRADE_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIsNotNull() {
            addCriterion("TRADE_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTypeEqualTo(String value) {
            addCriterion("TRADE_TYPE =", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotEqualTo(String value) {
            addCriterion("TRADE_TYPE <>", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeGreaterThan(String value) {
            addCriterion("TRADE_TYPE >", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_TYPE >=", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLessThan(String value) {
            addCriterion("TRADE_TYPE <", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLessThanOrEqualTo(String value) {
            addCriterion("TRADE_TYPE <=", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeLike(String value) {
            addCriterion("TRADE_TYPE like", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotLike(String value) {
            addCriterion("TRADE_TYPE not like", value, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeIn(List<String> values) {
            addCriterion("TRADE_TYPE in", values, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotIn(List<String> values) {
            addCriterion("TRADE_TYPE not in", values, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeBetween(String value1, String value2) {
            addCriterion("TRADE_TYPE between", value1, value2, "tradeType");
            return (Criteria) this;
        }

        public Criteria andTradeTypeNotBetween(String value1, String value2) {
            addCriterion("TRADE_TYPE not between", value1, value2, "tradeType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNull() {
            addCriterion("PAY_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("PAY_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(String value) {
            addCriterion("PAY_TYPE =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(String value) {
            addCriterion("PAY_TYPE <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(String value) {
            addCriterion("PAY_TYPE >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(String value) {
            addCriterion("PAY_TYPE <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(String value) {
            addCriterion("PAY_TYPE <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLike(String value) {
            addCriterion("PAY_TYPE like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotLike(String value) {
            addCriterion("PAY_TYPE not like", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<String> values) {
            addCriterion("PAY_TYPE in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<String> values) {
            addCriterion("PAY_TYPE not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(String value1, String value2) {
            addCriterion("PAY_TYPE between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(String value1, String value2) {
            addCriterion("PAY_TYPE not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayerMarkIsNull() {
            addCriterion("PAYER_MARK is null");
            return (Criteria) this;
        }

        public Criteria andPayerMarkIsNotNull() {
            addCriterion("PAYER_MARK is not null");
            return (Criteria) this;
        }

        public Criteria andPayerMarkEqualTo(String value) {
            addCriterion("PAYER_MARK =", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkNotEqualTo(String value) {
            addCriterion("PAYER_MARK <>", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkGreaterThan(String value) {
            addCriterion("PAYER_MARK >", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_MARK >=", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkLessThan(String value) {
            addCriterion("PAYER_MARK <", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkLessThanOrEqualTo(String value) {
            addCriterion("PAYER_MARK <=", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkLike(String value) {
            addCriterion("PAYER_MARK like", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkNotLike(String value) {
            addCriterion("PAYER_MARK not like", value, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkIn(List<String> values) {
            addCriterion("PAYER_MARK in", values, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkNotIn(List<String> values) {
            addCriterion("PAYER_MARK not in", values, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkBetween(String value1, String value2) {
            addCriterion("PAYER_MARK between", value1, value2, "payerMark");
            return (Criteria) this;
        }

        public Criteria andPayerMarkNotBetween(String value1, String value2) {
            addCriterion("PAYER_MARK not between", value1, value2, "payerMark");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNull() {
            addCriterion("ORG_CODE is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("ORG_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("ORG_CODE =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("ORG_CODE <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("ORG_CODE >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ORG_CODE >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("ORG_CODE <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("ORG_CODE <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("ORG_CODE like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("ORG_CODE not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("ORG_CODE in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("ORG_CODE not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("ORG_CODE between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("ORG_CODE not between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andDirectFlgIsNull() {
            addCriterion("DIRECT_FLG is null");
            return (Criteria) this;
        }

        public Criteria andDirectFlgIsNotNull() {
            addCriterion("DIRECT_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andDirectFlgEqualTo(String value) {
            addCriterion("DIRECT_FLG =", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgNotEqualTo(String value) {
            addCriterion("DIRECT_FLG <>", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgGreaterThan(String value) {
            addCriterion("DIRECT_FLG >", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgGreaterThanOrEqualTo(String value) {
            addCriterion("DIRECT_FLG >=", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgLessThan(String value) {
            addCriterion("DIRECT_FLG <", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgLessThanOrEqualTo(String value) {
            addCriterion("DIRECT_FLG <=", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgLike(String value) {
            addCriterion("DIRECT_FLG like", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgNotLike(String value) {
            addCriterion("DIRECT_FLG not like", value, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgIn(List<String> values) {
            addCriterion("DIRECT_FLG in", values, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgNotIn(List<String> values) {
            addCriterion("DIRECT_FLG not in", values, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgBetween(String value1, String value2) {
            addCriterion("DIRECT_FLG between", value1, value2, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDirectFlgNotBetween(String value1, String value2) {
            addCriterion("DIRECT_FLG not between", value1, value2, "directFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgIsNull() {
            addCriterion("DEBIT_FLG is null");
            return (Criteria) this;
        }

        public Criteria andDebitFlgIsNotNull() {
            addCriterion("DEBIT_FLG is not null");
            return (Criteria) this;
        }

        public Criteria andDebitFlgEqualTo(String value) {
            addCriterion("DEBIT_FLG =", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgNotEqualTo(String value) {
            addCriterion("DEBIT_FLG <>", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgGreaterThan(String value) {
            addCriterion("DEBIT_FLG >", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgGreaterThanOrEqualTo(String value) {
            addCriterion("DEBIT_FLG >=", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgLessThan(String value) {
            addCriterion("DEBIT_FLG <", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgLessThanOrEqualTo(String value) {
            addCriterion("DEBIT_FLG <=", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgLike(String value) {
            addCriterion("DEBIT_FLG like", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgNotLike(String value) {
            addCriterion("DEBIT_FLG not like", value, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgIn(List<String> values) {
            addCriterion("DEBIT_FLG in", values, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgNotIn(List<String> values) {
            addCriterion("DEBIT_FLG not in", values, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgBetween(String value1, String value2) {
            addCriterion("DEBIT_FLG between", value1, value2, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andDebitFlgNotBetween(String value1, String value2) {
            addCriterion("DEBIT_FLG not between", value1, value2, "debitFlg");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNull() {
            addCriterion("PLATFORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIsNotNull() {
            addCriterion("PLATFORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformIdEqualTo(String value) {
            addCriterion("PLATFORM_ID =", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotEqualTo(String value) {
            addCriterion("PLATFORM_ID <>", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThan(String value) {
            addCriterion("PLATFORM_ID >", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdGreaterThanOrEqualTo(String value) {
            addCriterion("PLATFORM_ID >=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThan(String value) {
            addCriterion("PLATFORM_ID <", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLessThanOrEqualTo(String value) {
            addCriterion("PLATFORM_ID <=", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdLike(String value) {
            addCriterion("PLATFORM_ID like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotLike(String value) {
            addCriterion("PLATFORM_ID not like", value, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdIn(List<String> values) {
            addCriterion("PLATFORM_ID in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotIn(List<String> values) {
            addCriterion("PLATFORM_ID not in", values, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdBetween(String value1, String value2) {
            addCriterion("PLATFORM_ID between", value1, value2, "platformId");
            return (Criteria) this;
        }

        public Criteria andPlatformIdNotBetween(String value1, String value2) {
            addCriterion("PLATFORM_ID not between", value1, value2, "platformId");
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

        public Criteria andArbFeeIsNull() {
            addCriterion("ARB_FEE is null");
            return (Criteria) this;
        }

        public Criteria andArbFeeIsNotNull() {
            addCriterion("ARB_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andArbFeeEqualTo(Long value) {
            addCriterion("ARB_FEE =", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeNotEqualTo(Long value) {
            addCriterion("ARB_FEE <>", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeGreaterThan(Long value) {
            addCriterion("ARB_FEE >", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeGreaterThanOrEqualTo(Long value) {
            addCriterion("ARB_FEE >=", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeLessThan(Long value) {
            addCriterion("ARB_FEE <", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeLessThanOrEqualTo(Long value) {
            addCriterion("ARB_FEE <=", value, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeIn(List<Long> values) {
            addCriterion("ARB_FEE in", values, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeNotIn(List<Long> values) {
            addCriterion("ARB_FEE not in", values, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeBetween(Long value1, Long value2) {
            addCriterion("ARB_FEE between", value1, value2, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFeeNotBetween(Long value1, Long value2) {
            addCriterion("ARB_FEE not between", value1, value2, "arbFee");
            return (Criteria) this;
        }

        public Criteria andArbFileIsNull() {
            addCriterion("ARB_FILE is null");
            return (Criteria) this;
        }

        public Criteria andArbFileIsNotNull() {
            addCriterion("ARB_FILE is not null");
            return (Criteria) this;
        }

        public Criteria andArbFileEqualTo(String value) {
            addCriterion("ARB_FILE =", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileNotEqualTo(String value) {
            addCriterion("ARB_FILE <>", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileGreaterThan(String value) {
            addCriterion("ARB_FILE >", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileGreaterThanOrEqualTo(String value) {
            addCriterion("ARB_FILE >=", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileLessThan(String value) {
            addCriterion("ARB_FILE <", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileLessThanOrEqualTo(String value) {
            addCriterion("ARB_FILE <=", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileLike(String value) {
            addCriterion("ARB_FILE like", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileNotLike(String value) {
            addCriterion("ARB_FILE not like", value, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileIn(List<String> values) {
            addCriterion("ARB_FILE in", values, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileNotIn(List<String> values) {
            addCriterion("ARB_FILE not in", values, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileBetween(String value1, String value2) {
            addCriterion("ARB_FILE between", value1, value2, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFileNotBetween(String value1, String value2) {
            addCriterion("ARB_FILE not between", value1, value2, "arbFile");
            return (Criteria) this;
        }

        public Criteria andArbFile1IsNull() {
            addCriterion("ARB_FILE1 is null");
            return (Criteria) this;
        }

        public Criteria andArbFile1IsNotNull() {
            addCriterion("ARB_FILE1 is not null");
            return (Criteria) this;
        }

        public Criteria andArbFile1EqualTo(String value) {
            addCriterion("ARB_FILE1 =", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1NotEqualTo(String value) {
            addCriterion("ARB_FILE1 <>", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1GreaterThan(String value) {
            addCriterion("ARB_FILE1 >", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1GreaterThanOrEqualTo(String value) {
            addCriterion("ARB_FILE1 >=", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1LessThan(String value) {
            addCriterion("ARB_FILE1 <", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1LessThanOrEqualTo(String value) {
            addCriterion("ARB_FILE1 <=", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1Like(String value) {
            addCriterion("ARB_FILE1 like", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1NotLike(String value) {
            addCriterion("ARB_FILE1 not like", value, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1In(List<String> values) {
            addCriterion("ARB_FILE1 in", values, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1NotIn(List<String> values) {
            addCriterion("ARB_FILE1 not in", values, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1Between(String value1, String value2) {
            addCriterion("ARB_FILE1 between", value1, value2, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile1NotBetween(String value1, String value2) {
            addCriterion("ARB_FILE1 not between", value1, value2, "arbFile1");
            return (Criteria) this;
        }

        public Criteria andArbFile2IsNull() {
            addCriterion("ARB_FILE2 is null");
            return (Criteria) this;
        }

        public Criteria andArbFile2IsNotNull() {
            addCriterion("ARB_FILE2 is not null");
            return (Criteria) this;
        }

        public Criteria andArbFile2EqualTo(String value) {
            addCriterion("ARB_FILE2 =", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2NotEqualTo(String value) {
            addCriterion("ARB_FILE2 <>", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2GreaterThan(String value) {
            addCriterion("ARB_FILE2 >", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2GreaterThanOrEqualTo(String value) {
            addCriterion("ARB_FILE2 >=", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2LessThan(String value) {
            addCriterion("ARB_FILE2 <", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2LessThanOrEqualTo(String value) {
            addCriterion("ARB_FILE2 <=", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2Like(String value) {
            addCriterion("ARB_FILE2 like", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2NotLike(String value) {
            addCriterion("ARB_FILE2 not like", value, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2In(List<String> values) {
            addCriterion("ARB_FILE2 in", values, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2NotIn(List<String> values) {
            addCriterion("ARB_FILE2 not in", values, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2Between(String value1, String value2) {
            addCriterion("ARB_FILE2 between", value1, value2, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile2NotBetween(String value1, String value2) {
            addCriterion("ARB_FILE2 not between", value1, value2, "arbFile2");
            return (Criteria) this;
        }

        public Criteria andArbFile3IsNull() {
            addCriterion("ARB_FILE3 is null");
            return (Criteria) this;
        }

        public Criteria andArbFile3IsNotNull() {
            addCriterion("ARB_FILE3 is not null");
            return (Criteria) this;
        }

        public Criteria andArbFile3EqualTo(String value) {
            addCriterion("ARB_FILE3 =", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3NotEqualTo(String value) {
            addCriterion("ARB_FILE3 <>", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3GreaterThan(String value) {
            addCriterion("ARB_FILE3 >", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3GreaterThanOrEqualTo(String value) {
            addCriterion("ARB_FILE3 >=", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3LessThan(String value) {
            addCriterion("ARB_FILE3 <", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3LessThanOrEqualTo(String value) {
            addCriterion("ARB_FILE3 <=", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3Like(String value) {
            addCriterion("ARB_FILE3 like", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3NotLike(String value) {
            addCriterion("ARB_FILE3 not like", value, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3In(List<String> values) {
            addCriterion("ARB_FILE3 in", values, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3NotIn(List<String> values) {
            addCriterion("ARB_FILE3 not in", values, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3Between(String value1, String value2) {
            addCriterion("ARB_FILE3 between", value1, value2, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile3NotBetween(String value1, String value2) {
            addCriterion("ARB_FILE3 not between", value1, value2, "arbFile3");
            return (Criteria) this;
        }

        public Criteria andArbFile4IsNull() {
            addCriterion("ARB_FILE4 is null");
            return (Criteria) this;
        }

        public Criteria andArbFile4IsNotNull() {
            addCriterion("ARB_FILE4 is not null");
            return (Criteria) this;
        }

        public Criteria andArbFile4EqualTo(String value) {
            addCriterion("ARB_FILE4 =", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4NotEqualTo(String value) {
            addCriterion("ARB_FILE4 <>", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4GreaterThan(String value) {
            addCriterion("ARB_FILE4 >", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4GreaterThanOrEqualTo(String value) {
            addCriterion("ARB_FILE4 >=", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4LessThan(String value) {
            addCriterion("ARB_FILE4 <", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4LessThanOrEqualTo(String value) {
            addCriterion("ARB_FILE4 <=", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4Like(String value) {
            addCriterion("ARB_FILE4 like", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4NotLike(String value) {
            addCriterion("ARB_FILE4 not like", value, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4In(List<String> values) {
            addCriterion("ARB_FILE4 in", values, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4NotIn(List<String> values) {
            addCriterion("ARB_FILE4 not in", values, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4Between(String value1, String value2) {
            addCriterion("ARB_FILE4 between", value1, value2, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andArbFile4NotBetween(String value1, String value2) {
            addCriterion("ARB_FILE4 not between", value1, value2, "arbFile4");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusIsNull() {
            addCriterion("REFUSE_STATUS is null");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusIsNotNull() {
            addCriterion("REFUSE_STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusEqualTo(String value) {
            addCriterion("REFUSE_STATUS =", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusNotEqualTo(String value) {
            addCriterion("REFUSE_STATUS <>", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusGreaterThan(String value) {
            addCriterion("REFUSE_STATUS >", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusGreaterThanOrEqualTo(String value) {
            addCriterion("REFUSE_STATUS >=", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusLessThan(String value) {
            addCriterion("REFUSE_STATUS <", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusLessThanOrEqualTo(String value) {
            addCriterion("REFUSE_STATUS <=", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusLike(String value) {
            addCriterion("REFUSE_STATUS like", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusNotLike(String value) {
            addCriterion("REFUSE_STATUS not like", value, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusIn(List<String> values) {
            addCriterion("REFUSE_STATUS in", values, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusNotIn(List<String> values) {
            addCriterion("REFUSE_STATUS not in", values, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusBetween(String value1, String value2) {
            addCriterion("REFUSE_STATUS between", value1, value2, "refuseStatus");
            return (Criteria) this;
        }

        public Criteria andRefuseStatusNotBetween(String value1, String value2) {
            addCriterion("REFUSE_STATUS not between", value1, value2, "refuseStatus");
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
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }

        public Criteria andRequestSerialIdLikeInsensitive(String value) {
            addCriterion("upper(REQUEST_SERIAL_ID) like", value.toUpperCase(), "requestSerialId");
            return this;
        }

        public Criteria andPartnerIdLikeInsensitive(String value) {
            addCriterion("upper(PARTNER_ID) like", value.toUpperCase(), "partnerId");
            return this;
        }

        public Criteria andOrderIdLikeInsensitive(String value) {
            addCriterion("upper(ORDER_ID) like", value.toUpperCase(), "orderId");
            return this;
        }

        public Criteria andCurrencyLikeInsensitive(String value) {
            addCriterion("upper(CURRENCY) like", value.toUpperCase(), "currency");
            return this;
        }

        public Criteria andPartnerDisplayNameLikeInsensitive(String value) {
            addCriterion("upper(PARTNER_DISPLAY_NAME) like", value.toUpperCase(), "partnerDisplayName");
            return this;
        }

        public Criteria andGoodsNameLikeInsensitive(String value) {
            addCriterion("upper(GOODS_NAME) like", value.toUpperCase(), "goodsName");
            return this;
        }

        public Criteria andTradeTypeLikeInsensitive(String value) {
            addCriterion("upper(TRADE_TYPE) like", value.toUpperCase(), "tradeType");
            return this;
        }

        public Criteria andPayTypeLikeInsensitive(String value) {
            addCriterion("upper(PAY_TYPE) like", value.toUpperCase(), "payType");
            return this;
        }

        public Criteria andPayerMarkLikeInsensitive(String value) {
            addCriterion("upper(PAYER_MARK) like", value.toUpperCase(), "payerMark");
            return this;
        }

        public Criteria andOrgCodeLikeInsensitive(String value) {
            addCriterion("upper(ORG_CODE) like", value.toUpperCase(), "orgCode");
            return this;
        }

        public Criteria andDirectFlgLikeInsensitive(String value) {
            addCriterion("upper(DIRECT_FLG) like", value.toUpperCase(), "directFlg");
            return this;
        }

        public Criteria andDebitFlgLikeInsensitive(String value) {
            addCriterion("upper(DEBIT_FLG) like", value.toUpperCase(), "debitFlg");
            return this;
        }

        public Criteria andPlatformIdLikeInsensitive(String value) {
            addCriterion("upper(PLATFORM_ID) like", value.toUpperCase(), "platformId");
            return this;
        }

        public Criteria andOperatorLikeInsensitive(String value) {
            addCriterion("upper(OPERATOR) like", value.toUpperCase(), "operator");
            return this;
        }

        public Criteria andArbFileLikeInsensitive(String value) {
            addCriterion("upper(ARB_FILE) like", value.toUpperCase(), "arbFile");
            return this;
        }

        public Criteria andArbFile1LikeInsensitive(String value) {
            addCriterion("upper(ARB_FILE1) like", value.toUpperCase(), "arbFile1");
            return this;
        }

        public Criteria andArbFile2LikeInsensitive(String value) {
            addCriterion("upper(ARB_FILE2) like", value.toUpperCase(), "arbFile2");
            return this;
        }

        public Criteria andArbFile3LikeInsensitive(String value) {
            addCriterion("upper(ARB_FILE3) like", value.toUpperCase(), "arbFile3");
            return this;
        }

        public Criteria andArbFile4LikeInsensitive(String value) {
            addCriterion("upper(ARB_FILE4) like", value.toUpperCase(), "arbFile4");
            return this;
        }

        public Criteria andRefuseStatusLikeInsensitive(String value) {
            addCriterion("upper(REFUSE_STATUS) like", value.toUpperCase(), "refuseStatus");
            return this;
        }

        public Criteria andRemarkLikeInsensitive(String value) {
            addCriterion("upper(REMARK) like", value.toUpperCase(), "remark");
            return this;
        }
    }
}