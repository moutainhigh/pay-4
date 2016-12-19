package com.pay.pe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.paymentservice.PaymentServiceService;

public class PaymentServiceController extends MultiActionController{
	private static final Log log = LogFactory.getLog(PaymentServiceController.class);
	
	private String indexView;

	private String listView;
	
	private PaymentServiceService paymentServiceService;
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
//		String psCode = (String)request.getSession().getAttribute("queryps_psCode");
//		String psName = (String)request.getSession().getAttribute("queryps_psName");
		
		return new ModelAndView(indexView, model);
	}
	
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response
			) throws Exception {
		Map<String,Object> model=new HashMap<String,Object>() ;
		
		String psName = request.getParameter("psName");
		Integer psCode = null ;
		try{
			psCode = Integer.parseInt(request.getParameter("psCode"));
		}
		catch(Exception e){
		}
		
		request.getSession().setAttribute("queryps_psCode", psCode);
		request.getSession().setAttribute("queryps_psName", psName);
		
		
		PaymentServiceDTO dto  = new PaymentServiceDTO();
		dto.setPaymentservicecode(psCode);
		dto.setPaymentservicename(psName);
		dto.setPaymentServiceType(2);
		List < PaymentServiceDTO > pks =paymentServiceService.getAllPaymentServiceByDTO(dto);
		
//		List < PaymentServiceDTO > pks = paymentServiceService.getAllPaymentService();
		model.put("paymentServiceList", pks);
		return new ModelAndView(listView, model);
	}
//	
//	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
//			SubjectBalanceQueryCommand command) throws Exception {
//		Map<String,Object> model=new HashMap<String,Object>() ;
////		String orderId = request.getParameter("orderId") ;
////		if (StringUtil.isEmpty(orderId)) {
////			String errorMsg = "订单号不可以为空!";
////			model.put("error", errorMsg);
////			return new ModelAndView(listView, model);
////		}
////		List<QueryEntryDTO> resultList = entryQueryService.getEntrieListByOrderId(orderId);
//		model.put("resultList", resultList);
//		return new ModelAndView(listView, model);
//	}

	
	
	
	
	
//	 /**
//     * list.
//     * @param request
//     *            the HttpServletRequest to set.
//     * @param response
//     *            the HttpServletResponse to set.
//     * @return ModelAndView
//     */
//    public ModelAndView list(final HttpServletRequest request,
//            final HttpServletResponse response) throws Exception {
//        logger.info("list begin!");
//        List < PaymentServiceDTO > paymentServiceDTOList =
//            this.paymentServiceService.getAllPaymentService();
//        if ( null == paymentServiceDTOList) {
//            paymentServiceDTOList = new ArrayList();
//        }
//        logger.info("list end!");
//        return new ModelAndView(this.getSuccessView()).addObject("rsList", paymentServiceDTOList);
//    }
    
	
	 /**
     * remove.
     * @param request
     *            the HttpServletRequest to set.
     * @param response
     *            the HttpServletResponse to set.
     * @return ModelAndView
     */
//    public ModelAndView remove(final HttpServletRequest request,
//            final HttpServletResponse response) throws Exception {
//        logger.info("remove begin!");
//        String[] codes = request.getParameterValues("code");
//        logger.info("codes.length=" + codes.length);
//
//        int count = 0;
//        for (String code : codes) {
//            logger.info("code=" + code);
//            this.paymentServiceService.removePaymentService(code);
//            count++;
//        }
//        logger.info("success count=" + count);
//        logger.info("remove end!");
//
//        return new ModelAndView(this.redirectView);
//    }
	
	
	
	/**
     * add.
     * @param request
     *            the HttpServletRequest to set.
     * @param response
     *            the HttpServletResponse to set.
     * @param cmd
     *            the Object to set.
     * @param ex
     *            the BindException to set.
     * @return ModelAndView
     */
