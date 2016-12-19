<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>


<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function forQuery(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#verifySearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/verifyLogList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function validFrom() {
    var reg = /^\d+$/;
    var memberCode = document.getElementById('memberCode').value; 
    if(memberCode!=null&&memberCode!=''){ 
    	
	    var isNumber = reg.test(memberCode.replace(/(^\s*)|(\s*$)/g, ""));
	    if(isNumber){
	    	forQuery();
	    }else{
	    	alert('会员号输入不合法');
	    }
    }else{
    	forQuery();
    }
}

</script>
</head>

<body>

<!-- <table width="30%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">实 名 认 证 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->

<h2 class="panel_title">实名认证查询</h2>
<form id="verifySearchFormBean" name="verifySearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4" align="left" >
		<input type="text" id="memberCode" name="memberCode">
		</td>
		<td class="border_top_right4" align="right" >登录名：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="loginName" name="loginName"></td>
		<td class="border_top_right4" align="right" >申请日期：</td>
		<td class="border_top_right4" align="left" >
		<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">到
		<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>		
			
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >身份证号码：</td>
		<td class="border_top_right4" align="left" >
		<input type="text" id="cardId" name="cardId">
		</td>
		<td class="border_top_right4" align="right" >姓名：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="memberName" name="memberName"></td>
		<td class="border_top_right4" align="right" >认证状态：</td>
		<td class="border_top_right4" align="left" >
			<select id="verifyStatus" name="verifyStatus" >
					<option value="" selected>---请选择---</option>
					<c:forEach items="${verifyLogStatusEnum}" var="verifyLogStatus">
						<option value="${verifyLogStatus.code}">${verifyLogStatus.description}</option>
					</c:forEach>			
			</select>
		</td>	
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" href="javascript:validFrom()">
			<input class="button2" type="button" value="查询"></a></td></tr>

</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

