<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>

<link rel="stylesheet" href="${ctx}/css/main.css">

<script language="javascript">

function closePage(url){
	parent.closePage(url);
}

/*单个移动*/
function moveOptions(moveFlag) {
	var objPush = document.getElementById("nofuncno");
	var objGet = document.getElementById("funcno");
	if(moveFlag=='1'){
		objPush = document.getElementById("funcno");
		objGet = document.getElementById("nofuncno");
	}
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
    for(var i=0;i<document.accountTempFormBean.funcno.length; i++){
       document.accountTempFormBean.funcno.options[i].selected=true;
    }
 }

 function checkForm(){
    select_all();
	document.accountTempFormBean.submit();

 }

</script>

</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="6"></td>
	</tr>
</table>
 
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">配置入款网关结算类型</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<br>
<form name="accountTempFormBean" action="${ctx}/prepicitation/channelConfig.do?method=save" method="post">
<table width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
  <tr>
    	<td  align="left">
    	 	&nbsp;配置网关结算类型：
    	 	<select name="configType" id="configType" onchange="changeNewCType(this);">
				<option value="1" <c:if test="${configType eq '1'}">selected</c:if>>T+0</option>
				<option value="2" <c:if test="${configType eq '2'}">selected</c:if>>T+0顺延</option>
				<option value="3" <c:if test="${configType eq '3'}">selected</c:if>>T+1</option>
				<option value="4" <c:if test="${configType eq '4'}">selected</c:if>>T+1顺延</option>
				<option value="5" <c:if test="${configType eq '5'}">selected</c:if>>T+2</option>
				<option value="6" <c:if test="${configType eq '6'}">selected</c:if>>T+2顺延</option>
		    </select>
      		结算网关
    	</td>
  </tr>	    
</table>

<c:if test="${not empty message}">
	<div>
		<font color="#FF0000">${message}</font>
	</div>
</c:if>
<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr align="left" class=trForContent1>
		<td width="643" valign="middle" nowrap class="border_top_right4">&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="left">未配置渠道名称</td>
				<td>&nbsp;</td>
				<td align="left"><div id="newCType">新增<font color='#FF0000'>${newCType}</font>结算网关</div></td>
			</tr>
			<tr align="center">

				<td width="192" align="left">
					<select name="nofuncno" id="nofuncno" class="input1" size="25" multiple="multiple" style="width: 235px;" ondblclick="moveOptions($('nofuncno'),$('funcno'));">
						<c:forEach items="${fiCList}" var="fiC">
							<option value="${fiC.id}">${fiC.description}</option>
						</c:forEach>
					</select>
				</td>
				<td width="121">
				<table width="58" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="58" align="center">
							<input name="addB" type="button" class="button1" value="添 加 &gt;" onClick="moveOptions('0');">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="delB" type="button" class="button1" value="&lt; 删 除" onClick="moveOptions('1');">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="saveSubmit" type="button" class="button1" value=" 保 存 " onClick="checkForm();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
	
					<tr>
						<td align="center">
							<input type="button" class="button1" value=" 关 闭 " onclick="javascript:closePage('accountTempJoinProduct.do?accountTempId=${accountTempId}')" />
						</td>
					</tr>
				</table>
				</td>
				<td width="192" align="right">
					<select name="funcno" id="funcno" size="25" class="input1" multiple style="width: 235px;" onDblClick="moveOptions($('funcno'),$('nofuncno'));">
					<c:forEach items="${ccList}" var="cc">
						<option value="${cc.fiChannel}">${cc.description}</option>
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
</form>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

<script language="javascript">
 function changeNewCType(obj){
	 //var textValue = "新增T+0结算网关";
	 //if(obj.value == "1"){
	//	 textValue = "新增T+0结算网关";
	 //}else if(obj.value == "2"){
	//	 textValue = "新增T+0顺延结算网关";
	 //}else if(obj.value == "3"){
	//	 textValue = "新增T+1结算网关";
	 //}else if(obj.value == "4"){
	//	 textValue = "新增T+1顺延结算网关";
	 //}else if(obj.value == "5"){
	//	 textValue = "新增T+2结算网关";
	// }else if(obj.value == "6"){
	//	 textValue = "新增T+2顺延结算网关";
	 //}
	// document.getElementById("newCType").innerHTML=textValue;
	 var configType = obj.value;
	 window.location.href="${ctx}/prepicitation/channelConfig.do?configType="+configType;
 	 //$("#newCType").html("123");
 }
</script>

</body>
</html>
