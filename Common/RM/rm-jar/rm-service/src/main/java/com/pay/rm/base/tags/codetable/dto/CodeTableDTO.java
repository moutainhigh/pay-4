/**
 * BocpCodeTableDefinitionDTO
 * Description: 基础代码表基类。
 * @author Henry_Zeng
 */
package com.pay.rm.base.tags.codetable.dto;

import com.pay.inf.model.BaseObject;

/**
 * 基础代码表基类。
 *
 */
public class CodeTableDTO extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 代码ID
	 */
	private String codeId;

	/**
	 * 代码描述
	 */
	private String description;

	/**
	 * 获取代码ID
	 * @return codeId
	 */
	public String getCodeId() {
		return codeId;
	}
	
	/**
	 * 设置代码ID
	 * @param codeId
	 */	
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	
	/**
	 * 获取代码描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置代码描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "<BocpCodeTableDTO>codeId:" + codeId + ",description:"
				+ description;
	}
}
