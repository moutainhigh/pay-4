<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>



<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function validateQuery(){
	return true;
}

function indexQuery(pageNo,totalCount,pageSize) {
	
	if(! validateQuery()){
		return false;
	}
	$('#infoLoadingDiv').dialog('open');
	
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/if_poss/accountingFee.do?method=search",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 


$(function(){
	var minTime = "2010-01-01";
	var curTime  = "<fmt:formatDate value="${curTime}" pattern="yyyy-MM-dd"/>";
	//$("#endDate").val(curTime);
	//$("#beginDate").val(minTime);
	$("#beginDate").focus(function (){
		var startMax = $("#endDate").val();
		startMax = (startMax.length != 0)? startMax:curTime;
		if(startMax > curTime){
			startMax = curTime;
		}
		WdatePicker({onpicked:function(){endDate.focus();},minDate:'#F{$dp.$D(\'endDate\',{M:-3})}',maxDate:startMax});
	});
	$("#endDate").focus(function (){
		var endMin = $("#beginDate").val();
		endMin = (endMin.length != 0)? endMin:minTime;
		var maxDate = $("#beginDate").val().length==0?curTime:'#F{$dp.$D(\'beginDate\',{M:+3})}';
		WdatePicker({skin:'whyGreen',minDate:endMin,"maxDate":maxDate});
	});
	
});



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
		<div align="center"><font class="titletext">查询计费扣费成功明细</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean" style="width: 100%;text-align: center">
		<table class="searchTermTable" class="inputTable" style="width:60%;margin: 0px auto" >
			<tr>
				<th style="text-align: right">日期：</th>
				<td style="text-align: left" colspan="3">
				<input type="text"  value="" name="beginDate" id="beginDate" class="Wdate" style="width: 150px;" readonly="readonly" />
				至
				<input type="text"  value="" name="endDate" id="endDate" class="Wdate" style="width: 150px;" readonly="readonly"/></td>
			</tr>
			<tr>
				<th style="text-align: right">会员号：</th>
				<td style="text-align: left" colspan="3">
				<input type="text" id="memberCode" name="memberCode"  title="会员号" style="width: 150px;" maxlength="11" />
				</td>
			</tr>
			<tr>
				<th style="text-align: right">账户名称：</th>
				<td style="text-align: left" colspan="3"><input type="text" id="acctName" name="acctName"  title="账户名称" style="width: 150px;" maxlength="40" />
				交易类型：
			<select name="dealType"  id="dealType" style="width: 150px;" >
				<option value="">全部</option>
			<c:forEach items="${payTypes }" var="tp" > 
					<option value="${tp.code }">${tp.message }</option>
			</c:forEach></select>
			</td>
			</tr>
			
			<tr>
				<td  colspan="4" style="text-align: center;  ">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
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

