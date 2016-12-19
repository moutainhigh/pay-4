package com.pay.poss.service;

import com.google.common.collect.Lists;
import com.pay.poss.domain.*;
import com.pay.poss.excel.ExcelUtil;
import com.pay.poss.form.DownloadDataForm;
import com.pay.poss.param.*;
import com.pay.poss.persistence.BalanceEntryMapper;
import com.pay.poss.persistence.ClearingMapper;
import com.pay.poss.persistence.RiskMapper;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.collections.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by songyilin on 2016/10/11 0011.
 */

@Service
public class DownloadService {

    //已清算标志位
    private static final Integer SETTLED_FLAG = 1;

    //未清算标志位
    private static final Integer UN_SETTLED_FLAG = 0;

    //分页步长
    private static final Integer INCREASE_STEP = 15000;
    private static final String CREATE_DATE = "tradeDate";
    private static final String ORDER_TYPE = "desc";

    @Autowired
    private ClearingMapper clearingMapper;

    @Autowired
    private BalanceEntryMapper balanceEntryMapper;

    @Autowired
    private RiskMapper riskMapper;
    private Logger logger = LoggerFactory.getLogger(DownloadService.class);


    public byte[] queryDownloadData(DownloadDataForm form){


        int type = form.getDataType();

        switch (type){
            case 1 : {
                //下载已清算订单表
                return getSettleByte(form, SETTLED_FLAG);
            }

            case 2 : {
                //下载拒付订单表
                return getRefuseData(form);
            }

            case 3 : {
                //下载每日清算保证金表
                return getBalanceEntryInfo(form, 2, 203L);

            }

            case 4 : {
                //下载未清算订单
                return getSettleByte(form, UN_SETTLED_FLAG);
            }

            case 5 : {
                //下载退款订单
                return getRefundData(form);

            }

            case 6 : {
                //下载中银MGS退款订单
                return getMigsRefundOrderInfo(form);
            }

            case 7 : {
                //下载归还保证金表
                return getBalanceEntryInfo(form, 2, 700L);
            }

            case 8 : {
                //下载每日交易数报表
                return getSettleByte(form, null);
            }

            case 9 : {
                //下载详细信息表
                return getOrderDetail(form);
            }

            case 10 : {
                //下载风控手续费表
                return getBalanceEntryInfo(form, 1, 204L);
            }

            case 11 : {
                //下载每日清算基本户表
               return getBalanceEntryInfo(form, 2, 202L);
            }

            case 12 : {
                //下载每日交易量变化表
                return getRiskTradeChangeInfo(form);
            }

            case 13 : {
                //下载T-1日交易明细表
                return getRiskTradeDetail(form);
            }

            case 14 : {
                //下载渠道二级商户日报
                return getRiskChannelDailyReport(form);
            }

            case 15 : {
                // 下载商户运营交易监控表
                return getRiskMerchantMonitorInfo(form);
            }

            case 16 : {
                //商户交易数据汇总表
                return getMerchantTradeData(form);
            }

            case 17 : {
                //下载102开头的交易数据
                return getOneZeroTwoRiskTradeData(form);
            }

            case 18 : {
                List<RiskRefuseTemplate> resList = Lists.newArrayList();
                RiskRefuseTemplate template = new RiskRefuseTemplate();
                template.setAuthorisation("");
                template.setCardNo("");
                template.setTradeDateTemplate("");
                resList.add(template);
                return ExcelUtil.getExcelByte(RiskRefuseTemplate.class, resList);
            }

            case 19 : {
                List<OriTransNoTemplate> resList = Lists.newArrayList();
                OriTransNoTemplate template = new OriTransNoTemplate();
                template.setOriTransNo("");
                resList.add(template);
                return ExcelUtil.getExcelByte(OriTransNoTemplate.class, resList);

            }
            default:
                break;

        }
        return null;
    }

