<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">
<script language="javascript">

function search() {
	var re2 = /^\d{1,11}$/;
	var partnerid =  $('#partnerid').val() ;

	if(partnerid.length>0){
		if(!re2.test(partnerid)) {
			alert('商户号：只能输入1-11位的数字');
			return false;
	 	}
	}else{
		alert('请输入商户号');
		return false;
	}
	var pars = "channelconfig.htm?method=channelQuery&" + $("#mainfromId").serialize()+ "&partner_Id=" + $("#partnerid").val();
	window.location.href=pars;
}
</script>
</head>

<body>
<table class="border_all2" width="60%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" >商户号：</td>
      <td class="border_top_right4">&nbsp;
      	<input type="text" name="partnerid" id="partnerid"  value="${partner_Id}" />&nbsp;
      	<input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
      </td>
     </tr>
</table>
<input type="hidden" name="isPreCard" value="${isPreCard}"/>
<input type="hidden" name="isRecCard" value="${isRecCard}"/>
 <div id="resultListDiv" class="listFence"></div>
  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>  
</body>
</html>
