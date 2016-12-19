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
function operate(id){
	var url="enterpriseSearchDetail.do?method=enterpriseSearchDetail&id="+id;
	parent.addMenu("企业会员密码重置详情",url);
}
function resetLogin(id){
	var url="enterprisePasswordResetDispose.do?method=disposeView&id="+id+"&typeName="+"typeLogin";
	parent.addMenu("企业会员登录密码重置审核",url);
}
function resetPay(id){
	var url="enterprisePasswordResetDispose.do?method=disposeView&id="+id+"&typeName="+"typePay";
	parent.addMenu("企业会员支付密码重置审核",url);
}
function histroySearch(loginName){
	var url="enterprisePasswordResetHistorySearch.do?method=enterpriseHistorySearch&loginName="+loginName;
	parent.addMenu("企业密码重置历史查询",url);
}

</script>
</head>

<body>
<form>
<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<th class=""  >创建时间</th>
			<th class=""  >申请单编号</th>
			<th class=""  >会员中文名</th>
			<th class=""  >会员登录名</th>
			<th class=""  >状态</th>
			<th class=""  >重置历史查询</th>
			<th class=""  >操作</th>
	
		</tr>
	</thead>
	
	<c:forEach items="${page.result}" var="passwordreset" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent1">
       </c:otherwise>
	</c:choose>			
		<td class="" align="center" >${passwordreset.strGmtCreate}&nbsp;</td>
		<td class="" align="center" >${passwordreset.formNumber}&nbsp;</td>
		<td class="" align="center" >${passwordreset.memberName}&nbsp;</td>
		<td class="" align="center" >${passwordreset.loginName}&nbsp;</td>
		<td class="" align="center" >${passwordreset.statusName}&nbsp;</td>
		<td class="" align="center" >
			<a href="javascript:void(0);" onclick="histroySearch('${passwordreset.loginName}');">&nbsp;查看&nbsp;</a>
		</td>
		<td class="" align="center" >
		<c:if test="${passwordreset.status==1}">
			<a href="javascript:void(0);" onclick="operate('${passwordreset.id}');">&nbsp;初审&nbsp;</a>
		</c:if>	
			<c:if test="${passwordreset.status==2}">
				<c:if test="${passwordreset.passwordType==1}">
					<a href="javascript:void(0);" onclick="resetLogin('${passwordreset.id}');">&nbsp;重置登录密码</a>&nbsp;
				</c:if>
				<c:if test="${passwordreset.passwordType==2}">
					<a href="javascript:void(0);" onclick="resetPay('${passwordreset.id}');">&nbsp;重置支付密码</a>&nbsp;
				</c:if>
			</c:if>
		</td>
		</tr>
	</c:forEach>
</table>
</form>
<li:pagination methodName="enterprisePasswordResetQuery" pageBean="${page}" sytleName="black2"/>
</body>

