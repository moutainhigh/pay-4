<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function specialMerchantQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#merchantSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize ;
	
	$.ajax({
		type: "POST",
		url: "${ctx}/specialMerchantList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function AddFrom() {
	//location.href = "${ctx}/specialMerchantAdd.do";
	parent.addMenu("添加特约商户",'${ctx}/specialMerchantAdd.do');
}

function validFrom() {
	specialMerchantQuery();
}
$(document).ready(function(){validFrom();});
</script>
</head>

<body>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext"> 特约商户信息查询 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="merchantSearchFormBean" name="merchantSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" nowrap>商户名称：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="spMerchantName" name="spMerchantName" value='${query_spMerchantName}'></td>
		
		<td class="border_top_right4" align="right" nowrap>地区：</td>
		<td class="border_top_right4" align="left" >
		<td class="border_top_right4" align="left" >
			<select id="provinceCode" name="provinceCode">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${cityList}" var="city">
					<option value="${city.citycode}"<c:if test="${city.citycode == query_spCity}"> selected="selected" </c:if>>${city.cityname}</option>
				</c:forEach>
			</select>
		</td>
		
		<td class="border_top_right4" align="right" nowrap>经营范围：</td>
		<td class="border_top_right4" align="left" >
			<select id="rangeId" name="rangeId">
				<option value="" selected>---请选择---</option>
				<c:forEach items="${rangIdList}" var="rangIdInfo">
					<option value="${rangIdInfo.enumCode}"<c:if test="${rangIdInfo.enumCode == query_spRangId}"> selected="selected" </c:if>>${rangIdInfo.enumName}</option>
				</c:forEach>
			</select>
		</td>
		<td align="center" class="border_top_right4" >
		      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="validFrom();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      <input type="button"  name="butAdd" value="添加商户" class="button2" onclick="AddFrom();">
		</td>
	</tr>

</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	<c:if test='${error != null}'>
			  	<script>
					alert('${error}')
				</script>
	</c:if>
</body>

