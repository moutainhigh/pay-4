package com.pay.txncore.aop;

import com.pay.fi.exception.BusinessException;

/**
 * Created by cuber on 2016/10/8.
 */
public class TestBean {
    public String returnObj(String name){
        int i = Integer.parseInt(name);
        return name;
    }


    public String returnObj(String name, int age){
        int i = Integer.parseInt(name);
        age = age + 1;
        throw new BusinessException("dfjdlfsjdlkf");
//        return name;
    }

}
