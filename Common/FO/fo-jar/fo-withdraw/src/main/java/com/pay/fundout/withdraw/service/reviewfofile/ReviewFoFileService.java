/**
 *  <p>File: CheckFoFileService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.withdraw.service.reviewfofile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.service.reviewfofile.dto.ReviewFoFileDTO;

/**
 * <p></p>
 * @author zengli
 * @since 2011-4-12
 * @see 
 */
public interface ReviewFoFileService {
	
	
	public Map<String,List<ReviewFoFileDTO>> compareFile(InputStream one , InputStream two) ;
	
}
