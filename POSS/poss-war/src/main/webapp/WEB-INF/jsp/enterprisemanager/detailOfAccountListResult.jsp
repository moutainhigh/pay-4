<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">

</script>
</head>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >
			交易日期</th>
		<th class=""  >
			交易流水号</th>
		<th class=""  >
			交易类型</th>		
		<th class=""  >
			收入</th>
	    <th class=""  >
			支出 </font></a></th>	
		<!-- <th class=""  >
			费用 </font></a></th> -->
		<th class=""  >
			余额</th>	
			
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="detailOfAccount" varStatus = "detailOfAccountStatus">
	<c:choose>
       <c:when test="${detailOfAccountStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			<td class="border_top_right4" align="center" >${detailOfAccount.createDate}&nbsp;</td>		
			<td class="border_top_right4" align="center" >${detailOfAccount.dealId}&nbsp;</td>
			<td class="border_top_right4" align="center" >
				${detailOfAccount.dealType}&nbsp;
			</td>
			<td class="border_top_right4" align="center" >${detailOfAccount.strRevenue}&nbsp;</td>
			<td class="border_top_right4" align="center" >${detailOfAccount.strPay}</td>
	        <!-- <td class="border_top_right4" align="center" >${detailOfAccount.fee}&nbsp;</td>-->
	        <td class="border_top_right4" align="center" >${detailOfAccount.balance}&nbsp;</td>
		   
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="queryList" pageBean="${page}" sytleName="black2"/>


</body>

