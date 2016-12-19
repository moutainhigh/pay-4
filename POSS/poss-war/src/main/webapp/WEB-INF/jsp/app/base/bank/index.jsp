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
					<font class="titletext">银 行 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="query_ebill_bankList.do?method=query" method="post" id="ebillbanklist_query_form" name="ebillbanklist_query_form">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">银行名称：</td>
				<td class="textLeft"><input type="text" name="bankName" id="bankName" style="width: 150px;" value="${command.bankName}"/></td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/add_ebill_bankList.do?method=add" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加银行&nbsp;
					</a>
				</td>
			</tr>
		</table>
	</form>
	<p></p>
	<table id="bankTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead>
	   <tr>
	       <th>银行代码</th>
	       <th>银行名称</th>
	       <th>银行机构代码</th>
	       <th>是否有效</th>
	       <th>银行卡类型</th>
	       <th>排序</th>
	       <th>操作</th>
	     </tr>
	 </thead>
	  <tbody>
	   <c:forEach items="${ebillBankLists}" var="list">
	      <tr>
	          <td>${list.bankCode}</td>
	          <td>${list.bankName}</td>
	          <td>${list.orgCode}</td>
	          <td><c:if test="${list.flag == 0}">无效</c:if><c:if test="${list.flag == 1}">有效</c:if></td>
	          <td><c:if test="${list.bankType == 0}">信用卡</c:if><c:if test="${list.bankType == 1}">储蓄卡</c:if></td>
	          <td>${list.indexNum}</td>                    
	          <td>  <a href="<c:url value='update_ebill_bankList.do?method=update&sequenceId=${list.sequenceId}' />">修改</a> | 
	          <a href="#" onclick="del('${list.sequenceId}')">删除</a></td>
	      </tr>
	    </c:forEach>   
	  </tbody>
	</table>
</body>
<script type="text/javascript">
	function query(){
		document.getElementById("ebillbanklist_query_form").submit();
	}

	function del(sequenceId){
		if(confirm("确定删除该项吗？"))	{
			window.location.href = "${ctx}/delete_ebill_bankList.do?method=delete&sequenceId="+sequenceId;  
		}
	}
</script>
</html>
