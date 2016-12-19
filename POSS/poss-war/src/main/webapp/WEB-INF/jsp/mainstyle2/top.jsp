<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<HTML>
<HEAD>
<TITLE>top菜单</TITLE>
<link href="${ctx}/css/head.css" rel="stylesheet" type="text/css" />

<style type="text/css">
body {
	background-color: #EAF5FF;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
</style>

<script type="text/javascript">

		$(document).ready(function(){

		//parent.frames["menuFrame"].location.href = "${ctx}/menu.do?method=left&menuCode="+${menuCode};
		
		
        $(".head_menu > li").mousedown(function(){ 
				$(".head_menu > li").addClass("noselect");
				$(this).removeClass("noselect");
				$(this).addClass("select");})						
		});
		
		//$(".head_menu > li:l_${menuCode}").addClass("select");
		//$("#l_${menuCode}").removeClass("noselect");
		//$("#l_${menuCode}").addClass("select");
		//alert($("#l_${menuCode}"));

		function processUrl(code){
			parent.frames["menuFrame"].location.href = "${ctx}/menu.do?method=left&menuCode=" + code;
		}
						
</script>



<BODY >
<table width="100%" height="149"  border="0" cellpadding="0" cellspacing="0" background="images/titlebg-3.jpg">
  <tr>
    <td width="100%" height="108" align="left" valign="top">
    <div class="head_top"></div>
	<div class="head_menu">
		<c:forEach var="item" items="${headMenu}">
			<li id="l_${item.code}" class="noselect" onmousedown="processUrl('${item.code}')">
				${item.text}
			</li>
		</c:forEach>
	</div>
	<br>
	</td>
  </tr>
  <tr>
    <td height="8" valign="top" bgcolor="003366" ><img src="images/spacer.gif" width="20" height="8"></td>
  </tr>
  <tr>
    <td height="30" valign="top" background="images/titlebg-6.jpg" class="topbg-x"><br></td>
  </tr>
</table>
</BODY>
</HTML>