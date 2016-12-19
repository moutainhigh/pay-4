package com.pay.pricingstrategy.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.pricingstrategy.model.PricingStrategyDetail;



public class PricingStrategyDetailDTOUtil {
    public static PricingStrategyDetail convertToPriceStrategyDetail(
            final PricingStrategyDetailDTO dto) {
        PricingStrategyDetail detail = new PricingStrategyDetail();
        detail.setChargeRate(dto.getChargeRate());
        detail.setEffectiveFrom(dto.getEffectiveFrom());
        detail.setEffectiveTo(dto.getEffectiveTo());
        detail.setFixedCharge(dto.getFixedCharge());
        detail.setMaxCharge(dto.getMaxCharge());
        detail.setMinCharge(dto.getMinCharge());
        detail.setPriceStrategyDetailCode(dto.getPriceStrategyDetailCode());
//        detail.setPricingStrategy(PricingStrategyDTOUtil
//            .convertToPricingStrategy(dto.getPricingStrategyDto()));
        detail.setPriceStrategyCode(dto.getPriceStrategyCode());
        detail.setRangBy(dto.getRangBy());
        detail.setRangFrom(dto.getRangFrom());
        detail.setRangTo(dto.getRangTo());
        detail.setTerminalTypeCode(dto.getTerminalTypeCode());
        detail.setReservedName(dto.getReservedName());
        detail.setReservedCode(dto.getReservedCode());
        return detail;
    }
    public static PricingStrategyDetailDTO convertToPriceStrategyDetailDTO(
            final PricingStrategyDetail detail){
        if (detail == null) {
            return null;
        }
        PricingStrategyDetailDTO dto = new PricingStrategyDetailDTO();
        dto.setChargeRate(detail.getChargeRate());
        dto.setEffectiveFrom(detail.getEffectiveFrom());
        dto.setEffectiveTo(detail.getEffectiveTo());
        dto.setFixedCharge(detail.getFixedCharge());
        dto.setMaxCharge(detail.getMaxCharge());
        dto.setMinCharge(detail.getMinCharge());
        dto.setPriceStrategyDetailCode(detail.getPriceStrategyDetailCode());
//        dto.setPricingStrategyDto(PricingStrategyDTOUtil
//            .convertToPricingStrategyDTO(detail.getPricingStrategy()));
        dto.setPriceStrategyCode(detail.getPriceStrategyCode());
        dto.setRangBy(detail.getRangBy());
        dto.setRangFrom(detail.getRangFrom());
        dto.setRangTo(detail.getRangTo());
        dto.setTerminalTypeCode(detail.getTerminalTypeCode());
        dto.setReservedCode(detail.getReservedCode());
        dto.setReservedName(detail.getReservedName());
        return dto;
    }
    public static List < PricingStrategyDetailDTO > convertToPricingDetailList ( 
        final List < PricingStrategyDetail > detailList) {
        Assert.notNull(detailList,
            "List < PricingStrategyDetail > must be not null");
        List < PricingStrategyDetailDTO > resultList =
            new ArrayList < PricingStrategyDetailDTO > (detailList.size());
        for (PricingStrategyDetail detail : detailList) {
            resultList.add(PricingStrategyDetailDTOUtil
                .convertToPriceStrategyDetailDTO(detail));
        }
        return resultList;
    }
}
