package com.pay.txncore.crosspay.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReclearOrderCriteria {
	
	protected String orderByClause;

	protected boolean distinct;

	protected Object record;

	protected List<Criteria> oredCriteria;

	private Integer oracleStart;

	private Integer oracleEnd;

	public ReclearOrderCriteria() {
		oredCriteria = new ArrayList<Criteria>();
	}

	protected ReclearOrderCriteria(ReclearOrderCriteria example) {
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

		public void setCriteriaWithSingleValue(
				List<Map<String, Object>> criteriaWithSingleValue) {
			this.criteriaWithSingleValue = criteriaWithSingleValue;
		}

		public List<Map<String, Object>> getCriteriaWithListValue() {
			return criteriaWithListValue;
		}

		public void setCriteriaWithListValue(
				List<Map<String, Object>> criteriaWithListValue) {
			this.criteriaWithListValue = criteriaWithListValue;
		}

		public List<Map<String, Object>> getCriteriaWithBetweenValue() {
			return criteriaWithBetweenValue;
		}

		public void setCriteriaWithBetweenValue(
				List<Map<String, Object>> criteriaWithBetweenValue) {
			this.criteriaWithBetweenValue = criteriaWithBetweenValue;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteriaWithoutValue.add(condition);
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("value", value);
			criteriaWithSingleValue.add(map);
		}

		protected void addCriterion(String condition,
				List<? extends Object> values, String property) {
			if (values == null || values.size() == 0) {
				throw new RuntimeException("Value list for " + property
						+ " cannot be null or empty");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condition", condition);
			map.put("values", values);
			criteriaWithListValue.add(map);
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
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

		public Criteria andTradeBaseNoIsNull() {
			addCriterion("TRADE_BASE_NO is null");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoIsNotNull() {
			addCriterion("TRADE_BASE_NO is not null");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoEqualTo(BigDecimal value) {
			addCriterion("TRADE_BASE_NO =", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoNotEqualTo(BigDecimal value) {
			addCriterion("TRADE_BASE_NO <>", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoGreaterThan(BigDecimal value) {
			addCriterion("TRADE_BASE_NO >", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("TRADE_BASE_NO >=", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoLessThan(BigDecimal value) {
			addCriterion("TRADE_BASE_NO <", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoLessThanOrEqualTo(BigDecimal value) {
			addCriterion("TRADE_BASE_NO <=", value, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoIn(List<BigDecimal> values) {
			addCriterion("TRADE_BASE_NO in", values, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoNotIn(List<BigDecimal> values) {
			addCriterion("TRADE_BASE_NO not in", values, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TRADE_BASE_NO between", value1, value2, "tradeBaseNo");
			return (Criteria) this;
		}

		public Criteria andTradeBaseNoNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TRADE_BASE_NO not between", value1, value2,
					"tradeBaseNo");
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

		public Criteria andTradeOrderNoEqualTo(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO =", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoNotEqualTo(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO <>", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoGreaterThan(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO >", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoGreaterThanOrEqualTo(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO >=", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoLessThan(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO <", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoLessThanOrEqualTo(BigDecimal value) {
			addCriterion("TRADE_ORDER_NO <=", value, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoIn(List<BigDecimal> values) {
			addCriterion("TRADE_ORDER_NO in", values, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoNotIn(List<BigDecimal> values) {
			addCriterion("TRADE_ORDER_NO not in", values, "tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TRADE_ORDER_NO between", value1, value2,
					"tradeOrderNo");
			return (Criteria) this;
		}

		public Criteria andTradeOrderNoNotBetween(BigDecimal value1,
				BigDecimal value2) {
			addCriterion("TRADE_ORDER_NO not between", value1, value2,
					"tradeOrderNo");
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
			addCriterion("REQUEST_SERIAL_ID between", value1, value2,
					"requestSerialId");
			return (Criteria) this;
		}

		public Criteria andRequestSerialIdNotBetween(String value1,
				String value2) {
			addCriterion("REQUEST_SERIAL_ID not between", value1, value2,
					"requestSerialId");
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

		public Criteria andReasonIsNull() {
			addCriterion("REASON is null");
			return (Criteria) this;
		}

		public Criteria andReasonIsNotNull() {
			addCriterion("REASON is not null");
			return (Criteria) this;
		}

		public Criteria andReasonEqualTo(String value) {
			addCriterion("REASON =", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotEqualTo(String value) {
			addCriterion("REASON <>", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonGreaterThan(String value) {
			addCriterion("REASON >", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonGreaterThanOrEqualTo(String value) {
			addCriterion("REASON >=", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLessThan(String value) {
			addCriterion("REASON <", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLessThanOrEqualTo(String value) {
			addCriterion("REASON <=", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonLike(String value) {
			addCriterion("REASON like", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotLike(String value) {
			addCriterion("REASON not like", value, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonIn(List<String> values) {
			addCriterion("REASON in", values, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotIn(List<String> values) {
			addCriterion("REASON not in", values, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonBetween(String value1, String value2) {
			addCriterion("REASON between", value1, value2, "reason");
			return (Criteria) this;
		}

		public Criteria andReasonNotBetween(String value1, String value2) {
			addCriterion("REASON not between", value1, value2, "reason");
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
			addCriterion("CREATE_DATE not between", value1, value2,
					"createDate");
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
			addCriterion("UPDATE_DATE not between", value1, value2,
					"updateDate");
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}

		public Criteria andRequestSerialIdLikeInsensitive(String value) {
			addCriterion("upper(REQUEST_SERIAL_ID) like", value.toUpperCase(),
					"requestSerialId");
			return this;
		}

		public Criteria andPartnerIdLikeInsensitive(String value) {
			addCriterion("upper(PARTNER_ID) like", value.toUpperCase(),
					"partnerId");
			return this;
		}

		public Criteria andOrderIdLikeInsensitive(String value) {
			addCriterion("upper(ORDER_ID) like", value.toUpperCase(), "orderId");
			return this;
		}

		public Criteria andRemarkLikeInsensitive(String value) {
			addCriterion("upper(REMARK) like", value.toUpperCase(), "remark");
			return this;
		}

		public Criteria andStatusLikeInsensitive(String value) {
			addCriterion("upper(STATUS) like", value.toUpperCase(), "status");
			return this;
		}

		public Criteria andReasonLikeInsensitive(String value) {
			addCriterion("upper(REASON) like", value.toUpperCase(), "reason");
			return this;
		}
	}
}