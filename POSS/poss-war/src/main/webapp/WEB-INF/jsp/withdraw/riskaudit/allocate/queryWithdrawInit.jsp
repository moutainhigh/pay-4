<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>提现申请管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function withdrawQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/withdrawAllocate.do?method=search",
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
						<font class="titletext">查询出款申请数据</font>
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
						value='' style="width: 150px;"  
						onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				至&nbsp;
					<input class="Wdate" type="text" id="endDate" name="endDate" 
						value='' style="width: 150px;"  
						onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\',{d:0});}'});">
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4"><input type="text" name="memberCode" style="width: 150px;" /></td>
				<td class=border_top_right4 align="right" >会员类型：</td>
				<td class="border_top_right4">
					<select name="memberType" style="width: 150px;">
						<option value='' checked>&nbsp;&nbsp;全部&nbsp;&nbsp;</option>
						<option value='1'>&nbsp;&nbsp;个人&nbsp;&nbsp;</option>
						<option value='2'>&nbsp;&nbsp;企业&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >帐户类型：</td>
				<td class="border_top_right4">
					<select name="memberAccType" style="width: 150px;">
						<option value='10' checked>&nbsp;&nbsp;人民币&nbsp;&nbsp;</option>
					</select>
				</td>
				<td class="border_top_right4" align="right" >交易状态：</td>
				<td class="border_top_right4">
					<select name="status" style="width: 150px;">
						<option value='' checked>&nbsp;&nbsp;全部&nbsp;&nbsp;</option>
						<option value='0' >工单初始</option>
						<option value='3'>审核滞留</option>
						<option value='1'>审核通过</option>
						<option value='2'>审核拒绝</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">交易流水号：</td>
				<td class="border_top_right4"><input type="text" name="sequenceId" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right">银行账户：</td>
				<td class="border_top_right4"><input type="text" name="bankAcct" style="width: 150px;" /></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">银行名称：</td>
				 <td class="border_top_right4">
	        		<!--<li:select name="bankKy" itemList="${withdrawBankList}" selected="${}"/>-->
	        		<li:select name="bankKy" defaultStyle="true" itemList="${withdrawBankList}"/>
				</td>
				<td class="border_top_right4" align="right">审核等级：</td>
				<td class="border_top_right4">
					<select name="prioritys" style="width: 150px;">
						<option value='5' checked>&nbsp;&nbsp;正常&nbsp;&nbsp;</option>
						<option value='6'>&nbsp;&nbsp;优先&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">业务类型：</td>
				<td class="border_top_right4">
					<select name="busiType" style="width: 150px;">
						<option value="">全部</option>
						<option value='0'>&nbsp;&nbsp;提现&nbsp;&nbsp;</option>
						<!--<option value='1'>批量出款</option>
						<option value='2'>信用卡还款</option>
						--><option value='3'>付款到银行</option>
						<option value='4'>批量付款到银行</option>
					</select>
				</td>
				<td class="border_top_right4" align="right">清结算是否废除：</td>
				<td class="border_top_right4">
					<select name="batchStatus" style="width: 150px;">
						<option value='' checked>&nbsp;&nbsp;否&nbsp;&nbsp;</option>
						<option value='2'>&nbsp;&nbsp;是&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
 			<tr class="trForContent1">
				<td class="border_top_right4" align="right">操作员：</td>
				<td class="border_top_right4" colspan="3">
					<select id="handleUser" name="handleUser" style="width:120px;">
		      			<option value="">请选择</option>
		      			<c:forEach items="${userInfo}" var="info">
		      				<option value="${info}">${info}</option>
		      			</c:forEach>
		      		</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="4">
					<input type="button" onclick="withdrawQuery();" name="submitBtn" value="查  询" class="button2">	
					<!-- <a href="${ctx}/userAdd.do" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加用户&nbsp;
					</a> -->
				</td>
			</tr>
		</table>
		</form>
		<div id="resultListDiv" class="listFence"></div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
