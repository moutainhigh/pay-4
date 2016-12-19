
package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.pay.pe.model.PaymentService;
import com.pay.pe.model.PostingRule;


public class PaymentServiceDTOUtil {
    public static PaymentService convertToPaymentService(PaymentServiceDTO dto) {
        if (dto == null)
            return null;
        PaymentService paymentService = new PaymentService();
        paymentService.setDescription(dto.getDescription());
        paymentService.setFixedPayee(dto.getFixedpayee());
        paymentService.setFixedPayer(dto.getFixedpayer());
        paymentService.setGetMethod(dto.getGetmethod());
        paymentService.setPayeeAcctTypeCode(dto.getPayeeAcctType());
        paymentService.setPayeeType(dto.getPayeetype());
        paymentService.setPayerAcctTypeCode(dto.getPayerAcctType());
        paymentService.setPayerType(dto.getPayertype());
        paymentService
                .setPaymentServiceCode(dto.getPaymentservicecode());
        paymentService
                .setPaymentServiceName(dto.getPaymentservicename());
        paymentService.setPayMethod(dto.getPaymethod());
        paymentService.setTakeon(dto.getTakeon());
        paymentService.setPaymentServiceType(dto.getPaymentServiceType());
        paymentService.setReservedCodeType(dto.getReservedCodeType());
        //add by scott.ling 
        paymentService.setDependOn(dto.getDependOn());
        
//        paymentService.setPostingRuleCollection(
//                PostingRuleDTOUtil.convertDtoToPostingRule(
//                        dto.getPostingRuleCollectionDTO(), paymentService));
        return paymentService;
    }
    public static PaymentServiceDTO convertPaymentServiceDTO(PaymentService service) {
        if (service == null)
            return null;
        PaymentServiceDTO paymentServiceDTO = new PaymentServiceDTO();
        paymentServiceDTO.setDescription(service.getDescription());
        paymentServiceDTO.setFixedpayee(service.getFixedPayee());
        paymentServiceDTO.setFixedpayer(service.getFixedPayer());
        paymentServiceDTO.setGetmethod(service.getGetMethod());
        paymentServiceDTO.setPayeeAcctType(service.getPayeeAcctTypeCode());
        paymentServiceDTO.setPayeetype(service.getPayeeType());
        paymentServiceDTO.setPayerAcctType(service.getPayerAcctTypeCode());
        paymentServiceDTO.setPayertype(service.getPayerType());
        paymentServiceDTO
                .setPaymentservicecode(service.getPaymentServiceCode());
        paymentServiceDTO
                .setPaymentservicename(service.getPaymentServiceName());
        paymentServiceDTO.setPaymethod(service.getPayMethod());
        paymentServiceDTO.setTakeon(service.getTakeon());
        paymentServiceDTO.setPaymentServiceType(service.getPaymentServiceType());
        paymentServiceDTO.setReservedCodeType(service.getReservedCodeType());
        
        //add  by scott.ling
        paymentServiceDTO.setDependOn(service.getDependOn());
        
        List postingRules = new ArrayList();
        Iterator it = service.getPostingRuleCollection().iterator();
        while (it.hasNext()) {
            postingRules.add((PostingRule)it.next());
        }
        paymentServiceDTO.setPostingRuleCollectionDTO(
                PostingRuleDTOUtil.convertPostingRuleToDto(
                        postingRules,
                        paymentServiceDTO));
        return paymentServiceDTO;
    }
    public static List < PaymentServiceDTO > ConvertToPaymentSerDTOList(
            final List< PaymentService > pkg) {
       if (pkg == null) 
           return null;
       Iterator ite = pkg.iterator();
       List result = new ArrayList(pkg.size());
       while (ite.hasNext()) {
           result.add(PaymentServiceDTOUtil.convertPaymentServiceDTO((PaymentService) ite.next()));
       }
       return result;
   }
}
