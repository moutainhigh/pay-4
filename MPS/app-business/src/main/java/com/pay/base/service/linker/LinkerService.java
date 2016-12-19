/**
 *  File: LinkerService.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.linker;

import java.util.List;

import com.pay.base.dto.LinkerDTO;
import com.pay.inf.service.BaseService;

/**
 * 
 */
public interface LinkerService extends BaseService{

	/**
	 * 分布查询联系人
	 * 
	 * @param linkerDto
	 * @return
	 */
	List<LinkerDTO> queryLinkByPage(LinkerDTO linkerDto);

	/**
	 * 查询联系人总数
	 * 
	 * @param linkerDto
	 * @return
	 */
	Integer queryLinkByCount(LinkerDTO linkerDto);

	/**
	 * 根据memberCode查询联系人
	 * 
	 * @param memberCode
	 * @return
	 */
	List<LinkerDTO> queryLinkerListBymemberCode(Long memberCode);

	/**
	 * 验证联系人是否重复()
	 * 
	 * @param linkerDto
	 *            (memberCode,linkername)
	 * @return false-联系人重复
	 */
	boolean verifylinkerrepeat(LinkerDTO linkerDto);

}
