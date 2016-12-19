<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/paymentChannelItem.htm?method=update";
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(1==res){
						alert('操作成功');
						window.location.href="${ctx}/paymentChannelItem.htm";
					}else{
						alert(res);
					}
				});
			}
		});
	});

	function checkInfo(){
		var code = $("#code").val();
		var orgCode = $("#orgCode").val();
		var name = $("#name").val();
		var rate = $("#rate").val();
		var cardType = $("#cardType").val();
		var protocolType = $("#protocolType").val();
		var preServerUrl = $("#preServerUrl").val();
		if(name == ""){
			alert("渠道名称不能为空");
			$("#name").focus();
			return false;
		}
		if(code == ""){
			alert("渠道code不能为空");
			$("#code").focus();
			return false;
		}
		if(orgCode == ""){
			alert("机构编码不能为空");
			$("#orgCode").focus();
			return false;
		}
		if(rate == ""){
			alert("渠道费率不能为空");
			$("#rate").focus();
			return false;
		}
		if(cardType == ""){
			alert("卡片类型不能为空");
			$("#cardType").focus();
			return false;
		}
		if(protocolType == ""){
			alert("协议类型不能为空");
			$("#protocolType").focus();
			return false;
		}
		if(preServerUrl == ""){
			alert("前置地址不能为空！");
			$("#preServerUrl").focus();
			return false;
		}
		return true;
	}
</script>

<h2 class="panel_title">支付通道修改</h2>
<form action="${ctx}/paymentChannelItem.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道名称：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name" value="${item.name}" />
			</td>
			<td class=border_top_right4 align="right">通道编码：</td>
			<td class="border_top_right4">
				<input type="text" name="code" id="code" value="${item.code}" readonly="readonly"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构编码：</td>
			<td class="border_top_right4">
				<select id="orgCode" name="orgCode">
					<option value="" selected>---请选择---</option>
					<c:forEach var="bank" items="${banks}" varStatus="v">
						<option value="${bank.bankId}" <c:if test="${item.orgCode == bank.bankId}">selected</c:if>>${bank.bankName}</option>
					</c:forEach>
				</select><i>定义后，配置PE.BANK_ORGCODE_MAPPING中的记录</i>
			</td>
			<td class=border_top_right4 align="right">通道别名：</td>
			<td class="border_top_right4">
				<input type="text" name="alias" id="alias" value="${item.alias}" />
			</td>
		</tr>
		<!-- <tr class="trForContent1">
			<td class=border_top_right4 align="right">银行缩写：</td>
			<td class="border_top_right4">
				<input type="text" name="bankCode" id="bankCode" value="${item.bankCode}" />
			</td>
			<td class=border_top_right4 align="right">通道费率：</td>
			<td class="border_top_right4">
				<input type="text" name="rate" id="rate" value="${item.rate}" />
			</td>
		</tr>-->
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">协议类型：</td>
			<td class="border_top_right4">
				<select name="protocolType" id="protocolType">
					<option value="HTTP" <c:if test="${item.protocolType== 'HTTP'}">selected</c:if>>HTTP</option>
					<option value="HESSIAN" <c:if test="${item.protocolType== 'HESSIAN'}">selected</c:if>>HESSIAN</option>
					<option value="SOCKET" <c:if test="${item.protocolType== 'SOCKET'}">selected</c:if>>SOCKET</option>
				</select>
			</td>
<!-- 			<td class=border_top_right4 align="right">路由金额：</td> -->
<!-- 			<td class="border_top_right4"> -->
<%-- 				<input type="text" name="routeAmount" id="routeAmount" value="${item.routeAmount }" />单位：元 --%>
<!-- 			</td> -->
				<td class=border_top_right4 align="right">平台在机构商户号：</td>
			<td class="border_top_right4">
				<input type="text" name="orgMerchantCode" id="orgMerchantCode" value="${item.orgMerchantCode }" />
			</td>
		</tr>
		<!-- <tr class="trForContent1">
			<td class=border_top_right4 align="right">交易类型：</td>
			<td class="border_top_right4">
				<select name="transType" id="transType">
					<option value="EDC" <c:if test="${item.transType== 'EDC'}">selected</c:if>>EDC</option>
					<option value="DCC" <c:if test="${item.transType== 'DCC'}">selected</c:if>>DCC</option>
				</select>
			</td>
		</tr>--->
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道说明：</td>
			<td class="border_top_right4">
				<input type="text" name="description" id="description" value="${item.description }" />
			</td>
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="status">
					<option value="1" <c:if test="${item.status== '1'}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${item.status== '0'}">selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
