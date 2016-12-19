<%@ page pageEncoding="UTF-8"%>
<%
	//随机产生的认证码(4位数字)
	String sour = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	StringBuffer rand = new StringBuffer();
	for (int i = 0; i < 5; i++) {
		rand.append(sour.charAt((int) (Math.random() * 62)));
	}
	request.setAttribute("validateCode",rand.toString().toLowerCase());
%>