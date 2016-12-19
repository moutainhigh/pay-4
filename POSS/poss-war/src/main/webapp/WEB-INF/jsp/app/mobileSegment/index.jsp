<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">手 机 号 段 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="queryMobileSegment.do?method=search" method="post" id="mobileSegment_query_form" name="mobileSegment_query_form">
		<table class="searchMobileSegment">
			<tr>
				<td class="textRight">手机号段：</td>
				<td class="textLeft"><input type="text" name="mobileNo" id="mobileNo" style="width: 150px;" value="${command.mobileNo}"/></td>
				<td class="textRight">省份名称：</td>
				<td class="textLeft"><select id="provinceCode" name="provinceCode" style="width: 150px;" onclick="getCitys(this.value)">
	             	<option value="-1" <c:if test="${command.provinceCode == -1}">selected="selected"</c:if>>请选择</option>
	             	<c:forEach var="pro" items="${provinceDTOList}">
	             		<option value="${pro.provincecode}" <c:if test="${command.provinceCode == pro.provincecode}">selected="selected"</c:if>>${pro.provincename}</option>
	             	</c:forEach>
	             </select></td>
				<td class="textRight">城市名称：</td>
				<td class="textLeft"><select id="cityName" name="cityName" style="width: 150px;">
					<option value="-1">请选择</option>
					<c:forEach var="city" items="${cityList}">
	             		<option value="${city.citycode},${city.cityname}" <c:if test="${command.cityCode == city.citycode}">selected="selected"</c:if>>${city.cityname}</option>
	             	</c:forEach>
	             </select></td>
				<td class="textRight">通信商家：</td>
				<td class="textLeft"><select id="bossType" name="bossType" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.bossType == -1}">selected="selected"</c:if>>请选择</option>
	             	<option value="0" <c:if test="${command.bossType == 0}">selected="selected"</c:if>>移动</option>
	             	<option value="1" <c:if test="${command.bossType == 1}">selected="selected"</c:if>>联通</option>
	             	<option value="2" <c:if test="${command.bossType == 2}">selected="selected"</c:if>>电信</option>
	             </select></td>
				<td class="textRight">号段类别：</td>
				<td class="textLeft"><select id="mobileType" name="mobileType" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.mobileType == -1}">selected="selected"</c:if>>请选择</option>
	             	<c:forEach var="mt" items="${mobileTypes}">
	             		<option value="${mt.key}" <c:if test="${command.mobileType == mt.key}">selected="selected"</c:if>>${mt.value}</option>
	             	</c:forEach>
	             </select></td>
	          </tr>
	          <tr></tr>
	          <tr>
				<td class="textRight" colspan="10">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="reset()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;重&nbsp;&nbsp;置&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/addMobileSegment.do?method=addOrUpdate" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加号段&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="del()">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;删除号段&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="patchUpdate()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;批量修改&nbsp;
					</a>
				</td>
			</tr>
		</table>
	
		<p></p>
		<table id="mobileSegmentTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		  <thead>
		   <tr>
		       <th><input type="checkbox" id="all" name="all" onclick="chekall(this)"/></th>
		       <th>手机号段</th>
		       <th>省份</th>
		       <th>城市</th>
		       <th>通信商家</th>
		       <th>号段类型</th>
		       <th>修改</th>
		     </tr>
		 </thead>
		  <tbody>
		   <c:forEach items="${page.result}" var="mobileSegment">
		      <tr>
		      	  <td><input type="checkbox" name="mobileNos" value="${mobileSegment.mobileNo}"/></td>
		          <td>${mobileSegment.mobileNo}</td>
		          <td>${mobileSegment.provinceName}</td>
		          <td>${mobileSegment.cityName}</td>
		          <td><c:if test="${mobileSegment.bossType == 0}">移动</c:if>
		         	<c:if test="${mobileSegment.bossType == 1}">联通</c:if>
		         	<c:if test="${mobileSegment.bossType == 2}">电信</c:if>
	         	  </td>
		          <td>
		          	<c:forEach var="mt" items="${mobileTypes}">
	             		<c:if test="${mobileSegment.mobileType == mt.key}">${mt.value}</c:if>
	             	</c:forEach>
		          </td>
		          <td>  <a href="<c:url value='updateMobileSegment.do?method=addOrUpdate&mobileNo=${mobileSegment.mobileNo}' />">修改</a></td>
		      </tr>
		    </c:forEach>   
		  </tbody>
		</table>
	</form>
	<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
</body>
<script type="text/javascript">
	if('${errorMsg}' != "")
		alert('${errorMsg}');
	
	var objForm = document.forms("mobileSegment_query_form");
	
	function reset(){
		for(var x=0;x<objForm.length;x++){
			if(objForm[x].type=="text")
				objForm[x].value = "";
			else if(objForm[x].type=="select")
				objForm[x].value = "-1";
		}
	}

	function chekall(checkObj){
		var objForm = document.forms("mobileSegment_query_form");
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

	function query(pageNo,totalCount,pageSize){
		document.getElementById("mobileSegment_query_form").action = "queryMobileSegment.do?method=search&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
		document.getElementById("mobileSegment_query_form").submit();
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

	function del(){
		if(!isCheck()){
			alert("请勾选需要删除的记录！");
			return false;
		}else{
			if(confirm("确定删除所选项吗？"))	{
				objForm.action = "${ctx}/deleteMobileSegment.do?method=delete";
				objForm.submit();
			}
		}
	}

	function patchUpdate(){
		if(!isCheck()){
			alert("请勾选需要修改的记录！");
			return false;
		}else{
			objForm.action = "${ctx}/mobileSegment.do?method=update";
			objForm.submit();
		}
	}

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
</script>
</html>
