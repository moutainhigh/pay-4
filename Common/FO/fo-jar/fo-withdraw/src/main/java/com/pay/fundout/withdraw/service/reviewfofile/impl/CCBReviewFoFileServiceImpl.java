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
 *  @author lIWEI
 *  @Date 2011-4-15
 *  @Description 建设银行文件解析器
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class CCBReviewFoFileServiceImpl extends AbstractReviewFoFileService {

	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.reviewfofile.AbstractReviewFoFileService#parserImportFile(java.io.InputStream)
	 */
	@Override
	protected List<ReviewFoFileDTO> parserImportFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));//导入文件的缓冲流
		String line;//文件的当前行
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();//解析结果
		try {
			line = buffered.readLine();
			line = buffered.readLine();
			while (line != null && line.length() != 0) {
				String[] lineArray = line.split("\\|");
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage("");//附言用途，含订单流水
				reviewFoFileDTO.setAmount(new BigDecimal(lineArray[0]));//金额
				reviewFoFileDTO.setPayeeAccountNo(lineArray[2]);//收款人账号
				reviewFoFileDTO.setPayeeName(lineArray[3]);//收款人名称
				reviewFoFileDTOs.add(reviewFoFileDTO);
				line = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.reviewfofile.AbstractReviewFoFileService#parserLoadLocalFile(java.io.InputStream)
	 */
	@Override
	protected List<ReviewFoFileDTO> parserLoadLocalFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));//本地文件的缓冲流
		String line;//文件的当前行
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();//解析结果
		try {
			line = buffered.readLine();
			line = buffered.readLine();
			while (line != null && line.length() != 0) {
				String[] lineArray = line.split("\\|");
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage("");//附言用途，含订单流水
				reviewFoFileDTO.setAmount(new BigDecimal(lineArray[0]));//金额
				reviewFoFileDTO.setPayeeAccountNo(lineArray[2]);//收款人账号
				reviewFoFileDTO.setPayeeName(lineArray[3]);//收款人名称
				reviewFoFileDTOs.add(reviewFoFileDTO);
				line = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

}
