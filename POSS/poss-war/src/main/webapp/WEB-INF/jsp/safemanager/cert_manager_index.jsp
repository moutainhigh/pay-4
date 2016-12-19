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
		url: "${ctx}/operatorCertManager.do?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 


function queryDetail(memberCode,id){
	$('#detailDiv').load("${ctx}/operatorCertManager.do",{"memberCode":memberCode,operatorId:id,method:"queryDetail"},function(msg){
		});

		$('#detailDiv').dialog( 
				{ 
				width:800,
				height:400,
				modal:true, 	
				title:'详细页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		} );

}





function removeCert(certMemberId){
	$('#unBindDiv').dialog({ 
				width:500,
				height:150,
				modal:true, 	
				title:'确定删除数字证书吗', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
				buttons:{ 
					'确定':function(){ 
						
							$.post("${ctx}/operatorCertManager.do?method=removeCert",{"certMemberId":certMemberId}
								,function (msg){
										if(msg=="S"){
											indexQuery();
											$('#unBindDiv').dialog("close")
										}
										else{
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

function disableUsePlace(certManageId,place){
	
	if(confirm("确定要删除该用户“"+place+"”的数字证书么?")){
		$.post("${ctx}/operatorCertManager.do?method=removePlace",{"certManageId":certManageId}
		,function (msg){
			if(msg=="S"){
				alert("删除成功!");
				$("#statusTd_"+certManageId).text("无效");
				$("#revTd_"+certManageId).html("&nbsp;");
			}
			else{
				alert(msg);
			}
			}
		);
	}
	
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
		<div align="center"><font class="titletext">数字证书管理</font></div>
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
				<td><input type="text" id="merchantCode" name="merchantCode" style="width: 150px;" maxlength="20"/></td>
				<th style="text-align: right">登录名：</th>
				<td><input type="text" id="loginName" name="loginName" style="width: 150px;" maxlength="50" /></td>
				
			</tr>
			<tr>
				<th style="text-align: right">操作员账户：</th>
				<td><input type="text" id="operatorName" name="operatorName" style="width: 150px;" maxlength="50" /></td>
				<th style="text-align: right">证书状态：</th>
				<td><select id="certStatus" name="certStatus">
						<option value="">--请选择--</option>
						<option value="-1">未申请</option>
						<option value="1">已申请未安装</option>
						<option value="2">已申请已安装</option>
						<option value="3">已注销</option>
				</select> </td>
			</tr>
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

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

<div id="modifyBindDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th width="100" nowrap="nowrap">新的手机号码：</th>
		<td align="left">
			<input type="text" name="newMobile" id="newMobile" maxlength="11" /><span id="newMobileTip" style="color: red"></span>
		</td>
	</tr>
	</table>
</div>

<div id="unBindDiv" style="display: none">
		<span style="font-size:  15pt">确定要取消此用户的数字证书么？</span>
</div>

<div id="detailDiv" style="display: none;width: 500px;height: 500px ">
	
</div>

</body>

