package com.pay.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MerchantConfigureCriteria {
    /**
     */
    protected String orderByClause;

    /**
     */
    protected List oredCriteria;

    /**
     */
    public MerchantConfigureCriteria() {
        oredCriteria = new ArrayList();
    }

    /**
     */
    protected MerchantConfigureCriteria(MerchantConfigureCriteria example) {
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("ID =", value, "id");
            return this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("ID <>", value, "id");
            return this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("ID >", value, "id");
            return this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ID >=", value, "id");
            return this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("ID <", value, "id");
            return this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("ID <=", value, "id");
            return this;
        }

        public Criteria andIdIn(List values) {
            addCriterion("ID in", values, "id");
            return this;
        }

        public Criteria andIdNotIn(List values) {
            addCriterion("ID not in", values, "id");
            return this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("ID between", value1, value2, "id");
            return this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ID not between", value1, value2, "id");
            return this;
        }

        public Criteria andMerchantCodeIsNull() {
            addCriterion("MERCHANT_CODE is null");
            return this;
        }

        public Criteria andMerchantCodeIsNotNull() {
            addCriterion("MERCHANT_CODE is not null");
            return this;
        }

        public Criteria andMerchantCodeEqualTo(String value) {
            addCriterion("MERCHANT_CODE =", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeNotEqualTo(String value) {
            addCriterion("MERCHANT_CODE <>", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeGreaterThan(String value) {
            addCriterion("MERCHANT_CODE >", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeGreaterThanOrEqualTo(String value) {
            addCriterion("MERCHANT_CODE >=", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeLessThan(String value) {
            addCriterion("MERCHANT_CODE <", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeLessThanOrEqualTo(String value) {
            addCriterion("MERCHANT_CODE <=", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeLike(String value) {
            addCriterion("MERCHANT_CODE like", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeNotLike(String value) {
            addCriterion("MERCHANT_CODE not like", value, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeIn(List values) {
            addCriterion("MERCHANT_CODE in", values, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeNotIn(List values) {
            addCriterion("MERCHANT_CODE not in", values, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeBetween(String value1, String value2) {
            addCriterion("MERCHANT_CODE between", value1, value2, "merchantCode");
            return this;
        }

        public Criteria andMerchantCodeNotBetween(String value1, String value2) {
            addCriterion("MERCHANT_CODE not between", value1, value2, "merchantCode");
            return this;
        }

        public Criteria andAuthorizeAddressIsNull() {
            addCriterion("AUTHORIZE_ADDRESS is null");
            return this;
        }

        public Criteria andAuthorizeAddressIsNotNull() {
            addCriterion("AUTHORIZE_ADDRESS is not null");
            return this;
        }

        public Criteria andAuthorizeAddressEqualTo(String value) {
            addCriterion("AUTHORIZE_ADDRESS =", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressNotEqualTo(String value) {
            addCriterion("AUTHORIZE_ADDRESS <>", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressGreaterThan(String value) {
            addCriterion("AUTHORIZE_ADDRESS >", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressGreaterThanOrEqualTo(String value) {
            addCriterion("AUTHORIZE_ADDRESS >=", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressLessThan(String value) {
            addCriterion("AUTHORIZE_ADDRESS <", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressLessThanOrEqualTo(String value) {
            addCriterion("AUTHORIZE_ADDRESS <=", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressLike(String value) {
            addCriterion("AUTHORIZE_ADDRESS like", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressNotLike(String value) {
            addCriterion("AUTHORIZE_ADDRESS not like", value, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressIn(List values) {
            addCriterion("AUTHORIZE_ADDRESS in", values, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressNotIn(List values) {
            addCriterion("AUTHORIZE_ADDRESS not in", values, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressBetween(String value1, String value2) {
            addCriterion("AUTHORIZE_ADDRESS between", value1, value2, "authorizeAddress");
            return this;
        }

        public Criteria andAuthorizeAddressNotBetween(String value1, String value2) {
            addCriterion("AUTHORIZE_ADDRESS not between", value1, value2, "authorizeAddress");
            return this;
        }

        public Criteria andNotifyUrlIsNull() {
            addCriterion("NOTIFY_URL is null");
            return this;
        }

        public Criteria andNotifyUrlIsNotNull() {
            addCriterion("NOTIFY_URL is not null");
            return this;
        }

        public Criteria andNotifyUrlEqualTo(String value) {
            addCriterion("NOTIFY_URL =", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlNotEqualTo(String value) {
            addCriterion("NOTIFY_URL <>", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlGreaterThan(String value) {
            addCriterion("NOTIFY_URL >", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlGreaterThanOrEqualTo(String value) {
            addCriterion("NOTIFY_URL >=", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlLessThan(String value) {
            addCriterion("NOTIFY_URL <", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlLessThanOrEqualTo(String value) {
            addCriterion("NOTIFY_URL <=", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlLike(String value) {
            addCriterion("NOTIFY_URL like", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlNotLike(String value) {
            addCriterion("NOTIFY_URL not like", value, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlIn(List values) {
            addCriterion("NOTIFY_URL in", values, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlNotIn(List values) {
            addCriterion("NOTIFY_URL not in", values, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlBetween(String value1, String value2) {
            addCriterion("NOTIFY_URL between", value1, value2, "notifyUrl");
            return this;
        }

        public Criteria andNotifyUrlNotBetween(String value1, String value2) {
            addCriterion("NOTIFY_URL not between", value1, value2, "notifyUrl");
            return this;
        }

        public Criteria andPublicKeyIsNull() {
            addCriterion("PUBLIC_KEY is null");
            return this;
        }

        public Criteria andPublicKeyIsNotNull() {
            addCriterion("PUBLIC_KEY is not null");
            return this;
        }

        public Criteria andPublicKeyEqualTo(String value) {
            addCriterion("PUBLIC_KEY =", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyNotEqualTo(String value) {
            addCriterion("PUBLIC_KEY <>", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyGreaterThan(String value) {
            addCriterion("PUBLIC_KEY >", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyGreaterThanOrEqualTo(String value) {
            addCriterion("PUBLIC_KEY >=", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyLessThan(String value) {
            addCriterion("PUBLIC_KEY <", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyLessThanOrEqualTo(String value) {
            addCriterion("PUBLIC_KEY <=", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyLike(String value) {
            addCriterion("PUBLIC_KEY like", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyNotLike(String value) {
            addCriterion("PUBLIC_KEY not like", value, "publicKey");
            return this;
        }

        public Criteria andPublicKeyIn(List values) {
            addCriterion("PUBLIC_KEY in", values, "publicKey");
            return this;
        }

        public Criteria andPublicKeyNotIn(List values) {
            addCriterion("PUBLIC_KEY not in", values, "publicKey");
            return this;
        }

        public Criteria andPublicKeyBetween(String value1, String value2) {
            addCriterion("PUBLIC_KEY between", value1, value2, "publicKey");
            return this;
        }

        public Criteria andPublicKeyNotBetween(String value1, String value2) {
            addCriterion("PUBLIC_KEY not between", value1, value2, "publicKey");
            return this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("CREATOR is null");
            return this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("CREATOR is not null");
            return this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("CREATOR =", value, "creator");
            return this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("CREATOR <>", value, "creator");
            return this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("CREATOR >", value, "creator");
            return this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("CREATOR >=", value, "creator");
            return this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("CREATOR <", value, "creator");
            return this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("CREATOR <=", value, "creator");
            return this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("CREATOR like", value, "creator");
            return this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("CREATOR not like", value, "creator");
            return this;
        }

        public Criteria andCreatorIn(List values) {
            addCriterion("CREATOR in", values, "creator");
            return this;
        }

        public Criteria andCreatorNotIn(List values) {
            addCriterion("CREATOR not in", values, "creator");
            return this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("CREATOR between", value1, value2, "creator");
            return this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("CREATOR not between", value1, value2, "creator");
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

        public Criteria andNotifyFlagIsNull() {
            addCriterion("NOTIFY_FLAG is null");
            return this;
        }

        public Criteria andNotifyFlagIsNotNull() {
            addCriterion("NOTIFY_FLAG is not null");
            return this;
        }

        public Criteria andNotifyFlagEqualTo(Integer value) {
            addCriterion("NOTIFY_FLAG =", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagNotEqualTo(Integer value) {
            addCriterion("NOTIFY_FLAG <>", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagGreaterThan(Integer value) {
            addCriterion("NOTIFY_FLAG >", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("NOTIFY_FLAG >=", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagLessThan(Integer value) {
            addCriterion("NOTIFY_FLAG <", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagLessThanOrEqualTo(Integer value) {
            addCriterion("NOTIFY_FLAG <=", value, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagIn(List values) {
            addCriterion("NOTIFY_FLAG in", values, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagNotIn(List values) {
            addCriterion("NOTIFY_FLAG not in", values, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagBetween(Integer value1, Integer value2) {
            addCriterion("NOTIFY_FLAG between", value1, value2, "notifyFlag");
            return this;
        }

        public Criteria andNotifyFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("NOTIFY_FLAG not between", value1, value2, "notifyFlag");
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

        public Criteria andUpdatorIsNull() {
            addCriterion("UPDATOR is null");
            return this;
        }

        public Criteria andUpdatorIsNotNull() {
            addCriterion("UPDATOR is not null");
            return this;
        }

        public Criteria andUpdatorEqualTo(String value) {
            addCriterion("UPDATOR =", value, "updator");
            return this;
        }

        public Criteria andUpdatorNotEqualTo(String value) {
            addCriterion("UPDATOR <>", value, "updator");
            return this;
        }

        public Criteria andUpdatorGreaterThan(String value) {
            addCriterion("UPDATOR >", value, "updator");
            return this;
        }

        public Criteria andUpdatorGreaterThanOrEqualTo(String value) {
            addCriterion("UPDATOR >=", value, "updator");
            return this;
        }

        public Criteria andUpdatorLessThan(String value) {
            addCriterion("UPDATOR <", value, "updator");
            return this;
        }

        public Criteria andUpdatorLessThanOrEqualTo(String value) {
            addCriterion("UPDATOR <=", value, "updator");
            return this;
        }

        public Criteria andUpdatorLike(String value) {
            addCriterion("UPDATOR like", value, "updator");
            return this;
        }

        public Criteria andUpdatorNotLike(String value) {
            addCriterion("UPDATOR not like", value, "updator");
            return this;
        }

        public Criteria andUpdatorIn(List values) {
            addCriterion("UPDATOR in", values, "updator");
            return this;
        }

        public Criteria andUpdatorNotIn(List values) {
            addCriterion("UPDATOR not in", values, "updator");
            return this;
        }

        public Criteria andUpdatorBetween(String value1, String value2) {
            addCriterion("UPDATOR between", value1, value2, "updator");
            return this;
        }

        public Criteria andUpdatorNotBetween(String value1, String value2) {
            addCriterion("UPDATOR not between", value1, value2, "updator");
            return this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return this;
        }

        public Criteria andStatusIn(List values) {
            addCriterion("STATUS in", values, "status");
            return this;
        }

        public Criteria andStatusNotIn(List values) {
            addCriterion("STATUS not in", values, "status");
            return this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
            return this;
        }
    }
}