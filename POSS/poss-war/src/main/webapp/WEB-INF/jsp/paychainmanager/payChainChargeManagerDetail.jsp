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

function payChainManagerDetail(pageNo,totalCount,pageSize) {
	var par = $("#payChainManagerForm").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	/*$.ajax({
		type: "POST",
		url: "${ctx}/payChainChargeManagerQuery.do?method=queryDetail",
		data: par,
		success: function(result) {
			$(document).html(result);
		}
	});*/
	window.location.href = "${ctx}/payChainChargeManagerQuery.do?method=queryDetail&"+par;
} 


function payChainDownExcelDetail(){
	var par = $("#payChainManagerForm").serialize();
	var url = "${ctx}/payChainChargeManagerQuery.do?method=downExcelPayChainDetail&"+par;
	window.open(url);
} 

</script>
</head>

<body>
<form id="payChainManagerForm" name="payChainManagerForm">
<input type="hidden" name="payChainNumber" id="payChainNumber" value="${chainDto.payChainNumber}" />


<table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="0" style="width: 20%"></td>
		<td height="1" style="width: 60%" bgcolor="#000000"></td>
		<td height="0" style="width: 20%"></td>
	</tr>
	<tr>
		<td height="0" style="width: 20%"></td>
		<td height="18" style="width: 60%">
		<div align="center"><font class="titletext">支付链收款链接信息</font></div>
		</td>
		<td height="18" style="width: 20%">
		<div align="right">					
		<a href="javascript:payChainDownExcelDetail();">下载全部信息(Excel)</a>&nbsp;
		</div>		
		</td>
	</tr>
	<tr>
		<td height="0" style="width: 20%"></td>
		<td height="1" style="width: 60%" bgcolor="#000000"></td>
		<td height="0" style="width: 20%"></td>
	</tr>

</table>
	<br/>
	<br/>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">

	<tr class="trForContent1">
		<td class="border_top_right4"  align="center" nowrap height="20">支付链收款链接地址</td>
		<td colspan="7" class="border_right4"  > ${chainDto.paychainUrl} </td>
	</tr>
	
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">有效时长</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">链接生成时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">过期时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接编号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款项目</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款方名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款方地址</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">联系电话</font> </a></td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="center" nowrap>${chainDto.effectiveDateName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap width="130">${chainDto.strCreateDate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${chainDto.strOverdueDate}&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap>${chainDto.payChainNumber}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${chainDto.receiptDescription}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${chainDto.zhName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${chainDto.address}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${chainDto.tel}&nbsp;</td>
	</tr>
	
</table>

<table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21"  >
		<div align="center" style="margin-top: 20px"><font class="titletext">付款方信息</font></div>
</table>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">付款时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">网关支付流水号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">付款方名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">支付金额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">备注</font> </a></td>
	</tr>
	<c:forEach items="${externalList}" var="payChainManager" varStatus = "payChainManagerStatus">
	<c:choose>
       <c:when test="${payChainManagerStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
		<td class="border_top_right4" align="center" nowrap>${payChainManager.updateDate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.gatewayNo}&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap>${payChainManager.payerName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.amount}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${payChainManager.remark}&nbsp;</td>
		</tr>
	</c:forEach>
	
</table>
	
</form>
<li:pagination methodName="payChainManagerDetail" pageBean="${page}" sytleName="black2"/>

</body>

