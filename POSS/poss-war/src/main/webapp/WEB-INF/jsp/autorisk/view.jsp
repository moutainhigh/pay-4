<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//返回
		function processBack(){
			location.href = "context_fundout_checklog.controller.htm";
		}
	</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">审核明细</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<br>
	
	<table class="border_all2" width="55%" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trbgcolor10">
			<td class="border_top_right4">
				会员号:
			</td>
			<td class="border_top_right4">
				${dto.memberCode}
			</td>
			<td class="border_top_right4">
				会员名称:
			</td>
			<td class="border_top_right4">
				${dto.memberName}
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">
				交易号:
			</td>
			<td class="border_top_right4">
				${dto.orderNo}
			</td>
			<td class="border_top_right4">
				规则号:
			</td>
			<td class="border_top_right4">
				${dto.ruleCode}
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">
				进入日期:
			</td>
			<td class="border_top_right4">
				<fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
			<td class="border_top_right4">
				审核日期:
			</td>
			<td class="border_top_right4">
				<fmt:formatDate value="${dto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">
				审核人:
			</td>
			<td class="border_top_right4">
				${dto.operator}
			</td>
			<td class="border_top_right4">
				审核备注:
			</td>
			<td class="border_top_right4">
				${dto.checkRemark}
				<c:if test="${empty dto.checkRemark}">&nbsp;</c:if>
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td colspan="4" align="center" class="border_top_right4">
				<input type="button" name="backBtn"  value="返  回" onclick="processBack();" class="button2"/>
			</td>
		</tr>
	</table>
</body>

