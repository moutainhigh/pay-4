
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">

var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
		function(){
					tradeQuery();
				});

function  validatMemberTradeSearchForm(){
	
	this.tradeQuery();
}

function tradeQuery(pageNo,totalCount) {	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#memberTradeSearchFormBean").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	
	$.ajax({
		type: "POST",
		url: "${ctx}/tradeList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function showMoreCondition(){
	
	if( document.getElementById('moreCondition1').style.display==''){
		
		document.getElementById('moreCondition1').style.display='none';
		document.getElementById('moreCondition2').style.display='none';		
		document.getElementById('timeSelect').disabled='';
	}else{
		
		document.getElementById('moreCondition1').style.display='';
		document.getElementById('moreCondition2').style.display='';
		document.getElementById('timeSelect').disabled='disabled';
		
	}
}        


</script>
</head>

<body>

<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">${titleText}</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form id="memberTradeSearchFormBean" name="memberTradeSearchFormBean">
<table class="border_all2" width="95%" border="0" cellspacing="0"	cellpadding="1" align="center">
	<input name = "memberCode" type="hidden" value="${memberCode}"></input>
	<input name = "memberType" type="hidden" value="${memberType}"></input>
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="left"  colspan="6">
			<font class="titletext">用户名：${userName}</font><br>
			<p align="right">
				<font color="red">可用余额：</font><input id="freeBalance" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly></input>  &nbsp;&nbsp;&nbsp;<font color="red">余额：</font><input id="totalBalance" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly ></input>  &nbsp;&nbsp;&nbsp;
				<input type="button" onclick="javascript:showMoreCondition();" value="更多筛选条件 "></input>
			</p>
			
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td  class="border_top_right4" align="right">账户类型：</td>
		<td  class="border_top_right4" align="left" >
			<select id="accountType" name="accountType" size="1">
				<option value="" selected>---请选择---</option>	
				<option value="" >人民币账户</option>
						
			</select>
		</td>
		<td class="border_top_right4"  align="right" >交易状态：</td>
		<td class="border_top_right4" align="left"  >
				<select id="transactionStatus" name="transactionStatus" size="1">
					<option value="" selected>---请选择---</option>	
					<c:forEach items="${transStatusEnumArray}" var="transStatus">
						<option value="${transStatus.code}" >${transStatus.description_zh}</option>	
					</c:forEach>
							
				</select>
			</td>
			<td  class="border_top_right4" align="right" >时间：</td>
			<td class="border_top_right4" align="left"  >
				<select id="timeSelect" name="timeSelect" size="1" >
					<option value="" selected>---请选择---</option>
					<option value="currDay" >当天</option>
					<option value="currWeek" >本周</option>
					<option value="currMonth" >本月</option>				
				</select>
			</td>
	</tr>
	<tr class="trForContent1" id="moreCondition1" style="display:none">
		<td  class="border_top_right4"  align="right" >消费类型：</td>
			<td class="border_top_right4"  align="left" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>
						<c:forEach items="${tradeTypeEnumArray}" var="tradeTypeEnum">
						<option value="${tradeTypeEnum.code}" >${tradeTypeEnum.description}</option>	
					</c:forEach>				
				</select>
			</td>
		<td  class="border_top_right4"  align="right" >资金流向：</td>
			<td  class="border_top_right4" align="left"  >
				<select id="funds" name="funds" size="1">
					<option value="" selected>---请选择---</option>	
					<c:forEach items="${fundsArray}" var="funds">
						<option value="${funds.code}" >${funds.description_zh}</option>	
					</c:forEach>															
				</select>
			</td>
			<td  class="border_top_right4"  align="right">起至时间：</td>
			<td  class="border_top_right4" align="left" >
							<input class="Wdate" type="text" id= "startTime" name="startTime"  onClick="WdatePicker()">
				到
				<input class="Wdate" type="text" name="endTime"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
			</td>
			
			
			
			
	</tr>
	
	<tr class="trForContent1" id="moreCondition2" style="display:none">
		<td  class="border_top_right4"  align="right" >消费名称：</td>
			<td  class="border_top_right4" align="left">
				<input type="text" id="goodsName" name="goodsName"> </input>
			</td>
		
			<td class="border_top_right4"  align="right" >对方名称：</td>
			<td class="border_top_right4"  align="left"  >
				<input id="counterPartName" name = "counterPartName" type="text">
			</td>
			<td class="border_top_right4"  align="right" >交易号：</td>
			<td class="border_top_right4"  align="left">
				<input id="transactionNo" name = "transactionNo" type="text">
			</td>
			
	</tr>
	
	<tr class="trForContent1">
		
			<td class="border_top_right4"  align="center"  colspan="6"><input type="button" onclick="javascript:validatMemberTradeSearchForm();" value=" 检索 "></input></td>
			
	</tr>
	
	</table>
</form>	

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>	

</body>

