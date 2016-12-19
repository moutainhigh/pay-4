<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>客户资金出账统计表</title>
<script type="text/javascript">

function isRightDateFormat(date) {
	if(date==null||date.length==0){
		return false;
	}
	var isRight = date.match(new RegExp("^(\\d{4})([-]?)(\\d{1,2})([-]?)(\\d{1,2})$"));
	if (isRight== null) {
		return false;
	}
	return true;
}



function isEmpty(fData)
{
    return ((fData==null) || (fData.length==0) )
} 

	function isNumber(sText){
	   var ValidChars = "0123456789.";
	   var IsNumber=true;
	   var Char;
	   for (i = 0; i < sText.length && IsNumber == true; i++){
	      Char = sText.charAt(i);
	      if (ValidChars.indexOf(Char) == -1){
	         IsNumber = false;
	      }
	   }
	   return IsNumber;  
	}


	
		function userQuery(pageNo,totalCount,pageSize){
			//$E("paginationResult").innerHTML='<div align="center"> 查询中........</div>';

			var acctCode = document.getElementById("acctCode").value;
			if(isEmpty(acctCode)){
				alert("科目不能为空.");
				return false;
			}
			if(!isNumber(acctCode)){
				alert("科目只能为数字.");
				return false;
			}
			
			var beginDate = document.getElementById("beginDate").value;
		  	if (!isRightDateFormat(beginDate)) {
		  		alert("开始日期格式错误！应为YYYY-MM-DD.");
		  		return ;
		  	}
		  	
		  	var endDate = document.getElementById("endDate").value;
		  	if (!isRightDateFormat(endDate)) {
		  		alert("结束日期格式错误！应为YYYY-MM-DD.");
		  		return ;
		  	}
		  	
		  	if (endDate.length!=0&&beginDate!=0&&isRightDateFormat(beginDate)&&isRightDateFormat(endDate)) {
		  		if(beginDate>endDate){
					alert("起始时间不应晚于结束时间");
					return;
				}
		  	}

		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/if_poss_query/bankSubjectBalanceQuery.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }
		        
</script>

</head>
<form id="process" action="">
<input type = 'hidden'  name="orderSeqId" value="" />
<input type = 'hidden'  name="rsIds" id="rsIds" value="" />
</form>

<body>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">客户资金出账统计表</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
</table>


<form method="POST" id="form1" name="form1" >
<input type="hidden" name="code" value="">
<table align="center">
<tr>
<td><input type="hidden" name="isRequestDate" value="1"/></td>
<td  colspan="5"><table width="100%"  border="0">
   <tr>
   <td><input type="hidden" name="acctCode" value="1002"/></td>
  
  
    <td>查询时间</td>    
    <td><input class="Wdate" type="text" id="beginDate" name="beginDate" value='${before}'  onClick="WdatePicker()"></td>
    <td>到</td>
    <td><input class="Wdate" type="text" id="endDate" name="endDate"  value='${today}' onClick="WdatePicker()"></td>
    <td><input type="button" name="query" id="query"  value="查询" onClick="userQuery()"/></td>
  </tr>

</table>
  </td>
</table>
</p>
</form>


<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
