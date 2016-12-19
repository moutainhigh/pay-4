package com.pay.txncore.service;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

/**
 * Created by cuber.huang on 2016/7/22.
 */
public interface AllChannelPayService {
    PaymentResult dispatchPay(PaymentInfo info) throws Exception;
}
