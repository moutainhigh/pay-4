package com.pay.pe.dao.closing;

import com.pay.util.MfDate;


public interface ClosingRefDAO {
    /**
     * 得到最近一次轧帐的日期.
     * @return MfDate
     */
    MfDate getLastClosingDate();

    /**
     * 调用存储过程轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     * <br>对参数中指定日期进行轧帐.
     */
    void create(MfDate acctPeriodDate);

    /**
     * 调用存储过程回滚轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     * <br>参数中的日期必须为系统中最后一次轧帐的日期.
     */
    void delete(MfDate acctPeriodDate);
}
