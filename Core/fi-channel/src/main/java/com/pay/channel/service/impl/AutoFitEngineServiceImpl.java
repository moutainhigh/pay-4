package com.pay.channel.service.impl;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.channel.dao.*;
import com.pay.channel.dto.MemberCurrentConnectDTO;
import com.pay.channel.dto.PaymentChannelCountDTO;
import com.pay.channel.dto.SecondMerchantCountDTO;
import com.pay.channel.dto.UsableSecondMerchantDTO;
import com.pay.channel.model.*;
import com.pay.channel.redis.JedisQueue;
import com.pay.channel.redis.lock.NormalLock;
import com.pay.channel.redis.model.ChannelObj;
import com.pay.channel.service.AutoFitEngineService;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.TimeUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by eva on 2016/8/15.
 */
public class AutoFitEngineServiceImpl implements AutoFitEngineService {
    private final Log logger = LogFactory.getLog(getClass());
    private JedisConnectionFactory connectionFactory;
    private JedisQueue<ChannelObj> queue = new JedisQueue<ChannelObj>();
    private NormalLock normalLock = new NormalLock();
    private MemberCurrentConnectDAO memberCurrentConnectDAO;
    private PaymentChannelItemDAO paymentChannelItemDAO;
    private MemberConnectHisDAO memberConnectHisDAO;
    private PaymentChannelCountDAO paymentChannelCountDAO;
    private SecondMerchantCountDAO secondMerchantCountDAO;
    private ChannelSecondRelationDAO channelSecondRelationDAO;
    private UsableSecondMerchantDAO usableSecondMerchantDAO;
    private ChannelConfigDAO channelConfigDAO;
    private MemberSecondLimitDAO memberSecondLimitDAO;
    private EnterpriseBaseService enterpriseBaseService;
    private JmsSender jmsSender;



    public void setJmsSender(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

    public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
        this.enterpriseBaseService = enterpriseBaseService;
    }

    public void setMemberSecondLimitDAO(MemberSecondLimitDAO memberSecondLimitDAO) {
        this.memberSecondLimitDAO = memberSecondLimitDAO;
    }

    public void setQueue(JedisQueue<ChannelObj> queue) {
        this.queue = queue;
    }

    public void setNormalLock(NormalLock normalLock) {
        this.normalLock = normalLock;
    }

    public void setMemberConnectHisDAO(MemberConnectHisDAO memberConnectHisDAO) {
        this.memberConnectHisDAO = memberConnectHisDAO;
    }

    public void setPaymentChannelCountDAO(PaymentChannelCountDAO paymentChannelCountDAO) {
        this.paymentChannelCountDAO = paymentChannelCountDAO;
    }

    public void setSecondMerchantCountDAO(SecondMerchantCountDAO secondMerchantCountDAO) {
        this.secondMerchantCountDAO = secondMerchantCountDAO;
    }

    public void setChannelSecondRelationDAO(ChannelSecondRelationDAO channelSecondRelationDAO) {
        this.channelSecondRelationDAO = channelSecondRelationDAO;
    }

    public void setUsableSecondMerchantDAO(UsableSecondMerchantDAO usableSecondMerchantDAO) {
        this.usableSecondMerchantDAO = usableSecondMerchantDAO;
    }

    public void setChannelConfigDAO(ChannelConfigDAO channelConfigDAO) {
        this.channelConfigDAO = channelConfigDAO;
    }

    public MemberCurrentConnectDAO getMemberCurrentConnectDAO() {
        return memberCurrentConnectDAO;
    }

    public void setMemberCurrentConnectDAO(MemberCurrentConnectDAO memberCurrentConnectDAO) {
        this.memberCurrentConnectDAO = memberCurrentConnectDAO;
    }

    public PaymentChannelItemDAO getPaymentChannelItemDAO() {
        return paymentChannelItemDAO;
    }

    public void setPaymentChannelItemDAO(PaymentChannelItemDAO paymentChannelItemDAO) {
        this.paymentChannelItemDAO = paymentChannelItemDAO;
    }

    public void setConnectionFactory(JedisConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        queue.setConnectionFactory(connectionFactory);
        queue.setName("channelObj");
        queue.setClazz(ChannelObj.class);
        normalLock.setConnectionFactory(connectionFactory);
    }


    @Override
    public void product2Redis(ChannelObj obj) {
        //queue.push(obj);
        consum4Redis(obj);
    }

