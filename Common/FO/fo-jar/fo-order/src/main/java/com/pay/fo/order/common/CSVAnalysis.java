package com.pay.fo.order.common;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;

public class CSVAnalysis {

	public static List<String[]> readCSVFile(byte[] file) throws IOException {
		return readCSVFile(new ByteArrayInputStream(file));
	}

	public static List<String[]> readCSVFile(InputStream file) throws IOException {
		List<String[]> csvList = new ArrayList<String[]>(); // 用来保存数据

		try {

			InputStreamReader fr = new InputStreamReader(file, "gb2312");
			CsvReader cr = new CsvReader(fr);
			while (cr.readRecord()) { // 逐行读入除表头的数据
				csvList.add(cr.getValues());
			}
			cr.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return csvList;
	}
}
