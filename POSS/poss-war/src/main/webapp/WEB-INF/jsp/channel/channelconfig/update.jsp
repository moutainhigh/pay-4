<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/channelConfig.htm?method=update";
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(1==res){
						alert('操作成功');
						window.location.href="${ctx}/channelConfig.htm";
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

<h2 class="panel_title">支付通道修改</h2>
<form action="${ctx}/channelConfig.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="65%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td width="44%" align="right" class=border_top_right4>机构编码：</td>
			<td width="56%" class="border_top_right4">
				<input type="text" name="orgCode" id="orgCode"  readonly value="${config.orgCode }" readonly style="width:150px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构商户号：</td>
			<td class="border_top_right4">
				<input type="text" name="orgMerchantCode"   readonly  id="orgMerchantCode" value="${config.orgMerchantCode }" style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">商户账单名：</td>
			<td class="border_top_right4">
				<input type="text" name="merchantBillName" id="merchantBillName" value="${config.merchantBillName }"  style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr height="6" class="trForContent1">
				<td align="right" class="border_top_right4">交易类型</td>
				<td align="left" class="border_top_right4">
				<select id="transType" name="transType" style="width:150px;"> <!--  -->
						<option value="EDC" <c:if test="${config.transType == 'EDC'}">selected</c:if>>EDC</option>
						<option value="DCC" <c:if test="${config.transType == 'DCC'}">selected</c:if>>DCC</option>
					</select><font color="red">*</font>
				</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_requestMerchantName">
			<td align="right" class="border_top_right4">申请商户名称：</td>
			<td align="left" class="border_top_right4">
				<input type="text" id="requestMerchantName" name="requestMerchantName" value="${config.requestMerchantName}" style="width:150px;"/>
			</td>
		</tr>
		
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">终端号</td>
			<td align="left" class="border_top_right4"><input type="text" name="terminalCode" id="terminalCode" value="${config. terminalCode}" style="width:150px;"/></td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">授权号</td>
			<td align="left" class="border_top_right4"><input type="text" name="accessCode" id="accessCode" value="${config.accessCode }" style="width:150px;"/></td>
		</tr>		
		<c:if test="${config.orgCode == '10075001'}">
		<tr height="6" class="trForContent1" id="t_currencyCode">
			<td align="right" class="border_top_right4">币种：</td>
			<td align="left" class="border_top_right4">
				<select id="currencyCode" name="currencyCode" style="width:150px;">
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}" <c:if test="${curCode.code == config.currencyCode }">selected</c:if>>${curCode.desc}</option>
					</c:forEach>
				</select>
				Credorax下必填
			</td>
		</tr>
		</c:if>
		<tr height="6" class="trForContent1" id="t_mcc">
			<td align="right" class="border_top_right4">MCC：</td>
			<td align="left" class="border_top_right4"><input type="text" name="mcc" id="mcc" value="${config.mcc }" style="width:150px;"/>Credorax下必填</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构密钥：</td>
			<td class="border_top_right4">
				<textarea rows="3" cols="50" name="orgKey" id="orgKey">${config.orgKey }</textarea>
			</td>
		</tr>
		
		<tr height="6" class="trForContent1" id="t_supportWebsite">
			<td align="right" class="border_top_right4">映射网址：</td>
			<td align="left" class="border_top_right4"><input type="text" name="supportWebsite" id="supportWebsite" value="${config.supportWebsite }" style="width:150px;"/>Credorax下必填</td>
		</tr>
		
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">模式：</td>
			<td class="border_top_right4">
				<select id="pattern" name="pattern" style="width:150px;">
						<option value="" selected>---请选择---</option>
						<option value="A" <c:if test="${config.pattern== 'A'}">selected</c:if>>全部</option>
						<option value="M" <c:if test="${config.pattern== 'M'}">selected</c:if>>MOTO</option>
						<option value="E" <c:if test="${config.pattern== 'E'}">selected</c:if>>3D</option>
					</select>
				</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4">
				<select id="status" name="status" style="width:150px;">
					<option value="" selected>---请选择---</option>
					<option value="1" <c:if test="${config.status== '1'}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${config.status== '0'}">selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">商户类型：</td>
			<td class="border_top_right4">
				<select id="fitMerchantType" name="fitMerchantType" style="width:150px;">
					<option value="" selected>---请选择---</option>
					<option value="500" <c:if test="${config.fitMerchantType== '500'}">selected</c:if>>iPayLinks</option>
					<option value="800" <c:if test="${config.fitMerchantType== '800'}">selected</c:if>>GCPayment</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
	<input type="hidden" name="id" id="id" value="${config.id}"/>
</form>