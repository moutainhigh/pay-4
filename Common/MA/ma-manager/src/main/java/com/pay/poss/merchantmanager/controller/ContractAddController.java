package com.pay.poss.merchantmanager.controller;



import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create
 */

public class ContractAddController extends SimpleFormController {

//	private Log log = LogFactory.getLog(ContractAddController.class);
//	private ContractServiceImpl contractService;
//	@Override
//	protected Map<Object,Object> referenceData(HttpServletRequest request) throws Exception {
//		log.debug("ContractAddController.referenceData() is running...");	
//		return null;
//	}
//	
//	@Override
//	protected ModelAndView onSubmit(HttpServletRequest request,
//			HttpServletResponse response, Object command, BindException errors)
//			throws Exception {
//		log.debug("ContractAddController.onSubmit() is running...");
//		ContractFormBean contractFormBean=(ContractFormBean) command;
//		String uploadDir = "E:\\upload";//上传文件路径
//		byte[] AnnexBytes = contractFormBean.getFile();
//               
//        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//        CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) multipartRequest.getFile("file");       
//
//        File file = new File(uploadDir);
//        if (!file.exists()) {
//        	file.mkdirs();
//        }
//        String sep = System.getProperty("file.separator");
//        log.debug("uploading to==> " + uploadDir + sep + commonsMultipartFile.getOriginalFilename());
//        File uploadedFile = new File(uploadDir + sep + commonsMultipartFile.getOriginalFilename());
//        FileCopyUtils.copy(AnnexBytes, uploadedFile);
//        log.debug("uploading filePath==>"+uploadedFile.getAbsolutePath());
//        BusinessContractBO businessContractBO = new BusinessContractBO();
//        try {
//			BeanUtils.copyProperties(businessContractBO,contractFormBean);
//			businessContractBO.setAnnex(uploadedFile.getAbsolutePath());
//		} catch (Exception e) {	
//			log.error("BeanUtils.copyProperties error");
//			e.printStackTrace();
//		}
//		long myReturn = contractService.insertContract(businessContractBO);
//		String sign ;
//		if(myReturn==1){
//			sign = "添加成功！";
//		}else{
//			sign = "添加成功！";
//		}
//		
//		log.debug("ContractAddController.onSubmit().sign==>"+sign);
//        return new ModelAndView(getSuccessView()).addObject("sign", sign);
//
//	}
//
//	
//	protected void initBinder(HttpServletRequest request,
//            ServletRequestDataBinder binder) throws ServletException {
//        binder.registerCustomEditor(byte[].class,new ByteArrayMultipartFileEditor());
//    }
//
//
//	
//
//	public void setContractService(ContractServiceImpl contractService) {
//		this.contractService = contractService;
//	}
//	

}
