
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>

<link rel="stylesheet" href="./css/main.css">
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript"><!--

//确认提交
function processCommit(memberCode){
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#shoppingCityConfigFormBean").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/shoppingCityConfigUpdate.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			alert("${memberCode}更新完成！");
		}
	});
}

//页面validate
$(document).ready(function(){
	//为inputMemberCodeForm注册validate函数
	$("#inputMemberCodeForm").validate({
		rules: { 
			memberCode:{
				required:true,
				digits:true,
				rangelength:[11,11]
				}
		},
		messages:{
			memberCode:{
				required:"请输入商户会员memberCode（11位纯数字）",
				digits:"请输入商户会员memberCode（11位纯数字）",
				rangelength:"请输入商户会员memberCode（11位纯数字）"
			}
		}
	});
});
--></script>
</head>

<body>


	<br/>
	
	<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext"> 商户支付渠道管理 </font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	
	<form id="inputMemberCodeForm" name="inputMemberCodeForm" action="customerChannelRuleSearchManage.do" method="post" >
		<table  class="border_all2" width="95%" border="0" cellspacing="0"
			cellpadding="1" align="center">
			<tr class="trForContent1">		
				<td class="border_top_right4" align="center" width="50%"  >
					请输入商户会员memberCode：
				</td>
				<td class="border_top_right4" align="center">
					<input type="text" id="memberCode" name="memberCode" value="${membercode}" >
				</td>
			</tr>
			<tr class="trForContent1" >	
				<td colspan="2" class="border_top_right4" align="center">
					<input type="submit" value="查询">
				</td>						
			</tr>
		</table>
	</form>
				
	<c:if test="${empty membercode}"> <strong>请输入商户会员memberCode进行查询.</strong></c:if>
	<c:if test="${!empty membercode}"> <strong>当前商户会员memberCode为: ${membercode}</strong></c:if>
	<br/>
				
	<c:if test="${!empty paymentChannelsAllList}">
		<form action="customerChannelRuleUpdateManage.do"  method="post">
			<input type="hidden" value="${membercode}" name="memberCode" ></input>
			<table class="border_all2" width="95%" border="0" cellspacing="0"
				cellpadding="1" align="center">
				<tr class="trForContent1">		
					<td class="border_top_right4" align="center" nowrap><strong>支付渠道</strong>&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap><strong>所属种类</strong>&nbsp;</td>
					<td class="border_top_right4" align="center" nowrap><strong>支付通道</strong>&nbsp;</td>	
					<td class="border_top_right4" align="center" nowrap><strong>是否开通</strong>&nbsp;</td>
				</tr>
				<c:forEach items="${paymentChannelsAllList}" var="paymentChannel" varStatus = "paymentChannelStatus">
					<c:forEach items="${paymentChannel.itemSortMap}" var="itemSortMap" varStatus = "itemSortMapStatus">
						<c:forEach items="${itemSortMap.value}" var="item" varStatus = "itemStatus">
							<tr class="trForContent2">		
								<td class="border_top_right4" align="center" nowrap>${paymentChannel.name}&nbsp;</td>
								<td class="border_top_right4" align="center" nowrap>${itemSortMap.key}&nbsp;</td>	
								<td class="border_top_right4" align="center" nowrap>${item.itemName}&nbsp;</td>
								<td class="border_top_right4" align="center" nowrap>
									<input type="checkbox" value="${item.id} "  name="itemCheckBox" 
										 <c:if test="${item.isChecked == '1'}">checked="checked"</c:if>
									>
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</table>
			<input type="submit" value ="submit">
		</form>
	</c:if>
		
</body>

