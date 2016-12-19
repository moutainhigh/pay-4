
package com.pay.pe.dto;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.pe.model.AccountSpecification;








/**
 * 
 * g
 *         <p>
 *         AcctSpecDTOUtil.java
 *         </p>
 * 
 */
public class AcctSpecDTOUtil {
    /**
     * Default constructor.
     * 
     */
    private AcctSpecDTOUtil() {
        super();
    }

    /**
     * 将AccountSpecification对象转换为AcctSpecDTO对象.
     * 
     * @param acctSpecList
     *            The AccountSpecification objects list.
     * @return List     
     */
    public static List convertAcctSpecToDto(final List acctSpecList){

        List result = new ArrayList();
        if (null == acctSpecList || 0 == acctSpecList.size()) {
            return result;
        } else {
            Iterator it = acctSpecList.iterator();
            while (it.hasNext()) {
                AccountSpecification acctSpec = (AccountSpecification) it
                        .next();
                AcctSpecDTO dto = convertAcctSpecToDto(acctSpec);
                result.add(dto);
            }
        }
        return result;

    }

    /**
     * 将AccountSpecification对象转换为AcctSpecDTO对象.
     * 
     * @param dtoList
     *            The AcctSpecDTO objects list.
     * @return List
     */
    public static List convertDtoToAcct(final List dtoList){
        List result = new ArrayList();
        if (null == dtoList || 0 == dtoList.size()) {
            return result;
        } else {
            Iterator it = dtoList.iterator();
            while (it.hasNext()) {
                AcctSpecDTO dto = (AcctSpecDTO) it.next();
                AccountSpecification acctSpec = convertDtoToAcctSpec(dto);
                result.add(acctSpec);
            }
        }

        return result;
    }

    /**
     * 将AccountSpecification对象转换为AcctSpecDTO对象.
     * 
     * @param acctSpec
     *            The AccountSpecification object.
     * @return AcctSpecDTO
     */
    public static AcctSpecDTO convertAcctSpecToDto(
            final AccountSpecification acctSpec){
        if (null == acctSpec) {
            return null;
        }
       
            AcctSpecDTO dto = new AcctSpecDTO();
            BeanUtils.copyProperties(acctSpec,dto);
            return dto;
       
    }

    /**
     * 将AcctSpecDTO对象转换为AccountSpecification对象.
     * 
     * @param dto
     *            The AcctSpecDTO object.
     * @return AccountSpecification
     */
    public static AccountSpecification convertDtoToAcctSpec(
            final AcctSpecDTO dto)  {
        if (null == dto) {
            return null;
        }
        
            AccountSpecification acctSpec = new AccountSpecification();
            BeanUtils.copyProperties(dto,acctSpec);
            return acctSpec;
        
    }
}
