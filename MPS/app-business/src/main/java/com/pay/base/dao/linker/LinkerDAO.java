/**
 *  File: LinkerDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.linker;

import java.util.List;

import com.pay.base.dto.LinkerDTO;
import com.pay.base.model.Linker;

/**
 * 
 */
public interface LinkerDAO {

	/**
	 * 分布查询联系人
	 * 
	 * @param linkerDto
	 * @return
	 */
	List<Linker> queryLinkByPage(LinkerDTO linkerDto);

	/**
	 * 获得联系人总数
	 * 
	 * @param linkerDto
	 * @return
	 */
	Integer queryLinkByCount(LinkerDTO linkerDto);

	/**
	 * 根据memberCode查询所有联系人
	 * 
	 * @return
	 */
	List<Linker> queryLinkerByMemberCode(Long memberCode);

	/**
	 * 验证添加的联系人是否重复
	 * 
	 * @param linkerName
	 * @return
	 */
	List<Linker> verifylinkerrepeat(LinkerDTO linkerDto);

}
