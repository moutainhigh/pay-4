package com.pay.pe.dao.account.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.account.AcctDiaryPostingDao;


public class AcctDiaryPostingDAOImpl 
	extends IbatisDAOSupport
	implements AcctDiaryPostingDao {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(AcctDiaryPostingDAOImpl.class);
	
	
	/**
	 * 记账/更改账户余额的存储过程名称.
	 */
	private String procName;
	
	private ArrayDescriptor entryArrayDesc;

	/**
	 * @return Returns the procName.
	 */
	public String getProcName() {
		return procName;
	}

	/**
	 * @param procName The procName to set.
	 */
	public void setProcName(String procName) {
		this.procName = procName;
	}



	
	public void callPostingProc(final long voucherCode, final Integer[] entries) {
		Connection conn = null;
		try {
			conn = getDataSource().getConnection();
			ARRAY entryPara = wrapProcEntryParameter(entries, conn);
			//创建存储过程的调用
			CallableStatement callable = conn.prepareCall("{ call " + getProcName() +  "(?,?)}");
			callable.clearParameters();
			callable.setLong(1, voucherCode);
			callable.setArray(2, entryPara);
			
			//执行存储过程
			callable.execute();

			entryPara = null;
			
			callable.close();
			//1表示分录及余额修改成功.
			
			logger.info("AcctDiary updated with " + voucherCode + " and " + entries.length + " entries.");
		} catch (SQLException e) {
			logger.error("++++++++++voucherCode:"+voucherCode+"  entries:"+entries.toString());
			logger.error(e);
			throw new IllegalStateException("Account diary posting failed, sql exception:" + e.getMessage());
		} finally {
			//关闭连接.
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error(e);
				}
			}
		}

	}
	
	/**
	 * 封装增加分录的存储过程数组参数.
	 * @param entries
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private ARRAY wrapProcEntryParameter(
			final Integer[] entries,
			final Connection conn) throws SQLException {
		if (null == entryArrayDesc) {
			//多个分录增加的数组.
			entryArrayDesc = new ArrayDescriptor("TYP_ENTRYCODE", conn);
		}
		ARRAY acctsPara = new ARRAY(entryArrayDesc, conn, entries);
		
		return acctsPara;
	}
}
