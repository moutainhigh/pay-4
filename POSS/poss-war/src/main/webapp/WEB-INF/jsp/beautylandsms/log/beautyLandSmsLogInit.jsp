<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function smsLogQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#merchantSmsLogFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize ;
	
	$.ajax({
		type: "POST",
		url: "${ctx}/beautyLandSmsLogList.htm",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function validFrom() {
    var reg = /^\d+$/;
    var merchantCode = document.getElementById('merchantCode').value; 
    if(merchantCode!=null&&merchantCode!=''){ 
    	
	    var isNumber = reg.test(merchantCode.replace(/(^\s*)|(\s*$)/g, ""));
	    if(isNumber){
	    	smsLogQuery();
	    }else{
	    	alert('商户号输入不合法');
	    }
    }else{
    	smsLogQuery();
    }
}

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
		<div align="center"><font class="titletext">锦 绣 大 地 商 户 短 信日志 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="merchantSmsLogFormBean" name="merchantSmsLogFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >商户号：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="merchantCode" name="merchantCode"></td>
		<td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="merchantName" name="merchantName"></td>
		
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >起始日期：</td>
		<td class="border_top_right4" align="left" >
			<input class="Wdate" type="text" size="26" id="startDate" name="startDate" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
		</td>
		<td class="border_top_right4" align="right" >结束日期：</td>
		<td class="border_top_right4" align="left">
			<input class="Wdate" type="text" size="26" id="endDate" name="endDate" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
		</td>
	</tr>
 	<tr class="trForContent1">
		      <td align="center" class="border_top_right4" colspan="6">
		      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="validFrom();">
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

