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
			新增关联产品
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="mobileProduct.do?method=addBeRelated" method="post" id="beRelated_index_form" name="beRelated_index_form">
	<table class="searchTermTable">
		<tr>
			<td class="textRight">关联商家：</td>
			<td class="textLeft"><input type="text" name="mechantId" id="mechantId" style="width: 150px;" maxlength="20" size="30" value=""/><font color="red"> *请输入登录账号 (如果是首页固定值，请直接输入“0000”)</font></td>
			<td class="textRight">
				<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="add()">
					<span class="ui-icon ui-icon-search"></span>&nbsp;新&nbsp;&nbsp;增&nbsp;
				</a>&nbsp;&nbsp;&nbsp;
				<a href="${ctx}/mobileProduct.do?method=beRelated" class="dialog_link ui-state-default ui-corner-all">
					<span class="ui-icon ui-icon-newwin"></span>&nbsp;返&nbsp;&nbsp;回&nbsp;
				</a>
			</td>
		</tr>
	</table>
	<p></p>
	<table id="beRelatedTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead>
	   <tr>
			<th><input type="checkbox" id="all" name="all" onclick="chekall(this)"/></th>
	       <th>产品名称</th>
	       <th>产品折扣</th>
	       <th>产品金额</th>
	       <th>通信商家</th>
	       <th>产品金额</th>
	     </tr>
	 </thead>
	  <tbody>
	   <c:forEach items="${mobileProductDtoList}" var="list">
	     <tr>
	     	 <td><input type="checkbox" name="pId" value="${list.productId},${list.chargeAmount},${list.bossType}"/></td>
	         <td>${list.productName}</td>
	         <td>${list.discount}</td>
	         <td>${list.chargeAmount}</td>
	         <td><c:if test="${list.bossType == 0}">移动</c:if>
	    	<c:if test="${list.bossType == 1}">联通</c:if>
	    	<c:if test="${list.bossType == 2}">电信</c:if></td>
	         <td>${list.maxWaitingTime}</td>
	     </tr>
		</c:forEach>
	  </tbody>
	</table>
  </form>
</body>
<script type="text/javascript">
if('${errorMsg}' != "")
	alert('${errorMsg}');

	function chekall(checkObj){
		var objForm = document.forms("beRelated_index_form");
		var lenth = objForm.length;
		for(var x=0;x<lenth;x++){
			if (checkObj.checked == true){
	            if (objForm.elements[x].type == "checkbox"){
	                objForm.elements[x].checked = true;
	            }
	        }else{
	            if (objForm.elements[x].type == "checkbox"){
	                objForm.elements[x].checked = false;
	            }
	        }
		}
	}

	function add(){
		var objForm = document.forms("beRelated_index_form");
		var mechantId = document.getElementById("mechantId").value;
		var arr = Array();
		var flag = false;
		var num = 0;
		for(var x=0;x<objForm.length;x++){
			if(objForm[x].type=="checkbox"){
				if(objForm[x].checked){
					flag = true;
					arr[num]=objForm[x].value.split(',')[1]+","+objForm[x].value.split(',')[2];
					num++;
				}
			}
			/*objForm[x].type=="checkbox" ? (objForm[x].checked?flag=true : "") : "";
			if(flag){
				arr[x]=objForm[x].value.split(',')[1]+","+objForm[x].value.split(',')[2];
			} */
		}
		if(mechantId == ""){
			alert("请输入关联商家账户！");
			return false;
		}
		if(!flag){
			alert("请勾选充值产品！");
		}else{
			if(arr.length > 1){
				for(var i=0;i<arr.length;i++){
					for(var y=i+1;y<arr.length;y++){
						if(arr[i] == arr[y]){
							alert("对不起！同一关联商家不能关联运营商和充值金额相同的充值产品！");
							return false;
						}
					}
				}
			}
		}
		objForm.submit();
	}
</script>
</html>
