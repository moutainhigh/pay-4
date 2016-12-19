<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">

function closePage() {
	var spMerchantId = document.getElementById('sp_merchant_id').value ;
	var url = "${ctx}/specialMerchantDetail.do?sp_merchant_id=" + spMerchantId;
	parent.closePage(url);
}
function processRet(){
		location.href = "${ctx}/specialMerchantInit.do";
}	
</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">特约商户详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>



<form method="post" id="specialMerchantFrom" name="specialMerchantFrom" action="${ctx}/specialMerchantEditForResult.do" enctype="multipart/form-data">
<input type="hidden" name="sp_merchant_id" id="sp_merchant_id" value="${specialmerchant.sp_merchant_id}" />
<table class="border_all2" width="700" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>商户编号：</td>
		<td class=border_top_right4>${specialMerchant.sp_merchant_id}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 >商户名称：</td>
		<td class=border_top_right4>${specialMerchant.sp_merchant_name}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>经营范围：</td>
		<td class=border_top_right4>${spEnumInfo.enumName}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>支持卡种及折扣信息：</td>
		<td class=border_top_right4>
			<c:forEach items="${spCardInfoList}" var="cardInfo"> 
			${cardInfo.enumName}：${cardInfo.discountInfo}<br>
			</c:forEach>
			&nbsp;
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4"  nowrap>所属地区：</td>
			<td class="border_top_right4" align="left" >${cityinfo.cityname}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户介绍：</td>
		<td class=border_top_right4>${specialMerchant.merchant_intro}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户地址：</td>
		<td class=border_top_right4>${specialMerchant.addr}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户联系电话：</td>
		<td class=border_top_right4>${specialMerchant.tel}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>网址：</td>
		<td class=border_top_right4>${specialMerchant.website_url}&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户小图：</td>
		<td class=border_top_right4>
			<c:if test="${not empty  specialMerchant.sp_merchant_logo}">
				<img id="imgimg1" src="${specialMerchant.sp_merchant_logo}" />
			</c:if>
			&nbsp;
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 nowrap>特约商户大图：</td>
		<td class=border_top_right4>
			<c:if test="${not empty specialMerchant.sp_merchant_logo_big}">
				<img id="imgimg2" src="${specialMerchant.sp_merchant_logo_big}" />
			</c:if>
			&nbsp;
		</td>
	</tr>
	<c:if test="${not empty info}">
				<li style="color: red;">${info }</li>
			</c:if>
</table>


<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td align="center" style="text-align: center">
		 	<input type="button" value="关闭" onclick="closePage()" />			
		</td>
	</tr>
</table>
</form>
</body>