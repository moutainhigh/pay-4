package com.pay.poss.db;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 * Created by songyilin on 2016/10/11.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static Logger logger = Logger.getLogger(DynamicDataSource.class);

    @Override
    public Object determineCurrentLookupKey() {
        //TODO
       // String type = DataBaseContextHolder.getDbType();
        String type = "fiDs";
        logger.debug(type);
        return type;
    }

}
