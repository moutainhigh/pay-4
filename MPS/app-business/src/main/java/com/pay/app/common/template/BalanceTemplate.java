package com.pay.app.common.template;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.ServiceLocator;
import com.pay.app.facade.cidverify.CidVerify2GovServiceFacade;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.model.Acct;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.linker.LinkerService;
import com.pay.base.service.matrixcard.IMatrixCardService;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class BalanceTemplate implements TemplateMethodModel{
	 private static AcctService acctService=ServiceLocator.getService(AcctService.class,"base-acctService");
	 private static IMatrixCardService matrixCardService=ServiceLocator.getService(IMatrixCardService.class,"matrixCardService");
	 private static CidVerify2GovServiceFacade verify2GovService=ServiceLocator.getService(CidVerify2GovServiceFacade.class,"app-cidVerify2GovServiceFacadeImpl");
	 private static LinkerService linkerService=ServiceLocator.getService(LinkerService.class,"base-linkerService");
	 public Object exec(List arguments) throws TemplateModelException {
		  LoginSession loginSession=SessionHelper.getLoginSession();
		  Map bmap=new HashMap(3);
		  BigDecimal balance=new BigDecimal(0.00);
		  int linkCount=0;
		  boolean boolvfy =false;
		  boolean isBind =false;
		  if(loginSession!=null && loginSession.getMemberCode()!=null){
		      try {
                Long memeberCode=new Long(loginSession.getMemberCode());
                  Acct acct =  acctService.getByMemberCode(memeberCode, AcctTypeEnum.BASIC_CNY.getCode());
                	if(acct!=null)
                	    //balance= FormatNumber.decimalsRound(acct.getBalance().divide(new BigDecimal(1000)),2);
                  boolvfy = verify2GovService.checkQueryCidVerify(loginSession.getMemberCode());
                  isBind = matrixCardService.isBindByMemberCode(memeberCode);
                  LinkerDTO ld=new LinkerDTO();
                 ld.setMemberCode(loginSession.getMemberCode());
                 linkCount=linkerService.queryLinkByCount(ld);
            } catch (Exception e) {
                e.printStackTrace();
            }
			
		  }
		  bmap.put("balance", balance);
          bmap.put("isBool", boolvfy);
          bmap.put("isBind", isBind);
          bmap.put("linkCount", linkCount);
		  return bmap;
	  }
	
	
}
