
package com.pay.pe.dao.closing.impl;

import java.util.Vector;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.closing.ClosingRefDAO;
import com.pay.util.MfDate;

public final class ClosingRefDAOImpl extends IbatisDAOSupport
    implements ClosingRefDAO {
    /**
     * 轧帐的存储过程名称.
     */
    private String closingProcName;

    /**
     * 轧帐回滚的存储过程名称.
     */
    private String unClosingProcName;
    
    /**
     * @return Returns the closingProcName.
     */
    public String getClosingProcName() {
        return closingProcName;
    }

    /**
     * @param closingProcName The closingProcName to set.
     */
    public void setClosingProcName(String closingProcName) {
        this.closingProcName = closingProcName;
    }

    /**
     * @return Returns the unClosingProcName.
     */
    public String getUnClosingProcName() {
        return unClosingProcName;
    }

    /**
     * @param unClosingProcName The unClosingProcName to set.
     */
    public void setUnClosingProcName(String unClosingProcName) {
        this.unClosingProcName = unClosingProcName;
    }

    /**
     * 得到最近一次轧帐的日期.
     * @return MfDate
     */
    public MfDate getLastClosingDate() {
//    	AccountReference accountReference = new AccountReference();
//    	this.getJdbcTemplate()"select max(closedacctperioddate) from ACCT_PREFERENCE"
//    	
//        ExpressionType type = ExpressionTypeFactory.max("closedacctperioddate");        
//        List result = super.reportObjectsByExpTypes(AccountReference.class,
//                new ExpressionType[]{type});
//        
//        ReportQueryResult temp = (ReportQueryResult) result.get(0);
//        
//        
//        Timestamp time = (Timestamp) temp.get("closedacctperioddate");
//        if (null != time) {
//            return new MfDate(time);
//        }
        return null;
    }

    /**
     * 调用存储过程轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     * <br>对参数中指定日期进行轧帐.
     */
    public void create(final MfDate acctPeriodDate) {
        Vector vIn = new Vector();
        vIn.add("V_CLOSING_DATE");
        vIn.add(acctPeriodDate.toString());
        super.execProc(getClosingProcName(), vIn);
    }

    /**
     * 调用存储过程回滚轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     */
    public void delete(final MfDate acctPeriodDate) {
        Vector vIn = new Vector();
        vIn.add("V_UNCLOSING_DATE");
        vIn.add(acctPeriodDate.toString());
        super.execProc(getUnClosingProcName(), vIn);
    }
}
