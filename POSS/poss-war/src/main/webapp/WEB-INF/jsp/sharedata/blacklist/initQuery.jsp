<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">查询黑名单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId">
	<input type="hidden" id="businessType" name="businessType" />
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">用户唯一识别码：</td>
			<td class="border_top_right4">
				<input type="text" name="sbm" id="sbm" />
			</td>
			<td align="right" class="border_top_right4">业务发生地：</td>
			<td class="border_top_right4">
				<input type="text" name="occuredArea" id="occuredArea" maxlength="6" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">黑名单类型：</td>
			<td class="border_top_right4">
				<select name="type" id="type">
					<option value="1">个人黑名单</option>
					<option value="2">机构黑名单</option>
				</select>
			</td>
			<td align="right" class="border_top_right4">身份证号码：</td>
			<td class="border_top_right4">
				<input type="text" name="identityCode" id="identityCode" maxlength="18" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">姓名：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name" />
			</td>
			<td align="right" class="border_top_right4">机构名称：</td>
			<td class="border_top_right4">
				<input type="text" name="orgName" id="orgName" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">营业执照编号：</td>
			<td class="border_top_right4">
				<input type="text" name="businessCode" id="businessCode" />
			</td>
			<td align="right" class="border_top_right4">手机号码：</td>
			<td class="border_top_right4">
				<input type="text" name="mobile" id="mobile" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">银行卡号：</td>
			<td class="border_top_right4">
				<input type="text" name="bankCode" id="bankCode" />
			</td>
			<td align="right" class="border_top_right4">邮箱地址：</td>
			<td class="border_top_right4">
				<input type="text" name="email" id="email" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">URL地址：</td>
			<td class="border_top_right4">
				<input type="text" name="urlAddress" id="urlAddress" />
			</td>
			<td align="right" class="border_top_right4">URL跳转地址：</td>
			<td class="border_top_right4">
				<input type="text" name="urlBranchAddress" id="urlBranchAddress" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4">
				<input type="button" name="butSubmit" value="预警核查" class="button2" onclick="validateForm('黑名单核查');" />&nbsp;&nbsp;
				<input type="button" name="butSubmit" value="数目查询" class="button2" onclick="validateForm('记录数查询');" />&nbsp;&nbsp;
				<input type="button" name="butSubmit" value="明细查询" class="button2" onclick="validateForm('黑名单查询');" />
			</td>
		</tr>
	</table>
</form>

<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,请稍候...
</div>
<script type="text/javascript">
	function validateForm(businessType){
		var sum = 0;
		var sbm = $("#sbm").val();
		var occuredArea = $("#occuredArea").val();
		var identityCode = $("#identityCode").val();
		var name = $("#name").val();
		var orgName = $("#orgName").val();
		var businessCode = $("#businessCode").val();
		var mobile = $("#mobile").val();
		var bankCode = $("#bankCode").val();
		var email = $("#email").val();
		var urlAddress = $("#urlAddress").val();
		var urlBranchAddress = $("#urlBranchAddress").val();
		if($.trim(sbm).length < 1){
			alert("用户唯一识别码不能为空");
			$("#sbm").focus();
			return;
		}
		if($.trim(occuredArea).length < 1){
			alert("业务发生地不能为空");
			$("#occuredArea").focus();
			return;
		}
		if($.trim(identityCode).length > 0){
			sum += 1;
		}
		if($.trim(name).length > 0){
			sum += 1;
		}
		if($.trim(orgName).length > 0){
			sum += 1;
		}
		if($.trim(businessCode).length > 0){
			sum += 1;
		}
		if($.trim(mobile).length > 0){
			sum += 1;
		}
		if($.trim(bankCode).length > 0){
			sum += 1;
		}
		if($.trim(email).length > 0){
			sum += 1;
		}
		if($.trim(urlAddress).length > 0){
			sum += 1;
		}
		if($.trim(urlBranchAddress).length > 0){
			sum += 1;
		}
		if(sum == 0){
			alert("用户唯一识别码、业务发生地、黑名单类型外必须至少输入一个查询项");
			return;
		}
		search(businessType);
	}

	function search(businessType){
		$('#infoLoadingDiv').dialog('open');
		$("#businessType").val(businessType);
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "context_fundout_foblacklistquery.controller.htm?method=search",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>