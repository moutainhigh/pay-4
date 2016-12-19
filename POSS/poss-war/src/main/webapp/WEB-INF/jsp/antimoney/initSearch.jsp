<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">查询反洗钱报文文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">报文分类：</td>
			<td class="border_top_right4">
				<select name="category" id="category">
					<option value="">--请选择--</option>
					<option value="0">可疑报文</option>
					<option value="1">补充报文</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">报文类型：</td>
			<td class="border_top_right4">
				<select name="type" id="type">
					<option value="">--请选择--</option>
					<option value="1">普通报文</option>
					<option value="2">纠错报文</option>
					<option value="3">重发报文</option>
					<option value="4">补报报文</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">报送批次号：</td>
			<td class="border_top_right4">	
				<input type="text" name="batchNo" id="batchNo" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">报文在批次中的编号：</td>
			<td class="border_top_right4">
				<input type="text" name="seqNo" id="seqNo" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">报送时间：</td>
			<td class="border_top_right4">
				<input class="Wdate" type="text" id="submitDate" value="${submitDate}" name="submitDate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="2"><input type="button" name="butSubmit" value="查  询" class="button2" onclick="search();"></td>
		</tr>
	</table>
</form>

<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,请稍候...
</div>
<script type="text/javascript">
	$(document).ready(function(){
		search();
	}); 
	
	function search(pageNo, totalCount, pageSize){
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "context_fundout_antimoney.controller.htm?method=search",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>