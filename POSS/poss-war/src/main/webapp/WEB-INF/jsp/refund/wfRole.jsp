<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<link rel="stylesheet" href="${ctx}/css/main.css">
<link rel="StyleSheet" href="${ctx}/css/tree/xtree.css" type="text/css" />
<link rel="StyleSheet" href="${ctx}/js/prototype/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/js/jquery/plugins/tablesorter/themes/blue/style.css" type="text/css" media="print, projection, screen" />
<script type="text/javascript" src="${ctx}/js/tree/xtree.js"></script>
<script type="text/javascript" src="${ctx}/js/prototype/prototype.js"></script>
<script type="text/javascript" src="${ctx}/js/prototype/validation.js"></script>
</head>
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
<div style="padding-left:300px;">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td height="6"></td>
		</tr>
	</table>
	<p align="center">
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">工作流节点角色管理</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	</p>
</div>
<form method="post" name="wfRole" id="wfRole" action="refund.wfRole.do?method=insert">
   <input id="nodeKey"  name="nodeKey"   value="${mfb.nodeId}" type="hidden">
<div style="padding-left:300px;">
<table  border="0" cellspacing="0" cellpadding="1" border="1" align="center" style="padding-right:200px;" >
	<tr align="left" class=trForContent1>
		<td width="500" valign="middle" nowrap class="border_top_right4">&nbsp;&nbsp;&nbsp;&nbsp;节点名称：${mfb.nodeName}</td>
	</tr>
	<tr align="left" class=trForContent1>
		<td width="500" valign="middle" nowrap class="border_top_right4">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注:<input id="remark" name="remark" value=""></td>
	</tr>
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="500" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">未分配角色</td>
				<td>&nbsp;</td>
				<td align="left">已分配角色</td>
			</tr>
			<tr align="center">
				<td width="100" align="left">
					<select name="nofuncno" id="nofuncno" class="input1" size="25" multiple="multiple" style="width: 235px;" >
						<c:forEach items="${list}" var="role">
							<option value="${role.roleKy}">${role.roleName}</option>
						</c:forEach>
					</select>
				</td>
				<td width="100">
				<table width="58" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="58" align="center">
							<input name="addB" type="button" class="button1" value="添 加 &gt;" onClick="moveOptions($('nofuncno'),$('funcno'));" >
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="delB" type="button" class="button1" value="&lt; 删 除" onClick="moveOptions($('funcno'),$('nofuncno'));" >
						</td>
					</tr>
					<tr>
						<td align="center">
							<input name="saveSubmit" type="button" class="button1" value=" 保 存 " onClick="checkForm();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
				</table>
				</td>
				<td width="100" align="right">
					<select name="funcno" id="funcno" size="25" class="input1" multiple style="width: 235px;">
						<c:forEach items="${RecordList}" var="record">
							<option value="${record.roleKey}">${record.roleName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
		</td>
	  </tr>
 </table>
</div>
</form>
</body>
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
function moveOptions(objPush,objGet) {   
    for (var i = 0;i < objPush.length;i++) {   
        if (objPush.options[i].selected) {   
              var pushOpt = objPush.options[i];   
              objGet.options.add(new Option(pushOpt.innerText, pushOpt.value));   
              objPush.remove(i);   
              i = i - 1;   
        }   
    }   
}
function select_all(){
    for(var i=0;i<document.wfRole.funcno.length; i++){
       document.wfRole.funcno.options[i].selected=true;
    }
 }

 function checkForm(){
    select_all();
	document.wfRole.submit();
 }
</script>