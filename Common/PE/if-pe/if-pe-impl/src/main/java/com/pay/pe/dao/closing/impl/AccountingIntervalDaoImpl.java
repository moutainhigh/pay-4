package com.pay.pe.dao.closing.impl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.pay.pe.dao.closing.AccountingIntervalDao;


public class AccountingIntervalDaoImpl extends SimpleJdbcDaoSupport implements
        AccountingIntervalDao {

    public boolean isAvailable(Date inputDate) {
        Date firstAvailableDate = getFirstAvailableDate();
        if (firstAvailableDate != null) {
            return firstAvailableDate.compareTo(DateUtils.truncate(inputDate,
                    Calendar.DATE)) <= 0;
        }
        return true;
    }

    public Date getFirstAvailableDate() {
        Date date = getSimpleJdbcTemplate()
                .queryForObject(
                        "select max(closing_date) from t_fs_closing_accounting_intvl where state = 'operating' or state = 'closed'",
                        Date.class);
        if (date != null) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.DATE, 1);
            date = c.getTime();
            return DateUtils.truncate(date, Calendar.DATE);
        }
        return null;
//        else {
//        	//这里会有性能问题！！！
//            date = getSimpleJdbcTemplate().queryForObject(
//                    "select min(acctperioddate) from acct_diary ", Date.class);
//            if (date != null) {
//                Calendar c = Calendar.getInstance();
//                c.setTime(date);
//                c.add(Calendar.DATE, -1);
//                date = c.getTime();
//            }
//        }
//        if (date != null) {
//            return DateUtils.truncate(date, Calendar.DATE);
//        } else {
//            return null;
//        }
    }
    
    public Date getLastAccountingInterval() {
		Date date = getSimpleJdbcTemplate()
        	.queryForObject(
                "select max(closing_date) from t_fs_closing_accounting_intvl where state = 'operating' or state = 'closed'",
                Date.class);
		if (date != null) {
            return DateUtils.truncate(date, Calendar.DATE);
        } else {
            return null;
        }
	}

}
