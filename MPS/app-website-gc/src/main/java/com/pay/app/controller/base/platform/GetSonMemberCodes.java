package com.pay.app.controller.base.platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.model.PlatformMember;
import com.pay.base.service.platformmember.PlatformMemberService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/corp")
public class GetSonMemberCodes {
	
	@Autowired
	private PlatformMemberService platformMemberService;

	@RequestMapping("/getsonmember.do")
	@ResponseBody
	public  JSONArray sonMemberCodes(){
		JSONArray result=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		
		LoginSession loginSession = SessionHelper.getLoginSession();
		long fathermemberCode = loginSession.getFathermemberCode();
		PlatformMember platformMember=new PlatformMember();
		platformMember.setFather_member_code(fathermemberCode);
		platformMember.setStatus(2);
		List<PlatformMember> list=platformMemberService.getSonMemberByFatherCode(platformMember);
		if(list.size()>0){
			for(PlatformMember platformMember1:list){
				jsonObject.put("membercode", loginSession.getMemberCode());
				jsonObject.put("sonmembercode", platformMember1.getSon_member_code());
				jsonObject.put("operatorid", platformMember1.getSon_operator_id());
				result.add(jsonObject);
			}
		
		}else{
			result=null;
		}
		return result;
	}
}
