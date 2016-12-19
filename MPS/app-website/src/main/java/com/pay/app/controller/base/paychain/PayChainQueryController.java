package com.pay.app.controller.base.paychain;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.EffectiveTypeEnum;
import com.pay.base.dto.PayChainDetailDto;
import com.pay.base.dto.PayChainPaymentParamDto;
import com.pay.base.dto.PayChainStatDto;
import com.pay.base.dto.PayChainStatParamDto;
import com.pay.base.exception.ResultToMachException;
import com.pay.base.model.ContextPicture;
import com.pay.base.model.PayChain;
import com.pay.base.service.contextPic.ContextPicService;
import com.pay.base.service.paychain.PayChainService;
import com.pay.util.DateUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;


/**
 * 查询支付链明细列表
 * @author DDR
 */
@OperatorPermission(operatPermission = "OPERATOR-PAY-CHAIN")
public class PayChainQueryController  extends MultiActionController {
    
	private PayChainService  payChainService;

	private String queryStatView;
	private String queryStatExportErrorView;
	private String queryPayChainDetailView;
	private String exportPayChainStatPath;  //文件下载
	private String exportPayChainDetailPath;
	private ContextPicService  contextPicService;
	
	

	/**
	 * @param exportPayChainStatPath the exportPayChainStatPath to set
	 */
	public void setExportPayChainStatPath(String exportPayChainStatPath) {
		this.exportPayChainStatPath = exportPayChainStatPath;
	}


	/**
	 * @param exportPayChainDetailPath the exportPayChainDetailPath to set
	 */
	public void setExportPayChainDetailPath(String exportPayChainDetailPath) {
		this.exportPayChainDetailPath = exportPayChainDetailPath;
	}


	/**
	 * @param payChainService the payChainService to set
	 */
	public void setPayChainService(PayChainService payChainService) {
		this.payChainService = payChainService;
	}

	
	/**
	 * @param queryPayChainDetailView the queryPayChainDetailView to set
	 */
	public void setQueryPayChainDetailView(String queryPayChainDetailView) {
		this.queryPayChainDetailView = queryPayChainDetailView;
	}


	/**
	 * @return the queryStatView
	 */
	public String getQueryStatView() {
		return queryStatView;
	}

	/**
	 * @param queryStatView the queryStatView to set
	 */
	public void setQueryStatView(String queryStatView) {
		this.queryStatView = queryStatView;
	}
	
	
	
	/**
	 * @return the queryStatExportErrorView
	 */
	public String getQueryStatExportErrorView() {
		return queryStatExportErrorView;
	}

	/**
	 * @param queryStatExportErrorView the queryStatExportErrorView to set
	 */
	public void setQueryStatExportErrorView(String queryStatExportErrorView) {
		this.queryStatExportErrorView = queryStatExportErrorView;
	}

	
	public void setContextPicService(ContextPicService contextPicService) {
		this.contextPicService = contextPicService;
	}


