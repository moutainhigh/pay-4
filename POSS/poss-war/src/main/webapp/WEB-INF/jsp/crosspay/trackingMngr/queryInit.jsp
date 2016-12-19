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
		<div align="center"><font class="titletext">运单管理</font></div>
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
	      <td align="right" class="border_top_right4" >订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId">
	      </td>
	      <td align="right" class="border_top_right4" >快递公司：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="expressCom" name="expressCom">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >运单状态：</td>
	      <td class="border_top_right4">
	      	<select id="trackingStatus" name="trackingStatus">
	      		<option value="">请选择</option>
	      		<option value="0">未上传运单</option>
	      		<option value='1'>待审核</option>
				<option value='2'>审核通过</option>
				<option value='3'>审核未通过</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >快递单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="trackingId" name="trackingId">
	      </td>
	     </tr>
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">运单上传起止日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="uploadStartTime"  name="uploadStartTime" value='<fmt:formatDate value="${webQueryRefundDTO.uploadStartTime}" type="date"/>'  onClick="WdatePicker({minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'uploadEndTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="uploadEndTime" name="uploadEndTime"  value='<fmt:formatDate value="${webQueryRefundDTO.uploadEndTime}" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'uploadStartTime\')}',maxDate:'%y-%M-%d'})">
      	</td>
      	<td class=border_top_right4 align="right" ></td>
      	<td class="border_top_right4" >
      	</td>
    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
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
			url: "${ctx}/crosspay/trackingMngr.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>