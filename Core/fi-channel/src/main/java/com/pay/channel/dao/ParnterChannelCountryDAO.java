package com.pay.channel.dao;

import com.pay.channel.model.ParnterChannelCountry;
import com.pay.inf.dao.Page;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public interface ParnterChannelCountryDAO {

    List<ParnterChannelCountry> queryListpcc(ParnterChannelCountry cc, Page page);
    
    List<ParnterChannelCountry> queryListpcc(ParnterChannelCountry cc);

    void updatepcc(ParnterChannelCountry cc);

    void addpcc(ParnterChannelCountry cc);

    void deletecc(ParnterChannelCountry cc);

    List<ParnterChannelCountry> selectBeforeUpdate(ParnterChannelCountry cc);
}
