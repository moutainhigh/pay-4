<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function pictureManagerQuery(pageNo,totalCount,pageSize) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	/*
	if('' == startDate){
		alert('请选择起止时间-开始段');
		return;
	}
	if('' == endDate){
		alert('请选择起止时间-结束段');
		return;
	}
	*/
	if(startDate > endDate){
		alert('开始时间不能大于结束时间！');
		return;
	}
	
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#picturePayChainManagerForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/picturePayChainManager.do?method=queryList",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function trim(str){ 
	//删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

function load(pageNo,totalCount,pageSize){
	payChainManagerQuery(pageNo,totalCount,pageSize);
}


</script>
</head>

<body onload=load()>

<table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">支 付 链 图片审核</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>
<form id="picturePayChainManagerForm" name="picturePayChainManagerForm" >
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >收款链接编号：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="payChainNumber" name="payChainNumber" value="${payChainNumber}" maxlength= "17">
		</td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="zhName" name="zhName" value="${zhName}"  maxlength= "32"></td>
		</td>
	</tr>
	<tr class="trForContent1"> 
		<td class="border_top_right4" align="right" >图片审核状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="pictureStatus" name="pictureStatus">
				<option value="" selected >全部</option>
				<option value="0" <c:if test="${pictureStatus=='0'}">select</c:if> >未审核</option>					
				<option value="1" <c:if test="${pictureStatus=='1'}">select</c:if> >审核通过</option>	
				<option value="2" <c:if test="${pictureStatus=='2'}">select</c:if> >审核拒绝</option>		
		   </select>
		</td>
		<td  class="border_top_right4" align="right" >起止时间：</td>
		<td  class="border_top_right4" align="left" >
			<input class="Wdate" type="text" id= "startDate" name="startDate"  value="${startDate}" 
			onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen'})" readonly />
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<a class="s03" href="javascript:pictureManagerQuery()"><img src="./images/query.jpg" border="0"></a>
			
		</td>
	</tr>
</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
</body>

