/**
 * 
 */
package com.pay.base.dao.suggest;

import com.pay.base.model.Suggest;



/**
 * @author Jeffrey_teng
 * @version
 * @data 2010-11-10
 */

public interface SuggestDAO {

	/**
	 * 添加一条 信息
	 * @param Suggest
	 * @return int
	 */
	public Long createSuggest(Suggest suggest);
	
}
