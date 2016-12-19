package com.pay.acc.service.cidverify.cid2gov;

import com.pay.acc.service.cidverify.dto.BaseCid2GovParameterModel;

/**
 * 组装公安网规定格式参数
 * @author lei.jiangl 
 * @version 
 * @data 2010-9-13 上午12:13:19
 */
public class XmlModelManager {
	/**
	 *根据提交参数组装服务条件模版
	 * @param bpm  提交参数
	 * @return
	 */
	public String getCondition(BaseCid2GovParameterModel bpm)throws Exception{
		String subModel = this.conditionModel(bpm.getNo(), bpm.getName());
		return subModel;
	}
	/**
	 *服务条件模板 
	 * @param idno 身份证号码
	 * @param name 姓名
	 * @return
	 */
	private String conditionModel(String idno,String name) throws Exception{
		StringBuffer con = new StringBuffer();
		con.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>")
			.append("<ROWS>")
			.append("<INFO><SBM>shwyshwy26336</SBM></INFO>")
			.append("<ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW>")
			.append("<ROW FSD='310000' YWLX='shwysfzrz'><GMSFHM>"+idno+"</GMSFHM><XM>"+name+"</XM></ROW>")
			.append("</ROWS>");		
		return con.toString();
	}
}
