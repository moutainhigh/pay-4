/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.suggest;

import com.pay.base.dto.SuggestDto;


public interface SuggestService {
    /**
     * 创建信息
     *
     * @param Suggest
     * @return
     */
    public Long createSuggest(SuggestDto suggestDto);
    
}
