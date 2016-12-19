/**
 * 
 */
package com.pay.batchpay.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pay.inf.dao.Page;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * @author PengJiangbo
 *
 */
public class PageParaDataUtil {
	/**
	 * 分页页面内容
	 * @param page
	 * @param bulkOrderResult
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<?> pageUtilData(Page page,
			List<?> lists) {
		Integer toNO = page.getPageNo() ;
		if( toNO == 1){
			if(lists.size()>page.getPageSize()){
				lists = lists.subList(0, page.getPageSize()) ;
			}
		}
		if( toNO > 1){
			Integer preRecordSize = (toNO-1) * page.getPageSize() ;
			Integer toIndex = (preRecordSize + page.getPageSize()) <= lists.size() ? (preRecordSize + page.getPageSize()) : lists.size() ;
			//lists = lists.subList(preRecordSize, lists.size()) ;
			lists = lists.subList(preRecordSize, toIndex) ;
		}
		return lists;
	}

	/**
	 * 分页对象page
	 * @param page
	 * @param bulkOrderResult
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Page pageUtilBean(Page page,
			List<?> lists) {
		Map paraMap = MapUtil.bean2map(null);
		//paraMap.put("page", page);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		int totalCount = lists.size();
		page.setTotalCount(totalCount);
		paraMap.put("page", page) ;
		reqMsg = JSonUtil.toJSonString(paraMap) ;
		
		Map resultMap = JSonUtil.toObject(reqMsg, new HashMap().getClass());
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return page;
	}

	/**
	 * 分页页面参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Page pagePara(HttpServletRequest request) {
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 15; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 15;// 此处为避免分页计算时出现被0除的情况
		}

		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		return page;
	}
}
