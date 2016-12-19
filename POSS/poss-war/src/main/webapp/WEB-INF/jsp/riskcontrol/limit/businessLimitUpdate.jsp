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

				var pars = $("#businessLimitForm").serialize();
				$.ajax({
					type: "POST",
					url: "${ctx}/rm_limit_businesslimit.htm?method=businessLimitUpdate",
					data: pars,
					success: function(result) {
						if (result == 'success') {
							$('#closeBtn').click();
							//重新载入列表
							searchBusinessLimit();
						} else {
							alert("操作失败!");
						}
					}
				});
			});
			
			function validate() {
				var singleLimit = $.trim($("#singleLimit").val());//单笔限额
				var dayLimit = $.trim($("#dayLimit").val());//每日限额
				var monthLimit = $.trim($("#monthLimit").val());//每月限额
				var dayTimes = $.trim($("#dayTimes").val());//每日次数
				var monthTimes = $.trim($("#monthTimes").val());//每月次数
				var batchAccounts = $.trim($("#batchAccounts").val());//转出账户数

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
		//		if('' == batchAccounts){
		//			alert("转出账户数为必填项!");
		//			return false;
		//		}
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
			//	if(!s_isNumber(batchAccounts)){
		//			alert("转出账户数必须为数字!");
		//			return false;
		//		}
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
		<form id="businessLimitForm" method="post">
			<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="3" align="center">
				<thead><tr class="trForContent1"><th colspan="2">编辑商户限额</th></tr></thead>
				<tbody>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">业务类型：</td>
						<td class="border_top_right4" align=""><li:codetable  selectedValue="${businesslimit.businessType}" style="desc" fieldName="businessType" attachOption="false" codeTableId="rmEnterpriseBusiTypeTable" /></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">风险等级：</td>
						<td class="border_top_right4" align=""><c:forEach items="${risklevellist}" var="risklevel">
				 <c:if test="${risklevel.riskLevel == businesslimit.riskLevel}">${risklevel.levelName}</c:if>
			</c:forEach></td>
					</tr>
				
				<tr class="trForContent1">
						<td class="border_top_right4" align="right">MCC码：</td>
						<td class="border_top_right4" align=""><input type="text" name="mcc" readonly="readonly" value="${businesslimit.mcc}" maxlength="8"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">单笔限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="singleLimit" id="singleLimit" value="${businesslimit.singleLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每日限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="dayLimit" id="dayLimit" value="${businesslimit.dayLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每月限额：</td>
						<td class="border_top_right4" align=""><input type="text" name="monthLimit" id="monthLimit" value="${businesslimit.monthLimit}" maxlength="19"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每日次数：</td>
						<td class="border_top_right4" align=""><input type="text" name="dayTimes" id="dayTimes" value="${businesslimit.dayTimes}" maxlength="8"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">每月次数：</td>
						<td class="border_top_right4" align=""><input type="text" name="monthTimes" id="monthTimes" value="${businesslimit.monthTimes}" maxlength="8"/></td>
					</tr>
					<tr class="trForContent1">
						<td class="border_top_right4" align="right">行业限额相关：</td>
						<td class="border_top_right4" align=""><select name="status">
			<option value="0" <c:if test="${businesslimit.status == 0}">selected</c:if>>相关</option>
			<option value="1" <c:if test="${businesslimit.status == 1}">selected</c:if>>不相关</option>
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
					<input type="hidden" name="sequenceId" value="${businesslimit.sequenceId}"/>
						</td>
						
					</tr>
					
					</tbody>
			</table>
		</form>
	</div>
	<%-- <div>编辑商户限额：</div>
		
		<div>业务类型:<li:codetable  selectedValue="${businesslimit.businessType}" style="desc" fieldName="businessType" attachOption="false" codeTableId="rmEnterpriseBusiTypeTable" />
		</div>
		<div>风险等级:
			<c:forEach items="${risklevellist}" var="risklevel">
				 <c:if test="${risklevel.riskLevel == businesslimit.riskLevel}">${risklevel.levelName}</c:if>
			</c:forEach>
		</div>
		<div>MCC码:<input type="text" name="mcc" readonly="readonly" value="${businesslimit.mcc}" maxlength="8"/></div>
		<div>单笔限额:<input type="text" name="singleLimit" id="singleLimit" value="${businesslimit.singleLimit}" maxlength="19"/></div>
		<div>每日限额:<input type="text" name="dayLimit" id="dayLimit" value="${businesslimit.dayLimit}" maxlength="19"/></div>
		<div>每月限额:<input type="text" name="monthLimit" id="monthLimit" value="${businesslimit.monthLimit}" maxlength="19"/></div>
		<div>每日次数:<input type="text" name="dayTimes" id="dayTimes" value="${businesslimit.dayTimes}" maxlength="8"/></div>
		<div>每月次数:<input type="text" name="monthTimes" id="monthTimes" value="${businesslimit.monthTimes}" maxlength="8"/></div>
		<!-- <div>转出账户数:<input type="text" name="batchAccounts" id="batchAccounts" value="${businesslimit.batchAccounts}" maxlength="8"></div>-->
		<div>行业限额相关: <select name="status">
			<option value="0" <c:if test="${businesslimit.status == 0}">selected</c:if>>相关</option>
			<option value="1" <c:if test="${businesslimit.status == 1}">selected</c:if>>不相关</option>
		</select></div>
		<div>
		<input type="hidden" name="sequenceId" value="${businesslimit.sequenceId}"/></div>
		<div>
			<input type="button" class="button01" value="保 存" id="processSave"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" class="nyroModalClose button01" id="closeBtn" value="取 消" />
		</div>
		</form>
		</div> --%>
	</body>
</html>