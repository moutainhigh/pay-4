package com.pay.poss.memberrelation.service;

import java.io.IOException;

/**   
* @Title: RelationDataReader.java 
* @Package com.pay.poss.memberrelation.service 
* @Description: 关联名单excel数据读取器
* @author cf
* @date 2011-9-22 下午01:32:00 
* @version V1.0   
*/

public interface RelationDataReader {
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
	 * 取得门店编号
	 */
	String getRelationStoreNumbers();
	
	/**
	 * @return
	 * 取得门店地址
	 */
	String getRelationStoreAddress();
	
	/**
	 * @return
	 * 取得店主姓名
	 */
	String getRelationStoreName();
	
	/**
	 * @return
	 * 手机号码
	 */
	String getRelationMobileNo();
	
	/**
	 * @return
	 * 取得账户（Email）
	 */
	String getAmountEmail();
	
	
	/**
	 * @return
	 * 总店会员号
	 */
	String getFatherMemberCode();
}
