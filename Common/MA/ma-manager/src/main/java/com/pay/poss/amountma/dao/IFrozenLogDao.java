/**
 * 
 */
package com.pay.poss.amountma.dao;

import java.math.BigDecimal;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.amountma.dto.FrozenLogDto;

/**
 * @Description
 * @project ma-manager
 * @file IFrozenLogDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2011-5-2 DDR Create
 */
public interface IFrozenLogDao extends BaseDAO<FrozenLogDto> {
	/**
	 * 分页查询用户的冻结信息
	 * 
	 * @param page
	 * @param frozeLog
	 * @return Page对象
	 */
	Page<FrozenLogDto> findPage(Page<FrozenLogDto> page, FrozenLogDto frozeLog);

	/**
	 * 冻结用户的资金
	 * 
	 * @param memberCode
	 * @param frozenAmount
	 * @return 1 成功，0为失败
	 */
	public int addFrozenAmount(Long memberCode, BigDecimal frozenAmount,String acctcode);

	/**
	 * 解冻结用户的资金
	 * 
	 * @param memberCode
	 * @param frozenAmount
	 * @return 1 成功，0为失败
	 */
	public int freeFrozenAmount(Long memberCode, BigDecimal frozenAmount,String acctCode);

	/**
	 * 更新冻结状态
	 * 
	 * @param id
	 * @param status
	 * @return 1表示成功
	 */
	public int updateFrozenLogStatus(Long id, Integer status);

}