<!-- 		<tr class="trForContent1"> -->
<!-- 			<td class=border_top_right4 align="right">排序：</td> -->
<!-- 			<td class="border_top_right4"> -->
<%-- 				<input type="text" name="serialNo" id="serialNo" value="${item.serialNo}" /> --%>
<!-- 			</td> -->
<!-- 			<td class=border_top_right4 align="right">单笔限额：</td> -->
<!-- 			<td class="border_top_right4" colspan="3"> -->
<%-- 				<input type="text" name="singleAmount" id="singleAmount" value="${item.singleAmount}" />单位：元 --%>
<!-- 			</td> -->
<!-- 		</tr> -->
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">卡类型：</td>
			<td class="border_top_right4">
				<select name="cardType" id="cardType">
					<option value="0" <c:if test="${item.cardType== '0'}">selected</c:if>>不分借贷</option>
					<option value="1" <c:if test="${item.cardType== '1'}">selected</c:if>>借记卡</option>
					<option value="2" <c:if test="${item.cardType== '2'}">selected</c:if>>信用卡</option>
				</select>
			</td>
<!-- 			<td class=border_top_right4 align="right">是否需签约：</td> -->
<!-- 			<td class="border_top_right4" colspan="3"> -->
<!-- 				<select name="signFlag" id="signFlag"> -->
<%-- 					<option value="0" <c:if test="${item.signFlag== '0'}">selected</c:if>>不需签约</option> --%>
<%-- 					<option value="1" <c:if test="${item.signFlag== '1'}">selected</c:if>>需要签约</option> --%>
<!-- 				</select> -->
<!-- 			</td> -->
				<td class=border_top_right4 align="right">模式：</td>
				<td class="border_top_right4">
					<select id="pattern" name="pattern">
						<option value="" selected>---请选择---</option>
						<option value="A" <c:if test="${item.pattern== 'A'}">selected</c:if>>全部</option>
						<option value="M" <c:if test="${item.pattern== 'M'}">selected</c:if>>MOTO</option>
						<option value="E" <c:if test="${item.pattern== 'E'}">selected</c:if>>3D</option>
					</select>
				</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道前置地址：</td>
			<td class="border_top_right4">
				<input type="text" name="preServerUrl" id="preServerUrl" value="${item.preServerUrl}" />
			</td>
			<td class=border_top_right4 align="right">所属渠道：</td>
			<td class="border_top_right4" colspan="3">
				<select id="paymentChannelCode" name="paymentChannelCode">
					<c:forEach var="channel" items="${paymentChannels}">
						<option value="${channel.code}" <c:if test="${item.paymentChannelCode == channel.code}">selected</c:if>>${channel.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">前台回调地址：</td>
			<td class="border_top_right4">
				<input type="text" name="frontCallbackUrl" id="frontCallbackUrl" value="${item.frontCallbackUrl }" />
			</td>
			<td class=border_top_right4 align="right">后台回调地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="backgroundCallbackUrl" id="backgroundCallbackUrl" value="${item.backgroundCallbackUrl }" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">银行图标样式：</td>
			<td class="border_top_right4">
				<select name="labelClass" id="labelClass">
					<c:forEach items="${bankLabelClassEnum}" var="bankLabel">
						<option value="${bankLabel.labelurl}" <c:if test="${item.labelClass == bankLabel.labelurl}">selected</c:if>>${bankLabel.description}</option>
					</c:forEach>
				</select>
			</td>
			<td class=border_top_right4 align="right">所属类别：</td>
			<td class="border_top_right4" colspan="3">
				<select id="paymentCategoryCode" name="paymentCategoryCode">
					<c:forEach var="category" items="${paymentCategorys}" varStatus="v">
						<option value="${category.code}" <c:if test="${item.paymentCategoryCode== category.code}">selected</c:if>>${category.name}</option>
					</c:forEach>
				</select>	
			</td>
		</tr>
<!-- 		<tr class="trForContent1"> -->
<!-- 			<td class=border_top_right4 align="right">结算币种：</td> -->
<!-- 			<td class="border_top_right4" colspan="3"> -->
<!-- 				<select id="currencyCode" name="currencyCode"> -->
<!-- 					<option value="" selected>---请选择---</option> -->
<%-- 					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v"> --%>
<%-- 						<option value="${curCode.code}" <c:if test="${curCode.code == item.currencyCode}">selected</c:if>>${curCode.desc}</option> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select> -->
<!-- 			</td> -->
<!-- 		</tr>		 -->
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">终端号</td>
			<td align="left" class="border_top_right4"><input type="text" name="terminalCode" id="terminalCode" value="${item.terminalCode}"/></td>
			<td align="right" class="border_top_right4">授权号</td>
			<td align="left" class="border_top_right4"><input type="text" name="accessCode" id="accessCode" value="${item.accessCode}"/></td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构密钥：</td>
			<td class="border_top_right4">
				<input type="text" name="orgKey" id="orgKey" value="${item.orgKey }" size="50" style="height: 30px;"/>
			</td>
			<td class=border_top_right4 align="right">商户账单名称：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="merchantBillName" id="merchantBillName" value="${item.merchantBillName }" style="height: 30px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
	<input type="hidden" name="id" id="id" value="${item.id}"/>
</form>