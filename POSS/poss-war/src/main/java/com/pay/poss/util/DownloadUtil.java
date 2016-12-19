package com.pay.poss.util;

import com.pay.poss.exception.IpayException;
import com.pay.poss.ipayconst.IpayConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by songyilin on 2016/10/13 0013.
 */
public class DownloadUtil {

    private static final Logger logger = LoggerFactory.getLogger(DownloadUtil.class);

    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     * @param data
     * @throws IpayException
     */
    public static void download(HttpServletResponse response, String fileName,
                                byte[] data, HttpServletRequest request) throws IpayException {

        if (data == null) {
            throw new IpayException(IpayException.BLANK_EXCEPTION);
        }

        OutputStream outputStream = null;
        try {
            String type = getBrowseType(request);
            if (IpayConst.BROWSE_FILEFOX.equals(type)
                    || IpayConst.BROWSE_CHEOME.equals(type)) {
                fileName = new String(fileName.getBytes(),
                        IpayConst.CHARSET_ISO8859_1);
            } else {
                fileName = URLEncoder.encode(fileName, IpayConst.CHARSET_UTF8);
            }
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\""
                    + fileName + "\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream;charset=UTF-8");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
            response.flushBuffer();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IpayException(IpayException.DOWNLOAD_EXCEPTION);
        } finally {
            IOUtil.closeOutputStream(outputStream);
        }
    }

    public static String getBrowseType(HttpServletRequest request) {
        String typename = request.getHeader("User-Agent");
        String type = "";
        if (typename.indexOf("MSIE") > -1) {
            type = IpayConst.BROWSE_IE;
        } else if (typename.indexOf("Firefox") > -1) {
            type = IpayConst.BROWSE_FILEFOX;
        } else if (typename.indexOf("Chrome") > -1) {
            type = IpayConst.BROWSE_CHEOME;
        } else if (typename.indexOf("Safari") > -1) {
            type = IpayConst.BROWSE_SAFARI;
        }
        return type;
    }

    /**
     * 下载文件
     *
     * @param response
     * @param fileName
     * @param path
     * @throws IpayException
     */
    public static void download(HttpServletResponse response, String fileName,
                                String path, HttpServletRequest request) throws IpayException {

        if (StringUtils.isBlank(path)) {
            throw new IpayException(IpayException.BLANK_EXCEPTION);
        }

        download(response, fileName, IOUtil.getFile(path), request);
    }
}
