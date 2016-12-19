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
			批量修改号段
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="mobileSegment.do?method=patchSave" method="post" id="mobileSegment_add_form" name="mobileSegment" onsubmit="return sub();">
     <table class="border_all2 inputTable" width="600" border="0" cellspacing="0" cellpadding="3" align="center">
   		<c:forEach var="mobileNo" items="${mobileNos}">
       		<input type="hidden" name="mobileNos" value="${mobileNo}"/> 
       	</c:forEach>
        <tr>
         <td rowspan="2"><input type="checkbox" id="provinceName_ch" name="provinceName_ch" checked="checked" onclick="chang(this.name)"/></td>
         <th class="border_top_right4">省份名称</th>
         <td class="border_top_right4"><select id="provinceName" name="provinceName" onclick="getCitys(this.value)">
          	<c:forEach var="pro" items="${provinceDTOList}">
          		<option value="${pro.provincecode},${pro.provincename}">${pro.provincename}</option>
          	</c:forEach>
          </select></td>
        </tr>
        <tr class="trbgcolor10">
         <th class="border_top_right4">城市名称</th>
         <td class="border_top_right4"><select id="cityName" name="cityName">
          	<c:forEach var="city" items="${cityList}">
           		<option value="${city.citycode},${city.cityname}" <c:if test="${mobileSegmentDto.cityCode == city.citycode}">selected="selected"</c:if>>${city.cityname}</option>
           	</c:forEach>
          </select></td>
        </tr>
        <tr>
         <td><input type="checkbox" id="bossType_ch" name="bossType_ch" onclick="chang(this.name)"/></td>
         <th class="border_top_right4">通信商家</th>
         <td class="border_top_right4"><select id="bossType" name="bossType" disabled="disabled">
          	<option value="0">移动</option>
          	<option value="1">联通</option>
          	<option value="2">电信</option>
          </select></td>
        </tr>
        <tr class="trbgcolor10">
         <td><input type="checkbox" id="mobileType_ch" name="mobileType_ch" onclick="chang(this.name)"/></td>
         <th class="border_top_right4">号段类别</th>
         <td class="border_top_right4"><select id="mobileType" name="mobileType" disabled="disabled">
          	<c:forEach var="mt" items="${mobileTypes}">
          		<option value="${mt.key}">${mt.value}</option>
          	</c:forEach>
          </select></td>
        </tr>
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
	var objForm = document.forms("mobileSegment_add_form");

	if('${errorMsg}' != "")
		alert('${errorMsg}');

	function getCitys(provincecode){
		$.get("${ctx}/mobileSegment.do?method=getCityList&provincecode="+provincecode, function(data){
			$("#cityName").empty();
			$("#cityName").append(data);
		});
	}

	function isCheck(){
		var flag = false;
		for(var x=0;x<objForm.length;x++){
			if(objForm[x].type=="checkbox"){
				if(objForm[x].checked){
					flag = true;
				}
			}
		}
		return flag;
	}

	function chang(tabName){
		var flag = false;
		var name = tabName.split("_")[0];
		if($("#"+tabName).attr("checked") == true)
			flag = true;
		if(name == "provinceName"){
			$("#provinceName").attr("disabled", !flag);
			$("#cityName").attr("disabled", !flag);
		}else{
			$("#"+name).attr("disabled", !flag);
		}
	}

	function sub(){
		if(!isCheck()){
			alert("请至少勾选一项记录进行修改！");
			return false;
		}else{
			objForm.submit();
		}
	}

	function retn(){
		
		document.getElementById("mobileSegment_add_form").action = "mobileSegment.do";
		document.getElementById("mobileSegment_add_form").submit();
	}
	
	$(document).ready(function(){
		$(".must").each(function(i){
			$(this).html("<span style='color:red'>*</span>"+$(this).html());
		 });
	});
</script>
</body>
</html>
