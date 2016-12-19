<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/head.jsp"%>

<HTML>
<HEAD>
<TITLE>顶部页面</TITLE>
<style type="text/css">
td {
	font-size: 9pt;
}

a {
	text-decoration: none
}

a:hover {
	text-decoration: underline;
}

a:link {
	text-decoration: none
}

a:active {
	text-decoration: underline;
}

.big {
	font-size: 14.8px;
}

.cool {
	color: #000000
}

.double {
	height: 2px;
}

body {
	background-color: #EAF5FF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>
<META http-equiv=Content-Type content="text/html; charset=gb2312">
<script language="javascript">
function showUrl(menu,Url){
    parent.bodyFrame.addMenu(menu,Url);
}
/*删除角色*/
function processLogout() {
	if (confirm("确定退出？")) {
		parent.location.href = "${ctx}/j_spring_security_logout";
	}
}

</script>
<link href="css/topstyle.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {
	color: #FF0000
}

.style2 {
	color: #FFFFFF
}
-->
</style>
<body onbeforeunload="processLogout()">
<table width="100%" height="80" border="0" cellpadding="0" cellspacing="0" background="images/titlebg-3.jpg">
	<tr>
		<td width="100%" height="50" align="left" valign="top">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/titlebg-3.jpg">
			<tr>
				<td width="32%" height="50" align="left" valign="top"></td>
				<td width="29%" align="right" valign="top">
				<table width="129" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="10" colspan="3" align="center" valign="bottom"><!--<img src="images/tim-1.jpg" width="129" height="25">--></td>
					</tr>
					<tr>
						<td height="60" align="left" valign="top"><!--<img src="images/tim-3.jpg" width="1" height="64">--></td>
						<td width="127" align="center" valign="top">
						<table width="115" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="6"></td>
							</tr>
							<tr>
								<td width="115">
								<!--<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="30%"><img src="images/hour.gif" width="32"
											height="32"></td>
										<td width="70%" align="center" class="clock">06 : 30 : 20</td>
									</tr>
								</table>
								--></td>
							</tr>
							<tr>
								<td height="6"></td>
							</tr>
							<tr>
								<td height="12" align="center" valign="middle">
								<!--<input name="textfield" type="text" class="input1" size="18" style="height: 20px;">-->
								</td>
							</tr>
						</table>
						</td>
						<td width="1" align="right" valign="top"><!--<img src="images/tim-3.jpg" width="1" height="64">--></td>
					</tr>
					<tr>
						<td height="10" colspan="3"><!--<img src="images/tim-2.jpg" width="129" height="20">--></td>
					</tr>
				</table>
				</td>
				<td width="5" align="right" valign="top">&nbsp;</td>
				<td width="39%" align="right" valign="top">
				<table width="670" height="67" border="0" cellpadding="0"
					cellspacing="0">
					<tr>
						<td width="7" align="right" valign="top"><!--<img src="images/titlemenubg-1.gif" width="5" height="95">--></td>
						<td width="656" align="left" valign="top"  class="topbgx-b">
						<table width="100%" height="85" border="0" cellpadding="0"
							cellspacing="0">
							<tr>
								<td width="67%" height="37" align="center">
								<table width="399" height="30" border="0" cellpadding="0"
									cellspacing="0">
									<tr valign="bottom">
										<td width="48" align="right" nowrap class="zilao">编号：</td>
										<td width="48" align="left" nowrap class="ziliao2">${userKy}</td>
										<td width="49" align="right" nowrap class="zilao">姓名：</td>
										<td width="57" align="left" nowrap class="ziliao2">${userName}</td>
										<td width="45" align="right" nowrap class="zilao"></td>
										<td width="27" align="left" nowrap class="ziliao2"></td>
										<td width="49" align="right" nowrap class="zilao"></td>
										<td width="76" align="left" nowrap class="ziliao2"></td>
										<td width="76" align="left" nowrap class="ziliao2"><a href="javascript:processLogout()"><font color="#ffffff">退出登录</font></a></td>
										<td width="76" align="left" nowrap class="ziliao2"><a href="javascript:showUrl('修改密码','updatepassword.do')"><font color="#ffffff">修改密码</font></a></td>
									</tr>
								</table>
								</td>
								<td width="4%" align="left" ><!--<img src="images/titlemenubg-3.gif" width="29" height="42">--></td>
								<td width="29%" align="center" valign="bottom">
								<table width="90%" border="0" cellspacing="0" cellpadding="0">
									<tr align="center">
										<td><!--<img id="img_button0" src="images/button/zuoxi-1.gif" onClick="secBoard(0)" width="49" height="21" border="0" style="cursor:hand">--></a></td>
										<td><!--<img id="img_button1" src="images/button/huawu.gif" onClick="secBoard(1)" width="49" height="21" border="0" style="cursor:hand">--></a></td>
										<td><!--<img id="img_button2" src="images/button/fuzhu.gif" onClick="secBoard(2)" width="49" height="21" border="0" style="cursor:hand">--></a></td>
									</tr>
									<tr>
										<td height="4" colspan="3"></td>
									</tr>
								</table>
								</td>
							</tr>
							
						</table>
						</td>
						<td width="15" align="left" valign="top"><!--<img src="images/titlemenubg-4.gif" width="10" height="95">--></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td height="8" valign="top" bgcolor="003366"><img src="images/spacer.gif" width="20" height="8"></td>
	</tr>
	<tr>
		<td height="30" valign="top" background="images/titlebg-6.jpg" class="topbg-x">
		<table width="100%" border="0" cellpadding="0" cellspacing="0" background="images/titlebg-5.jpg" class="topbg-no">
			<tr>
				<td width="22%">&nbsp;</td>
				<td width="78%" class="xinxi">
				<table width="100%" height="37" border="0" cellpadding="0"
					cellspacing="0" class="topbg-no">
					<tr>
						<td width="56%" align="left" valign="middle" class="xinxi">&nbsp;</td>
						<td width="28%" align="right" valign="middle" nowrap class="xinxi">
						<!--<marquee direction="left" scrollamount="1">当前有<span class="style1">10</span>条未读信息</marquee>--></td>
						<!--<td width="16%" align="right" nowrap><img
							src="images/kj-1.gif" width="38" height="37"><img
							src="images/kj-2.gif" width="38" height="37"><img
							src="images/kj-3.gif" width="38" height="37"></td>
					--></tr>
				</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</BODY>
</HTML>