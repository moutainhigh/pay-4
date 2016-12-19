package com.pay.txncore.service.preauth;

import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;

/**
 * Created by cuber on 2016/9/1.
 */
public interface PreAuth2Service {

    PaymentResult preAuthApply(PaymentInfo paymentInfo);

    PaymentResult preAuthCapture(PaymentInfo paymentInfo);

    PaymentResult preAuthVoid(PaymentInfo paymentInfo);

    PaymentResult cashierPreAuthApply(PaymentInfo paymentInfo);
}
