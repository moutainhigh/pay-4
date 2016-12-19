package com.pay.risk.service;

import java.util.Map;

import com.pay.risk.dto.PaymentInfo;


public interface RiskValidateService {
      Map<String,String> validate(PaymentInfo paymentInfo);
}
