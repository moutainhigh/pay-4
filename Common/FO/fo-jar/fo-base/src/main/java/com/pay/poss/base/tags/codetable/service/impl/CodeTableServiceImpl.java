 /** @Description 
 * @project 	poss-base
 * @file 		CodeTableServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
*/
package com.pay.poss.base.tags.codetable.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.tags.codetable.dao.CodeTableDAO;
import com.pay.poss.base.tags.codetable.dto.CodeTableDTO;
import com.pay.poss.base.tags.codetable.dto.CodeTableDefinitionDTO;
import com.pay.poss.base.tags.codetable.service.CodeTableService;

/**
 * <p>获取基础数据表的Service</p>
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see 
 */
public class CodeTableServiceImpl implements CodeTableService {
	
	protected transient  Log logger = LogFactory.getLog(getClass());
	/**
	 * 基础表配置定义Map
	 */
	private transient Map<String,Object> defininitionMap;
	
	public void setDefininitionMap(final Map<String, Object> defininitionMap) {
		this.defininitionMap = defininitionMap;
	}
	
	private transient CodeTableDAO codeTableDAO ;
		
	public void setCodeTableDAO(final CodeTableDAO codeTableDAO) {
		this.codeTableDAO = codeTableDAO;
	}
	
	
	@Override
	public List<CodeTableDTO> getCodeTables(String codeTableId) throws PossUntxException {
		try {
			/**
			 * TODO 读取缓存暂时未实现。现在默认全部从数据库取数据
			 * */
			List<CodeTableDTO> codeTables = null;
			CodeTableDefinitionDTO definition = getCodeTableDefinitionDTO(codeTableId);

			if (definition == null) {
				throw new PossUntxException("获取基础表异常: CodeTable"+codeTableId+"没有在配置文件定义",ExceptionCodeEnum.ILLEGAL_PARAMETER);
			}
			/** 1.TODO从缓存中取 暂未实现
			 * if (definition.isNeedCache()) {
				codeTables = (List) pool.get(codeTableId);
			}*/
			if (codeTables == null) {
				codeTables = codeTableDAO.getCodeTables(definition);
				/**TODO 加入到缓存
				 * if (definition.isNeedCache()) {
					pool.put(codeTableId, codeTables);
				}*/
			} else {
				throw new PossUntxException(this.getClass().getName()+".getCodeTables 加载基础数据表失败", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			}
			return codeTables;

		} catch (PossUntxException e) {
			throw e;
		} catch (Exception e) {
			throw new PossUntxException("getCodeTableError 获取基础表异常:CodeTableServiceImpl清楚cache异常", ExceptionCodeEnum.ILLEGAL_PARAMETER,e);
		}
	}

	@Override
	public CodeTableDTO getCodeTable(String codeTableId, String selectedValue) throws PossUntxException {
		try {
			List<CodeTableDTO> codeTables = this.getCodeTables(codeTableId);
			CodeTableDTO tempDto = null;
			for (CodeTableDTO codeTableDTO : codeTables) {
				if (codeTableDTO != null && codeTableDTO.getCodeId().equals(selectedValue)) {
					tempDto = codeTableDTO;
					break;
				}
			}
			return tempDto;
		} catch (Exception e) {
			throw new PossUntxException("getCodeTableError 获取基础表异常:CodeTableServiceImpl清楚cache异常", ExceptionCodeEnum.ILLEGAL_PARAMETER,e);
		}
	}
	
	/**
	 * 根据基础代码表ID清空缓存
	 * 
	 * @param codeTableId  基础代码表ID
	 * @throws PossUntxException 
	 */
	public void clearCodeTableCache(String codeTableId) throws PossUntxException {
		//TODO 暂时未提供
		/*try {
			CodeTableDefinitionDTO definition = getCodeTableDefinitionDTO(codeTableId);
			if (definition == null) {
				throw new PossUntxException("获取基础表异常: CodeTable"+codeTableId+"没有在配置文件定义",ExceptionCodeEnum.ILLEGAL_PARAMETER); 
			}
			if (definition.isNeedCache()) {
				
			}
		} catch (PossUntxException e) {
			throw e;
		} catch (Exception e) {
			throw new PossUntxException("clearCodeTableCacheError 获取基础表异常:CodeTableServiceImpl清楚cache异常", ExceptionCodeEnum.ILLEGAL_PARAMETER,e);
		}*/

	}
	
	
	/**
	 * 根据基础代码表ID返回基础表配置定义Map到CodeTableDefinitionDTO
	 * @param codeTableId
	 * @return CodeTableDefinitionDTO
	 */
	private CodeTableDefinitionDTO getCodeTableDefinitionDTO(
			String codeTableId) {
		return (CodeTableDefinitionDTO) this.defininitionMap
				.get(codeTableId);
	}
	
}
