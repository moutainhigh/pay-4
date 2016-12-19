package com.pay.channel.dao;

import com.pay.channel.dto.MemberConnectHisDTO;
import com.pay.channel.model.MemberConnectHis;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public interface MemberConnectHisDAO {
    MemberConnectHis findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void updateModelByModel(MemberConnectHis updateModel);

    void insert(MemberConnectHis his);

    List<MemberConnectHisDTO> findPageResult(MemberConnectHisDTO searchDto, Page page);
    List<MemberConnectHisDTO> findPageResult(MemberConnectHisDTO searchDto);
}
