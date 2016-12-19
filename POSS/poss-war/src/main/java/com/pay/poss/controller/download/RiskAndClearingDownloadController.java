package com.pay.poss.controller.download;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Objects;
import com.pay.poss.form.DownloadDataForm;
import com.pay.poss.ipayenum.DownloadTypeEnum;
import com.pay.poss.service.DownloadService;
import com.pay.poss.util.DateUtil;
import com.pay.poss.util.DownloadUtil;
import com.pay.poss.util.JsonUtil;
import com.pay.poss.util.PoiUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by songyilin on 2016/10/11 0011.
 */

@Controller
@RequestMapping("riskAndClearingDownload")
public class RiskAndClearingDownloadController extends BaseController{

    @Autowired
    private DownloadService downloadService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //原始数据
    private static final Integer UPLOAD_TYPE_ORI = 1;

    //拒付数据
    private static final Integer UPLOAD_TYPE_REFUSE = 2;

    private static final Integer SUCCESS_FLAG = 1;

    private static final Integer FAIL_FLAG = 0;

    private static final Integer NO_DATA_FALG = 2;


    @RequestMapping(value="toDownClearingPage.do", method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView toDownClearingPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/download/clearingDownload");
        return modelAndView;
    }

    @RequestMapping(value="toRiskPage.do", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView toRiskPage(){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/download/riskDownload");
        return mav;

    }




    //下载报表
    @ResponseBody
    @RequestMapping(value = "downloadData.do", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadData(DownloadDataForm form, HttpServletRequest request, HttpServletResponse response){
        logger.info("start downloadData... form: " + JsonUtil.entity2Json(form));
        String type = form.getDownloadType();
        String[] typeArray = type.split("\\|");
        List<byte[]> byteList = Lists.newArrayList();
        List<String> fileNameList = Lists.newArrayList();
        ArrayList<File> fileList = new ArrayList<File>();
        String now = DateUtil.format(new Date(), DateUtil.YYYYMMDD3) + "_" + RandomUtils.nextInt(new Random(System.currentTimeMillis()), 9999);
        String zipFileName = "download_" + now +".zip";
        try{
            for(String str : typeArray){
                form.setDataType(Integer.valueOf(str));
                byte[] data = downloadService.queryDownloadData(form);
                if(data != null){
                    String suffix = "";
                    if(form.getStartDate() != null || form.getEndDate() != null){
                        suffix = "(" + Objects.firstNonNull(form.getStartDate(), "") + "~" + Objects.firstNonNull(form.getEndDate(), "") + ")";
                    }
                    fileNameList.add("/" + DownloadTypeEnum.getByType(Integer.valueOf(str)) + suffix + "_" + now + ".xlsx");
                    byteList.add(data);
                }
            }

            if(byteList.size() == 1){
                DownloadUtil.download(response, fileNameList.get(0), byteList.get(0), request);
            }else if(byteList.size() > 1){
                //压缩文件
                for(String name : fileNameList){
                    fileList.add(new File(name));
                }
                byte[] zipData = downloadService.getZipByte(fileList, byteList, zipFileName);
                if (zipData != null){
                    DownloadUtil.download(response, zipFileName, zipData, request);
                }
            }


        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            for(File file : fileList){
                try {
                    if(file != null && file.exists()){
                        file.delete();
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                }
            }
            //压缩文件删除
            try {
                File file = new File(zipFileName);
                if(file != null && file.exists()){
                    file.delete();
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }

    }

    //上传报表后下载
    @ResponseBody
    @RequestMapping(value = "uploadAndDownload.do", method = {RequestMethod.POST, RequestMethod.GET})
    public Object uploadAndDownload(@RequestParam("fileToUpload") MultipartFile file,
                                    @RequestParam("type") Integer type,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws IOException {
        logger.info("start uploadAndDownload...fileName: "+file.getOriginalFilename() + "type: " + type);
        Integer colLength = 1;
        if(type == UPLOAD_TYPE_ORI){
            colLength = 1;
        }else if(type == UPLOAD_TYPE_REFUSE){
            colLength = 3;
        }
        String realPath = "/";
        File newFile = new File(realPath, file.getOriginalFilename());
        try{
            FileUtils.copyInputStreamToFile(file.getInputStream(), newFile);
            //解析excel文件
            List<String[]> resList = PoiUtil.readerExcel(newFile.getAbsolutePath(), "data", colLength);
            //查询记录并下载
            byte[] data = downloadService.getUploadAndDownloadData(resList, type);
            if(data != null){
                DownloadUtil.download(response, DateUtil.getNow(DateUtil.YYYYMMDDHHMMSSMS) + ".xlsx", data, request);
            }else{
                return NO_DATA_FALG;
            }


        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return FAIL_FLAG;
        }finally {
            try {
                if(newFile.exists()){
                    newFile.delete();
                }
            }catch (Exception ex){
                logger.error(ex.getMessage(), ex);
            }
        }
        return SUCCESS_FLAG;

    }


}



