<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


$(function(){
	$("#type").change(function(){
		$("#typeValue").val("");
	});
})

function indexQuery() {
	
	var type = $(":selected").val();
	var typeValue = $("#typeValue").val();
	if(typeValue==null||typeValue.length==0){
		alert("请输入值!");
		return false;
	}
	if(type=='2'){
	  if(typeValue.length >0 && !(/^\d+$/.test(typeValue))){
		alert("会员号必须是数字");
		return false;
	  }
    }	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#searchFormBean").serialize() +"&type=" + type + "&typeValue=" + typeValue; 
	$.ajax({
		type: "POST",
		url: "${ctx}/artificialfrozenBalance.do?method=searchFrozenBalance",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 


function showFrozenBalanceDetail(id,isfrozenAmount){
	$('#detailDiv').load("${ctx}/artificialfrozenBalance.do?method=getFrozenBalanceDetail",{id:id,isfrozenAmount:isfrozenAmount},function(msg){
		});
	

		$('#detailDiv').dialog( 
				{ 
				width:500,	
				modal:true, 	
				title:'详细页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
				
				
		} );

}


function freeFrozenBalance(memberCode,acctCode,balance,frozenAmount,isfrozenAmount){
	$("#memberCodeDiv").text(memberCode);
	$("#memberCode").val(memberCode);
	$("#balanceDiv").text(balance/1000);
	$("#frozenAmountDiv").text(frozenAmount/1000);
	$("#frozenAmount").val(frozenAmount/1000);
	$("#acctCodeDiv").text(acctCode);
	$("#acctCode").val(acctCode);
	$("#unFrozenAmount").val("");
	$("#isunFrozenAmountDiv").text(isfrozenAmount/1000);
	$("#isunFrozenAmount").val(isfrozenAmount/1000);
	$('#freeBalanceDiv').dialog( 
			{ 
				modal:true, 		
			autoOpen:true,	
			height:170, 
			width:500, 
			title:'解冻信息', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
				 if(checkAmount()){
					$.post("${ctx}/artificialfrozenBalance.do?method=freeFrozenBalance",{memberCode:$("#memberCode").val(),acctCode:$("#acctCode").val(),unFrozenAmount:$("#unFrozenAmount").val()}
						,function (msg){
								if(msg=="S"){
									indexQuery();
									$('#freeBalanceDiv').dialog("close")
								}
								else{
									alert(msg);
								}
						}
					);
				}
			}, 
			'取消':function(){ 
				$("#freeBalanceDiv").dialog("close");
			} 
			} 
} );}

function checkAmount(){
	
	$("#unFrozenAmount").val($.trim($("#unFrozenAmount").val()));
	value = $("#unFrozenAmount").val();
	var reg = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
	if(value.length>0 && !reg.test(value)){
		alert("输入数字，只能是非负数且小数位后最多2位有效数字")	
		return false;		
	}

	var frozenAmount = $("#isunFrozenAmount").val();

	var regDouble = /^\d+(\.\d{1,2})?$/;
	if(! regDouble.test(value) ){
		alert("最多只保留两位有效数字");
		$("#unFrozenAmount").css({color:'red'});	
		return false;	
	}
	
	if(frozenAmount-value < 0){
		alert("输入的解冻金额超过当前可解冻金额 ")
		$("#unFrozenAmount").css({color:'red'});	
		return false;	
	}	
	return true;
}

function checkAmountValue(){
	$("#unFrozenAmount").val($.trim($("#unFrozenAmount").val()));
	value = $("#unFrozenAmount").val();
	var reg = /^(([1-9]+)|([0-9]+\.[0-9]{1,2}))$/;
	if(value.length>0 && !reg.test(value)){
		$("#unFrozenAmount").css({color:'red'});
		return false;
	}
	var frozenAmount = $("#isunFrozenAmount").val();

	var regDouble = /^\d+(\.\d{1,2})?$/;
	if(! regDouble.test(value) ){
		$("#unFrozenAmount").css({color:'red'});	
		return false;	
	}
	
	if(frozenAmount-value < 0){
		$("#unFrozenAmount").css({color:'red'});	
		return false;	
	}
	$("#unFrozenAmount").css({color:''});
	return true;
}


</script>
</head>

<body>

<!-- <table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">人 工 解 冻 管 理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->
<h2 class="panel_title">人工解冻管理</h2>

	<form id="searchFormBean">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<th class="border_top_right4" >请选择：
				   <select name="type" id="type" style="width: 100px;">
						<option value="1">登陆名</option>
						<option value="2">会员号</option>
					</select>
				</th>
				<th  class="border_top_right4">请输入值：<input type="text" id="typeValue" name="typeValue" style="width: 150px;" maxlength="20"/></th>	
			</tr>
			<tr class="trForContent1">
					<td  class="border_top_right4" colspan="4" style="text-align: center;  ">
					<a href="#" class="" onclick="indexQuery()">
						<input class="button2" type="button" value="查询">
					</a>
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
<div id="freeBalanceDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>	
		<th>会员号</th>
		<td >
		    <div id="memberCodeDiv" style="font-size: 16px"></div>
			<input type="hidden" name="memberCode" id="memberCode"  />
		</td>
	</tr>
	<tr>
		<th >账户号</th>
		<td >
			<div id="acctCodeDiv" style="font-size: 16px"></div>
			<input type="hidden" name="acctCode" id="acctCode"  />
		</td>
	</tr>
		<tr>
		<th >可用金额(￥)</th>
		<td >
			<div id="balanceDiv" style="font-size: 16px"></div>
			<input type="hidden" name="balance" id="balance"  />
		</td>
	</tr>
	<tr>
		<th >冻结金额(￥)</th>
		<td >
			<div id="frozenAmountDiv" style="font-size: 16px"></div>
			<input type="hidden" name="frozenAmount" id="frozenAmount"  />
		</td>
	</tr>
	<tr>
		<th  style="color: green;">可解冻金额(￥)</th>
		<td >
			<div id="isunFrozenAmountDiv" style="font-size: 16px;color: green;"></div>
			<input type="hidden" name="isunFrozenAmount" id="isunFrozenAmount"  />
		</td>
	</tr>
	<tr>
		<th><span class="must">*</span>解冻金额(￥)</th>
		<td>
			<input type="text" name="unFrozenAmount" id="unFrozenAmount" onkeyup="checkAmountValue()"/>
		</td>
	</tr>
	</table>
</div>

<div id="detailDiv" style="display: none;width: 500px;height: 500px ">
	
</div>

</body>

