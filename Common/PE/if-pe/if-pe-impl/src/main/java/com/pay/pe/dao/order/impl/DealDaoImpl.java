package com.pay.pe.dao.order.impl;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.order.DealDAO;
import com.pay.pe.helper.DealStatus;
import com.pay.pe.model.Deal;
import com.pay.pe.model.PaymentOrder;
import com.pay.util.StringUtil;


@SuppressWarnings("unchecked")
public class DealDaoImpl extends IbatisDAOSupport implements DealDAO {
	/**
	 * sequence name.
	 */
	
	private final static int DEFAULT_WAIT_SECONDS = 0;
	
	private String syncToeknSeqName = "SEQ_DEALSYNCTOKEN";
	

	/**
     * Default constructor.
     */
    public DealDaoImpl() {
        super();
    }

    /**
	 * @return Returns the syncToeknSeqName.
	 */
	public String getSyncToeknSeqName() {
		return syncToeknSeqName;
	}

	/**
	 * @param syncToeknSeqName The syncToeknSeqName to set.
	 */
	public void setSyncToeknSeqName(String syncToeknSeqName) {
		this.syncToeknSeqName = syncToeknSeqName;
	}

	/* (non-Javadoc)
     * 
     */
    public Class<?> getModelClass() {
        return Deal.class;
    }

