<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>

<head>
﻿<title></title>
</head>

<script>

//用来判断是否可以新增  大于0 认为不可 增加 
detailSize = '${fn:length(resultList)}'  ;
//alert(detailSize);
   
</script>
<body>

<!--正文内容框开始束 -->
 <table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
        <thead><tr >
          <th width="5%" height="25" align="center" ><strong>
            <label>
            <input type="checkbox"  name="AllId" onClick="selectAll('rsId');"  id="AllId" class="aaa" />
            </label>
          全选</strong></th>
          <th width="10%" align="center" ><strong>固定费用</strong></th>
          <th width="7%" align="center" ><strong>起始金额</strong></th>
          <th width="8%" align="center" ><strong>终止金额</strong></th>
          <th width="7%" align="center" ><strong>费率</strong></th>
          <th width="8%" align="center" ><strong>上限</strong></th>
          <th width="6%" align="center" ><strong>下限</strong></th>
          <th width="8%" align="center" ><strong>对方会员号</strong></th>
          <th width="7%" align="center" ><strong>操作</strong></th>
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

          <td height="25" align="center">
          <input type=checkbox name="rsId" value="${rs.priceStrategyDetailCode}"></td>
          <td align="center">${rs.fixedCharge}</td>
          <td align="center">${rs.rangFrom}</td>
          <td align="center">${rs.rangTo}</td>
          <td align="center">${rs.chargeRate}</td>
          <td align="center">${rs.maxCharge}</td>
          <td align="center">${rs.minCharge}</td>
          <td align="center">${rs.reservedCode}</td>
		  <td align="center">
       		<a href="#" onclick="$U('${ctx}/if_poss_datamanage/changePricingStrategyDetail.do?pricestrategydetailcode=${rs.priceStrategyDetailCode}')">修改</a></td>
        </tr>
		   </c:forEach>
        </table>
 <!--正文内容框结束 -->

</body>
</html>