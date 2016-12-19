package com.pay.channel.dao.impl;

import com.pay.channel.dao.ParnterChannelCountryDAO;
import com.pay.channel.model.ParnterChannelCountry;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class ParnterChannelCountryDAOImpl extends BaseDAOImpl<ParnterChannelCountry> 
                             implements ParnterChannelCountryDAO {
    @Override
    public List<ParnterChannelCountry> queryListpcc(ParnterChannelCountry cc, Page page) {
        return super.findByCriteria(cc,page);
    }

    @Override
    public void updatepcc(ParnterChannelCountry cc) {
        super.update(cc);
    }

    @Override
    public void addpcc(ParnterChannelCountry cc) {
        super.create(cc);
    }

    @Override
    public void deletecc(ParnterChannelCountry cc) {
        super.delete(cc);
    }

    @Override
    public List<ParnterChannelCountry> selectBeforeUpdate(ParnterChannelCountry cc) {
       return super.findByCriteria("findBeforeUpdate", cc);
    }
	@Override
	public List<ParnterChannelCountry> queryListpcc(ParnterChannelCountry cc) {
		return super.findByCriteria(cc);
	}

}
