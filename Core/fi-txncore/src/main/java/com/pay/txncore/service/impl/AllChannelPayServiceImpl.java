package com.pay.txncore.service.impl;

import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dto.*;
import com.pay.txncore.model.TradeBase;
import com.pay.txncore.service.CashierPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.txncore.service.AllChannelPayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by cuber.huang on 2016/7/22.
 */
public class AllChannelPayServiceImpl implements AllChannelPayService{

    private final Log logger = LogFactory.getLog(getClass());

    private CashierPayService cashierPayService;

    private PaymentService paymentService;

    private TradeOrderService tradeOrderService;

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    public CashierPayService getCashierPayService() {
        return cashierPayService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setCashierPayService(CashierPayService cashierPayService) {
        this.cashierPayService = cashierPayService;
    }

    @Override
    public PaymentResult dispatchPay(PaymentInfo info) throws Exception{
        if("000".equals(info.getOrgCode())){//国际化支付
            info.setTradeType("1000");
            return cashierPayService.crossCashierPay(info);
        }else{
            info.setTradeType("4000");
            return localCashierPay(info);
        }
    }

    private PaymentResult localCashierPay(PaymentInfo paymentInfo) {
        PaymentResult paymentResult = new PaymentResult();
        Long tradeOrderNo = paymentInfo.getTradeOrderNo();
        TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
                .queryTradeOrderById(Long.valueOf(tradeOrderNo));
        // 订单不存在
        if (null == tradeOrderDTO) {
            throw new BusinessException("tradeOrder not exists",
                    ExceptionCodeEnum.ORDER_NOT_EXIST);
        }
        if (tradeOrderDTO.getStatus() == TradeOrderStatusEnum.PROCESSING
                .getCode()) {
            throw new BusinessException("tradeOrder is processing",
                    ExceptionCodeEnum.ORDER_DOUBLED);
        }
        // 订单已支付
        if (tradeOrderDTO.getStatus() != TradeOrderStatusEnum.WAIT_PAY
                .getCode()) {
            throw new BusinessException("tradeOrder was payment",
                    ExceptionCodeEnum.TRANSACTIONS_PAYMENTS);
        }
        // 基础订单
        TradeBaseDTO tradeBaseDTO = paymentService.getTradeOrderService()
                .queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
        TradeExtendsDTO tradeExtendsDTO = paymentService.getTradeExtendsService()
                .findByTradeOrderNo(Long.valueOf(tradeOrderNo));
        if (null == tradeExtendsDTO) {
            throw new BusinessException("TradeExtends not exists",
                    ExceptionCodeEnum.ORDER_NOT_EXIST);
        }

        // 更新 TradeExtendsDTO 2016-05-23 sch.
        paymentService.updateTradesExtendsDTO(tradeExtendsDTO, paymentInfo);
        TradeBase tradeBase = new TradeBase();
        BeanUtils.copyProperties(tradeBaseDTO, tradeBase);
        tradeBase.setOrgCode(paymentInfo.getOrgCode());//将客户选的orgCode放入到tradeBase
        tradeOrderService.updateTradeBase(tradeBase);
        // end 2016-05-23
        // 更新交易数据
        tradeOrderDTO.setTradeType(paymentInfo.getTradeType());
        paymentService.updateTradeDataNew(paymentInfo,tradeOrderDTO);
        //风控校验
        paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        if(!"ACCEPT".equals(desc)){
            return paymentResult;
        }
        tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode()); // 网关订单状态改为订单处理中
        boolean updateResult = tradeOrderService.updateTradeOrderRnTx(
                tradeOrderDTO, TradeOrderStatusEnum.WAIT_PAY.getCode());
        if (!updateResult) {
            logger.info("set processing status to trade order is failed.");
        }
        try {
            paymentResult = paymentService.pay4All(paymentInfo, tradeOrderDTO,
                    tradeBaseDTO, PaymentWayEnum.API.getCode());
        } catch (Exception e) {
            logger.error("payment error:", e);
            paymentResult.setResponseCode("3099");
            paymentResult.setResponseDesc("Other Error:其他异常");

            tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
            tradeOrderDTO.setRespCode("3099");
            tradeOrderDTO.setCompleteDate(new Date());
            tradeOrderDTO.setRespMsg("Other Error:其他异常");
            paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
            return paymentResult;
        }
        return paymentResult;
    }
}
