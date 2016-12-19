<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="${ctx}/css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(function(){
	query();
});

function query() {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#floatRate").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	var partnerId = $.trim($('#partnerId').val());
	if(''==partnerId){
		alert('请输入会员号！');
		return;
	}
	var startPoint = $('#startPoint').val();
	if(''==startPoint){
		alert('请输入起始点！');
		return;
	}
	var endPoint = $('#endPoint').val();
	if(''==endPoint){
		alert('请输入截止点！');
		return;
	}
	
	var pars = $("#floatRate").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=add",
		data: pars,
		success: function(d) {
			if (1==d) {
				window.location.href="${ctx}/partnerRateFloat.do";
			} else {
				return;
			}
		}
	});
}


function deleteCure(id) {
	
	if(!confirm("确认删除？")) {
		return ;
	}
	
	$.ajax({
		type: "POST",
		url: "${ctx}/partnerRateFloat.do?method=delete",
		data: 'id='+id,
		success: function(data) {
			var str = data;
			var jsonStr = eval("("+str+")");
			if (jsonStr.result === "success") {
				window.location.href="${ctx}/partnerRateFloat.do";
				return;
			} else {
				alert(jsonStr.message);
				return;
			}
		}
	});
} 

</script>
</head>

<body>
<h2 class="panel_title">商户汇率浮动管理</h2>
<form id="floatRate" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="partnerId" name="partnerId" value="${partnerId}">
				</td>	
				<td class="border_top_right4" align="right" >浮动起始点：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="startPoint" name="startPoint">
				</td>
				<td class="border_top_right4">‰ 到</td>
				<td class="border_top_right4" align="right" >浮动截止点：</td>
				<td class="border_top_right4" align="left">
                    <input type="text" id="endPoint" name="endPoint">‰
				</td>
			</tr>		
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="7" align="center">
				   <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="query();">
	               <button onclick="javascript:add()">添加</button>
				</td>
			</tr>
	</table>
</form>

<div id="resultListDiv" class="listFence" style="text-align: center;"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>

