/**
 *  <p>File: CheckFoFileServiceImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
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
 * <p>
 * </p>
 * 
 * @author zengli
 * @since 2011-4-12
 * @see
 */
public class ACBReviewFoFileService2EImpl extends AbstractReviewFoFileService {


	@Override
	protected List<ReviewFoFileDTO> parserImportFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(
				new InputStreamReader(inputStream));
		String str;
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();
		try {
			str = buffered.readLine();
			str = buffered.readLine();
			while (str != null && str.length() != 0) {
				String[] strArray = str.split("\\|");
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage(strArray[8]);
//				reviewFoFileDTO.setSerialNo(Long.valueOf(strArray[0]));
				reviewFoFileDTO.setAmount(new BigDecimal(strArray[11]));
				reviewFoFileDTO.setPayeeAccountNo(strArray[5]);
				reviewFoFileDTO.setPayeeName(strArray[6]);
				reviewFoFileDTOs.add(reviewFoFileDTO);
				str = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

	@Override
	protected List<ReviewFoFileDTO> parserLoadLocalFile(InputStream inputStream) {

		final BufferedReader buffered = new BufferedReader(
				new InputStreamReader(inputStream));

		String str;

		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();

		try {
			str = buffered.readLine();
			str = buffered.readLine();
			while (str != null && str.length() != 0) {
				String[] strArray = str.split("\\|");
				ReviewFoFileDTO reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage(strArray[8]);
				reviewFoFileDTO.setSerialNo(Long.valueOf(strArray[0]));
				reviewFoFileDTO.setAmount(new BigDecimal(strArray[11]));
				reviewFoFileDTO.setPayeeAccountNo(strArray[5]);
				reviewFoFileDTO.setPayeeName(strArray[6]);
				reviewFoFileDTOs.add(reviewFoFileDTO);
				str = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

	
	public static void main(String[] args) {
		
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		list.add(1);
		List<Integer> list1 = new ArrayList<Integer>();
		
		list1.add(1);
		list1.add(2);
		list1.add(3);
		list1.add(4);
		list1.add(5);
		
		
		//list.retainAll(list1);
		list1.removeAll(list);
		
		for(Integer i : list){
			System.out.println(i);
		}
		
	}
	
	
	
}
