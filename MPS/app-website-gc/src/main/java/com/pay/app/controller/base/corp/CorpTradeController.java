package com.pay.app.controller.base.corp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class CorpTradeController extends MultiActionController{
	//行业解决方案
	private String toIndex;   //首页
	private String toB2C;     //B2C
	private String toIdc;     //IDC
	private String toLotteryTicket; //彩票
	private String toAirlineTicket;  //机票
	private String toEducation;  //教育
	private String toGame;   //游戏
	private String toDirectSale; //直销
	
	 //首页
	public ModelAndView toIndex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return new ModelAndView(toIndex);
	}
	//B2C
	public ModelAndView toB2C(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toB2C);
		mv.addObject("select", "toB2C");
		return mv;
		//return new ModelAndView(toB2C);
	}
	//IDC
	public ModelAndView toIdc(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toIdc);
		mv.addObject("select", "toIdc");
		return mv;
		//return new ModelAndView(toIdc);
	}
	//彩票
	public ModelAndView toLotteryTicket(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toLotteryTicket);
		mv.addObject("select", "toLotteryTicket");
		return mv;
	}
	//机票
	public ModelAndView toAirlineTicket(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toAirlineTicket);
		mv.addObject("select", "toAirlineTicket");
		return mv;
		//return new ModelAndView(toAirlineTicket);
	}
	//教育
	public ModelAndView toEducation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toEducation);
		mv.addObject("select", "toEducation");
		return mv;
		//return new ModelAndView(toEducation);
	}
	//游戏
	public ModelAndView toGame(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toGame);
		mv.addObject("select", "toGame");
		return mv;
		//return new ModelAndView(toGame);
	}
	//直销
	public ModelAndView toDirectSale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(toDirectSale);
		mv.addObject("select", "toDirectSale");
		return mv;
		//return new ModelAndView(toDirectSale);
	}
	
	
	public String getToIndex() {
		return toIndex;
	}

	public void setToIndex(String toIndex) {
		this.toIndex = toIndex;
	}

	public String getToB2C() {
		return toB2C;
	}

	public void setToB2C(String toB2C) {
		this.toB2C = toB2C;
	}

	public String getToIdc() {
		return toIdc;
	}

	public void setToIdc(String toIdc) {
		this.toIdc = toIdc;
	}
	public String getToLotteryTicket() {
		return toLotteryTicket;
	}
	public void setToLotteryTicket(String toLotteryTicket) {
		this.toLotteryTicket = toLotteryTicket;
	}
	public String getToAirlineTicket() {
		return toAirlineTicket;
	}
	public void setToAirlineTicket(String toAirlineTicket) {
		this.toAirlineTicket = toAirlineTicket;
	}
	public String getToEducation() {
		return toEducation;
	}
	public void setToEducation(String toEducation) {
		this.toEducation = toEducation;
	}
	public String getToGame() {
		return toGame;
	}
	public void setToGame(String toGame) {
		this.toGame = toGame;
	}
	public String getToDirectSale() {
		return toDirectSale;
	}
	public void setToDirectSale(String toDirectSale) {
		this.toDirectSale = toDirectSale;
	}

	
}
