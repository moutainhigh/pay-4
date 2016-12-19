<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<head>
		<title>提现管理</title>
		<script type="text/javascript">
			$(function(){
				 $("#allCheck").click(function(){ 
					 $(':checkbox[name=riskLeveCode]').each(function() {
							this.checked = $("#allCheck").attr("checked");
					 });
				 });
			}); 
		
			function downLoad(){
				
				document.userSearchForm.action = "withdrawAuditDownLoad.do?method=exportExcel";
				document.userSearchForm.submit();
			}
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function withdrawQuery(pageNo,totalCount,pageSize) {
				
				if(!validate()){
					return false;
				}
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/withdrawAudit.do?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			//$(document).ready(function(){withdrawQuery();});

			function validate() {
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();

				if('' == startDate){
					$.fo.alert("起始时间不能为空!");
					return false;
				}
				if('' == endDate){
					$.fo.alert("结束时间不能为空!");
					return false;
				}
				return true;
			}
		</script>
	</head>

	<body>
	
		<h2 class="panel_title">查询待审核出款数据</h2>
		
		<form id="userSearchForm" name="userSearchForm" method="post" action="">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易时间：</td>
				<td class="border_top_right4" colspan="3">
					<input class="Wdate" type="text" id="startDate" name="startDate" 
						value='<fmt:formatDate value="${startTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				至&nbsp;
					<input class="Wdate" type="text" id="endDate" name="endDate" 
						value='<fmt:formatDate value="${endTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\',{d:0});}'});">
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4"><input type="text" name="memberCode" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right" >会员类型：</td>
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
						<option value='' checked>--请选择--</option>
						<c:forEach items="${acctTypes}" var="acct">
							<option value='${acct.code }'>${acct.displayName}</option>
						</c:forEach>
					</select>
				</td>
				<td class="border_top_right4" align="right" >交易状态：</td>
				<td class="border_top_right4">
					<select name="status" style="width: 150px;">
						<option value='' checked>&nbsp;&nbsp;全部&nbsp;&nbsp;</option>
						<option value='0' checked>工单初始</option>
						 <option value='3'>审核滞留</option>
						<!--<option value='1'>审核通过</option>
						<option value='2'>审核拒绝</option> -->
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易流水号：</td>
				<td class="border_top_right4"><input type="text" name="sequenceId" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right" >银行账户：</td>
				<td class="border_top_right4"><input type="text" name="bankAcct" style="width: 150px;" /></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >银行名称：</td>
			    <td class="border_top_right4">
	        		<li:select name="bankKy" defaultStyle="true" itemList="${withdrawBankList}" />
				</td>
				<td class="border_top_right4" align="right" >审核等级：</td>
				<td class="border_top_right4">
					<select name="prioritys" style="width: 150px;">
						<option value='5' checked>&nbsp;&nbsp;正常&nbsp;&nbsp;</option>
						<option value='6'>&nbsp;&nbsp;优先&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易类型：</td>
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
				<td class="border_top_right4" align="right" >清结算是否废除：</td>
				<td class="border_top_right4">
					<select name="batchStatus" style="width: 150px;">
						<option value='' checked>&nbsp;&nbsp;否&nbsp;&nbsp;</option>
						<option value='2'>&nbsp;&nbsp;是&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">业务批次号：</td>
				<td class="border_top_right4">
					<input type="text" name="batchNo" id="batchNo" style="width: 150px;" />
				</td>
				<td align="right" class="border_top_right4">总订单号：</td>
				<td class="border_top_right4">
					<input type="text" name="primaryOrderNo" id="primaryOrderNo" style="width: 150px;" />
				</td>
			</tr>
			<tr class="trForContent1" data-sign="fo-batch">
				<td align="right" class="border_top_right4">会员登陆标志：</td>
				<td class="border_top_right4">
					<input type="text" name="loginName" id="loginName" style="width: 150px;" />
				</td>
				<td class="border_top_right4" colspan="2">
					商户风控等级：
					<input type="checkbox" name="allCheck" id="allCheck" checked="checked">全部 &nbsp;&nbsp;
					<c:forEach items="${riskLeveCodeList}" var="map">
						<input type="checkbox" name="riskLeveCode" value="${map.value}">${map.text}&nbsp;&nbsp;
					</c:forEach>
				</td>
			</tr>
			<tr class="trForContent1" data-sign="fo-batch">
				<td align="right" class="border_top_right4">结算周期：</td>
				<td class="border_top_right4" colspan="3">
					<select name="accountMode" style="width: 150px;">
						<option value='' checked>全部</option>
						<c:forEach items="${accountModeList}" var="map">
							<option value='${map.value}'>${map.text}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="4">
					<input type="button" onclick="withdrawQuery()" name="submitBtn" value="查询" class="button2">
					<input type="button" onclick="downLoad()" name="submitBtn" value="下载" class="button2">
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
		
