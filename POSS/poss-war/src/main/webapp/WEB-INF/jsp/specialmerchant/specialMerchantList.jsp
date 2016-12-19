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
function spEdit(spMerchantId){
	var url = "${ctx}/specialMerchantEdit.do?sp_merchant_id=" + spMerchantId;
	parent.addMenu("修改特约商户",url);
	//location.href = "${ctx}/specialMerchantEdit.do?sp_merchant_id=" + spMerchantId;
}
function spDelete(spMerchantId){
	if(confirm("确定要删除吗?")){
		location.href = "${ctx}/specialMerchantDel.do?sp_merchant_id=" + spMerchantId;
	}
}	

function spAddCard(spMerchantId){
	var url = "${ctx}/spCardInfoInit.do?spMerchantId=" + spMerchantId;
	parent.addMenu("特约商户卡种",url);
	//location.href = "${ctx}/spCardInfoInit.do?spMerchantId=" + spMerchantId;
}
function spDetail(spMerchantId){
	var url = "${ctx}/specialMerchantDetail.do?sp_merchant_id=" + spMerchantId;
	parent.addMenu("特约商户详情",url);
	//location.href = "${ctx}/specialMerchantDetail.do?sp_merchant_id=" + spMerchantId;
}
</script>
</head>

<body>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">特约商户名称</font> </a></td>
		<!-- <td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">省份城市</font> </a></td> -->
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">经营范围</font> </a></td>
		<!-- <td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">卡种类</font> </a></td> -->
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">联系电话</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">商户地址</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">操作</font> </a></td>
	</tr>
	<c:forEach items="${page.result}" var="smInfo" varStatus = "smInfoStatus">
	<c:choose>
       <c:when test="${smInfoStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			<td class="border_top_right4" align="center" nowrap>${smInfo.sp_merchant_name}</a>&nbsp;</td>		
			<%-- <td class="border_top_right4" align="center" nowrap>${smInfo.province_code}&nbsp;</td> --%>
			<td class="border_top_right4" align="center" nowrap>${smInfo.enum_name}&nbsp;</td>
			<!-- <td class="border_top_right4" align="center" nowrap>${smInfo.card_type_code}&nbsp;</td> -->
			<td class="border_top_right4" align="center" nowrap>${smInfo.tel}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${smInfo.addr}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>
				<a href="javascript:spDetail(${smInfo.sp_merchant_id});">详情</a>
				&nbsp;
				<a href="javascript:spEdit(${smInfo.sp_merchant_id});">编辑</a>
				&nbsp;
				<a href="javascript:spDelete(${smInfo.sp_merchant_id});">删除</a>
				&nbsp;
				<a href="javascript:spAddCard(${smInfo.sp_merchant_id});">添加支持卡种</a>
				
			</td>
		</tr>
	</c:forEach>
</table>

<li:pagination methodName="specialMerchantQuery" pageBean="${page}" sytleName="black2"/>


</body>

