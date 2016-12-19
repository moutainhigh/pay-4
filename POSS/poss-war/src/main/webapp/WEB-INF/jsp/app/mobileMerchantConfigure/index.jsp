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
					<font class="titletext">个 人 应 用 商 户 配 置 维 护</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="queryMobileMerchantConfigure.do?method=query" method="post" id="mobileMerchantConfig_query_form" name="mobileMerchantConfig_query_form">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">商户登录名：</td>
				<td class="textLeft"><input type="text" name="merchantId" id="merchantId" style="width: 150px;" value="${command.merchantId}"/></td>
				<td class="textRight">通知地址：</td>
				<td class="textLeft"><input type="text" name="notifyUrl" id="notifyUrl" style="width: 150px;" value="${command.notifyUrl}"/></td>
				<td class="textRight">IP校验：</td>
				<td class="textLeft"><select id="ipCheckFlag" name="ipCheckFlag" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.ipCheckFlag == -1}">selected="selected"</c:if>>全部</option>
	             	<option value="0" <c:if test="${command.ipCheckFlag == 0}">selected="selected"</c:if>>否</option>
	             	<option value="1" <c:if test="${command.ipCheckFlag == 1}">selected="selected"</c:if>>是</option>
	             </select></td>
	             <td class="textRight">渠道开关：</td>
				<td class="textLeft"><select id="flag" name="flag" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.flag == -1}">selected="selected"</c:if>>全部</option>
	             	<option value="0" <c:if test="${command.flag == 0}">selected="selected"</c:if>>关</option>
	             	<option value="1" <c:if test="${command.flag == 1}">selected="selected"</c:if>>开</option>
	             </select></td>
             </tr>
             <tr>
             	<td class="textRight">配置类别：</td>
				<td class="textLeft"><select id="conType" name="conType" style="width: 150px;">
	             	<option value="-1" <c:if test="${command.conType == -1}">selected="selected"</c:if>>全部</option>
	             	<option value="0" <c:if test="${command.conType == 0}">selected="selected"</c:if>>手机</option>
	             	<option value="1" <c:if test="${command.conType == 1}">selected="selected"</c:if>>缴费</option>
	             </select></td>
				<td class="textRight" colspan="6">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/addMobileMerchantConfigure.do?method=addOrUpdate" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加配置&nbsp;
					</a>
				</td>
			</tr>
		</table>
	</form>
<p></p>
	<table id="mobileMerchantConfigTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead>
	   <tr>
	       <th>商户登录名</th>
	       <th>IP校验</th>
	       <th>IP地址列表</th>
	       <th>渠道开关</th>
	       <th>商户公钥</th>
	       <th>通知地址</th>
	       <th>省份列表</th>
	       <th>单日限额</th>
	       <th>通知次数</th>
	       <th>账单类型</th>
	       <th>配置类别</th>
	       <th>操作</th>
	     </tr>
	 </thead>
	  <tbody>
	   <c:forEach items="${mobileMerchantConfigureDtoList}" var="list">
	      <tr>
	          <td style="width:120px; word-break:break-all;overflow:hidden">${list.merchantId}</td>
	          <td style="width:38px; word-break:break-all;overflow:hidden">
	          	<c:if test="${list.ipCheckFlag == 0}">否</c:if>
	          	<c:if test="${list.ipCheckFlag == 1}">是</c:if>
	          </td>
	          <td style="width:90px; word-break:break-all;overflow:hidden">${list.ips}</td>
	          <td style="width:30px; word-break:break-all;overflow:hidden">
	          	<c:if test="${list.flag == 0}">关</c:if>
	          	<c:if test="${list.flag == 1}">开</c:if>
	          </td>
	          <td style="width:380px; word-break:break-all;overflow:hidden">${list.merchantPublicKey}</td>
	          <td style="width:120px; word-break:break-all;overflow:hidden">${list.notifyUrl}</td>
	          <td style="width:100px; word-break:break-all;overflow:hidden">${list.provinces}</td>
	          <td style="width:55px; word-break:break-all;overflow:hidden">${list.limitAmount}</td>
	          <td style="width:30px; word-break:break-all;overflow:hidden">${list.notifyTimes}</td>
	          <td style="width:55px; word-break:break-all;overflow:hidden">${list.payType}</td>
	          <td style="width:30px; word-break:break-all;overflow:hidden">
	          	<c:if test="${list.conType == 0}">手机充值</c:if>
	          	<c:if test="${list.conType == 1}">缴费</c:if>
	          </td>                  
	          <td style="width:70px; word-break:break-all;overflow:hidden">  
	          <a href="<c:url value='updateMobileMerchantConfigure.do?method=addOrUpdate&sequenceId=${list.sequenceId}' />">修改</a> | 
	          <a href="#" onclick="del('${list.sequenceId}')">删除</a></td>
	      </tr>
	    </c:forEach>   
	  </tbody>
	</table>
</body>
<script type="text/javascript">

	function query(){
		document.getElementById("mobileMerchantConfig_query_form").submit();
	}

	function del(sequenceId){
		if(confirm("确定删除该项吗？"))	{
			window.location.href = "${ctx}/deleteMobileMerchantConfigure.do?method=delete&sequenceId="+sequenceId;  
		}
	}
</script>
</html>
