<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">拒付交易查询1</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >商户号：</td>
			<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantCode" name="merchantCode"></td>
			<td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="memberCode" name="memberCode">
	      </td>
	      <td class="border_top_right4" align="right" >商户名称：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="merchantName" name="merchantName"></td>
	      <td align="right" class="border_top_right4" >系统交易号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
			</td>
	      
	    </tr>
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >卡号：</td>
			<td class="border_top_right4" align="left" >
			<input	type="text" id="cardNo" name="cardNo"></td>
			<td align="right" class="border_top_right4" >交易邮箱：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="cardHolderEmail" name="cardHolderEmail">
	      </td>
	      <td class="border_top_right4" align="right" >交易IP：</td>
		<td class="border_top_right4" align="left" >
			<input	type="text" id="ip" name="ip"></td>
	      <td align="right" class="border_top_right4" >类别：</td>
	      <td class="border_top_right4">
	      	<select name="cpType" id="cpType">
	      		<option value="1">内部调查单</option>
	      		<option value="2">银行调查单</option>
	      		<option value="3" selected="selected">拒付单</option>
	      	</select>
			</td>
	      
	    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="下  载" class="button2" onclick="download();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
  <script type="text/javascript">
	/* $(document).ready(function(){
		search();
	});  */
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/chargeBackQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function download() {
		var pars = $("#mainfromId").serialize();
		window.location.href = "${ctx}/chargeBackQuery.do?method=download";
	}
  </script>