//    @Override
//    public ModelAndView onSubmit(final HttpServletRequest request,
//            final HttpServletResponse response, final Object cmd,
//            final BindException ex) throws Exception {
//
//        logger.info("add begin option!");
//
//        PaymentServiceCommand paymentServiceCommand =
//            (PaymentServiceCommand)cmd;
//        paymentServiceCommand.setPaymentservicename(paymentServiceCommand
//            .getPaymentservicename().trim());
//        if (this.paymentServiceService.isPaymentServiceExist(
//                paymentServiceCommand.getPaymentservicename()) 
//                ) {
//            BindException errors = new BindException(
//                    cmd, "paymentServiceCommand");
//            return new ModelAndView(this.getFormView(), referenceData(request))
//                .addObject("stauts", "isExistname")
//                .addAllObjects(errors.getModel());
//        }
//        if (null != this.paymentServiceService
//                .getPaymentService(paymentServiceCommand
//                    .getPaymentservicecode().toString())) {
//            BindException errors = new BindException(
//                cmd, "paymentServiceCommand");
//            return new ModelAndView(this.getFormView(), referenceData(request))
//            .addObject("stauts", "isExistcode").addAllObjects(errors.getModel());
//        }
//        logger.info("add begin!"); 
//
//        PaymentServiceDTO paymentServiceDTO = new PaymentServiceDTO();
//        paymentServiceDTO.setPaymentservicename(
//                paymentServiceCommand.getPaymentservicename());
//        paymentServiceDTO.setDescription(
//                paymentServiceCommand.getDescription());
//        paymentServiceDTO.setPaymentservicecode(paymentServiceCommand
//            .getPaymentservicecode());
//        if (!StringUtil.isNull(paymentServiceCommand.getFixedpayee())) {
//            paymentServiceDTO.setFixedpayee(paymentServiceCommand
//                .getFixedpayee());
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getFixedpayer())) {
//            paymentServiceDTO.setFixedpayer(paymentServiceCommand
//                .getFixedpayer());
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getGetmethod())) {
//            paymentServiceDTO.setGetmethod(Helper
//                .getGETMETHODValue(paymentServiceCommand.getGetmethod()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPaymethod())) {
//            paymentServiceDTO.setPaymethod(Helper
//                .getPAYMETHODValue(paymentServiceCommand.getPaymethod()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getTakeon())) {
//            paymentServiceDTO.setTakeon(Helper
//                .getTRANSACTORValue(paymentServiceCommand.getTakeon()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPaymentServiceType())) {
//            paymentServiceDTO.setPaymentServiceType(Helper
//                .getPAYMENTSERVICETYPEValue(paymentServiceCommand
//                    .getPaymentServiceType()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPayeetype())) {
//            paymentServiceDTO.setPayeetype(Helper
//                .getORGTYPEValue(paymentServiceCommand.getPayeetype()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPayertype())) {
//            paymentServiceDTO.setPayertype(Helper
//                .getORGTYPEValue(paymentServiceCommand.getPayertype()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPayeeAcctType())) {
//            paymentServiceDTO.setPayeeAcctType(Helper
//                .getACCTTYPEValue(paymentServiceCommand.getPayeeAcctType()));
//        }
//        if (!StringUtil.isNull(paymentServiceCommand.getPayerAcctType())) {
//            paymentServiceDTO.setPayerAcctType(Helper
//                .getACCTTYPEValue(paymentServiceCommand.getPayerAcctType()));
//        }
//        
//        paymentServiceDTO.setReservedCodeType(paymentServiceCommand.getReservedCodeType());
//        
//
//        this.paymentServiceService.addPaymentService(
//                paymentServiceDTO);
//        logger.info("add end!");
//        return new ModelAndView(this.getSuccessView());
//    }
    
	
	
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}

	public void setPaymentServiceService(PaymentServiceService paymentServiceService) {
		this.paymentServiceService = paymentServiceService;
	}

	public String getIndexView() {
		return indexView;
	}

	public String getListView() {
		return listView;
	}
	
	
}
