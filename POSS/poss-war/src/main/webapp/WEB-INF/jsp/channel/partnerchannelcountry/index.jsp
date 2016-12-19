<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
<!--
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});
function query(pageNo) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#partnerChannelForm").serialize() + "&pageNo=" + pageNo;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerchannelcountry.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function deleteConfig(id){
	if (!confirm("确认删除？")) {
		return;
	}
	var pars = "id="+id;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerchannelcountry.do?method=delete",
		data: pars,
		success: function(result) {
			var msg = eval('('+result+')');
			if(msg.isSuccess==true){
				$('#addLogDiv').dialog('close');
				alert("操作成功");
				query();
			}else{
				alert(msg.reason);
			}

		}
	});
}
function add(){
	$("#memCode4Edit").val('');
	$("#orgCode4Edit").val('');
	$("#countryCode4Edit").val('');
	$("#editId").val('');
	$('#memCode4Edit').attr("readonly",false);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:200,
		modal:true,
		title:'增加会员渠道优先级配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
	$('#addLogDiv').dialog('open');
}
function editpcc(){
	var memCode = $("#memCode4Edit").val();
	var orgCode = $("#orgCode4Edit").val();
	var countryCode = $("#countryCode4Edit").val();
	var editId = $("#editId").val();

	if('' == orgCode){
		alert("请选择渠道！");
		return;
	}

	if('' == countryCode){
		alert("请选择国家！");
		return;
	}
	var pars ="memCode=" + memCode + "&orgCode=" + orgCode+ "&countryCode=" + countryCode + "&editId=" + editId;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerchannelcountry.do?method=edit",
		data: pars,
		success: function(result) {
			var msg = eval('('+result+')');
			if(msg.isSuccess==true){
				$('#addLogDiv').dialog('close');
				alert("操作成功");
				query();
			}else{
				alert(msg.reason);
			}
		}
	});
}
function closepcc(){
	$('#addLogDiv').dialog('close');
}
function editConfig(editId,memCode,orgCode,countryCode){
	$("#memCode4Edit").val(memCode);
	$("#orgCode4Edit").val(orgCode);
	$("#countryCode4Edit").val(countryCode);
	$("#editId").val(editId);
	$('#memCode4Edit').attr("readonly",true);
	$('#addLogDiv').dialog({
		position:["center","center"],
		width:600,
		height:200,
		modal:true,
		title:'修改会员渠道优先级配置',
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	});
}
-->
</script>
</head>

<body>
<h2 class="panel_title">渠道路由优先级配置</h2>
<form id="partnerChannelForm" name="partnerChannelForm"  action="memchannelcountry.do?method=list"  method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="left">
					会员号：<input type="text" id="memCode" name="memCode" value="">
				</td>
				<td class="border_top_right4" align="left">
					渠道：<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${paymentChannels}" varStatus="v">
							<option value="${channel.code}">${channel.desc}</option>
						</c:forEach>
					</select>	
				</td>
				<td class="border_top_right4" align="left">
					&nbsp;&nbsp;IP国家:<select id="countryCode" name="countryCode">
						<option value="" selected>---请选择---</option>
						<option value="USA,CAN">美国USA，加拿大CAN</option>
						<option value="000">其他000</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="3" align="center">
					<a class="s03" href="javascript:query()"><input class="button2" type="button" value="查询"> </a>
					<a class="s03" href="javascript:add()"><input class="button2" type="button" value="新增"> </a>
				</td>
			</tr>
			
	</table>
</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<div id="addLogDiv" name="addLogDiv" style="display: none" align="center">
	<form id="setPriorityForm" name="setPriorityForm" >
		<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>会员号</td>
				<td class="border_top_right4" align="center" nowrap><input id="memCode4Edit" name="memCode4Edit"/></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>渠道</td>
				<td class="border_top_right4" align="center" nowrap>
					<select id="orgCode4Edit" name="orgCode4Edit">
					<option value="" selected>---请选择---</option>
					<c:forEach var="channel" items="${paymentChannels}" varStatus="v">
						<option value="${channel.code}">${channel.desc}</option>
					</c:forEach>
				</select></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>IP国家</td>
				<td class="border_top_right4" align="center" nowrap><select id="countryCode4Edit" name="countryCode4Edit">
					<option value="" selected>---请选择---</option>
					<option value="USA,CAN">美国USA，加拿大CAN</option>
					<option value="000">其他000</option>
				</select></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" colspan="2" align="center" nowrap>
					<input type="hidden" id="editId"/>
					<input type="button" value="提交" onclick="editpcc()"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="closepcc()"/>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>

