package com.pay.poss.ipayenum;

import java.nio.channels.Channel;

/**
 * Created by songyilin on 2016/10/13 0013.
 */
public enum DownloadTypeEnum {

    SETTLED_ORDER_DOWNLOAD(1, "已清算订单表"),
    REFUSE_ORDER_DOWNLOAD(2, "拒付订单表"),
    DAILY_CLEARING_ENSURE_DOWNLOAD(3, "每日清算保证金表"),
    UNSETTILED_ORDER_DOWNLOAD(4, "未清算订单表"),
    REFUND_ORDER_DOWNLOAD(5, "退款订单表"),
    MIGS_DOWNLOAD(6, "中银MIGS退款订单表"),
    RETURN_ENSURE_DOWNLOAD(7, "归还保证金表"),
    DAILY_TRADE_DOWNLOAD(8, "每日交易数据表"),
    DETAIL_INFO_DOWNLOAD(9, "详细信息表"),
    RISK_FEE_DOWNLOAD(10, "风控手续费表"),
    DAILY_CLEARING_BASE_DOWNLOAD(11, "每日清算基本户表"),
    DAILY_TRADE_CHANGE_DOWNLOAD(12, "每日交易量变化报表"),
    DAILY_DETAIL_DOWNLOAD(13, "T-1日交易明细报表"),
    CHANNEL_DAILY_DOWNLOAD(14, "渠道二级商户号日报"),


    MERCHANT_MONITOR_DOWNLOAD(15, "商户运营交易监控表"),

    MERCHANT_TRADE_SUM_DOWNLOAD(16, "商户交易数据汇总表"),


    ONE_ZERO_TWO_START_TRADE_DOWNLOAD(17, "102开头的网关订单表"),

    REFUSE_DATA_DOWNLOAD(18, "拒付交易数据模板"),

    ORI_TRADE_DATA_DOWNLOAD(19, "原交易订单数据查询模板");


    private Integer type;

    private String info;

    DownloadTypeEnum(Integer type, String info) {
        this.type = type;
        this.info = info;
    }

    public Integer getType() {
        return type;
    }


    public void setType(Integer type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static String getByType(Integer type) {
        for (DownloadTypeEnum rate : DownloadTypeEnum.values()) {
            if (rate.getType() == type) {
                return rate.getInfo();
            }
        }
        return "下载文件";
    }
}
