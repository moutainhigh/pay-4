<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	5: {sorter: false}
				 }});      
		});
</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	
	
	<div class="tableList">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	  	<th class="tabTitle" align="center"  >日期</th>
	  	<th class="tabTitle" align="center"  >用户类型</th>
	  	<th class="tabTitle" align="center"  >会员编号</th>
	  	<th class="tabTitle" align="center"  >会员名称</th>
	    <th class="tabTitle" align="center"  >是否包量 </th>
	    <th class="tabTitle" align="center"  >入款流量（元）</th>
	    <th class="tabTitle" align="center"  >出款流量（元）</th>
	    <th class="tabTitle" align="center"  >入款手续费收入（元）</th>
	    <th class="tabTitle" align="center"  >出款手续费收入（元）</th>
	    <th class="tabTitle" align="center"  >入款成本（元）</th>
	    <th class="tabTitle" align="center"  >出款成本（元）</th>
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	  	<td style="border-right:2px solid #aaa" >&nbsp;
	  		<fmt:formatDate value="${dto.reportDate}" type="date"/>
	    </td>
	    <td style="border-right:2px solid #aaa" >&nbsp;
	    	${dto.memberTypeStr}
	    </td>
	    <td style="border-right:2px solid #aaa" >&nbsp;
	    	${dto.memberCode}
	    </td>
	    <td style="border-right:2px solid #aaa" >&nbsp;
	    	${dto.name}
	    </td>
	    <td style="border-right:2px solid #aaa" >&nbsp;
	    	${dto.isPackageStr}
	    </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiFlow/1000}" pattern="#,##0.00"/>
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foFlow/1000}" pattern="#,##0.00"/>
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiCost/1000}" pattern="#,##0.00"/>
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foCost/1000}" pattern="#,##0.00"/>
	     </td>
	  </c:forEach>
	</table>
	<div class="e_cur_tit2">
		<i class="fr"><a href="javascript:exportExcel(${page.totalCount});"><input class="button2" type="button" value="下载"></a></i><span></span>
	</div>
	<li:pagination methodName="memberDailyQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
