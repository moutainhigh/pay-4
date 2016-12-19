<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">

	function add() {

		var memberCode = $("#memberCode").val();
		var orgCode = $("#orgCode").val().split("-")[0];
		var orgMerchantCode = $("#orgMerchantCode").val();
		var cardOrg = $("#cardOrg").val();
		
		if('' == memberCode){
			alert("请输入会员号！");
			return;
		}
		
		if('' == orgCode){
			alert("请选择机构！");
			return;
		}
		
		if('' == orgMerchantCode){
			alert("请输入二级商户号！");
			return;
		}
		
		window.location.href="${ctx}/second_merchant_relation.htm?method=add&memberCode="+memberCode+"&orgCode="+orgCode+"&orgMerchantCode="+orgMerchantCode+"&payChannelItemId="+$("#payChannelItemId").val()+"&cardOrg="+cardOrg;
	}

	function changeItem(data){
		var pars = "";
		var orgCode=data.split("-")[0];
		var cardOrg=data.split("-")[2];
		$("#cardOrg").val(cardOrg);
		$("#payChannelItemId").val(data.split("-")[1]);
		$.ajax({
			type: "POST",
			url: '${ctx}/second_merchant_relation.htm?method=queryChannelConfigItem&orgCode='+orgCode,
			data: pars,
			dataType: "json",
			success: function(res) {
				//var dataObj = eval("("+res+")");//转换为json对象
				var str = "";
				if(null != res){
					$.each(res,function(idx,item){
						if('10075001' == orgCode){
							str = str + '<option value="'+ item.id+ "," + item.orgMerchantCode +'" selected>'+ item.orgMerchantCode + "--" + item.transType + "--" + item.currencyCode + "--" + item.mcc +'</option>';
						}else{
							str = str + '<option value="'+ item.id+","+ item.orgMerchantCode +'" selected>'+ item.orgMerchantCode + "--" + item.transType +'</option>';
						}
						
					});
				}else{
					str = '<option value="" selected>---请选择---</option>';
				}
				
				$('#orgMerchantCode').html(str);
			}
		});
	}
	
</script>

</head>

<body>
<h2 class="panel_title">通道二级商户号添加</h2>
<c:if test="${! empty errorMsg}">
				<p>
				<font color="red">${errorMsg}</font></p>
			</c:if>
<table class="border_all2" width="30%" border="0" cellspacing="0" cellpadding="1" align="center">
	<form id="testjop1" action="${ctx}/second_merchant_relation.htm?method=add" method="post">
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">会员号</td>
				<td align="left" class="border_top_right4"><input type="text" name="memberCode" id="memberCode" style="width:150px;"/></td>
			</tr>
			<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">通道名称</td>
				<td align="left" class="border_top_right4">
					<select id="orgCode" name="orgCode" style="width:150px;" onchange="changeItem(this.value);">
						<option value="" selected>---请选择---</option>
						<c:forEach var="item" items="${channelItems}" varStatus="v">
							<option value="${item.orgCode}-${item.id}-${item.paymentCategoryCode}">${item.name}</option>
						</c:forEach>
					</select>
					<input type="hidden" id="payChannelItemId"/>
					<input type="hidden" id="cardOrg" name="cardOrg"/>
				</td>
			</tr>
			<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">机构二级商户号</td>
				<td align="left" class="border_top_right4">
					<select id="orgMerchantCode" name="orgMerchantCode" style="width:150px;">
						<option value="" selected>---请选择---</option>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="2" align="center">
					<a class="s03" href="javascript:add()"><input class="button2" type="button" value="新增"> </a>
					<a class="s03" href="${ctx}/second_merchant_relation.htm"><input class="button2" type="button" value="返回"> </a>
				</td>
			</tr>
	</tr>
	</form>
</table>
