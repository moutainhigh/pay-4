<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">

var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
$(function(){
	query();
});
function query() {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#paymentChannelItemFormBean").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentchannelconfigformerchant.htm?method=query",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function add() {
	var memberCode = $.trim($('#memberCode').val());
	if(''==memberCode){
		alert('请输入商户号！');
		return;
	}
	var paymentType = $('#paymentType').val();
	if(''==paymentType){
		alert('请选择业务类型！');
		return;
	}
	//验证会员存在
	var pars = $("#paymentChannelItemFormBean").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentchannelconfigformerchant.htm?method=checkMember",
		data: pars,
		success: function(d) {
			if(1==d){
				window.location.href="${ctx}/paymentchannelconfigformerchant.htm?method=setItem&memberCode=" + memberCode + "&paymentType=" + paymentType;
			}else{
				alert('商户不存在！');
				return;
			}
		}
	});
} 

function delconfig(id,memberCode,paymentType) {
	
	if(!confirm("确认删除？")) {
		return false;
	}
	
	var pars = "paymentChannelItemId=" + id + "&memberCode=" + memberCode + "&paymentType=" + paymentType;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentchannelconfigformerchant.htm?method=removeMerchantConfig",
		data: pars,
		success: function(result) {
			query();
		}
	});
} 


function setPriority(rowNum,id){
	rowNum=rowNum+1;//因为第一行是th列名，所以要加一
	$("#paymentChannelItemConfigId").val(id)
	var memberCode = $("#tabMerchantConfig tr:eq("+rowNum+")").children(":eq(0)").html();
	var channelName = $("#tabMerchantConfig tr:eq("+rowNum+")").children(":eq(1)").html();
	var payType = $("#tabMerchantConfig tr:eq("+rowNum+")").children(":eq(2)").html();
	var channelPriority = $("#"+id+"ChannelPriority").val();
	var categoryType = $("#tabMerchantConfig tr:eq("+rowNum+")").children(":eq(4)").html();
	$("#tdMemberCode").html(memberCode);
	$("#tdChannelName").html(channelName);
	$("#tdPayType").html(payType);
	$("#tdPayCategoryCode").html(categoryType);
	$("#txtPriority").val(channelPriority);
	$('#setChannelPriority').dialog( 
		{ 
			position:"top",
			width:550,
			modal:true, 	
			title:'优先级设置', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	} );
}
function cancel(){
	$("#setChannelPriority").dialog("close");
}

function submitPriority(){
	if(!$("#txtPriority").val()){
		alert("请输入优先级！");
		return false;
	}
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentchannelconfigformerchant.htm?method=setPriority",
		data: {"paymentChannelItemConfigId":$("#paymentChannelItemConfigId").val(),"priority":$("#txtPriority").val()},
		success: function(result) {
			if(result){
				alert("修改成功！");
			}else{
				alert("修改失败！");
			};
		}
	});
}
</script>
</head>

<body>
<h2 class="panel_title">商户通道配置</h2>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="memberCode" name="memberCode" value="">
				</td>	
				<td class="border_top_right4" align="right" >业务类型：</td>
				<td class="border_top_right4" align="left">
					<select id="paymentType" name="paymentType" size="1">
						<option value="" selected>---请选择---</option>
						<option value="2" >支付</option>
						<option value="1" >充值</option>
						<option value="3" >直连</option>
						<option value="4" >预授权</option>
					</select>	
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道名称：</td>
				<td class="border_top_right4" align="left">
					<select id="paymentChannelCode" name="paymentChannelCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${paymentChannels}" varStatus="v">
							<option value="${channel.code}">${channel.name}</option>
						</c:forEach>
					</select>	
				</td>
				<td class="border_top_right4" align="right" >渠道类别：</td>
				<td class="border_top_right4" align="left">
					<select id="paymentCategoryCode" name="paymentCategoryCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="category" items="${paymentCategorys}" varStatus="v">
							<option value="${category.code}">${category.name}</option>
						</c:forEach>
					</select>	
				</td>
			</tr>				
			
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
					<a class="s03" href="javascript:query()"><input class="button2" type="button" value="查询"></a>
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

<div id="setChannelPriority" style="display: none">
	<form id="setPriorityForm" name="setPriorityForm" onsubmit="return submitPriority()">
		<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>会员号</td>
				<td id="tdMemberCode" class="border_top_right4" align="center" nowrap></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>渠道名称</td>
				<td id="tdChannelName" class="border_top_right4" align="center" nowrap></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>业务类型</td>
				<td id="tdPayType" class="border_top_right4" align="center" nowrap></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>渠道类别</td>
				<td id="tdPayCategoryCode" class="border_top_right4" align="center" nowrap></td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" align="center" nowrap>优先级</td>
				<td class="border_top_right4" align="center" nowrap>
					<input id="txtPriority" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))"/>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" colspan="2" align="center" nowrap>
					<font size="3" color="red">填写数字1、2、3等正整数数字，1为优先级最高，2次之，依次排列；</font><br>
					<font size="3" color="red">填写数字0，为暂不使用该渠道。</font>
					<input type="hidden" id="paymentChannelItemConfigId"/>
				</td>
			</tr>
			<tr class="trForContent1" align="center" valign="middle">
				<td class="border_top_right4" colspan="2" align="center" nowrap>
					<input type="submit" value="提交"/>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="取消" onclick="cancel()"/>
				</td>
			</tr>
	</form>
</div>

</body>

