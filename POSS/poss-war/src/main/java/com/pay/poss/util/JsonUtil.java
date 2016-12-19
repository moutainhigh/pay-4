package com.pay.poss.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * Json处理共通
 * Created by ZHW on 2015/5/11.
 */
public class JsonUtil {

    private static Logger logger = Logger.getLogger(JsonUtil.class);

    /**
     * 把对象转换成Json字符串
     *
     * @param obj
     * @return
     */
    public static String entity2Json(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        String json = StringUtils.EMPTY;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            json = StringUtils.EMPTY;
        }
        return json;
    }

    /**
     * 把Json字符串转换成对象,Map,List<Map>
     *
     * @param json
     * @param c
     * @return
     */
    public static Object json2Entity(String json, Class<?> c) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, c);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 把Json字符串转换成对象List
     *
     * @param json
     * @param t    new TypeReference<List<MstRole>>() {}
     * @return
     */
    public static List<?> json2Entity(String json, TypeReference<?> t) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, t);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
