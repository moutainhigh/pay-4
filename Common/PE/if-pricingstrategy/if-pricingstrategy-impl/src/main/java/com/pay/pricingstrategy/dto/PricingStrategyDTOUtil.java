package com.pay.pricingstrategy.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pay.pricingstrategy.model.PricingStrategy;



public class PricingStrategyDTOUtil {
    public static PricingStrategyDTO convertToPricingStrategyDTO(
            final PricingStrategy pricingStrategy) {
        if (pricingStrategy == null) {
            return null;
        }
        PricingStrategyDTO dto = new PricingStrategyDTO();
        dto.setCaculateMethod(pricingStrategy.getCaculateMethod());
        dto.setEffectiveOn(pricingStrategy.getEffectiveOn());
        dto.setInvalidDate(pricingStrategy.getInvalidDate());
//        dto.setMember(pricingStrategy.getMember());
        dto.setMemberCode(pricingStrategy.getMemberCode());
//        dto.setPaymentServiceDto(PaymentServiceDTOUtil.convertPaymentServiceDTO(pricingStrategy.getPaymentService()));
        dto.setPaymentServiceCode(pricingStrategy.getPaymentServiceCode());
        dto.setPriceStrategyCode(pricingStrategy.getPriceStrategyCode());
        dto.setPriceStrategyName(pricingStrategy.getPriceStrategyName());
        dto.setPriceStrategyType(pricingStrategy.getPriceStrategyType());
//        dto.setServiceLevel(pricingStrategy.getServiceLevel());
        dto.setServiceLevelCode(pricingStrategy.getServiceLevelCode());
        dto.setStatus(pricingStrategy.getStatus());
        dto.setValidDate(pricingStrategy.getValidDate());
        dto.setPriceStrategySeq(pricingStrategy.getPriceStrategySeq());
        return dto;
    }
    public static PricingStrategy convertToPricingStrategy(
            final PricingStrategyDTO dto) {
        if (dto == null) {
            return null;
        }
        PricingStrategy pricingStrategy = new PricingStrategy();
        pricingStrategy.setCaculateMethod(dto.getCaculateMethod());
        pricingStrategy.setEffectiveOn(dto.getEffectiveOn());
        pricingStrategy.setInvalidDate(dto.getInvalidDate());
//        pricingStrategy.setMember(dto.getMember());
        pricingStrategy.setMemberCode(dto.getMemberCode());    
        
//        pricingStrategy.setPaymentService(PaymentServiceDTOUtil
//            .convertToPaymentService(dto.getPaymentServiceDto()));        
        pricingStrategy.setPaymentServiceCode(dto.getPaymentServiceCode());
        
        pricingStrategy.setPriceStrategyCode(dto.getPriceStrategyCode());
        pricingStrategy.setPriceStrategyName(dto.getPriceStrategyName());
        pricingStrategy.setPriceStrategyType(dto.getPriceStrategyType());
//        pricingStrategy.setServiceLevel(dto.getServiceLevel());
        pricingStrategy.setServiceLevelCode(dto.getServiceLevelCode());
        pricingStrategy.setStatus(dto.getStatus());
        pricingStrategy.setValidDate(dto.getValidDate());
        pricingStrategy.setPriceStrategySeq(dto.getPriceStrategySeq());
        return pricingStrategy;
    }
    @SuppressWarnings("unchecked")
    public static List < PricingStrategyDTO > convertToPricingStrategyDTOList(
            final List < PricingStrategy > args) {
      if (args == null) {
          return null;
      }
      Iterator ite = args.iterator();
      List < PricingStrategyDTO > result = new ArrayList(args.size());
      while (ite.hasNext()) {
          result.add(PricingStrategyDTOUtil
                    .convertToPricingStrategyDTO((PricingStrategy) ite
                    .next()));
      }
      return result;
    }
}

