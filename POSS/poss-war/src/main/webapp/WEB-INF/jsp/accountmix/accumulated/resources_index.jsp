<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function validateQuery(){
	var reg = /^\d{0,20}$/;
	var inputs = $("#searchFormBean :input");
	
	for(var i=0;i<inputs.length;i++){
		var input = inputs.get(i);
		if(! reg.test($(input).val())){
			alert($(input).attr("title")+"必须是数字");
			return false;
		}
	}
	
	return true;
}

function validateAdd(){
	var reg = /^\d{1,20}$/;
	var inputs = $("#modifyForm input[type=text]");
	for(var i=0;i<inputs.length;i++){
		var input = inputs.get(i);
		if(! reg.test($(input).val())){
			alert($(input).attr("title")+"必须是数字，并且不能为空");
			return false;
		}
	}
	
	var takeOn = $("#modifyForm :radio[name=takeOn][checked]").val();
	
	if( !(takeOn==1|| takeOn==2) ){
		alert("收款方或付款式必须选择");
		return false;
	}

	return true;
}

function indexQuery(pageNo,totalCount,pageSize) {
	
	if(! validateQuery()){
		return false;
	}
	$('#infoLoadingDiv').dialog('open');
	
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss/accum_resources.do?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 



function addAccuRes(){

		$("#modifyForm :input").val([""]);//清空
		
		$('#modifyBindDiv').dialog({ 
			width:500,
			modal:true, 	
			title:'新增配置', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
			buttons:{ 
				'确定':function(){ 
						
					if(!validateAdd()){
						return;
					}
						
						$.post("${ctx}/if_poss/accum_resources.do?method=creareRes",$("#modifyForm").serialize()
							,function (msg){
									if(msg=="S"){
										indexQuery();
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

function modifyRes(operId,isCopy){
	$.getJSON("${ctx}/if_poss/accum_resources.do",{"method":"queryDetail","id":operId,"htmlType":"json"},function(result){
		for(x in result){
			$(modifyForm[x]).val([result[x]+""]);
		}
		//
		var actionUrl = "${ctx}/if_poss/accum_resources.do?method=modifyRes";
		if(isCopy!=null && isCopy){
			$(modifyForm["id"]).val([""]);
			 actionUrl = "${ctx}/if_poss/accum_resources.do?method=creareRes";
		}
		$('#modifyBindDiv').dialog({ 
			width:500,
			modal:true, 	
			title:(!isCopy)?'修改配置':"新增配置", 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
			buttons:{ 
				'确定':function(){ 
					
					if(!validateAdd()){
						return;
					}
						$.post(actionUrl,$("#modifyForm").serialize()
							,function (msg){
									if(msg=="S"){
										indexQuery();
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
		
		
	});
						
}

function copyAddRes(operId){
	modifyRes(operId,true);
}


function removeRes(operId){
	$('#unBindDiv').dialog({ 
				width:500,
				height:150,
				modal:true, 	
				title:'删除配置信息', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
				buttons:{ 
					'确定':function(){ 
						
							$.post("${ctx}/if_poss/accum_resources.do?method=removeAccRes",{id:operId}
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
		<div align="center"><font class="titletext">累计资源配制表管理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean" style="width: 100%;text-align: center">
		<table class="searchTermTable" class="inputTable" style="width:60%;margin: 0px auto" >
			<tr>
				<th style="text-align: right">订单类型：</th>
				<td style="text-align: left"><input type="text" id="orderType" name="orderType" title="订单类型" style="width: 150px;" maxlength="40"/></td>
				<th style="text-align: right">支付方式：</th>
				<td style="text-align: left"><input type="text" id="dealCode" name="dealCode"  titile="支付方式" style="width: 150px;" maxlength="40" /></td>
				
			</tr>
			<tr>
				<th style="text-align: right">交易类型：</th>
				<td style="text-align: left"><input type="text" id="dealType" name="dealType"  title="交易类型" style="width: 150px;" maxlength="40" /></td>
				<th style="text-align: right">支付服务代码：</th>
				<td style="text-align: left"><input type="text" id="paymentServiceCode"  title="支付服务代码" name="paymentServiceCode" style="width: 150px;" maxlength="40"/></td>
			</tr>
			
			<tr>
				<td  colspan="4" style="text-align: center;  ">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="addAccuRes()">
			<span class="ui-icon ui-icon-newwin" ></span>&nbsp;新&nbsp;&nbsp;增&nbsp;
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

	
<div id="modifyBindDiv" style="display: none;">
	<form action="" id="modifyForm">
	<table class="inputTable"  border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>
		<th width="100" nowrap="nowrap">订单类型：</th>
		<td align="left">
			<input type="text" name="orderType" maxlength="3"  til="订单类型"/></span>
		</td>
	</tr>
	<tr>
		<th width="100" nowrap="nowrap">支付方式：</th>
		<td align="left">
			<input type="text" name="dealCode"  maxlength="3"  title="支付方式"/></span>
		</td>
	</tr>
	<tr>
		<th width="100" nowrap="nowrap">交易类型：</th>
		<td align="left">
			<input type="text" name="dealType" maxlength="3" title="订单类型" /></span>
		</td>
	</tr>
	<tr>
		<th width="100" nowrap="nowrap">支付服务代码：</th>
		<td align="left">
			<input type="text" name="paymentServiceCode"  maxlength="10" title="支付服务代码" /></span>
		</td>
	</tr>
	<tr>
		<th width="100" nowrap="nowrap">作用方：</th>
		<td align="left">
			<input type="radio" name="takeOn" value="1"  title="付款方"/> 付款方   <input type="radio" name="takeOn" value="2"  title="收方式" /><font color="red">收款方</font></span>
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

