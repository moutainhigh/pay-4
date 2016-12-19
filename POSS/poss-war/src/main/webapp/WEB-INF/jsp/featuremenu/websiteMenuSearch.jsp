<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function websiteMenuSearch(pageNo,totalCount,pageSize) {
	var type = $("#type").val();
	var checkType = $("#checkType").val();
	var pid = $("#pid").val();
	//在此做判断是因为在查看子菜单时,如果再看别的类型显示会有问题
	//特做判断 如果选择类型和原类型不同,则清除pid展现给客户所要查询的正确的数据
//	if(type != checkType){
//		document.getElementById("pid").value = "";
//		}
	
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#websiteMenuSearch").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	
	$.ajax({
		type: "get",
		url: "${ctx}/websiteMenu.do?method=menuSearchList&math="+Math.random(),
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function load(pageNo,totalCount ){
	websiteMenuSearch(pageNo,totalCount);
}



</script>
</head>

<body  onload=load();>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE  菜   单  查  询 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">WEBSITE菜单查询</h2>

<form id="websiteMenuSearch" name="websiteMenuSearch" >
<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<input type="hidden" id="pname" name="pname" value="${pname}" />
	<input type="hidden" id="pid" name="pid" value="${pid}" />
	<input type="hidden" id="checkType" name="checkType" value="${type}" />
	<tr class="trForContent1">
		<td class="border_top_right4" align="center" >菜单类型</td>
	</tr>
	<tr class="trForContent1">
	
		<td class="border_top_right4" align="center" >
			<select id="type" name="type"  >
				<option value="1"  <c:if test="${type==1}">selected</c:if> >公共</option>
				<option value="2" <c:if test="${type==2}">selected</c:if> >企业</option>
				<option value="3" <c:if test="${type==3}">selected</c:if> >个人</option>
				<option value="4" <c:if test="${type==4}">selected</c:if> >后台</option>
				<option value="5" <c:if test="${type==5}">selected</c:if> >业务级</option>
				<option value="6" <c:if test="${type==6}">selected</c:if> >功能级</option>
				<option value="8" <c:if test="${type==8}">selected</c:if> >个人产品</option>
				<option value="9" <c:if test="${type==9}">selected</c:if> >企业产品</option>
				<option value="10" <c:if test="${type==10}">selected</c:if> >交易中心</option>
		  </select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
		<a class="button" href="javascript:websiteMenuSearch()">
			<input class="button2" type="button" value="查询"></a>
		</td>
	</tr>

</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	<div id="delCheckDiv"  style="display: none;">
		请输入删除的密码：<input type="password" id="checkPassword" name="checkPassword"  value="" />
	</div>
	
	
	<div id="securityLevelDiv" style="display: none">
	<table class="inputTable" width="300" border="0" cellspacing="0" cellpadding="3" align="center">
	
	<tr>
		<th>安全级别</th>
		<td>
			
			<input type="checkbox" name="secLevel" value="1" name="secLel" checked　 />数字证书 
			<!-- <input type="checkbox" name="secLevel" value="2" name="secLel" checked　 />口令卡 -->
			
		</td>
	</tr>
	
	</table>
	</div>
</body>

