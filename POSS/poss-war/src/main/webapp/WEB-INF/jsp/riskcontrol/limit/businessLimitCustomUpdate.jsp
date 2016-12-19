<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/rmtaglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%> 
<html>
<head>
	<title>编辑商户限额</title>
		<script type="text/javascript">
			$("#processSave").click(function(){
				if(!validate())
					return ;

				var pars = $("#bcForm").serialize();
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_businesslimitcustom.htm?method=businessLimitCustomUpdate",
					data: pars,
					success: function(result) {
						if (result == 'success') {
							$('#closeBtn').click();
							//重新载入列表
							searchBusinessLimitCustom();
							
							$('#handleMessageDiv').html("操作成功!");
							$('#handleMessageDiv').dialog('open');
						} else {
							$('#handleMessageDiv').html("操作失败!");
							$('#handleMessageDiv').dialog('open');
						}
					}
				});
			});
			
			function validate() {
				//var mcc = $.trim($("#mcc").val());//mcc
				var businessId = $.trim($("#businessId").val());//会员号
				var singleLimit = $.trim($("#singleLimit").val());//单笔限额
				var dayLimit = $.trim($("#dayLimit").val());//每日限额
				var monthLimit = $.trim($("#monthLimit").val());//每月限额
				var dayTimes = $.trim($("#dayTimes").val());//每日次数
				var monthTimes = $.trim($("#monthTimes").val());//每月次数
				//var batchAccounts = $.trim($("#batchAccounts").val());//转出账户数
				
				if(businessId == ''){
					alert("会员号为必填项!");
					return false;
				}
				if (!s_validateStrLength(businessId, 2, 20)) {
					alert("会员号应在2~20个字符之间!");
					return false;
				}
			//	if('' == mcc){
			//		alert("MCC编码为必填项!");
			//	}
				if('' == singleLimit){
					alert("单笔限额为必填项!");
					return false;
				}
				if('' == dayLimit){
					alert("每日限额为必填项!");
					return false;
				}
				if('' == monthLimit){
					alert("每月限额为必填项!");
					return false;
				}
				if('' == dayTimes){
					alert("每日次数为必填项!");
					return false;
				}
				if('' == monthTimes){
					alert("每月次数为必填项!");
					return false;
				}
				if(!s_isNumber(businessId)){
					alert("会员号必须为数字!");
					return false;
				}
			//	if(!s_isNumber(mcc)){
			//		alert("MCC编码必须为数字!");
			//		return false;
			//	}
				if(!s_isNumber(singleLimit)){
					alert("单笔限额必须为数字!");
					return false;
				}
				if(!s_isNumber(dayLimit)){
					alert("每日限额必须为数字!");
					return false;
				}
				if(!s_isNumber(monthLimit)){
					alert("每月限额必须为数字!");
					return false;
				}
				if(!s_isNumber(dayTimes)){
					alert("每日次数必须为数字!");
					return false;
				}
				if(!s_isNumber(monthTimes)){
					alert("每月次数必须为数字!");
					return false;
				}
				//if(!s_isNumber(batchAccounts)){
				//	alert("转出账户数必须为数字!");
				//	return false;
				//}
				if(parseInt(singleLimit)>parseInt(dayLimit)||parseInt(singleLimit)>parseInt(monthLimit)||parseInt(dayLimit)>parseInt(monthLimit)){
					alert("必须符合单笔不大于每日不大于每月限额");	
					return false;
				}

				if(parseInt(dayTimes)>parseInt(monthTimes)){
					alert("每日次数不能大于每月次数");
					return false;
				}		
				
				if(singleLimit<0){
					alert("单笔限额必须大于零!");
					return false;
				}
				if(dayLimit<0){
					alert("每日限额必须大于零!");
					return false;
				}
				if(monthLimit<0){
					alert("每月限额必须大于零!");
					return false;
				}
				if(dayTimes<0){
					alert("每日次数必须大于零!");
					return false;
				}
				if(monthTimes<0){
					alert("每月次数必须大于零!");
					return false;
				}
				return true;
			}
			
		</script>
