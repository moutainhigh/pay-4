<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<form id="orderForm" name="orderForm">
	<input type="hidden" name="wkKey" id="wkKey" value="${order.workOrderky}">
	<title align="left">复核提现数据详情</title>
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
				<fmt:formatDate value="${order.createTime}" type="date"/>
			</td>
			<td>
				状态:
			</td>
			<td>
				${order.statusStr}
			</td>
		</tr>

		<tr>
			<td>
				交易备注：:
			</td>
			<td>
				${order.orderRemarks}
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
			<p align="left"><font size="3" ><strong>风控操控记录</strong></font></p>
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
				<td  width="15%">${his.endTime}&nbsp;</td>
				<td width="9%">操作备注：</td>     
				<td>${his.taskMessage}&nbsp;</td>
				     
			</tr>
			</c:forEach>
	</table>
	</form>
	<br><br>
	<table align="center">
		<tr>
			<td align="center">
				<input type="button" onclick="javascript:parent.closePage('${orderDtosequenceId}allocate')" name="submitBtn" value="关 闭" class="button2"> 
				<!--<input type="button" onclick="back();" name="submitBtn" value="返    回" class="button2">-->			
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		function back() {
			window.location="${ctx}/withdrawAllocate.do";
		}
	</script>
</html>