    private byte[] getOneZeroTwoRiskTradeData(DownloadDataForm form){
        List<RiskTradeData> resList = Lists.newArrayList();
        TradeDataParam param = new TradeDataParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        Integer count = riskMapper.countOneZeroStarter(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(RiskTradeData.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<RiskTradeData> queryRestList = riskMapper.selectOneZeroStarter(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = riskMapper.selectRiskTradeData(param);
        }

        return ExcelUtil.getExcelByte(RiskTradeData.class, resList);
    }


    //下载交易明细
    private byte[] getRiskTradeDetail(DownloadDataForm form){
        List<RiskTradeDetail> resList = Lists.newArrayList();
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        Integer count = riskMapper.countRiskTradeDetail(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(RiskTradeDetail.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<RiskTradeDetail> queryRestList = riskMapper.selectRiskTradeDetail(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = riskMapper.selectRiskTradeDetail(param);
        }

        return ExcelUtil.getExcelByte(RiskTradeDetail.class, resList);
    }





    //上传数据并下载
    public byte[] getUploadAndDownloadData(List<String[]> list, Integer type){
        switch (type){
            case 1 : {
                return getBankOrderData(list);
            }

            case 2 : {
                return getRiskRefuseData(list);
            }

            default : {
                return null;
            }
        }

    }

    private byte[] getRiskRefuseData(List<String[]> list) {
        TradeDataParam param = new TradeDataParam();
        List<String> tradDateList = Lists.newArrayList();
        List<String> cardNoList = Lists.newArrayList();
        List<String> grantList = Lists.newArrayList();

        for(int i = 0; i < list.size(); i++){
            //跳过第一行
            if(i == 0){
                continue;
            }
            if(list.get(i)[0] != null){
                tradDateList.add(list.get(i)[0]);
            }
            if(list.get(i)[1] != null){
                cardNoList.add(list.get(i)[1]);
            }
            if(list.get(i)[2] != null){
                grantList.add(list.get(i)[2]);
            }

        }
        if(tradDateList.size() != 0){
            param.setTradeDateList(tradDateList);
        }
        if(grantList.size() != 0){
            param.setGrantCodeList(grantList);
        }
        if(cardNoList.size() != 0){
            param.setCardNoList(cardNoList);
        }
        List<RiskTradeData> resList = Lists.newArrayList();
        Integer count = riskMapper.countRiskTradeData(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(RiskTradeData.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<RiskTradeData> queryRestList = riskMapper.selectRiskTradeData(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            riskMapper.selectRiskTradeData(param);
        }

        return ExcelUtil.getExcelByte(RiskTradeData.class, resList);
    }


    private byte[] getBankOrderData(List<String[]> list) {
        BankOrderParam param = new BankOrderParam();
        List<String> bankOrderNoList = Lists.newArrayList();
        for(int i = 0; i < list.size(); i++){
            //跳过第一行
            if(i == 0){
                continue;
            }
            bankOrderNoList.add(list.get(i)[0]);

        }
        if(bankOrderNoList.size() != 0){
            param.setBankOrderList(bankOrderNoList);
        }
        List<RiskBankOrder> resList = Lists.newArrayList();
        Integer count = riskMapper.countBankOrder(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(RiskBankOrder.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<RiskBankOrder> queryRestList = riskMapper.selectBankOrder(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            riskMapper.selectBankOrder(param);
        }

        return ExcelUtil.getExcelByte(RiskBankOrder.class, resList);
    }


    //下载渠道二级商户日报
    private byte[] getRiskChannelDailyReport(DownloadDataForm form){
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        List<RiskChannelDailyReport> resList = riskMapper.selectRiskChannelDailyReport(param);
        return ExcelUtil.getExcelByte(RiskChannelDailyReport.class, resList);
    }

    //商户交易数据汇总表
    private byte[] getMerchantTradeData(DownloadDataForm form){
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        List<RiskMerchantTrade> resList = riskMapper.selectRiskMerchantTrade(param);
        return ExcelUtil.getExcelByte(RiskMerchantTrade.class, resList);

    }

    //下载商户运营交易监控表
    private byte[] getRiskMerchantMonitorInfo(DownloadDataForm form){
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        List<RiskMerchantMonitor> resList = riskMapper.selectRiskMerchantMonitor(param);
        return ExcelUtil.getExcelByte(RiskMerchantMonitor.class, resList);
    }

    //下载每日交易变化数据
    private byte[] getRiskTradeChangeInfo(DownloadDataForm form) {
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        List<RiskTradeChange> resList = riskMapper.selectRiskTradeChange(param);
        return ExcelUtil.getExcelByte(RiskTradeChange.class, resList);
    }

    //



    //下载中银MGS退款订单
    private byte[] getMigsRefundOrderInfo(DownloadDataForm form){

        List<ClearingMigsRefundOrder> resList = Lists.newArrayList();
        BaseTradeParam param = new BaseTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        param.setStatus(form.getStatus());
        param.setOrderType("desc");
        List<String> orderByList = Lists.newArrayList();
        orderByList.add("createDate");
        Integer count = clearingMapper.countMigsRefundOrder(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(ClearingMigsRefundOrder.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<ClearingMigsRefundOrder> queryRestList = clearingMapper.selectMigsRefundOrder(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = clearingMapper.selectMigsRefundOrder(param);
        }

        return ExcelUtil.getExcelByte(ClearingMigsRefundOrder.class, resList);
    }

    //下载BalanceEntry表信息
    private byte[] getBalanceEntryInfo(DownloadDataForm form, Integer crdr, Long dealCode){
        List<ClearingEntry> resList = Lists.newArrayList();
        BalanceEntryQueryTradeParam param = new BalanceEntryQueryTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        param.setCrdr(crdr.shortValue());
        param.setDealCode(dealCode);
        param.setOrderType("desc");
        List<String> orderByList = Lists.newArrayList();
        orderByList.add("entryCreationDate");
        orderByList.add("entryBentryId");
        Integer count = balanceEntryMapper.countSelective(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(ClearingEntry.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<ClearingEntry> queryRestList = balanceEntryMapper.selectSelective(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = balanceEntryMapper.selectSelective(param);
        }

        return ExcelUtil.getExcelByte(ClearingEntry.class, resList);
    }


    //下载详细信息表
    private byte[] getOrderDetail(DownloadDataForm form){
        List<RiskOrderDetail> resList = Lists.newArrayList();

        SettlementTradeParam param = new SettlementTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        Integer count = clearingMapper.countOrderDetail(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(RiskOrderDetail.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<RiskOrderDetail> queryRestList = clearingMapper.selectOrderDetail(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = clearingMapper.selectOrderDetail(param);
        }

        return ExcelUtil.getExcelByte(RiskOrderDetail.class, resList);

    }



    //下载退款订单表
    private byte[] getRefundData(DownloadDataForm form){
        List<ClearingRefundOrder> resList = Lists.newArrayList();

        RefundOrderTradeParam param = new RefundOrderTradeParam();

        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        param.setStatus(form.getStatus());
        Integer count = clearingMapper.countRefundOrder(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(ClearingRefundOrder.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<ClearingRefundOrder> queryRestList = clearingMapper.selectRefundOrder(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = clearingMapper.selectRefundOrder(param);
        }

        return ExcelUtil.getExcelByte(ClearingRefundOrder.class, resList);

    }

    public byte[] getZipByte(ArrayList<File> files, List<byte[]> byteList, String zipFileName){
        for(int i=0; i<files.size(); i++){
            OutputStream output = null;
            BufferedOutputStream bufferedOutput = null;

            try {
                output = new FileOutputStream(files.get(i));
                bufferedOutput = new BufferedOutputStream(output);
                bufferedOutput.write(byteList.get(i));
                bufferedOutput.flush();
                output.flush();

            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }finally {
                try{
                    if(output != null){
                        output.close();
                    }
                    if(bufferedOutput != null){
                        bufferedOutput.close();
                    }
                }catch (Exception e){
                    logger.error(e.getMessage(), e);
                }

            }

        }
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        File file = null;
        try {
            ZipFile zipFile = new ZipFile("/" + zipFileName);
            File deleteFile = new File("/" + zipFileName);
            if(deleteFile.exists()){
                deleteFile.delete();
            }
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.createZipFile(files, parameters);

            file = new File("/" + zipFileName);
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1)
            {
                bos.write(b, 0, n);
            }
            return bos.toByteArray();

        }catch (Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }finally {
            try{
                if(fis != null){
                    fis.close();
                }
                if(bos != null){
                    bos.close();
                }
                if(file != null && file.exists()){
                    file.delete();
                }
                for(File file1 : files){
                    try {
                        if(file1 != null && file1.exists()){
                            file1.delete();
                        }
                    }catch (Exception e){
                        logger.error(e.getMessage(), e);
                    }
                }
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        }


    }


    //下载交易数据
    private byte[] getSettleByte(DownloadDataForm form, Integer settlementStatus) {
        List<ClearingOrderSettle> resList = Lists.newArrayList();

        SettlementTradeParam param = new SettlementTradeParam();

        List<String> orderByList = Lists.newArrayList();
        orderByList.add(CREATE_DATE);
        param.setOrderByColList(orderByList);
        param.setOrderType(ORDER_TYPE);
        param.setSettlementStatus(settlementStatus);
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());

        Integer count = clearingMapper.countSettleOrder(param);
        if(count == 0){
            return ExcelUtil.getExcelByte(ClearingOrderSettle.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<ClearingOrderSettle> queryRestList = clearingMapper.selectSettleOrder(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = clearingMapper.selectSettleOrder(param);
        }

        return ExcelUtil.getExcelByte(ClearingOrderSettle.class, resList);
    }

    //下载拒付数据
    private byte[] getRefuseData(DownloadDataForm form){
        RefuseOrderTradeParam param = new RefuseOrderTradeParam();
        param.setStartDate(form.getStartDate());
        param.setEndDate(form.getEndDate());
        Integer count = clearingMapper.countRefuseOrder(param);
        List<ClearingRefuseOrder> resList = Lists.newArrayList();
        if(count == 0){
            return ExcelUtil.getExcelByte(ClearingRefuseOrder.class, resList);
        }
        if(count > INCREASE_STEP){
            Integer num = count/INCREASE_STEP;
            for(int i=0; i<= num + 1; i++){
                getQueryIndex(param, count, num, i);
                List<ClearingRefuseOrder> queryRestList = clearingMapper.selectRefuseOrder(param);
                resList = ListUtils.union(resList, queryRestList);

            }
        }else{
            resList = clearingMapper.selectRefuseOrder(param);
        }

        return ExcelUtil.getExcelByte(ClearingRefuseOrder.class, resList);

    }




    public void getQueryIndex(BaseTradeParam param, Integer count, Integer num, int i) {
        param.setStartIndex(i*INCREASE_STEP);
        if(i<num+1){
            param.setEndIndex(i*INCREASE_STEP + INCREASE_STEP);

        }else{
            param.setEndIndex(count);
        }
    }

   /* public BaseTradeParam getQueryIndex(BaseTradeParam param, Integer count, Integer step){
        if(count > step){
            Integer
        }
    }*/


}
