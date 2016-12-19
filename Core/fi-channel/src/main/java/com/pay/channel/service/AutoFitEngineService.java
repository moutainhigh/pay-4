package com.pay.channel.service;

import com.pay.channel.model.MemberSecondLimit;
import com.pay.channel.redis.model.ChannelObj;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by eva on 2016/8/14.
 */
public interface AutoFitEngineService {

    void product2Redis(ChannelObj obj);

    void consum4Redis(ChannelObj obj);

    void addToFree(List<String> ids);

    void deleteMemberSecondLimit(BigDecimal id);

    void updateMemberSecondLimit(MemberSecondLimit memberSecondLimit);

    void insertMemberSecondLimit(MemberSecondLimit memberSecondLimit) throws Exception;

}
