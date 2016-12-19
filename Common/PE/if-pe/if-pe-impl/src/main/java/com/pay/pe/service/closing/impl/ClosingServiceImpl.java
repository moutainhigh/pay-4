package com.pay.pe.service.closing.impl;

import com.pay.pe.dao.closing.ClosingRefDAO;
import com.pay.pe.service.closing.ClosingService;
import com.pay.util.MfDate;


/**
 * 调用存储过程完成轧帐.轧帐的算法如下:
 * OpeningBal:当日初始时余额,等于前一天的ClosingBal
 * Value:账户的当日发生金额，等于帐户在当日的所有交易的金额总和,可以从AccountEntry表中得到
 * ClosingBal:当日结束时余额,等于当日的OpeningBal+当日的Value
 *           OpeningBal     Value      ClosingBal
 *   第一天                           0        0           0
 *       发生交易1元    0        1           0
 *       发生交易2元    0        3           0
 *
 *   第二天
 *       轧帐 第一天    0        3           3
 *           第二天    3        0           0
 *       发生交易3元    3        3           0
 *       发生交易4元    3        7           0
 *
 *   第三天
 *       轧帐 第二天    3        7          10
 *           第三天    10       0           0
 *       无交易
 *
 *   第四天
 *       轧帐 第三天    10       0          10
 *            第四天    10      0           0
 *
 *   第五天
 *       轧帐 第四天    10       0           10
 *            第五天    10      0           0
 *       发生交易1元    10       1           0
 *       发生交易2元    10       3           0
 */
public final class ClosingServiceImpl implements ClosingService {
    /**
     * 轧帐DAO.
     */
    private ClosingRefDAO closingRefDAO;

    /**
     * @return Returns the closingRefDAO.
     */
    public ClosingRefDAO getClosingRefDAO() {
        return closingRefDAO;
    }

    /**
     * @param closingRefDAO The closingRefDAO to set.
     */
    public void setClosingRefDAO(ClosingRefDAO closingRefDAO) {
        this.closingRefDAO = closingRefDAO;
    }

    /**
     * 得到最近一次轧帐的日期.
     * @return MfDate
     */
    public MfDate getLastClosingDate() {
        return getClosingRefDAO().getLastClosingDate();
    }

    /**
     * 调用存储过程轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     * <br>对参数中指定日期进行轧帐.
     * <br>如果指定的日期不等于最后轧帐日期的后一天，则返回不做处理.
     */
    public void closing(final MfDate acctPeriodDate) {
        MfDate lastClosingDate = getLastClosingDate();
        if (null == acctPeriodDate) {
            return;
        }
        if (lastClosingDate != null && !lastClosingDate
                .excursionDays(1).isSameDay(acctPeriodDate)) {
            return;
        }
        getClosingRefDAO().create(acctPeriodDate);
    }

    /**
     * 调用存储过程回滚轧帐.
     * @param acctPeriodDate <code>MfDate</code> object.
     * <br>参数中的日期必须为系统中最后一次轧帐的日期.
     */
    public void closingReturn(final MfDate acctPeriodDate) {
        MfDate lastClosingDate = getLastClosingDate();
        if (null == lastClosingDate || null == acctPeriodDate) {
            return;
        }
        if (lastClosingDate != null && !lastClosingDate.
                isSameDay(acctPeriodDate)) {
            return;
        }
        getClosingRefDAO().delete(acctPeriodDate);
    }
}
