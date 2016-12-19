package com.pay.fo.controller.fundout.check;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fundout.withdraw.dto.check.FundoutCheckBatchDto;
import com.pay.fundout.withdraw.dto.check.FundoutCheckDto;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.model.result.WithdrawImportFile;
import com.pay.fundout.withdraw.service.check.FundoutCheckService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.WebBindUtils;

public class FundoutCheckController extends MultiActionController{
	protected transient Log log = LogFactory.getLog(getClass());
	private  String  index;

	private FundoutCheckService  fundoutCheckService;
	
	private String fundoutCheckResult;
	
	private String initFindCheck;
	
	private String findCheckResult;
	
	private String findCheckResultDateil;
	
	public void setFindCheckResultDateil(String findCheckResultDateil) {
		this.findCheckResultDateil = findCheckResultDateil;
	}

	public void setFindCheckResult(String findCheckResult) {
		this.findCheckResult = findCheckResult;
	}

	public void setInitFindCheck(String initFindCheck) {
		this.initFindCheck = initFindCheck;
	}

	public void setFundoutCheckService(FundoutCheckService fundoutCheckService) {
		this.fundoutCheckService = fundoutCheckService;
	}

	public void setFundoutCheckResult(String fundoutCheckResult) {
		this.fundoutCheckResult = fundoutCheckResult;
	}



