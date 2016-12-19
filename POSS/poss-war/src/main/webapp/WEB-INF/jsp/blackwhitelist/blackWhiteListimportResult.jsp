<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">黑名单批量导入</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">黑名单批量导入</h2>
<form>
	  <c:if test="${flag ==1}">
	  	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		    <thead>
				<tr class="" align="center" valign="middle">
				    <th class=""  >行号</th>
				    <th class=""  >业务类型</th>
				    <th class=""  >匹配类型</th>
					<th class=""  >内容1</th>
					<th class=""  >内容2</th>
					<th class=""  >备注</th>
					<th class=""  >错误信息</th>
				</tr>
			</thead>
			
			<c:forEach items="${errorList}" var="result" varStatus = "personalStatus">
				<c:choose>
			       <c:when test="${personalStatus.index%2==0}">
			             <tr class="trForContent1">
			       </c:when>
			       <c:otherwise>
			             <tr class="trForContent2">
			       </c:otherwise>
				</c:choose>
				<td class="" align="center" >${result.lineNum }</td>
				<td class="" align="center" >
					<c:if test="${not empty result['0']}">
						<c:if test="${result['0'] == '1'}">卡号</c:if>
						<c:if test="${result['0'] == '2'}">邮箱</c:if>
						<c:if test="${result['0'] == '3'}">IP</c:if>
						<c:if test="${result['0'] == '4'}">国家</c:if>
						<c:if test="${result['0'] == '5'}">MAC地址</c:if>
						<c:if test="${result['0'] == '6'}">手机号码</c:if>
						<c:if test="${result['0'] == '7'}">证件号码</c:if>
						<c:if test="${result['0'] == '8'}">收货地址</c:if>
						<c:if test="${result['0'] == '9'}">账单地址</c:if>
						<c:if test="${result['0'] == '10'}">卡Bin段</c:if>
						<c:if test="${result['0'] == '11'}">IP地址段</c:if>
					</c:if>
				</td>
				<td class="" align="center" >
					<c:if test="${not empty result['1'] }">
						<c:if test="${result['1'] == '1'}">全匹配</c:if>
						<c:if test="${result['1'] == '2'}">部分匹配</c:if>
						<c:if test="${result['1'] == '3'}">包含</c:if>
						<c:if test="${result['1'] == '4'}">区段</c:if>
					</c:if>
				</td>	
				<td class="" align="center" >${result['2']}&nbsp;</td>
				<td class="" align="center" >${result['3']}&nbsp;</td>
				<td class="" align="center" >${result['4']}&nbsp;</td>	
				<td class="" align="center" >${result.errorMsg}&nbsp;</td>	
			</c:forEach>
		</table>
	</c:if>
		
   <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
   		<tr class="" align="center" valign="middle">
			<td class="" align="center" >
			<c:if test="${flag ==2}">导入失败</c:if>
			<c:if test="${flag ==3}">导入成功，共计${successCount}行</c:if>
			</td>
		</tr>
		<tr class="" align="center" valign="middle">
			<td class="" align="center" >
				<input type="button"  value="返  回" class="button2" onclick="closePage();">
			</td>
		</tr>
   </table>
<script type="text/javascript">
	function closePage() {
		window.location.href="${ctx}/blackWhiteListSearch.do?method=blackWhiteListSearchView";
	}
</script>
</form>