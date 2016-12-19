
package com.pay.poss.exception;

/**
 * 项目共通异常
 * Created by songyilin on 2016/5/11.
 */
public class IpayException extends Exception {

    public static final String GET_EXCEL_EXCEPTION_MSG = "生成excel文件异常";

    public static final String GET_DATA_EXCETION_MSG = "获取excel内容异常";

    public static final String BLANK_EXCEPTION = "参数为空";

    public static final String DOWNLOAD_EXCEPTION = "下载异常";

    private static final long serialVersionUID = 1L;

    public IpayException(String message) {
        super(message);
    }

    public IpayException(String message, Throwable cause) {
        super(message, cause);
    }

}
