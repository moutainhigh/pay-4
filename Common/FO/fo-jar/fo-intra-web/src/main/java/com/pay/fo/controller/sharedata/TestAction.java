package com.pay.fo.controller.sharedata;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestAction {

	/**
	 * 这个方法为了将黑名单实体中所有String类型字段中的一些在xml文件中需要转译的东西进行处理
	 * @param args
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
//		BlackList blackList = new BlackList();
//		blackList.setId("1234561");
//		blackList.setAddress("地址1231");
//		blackList.setMarkTime(new Date());
//		Class c = blackList.getClass();
//		Field[] fields = c.getDeclaredFields();
//		for(Field f : fields){
//			f.setAccessible(true);
//			if(f.getType().equals(String.class)){
//				String obj = (String)f.get(blackList);
//				if(obj != null){
//					f.set(blackList, obj.replaceAll("1", "x"));
//					System.out.println(f.getName() + "'s value is " + (String)f.get(blackList));
//				}
//			}
//		}
//	}
	
	/**
	 * 下载xml文件例子
	 */
//	public static void main(String[] args){
//		StringBuffer sb = new StringBuffer();
//		String separator = File.separator;
//		sb.append(separator).append("opt").append(separator).append("pay").append(separator).append("sharedata").append(separator).append("10022012061413423030.xml");
	
//		Long fileKy = Long.valueOf(request.getParameter("fileKy"));
//	
//		WithholdingFileInfoDto fileInfo = withholdingQueryService
//				.withholdingSummaryQuery(fileKy);
//	
//		InputStream inputStream = null;
//		OutputStream os = null;
//		try {
//			inputStream = new FileInputStream(new File(fileInfo.getFileName()));
//	
//			response.reset();
//			response.setContentType("text/html;charset=UTF-8");
//			response.setHeader("Content-disposition", "attachment; filename="
//					+ fileInfo.getFileNameDesc());
//			response.setBufferSize(1024);
//	
//			os = new BufferedOutputStream(response.getOutputStream());
//			byte[] b = new byte[1024];
//			int len = 0;
//			while ((len = inputStream.read(b)) > 0) {
//				os.write(b, 0, len);
//				os.flush();
//			}
//			os.close();
//			inputStream.close();
//			return null;
//		} catch (Throwable e) {
//			throw new RuntimeException(e);
//		} finally {
//			os = null;
//			inputStream = null;
//		}
//	}
	
	/**
	 * 创建文件夹
	 */
//	public static void main(String[] args){
//		StringBuffer sb = new StringBuffer();
//		String separator = File.separator;
//		sb.append(separator).append("opt").append(separator).append("pay").append(separator).append("sharedata");
//		String str = sb.toString();
//		File file = new File(str);
//		if(!file.exists()){
//			file.mkdirs();
//		}
//		System.out.println("success");
//	}
	
	/**
	 * 日期格式转换
	 */
	public static void main(String[] args){
		Date now = new Date();
		String nowStr = new SimpleDateFormat("yyyyMMddHHmmss").format(now);
		System.out.println("now time is " + nowStr);
	}
}
