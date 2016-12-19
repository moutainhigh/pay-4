package com.pay.channel.dao;

import com.pay.channel.model.MemberSecondLimit;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public interface MemberSecondLimitDAO {

    MemberSecondLimit findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void insert(MemberSecondLimit insertModel);

    void updateModelByModel(MemberSecondLimit updateModel);

    List<MemberSecondLimit> findPageResult(MemberSecondLimit searchDto, Page page);

    List<MemberSecondLimit> findPageResult(MemberSecondLimit searchDto);

    List<MemberSecondLimit> findAll();
}
