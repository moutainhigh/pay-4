/**
 *  File: AcctAttribDto.java
 *  Description:withdrawOrder model
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-10      jack.liu_liu      Changes
 *  
 *
 */
package com.pay.poss.dto.withdraw.pay2account;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jack.liu_liu
 */
public class PacctAttribDto  implements Serializable{


    public PacctAttribDto()
    {
    }

    public Long getAttribId()
    {
        return attribId;
    }

    public void setAttribId(Long attribId)
    {
        this.attribId = attribId;
    }

    public String getAcctCode()
    {
        return acctCode;
    }

    public void setAcctCode(String acctCode)
    {
        this.acctCode = acctCode;
    }

    public Integer getAllowDeposit()
    {
        return allowDeposit;
    }

    public void setAllowDeposit(Integer allowDeposit)
    {
        this.allowDeposit = allowDeposit;
    }

    public Integer getAllowWithdrawal()
    {
        return allowWithdrawal;
    }

    public void setAllowWithdrawal(Integer allowWithdrawal)
    {
        this.allowWithdrawal = allowWithdrawal;
    }

    public Integer getAllowTransferIn()
    {
        return allowTransferIn;
    }

    public void setAllowTransferIn(Integer allowTransferIn)
    {
        this.allowTransferIn = allowTransferIn;
    }

    public Integer getAllowTransferOut()
    {
        return allowTransferOut;
    }

    public void setAllowTransferOut(Integer allowTransferOut)
    {
        this.allowTransferOut = allowTransferOut;
    }

    public Integer getAllowIn()
    {
        return allowIn;
    }

    public void setAllowIn(Integer allowIn)
    {
        this.allowIn = allowIn;
    }

    public Integer getAllowOut()
    {
        return allowOut;
    }