    /* (non-Javadoc)
     * 
     */
    public List<Deal> findByOrderSeqId(PaymentOrder order) {
    	final Long orderSeqId = order.getSequenceId();
    	String sql = "select dealid from deal where orderseqid = ? ";
    	final List<Long> dealIds = new ArrayList<Long>();
    	
    	super.getJdbcTemplate().query(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setLong(1, orderSeqId);
			}  		
    	}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				dealIds.add(rs.getLong("dealid"));
			} 		
    	});
    	
    	if (dealIds == null || dealIds.isEmpty()) {
    		return null;
    	}
    	
		List<Deal> deals = new LinkedList<Deal>();
		for(int i = 0; i < dealIds.size(); i++){
			Long dealId = dealIds.get(i);
			Deal deal = (Deal)this.findObjectById(dealId);
			deals.add(deal);
		}
    	
        return deals;
    }
    
    /**
     * 查询在一段时间内某一账户的某一订单类型的指定的交易状态的交易额.
     * @param dealStatus
     * 如果为空则查询在一段时间内某一账户的某一订单类型的交易总额,包括成功失败创建的所有交易.
     * @param orderCode int
     * 如果为空则查询在一段时间内某一账户全部的指定的交易状态的交易额.
     * @param memberAcctCode String
     * 如果会员账户为空则查询在一段时间内某一订单类型全部的指定的交易状态的交易额.
     * @param from  Date
     * @param end Date
     * 如果为空则默认为到当前系统时间.
     * @return long
     * 为了避免SQL攻击,需要判断memberAcctCode为合法的账户号.
     */
    public long countDealAmount(
    		final Integer dealStatus,
    		final Integer orderCode,
    		final String memberAcctCode, 
    		final Date from,
    		final Date end) {
    	//判断账户号的正确性，只要不影响SQL的执行即可.
    	//如果为非法的字符，则会抛出RuntimeException.
    	Long.parseLong(memberAcctCode);
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT sum(d.dealAmount) as cnt FROM deal d, paymentorder p WHERE ")
    		.append(" d.orderseqid = p.sequenceid ");
    	if (null != orderCode) {
    		sql.append(" AND p.ordercode = ? ");
    	}
    	if (!StringUtil.isEmpty(memberAcctCode)) {
    		sql.append(" AND d.payeeAcctCode = ? ");
    	}
    	if (null != dealStatus) {
    		sql.append(" AND d.dealStatus = ? ");
    	}
    	if (null != from) {
    		sql.append(" AND d.dealBeginDate > ? ");
    	}
    	sql.append(" AND d.dealBeginDate < ? ");
    	
    	final List<BigDecimal> row = new ArrayList<BigDecimal> ();
    	super.getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				int i = 0;
				if (null != orderCode) {
		    		ps.setInt(++i, orderCode);
		    	}
		    	if (!StringUtil.isEmpty(memberAcctCode)) {
		    		ps.setString(++i, memberAcctCode);
		    	}
		    	if (null != dealStatus) {
		    		ps.setInt(++i, dealStatus);
		    	}
		    	if (null != from) {
		    		ps.setDate(++i, new java.sql.Date(from.getTime()));
		    	}
		    	Date endDate = end;
		    	if (null == end) {
		    		endDate = new Date();
		    	}
		    	ps.setDate(++i, new java.sql.Date(endDate.getTime()));
			}  		
    	}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				row.add(rs.getBigDecimal("cnt"));
			} 	
    	});
    	
    	if (null == row || row.isEmpty()) {
    		return 0L;
    	}
    	BigDecimal value = row.get(0);
    	if (null == value) {
    		return 0L;
    	}
    	long count = value.longValue();
    	return count;
    }

	/* (non-Javadoc)
	 * 
	 */
	public long getDealAccountingToken(final String dealId) {
//		//get a token from the sequence.
//		final long token = DefaultDAO.getInstance().getNextID(getSyncToeknSeqName()).longValue();
//		//execute the next sql in a new tx.
//		
//		String sql = "update deal set syncToken = ? " 
//			+ " where dealId = ?  and dealStatus = 0 and (syncToken <= 0 or syncToken is null)";
//		int rowCount = jdbcTemplate.update(sql, new PreparedStatementSetter() {
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setLong(1, token);
//				ps.setString(2, dealId);
//			}});
//		
//		if (rowCount >= 0) {
//			ToplinkCriteria c1 = ToplinkCriteriaFactory.equal("dealId", dealId);
//			Deal model = (Deal) super.findModelInSameTx(c1);
//			if (null!=model && model.getSyncToken()==token && model.getDealStatus()==0) {
//				return token;
//			}
//		}
//		return -1L;
		return -1L ;
	}

	public List<Deal> queryMassPayDealToUnregisted(final String payeeIdContent) {
		StringBuffer sql = new StringBuffer();
			
		final List<Long> dealIds = new ArrayList<Long> ();
		super.getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, payeeIdContent);
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				dealIds.add(rs.getLong("dealid"));
			}
		});
		/*Map result = this.findObjectsBySql(sql);
		if (null==result || result.isEmpty()) {
			return null;
		}*/
		
		List<Deal> deals = new LinkedList<Deal>();
		for(int i = 0; dealIds != null && i < dealIds.size(); i++){
			Long dealId = dealIds.get(i);
			Deal deal = (Deal)this.findObjectById(dealId);
			deals.add(deal);
		}
		
		return deals;
		
	}

	/**
	 * 获取订单
	 */
	public Deal getDealByVoucherNo(final Long voucherNo) {
		return (Deal) super.getSqlMapClientTemplate().queryForObject(super.namespace.concat("findByVoucherCode"), voucherNo);
	}
	
	
	public Deal getDealAndLocked(final String dealId) {
		return this.getDealAndLocked(dealId, DEFAULT_WAIT_SECONDS, null);
	}

	public Deal getDealAndLocked(final String dealId, long waitSeconds) {
		return this.getDealAndLocked(dealId, waitSeconds, null);
	}
	
	public Deal getDealAndLocked(final String dealId, DealStatus status) {
		return this.getDealAndLocked(dealId, DEFAULT_WAIT_SECONDS, status);
	}
	
	public Deal getDealAndLocked(final String dealId,long waitSeconds,final DealStatus status) {
		StringBuffer sql = new StringBuffer("");
		String statusCondition = "";
		
		if(status != null){
			statusCondition = " and deal_status = ? ";
		}
			
		if(waitSeconds>0){
		   sql.append("select deal_id from deal where deal_id = ? ").append(statusCondition).append(" for update wait ").append(waitSeconds);
		}else {
//		   sql.append("select dealid from deal where dealid = ? ").append(statusCondition).append(" for update nowait ");
		   sql.append("select deal_id from deal where deal_id = ? ").append(statusCondition).append(" for update SKIP LOCKED ");
		}
		
		final List<String> dealIds = new LinkedList<String>();
		this.getJdbcTemplate().query(sql.toString(), new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, dealId);
				if(status!=null){
					ps.setInt(2, status.getValue());
				}
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				dealIds.add(rs.getString("deal_id"));
			}
		});
		
		if (dealIds != null && dealIds.size() > 0) {
			Deal deal = (Deal)this.findObjectById(dealIds.get(0));
			return deal;
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * 
	 */
	public List<Deal> findDealByOrgOrderId(final String orgOrderId){
		Deal deal = new Deal();
		deal.setOrgOrderId(orgOrderId);
		return super.findBySelective(deal);
	}

}
