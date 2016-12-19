<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/channelConfig.htm?method=add";
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
		var pattern = $("#pattern").val();
		var merchantBillName=$("#merchantBillName").val();

		var fitMerchantType = $("#fitMerchantType").val();
		//增加商户账单名非空验证  2016年5月19日 17:18:18 delin.dong
		if(merchantBillName == ""){
			alert("请填写商户账单名!");
			$("#merchantBillName").focus();
			return false;
		}
		
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
		
		if(transType == ""){
			alert("请选择交易类型");
			$("#transType").focus();
			return false;
		}
		
		//卡司
		if('10076001' == orgCode){
			if(terminalCode == ""){
				alert("终端号不能为空");
				$("#terminalCode").focus();
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
		if(!pattern){
			alert("交易模式不能为空");
			$("#pattern").focus();
			return false;
		}
		if(fitMerchantType == ""){
			alert("商户类型不能为空");
			$("#fitMerchantType").focus();
			return false;
		}
		return true;
	}
	
	function changeItem(orgCode){
		if('10075001' == orgCode){
			$("#t_website").show();
		}else{
			$("#t_website").hide();
		}
	}
</script>

<h2 class="panel_title">通道配置信息新增</h2>
<form action="${ctx}/channelConfig.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="58%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td width="46%" align="right" class=border_top_right4>渠道：</td>
			<td width="54%" class="border_top_right4">
				<select id="orgCode" name="orgCode" style="width:150px;" onchange="changeItem(this.value);">
					<option value="" selected>---请选择---</option>
					<c:forEach var="bank" items="${channelItems}" varStatus="v">
						<option value="${bank.code}">${bank.desc}</option>
					</c:forEach>
				</select><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构商户号：</td>
			<td class="border_top_right4">
				<input type="text" name="orgMerchantCode" id="orgMerchantCode" value=""  style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">商户账单名：</td>
			<td class="border_top_right4">
				<input type="text" name="merchantBillName" id="merchantBillName" value=""  style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_transType">
				<td align="right" class="border_top_right4">交易类型：</td>
				<td align="left" class="border_top_right4">
				<select id="transType" name="transType" style="width:150px;">
						<option value="" selected>---请选择---</option>
						<option value="EDC">EDC</option>
						<option value="DCC">DCC</option>
					</select>
				</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_pattern">
				<td align="right" class="border_top_right4">模式：</td>
				<td align="left" class="border_top_right4">
				<select id="pattern" name="pattern" style="width:150px;">
						<option value="" selected>---请选择---</option>
						<option value="A">全部</option>
						<option value="M">MOTO</option>
						<option value="E">3D</option>
					</select>
				</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_requestMerchantName">
			<td align="right" class="border_top_right4">申请商户名称：</td>
			<td align="left" class="border_top_right4">
				<input type="text" id="requestMerchantName" style="width:150px;"/>
			</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_terminalCode">
			<td align="right" class="border_top_right4">终端号：</td>
			<td align="left" class="border_top_right4"><input type="text" name="terminalCode" id="terminalCode" style="width:150px;"/>卡司下必填</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_accessCode">
			<td align="right" class="border_top_right4">授权号：</td>
			<td align="left" class="border_top_right4"><input type="text" name="accessCode" id="accessCode" style="width:150px;"/>卡司下必填</td>
		</tr>		
 		<%-- <tr height="6" class="trForContent1" id="t_currencyCode"> 
			<td align="right" class="border_top_right4">币种：</td> 
			<td align="left" class="border_top_right4"> 
 				<select id="currencyCode" name="currencyCode" style="width:150px;"> 
 					<option value="" selected>---请选择---</option> 
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v"> 
						<option value="${curCode.code}">${curCode.desc}</option>
 					</c:forEach> 
				</select> 
				Credorax下必填 
 			</td> 
 		</tr>  --%>
		<!-- 取消mcc注释 -->
		<tr height="6" class="trForContent1" id="t_mcc"> 
			<td align="right" class="border_top_right4">MCC：</td> 
 			<td align="left" class="border_top_right4"><input type="text" name="mcc" id="mcc" style="width:150px;"/></td> 
 		</tr> 
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构密钥：</td>
			<td class="border_top_right4">
				<textarea rows="3" cols="50" name="orgKey" id="orgKey">${config.orgKey }</textarea>
			</td>
		</tr>
		<tr height="6" class="trForContent1" id="t_fitMerchantType">
			<td align="right" class="border_top_right4">商户类型：</td>
			<td align="left" class="border_top_right4">
				<select id="fitMerchantType" name="fitMerchantType" style="width:150px;">
					<option value="" selected>---请选择---</option>
					<option value="500" >iPayLinks</option>
					<option value="800" >GCPayment</option>
				</select><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1" id="t_website" style="display: none;">
			<td class=border_top_right4 align="right">映射网址：</td>
			<td class="border_top_right4">
				<input type="text" id="supportWebsite" name="supportWebsite" style="width:150px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
</form>