package com.pay.poss.db;

/**
 * 数据库转换类
 *
 * Created by songyilin on 2016/10/11.
 */
public class DataBaseContextHolder {
   public static final String POSS_DS = "possDs";

    public static final String FI_DS = "fiDs";

    public static final String FO_DS = "foDs";

    public static final String INF_DS = "infDs";

    public static final String ACC_DS = "accDs";

    public static final String PE_DS = "peDs";

    public static final String RISK_DS = "riskDs";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
