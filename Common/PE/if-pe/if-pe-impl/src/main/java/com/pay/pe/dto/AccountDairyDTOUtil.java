/**
 * 
 */
package com.pay.pe.dto;

import org.springframework.beans.BeanUtils;

import com.pay.pe.model.AccountDairy;



/**
 * g
 *
 */
public class AccountDairyDTOUtil {
    public static AccountDairy convertToModel(AccountDairyDTO dto) {
        if (null == dto) {
            return null;
        }
        AccountDairy accountDariy = new AccountDairy();
        BeanUtils.copyProperties(dto , accountDariy);
        return accountDariy;
    }
    public static AccountDairyDTO convertToDto(AccountDairy dairy) {
        if (null == dairy) {
            return null;
        }
        AccountDairyDTO dto = new AccountDairyDTO();
        BeanUtils.copyProperties(dairy , dto);
        return dto;
    }
}
