<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<head>
<title>入款网关结算配置</title>
<script type="text/javascript">
		function userQuery(pageNo,totalCount,pageSize){
			var configType = document.getElementById("configType").value;
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize + "&configType=" + configType;
			$.ajax({
				type: "POST",
				url: "${ctx}/prepicitation/settlementConfig.do?method=list",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		$(document).ready(function(){userQuery();});
</script>
</head>

<body>
	<table width="30%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">入款网关结算配置</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
	</table>
	<br>

	<form id="form1" name="form1" action="${ctx}/prepicitation/settlementConfig.do?method=toAdd" method="post">
	  	<table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	    <tr>
	      	<td  align="center">
	      	 	&nbsp;
	      	</td>
	      	<td align="right">
	      		<input type="submit" name="addPolicyBtn" value="配置网关结算时间" class="button4" >
	      		
	      		<!-- 
		      		<input type="button" onclick="javascript:window.location.href='${ctx}/report/costRateSetting.do?method=toAdd?'"  name="addPolicyBtn" value="新增费率配置" class="button2">
	      		 -->
	        </td>
	    </tr>	    
	  </table>
	  <table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	    <tr>
	      	<td  align="left">
	      	 	&nbsp;查询网关类型：
	      	 	<select name="configType" id="configType" onchange="changeNewCType(this);">
	      	 		<option value="" <c:if test="${configType eq '' || configType == null}">selected</c:if>>全部</option>
					<option value="1" <c:if test="${configType eq '1'}">selected</c:if>>T+0</option>
					<option value="2" <c:if test="${configType eq '2'}">selected</c:if>>T+0顺延</option>
					<option value="3" <c:if test="${configType eq '3'}">selected</c:if>>T+1</option>
					<option value="4" <c:if test="${configType eq '4'}">selected</c:if>>T+1顺延</option>
					<option value="5" <c:if test="${configType eq '5'}">selected</c:if>>T+2</option>
					<option value="6" <c:if test="${configType eq '6'}">selected</c:if>>T+2顺延</option>
		    	</select>
	        	结算网关
	      	</td>
	    </tr>	    
	  </table>
	 <c:if test="${not empty message}">
	 	<div>
	 		<font color="#FF0000">${message}</font>
		</div>
	 </c:if>
	 </form>
	<div align="center" id="paginationResult"></div>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
				<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>
	<p>&nbsp;</p>
<script language="javascript">
 function changeNewCType(obj){
	 userQuery();
}
</script>
</body>
