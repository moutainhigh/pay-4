
package com.pay.pe.dao.accounting.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.accounting.PostingDao;
import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.exception.PEBisnessRuntimeException;
import com.pay.util.DateUtil;


public class PostingDaoImpl extends IbatisDAOSupport implements PostingDao {
	/**
	 * 日志.
	 */
	private Log logger = LogFactory.getLog(getClass());

	/**
	 * 获取connection.
	 */
	// private DataSource dataSource;

	/**
	 * 记账/更改账户余额的存储过程名称.
	 */
	private String procName;

	private ArrayDescriptor acctArrDesc;

	private ArrayDescriptor acctTabDesc;

	private ArrayDescriptor entryArrDesc;

	private ArrayDescriptor entryTabDesc;

	private String arr_acct;

	private String tab_acct;

	private String arr_entry;

	private String tab_entry;

	private JdbcTemplate jt;

	public String getArr_acct() {
		return arr_acct;
	}

	public void setArr_acct(String arr_acct) {
		this.arr_acct = arr_acct;
	}

	public String getTab_acct() {
		return tab_acct;
	}

	public void setTab_acct(String tab_acct) {
		this.tab_acct = tab_acct;
	}

	public String getArr_entry() {
		return arr_entry;
	}

	public void setArr_entry(String arr_entry) {
		this.arr_entry = arr_entry;
	}

	public String getTab_entry() {
		return tab_entry;
	}

	public void setTab_entry(String tab_entry) {
		this.tab_entry = tab_entry;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}

	/**
	 * @return Returns the procName.
	 */
	public String getProcName() {
		return procName;
	}

