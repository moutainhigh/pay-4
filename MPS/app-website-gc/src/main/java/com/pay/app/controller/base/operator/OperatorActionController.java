/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.controller.base.operator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Operator;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.operator.statusEnum.OperatorStatusEnum;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;

/**
 * 操作员Action
 * 
 * @author wangzhi
 * @version $Id: OperatorActionController.java, v 0.1 2010-9-24 下午02:51:15
 *          wangzhi Exp $
 */
public class OperatorActionController extends MultiActionController {

    /** 操作员服务 */
    private OperatorServiceFacade operatorServiceFacade;
    /** 添加操作员的页面 */
    private String                addOperator;
    /** 修改操作员页面 */
    private String                editOperator;

    private String                login;

    private String                deleteOperatorSuccess;

    private String                updateOperatorLoginPwd;
    private String  updateOperatorPayPwd;
    /** 修改操作员 */
    private final static String   MODIFY       = "modify";
    /** 创建操作员 */
    private final static String   CREATE       = "create";
    
    /** 验证登录密码与支付是否相同  */
	private IMessageDigest iMessageDigest;

    /**
     * 创建或修改操作员
     * 
     * @param request
     * @param operator
     * @param memberCode
     * @param type
     * @return
     */
    private Map<String, Object> modifyOrCreateOperator(HttpServletRequest request,
                                                       Operator operator, Long memberCode,
                                                       String type) {
        // String[] menuIds = request.getParameterValues("chMenuId");
        String selectVal = request.getParameter("selectVal");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("operator", operator);
        // 操作员姓名不能为admin
        if (operator != null && StringUtils.equals(operator.getName(), "admin")) {
            map.put("errorMsg", "操作员姓名不能为admin");
            return map;
        }
        
        if(StringUtils.isBlank(operator.getName()) || operator.getName().length() < 2 || !CheckUtil.checkStringLength(operator.getName(), 32)){
        	map.put("errorMsg", MessageConvertFactory.getMessage("operatorNameError"));
            return map;
        }
        
        if(StringUtils.isBlank(operator.getDepart()) ||  !CheckUtil.checkStringLength(operator.getDepart(), 64)){
        	map.put("errorMsg", MessageConvertFactory.getMessage("operatorDepartError"));
            return map;
        }
        
        if(StringUtils.isNotEmpty(operator.getNote()) &&  !CheckUtil.checkStringLength(operator.getNote(), 128)){
        	map.put("errorMsg", MessageConvertFactory.getMessage("operatorNoteError"));
            return map;
        }
        if (StringUtils.isBlank(selectVal)) {
            String oldIdentity = request.getParameter("oldIdentity");
            if (StringUtils.isNotBlank(oldIdentity)) {
                operator.setIdentity(oldIdentity);
            }
            map.put("chErrorMsg", "isEmpty");
            return map;
        }
        // 获取功能权限ID列表
        List<Integer> list = new ArrayList<Integer>();

        String[] mStr = StringUtils.split(selectVal, ",");

        for (String mId : mStr) {
            try {
                if (!list.contains(Integer.valueOf(mId))) {
                    list.add(Integer.valueOf(mId));
                }
            } catch (NumberFormatException e) {
                map.put("errorMsg", "功能权限异常");
                return map;
            }
        }
        if (operator != null) {
            try {
                if (StringUtils.isNotBlank(operator.getCertCode())) {
                    // 设置操作员证件类型默认为身份证
                    operator.setCertType(1);
                }
                ResultDto result = null;
                operator.setMemberCode(memberCode);
                if (StringUtils.equals(type, CREATE)) {
                    // 设置操作员状态为正常
                    operator.setStatus(OperatorStatusEnum.NORMAL.getValue());
                    // 创建操作员
                    result = operatorServiceFacade.createOperatorMenuRnTx(operator, list);
                    map.put("operatorName", operator.getIdentity());
                } else {
                    result = operatorServiceFacade.modifyOperatorMenuRnTx(operator, list);
                    map.put("operatorName", request.getParameter("oldIdentity"));
                }
                map.put("memberCode", operator.getMemberCode());
                if (StringUtils.isBlank(result.getErrorMsg())) {
                    map.put("isSuccess", "yes");
                } else {
                    map.put("errorMsg", result.getErrorMsg());
                }
            } catch (Exception ex) {
                logger.error(type + "操作员异常！", ex);
                map.put("errorMsg", "系统暂时异常，请稍后再试。");
            }
        } else {
            map.put("checkMsg", "功能权限不能为空！");
        }

        return map;
    }

