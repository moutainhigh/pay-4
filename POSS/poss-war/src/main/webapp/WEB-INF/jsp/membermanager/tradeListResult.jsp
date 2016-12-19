<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language="javascript">
$(document).ready(
		function(){
			initSearchPage();
				});
				
function  initSearchPage(){
	
	document.getElementById('freeBalance').value = "<fmt:formatNumber value="${queryTransactionCountDTO.freeBalance}" pattern="#,#00.00#"/> 元";
	document.getElementById('totalBalance').value = "<fmt:formatNumber value="${queryTransactionCountDTO.totalBalance}" pattern="#,#00.00#"/> 元";
	document.getElementById('sumIncome').value = "<fmt:formatNumber value="${queryTransactionCountDTO.sumIncome}" pattern="#,#00.00#"/> 元";
	document.getElementById('sumPayment').value = "<fmt:formatNumber value="${queryTransactionCountDTO.sumPayment}" pattern="#,#00.00#"/> 元";
}
</script>
</head>

<body>

	<table class="border_all2" width="95%" border="0" cellspacing="0"	cellpadding="0" align="center">
	<tr class="trForContent1">
		   <td   align="right"  colspan="2">&nbsp;</td>            
			<td  align="center" colspan="5"><font color="red">收入：</font><input id="sumIncome" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly></input> &nbsp;&nbsp;&nbsp;<font color="red">支出：</font><input id="sumPayment" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly></input></td>
	</tr>	
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">流水号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易对方</font> </a></td>	
		<td class="border_right4"  nowrap><a class="s03"><font		
			color="#FFFFFF">收|支</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易金额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易状态</font> </a></td>
						
		
	</tr>
	<c:forEach items="${page.result}" var="memberTrade" varStatus = "memberTradeStatus">
	<c:choose>
       <c:when test="${memberTradeStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${memberTrade.businessDate}" type="date"/>&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.businessNo}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.productName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.bargainer}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.incomeAndExpenses_zh}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatNumber value="${memberTrade.price}" pattern="#,#00.00#"/> 元&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.businessStatus_zh}&nbsp;</td>	
						
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="tradeQuery" pageBean="${page}" sytleName="black2"/>
</body>

