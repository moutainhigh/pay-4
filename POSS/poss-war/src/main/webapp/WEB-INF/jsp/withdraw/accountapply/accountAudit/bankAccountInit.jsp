<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<head>
		<title>POSS提现账号审核</title>
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
			
			function bankAccountQuery(pageNo,totalCount,pageSize) {
				
				if(!validate()){
					return false;
				}
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/withdrawAccountAduit.do?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			

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
			
			function numCheck(obj){
				var value = obj.value ;
				if(isNaN(value)){
					obj.value = "" ;
				}
			}
		</script>
	</head>

	<body>
		<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">提现账号审核</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table> -->
		<h2 class="panel_title">提现账号审核</h2>
		
		<form id="userSearchForm" name="userSearchForm" method="post" action="">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4"><input type="text" name="memberCode" id="memberCode" onkeyup="javascript:numCheck(this) ;" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right">申请时间：</td>
				<td class="border_top_right4" colspan="3">
					<input class="Wdate" type="text" id="startDate" name="startDate" 
						value='<fmt:formatDate value="${startTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
				至&nbsp;
					<input class="Wdate" type="text" id="endDate" name="endDate" 
						value='<fmt:formatDate value="${endTime}" type="both"/>' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\',{d:0});}'});">
				</td>
				<td class="border_top_right4" align="right">审核状态：</td>
				<td class="border_top_right4">
					<select name="auditStatus" id="auditStatus">
						<option value="">全部</option>
						<option value="101" selected="selected">待审核</option>
						<option value="102">审核通过</option>
						<option value="103">审核拒绝</option>
						<option value="104">滞留</option>
					</select>
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="8" style="height:26px;">
					<input type="button" onclick="bankAccountQuery()" name="submitBtn" value="查   询" class="button2">
					<!-- <input type="button" onclick="downLoad()" name="submitBtn" value="下   载" class="button2"> -->
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
		
