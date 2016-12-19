package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.pe.model.PaymentSrvPkgAssignment;


public class PaymentServicePkgADTOUtil {
    public static PaymentServicePkgAssignmentDTO convertToPkgADTO(
        final PaymentSrvPkgAssignment pkgA) {
        PaymentServicePkgAssignmentDTO dto =
            new PaymentServicePkgAssignmentDTO();
        dto.setPaymentService(pkgA.getPaymentServiceCode());
        dto.setPaymentServicePKG(pkgA.getPaymentServicePkgCode());
        return dto;
    }
    public static PaymentSrvPkgAssignment convertToPkg(
        final PaymentServicePkgAssignmentDTO dto) {
        PaymentSrvPkgAssignment pkgA = new PaymentSrvPkgAssignment();
        BeanUtils.copyProperties(dto , pkgA);
        return pkgA;
    }
    public static List < PaymentServicePkgAssignmentDTO > convertToPkgADTOList(
        final List < PaymentSrvPkgAssignment > pkgAList) {
        List < PaymentServicePkgAssignmentDTO > resultList =
            new ArrayList < PaymentServicePkgAssignmentDTO >(pkgAList.size());
        for (Iterator ite = pkgAList.iterator(); ite.hasNext();) {
            resultList.add(PaymentServicePkgADTOUtil
                .convertToPkgADTO((PaymentSrvPkgAssignment) ite.next()));
        }
//        for (PaymentSrvPkgAssignment pkgA : pkgAList) {
//            resultList.add(PaymentServicePkgADTOUtil.convertToPkgADTO(pkgA));
//        }
        return resultList;
    }


//    public static PaymentServicePkgAssignmentDTO convertToPkgADTO(
//        final PaymentSrvPkgAssignment pkgA,
//        final PaymentServicePKGDTO paymentServicePKGDTO) {
//        PaymentServicePkgAssignmentDTO dto =
//            new PaymentServicePkgAssignmentDTO();
//        dto.setPaymentServiceDto(PaymentServiceDTOUtil
//            .convertPaymentServiceDTO(pkgA.getPaymentService()));
//        dto.setPaymentServicePkgDto(paymentServicePKGDTO);
//        return dto;
//    }
//
//    public static List < PaymentServicePkgAssignmentDTO > convertToPkgADTOList(
//        final List < PaymentSrvPkgAssignment > pkgAList,
//        final PaymentServicePKGDTO paymentServicePKGDTO) {
//        List < PaymentServicePkgAssignmentDTO > resultList =
//            new ArrayList < PaymentServicePkgAssignmentDTO > (pkgAList.size());
//        for (PaymentSrvPkgAssignment pkgA : pkgAList) {
//            resultList.add(PaymentServicePkgADTOUtil.convertToPkgADTO(pkgA,
//                paymentServicePKGDTO));
//        }
//        return resultList;
//    }
}
