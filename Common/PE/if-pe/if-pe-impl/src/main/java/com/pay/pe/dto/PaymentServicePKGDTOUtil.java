package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.pe.model.PaymentServicePKG;



public class PaymentServicePKGDTOUtil {
   public static PaymentServicePKG convertToPaymentSerPKG(PaymentServicePKGDTO dto) {
       PaymentServicePKG paymentServicePKG = new PaymentServicePKG();
       BeanUtils.copyProperties(dto , paymentServicePKG);
       return paymentServicePKG;
   }
   public static PaymentServicePKGDTO convertToPaymentServicePKGDTO(PaymentServicePKG pkg) {
       if (pkg == null) {
           return null;
       }
       PaymentServicePKGDTO paymentServicePKGDTO = new PaymentServicePKGDTO();
       BeanUtils.copyProperties(pkg , paymentServicePKGDTO);
       paymentServicePKGDTO.setPkgAssignDTOList(PaymentServicePkgADTOUtil.convertToPkgADTOList(
               (List)pkg.getPaymentSrvPkgAssignList()));
       return paymentServicePKGDTO;
   }
   
public static List < PaymentServicePKGDTO > ConvertToPaymentSerPKGList(
            final List< PaymentServicePKG > pkg) {
       Assert.notNull(pkg , "List < PaymentServicePKG > must be not null");
       Iterator ite = pkg.iterator();
       List result = new ArrayList(pkg.size());
       while (ite.hasNext()) {
           result.add(PaymentServicePKGDTOUtil.convertToPaymentServicePKGDTO((PaymentServicePKG) ite.next()));
       }
       return result;
   }
}
