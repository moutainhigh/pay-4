package com.pay.poss.util;


import com.pay.poss.ipayconst.IpayConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IO处理共通
 *
 * Created by ZHW on 2015/5/11.
 */
public class IOUtil {

    private static Logger logger = Logger.getLogger(IOUtil.class);

    /**
     * 获取文件类型
     *
     * @param file
     * @return
     */
    public static String getFileType(File file) {
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return IpayConst.EMPTY;
        } else {
            return fileName.substring(index + 1);
        }
    }

    /**
     * 关闭输出流
     *
     * @param stream
     */
    public static void closeOutputStream(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param stream
     */
    public static void closeInputStream(InputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param reader
     */
    public static void closeReader(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 获取文件的二进制数据
     *
     * @param path
     * @return
     */
    public static byte[] getFile(String path) {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(path));
            out = new ByteArrayOutputStream(1024 * 1024 * 10);

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }

            return out.toByteArray();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeInputStream(in);
            closeOutputStream(out);
        }
    }

    /**
     * 获取文件的文本内容
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {
        BufferedReader br = null;
        try {
            InputStream is = new FileInputStream(path);
            br = new BufferedReader(new InputStreamReader(is, IpayConst.CHARSET_UTF8));
            StringBuilder sb = new StringBuilder();

            while (true) {
                char[] data = new char[1024];
                int count = br.read(data, 0, 1024);
                if (count < 0) {
                    break;
                } else {
                    sb.append(data);
                }
            }

            return sb.toString();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            closeReader(br);
        }
    }

    /**
     * 保存文件
     *
     * @param path
     * @param data
     * @return
     */
    public static Boolean saveFile(String path, byte[] data) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(path));
            bos.write(data);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            closeOutputStream(bos);
        }
    }

    /**
     * 保存文件
     *
     * @param dir
     * @param name
     * @param data
     * @return
     */
    public static Boolean saveFile(String dir, String name, byte[] data) {
        BufferedOutputStream bos = null;
        try {
            if (!exist(dir)) {
                IOUtil.createDir(dir);
            }

            bos = new BufferedOutputStream(new FileOutputStream(dir + File.separator + name));
            bos.write(data);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            closeOutputStream(bos);
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param sourPath
     * @param destPath
     */
    public static void copyFile(String sourPath, String destPath) {
        saveFile(destPath, getFile(sourPath));
    }

    /**
     * 获取临时文件名
     *
     * @param fileName
     * @return
     */
    public static String getTmpFileName(String fileName) {
        int index = fileName.lastIndexOf(".");
        StringBuilder sb = new StringBuilder();

        if (index < 0) {
            sb.append(fileName).append("_");
            sb.append(System.currentTimeMillis());
        } else {
            sb.append(fileName.substring(0, index)).append("_");
            sb.append(System.currentTimeMillis());
            sb.append(fileName.substring(index));
        }

        return sb.toString();
    }

    /**
     * 获取文件名
     *
     * @param path
     * @return
     */
    public static String getFileName(String path) {
        if (StringUtils.isBlank(path)) {
            return IpayConst.EMPTY;
        } else {
            int index = path.lastIndexOf("\\");
            if (index > -1) {
                return path.substring(index + 1);
            } else {
                return path;
            }
        }
    }

    /**
     * 新建文件夹
     *
     * @param path
     * @return
     */
    public static Boolean createDir(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        } else {
            File dir = new File(path);
            if (dir.exists()) {
                return true;
            } else {
                return dir.mkdirs();
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    public static Boolean exist(String path) {
        if (StringUtils.isBlank(path)) {
            return false;
        } else {
            File file = new File(path);
            file.list();
            return file.exists();
        }
    }

    /**
     * 获取文件夹下的文件名
     *
     * @param dir
     * @param withOutPrefix
     * @return
     */
    public static List<String> getFileName(String dir, String withOutPrefix) {
        if (!StringUtils.isBlank(dir)) {
            File file = new File(dir);
            String[] fileList = file.list();

            if (fileList != null) {
                List<String> files = new ArrayList<String>();

                for (int i = 0; i < fileList.length; ++i) {
                    if (!fileList[i].startsWith(withOutPrefix)) {
                        files.add(fileList[i]);
                    }
                }

                return files;
            }
        }

        return new ArrayList<String>();
    }

    /**
     * 获取文件夹下的文件路径
     *
     * @param dir
     * @param withOutPrefix
     * @return
     */
    public static List<String> getFilePath(String dir, String withOutPrefix) {
        if (!StringUtils.isBlank(dir)) {
            File file = new File(dir);
            File[] fileList = file.listFiles();

            if (fileList != null) {
                List<String> files = new ArrayList<String>();

                for (int i = 0; i < fileList.length; ++i) {
                    if (!fileList[i].getName().startsWith(withOutPrefix)) {
                        files.add(fileList[i].getPath());
                    }
                }

                return files;
            }
        }

        return new ArrayList<String>();
    }

    /**
     * 获取文件所在的目录
     *
     * @param filePath
     * @return
     */
    public static String getDir(String filePath) {
        if (!StringUtils.isBlank(filePath)) {
            File file = new File(filePath);
            return file.getParent();
        }

        return StringUtils.EMPTY;
    }

}
