<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#subMemberTable").tablesorter({
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
	<table id="subMemberTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	
	   <tr class="tabT">
	    <th class="tabTitle" align="center" scope="col">会员编号</th>
	    <th class="tabTitle" align="center" scope="col">商户名称</th>
	    <th class="tabTitle" align="center" scope="col">操作员</th>
	    <th class="tabTitle" align="center" scope="col">创建时间</th>
	    <th class="tabTitle" align="center" scope="col">操作</th>
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
	    	${dto.operator}
	    </td>
	     <td>&nbsp;
	    	<fmt:formatDate value="${dto.createTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
	    </td>
	    <td>&nbsp;
		    <a href="${ctx}/report/subMember.do?method=toUpdate&id=${dto.sequenceId}">修改</a>
			<c:if test='${parentId != dto.memberCode}'> 
		    	<a href="${ctx}/report/subMember.do?method=toDelete&id=${dto.sequenceId}">删除</a>
			</c:if>
	    </td>
	  	</tr>
	  </c:forEach>
	</table>
		<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
