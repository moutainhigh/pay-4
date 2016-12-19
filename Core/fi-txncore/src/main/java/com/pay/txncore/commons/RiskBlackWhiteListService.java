package com.pay.txncore.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;


/**
 * 黑白名单service 为了可以动态更新
 * @author peiyu.yang
 *
 */
public class RiskBlackWhiteListService {
	private static Logger logger = LoggerFactory.getLogger(RiskBlackWhiteListService.class);
	private BlackWhiteListService blackWhiteListService;
	private volatile static int FLG=0;
	private static int ALLCOUNT=0;
	private static Map<String,List<BlackWhiteListDto>> listMap = 
			                      new ConcurrentHashMap<String,List<BlackWhiteListDto>>();
	public void setBlackWhiteListService(BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}
	
	/**
	 * 获取列表
	 * @param key
	 * @return
	 */
	public  List<BlackWhiteListDto> getBlackWhiteList(String key) {
		logger.info("FLG: "+FLG);
		if(listMap.isEmpty()){
			logger.info("BlackWhiteList is Empty,Refresh Now");
			if(FLG==0){
			  this.refresh();
			}
			logger.info("method: getBlackWhiteList,Refresh Completed!");
		}
		return listMap.get(key);
	}
	
	public boolean containKey(String key){
		return listMap.containsKey(key);
	}
	
	public List<BlackWhiteListDto> remove(String key){
		 return listMap.remove(key);
	}
	
	/**
	 * 刷新列表
	 */
	public  Map<String,String> refresh(){
		FLG=1;
		logger.info("RiskBlackList refresh ...FLG:"+FLG);
		logger.info("listMap.size: "+listMap.size());
		if(!listMap.isEmpty()){
			listMap.clear();
		}
		Map<String,String> map = new HashMap<String, String>();
		if(listMap.isEmpty()){
			logger.info("all black list count: "+ALLCOUNT);
			int tmpCount = ALLCOUNT;
			List<BlackWhiteListDto> list = blackWhiteListService.queryAllBlackWhiteList();
			if(list!=null&&list.size()>0){
				ALLCOUNT = list.size();
				List<BlackWhiteListDto> tmpList= null;
				for(BlackWhiteListDto dto:list){
					String key = dto.getBusinessTypeId().intValue()+"-"
				                     +dto.getListType().intValue();//这种方式支持黑名单和白名单  1:白名单，2：黑名单
					if(listMap.containsKey(key)){
						List<BlackWhiteListDto> tmp = listMap.get(key);
						tmp.add(dto);
						listMap.put(key, tmp);
					}else{
						tmpList = new ArrayList<BlackWhiteListDto>();  //modify by sch2016-04-25
						tmpList.add(dto);
						listMap.put(key, tmpList);
					}
				}
			}
			
			logger.info("black list count "+ALLCOUNT+",added: "+(ALLCOUNT-tmpCount));
			map.put("newAddAmount", ""+(ALLCOUNT-tmpCount));
			map.put("oldAmount",""+tmpCount);
			map.put("mowAmount",""+ALLCOUNT);
		}
		FLG=0;
		logger.info("RiskBlackList refresh completed! FLG:"+FLG+",siez: "+listMap.size());
		return map;
	}

	/**
	 * 初始化
	 */
	public void init(){
		this.refresh();
	}
}