	/**
	 * @param procName
	 *            The procName to set.
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}

	/**
	 * @return Returns the dataSource.
	 */
	// public DataSource getDataSource() {
	// return dataSource;
	// }
	//
	// /**
	// * @param dataSource The dataSource to set.
	// */
	// public void setDataSource(DataSource dataSource) {
	// this.dataSource = dataSource;
	// }
	private Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.200.63:1521:primary", "inf",
					"inf");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * 
	 * 
	 * @see 参考下面的连接地址关于如何调用以数组为参数的存储过程.
	 * http://forums.oracle.com/forums/thread.jspa
	 * ;jsessionid=8d92200830de1141ea1b4829462cabb2ca090ebd6b07
	 * .e34QbhuKaxmMai0MaNeMb3eKaN90?messageID=1479016&#1479016
	 */
	public long callPostingProc(final PostingProcParameter procPara) {
		
		Connection conn = null;
		try {
			Long voucherCode = 0L;
			// conn = getDataSource().getConnection();
			// conn = getNoProxyDataSource().getConnection();
			conn = getConnection();
			ARRAY acctsPara = wrapProcAcctParameter(procPara, conn);
			ARRAY entryPara = wrapProcEntryParameter(procPara, conn);
			
			// 创建存储过程的调用
			CallableStatement callable = conn.prepareCall("{ call "
					+ getProcName() + "(?,?,?)}");
			callable.clearParameters();
			callable.setArray(1, acctsPara);
			callable.setArray(2, entryPara);
			callable.registerOutParameter(3, OracleTypes.NUMBER);
			// 执行存储过程
			callable.execute();
			// 得到返回结果.
			voucherCode = callable.getLong(3);
			acctsPara = null;
			entryPara = null;
			callable.close();
			// 1表示分录及余额修改成功.
			if (0 == voucherCode.longValue()) {
				throw new IllegalStateException("posting failed.");
			} else if (-1 == voucherCode.longValue()) {
				StringBuilder acctCodes = new StringBuilder();
				acctCodes.append("acctCodes={");
				for (PostingProcAcctParameter e : procPara.getAcctParameters()) {
					acctCodes.append(e.getAccountCode());
					acctCodes.append(",");
				}
				acctCodes.append("}");
				throw new PEBisnessRuntimeException(
						ErrorCodeType.BALANCE_NOT_ENOUGH, acctCodes.toString());
			}
			return voucherCode;
		} catch (SQLException e) {
			logger.error(e);
			throw new IllegalStateException("posting failed,sql exception:"
					+ e.getMessage(), e);
		} finally {
			// 关闭连接.
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 *
	 */
	public void changeEntriesStatus(final String voucherCode, final int status) {
		String sql = "update entry set status = ? where vouchercode = ? and status != ? ";
		jt.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, status);
				ps.setString(2, voucherCode);
				ps.setInt(3, status);
			}
		});
	}

	/**
	 * 封状修改余额的存储过程参数.
	 * 
	 * @param procPara
	 * @param acctArrDesc
	 * @param acctTabDesc
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private ARRAY wrapProcAcctParameter(final PostingProcParameter procPara,
			final Connection conn) throws SQLException {
		if (null == acctArrDesc) {
			// 更改一个账户余额的参数说明.
			// acctArrDesc = new ArrayDescriptor("ARR_ACCT", conn);
			acctArrDesc = new ArrayDescriptor(getArr_acct(), conn);
			// 多个账户余额修改的数组.
			// acctTabDesc = new ArrayDescriptor("TAB_ACCT", conn);
			acctTabDesc = new ArrayDescriptor(getTab_acct(), conn);
		}
		int lenAcct = procPara.getAcctParameters().size();
		Object[] result = new Object[lenAcct];
		Iterator<PostingProcAcctParameter> it = procPara.getAcctParameters()
				.iterator();
		int index = 0;
		while (it.hasNext()) {
			Object[] acctsData = new Object[5];
			PostingProcAcctParameter acctPara = it.next();
			acctsData[0] = acctPara.getBalance();
			acctsData[1] = acctPara.getCreditBalance();
			acctsData[2] = acctPara.getDebitBalance();
			acctsData[3] = acctPara.getAccountCode();
			// 存储过程中用1表示账户余额可以为负.
			acctsData[4] = acctPara.isCanBeNegativeBalance() ? 1 : 0;
			ARRAY acctsArr = new ARRAY(acctArrDesc, conn, acctsData);
			result[index] = acctsArr;
			index++;
		}
		ARRAY acctsPara = new ARRAY(acctTabDesc, conn, result);
		return acctsPara;
	}

	/**
	 * 封装增加分录的存储过程参数.
	 * 
	 * @param procPara
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private ARRAY wrapProcEntryParameter(final PostingProcParameter procPara,
			final Connection conn) throws SQLException {
		// 增加一个分录的参数说明.
		if (null == entryArrDesc) {
			// entryArrDesc = new ArrayDescriptor("ARR_ENTRY_V2", conn);
			entryArrDesc = new ArrayDescriptor(getArr_entry(), conn);
			// 多个分录增加的数组.
			// entryTabDesc = new ArrayDescriptor("TAB_ENTRY_V2", conn);
			entryTabDesc = new ArrayDescriptor(getTab_entry(), conn);
		}
		int lenEntry = procPara.getEntryParameters().size();
		Object[] result = new Object[lenEntry];
		Iterator<PostingProcEntryParameter> it = procPara.getEntryParameters()
				.iterator();
		int index = 0;
		while (it.hasNext()) {
			Object[] entriesData = new Object[11];
			PostingProcEntryParameter entryPara = it.next();
			entriesData[0] = entryPara.getAccountCode();
			entriesData[1] = entryPara.getCrdr();
			entriesData[2] = entryPara.getAmount();
			entriesData[3] = entryPara.getMemo();
			entriesData[4] = entryPara.getDealId();
			entriesData[5] = entryPara.getPaymentServiceCode();
			entriesData[6] = entryPara.getStatus();
			entriesData[7] = entryPara.getEntryCode();
			// 设置币种和汇率
			entriesData[8] = entryPara.getCurrencyCode();
			entriesData[9] = entryPara.getExchangeRate();
			// 设置交易时间
			entriesData[10] = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",entryPara.getTransactiondate());

			System.out.println(entriesData[0]+"--"+entriesData[1]+"--"+entriesData[2]+"--"+entriesData[3]+"--"+entriesData[4]+"--"+entriesData[5]+"--"+entriesData[6]+"--"+entriesData[7]+"--"+entriesData[8]+"--"+entriesData[9]+"--"+entriesData[10]);
			
			ARRAY entriesArr = new ARRAY(entryArrDesc, conn, entriesData);
			result[index] = entriesArr;
			index++;
		}
		ARRAY acctsPara = new ARRAY(entryTabDesc, conn, result);
		
		
		
//		Object[] objarray = (Object[])acctsPara.getArray();
//		for(int i = 0 ;i<objarray.length;i++){
//			System.out.println("------"+i+"   "+objarray[i].getClass());
//			oracle.sql.ARRAY aa = (oracle.sql.ARRAY)objarray[i];
//			Object[] bb = (Object[])aa.getArray();
//			for(int j = 0 ;j<bb.length;j++){
//				System.out.println("------"+i+"   "+bb[i]);
//			}
//		}
		
		return acctsPara;
	}

	
	public void changeEntryStatus(final String voucherCode,
			final Integer entryCode, final int status) {
		String sql = "update entry set status = ? where vouchercode = ? and entrycode = ? and status != ? ";
		jt.update(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setInt(1, status);
				ps.setString(2, voucherCode);
				ps.setInt(3, entryCode.intValue());
				ps.setInt(4, status);
			}
		});
	}
}
 