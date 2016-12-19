<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<link rel="StyleSheet" href="${ctx}/css/tree/xtree.css" type="text/css" />
<link rel="StyleSheet" href="${ctx}/js/prototype/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/jquery/plugins/tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="${ctx}/js/tree/xtree.js"></script>
<script type="text/javascript" src="${ctx}/js/prototype/prototype.js"></script>
<script type="text/javascript" src="${ctx}/js/prototype/validation.js"></script>
</head>
<script type="text/javascript">
</script>
<body>
<div  id="xtreeView" style="position: absolute; width: 300px; top: 0px; left: 0px; height: 100%; padding: 5px; overflow: auto;">
<p>
	<input type="button" value="展开全部节点" onclick="tree.expandChildren();">
	<input type="button" value="关闭全部节点" onclick="tree.collapseChildren();">
</p>
<script type="text/javascript">
	var tree = new WebFXTree('0','菜单树管理','${ctx}');
	tree.setBehavior('classic');
	${xtreeStr}
	document.write(tree);
	tree.expandChildren();
</script>
</div>
<div style="position: absolute; left: 355px; top: 10px;">

<form method="post" name="wfRole" id="wfRole" action="refund.wfRole.do?method=edit">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="6"></td>
	</tr>
</table>
<p align="center">
<table >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		</td>
	</tr>
</table>
</form>
</div>

<script type="text/javascript">
var valid2 = new Validation('menuFormBean', {useTitles:true});
//查询当前选中节点
function show(){
	if(tree.getSelected()){
		if(tree.getSelected().id != '0'){
			 var id = tree.getSelected().id;
			 location.href = "refund.wfRole.do?method=show&id="+id;
		 }
	}
}

</script>