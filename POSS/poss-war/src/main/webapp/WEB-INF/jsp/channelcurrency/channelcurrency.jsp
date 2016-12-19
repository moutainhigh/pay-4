<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">渠道币种规则管理</h2>
<h2><font color="red">${error}</font></h2>
<form action="channel_currency_management.do" method="post" id="channelCurrencyConf">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="center" class="border_top_right4">机构： <select
				style="width: 120px; height: 20px;" id="mechanism"
				onclick="mechanismSelected();">
					<option value="">--请选择--</option>
					<c:forEach items="${result}" var="me">
						<option value=${me.orgCode}>${me.name}</option>
					</c:forEach>
			</select> <input type="hidden" name="mechanism" id="mechanism2">
			</td>
			<td align="center" class="border_top_right4">交易币种： <input
				type="text" id="tradeCurrency2" onclick="tradeCurrency();"
				name="tradeCurrency2">
			</td>
			<td align="center" class="border_top_right4">提交渠道币种： 
			<input type="text" id="channelCurrency" name="channelCurrency" >
			<input type="hidden" id="channelCurrency1" name="channelCurrency1">
			</td>
			<td align="center" class="border_top_right4">会员号： <input
				type="text" onkeyup="checkNum(this);" name="partnerId">
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" colspan="4" class="border_top_right4">
			<input type="button" onclick="deleteChannelAll();" value="删除">
			&nbsp;&nbsp;	<input type="button" onclick="addChannelCurrency();" value="添加">
				&nbsp;&nbsp; <input type="button" onclick="findChannelCurrencyList();" value="查询">
				</td>
		</tr>
	</table>
	<input type="hidden" name="method" value="AddChannelCurrency">
</form>

<div id="tradeCurrencyDiv" style="display: none">
	<form action="" id="tradeCurrencyFrom">
		<h3 align="left">请选择交易币种:</h3>
		<table id="tradeCurrencyTable">
			<tr></tr>

		</table>
		<br> <br>
		<br>
		<br>
		<input type="button" onclick="commit();" value="确定">&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancel();" value="取消">&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="checkAll();" value="全选">
	</form>
</div>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
	
	var globalData;
	function findChannelCurrencyList(pageNo, totalCount){
		var channelCurrency=$("#channelCurrency").val();
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
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}

	function addChannelCurrency(){
		var channelCurrency=$("#channelCurrency").val();
		 for(var k in globalData){
			if(channelCurrency==k){
				$("#channelCurrency1").attr("value",k);							
			}
		 }
		 var mechanism2=$("#mechanism2").val();
		 if(mechanism2==""){
			 alert("请选择机构");
			 return;
		 }
		 var tradeCurrency2=$("#tradeCurrency2").val();
		 if(tradeCurrency2==""){
			 alert("请选择币种");
			 return;
		 }
		 var channelCurrency1=$("#channelCurrency1").val();
		 if(channelCurrency1==""){
			 alert("请输入渠道币种");
			 return;
		 }
	  $("#channelCurrencyConf").submit();
	}


	 $(document).ready(function() {
		 findChannelCurrencyList();
	});

	function checkAll() {
		var checkbox = $("#tradeCurrencyFrom input[name='tradeCurrency']").is(':checked');
		if (checkbox) {
			$("#tradeCurrencyFrom input[name='tradeCurrency']").removeAttr("checked");
		} else {
			$("#tradeCurrencyFrom input[name='tradeCurrency']").attr("checked", "checked");
		}
	}

	function mechanismSelected() {
		var mechanism = $('#mechanism option:selected').val();
		$('#mechanism2').attr("value", mechanism);
	}

	function cancel() {
		$('#tradeCurrency2').attr('value','');
		$('#tradeCurrencyTable').empty();
		$('#tradeCurrencyDiv').dialog("close");
	}
	function commit() {
		var currency = "";
		var currency1 ="&";
		$("input:checked").each(function() {
			currency += $(this).val() + " ";
			currency1 += $(this).val() +"&";
		});
		$("#tradeCurrency2").attr("value", currency);
		$("#tradeCurrency1").attr("value", currency1);
		$('#tradeCurrencyTable').empty();
		$('#tradeCurrencyDiv').dialog("close");
	}
	function tradeCurrency() {
		$.ajax({
			type : "POST",
			url : "channel_currency_management.do?method=getTradeCurrency",
			success : function(result) {
				result = JSON.parse(result);
				$("#tradeCurrencyTable").append("<tr></tr>");
				var index=1;
				for(var key in result){
					if (index % 4 == 0) {
						$("#tradeCurrencyTable tr:last-child").append(
								"<td><input name='tradeCurrency' value="+key+" type='checkbox' />"
										+ result[key] + "&nbsp&nbsp&nbsp;</td>");
						$("#tradeCurrencyTable").append("<tr></tr>")
					} else {
						$("#tradeCurrencyTable tr:last-child").append(
								"<td><input name='tradeCurrency' value="+key+" type='checkbox' />"
										+ result[key] + "&nbsp&nbsp&nbsp;</td>");
					}
					index++;
				}
			}
		});
		$('#tradeCurrencyTable').empty();
		$('#tradeCurrencyDiv').dialog({
			position : "top",
			width : 550,
			modal : true,
			title : '选择币种',
			height : 400,
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}
		});
	}
	
	$(function() {
		  $.ajax({
			type : "POST",
			url : "channel_currency_management.do?method=getTradeCurrency",
			success : function(result) { 
			globalData = JSON.parse(result);
				var data=[];
				for(var k in globalData){
					data.push(k);
				}
				$("#channelCurrency").autocomplete({
					source : data
				});
			}
		}); 
	});
	
	function deleteChannelAll(){
		var deleteId="";
		$("input:checked").each(function(){
			deleteId+=$(this).val()+",";
		});
		if(deleteId==""){
			alert("请选择删除的币种规则");
			return;
		}
		 if (!confirm("确认要删除？")) {
	      return;
	     }
		 $.ajax({
				type : "POST",
				url : "${ctx}/channel_currency_management.do?method=deleteChannel&deleteId="+deleteId,
				success : function() { 
					 location.replace(location.href);
				}
			});
	}
</script>