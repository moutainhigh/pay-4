<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/main.css" type="text/css"  />
<link rel="stylesheet" href="${ctx}/css/ztree/zTreeStyle.css" />
<link rel="stylesheet" href="${ctx}/css/ztree/demoStyle/demo.css">
<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/plugins/ztree/jquery.ztree-2.6.min.js" type="text/javascript"></script>

 <script type="text/javascript">
  <!--
  	var copyType = ${copyType};//2为剪切
	var zTree1, zTree2;
	var setting1 ={
				 isSimpleData : true,
				  rootPID : -999,
				  treeNodeKey : "id",
				 treeNodeParentKey : "pId",
			editable: true,
			edit_renameBtn:false,
			edit_removeBtn:false,
			dragCopy: true,
			dragMove: copyType==2,
			callback: {
				click:	zTreeOnClick

			
	}
		};


		var setting2 = {
				 isSimpleData : true,
				  rootPID : -1,
				  treeNodeKey : "id",
				 treeNodeParentKey : "pId",
			editable: true,
			edit_renameBtn:false,
			edit_removeBtn:true,
			dragMove: false,
			callback: {
				click:	zTreeOnClick,
				drop: zTreeOnDrop
		}
			
					 
		};

	$(document).ready(function(){
		reloadTree();
	});

	var preSelectedNode1;
	var preSelectedNode2;

	function zTreeOnClick(event, treeId, treeNode) {
		if (treeId=="treeDemo") {
			if (preSelectedNode1 == treeNode) {
				zTree1.cancelSelectedNode();
				preSelectedNode1 = null;
			} else {
				preSelectedNode1 = treeNode;
			}
		} else {			
			if (preSelectedNode2 == treeNode) {
				zTree2.cancelSelectedNode();
				preSelectedNode2 = null;
			} else {
				preSelectedNode2 = treeNode;
			}
		}
	}

	function zTreeOnDrop(event, treeId, treeNode, targetNode, moveType) {
		preSelectedNode1 = zTree1.getSelectedNode();
		preSelectedNode2 = zTree2.getSelectedNode();
	}

	function moveTreeL2R() {
		moveTreeNode(zTree1, zTree2);
	}
	
	function moveTreeR2L() {
		return false;
		//moveTreeNode(zTree2, zTree1);
	}
	
	function moveTreeNode(srcTree, targetTree) {
		
		var srcNode = srcTree.getSelectedNode();
		if (!srcNode) {
			alert("请先选择需要移动的节点！");
			return;
		}
		var targetNode = targetTree.getSelectedNode()	
		if(srcNode.id == targetNode.id){
			alert("不能移动到自己结点下！");
			return false;
		}
		
		srcTree.removeNode(srcNode);		
		targetTree.addNodes(targetNode, [srcNode]);
		targetTree.selectNode(srcNode);		
	}

	function reloadTree() {
		zTree1 = $("#treeDemo").zTree(setting1, clone(simpleNodes_2,"_1"));
		zTree2 = $("#treeDemo2").zTree(setting2, clone(simpleNodes_2,"_2"));
	}

	function clone(jsonObj, newName) {
	    var buf;
	    if (jsonObj instanceof Array) {
	        buf = [];
	        var i = jsonObj.length;
	        while (i--) {
	            buf[i] = clone(jsonObj[i], newName);
	        }
	        return buf;
	    }else if (typeof jsonObj == "function"){
	        return jsonObj;
	    }else if (jsonObj instanceof Object){
	        buf = {};
	        for (var k in jsonObj) {
		        if (k!="parentNode") {
		            buf[k] = clone(jsonObj[k], newName);
		            if (newName && k=="name") buf[k] += newName;
		        }
	        }
	        return buf;
	    }else{
	        return jsonObj;
	    }
	}

	function submitCopy(){
		var nodes  = zTree2.getCheckedNodes(false);
		var	data = packageNodes(nodes);
		if(data===false){
			alert("含有子结点和父结点相同的非法菜单");
			return false;
		}
		if(! data){
			alert("右侧菜单未有增加项，无需提交 ");
			return ;
		}
		if(data.indexOf("-1")>=0){
			alert("暂时不支持复制到根目录，需确认开通新的功能 ");
			return ;
		}
		if(confirm("确定执行复制吗?")){
			
			if(copyType ==2 ){
				data  = "method=exeCutMenu"+data;	
			}else{
				data  = "method=exeCopyMenu"+data;	
			}
			$.post("websiteMenu.do",data,function cbk(msg){
					window.location.reload();
				});
		}	
	}

	function packageNodes(nodes){
		var str = "";
		for(var i=0;i<nodes.length;i++){
			if(nodes[i].name.indexOf("_1")>=0){
				if(nodes[i].id == nodes[i].pId){
					return false;
				}
				str+="&copyId="+nodes[i].id+"&copyPId="+(nodes[i].pId);
			}
		}
		return str;
	}
  //-->
  </script>


