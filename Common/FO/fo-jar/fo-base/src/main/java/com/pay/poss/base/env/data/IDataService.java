package com.pay.poss.base.env.data;

import java.util.List;

import com.pay.poss.base.model.CodeMapping;

public interface IDataService {
	public final static String BEAN_ID = "POSS.DATASERVICE";

	public Object getData(String busiCode);

	/**
	 * 获取列表
	 * 
	 * @param family
	 *            族
	 * @param category
	 *            类别
	 * @return
	 */
	public List<CodeMapping> getCodeMapping(String family, String category);

	/**
	 * 获取指定编码的映射
	 * 
	 * @param family
	 *            族
	 * @param category
	 *            类别
	 * @param code
	 *            编码
	 * @return
	 */
	public CodeMapping getCodeMapping(String family, String category, String code);
}
