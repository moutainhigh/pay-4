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
	
	<div class="e_cur_tit2">
		<i class="fr"><a href="#" onclick="exportExcel(${page.totalCount});">下载报表</a></i><span></span>
	</div>
	<div class="tableList">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	  	<th>
			<input type="checkbox" value="0" name="checkAll" id="checkAll" onclick="selectAll();" />全选
		</th>	  
	    <th class="tabTitle" align="center">日期</th>
	  	<th class="tabTitle" align="center">会员类型</th>
	  	<th class="tabTitle" align="center">会员编号</th>
	  	<th class="tabTitle" align="center">会员名称</th>
		<th class="tabTitle" align="center">所属分子公司</th>
	    <th class="tabTitle" align="center">会员等级</th>
	    <th class="tabTitle" align="center">实际入款量（元）</th>
	    <th class="tabTitle" align="center">出款量（元）</th>
	    <th class="tabTitle" align="center">资金沉淀量（元）</th>
	  </tr>
	</thead>
	
	  <c:forEach items="${page.result}" var="dto">
	  	<tr>  
		  	<td>
				<input type="checkbox" name="wkKey" value="${dto.cpAmount}" id="id" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
			</td>     
		    <td>&nbsp;${dto.transDate}</td>
		    <td>&nbsp;
		      <c:choose>
		    	 <c:when test="${dto.memberType == '1'}">    
		    	 	个人
		    	 </c:when>
		    	 <c:when test="${dto.memberType == '2'}"> 
		    		 企业
		    	 </c:when>
		     </c:choose>
		    </td>
		    <td>&nbsp;${dto.memberNo}</td>
		    <td>&nbsp;${dto.memberName}</td>
		    <c:choose>
		       <c:when test="${dto.innerName == null}">    
				    <td align="center">&nbsp;/</td>		    	 	
		       </c:when>
		       <c:otherwise>
				    <td>&nbsp;
				    	${dto.innerName} 
				     </td>
		       </c:otherwise>
		    </c:choose> 
		    <td>&nbsp;${dto.serviceLevelName} </td>		    
		    <td align="right">&nbsp;
		   	 	<fmt:formatNumber value="${dto.realFiAmount / 1000}" pattern="#,##0.00"/>
		    </td>
		    <td align="right">&nbsp;
		   	 	<fmt:formatNumber value="${dto.realFoAmount / 1000}" pattern="#,##0.00"/>
		    </td>
		    <td align="right">&nbsp;
		   	 	<fmt:formatNumber value="${dto.cpAmount / 1000}" pattern="#,##0.00"/>
		    </td>		    
	  	</tr>
	  </c:forEach>
	 
	</table>
	<div id="showSum">选中金额共 0.00 元，笔数共 0 笔</div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
