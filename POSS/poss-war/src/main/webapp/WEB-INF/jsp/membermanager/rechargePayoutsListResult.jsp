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
	//queryFIFOCountDTO.totalBalance																					
	document.getElementById('freeBalance').value = "<fmt:formatNumber value="${queryFIFOCountDTO.freeBalance}" pattern="#,#00.00#"/> 元";
	document.getElementById('totalBalance').value = "<fmt:formatNumber value="0" pattern="#,#00.00#"/> 元";
	document.getElementById('despositTotal').value = "<fmt:formatNumber value="${queryFIFOCountDTO.despositTotal}" pattern="#,#00.00#"/> 元";
	document.getElementById('withdrawTotal').value = "<fmt:formatNumber value="${queryFIFOCountDTO.withdrawTotal}" pattern="#,#00.00#"/> 元";
	document.getElementById('despositBackTotal').value = "<fmt:formatNumber value="${queryFIFOCountDTO.despositBackTotal}" pattern="#,#00.00#"/> 元";
}
</script>
</head>

<body>

	<table class="border_all2" width="95%" border="0" cellspacing="0"	cellpadding="0" align="center">
	<tr class="trForContent1">
		<td  align="right"  colspan="2">&nbsp;</td>
		<td  align="center" colspan="5"><font color="red">充值：<input id="despositTotal" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly ></input>  &nbsp;提现：<input id="withdrawTotal" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly ></input> &nbsp;充退：<input id="despositBackTotal" type="text" style="border:0;border-bottom:0 solid black;background:;" readonly ></input> </font></td>
	</tr>	
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">流水号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">交易类型</font> </a></td>	
		<td class="border_right4"  nowrap><a class="s03"><font		
			color="#FFFFFF">收|支</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">金额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">资金渠道</font> </a></td>
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
			<td class="border_top_right4" align="center" nowrap><fmt:formatDate value="${memberTrade.tradeOrderTime}" type="date"/>&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.serialNo}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.fifoStatue}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.incomeOrPay}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap><fmt:formatNumber value="${memberTrade.orderAmount}" pattern="#,#00.00#"/> 元&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.bankName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${memberTrade.businessStatue}&nbsp;</td>	
						
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="rechargePayoutsQuery" pageBean="${page}" sytleName="black2"/>
</body>

