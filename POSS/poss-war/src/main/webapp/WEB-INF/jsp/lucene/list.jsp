<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="li" uri="poss"%>
<c:if test="${!empty result}">
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
<tr class="trForContent1"  align="center">
	<td class="border_top_right4" align="center">联行号</td>
	<td class="border_top_right4" align="center">银行名称</td>
	<td class="border_top_right4" align="center">地区</td>
	<td class="border_top_right4" align="center">城市</td>
	<td class="border_top_right4" align="center">开户行地址</td>
	<td class="border_top_right4" align="center">状态</td>
	<td class="border_top_right4" align="center">类别</td>
	<td class="border_top_right4" align="center">操作</td>
</tr>
<c:forEach items="${result}" var="bank">
<tr class="trForContent1" >
	<td class="border_top_right4" align="center"><font size="4">${bank.bankNumber}</font></td>
	<td class="border_top_right4" align="center">${bank.bankName}</td>
	<td class="border_top_right4" align="center">${bank.province}</td>
	<td class="border_top_right4" align="center">${bank.city}</td>
	<td class="border_top_right4" align="center">${bank.bankKaihu}</td>
	<td class="border_top_right4" align="center"><c:if test="${bank.flag == 0}">不可用</c:if><c:if test="${bank.flag == 1}">可用</c:if></td>
	<td class="border_top_right4" align="center"><c:if test="${bank.type == 1}">普通</c:if><c:if test="${bank.type == 2}">专用</c:if></td>
	<td class="border_top_right4" align="center"><a href="javascript:toUpdate('${bank.id}')">修改</a>&nbsp;&nbsp;<a href="javascript:del('${bank.id}')">删除</a></td>
</tr>
</c:forEach>
<tr>
</tr>
</table>
<li:pagination methodName="query1" pageBean="${page}" sytleName="black2"/>
</c:if>
