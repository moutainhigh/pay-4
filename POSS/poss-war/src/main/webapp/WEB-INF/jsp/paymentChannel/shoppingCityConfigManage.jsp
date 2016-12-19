 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script language='javascript' src="${ctx}/js/formvalid/formValidFI.js"></script>
<script language="javascript"><!--

//确认提交
function processCommit(memberCode){
	$('#infoLoadingDiv').dialog('open');	
	if( validateForm()){
		var pars = $("#shoppingCityConfigFormBean").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/shoppingCityConfigUpdate.do",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				alert("${memberCode}更新完成！");
			}
		});
	}else{
		$('#infoLoadingDiv').dialog('close');
	}
}

function validateForm(){

	var patt_base64 = new RegExp("^[A-Za-z0-9+/]+$");
	var patt_URL = new RegExp("[a-zA-z]+://[^\s]*");

	if($("#inValue0").val().length!=0){
		if(!patt_base64.test($("#inValue0").val())){
			alert("公钥证书不符合base64编码，请查证后重新输入！");
			return false;		
		}
	}
	
	if($("#inValue1").val().length!=0){
		if(!patt_URL.test($("#inValue1").val())){
			alert("支付结果通知的URL 不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}
	
	if($("#inValue2").val().length!=0){
		if(!patt_URL.test($("#inValue2").val())){
			alert("确认付款通知URL（结算）不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}
	
	if($("#inValue3").val().length!=0){
		if(!patt_URL.test($("#inValue3").val())){
			alert("退款通知的URL 不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}
	
	if($("#inValue4").val().length!=0){
		if(!patt_URL.test($("#inValue4").val())){
			alert("发送到银行通知URL 不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}

	if($("#inValue5").val().length!=0){
		if(!patt_URL.test($("#inValue5").val())){
			alert("查询订单详情的URL 不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}

	if($("#inValue8").val().length!=0){
		if(!patt_URL.test($("#inValue5").val())){
			alert("查询订单详情的URL 不符合URL格式，请查证后重新输入！");
			return false;		
		}
	}
	
	return true;
}





//页面validate
$(document).ready(function(){
	//为inputMemberCodeForm注册validate函数
	$("#inputMemberCodeForm").validate({
		rules: { 
			inputMemberCode:{
				required:true,
				digits:true,
				rangelength:[11,11]
				}
		},
		messages:{
			inputMemberCode:{
				required:"请输入商户会员memberCode（11位纯数字）",
				digits:"请输入商户会员memberCode（11位纯数字）",
				rangelength:"请输入商户会员memberCode（11位纯数字）"
			}
		}
	});
});
--></script>
</head>

<body>
	<br>
	<table width="25%" border="0" cellspacing="0" cellpadding="0"
		 height="21" style="">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18" >
			<div align="center"><font class="titletext"> 商 城 参 数 设 置</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>

	<table width="60%" border="0" cellspacing="0"  >
		<tr>
			<td>
				<form id="inputMemberCodeForm" name="inputMemberCodeForm" action="shoppingCityConfigSearch.do" method="post" >
					<table  class="border_all2 inputTable" width="95%" border="0" cellspacing="0"
						cellpadding="1" >
						<tr class="trForContent1">		
							<th class="border_top_right4 must"  style="width: 175px;"   >
								请输入商户会员memberCode：
							</th>
							<td class="border_top_right4" >
								<input type="text" id="inputMemberCode" name="inputMemberCode" value="${membercode}" style="width: 125px;"   >
							</td>
						</tr>
						<tr class="trForContent1" >	
							<td colspan="2" class="border_top_right4" align="center" >
								<input type="submit" value="查询">
							</td>						
						</tr>
					</table>
				</form>
			</td>
		</tr>
		<tr >
			<td  align="center">
				<c:if test="${empty memberCode}"> <strong>请输入商户会员memberCode进行查询.</strong></c:if>
				<c:if test="${!empty memberCode}"> <strong>当前商户会员memberCode为: ${memberCode}</strong></c:if>
				<br/><br/>
			</td >
		</tr>
		<tr>
			<td>		
				<form id="shoppingCityConfigFormBean" name="shoppingCityConfigFormBean" 
					action="shoppingCityConfigUpdate.do"  method="post">
					<input type="hidden" name="memberCode" value="${memberCode}"/>
					<table class="border_all2 inputTable" width="95%" border="0" cellspacing="0"
						cellpadding="1" >	
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								公钥证书  ：
							</th>
							<td class="border_top_right4">
								<input type="hidden" id="sccID0"   name="sccID"	  value="${ordLiShoppingCityConfigs[0].shoppingCityConfigId}">
								<input type="text"   id="inValue0" name="inValue" value="${ordLiShoppingCityConfigs[0].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								支付结果通知的URL ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID1"    name="sccID"   value="${ordLiShoppingCityConfigs[1].shoppingCityConfigId}"></input>
								<input type="text"   id="inValue1"  name="inValue" value="${ordLiShoppingCityConfigs[1].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								确认付款通知URL（结算） ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID2"    name="sccID" 	value="${ordLiShoppingCityConfigs[2].shoppingCityConfigId}"></input>
								<input type="text"	 id="inValue2"  name="inValue"  value="${ordLiShoppingCityConfigs[2].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								退款通知的URL ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID3"    name="sccID"  	value="${ordLiShoppingCityConfigs[3].shoppingCityConfigId}"></input>
								<input type="text"	 id="inValue3"  name="inValue"  value="${ordLiShoppingCityConfigs[3].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								发送到银行通知URL ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID4"    name="sccID"	value="${ordLiShoppingCityConfigs[4].shoppingCityConfigId}"></input>
								<input type="text" 	 id="inValue4"  name="inValue"  value="${ordLiShoppingCityConfigs[4].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								查询订单详情的URL ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID5"    name="sccID" 	value="${ordLiShoppingCityConfigs[5].shoppingCityConfigId}"  style="width:175px" />
								<input type="text"	 id="inValue5"  name="inValue"  value="${ordLiShoppingCityConfigs[5].value}" style="width: 400px;" />
							</td>
						</tr>		
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								银行是否直连 ：
							</th>
							<td class="border_top_right4" align="left" >
								<input type="hidden" id="sccID6"    name="sccID" 	value="${ordLiShoppingCityConfigs[6].shoppingCityConfigId}"  style="width:175px" />
								<select id="inValue6" name="inValue" size="1">
									<option value="true"
										<c:if test="${ordLiShoppingCityConfigs[6].value == 'ture'}" > selected</c:if>
									>true</option>
									<option value="false"
										<c:if test="${ordLiShoppingCityConfigs[6].value == 'false'}" >selected</c:if>
										<c:if test="${empty ordLiShoppingCityConfigs[6].value}" >selected</c:if>										
									>false</option>
								</select>	
							</td>		
						</tr>			
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								是否需要收银台验证用户 ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID7"    name="sccID" 	value="${ordLiShoppingCityConfigs[7].shoppingCityConfigId}"  style="width:175px" />
								<select id="inValue7" name="inValue" size="1">
									<option value="true"
										<c:if test="${empty ordLiShoppingCityConfigs[7].value}" >selected</c:if>		
										<c:if test="${ordLiShoppingCityConfigs[7].value == 'ture'}" > selected</c:if>							
									>true</option>
									<option value="false"
										<c:if test="${ordLiShoppingCityConfigs[7].value == 'false'}" >selected</c:if>
									>false</option>
								</select>	
							</td>
						</tr>			
						<tr class="trForContent1">
							<th class="border_top_right4 must"  style="width: 175px;"   >
								查询订单详情的URL ：
							</th>
							<td class="border_top_right4"  >
								<input type="hidden" id="sccID8"    name="sccID" 	value="${ordLiShoppingCityConfigs[8].shoppingCityConfigId}"  style="width:175px" />
								<input type="text"	 id="inValue8"  name="inValue"  value="${ordLiShoppingCityConfigs[8].value}" style="width: 400px;" />
							</td>
						</tr>			
						
						<tr class="trForContent1">		
							<td class="border_top_right4"  align="center"  colspan="2">
								<input type="button" value="确认修改"  onClick="javascript:processCommit(${memberCode})"
									<c:if test="${empty memberCode}"> disabled="disabled" </c:if>
								>
							</td>
						</tr>
					</table>	
				</form>
			</td>
		</tr>
	</table>
	
	<div id="resultListDiv" class="listFence"></div>		
	<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>
</body>

