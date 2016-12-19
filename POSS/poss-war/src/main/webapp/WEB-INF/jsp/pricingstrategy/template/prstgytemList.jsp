
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
		function processSel(id){
			location.href = "${ctx}/if_poss_datamanage/prstgytemSel.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}&id=" + id;
		}
		function processEdit(id){
			location.href = "${ctx}/if_poss_datamanage/prstgytemEdit.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}&id=" + id;
		}

		function processDel(id){
			if(confirm("确 定 删 除 ?")){
				location.href = "${ctx}/if_poss_datamanage/prstgytemDel.do?priceStrategyCode=${priceStrategyCode}&pricestrategydetailcode=${pricestrategydetailcode}&id=" + id;
			}
		}
</script>
</head>

<body>
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
       <thead> <tr>
          <th width="15%" align="center"><strong>策略名称</strong></th>
          <th width="30%" align="center"><strong>价格策略</strong></th>
          <th width="10%" align="center"><strong>操作</strong></th>
        </tr>
        </thead>
    <c:forEach items="${page.result}" var="temInfo" varStatus = "temStatus">  
        <c:choose>
	       <c:when test="${temStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
          <td class="border_top_right4" align="center" nowrap><a href="javascript:processSel('${temInfo.id}')">${temInfo.templateName}</a>&nbsp;</td>		
			<td class="border_top_right4" align="center" nowrap>
			价格策略類型:<c:out value="${pricestrategytypeMap[temInfo.priceStrategyType]}" /> ${pricestrategytypeMap[2]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计算方式:单笔交易|每月累计<br>
			固定費用:${temInfo.fixedCharge}&nbsp;(厘)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;費率:${temInfo.chargeRate}&nbsp;(萬分之一)<br>
			起始金額:${temInfo.rangFrom}&nbsp;(厘)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;終止金額:${temInfo.rangTo}&nbsp;(厘)<br>
			上限:${temInfo.maxCharge}&nbsp;(厘)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;下限:${temInfo.minCharge}&nbsp;(厘)
			</td>
			<td class="border_top_right4" align="center" nowrap>
				<a href="javascript:processEdit('${temInfo.id}')">修改</a>
				<a href="javascript:processDel('${temInfo.id}')">删除</a>
			</td>
        </tr>
		   </c:forEach>
        </table>

<li:pagination methodName="merchantQuery" pageBean="${page}" sytleName="black2"/>
</body>



