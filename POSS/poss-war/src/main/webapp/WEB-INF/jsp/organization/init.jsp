<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function merchantQuery(pageNo,totalCount,pageSize) {
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#merchantSearchFormBean").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/organization.do?method=queryOrg",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
function validFrom() {
    var reg = /^\d+$/;
    var merchantCode = document.getElementById('orgCode').value; 
    if(merchantCode!=null&&merchantCode!=''){ 
    	
    	var isNumber = reg.test(merchantCode.replace(/(^\s*)|(\s*$)/g, ""));
	    if(isNumber){
	    	merchantQuery();
	    }else{
	    	alert('机构号输入不合法!');
	    }
    }else{
    	merchantQuery();
    }
}


//根据code查看机构详细信息
function forCheck(code){
	$.ajaxSetup({ cache: false });
	$.getJSON("${ctx}/organization.do?method=queryOrgByCode",{"code":code,"htmlType":"json"},function(msg){
		for(x in msg){	
			if(msg[x]==null){
				$(updateForm[x]).val('');
			}else{
				$(updateForm[x]).val(msg[x]+'');
			}
			
			$('#select option:updateForm["orgType"]('+$(msg["orgType"]+1)+')').attr('selected','selected');
			$('#select option:updateForm["displayForWithdraw"]('+$(msg["displayForWithdraw"])+')').attr('selected','selected'); 
			$('#select option:updateForm["displayForDeposit"]('+$(msg["displayForDeposit"])+')').attr('selected','selected'); 
			$('#select option:updateForm["displayForCredit"]('+$(msg["displayForCredit"])+')').attr('selected','selected'); 
			$('#select option:updateForm["displayForChannel"]('+$(msg["displayForChannel"])+')').attr('selected','selected'); //add by davis.guo 2016-08-11
		}
		$('#updateLogDiv').dialog( 
				{ 
				position:"top",
				width:600,
				modal:true, 	
				title:'机构详细页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		} );
	});
	
}
//修改机构信息
function updateOrg(){
	if(validOrgName1()&&validDesciption1()&&validDisplayName1()){
		var pars = $("#updateForm").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/organization.do?method=updateOrg",
			data: pars,
			success: function(result) {
				if(result){
					alert("修改成功！");
					$("#updateLogDiv").dialog("close");  //关闭Dialog
					validFrom();  //重新查询所有信息
				}else{
					alert("修改失败！");
				};
			}
		});
	};
	
	
}
//显示添加机构的dialog
function shouAddOrg(){
	$('#orgCode1').attr('value',"");
	$('#orgName1').attr('value',"");
	$('#description1').attr('value',"");
	$('#displayName1').attr('value',"");
	$.getJSON("${ctx}/organization.do?method=queryOrgCode",{"htmlType":"json"},function(msg){
		$('#orgCodeHint').text("当前机构的最大编号为"+msg);
		$('#addLogDiv').dialog( 
				{ 
				position:"top",
				width:600,
				modal:true, 	
				title:'添加机构页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		} );
	});
}

//验证添加的机构号
function validAddCode() {
    var reg = /^[0-9]+$/;
    var merchantCode = document.getElementById('orgCode1').value;

	var codeHint=$('#orgCodeHint').text();
    if(merchantCode!=null&&merchantCode!=''){ 
    	var isNumber = reg.test(merchantCode);
	    if(isNumber&&codeHint=="code可以使用!"){
	    	addOrg();
	    }else{
	    	alert('机构号输入不合法!');
	    }
    }else{
    	alert('请输入机构号!');
    }
	
}
//验证修改时的各项参数
function validOrgName1(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var orgName=$('#orgName2').val();
	if(orgName==null||orgName==""){
		alert("机构名称不能为空或空格!");	
		return false;
	};
	if(!reg.test(orgName)){
		alert("机构名称只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
function validDesciption1(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var description=$('#description2').val();
	if(description==null||description==""){
		alert("描述不能为空或空格!");	
		return false;
	};
	if(!reg.test(description)){
		alert("描述只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
function validDisplayName1(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var displayName=$('#displayName2').val();
	if(displayName==null||displayName==""){
		alert("显示名称不能为空或空格!");	
		return false;
	};
	if(!reg.test(displayName)){
		alert("显示名称只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
//验证添加时的各项参数
function validOrgName(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var orgName=$('#orgName1').val();
	if(orgName==null||orgName==""){
		alert("机构名称不能为空或空格!");	
		return false;
	};
	if(!reg.test(orgName)){
		alert("机构名称只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
function validDesciption(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var description=$('#description1').val();
	if(description==null||description==""){
		alert("描述不能为空或空格!");	
		return false;
	};
	if(!reg.test(description)){
		alert("描述只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
function validDisplayName(){
	var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+$/;
	var displayName=$('#displayName1').val();
	if(displayName==null||displayName==""){
		alert("显示名称不能为空或空格!");	
		return false;
	};
	if(!reg.test(displayName)){
		alert("显示名称只能为汉字、数字和字母!");	
		return false;
	};
	return true;
}
//添加机构信息
function addOrg(){
	if(validOrgName()&&validDesciption()&&validDisplayName()){
		var pars = $("#addForm").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/organization.do?method=addOrg",
			data: pars,
			success: function(result) {
				if(result){
					alert("添加成功！");
					$("#addLogDiv").dialog("close");  //关闭Dialog
					validFrom();  //重新查询所有信息
				}else{
					alert("添加失败！");
				};
			}
		});
		$('#orgCode1').attr('value',"");
		$('#orgName1').attr('value',"");
		$('#description1').attr('value',"");
		$('#displayName1').attr('value',"");
	}
	
}
//验证code是否存在
function verifyCode(){
	var code=$('#orgCode1').val();
	var reg = /^[0-9]+$/;
	var isNumber = reg.test(code);
	$.getJSON("${ctx}/organization.do?method=queryOrgByCode",{"code":code,"htmlType":"json"},function(msg){	
		if(msg!=null){
			$('#orgCodeHint').text("code已存在!");
		}else if(isNumber==false){
			$('#orgCodeHint').text("code不合法,只能为数字!");
		}else{
			$('#orgCodeHint').text("code可以使用!");
		}
	});
}
</script>
</head>

<body>

<h2 class="panel_title">机构管理</h2>
<form id="merchantSearchFormBean" name="merchantSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >机构编号：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="orgCode" name="orgCode"></td>
		<td class="border_top_right4" align="right" >机构名称：</td>
		<td class="border_top_right4" align="left" >
		<input	type="text" id="orgName" name="orgName"></td>
		<td class="border_top_right4" align="right" >机构类型：</td>
		<td class="border_top_right4" align="left" >
			<select id="orgType" name="orgType" size="1">
				<option value="" selected>---请选择---</option>
				<option value="1">借记卡</option>
				<option value="2">贷记卡</option>
				<option value="3">信用卡</option>
			</select>
		</td>
		<!-- 添加“是否渠道”查询条件 add by davis.guo 2016-08-25 -->
		<td class="border_top_right4" align="right" >是否渠道：</td>
		<td class="border_top_right4" align="left" >
			<select id="displayForChannel" name="displayForChannel" size="1">
				<option value="" selected>---请选择---</option>
				<option value="0">否</option>
				<option value="1">是</option>
			</select>
		</td>
		<td class="border_top_right4" rowspan="2" align="center" valign="middle">
		<input class="button2" type="button" value="添加" onclick="shouAddOrg()" />
		</td>
		
	</tr>

	<tr class="trForContent1">
		<td class="border_top_right4" colspan="8" align="center"><a
			class="s03" href="javascript:validFrom()">
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
<!-- 修改 -->
<div id="updateLogDiv" style="display: none">
	<form id="updateForm" name="updateForm">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>机构编号</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="orgCode" name="orgCode" value="" disabled="disabled">
				<input	type="hidden" id="orgCode" name="orgCode" value="">
			</td>
			<td class="border_top_right4" align="center" nowrap>机构名称</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="orgName2" name="orgName">
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>机构类型</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="orgType" name="orgType" size="1">
					<option value="1">借记卡</option>
					<option value="2">贷记卡</option>
					<option value="3">信用卡</option>
				</select>
			</td>
			<td class="border_top_right4" align="center" nowrap>描述</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="description2" name="description">
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>创建日期</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="creationDates" name="creationDates" value="" disabled="disabled" />
			</td>
			<td class="border_top_right4" align="center" nowrap>是否在提现时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForWithdraw" name="displayForWithdraw" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>是否在充值时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForDeposit" name="displayForDeposit" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
			<td class="border_top_right4" align="center" nowrap>是否在还信用卡时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForCredit" name="displayForCredit" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>显示名称</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="displayName2" name="displayName">
			</td>
			<td class="border_top_right4" align="center" nowrap>是否渠道</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForChannel" name="displayForChannel" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap colspan="4">
				<input	type="button" value="修改" onclick="updateOrg()"/>
			</td>
		</tr>
	</table>
	</form>
</div>
<!-- 添加机构 -->
<div id="addLogDiv" name="addLogDiv" style="display: none">
	<form id="addForm" name="addForm">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>机构编号</td>
			<td class="border_top_right4" align="left" nowrap colspan="4">
				<input	type="text" id="orgCode1" name="orgCode" value="" onblur="verifyCode()"><span style="Color:red">*</span><span name="orgCodeHint" id="orgCodeHint" style="color: red;"></span>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>机构名称</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="orgName1" name="orgName"><span style="Color:red">*</span>
			</td>
			<td class="border_top_right4" align="center" nowrap>机构类型</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="orgType" name="orgType" size="1">
					<option value="1">借记卡</option>
					<option value="2">贷记卡</option>
					<option value="3">信用卡</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>描述</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="description1" name="description"><span style="Color:red">*</span>
			</td>
			<td class="border_top_right4" align="center" nowrap>是否在提现时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForWithdraw" name="displayForWithdraw" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>是否在充值时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForDeposit" name="displayForDeposit" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
			<td class="border_top_right4" align="center" nowrap>是否在还信用卡时显示</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForCredit" name="displayForCredit" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap>显示名称</td>
			<td class="border_top_right4" align="center" nowrap>
				<input	type="text" id="displayName1" name="displayName"><span style="Color:red">*</span>
			</td>
			<td class="border_top_right4" align="center" nowrap>是否渠道</td>
			<td class="border_top_right4" align="center" nowrap>
				<select id="displayForChannel" name="displayForChannel" size="1">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="center" nowrap colspan="4">
				<input	type="button" value="添加" onclick="validAddCode()"/>
			</td>
		</tr>
	</table>
	</form>
</div>
</body>

