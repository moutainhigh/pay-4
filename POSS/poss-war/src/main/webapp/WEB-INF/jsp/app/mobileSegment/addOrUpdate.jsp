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
			编辑手机号段维护
			</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="mobileSegment.do?method=save" method="post" id="mobileSegment_add_form" name="mobileSegment" onsubmit="return sub();">
     <table class="border_all2 inputTable" width="600" border="0" cellspacing="0" cellpadding="3" align="center">
     	<c:if test="${mobileSegmentDto != null}">
            <tr class="trbgcolor10">
             <th class="border_top_right4">手机号段</th>
             <td class="border_top_right4"><input type="text" name="mobileNo" id="mobileNo" size="50" value="${mobileSegmentDto.mobileNo}" readonly="readonly"/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">省份名称</th>
             <td class="border_top_right4"><select id="provinceName" name="provinceName" onclick="getCitys(this.value)">
	             	<option value="-1">请选择</option>
	             	<c:forEach var="pro" items="${provinceDTOList}">
	             		<option value="${pro.provincecode},${pro.provincename}" <c:if test="${mobileSegmentDto.provinceCode == pro.provincecode}">selected="selected"</c:if>>${pro.provincename}</option>
	             	</c:forEach>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">城市名称</th>
             <td class="border_top_right4"><select id="cityName" name="cityName">
	             	<option value="-1">请选择</option>
	             	<c:forEach var="city" items="${cityList}">
	             		<option value="${city.citycode},${city.cityname}" <c:if test="${mobileSegmentDto.cityCode == city.citycode}">selected="selected"</c:if>>${city.cityname}</option>
	             	</c:forEach>
	             </select></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">通信商家</th>
             <td class="border_top_right4"><select id="bossType" name="bossType">
	             	<option value="-1" <c:if test="${mobileSegmentDto.bossType == -1}">selected="selected"</c:if>>请选择</option>
	             	<option value="0" <c:if test="${mobileSegmentDto.bossType == 0}">selected="selected"</c:if>>移动</option>
	             	<option value="1" <c:if test="${mobileSegmentDto.bossType == 1}">selected="selected"</c:if>>联通</option>
	             	<option value="2" <c:if test="${mobileSegmentDto.bossType == 2}">selected="selected"</c:if>>电信</option>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">号段类别</th>
             <td class="border_top_right4"><select id="mobileType" name="mobileType">
	             	<option value="-1">请选择</option>
	             	<c:forEach var="mt" items="${mobileTypes}">
	             		<option value="${mt.key}" <c:if test="${mobileSegmentDto.mobileType == mt.key}">selected="selected"</c:if>>${mt.value}</option>
	             	</c:forEach>
	             </select></td>
            </tr>
       	</c:if>
       	<c:if test="${mobileSegmentDto == null}"> 
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">手机号段</th>
             <td class="border_top_right4"><input type="text" name="mobileNo" id="mobileNo" size="50" maxlength="7" onkeyup="this.value=this.value.replace(/\D/g,'')" value=""/></td>
            </tr>
            <tr>
             <th class="border_top_right4 must">省份名称</th>
             <td class="border_top_right4"><select id="provinceName" name="provinceName" onclick="getCitys(this.value)">
	             	<option value="-1">请选择</option>
	             	<c:forEach var="pro" items="${provinceDTOList}">
	             		<option value="${pro.provincecode},${pro.provincename}">${pro.provincename}</option>
	             	</c:forEach>
	             </select></td>
            </tr>
            <tr class="trbgcolor10">
             <th class="border_top_right4 must">城市名称</th>
             <td class="border_top_right4"><select id="cityName" name="cityName">
	             	<option value="-1">请选择</option>
	             </select></td>
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
             <th class="border_top_right4 must">号段类别</th>
             <td class="border_top_right4"><select id="mobileType" name="mobileType">
	             	<option value="-1">请选择</option>
	             	<c:forEach var="mt" items="${mobileTypes}">
	             		<option value="${mt.key}">${mt.value}</option>
	             	</c:forEach>
	             </select></td>
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

	function getCitys(provincecode){
		if(provincecode != "-1"){
			$.get("${ctx}/mobileSegment.do?method=getCityList&provincecode="+provincecode, function(data){
				$("#cityName").empty();
				$("#cityName").append("<option value='-1'>请选择</option>");
				$("#cityName").append(data);
			});
		}else{
			$("#cityName").empty();
			$("#cityName").append("<option value='-1'>请选择</option>");
		}
		
	}

	function sub(){
		var mobileNo = document.getElementById("mobileNo").value;
		var provinceName = document.getElementById("provinceName").value;
		var cityName = document.getElementById("cityName").value;
		var bossType = document.getElementById("bossType").value;
		var mobileType = document.getElementById("mobileType").value;
		var msg = "";
		if(mobileNo == ""){
			msg += "手机号段不能为空！\n";
		}else if(mobileNo.length < 7){
			msg += "请输入7位手机号段！\n";
		}else if(mobileNo.substring(0,1) != 1 && (mobileNo.substring(1,2) != 3 || mobileNo.substring(1,2) != 4 || mobileNo.substring(1,2) != 5 || mobileNo.substring(1,2) != 8)){
			msg += "请输入正确的手机号段！\n";
		}
		if(provinceName == "-1")
			msg += "请选择省份！\n";
		if(cityName == "-1")
			msg += "请选择城市！\n";
		if(bossType == "-1")
			msg += "请选择通信商家！\n";
		if(mobileType == "-1")
			msg += "请选择号段类别！\n";

		if(msg != ""){
			alert(msg);
			return false;
		}
		return true;
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