</head>
<body>
	<div>
		<form id="bcForm" method="post" action="${ctx}/rm_limit_businesslimitcustom.htm?method=businessLimitCustomUpdate">
			<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
				<thead><tr class="trForContent1"><th colspan="2">编辑商户限额</th></tr></thead>
				<tbody>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">会员号：</td>
						<td class="border_top_right4" align=""><input type="text" name="businessId" id="businessId" maxlength="19" value="${businesslimitcustom.businessId}" readonly="readonly"></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">业务类型：</td>
						<td class="border_top_right4" align=""><li:codetable style="desc" selectedValue="${businesslimitcustom.businessType}" fieldName="businessType" attachOption="false" codeTableId="rmEnterpriseBusiTypeTable" /></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">单笔限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="singleLimit" id="singleLimit" value="${businesslimitcustom.singleLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每日限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="dayLimit" id="dayLimit" value="${businesslimitcustom.dayLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每月限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="monthLimit" id="monthLimit" value="${businesslimitcustom.monthLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每日次数：</td>
						<td class="border_top_right4" align=""><input type="text" name="dayTimes" id="dayTimes" value="${businesslimitcustom.dayTimes}" maxlength="8"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每月次数：</td>
						<td class="border_top_right4" align=""><input type="text" name="monthTimes" id="monthTimes" value="${businesslimitcustom.monthTimes}" maxlength="8"/></td>
					</tr>
					
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">状态：</td>
						<td class="border_top_right4" align=""><select name="status">
			<option value="0" <c:if test="${businesslimitcustom.status == 0}">selected</c:if>>无效</option>
			<option value="1" <c:if test="${businesslimitcustom.status == 1}">selected</c:if>>有效</option>
		</select></td>
					</tr>
					<tr class="trForContent1">
						<td colspan="2" class="border_top_right4" align="center">
						<input type="button" class="button01 button2" value="保 存" id="processSave"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="nyroModalClose button01 button2" id="closeBtn" value="取 消" />
						</td>
						
					</tr>
					<tr class="trForContent1">
						<td colspan="2" class="border_top_right4" align="center">
							<input type="hidden" name="sequenceId" value="${businesslimitcustom.sequenceId}"/>
						</td>
						
					</tr>
					</tbody>
			</table>
		</form>
	</div>
	<%-- <div>
	<div>编辑商户限额：</div>
	<form id="bcForm" method="post" action="${ctx}/rm_limit_businesslimitcustom.htm?method=businessLimitCustomUpdate">
	<div>会员号:<input type="text" name="businessId" id="businessId" maxlength="19" value="${businesslimitcustom.businessId}" readonly="readonly"></div>	
	<div>业务类型:
		
		<li:codetable style="desc" selectedValue="${businesslimitcustom.businessType}" fieldName="businessType" attachOption="false" codeTableId="rmEnterpriseBusiTypeTable" />
	</div>
	<!-- <div>MCC码:<input type="text" name="mcc" id="mcc" value="${businesslimitcustom.mcc}" maxlength="8"></input></div> -->
	<div>单笔限额:<input type="text" name="singleLimit" id="singleLimit" value="${businesslimitcustom.singleLimit}" maxlength="19"/></div>
	<div>每日限额:<input type="text" name="dayLimit" id="dayLimit" value="${businesslimitcustom.dayLimit}" maxlength="19"/></div>
	<div>每月限额:<input type="text" name="monthLimit" id="monthLimit" value="${businesslimitcustom.monthLimit}" maxlength="19"/></div>
	<div>每日次数:<input type="text" name="dayTimes" id="dayTimes" value="${businesslimitcustom.dayTimes}" maxlength="8"></div>
	<div>每月次数:<input type="text" name="monthTimes" id="monthTimes" value="${businesslimitcustom.monthTimes}" maxlength="8"></div>
	<!-- <div>转出账户数:<input type="text" name="batchAccounts" id="batchAccounts" value="${businesslimitcustom.batchAccounts}" maxlength="8"></div> -->
	<div>状态: 
		<select name="status">
			<option value="0" <c:if test="${businesslimitcustom.status == 0}">selected</c:if>>无效</option>
			<option value="1" <c:if test="${businesslimitcustom.status == 1}">selected</c:if>>有效</option>
		</select>
	</div>
	<div><input type="hidden" name="sequenceId" value="${businesslimitcustom.sequenceId}"></input></div>
	<div>
		<input type="button" class="button01" value="保 存" id="processSave"/>&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="nyroModalClose button01" id="closeBtn" value="取 消" />
	</div>
	</form>
	</div> --%>
</body>
</html>