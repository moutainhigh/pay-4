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
function deleteBusinessType(businessTypeId){
	var url="businessTypeDelete.do?method=deleteBusinessType&businessTypeId="+businessTypeId;
	location.href = url;
}
function updateBusinessType(businessTypeId){
	var url="businessTypeUpdate.do?method=updateView&businessTypeId="+businessTypeId;
	parent.addMenu("修改业务类型",url);
}
</script>
</head>

<body>
<form>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<!-- 
		<c:if test="${show=='YES'}">
			<tr > 
				<td height="2" ><font color="red" >操作成功</font></td>
			</tr>
		</c:if>
	 -->
	 <thead>
	<tr class="" align="center" valign="middle">
		<th class=""  >业务类型ID</th>
		<th class=""  >业务描述</th>
		<th class=""  >有效性标志</th>
		<th class=""  >业务类型</th>
		<th class=""  >备注</th>
		<th class=""  >操作</th>

	</tr>
	</thead>
	<c:forEach items="${page.result}" var="business" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
		<td class="" align="center" >${business.businessTypeId}&nbsp;</td>
		<td class="" align="center" >${business.businessName}&nbsp;</td>
		<td class="" align="center" >
			<c:if test="${business.status==1}">有效</c:if>
			<c:if test="${business.status==0}">无效</c:if>
		&nbsp;
		</td>
		<td class="" align="center" >
			<c:if test="${business.businessType==1}">FO</c:if>
			<c:if test="${business.businessType==2}">FI</c:if>
			<c:if test="${business.businessType==3}">MA</c:if>
			<c:if test="${business.businessType==4}">APP</c:if>
		&nbsp;
		</td>
		<td class="" align="center" >${business.remark}&nbsp;</td>
		
		<td class="" align="center" >
			<a href="javascript:void(0);" onclick="updateBusinessType('${business.businessTypeId}');">&nbsp;修改&nbsp;</a>
			<a href="javascript:void(0);" onclick="deleteBusinessType('${business.businessTypeId}');">&nbsp;删除&nbsp;</a>
		</td>
		</tr>
	</c:forEach>
</table>
</form>
<li:pagination methodName="businessTypeSearch" pageBean="${page}" sytleName="black2"/>
</body>