	public void setIndex(String index) {
		this.index = index;
	}
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		//add by davis.guo at 2016-08-29 
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		try{
			WebBindUtils.bind(request, importFileDTO, "importFileDTO", true, null);
		}catch (ServletException e) {
			log.error(e.getMessage(), e);
		}
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("importFileDTO", importFileDTO);
		//end --------------- 
		return new ModelAndView(index, model);
	}
	/**
	 * 上传文件解析
	 * @param request
	 * @param response
	 * @return 
	 */
	public ModelAndView uploadFundoutCheck(HttpServletRequest request,
			HttpServletResponse response) {
		final MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest
				.getFile("orginalFile");
		String fileName = orginalFile.getOriginalFilename();
		/*String[] batchNos = fileName.split("_");
		String batchno = batchNos[2] + "_" + batchNos[3];*/
		//以上从文件名中获取批次号的值不对，应该是batchNos[3] + "_" + batchNos[4]，modified by davis.guo 2016-08-29 begin
		String[] batchNos = fileName.split("-");
		String batchno = (batchNos!=null?batchNos[0]:"");
		WithdrawImportFileDTO importFileDTO = new WithdrawImportFileDTO();
		importFileDTO.setFileName(orginalFile.getOriginalFilename());
		try{
			WebBindUtils.bind(request, importFileDTO, "importFileDTO", true, null);
		}catch (ServletException e) {
			log.error(e.getMessage(), e);
		}
		batchno = importFileDTO.getBatchNum();
		Long fileKy = importFileDTO.getgFileKy();//使用batchno+fileKy条件进行查询，更新记录
		System.out.println("###batchno="+batchno);
		//add end----------------------------------
		String loginId = SessionUserHolderUtil.getLoginId();
		List<FundoutCheckDto> fundoutCheckDtos = null;
		List<FundoutCheckDto> checkDtos = new ArrayList<FundoutCheckDto>();
		if (fileName.endsWith(".xls") && fileName.indexOf("cmbc") != -1) {// 民生银行出款文件
			jxl.Workbook book;
			try {
				book = jxl.Workbook.getWorkbook(orginalFile.getInputStream());
				List<FundoutCheckDto> list = ExcelUtils.getListByReadShell(
						// 无用的字段
						book.getSheet(0), 4, 0, 19, new String[] {
								"extrafields", "extrafields", "extrafields",
								"extrafields", "extrafields", "amount",
								"bankAccountCode", "bankAccount",
								"extrafields", "extrafields", "extrafields",
								"extrafields", "extrafields", "extrafields",
								"extrafields", "extrafields", "extrafields",
								"extrafields", "bankName" },
						FundoutCheckDto.class);
				for (FundoutCheckDto fundoutCheckDto : list) {
					String bankName = fundoutCheckDto.getBankName();
					String[] split = bankName.split("&");
					fundoutCheckDto.setBankCode(split[0]);
					fundoutCheckDto.setBankName(split[1]);
					fundoutCheckDto.setBatchNum(batchno);
					fundoutCheckDto.setId(String.valueOf(fileKy));//add by davis.guo 借用id来保存，fileKy的值。
				}
				checkDtos.addAll(list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (fileName.endsWith(".txt") && fileName.indexOf("boc") != -1) {// 中行的出款文件
			File file;
			try {
				file = this.inputstreamtofile(orginalFile.getInputStream(),
						fileName);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "GBK"));// 乱码解决
				int count = 0;
				String readLine = reader.readLine();
				while ((readLine = reader.readLine()) != null) {
					FundoutCheckDto fundoutCheckDto = new FundoutCheckDto();
					count++;
					if (readLine == null || readLine.trim().length() == 0) {
						continue;
					}
					String[] line = readLine.split("\\|"); // 分隔
					/*
					 * String[] line=new String[10]; int y=0; for (int i = 0; i
					 * < _line.length; i++) {
					 * if(StringUtils.isNotEmpty(_line[i])){ //过滤掉元素为空 line[y]=
					 * _line[i]; y++; } }
					 */
					if (line.length == 1) {// 最后一行 跳过
						continue;
					}
					try {
						// 收款人帐号
						fundoutCheckDto.setBankAccountCode(line[4]);
						// 收款人
						fundoutCheckDto.setBankAccount(line[8]);
						// 金额
						fundoutCheckDto.setAmount(line[11]);
						// 开户行名称
						fundoutCheckDto.setBankName(line[7]);
						// 开户行行号
						if (line.length > 20)// 如果生成的模板文件没有更新会异常，导致页面无法显示 add by davis.guo at 2016-08-23
							fundoutCheckDto.setBankCode(line[20]);
						fundoutCheckDto.setBatchNum(batchno);
						fundoutCheckDto.setId(String.valueOf(fileKy));//add by davis.guo 借用id来保存，fileKy的值。
						checkDtos.add(fundoutCheckDto);
					} catch (Exception e) {
						log.error(
								"中行出款文件解析错误，文件长度为:"
										+ line.length
										+ ",正确的文件长度为20，请确认/opt/pay/template/generator/boc_gen2e.vm文件的版本是否对？",
								e);
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (fileName.endsWith(".txt")) {// 农行的出款文件
			File file;
			try {
				file = this.inputstreamtofile(orginalFile.getInputStream(),
						fileName);
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(file), "GBK"));// 乱码解决
				int count = 0;
				String readLine = reader.readLine();
				while ((readLine = reader.readLine()) != null) {
					FundoutCheckDto fundoutCheckDto = new FundoutCheckDto();
					count++;
					if (readLine == null || readLine.trim().length() == 0) {
						continue;
					}
					String[] _line = readLine.split("\\|"); // 分隔
					String[] line = new String[10];
					int y = 0;
					for (int i = 0; i < _line.length; i++) {
						if (StringUtils.isNotEmpty(_line[i])) { // 过滤掉元素为空
							line[y] = _line[i];
							y++;
						}
					}
					if (StringUtils.isEmpty(line[1])) {// 最后一行 跳过
						continue;
					}
					fundoutCheckDto.setBankAccountCode(line[1]);
					fundoutCheckDto.setBankAccount(line[3]);
					fundoutCheckDto.setAmount(line[4]);
					fundoutCheckDto.setBankName(line[2]);
					fundoutCheckDto.setBankCode(line[9]);
					fundoutCheckDto.setBatchNum(batchno);
					fundoutCheckDto.setId(String.valueOf(fileKy));//add by davis.guo 借用id来保存，fileKy的值。
					checkDtos.add(fundoutCheckDto);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		FundoutCheckBatchDto batchDto = new FundoutCheckBatchDto();
		batchDto.setOperator(loginId);
		batchDto.setUploadFileName(fileName);
		batchDto.setApplyCount(checkDtos.size() + "");
		batchDto.setCheckDate(new Date());
		batchDto.setBatchNo(batchno);
		this.createFundoutFileBatch(batchDto);
		fundoutCheckDtos = fundoutCheckService.checkFundoutFile(checkDtos);

		String successCount = fundoutCheckDtos.get(0).getSuccessCount();
		return new ModelAndView(fundoutCheckResult)
				.addObject("fundoutCheckDtos", fundoutCheckDtos)
				.addObject("applyCount", checkDtos.size())
				.addObject("successCount", successCount);
	}
	/**
	 * 创建 批次文件数据
	 * @param fundoutCheckBatchDto
	 */
	public void createFundoutFileBatch(FundoutCheckBatchDto fundoutCheckBatchDto){
		fundoutCheckService.createFundoutFileBatch(fundoutCheckBatchDto);
	}
	
	public File inputstreamtofile(InputStream ins, String fileName) {
		File file = new File(fileName);
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}
	/**
	 * 初始化查询复核页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView findFundoutCheck(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(initFindCheck);
	}
	
	public ModelAndView queryFundoutCheck(HttpServletRequest request,HttpServletResponse response){
		String batchNo=request.getParameter("batchNo");
		String operator=request.getParameter("operator");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		Page  page= PageUtils.getPage(request);
		FundoutCheckBatchDto batchDto=new FundoutCheckBatchDto();
		batchDto.setBatchNo(batchNo);
		batchDto.setOperator(operator);
		batchDto.setStartDate(DateUtil.strToDate(startTime, "yyyy-MM-dd"));
		batchDto.setEndDate(DateUtil.strToDate(endTime, "yyyy-MM-dd"));
		List<FundoutCheckBatchDto> checkBatchDtos=fundoutCheckService.queryFundoutCheck(batchDto,page);
		return new ModelAndView(findCheckResult).addObject("checkBatchDtos", checkBatchDtos).addObject("page", page);
	}
	public ModelAndView queryFundoutCheckDetail(HttpServletRequest request,HttpServletResponse response){
		String batchNo = request.getParameter("batchNo");
		//Page page= PageUtils.getPage(request);
		List<FundoutCheckDto> fundoutCheckDtos=fundoutCheckService.queryFundoutCheckDetail(batchNo);
		Map<String,Integer> count=new HashMap<String, Integer>();
		count.put("totalCount", fundoutCheckDtos.size());
		int successCount=0;
		for (FundoutCheckDto fundoutCheckDto : fundoutCheckDtos) {
			if(fundoutCheckDto.getStatus().equals("1")){
				successCount++;
			}
		}
		
		count.put("successCount", successCount);
		count.put("failedCount", fundoutCheckDtos.size()- successCount);
		
		return new ModelAndView(findCheckResultDateil).addObject("fundoutCheckDtos", fundoutCheckDtos).addObject("count", count);
	}
	
	
	
}
