<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";



function indexQuery(pageNo,totalCount,pageSize) {
	
	if($("#beginTime").val().length==0 || $("#endTime").val().length ==0){
		alert("开始和结束时间都不能为空");
		return false;
	}
	
	$('#infoLoadingDiv').dialog('open');
	
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/operatorCertStat.do?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 



</script>
</head>

<body>

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">数字证书统计</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean">
		<table class="searchTermTable" class="inputTable" style="width:80%" >
			<tr>
				<th >创建时间：<input type="text"  class="Wdate"  id="beginTime" name="beginTime" style="width: 150px;" onclick="WdatePicker()"/> 
				~ <input type="text"   class="Wdate"  id="endTime" name="endTime" style="width: 150px;" maxlength="50" onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})"/></th>
				
			</tr>
			
			<tr ><td height="10"></td>
			</tr>
			<tr>
					<td  colspan="2" style="text-align: center;  ">
					<a href="javascript:void(0)" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

</body>

