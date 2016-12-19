package com.pay.pe.dto;

import java.util.ArrayList;
import java.util.List;

import com.pay.pe.model.AccountEntry;



/**
 *g
 */
public class AccountEntryDTOUtil {

    public static AccountEntryDTO convertAccountEntryToDTO(AccountEntry accountEntry) {
        if (accountEntry == null) return null;
        
        AccountEntryDTO dto = new AccountEntryDTO();
        
        dto.setCrdr(accountEntry.getCrdr());
        dto.setCreatedate(accountEntry.getCreationDate());
        dto.setEntrycode(accountEntry.getEntryCode());
        dto.setDealId(accountEntry.getDealId());
        dto.setPaymentServiceId(accountEntry.getPaymentServiceCode());
        dto.setStatus(accountEntry.getStatus());
        dto.setText(accountEntry.getText());
        dto.setValue(accountEntry.getValue());
        dto.setVouchercode(accountEntry.getVoucherCode());
        dto.setAcctcode(accountEntry.getAcctCode());
        dto.setTransactiondate(accountEntry.getTransactionDate());//增加交易时间
        return dto;
    }
    
    
    public static List <AccountEntryDTO> convertAccountEntryToDTO(List<AccountEntry> accountEntry) {
        List <AccountEntryDTO> dtoList = new ArrayList <AccountEntryDTO>();
        if (accountEntry == null || accountEntry.size() == 0) return dtoList;
        
        for (Object o : accountEntry) {
            dtoList.add(convertAccountEntryToDTO((AccountEntry) o));
        }
        return dtoList;
    }
    
    
    public static AccountEntry convertDTOtoAccountEntry(AccountEntryDTO accountEntryDTO) {
        if (accountEntryDTO == null) return null;
        
        AccountEntry accountEntry = new AccountEntry();
        accountEntry.setMaBlanceBy(accountEntryDTO.getMaBlanceBy());
        accountEntry.setCrdr(accountEntryDTO.getCrdr());
        accountEntry.setCreationDate(accountEntryDTO.getCreatedate());
        accountEntry.setEntryCode(accountEntryDTO.getEntrycode());
        accountEntry.setDealId(accountEntryDTO.getDealId());
        accountEntry.setPaymentServiceCode(accountEntryDTO.getPaymentServiceId());
        accountEntry.setStatus(accountEntryDTO.getStatus());
        accountEntry.setText(accountEntryDTO.getText());
        accountEntry.setValue(accountEntryDTO.getValue());
        accountEntry.setVoucherCode(accountEntryDTO.getVouchercode());
        accountEntry.setCurrencyCode(accountEntryDTO.getCurrencyCode());
        accountEntry.setExchangeRate(accountEntryDTO.getExchangeRate());
        accountEntry.setAcctCode(accountEntryDTO.getAcctcode());
        accountEntry.setTransactionDate(accountEntryDTO.getTransactiondate());//增加交易时间
        return accountEntry;
    }
    
    
    public static List <AccountEntry> convertDTOtoAccountEntry(List<AccountEntryDTO> accountEntryDTO) {
        List <AccountEntry> entries = new ArrayList <AccountEntry>();
        if (accountEntryDTO == null || accountEntryDTO.size() == 0) return entries;
        
        for (Object o : accountEntryDTO) {
            entries.add(convertDTOtoAccountEntry((AccountEntryDTO) o));
        }
        return entries;
    }
}
