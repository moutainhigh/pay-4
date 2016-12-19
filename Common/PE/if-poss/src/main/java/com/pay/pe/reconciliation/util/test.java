package com.pay.pe.reconciliation.util;
//package com.pay.pe.reconciliation.util;
//
//public class test {
// 
//		public void test(){
//			   String filePath = src;
//			   String fileName = "测试文件";
//
//			   boolean isInline = false;
//			   out.clear();
//			   response.reset();
//
//			   fileName = "测试文件" + ".txt";
//			   java.io.File f = new java.io.File(filePath);
//			   response.setContentType("application/x-download");
//			   response.setHeader("Content-Disposition",
//			   "attachment;filename="
//			     + java.net.URLEncoder.encode(fileName, "UTF8"));
//			   response.setContentLength((int) f.length()); //  设置下载内容大小 
//			   if (fileName != null && filePath != null) {
//			    try {
//			   if (f.exists() && f.canRead()) {
//			    String mimetype = null;
//			    byte[] buffer = new byte[4096]; //  缓冲区
//			    BufferedOutputStream output = null;
//			    BufferedInputStream input = null;
//			    try {
//			     output = new BufferedOutputStream(response
//			     .getOutputStream());
//			     input = new BufferedInputStream(
//			     new FileInputStream(f));
//
//			     int n = (-1);
//			     while ((n = input.read(buffer, 0, 4096)) > -1) {
//			    output.write(buffer, 0, n);
//			     }
//			     response.flushBuffer();
//			    } catch (Exception e) {
//			    } //  用户可能取消了下载
//			    finally {
//			     if (input != null)
//			    input.close();
//			     if (output != null)
//			    output.close();
//			    }
//
//			   }
//			   return;
//			    } catch (Exception ex) {
//			   ex.printStackTrace();
//			    }
//		}
//}
