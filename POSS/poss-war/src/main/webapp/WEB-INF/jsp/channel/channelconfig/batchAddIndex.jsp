<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
  
 	function  back(){
		window.location.href="${ctx}/channelConfig.htm"; 
 	}
 	function batchAddFile(){
 		var orgCode=$("#orgCode").val();
 		var batchAdd=$("#batchAdd").val();
		if(orgCode == '' ){
			alert("请选择渠道！！！");
			return;
		}
 		if(batchAdd==''){
			alert("请选择要上传的文件！！！");
			return;
		}
 		$("#channelConfigForm").submit();
 	}
 
</script>
</head>

<body>
<h2 class="panel_title">批量新增二级商户号</h2>
<c:if test="${not empty msg}">
	<p><font color="red"><b>${msg}</b></font></p>
</c:if>
<form id="channelConfigForm" name="channelConfigForm"  enctype="multipart/form-data" action="channelConfig.htm?method=batchAdd"  method="POST"  >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="left">
					渠道名称：<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<option value="${channel.code}">${channel.desc}</option>
						</c:forEach>
					</select>	
				</td>
				<td class="border_top_right4" align="left" >
					<input type="file" id="batchAdd" name="batchAdd" value="">
				</td>
				<td class="border_top_right4" align="left" >
					<a href="${ctx}/channelConfig.htm?method=dowloadTemp">模版下载</a>
				</td>
			</tr>	
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="5" align="center">
						<input type="button" class="button2" value="确定提交" onclick="batchAddFile();">
						<input type="button" class="button2" value="返回" onclick="back();">
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
<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>机构商户号</th>
					<th>商户账单号</th>
					<th>交易类型</th>
					<th>模式</th>
					<th>申请商户名称</th>
					<th>终端号</th>
					<th>授权号</th>
					<th>币种</th>
					<th>mcc</th>
					<th>机构密钥</th>
					<th>映射网址</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}" varStatus="v">
					<tr>
						<td>${item.orgMerchantCode}</td>
						<td>${item.merchantBillName}</td>
						<td>${item.transType}</td>
						<td>${item.pattern}</td>
						<td>
							${item.requestMerchantName}
						</td>
						<td>${item.terminalCode }</td>
						<td>${item.accessCode}</td>
						<td>${item.currencyCode}</td>
						<td>${item.mcc}</td>
						<td>${item.orgKey}</td>
						<td>${item.supportWebsite}</td>
						<td><font color="red">${item.comments}</font></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>