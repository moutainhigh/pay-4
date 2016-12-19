<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查询代扣商户配置</font></div>
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
	      <td align="right" class="border_top_right4" >代扣渠道编号：</td>
	      <td class="border_top_right4">
	      	<input type="text" value="" name="bankCode"/>
	      </td>
	      <td align="right" class="border_top_right4" >代扣目标银行：</td>
	      <td class="border_top_right4">
	      	<input type="text" value="" name="targetBankCode"/>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit1" value="添  加" class="button2" onclick="javascript:window.location.href='${ctx}/context_withhold_bankprotocol.relation.controller.htm?method=toAdd';">
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
		;
			$.ajax({
				type: "POST",
				url: "/context_withhold_bankprotocol.relation.controller.htm?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
  </script>