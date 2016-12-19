<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
function exportExcel(){
	//url: "${ctx}/",
	//location.href = "websiteAnnouncement.do?method=doExcel";
	document.getElementById("balanceExportForm").submit();
}
</script>
</head>

<body>
<form id="balanceExportForm" name="balanceExportForm"  method="post" action="personalAcctBalanceExport.do">
 	<input type="hidden" name="method" value="doExport"/>
 	
	<input type="hidden" name="loginName" value="${paraMap.loginName}"/>
	<input type="hidden" name="dealType" value="${paraMap.dealType}"/>
	<input type="hidden" name="type" value="${paraMap.type}"/>
	<input type="hidden" name="balanceStrat" value="${paraMap.balanceStrat}"/>
	<input type="hidden" name="balanceEnd" value="${paraMap.balanceEnd}"/>
	<input type="hidden" name="startDate" value="${paraMap.startDate}"/>
	<input type="hidden" name="endDate" value="${paraMap.endDate}"/>
 </form>
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<tr class="trForContent1" > 
		<td class=""  colspan="9" align="right">
			<input id="download" type="button" onclick="exportExcel();" value="导出EXCEL" class="button5" />
		</td>
	</tr>
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >交易日期</th>
		<th class=""  >交易流水号</th>
		<th class=""  >交易类型</th>
		<!-- 
		<th class=""  >交易对方账号</th>
		 -->
		<th class=""  >收入</th>
		<th class=""  >支出</th>
		<!-- <th class=""  >费用</th>-->
		<th class=""  >余额</th>

	</tr>
	</thead>
	<c:forEach items="${page.result}" var="personal" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="" align="center" >${personal.payDate}&nbsp;</td>
			<td class="" align="center" >${personal.orderNumber}&nbsp;</td>
			<td class="" align="center" >${personal.dealType}&nbsp;</td>
			<!-- <td class="" align="center" >${personal.another}&nbsp;</td> -->
			<td class="" align="center" >${personal.strRevenue}&nbsp;</td>
			<td class="" align="center" >${personal.strPay}&nbsp;</td>
			<!-- <td class="" align="center" >${personal.fee}&nbsp;</td>-->
			<td class="" align="center" >${personal.strBalance}&nbsp;</td>
		</tr>
	</c:forEach>
	
</table>
<table  width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
	<tr class="" align="center" valign="middle"></tr>
	</br>
	<tr class="trForContent1" align="center" valign="middle">
		<table width="100%" class="trForContent1">
			<tr>
				<td align="center">
					<span>收入总额：${returnMap.totalRevenue}</span> 
					<span style="padding:0 20px;">支出总额：${returnMap.totalPay}</span>
				</td>
			</tr>
		</table>
	</tr>
</table>	
<li:pagination methodName="personalAcctBalanceQuery" pageBean="${page}" sytleName="black2"/>
</body>

