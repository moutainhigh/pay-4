<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>

﻿<title>支付服务管理</title>

<body>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
       <thead> <tr >          
          <th  align="center" ><strong>支付服务代码</strong></th>
          <th  align="center" ><strong>支付服务名称</strong></th>
          <th  align="center" ><strong>交易支付服务名称</strong></th>
          <th  align="center" ><strong>操作</strong></th>
        </tr>
        </thead>
        
        <c:forEach items="${paymentServiceList}" var="paymentService" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
          
          <td align="center">${paymentService.paymentservicecode}</td>
          <td align="center">${paymentService.paymentservicename}</td>
          <td align="center">${paymentService.paymentServiceTypedesc}</td>
          <td align="center">
       		<c:if test="${paymentService.paymentServiceType == 2}">
				<a href="#" onclick=$U('${ctx}/if_poss_datamanage/managePricingStrategy.do?paymentservicecode=${paymentService.paymentservicecode}')>设置价格策略</a>
			</c:if>       		
        </tr>
          </c:forEach>
        </table>

 



</body>
</html>