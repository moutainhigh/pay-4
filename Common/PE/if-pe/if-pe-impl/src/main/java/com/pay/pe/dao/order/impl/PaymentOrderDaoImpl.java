/**
 * Created on Wed Dec 06 18:09:11 CST 2006.
 */
package com.pay.pe.dao.order.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.SqlParameterValue;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.QueryCondition;
import com.pay.pe.dao.order.PaymentOrderDAO;
import com.pay.pe.model.PaymentOrder;

/**or
 * @Company: 
 * g
 * @version 1.0
 */
public class PaymentOrderDaoImpl extends IbatisDAOSupport implements PaymentOrderDAO {
	private Log logger = LogFactory.getLog(getClass());
	
	JdbcTemplate queryJdbcTemplate;
	
	
    /**
     * Default constructor.
     */
    public PaymentOrderDaoImpl() {
        super();
    }

    /* (non-Javadoc)
     * 
     */
    public Class getModelClass() {
        return PaymentOrder.class;
    }
    
	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> findAllPaymentOrder(String payeeIdentity) {
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setPayeeIdentity(payeeIdentity);
		return super.findBySelective(paymentOrder);
		
//		ToplinkCriteria c = ToplinkCriteriaFactory.equal("payeeIdentity", payeeIdentity);
//		List <PaymentOrder> result = super.findModelsByCriteria(c);
//		return result;
	}

	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> findAllRelatedOrder(final String orderId, final int relatedType) {
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setRelatedSequenceID(orderId);
		paymentOrder.setRelatedType(relatedType);		
		return super.findBySelective(paymentOrder);
		
//		
//		ToplinkCriteria c1 = ToplinkCriteriaFactory.equal("relatedSequenceID", orderId);
//		ToplinkCriteria c2 = ToplinkCriteriaFactory.equal("relatedType", relatedType);
//		List <ToplinkCriteria> cList = new ArrayList <ToplinkCriteria>();
//		cList.add(c1);
//		cList.add(c2);
//		List <PaymentOrder> result = super.findModelsByCriteria(cList);
//		return result;
	}

	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> findAllPaymentOrder(
			final Integer orderStatus,
			final Integer orderType,
			final String orderNo) {
		if (null==orderStatus && null==orderType && null==orderNo) {
			return null;
		}
		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setOrderStatus(orderStatus);
		paymentOrder.setOrderType(orderType);
		paymentOrder.setOrderId(orderNo);
		return super.findBySelective(paymentOrder);
		
		
//		List <ToplinkCriteria> cList = new ArrayList <ToplinkCriteria>();
//		if (null != orderStatus) {
//			ToplinkCriteria c1 = ToplinkCriteriaFactory.equal("orderStatus", orderStatus);
//			cList.add(c1);
//		}
//		if (null != orderType) {
//			ToplinkCriteria c2 = ToplinkCriteriaFactory.equal("orderType", orderType);
//			cList.add(c2);
//		}
//		if (null != orderNo) {
//			ToplinkCriteria c3 = ToplinkCriteriaFactory.equal("orderId", orderNo);
//			cList.add(c3);
//		}
//		return super.findModelsByCriteria(cList);
	}

	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> findAllPaymentOrder(
			final Integer orderStatus,
			final Integer orderType) {
		return findAllPaymentOrder(orderStatus, orderType, null);
	}

	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> findAllPaymentOrder(final PaymentOrder orderCriteria) {
		if (null == orderCriteria) {
			return null;
		}
		return super.findBySelective(orderCriteria);
//		List <ToplinkCriteria> cList = new ArrayList <ToplinkCriteria>();
//		List <BeanInfo> beanInfo = ClassUtil.getPropertyDesc(PaymentOrder.class);
//		Iterator <BeanInfo> it = beanInfo.iterator();
//		while (it.hasNext()) {
//			BeanInfo bean = it.next();
//			if (null != bean) {
//				String property = bean.getProperty();
//				if ("primaryKey".equals(property) || "primaryKeyFields".equals(property)) {
//					continue;
//				}
//				try {
//					Object value = bean.getReadMethod().invoke(orderCriteria, new Object[]{});
//					if (null != value) {
//						ToplinkCriteria c = ToplinkCriteriaFactory.equal(property, value);
//						cList.add(c);
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage(), e);
//				} 
//			}
//		}
//		return super.findModelsByCriteria(cList);
	}

	/* (non-Javadoc)
	 * 
	 */
	public List<PaymentOrder> getAllPaymentOrders(final List<String> orderIds) {
//		if (null==orderIds || orderIds.size()==0) {
//			return null;
//		}
//		Long[] ids = new Long[orderIds.size()];
//		Iterator <String> it = orderIds.iterator();
//		int index = 0;
//		while (it.hasNext()) {
//			String orderId = it.next();
//			ids[index] = Long.valueOf(orderId);
//			index++;
//		}
//		Criteria c = ToplinkCriteriaFactory.in("sequenceId", ids);
//		return super.findModelsByCriteria(c);
		return null ;
	}

	/* (non-Javadoc)
	 * 
	 * java.lang.String, 
	 * java.lang.String, 
	 * java.lang.Integer, 
	 * java.util.Date, 
	 * java.util.Date)
	 */
	public long caculateTotalPayAmount(
			final String payerMemberCode, 
			final String payeeMemberCode, 
			final Integer orderCode, 
			final Date begin, 
			final Date end) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("Select Sum(orderAmount) sum From paymentorder po Where ");
		sb.append("po.orderstatus = 111 And ");
		if (null != orderCode) {
			sb.append("po.orderCode = ? And ");
		}
		if (null != begin) {
			sb.append("po.orderTime > ? And ");
		}
		if (null != end) {
			sb.append("po.orderTime < ? And ");
		}
		sb.append("po.payeeacctcode in (select memberacctcode from acct_attrib where membercode = ?) And ");
		sb.append("po.payeracctcode in (select memberacctcode from acct_attrib where membercode = ?) ");
		
		final List<Long> sums = new ArrayList<Long> ();
		queryJdbcTemplate.query(sb.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 1;
				if (orderCode != null) {
					ps.setInt(i++, orderCode.intValue());
				}
				if (null != begin) {
					ps.setTimestamp(i++, new Timestamp(begin.getTime()));
				}
				if (null != end) {
					ps.setTimestamp(i++, new Timestamp(end.getTime()));
				}
				ps.setString(i++, payeeMemberCode);
				ps.setString(i, payerMemberCode);
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Long sum = rs.getLong("sum");
				if (sum != null) {
					sums.add(sum);
				}
			}
		});
		
		if (sums != null && !sums.isEmpty()) {
			Long ret = sums.get(0);
			if (ret != null) {
				return ret.longValue();
			}
		}
		
		return 0;
	}

	public PaymentOrder findByReferenceNum(int orderCode, String referenceNum) {
		QueryCondition sql=new QueryCondition();
		sql.append("select sequenceid from "
			+ " ( "
			+ " select sequenceid from paymentorder ") 
			.append(" where ordercode=? and referencenum=?" ,orderCode,referenceNum )
			.append(" order by sequenceid desc "
			+ " ) "
			+ " where rownum=1");
		

		
		List seqids = queryJdbcTemplate.queryForList(sql.getSql(),sql.getParams());
		
		if(seqids == null || seqids.isEmpty()){
			return null;
		}
		
		Map<String, Object> mapSeqId = (Map) seqids.get(0);
		Long seqId = Long.valueOf(mapSeqId.get("SEQUENCEID").toString());
		
		return (PaymentOrder)this.findById(seqId);
	}

	public void setQueryJdbcTemplate(JdbcTemplate queryJdbcTemplate) {
		this.queryJdbcTemplate = queryJdbcTemplate;
	}

	/* (non-Javadoc)
	 * 
	 */
	public void updateOrderDigest(final String orderId, final String digest) {
		//不要用find,update方法,这样会更复杂,还要考虑并发情况.
		String sql = "update paymentorder set orderDigest = ? where sequenceid = ? ";
		queryJdbcTemplate.update(sql, new PreparedStatementSetter(){
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, digest);
				ps.setString(2, orderId);
			}});
	}
	
	public  PaymentOrder getOerderAndLock(Object seqId, Map<String, SqlParameterValue> status){		
		List<SqlParameterValue> args = new ArrayList<SqlParameterValue>();
		args.add(new SqlParameterValue(java.sql.Types.NUMERIC,seqId));
		StringBuffer sql = new StringBuffer();
		sql.append("select SEQUENCEID from PAYMENTORDER where SEQUENCEID = ?");
		if(status != null){
			for(Entry<String, SqlParameterValue> entry : status.entrySet()){
				sql.append(" and ");
				sql.append(entry.getKey());
				sql.append(" = ? ");
				args.add(entry.getValue());
			}
		}
		sql.append(" for update nowait");
		List l = queryJdbcTemplate.queryForList(sql.toString(), args.toArray());
		if(l.size()<1){
			return null;
		}
		return (PaymentOrder)this.findObjectById(seqId);
	}

	@Override
	public PaymentOrder findById(Long orderSeqId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public PaymentOrder findByOrderIdAndOrderCode(Integer orderCode, String orderid) {
	    // TODO Auto-generated method stub
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("orderCode", orderCode);
		paramMap.put("orderId", orderid);
		return (PaymentOrder)this.getSqlMapClientTemplate().queryForObject(namespace.concat(""),paramMap);
//	    return (PaymentOrder)this.queryJdbcTemplate.queryForObject("", paramMap);
    }
}
