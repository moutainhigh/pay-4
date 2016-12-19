package com.pay.poss.db;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源切换AOP
 * Created by songyilin on 2016/10/11.
 */
public class DataSourceHandler {

    private static ImmutableList<String> fiMethodList = new ImmutableList.Builder<String>()
            .add("").build();

    private static ImmutableList<String> accMethodList = new ImmutableList.Builder<String>()
            .add("").build();


    /**
     * @param joinPoint
     */
    public void before(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        //String className = joinPoint.getSignature().getDeclaringTypeName();

        if (fiMethodList.contains(method)) {
            DataBaseContextHolder.setDbType(DataBaseContextHolder.FI_DS);
        } else if (accMethodList.contains(method)) {
            DataBaseContextHolder.setDbType(DataBaseContextHolder.ACC_DS);
        }


    }


}
