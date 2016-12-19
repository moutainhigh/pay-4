<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";



function appealBaseDataRelationQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#appealBaseDataRelationSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/appealBaseDataRelationList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

</script>
</head>

<body>

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">基 础 数 据 关 系 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

<form id="appealBaseDataRelationSearchFormBean" name="appealBaseDataRelationSearchFormBean">
<input type="hidden" id="baseDataRelationId" name="baseDataRelationId"></input>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >父类数据：</td>
		<td class="border_top_right4" align="left" >
			<select	id="fatherDataCode" name="fatherDataCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${baseDataList}" var="baseData">
					<option value="${baseData.code}">${baseData.name}</option>
					</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" align="right" >子类数据：</td>
		<td class="border_top_right4" align="left" >
			<select	id="sonDataCode" name="sonDataCode" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${baseDataList}" var="baseData">
					<option value="${baseData.code}">${baseData.name}</option>
					</c:forEach>
			</select>
		</td>
		<td class="border_top_right4" align="right" >数据关系类型：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="type" name="type">
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:appealBaseDataRelationQuery()">
			<img src="./images/query.jpg" border="0"></a></td>
	</tr>

</table>
</form>
<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

</body>

