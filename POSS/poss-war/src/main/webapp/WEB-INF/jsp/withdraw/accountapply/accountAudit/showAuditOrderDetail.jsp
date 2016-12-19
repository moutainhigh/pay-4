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
	<br>
	<table align="center" width="70%">
	<tr>
		<td>
			<p align="left"><font size="3" ><strong>付款人信息</strong></font></p>
		<hr>
		</td>
	</tr>
	</table>
	<table align="center" width="70%">
		<tr>
			<td width="25%">
				会员号:
			</td>
			<td width="25%">
				${order.memberCode}		
			</td>
			<td width="25%">
				付款人:
			</td>
			<td width="25%">
				${order.payer}	
			</td>
		</tr>

		<tr>
			<td>
				会员类型:
			</td>
			<td>
				${order.memberTypeStr}
			</td>
			<td>
				账户类型:
			</td>
			<td>
				${order.memberAccTypeStr}	
			</td>
		</tr>
		<tr>
			<td>
				审核等级:
			</td>
			<td>
				${order.prioritysStr}
			</td>
			<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
	<br>
	<br>
	
	<table align="center" width="70%">
	<tr>
		<td>
			<p align="left"><font size="3" ><strong>提现记录详细信息</strong></font></p>
			<hr>
		</td>
	</tr>
	</table>
	<table align="center" width="70%">
		<tr>
			<td width="25%">
				交易流水号:
			</td>
			<td width="25%">
				${order.sequenceId}		
			</td>
			<td width="25%">
				银行名称:
			</td>
			<td width="25%">
				<li:code2name itemList="${withdrawBankList}"  selected="${order.bankKy}"/>
			</td>
		</tr>

		<tr>
			<td>
				开户行名称:
			</td>
			<td>
				${order.bankBranch}		
			</td>
			<td>
				银行账户:
			</td>
			<td>
				${order.bankAcct}		
			</td>
		</tr>
		<tr>
			<td>
				收款人:
			</td>
			<td>
				${order.accountName}
			</td>
			<td>
				汇款金额(元):
			</td>
			<td>
				<fmt:formatNumber value="${order.amount/1000}" pattern="#,##0.00"  />
			</td>
		</tr>

		<tr>
			<td>
				省份:
			</td>
			<td>
				${order.bankProvinceStr}	
			</td>
			<td>
				城市:
			</td>
			<td>
				${order.bankCityStr}
			</td>
		</tr>

		<tr>
			<td>
				交易时间:
			</td>
			<td>
				<fmt:formatDate value="${order.webAuditTime}" type="both"/>
			</td>
			<td>
				创建时间:
			</td>
			<td>
				<fmt:formatDate value="${order.createTime}" type="both"/>
			</td>
		</tr>

		<tr>
			<td>
				交易备注：
			</td>
			<td>
				${order.orderRemarks}
			</td>
			<td>
				状态:
			</td>
			<td>
				${order.statusStr}
			</td>
		</tr>
	</table>
	<br>
	<br>
	<table align="center" width="70%">
	<tr>
		<td>
			<p align="left"><font size="3" ><strong>后台操控记录</strong></font></p>
			<hr>
		</td>
	</tr>
	</table>
	<table border="1" align="center" width="70%">
		<c:forEach items="${history}" var="his"> 
			<tr>
				<td width="9%">节点名称：</td>     
				<td width="9%">${his.nodeName}&nbsp;</td>
				<td width="9%">操作员：</td>     
				<td width="9%">${his.assignee}&nbsp;</td>
				<td width="9%">操作状态：</td>     
				<td width="9%">${his.handleStatus}&nbsp;</td>
				<td width="9%">操作时间：</td>     
				<td  width="15%"><fmt:formatDate value="${his.createTime}" type="both"/>&nbsp;</td>
				<td width="9%">操作备注：</td>     
				<td>${his.taskMessage}&nbsp;</td>
			</tr>
			</c:forEach>
	</table>
		操作备注：<textarea rows="4" cols="45" name="auditRemark" id="auditRemark">0000</textarea>
	</form>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>
	<br><br>
	<table align="center">
		<tr>
			<td align="center">
				<c:if test="${(not empty order.status) && (3 == order.status || 0 == order.status)}">
					<input type="button" onclick="sendAudit(1)" name="submitBtn" id="btn1" value="审核通过" class="button4">
					<input type="button" onclick="sendAudit(2)" name="submitBtn" id="btn2" value="审核拒绝" class="button4">
					<c:if test="${0 == order.status}">
						<input type="button" onclick="sendDelay(3)" name="submitBtn" id="btn3" value="滞   留" class="button4">
					</c:if>
				</c:if>
				<input type="button" onclick="javascript:parent.closePage('${orderDtosequenceId}audit')" id="btn4" name="submitBtn" value="关    闭" class="button4"> 
			</td>
		</tr>
	</table>
</html>

