<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
</head>
<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">
			编辑个人应用商户配置
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="mobileMerchantConfigure.do?method=save" method="post" id="mobileMerchantConfig_add_form" name="mobileMerchantConfig_add_form" onsubmit="return sub();">
     <table class="border_all2 inputTable" width="600" border="0" cellspacing="0" cellpadding="3" align="center">
     	<c:if test="${mobileMerchantConfigureDto != null}">
     		<input type="hidden" id="sequenceId" name="sequenceId" value="${mobileMerchantConfigureDto.sequenceId}"/>
            <tr class="trbgcolor10">
             <th class="border_top_right4">商户登录名</th>
             <td class="border_top_right4"><input type="text" name="merchantId" id="merchantId" maxlength="200" size="50" value="${mobileMerchantConfigureDto.merchantId}"/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">IP校验</th>
             <td class="border_top_right4"><select id="ipCheckFlag" name="ipCheckFlag">
	             	<option value="0" <c:if test="${mobileMerchantConfigureDto.ipCheckFlag == 0}">selected="selected"</c:if>>否</option>
	             	<option value="1" <c:if test="${mobileMerchantConfigureDto.ipCheckFlag == 1}">selected="selected"</c:if>>是</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">IP地址列表</th>
             <td class="border_top_right4"><textarea name="ips" id="ips" rows="5" cols="50">${mobileMerchantConfigureDto.ips}</textarea> IP地址之间以逗号‘,’分开</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">渠道开关</th>
             <td class="border_top_right4"><select id="flag" name="flag">
	             	<option value="0" <c:if test="${mobileMerchantConfigureDto.flag == 0}">selected="selected"</c:if>>关</option>
	             	<option value="1" <c:if test="${mobileMerchantConfigureDto.flag == 1}">selected="selected"</c:if>>开</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">商户公钥</th>
             <td class="border_top_right4"><input type="text" name="merchantPublicKey" id="merchantPublicKey" maxlength="300" size="80" value="${mobileMerchantConfigureDto.merchantPublicKey}"/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">通知地址</th>
             <td class="border_top_right4"><input type="text" name="notifyUrl" id="notifyUrl" maxlength="300" size="50" value="${mobileMerchantConfigureDto.notifyUrl}"/></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">省份列表</th>
             <td class="border_top_right4"><table border="0" cellspacing="0" cellpadding="0"><tr>
	             <c:forEach items="${provinceDTOList}" var="pro" varStatus="p">
	             	<c:choose>
	             		<c:when test="${((p.index+1)%6) == 0}">
	             			<td>
	             				<input type="checkbox" id="province_${p.index}" name="provinces" value="${pro.provincecode}" 
	             					<c:forEach var="provinceCode" items="${mobileMerchantConfigureDto.provinces}">
	             						<c:if test="${provinceCode == pro.provincecode}">checked="checked"</c:if>
	             					</c:forEach>
             					/><label for="province_${p.index}">${pro.provincename}</label>
             				</td></tr><tr>
	             		</c:when>
	             		<c:otherwise>
	             			<td>
	             				<input type="checkbox" id="province_${p.index}" name="provinces" value="${pro.provincecode}" 
	             					<c:forEach var="provinceCode" items="${mobileMerchantConfigureDto.provinces}">
	             						<c:if test="${provinceCode == pro.provincecode}">checked="checked"</c:if>
	             					</c:forEach>
             					/><label for="province_${p.index}">${pro.provincename}</label>
             				</td>
	             		</c:otherwise>
	             	</c:choose>
	             </c:forEach>
	             </table></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">单日限额</th>
             <td class="border_top_right4"><input type="text" name="limitAmount" id="limitAmount" maxlength="13" size="20" value="${mobileMerchantConfigureDto.limitAmount}" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 元</td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">通知次数</th>
             <td class="border_top_right4"><input type="text" name="notifyTimes" id="notifyTimes" maxlength="5" size="10" value="${mobileMerchantConfigureDto.notifyTimes}" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 次</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">配置类别</th>
             <td class="border_top_right4"><select id="conType" name="conType" onchange="isShow(this.value)">
	             	<option value="0" <c:if test="${mobileMerchantConfigureDto.conType == 0}">selected="selected"</c:if>>手机充值</option>
	             	<option value="1" <c:if test="${mobileMerchantConfigureDto.conType == 1}">selected="selected"</c:if>>缴费</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10" id="pay" <c:if test="${mobileMerchantConfigureDto.conType == 0}">style="display: none;"</c:if> 
            	<c:if test="${mobileMerchantConfigureDto.conType == 1}">style="display: block;"</c:if>>
             <th class="border_top_right4 must">账单类型</th>
             <td class="border_top_right4"><table border="0" cellspacing="0" cellpadding="0"><tr>
	             <c:forEach items="${ebillTypeDtoList}" var="type" varStatus="p">
        			<td>
        				<input type="checkbox" id="payType_${p.index}" name="payType" value="${type.id}" 
        					<c:forEach var="payType" items="${mobileMerchantConfigureDto.payType}">
        						<c:if test="${payType == type.id}">checked="checked"</c:if>
        					</c:forEach>
       					/><label for="payType_${p.index}">${type.name}</label>
       				</td>
	             </c:forEach>
	             </tr></table></td>
            </tr>
       	</c:if>
       	<c:if test="${mobileMerchantConfigureDto == null}"> 
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">商户登录名</th>
             <td class="border_top_right4"><input type="text" name="merchantId" id="merchantId" maxlength="200" size="50" value=""/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">IP校验</th>
             <td class="border_top_right4"><select id="ipCheckFlag" name="ipCheckFlag">
	             	<option value="-1">请选择</option>
	             	<option value="0">否</option>
	             	<option value="1">是</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">IP地址列表</th>
             <td class="border_top_right4"><textarea name="ips" id="ips" rows="5" cols="50"></textarea> IP地址之间以逗号‘,’分开</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">渠道开关</th>
             <td class="border_top_right4"><select id="flag" name="flag">
	             	<option value="-1">请选择</option>
	             	<option value="0">关</option>
	             	<option value="1">开</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">商户公钥</th>
             <td class="border_top_right4"><input type="text" name="merchantPublicKey" id="merchantPublicKey" maxlength="300" size="80" value=""/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">通知地址</th>
             <td class="border_top_right4"><input type="text" name="notifyUrl" id="notifyUrl" maxlength="300" size="50" value=""/></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">省份列表</th>
             <td class="border_top_right4"><table border="0" cellspacing="0" cellpadding="0"><tr>
	             <c:forEach items="${provinceDTOList}" var="pro" varStatus="p">
	             	<c:choose>
	             		<c:when test="${((p.index+1)%6) == 0}">
	             			<td><input type="checkbox" id="province_${p.index}" name="provinces" value="${pro.provincecode}"/><label for="province_${p.index}">${pro.provincename}</label></td></tr><tr>
	             		</c:when>
	             		<c:otherwise>
	             			<td><input type="checkbox" id="province_${p.index}" name="provinces" value="${pro.provincecode}"/><label for="province_${p.index}">${pro.provincename}</label></td>
	             		</c:otherwise>
	             	</c:choose>
	             </c:forEach>
	             </table></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">单日限额</th>
             <td class="border_top_right4"><input type="text" name="limitAmount" id="limitAmount" maxlength="13" size="20" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 元</td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">通知次数</th>
             <td class="border_top_right4"><input type="text" name="notifyTimes" id="notifyTimes" maxlength="5" size="10" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 次</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">配置类别</th>
             <td class="border_top_right4"><select id="conType" name="conType" onchange="isShow(this.value)">
           		<option value="-1">请选择</option>
             	<option value="0">手机充值</option>
             	<option value="1">缴费</option>
             </select></td>
            </tr>
            <tr class="trbgcolor10" id="pay" style="display: none;">
             <th class="border_top_right4 must">账单类型</th>
             <td class="border_top_right4"><table border="0" cellspacing="0" cellpadding="0"><tr>
	             <c:forEach items="${ebillTypeDtoList}" var="type" varStatus="p">
        			<td>
        				<input type="checkbox" id="payType_${p.index}" name="payType" value="${type.id}"/><label for="payType_${p.index}">${type.name}</label>
       				</td>
	             </c:forEach>
	             </tr></table></td>
            </tr>
        </c:if>
     </table>
	<br>
	<br>
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
		<tr class="trbgcolor6" align="center">
			<td>
			 	<input type="submit" name="Submit" value="保 存">
			 	<input type="button" onclick="retn()" name="Submit2" value="返  回">
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	if('${errorMsg}' != "")
		alert('${errorMsg}');

	function sub(){
		var merchantId = document.getElementById("merchantId").value;
		var ipCheckFlag = document.getElementById("ipCheckFlag").value;
		var ips = document.getElementById("ips").value;
		var flag = document.getElementById("flag").value;
		var merchantPublicKey = document.getElementById("merchantPublicKey").value;
		var notifyUrl = document.getElementById("notifyUrl").value;
		var limitAmount = document.getElementById("limitAmount").value;
		var notifyTimes = document.getElementById("notifyTimes").value;
		var conType = document.getElementById("conType").value;
		var objForm = document.forms("mobileMerchantConfig_add_form");
		var msg = "";
		if(merchantId == "")
			msg += "商户登录名不能为空！\n";
		if(ipCheckFlag == "-1")
			msg += "请选择是否进行IP校验！\n";
		if(ips == "")
			msg += "商户IP地址列表不能为空！\n";
		if(flag == "-1")
			msg += "请选择渠道开关！\n";
		if(merchantPublicKey == "")
			msg += "商户公钥不能为空！\n";
		if(notifyUrl == "")
			msg += "通知地址不能为空！\n";
		if(limitAmount == "")
			msg += "单日限额不能为空！\n";
		if(notifyTimes == "")
			msg += "通知次数不能为空！\n";
		if(conType == "-1"){
			msg += "请选择配置类别！\n";
		}else if(conType == "1"){
			var checked = false;
			for(var x=0;x<objForm.length;x++){
				objForm[x].type=="checkbox"&&objForm[x].name=="payType" ? (objForm[x].checked?checked=true : "") : ""; 
			}
			if(!checked)
				msg += "请勾选允许商户提交的账单类别！\n";
		}
		var checking = false;
		for(var x=0;x<objForm.length;x++){
			objForm[x].type=="checkbox"&&objForm[x].name=="provinces" ? (objForm[x].checked?checking=true : "") : ""; 
		}
		if(!checking)
			msg += "请勾选允许商户提交的省份列表！\n";

		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
	}

	function isShow(select){
		if("0" == select || "-1" == select)
			$("#pay").hide();
		else
			$("#pay").show();
	}

	function retn(){
		
		document.getElementById("mobileMerchantConfig_add_form").action = "mobileMerchantConfigure.do";
		document.getElementById("mobileMerchantConfig_add_form").submit();
	}
	
	$(document).ready(function(){
		$(".must").each(function(i){
			$(this).html("<span style='color:red'>*</span>"+$(this).html());
		 });
	});
</script>
</body>
</html>
