package com.pay.fundout.withdraw.service.reviewfofile.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.pay.fundout.withdraw.service.reviewfofile.AbstractReviewFoFileService;
import com.pay.fundout.withdraw.service.reviewfofile.dto.ReviewFoFileDTO;

/**
 * @author lIWEI
 * @Date 2011-4-15
 * @Description 招商银行文件解析器对公
 * @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class CMBReviewFoFileServiceForEnterpriseImpl extends AbstractReviewFoFileService {

	
	/**
	 * 招商银行提现复核
	 *
	 
	对公：
	C_EPTDAT,M_DBTACC,C_TRSAMT,M_CRTACC,CRTNAM,C_STSFLG,OPRDAT,YURREF,NUSAGE,DBTACC,CRTADR,CRTBNK,C_DBTREL,C_STLCHN,DBTADR,DBTBNK,DBTNAM,RTNFLG
	20110302-08,"上海, 121907509610802, 人民币",0.01,"上海, 121907936810702, 人民币",上海盛付通电子商务有限公司,等待审批(初级审批),20110302,201101252,W000110125046014,121907509610802,上海市,招商银行,上海盛付通电子商务有限公司,普通,上海市,招商银行上海分行东方支行,上海盛付通电子商务有限公司,
	20110302-08,"上海, 121907509610802, 人民币","6,472,437.34","上海, 121907936810702, 人民币",上海益充电子商务有限公司,等待审批(初级审批),20110302,201101253,W000110125046015,121907509610802,上海市,招商银行,上海盛付通电子商务有限公司,普通,上海市,招商银行上海分行东方支行,上海盛付通电子商务有限公司,
	 */
	@Override
	protected List<ReviewFoFileDTO> parserImportFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));// 导入文件的缓冲流
		String line;// 文件的当前行
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();// 解析结果
		try {
			line = buffered.readLine();
			line = buffered.readLine();
			while (line != null && line.length() != 0) {
				
				 /**
	                * 对公格式
	                * 金额过大时，金额字段会变成双引号包裹，且千分位有逗号。此处特殊处理该种情况
	                
	                String[] llb = line.split("\",\"");
	                if(llb.length == 3){
	                    line = llb[0] + "," + llb[1].replaceAll("," , "") + "," + llb[2];
	                }else{
	                    line = line;
	                }
	                */
	                
				String[] lineArray = line.split(",");//,号分隔
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage(lineArray[7]);// 附言用途，含订单流水
				reviewFoFileDTO.setAmount(new BigDecimal(lineArray[6]));// 金额
				reviewFoFileDTO.setPayeeAccountNo(lineArray[2]);// 收款人账号
				reviewFoFileDTO.setPayeeName(lineArray[3]);// 收款人名称
				reviewFoFileDTOs.add(reviewFoFileDTO);
				line = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}
	
	
	
	/**
	 * 招商银行对公
		业务参考号,收款人编号,收款人帐号,收款人名称,收方开户支行,收款人所在省,收款人所在市,收方邮件地址,收方移动电话,币种,付款分行,结算方式,付方帐号,期望日,期望时间,用途,金额,收方联行号,收方开户银行,业务摘要$breakTag
	**/
	@Override
	protected List<ReviewFoFileDTO> parserLoadLocalFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));// 导入文件的缓冲流
		String line;// 文件的当前行
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();// 解析结果
		try {
			line = buffered.readLine();
			line = buffered.readLine();
			while (line != null && line.length() != 0) {
				String[] lineArray = line.split(",");
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage(lineArray[17]);// 附言用途，含订单流水
				reviewFoFileDTO.setAmount(new BigDecimal(lineArray[18]));// 金额
				reviewFoFileDTO.setPayeeAccountNo(lineArray[4]);// 收款人账号
				reviewFoFileDTO.setPayeeName(lineArray[5]);// 收款人名称
				reviewFoFileDTOs.add(reviewFoFileDTO);
				line = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

}
