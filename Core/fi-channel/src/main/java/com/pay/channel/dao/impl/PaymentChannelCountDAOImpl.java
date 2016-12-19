package com.pay.channel.dao.impl;

import com.pay.channel.dao.PaymentChannelCountDAO;
import com.pay.channel.dto.PaymentChannelCountDTO;
import com.pay.channel.model.PaymentChannelCount;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by eva on 2016/8/17.
 */
public class PaymentChannelCountDAOImpl extends BaseDAOImpl implements PaymentChannelCountDAO{
    @Override
    public PaymentChannelCount findById(BigDecimal id) {
        return (PaymentChannelCount)super.findById(id);
    }

    @Override
    public void deleteById(BigDecimal id) {
        super.delete(id);
    }

    @Override
    public void insert(PaymentChannelCount insertModel) {
        super.create(insertModel);
    }

    @Override
    public void updateModelByModel(PaymentChannelCount updateModel) {
        super.update("updateModelByModel", updateModel);
    }

    @Override
    public List<PaymentChannelCountDTO> findPageResult(PaymentChannelCountDTO searchDTO, Page page) {
        return super.findByCriteria(searchDTO,page);
    }

    @Override
    public List<PaymentChannelCountDTO> findPageResult(PaymentChannelCountDTO searchDTO) {
        return super.findByCriteria(searchDTO);
    }

}
