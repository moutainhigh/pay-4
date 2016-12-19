/**
 *  <p>File: VelocityTest.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.service.product.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.pay.fundout.bankfile.generator.model.cmb.CmbGeneratorModel;

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-8
 * @see 
 */
public class VelocityTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		/*VelocityEngine engine = new VelocityEngine(); 
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,"g:/g_test"); //此处的
		engine.init(properties);
		Template t = engine.getTemplate("ccb_wtd.vm");
		VelocityContext ctx = new VelocityContext();
		List<CmbGeneratorModel> list = new ArrayList<CmbGeneratorModel>();
		CmbGeneratorModel cmbGeneratorModel = new CmbGeneratorModel();
		cmbGeneratorModel.setAmount("12222222");
		cmbGeneratorModel.setPayeeAccNo("6222123123123123123123");
		cmbGeneratorModel.setPayeeAccName("曾利");
		cmbGeneratorModel.setPayeeBankBranch("上海工商银行");
		list.add(cmbGeneratorModel);
		cmbGeneratorModel = new CmbGeneratorModel();
		cmbGeneratorModel.setAmount("3333");
		cmbGeneratorModel.setPayeeAccNo("62222342342323123123123");
		cmbGeneratorModel.setPayeeAccName("吴灿");
		cmbGeneratorModel.setPayeeBankBranch("上海建设银行");
		list.add(cmbGeneratorModel);
		ctx.put("orderList", list);
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("g:/g_test/ccb_wtd.txt")), "GBK"));
		t.merge(ctx, bw);
	    bw.flush();
	    bw.close();
		System.out.println(bw.toString());*/
		
		VelocityEngine engine = new VelocityEngine(); 
		Properties properties = new Properties();
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,"E:/test"); //此处的
		engine.init(properties);
		Template t = engine.getTemplate("cnpy_2p.vm");
		VelocityContext ctx = new VelocityContext();
		List<CmbGeneratorModel> list = new ArrayList<CmbGeneratorModel>();
		CmbGeneratorModel cmbGeneratorModel = new CmbGeneratorModel();
		cmbGeneratorModel.setAmount("12222222");
		cmbGeneratorModel.setPayeeAccNo("6222123123123123123123");
		cmbGeneratorModel.setPayeeAccName("曾利");
		cmbGeneratorModel.setPayeeBankBranch("上海工商银行");
		list.add(cmbGeneratorModel);
		cmbGeneratorModel = new CmbGeneratorModel();
		cmbGeneratorModel.setAmount("3333");
		cmbGeneratorModel.setPayeeAccNo("62222342342323123123123");
		cmbGeneratorModel.setPayeeAccName("吴灿");
		cmbGeneratorModel.setPayeeBankBranch("上海建设银行");
		list.add(cmbGeneratorModel);
		ctx.put("orderList", list);
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("g:/g_test/ccb_wtd.txt")), "GBK"));
		t.merge(ctx, bw);
	    bw.flush();
	    bw.close();
		System.out.println(bw.toString());
	}

}
