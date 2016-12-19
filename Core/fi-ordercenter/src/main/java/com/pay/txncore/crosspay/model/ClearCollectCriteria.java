package com.pay.txncore.crosspay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClearCollectCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected Object record;

    protected List<Criteria> oredCriteria;

    private Integer oracleStart;

    private Integer oracleEnd;

    public ClearCollectCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    protected ClearCollectCriteria(ClearCollectCriteria example) {
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

        public Criteria andTradeAmountIsNull() {
            addCriterion("TRADE_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andTradeAmountIsNotNull() {
            addCriterion("TRADE_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andTradeAmountEqualTo(BigDecimal value) {
            addCriterion("TRADE_AMOUNT =", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountNotEqualTo(BigDecimal value) {
            addCriterion("TRADE_AMOUNT <>", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountGreaterThan(BigDecimal value) {
            addCriterion("TRADE_AMOUNT >", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRADE_AMOUNT >=", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountLessThan(BigDecimal value) {
            addCriterion("TRADE_AMOUNT <", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRADE_AMOUNT <=", value, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountIn(List<BigDecimal> values) {
            addCriterion("TRADE_AMOUNT in", values, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountNotIn(List<BigDecimal> values) {
            addCriterion("TRADE_AMOUNT not in", values, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRADE_AMOUNT between", value1, value2, "tradeAmount");
            return (Criteria) this;
        }

        public Criteria andTradeAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRADE_AMOUNT not between", value1, value2, "tradeAmount");
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

        public Criteria andRefundAmountEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT =", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <>", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT >=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThan(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_AMOUNT <=", value, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_AMOUNT not in", values, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_AMOUNT not between", value1, value2, "refundAmount");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIsNull() {
            addCriterion("REFUND_FEE is null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIsNotNull() {
            addCriterion("REFUND_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andRefundFeeEqualTo(BigDecimal value) {
            addCriterion("REFUND_FEE =", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotEqualTo(BigDecimal value) {
            addCriterion("REFUND_FEE <>", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThan(BigDecimal value) {
            addCriterion("REFUND_FEE >", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_FEE >=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThan(BigDecimal value) {
            addCriterion("REFUND_FEE <", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUND_FEE <=", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeLike(BigDecimal value) {
            addCriterion("REFUND_FEE like", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotLike(BigDecimal value) {
            addCriterion("REFUND_FEE not like", value, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeIn(List<BigDecimal> values) {
            addCriterion("REFUND_FEE in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotIn(List<BigDecimal> values) {
            addCriterion("REFUND_FEE not in", values, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUND_FEE between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andRefundFeeNotBetween(BigDecimal value1,BigDecimal value2) {
            addCriterion("REFUND_FEE not between", value1, value2, "refundFee");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountIsNull() {
            addCriterion("FROZEN_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountIsNotNull() {
            addCriterion("FROZEN_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountEqualTo(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT =", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountNotEqualTo(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT <>", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountGreaterThan(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT >", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT >=", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountLessThan(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT <", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FROZEN_AMOUNT <=", value, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountIn(List<BigDecimal> values) {
            addCriterion("FROZEN_AMOUNT in", values, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountNotIn(List<BigDecimal> values) {
            addCriterion("FROZEN_AMOUNT not in", values, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_AMOUNT between", value1, value2, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andFrozenAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FROZEN_AMOUNT not between", value1, value2, "frozenAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountIsNull() {
            addCriterion("REFUSE_PAYMENT_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountIsNotNull() {
            addCriterion("REFUSE_PAYMENT_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountEqualTo(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT =", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountNotEqualTo(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT <>", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountGreaterThan(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT >", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT >=", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountLessThan(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT <", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUSE_PAYMENT_AMOUNT <=", value, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountIn(List<BigDecimal> values) {
            addCriterion("REFUSE_PAYMENT_AMOUNT in", values, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountNotIn(List<BigDecimal> values) {
            addCriterion("REFUSE_PAYMENT_AMOUNT not in", values, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUSE_PAYMENT_AMOUNT between", value1, value2, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefusePaymentAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("REFUSE_PAYMENT_AMOUNT not between", value1, value2, "refusePaymentAmount");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeIsNull() {
            addCriterion("REFUSE_FEE is null");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeIsNotNull() {
            addCriterion("REFUSE_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeEqualTo(BigDecimal value) {
            addCriterion("REFUSE_FEE =", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeNotEqualTo(BigDecimal value) {
            addCriterion("REFUSE_FEE <>", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeGreaterThan(BigDecimal value) {
            addCriterion("REFUSE_FEE >", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUSE_FEE >=", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeLessThan(BigDecimal value) {
            addCriterion("REFUSE_FEE <", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("REFUSE_FEE <=", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeLike(BigDecimal value) {
            addCriterion("REFUSE_FEE like", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeNotLike(BigDecimal value) {
            addCriterion("REFUSE_FEE not like", value, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeIn(List<BigDecimal> values) {
            addCriterion("REFUSE_FEE in", values, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeNotIn(List<BigDecimal> values) {
            addCriterion("REFUSE_FEE not in", values, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeBetween(BigDecimal value1,BigDecimal value2) {
            addCriterion("REFUSE_FEE between", value1, value2, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andRefuseFeeNotBetween(BigDecimal value1,BigDecimal value2) {
            addCriterion("REFUSE_FEE not between", value1, value2, "refuseFee");
            return (Criteria) this;
        }

        public Criteria andClearAmountIsNull() {
            addCriterion("CLEAR_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andClearAmountIsNotNull() {
            addCriterion("CLEAR_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andClearAmountEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT =", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountNotEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT <>", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountGreaterThan(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT >", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT >=", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountLessThan(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT <", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CLEAR_AMOUNT <=", value, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountIn(List<BigDecimal> values) {
            addCriterion("CLEAR_AMOUNT in", values, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountNotIn(List<BigDecimal> values) {
            addCriterion("CLEAR_AMOUNT not in", values, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_AMOUNT between", value1, value2, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andClearAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CLEAR_AMOUNT not between", value1, value2, "clearAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountIsNull() {
            addCriterion("FEE_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andFeeAmountIsNotNull() {
            addCriterion("FEE_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andFeeAmountEqualTo(BigDecimal value) {
            addCriterion("FEE_AMOUNT =", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountNotEqualTo(BigDecimal value) {
            addCriterion("FEE_AMOUNT <>", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountGreaterThan(BigDecimal value) {
            addCriterion("FEE_AMOUNT >", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE_AMOUNT >=", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountLessThan(BigDecimal value) {
            addCriterion("FEE_AMOUNT <", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("FEE_AMOUNT <=", value, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountIn(List<BigDecimal> values) {
            addCriterion("FEE_AMOUNT in", values, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountNotIn(List<BigDecimal> values) {
            addCriterion("FEE_AMOUNT not in", values, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE_AMOUNT between", value1, value2, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andFeeAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("FEE_AMOUNT not between", value1, value2, "feeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountIsNull() {
            addCriterion("PLEDGE_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountIsNotNull() {
            addCriterion("PLEDGE_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT =", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountNotEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT <>", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountGreaterThan(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT >", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT >=", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountLessThan(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT <", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("PLEDGE_AMOUNT <=", value, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_AMOUNT in", values, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountNotIn(List<BigDecimal> values) {
            addCriterion("PLEDGE_AMOUNT not in", values, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_AMOUNT between", value1, value2, "pledgeAmount");
            return (Criteria) this;
        }

        public Criteria andPledgeAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("PLEDGE_AMOUNT not between", value1, value2, "pledgeAmount");
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

        public Criteria andUnclearAmountIsNull() {
            addCriterion("UNCLEAR_AMOUNT is null");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountIsNotNull() {
            addCriterion("UNCLEAR_AMOUNT is not null");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountEqualTo(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT =", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountNotEqualTo(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT <>", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountGreaterThan(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT >", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT >=", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountLessThan(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT <", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("UNCLEAR_AMOUNT <=", value, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountIn(List<BigDecimal> values) {
            addCriterion("UNCLEAR_AMOUNT in", values, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountNotIn(List<BigDecimal> values) {
            addCriterion("UNCLEAR_AMOUNT not in", values, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNCLEAR_AMOUNT between", value1, value2, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andUnclearAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("UNCLEAR_AMOUNT not between", value1, value2, "unclearAmount");
            return (Criteria) this;
        }

        public Criteria andExpressFeeIsNull() {
            addCriterion("EXPRESS_FEE is null");
            return (Criteria) this;
        }

        public Criteria andExpressFeeIsNotNull() {
            addCriterion("EXPRESS_FEE is not null");
            return (Criteria) this;
        }

        public Criteria andExpressFeeEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_FEE =", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeNotEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_FEE <>", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeGreaterThan(BigDecimal value) {
            addCriterion("EXPRESS_FEE >", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_FEE >=", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeLessThan(BigDecimal value) {
            addCriterion("EXPRESS_FEE <", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("EXPRESS_FEE <=", value, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeIn(List<BigDecimal> values) {
            addCriterion("EXPRESS_FEE in", values, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeNotIn(List<BigDecimal> values) {
            addCriterion("EXPRESS_FEE not in", values, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPRESS_FEE between", value1, value2, "expressFee");
            return (Criteria) this;
        }

        public Criteria andExpressFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("EXPRESS_FEE not between", value1, value2, "expressFee");
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

        public Criteria andRefundFeeLikeInsensitive(String value) {
            addCriterion("upper(REFUND_FEE) like", value.toUpperCase(), "refundFee");
            return this;
        }

        public Criteria andRefuseFeeLikeInsensitive(String value) {
            addCriterion("upper(REFUSE_FEE) like", value.toUpperCase(), "refuseFee");
            return this;
        }

        public Criteria andRemarkLikeInsensitive(String value) {
            addCriterion("upper(REMARK) like", value.toUpperCase(), "remark");
            return this;
        }
    }
}