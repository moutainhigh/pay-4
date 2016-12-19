<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
﻿<title></title>
</head>

<body>

  <table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
        <thead>
        <tr valign="top">
          <th width="5%" height="25" align="center"><strong>
            <label>
            <input type="checkbox"  name="AllId" onClick="selectAll('rsId');"  id="AllId"  />
            </label>            
          	全选</strong></th>
          
          <th width="10%" align="center"><strong>价格策略代码</strong></th>
          <th width="11%" align="center"><strong>价格策略名称</strong></th>
          <th width="11%" align="center"><strong>价格策略类型</strong></th>

          <th width="9%" align="center"><strong>服务等级</strong></th>
          <th width="7%" align="center"><strong>会员</strong></th>
          <th width="10%" align="center"><strong>生效范围</strong></th>
          <th width="7%" align="center"><strong>生效日期</strong></th>
          <th width="8%" align="center"><strong>失效日期</strong></th>
          
          <th width="7%" align="center"><strong>操作</strong></th>
        </tr>
        </thead>
    <c:forEach items="${resultList}" var="rs" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
          <td height="25" align="center"><input type=checkbox name="rsId" id="rsId" value="${rs.priceStrategyCode}"></td>
          <td align="center">${rs.priceStrategyCode}</td>
          <td align="center">${rs.priceStrategyName}</td>
          <td align="center">${rs.priceStrategyTypeDesc}</td>
          
          
          
          <td align="center">${rs.serviceLevelDesc}</td>
          <td align="center">${rs.memberCode}</td>
          <td align="center">${rs.effectiveOnDesc}</td>
          <td align="center"><fmt:formatDate value="${rs.validDate}" type="date"/></td>
          <td align="center">
              <c:if test='${rs.invalidDate == "1900-01-01"}'>
		      	&nbsp;	    		
			  </c:if> 
	          <c:if test='${rs.invalidDate != "1900-01-01"}'>
		      	<fmt:formatDate value="${rs.invalidDate}" type="date"/>	    		
			  </c:if>
          </td>
          
         
          <td align="center">
          <c:if test='${rs.status != "9"}'>
	      	<a href="#" onclick=$U('${ctx}/if_poss_datamanage/changePricingStrategy.do?paymentservicecode=${rs.priceStrategyCode}')>修改</a>|
          </c:if>
       		<a href="#" onclick=$U('${ctx}/if_poss_datamanage/managePricingStrategyDetail.do?pricingstrategycode=${rs.priceStrategyCode}')>明细</a></td>
        </tr>
		  </c:forEach>
        </table>
 
 

</body>
</html>