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
			<c:if test="${mobileProductDto != null}">修改充值产品</c:if>
			<c:if test="${mobileProductDto == null}">新增充值产品</c:if>
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="mobileProduct.do?method=save" method="post" id="mobileProduct_add_form" name="mobileProduct_add_form" onsubmit="return sub();">
     <table class="border_all2 inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
     	<c:if test="${mobileProductDto != null}">
     		<input type="hidden" id="productId" name="productId" value="${mobileProductDto.productId}" />
            <tr class="trbgcolor10">
             <th class="border_top_right4">产品名字</th>
             <td class="border_top_right4"><input type="text" name="productName" id="productName" maxlength="10" size="30" value="${mobileProductDto.productName}"/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">产品折扣</th>
             <td class="border_top_right4"><input type="text" name="discount" id="discount" maxlength="20" size="30" value="${mobileProductDto.discount}"/> 折扣范围：0.0001 ~ 1</td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">产品金额</th>
             <td class="border_top_right4"><input type="text" name="chargeAmount" id="chargeAmount" maxlength="9" size="30" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${mobileProductDto.chargeAmount}"/> 元</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">通信商家</th>
             <td class="border_top_right4"><select id="bossType" name="bossType">
	             	<option value="0" <c:if test="${mobileProductDto.bossType == 0}">selected="selected"</c:if>>移动</option>
	             	<option value="1" <c:if test="${mobileProductDto.bossType == 1}">selected="selected"</c:if>>联通</option>
	             	<option value="2" <c:if test="${mobileProductDto.bossType == 2}">selected="selected"</c:if>>电信</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">最大等待时间</th>
             <td class="border_top_right4"><input type="text" name="maxWaitingTime" id="maxWaitingTime" maxlength="9" size="30" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${mobileProductDto.maxWaitingTime}"/> 分钟</td>
            </tr>
       	</c:if>
       	<c:if test="${mobileProductDto == null}"> 
            <tr class="trbgcolor10">
             <th class="border_top_right4">产品名字</th>
             <td class="border_top_right4"><input type="text" name="productName" id="productName" maxlength="10" size="30" value=""/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">产品折扣</th>
             <td class="border_top_right4"><input type="text" name="discount" id="discount" maxlength="20" size="30" value=""/> 折扣范围：0.0001 ~ 1</td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">产品金额</th>
             <td class="border_top_right4"><input type="text" name="chargeAmount" id="chargeAmount" maxlength="9" size="30" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 元</td>
            </tr>
            <tr>
             <th class="border_top_right4 must">通信商家</th>
             <td class="border_top_right4"><select id="bossType" name="bossType">
             		<option value="-1">请选择</option>
	             	<option value="0">移动</option>
	             	<option value="1">联通</option>
	             	<option value="2">电信</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">最大等待时间:</th>
             <td class="border_top_right4"><input type="text" name="maxWaitingTime" id="maxWaitingTime" maxlength="9" size="30" value="" onkeyup="this.value=this.value.replace(/\D/g,'')"/> 分钟</td>
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
		var discount = document.getElementById("discount").value;
		var chargeAmount = document.getElementById("chargeAmount").value;
		var bossType = document.getElementById("bossType").value;
		var maxWaitingTime = document.getElementById("maxWaitingTime").value;
		var msg = "";
		if(discount != ""){
			if(isNaN(discount * 1)){
				msg += "请输入数字！\n";
			}else if((discount < 0.0001) || (discount > 1)){
				msg += "请输入合理范围的折扣！\n";
			}else if(discount.length > 6){
				msg += "请保留4位小数！\n";
			}
		}else{
			msg += "产品折扣不能为空！\n";
		}
		if(chargeAmount == "")
			msg += "产品金额不能为空！\n";
		if(maxWaitingTime == "")
			msg += "最大等待时间不能为空！\n";
		if(bossType == "-1")
			msg += "通信商家不能为空！\n";
		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
	}

	function retn(){
		
		document.getElementById("mobileProduct_add_form").action = "mobileProduct.do";
		document.getElementById("mobileProduct_add_form").submit();
	}
	
	$(document).ready(function(){
		$(".must").each(function(i){
			$(this).html("<span style='color:red'>*</span>"+$(this).html());
		 });
	});
</script>
</body>
</html>
