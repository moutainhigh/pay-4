/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.controller.base.linker;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.service.linker.LinkerService;

/**
 * 查询联系人
 * @author zhi.wang
 * @version $Id: QueryLinkerListController.java, v 0.1 2010-10-10 下午07:09:33 zhi.wang Exp $
 */
public class QueryLinkerListController extends MultiActionController {
    private String queryLinkerList;

    private LinkerService linkerService;

    public ModelAndView querylist(HttpServletRequest request, HttpServletResponse response)
                                                                                   throws Exception {
        LoginSession loginSession = SessionHelper.getLoginSession();
        String memberCode = loginSession.getMemberCode();
        List<LinkerDTO> list = linkerService.queryLinkerListBymemberCode(Long.valueOf(memberCode));
        
        return new ModelAndView(queryLinkerList).addObject("linkerList", list);
    }

    public void setLinkerService(LinkerService linkerService) {
        this.linkerService = linkerService;
    }

    public void setQueryLinkerList(String queryLinkerList) {
        this.queryLinkerList = queryLinkerList;
    }

}