    /**
     * 添加操作员
     * 
     * @param request
     * @param response
     * @param operator
     * @return
     * @throws Exception
     */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_ADD")
    public ModelAndView addOperator(HttpServletRequest request, HttpServletResponse response,
                                    Operator operator) throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        String identity = operator.getIdentity();
        if(!CheckUtil.checkLoginName(identity)){
        	Map<String, Object> map = new HashMap<String, Object>();
            map.put("errorMsg",  MessageConvertFactory.getMessage("loginNamelengthError"));
        	return new ModelAndView(addOperator, map);
        }
        // 创建操作员
        Map<String, Object> map = this.modifyOrCreateOperator(request, operator, Long
            .valueOf(memberCode), CREATE);

        return new ModelAndView(addOperator, map);
    }

    /** 修改操作员 */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_EDIT")
    public ModelAndView editOperator(HttpServletRequest request, HttpServletResponse response,
                                     Operator operator) throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        // 修改操作员信息
        Map<String, Object> map = this.modifyOrCreateOperator(request, operator, Long
            .valueOf(memberCode), MODIFY);
        return new ModelAndView(editOperator, map);
    }

    /** 修改操作员登录密码 */
    
    public ModelAndView updateOperatorPwd(HttpServletRequest request, HttpServletResponse response,
                                          Operator operator) throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("operator", operator);
        // 修改操作员登录密码
        if (operator != null && operator.getOperatorId() > 0) {
        	 Operator op = operatorServiceFacade.getOperatorById(operator.getOperatorId());
         	if(op==null){
         		 map.put("errorMsg", "操作员不存在，请正确操作");
                  return new ModelAndView(updateOperatorPayPwd, map);
         	}
            // 操作员姓名不能为admin
            if (StringUtils.equals(operator.getName(), "admin") || StringUtils.equals(op.getName(), "admin")) {
                map.put("errorMsg", "不能修改管理员密码");
                return new ModelAndView(updateOperatorLoginPwd, map);
            }
            LoginSession loginSession = SessionHelper.getLoginSession();
            if (loginSession.getOperatorId() > 0) {
                if (!StringUtils.equals(operator.getIdentity(), loginSession.getOperatorIdentity())) {
                    map.put("errorMsg", "不能修改其他操作员密码");
                }
            }
    		
            //
            String newLoginPwd1 = operator.getPassword();
    		//验证登录密码是否包含特殊字符
    		if(CheckUtil.isPwdContainsSpecialCharacter(newLoginPwd1)){
    			map.put("errorMsg", MessageConvertFactory.getMessage("specialCharInLoginPwd"));
    			
    			return new ModelAndView(updateOperatorLoginPwd, map);
    		}
    		//登录密码格式不正确
    		if(!CheckUtil.checkLoginPwd(newLoginPwd1)){
    			map.put("errorMsg", MessageConvertFactory.getMessage("invalideLoginPwd"));
    			
    			return new ModelAndView(updateOperatorLoginPwd, map);
    		}
    		
    		// 验证支付密码和登录密码是否一致   start
    		Operator oldOperator = operatorServiceFacade.getOperatorById(operator.getOperatorId());
    		if(oldOperator != null){
    			if(oldOperator.getPayPassWord() != null && oldOperator.getPayPassWord().equals(iMessageDigest.genMessageDigest(newLoginPwd1.getBytes()))){
    				map.put("errorMsg", MessageConvertFactory.getMessage("errorForSamePassword"));

					return new ModelAndView(updateOperatorLoginPwd, map);
    			}
    		}    		
    		//验证支付密码和登录密码是否一致 end
    		
            try {
                if(operatorServiceFacade.updateOperatorPWD(operator.getPassword(), operator
                    .getOperatorId(),Long.valueOf(memberCode))){
                	 map.put("operatorName", request.getParameter("oldIdentity"));
                     map.put("memberCode", operator.getMemberCode());
                     map.put("isSuccess", "yes");
                }else{
                	 map.put("errorMsg", "无权限操作");
                }
               
            } catch (Exception ex) {
                logger.error("修改操作员登录密码异常！", ex);
                map.put("errorMsg", "系统暂时异常，请稍后再试");
            }
        }
        return new ModelAndView(updateOperatorLoginPwd, map);
    }
    
    
    /** 修改操作员支付密码 */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PAY_PWD")
    public ModelAndView updateOperatorPayPwd(HttpServletRequest request, HttpServletResponse response,
                                          Operator operator) throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("operator", operator);
        // 修改操作员登录密码
        if (operator != null && operator.getOperatorId() > 0) {
            // 操作员姓名不能为admin
        	 Operator op = operatorServiceFacade.getOperatorById(operator.getOperatorId());
        	if(op==null){
        		 map.put("errorMsg", "操作员不存在，请正确操作");
                 return new ModelAndView(updateOperatorPayPwd, map);
        	}
            if (StringUtils.equals(operator.getName(), "admin") || StringUtils.equals(op.getName(), "admin")) {
                map.put("errorMsg", "不能修改管理员支付密码");
                return new ModelAndView(updateOperatorPayPwd, map);
            }
            LoginSession loginSession = SessionHelper.getLoginSession();
            if (loginSession.getOperatorId() > 0) {
                if (!StringUtils.equals(operator.getIdentity(), loginSession.getOperatorIdentity())) {
                    map.put("errorMsg", "不能修改其他操作员支付密码");
                }
            }
            
            //
            String newPayPwd1 = operator.getPayPassWord();
    		//验证支付密码是否包含特殊字符
    		if(CheckUtil.isPwdContainsSpecialCharacter(newPayPwd1)){
    			map.put("errorMsg", MessageConvertFactory.getMessage("specialCharInPayPwd"));
    			
    			return new ModelAndView(updateOperatorPayPwd, map);
    		}
    		//支付密码格式不正确
    		if(!CheckUtil.checkPayPwd(newPayPwd1)){
    			map.put("errorMsg", MessageConvertFactory.getMessage("invalidePayPwd"));
    			
    			return new ModelAndView(updateOperatorPayPwd, map);
    		}
    		
    		// 验证支付密码和登录密码是否一致   start
    		
    		Operator oldOperator = operatorServiceFacade.getOperatorById(operator.getOperatorId());
    		if(oldOperator != null){
    			if(oldOperator.getPassword() != null && oldOperator.getPassword().equals(iMessageDigest.genMessageDigest(newPayPwd1.getBytes()))){
    				map.put("errorMsg", MessageConvertFactory.getMessage("errorForSamePassword"));

					return new ModelAndView(updateOperatorPayPwd, map);
    			}
    		}
			//验证支付密码和登录密码是否一致 end
    		
            try {
                if(operatorServiceFacade.updateOperatorPayPWD(Long.valueOf(memberCode), 
                		operator.getOperatorId(), operator.getPayPassWord())){
                	 map.put("operatorName", request.getParameter("oldIdentity"));
                     map.put("memberCode", operator.getMemberCode());
                     map.put("isSuccess", "yes");
                }else{
                	 map.put("errorMsg", "无权限操作");
                }
               
            } catch (Exception ex) {
                logger.error("修改操作员支付密码异常！", ex);
                map.put("errorMsg", "系统暂时异常，请稍后再试");
            }
        }
        return new ModelAndView(updateOperatorPayPwd, map);
    }

    /** 关闭操作员 */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_CLOSE")
    public ModelAndView closeOperator(HttpServletRequest request, HttpServletResponse response)
                                                                                               throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }
        String view = request.getParameter("view");
        String operatorName = request.getParameter("operatorName");
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(operatorName)) {
            try {
                Operator operator = operatorServiceFacade.getByIdentityMemCode(operatorName, Long
                    .valueOf(memberCode));
                if (operator != null) {
                	if (StringUtils.equals(operator.getName(), "admin")) {
                        map.put("errorMsg", "不能关闭管理员");
                    }else if(operator.getStatus() != OperatorStatusEnum.CLOSE.getValue()){
                        // 更新操作员状态为关闭
                        operatorServiceFacade.updateOperatorStatus(Long.valueOf(memberCode), operator
                            .getOperatorId(), OperatorStatusEnum.CLOSE.getValue());
                    }
                } 
            } catch (Exception ex) {
                logger.error("关闭操作员异常！", ex);
                map.put("errorMsg", "系统暂时异常，请稍后再试");
            }
        }
        if (StringUtils.equals(view, "login")) {
            // 返回登录信息页面
            return new ModelAndView("redirect:/corp/operatorManage.htm?method=showOperatorLogin&pager_offset="+request.getParameter("pager_offset"));
        } else {
            // 返回操作员管理信息页面
            return new ModelAndView("redirect:/corp/operatorManage.htm?method=showOperatorView&pager_offset="+request.getParameter("pager_offset"));
        }
            
    }

    /** 删除操作员 */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_DELETE")
    public ModelAndView deleteOperator(HttpServletRequest request, HttpServletResponse response)
                                                                                                throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }

        String operatorName = request.getParameter("operatorName");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("memberCode", memberCode);
        if (StringUtils.isNotBlank(operatorName)) {
            try {
                Operator operator = operatorServiceFacade.getByIdentityMemCode(operatorName, Long
                    .valueOf(memberCode));
                if (operator != null) {
                    // 更新操作员状态为删除
                	if (StringUtils.equals(operator.getName(), "admin")) {
                        	map.put("errorMsg", "不能删除管理员");
                       }else{
                    	   operatorServiceFacade.updateOperatorStatus(Long.valueOf(memberCode), operator
                                   .getOperatorId(), OperatorStatusEnum.DELETE.getValue());
                       }
                    
                    map.put("operatorName", operator.getIdentity());
                } else {
                    map.put("operatorName", operatorName);
                    map.put("errorMsg", "操作员不存，无法删除");
                }
            } catch (Exception ex) {
                logger.error("删除操作员异常！", ex);
                map.put("operatorName", operatorName);
                map.put("errorMsg", "系统暂时异常，请稍后再试");
            }
        } else {
            map.put("errorMsg", "操作员不存在，无法删除");
        }
        return new ModelAndView(deleteOperatorSuccess, map);

    }
    
    /** 获取当前登录者的MemberCode */
    private String getMemberCode(HttpServletRequest request) {
        // return request.getParameter("memberCode");
        if (request.getSession().getAttribute("memberCode") == null) {
            return "";
        }
        LoginSession loginSession = SessionHelper.getLoginSession();
        return loginSession.getMemberCode();

    }

    /** 开通操作员 */
    @OperatorPermission(operatPermission = "OPERATOR_MANAGE_OPEN")
    public ModelAndView openOperator(HttpServletRequest request, HttpServletResponse response)
                                                                                              throws Exception {
        String memberCode = this.getMemberCode(request);
        if (StringUtils.isBlank(memberCode)) {
            return new ModelAndView(login);
        }

        String operatorName = request.getParameter("operatorName");
        String view = request.getParameter("view");
        if (StringUtils.isNotBlank(operatorName) && StringUtils.isNotBlank(memberCode)) {
            try {
                Operator operator = operatorServiceFacade.getByIdentityMemCode(operatorName, Long
                    .valueOf(memberCode));
                if (operator != null) {
                    if(operator.getStatus() != OperatorStatusEnum.NORMAL.getValue()){
                        // 更新操作员状态为正常
                        operatorServiceFacade.updateOperatorStatus(Long.valueOf(memberCode), operator
                            .getOperatorId(), OperatorStatusEnum.NORMAL.getValue());
                    }
                } 
            } catch (Exception ex) {
                logger.error("开通操作员:"+operatorName+"异常！", ex);
            }
        }

        if (StringUtils.equals(view, "login")) {
            // 返回登录信息页面
            return new ModelAndView("redirect:/corp/operatorManage.htm?method=showOperatorLogin&pager_offset="+request.getParameter("pager_offset"));
        } else {
            // 返回操作员管理信息页面
            return new ModelAndView("redirect:/corp/operatorManage.htm?method=showOperatorView&pager_offset="+request.getParameter("pager_offset"));
        }
    }

    public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }

    public void setAddOperator(String addOperator) {
        this.addOperator = addOperator;
    }

    public void setEditOperator(String editOperator) {
        this.editOperator = editOperator;
    }

    public void setDeleteOperatorSuccess(String deleteOperatorSuccess) {
        this.deleteOperatorSuccess = deleteOperatorSuccess;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setUpdateOperatorLoginPwd(String updateOperatorLoginPwd) {
        this.updateOperatorLoginPwd = updateOperatorLoginPwd;
    }

	public String getUpdateOperatorPayPwd() {
		return updateOperatorPayPwd;
	}

	public void setUpdateOperatorPayPwd(String updateOperatorPayPwd) {
		this.updateOperatorPayPwd = updateOperatorPayPwd;
	}

	

	

	/**
	 * @return the iMessageDigest
	 */
	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	/**
	 * @param iMessageDigest the iMessageDigest to set
	 */
	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
	
}
