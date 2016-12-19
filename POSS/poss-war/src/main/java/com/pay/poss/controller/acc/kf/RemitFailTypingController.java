package com.pay.poss.controller.acc.kf;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.pay.execlUtil.PoiTemplateUtil;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.KfPayService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;

/**
 * 出款失败录入
 * @author delin
 * @date 2016年8月26日14:19:19
 */
public class RemitFailTypingController  extends MultiActionController{

		private String index;
		
		private String list;

		private KfPayService kfPayService;

		private MyOSS myoss;
		
		public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		   return new ModelAndView(index);
		}
		
		public ModelAndView list(HttpServletRequest request, HttpServletResponse response ){
			String orderId=request.getParameter("orderId");
			String batchNo=request.getParameter("batchNo");
			String type=request.getParameter("type");
			String status=request.getParameter("status");
			String beginCreateDate=request.getParameter("beginCreateDate");
			String endCreateDate=request.getParameter("endCreateDate");
			Page<Object> page = PageUtils.getPage(request);
			Map<String, Object> paraMap=new HashMap<String, Object>();
			Map<String, Object> crossOrderMap=new HashMap<String, Object>();
			crossOrderMap.put("orderId",  orderId);
			crossOrderMap.put("batchNo", batchNo);
			crossOrderMap.put("type", type);
			crossOrderMap.put("beginCreateDate", beginCreateDate);
			crossOrderMap.put("endCreateDate", endCreateDate);
			crossOrderMap.put("outStatus", "'0','1','2','3','4'");
			if(StringUtils.isNotEmpty(status)){
				crossOrderMap.put("outStatus", status);
			}
			paraMap.put("crossOrder", crossOrderMap);
			paraMap.put("page", page);
			Map<String,Object>  resultMap=kfPayService.FindRemitFailTyping(paraMap);
			List<Map<String,Object>> resultList=(List<Map<String,Object>>)resultMap.get("list");
			for (Map<String, Object> map : resultList) {
				   Long comDate= (Long) map.get("createDate");
				   Long completeDate= (Long) map.get("completeDate");
				   Date date = new Date(comDate);
			   	   SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				   map.put("createDate", sdf.format(date));
				   if(completeDate!=null){
					   map.put("completeDate", sdf.format(completeDate));
				   }
				   BigDecimal bigDecimal = new  BigDecimal(1000);
				   map.put("remitAmount",new BigDecimal(map.get("remitAmount").toString()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).toString());
				   map.put("payAmount", new BigDecimal(map.get("payAmount").toString()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).toString());
				   map.put("remitExpense", new BigDecimal(map.get("remitExpense").toString()).divide(bigDecimal,2,BigDecimal.ROUND_HALF_UP).toString());
			}
			Map pageMap = (Map) resultMap.get("page");
			page = MapUtil.map2Object(Page.class, pageMap);	
			return new ModelAndView(list).addObject("resultList", resultList).addObject("page", page);
		}
		
		/**
		 *  批量审核
		 * @param request
		 * @param response
		 * @return
		 */
		public ModelAndView bacthReviewed(HttpServletRequest request ,HttpServletResponse response){
			ModelAndView indexView=null;
			try {
				indexView = new ModelAndView(index);
				String re=request.getParameter("re");
				String[] split = re.split("#");// [0] detailNo [1] outStatus [2] batchNo [3] orderId
				Map<String,List<String>> para=new HashMap<String, List<String>>();
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split(";");
					List<String> list=new ArrayList<String>();
					para.put(split2[2], list);
					if(para.containsKey(split2[2])){
						para.get(split2[2]).add(split2[3]);
					}else{
						para.put(split2[2], list);
					}
				}
				String detailNos=request.getParameter("detailNos");
				String batchNos=request.getParameter("batchNos");
				String status=request.getParameter("status");
				String statusDesc=null;
				if(status.equals("0")){
					statusDesc = "待审核";
				}else if(status.equals("1")){
					statusDesc = "审核通过";
				}else if(status.equals("2")){
					statusDesc = "审核拒绝";
				}else if(status.equals("3")){
					statusDesc = "出款成功";
				}else if(status.equals("4")){
					statusDesc = "出款失败";
				}
				String remark=request.getParameter("remark")==null?"":request.getParameter("remark");
				Map<String,String> paraMap=new HashMap<String, String>();
				paraMap.put("detailNos", detailNos);
				paraMap.put("status", status);
				paraMap.put("operator",SessionUserHolderUtil.getLoginId());
				
				if(StringUtils.isNotEmpty(remark)){
					try {
						remark= java.net.URLDecoder.decode(remark, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					paraMap.put("remark", remark);
				}
				batchNos=batchNos.replace("‘","'");
				Set<Entry<String,List<String>>> entrySet = para.entrySet();
				Iterator<Entry<String, List<String>>> iterator = entrySet.iterator();
				Map<String,Object>  resourceFileUrl=kfPayService.queryResourceFileUrl(batchNos);
				List<Map> KfPayResourceList = (List<Map>) resourceFileUrl.get("result");
				for (Entry<String, List<String>> entry : entrySet) {
						String key = entry.getKey();
						String filePath = null;
						if(KfPayResourceList.size()>0){
							for (Map map : KfPayResourceList) {
								if(key.equals(map.get("batchNo"))){
									filePath =String.valueOf(map.get("filePath"));
								}
							}
						}else{
							indexView.addObject("error", "未找到对应的资源文件！！！");
							return indexView;
						}
						List<String> value = entry.getValue();
						for (String orderId: value) {
							JSONObject init = myoss.init("/CrossBorerPay");
							OSSClient ossClient = myoss.getOSSClient();
							OSSObject object = ossClient.getObject(init.getString("bucket"), filePath);
							InputStream updateExcel = PoiTemplateUtil.updateExcel(object.getObjectContent(), orderId, statusDesc, remark);
							PutObjectResult putObject = ossClient.putObject(init.getString("bucket"), filePath, updateExcel);
						}
				}
				Map<String,Object> resultMap=kfPayService.bacthReviewed(paraMap);
				String responseCode=String.valueOf(resultMap.get("responseCode"));
				if(responseCode.equals("0000")){
					indexView.addObject("error", "操作成功！！！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return indexView;
		}
		
		public void setKfPayService(KfPayService kfPayService) {
			this.kfPayService = kfPayService;
		}
		
		public void setMyoss(MyOSS myoss) {
			this.myoss = myoss;
		}

		public void setIndex(String index) {
			this.index = index;
		}

		public void setList(String list) {
			this.list = list;
		}
}
