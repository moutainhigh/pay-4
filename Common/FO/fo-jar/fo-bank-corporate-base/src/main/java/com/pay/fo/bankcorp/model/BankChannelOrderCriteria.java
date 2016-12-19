package com.pay.fo.bankcorp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankChannelOrderCriteria {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected List oredCriteria;

    /**
     */
    public BankChannelOrderCriteria() {
        oredCriteria = new ArrayList();
    }

    /**
     */
    protected BankChannelOrderCriteria(BankChannelOrderCriteria example) {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return this;
        }

        public Criteria andOrderIdEqualTo(Long value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return this;
        }

        public Criteria andOrderIdNotEqualTo(Long value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return this;
        }

        public Criteria andOrderIdGreaterThan(Long value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return this;
        }

        public Criteria andOrderIdLessThan(Long value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return this;
        }

        public Criteria andOrderIdIn(List values) {
            addCriterion("ORDER_ID in", values, "orderId");
            return this;
        }

        public Criteria andOrderIdNotIn(List values) {
            addCriterion("ORDER_ID not in", values, "orderId");
            return this;
        }

        public Criteria andOrderIdBetween(Long value1, Long value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return this;
        }

        public Criteria andOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return this;
        }

        public Criteria andOrderStatusIsNull() {
            addCriterion("ORDER_STATUS is null");
            return this;
        }

        public Criteria andOrderStatusIsNotNull() {
            addCriterion("ORDER_STATUS is not null");
            return this;
        }

        public Criteria andOrderStatusEqualTo(Integer value) {
            addCriterion("ORDER_STATUS =", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusNotEqualTo(Integer value) {
            addCriterion("ORDER_STATUS <>", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusGreaterThan(Integer value) {
            addCriterion("ORDER_STATUS >", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("ORDER_STATUS >=", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusLessThan(Integer value) {
            addCriterion("ORDER_STATUS <", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusLessThanOrEqualTo(Integer value) {
            addCriterion("ORDER_STATUS <=", value, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusIn(List values) {
            addCriterion("ORDER_STATUS in", values, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusNotIn(List values) {
            addCriterion("ORDER_STATUS not in", values, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_STATUS between", value1, value2, "orderStatus");
            return this;
        }

        public Criteria andOrderStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("ORDER_STATUS not between", value1, value2, "orderStatus");
            return this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("CREATE_DATE is null");
            return this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("CREATE_DATE is not null");
            return this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("CREATE_DATE =", value, "createDate");
            return this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("CREATE_DATE <>", value, "createDate");
            return this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("CREATE_DATE >", value, "createDate");
            return this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE >=", value, "createDate");
            return this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("CREATE_DATE <", value, "createDate");
            return this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("CREATE_DATE <=", value, "createDate");
            return this;
        }

        public Criteria andCreateDateIn(List values) {
            addCriterion("CREATE_DATE in", values, "createDate");
            return this;
        }

        public Criteria andCreateDateNotIn(List values) {
            addCriterion("CREATE_DATE not in", values, "createDate");
            return this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE between", value1, value2, "createDate");
            return this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("CREATE_DATE not between", value1, value2, "createDate");
            return this;
        }

        public Criteria andUpdateDateIsNull() {
            addCriterion("UPDATE_DATE is null");
            return this;
        }

        public Criteria andUpdateDateIsNotNull() {
            addCriterion("UPDATE_DATE is not null");
            return this;
        }

        public Criteria andUpdateDateEqualTo(Date value) {
            addCriterion("UPDATE_DATE =", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateNotEqualTo(Date value) {
            addCriterion("UPDATE_DATE <>", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateGreaterThan(Date value) {
            addCriterion("UPDATE_DATE >", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE >=", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateLessThan(Date value) {
            addCriterion("UPDATE_DATE <", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("UPDATE_DATE <=", value, "updateDate");
            return this;
        }

        public Criteria andUpdateDateIn(List values) {
            addCriterion("UPDATE_DATE in", values, "updateDate");
            return this;
        }

        public Criteria andUpdateDateNotIn(List values) {
            addCriterion("UPDATE_DATE not in", values, "updateDate");
            return this;
        }

        public Criteria andUpdateDateBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE between", value1, value2, "updateDate");
            return this;
        }

        public Criteria andUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("UPDATE_DATE not between", value1, value2, "updateDate");
            return this;
        }

        public Criteria andTradeOrderIdIsNull() {
            addCriterion("TRADE_ORDER_ID is null");
            return this;
        }

        public Criteria andTradeOrderIdIsNotNull() {
            addCriterion("TRADE_ORDER_ID is not null");
            return this;
        }

        public Criteria andTradeOrderIdEqualTo(Long value) {
            addCriterion("TRADE_ORDER_ID =", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdNotEqualTo(Long value) {
            addCriterion("TRADE_ORDER_ID <>", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdGreaterThan(Long value) {
            addCriterion("TRADE_ORDER_ID >", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("TRADE_ORDER_ID >=", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdLessThan(Long value) {
            addCriterion("TRADE_ORDER_ID <", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("TRADE_ORDER_ID <=", value, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdIn(List values) {
            addCriterion("TRADE_ORDER_ID in", values, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdNotIn(List values) {
            addCriterion("TRADE_ORDER_ID not in", values, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdBetween(Long value1, Long value2) {
            addCriterion("TRADE_ORDER_ID between", value1, value2, "tradeOrderId");
            return this;
        }

        public Criteria andTradeOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("TRADE_ORDER_ID not between", value1, value2, "tradeOrderId");
            return this;
        }

        public Criteria andTradeDateIsNull() {
            addCriterion("TRADE_DATE is null");
            return this;
        }

        public Criteria andTradeDateIsNotNull() {
            addCriterion("TRADE_DATE is not null");
            return this;
        }

        public Criteria andTradeDateEqualTo(Date value) {
            addCriterion("TRADE_DATE =", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateNotEqualTo(Date value) {
            addCriterion("TRADE_DATE <>", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateGreaterThan(Date value) {
            addCriterion("TRADE_DATE >", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("TRADE_DATE >=", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateLessThan(Date value) {
            addCriterion("TRADE_DATE <", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateLessThanOrEqualTo(Date value) {
            addCriterion("TRADE_DATE <=", value, "tradeDate");
            return this;
        }

        public Criteria andTradeDateIn(List values) {
            addCriterion("TRADE_DATE in", values, "tradeDate");
            return this;
        }

        public Criteria andTradeDateNotIn(List values) {
            addCriterion("TRADE_DATE not in", values, "tradeDate");
            return this;
        }

        public Criteria andTradeDateBetween(Date value1, Date value2) {
            addCriterion("TRADE_DATE between", value1, value2, "tradeDate");
            return this;
        }

        public Criteria andTradeDateNotBetween(Date value1, Date value2) {
            addCriterion("TRADE_DATE not between", value1, value2, "tradeDate");
            return this;
        }

        public Criteria andTradeOrderTypeIsNull() {
            addCriterion("TRADE_ORDER_TYPE is null");
            return this;
        }

        public Criteria andTradeOrderTypeIsNotNull() {
            addCriterion("TRADE_ORDER_TYPE is not null");
            return this;
        }

        public Criteria andTradeOrderTypeEqualTo(Integer value) {
            addCriterion("TRADE_ORDER_TYPE =", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeNotEqualTo(Integer value) {
            addCriterion("TRADE_ORDER_TYPE <>", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeGreaterThan(Integer value) {
            addCriterion("TRADE_ORDER_TYPE >", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TRADE_ORDER_TYPE >=", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeLessThan(Integer value) {
            addCriterion("TRADE_ORDER_TYPE <", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("TRADE_ORDER_TYPE <=", value, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeIn(List values) {
            addCriterion("TRADE_ORDER_TYPE in", values, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeNotIn(List values) {
            addCriterion("TRADE_ORDER_TYPE not in", values, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("TRADE_ORDER_TYPE between", value1, value2, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TRADE_ORDER_TYPE not between", value1, value2, "tradeOrderType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeIsNull() {
            addCriterion("TRADE_ORDER_SMALL_TYPE is null");
            return this;
        }

        public Criteria andTradeOrderSmallTypeIsNotNull() {
            addCriterion("TRADE_ORDER_SMALL_TYPE is not null");
            return this;
        }

        public Criteria andTradeOrderSmallTypeEqualTo(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE =", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeNotEqualTo(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE <>", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeGreaterThan(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE >", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE >=", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeLessThan(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE <", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeLessThanOrEqualTo(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE <=", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeLike(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE like", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeNotLike(String value) {
            addCriterion("TRADE_ORDER_SMALL_TYPE not like", value, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeIn(List values) {
            addCriterion("TRADE_ORDER_SMALL_TYPE in", values, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeNotIn(List values) {
            addCriterion("TRADE_ORDER_SMALL_TYPE not in", values, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeBetween(String value1, String value2) {
            addCriterion("TRADE_ORDER_SMALL_TYPE between", value1, value2, "tradeOrderSmallType");
            return this;
        }

        public Criteria andTradeOrderSmallTypeNotBetween(String value1, String value2) {
            addCriterion("TRADE_ORDER_SMALL_TYPE not between", value1, value2, "tradeOrderSmallType");
            return this;
        }

        public Criteria andPayeeBankAccIsNull() {
            addCriterion("PAYEE_BANK_ACC is null");
            return this;
        }

        public Criteria andPayeeBankAccIsNotNull() {
            addCriterion("PAYEE_BANK_ACC is not null");
            return this;
        }

        public Criteria andPayeeBankAccEqualTo(String value) {
            addCriterion("PAYEE_BANK_ACC =", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccNotEqualTo(String value) {
            addCriterion("PAYEE_BANK_ACC <>", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccGreaterThan(String value) {
            addCriterion("PAYEE_BANK_ACC >", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccGreaterThanOrEqualTo(String value) {
            addCriterion("PAYEE_BANK_ACC >=", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccLessThan(String value) {
            addCriterion("PAYEE_BANK_ACC <", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccLessThanOrEqualTo(String value) {
            addCriterion("PAYEE_BANK_ACC <=", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccLike(String value) {
            addCriterion("PAYEE_BANK_ACC like", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccNotLike(String value) {
            addCriterion("PAYEE_BANK_ACC not like", value, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccIn(List values) {
            addCriterion("PAYEE_BANK_ACC in", values, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccNotIn(List values) {
            addCriterion("PAYEE_BANK_ACC not in", values, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccBetween(String value1, String value2) {
            addCriterion("PAYEE_BANK_ACC between", value1, value2, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeBankAccNotBetween(String value1, String value2) {
            addCriterion("PAYEE_BANK_ACC not between", value1, value2, "payeeBankAcc");
            return this;
        }

        public Criteria andPayeeNameIsNull() {
            addCriterion("PAYEE_NAME is null");
            return this;
        }

        public Criteria andPayeeNameIsNotNull() {
            addCriterion("PAYEE_NAME is not null");
            return this;
        }

        public Criteria andPayeeNameEqualTo(String value) {
            addCriterion("PAYEE_NAME =", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameNotEqualTo(String value) {
            addCriterion("PAYEE_NAME <>", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameGreaterThan(String value) {
            addCriterion("PAYEE_NAME >", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAYEE_NAME >=", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameLessThan(String value) {
            addCriterion("PAYEE_NAME <", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameLessThanOrEqualTo(String value) {
            addCriterion("PAYEE_NAME <=", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameLike(String value) {
            addCriterion("PAYEE_NAME like", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameNotLike(String value) {
            addCriterion("PAYEE_NAME not like", value, "payeeName");
            return this;
        }

        public Criteria andPayeeNameIn(List values) {
            addCriterion("PAYEE_NAME in", values, "payeeName");
            return this;
        }

        public Criteria andPayeeNameNotIn(List values) {
            addCriterion("PAYEE_NAME not in", values, "payeeName");
            return this;
        }

        public Criteria andPayeeNameBetween(String value1, String value2) {
            addCriterion("PAYEE_NAME between", value1, value2, "payeeName");
            return this;
        }

        public Criteria andPayeeNameNotBetween(String value1, String value2) {
            addCriterion("PAYEE_NAME not between", value1, value2, "payeeName");
            return this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("AMOUNT is null");
            return this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("AMOUNT is not null");
            return this;
        }

        public Criteria andAmountEqualTo(Long value) {
            addCriterion("AMOUNT =", value, "amount");
            return this;
        }

        public Criteria andAmountNotEqualTo(Long value) {
            addCriterion("AMOUNT <>", value, "amount");
            return this;
        }

        public Criteria andAmountGreaterThan(Long value) {
            addCriterion("AMOUNT >", value, "amount");
            return this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(Long value) {
            addCriterion("AMOUNT >=", value, "amount");
            return this;
        }

        public Criteria andAmountLessThan(Long value) {
            addCriterion("AMOUNT <", value, "amount");
            return this;
        }

        public Criteria andAmountLessThanOrEqualTo(Long value) {
            addCriterion("AMOUNT <=", value, "amount");
            return this;
        }

        public Criteria andAmountIn(List values) {
            addCriterion("AMOUNT in", values, "amount");
            return this;
        }

        public Criteria andAmountNotIn(List values) {
            addCriterion("AMOUNT not in", values, "amount");
            return this;
        }

        public Criteria andAmountBetween(Long value1, Long value2) {
            addCriterion("AMOUNT between", value1, value2, "amount");
            return this;
        }

        public Criteria andAmountNotBetween(Long value1, Long value2) {
            addCriterion("AMOUNT not between", value1, value2, "amount");
            return this;
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

        public Criteria andFundoutBankCodeIsNull() {
            addCriterion("FUNDOUT_BANK_CODE is null");
            return this;
        }

        public Criteria andFundoutBankCodeIsNotNull() {
            addCriterion("FUNDOUT_BANK_CODE is not null");
            return this;
        }

        public Criteria andFundoutBankCodeEqualTo(Long value) {
            addCriterion("FUNDOUT_BANK_CODE =", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeNotEqualTo(Long value) {
            addCriterion("FUNDOUT_BANK_CODE <>", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeGreaterThan(Long value) {
            addCriterion("FUNDOUT_BANK_CODE >", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeGreaterThanOrEqualTo(Long value) {
            addCriterion("FUNDOUT_BANK_CODE >=", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeLessThan(Long value) {
            addCriterion("FUNDOUT_BANK_CODE <", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeLessThanOrEqualTo(Long value) {
            addCriterion("FUNDOUT_BANK_CODE <=", value, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeIn(List values) {
            addCriterion("FUNDOUT_BANK_CODE in", values, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeNotIn(List values) {
            addCriterion("FUNDOUT_BANK_CODE not in", values, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeBetween(Long value1, Long value2) {
            addCriterion("FUNDOUT_BANK_CODE between", value1, value2, "fundoutBankCode");
            return this;
        }

        public Criteria andFundoutBankCodeNotBetween(Long value1, Long value2) {
            addCriterion("FUNDOUT_BANK_CODE not between", value1, value2, "fundoutBankCode");
            return this;
        }

        public Criteria andCustomSequenceIdIsNull() {
            addCriterion("CUSTOM_SEQUENCE_ID is null");
            return this;
        }

        public Criteria andCustomSequenceIdIsNotNull() {
            addCriterion("CUSTOM_SEQUENCE_ID is not null");
            return this;
        }

        public Criteria andCustomSequenceIdEqualTo(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID =", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdNotEqualTo(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID <>", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdGreaterThan(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID >", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdGreaterThanOrEqualTo(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID >=", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdLessThan(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID <", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdLessThanOrEqualTo(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID <=", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdLike(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID like", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdNotLike(String value) {
            addCriterion("CUSTOM_SEQUENCE_ID not like", value, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdIn(List values) {
            addCriterion("CUSTOM_SEQUENCE_ID in", values, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdNotIn(List values) {
            addCriterion("CUSTOM_SEQUENCE_ID not in", values, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdBetween(String value1, String value2) {
            addCriterion("CUSTOM_SEQUENCE_ID between", value1, value2, "customSequenceId");
            return this;
        }

        public Criteria andCustomSequenceIdNotBetween(String value1, String value2) {
            addCriterion("CUSTOM_SEQUENCE_ID not between", value1, value2, "customSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdIsNull() {
            addCriterion("BANK_SEQUENCE_ID is null");
            return this;
        }

        public Criteria andBankSequenceIdIsNotNull() {
            addCriterion("BANK_SEQUENCE_ID is not null");
            return this;
        }

        public Criteria andBankSequenceIdEqualTo(String value) {
            addCriterion("BANK_SEQUENCE_ID =", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdNotEqualTo(String value) {
            addCriterion("BANK_SEQUENCE_ID <>", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdGreaterThan(String value) {
            addCriterion("BANK_SEQUENCE_ID >", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_SEQUENCE_ID >=", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdLessThan(String value) {
            addCriterion("BANK_SEQUENCE_ID <", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdLessThanOrEqualTo(String value) {
            addCriterion("BANK_SEQUENCE_ID <=", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdLike(String value) {
            addCriterion("BANK_SEQUENCE_ID like", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdNotLike(String value) {
            addCriterion("BANK_SEQUENCE_ID not like", value, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdIn(List values) {
            addCriterion("BANK_SEQUENCE_ID in", values, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdNotIn(List values) {
            addCriterion("BANK_SEQUENCE_ID not in", values, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdBetween(String value1, String value2) {
            addCriterion("BANK_SEQUENCE_ID between", value1, value2, "bankSequenceId");
            return this;
        }

        public Criteria andBankSequenceIdNotBetween(String value1, String value2) {
            addCriterion("BANK_SEQUENCE_ID not between", value1, value2, "bankSequenceId");
            return this;
        }

        public Criteria andFailedReasonIsNull() {
            addCriterion("FAILED_REASON is null");
            return this;
        }

        public Criteria andFailedReasonIsNotNull() {
            addCriterion("FAILED_REASON is not null");
            return this;
        }

        public Criteria andFailedReasonEqualTo(String value) {
            addCriterion("FAILED_REASON =", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonNotEqualTo(String value) {
            addCriterion("FAILED_REASON <>", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonGreaterThan(String value) {
            addCriterion("FAILED_REASON >", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonGreaterThanOrEqualTo(String value) {
            addCriterion("FAILED_REASON >=", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonLessThan(String value) {
            addCriterion("FAILED_REASON <", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonLessThanOrEqualTo(String value) {
            addCriterion("FAILED_REASON <=", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonLike(String value) {
            addCriterion("FAILED_REASON like", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonNotLike(String value) {
            addCriterion("FAILED_REASON not like", value, "failedReason");
            return this;
        }

        public Criteria andFailedReasonIn(List values) {
            addCriterion("FAILED_REASON in", values, "failedReason");
            return this;
        }

        public Criteria andFailedReasonNotIn(List values) {
            addCriterion("FAILED_REASON not in", values, "failedReason");
            return this;
        }

        public Criteria andFailedReasonBetween(String value1, String value2) {
            addCriterion("FAILED_REASON between", value1, value2, "failedReason");
            return this;
        }

        public Criteria andFailedReasonNotBetween(String value1, String value2) {
            addCriterion("FAILED_REASON not between", value1, value2, "failedReason");
            return this;
        }

        public Criteria andPayerBankAccIsNull() {
            addCriterion("PAYER_BANK_ACC is null");
            return this;
        }

        public Criteria andPayerBankAccIsNotNull() {
            addCriterion("PAYER_BANK_ACC is not null");
            return this;
        }

        public Criteria andPayerBankAccEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC =", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccNotEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC <>", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccGreaterThan(String value) {
            addCriterion("PAYER_BANK_ACC >", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC >=", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccLessThan(String value) {
            addCriterion("PAYER_BANK_ACC <", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccLessThanOrEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC <=", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccLike(String value) {
            addCriterion("PAYER_BANK_ACC like", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccNotLike(String value) {
            addCriterion("PAYER_BANK_ACC not like", value, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccIn(List values) {
            addCriterion("PAYER_BANK_ACC in", values, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccNotIn(List values) {
            addCriterion("PAYER_BANK_ACC not in", values, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccBetween(String value1, String value2) {
            addCriterion("PAYER_BANK_ACC between", value1, value2, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccNotBetween(String value1, String value2) {
            addCriterion("PAYER_BANK_ACC not between", value1, value2, "payerBankAcc");
            return this;
        }

        public Criteria andPayerBankAccNameIsNull() {
            addCriterion("PAYER_BANK_ACC_NAME is null");
            return this;
        }

        public Criteria andPayerBankAccNameIsNotNull() {
            addCriterion("PAYER_BANK_ACC_NAME is not null");
            return this;
        }

        public Criteria andPayerBankAccNameEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC_NAME =", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameNotEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC_NAME <>", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameGreaterThan(String value) {
            addCriterion("PAYER_BANK_ACC_NAME >", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameGreaterThanOrEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC_NAME >=", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameLessThan(String value) {
            addCriterion("PAYER_BANK_ACC_NAME <", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameLessThanOrEqualTo(String value) {
            addCriterion("PAYER_BANK_ACC_NAME <=", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameLike(String value) {
            addCriterion("PAYER_BANK_ACC_NAME like", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameNotLike(String value) {
            addCriterion("PAYER_BANK_ACC_NAME not like", value, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameIn(List values) {
            addCriterion("PAYER_BANK_ACC_NAME in", values, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameNotIn(List values) {
            addCriterion("PAYER_BANK_ACC_NAME not in", values, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameBetween(String value1, String value2) {
            addCriterion("PAYER_BANK_ACC_NAME between", value1, value2, "payerBankAccName");
            return this;
        }

        public Criteria andPayerBankAccNameNotBetween(String value1, String value2) {
            addCriterion("PAYER_BANK_ACC_NAME not between", value1, value2, "payerBankAccName");
            return this;
        }
    }
}