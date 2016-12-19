/**
 *  <p>File: AbstractCheckFoFileService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.withdraw.service.reviewfofile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.service.reviewfofile.dto.ReviewFoFileDTO;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * <p></p>
 * @author zengli
 * @since 2011-4-13
 * @see 
 */
public abstract class AbstractReviewFoFileService implements ReviewFoFileService {

	@Override
	public Map<String, List<ReviewFoFileDTO>> compareFile(InputStream importStream,
			InputStream loadLocalStream) {
		//返回到页面的组装Map
		final Map<String,List<ReviewFoFileDTO>> hashMap = new HashMap<String, List<ReviewFoFileDTO>>();
		//用于组装导入文件与load本地文件的差集(即取出导入文件与load本地文件不相同的数据) 导入文件多的数据
		final List<ReviewFoFileDTO> importDisMatchList = new ArrayList<ReviewFoFileDTO>();
		//用于组装load本地文件与导入文件的差集(即取出load本地文件与导入文件不相同的数据) 本地文件多的数据
		final List<ReviewFoFileDTO> localDisMatchList = new ArrayList<ReviewFoFileDTO>();
		//用于组装两个文件的交集(即相同的数据)
		final List<ReviewFoFileDTO> retainList = new ArrayList<ReviewFoFileDTO>();
		//解析导入文件的数据
		final List<ReviewFoFileDTO> importList = this.parserImportFile(importStream);
		//解析加载本地文件的数据
		final List<ReviewFoFileDTO> loadLocalList = this.parserLoadLocalFile(loadLocalStream);
		
		importDisMatchList.addAll(importList);
		
		localDisMatchList.addAll(loadLocalList);
		
		retainList.addAll(importList);
		
		//获取第一个文件与第二个文件的差集
		importDisMatchList.removeAll(loadLocalList);
		
		hashMap.put("importDisMatchList", importDisMatchList);
		//获取第二个文件与第一个文件的差集
		localDisMatchList.removeAll(importList);
		
		hashMap.put("localDisMatchList", localDisMatchList);
		
		//获取第一个文件与第二个文件的交集
		retainList.retainAll(loadLocalList);
		hashMap.put("retainList", retainList);
	
		LogUtil.info(this.getClass(), "复核文件匹配", OPSTATUS.SUCCESS, "", "");
		
		
		return hashMap;
	}

	/**
	 * 获取导入文件解析数据
	 * @param inputStream
	 * @return
	 */
	protected abstract List<ReviewFoFileDTO> parserImportFile(InputStream inputStream);
	/**
	 * 获取加载本地文件解析数据
	 * @param inputStream
	 * @return
	 */
	protected abstract List<ReviewFoFileDTO> parserLoadLocalFile(InputStream inputStream);
	
	
	
	
}
