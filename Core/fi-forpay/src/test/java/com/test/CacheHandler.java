package com.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

public class CacheHandler {
    
//    private Map<String, String> cacheParams;
//     
//    private static ShardedJedisPool shardedJedisPool = null;
//     
//    private static CacheHandler instance = new CacheHandler();
//     
//    public static CacheHandler getInstance(){
//        return instance;
//    }
//     
//    /**
//     * <p>Title: 私有化默认构造函数</p> 
//     */
//    private CacheHandler(){
//        shardedJedisPool = initShardedJedisPool();
//    }
//     
//    public ShardedJedis getShardedJedis(){
//        return  shardedJedisPool.getResource();
//    }
// 
//    public Jedis getJedis(){
//        return shardedJedisPool.getResource().getShard("");
//    }
//     
//    public ShardedJedisPool getShardedJedisPool(){
//        return shardedJedisPool;
//    }
// 
//    private ShardedJedisPool initShardedJedisPool(){
//         
//        getPropertiesByFile();
//        shardedJedisPool = initJedisShardInfo();
//         
//        return shardedJedisPool;
//    }
//     
//    public void returnResource(ShardedJedis shardedJedis){
//        if(shardedJedis != null){
//            shardedJedisPool.returnResource(shardedJedis);
//            shardedJedis = null;
//        }
//    }
// 
//    public void returnBrokenResource(ShardedJedis shardedJedis){
//        if(shardedJedis != null){
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//        }
//    }
//     
//    private Map<String,String> getPropertiesByFile(){
//        cacheParams = PropertiesUtils.getProMap("redis.properties");
//        return cacheParams;
//    }
//     
//    private JedisPoolConfig initRedisPool(){
//        JedisPoolConfig config = new JedisPoolConfig();
//        config.setMaxIdle(Integer.valueOf(cacheParams.get(CacheConstant.MAX_IDLE)).intValue());
//        config.setMinIdle(Integer.valueOf(cacheParams.get(CacheConstant.MIN_IDLE)).intValue());
//        config.setMaxTotal(Integer.valueOf(cacheParams.get(CacheConstant.MAX_TOTAL)).intValue());
//        config.setMaxWaitMillis(Integer.valueOf(cacheParams.get(CacheConstant.MAX_WAIT_MILLS)).longValue());
//        config.setTestOnBorrow(Boolean.valueOf(cacheParams.get(CacheConstant.TEST_ON_BORRW)).booleanValue());
//        config.setTestOnReturn(Boolean.valueOf(cacheParams.get(CacheConstant.TEST_ON_RETURN)).booleanValue());
//        config.setMinEvictableIdleTimeMillis(Integer.valueOf(cacheParams.get(CacheConstant.MIN_EVICTABLE_IDLE_TIME_MILLIS)).longValue());
//        config.setTimeBetweenEvictionRunsMillis(Integer.valueOf(cacheParams.get(CacheConstant.TIME_BETWEEN_EVICTION_RUNS_MILLIS)).intValue());
//        config.setBlockWhenExhausted(Boolean.valueOf(cacheParams.get(CacheConstant.BLOCK_WHER_EXHAUSTED)).booleanValue());
//        config.setJmxEnabled(Boolean.valueOf(cacheParams.get(CacheConstant.JMX_ENABLED)).booleanValue());
//        config.setNumTestsPerEvictionRun(Integer.valueOf(cacheParams.get(CacheConstant.NUM_TESTS_PER_EVICTION_RUN)).intValue());
//        config.setTestWhileIdle(Boolean.valueOf(cacheParams.get(CacheConstant.TEST_WHILE_IDLE)).booleanValue());
//        return config;
//    }
//     
//    private ShardedJedisPool initJedisShardInfo(){
//        List<JedisShardInfo> shards = setJedisShardInfos(cacheParams);
//        JedisPoolConfig config = initRedisPool();
//        if(shardedJedisPool == null)
//            shardedJedisPool =  new ShardedJedisPool(config, shards,Hashing.MURMUR_HASH,Sharded.DEFAULT_KEY_TAG_PATTERN);
//         
//        return shardedJedisPool;
//    }
//     
//    private List<JedisShardInfo> setJedisShardInfos(Map<String,String> params){
//        List<JedisShardInfo> jedisShardInfos = new ArrayList<JedisShardInfo>();
//        List<String> ips = new ArrayList<String>();
//        List<String> ports = new ArrayList<String>();
//         
//        for(Map.Entry<String, String> entry : params.entrySet()){
//             
//            if(entry.getKey().contains("redis.ip") && entry.getValue() != null)
//                 ips.add(entry.getValue());
//             
//            if(entry.getKey().contains("redis.port") && entry.getValue() != null)
//                ports.add(entry.getValue());
//        }
//         
//        for(int i=0; i<ips.size(); i++){
//            String ip = ips.get(i);
//            String port = ports.get(i);
//            JedisShardInfo info = new JedisShardInfo(ip, Integer.valueOf(port).intValue(),Integer.valueOf(params.get("redis.timeBetweenEvictionRunsMillis")).intValue(),ip);
//            String pwd = cacheParams.get("redis.password");
//            if(VerifyHandler.INSTANCE.isVerify(pwd))
//                info.setPassword(pwd);
//            jedisShardInfos.add(info);
//        }
//        return jedisShardInfos;
//    }
}
