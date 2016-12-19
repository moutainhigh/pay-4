<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<head>
		<title>提现管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function withdrawQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "withdrawLiquidateAudit.do?method=processingSearch",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			$(document).ready(function(){withdrawQuery();});

		</script>
	</head>

	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">查询处理中的交易</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		
		<form id="userSearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易时间：</td>
				<td class="border_top_right4" colspan="3">
					<input class="Wdate" type="text" id="startDate" name="startDate" 
						value='<fmt:formatDate value="${startTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				&nbsp;至&nbsp;
					<input class="Wdate" type="text" id="endDate" name="endDate" 
						value='<fmt:formatDate value="${endTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\',{d:0});}'});">
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">目的银行：</td>
				<td class="border_top_right4">
					<li:select name="bankKy" defaultStyle="true" itemList="${withdrawBankList}"/>
				</td>
				<td class="border_top_right4" align="right">出款银行：</td>
				 <td class="border_top_right4">
	        		<li:select name="withdrawBankCode" defaultStyle="true" itemList="${withdrawBankList}"/>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">交易流水号：</td>
				<td class="border_top_right4"><input type="text" name="sequenceId" style="width: 150px;" /></td>
				<td align="right" class="border_top_right4">业务批次号：</td>
				<td class="border_top_right4">
					<input type="text" name="batchNo" id="batchNo" style="width: 150px;" />
				</td>
			</tr>
			<tr class="trForContent1">
				<td align="right" class="border_top_right4"  >结算周期：</td>
				<td class="border_top_right4" >
					<select name="accountMode" style="width: 150px;">
						<option value='' checked>全部</option>
						<c:forEach items="${accountModeList}" var="map">
							<option value='${map.value}'>${map.text}</option>
						</c:forEach>
					</select>
      			</td>
				<td class="border_top_right4" align="right" >用户类型：</td>
				<td class="border_top_right4">
					<select name="memberType" style="width: 150px;">
						<option value="">全部</option>
						<option value='1'>&nbsp;&nbsp;个人&nbsp;&nbsp;</option>
						<option value='2'>&nbsp;&nbsp;企业&nbsp;&nbsp;</option>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right">商户名称：</td>
				<td class="border_top_right4">
					<input type="text" name="memberName" style="width: 150px;" />
				</td>
				<td class=border_top_right4 align="right">会员号：</td>
				<td class="border_top_right4">
					<input type="text" name="memberCodeList" style="width: 150px;" />（多个以“,”隔开）
				</td>
			</tr>
			<tr class="trForContent1">
				<td align="center" colspan="4">
					<input type="button" onclick="withdrawQuery();" name="submitBtn" value="查    询" class="button2">	
				</td>
			</tr>
		</table>
		</form>
		<div id="resultListDiv" class="listFence"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
