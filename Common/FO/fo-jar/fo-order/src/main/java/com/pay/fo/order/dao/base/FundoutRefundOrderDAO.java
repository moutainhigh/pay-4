package com.pay.fo.order.dao.base;

import java.util.List;

import com.pay.fo.order.model.base.FundoutRefundOrder;
import com.pay.fo.order.model.base.FundoutRefundOrderExample;

public interface FundoutRefundOrderDAO {
    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    Long insert(FundoutRefundOrder record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int updateByPrimaryKey(FundoutRefundOrder record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int updateByPrimaryKeySelective(FundoutRefundOrder record);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    List selectByExample(FundoutRefundOrderExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    FundoutRefundOrder selectByPrimaryKey(Long orderId);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int deleteByExample(FundoutRefundOrderExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int deleteByPrimaryKey(Long orderId);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int countByExample(FundoutRefundOrderExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int updateByExampleSelective(FundoutRefundOrder record, FundoutRefundOrderExample example);

    /**
     * This method was generated by Abator for iBATIS.
     * This method corresponds to the database table FO.FUNDOUT_REFUNDORDER
     *
     * @abatorgenerated Mon Jul 25 14:49:57 CST 2011
     */
    int updateByExample(FundoutRefundOrder record, FundoutRefundOrderExample example);
}