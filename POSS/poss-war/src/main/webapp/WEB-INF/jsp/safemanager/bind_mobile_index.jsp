<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
);

function indexQuery(pageNo,totalCount,pageSize) {
	
	var memberCode = $("#merchantCode").val();
	if(memberCode.length >0 && !(/\d{1,20}/.test(memberCode))){
		alert("账号必须是数字");
		return false;
	}
	var loginName = $("#loginName").val();
	if(loginName.length==0 && memberCode.length==0 ){
		alert("商户号或是登录名必须填写一个");
		return false;
	}
	
	$('#infoLoadingDiv').dialog('open');
	
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/bindMobile.do?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 


function queryDetail(id){
	$('#detailDiv').dialog( 
			{ 
			width:700,
			modal:true, 	
			title:'详细页面', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	} );
	
	$('#detailDiv').load("${ctx}/bindMobile.do",{operatorId:id,method:"queryDetail"},function(msg){
		});

		

}


function bindNewMobile(operId){
	$("#newMobileTip").text("");
	$("#newMobile").val("");
	$('#modifyBindDiv').dialog({ 
				width:500,
				height:200,
				modal:true, 	
				title:'绑定新手机', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
				buttons:{ 
					'确定':function(){ 
						
						var mobile =  $("#newMobile").val();
						if(! /\d{11}/.test(mobile)){
							$("#newMobileTip").text("手机号码必须是11位数字");
							return false;
						}
						if(!  /^1[3|4|5|8]\d{9}$/.test(mobile)){
							$("#newMobileTip").text("手机号码格式不正确，请输入正确的手机号");
							return false;
						}
							
							$.post("${ctx}/bindMobile.do?method=exeBind",{operatorId:operId,newMobile:$("#newMobile").val()}
								,function (msg){
										if(msg=="S"){
											indexQuery();
											$('#modifyBindDiv').dialog("close")
										}
										else{
											alert(msg);
										}
								}
							);
					}, 
					'取消':function(){ 
						$("#modifyBindDiv").dialog("close");
					} 
					} 
			
			}); 

						
						
}

function unBindMobile(operId,memberCode){
	$('#unBindDiv').dialog({ 
				width:500,
				height:150,
				modal:true, 	
				title:'取消手机绑定', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
				buttons:{ 
					'确定':function(){ 
						
							$.post("${ctx}/bindMobile.do?method=unBind",{operatorId:operId,'memberCode':memberCode}
								,function (msg){
										if(msg=="S"){
											indexQuery();
											$('#unBindDiv').dialog("close")
										}
										else{
											$('#unBindDiv').dialog("close")
											alert(msg);
										}
								}
							);
					}, 
					'取消':function(){ 
						$("#unBindDiv").dialog("close");
					} 
					} 
			
			}); 

						
						
}





</script>
</head>

<body>

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手机绑定查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean" style="width: 100%;text-align: center">
		<table class="searchTermTable" class="inputTable" style="width:60%;margin: 0px auto" >
			<tr>
				<th style="text-align: right">商户号：</th>
				<td style="text-align: left"><input type="text" id="merchantCode" name="merchantCode" style="width: 150px;" maxlength="20"/></td>
				<th style="text-align: right">登录名：</th>
				<td style="text-align: left"><input type="text" id="loginName" name="loginName" style="width: 150px;" maxlength="50" /></td>
				
			</tr>
			<tr>
				<th style="text-align: right">操作员账户：</th>
				<td style="text-align: left"><input type="text" id="operatorName" name="operatorName" style="width: 150px;" maxlength="50" /></td>
				<th style="text-align: right">手机号码：</th>
				<td style="text-align: left"><input type="text" id="bindMobile" name="bindMobile" style="width: 150px;" maxlength="20"/></td>
			</tr>
			
			<tr>
				<td  colspan="4" style="text-align: center;  ">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</form>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>

</c:if>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

<div id="modifyBindDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th width="100" nowrap="nowrap">新手机号码：</th>
		<td align="left">
			<input type="text" name="newMobile" id="newMobile" maxlength="11" /><span id="newMobileTip" style="color: red"></span>
		</td>
	</tr>
	</table>
</div>

<div id="unBindDiv" style="display: none">
		<span style="font-size:  15pt">确定要取消此用户的手机绑定吗？</span>
</div>

<div id="detailDiv" style="display: none; ">
	
</div>

</body>

