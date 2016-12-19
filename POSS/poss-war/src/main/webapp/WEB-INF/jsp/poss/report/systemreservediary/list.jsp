<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">

$(document).ready(function(){
	  
});
</script>
</head>
<body>
	<div class="e_cur_tit2"><i class="fr"><a href="javascript:exportExcel(${page.totalCount});">下载查询结果</a></i></div>
	<div class="tableList">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="90%">
	<thead>
	  <tr class="tabT">
	    <th class="tabTitle" align="center" scope="col">日期</th>
	    <th class="tabTitle" align="center" scope="col">企业会员账户余额(元)</th>
	    <th class="tabTitle" align="center" scope="col">个人会员账户余额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应收未收金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应付未付金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应退未退金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">系统备付金余额(元)</th>
	  </tr>
	</thead> 
	<c:forEach items="${page.result}" var="dto">
	  <tr>
	  	<td align="center">&nbsp;
	  		${dto.countDateStr}
	    </td>
	    <td align="center">&nbsp;
	    	<fmt:formatNumber value="${dto.enterpriseAccBalance/1000}" pattern="#,##0.00"/>
	    </td>
	    <td align="center">&nbsp;
	    	<fmt:formatNumber value="${dto.individualAccBalance/1000}" pattern="#,##0.00"/>
	    </td>
       <td align="center">&nbsp;
     		<fmt:formatNumber value="${dto.uncollectedAmt/1000}" pattern="#,##0.00"/>&nbsp;&nbsp;
     		<a href="javascript:void(window.location.href='${ctx}/report/systemReserveDiary.do?method=detail&countDate=${dto.countDateStr}&startDate=${map.startDate}&endDate=${map.endDate}')">明细</a>
       </td>
       <td align="center">&nbsp;
	    	<fmt:formatNumber value="${dto.unpaidAmt/1000}" pattern="#,##0.00"/>
	    </td>
	    <td align="center">&nbsp;
	    	<fmt:formatNumber value="${dto.noRefundAmount/1000}" pattern="#,##0.00"/>
	    </td>
	    <td align="center">&nbsp;
	    	<fmt:formatNumber value="${dto.systemReserveBalance/1000}" pattern="#,##0.00"/>
	    </td>
	  	</tr>
	  </c:forEach>
	</table>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
</body>
</html>
