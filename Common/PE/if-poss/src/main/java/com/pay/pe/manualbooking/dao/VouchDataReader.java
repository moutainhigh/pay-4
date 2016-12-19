package com.pay.pe.manualbooking.dao;

import java.io.IOException;

/**
 * 手工记账数据读取器
 */
public interface VouchDataReader {
	/**
	 * @throws IOException
	 * 读入数据
	 */
	void read() throws IOException;
	
	/**
	 * @return
	 * 是否有下一条明细
	 */
	boolean next();
	
	/**
	 * @throws IOException
	 * 关闭读取
	 */
	void close() throws IOException;
	
	/**
	 * @return
	 * 是否是第一条明细
	 */
	boolean isFirst();
	
	/**
	 * @return
	 * 是否是最后一条明细
	 */
	boolean isLast();
	
	/**
	 * @return
	 * 取得明细备注
	 */
	String getVouchDetailRemark();
	
	/**
	 * @return
	 * 取得明细账号
	 */
	String getAccountCode();
	
	/**
	 * @return
	 * 取得明细账号名称
	 */
	String getAccountName();
	
	/**
	 * @return
	 * 取得借款额
	 */
	String getCrAmount();
	
	/**
	 * @return
	 * 取得贷款额
	 */
	String getDrAmount();
}
