package com.pay.channel.redis.lock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by eva on 2016/8/14.
 */
public class NormalLock {


    private JedisConnectionFactory connectionFactory;

    private long lockTime = 1l;


    public long getLockTime() {
        return lockTime;
    }

    public void setLockTime(long lockTime) {
        this.lockTime = lockTime;
    }

    public final Log logger = LogFactory.getLog(NormalLock.class);

    public JedisConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }


    public boolean tryLock(String lockName,  long timeout, TimeUnit unit) {
        JedisConnection connection = connectionFactory.getConnection();
        try {
            long nano = System.nanoTime();
            do {
                logger.debug("try lock key: " + lockName);
                if(connection.setNX(lockName.getBytes("utf-8"),lockName.getBytes("utf-8"))){
                    connection.expire(lockName.getBytes("utf-8"),lockTime);
                    return true;
                }else{
                    logger.debug("key: " + lockName + " locked by another business：" + lockName);
                }
                if (timeout == 0) {
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(300);
            }while((System.nanoTime() - nano) < unit.toNanos(timeout));
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
        }finally {
            connection.close();
        }
        return false;
    }

    public boolean tryLock(String lockName) {
        return tryLock(lockName, 0l, TimeUnit.SECONDS);
    }

    public void lock(String lockName, long tryMilliSecond){
        JedisConnection connection = connectionFactory.getConnection();
        try{
            logger.debug("lock key: " + lockName);
            do{
                if(connection.setNX(lockName.getBytes("utf-8"),lockName.getBytes("utf-8"))){
                    connection.expire(lockName.getBytes("utf-8"),lockTime);
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(tryMilliSecond);
            }while(true);
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }

    public void lock(String lockName){
        lock(lockName, 0l);
    }

    public void unLock(String lockName){
        JedisConnection connection = connectionFactory.getConnection();
        try{
            connection.del(lockName.getBytes("utf-8"));
        }catch(Exception e){
            logger.info(e);
            e.printStackTrace();
        }finally {
            connection.close();
        }
    }
}
