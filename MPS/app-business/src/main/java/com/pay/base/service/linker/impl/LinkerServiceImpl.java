/**
 *  File: LinkerServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.linker.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.base.dao.linker.LinkerDAO;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.model.Linker;
import com.pay.base.service.linker.LinkerService;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class LinkerServiceImpl extends BaseServiceImpl implements LinkerService {

	@SuppressWarnings("unchecked")
	public Class getDtoClass() {
		return LinkerDTO.class;
	}

	public List<LinkerDTO> queryLinkByPage(LinkerDTO linkerDto) {
		List<Linker> linkerList = ((LinkerDAO) mainDao)
				.queryLinkByPage(linkerDto);
		List<LinkerDTO> linkerDtoList = new ArrayList<LinkerDTO>(
				linkerList.size());
		if (linkerList.size() > 0) {
			for (Linker linker : linkerList) {
				linkerDtoList.add(BeanConvertUtil.convert(LinkerDTO.class,
						linker));
			}
		}
		return linkerDtoList;
	}

	public Integer queryLinkByCount(LinkerDTO linkerDto) {
		return ((LinkerDAO) mainDao).queryLinkByCount(linkerDto);
	}

	@Override
	public List<LinkerDTO> queryLinkerListBymemberCode(Long memberCode) {
		List<Linker> linkerList = ((LinkerDAO) mainDao)
				.queryLinkerByMemberCode(memberCode);
		List<LinkerDTO> linkerDtoList = new ArrayList<LinkerDTO>(
				linkerList.size());
		if (linkerList.size() > 0) {
			for (Linker linker : linkerList) {
				linkerDtoList.add(BeanConvertUtil.convert(LinkerDTO.class,
						linker));
			}
		}
		return linkerDtoList;
	}

	@Override
	public boolean verifylinkerrepeat(LinkerDTO linkerDTO) {
		List<Linker> linkerList = ((LinkerDAO) mainDao)
				.verifylinkerrepeat(linkerDTO);
		List<LinkerDTO> linkerDtoList = new ArrayList<LinkerDTO>(
				linkerList.size());
		if (linkerList.size() > 0) {
			for (Linker linker : linkerList) {
				linkerDtoList.add(BeanConvertUtil.convert(LinkerDTO.class,
						linker));
			}
		}
		return (!(linkerDtoList.size() > 0));

	}

	@Override
	protected Class getModelClass() {
		return Linker.class;
	}
}
