<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>提现报表数据</title>
		<script type="text/javascript">
			function downLoad(){
				
				document.userSearchForm.action = "withdrawAudit.do?method=exportExcel";
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
					url: "${ctx}/withdrawreport.htm?method=queryNoDisposeData",
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
		<h2 class="panel_title">查询风控未处理数据</h2>
		<form id="userSearchForm" name="userSearchForm" method="post" action="">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易时间：</td>
				<td class="border_top_right4" colspan="3">
					<input class="Wdate" type="text" id="startTime" name="startTime" 
						value='${startDate}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'});">
				至&nbsp;
					<input class="Wdate" type="text" id="endTime" name="endTime" 
						value='${endDate}' style="width: 150px;"  
						onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'});">
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
				<td class="border_top_right4" align="right" >审核等级：</td>
				<td class="border_top_right4">
					<select name="auditGrade" style="width: 150px;">
						<option value='5' checked>&nbsp;&nbsp;正常&nbsp;&nbsp;</option>
						<option value='6'>&nbsp;&nbsp;优先&nbsp;&nbsp;</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >会员号：</td>
				<td class="border_top_right4"><input type="text" name="memberCode" onkeyup="this.value=this.value.replace(/\D/g,'')" style="width: 150px;" /></td>
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
					<select name="acctType" style="width: 150px;">
						<option value='10' checked>&nbsp;&nbsp;人民币&nbsp;&nbsp;</option>
					</select>
				</td>
				<td class="border_top_right4" align="right" >银行名称：</td>
			    <td class="border_top_right4">
	        		<li:select name="bankCode" defaultStyle="true" itemList="${withdrawBankList}" />
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >交易流水号：</td>
				<td class="border_top_right4"><input type="text" name="businessNo" onkeyup="this.value=this.value.replace(/\D/g,'')" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right" >银行账户：</td>
				<td class="border_top_right4"><input type="text" name="bankAcct" onkeyup="this.value=this.value.replace(/\D/g,'')" style="width: 150px;" /></td>
			</tr>		
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="4">
					<input type="button" onclick="withdrawQuery()" name="submitBtn" value="查询" class="button2">
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
		
