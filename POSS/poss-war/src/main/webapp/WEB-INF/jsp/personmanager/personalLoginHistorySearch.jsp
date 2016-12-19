<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function personalLogHistoryQuery(pageNo,totalCount,pageSize) {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	
	if(null != endDate && 0 != endDate.length){
		if(0 == startDate.length ||  null == startDate){
			alert("请输入登录日期起始端");
			return
			}
		}

	
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#personSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalLoginHistory.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
//验证日期
function validDate(strDate1,strDate2){
	if(null != strDate1 && null != strDate2 &&
			0 != strDate1.length && 0 != strDate2.length){
		var date1 = new Date(strDate1.replace("-","/"));
		var date2 = new Date(strDate2.replace("-","/"));
		if(date1 > date2){
			return false;
		}
	}else{
		return false;
	}
	return true;
}
function load(pageNo,totalCount ){
	var str = $('#memberCode').val();
	if(null!=str && ""!=str){
		personalLogHistoryQuery(pageNo,totalCount);
		}
}
</script>

</head>

<body onload=load();>

<!-- <table width="30%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">会  员  日  志  查  询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">会员日志查询</h2>


<form id="personSearchFormBean" name="personSearchFormBean" >

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	
	<tr class="trForContent1">
		<td class="border_top_right4" align="right" >会员号：</td>
		<td class="border_top_right4"align="left" >
			<input	type="text" id="memberCode" name="memberCode" maxlength= "11" value="${memberCode}">
		</td>
		<td  class="border_top_right4" align="right" >姓名：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="userName" name="userName" maxlength= "8">
		</td>
	</tr>
	<tr class="trForContent1">
		<td  class="border_top_right4" align="right" >会员类型：</td>
		<td class="border_top_right4"align="left" >
			<select id="memberType" name="memberType"  >
					<option value="" selected>--请选择--</option>	
					<option value="1">个人会员</option>	
					<option value="2">企业会员</option>	
				</select>
		</td>
		<td  class="border_top_right4" align="right" >日志类型：</td>
		<td class="border_top_right4"align="left" >
			<select id="loginType" name="loginType"  >
					<option value="" selected>--请选择--</option>	
					<option value="1">登录</option>	
					<option value="2">支付</option>	
					<option value="3">付款</option>	
					<option value="4">充值</option>	
					<option value="5">确认付款</option>	
					<option value="6">余额查询</option>	
					<option value="7">交易查询</option>	
					<option value="8">设置安全问题</option>
					<option value="9">修改支付密码</option>
					<!-- <option value="10">订阅通知</option>
					<option value="11">添加联系人</option> -->
					<option value="12">找回支付密码</option>
					<option value="13">修改问候语</option>
					<option value="14">提现</option>
					<option value="15">补全资料</option>
					<option value="16">注册成功</option>
					<option value="17">安全登录</option>
					<!-- <option value="18">口令卡登录</option> -->
					<option value="19">登录失败</option>
				</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td  class="border_top_right4" align="right" >登陆用户名：</td>
		<td class="border_top_right4"align="left" >
			<input	type="text" id="loginName" name="loginName" maxlength= "64">
		</td>
	 	<td  class="border_top_right4" align="right" >登录日期：</td>
		<td  class="border_top_right4" align="left" colspan="3" >
			<input class="Wdate" type="text" id= "startDate" name="startDate"  onClick="WdatePicker()">
			～
			<input class="Wdate" type="text" id= "endDate" name="endDate"  onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center"><a
			class="s03" onclick="personalLogHistoryQuery()">
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

</body>

