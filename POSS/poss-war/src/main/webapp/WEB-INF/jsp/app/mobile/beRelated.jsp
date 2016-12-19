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
					<font class="titletext">关 联 产 品 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="query_beRelated.do?method=queryProduct" method="post" id="beRelated_index_form" name="beRelated_index_form">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">关联商家：</td>
				<td class="textLeft"><input type="text" name="mechantId" id="mechantId" style="width: 150px;" value="${command.mechantId}"/></td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/add_beRelated.do?method=addProduct" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;关联产品&nbsp;
					</a>
				</td>
			</tr>
		</table>
	</form>
 	<p></p>
    <table id="chargeProductRelationTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
      <thead>
       <tr>
           <th>商家账户</th>
           <th>充值产品名称</th>
           <th>充值产品折扣</th>
           <th>充值产品金额(单位：元)</th>
           <th>充值通信商家</th>
           <th>最大等待时间(单位：分钟)</th>
           <th>删除</th>
         </tr>
     </thead>
      <tbody>
       <c:forEach items="${chargeProductRelationList}" var="list">
          <tr>
          	   <td>${list.loginName}</td>
              <td>${list.productName}</td>
              <td>${list.discount}</td>
              <td>${list.chargeAmount}</td>
              <td><c:if test="${list.bossType == 0}">移动</c:if>
         	<c:if test="${list.bossType == 1}">联通</c:if>
         	<c:if test="${list.bossType == 2}">电信</c:if></td>
              <td>${list.maxWaitingTime}</td>                    
              <td><a href="#" onclick="del('${list.id}')">删除</a></td>
          </tr>
        </c:forEach>   
      </tbody>
    </table>
</body>
<script type="text/javascript">
if('${errorMsg}' != "")
	alert('${errorMsg}');

	function query(){
		document.getElementById("beRelated_index_form").submit();
	}

	function del(id){
		if(confirm("确定删除该项吗？"))	{
			window.location.href = "${ctx}/delete_beRelated.do?method=deleteBeRelated&id="+id;  
		}
	}
</script>
</html>
