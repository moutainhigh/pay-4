package com.pay.order.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.pay.jms.notification.request.NotifyRequest;
import com.pay.order.dao.MerchantWebsiteCheckDao;
import com.pay.order.service.MerchantWebsiteCheckService;
import com.pay.util.JSonUtil;
/****
 * 
 * 	系统审核域名
 * @author delin.dong
 * @date 2016年6月15日 16:20:50
 */
public class MerchantWebsiteCheckServiceImpl implements MerchantWebsiteCheckService{

	private MerchantWebsiteCheckDao merchantWebsiteCheckDao;
	
	@Override
	public boolean process(NotifyRequest notifyRequest) {
		System.out.println("域名系统审核，接收到发送的MQ。。。。。。。"+notifyRequest.getData());
		Map<String, String> requestData = notifyRequest.getData();
		Random random = new Random();
		String batch=requestData.get("batch");
		if(batch.equals("Y")){ //判断状态是否批次上传的域名
			int sleepStart=120; //2 - 7分钟
			int sleepEnd=420; 
			int a = random.nextInt(sleepEnd)%(sleepEnd-sleepStart+sleepStart) + sleepStart;  //随机暂停秒数
			try {
				Thread.sleep(a*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}  
			String list = requestData.get("list");
			List<Map> paraMap = JSonUtil.toObject(list, new ArrayList<Map>().getClass());
			for (Map map : paraMap) {
							//  修改域名状态为 系统审核通过
							//	5：系统审核未通过
							//	6：系统审核通过
				map.put("status", "6");
				Boolean f=merchantWebsiteCheckDao.updateWebsiteStatus(map);
			}
		}else{
				int sleepStart=120; //2 - 7分钟
				int sleepEnd=420; 
				int a = random.nextInt(sleepEnd)%(sleepEnd-sleepStart+sleepStart) + sleepStart;  //随机暂停秒数
				try {
					Thread.sleep(a*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}  
					//  修改域名状态为 系统审核通过
				requestData.put("status", "6");
				Boolean f	=merchantWebsiteCheckDao.updateWebsiteStatus(requestData);
				if(!f){
					return false;
				}
		}
		return true;
	}
	
	public void setMerchantWebsiteCheckDao(
			MerchantWebsiteCheckDao merchantWebsiteCheckDao) {
		this.merchantWebsiteCheckDao = merchantWebsiteCheckDao;
	}
	
}
