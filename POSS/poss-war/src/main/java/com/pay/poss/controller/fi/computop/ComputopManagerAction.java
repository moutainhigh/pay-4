package com.pay.poss.controller.fi.computop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ComputopManagerAction {
	@RequestMapping(value="/ComputopManagerAction/intoMain.do")
	public String intoTest(){
		System.out.println("111111111111");
		return "/computop/ComputopManager";
	}
}
