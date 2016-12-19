<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
		function(){
			indexQuery();
		}
);

function indexQuery(pageNo,totalCount,pageSize) {
	
	var memberCode = $("#memberCode").val();
	if(memberCode.length >0 && !(/\d{1,20}/.test(memberCode))){
		alert("账号必须是数字");
		return false;
	}
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/frozenLog.do?method=searchFrozenLog",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function addFrozenLog(){	
	if($("#memberCode").val()===""){
		alert("账号不能为空!");	
		return ;
	}	
	//修改loginName 为memberCode  2016年6月2日16:39:26 by tom
	$("#nameOrCode").val($("#memberCode").val());
	$("#mountDiv").text("");
	$("#amount").val("");
	$("#desc").val("");
	$('#addLogDiv').dialog( 
			{ 
				modal:true, 		
			autoOpen:true,	
			height:300, 
			width:500, 
			title:'冻结确认', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto'}, 
			buttons:{ 
			'确定':function(){ 
				if(!checkDescBlank())//冻结原因不能为空 add by davis.guo at 2016-08-25
				{
					alert("冻结原因不能为空!");
					return false;
				}
				if(!checkDescLength()){
					alert("描述输入字数不能超过90个字");
					return ;
				}
				if(checkAmount()){				
					//memberCode					
					$.post("${ctx}/frozenLog.do?method=addFrozenLog",{nameOrCode:$("#nameOrCode").val(),amount:$("#amount").val(),desc:$("#desc").val(),acctType:$("#acctType").val()}
						,function (msg){
								if(msg=="S"){
									indexQuery();
									$('#addLogDiv').dialog("close")
								}
								else{
									alert(msg);
								}
						}
					);
				}
			}, 
			'取消':function(){ 
				$("#addLogDiv").dialog("close");
			} 
			} 
} );}

function showFrozenDetail(id){
	$('#detailDiv').load("${ctx}/frozenLog.do?method=getFrozenDetial",{id:id},function(msg){
		});
	

		$('#detailDiv').dialog( 
				{ 
				width:500,	
				modal:true, 	
				title:'详细页面', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
				
				
		} );

}


function freeFrozenLog(memberCode,id,seariNo,amout,acctType){
	if(!confirm("确定对流水号"+seariNo+"，金额为"+amout+"解冻吗？"))
		return ;	
	$.post("${ctx}/frozenLog.do?method=freeFrozenLog",{memberCode:memberCode,id:id,acctType:acctType}
	,function (msg){
		if(msg=="S"){
			indexQuery();
		}
		else{
			alert(msg);
		}
		}
);
}

function getBlance(nameOrCode,acctType){
	if(nameOrCode.length>=0){
		$("#mountDiv").load("${ctx}/frozenLog.do?method=searchBlance",{"nameOrCode":nameOrCode,"acctType":acctType,"format":1});

		$.post("${ctx}/frozenLog.do?method=searchBlance",{"nameOrCode":nameOrCode,"acctType":acctType},function (msg){$("#blance").val(msg)});
	}
	
	
}
function checkAmount(){
	$("#amount").val($.trim($("#amount").val()));
	value = $("#amount").val();
	if(value.length>0 && isNaN(value)){
		alert("输入数字，并且不能为负数")	
		return false;		
	}
	var balnce = $("#blance").val();
	
	var regDouble = /^\d+(\.\d{1,2})?$/;
	if(! regDouble.test(value) ){
		alert("最多只保留两位有效数字");
		$("#amount").css({color:'red'});	
		return false;	
	}
	
	if(balnce-value < 0){
		alert("输入的冻结金额超过当前可用金额")
		$("#amount").css({color:'red'});	
		return false;	
	}

	
	return true;
}
function checkAmountValue(){
	$("#amount").val($.trim($("#amount").val()));
	value = $("#amount").val();
	if(value.length>0 && isNaN(value)){
		$("#amount").css({color:'red'});
		return false;
	}
	var balnce = $("#blance").val();

	var regDouble = /^\d+(\.\d{1,2})?$/;
	if(! regDouble.test(value) ){
		$("#amount").css({color:'red'});	
		return false;	
	}
	
	if(balnce-value < 0){
		$("#amount").css({color:'red'});	
		return false;	
	}
	$("#amount").css({color:''});
	return true;
}
function checkDescLength(){
	var desc = $("#desc").val();
	if(desc.length>90){
		$("#desc").css({color:'red'});
		return false;
	}
	$("#desc").css({color:''});
	return true;
}

//冻结原因不能为空 add by davis.guo at 2016-08-25
function checkDescBlank(){
	var desc = $("#desc").val();
	if(desc.length==0){
		$("#desc").css({color:'red'});
		return false;
	}
	$("#desc").css({color:''});
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
		<div align="center"><font class="titletext">资 金 冻 结 管 理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table> -->
<h2 class="panel_title">资金冻结管理</h2>

	<form id="searchFormBean">
		
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<th class="border_top_right4" >账号：<input type="text" id="memberCode" name="memberCode" style="width: 150px;"/></th>
				<th class="border_top_right4">操作类型：
					<select name="frozenType">
						<option value="">全部</option>
						<option value="1">冻结</option>
						<option value="2">解冻</option>
					</select>
				</th>
				<th class="border_top_right4" >状态：

					<select name="status">
						<option value="">全部</option>
						<option value="1" selected="selected">成功</option>
						<option value="0">失败</option>
						<option value="2">已解冻</option>
					</select>
				</th>
				
			</tr>
			<tr class="trForContent1"><td align="center"  class="border_top_right4" >
			      账户类型：

					<select name="acctType" id="acctType">
					    <c:forEach items="${acctType}" var="obj">
						  <option value="${obj.code}">${obj.displayName}(${obj.currency})</option>
					   </c:forEach>
					</select>
			</td>
			<td class="border_top_right4" ></td>
			<td class="border_top_right4" ></td>
			</tr>
			<tr class="trForContent1">
					<td  class="border_top_right4" colspan="4" style="text-align: center;  ">
					<a href="#" class="" onclick="indexQuery()">
						<input class="button2" type="button" value="查询">
					</a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:void(0)" onclick="addFrozenLog()" class="">
						<input class="button2" type="button" value="添加冻结">
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
<div id="addLogDiv" style="display: none">
	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>	
		<th><span class="must">*</span>会员号/登录名</th>
		<td >
			<input type="text" name="nameOrCode" readonly="readonly" id="nameOrCode" onblur="getBlance($('#memberCode').val(),$('#acctType').val())" />
		</td>
	</tr>
	
	<tr>
		<th >可用金额(￥)</th>
		<td >
			<div id="mountDiv" style="font-size: 16px">0</div>
			<input type="hidden" name="blance" id="blance"  />
		</td>
	</tr>
	<tr>
		<th><span class="must">*</span>冻结金额(￥)</th>
		<td>
			<input type="text" name="amount" id="amount" onkeyup="checkAmountValue()" 　 />
		</td>
	</tr>
	<tr>
		<th><span class="must">*</span>备注</th>
		<td >
			<textarea name="desc" id="desc" onkeyup="checkDescLength()" style="width:250px;height: 40px"/></textarea>
		</td>
	</tr>
	</table>
</div>

<div id="detailDiv" style="display: none;width: 500px;height: 500px ">
	
</div>

</body>

