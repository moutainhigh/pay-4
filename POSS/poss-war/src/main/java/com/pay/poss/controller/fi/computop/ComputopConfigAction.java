package com.pay.poss.controller.fi.computop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComputopConfigAction {
	@RequestMapping(value="/ComputopConfigAction/intoMain.do")
	public String intoTest(){
		System.out.println("111111111111");
		return "/computop/ComputopConfig";
	}
}
 