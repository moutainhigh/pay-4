package com.pay.pe.dao;

import java.util.Map;

/**
 * @author limeng
 * @Date 2011-12-22
 * @Description 渠道记账科目映射
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public interface ChannelBankSubjectMappingDAO {

	public Map<String, String> queryChannelBankSubjectByType(int type);
}
