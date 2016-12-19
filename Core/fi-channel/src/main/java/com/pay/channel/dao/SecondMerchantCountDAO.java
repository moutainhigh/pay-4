package com.pay.channel.dao;

import com.pay.channel.dto.SecondMerchantCountDTO;
import com.pay.channel.model.SecondMerchantCount;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/13.
 */
public interface SecondMerchantCountDAO {
    SecondMerchantCount findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void insert(SecondMerchantCount insertModel);

    void updateModelByModel(SecondMerchantCount updateModel);

    List<SecondMerchantCountDTO> findPageResult(SecondMerchantCountDTO searchDTO, Page page);
    List<SecondMerchantCountDTO> findPageResult(SecondMerchantCountDTO searchDTO);
}