    public void setAllowOut(Integer allowOut)
    {
        this.allowOut = allowOut;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getFrozen()
    {
        return frozen;
    }

    public void setFrozen(Integer frozen)
    {
        this.frozen = frozen;
    }

    public Long getMemberCode()
    {
        return memberCode;
    }

    public void setMemberCode(Long memberCode)
    {
        this.memberCode = memberCode;
    }

    public Integer getDefRecAcct()
    {
        return defRecAcct;
    }

    public void setDefRecAcct(Integer defRecAcct)
    {
        this.defRecAcct = defRecAcct;
    }

    public String getCurCode()
    {
        return curCode;
    }

    public void setCurCode(String curCode)
    {
        this.curCode = curCode;
    }

    public String getPayPwd()
    {
        return payPwd;
    }

    public void setPayPwd(String payPwd)
    {
        this.payPwd = payPwd;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public Integer getAcctLevel()
    {
        return acctLevel;
    }

    public void setAcctLevel(Integer acctLevel)
    {
        this.acctLevel = acctLevel;
    }

    public Integer getBalanceBy()
    {
        return balanceBy;
    }

    public void setBalanceBy(Integer balanceBy)
    {
        this.balanceBy = balanceBy;
    }

    public Integer getPayAble()
    {
        return payAble;
    }

    public void setPayAble(Integer payAble)
    {
        this.payAble = payAble;
    }

    public Integer getAllowOverdraft()
    {
        return allowOverdraft;
    }

    public void setAllowOverdraft(Integer allowOverdraft)
    {
        this.allowOverdraft = allowOverdraft;
    }

    public Integer getNeedProtect()
    {
        return needProtect;
    }

    public void setNeedProtect(Integer needProtect)
    {
        this.needProtect = needProtect;
    }

    public Integer getManagerable()
    {
        return managerable;
    }

    public void setManagerable(Integer managerable)
    {
        this.managerable = managerable;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getBearInterest()
    {
        return bearInterest;
    }

    public void setBearInterest(Integer bearInterest)
    {
        this.bearInterest = bearInterest;
    }

    public Long getMemberAcctCode()
    {
        return memberAcctCode;
    }

    public void setMemberAcctCode(Long memberAcctCode)
    {
        this.memberAcctCode = memberAcctCode;
    }

    public Long getSubjectCode()
    {
        return subjectCode;
    }

    public void setSubjectCode(Long subjectCode)
    {
        this.subjectCode = subjectCode;
    }

    public String toString()
    {
        return (new StringBuilder()).append("AcctAttribDto [acctCode=").append(acctCode).append(", acctLevel=").append(acctLevel).append(", allowDeposit=").append(allowDeposit).append(", allowIn=").append(allowIn).append(", allowOut=").append(allowOut).append(", allowOverdraft=").append(allowOverdraft).append(", allowTransferIn=").append(allowTransferIn).append(", allowTransferOut=").append(allowTransferOut).append(", allowWithdrawal=").append(allowWithdrawal).append(", attribId=").append(attribId).append(", balanceBy=").append(balanceBy).append(", bearInterest=").append(bearInterest).append(", createDate=").append(createDate).append(", curCode=").append(curCode).append(", defRecAcct=").append(defRecAcct).append(", description=").append(description).append(", frozen=").append(frozen).append(", managerable=").append(managerable).append(", memberAcctCode=").append(memberAcctCode).append(", memberCode=").append(memberCode).append(", needProtect=").append(needProtect).append(", payAble=").append(payAble).append(", payPwd=").append(payPwd).append(", subjectCode=").append(subjectCode).append(", type=").append(type).append(", updateDate=").append(updateDate).append("]").toString();
    }

    public int hashCode()
    {
        int prime = 31;
        int result = 1;
        result = 31 * result + (acctCode != null ? acctCode.hashCode() : 0);
        result = 31 * result + (acctLevel != null ? acctLevel.hashCode() : 0);
        result = 31 * result + (allowDeposit != null ? allowDeposit.hashCode() : 0);
        result = 31 * result + (allowIn != null ? allowIn.hashCode() : 0);
        result = 31 * result + (allowOut != null ? allowOut.hashCode() : 0);
        result = 31 * result + (allowOverdraft != null ? allowOverdraft.hashCode() : 0);
        result = 31 * result + (allowTransferIn != null ? allowTransferIn.hashCode() : 0);
        result = 31 * result + (allowTransferOut != null ? allowTransferOut.hashCode() : 0);
        result = 31 * result + (allowWithdrawal != null ? allowWithdrawal.hashCode() : 0);
        result = 31 * result + (attribId != null ? attribId.hashCode() : 0);
        result = 31 * result + (balanceBy != null ? balanceBy.hashCode() : 0);
        result = 31 * result + (bearInterest != null ? bearInterest.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (curCode != null ? curCode.hashCode() : 0);
        result = 31 * result + (defRecAcct != null ? defRecAcct.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (frozen != null ? frozen.hashCode() : 0);
        result = 31 * result + (managerable != null ? managerable.hashCode() : 0);
        result = 31 * result + (memberAcctCode != null ? memberAcctCode.hashCode() : 0);
        result = 31 * result + (memberCode != null ? memberCode.hashCode() : 0);
        result = 31 * result + (needProtect != null ? needProtect.hashCode() : 0);
        result = 31 * result + (payAble != null ? payAble.hashCode() : 0);
        result = 31 * result + (payPwd != null ? payPwd.hashCode() : 0);
        result = 31 * result + (subjectCode != null ? subjectCode.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (updateDate != null ? updateDate.hashCode() : 0);
        return result;
    }

    private Long attribId;
    private String acctCode;
    private Integer allowDeposit;
    private Integer allowWithdrawal;
    private Integer allowTransferIn;
    private Integer allowTransferOut;
    private Integer allowIn;
    private Integer allowOut;
    private String description;
    private Integer frozen;
    private Long memberCode;
    private Integer defRecAcct;
    private String curCode;
    private String payPwd;
    private Date createDate;
    private Date updateDate;
    private Integer acctLevel;
    private Integer balanceBy;
    private Integer payAble;
    private Integer allowOverdraft;
    private Integer needProtect;
    private Integer managerable;
    private Integer type;
    private Integer bearInterest;
    private Long memberAcctCode;
    private Long subjectCode;
}

