/**
 * 
 */
package com.pay.app.common.util;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.common.helper.AppConf;

/**
 * @author fjl
 *
 */
public class ImgUrlUtil {
	
	/**
	 * 得到website 支付链图片查看路径上下文
	 * @param request
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request){
		if(request != null){
			int port = request.getServerPort();
			StringBuffer sb = new StringBuffer("");
			sb.append(request.getScheme());
			sb.append("://");
			sb.append(request.getServerName());
			
			if(port != 80 && port != 443){
				sb.append(":").append(port);
			}
			sb.append(AppConf.get(AppConf.uploadContextPath));
			return sb.toString();
		}else{
			//return "/file";
			return AppConf.get(AppConf.uploadContextPath);
		}

	}

}
