package com.pay.channel.service;

import com.pay.channel.model.ChannelConfig;

import java.math.BigDecimal;

/**
 * Created by eva on 2016/8/14.
 */
public interface UsableSecondMerchantService {
    /**
     * 把一个商户号加入自由配置池
     * @param config
     */
    void joinUsablePool(ChannelConfig config);

    /**
     * 删除一个商户号
     * @param config
     */
    void dragOutPool(ChannelConfig config);


    void dragOutPoolById(BigDecimal id);


}
