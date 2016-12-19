package com.pay.pe.dao.closing;

import java.util.Date;


public interface AccountingIntervalDao {
    /**
     * 返回最早可操作的会计区间.
     * 内部逻辑:如果存在操作中的会计区间,返回次日，如果不存在，则查询最大的已关闭的区间的次日，如果不存在，则查询最小的日记帐日期，如果不存在怎返回空
     * 
     * @return 最早可操作的会计区间,去除保留日期，去除时间，可直接用于数值比较
     */
    Date getFirstAvailableDate();

    /**
     * 判断是否该日期可用.
     * 内部逻辑:如果第一个有效区间不存在怎返回true
     * 
     * @param inputDate
     * @return
     */
    boolean isAvailable(Date inputDate);
    /**
     * 获取最后一次的轧账区间日期。如果没有，则返回空。返回的结果已Trunc过。
     * 轧账日期为最后一次已经轧完或正在轧的区间。
     * @return Date
     */
    Date getLastAccountingInterval();
}