    @Override
    public void consum4Redis(ChannelObj obj) {
        try{
            if(!normalLock.tryLock("_channelObjLock"))
                normalLock.lock("_channelObjLock");
            //ChannelObj obj = queue.poll();
            if(obj != null){
                String dealType = obj.getDealType();

                if("1".equals(dealType)){
                    handlerTradeOrder(obj);
                }else if("2".equals(dealType)){
                    handlerAddConfig(obj);
                }else if("3".equals(dealType)){//删除2级商户号
                    handlerDelete(obj);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            normalLock.unLock("_channelObjLock");
        }
    }
    private void handlerDelete(ChannelObj obj){
        String subDealType = obj.getSubDealType();
        if("1".equals(subDealType)){//大表删除
            ChannelConfig channelConfig = channelConfigDAO.findById(obj.getChannelConfigId().longValue());
            PaymentChannelItem paymentChannelItem = new PaymentChannelItem();
            paymentChannelItem.setOrgCode(channelConfig.getOrgCode());
            List<PaymentChannelItem> findPaymentChannelIds = paymentChannelItemDAO.findByCriteria(paymentChannelItem);
            if("1".equals(channelConfig.getAucrFlag())){
                UsableSecondMerchantDTO searchDTO = new UsableSecondMerchantDTO();
                searchDTO.setChannelConfigId(new BigDecimal(channelConfig.getId()));
                List<UsableSecondMerchantDTO> usableSecondMerchantDTOs = usableSecondMerchantDAO.findPageResult(searchDTO);
                if(null!= usableSecondMerchantDTOs && usableSecondMerchantDTOs.size() >0){
                    for(UsableSecondMerchantDTO usableSecondMerchantDTO : usableSecondMerchantDTOs){
                        usableSecondMerchantDAO.deleteById(usableSecondMerchantDTO.getId());
                    }
                }
            }

            if(null != findPaymentChannelIds && findPaymentChannelIds.size() > 0) {
                for (PaymentChannelItem paymentChannelItemOne : findPaymentChannelIds
                        ) {
                    deleteManualRelate(channelConfig.getId(), paymentChannelItemOne);
                    dealDelPaymentChannelItemIdVChannelConfigId(channelConfig.getId(), paymentChannelItemOne, obj.getOperator());
                }
            }
        }else if("2".equals(subDealType)){//变成1.
            ChannelConfig channelConfig = channelConfigDAO.findById(obj.getChannelConfigId().longValue());
            channelConfig.setAucrFlag("0");
            channelConfigDAO.update(channelConfig);
            UsableSecondMerchantDTO searchDTO = new UsableSecondMerchantDTO();
            searchDTO.setChannelConfigId(obj.getChannelConfigId());
            List<UsableSecondMerchantDTO> usableSecondMerchantDTOs = usableSecondMerchantDAO.findPageResult(searchDTO);
            if(null!= usableSecondMerchantDTOs && usableSecondMerchantDTOs.size() >0){
                for(UsableSecondMerchantDTO usableSecondMerchantDTO : usableSecondMerchantDTOs){
                    usableSecondMerchantDAO.deleteById(usableSecondMerchantDTO.getId());
                }
            }
            PaymentChannelItem paymentChannelItem = new PaymentChannelItem();
            paymentChannelItem.setOrgCode(channelConfig.getOrgCode());
            List<PaymentChannelItem> findPaymentChannelIds = paymentChannelItemDAO.findByCriteria(paymentChannelItem);
            if(null != findPaymentChannelIds && findPaymentChannelIds.size() > 0) {
                for (PaymentChannelItem paymentChannelItemOne : findPaymentChannelIds
                        ) {
                    dealDelPaymentChannelItemIdVChannelConfigId(channelConfig.getId(), paymentChannelItemOne, obj.getOperator());
                }
            }
        }else if("3".equals(subDealType)){//删除自动配置
            MemberCurrentConnect mccOld = memberCurrentConnectDAO.findById(obj.getMemmberCurrentConnectId());
            PaymentChannelItem paymentChannelItem = paymentChannelItemDAO.findById(mccOld.getPaymentChannelItemId().longValue());
            deleteOldConnect(mccOld,obj.getOperator(),"6");
            if(!tryGetSecondMerChant(mccOld,paymentChannelItem)){
                String msg = "会员号[" + mccOld.getPartnerId() + "]在通道号[" +paymentChannelItem.getName()+"]没有可用2级通道号，请尽快匹配一个";
                this.sendAlertMsg(msg);
                logger.error("不能为会员[" +mccOld.getPartnerId() + "]在通道[" +  paymentChannelItem.getId() + "]匹配一个2级商户号");
            }
        }
    }

    private void deleteManualRelate(long channelConfigId, PaymentChannelItem paymentChannelItem){
        ChannelSecondRelation csr  = new ChannelSecondRelation();
        csr.setChannelConfigId(channelConfigId);
        csr.setPaymentChannelItemId(String.valueOf(paymentChannelItem.getId()));
        List<ChannelSecondRelation> channelSecondRelations = channelSecondRelationDAO.findByChannelSecondRelation(csr);
        for (ChannelSecondRelation channelSecondRelation:channelSecondRelations
             ) {
            channelSecondRelationDAO.delete(channelSecondRelation.getId());
        }
    }



    private void dealDelPaymentChannelItemIdVChannelConfigId(long channelConfigId, PaymentChannelItem paymentChannelItem, String operator){
        MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
        searchDto.setPaymentChannelItemId(new BigDecimal(paymentChannelItem.getId()));
        searchDto.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
        searchDto.setChannelConfigId(new BigDecimal(channelConfigId));
        List<MemberCurrentConnectDTO> memberCurrentConnectDTOs = memberCurrentConnectDAO.findPageResult(searchDto);
        if(null != memberCurrentConnectDTOs && memberCurrentConnectDTOs.size() > 0){
            for (MemberCurrentConnectDTO memberCurrentConnectDTO:memberCurrentConnectDTOs
                 ) {
                deleteOldConnect(memberCurrentConnectDTO,operator,"5");
                EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(memberCurrentConnectDTO.getPartnerId().longValue());
                String merchantCode = String.valueOf(enterpriseBaseDto.getMerchantCode());
                if(!merchantCode.startsWith("500")) {//ipaylink永远不超时
                    if(!tryGetSecondMerChant(memberCurrentConnectDTO,paymentChannelItem)){
                        String msg = "会员号[" + memberCurrentConnectDTO.getPartnerId() + "]在通道号[" +paymentChannelItem.getName()+"]没有可用2级通道号，请尽快匹配一个";
                        this.sendAlertMsg(msg);
                        logger.error("不能为会员[" +memberCurrentConnectDTO.getPartnerId() + "]在通道[" +  paymentChannelItem.getId() + "]匹配一个2级商户号");
                    }
                }
            }
        }
    }
    private void handlerAddConfig(ChannelObj obj){
        String subDealType = obj.getSubDealType();
        if("1".equals(subDealType)){//增加到自由匹配表
            save2UsableSecondMerchant(obj);
        }else if("2".equals(subDealType)){//手动为商户匹配一个商户号
            insertManualSetting(obj);
        }else if("3".equals(subDealType)){//手动为商户配置通道号
            autoSetMcc(obj);
        }
    }
    private void autoSetMcc(ChannelObj obj){
        BigDecimal partnerId = obj.getPartnerId();
        BigDecimal paymentChannelItemId = obj.getPaymentChannelItemId();
        MemberCurrentConnectDTO searchMccDto = new MemberCurrentConnectDTO();
        searchMccDto.setPartnerId(partnerId);
        searchMccDto.setPaymentChannelItemId(paymentChannelItemId);

        List<MemberCurrentConnectDTO> memberCurrentConnectDTOs = memberCurrentConnectDAO.findPageResult(searchMccDto);
        if(null != memberCurrentConnectDTOs && memberCurrentConnectDTOs.size() > 0){
            //之前没删除干净， 继续使用
        }else{
            PaymentChannelItem paymentChannelItem = paymentChannelItemDAO.findById(obj.getPaymentChannelItemId().longValue());
            searchMccDto.setCardOrg(paymentChannelItem.getOrgCode());
            if(!tryGetSecondMerChant(searchMccDto,paymentChannelItem)){
                String msg = "会员号[" + obj.getPartnerId() + "]在通道号[" +paymentChannelItem.getName()+"]没有可用2级通道号，请尽快匹配一个";
                this.sendAlertMsg(msg);
                logger.error("不能为会员[" +obj.getPartnerId() + "]在通道[" +  paymentChannelItem.getId() + "]匹配一个2级商户号");
            }
        }
    }
    private void insertManualSetting(ChannelObj obj){
        MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
        searchDto.setPaymentChannelItemId(obj.getPaymentChannelItemId());
        searchDto.setPartnerId(obj.getPartnerId());
        PaymentChannelItem paymentChannelItem = paymentChannelItemDAO.findById(obj.getPaymentChannelItemId().longValue());
        List<MemberCurrentConnectDTO> memberCurrentConnectDTOs = memberCurrentConnectDAO.findPageResult(searchDto);
        if(memberCurrentConnectDTOs != null && memberCurrentConnectDTOs.size() > 0){
            String overLimitFlag = overLimit(memberCurrentConnectDTOs.get(0),obj);
            if(!"0".equals(overLimitFlag)){
                MemberCurrentConnect mccNew = createManualMcc(obj,paymentChannelItem);
                memberCurrentConnectDAO.insert(mccNew);
                deleteOldConnect(memberCurrentConnectDTOs.get(0),"system",overLimitFlag);
            }
        }else {//没有给配置一个
            MemberCurrentConnect mccNew = createManualMcc(obj,paymentChannelItem);
            memberCurrentConnectDAO.insert(mccNew);
        }
    }

    private MemberCurrentConnect createManualMcc(ChannelObj obj,PaymentChannelItem paymentChannelItem){
        MemberCurrentConnect mccNew = new MemberCurrentConnect();
        mccNew.setPartnerId(obj.getPartnerId());
        mccNew.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
        mccNew.setPaymentChannelItemId(obj.getPaymentChannelItemId());
        mccNew.setChannelConfigId(obj.getChannelConfigId());
        mccNew.setConnectTime(new Date());
        mccNew.setCountTimes(BigDecimal.ZERO);
        mccNew.setCountAmount(BigDecimal.ZERO);
        mccNew.setManual("1");
        mccNew.setHasWarning("0");
        mccNew.setChannelSecondRelationId(obj.getChannelSecondRelationId());
        return mccNew;
    }

    private void save2UsableSecondMerchant(ChannelObj obj){
        ChannelConfig channelConfig = channelConfigDAO.findById(obj.getChannelConfigId().longValue());
        PaymentChannelItem paymentChannelItem = new PaymentChannelItem();
        paymentChannelItem.setOrgCode(channelConfig.getOrgCode());
        List<PaymentChannelItem> findPaymentChannelIds = paymentChannelItemDAO.findByCriteria(paymentChannelItem);

        if(null != findPaymentChannelIds && findPaymentChannelIds.size() > 0)
            for (PaymentChannelItem paymentChannelItemEach:findPaymentChannelIds
                    ) {
                UsableSecondMerchant usableSecondMerchant =  new UsableSecondMerchantDTO();
                MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
                searchDto.setPaymentChannelItemId(new BigDecimal(paymentChannelItemEach.getId()));
                searchDto.setChannelConfigId(new BigDecimal(channelConfig.getId()));
                List<MemberCurrentConnectDTO> memberCurrentConnects = memberCurrentConnectDAO.findPageResult(searchDto);
                usableSecondMerchant.setUseReference(null!=memberCurrentConnects&&memberCurrentConnects.size()>0?"1":"0");
                usableSecondMerchant.setChannelConfigId(new BigDecimal(channelConfig.getId()));
                usableSecondMerchant.setCardOrg(paymentChannelItemEach.getPaymentCategoryCode());
                usableSecondMerchant.setPaymentChannelItemId(new BigDecimal(paymentChannelItemEach.getId()));
                usableSecondMerchantDAO.insert(usableSecondMerchant);
            }
    }

    private void handlerTradeOrder(ChannelObj obj){
        logger.info("send back obj:" + obj);
        PaymentChannelItem paymentChannelItem = paymentChannelItemDAO.findById(obj.getPaymentChannelItemId().longValue());
        logger.info("query paymentChannelItem:no way can't find" + paymentChannelItem);
        savePaymentChannelCount(obj);
        saveSecondMerChantCount(obj, paymentChannelItem);
        saveMerchantCurrentConnect(obj, paymentChannelItem);
    }

    private void savePaymentChannelCount(ChannelObj obj){
        boolean insert =true;
        boolean updateDateBase = false;
        String dealType = obj.getDealType();
        String subDealType = obj.getSubDealType();
        PaymentChannelCountDTO paymentChannelCountDTO = new PaymentChannelCountDTO();
        paymentChannelCountDTO.setPaymentChannelItemId(obj.getPaymentChannelItemId());
        paymentChannelCountDTO.setFailureTimes(BigDecimal.ZERO);
        paymentChannelCountDTO.setAmount(BigDecimal.ZERO);
        paymentChannelCountDTO.setSuccessTimes(BigDecimal.ZERO);
        paymentChannelCountDTO.setMonthStamp(TimeUtil.getDate("yyyyMM"));
        List<PaymentChannelCountDTO> paymentChannelCountDTOs = paymentChannelCountDAO.findPageResult(paymentChannelCountDTO);
        if(null != paymentChannelCountDTOs && paymentChannelCountDTOs.size() > 0){
            paymentChannelCountDTO = paymentChannelCountDTOs.get(0);
            insert = false;
        }
        if(updateDateBase = ("1".equals(dealType) && "1".equals(subDealType))){//成功
            paymentChannelCountDTO.setSuccessTimes(new BigDecimal(1).add(paymentChannelCountDTO.getSuccessTimes()));//成功+1
            paymentChannelCountDTO.setAmount(obj.getAmount().add(paymentChannelCountDTO.getAmount()));
        }else if(updateDateBase = ("1".equals(dealType) && "2".equals(subDealType))){//失败
            paymentChannelCountDTO.setFailureTimes(new BigDecimal(1).add(paymentChannelCountDTO.getFailureTimes()));
        }
        PaymentChannelCount model = new PaymentChannelCount();
        BeanUtils.copyProperties(paymentChannelCountDTO,model);
        if(updateDateBase && insert){
            paymentChannelCountDAO.insert(model);
        }else if(updateDateBase){
            paymentChannelCountDAO.updateModelByModel(model);
        }
    }

    private void saveSecondMerChantCount(ChannelObj obj,PaymentChannelItem paymentChannelItem){
        boolean insert =true;
        boolean updateDateBase = false;
        String dealType = obj.getDealType();
        String subDealType = obj.getSubDealType();
        SecondMerchantCountDTO secondMerchantCountDTO = new SecondMerchantCountDTO();
        secondMerchantCountDTO.setPaymentChannelItemId(obj.getPaymentChannelItemId());
        secondMerchantCountDTO.setChannelConfigId(obj.getChannelConfigId());
        secondMerchantCountDTO.setFailureTimes(BigDecimal.ZERO);
        secondMerchantCountDTO.setAmount(BigDecimal.ZERO);
        secondMerchantCountDTO.setSuccessTimes(BigDecimal.ZERO);
        secondMerchantCountDTO.setMonthStamp(TimeUtil.getDate("yyyyMM"));
        List<SecondMerchantCountDTO> secondMerchantCountDTOs = secondMerchantCountDAO.findPageResult(secondMerchantCountDTO);
        if(null != secondMerchantCountDTOs && secondMerchantCountDTOs.size() > 0){
            secondMerchantCountDTO = secondMerchantCountDTOs.get(0);
            insert = false;
        }
        if(updateDateBase = ("1".equals(dealType) && "1".equals(subDealType))){//成功
            secondMerchantCountDTO.setSuccessTimes(new BigDecimal(1).add(secondMerchantCountDTO.getSuccessTimes()));//成功+1
            secondMerchantCountDTO.setAmount(obj.getAmount().add(secondMerchantCountDTO.getAmount()));
            secondMerchantCountDTO.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
        }else if(updateDateBase = ("1".equals(dealType) && "2".equals(subDealType))){//失败
            secondMerchantCountDTO.setFailureTimes(new BigDecimal(1).add(secondMerchantCountDTO.getFailureTimes()));
            secondMerchantCountDTO.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
        }

        SecondMerchantCount model = new SecondMerchantCount();
        BeanUtils.copyProperties(secondMerchantCountDTO,model);
        if(updateDateBase && insert){
            secondMerchantCountDAO.insert(model);
        }else if(updateDateBase){
            secondMerchantCountDAO.updateModelByModel(model);
        }
    }

    private void saveMerchantCurrentConnect(ChannelObj obj,PaymentChannelItem paymentChannelItem){
        boolean inHis =false;
        String dealType = obj.getDealType();
        String subDealType = obj.getSubDealType();
        if(null == obj.getId()){//没有当前的
            return;
        }
        MemberCurrentConnect mcc = memberCurrentConnectDAO.findById(obj.getId());
        inHis  = null == mcc;
        logger.info("find mcc:" + mcc + ",inHis:"  + inHis);
        if(!inHis){
            if("1".equals(subDealType)){//成功
                mcc.setCountTimes(new BigDecimal(1).add(mcc.getCountTimes()));
                mcc.setCountAmount(obj.getAmount().add(mcc.getCountAmount()));
                String overLimitFlag = overLimit(mcc,obj);
                if(!"0".equals(overLimitFlag)){
                    if(!tryGetSecondMerChant(mcc,paymentChannelItem)){
                        if(!"1".equals(mcc.getHasWarning())){
                            String msg = "会员号[" + mcc.getPartnerId() + "]在通道号[" +paymentChannelItem.getName()+"]没有可用2级通道号，请尽快匹配一个";
                            this.sendAlertMsg(msg);
                            logger.error("不能为会员[" +obj.getPartnerId() + "]在通道[" +  paymentChannelItem.getId() + "]匹配一个2级商户号");
                            mcc.setHasWarning("1");
                        }
                        memberCurrentConnectDAO.updateModelByModel(mcc);
                    }else{
                        deleteOldConnect(mcc,"system",overLimitFlag);
                    }
                }else{
                    memberCurrentConnectDAO.updateModelByModel(mcc);
                }
            }
        }else{
            MemberConnectHis mch = memberConnectHisDAO.findById(obj.getId());
            if(mch != null){
                if("1".equals(subDealType)) {//成功
                    mch.setCountTimes(new BigDecimal(1).add(mch.getCountTimes()));
                    mch.setCountAmount(obj.getAmount().add(mch.getCountAmount()));
                    memberConnectHisDAO.updateModelByModel(mch);
                }
            }else if("missSecond".equals(obj.getFailure())){
                this.findMissSecond(obj,paymentChannelItem);
            }

        }
    }
    private void findMissSecond(ChannelObj obj, PaymentChannelItem item){
        String msg = "会员号[" + obj.getPartnerId()+ "]在通道号[" +item.getName()+"]没有可用2级通道号，交易失败";
        MemberCurrentConnectDTO dto = new MemberCurrentConnectDTO();
        dto.setPartnerId(obj.getPartnerId());
        dto.setCardOrg(item.getPaymentCategoryCode());
        dto.setPaymentChannelItemId(new BigDecimal(item.getId()));
        MemberCurrentConnect mcc = new MemberCurrentConnect();
        mcc.setPartnerId(obj.getPartnerId());
        mcc.setPaymentChannelItemId(new BigDecimal(item.getId()));
        mcc.setCardOrg(item.getPaymentCategoryCode());
        if(!tryGetSecondMerChant(mcc,item)){
            logger.error("不能为会员[" +obj.getPartnerId() + "]在通道[" +  item.getId() + "]匹配一个2级商户号");
        }
        this.sendAlertMsg(msg);
    }


    private void deleteOldConnect(MemberCurrentConnect mcc, String operator, String deleteReason){
        memberCurrentConnectDAO.deleteById(mcc.getId());
        MemberConnectHis his = new MemberConnectHis();
        BeanUtils.copyProperties(mcc, his);
        his.setDeleteReason(deleteReason);
        his.setOperator(operator);
        memberConnectHisDAO.insert(his);
        if("1".equals(mcc.getManual())){
            ChannelSecondRelation deleteObj = new ChannelSecondRelation();
            deleteObj.setId(mcc.getChannelSecondRelationId().longValue());
            channelSecondRelationDAO.delete(deleteObj);
        }
        UsableSecondMerchantDTO searchDto2 = new UsableSecondMerchantDTO();
        searchDto2.setChannelConfigId(mcc.getChannelConfigId());
        searchDto2.setPaymentChannelItemId(mcc.getPaymentChannelItemId());
        List<UsableSecondMerchantDTO> usableSecondMerchants = usableSecondMerchantDAO.findPageResult(searchDto2);
        if(null != usableSecondMerchants && usableSecondMerchants.size() > 0){
            MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
            searchDto.setChannelConfigId(mcc.getChannelConfigId());
            searchDto.setPaymentChannelItemId(mcc.getPaymentChannelItemId());
            List<MemberCurrentConnectDTO> nowConnects = memberCurrentConnectDAO.findPageResult(searchDto);
            String useReference = null != nowConnects && nowConnects.size() > 0 ? "1" : "0";

            UsableSecondMerchant model = new UsableSecondMerchant();
            BeanUtils.copyProperties(usableSecondMerchants.get(0),model);
            model.setDeleteDate( new Date());
            model.setUseReference(useReference);
            usableSecondMerchantDAO.updateModelByModel(model);
        }
    }

    private boolean tryGetSecondMerChant(MemberCurrentConnect mcc,PaymentChannelItem paymentChannelItem){
        boolean find = false;
        boolean stayManual = false;
        EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(mcc.getPartnerId().longValue());
        boolean merchantTypeGc = String.valueOf(enterpriseBaseDto.getMerchantCode()).startsWith("800");
        stayManual = !merchantTypeGc;//ipaylink不会切回自动
        MemberCurrentConnect mccNew = findMccByManual(mcc,paymentChannelItem);
        if("1".equals(mcc.getManual()) && null != mcc.getChannelSecondRelationId()){//旧的是手动
            ChannelSecondRelation channelSecondRelation = (ChannelSecondRelation)channelSecondRelationDAO.findById(mcc.getChannelSecondRelationId().longValue());
            stayManual = null != channelSecondRelation;
        }
        if(null == mccNew && !stayManual){
            mccNew = findByFreePool(mcc,paymentChannelItem);
        }
        if(find=(null != mccNew)){
            memberCurrentConnectDAO.insert(mccNew);//保留新的
        }
        return find;
    }

    private MemberCurrentConnect findByFreePool(MemberCurrentConnect mcc,PaymentChannelItem paymentChannelItem){
        UsableSecondMerchant lookforBo = new UsableSecondMerchant();
        lookforBo.setChannelConfigId(mcc.getChannelConfigId());
        lookforBo.setPaymentChannelItemId(mcc.getPaymentChannelItemId());
        List<UsableSecondMerchantDTO> usableSecondMerchantDTOs = usableSecondMerchantDAO.findFreeSecondMerChantByPaymentChannelItemId(lookforBo);
        if(null != usableSecondMerchantDTOs && usableSecondMerchantDTOs.size() > 0){
            UsableSecondMerchantDTO usableSecondMerchantDTO = usableSecondMerchantDTOs.get(0);
            MemberCurrentConnect mccNew = new MemberCurrentConnect();
            mccNew.setPartnerId(mcc.getPartnerId());
            mccNew.setManual("0");
            mccNew.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
            mccNew.setHasWarning("0");
            mccNew.setPaymentChannelItemId(new BigDecimal(paymentChannelItem.getId()));
            mccNew.setConnectTime(new Date());
            mccNew.setChannelConfigId(usableSecondMerchantDTO.getChannelConfigId());
            mccNew.setCountAmount(BigDecimal.ZERO);
            mccNew.setCountTimes(BigDecimal.ZERO);
            mccNew.setRejectTimes(0l);
            usableSecondMerchantDTO.setUseReference("1");
            usableSecondMerchantDAO.updateModelByModel(usableSecondMerchantDTO);
            return mccNew;
        }
        return null;
    }

    private MemberCurrentConnect findMccByManual(MemberCurrentConnect mcc,PaymentChannelItem paymentChannelItem){
        List<ChannelSecondRelation> channelSecondRelations = channelSecondRelationDAO.
                findByMemberCode(String.valueOf(mcc.getPartnerId().longValue()),paymentChannelItem.getOrgCode());
        ChannelSecondRelation chooseOne = null;
        BigDecimal manualIdBig = mcc.getChannelSecondRelationId();
        Long manualId = manualIdBig != null ? manualIdBig.longValue() : null;
        if(null != channelSecondRelations && channelSecondRelations.size() > 0)
            for (ChannelSecondRelation channelSecondRelation:channelSecondRelations
                 ) {
                if(channelSecondRelation.getPaymentChannelItemId().equals(String.valueOf(paymentChannelItem.getId()))
                        && !channelSecondRelation.getId().equals(manualId)){
                    chooseOne  =  channelSecondRelation;
                }
            }
        if(null != chooseOne){
            MemberCurrentConnect mccNew = new MemberCurrentConnect();
            mccNew.setPartnerId(mcc.getPartnerId());
            mccNew.setCardOrg(paymentChannelItem.getPaymentCategoryCode());
            mccNew.setChannelConfigId(new BigDecimal(chooseOne.getChannelConfigId()));
            mccNew.setPaymentChannelItemId(mcc.getPaymentChannelItemId());
            mccNew.setManual("1");
            mccNew.setHasWarning("0");
            mccNew.setChannelSecondRelationId(new BigDecimal(chooseOne.getId()));
            mccNew.setConnectTime(new Date());
            mccNew.setCountAmount(BigDecimal.ZERO);
            mccNew.setCountTimes(BigDecimal.ZERO);
            mccNew.setRejectTimes(0l);
            return mccNew;
        }
        return null;
    }

    private String overLimit(MemberCurrentConnect mcc,ChannelObj obj){
        EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(mcc.getPartnerId().longValue());
        String merchantCode = String.valueOf(enterpriseBaseDto.getMerchantCode());
        if(merchantCode.startsWith("500")){//ipaylink永远不超时
            return "0";
        }else{
            MemberSecondLimit msl = findSencondeLimit(mcc.getCardOrg(), obj.getPartnerId());
            return checkOverLimit(msl,mcc);
        }
    }

    private String checkOverLimit(MemberSecondLimit msl, MemberCurrentConnect mcc) {
        if(msl == null)
            return "0";
        else{
            BigDecimal limitTimes = msl.getLimitTimes();
            BigDecimal limitAmount = msl.getLimitAmount();
            BigDecimal limitDay = msl.getLimitDay();
            BigDecimal nowTimes = mcc.getCountTimes();
            BigDecimal nowAmount = mcc.getCountAmount();
            Calendar connectDay = Calendar.getInstance();
            connectDay.setTime(mcc.getConnectTime());
            Calendar now = Calendar.getInstance();
            if(!noLimit(limitTimes))
                 if(overLimit(limitTimes, nowTimes))
                     return "1";
            if(!noLimit(limitAmount))
                if(overLimit(limitAmount, nowAmount))
                    return "2";
            if("1".equals(msl.getSwitchFlag()) && shouldSwitch(connectDay,now,msl.getCardOrg()))
                return "4";

            if(!noLimit(limitDay))
                if(shouldChange(connectDay,now,limitDay))
                    return "3";
            return "0";
        }
    }

    private boolean shouldChange(Calendar connectDay, Calendar now, BigDecimal limitDay){
        Calendar copyOfConnectDay = Calendar.getInstance();
        copyOfConnectDay.setTime(connectDay.getTime());
        copyOfConnectDay.add(Calendar.DATE, limitDay.intValue());
        if(now.compareTo(copyOfConnectDay) >= 0)
            return true;
        return false;
    }
    private boolean shouldSwitch(Calendar connectDay, Calendar now, String cardOrg){
        if("VISA".equals(cardOrg)){
            Calendar copyOfConnectDay28 = Calendar.getInstance();
            copyOfConnectDay28.setTime(connectDay.getTime());
            copyOfConnectDay28.set(Calendar.DAY_OF_MONTH, 28);

            Calendar copyOfNow28 = Calendar.getInstance();
            copyOfNow28.setTime(now.getTime());
            copyOfNow28.set(Calendar.DAY_OF_MONTH, 28);

            if(connectDay.compareTo(copyOfConnectDay28) >= 0){//新月的比较方式
                if(now.compareTo(copyOfNow28) >= 0 && now.get(Calendar.MONTH) != connectDay.get(Calendar.MONTH))
                    return true;
            }else{//旧月比较方式
                if(now.compareTo(copyOfConnectDay28) >= 0)
                    return true;
            }

        }else{
            if(connectDay.get(Calendar.YEAR) <  now.get(Calendar.YEAR)){//同年
                return true;
            }
            if(connectDay.get(Calendar.MONTH) <  now.get(Calendar.MONTH)){
                return true;
            }
        }

        return false;
    }

    private boolean overLimit(BigDecimal limit, BigDecimal now){
        return limit.compareTo(now) <= 0;
    }

    private boolean noLimit(BigDecimal limit){
        return null == limit || BigDecimal.ZERO.equals(limit);
    }


    private MemberSecondLimit findSencondeLimit(String cardOrg, BigDecimal partnerId) {
        MemberSecondLimit secondLimit = new MemberSecondLimit();
        secondLimit.setPartnerId(partnerId);
        secondLimit.setCardOrg(cardOrg);
        List<MemberSecondLimit> secondLimits = memberSecondLimitDAO.findPageResult(secondLimit);
        if(null != secondLimits && secondLimits.size() > 0)
            return secondLimits.get(0);
        secondLimit.setPartnerId(BigDecimal.ZERO);
        secondLimits = memberSecondLimitDAO.findPageResult(secondLimit);
        if(null != secondLimits && secondLimits.size() > 0)
            return secondLimits.get(0);
        return null;
    }

    private void sendAlertMsg(String msg){
        Map<String, String> data =  new HashMap<String, String>();
        data.put("first",msg);
        data.put("keyword1","二级商户号自动匹配告警");
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        data.put("keyword2",formatter.format(new Date()));
        data.put("remark","请尽快检查！");
        WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);

        request.setData(data);
        jmsSender.send(request);
    }

    public static void main(String[] args){
        Calendar now = Calendar.getInstance();
        Calendar copyOfConnectDay = Calendar.getInstance();
        copyOfConnectDay.setTime(now.getTime());
        copyOfConnectDay.set(Calendar.DAY_OF_MONTH, 28);
        System.out.println(copyOfConnectDay.get(Calendar.DAY_OF_MONTH));
    }

    public void addToFree(List<String> ids){
        if(ids != null && ids.size() >0){
            for (String idSingle:ids
                 ) {
                Long id = Long.parseLong(idSingle);
                dealOneToFree(id);
            }
        }
    }

    private void dealOneToFree(long id){
        ChannelConfig oneChannelConfig = channelConfigDAO.findById(id);
        oneChannelConfig.setAucrFlag("1");
        channelConfigDAO.update(oneChannelConfig);
        ChannelObj obj = new ChannelObj();
        obj.setDealType("2");
        obj.setSubDealType("1");//加入到自由表
        obj.setChannelConfigId(new BigDecimal(oneChannelConfig.getId()));
        this.product2Redis(obj);
    }

    public void deleteMemberSecondLimit(BigDecimal id){
        memberSecondLimitDAO.deleteById(id);
    }

    public void updateMemberSecondLimit(MemberSecondLimit memberSecondLimit){
        memberSecondLimitDAO.updateModelByModel(memberSecondLimit);
    }

    public void insertMemberSecondLimit(MemberSecondLimit memberSecondLimit) throws Exception{
        MemberSecondLimit searchDto = new MemberSecondLimit();
        searchDto.setCardOrg(memberSecondLimit.getCardOrg());
        searchDto.setPartnerId(memberSecondLimit.getPartnerId());
        List<MemberSecondLimit> memberSecondLimits = memberSecondLimitDAO.findPageResult(searchDto);
        if(null != memberSecondLimits&& memberSecondLimits.size() > 0){
            throw new Exception("不能重复添加");
        }
        memberSecondLimitDAO.insert(memberSecondLimit);
    }

}
