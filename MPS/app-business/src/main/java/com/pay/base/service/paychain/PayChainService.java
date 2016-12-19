package com.pay.base.service.paychain;

import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.base.common.enums.PayChainValidateMsgEnum;
import com.pay.base.dto.PayChainDetailDto;
import com.pay.base.dto.PayChainPaymentParamDto;
import com.pay.base.dto.PayChainStatDto;
import com.pay.base.dto.PayChainStatParamDto;
import com.pay.base.exception.ResultToMachException;
import com.pay.base.model.PayChain;

public interface PayChainService {

    public Long createPayChain(PayChain payChain);
    
    public String generatePayChainNum();
    
    public String encryptChainNum(String chainNum);
    
    public String decryptChainNum(String encryChainNum);
    
	public Date getOverDate(int day);
	
	/**
	 * 查询支付链明细列表
	 * @author DDR
	 * @param paramDto 参数列表，如果不传查询的是全部，也可以传部份明细
	 * @return PayChainStatDto 得到对应条件的结果列表
	 */
	public PayChainStatDto queryPayChainStat(PayChainStatParamDto paramDto);
	
	/**
	 * 查询支付链总记录数，用于判定是否数据量过大
	 * @author DDR
	 * @param paramDto 
	 * @return 记录数
	 */
	public int queryPayChainStatCount(PayChainStatParamDto paramDto);
	
	/**
	 * 下载支付链明细列表
	 * 同queryPayChainStat查询，但是以excel下载
	 * @param paramDto 
	 * @param templatePath
	 * @return HSSFWorkbook excel表格
	 * @throws  ResultToMachException 记录超过限制时（5000默认）会自动抛出异常
	 * @throws  Excepiton 主要是流的异常
	 * @author DDR
	 */
	public HSSFWorkbook exportPayChainStat(PayChainStatParamDto paramDto,String templatePath) throws ResultToMachException,Exception;
	
	
	//给预览用的编号
	public String getNextPayChainNum();
	
	/**
	 * 根据支付链编号 查询支付链
	 * @param chainNum
	 * @return
	 */
	public PayChain getPayChainByChainNum(String chainNum);
	
	public PayChainValidateMsgEnum validate(String chainNum);
	
	/**
	 * 查询支付链接详情DTO，分布查询
	 * @param payParamDto 分页查询的参数
	 * @return PayChainDetailDto
	 * @author DDR 
	 */
	public PayChainDetailDto queryPayChainDetail(PayChainPaymentParamDto payParamDto);
	
	/**
	 * 下载支付链接详情，分布查询，但在里边下载的是全部信息，不能超过最大限制记录
	 * @param payParamDto
	 * @param templatePath
	 * @param serverPath 访问服务器的contextPath 如 http://www.pay.com/website
	 * @return HSSFWorkbook jxl的excel
	 * @throws ResultToMachException
	 * @throws Exception
	 * @author DDR
	 */
	public HSSFWorkbook exportPayChainDetail(PayChainPaymentParamDto payParamDto,String templatePath,String serverPath) throws ResultToMachException,Exception;

	/**
	 * 根据payChainNumber关闭支付链
	 * @param payChainNumer
	 * @return true / false
	 * @author DDR
	 */
	public boolean closePayChainRdTx(String payChainNumer);
	
	/**
	 * 更改有效期
	 * @param payNum
	 * @param effectiveType
	 * @return true/false
	 * @author DDR
	 */
	public boolean updateEffectiveDate(String payNum,int effectiveType);


}
