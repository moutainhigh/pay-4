package com.pay.channel.redis;

import com.pay.util.JSonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Created by eva on 2016/8/14.
 */
public class JedisQueue<T> {

    public final Log logger = LogFactory.getLog(JedisQueue.class);

    private JedisConnectionFactory connectionFactory;

    private String suffix="_";

    private Class<T> clazz;

    private String name;

    public void push(T... ts){
        push(Arrays.asList(ts));
    }


    public void push(Collection<T> collections) {
        JedisConnection connection = connectionFactory.getConnection();
        try{
            for (T t: collections
                    ) {
                connection.lPush((suffix + name).getBytes("utf-8"), JSonUtil.bean2json(t).getBytes("utf-8"));
            }
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    public T take() {
        JedisConnection connection = connectionFactory.getConnection();
        T returnt = null;
        try{
            while (true) {
                TimeUnit.MILLISECONDS.sleep(300);
                if (!connection.exists((suffix + name).getBytes("utf-8")))
                    continue;
                byte[] bytes = connection.rPop((suffix + name).getBytes("utf-8"));
                if (bytes != null && bytes.length > 0) {
                    returnt = (T)JSonUtil.json2Object(new String(bytes,"utf-8"),clazz);
                }
            }
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
        }finally {
            connection.close();
            return returnt;
        }
    }

    public T poll() {
        JedisConnection connection = connectionFactory.getConnection();
        try{
            if(!connection.exists((suffix + name).getBytes("utf-8"))){
                return null;
            }else{
                byte[] bytes = connection.rPop((suffix + name).getBytes("utf-8"));
                if (bytes != null && bytes.length > 0) {
                    String jsonObj = new String(bytes,"utf-8");
                    return (T)JSonUtil.json2Object(jsonObj,clazz);
                }
            }
            return null;
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }finally {
            connection.close();
        }
    }
    public Long size() {
        JedisConnection connection = connectionFactory.getConnection();
        try{
            if(!connection.exists((suffix + name).getBytes("utf-8"))){
                return 0l;
            }else{
                return connection.lLen((suffix + name).getBytes("utf-8"));
            }
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
            return 0l;
        }finally {
            connection.close();
        }
    }

    public T poll(long timeout, TimeUnit unit) {
        JedisConnection connection = connectionFactory.getConnection();
        try{
            long nanos = unit.toNanos(timeout);
            while(true){
                long lastTime = System.nanoTime();
                TimeUnit.MILLISECONDS.sleep(300);
                if (!connection.exists((suffix + name).getBytes("utf-8")))
                    continue;
                byte[] bytes = connection.rPop((suffix + name).getBytes("utf-8"));
                if (bytes != null && bytes.length > 0) {
                     return (T)JSonUtil.json2Object(new String(bytes,"utf-8"),clazz);
                }
                if (nanos <= 0)
                    return null;
                nanos -= (System.nanoTime() - lastTime);
            }
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
            return null;
        }finally {
            connection.close();
        }
    }

    public JedisConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
