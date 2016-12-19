<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
<%--引入用于格式化页面的CSS文件--%>
<script language="javascript">

function closePage(id){
	parent.closePage('${ctx}/operatorSearchList.do?method=gotoDetail&operatorId=' + id);
}
</script>
</head>
<body>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 操 作 员 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户操作员信息</h2>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >会员号：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.memberCode}
			</td>
			<td class="border_top_right4" align="right" >商户号：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.merchantCode}
			</td>
			<td class="border_top_right4" align="right" >商户名称：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.merchantName}
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >用户名：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.loginName}			
			</td>
			<td class="border_top_right4" align="right" >操作员标识：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.identity}
			</td>
			<td class="border_top_right4" align="right" >操作员姓名：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.name}				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >证件号码：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.certCode}			
			</td>
			<td class="border_top_right4" align="right" >部门：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.depart}
			</td>
			<td class="border_top_right4" align="right" >类型：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				<c:choose>
					<c:when test="${operator.name != null && operator.name == 'admin' }">
					管理员
					</c:when>
					<c:otherwise>
					普通操作员
					</c:otherwise>
				</c:choose>		
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >Email：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.email}			
			</td>
			<td class="border_top_right4" align="right" >电话：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.phone}
			</td>
			<td class="border_top_right4" align="right" >手机：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				${operator.mobile}				
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >状态：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				<c:forEach items="${operatorEnums}" var="item">
					<c:if test="${item.code == operator.status}">
						${item.desc}
					</c:if>
				</c:forEach>
			</td>
			<td class="border_top_right4" align="right" >创建时间：</td>
			<td class="border_top_right4" align="left" >&nbsp;
				<fmt:formatDate value="${operator.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="border_top_right4">&nbsp;</td>
			<td class="border_top_right4">&nbsp;</td>
		</tr>
		<tr class="trForContent1">	
		<td  class="border_top_right4" colspan="6" align="center">
			<input type = "button"  onclick="javascript:closePage('${operator.operatorId}');" value="关闭">
		</td>
	</tr>
</table>

</body>