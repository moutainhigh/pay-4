<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" 	uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="myIds" value="|"/>


<html>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">
<link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" />
<link rel="stylesheet" href="${ctx}/css/ztree/demoStyle/demo.css">
<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/plugins/ztree/jquery.ztree-2.6.min.js" type="text/javascript"></script>

<script language="javascript">

/**
 * DDR 2012-05-08 配置产品功能
 * 修改成树型结果，ajax
 */
var node_ = {};
var simpleNodes = []; //菜单功能数组
var pdSipNodes = []; //产品菜单数组

var zTree1, zTree2;
var setting1 ={
		 checkable : true,
			isSimpleData : true,
			  rootPID : -1,
			  treeNodeKey : "id",
			 treeNodeParentKey : "pId",
		editable: false,
		edit_renameBtn:false,
		edit_removeBtn:false,
		dragCopy: true,
		dragMove: false,
		callback: {
			click:	null
		
		}
	};
	
	var setting2 ={
			isSimpleData : true,
			  rootPID : -1,
			  treeNodeKey : "id",
			 treeNodeParentKey : "pId",
		editable: false
		
		
	};

	//加载树的方法
	function reloadTree() {
		zTree1 = $("#treeDemo").zTree(setting1,simpleNodes);
		zTree2 = $("#treeDemo2").zTree(setting2,pdSipNodes);
		zTree2.expandAll(true);
	}
	
	//保存菜单到后台,并刷新右菜单  
	function saveProductMenu(){
		var nodes = zTree1.getCheckedNodes(); 
		 if(nodes.length==0){
			if(!confirm("未有选中的菜单及功能,确定要删除所有配置的产品吗？ ")){
				return ;
			}
				
		} 
		var	data = packageNodes(nodes)+$("#productJoinMenuFormBean").serialize();
		if(confirm("确定保存选中的菜单及功能吗?")){
			$.post("productJoinMenu.do",data,function cbk(msg){
					if(msg == "S"){
						$("#opTip").text("操作已成功 ");
						resetPdTree();
						setTimeout(function(){$("#opTip").text(" ");},3000);
					}
					
				});
		}	
	}

	//包装提交参数
	function packageNodes(nodes){
		var str = "";
		for(var i=0;i<nodes.length;i++){
				str+="funcno="+nodes[i].id+"&";
		}
		return str;
	}
	
	//重新加载已选择的树
	function resetPdTree(){
		var checkedNodes =   zTree1.transformToArray(zTree1.getCheckedNodes());
		zTree2 = $("#treeDemo2").zTree(setting2,null);
		var newNodes = [];
		var ids = "|"
		newNodes.push( { id:0, pId:-1, name:"已有产品功能列表", ename:"ent"});
		for(var i=0;i<checkedNodes.length;i++){
			var nod = checkedNodes[i];
			if(nod.checked && nod.id>0 && ids.indexOf(nod.id+"")==-1){
				newNodes.push({id:nod.id,pId:nod.pId,name:nod.name});
				ids+=nod.id+"|";
			}
		}
		zTree2 = $("#treeDemo2").zTree(setting2,newNodes);
		zTree2.expandAll(true);

	}
	
	
$(function(){
	pdSipNodes.push( { id:0, pId:-1, name:"已有产品功能列表", ename:"ent"});
	<c:forEach items="${menuOfProductTree}" var="menu">
	<c:set var="myIds" value="${myIds}${menu.menuId}|"/>
	 node_ = { id:${menu.menuId}, pId:${menu.parentId}, name:"${menu.name}${menu.displayFlag==0?("(不显示)"):""}", url:"${menu.url}"};
	 pdSipNodes.push(node_); 	
	</c:forEach>
	simpleNodes.push( { id:0, pId:-1, name:"所有产品功能配置", ename:"ent",checked:pdSipNodes.length>1});
	<c:forEach items="${menuAllTree}" var="menu">
		<c:set var="thisId" value="|${menu.menuId}|"/>
		 node_ = { id:${menu.menuId}, pId:${menu.parentId}, name:"${menu.name}${menu.type=='6'?'(功能)':''}${menu.displayFlag==0?("(不显示)"):""}",checked:false};
		 <c:if test="${fn:contains(myIds, thisId)}">node_.checked = true</c:if>
		 simpleNodes.push(node_);	
	</c:forEach>
	
	//加载树
	reloadTree();
	
});
</script>

</head>
<body>

<h2 class="panel_title">产品菜单配置</h2>

<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center" style="margin:0 auto;">
	<form id="productJoinMenuFormBean" name="productJoinMenuFormBean" action="productJoinMenu.do" method="post">
	<input name="productId" type="hidden" value="${productId}">
	<input name="productName" type="hidden" value="${productName}">
	<input name="allowObject" type="hidden" value="${allowObject}">
	<tr align="left" class=trForContent1>
		<td width="643" valign="middle" nowrap class="border_top_right4">&nbsp;&nbsp;&nbsp;&nbsp;产品名称：${productName}
		<span id="opTip" style="color: red"></span></td>
	</tr>
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">分配菜单 <input type="button" class="button1 button2" value="全部展开 " onclick="zTree1.expandAll(true);" />
									  <input type="button" class="button1 button2" value="全部闭合" onclick="zTree1.expandAll(false);" />	
					</td>
				<td>&nbsp;</td>
				<td align="left">已选菜单列表<input type="button" class="button1" value="全部展开 " onclick="zTree2.expandAll(true);" />
									  <input type="button" class="button1 button2" value="全部闭合" onclick="zTree2.expandAll(false);" />	</td>
			</tr>
			<tr align="center">

				<TD width=340px  height="500" align=center valign=top style="width:300px; overflow:auto;">
					
				<ul id="treeDemo" class="tree" style="width:250px; height:100%;overflow:auto;border: 1px solid #ccc;"></ul>
				</td>
				<td width="121">
				<input type="button" class="button1 button2" value="保存&gt;&gt; " onclick="saveProductMenu()" />
				</td>
				<TD width=340px  height="500" align=center valign=top style="width:300px; overflow:auto;">
					
				<ul id="treeDemo2" class="tree" style="width:250px; height:100%;overflow:auto;border: 1px solid #ccc;"></ul>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	</form>
</table>
</body>
</html>
