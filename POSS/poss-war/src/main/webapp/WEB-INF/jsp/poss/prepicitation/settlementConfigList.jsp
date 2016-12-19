<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#policyTable").tablesorter(); 
			 $(".tablesorter tbody tr").mouseover(function(){$(this).find("td").css({"background":"#cec"});})
			 .mouseout(function(){$(this).find("td").css({"background":"#fff"});} ) ;         
		});
		
		function cfm(id){
			if(confirm("确定要删除当前费率配置么？")){
				window.location.href="${ctx}/prepicitation/settlementConfig.do?method=delete&id="+id;
			}	
		}		
	</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	<div class="tableList">
		<table id="policyTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
		<thead>
		   <tr class="tabT">
		    <th class="tabTitle" align="center" scope="col">序号</th>
		    <th class="tabTitle" align="center" scope="col">网关类型</th>
		    <th class="tabTitle" align="center" scope="col">结算日</th>
		    <th class="tabTitle" align="center" scope="col">启用日期</th>
		    <th class="tabTitle" align="center" scope="col">停用日期</th>
		    <th class="tabTitle" align="center" scope="col">状态</th>
		    <th class="tabTitle" align="center" scope="col">操作</th>
		  </tr>
		</thead> 
		<tbody>
		  <c:forEach items="${page.result}" var="dto" varStatus="status">
			  <tr>
			    <td>&nbsp;
			    	${ status.index + 1}
			    </td>
			    <td>&nbsp;
			    	<c:if test="${dto.configType eq '1'}">T+0</c:if>
					<c:if test="${dto.configType eq '2'}">T+0顺延</c:if>
					<c:if test="${dto.configType eq '3'}">T+1</c:if>
					<c:if test="${dto.configType eq '4'}">T+1顺延</c:if>
					<c:if test="${dto.configType eq '5'}">T+2</c:if>
					<c:if test="${dto.configType eq '6'}">T+2顺延</c:if>
			    </td>
			    <td>&nbsp;
			    	${dto.settlementDate}
			    </td>
			    <td>
			    	<fmt:formatDate value="${dto.startDate}" type="date" pattern="yyyy-MM-dd"/>
			    </td>
			    <td>
			    	<fmt:formatDate value="${dto.endDate}" type="date" pattern="yyyy-MM-dd"/>
			    </td>	
			    <td>&nbsp;
			   		${dto.status == 1 ? "使用中" : ""}
					${dto.status == 2 ? "已停用" : ""}
					${dto.status == 3 ? "未启用" : ""}
					${dto.status == 4 ? "失效" : ""}
			    </td>				    			    
			    <td>&nbsp;
			    	<a href='${ctx}/prepicitation/settlementConfig.do?method=query&id=${dto.id}'>查看</a>
					<c:if test='${dto.status eq 2 || dto.status eq 3}'> 
						&nbsp;&nbsp;
				    	<a href="javascript:cfm('${dto.id}')">删除</a>
					</c:if>
					<c:if test='${dto.status eq 1 || dto.status eq 3}'> 
						&nbsp;&nbsp;
				    	<a href='${ctx}/prepicitation/settlementConfig.do?method=toUpdate&id=${dto.id}'>修改</a>
					</c:if>					
					
			        <!-- 
				        <input type="button" onclick="javascript:window.location.href='${ctx}/report/costRateSetting.do?method=query&id=${dto.id}'" name="Submit2" value="查看">${dto.status == 3 ? "" : "&nbsp;&nbsp;&nbsp;"}
				        <input type="button" onclick="javascript:window.location.href='${ctx}/report/costRateSetting.do?method=delete&id=${dto.id}'" name="Submit2" value="删除" ${dto.status == 3 ? "style='display:none;'" : ""}>${dto.status >= 2 ? "" : "&nbsp;&nbsp;&nbsp;"}
				        <input type="button" onclick="javascript:window.location.href='${ctx}/report/costRateSetting.do?method=toUpdate&id=${dto.id}'" name="Submit2" value="修改" ${dto.status >= 2 ? "style='display:none;'" : ""}>
			         -->			    
			    </td>
			  </tr>
		   </c:forEach>
		 </tbody>
		</table>
	</div>
	</c:if>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
</body>
