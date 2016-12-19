<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">查询风险审核队列</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">进入日期：</td>
			<td class="border_top_right4" colspan="3">
				<input class="Wdate" type="text" id="beginCreateDate" value="" name="beginCreateDate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				&nbsp;&nbsp;至&nbsp;&nbsp;
				<input class="Wdate" type="text" id="endCreateDate" value="" name="endCreateDate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">审核日期：</td>
			<td class="border_top_right4" colspan="3">
				<input class="Wdate" type="text" id="beginUpdateDate" value="" name="beginUpdateDate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
				&nbsp;&nbsp;至&nbsp;&nbsp;
				<input class="Wdate" type="text" id="endUpdateDate" value="" name="endUpdateDate"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">会员号：</td>
			<td class="border_top_right4">	
				<input type="text" name="memberCode" id="memberCode" />
			</td>
			<td align="right" class="border_top_right4">会员类型：</td>
			<td class="border_top_right4">	
				<select name="memberType" id="memberType" style="width: 100px;">
					<option value="">全部</option>
					<option value="2">企业</option>
					<option value="1">个人</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">会员名称：</td>
			<td class="border_top_right4">
				<input type="text" name="memberName" id="memberName" />
			</td>
			<td align="right" class="border_top_right4">审核状态：</td>
			<td class="border_top_right4">
				<select name="status" id="status" style="width: 100px;">
					<option value="">全部</option>
					<option value="0">待审核</option>
					<option value="1">已审核</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">审核人：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="operator" id="operator" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4"><input type="button" name="butSubmit" value="查  询" class="button2" onclick="search();"></td>
		</tr>
	</table>
</form>

<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,请稍候...
</div>
<script type="text/javascript">
	function search(pageNo, totalCount, pageSize){
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "context_fundout_checklog.controller.htm?method=search",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>