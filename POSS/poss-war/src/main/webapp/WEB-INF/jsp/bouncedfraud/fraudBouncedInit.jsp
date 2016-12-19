<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">拒付欺诈比例报表</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">拒付欺诈比例报表</h2>
<form action="" method="post" id="initForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
		<input type="hidden" name="export" id="export" value="" />
		<input type='hidden' id='orgCurrencyCode' name='orgCurrencyCode' value="">
		<td class="border_top_right4">开始时间：
	      	<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	    </td>
	    <td class="border_top_right4">结束时间：
	      	<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	     </td> 	
      	<td class='border_top_right4' align='left'>IPAY/GC：<select
					id='flag' name='flag'>
						<option value='' selected>---请选择---</option>
						<option value='5'>IPAY</option>
						<option value='8'>GC</option>
		</select>
		</td>	
      	<td class='border_top_right4' align='left'>维度：<select
					id='type' name='type'  onchange="selectType();">
						<option value='' selected>---请选择---</option>
						<option value='0'>商户</option>
						<option value='1'>渠道</option>
		</select>
		</td>
		<td id="partnerIdDiv"  class=border_top_right4 align="center" style="display: none;"  >会员号：
		<input type="text" name="partnerId" id="partnerId" />
		</td>
		</div>
		<td id="merchantNoDiv" style="display:none;"  class=border_top_right4 align="center">二级渠道号：<input type="text"
				name="merchantNo" id="merchantNo" />
		</td>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="10"><input
				type="button" onclick="query();" name="submitBtn" value="查询"
				class="button2" />
				<input
				type="button" onclick="download();" name="submitBtn" value="下载"
				class="button2" />
				<input
				type="button" onclick="orgCodeQuery();" name="submitBtn" value="渠道汇总"
				class="button2" />
			</td>
		</tr>
	</table>
</form>
<c:if test="${not empty errorMsg}">
	<li style="color: red">${errorMsg }</li>
</c:if>
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
function selectType()
{
	var type = $("#type").val();
	if(""==type)
	{
		$('#partnerIdDiv').css("display","none" );
		$('#merchantNoDiv').css("display","none" );
	}else if("0"==type)
	{
		$('#partnerIdDiv').css("display","block" );
		$("#merchantNo").val("");
		$('#merchantNoDiv').css("display","none" );
		
	}else if("1"==type)
	{
		$('#merchantNoDiv').css("display","block" );
		$("#partnerId").val("");
		$('#partnerIdDiv').css("display","none" );
	}
}
	
	function query(pageNo, totalCount, pageSize) {
		var type = $("#type").val();
		$("#export").val("");
		$("#orgCurrencyCode").val("");
		if(type == '')
		{
			alert('请选择维度！');
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type : "POST",
			url : "bounced-fraud.do?method=query",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
function orgCodeQuery(pageNo, totalCount, pageSize) {
		$("#type").val("1");
		$("#export").val("");
		$("#partnerId").val("");
		$("#merchantNo").val("");
		$("#orgCurrencyCode").val("true");
		$('#infoLoadingDiv').dialog('open');
		selectType();
		var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount + "&pageSize=" + pageSize+ "&orgcodeQuery=true";
		$.ajax({
			type : "POST",
			url : "bounced-fraud.do?method=query",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function download(){
		$("#export").val("1");
		var orgCurrencyCode = $("#orgCurrencyCode").val();
		var pars = $("#initForm").serialize()+ "&orgcodeQuery="+orgCurrencyCode;
		window.location.href = 	"${ctx}/bounced-fraud.do?method=query&"+pars;
	}
</script>