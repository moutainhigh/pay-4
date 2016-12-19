<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function findChannelCurrencyList(pageNo, totalCount){
	$("#channelCurrency1").removeAttr("value");
	var channelCurrency=$("#channelCurrency").val();
	//alert(channelCurrency);
	 for(var k in globalData){
		if(channelCurrency==k){
			$("#channelCurrency1").attr("value",k);							
		}
	 }
  	$('#infoLoadingDiv').dialog('open');
	var pars = $("#channelCurrencyConf").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./channel_currency_management.do?method=findChannelCurrencyPage",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
function checkAll1(){
	var checkbox = $("#check").is(':checked');
	if (checkbox) {
		$("#Form input[name='checkbox']").attr("checked","checked");
	} else {
		$("#Form input[name='checkbox']").removeAttr("checked");
	}
}
function deleteChannel(ele){
	 if (!confirm("确认要删除？")) {
		return;
	 }
		var deleteId=""
		if(deleteId==""){
			var html=$(ele).parent().parent();
			 deleteId=html.find("td:eq(0)").find("input").val();
		}
	$.ajax({
		type : "POST",
		url : "${ctx}/channel_currency_management.do?method=deleteChannel&deleteId="+deleteId,
	});
}
 function editChannel(ele){
	 $('#editChannelTable').empty();
	var html=$(ele).parent().parent();
	var id=html.find("td:eq(0)").find("input").val();
	var name=html.find("td:eq(1)").html();
	var partnerId=html.find("td:eq(2)").html();
	var currencyCode=html.find("td:eq(3)").html();
	var orgCurrencyCode=html.find("td:eq(4)").html();
			 
	$("#editChannelTable").append("<input name='id' type='hidden' value="+id+">");
	
	$("#editChannelTable").append("<tr><td >提交渠道币种:<input  type='text'  id='orgCurrencyCode1' name='orgCurrencyCode1'>"+
		"</input></td><td >&nbsp;&nbsp;")
	
	var data=[];
	for(var k in globalData){
		if(k==orgCurrencyCode){
			$("#orgCurrencyCode1").attr("value",k);
		}
		data.push(k);
	}
	$("#orgCurrencyCode1").autocomplete({
		source : data
	});
	
	
	$('#editChannelDiv').dialog({
		position : "top",
		width : 450,
		modal : true,
		title : '选择币种',
		height : 300,
		overlay : {
			opacity : 0.5,
			background : "black",
			overflow : 'auto'
		}
	}); 
} 
 function cancel1() {
		$('#editChannelTable').empty();
		$('#editChannelDiv').dialog("close");
	}
 
 function editChannel1(){
	 var data=[];
	var orgCurrencyCode=$("#orgCurrencyCode1").val();
		for(var k in globalData){
			if(k==orgCurrencyCode){
				$("#orgCurrencyCode").attr("value",k);
			}
		}
	$("#channelCurrencyForm").submit();
 }
</script>
<div id="editChannelDiv" style="display: none">
	<h3>渠道币种规则管理</h3>
	<form action="./channel_currency_management.do" id="channelCurrencyForm">
		<input type="hidden" name="method" value="editChannel"><br><br>
		<input type='hidden' id='orgCurrencyCode' name='orgCurrencyCode'>
		<table  id="editChannelTable" >


		</table>
		<br> <br>
		<br>
		<br>
		<input type="button" onclick="editChannel1();" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancel1();" value="取消">&nbsp;&nbsp;&nbsp; 
	</form>
</div>



<body>
	<form action="" id="Form">
	<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
		<thead><tr class="">
			<th align="center" class="">
			<input type="checkbox" id="check" value="," onclick="checkAll1();">
			全选</th>
			<th align="center" class="">机构名称</th>
			<th align="center" class="">会员号</th>
			<th align="center" class="">交易币种</th>
			<th align="center" class="">提交渠道币种</th>
			<th align="center" class="">操作人</th>
			<th align="center" class="">操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${channelCurrencys}" var="channel" >
		<tr class="trForContent1">
			<td align="center" class="">
			<input type="checkbox" name="checkbox" value="${channel.id}">
			选择</td>
				<td align="center" class="">
					<c:choose>
					<c:when test="${channel.orgCode=='0000000'}">测试通道</c:when>
					<c:when test="${channel.orgCode=='10076001'}">中银卡司</c:when>
					<c:when test="${channel.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${channel.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${channel.orgCode=='10002001'}">农业银行</c:when>
					<c:when test="${channel.orgCode=='10081016'}">前海万融</c:when>
					<c:otherwise>未知机构</c:otherwise>
					</c:choose>
				</td>
			<td align="center"  class="">${channel.partnerId}</td>
			<td align="center"  class="">${channel.currencyCode}</td>
			<td align="center" class="">${channel.orgCurrencyCode}</td>
			<td align="center" class="">${channel.operator}</td>
			<td align="center" class="">
			<a href="#" onclick="editChannel(this)">编辑</a>
			<a href="" onclick="deleteChannel(this);" >删除</a> 
			</td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	<br><br><br><br>
	<table align="center">	<tr>
			<td colspan="13" align="center"><li:pagination methodName="findChannelCurrencyList" pageBean="${page}" sytleName="black2"/></td>
		</tr>
		</table>