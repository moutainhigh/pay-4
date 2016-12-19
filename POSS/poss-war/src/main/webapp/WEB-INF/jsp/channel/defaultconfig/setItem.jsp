<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script language="javascript">

/*单个移动*/
function moveOptions() { 
	$("select[id=nofuncno] option:selected").each(function() {
		var same = false;
		var aa = $("#funcno option");
		for ( var i = 0; i < aa.length; i++) {
			if (this.value == aa[i].value) {
				same = true;
				break;
			}
		}
		if (!same)
			$(this).clone().appendTo($("#funcno"));
	});
		disalliopt();
}
	function moveOptions2() {
		$("#funcno option:selected").remove();
		disalliopt();
	}
	
	function disalliopt() {
		$('#funcno option').each(function() {
			$(this).attr('selected', false);
		});
	}
	function select_all() {
		$('#funcno option').each(function() {
			$(this).attr('selected', true);
		});
	}

	function checkForm() {

		if ($('#funcno option').length <= 0) {

			//if(!confirm("此操作会删除该商户唯一通道所属的渠道，确认删除？")) {
			//	return false;
			//}
		}
		
		select_all();
		var url="${ctx}/defaultChannelItem.htm?method=setItemUpdate";
		var data= $("#testjop1").serialize();
		$.post(url,data,function(res){
			alert('操作成功');
			//window.location.href="${ctx}/paymentChannelItemNew.htm?method=initSearch";
			window.history.go(-1);
		});
		//$("#testjop1").submit();
	}

</script>

</head>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="6"></td>
	</tr>
</table>
<p align="center">
<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
	<form id="testjop1" action="${ctx}/defaultChannelItem.htm?method=setItemUpdate" method="post">
	<tr valign="top" class="trForContent1">
		<td align="center" nowrap class="border_top_right4">
		<table width="617" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="3">&nbsp;</td>
			</tr>
			<tr align="center">
				<td align="center">可配置的通道</td>
				<td>&nbsp;</td>
				<td align="center">已配置的通道</td>
			</tr>
			<tr align="center">

				<td width="192" align="left">
						<select name="nofuncno" id="nofuncno" class="input1" size="25" multiple="multiple" style="width: 235px;">
						<c:forEach items="${alllist}" var="ke">
							<option value="${ke.id}">${ke.name}</option>
						</c:forEach>
					</select>
				</td>
				<td width="121">
				<table width="58" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="58" align="center">
							<input name="addB" type="button" class="button1" value="添 加 &gt;" onClick="moveOptions();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="delB" type="button" class="button1" value="&lt; 删 除" onClick="moveOptions2();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="saveSubmit" type="button" class="button1" value=" 提 交 " onClick="return checkForm();">
						</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<input name="saveSubmit" type="button" class="button1" value=" 返 回 " onClick="window.history.go(-1);">
						</td>
					</tr>
	
					
				</table>
				</td>
					<td width="192" align="right">
					<select name="funcno" id="funcno" size="25" class="input1" multiple style="width: 235px;" ">
						<c:forEach items="${hasList}" var="ke">
							<option value="${ke.id}">${ke.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3">&nbsp;</td>
			</tr>
		</table>
		</td>
	</tr>
	<input type="hidden" name="memberType" id="memberType" value="${paymentChannelItem.memberType}"/>
	<input type="hidden" name="paymentType" id="paymentType" value="${paymentChannelItem.paymentType}"/>
	</form>
</table>
