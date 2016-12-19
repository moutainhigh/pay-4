<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/couponList.do?method=update";
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(1==res){
						alert('操作成功');
						window.location.href="${ctx}/couponList.do";
					}else{
						alert(res);
					}
				});
			}
		});
	});

	function checkInfo(){
		var orgCode = $("#orgCode").val();
		var orgMerchantCode = $("#orgMerchantCode").val();
		var terminalCode = $("#terminalCode").val();
		var accessCode = $("#accessCode").val();
		var transType = $("#transType").val();
		var orgKey = $("#orgKey").val();
		var currencyCode = $("#currencyCode").val();
		var mcc = $("#mcc").val();
		
		if(orgCode == ""){
			alert("请选择银行机构!");
			$("#orgCode").focus();
			return false;
		}
		if(orgMerchantCode == ""){
			alert("机构商户号不能为空");
			$("#orgMerchantCode").focus();
			return false;
		}
		//卡司
		if('10076001' == orgCode){
			if(terminalCode == ""){
				alert("终端号不能为空");
				$("#terminalCode").focus();
				return false;
			}
			if(transType == ""){
				alert("请选择交易类型");
				$("#transType").focus();
				return false;
			}
			if(accessCode == ""){
				alert("请输入授权码");
				$("#accessCode").focus();
				return false;
			}
		}
		
		//Credorax
		if('10075001' == orgCode){
			if(currencyCode == ""){
				alert("请选择币种");
				$("#currencyCode").focus();
				return false;
			}
			if(mcc == ""){
				alert("请输入MCC");
				$("#mcc").focus();
				return false;
			}
		}
		
		if(orgKey == ""){
			alert("机构密钥不能为空");
			$("#orgKey").focus();
			return false;
		}
		return true;
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">支付通道修改</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/couponList.do?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构编码：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCode" id="orgCode" value="${config.orgCode }" readonly="readonly"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构商户号：</td>
			<td class="border_top_right4">
				<input type="text" name="orgMerchantCode" id="orgMerchantCode" value="${config.orgMerchantCode }" />
			</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">终端号</td>
			<td align="left" class="border_top_right4"><input type="text" name="terminalCode" id="terminalCode" value="${config. terminalCode}"/></td>
		</tr>
		<tr height="6" class="trForContent1" id="t_currencyCode">
			<td align="right" class="border_top_right4">币种：</td>
			<td align="left" class="border_top_right4">
				<select id="currencyCode" name="currencyCode">
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}" <c:if test="${curCode.code == config.currencyCode }">selected</c:if>>${curCode.desc}</option>
					</c:forEach>
				</select>
				Credorax下必填
			</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">授权号</td>
			<td align="left" class="border_top_right4"><input type="text" name="accessCode" id="accessCode" value="${config.accessCode }"/></td>
		</tr>
		<tr height="6" class="trForContent1" id="t_mcc">
			<td align="right" class="border_top_right4">MCC：</td>
			<td align="left" class="border_top_right4"><input type="text" name="mcc" id="mcc" value="${config.mcc }"/>Credorax下必填</td>
		</tr>
		<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">交易类型</td>
				<td align="left" class="border_top_right4">
				<select id="transType" name="transType">
						<option value="EDC" <c:if test="${item.transType == 'EDC'}">selected</c:if>>EDC</option>
						<option value="DCC" <c:if test="${item.transType == 'DCC'}">selected</c:if>>DCC</option>
					</select>
				</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构密钥：</td>
			<td class="border_top_right4">
				<input type="text" name="orgKey" id="orgKey" value="${config.orgKey }" />
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
	<input type="hidden" name="id" id="id" value="${config.id}"/>
</form>