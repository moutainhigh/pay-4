<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">参 数 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="query_ebpp_config.do?method=query" method="post" id="ebppconfig_query_form" name="ebppconfig_query_form">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">KEY：</td>
				<td class="textLeft"><input type="text" name="configKey" id="configKey" style="width: 150px;" value="${command.configKey}"/></td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/add_ebpp_config.do?method=add" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加参数&nbsp;
					</a>
				</td>
			</tr>
		</table>
	</form>
<p></p>
	<table id="configTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead>
	   <tr>
	       <th>KEY</th>
	       <th>VALUE</th>
	       <th>描述</th>
	       <th>操作</th>
	     </tr>
	 </thead>
	  <tbody>
	   <c:forEach items="${ebppConfigList}" var="list">
	      <tr>
	          <td>${list.configKey}</td>
	          <td>${list.configValue}</td>
	          <td>${list.configDesc}</td>                   
	          <td>  <a href="<c:url value='update_ebpp_config.do?method=update&id=${list.id}' />">修改</a> | 
	          <a href="#" onclick="del('${list.id}')">删除</a></td>
	      </tr>
	    </c:forEach>   
	  </tbody>
	</table>
</body>
<script type="text/javascript">

	function query(){
		document.getElementById("ebppconfig_query_form").submit();
	}

	function del(sequenceId){
		if(confirm("确定删除该项吗？"))	{
			window.location.href = "${ctx}/delete_ebpp_config.do?method=deletes&id="+sequenceId;  
		}
	}
</script>
</html>
