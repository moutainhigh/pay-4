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
			<c:if test="${ebillBankList != null}">修改银行</c:if>
			<c:if test="${ebillBankList == null}">新增银行</c:if>
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="ebill_bankList.do?method=save" method="post" id="ebillbanklist_add_form" name="ebillbanklist_add_form" onsubmit="return sub();">
     <table class="border_all2 inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
     	<c:if test="${ebillBankList != null}">
     		<input type="hidden" id="sequenceId" name="sequenceId" value="${ebillBankList.sequenceId}" />
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">银行卡类型:</th>
             <td class="border_top_right4">
	             <select id="bankType" name="bankType" onchange="getBanks(this.value)">
	             	<option value="-1" <c:if test="${ebillBankList.bankType == -1}">selected</c:if>>请选择</option>
	             	<option value="0" <c:if test="${ebillBankList.bankType == 0}">selected</c:if>>信用卡</option>
	             	<option value="1" <c:if test="${ebillBankList.bankType == 1}">selected</c:if>>储蓄卡</option>
	             </select>
             </td>
            </tr> 
            <!--<tr class="trbgcolor10">
				<th class="border_top_right4 must">银行代码</th>
				<td class="border_top_right4">
					<input type="text" name="bankCode" id="bankCode" maxlength="20" size="30" value="${ebillBankList.bankCode}"/>
				</td>
			</tr>
            --><tr>
             <th class="border_top_right4 must">银行名称:</th>
             <td class="border_top_right4">
             	<select id="bankName" name="bankName">
             		<option value="-1">请选择</option>
             		<c:forEach var="bank" items="${banks}">
             			<option value="${bank.key},${bank.value}" <c:if test="${ebillBankList.bankCode == bank.key}">selected</c:if>>${bank.value}</option>
             		</c:forEach>
	             </select>
            </td>
            </tr>
            <!--<tr class="trbgcolor10">
             <th class="border_top_right4 must">银行机构代码:</th>
             <td class="border_top_right4"><input type="text" name="orgCode" id="orgCode" maxlength="20" size="30" value="${ebillBankList.orgCode}"/></td>
            </tr> -->
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">是否有效:</th>
             <td class="border_top_right4">
	             <select id="flag" name="flag">
             		<option value="-1">请选择</option>
	             	<option value="0" <c:if test="${ebillBankList.flag == 0}">selected</c:if>>无效</option>
	             	<option value="1" <c:if test="${ebillBankList.flag == 1}">selected</c:if>>有效</option>
	             </select>
	         </td>
            </tr>
            <tr>
             <th class="border_top_right4">排序:</th>
             <td class="border_top_right4"><input type="text" name="indexNum" id="indexNum" maxlength="5" size="30" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${ebillBankList.indexNum}"/></td>
            </tr>
       	</c:if>
       	<c:if test="${ebillBankList == null}">
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">银行卡类型:</th>
             <td class="border_top_right4">
	             <select id="bankType" name="bankType" onchange="getBanks(this.value)">
	             	<option value="-1">请选择</option>
	             	<option value="0">信用卡</option>
	             	<option value="1">储蓄卡</option>
	             </select>
             </td>
            </tr> 
            <!--<tr class="trbgcolor10">
             <th class="border_top_right4 must">银行代码:</th>
             <td class="border_top_right4"><input type="text" name="bankCode" id="bankCode" maxlength="20" size="30" value=""/></td>
            </tr>
            --><tr>
             <th class="border_top_right4 must">银行名称:</th>
             <td class="border_top_right4">
             	<select id="bankName" name="bankName">
	             	<option value="-1">请选择</option>
	             </select>
			</td>
            </tr>
            <!-- <tr class="trbgcolor10">
             <th class="border_top_right4 must">银行机构代码:</th>
             <td class="border_top_right4"><input type="text" name="orgCode" id="orgCode" maxlength="20" size="30" value=""/></td>
            </tr> -->
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">是否有效:</th>
             <td class="border_top_right4">
	             <select id="flag" name="flag">
	             	<option value="-1">请选择</option>
	             	<option value="0">无效</option>
	             	<option value="1">有效</option>
	             </select>
	         </td>
            </tr>
            <tr>
             <th class="border_top_right4">排序:</th>
             <td class="border_top_right4"><input type="text" name="indexNum" id="indexNum" maxlength="5" size="30" onkeyup="this.value=this.value.replace(/\D/g,'')" value=""/></td>
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
	function retn(){
		document.getElementById("ebillbanklist_add_form").action = "ebill_bankList.do";
		document.getElementById("ebillbanklist_add_form").submit();
	}

	function getBanks(typeValue){
		if(typeValue != "-1"){
			$.get("${ctx}/ebill_bankList.do?method=getAjaxBanks&bankType="+typeValue, function(data){
				$("#bankName").empty();
				$("#bankName").append(data);
			});
		}else{
			$("#bankName").empty();
			$("#bankName").append("<option value='-1'>请选择</option>");
		}
		
	}

	function sub(){
		var bankType = $("#bankType").val();
		var bankName = $("#bankName").val();
		var flag = $("#flag").val();
		var msg = "";
		if(bankType == "-1")
			msg += "请选择银行卡类型！\n";
		if(flag == "-1")
			msg += "请选择是否有效！\n";
		if(bankName == "-1")
			msg += "请选择银行名称！\n";

		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
	}
	
	$(document).ready(function(){
		$(".must").each(function(i){
			$(this).html("<span style='color:red'>*</span>"+$(this).html());
		 });
	});
</script>
</body>
</html>
