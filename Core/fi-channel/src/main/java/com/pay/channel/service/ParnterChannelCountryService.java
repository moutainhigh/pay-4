package com.pay.channel.service;

import com.pay.channel.model.ParnterChannelCountry;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public interface ParnterChannelCountryService {
    void deleteConfig(ParnterChannelCountry cc);
    void createConfig(ParnterChannelCountry cc) throws Exception;

    void updateConfig(ParnterChannelCountry cc) throws Exception;
}
