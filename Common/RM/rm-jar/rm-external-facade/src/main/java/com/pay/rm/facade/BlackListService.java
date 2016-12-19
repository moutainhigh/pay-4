/**
 * 
 */
package com.pay.rm.facade;

/**
 * @author chaoyue
 *
 */
public interface BlackListService {

	/**
	 * 黑名单校验
	 * 
	 * @param content
	 * @return
	 */
	String blackListCheck(String content);
}
