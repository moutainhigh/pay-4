package com.pay.channel.dao.impl;

import com.pay.channel.dao.MemberConnectHisDAO;
import com.pay.channel.dto.MemberConnectHisDTO;
import com.pay.channel.model.MemberConnectHis;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class MemberConnectHisDAOImpl  extends BaseDAOImpl implements MemberConnectHisDAO {

    @Override
    public MemberConnectHis findById(BigDecimal id) {
        return (MemberConnectHis)super.findById("findById",id);
    }

    @Override
    public void deleteById(BigDecimal id) {
            super.delete("delete", id);
    }

    @Override
    public void insert(MemberConnectHis his) {
        super.create(his);
    }

    @Override
    public void updateModelByModel(MemberConnectHis updateModel) {
        super.update("updateModelByModel",updateModel);
    }

    @Override
    public List<MemberConnectHisDTO> findPageResult(MemberConnectHisDTO searchDto, Page page) {
        return super.findByCriteria(searchDto,page);
    }

    @Override
    public List<MemberConnectHisDTO> findPageResult(MemberConnectHisDTO searchDto) {
        return super.findByCriteria(searchDto);
    }
}
