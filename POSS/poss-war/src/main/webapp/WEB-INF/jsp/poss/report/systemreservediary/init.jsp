<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>系统备付金查询</title>
<script type="text/javascript">
function changeQueryType(){
	document.getElementById("startDate").value="";
	document.getElementById("endDate").value="";
}

function changeWdate(obj){
	var queryType=document.getElementById("queryType").value;
	var myDateFmt='yyyy-MM-dd';
	var myMaxDate='%y-%M-{%d-1}'; 
	if(queryType==2){  
	    myDateFmt='yyyy'; 
	    myMaxDate='{%y-1}'; 
	}  
	else if(queryType==1){  
	    myDateFmt='yyyy-MM';  
	    myMaxDate='%y-{%M-1}'; 
	}
	
	if(obj.name=="startDate"){
		var endDate=document.getElementById("endDate").value;
		WdatePicker({dateFmt:myDateFmt,maxDate:endDate!=''?'#F{$dp.$D(\'endDate\')}':myMaxDate});
	}else{
		WdatePicker({dateFmt:myDateFmt,minDate:'#F{$dp.$D(\'startDate\')}',maxDate:myMaxDate});
	}
}

function isEmpty(fData){
    return ((fData==null) || (fData.length==0) )
}

function validateQuery(){
	var startDateStr = document.getElementById("startDate").value;
	var endDateStr = document.getElementById("endDate").value;
	if(isEmpty(startDateStr)){
		alert("开始时间不能为空");
		return false;
	}
	if(isEmpty(endDateStr)){
		alert("结束时间不能为空");
		return false;
	}
	if(startDateStr > endDateStr){
		alert("开始时间必须小于结束时间");
		return false;
	}else{
		var startTimeArr = startDateStr.split("-");
		var endTimeArr = endDateStr.split("-");
		var startDate = new Date(startTimeArr[0], startTimeArr[1], startTimeArr[2]);
		var endDate = new Date(endTimeArr[0], endTimeArr[1], endTimeArr[2]);
		if(((endDate - startDate)/86400000) > 90){
			alert("查询时间跨度不能超过三个月");
			return false;
		}
	}

	return true;
}

function exportExcel(totalCount) {
	if(totalCount <= 0){
		alert("无结果集,不能下载！");
	}else if(totalCount > 60000){
		alert("结果集大于60000,不能下载！");
	}else{
		if(validateQuery()){
			var pars = $("#form1").serialize();
			window.location="${ctx}/report/systemReserveDiary.do?method=list&export=1&"+pars;
		}
	}
}

function userQuery(pageNo,totalCount,pageSize){
	if(!validateQuery()){
		return false;
	}
  	$('#infoLoadingDiv').dialog('open');
	var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/report/systemReserveDiary.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#paginationResult').html(result);
		}
	});
}

$(document).ready(function(){userQuery();});
</script>
</head>

<body>
<br/>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">系统备付金查询</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
</table>
<br/>
<br/>
<form action="" method="post" id="form1" name="form1">
  <table width="40%" border="0" cellspacing="0" cellpadding="1" align="center">	
    <tr>
    	<td align="right">交易日期：</td>
    	<td align="left">
			<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()" />
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd"/>' onClick="WdatePicker()" />
		</td>
    </tr>
    <tr>
      <td align="center" colspan="2">
      <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
