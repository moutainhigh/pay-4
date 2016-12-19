package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.model.PricingStrategy;
import com.pay.pricingstrategy.model.PricingStrategyDetail;

public class ImportData {
	private InputStreamReader fr = null;
	private BufferedReader br = null;

	public ImportData(String f) throws IOException {
		fr = new InputStreamReader(new FileInputStream(f), "GBK");
	}

	private static String format = "MM-dd-yyyy HH:mm:ss";

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:/context/*.xml");
		PricingStrategyDAO pricingStrategyDAO = (PricingStrategyDAO) context
				.getBean("pricingStrategyDAO");
		preparedPS(pricingStrategyDAO);
		preparedPSD(pricingStrategyDAO);
	}

	private static void preparedPSD(PricingStrategyDAO pricingStrategyDAO) throws Exception{
		ImportData data = new ImportData("D:\\DropBox\\My Dropbox\\Public\\资料\\\\支付计费\\DB\\PRICESTRATEGYDETAIL.csv");
		
		List<List<String>> d = data.readCSVFile();
		for (int i = 1; i < d.size(); i++) {
			String[] st = (String[]) d.get(i).toArray(
					new String[d.get(i).size()]);
			try{
				PricingStrategyDetail detail = new PricingStrategyDetail();
				detail.setPriceStrategyDetailCode(parseLong(st[0]));
				detail.setPriceStrategyCode(parseLong(st[1]));
				detail.setTerminalTypeCode(parseInt(st[2]));
				detail.setEffectiveFrom(parseTime(st[3]));
				detail.setEffectiveTo(parseTime(st[4]));
				detail.setRangBy(parseLong(st[5]));
				detail.setRangFrom(parseLong(st[6]));
				detail.setRangTo(parseLong(st[7]));
				detail.setFixedCharge(parseLong(st[8]));
				detail.setChargeRate(parseLong(st[9]));
				detail.setMaxCharge(parseLong(st[10]));
				detail.setMinCharge(parseLong(st[11]));
				detail.setReservedCode(st[12]);
				detail.setReservedName(st[13]);
				pricingStrategyDAO.addPricingStrategyDetail(detail);
			}catch(Exception e){
				System.err.println(d.get(i));
				System.out.println(d.get(i).size());
				for (int j = 0; j < d.get(i).size(); j++) {
					System.out.println("st[" + j + "] = " + d.get(i).get(j));
				}
			}
		}
	}
	
	private static void preparedPS(PricingStrategyDAO pricingStrategyDAO)
			throws IOException, Exception {
		ImportData data = new ImportData(
				"D:\\DropBox\\My Dropbox\\Public\\资料\\\\支付计费\\DB\\PRICESTRATEY.csv");

		List<List<String>> d = data.readCSVFile();
		for (int i = 1; i < d.size(); i++) {
			String[] st = (String[]) d.get(i).toArray(
					new String[d.get(i).size()]);
			try {
				PricingStrategy ps = new PricingStrategy();
				ps.setPaymentServiceCode(parseInt(st[0]));
				ps.setPriceStrategyCode(parseLong(st[1]));
				ps.setPriceStrategyName(st[2]);
				ps.setPriceStrategyType(parseInt(st[3]));
				ps.setServiceLevelCode(parseInt(st[4]));
				ps.setMemberCode(parseLong(st[5]));
				ps.setEffectiveOn(parseInt(st[6]));
				ps.setValidDate(parseTime(st[7]));
				ps.setInvalidDate(parseTime(st[8]));
				ps.setStatus(parseInt(st[9]));
				ps.setCaculateMethod(parseInt(st[10]));
				ps.setPriceStrategySeq(parseInt(st[11]));
				pricingStrategyDAO.addPricingStrategy(ps);
			} catch (Exception e) {
				System.err.println(d.get(i));
				System.out.println(d.get(i).size());
				for (int j = 0; j < d.get(i).size(); j++) {
					System.out.println("st[" + j + "] = " + d.get(i).get(j));
				}
			}
		}
	}

	private static Timestamp parseTime(String str) throws Exception {
		if (str.length() == 8) {
			str = "01-01-1900 00:00:00";
		}
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = df.parse(str);
		Timestamp ts = new Timestamp(date.getTime());
		return ts;

	}

	private static Integer parseInt(String str) {
		if (str == null || str.trim().length() == 0)
			return null;
		return Integer.parseInt(str);
	}

	private static Long parseLong(String str) {
		if (str == null || str.trim().length() == 0)
			return null;
		return Long.parseLong(str);
	}

	/**
	 * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<List<String>> readCSVFile() throws Exception {
		br = new BufferedReader(fr);
		String rec = null;// 一行
		String str;// 一个单元格
		List<List<String>> listFile = new ArrayList<List<String>>();
		try {
			// 读取一行
			while ((rec = br.readLine()) != null) {
				Pattern pCells = Pattern
						.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
				Matcher mCells = pCells.matcher(rec);
				List<String> cells = new ArrayList<String>();// 每行记录一个list
				// 读取每个单元格
				while (mCells.find()) {
					str = mCells.group();
					str = str.replaceAll(
							"(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str = str.replaceAll("(?sm)(\"(\"))", "$2");
					cells.add(str);
				}
				listFile.add(cells);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fr != null) {
				fr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		return listFile;
	}

}
