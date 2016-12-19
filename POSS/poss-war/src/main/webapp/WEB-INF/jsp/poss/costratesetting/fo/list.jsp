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
				window.location.href="${ctx}/report/costRateSetting.do?method=delete&id="+id;
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
		    <th class="tabTitle" align="center" scope="col">银行渠道</th>
		    <th class="tabTitle" align="center" scope="col">对公/对私</th>
		    <th class="tabTitle" align="center" scope="col">同行/跨行</th>
		    <th class="tabTitle" align="center" scope="col">同城/异地</th>
		    <th class="tabTitle" align="center" scope="col">缓急</th>
		    <th class="tabTitle" align="center" scope="col">出款类型</th>		    
		    <th class="tabTitle" align="center" scope="col">费率类型</th>
		    <th class="tabTitle" align="center" scope="col">计算方式</th>
		    <th class="tabTitle" align="center" scope="col">启用日期</th>
		    <th class="tabTitle" align="center" scope="col">停用日期</th>
		    <th class="tabTitle" align="center" scope="col">状态</th>
		    <th class="tabTitle" align="center" scope="col">操作</th>
		  </tr>
		</thead> 
		<tbody>
		  <c:forEach items="${page.result}" var="dto">
			  <tr>
			    <td>&nbsp;
			    	${dto.id}
			    </td>			  
			    <td>&nbsp;
			    	<c:forEach items="${pciList}" var="pci">
			    		${pci.orgCode == dto.channelNo ? pci.desciption : ""}
			    	</c:forEach>
			    </td>
			    <td>&nbsp;
			    	${dto.isPrivate == 0 ? "对私" : ""}
			   		${dto.isPrivate == 1 ? "对公" : ""}
			    </td>
			    <td>&nbsp;
			    	${dto.inOut == 1 ? "同行" : ""}
			   		${dto.inOut == 2 ? "跨行" : ""}
			    </td>
			    <td>&nbsp;
			    	${dto.address == 1 ? "同城" : ""}
			   		${dto.address == 2 ? "异地" : ""}
			    </td>
			    <td>&nbsp;
			    	${dto.isUrgency == 0 ? "普通" : ""}
			   		${dto.isUrgency == 1 ? "加急" : ""}
			    </td>
			    <td>&nbsp;
			    	${dto.outType == 1 ? "代发" : ""}
			   		${dto.outType == 2 ? "转账" : ""}
			    </td>
			    <td>&nbsp;
			    	${dto.policyType == 1 ? "固定费用" : ""}
					${dto.policyType == 2 ? "费率" : ""}
					${dto.policyType == 3 ? "费率及下限" : ""}
					${dto.policyType == 4 ? "费率及上限" : ""}
					${dto.policyType == 5 ? "费率及上下限" : ""}
					${dto.policyType == 6 ? "固定费用_费率" : ""}
					${dto.policyType == 7 ? "固定费用_费率及下限" : ""}
					${dto.policyType == 8 ? "固定费用_费率及上限" : ""}
					${dto.policyType == 9 ? "固定费用_费率及上下限" : ""}	
			    </td>
			    <td>&nbsp;
			   		${dto.countType == 1 ? "单笔交易" : ""}
					${dto.countType == 2 ? "按月累计流量" : ""}
					${dto.countType == 3 ? "按年累计流量" : ""}
					${dto.countType == 4 ? "按季度累计流量" : ""}			    	
			    </td>			    
			    <td>
			    	<fmt:formatDate value="${dto.startDate}" type="date" pattern="yyyy-MM-dd"/>
			    </td>
			    <td>
			    	<fmt:formatDate value="${dto.endDate}" type="date" pattern="yyyy-MM-dd"/>
			    </td>	
			    <td>&nbsp;
					${dto.status == 0 ? "未启用" : ""}
			   		${dto.status == 1 ? "使用中" : ""}
					${dto.status == 2 ? "已停用" : ""}
					${dto.status == 3 ? "失效" : ""}
			    </td>				    			    
			    <td>&nbsp;
			    	<a href='${ctx}/report/costRateSetting.do?method=query&id=${dto.id}'">查看</a>
					<c:if test='${dto.status < 3}'> 
						&nbsp;&nbsp;
				    	<a href="javascript:cfm('${dto.id}')">删除</a>
					</c:if>
					<c:if test='${dto.status < 2}'> 
						&nbsp;&nbsp;
				    	<a href='${ctx}/report/costRateSetting.do?method=toUpdate&id=${dto.id}'">修改</a>
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
