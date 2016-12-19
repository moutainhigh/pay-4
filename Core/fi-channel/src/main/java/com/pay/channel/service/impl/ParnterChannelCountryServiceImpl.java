package com.pay.channel.service.impl;

import com.pay.channel.dao.ParnterChannelCountryDAO;
import com.pay.channel.model.ChannelItemRCurrency;
import com.pay.channel.model.ParnterChannelCountry;
import com.pay.channel.service.ParnterChannelCountryService;
import com.pay.inf.dao.Page;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class ParnterChannelCountryServiceImpl implements ParnterChannelCountryService{

    private ParnterChannelCountryDAO parnterChannelCountryDAO;

    public void setParnterChannelCountryDAO(ParnterChannelCountryDAO parnterChannelCountryDAO) {
        this.parnterChannelCountryDAO = parnterChannelCountryDAO;
    }

    @Override
    public void deleteConfig(ParnterChannelCountry cc) {
        parnterChannelCountryDAO.deletecc(cc);
    }

    @Override
    public void createConfig(ParnterChannelCountry cc) throws Exception{
        Page page = new Page();
        List<ParnterChannelCountry> array = parnterChannelCountryDAO.queryListpcc(cc, page);
        if(array!= null && array.size() > 0){
            throw new Exception("该配置已存在");
        }else{
            parnterChannelCountryDAO.addpcc(cc);
        }

    }


    @Override
    public void updateConfig (ParnterChannelCountry cc) throws Exception{
        List<ParnterChannelCountry> array = parnterChannelCountryDAO.selectBeforeUpdate(cc);
        if(array!= null && array.size() > 0){
            throw new Exception("该配置已存在");
        }else{
            parnterChannelCountryDAO.updatepcc(cc);
        }
    }
}
