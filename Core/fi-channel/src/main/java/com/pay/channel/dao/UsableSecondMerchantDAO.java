package com.pay.channel.dao;

import com.pay.channel.dto.UsableSecondMerchantDTO;
import com.pay.channel.model.UsableSecondMerchant;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/13.
 */
public interface UsableSecondMerchantDAO {

    UsableSecondMerchant findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void insert(UsableSecondMerchant insertModel);

    void updateModelByModel(UsableSecondMerchant updateModel);

    List<UsableSecondMerchantDTO> findPageResult(UsableSecondMerchantDTO searchDTO, Page page);

    List<UsableSecondMerchantDTO> findPageResult(UsableSecondMerchantDTO searchDTO);

    List<UsableSecondMerchantDTO> findFreeSecondMerChant();

    List<UsableSecondMerchantDTO> findFreeSecondMerChantByPaymentChannelItemId(UsableSecondMerchant dto);
}
