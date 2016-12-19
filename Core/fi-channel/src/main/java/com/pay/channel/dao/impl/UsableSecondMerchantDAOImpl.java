package com.pay.channel.dao.impl;

import com.pay.channel.dao.UsableSecondMerchantDAO;
import com.pay.channel.dto.UsableSecondMerchantDTO;
import com.pay.channel.model.UsableSecondMerchant;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by cuber.huang on 2016/8/13.
 */
public class UsableSecondMerchantDAOImpl extends BaseDAOImpl implements UsableSecondMerchantDAO {

    @Override
    public UsableSecondMerchant findById(BigDecimal id) {
        return (UsableSecondMerchant)super.findById(id);
    }

    @Override
    public void deleteById(BigDecimal id) {
        super.delete(id);
    }

    @Override
    public void insert(UsableSecondMerchant insertModel) {
        super.create(insertModel);
    }

    @Override
    public void updateModelByModel(UsableSecondMerchant updateModel) {
        super.update("updateModelByModel", updateModel);
    }

    @Override
    public List<UsableSecondMerchantDTO> findPageResult(UsableSecondMerchantDTO searchDTO, Page page) {
        return super.findByCriteria(searchDTO,page);
    }

    @Override
    public List<UsableSecondMerchantDTO> findPageResult(UsableSecondMerchantDTO searchDTO) {
        return super.findByCriteria(searchDTO);
    }

    @Override
    public List<UsableSecondMerchantDTO> findFreeSecondMerChant(){
        return super.findByCriteria("findFreeSecondMerChant");
    }

    @Override
    public List<UsableSecondMerchantDTO> findFreeSecondMerChantByPaymentChannelItemId(UsableSecondMerchant dto){
        return super.findByCriteria("findFreeSecondMerChantByPaymentChannelItemId",dto);
    }
}
