package com.pay.channel.dao;

import com.pay.channel.dto.MemberCurrentConnectDTO;
import com.pay.channel.model.MemberCurrentConnect;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public interface MemberCurrentConnectDAO {

    MemberCurrentConnect findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void insert(MemberCurrentConnect insertModel);

    void updateModelByModel(MemberCurrentConnect updateModel);

    List<MemberCurrentConnectDTO> findPageResult(MemberCurrentConnectDTO searchDto, Page page);

    List<MemberCurrentConnectDTO> findPageResult(MemberCurrentConnectDTO searchDto);
}
