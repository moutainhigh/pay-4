<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<script type="text/javascript">
		$(function(){
			$("#enforceWithdrawBtn").click(function(){
				if(!validate())
					return false;
			});
			
			var msg = "${msg}";
			if('' != msg)
				alert(msg);
			
			$("#bankProvince").change(function(){
				$("#bankCity").load("${ctx}/fundout_withdraw_enforce.htm?method=city&provinceId="+$(this).val());
				$("#payeeOpeningBankNameKey").val('');
				getOpeningBankNames();
			});
			
			$("#bankCity").change(function(){
				$("#payeeOpeningBankNameKey").val('');
				getOpeningBankNames();
			});
			
			$("#loginName").focusout(function(){
				$('#loginNameTip').html('');
				$('#availableBalances').html('0.00');
				var loginName = $("#loginName").val();
				if(loginName != ''){
					var pars = "loginName=" + loginName;
					$.ajax({
						type: "POST",
						url: "${ctx}/fundout_withdraw_enforce.htm?method=checkMemberInfo",
						data: pars,
						success: function(result) {
							if('' == result){
								$.ajax({
									type: "POST",
									url: "${ctx}/fundout_withdraw_enforce.htm?method=getAvailableBalances",
									data: pars,
									success: function(result) {
										if('' != result){
											var availableBalances = result * 0.001;
											availableBalances = Math.round(availableBalances*Math.pow(10,2))/Math.pow(10,2);
											$('#availableBalances').html(Number(availableBalances));
										}
									}
								});
							}else{
								$('#loginNameTip').html(result);
							}
							
						}
					});
				}
			});
			
			$("#bankAcct").focusout(function(){
				checkBankAcct();
			});
			$("#bankName").change(function(){
				checkBankAcct();
				$("#payeeOpeningBankNameKey").val('');
				getOpeningBankNames();
				
				$('#bankNameTip').html("");
				var targetBankId = $("#bankName").val();
				var bankName  = $.trim($("#bankName option:selected").text());
				if(targetBankId != ''){
					var pars = "targetBankId=" + targetBankId;
					$.ajax({
						type: "POST",
						url: "${ctx}/fundout_withdraw_enforce.htm?method=getWithdrawChannel",
						data: pars,
						success: function(result) {
							if('1' != result){
								$('#bankNameTip').html("暂不支持提现到"+bankName);
							}
						}
					});
				}
				
			});
		});


		//卡bin校验
		function checkBankAcct(){
			
			var bankAcct = $.trim($("#bankAcct").val());
			var orgCode = $("#bankName").val();
			var tradeType = 0;
			
			if(bankAcct != '' && orgCode != ''){
				var pars = "bankAcct=" + bankAcct + "&orgCode=" + orgCode + "&tradeType=" + tradeType;
				$.ajax({
					type: "POST",
					url: "${ctx}/fundout_withdraw_enforce.htm?method=checkBankAcct",
					data: pars,
					success: function(result) {
						$('#payeeBankAccountTip').html(result);
					}
				});
			}
		}


		//查询城市对应的开户支行
		function getOpeningBankNames(){
			
			$("#bankBranch").html("<option value=''>请选择开户行</option>");
			var payeeBankName = ""; //银行编号
			var payeeBankProvinceName = ""; //省份
			var payeeBankCityName =  ''; //城市
			
			if($("#bankName").val()!=''){
				payeeBankName = $.trim($("#bankName option:selected").text());
			}
			if($("#bankProvince").val()!=''){
				payeeBankProvinceName = $.trim($("#bankProvince option:selected").text());
			}
			if($("#bankCity").val()!=''){
				payeeBankCityName = $.trim($("#bankCity option:selected").text());
			}
			
			var payeeOpeningBankNameKey = $.trim($("#payeeOpeningBankNameKey").val());

			
			var url = "${ctx}/fundout_withdraw_enforce.htm";

			if(payeeBankName != '' && payeeBankProvinceName != '' && payeeBankCityName != '') {

				var data = {"method":"queryOpeningBankNameList","b":payeeBankName,"p":payeeBankProvinceName,"c":payeeBankCityName,"k":payeeOpeningBankNameKey};
				jQuery.post(url+"?date="+new Date(),data,function(response){
					if(""!=response){
						$("#bankBranch").append(response);
					}
				});
			}
		}

		function validate() {
			var loginName = $.trim($("#loginName").val()); //用户名
			var bankName = $.trim($("#bankName option:selected").val()); //银行名称
			
			var showBankName = $.trim($("#bankName option:selected").text()); //银行名称
			var bankProvinceName = $.trim($("#bankProvince option:selected").text()); //省份名称
			var bankCityName = $.trim($("#bankCity option:selected").text()); //城市名称
			
			var bankBranch = $.trim($("#bankBranch option:selected").val()); //开户行
			var accHolder = $.trim($("#accHolder").val()); //开户人
			var bankAcct = $.trim($("#bankAcct").val()); //银行账号
			var amount = $.trim($("#amount").val()); //提现金额
			var remark = $.trim($("#remark").val()); //备注
			var payeeBankAccountTip = $.trim($("#payeeBankAccountTip").text());//卡bin校验
			var availableBalances = $.trim($("#availableBalances").text());//可提现余额
			var loginNameTip = $.trim($("#loginNameTip").text());//用户名校验
			var bankNameTip = $.trim($("#bankNameTip").text());//是否支持提现到该银行
			var bankAcctlength = bankAcct.length;
			var is = amount.indexOf(".");
			var amountlength = amount.length;

			if('' == loginName){
				alert("请输入用户名!");
				return false;
			}
			if('' != bankNameTip){
				alert(bankNameTip);
				return false;
			}
			if('' == bankName){
				alert("请选择银行名称!");
				return false;
			}
			if('' == bankBranch){
				alert("请选择开户行!");
				return false;
			}
			if('' == accHolder){
				alert("请填写开户人名称!");
				return false;
			}
			if('' == bankAcct){
				alert("请填写银行账号!");
				return false;
			}
			if(bankAcctlength < 8){
				alert("请填写正确的银行账号,银行账号为8至32位数字!");
				return false;
			}
			if(!s_isNumber(bankAcct)){
				alert("!请填写正确的银行账号,银行账号为8至32位数字!");
				return false;
			}
			if('' == amount){
				alert("请填写提现金额!");
				return false;
			}
			if(!s_isNumber(amount)){
				alert("提现金额必须为数字!");
				return false;
			}
			if(0 < is && 0 < amountlength){
				var amountBalances = amountlength - is;
				if(2 < amountBalances - 1){
					alert("请输入正确的提现金额!");
					return false;
				}
			}
			if('' == remark){
				alert("备注不能为空!");
				return false;
			}
			if('' != payeeBankAccountTip){
				alert(payeeBankAccountTip);
				return false;
			}
			if('' != loginNameTip){
				alert(loginNameTip);
				return false;
			}
			if(Number(amount) > Number(availableBalances)){
				alert("提现金额不能大于可提现金额!");
				return false;
			}
			if(Number(amount) <= 0){
				alert("请输入正确的提现金额!");
				return false;
			}
			var info = "用户名："+loginName+"\n"+"银行名称："+showBankName+"\n"+"开户行："+bankBranch+"\n"+"开户人："+accHolder+"\n"+"银行账号："+bankAcct+"\n"+"提现金额："+amount+"元";
			if (!confirm(info)) {
				return false;
			}
			$("#bankNameStr").val(showBankName);
			$("#bankProvinceName").val(bankProvinceName);
			$("#bankCityName").val(bankCityName);
			return true;
		}
		
	</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">强制提现</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	
	<form method="post" action="${ctx}/fundout_withdraw_enforce.htm?method=confirmWithdraw">
	<table class="border_all2" width="50%" border="0" cellspacing="0" cellpadding="3" align="center">
		<input type="hidden" name="bankNameStr" id="bankNameStr">
		<input type="hidden" name="bankProvinceName" id="bankProvinceName">
		<input type="hidden" name="bankCityName" id="bankCityName">
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">用户名:</td>
			<td class="border_top_right4">
				<input type="text" name="loginName" id="loginName" maxlength="30" size="40" onblur=""/>
				<strong style="color: red;" id="loginNameTip"></strong>
			</td>
		</tr>
		
		<!--
		<tr class="trbgcolor10">
			<td class="border_top_right4">账户类型:</td>
			<td class="border_top_right4">
				<input type="radio" name="memberAccType" id="memberAccType" checked="checked"  value="1"/>个人账户
				<input type="radio" name="memberAccType" id="memberAccType" value="2"/>企业账户
			</td>
		</tr>
		-->
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行名称:</td>
			<td class="border_top_right4">
				<select name="bankName" id="bankName">
					<option value="" selected>---请选择银行---</option>	
					<c:forEach items="${bankList}" var="bank">
						<option value="${bank.bankId}">${bank.bankName}</option>
					</c:forEach>
				</select>
				<strong style="color: red;" id="bankNameTip"></strong>
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">开户银行所属省份:</td>
			<td class="border_top_right4">
				<select name="bankProvince" id="bankProvince">
					<option value="" selected>---请选择省份---</option>	
					<c:forEach items="${provinceList}" var="province">
						<option value="${province.provincecode}">${province.provincename}</option>
					</c:forEach>
				</select>
				<select name="bankCity" id="bankCity">										
						<option value="" selected>---请选择城市---</option>					
				</select>
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">开户行名称:</td>
			<td class="border_top_right4">
				<input  id="payeeOpeningBankNameKey" name="payeeOpeningBankNameKey" tabindex="5" type="text" maxlength="60" size="35" style="width: 180px">
				<input type="button" onclick="getOpeningBankNames()" value="按所填关键字过滤">
				<br/>
				<span style="color: red;">如：高安支行，如果您无法确定，建议您致电银行客服询问。</span>
				<p class="mt10">
					<select id="bankBranch" name="bankBranch" style="width:400px;">
					<option value=''>请选择开户行</option>
					</select>
					<br/>
					<span style="color: red;" id="payeeOpeningBankNameTip">如果选择开户行名称错误，可能导致提现失败。</span>
				</p>
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">开户人名称:</td>
			<td class="border_top_right4">
				<input type="text" name="accHolder" id="accHolder" maxlength="30"/>
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行账号:</td>
			<td class="border_top_right4">
				<input type="text" id="bankAcct" name="bankAcct" maxlength="32"/>
				<strong style="color: red;" id="payeeBankAccountTip"><span style="color: red;">仅支持借记卡</span></strong>
			</td>
		</tr>	
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">可提现金额:</td>
			<td class="border_top_right4">
				<strong style="color: red;" id="availableBalances">0.00</strong>元
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">提现金额:</td>
			<td class="border_top_right4">
				<input type="text" name="amount" id="amount" maxlength="19"/>元
			</td>
		</tr>
		
		<tr class="trbgcolor10">
			<td class="border_top_right4">备注:</td>
			<td class="border_top_right4">
				<textarea rows="2" cols="40" name="remark" id="remark" readonly="readonly">强制提现</textarea>
			</td>
		</tr>
	</table>
	
	<br>
	
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
			<tr class="trbgcolor6" align="center">
				<td>
					<input type="hidden" name="batchAccounts" value="0"/>
					<input type="hidden" name="mcc" value="0"/>
				 	<input type="submit" name="Submit" id="enforceWithdrawBtn" value="提交">
				</td>
			</tr>
	 </table>
	</form>
</body>
</html>