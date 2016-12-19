package com.pay.fo.controller.sharedata.blacklist;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.batchinfo.service.model.BlackList;
import com.pay.fundout.batchinfo.service.model.BlackListFile;
import com.pay.fundout.batchinfo.service.sharedata.BlackListParser;
import com.pay.fundout.batchinfo.service.sharedata.ShareDataService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 数据共享之黑名单控制器
 * @author limeng
 *
 */
public class BlackListController extends AbstractBaseController{
	
	/*
	 * 指定黑名单文件的生成路径，为/opt/pay/sharedata/blacklist/
	 */
	public static String BLACK_LIST_PATH = new StringBuffer().append(File.separator).append("opt").append(File.separator).append("pay").append(File.separator).append("sharedata").append(File.separator).append("blacklist").append(File.separator).toString(); 
	
	/*
	 * 黑名单文件中的存放数据最大条目数，目前是1000
	 */
	public static int MAX_LENGTH = 1000;
	
	/*
	 * 对应的成员单位编码
	 */
	public static final String MEMBER_UNIT_CODE = "1003";
	
	/**
	 * 如果不存在的话，加载类的时候就创建黑名单存放的目录
	 */
	static{
		File blackListFile = new File(BLACK_LIST_PATH.toString());
		if(!blackListFile.exists()){
			blackListFile.mkdirs();
		}
	}
	
	private ShareDataService shareDataService;
	
	public void setShareDataService(ShareDataService shareDataService){
		this.shareDataService = shareDataService;
	}
	
	/**
	 * 新增黑名单初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addBlackList");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 添加黑名单并且将数据写入到对应的xml文件中
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView add(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		final BlackList blackList = new BlackList();
		bind(request, blackList, "blackList", "yy-MM-dd HH:mm:ss");
		blackList.setOperator(SessionUserHolderUtil.getLoginId());
		String viewName = urlMap.get("addBlackList");
		String info = "操作成功";
		try{
			// 找到符合条件可以写入的xml
			BlackListFile exitFile = shareDataService.findDealingFile();
			BlackListFile blackListFile = new BlackListFile();
			String filePath;
			// 不存在则创建新的文件并且将黑名单文件信息保存进数据库
			if(exitFile == null){
				Date now = new Date();
				String nowStr =  MEMBER_UNIT_CODE.concat(new SimpleDateFormat("yyyyMMddHHmmss").format(now));
				filePath = new StringBuffer(BLACK_LIST_PATH).append(nowStr).append(".xml").toString();
				// 封装黑名单文件实体并将对应的数据保存入库
				blackListFile.setMaxLength(MAX_LENGTH);
				blackListFile.setDownloadStatus("0");
				blackListFile.setCreateTime(new Date());
				blackListFile.setUpdateTime(new Date());
				blackListFile.setCurrentLength(1);
				blackListFile.setName(nowStr.concat(".xml"));
			}else{
				// 存在则对已存在文件进行相关操作
				filePath = new StringBuffer(BLACK_LIST_PATH).append(exitFile.getName()).toString();
			}
			File file = new File(filePath);
			if(!file.exists()){
				file.createNewFile();
			}
			// 到上一步，xml文件在对应的目录中创建成功
			
			// 判断xml是否是空文件
			// 空文件处理逻辑
			if(exitFile == null){
				String msg = BlackListParser.createBlackListFile(blackList, filePath);
				if(msg != null){
					log.error(msg);
					return new ModelAndView(viewName).addObject("info", msg);
				}
				// 生成成功以后，将数据插入到黑名单和黑名单文件表中去
				shareDataService.createBlackList(blackList);
				shareDataService.createBlackListFile(blackListFile);
			}else{
				String msg = BlackListParser.updateBlackListFile(blackList, file, filePath);
				if(msg != null){
					log.error(msg);
					return new ModelAndView(viewName).addObject("info", msg);
				}
				// 生成成功以后，将数据插入到黑名单和黑名单文件表中去
				shareDataService.createBlackList(blackList);
				exitFile.setCurrentLength(exitFile.getCurrentLength() + 1);
				exitFile.setUpdateTime(new Date());
				shareDataService.updateBlackListFile(exitFile);
			}
		}catch (Exception e) {
			log.warn("保存黑名单异常", e);
			info = "操作失败";
		}
		return new ModelAndView(viewName).addObject("info", info);
	}
	
	/**
	 * 检查相同ID的黑名单实体是否存在，返回yes表示已经存在同ID的黑名单，则不予添加，否则符合条件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkRepeat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		BlackList exsitBlackList = shareDataService.findById(id);
		String res = "no";
		if(exsitBlackList != null) {
			res = "yes";
		}
		response.getWriter().print(res);
		response.getWriter().close();
		return null;
	}
	
}