</head>

<body>

<!-- <table width="80%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE  菜 单 复 制/剪切</font></div>
		</td>
		
	</tr>
	
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">WEBSITE 菜单复制/剪切</h2>

<TABLE border=0 width="850" class="tb1" style="margin:0 auto;">
	
	<TR>
		<TD width=340px  height="500" align=center valign=top style="width:300px; overflow:auto;">
			<ul id="treeDemo" class="tree" style="width:250px; height:100%;overflow:auto;border: 1px solid #ccc;"></ul>
		</TD>
		<TD width=30px align="center" valign="middle" class="operate">
			
			<button class="ico moveNode" onfocus="this.blur();" title="移动节点 右->左" onclick="moveTreeL2R();"></button>
			<br/><br/><br/><br/>
			<button class="ico refresh" onfocus="this.blur();" title="恢复初始状态" onclick="reloadTree();"></button>
			<br/><br/><br/><br/><br/><br/><br/><br/>
			<input type="button" class="button2" value="提交" onclick="submitCopy()"/>
			
		</TD>
		<TD width=340px height="500" align=center valign="top" > 
			<ul id="treeDemo2" class="tree" style="width:250px;height:100%; overflow:auto;border: 1px solid #ccc;"></ul>
		</TD>
		<td align=center valign="top" >
			<div style="border: 1px solid #ccccff;margin: 3px;font-size: 12px;width=300px;text-align: left" >温馨提示：<br />
			1.使用拖拉形式直接把左边菜单拖到右边对应的菜单下，还可以选中左边要复制的菜单，再选中右边的菜单，点击移动。<br />
			2.现只支持复制到企业，个人，交易中心功能中。<br />
			3.编辑完成别完记提交。<br />
			4.特别提示：这里的删除只是便于编辑，不会真正删除数据库。<br />
			</div>
		</td>
		
	</TR>
</TABLE>
	
</body>

<script language="javascript">

var node_ = {};
var simpleNodes_2 = [];
var oriIds = [-1,-2,-3,-10];
//simpleNodes_2.push( { id:-1, pId:-999, name:"所有功能根结点", ename:"ent"});
simpleNodes_2.push( { id:-2, pId:-1, name:"企业功能", ename:"ent"});
simpleNodes_2.push( { id:-3, pId:-1, name:"个人功能", ename:"ent"});
simpleNodes_2.push( { id:-10, pId:-1, name:"交易中心功能", ename:"ent"});

<%--
<c:forEach var="bigTypeValue" items="<%= new int[]{2,3,10} %>">
<c:forEach var="menu"  items="${treeMenuList}">
<c:if test="${menu.type==bigTypeValue }">
	 node_ = { id:${menu.menuId}, pId:${menu.parentId==0?(-menu.type):menu.parentId}, name:"${menu.name}${menu.displayFlag==0?("(不显示)"):""}", url:"${menu.url}"};
	 simpleNodes_2.push(node_);
	 oriIds.push(${menu.menuId});
</c:if>
</c:forEach>
</c:forEach>
--%>
<c:forEach var="menu"  items="${treeMenuList}">
	 node_ = { id:${menu.menuId}, pId:${menu.parentId==0?(-menu.type):menu.parentId}, name:"${menu.name}${menu.displayFlag==0?("(不显示)"):""}", url:"${menu.url}"};
	 simpleNodes_2.push(node_);
	 oriIds.push(${menu.menuId});
</c:forEach>

</script>

