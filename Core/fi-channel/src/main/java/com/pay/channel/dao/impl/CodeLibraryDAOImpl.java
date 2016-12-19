package com.pay.channel.dao.impl;

import com.pay.channel.dao.CodeLibraryDAO;
import com.pay.channel.model.CodeLibrary;
import com.pay.inf.dao.impl.BaseDAOImpl;

import java.util.List;

/**
 * Created by cuber.huang on 2016/8/12.
 */
public class CodeLibraryDAOImpl extends BaseDAOImpl implements CodeLibraryDAO{

    public List<CodeLibrary> findAll(){
        return super.findAll("findAll");
    }

    @Override
    public List<CodeLibrary> findByType(String codeType) {
        return super.findByCriteria("findByType",codeType);
    }
}
