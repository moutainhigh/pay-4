<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#memberFlowPackageMonitorTable").tablesorter({
				 headers: {
				 	6: {sorter: false},
				 	7: {sorter: false}
				 }}); 
	
			 $(".tablesorter tbody tr").mouseover(function(){$(this).find("td").css({"background":"#cec"});})
			 .mouseout(function(){$(this).find("td").css({"background":"#fff"});} ) ;         
		});

	</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	
	<div class="tableList">
	<table id="memberFlowPackageMonitorTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	
	   <tr class="tabT">
	    <th class="tabTitle" align="center" scope="col">会员编号</th>
	    <th class="tabTitle" align="center" scope="col">会员名称</th>
	    <th class="tabTitle" align="center" scope="col">预付金额</th>
	    <th class="tabTitle" align="center" scope="col">预付流量</th>
	    <th class="tabTitle" align="center" scope="col">预付日期</th>
	    <th class="tabTitle" align="center" scope="col">包量起始日期</th>
	    <th class="tabTitle" align="center" scope="col">平均费率</th>
	    <th class="tabTitle" align="center" scope="col">预警流量</th>
	    <th class="tabTitle" align="center" scope="col">预警联系人</th>
	    <th class="tabTitle" align="center" scope="col">关停流量</th>
	    <th class="tabTitle" align="center" scope="col">剩余流量</th>
	    <th class="tabTitle" align="center" scope="col">预警状态</th>
	    <th class="tabTitle" align="center" scope="col">网关状态</th>
	    
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	    <td>&nbsp;
	    	${dto.memberCode}
	    </td>
	     <td>&nbsp;
	    	${dto.memberName}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.prePayAmount/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.prePayFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatDate value="${dto.prePayDate}" type="date" pattern="yyyy-MM-dd"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatDate value="${dto.beginDate}" type="date" pattern="yyyy-MM-dd"/>
	    </td>
	    <td>&nbsp;
	    	${dto.averageRate}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.warnFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	${dto.warnLinkman}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.shutDownFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	${dto.residualFlowStr}
	    </td>
	    <td>&nbsp;
	    	${dto.warnStatusStr}
	    </td>
	    <td>&nbsp;
	    	${dto.gatewayStatusStr}
	    </td>
	  	</tr>
	  </c:forEach>
	</table>
		<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
