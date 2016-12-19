package com.pay.channel.dao.impl;

import com.pay.channel.dao.MemberCurrentConnectDAO;
import com.pay.channel.dto.MemberCurrentConnectDTO;
import com.pay.channel.model.MemberCurrentConnect;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class MemberCurrentConnectDAOImpl extends BaseDAOImpl implements MemberCurrentConnectDAO {

    @Override
    public MemberCurrentConnect findById(BigDecimal id) {
        return (MemberCurrentConnect)super.findById("findById",id);
    }

    @Override
    public void deleteById(BigDecimal id) {
        super.delete("delete", id);
    }

    @Override
    public void insert(MemberCurrentConnect insertModel) {
        super.create(insertModel);
    }

    @Override
    public void updateModelByModel(MemberCurrentConnect updateModel) {
        super.update("updateModelByModel",updateModel);
    }

    @Override
    public List<MemberCurrentConnectDTO> findPageResult(MemberCurrentConnectDTO searchDto, Page page) {
        return super.findByCriteria(searchDto,page);
    }

    @Override
    public List<MemberCurrentConnectDTO> findPageResult(MemberCurrentConnectDTO searchDto) {
        return super.findByCriteria(searchDto);
    }
}
