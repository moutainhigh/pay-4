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
		<div align="center"><font class="titletext">交易查询</font></div>
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
	      <td align="right" class="border_top_right4" >交易号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >商户号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >订单状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status" class="inp_normal w100">
				<option value="all" selected="selected">全部</option>
				<option value="1">交易成功</option>
				<option value="2">拒付</option>
				<option value="3">退款</option>
				<option value="3">冻结</option>
			</select>
	      </td>
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId">
	      </td>
	     </tr>
	      <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >订单时间：</td>
	      <td class="border_top_right4">
	      	<input type="text" class="Wdate inp_normal w380" name="startTime"  readonly="readonly" id="startTime" gtbfieldid="109" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen',isShowClear:false})">
			<span>-</span>
			<input type="text" class="Wdate inp_normal w380" name="endTime" readonly="readonly" id="endTime" gtbfieldid="110" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',skin:'whyGreen',isShowClear:false})">
		
	      </td>
	      <td align="right" class="border_top_right4" ></td>
	      <td class="border_top_right4">
	      	
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="2">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	       <input type="button"  name="butDownLoad" value="下  载" class="button2" onclick="download();">
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
			url: "${ctx}/crosspay/orderQuery.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function download() {
		document.mainfromId.action = "${ctx}/crosspay/orderQuery.htm?method=excelDownload";
		document.mainfromId.submit();
		document.mainfromId.action = "";
	}
  </script>