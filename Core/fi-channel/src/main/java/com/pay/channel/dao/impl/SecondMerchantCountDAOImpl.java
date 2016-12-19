package com.pay.channel.dao.impl;

import com.pay.channel.dao.SecondMerchantCountDAO;
import com.pay.channel.dto.SecondMerchantCountDTO;
import com.pay.channel.model.SecondMerchantCount;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/13.
 */
public class SecondMerchantCountDAOImpl extends BaseDAOImpl implements SecondMerchantCountDAO {
    @Override
    public SecondMerchantCount findById(BigDecimal id) {
        return (SecondMerchantCount)super.findById(id);
    }

    @Override
    public void deleteById(BigDecimal id) {
        super.delete(id);
    }

    @Override
    public void insert(SecondMerchantCount insertModel) {
        super.create(insertModel);
    }

    @Override
    public void updateModelByModel(SecondMerchantCount updateModel) {
        super.update("updateModelByModel", updateModel);
    }

    @Override
    public List<SecondMerchantCountDTO> findPageResult(SecondMerchantCountDTO searchDTO, Page page) {
        return super.findByCriteria(searchDTO,page);
    }

    @Override
    public List<SecondMerchantCountDTO> findPageResult(SecondMerchantCountDTO searchDTO) {
        return super.findByCriteria(searchDTO);
    }
}
