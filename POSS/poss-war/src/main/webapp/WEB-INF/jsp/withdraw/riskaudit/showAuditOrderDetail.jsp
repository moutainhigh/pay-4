<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<script language="javascript">
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		var auditRemark = $.trim($("#auditRemark").val());
		if(!s_validateStrLength(auditRemark,2,100)){
			$.fo.alert('批量操作备注最小2个字符,最大不超过50个汉字！');
			btnDisabledSetFalse();	
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
		$.ajax({
			type: "POST",
			url: "${ctx}/withdrawAudit.do?method=audit",
			data: pars,
			success: function(result) {
				var msg = eval('('+result+')');
				if(msg.isSuccess){
					$('#infoLoadingDiv').dialog('close');
					$.fo.alert('审核提交成功');
					$("#btn4").attr("disabled",false);
				}else{
					$('#infoLoadingDiv').dialog('close');	
					$.fo.alert(msg.sequenceId+'审核提交失败');
					btnDisabledSetFalse();
				}
			}
		});
	}

	function btnDisabledSetTrue(){
		$("#btn1").attr("disabled",true);
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
		$("#btn4").attr("disabled",true);
	}

	function btnDisabledSetFalse(){
		$("#btn1").attr("disabled",false);
		$("#btn2").attr("disabled",false);
		$("#btn3").attr("disabled",false);
		$("#btn4").attr("disabled",false);
	}

	function back(){
		window.location="${ctx}/withdrawAudit.do";
	}

	function sendDelay(key,obj){
		btnDisabledSetTrue();
		var auditRemark = $.trim($("#auditRemark").val());
		if(!s_validateStrLength(auditRemark,2,100)){
			$.fo.alert('批量操作备注最小2个字符,最大不超过50个汉字！');
			btnDisabledSetFalse();	
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
		$.ajax({
			type: "POST",
			url: "${ctx}/withdrawAudit.do?method=delay",
			data: pars,
			success: function(result) {
				var msg = eval('('+result+')');
				if(msg.isSuccess){
					$('#infoLoadingDiv').dialog('close');
					$.fo.alert('滞留提交成功');
					$("#btn4").attr("disabled",false);
				}else{
					$('#infoLoadingDiv').dialog('close');	
					$.fo.alert(msg.sequenceId+'滞留提交失败');
					btnDisabledSetFalse();
				}
			}
		});
	}
	
	</script>
</head>
	<form id="orderForm" name = "orderForm">
	<input type="hidden" name="wkKey" id="wkKey" value="${order.workOrderky}">
	<title align="left">审核提现数据详情</title>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4" >
			<p align="left"><font size="3" ><strong>付款人信息</strong></font></p>
		</td>
	</tr>
	</table>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent2">
			<td class="border_top_right4" width="25%">
				会员号:
			</td>
			<td class="border_top_right4" width="25%">
				${order.memberCode}		
			</td>
			<td class="border_top_right4" width="25%">
				付款人:
			</td>
			<td class="border_top_right4" width="25%">
				${order.payer}	
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4" >
				会员类型:
			</td>
			<td class="border_top_right4" >
				${order.memberTypeStr}
			</td>
			<td class="border_top_right4" >
				账户类型:
			</td>
			<td class="border_top_right4" >
				${order.memberAccTypeStr}	
			</td>
		</tr>
		<tr class="trForContent2">
			<td class="border_top_right4" >
				审核等级:
			</td>
			<td class="border_top_right4" >
				${order.prioritysStr}
			</td>
			<td class="border_top_right4" >
				&nbsp;
			</td>
			<td class="border_top_right4" >
				&nbsp;
			</td>
		</tr>
	</table>
	<br>
	<br>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4" >
			<p align="left"><font size="3" ><strong>提现记录详细信息</strong></font></p>
			
		</td>
	</tr>
	</table>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent2">
			<td class="border_top_right4" width="25%">
				交易流水号:
			</td>
			<td class="border_top_right4" width="25%">
				${order.sequenceId}		
			</td>
			<td class="border_top_right4" width="25%">
				银行名称:
			</td>
			<td class="border_top_right4" width="25%">
				<li:code2name itemList="${withdrawBankList}"  selected="${order.bankKy}"/>
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4" >
				开户行名称:
			</td>
			<td class="border_top_right4" >
				${order.bankBranch}		
			</td>
			<td class="border_top_right4" >
				银行账户:
			</td>
			<td class="border_top_right4" >
				${order.bankAcct}		
			</td>
		</tr>
		<tr class="trForContent2">
			<td class="border_top_right4" >
				收款人:
			</td>
			<td class="border_top_right4" >
				${order.accountName}
			</td>
			<td class="border_top_right4" >
				汇款金额(元):
			</td>
			<td class="border_top_right4" >
				<fmt:formatNumber value="${order.amount/1000}" pattern="#,##0.00"  />
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4" >
				省份:
			</td>
			<td class="border_top_right4" >
				${order.bankProvinceStr}	
			</td>
			<td class="border_top_right4" >
				城市:
			</td>
			<td class="border_top_right4" >
				${order.bankCityStr}
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4" >
				交易时间:
			</td>
			<td class="border_top_right4" >
				<fmt:formatDate value="${order.webAuditTime}" type="both"/>
			</td>
			<td class="border_top_right4" >
				创建时间:
			</td>
			<td class="border_top_right4" >
				<fmt:formatDate value="${order.createTime}" type="both"/>
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4" >
				交易备注：
			</td>
			<td class="border_top_right4" >
				${order.orderRemarks}
			</td>
			<td class="border_top_right4" >
				状态:
			</td>
			<td class="border_top_right4" >
				${order.statusStr}
			</td>
		</tr>
	</table>
	<br>
	<br>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4" >
			<p align="left"><font size="3" ><strong>后台操控记录</strong></font></p>
			
		</td>
	</tr>
	</table>
	<table border="1" align="center" width="80%">
		<c:forEach items="${history}" var="his"> 
			<tr class="trForContent2">
				<td class="border_top_right4" width="9%">节点名称：</td>     
				<td class="border_top_right4" width="9%">${his.nodeName}&nbsp;</td>
				<td class="border_top_right4" width="9%">操作员：</td>     
				<td class="border_top_right4" width="9%">${his.assignee}&nbsp;</td>
				<td class="border_top_right4" width="9%">操作状态：</td>     
				<td class="border_top_right4" width="9%">${his.handleStatus}&nbsp;</td>
				<td class="border_top_right4" width="9%">操作时间：</td>     
				<td class="border_top_right4"  width="15%"><fmt:formatDate value="${his.createTime}" type="both"/>&nbsp;</td>
				<td class="border_top_right4" width="9%">操作备注：</td>     
				<td class="border_top_right4" >${his.taskMessage}&nbsp;</td>
			</tr>
			</c:forEach>
	</table><br>
	<table width="80%" align="center">
		<tr>
			<td width="50%" align="right">操作备注：</td>
			<td><textarea rows="4" cols="45" name="auditRemark" id="auditRemark">0000</textarea></td>
		</tr>
	</table>
		<br>
	</form>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>
	<table align="center">
		<tr>
			<td class="" align="center">
				<c:if test="${(not empty order.status) && (3 == order.status || 0 == order.status)}">
					<input type="button" onclick="sendAudit(1)" name="submitBtn" id="btn1" value="审核通过" class="button2">
					<input type="button" onclick="sendAudit(2)" name="submitBtn" id="btn2" value="审核拒绝" class="button2">
					<c:if test="${0 == order.status}">
						<input type="button" onclick="sendDelay(3)" name="submitBtn" id="btn3" value="滞   留" class="button2">
					</c:if>
				</c:if>
				<input type="button" onclick="javascript:parent.closePage('${orderDtosequenceId}audit')" id="btn4" name="submitBtn" value="关    闭" class="button2"> 
			</td>
		</tr>
	</table>
</html>

