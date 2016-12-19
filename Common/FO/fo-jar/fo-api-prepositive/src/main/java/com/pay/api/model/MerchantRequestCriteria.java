package com.pay.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantRequestCriteria {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected List oredCriteria;

    /**
     */
    public MerchantRequestCriteria() {
        oredCriteria = new ArrayList();
    }

    /**
     */
    protected MerchantRequestCriteria(MerchantRequestCriteria example) {
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

        public Criteria andSequenceIdIsNull() {
            addCriterion("SEQUENCE_ID is null");
            return this;
        }

        public Criteria andSequenceIdIsNotNull() {
            addCriterion("SEQUENCE_ID is not null");
            return this;
        }

        public Criteria andSequenceIdEqualTo(Long value) {
            addCriterion("SEQUENCE_ID =", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdNotEqualTo(Long value) {
            addCriterion("SEQUENCE_ID <>", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdGreaterThan(Long value) {
            addCriterion("SEQUENCE_ID >", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("SEQUENCE_ID >=", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdLessThan(Long value) {
            addCriterion("SEQUENCE_ID <", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdLessThanOrEqualTo(Long value) {
            addCriterion("SEQUENCE_ID <=", value, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdIn(List values) {
            addCriterion("SEQUENCE_ID in", values, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdNotIn(List values) {
            addCriterion("SEQUENCE_ID not in", values, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdBetween(Long value1, Long value2) {
            addCriterion("SEQUENCE_ID between", value1, value2, "sequenceId");
            return this;
        }

        public Criteria andSequenceIdNotBetween(Long value1, Long value2) {
            addCriterion("SEQUENCE_ID not between", value1, value2, "sequenceId");
            return this;
        }

        public Criteria andMerchantIdIsNull() {
            addCriterion("MERCHANT_ID is null");
            return this;
        }

        public Criteria andMerchantIdIsNotNull() {
            addCriterion("MERCHANT_ID is not null");
            return this;
        }

        public Criteria andMerchantIdEqualTo(String value) {
            addCriterion("MERCHANT_ID =", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdNotEqualTo(String value) {
            addCriterion("MERCHANT_ID <>", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdGreaterThan(String value) {
            addCriterion("MERCHANT_ID >", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdGreaterThanOrEqualTo(String value) {
            addCriterion("MERCHANT_ID >=", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdLessThan(String value) {
            addCriterion("MERCHANT_ID <", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdLessThanOrEqualTo(String value) {
            addCriterion("MERCHANT_ID <=", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdLike(String value) {
            addCriterion("MERCHANT_ID like", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdNotLike(String value) {
            addCriterion("MERCHANT_ID not like", value, "merchantId");
            return this;
        }

        public Criteria andMerchantIdIn(List values) {
            addCriterion("MERCHANT_ID in", values, "merchantId");
            return this;
        }

        public Criteria andMerchantIdNotIn(List values) {
            addCriterion("MERCHANT_ID not in", values, "merchantId");
            return this;
        }

        public Criteria andMerchantIdBetween(String value1, String value2) {
            addCriterion("MERCHANT_ID between", value1, value2, "merchantId");
            return this;
        }

        public Criteria andMerchantIdNotBetween(String value1, String value2) {
            addCriterion("MERCHANT_ID not between", value1, value2, "merchantId");
            return this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("ORDER_ID is null");
            return this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("ORDER_ID is not null");
            return this;
        }

        public Criteria andOrderIdEqualTo(String value) {
            addCriterion("ORDER_ID =", value, "orderId");
            return this;
        }

        public Criteria andOrderIdNotEqualTo(String value) {
            addCriterion("ORDER_ID <>", value, "orderId");
            return this;
        }

        public Criteria andOrderIdGreaterThan(String value) {
            addCriterion("ORDER_ID >", value, "orderId");
            return this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(String value) {
            addCriterion("ORDER_ID >=", value, "orderId");
            return this;
        }

        public Criteria andOrderIdLessThan(String value) {
            addCriterion("ORDER_ID <", value, "orderId");
            return this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(String value) {
            addCriterion("ORDER_ID <=", value, "orderId");
            return this;
        }

        public Criteria andOrderIdLike(String value) {
            addCriterion("ORDER_ID like", value, "orderId");
            return this;
        }

        public Criteria andOrderIdNotLike(String value) {
            addCriterion("ORDER_ID not like", value, "orderId");
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

        public Criteria andOrderIdBetween(String value1, String value2) {
            addCriterion("ORDER_ID between", value1, value2, "orderId");
            return this;
        }

        public Criteria andOrderIdNotBetween(String value1, String value2) {
            addCriterion("ORDER_ID not between", value1, value2, "orderId");
            return this;
        }

        public Criteria andContentIsNull() {
            addCriterion("CONTENT is null");
            return this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("CONTENT is not null");
            return this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("CONTENT =", value, "content");
            return this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("CONTENT <>", value, "content");
            return this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("CONTENT >", value, "content");
            return this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("CONTENT >=", value, "content");
            return this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("CONTENT <", value, "content");
            return this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("CONTENT <=", value, "content");
            return this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("CONTENT like", value, "content");
            return this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("CONTENT not like", value, "content");
            return this;
        }

        public Criteria andContentIn(List values) {
            addCriterion("CONTENT in", values, "content");
            return this;
        }

        public Criteria andContentNotIn(List values) {
            addCriterion("CONTENT not in", values, "content");
            return this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("CONTENT between", value1, value2, "content");
            return this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("CONTENT not between", value1, value2, "content");
            return this;
        }

        public Criteria andRequestDateIsNull() {
            addCriterion("REQUEST_DATE is null");
            return this;
        }

        public Criteria andRequestDateIsNotNull() {
            addCriterion("REQUEST_DATE is not null");
            return this;
        }

        public Criteria andRequestDateEqualTo(Date value) {
            addCriterion("REQUEST_DATE =", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateNotEqualTo(Date value) {
            addCriterion("REQUEST_DATE <>", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateGreaterThan(Date value) {
            addCriterion("REQUEST_DATE >", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateGreaterThanOrEqualTo(Date value) {
            addCriterion("REQUEST_DATE >=", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateLessThan(Date value) {
            addCriterion("REQUEST_DATE <", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateLessThanOrEqualTo(Date value) {
            addCriterion("REQUEST_DATE <=", value, "requestDate");
            return this;
        }

        public Criteria andRequestDateIn(List values) {
            addCriterion("REQUEST_DATE in", values, "requestDate");
            return this;
        }

        public Criteria andRequestDateNotIn(List values) {
            addCriterion("REQUEST_DATE not in", values, "requestDate");
            return this;
        }

        public Criteria andRequestDateBetween(Date value1, Date value2) {
            addCriterion("REQUEST_DATE between", value1, value2, "requestDate");
            return this;
        }

        public Criteria andRequestDateNotBetween(Date value1, Date value2) {
            addCriterion("REQUEST_DATE not between", value1, value2, "requestDate");
            return this;
        }

        public Criteria andRequestIpIsNull() {
            addCriterion("REQUEST_IP is null");
            return this;
        }

        public Criteria andRequestIpIsNotNull() {
            addCriterion("REQUEST_IP is not null");
            return this;
        }

        public Criteria andRequestIpEqualTo(String value) {
            addCriterion("REQUEST_IP =", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpNotEqualTo(String value) {
            addCriterion("REQUEST_IP <>", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpGreaterThan(String value) {
            addCriterion("REQUEST_IP >", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpGreaterThanOrEqualTo(String value) {
            addCriterion("REQUEST_IP >=", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpLessThan(String value) {
            addCriterion("REQUEST_IP <", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpLessThanOrEqualTo(String value) {
            addCriterion("REQUEST_IP <=", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpLike(String value) {
            addCriterion("REQUEST_IP like", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpNotLike(String value) {
            addCriterion("REQUEST_IP not like", value, "requestIp");
            return this;
        }

        public Criteria andRequestIpIn(List values) {
            addCriterion("REQUEST_IP in", values, "requestIp");
            return this;
        }

        public Criteria andRequestIpNotIn(List values) {
            addCriterion("REQUEST_IP not in", values, "requestIp");
            return this;
        }

        public Criteria andRequestIpBetween(String value1, String value2) {
            addCriterion("REQUEST_IP between", value1, value2, "requestIp");
            return this;
        }

        public Criteria andRequestIpNotBetween(String value1, String value2) {
            addCriterion("REQUEST_IP not between", value1, value2, "requestIp");
            return this;
        }

        public Criteria andVersionNoIsNull() {
            addCriterion("VERSION_NO is null");
            return this;
        }

        public Criteria andVersionNoIsNotNull() {
            addCriterion("VERSION_NO is not null");
            return this;
        }

        public Criteria andVersionNoEqualTo(String value) {
            addCriterion("VERSION_NO =", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoNotEqualTo(String value) {
            addCriterion("VERSION_NO <>", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoGreaterThan(String value) {
            addCriterion("VERSION_NO >", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoGreaterThanOrEqualTo(String value) {
            addCriterion("VERSION_NO >=", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoLessThan(String value) {
            addCriterion("VERSION_NO <", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoLessThanOrEqualTo(String value) {
            addCriterion("VERSION_NO <=", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoLike(String value) {
            addCriterion("VERSION_NO like", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoNotLike(String value) {
            addCriterion("VERSION_NO not like", value, "versionNo");
            return this;
        }

        public Criteria andVersionNoIn(List values) {
            addCriterion("VERSION_NO in", values, "versionNo");
            return this;
        }

        public Criteria andVersionNoNotIn(List values) {
            addCriterion("VERSION_NO not in", values, "versionNo");
            return this;
        }

        public Criteria andVersionNoBetween(String value1, String value2) {
            addCriterion("VERSION_NO between", value1, value2, "versionNo");
            return this;
        }

        public Criteria andVersionNoNotBetween(String value1, String value2) {
            addCriterion("VERSION_NO not between", value1, value2, "versionNo");
            return this;
        }
    }
}