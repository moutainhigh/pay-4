<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量到银行有未生成子订单明细列表</title>
		<script type="text/javascript">
			function genOrder(btn){
				btn.setAttribute("disabled",true);
				window.location="${ctx}/orderMassPayToBank.htm?method=generatorOrder&uploadSeq="+${uploadSeq}+"&massOrderSeq="+
					document.getElementById("massOrderSeq").value;
			}

			function back(){
				window.location="${ctx}/orderMassPayToBank.htm";
			}
		</script>
	</head>
	
	<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0"
			align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
				<div align="center"><font class="titletext">批量到银行未生成子订单明细列表</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		<input type="hidden" name="massOrderSeq" id="massOrderSeq" value="${massOrderSeq}">
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th >序号</th>
					<th >上传批次号</th>
					<th >付款会员号</th>
					<th >收款人开户行</th>
					<th >收款人银行账号</th>
					<th >付款金额</th>
					<th >记录校验状态</th>
					<th >订单生成状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="detail" items="${detailList}" varStatus="v">
					<tr>
						<td>${v.index+1}</td>
						<td>${detail.businessNo}</td>
						<td>${detail.payerMemberCode}</td>
						<td>${detail.openingBankName}</td>
						<td>${detail.payeeBankAcct}</td>
						<td><fmt:formatNumber value="${detail.amount * 0.001}" pattern="#,##0.00"/></td>
						<td>
							${detail.validateStatus==1?"正常":"有误"}
						</td>
						<td>
							${detail.orderStatus==0?"未生成":"已生成"}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		
		<table width="25%" border="0" cellspacing="0" cellpadding="0"
			align="center" height="21">
			<tr>
				<td><input type="button" class="button4" value="生成订单" onclick="genOrder(this);"/></td>
				<td><input type="button" class="button4" value="返回" onclick="back();"/></td>
			</tr>
		</table>
	</body>
</html>