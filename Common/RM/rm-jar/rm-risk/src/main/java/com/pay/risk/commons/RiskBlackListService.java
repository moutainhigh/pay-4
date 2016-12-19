package com.pay.risk.commons;

import java.util.ArrayList;
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
public class RiskBlackListService {
	private static Logger logger = LoggerFactory.getLogger(RiskBlackListService.class);
	
	private BlackWhiteListService blackWhiteListService;
	private static final int BLACK_LIST_TYPE = 2;
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
	public  void refresh(){
		FLG=1;
		logger.info("RiskBlackList refresh ...FLG:"+FLG);
		logger.info("listMap.size: "+listMap.size());
		if(!listMap.isEmpty()){
			listMap.clear();
		}
		if(listMap.isEmpty()){
			logger.info("all black list count: "+ALLCOUNT);
			int tmpCount = ALLCOUNT;
			List<BlackWhiteListDto> list = blackWhiteListService.queryAllBlackWhiteList();
			if(list!=null&&list.size()>0){
				ALLCOUNT = list.size();
				List<BlackWhiteListDto> tmpList= null;
				for(BlackWhiteListDto dto:list){
					//tmpList = new ArrayList<BlackWhiteListDto>();
					String key = dto.getBusinessTypeId().intValue()+"-"+BLACK_LIST_TYPE;
					
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
		}
		FLG=0;
		logger.info("RiskBlackList refresh completed! FLG:"+FLG+",siez: "+listMap.size());
	}

	//仅做参考，原来的代码，应该也没啥问题 . 	
	//可能在中间刷新的过程中，比如需要1秒钟，用一个临时变量  这一秒内的收单，也可以使用老的List值. 
	//如果 不是 List<BlackWhiteListDto，而是map<string,BlackWhiteListDto> 这样的代码，建议使用refresh2,可能更加安全一些。
	//因为此处在修改 listmap 中的对象，外面有使用者在使用这个 对象.  该对象未必是线程安全的。 
	/*
	public  void refresh2(){
		FLG=1;
		logger.info("RiskBlackList refresh ...FLG:"+FLG);
		logger.info("listMap.size: "+listMap.size());

		//用一个临时变量
		 Map<String,List<BlackWhiteListDto>> listMap2 = 
						        new ConcurrentHashMap<String,List<BlackWhiteListDto>>();


		logger.info("all black list count: "+ALLCOUNT);
		int tmpCount = ALLCOUNT;
		List<BlackWhiteListDto> list = blackWhiteListService.queryAllBlackWhiteList();
		if(list!=null&&list.size()>0){
			ALLCOUNT = list.size();
			List<BlackWhiteListDto> tmpList= null;
			for(BlackWhiteListDto dto:list){					
					//tmpList = new ArrayList<BlackWhiteListDto>();  //这里没有必要 
					String key = dto.getBusinessTypeId().intValue()+"-"+BLACK_LIST_TYPE;
					
					if(listMap2.containsKey(key)){
						List<BlackWhiteListDto> tmp = listMap.get(key);
						tmp.add(dto);
						listMap2.put(key, tmp);
					}else{
						tmpList = new ArrayList<BlackWhiteListDto>();		
						tmpList.add(dto);
						listMap2.put(key, tmpList);
					}
			}
			
			
			logger.info("black list count "+ALLCOUNT+",added: "+(ALLCOUNT-tmpCount));
		}

		if(!listMap.isEmpty()){
			listMap.clear();
		}

		//listMap2==>list
		for (Map.Entry<String, List<BlackWhiteListDto>> entry : listmap2.entrySet()) {  
			listMap.set(entry.getKey(),entry.getValue());  			
		}
		listmap2.clear();

		FLG=0;
		logger.info("RiskBlackList refresh completed! FLG:"+FLG+",siez: "+listMap.size());
	}
	*/

	/**
	 * 初始化
	 */
	public void init(){
		this.refresh();
	}
}
