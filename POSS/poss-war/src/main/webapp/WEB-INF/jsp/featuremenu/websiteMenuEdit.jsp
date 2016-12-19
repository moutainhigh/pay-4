<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function closePage() {
	parent.closePage('websiteMenu.do?method=menuEditView&menuId=${menu.menuId}');
}
function submitSave() {
	var name = $('#name').val(); 
	var url = $('#url').val(); 
	var menuCode = $('#menuCode').val(); 
	if($.trim(name) == "" ){
		alert("请输入菜单名称");
		return;
	}
	if($.trim(url) == "" ){
		alert("请输入菜单链接");
		return;
	}
	if($.trim(menuCode) == "" ){
		alert("请输入菜单编码");
		return;
	}
	$('#infoLoadingDiv').dialog('open');
	$.post("websiteMenu.do",$('#websiteMenu').serialize(),function cbk(obj){
		$('#infoLoadingDiv').dialog('close');
		var obj = eval("("+obj+")");
		alert(obj.msg);
	}			
	);
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

$(document).ready(function(){
	if(6 == '${menu.type}'){
		document.getElementById('hierarchyTr').style.display ='';
	}
	
	////初始化checkbox
	var checkedSe = "${menu.securityLevel}".split(",")
	$(":input[name=securityLevel]").val(checkedSe)
	
	
});

</script>
</head>

<body onload="onLoad()">

<!-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE  菜   单    操    作      </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">WEBSITE菜单操作</h2>

<form id="websiteMenu" name="websiteMenu" method="post" action="${ctx}/websiteMenu.do">
	<input type="hidden" name="menuId" value="${menu.menuId}"/>
	<input type="hidden" name="method" value="editSaveMenu"/>
	<input type="hidden" name="hiddenType" value="${menu.type}"/>
	<input type="hidden" name="orderId" value="${menu.orderId}"/>
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >菜单名称</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="name" name="name" maxlength= "32" value="${menu.name}" style="width: 260px;"/>
			</td>
		</tr>

		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >菜单URL</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="url" name="url" maxlength= "128" value="${menu.url}" style="width: 260px;"/>
			</td>
		</tr>
	
	<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >菜单编码</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="menuCode" name="menuCode" maxlength= "32" value="${menu.menuCode}" style="width: 220px;"/>
				<font color="red">注：个人以app开头，企业以corp。以此类推  如：bsp_account_info</font>
			</td>
		</tr>
		<tr class="trForContent1">
		<td class="border_top_right4"  align="right">菜单类型:</td>
			<td class="border_top_right4"  align="left">
				<select name="type" disabled="true">
					<option value="1" <c:if test="${menu.type==1}">selected</c:if> >公共</option>
					<option value="2" <c:if test="${menu.type==2}">selected</c:if> >企业</option>
					<option value="3" <c:if test="${menu.type==3}">selected</c:if> >个人</option>
					<option value="4" <c:if test="${menu.type==4}">selected</c:if> >后台</option>
					<option value="5" <c:if test="${menu.type==5}">selected</c:if> >业务级</option>
					<option value="6" <c:if test="${menu.type==6}">selected</c:if> >功能级</option>
					<option value="8" <c:if test="${menu.type==8}">selected</c:if> >个人产品</option>
					<option value="9" <c:if test="${menu.type==9}">selected</c:if> >企业产品</option>
					<option value="10" <c:if test="${menu.type==10}">selected</c:if> >交易中心</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" id="hierarchyTr"  style="display:none;">
		<td class="border_top_right4"  align="right">菜单层级:</td>
			<td class="border_top_right4"  align="left">
				<select name="hierarchy">
					<option value="10" <c:if test="${menu.hierarchy==10}">selected</c:if>>功能级菜单</option>
					<option value=""<c:if test="${menu.hierarchy==null}">selected</c:if>>--请选择--</option>
				</select>
			</td>
		</tr>			
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">是否有效:</td>
			<td class="border_top_right4"  align="left">
				<select name="status">
					<option value="1" <c:if test="${menu.status==1}">selected</c:if>>有效</option>
					<option value="0" <c:if test="${menu.status==0}">selected</c:if>>无效</option>
				</select>
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">是否显示</td>
			<td class="border_top_right4"  align="left">
				<select name="displayFlag">
					<option value="1" <c:if test="${menu.displayFlag==1}">selected</c:if>>显示</option>
					<option value="0" <c:if test="${menu.displayFlag==0}">selected</c:if>>不显示</option>
				</select>
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">是否需要数字证书</td>
			<td class="border_top_right4"  align="left">
					<input type="checkbox" name="securityLevel" value="1"  　 />数字证书
					<!-- <input type="checkbox" name="securityLevel" value="2"  　 />口令卡 -->
					<input type="checkbox" name="securityLevel" value="0"  style="display: none"　 />
			</td>
		</tr>
	
	
	
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">备注：</td>
			<td class="border_top_right4"  align="left">
				<textarea rows="7" cols="30" name="description">${menu.description}</textarea>
			</td>
		</tr>
		<tr class="trForContent1">
			<td colspan="2" align="center">
				<input type = "button" class="button2" onclick="javascript:submitSave();" value="修改">
			</td>
		</tr>
	</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

