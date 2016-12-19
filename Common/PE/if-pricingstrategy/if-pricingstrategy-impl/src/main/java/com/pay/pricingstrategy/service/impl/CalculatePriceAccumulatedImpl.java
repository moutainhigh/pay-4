/**
 * 
 */
package com.pay.pricingstrategy.service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTOUtil;
import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.model.PricingStrategyDetail;
import com.pay.pricingstrategy.service.CalFeeFactory;
import com.pay.pricingstrategy.service.CalFeeInnerParam;
import com.pay.pricingstrategy.service.CalPriceInnerDealAmount;
import com.pay.pricingstrategy.service.CalPriceInnerParam;
import com.pay.pricingstrategy.service.CalculatePrice;



/**
 * caculatemethod是每月累计的交易费用计算.
 * @author 
 *
 */
public final class CalculatePriceAccumulatedImpl implements CalculatePrice {
    /**
     * 注入calpriceFactory.
     */
    private CalFeeFactory calFeeFactory;
    /**
     * 注入pricingStrategyDAO.
     */
    private PricingStrategyDAO pricingStrategyDAO;
    
    private CalPriceInnerDealAmount innerDealAmount;
    /**
     * 具体的caculatemethod.
     */
    private final int caculatemethod = CACULATEMETHOD.ACCUMULATED.getValue();
    /* (non-Javadoc)
     *
     */
    public long calPrice(final CalPriceInnerParam param) {
        return this.calPriceDetail(param).getFee();
    }

    /* (non-Javadoc)
     * 
     */
    public int getCaculatemethod() {
        return caculatemethod;
    }
/*
 * (non-Javadoc)
 *
 */
    public CalPriceFeeResponse calPriceDetail(CalPriceInnerParam param) {
//            assert param != null;
            Assert.notNull(param);
//            assert param.getTransactionAmount() != null && param.getTransactionAmount() > 0;
//            assert param.getTransactionAmount() != null;
            Assert.notNull(param.getTransactionAmount());
            //真正的交易金额
            Long realTrancAmount = param.getTransactionAmount();
            //真正的算累计金额的费用
            
            //构建需要计算费的参数
            CalFeeInnerParam feeParam = new CalFeeInnerParam();
            if(realTrancAmount==0){
            	  //得到累计交易额
        		Calendar cal=Calendar.getInstance();
        		cal.add(Calendar.MONTH, -1);
        		
                Long dealAmountSum = innerDealAmount.getSuccAccumulatedAmount(cal.get(Calendar.MONTH), param.getMemberCode(), param.getPaymentServiceCode()).longValue();
                param.setTransactionAmount(dealAmountSum);//根据累计交易额得到价格策略明细
             
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
                
                if (detailDtoList == null
                    || detailDtoList.size() > 1) {
                    throw new IllegalStateException("获得价格策略明细错误");
                }
                if (detailDtoList.isEmpty()) {
                    return new CalPriceFeeResponse();
                }
                feeParam.setPricingstategydetaildto(detailDtoList.get(0));
                feeParam.setTransactionAmount(dealAmountSum);//将本次交易金额赋值计算费用
                return calFeeFactory.getCalFee(
                    param.getPricingStrategyDto().getPriceStrategyType()).calculateFeeDetail(
                    feeParam);
            }else{ 	
                CalPriceFeeResponse response = new CalPriceFeeResponse();
                response.setFixedFee(0L);
                response.setMixFee(0L);
                response.setMaxFee(0L);
                response.setFee(0L);
                return response;
            }          

     
    }
    public void setInnerDealAmount(CalPriceInnerDealAmount innerDealAmount) {
        this.innerDealAmount = innerDealAmount;
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
