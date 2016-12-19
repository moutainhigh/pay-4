<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function specialMerchantCardQuery(pageNo,totalCount,pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#merchantSearchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize ;
	
	$.ajax({
		type: "POST",
		url: "${ctx}/spCardInfoList.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function queryCard() {
	specialMerchantCardQuery();
}

function AddCard(merchantId){
	$("#modifyForm :input").val([""]);//清空
	$('#modifyBindDiv').dialog({ 
		width:500,
		modal:true, 	
		title:'新增配置', 
		overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
		buttons:{ 
			'确定':function(){ 
					$.post("${ctx}/spCardInfoAdd.do",$("#modifyForm").serialize()+"&spMerchantId="+merchantId
						,function (msg){
								if(msg=="S"){
									specialMerchantCardQuery();
									$('#modifyBindDiv').dialog("close");
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
function modifyCard(cardIndexId){
	$.getJSON("${ctx}/spCardInfoDetail.do",{"id":cardIndexId,"htmlType":"json"},function(result){
		 for(x in result){
			$(modifyForm[x]).val([result[x]+""]);
		} 
		var actionUrl = "${ctx}/spCardInfoModify.do";
		$('#modifyBindDiv').dialog({ 
			width:500,
			modal:true, 	
			title:'修改配置', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
			buttons:{ 
				'确定':function(){
						$.post("${ctx}/spCardInfoModify.do",$("#modifyForm").serialize()+"&spMerchantCardId="+cardIndexId
							,function (msg){
									if(msg=="S"){
										specialMerchantCardQuery();
										$('#modifyBindDiv').dialog("close");
									}
									else{
										alert(msg);
										$('#modifyBindDiv').dialog("close");
									}
							}
						);
				}, 
				'取消':function(){ 
					$("#modifyBindDiv").dialog("close");
				} 
				} 
		}); 
	});
}
function removeCard(cardIndexId){
	$('#unBindDiv').dialog({ 
				width:500,
				height:150,
				modal:true, 	
				title:'删除配置信息', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
				buttons:{ 
					'确定':function(){ 
						
							$.post("${ctx}/spCardInfoRemove.do",{spMerchantCardId:cardIndexId}
								,function (msg){
										if(msg=="S"){
											specialMerchantCardQuery();
											$('#unBindDiv').dialog("close");
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
function cancel(merchantId){
	location.href = "${ctx}/specialMerchantInit.do";
}
$(document).ready(function(){queryCard();});
</script>
</head>

<body>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">特约商户支持卡种信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form id="merchantSearchFormBean" name="merchantSearchFormBean" >
<input type="hidden" name="spMerchantId" value="${spmerchant.sp_merchant_id}" />
<br>
<table  width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr>
		<td  align="left" >特约商户名称:${spmerchant.sp_merchant_name}</td>
		<td align="right"  colspan="6">
		      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="queryCard();">
		      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="AddCard(${spmerchant.sp_merchant_id});">
		      </td>
	</tr>
</table>

</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

<div id="modifyBindDiv" style="display: none;">
	<form action="" id="modifyForm">
	<table class="inputTable"  border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th width="100" nowrap="nowrap">卡种名称：</th>
		<td align="left">
			<select id="cardTypeId" name="cardTypeId">
			<option value="" selected>---请选择---</option>
				<c:forEach items="${spEnumInfoList}" var="spEnumInfo">
					<option value="${spEnumInfo.enumCode}"<c:if test="${spEnumInfo.enumCode == query_spRangId}"> selected="selected" </c:if>>${spEnumInfo.enumName}</option>
				</c:forEach>
			</select>
			<!-- <input type="text" name="card_type_id" maxlength="10"  til="卡种名称"/></span> -->
		</td>
	</tr>
	<tr>
		<th width="100" nowrap="nowrap">折扣信息：</th>
		<td align="left">
			<input type="text" name="discountInfo"  id="discountInfo"  title="折扣信息"/></span>
		</td>
	</tr>
	</table>
	<input type="hidden" name="id"> 
	</form>
</div>

<div id="unBindDiv" style="display: none">
		<span style="font-size:  15pt">确定要删除此记录吗？</span>
</div>

<div id="detailDiv" style="display: none; ">
	
</div>
</body>

