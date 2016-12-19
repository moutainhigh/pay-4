<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
	<script language="javascript">
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		if(document.getElementById('batchStatus').value==1){
			alert('已出批次,不能拒绝或者回退');
			btnDisabledSetFalse();
			return false;
		}
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
			url: "${ctx}/withdrawLiquidateAudit.do?method=audit&wkKey="+$("#wkKey").val(),
			data: pars,
			success: function(result) {
				var msg = eval('('+result+')');
				if(msg.isSuccess){
					$('#infoLoadingDiv').dialog('close');
					$.fo.alert('清结算提交成功');
					$("#btn3").attr("disabled",false);
				}else{
					$('#infoLoadingDiv').dialog('close');	
					$.fo.alert(msg.sequenceId+'清结算提交失败');
					btnDisabledSetFalse();
				}
			}
		});
	}

	function back(){
		window.location="${ctx}/withdrawLiquidateAudit.do";
	}

	function btnDisabledSetTrue(){
		$("#btn1").attr("disabled",true);
		$("#btn2").attr("disabled",true);
		$("#btn3").attr("disabled",true);
	}

	function btnDisabledSetFalse(){
		$("#btn1").attr("disabled",false);
		$("#btn2").attr("disabled",false);
		$("#btn3").attr("disabled",false);
	}
	</script>
</head>
	<form id="orderForm" name="orderForm">
	<input type="hidden" name="wkKey" id="wkKey" value="${order.workOrderky}">
	<input type="hidden" name="batchStatus" id="batchStatus" value="${order.batchStatus}">
	<title align="left">复核提现数据详情</title>
	<br>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4">
			<p align="left"><font size="3" ><strong>付款人信息</strong></font></p>
		
		</td>
	</tr>
	</table>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent2">
			<td class="border_top_right4">
				会员号:
			</td>
			<td class="border_top_right4">
				${order.memberCode}		
			</td>
			<td class="border_top_right4">
				付款人:
			</td>
			<td class="border_top_right4">
				${order.payer}	
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4">
				会员类型:
			</td>
			<td class="border_top_right4">
				${order.memberTypeStr}
			</td>
			<td class="border_top_right4">
				账户类型:
			</td>
			<td class="border_top_right4">
				${order.memberAccTypeStr}	
			</td>
		</tr>
		<tr class="trForContent2">
			<td class="border_top_right4">
				审核等级:
			</td>
			<td class="border_top_right4">
				${order.prioritysStr}
			</td>
			<td class="border_top_right4">
				&nbsp;
			</td>
			<td class="border_top_right4">
				&nbsp;
			</td>
		</tr>
	</table>
	<br>
	<br>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4">
			<p align="left"><font size="3" ><strong>提现记录详细信息</strong></font></p>
			
		</td>
	</tr>
	</table>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent2">
			<td class="border_top_right4">
				交易流水号:
			</td>
			<td class="border_top_right4">
				${order.sequenceId}		
			</td>
			<td class="border_top_right4">
				银行名称:
			</td>
			<td class="border_top_right4">
				<li:code2name itemList="${withdrawBankList}"  selected="${order.bankKy}"/>
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4">
				开户行名称:
			</td>
			<td class="border_top_right4">
				${order.bankBranch}		
			</td>
			<td class="border_top_right4">
				银行账户:
			</td>
			<td class="border_top_right4">
				${order.bankAcct}		
			</td>
		</tr>
		<tr class="trForContent2">
			<td class="border_top_right4">
				收款人:
			</td>
			<td class="border_top_right4">
				${order.accountName}
			</td>
			<td class="border_top_right4">
				汇款金额(元):
			</td>
			<td class="border_top_right4">
				<fmt:formatNumber value="${order.amount/1000}" pattern="#,##0.00"  />
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4">
				省份:
			</td>
			<td class="border_top_right4">
				${order.bankProvinceStr}	
			</td>
			<td class="border_top_right4">
				城市:
			</td>
			<td class="border_top_right4">
				${order.bankCityStr}
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4">
				交易时间:
			</td>
			<td class="border_top_right4">
				<fmt:formatDate value="${order.webAuditTime}" type="both"/>
			</td>
			<td class="border_top_right4">
				创建时间:
			</td>
			<td class="border_top_right4">
				<fmt:formatDate value="${order.createTime}" type="both"/>
			</td>
		</tr>

		<tr class="trForContent2">
			<td class="border_top_right4">
				交易备注：:
			</td>
			<td class="border_top_right4">
				${order.orderRemarks}
			</td>
			<td class="border_top_right4">
				状态:
			</td>
			<td class="border_top_right4">
				${order.statusStr}
			</td>
		</tr>
	</table>
	<br>
	<br>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td class="border_top_right4">
			<p align="left"><font size="3" ><strong>后台操控记录</strong></font></p>
			
		</td>
	</tr>
	</table>
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" style="width:80%;margin:0 auto;">
		<c:forEach items="${history}" var="his" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="">节点名称：</td>     
				<td class="">${his.nodeName}&nbsp;</td>
				<td class="">操作员：</td>     
				<td class="">${his.assignee}&nbsp;</td>
				<td class="">操作状态：</td>     
				<td class="">${his.handleStatus}&nbsp;</td>
				<td class="">操作时间：</td>     
				<td ><fmt:formatDate value="${his.createTime}" type="both"/>&nbsp;</td>
				<td class="">操作备注：</td>     
				<td class="">${his.taskMessage}&nbsp;</td>
				     
			</tr>
			</c:forEach>
	</table>
	<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">操作备注：</td>
		<td><textarea rows="4" cols="45" name="auditRemark" id="auditRemark"></textarea></td>
		</tr>
		
	</table>
	
	
	</form>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>

	<table align="center">
		<tr>
			<td align="center">
				<c:if test="${(not empty order.status) && (6 > order.status) && (0 != order.status)}">
					<input type="button" onclick="sendAudit(1);" id="btn1" name="submitBtn" value="退回" class="button2">	
					<input type="button" onclick="sendAudit(0);"  id="btn2" 
						<c:if test="${4 eq order.status }">disabled="disabled"</c:if> 
						name="submitBtn" value="拒绝" class="button2">
				</c:if>
				<input type="button" onclick="javascript:parent.closePage('${orderDtosequenceId}liquidate')" 
						id="btn3" name="submitBtn" value="关闭" class="button2">
			</td>
		</tr>
	</table>
</html>

