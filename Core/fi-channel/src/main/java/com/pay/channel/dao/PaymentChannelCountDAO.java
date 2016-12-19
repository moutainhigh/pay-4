package com.pay.channel.dao;

import com.pay.channel.dto.PaymentChannelCountDTO;
import com.pay.channel.model.PaymentChannelCount;
import com.pay.inf.dao.Page;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/13.
 */
public interface PaymentChannelCountDAO {
    PaymentChannelCount findById(BigDecimal id);

    void deleteById(BigDecimal id);

    void insert(PaymentChannelCount insertModel);

    void updateModelByModel(PaymentChannelCount updateModel);

    List<PaymentChannelCountDTO> findPageResult(PaymentChannelCountDTO searchDTO, Page page);

    List<PaymentChannelCountDTO> findPageResult(PaymentChannelCountDTO searchDTO);

}
