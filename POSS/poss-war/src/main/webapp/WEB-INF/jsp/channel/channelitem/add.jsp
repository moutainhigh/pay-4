<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/paymentChannelItem.htm?method=add";
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
		var paymentCategoryCode = $("#paymentCategoryCode").val();
		if(name == ""){
			alert("渠道名称不能为空");
			$("#name").focus();
			return false;
		}
		if(code == ""){
			alert("机构编码不能为空");
			$("#code").focus();
			return false;
		}
		if(orgCode == ""){
			alert("渠道orgCode不能为空");
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
		if(!paymentCategoryCode){
			alert("所属类别不能为空！");
			$("#paymentCategoryCode").focus();
			return false;
		}
		return true;
	}
</script>

<h2 class="panel_title">支付通道新增</h2>
<form action="${ctx}/paymentChannelItem.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道名称：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name" value="" />
			</td>
			<td class=border_top_right4 align="right">通道编码：</td>
			<td class="border_top_right4">
				<input type="text" name="code" id="code" value="" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构编码：</td>
			<td class="border_top_right4">
				<select id="orgCode" name="orgCode">
					<option value="" selected>---请选择---</option>
					<c:forEach var="bank" items="${banks}" varStatus="v">
						<option value="${bank.bankId}">${bank.bankName}</option>
					</c:forEach>
				</select><i>定义后，配置PE.BANK_ORGCODE_MAPPING中的记录</i>
			</td>
			<td class=border_top_right4 align="right">通道别名：</td>
			<td class="border_top_right4">
				<input type="text" name="alias" id="alias" value="" />
			</td>
		</tr>
		<!-- <tr class="trForContent1">
			<td class=border_top_right4 align="right">银行缩写：</td>
			<td class="border_top_right4">
				<input type="text" name="bankCode" id="bankCode" value="" />
			</td>
			<td class=border_top_right4 align="right">通道费率：</td>
			<td class="border_top_right4">
				<input type="text" name="rate" id="rate" value="" />万分之几
			</td>
		</tr> -->
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">协议类型：</td>
			<td class="border_top_right4">
				<select name="protocolType" id="protocolType">
					<option value="" selected>---请选择---</option>
					<option value="HTTP">HTTP</option>
					<option value="HESSIAN">HESSIAN</option>
					<option value="SOCKET">SOCKET</option>
				</select>
			</td>
			<!-- <td class=border_top_right4 align="right">路由金额：</td>
			<td class="border_top_right4">
				<input type="text" name="routeAmount" id="routeAmount" value="" />单位：元
			</td> -->
			<td class=border_top_right4 align="right">平台在机构商户号：</td>
			<td class="border_top_right4">
				<input type="text" name="orgMerchantCode" id="orgMerchantCode" value="" />
			</td>
		</tr>
<!-- 		<tr class="trForContent1"> -->
			
			<!-- <td class=border_top_right4 align="right">交易类型：</td>
			<td class="border_top_right4">
				<select name="transType" id="transType">
					<option value="" selected>---请选择---</option>
					<option value="EDC">EDC</option>
					<option value="DCC">DCC</option>
				</select>
			</td> -->
<!-- 		</tr> -->
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道说明：</td>
			<td class="border_top_right4">
				<input type="text" name="description" id="description" value="${channel.description }" />
			</td>
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="status">
					<option value="1" <c:if test="${channel.status== '1'}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${channel.status== '0'}">selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<%-- <tr class="trForContent1">
			<td class=border_top_right4 align="right">排序：</td>
			<td class="border_top_right4">
				<input type="text" name="serialNo" id="serialNo" value="${channel.serialNo }" />
			</td>
			<td class=border_top_right4 align="right">单笔限额：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="singleAmount" id="singleAmount" value="${channel.orgcode }" />单位：元
			</td>
		</tr> --%>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">卡类型：</td>
			<td class="border_top_right4">
				<select name="cardType" id="cardType">
					<option value="" selected>---请选择---</option>
					<option value="0">不分借贷</option>
					<option value="1">借记卡</option>
					<option value="2">信用卡</option>
				</select>
			</td>
			<!-- <td class=border_top_right4 align="right">是否需签约：</td>
			<td class="border_top_right4" colspan="3">
				<select name="signFlag" id="signFlag">
					<option value="" selected>---请选择---</option>
					<option value="0">不需签约</option>
					<option value="1">需要签约</option>
				</select>
			</td> -->
			<td class=border_top_right4 align="right">模式：</td>
			<td class="border_top_right4">
				<select id="pattern" name="pattern">
					<option value="" selected>---请选择---</option>
					<option value="A">全部</option>
					<option value="M">MOTO</option>
					<option value="E">3D</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道前置地址：</td>
			<td class="border_top_right4">
				<input type="text" name="preServerUrl" id="preServerUrl" value="${channel.serviceUrl }" />
			</td>
			<td class=border_top_right4 align="right">所属渠道：</td>
			<td class="border_top_right4" colspan="3">
				<select id="paymentChannelCode" name="paymentChannelCode">
					<option value="" selected>---请选择---</option>
					<c:forEach var="channel" items="${paymentChannels}" varStatus="v">
						<option value="${channel.code}">${channel.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">前台回调地址：</td>
			<td class="border_top_right4">
				<input type="text" name="frontCallbackUrl" id="frontCallbackUrl" value="${channel.serviceUrl }" />
			</td>
			<td class=border_top_right4 align="right">后台回调地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="backgroundCallbackUrl" id="backgroundCallbackUrl" value="${channel.serviceUrl }" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">银行图标样式：</td>
			<td class="border_top_right4">
				<select name="labelClass" id="labelClass">
					<option value="">请选择</option>
					<c:forEach items="${bankLabelClassEnum}" var="bankLabel">
						<option value="${bankLabel.labelurl}">${bankLabel.description}</option>
					</c:forEach>
				</select>
			</td>
			<td class=border_top_right4 align="right">所属类别：</td>
			<td class="border_top_right4" colspan="3">
				<select id="paymentCategoryCode" name="paymentCategoryCode">
					<option value="" selected>---请选择---</option>
					<c:forEach var="category" items="${paymentCategorys}" varStatus="v">
						<option value="${category.code}">${category.name}</option>
					</c:forEach>
				</select>	
			</td>
		</tr>
<!-- 		<tr class="trForContent1"> -->
			<%-- <td class=border_top_right4 align="right">结算币种：</td>
			<td class="border_top_right4" colspan="3">
				<select id="currencyCode" name="currencyCode">
					<option value="" selected>---请选择---</option>
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}">${curCode.desc}</option>
					</c:forEach>
				</select>
			</td> --%>
<!-- 		</tr> -->
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">终端号</td>
			<td align="left" class="border_top_right4"><input type="text" name="terminalCode" id="terminalCode"/></td>
			<td align="right" class="border_top_right4">授权号</td>
			<td align="left" class="border_top_right4"><input type="text" name="accessCode" id="accessCode"/></td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">机构密钥：</td>
			<td class="border_top_right4">
				<input type="text" name="orgKey" id="orgKey" value="" size="50" style="height: 30px;"/>
			</td>
			<td class=border_top_right4 align="right">商户账单名：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="merchantBillName" id="merchantBillName" value="" style="height: 30px;"/>
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