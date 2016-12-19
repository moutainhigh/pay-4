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

		function exportFile(totalCount) {
			if(totalCount <= 0){
				alert("无结果集,不能下载！");
			}
			else{
				if(!validateQuery()){
					return false;
				}
				document.getElementById("form1").method="POST";
				document.getElementById("form1").action="${ctx}/ipBlackListDownload.do?method=download";
				document.getElementById("form1").submit();
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
	<a href="javascript:onClick=exportFile(${page.totalCount});">下载查询结果</a>
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	    <th class="tabTitle" scope="col">创建时间</th>
	    <th class="tabTitle" scope="col">IP地址</th>
	    <th class="tabTitle" scope="col">状态</th>
	    <th class="tabTitle" scope="col">更新时间</th>
	    <th class="tabTitle" scope="col">禁用次数</th>
	    <th class="tabTitle" scope="col">激活次数</th>
	    <th class="tabTitle" scope="col">操作</th>
	  </tr>
	</thead> 
	  	
	  <c:forEach items="${page.result}" var="dto">
	  	<tr>  
		    <td>&nbsp;<fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td>&nbsp;${dto.ipAddress}</td>
			<td>&nbsp;
			   <c:if test="${dto.status eq 1}">激活</c:if>
			   <c:if test="${dto.status eq 2}">禁用</c:if>
			</td>
		    <td>&nbsp;<fmt:formatDate value="${dto.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		    <td>&nbsp;${dto.disableCount}</td>
		    <td>&nbsp;${dto.enableCount}</td>
		    <td>&nbsp;
		    	<c:choose>
					<c:when test="${dto.status eq '1'}">
						<a href="javascript:ipBlackDisable('${dto.ipBlacklistNo}')">禁用 </a>
					</c:when>
					<c:otherwise>
						<a href="javascript:ipBlackEnable('${dto.ipBlacklistNo}')">激活 </a>
					</c:otherwise>
				</c:choose>
				<a  href="${ctx}/ipBlackList.do?method=editIpBlack&ipBlackNo=${dto.ipBlacklistNo}">修改</a>
		    </td>
	  	</tr>
	  </c:forEach>
	</table>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</c:if>
</body>
  <script type="text/javascript">
	   function ipBlackDisable(ipBlackNo) {
			if (confirm("确定禁用IP黑名单？")) {
				location.href = "ipBlackList.do?method=updateStatus&ipBlackNo="+ipBlackNo+"&status="+2;
			}
		}
	   function ipBlackEnable(ipBlackNo) {
			if (confirm("确定激活IP黑名单？")) {
				location.href = "ipBlackList.do?method=updateStatus&ipBlackNo="+ipBlackNo+"&status="+1;
			}
		}

	   function updateIpBlack(no){
			var url="ipBlackList.do?method=editIpBlack&ipBlackNo="+no;
			parent.addMenu("修改IP黑名单",url);
		}
  </script>
</html>