	/**
	 * 查询支付链明细列表
	 * @author DDR
	 * @param request
	 * @param response
	 * @param paramDto
	 * @return view
	 * Date 2011-09-21
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,PayChainStatParamDto paramDto){
		//如果页面时间为空
		//指定当前时间为最大值
		String maxDate = DateUtil.formatDateTime("yyyy-MM-dd HH:mm",new Date((System.currentTimeMillis()+60*1000*5)));
		String maxEndDate = maxDate;//最大结束时间，个人版为 maxDate
		String minDate = FormatDate.formatDate(FormatDate.getDateLastestMonth(120),"yyyy-MM-dd");//DateUtil.skipDate( maxDate,-180);/6个月;
		String suffer = " 00:00"; 
			//最大的结束时间，企业为maxDate + 1 天
		maxEndDate = maxDate;
		minDate = FormatDate.formatDate(FormatDate.getDateLastestMonth(120),"yyyy-MM-dd")+ suffer;//DateUtil.skipDate( maxDate,-180);/6个月;
		String startTime = "";
		String endTime = "";
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("maxDate", maxEndDate);
		model.put("minDate", minDate);
		
		if(StringUtil.isEmpty(paramDto.getStartTime())|| StringUtil.isEmpty(paramDto.getEndTime())){
				startTime = DateUtil.skipDate(maxDate, -3)+ suffer; 
				endTime = maxDate;
				paramDto.setStartTime(startTime);
				paramDto.setEndTime(endTime);
				model.put("paramDto", paramDto);
				model.put("exedQuery", false);//是否执行过查询的标识 
				logger.info("首次查询，不执行查询统计，初始化参数完成");
			return new ModelAndView(queryStatView,model);
		}
		//如果时间有值，则调用查询去查询明细列表
		logger.info("执行支付链查询统计....开始.....");
		paramDto.setDatePattern("yyyy-MM-dd HH:mm");
		LoginSession loginSession = SessionHelper.getLoginSession();
		paramDto.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
		int pageNo =  ServletRequestUtils.getIntParameter(request, "pager_offset",1);
		int viewType =  ServletRequestUtils.getIntParameter(request, "vtype",0);
		if(viewType == 1){//表示下载excel
			return exportPayChain(request, response, paramDto);
		}
		paramDto.setPageNo(pageNo);
		PayChainStatDto payChainStatDto  = payChainService.queryPayChainStat(paramDto);
		model.put("payChainStatDto", payChainStatDto);
		model.put("effectiveTypes", EffectiveTypeEnum.values());
		model.put("paramDto", paramDto);
		model.put("exedQuery", true);//是否执行过查询的标识 
		PageUtil pu = new PageUtil(paramDto.getPageNo(),paramDto.getPageSize(),payChainStatDto.getRecordsCount());//分页处理
		model.put("pu", pu);
		logger.info("执行支付链查询统计....结束.....");
		
		return new ModelAndView(queryStatView,model);
	}
	
	private ModelAndView exportPayChain(HttpServletRequest request, HttpServletResponse response,PayChainStatParamDto paramDto){
		
		
		  String fullPath = request.getSession().getServletContext().getRealPath(exportPayChainStatPath);
	         HSSFWorkbook book ;
	         OutputStream os = null ;
	         try {
	          //创建workbook
	        	 book = payChainService.exportPayChainStat(paramDto, fullPath);
	          // 设置格式
             // 输出
             response.setContentType("application/vnd.ms-excel;charset=utf-8");
             response.addHeader("Content-Disposition", (new StringBuilder("attachment;   filename=\""))
                     .append(new String("paychain.xls".getBytes("utf-8"), "ISO-8859-1")).append("\"").toString());
             os = response.getOutputStream();
             book.write(os);
             os.flush();
	         }catch (ResultToMachException e) {
	        	 logger.error("记录数过多，下载不成功", e);
	        	 return new ModelAndView(queryStatExportErrorView);
		
         } catch (FileNotFoundException e) {
             e.printStackTrace();
             logger.error("写excel报错，模板文件不存在", e);
         } catch (Exception e) {
             e.printStackTrace();
             logger.error("写excel报错IO", e);
         }
         finally{
        	 try{
        		 if(os!=null)
              		 os.close();
        	 }catch (Exception e) {
        		 e.printStackTrace();
        		 logger.error("关闭流出错", e);
			}
         }
		return null;
		
	}
	
	
	
	public ModelAndView queryPayChainDetail(HttpServletRequest request, HttpServletResponse response,PayChainPaymentParamDto paramDto){
		
		int pageNo =  ServletRequestUtils.getIntParameter(request, "pager_offset",1);
		int viewType =  ServletRequestUtils.getIntParameter(request, "vtype",0);
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession());
		PayChain paychain  = payChainService.getPayChainByChainNum(paramDto.getPayChainNumber());
		if(paychain == null || memberCode.longValue() != paychain.getMemberCode().longValue() ){
			return new ModelAndView("redirect:/error.htm?method=noFeature");
		}
		if(viewType == 1){//表示下载excel
			return exportPayChainDetail(request, response, paramDto);
		}
	
		Map<String, Object> model = new HashMap<String, Object>();
		paramDto.setPageNo(pageNo);
		paramDto.setPageSize(5);//默认5条
		PayChainDetailDto payChainDetailDto = payChainService.queryPayChainDetail(paramDto);
		payChainDetailDto.setPayChainUrl(getFullUrl(request,payChainDetailDto.getPayChainUrl()));
	
		PageUtil pu = new PageUtil(paramDto.getPageNo(),paramDto.getPageSize(),payChainDetailDto.getRecordsCount());//分页处理
		List<ContextPicture>  picList=contextPicService.queryPicListByOwnerId(paychain.getPayChainId());
		model.put("payChainDetailDto", payChainDetailDto);
		model.put("payChainPayInfo", payChainDetailDto.getPayChainPayInfo());
		model.put("paramDto", paramDto);
		model.put("pu", pu);
		model.put("picList", picList);
		return new ModelAndView(queryPayChainDetailView,model); 
	}
	
	private ModelAndView exportPayChainDetail(HttpServletRequest request, HttpServletResponse response,PayChainPaymentParamDto paramDto){
		
		
		  String serverPath = getFullUrl(request,"");	
		  String fullPath = request.getSession().getServletContext().getRealPath(exportPayChainDetailPath);
	         HSSFWorkbook book ;
	         OutputStream os = null ;
	         try {
	          //创建workbook
	        	 book = payChainService.exportPayChainDetail(paramDto, fullPath,serverPath);
	          // 设置格式
           // 输出
	        	 response.setContentType("application/vnd.ms-excel;charset=utf-8");
           response.addHeader("Content-Disposition", (new StringBuilder("attachment;   filename=\""))
                   .append(new String("detail.xls".getBytes("utf-8"), "ISO-8859-1")).append("\"").toString());
           os = response.getOutputStream();
           book.write(os);
           os.flush();
	    }catch (ResultToMachException e) {
	    	 logger.error("记录数过多，下载不成功", e);
	        return new ModelAndView(queryStatExportErrorView);
		
       } catch (FileNotFoundException e) {
           e.printStackTrace();
           logger.error("写excel报错，模板文件不存在", e);
       } catch (Exception e) {
           e.printStackTrace();
           logger.error("写excel报错IO", e);
       }
       finally{
      	 try{
      	 if(os!=null)
      		 os.close();
      	 }catch (Exception e) {
      		 e.printStackTrace();
      		 logger.error("关闭流出错", e);
			}
       }
		return null;
		
	}
	
	
	 private String getFullUrl(HttpServletRequest request,String  payChainUrl){//+":"+request.getServerPort()
		 String   path   =   request.getContextPath();  
		 String   basePath   =   request.getScheme()+"://"+request.getServerName()+path+"/"; 
		 return (basePath+payChainUrl);
	 }
	
			
	 
	 
}
