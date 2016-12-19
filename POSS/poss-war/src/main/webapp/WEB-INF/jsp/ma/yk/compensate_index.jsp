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
	
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/ma/ykCompensate.do",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

function aplayRefund(orderNo) {
	if(! confirm("对订单"+orderNo+"申请退款吗?")){
		return false;
	}
	$('#refundWatingDiv').dialog( 
			{ 
			width:500,	
			modal:true, 	
			title:'订单'+orderNo+'退款中', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
	} );
	
	var pars ={"orderNo":orderNo,d:Math.random()}
	$.ajax({
		type: "POST",
		url: "${ctx}/ma/ykAplayRefund.do",
		data: pars,
		success: function(result) {
			if(result=="S"){
				setTimeout(function(){watingRefund(pars)},2500);
			}else{
				alert(result);
				$('#refundWatingDiv').dialog('close');
			}
			
		}
	});
} 
function watingRefund(pars){

		$.get("${ctx}/ma/ykQueryStatus.do",pars,function cbk2(status){
			if(status == 3){
				$('#refundWatingDiv').dialog('close');
				alert("退款成功");
				indexQuery();
				return;
			}
			if(status != 3){
				$('#refundWatingDiv').dialog('close');
				alert("退款需要一定的时间，请过一段时间再查询！");
				indexQuery();
				return;
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
		<div align="center"><font class="titletext">易 卡 充 值 补 单 管 理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean">
		<table class="searchTermTable" class="inputTable" style="width:80%" >
			<tr>
				<th >订单号：<input type="text" id="orderNo" name="orderNo" style="width: 150px;" maxlength="30" /></th>
				<th >16位易卡卡号：<input type="text" id="cardNo" name="cardNo" style="width: 150px;" maxlength="16" /></th>
			
				<th >充值状态：

					<select name="externalProcessStatus">
						<option value="">全部</option>
						<option value="3" >充值超时</option>
						<option value="1">充值成功</option>
						<option value="2">充值失败</option>
					</select>
				</th>
				
			</tr>
			<tr ><td height="10"></td>
			</tr>
			<tr>
					<td  colspan="2" style="text-align: center;  ">
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
<div id="refundWatingDiv" title="退款中" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;退款申请中，请等待...
</div>




</body>

