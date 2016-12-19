/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTOUtil;
import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.model.PricingStrategyDetail;
import com.pay.pricingstrategy.service.CalFeeFactory;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalPriceInnerParam;
import com.pay.pricingstrategy.service.CalculatePrice;



/**
 * caculatemethod是单笔交易的交易费用计算.
 * @author 
 *
 */
public final class CalculatePriceSingleImpl implements CalculatePrice{
    /**
     * 注入calpriceFactory.
     */
    private CalFeeFactory calFeeFactory;
    /**
     * 注入pricingStrategyDAO.
     */
    private PricingStrategyDAO pricingStrategyDAO;
    /**
     * 具体的caculatemethod.
     */
    private final int caculatemethod = CACULATEMETHOD.SINGLETRANSACTION.getValue();
    

    /**
     * 计算费用.
     * @param param CalPriceInnerParam
     */
    public long calPrice(final CalPriceInnerParam param) {
    	CalPriceFeeResponse calPriceFeeResponse = this.calPriceDetail(param);
    	if (calPriceFeeResponse == null) {
    		return new CalPriceFeeResponse().getFee();
    	} else {
    		return calPriceFeeResponse.getFee();
    	}
    }
    /*
     * (non-Javadoc)
     * 
     */
    public CalPriceFeeResponse calPriceDetail(CalPriceInnerParam param) {
        Assert.notNull(param);
        Assert.notNull(param.getTransactionAmount());
        Assert.isTrue(param.getTransactionAmount() > 0);
                
        List<PricingStrategyDetail>  pricingStrategyDetailList = this.pricingStrategyDAO.getAllPricingStrategyDetailByParam(
                        param.getPricingStrategyDto().getPriceStrategyCode()
                                , param
                                .getTransactionAmount(), param
                                .getTerminltype(), param
                                .getReservedCode());
        
        if(null!=param .getReservedCode()  && ( null==pricingStrategyDetailList || pricingStrategyDetailList.size()==0)){
        	pricingStrategyDetailList=this.pricingStrategyDAO.getPricingStrategyDetailByReservedCode( param.getPricingStrategyDto().getPriceStrategyCode()
                    , param
                    .getTransactionAmount(), param
                    .getTerminltype(),  param
                    .getReservedCode());
        }
        	
        //得到价格策略明细列表
        List<PricingStrategyDetailDTO> detailDtoList = PricingStrategyDetailDTOUtil
                .convertToPricingDetailList(pricingStrategyDetailList);
        if (detailDtoList == null || detailDtoList.size() > 1) {
            throw new IllegalStateException("获得价格策略明细错误");
        }
        if (detailDtoList.isEmpty()) {
            return null; //new CalPriceFeeResponse();
        }
        CalFeeInnerParam feeParam = new CalFeeInnerParam();
        feeParam.setPricingstategydetaildto(detailDtoList.get(0));
        feeParam.setTransactionAmount(param.getTransactionAmount());
        return calFeeFactory.getCalFee(
                param.getPricingStrategyDto().getPriceStrategyType())
                .calculateFeeDetail(feeParam);
    }
    /**
     * 得到caculatemethod.
     */
    public int getCaculatemethod() {
        return caculatemethod;
    }
    public void setCalFeeFactory(CalFeeFactory calFeeFactory) {
        this.calFeeFactory = calFeeFactory;
    }
	public PricingStrategyDAO getPricingStrategyDAO() {
		return pricingStrategyDAO;
	}
	public void setPricingStrategyDAO(PricingStrategyDAO pricingStrategyDAO) {
		this.pricingStrategyDAO = pricingStrategyDAO;
	}
    

}
