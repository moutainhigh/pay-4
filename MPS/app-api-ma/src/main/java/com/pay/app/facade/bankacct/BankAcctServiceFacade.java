/**
 * @create time 2010-9-16 -下午03:51:20
 * @description TODO
 * @project app-api-ma
 * @author & mail   sunny.ying@staff.pay.com
 */
package com.pay.app.facade.bankacct;

import java.util.List;

import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;

/**
 * @author sunny.ying
 * 
 */
public interface BankAcctServiceFacade {

	/**
	 * 根据银行机构号和借记卡号，来验证 输入的卡号是否正确
	 * 
	 * @param bankCode
	 *            银行机构号
	 * @param bankNumber
	 *            银行借记卡号
	 * @return boolean
	 */
	public boolean isValidCardBin(String bankCode, String bankNumber);

	/**
	 * 查询所有可以绑定的 银行列表
	 * 
	 * @return List<Bank>
	 */
	public List<Bank> getBindAllBankList(String param);

	/**
	 * 查询得到所有的 省份/直辖市
	 * 
	 * @return List<ProvinceDTO>
	 */
	public List<ProvinceDTO> getAllProvince();

	/**
	 * 查询得到所有的 市/区
	 * 
	 * @return
	 */
	public List<CityDTO> getAllCity(Integer provinceId);

	/**
	 * 根据城市code 得到城市名称
	 * 
	 * @param cityId
	 * @return String
	 */
	public String getCityById(Integer cityCode);

	/**
	 * 根据省code 得到省名称
	 * 
	 * @param provinceCode
	 * @return String
	 */
	public String getProvinceById(Integer provinceCode);

	/**
	 * 根据银行code 得到银行名称
	 * 
	 * @param bankCode
	 * @return
	 */
	public String getBankNameByCode(String bankCode);
	
	/**
	 * 验证银行卡，0代表正确。  1 不是借记卡，2 卡号不正确
	 * @author Sunny Ying
	 * @param bankCode
	 * @param bankNumber
	 * @throw null
	 * @return int
	 */
	public int validCardNo(String bankCode, String bankNumber);
	
	
	/**
	 * 根据银行机构号和信用卡号，来验证 输入的卡号是否正确
	 * 
	 * @param bankCode
	 *            银行机构号
	 * @param bankNumber
	 *            银行信用卡卡号
	 * @return boolean
	 */
	public boolean isCreditCard(String bankCode, String bankNumber);
}
