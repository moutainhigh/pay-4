<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">卡bin过滤配置</h2>
<form action="" method="post" id="mainfromId">
	<input type="hidden" name="queryHisStatus" value="0"/>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input name="partnerId" id="partnerId" value="${partnerId}" />
	      </td>
	      <td align="right" class="border_top_right4" >卡号：</td>
	      <td class="border_top_right4">
	      	<input name="cardNo" id="cardNo" value="${cardNo}"/>
	      </td>
	      <td align="right" class="border_top_right4" >创建时间</td>
	      <td class="border_top_right4">
	      	<input class="Wdate" type="text" id="effectDate" value="${effectDate}" name="effectDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'expireDate\')}'})">
				        	～
						<input class="Wdate" type="text" id="expireDate" name="expireDate" value="${expireDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'effectDate\')}'})">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>

<c:if test="${not empty responseCode && responseCode != '0000'}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">

	/* $(document).ready(function(){
		search();
	});  */
	
	function toAdd(){
		window.location.href="${ctx}/cardbin/cardfilter.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		re = new RegExp("^[0-9]+$")
		var partnerId = $('#partnerId').val();
		if ("" != partnerId && !re.test(partnerId))
		{
			alert("商户会员号必须为数字！");
			return false;
		}
		$.ajax({
			type: "POST",
			url: "${ctx}/cardbin/cardfilter.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>