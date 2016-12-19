package com.pay.gateway.service;

import com.pay.gateway.dto.ValidateResponse;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public interface BuilderPayGateService {
    ValidateResponse validation(Object obj);
}
