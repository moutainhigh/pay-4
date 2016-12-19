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
function findchild(pid,pname,type){
	 window.location="websiteMenu.do?t="+Math.random()+"&method=index&pid="+pid+"&pname="+pname+"&type="+type;
}
function back(pid,pname,type){
	window.location="websiteMenu.do?t="+Math.random()+"&method=index&pid="+pid+"&pname="+pname+"&type="+type;
}
function editMenu(menuId){
	var url = "websiteMenu.do?t="+Math.random()+"&method=menuEditView&menuId="+menuId;
	parent.addMenu("编辑菜单",url);
}

function addChild(parentId,type){
	var url = "websiteMenu.do?t="+Math.random()+"&method=menuCreateView&parentId="+parentId+"&type="+type;
	parent.addMenu("添加菜单",url);
}
function sort(parentId,type){
	var url = "websiteMenu.do?t="+Math.random()+"&method=sortView&pId="+parentId+"&type="+type;
	parent.addMenu("排序",url);
}

function copyAddTo(menuId){
	var url = "websiteMenu.do?t="+Math.random()+"&method=copyAddTo&id="+menuId;
	parent.addMenu("复制菜单",url);
	
}
function cutAddTo(menuId){
	var url = "websiteMenu.do?t="+Math.random()+"&method=copyAddTo&copyType=2&id="+menuId;
	parent.addMenu("剪切菜单",url);
}

var delMenuId = null;
function delCheck(id){
	delMenuId = id;
	$('#delCheckDiv').dialog('open'); //修改标题 
	
}

$(document).ready(function(){

	$('#delCheckDiv').dialog( 
			{ 
			autoOpen:false,	
			height:170, 
			width:400, 
			title:'删除确认', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
					var checkPassword= $("#checkPassword").val();
					$.post("websiteMenu.do?method=delMenu",{"checkPassword":checkPassword,"id":delMenuId},function cbk(msg){
						if(msg=="S"){
							websiteMenuSearch();
							$('#delCheckDiv').dialog('close');
						}else{
							alert(msg);
							$("#delCheckDiv").dialog("close");
						}
						});
			}, 
			'取消':function(){ 
				$("#delCheckDiv").dialog("close");
			} 
			} 
			} 
			); 

	
	$('#securityLevelDiv').dialog( 
			{ 
			autoOpen:false,	
			height:170, 
			width:400, 
			title:'设置安全级别', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
				var appener = "";
				$('input[name="menuChecked"]').each(function(i){
					 if(this.checked) appener +="&menuId="+($(this).val());
					});
				appener += "&securityLevel="
				$('input[name="secLevel"]').each(function(i){
					 if(this.checked) appener +=($(this).val())+",";
				});
				
				appener+="0";
			
				$.post("websiteMenu.do?method=setSecuLevel"+appener,{},function cbk(msg){
					if(msg=="S"){
						alert("安全级别设置成功！");
						$('#securityLevelDiv').dialog('close');
					}else{
						alert(msg);
						$("#securityLevelDiv").dialog("close");
					}
					});
			}, 
			'取消':function(){ 
				$("#securityLevelDiv").dialog("close");
			} 
			} 
			} 
			); 
	
});


function checkedAllMenu(){
	var value = $("#checkedAll").attr("checked");
	$("input[name=menuChecked]").attr("checked",value);
}

function setSecurityLevel(){
	var count = 0;
	$('input[name="menuChecked"]').each(function(i){
		 if(this.checked){
			count ++;
		 }
	});
	
	if(count > 0){
		$('#securityLevelDiv').dialog('open'); 
	}else{
		alert("请选择需要设置的菜单！");
	}
	
}
</script>
</head>

<body>


<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<input type="hidden" name="tess" value="${menuList.parentId}"/>
	<tr class="trForContent2" align="center" valign="middle">
		<td class="" align="center" >父菜单：${pname}</td>
		<td ></td>
		<td></td>
		<td ></td>
		<td ></td>
		<td ></td>
		<td ></td>
		<td class="" align="center" >&nbsp;&nbsp;
		<c:if test="${pid!=0}">
			<a href="javascript:void(0);" onclick="back('${backParentId}','${pname}','${type}');">返回上一级</a>
		</c:if>
		<a href="javascript:void(0);" onclick="cutAddTo('${menuList.menuId}')">剪切菜单</a>
		<a href="javascript:void(0);" onclick="copyAddTo('${menuList.menuId}')">复制菜单</a>
		<a href="javascript:void(0);" onclick="setSecurityLevel()"> 设置安全极别</a>
		</td>
	
	</tr>
	<thead>
	<tr class="" align="center" valign="middle">
		<th class=""  ><input type="checkbox" value="1" id="checkedAll" name="checkedAll" onclick="checkedAllMenu()" ></th>
		<th class=""  >菜单名称</th>
		<th class=""  >菜单编号</th>
		<th class=""  >菜单链接</th>
		<th class=""  >是否有效</th>
		<th class=""  >是否显示</th>
		<th class=""  >所属</th>
		<th class=""  >操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="menuList" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
	
	<td class="" align="center" ><input type="checkbox" value="${ menuList.menuId}" name="menuChecked" ></td>
			<td class="" align="center" >${menuList.name}&nbsp;</td>
			<td class="" align="center" >${menuList.menuCode}&nbsp;</td>
			<td class="" align="center" >${menuList.url}&nbsp;</td>
			<td class="" align="center" >
				<c:if test="${menuList.status==1}">有效&nbsp;</c:if>
				<c:if test="${menuList.status==0}">无效&nbsp;</c:if>
			</td>
			<td class="" align="center" >
				<c:if test="${menuList.displayFlag==1}">显示&nbsp;</c:if>
				<c:if test="${menuList.displayFlag==0}">不显示&nbsp;</c:if>
			</td>
		
			<td class="" align="center" >
				<c:if test="${menuList.type==1}">公共&nbsp;</c:if>
				<c:if test="${menuList.type==2}">企业&nbsp;</c:if>
				<c:if test="${menuList.type==3}">个人&nbsp;</c:if>
				<c:if test="${menuList.type==4}">后台&nbsp;</c:if>
				<c:if test="${menuList.type==5}">业务级&nbsp;</c:if>
				<c:if test="${menuList.type==6}">功能级&nbsp;</c:if>
				<c:if test="${menuList.type==7}">个人卖家&nbsp;</c:if>
				<c:if test="${menuList.type==8}">个人产品&nbsp;</c:if>
				<c:if test="${menuList.type==9}">企业产品&nbsp;</c:if>
				<c:if test="${menuList.type==10}">交易中心&nbsp;</c:if>
			</td>
			<td class="" align="center" >
				<a href="javascript:void(0);" onclick="editMenu('${menuList.menuId}');">编辑</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" onclick="sort('${menuList.parentId}','${menuList.type}');">排序</a>
				<a href="javascript:void(0);" onclick="addChild('${menuList.menuId}','${menuList.type}');">&nbsp;&nbsp;添加子菜单</a>
				<a href="javascript:void(0);" onclick="findchild('${menuList.menuId}','${menuList.name}','${menuList.type}');">&nbsp;&nbsp;查看子菜单</a>
				<a href="javascript:void(0);" onclick="delCheck('${menuList.menuId}')"> 删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="websiteMenuSearch" pageBean="${page}" sytleName="black2"/>

</body>

