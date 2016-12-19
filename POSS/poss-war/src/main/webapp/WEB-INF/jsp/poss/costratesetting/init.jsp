<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<head>
<title>成本费率配置</title>
<script type="text/javascript">
		function userQuery(pageNo,totalCount,pageSize){
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/costRateSetting.do?method=list",
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
	<table width="200" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">成本费率配置</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
	</table>
	<br>

	<form id="form1" name="form1" action="${ctx}/report/costRateSetting.do?method=toAdd" method="post">
	  	<table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	    <tr>
	    	<td align="left">
				<input type="radio" name="fifoChannel" id="fifoChannel" value="1" onclick="userQuery();" ${fifo==1 ? "Checked" : ""}>入款成本费率 &nbsp;&nbsp;&nbsp;
				<input type="radio" name="fifoChannel" id="fifoChannel" value="2" onclick="userQuery();" ${fifo==2 ? "Checked" : ""}>出款成本费率
		    </td>		    
	      	<td  align="center">
	      	 	&nbsp;
	      	</td>
	    </tr>
	    <tr>
	      	<td  align="center">
	      	 	&nbsp;
	      	</td>
	      	<td align="right">
	      		<input type="submit" name="addPolicyBtn" value="新增费率" class="button2" >
	      		
	      		<!-- 
		      		<input type="button" onclick="javascript:window.location.href='${ctx}/report/costRateSetting.do?method=toAdd?'"  name="addPolicyBtn" value="新增费率配置" class="button2">
	      		 -->
	        </td>
	    </tr>	    
	  </table>
	 <br>
	 <c:if test="${not empty message}">
	 	<div>
			${message}
		</div>
	 </c:if>
	 </form>
	<div align="center" id="paginationResult"></div>
	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
				<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>
	<p>&nbsp;</p>
</body>
