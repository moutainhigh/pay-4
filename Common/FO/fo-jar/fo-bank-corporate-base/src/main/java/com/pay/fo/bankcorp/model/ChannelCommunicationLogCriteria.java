package com.pay.fo.bankcorp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChannelCommunicationLogCriteria {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected List oredCriteria;

    /**
     */
    public ChannelCommunicationLogCriteria() {
        oredCriteria = new ArrayList();
    }

    /**
     */
    protected ChannelCommunicationLogCriteria(ChannelCommunicationLogCriteria example) {
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

        public Criteria andChannelOrderIdIsNull() {
            addCriterion("CHANNEL_ORDER_ID is null");
            return this;
        }

        public Criteria andChannelOrderIdIsNotNull() {
            addCriterion("CHANNEL_ORDER_ID is not null");
            return this;
        }

        public Criteria andChannelOrderIdEqualTo(Long value) {
            addCriterion("CHANNEL_ORDER_ID =", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdNotEqualTo(Long value) {
            addCriterion("CHANNEL_ORDER_ID <>", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdGreaterThan(Long value) {
            addCriterion("CHANNEL_ORDER_ID >", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CHANNEL_ORDER_ID >=", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdLessThan(Long value) {
            addCriterion("CHANNEL_ORDER_ID <", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdLessThanOrEqualTo(Long value) {
            addCriterion("CHANNEL_ORDER_ID <=", value, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdIn(List values) {
            addCriterion("CHANNEL_ORDER_ID in", values, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdNotIn(List values) {
            addCriterion("CHANNEL_ORDER_ID not in", values, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdBetween(Long value1, Long value2) {
            addCriterion("CHANNEL_ORDER_ID between", value1, value2, "channelOrderId");
            return this;
        }

        public Criteria andChannelOrderIdNotBetween(Long value1, Long value2) {
            addCriterion("CHANNEL_ORDER_ID not between", value1, value2, "channelOrderId");
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

        public Criteria andBankTransCodeIsNull() {
            addCriterion("BANK_TRANS_CODE is null");
            return this;
        }

        public Criteria andBankTransCodeIsNotNull() {
            addCriterion("BANK_TRANS_CODE is not null");
            return this;
        }

        public Criteria andBankTransCodeEqualTo(String value) {
            addCriterion("BANK_TRANS_CODE =", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeNotEqualTo(String value) {
            addCriterion("BANK_TRANS_CODE <>", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeGreaterThan(String value) {
            addCriterion("BANK_TRANS_CODE >", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeGreaterThanOrEqualTo(String value) {
            addCriterion("BANK_TRANS_CODE >=", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeLessThan(String value) {
            addCriterion("BANK_TRANS_CODE <", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeLessThanOrEqualTo(String value) {
            addCriterion("BANK_TRANS_CODE <=", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeLike(String value) {
            addCriterion("BANK_TRANS_CODE like", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeNotLike(String value) {
            addCriterion("BANK_TRANS_CODE not like", value, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeIn(List values) {
            addCriterion("BANK_TRANS_CODE in", values, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeNotIn(List values) {
            addCriterion("BANK_TRANS_CODE not in", values, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeBetween(String value1, String value2) {
            addCriterion("BANK_TRANS_CODE between", value1, value2, "bankTransCode");
            return this;
        }

        public Criteria andBankTransCodeNotBetween(String value1, String value2) {
            addCriterion("BANK_TRANS_CODE not between", value1, value2, "bankTransCode");
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

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("TYPE =", value, "type");
            return this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("TYPE <>", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("TYPE >", value, "type");
            return this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("TYPE >=", value, "type");
            return this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("TYPE <", value, "type");
            return this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("TYPE <=", value, "type");
            return this;
        }

        public Criteria andTypeIn(List values) {
            addCriterion("TYPE in", values, "type");
            return this;
        }

        public Criteria andTypeNotIn(List values) {
            addCriterion("TYPE not in", values, "type");
            return this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return this;
        }
    }
}