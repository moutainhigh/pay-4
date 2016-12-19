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
					<font class="titletext">充 值 产 品 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="query_mobileProduct.do?method=query" method="post" id="mobileProduct_query_form" name="mobileProduct_query_form">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">产品名称：</td>
				<td class="textLeft"><input type="text" name="productName" id="productName" style="width: 150px;" value="${command.productName}"/></td>
				<td class="textRight">通信商家：</td>
				<td class="textLeft"><select id="bossType" name="bossType" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.bossType == -1}">selected="selected"</c:if>>请选择</option>
	             	<option value="0" <c:if test="${command.bossType == 0}">selected="selected"</c:if>>移动</option>
	             	<option value="1" <c:if test="${command.bossType == 1}">selected="selected"</c:if>>联通</option>
	             	<option value="2" <c:if test="${command.bossType == 2}">selected="selected"</c:if>>电信</option>
	             </select></td>
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/add_mobileProduct.do?method=add" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加充值产品&nbsp;
					</a>
				</td>
			</tr>
		</table>
	</form>
 	<p></p>
    <table id="mobileProductTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
      <thead>
       <tr>
           <th>产品名称</th>
           <th>产品折扣</th>
           <th>产品金额(单位：元)</th>
           <th>通信商家</th>
           <th>最大等待时间(单位：分钟)</th>
           <th>修改 | 删除</th>
         </tr>
     </thead>
      <tbody>
       <c:forEach items="${mobileProductDtoList}" var="list">
          <tr>
              <td>${list.productName}</td>
              <td>${list.discount}</td>
              <td>${list.chargeAmount}</td>
              <td><c:if test="${list.bossType == 0}">移动</c:if>
         	<c:if test="${list.bossType == 1}">联通</c:if>
         	<c:if test="${list.bossType == 2}">电信</c:if></td>
              <td>${list.maxWaitingTime}</td>
              <td>  <a href="<c:url value='update_mobileProduct.do?method=update&productId=${list.productId}' />">修改</a> | 
              <a href="#" onclick="del('${list.productId}')">删除</a></td>
          </tr>
        </c:forEach>   
      </tbody>
    </table>
</body>
<script type="text/javascript">

	function query(){
		document.getElementById("mobileProduct_query_form").submit();
	}

	function del(productId){
		if(confirm("确定删除该项吗？"))	{
			window.location.href = "${ctx}/delete_mobileProduct.do?method=delete&productId="+productId;  
		}
	}
</script>
</html>
