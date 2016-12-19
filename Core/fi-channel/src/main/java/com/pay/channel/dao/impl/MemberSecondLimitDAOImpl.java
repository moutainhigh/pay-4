package com.pay.channel.dao.impl;

import com.pay.channel.dao.MemberSecondLimitDAO;
import com.pay.channel.model.MemberSecondLimit;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class MemberSecondLimitDAOImpl extends BaseDAOImpl implements MemberSecondLimitDAO{
    @Override
    public MemberSecondLimit findById(BigDecimal id) {
        return (MemberSecondLimit)super.findById("findById",id);
    }

    @Override
    public void deleteById(BigDecimal id) {
        super.delete("delete", id);
    }

    @Override
    public void insert(MemberSecondLimit insertModel) {
        super.create(insertModel);
    }

    @Override
    public void updateModelByModel(MemberSecondLimit updateModel) {
        super.update("updateModelByModel",updateModel);
    }

    @Override
    public List<MemberSecondLimit> findPageResult(MemberSecondLimit searchDto, Page page) {
        return super.findByCriteria(searchDto,page);
    }

    @Override
    public List<MemberSecondLimit> findPageResult(MemberSecondLimit searchDto) {
        return super.findByCriteria(searchDto);
    }

    public List<MemberSecondLimit> findAll(){
        return super.findAll("findAll");
    }
}
