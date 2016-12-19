package com.pay.channel.dao;

import com.pay.channel.model.CodeLibrary;

import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public interface CodeLibraryDAO {
    List<CodeLibrary> findAll();
    List<CodeLibrary> findByType(String codeType);
}
