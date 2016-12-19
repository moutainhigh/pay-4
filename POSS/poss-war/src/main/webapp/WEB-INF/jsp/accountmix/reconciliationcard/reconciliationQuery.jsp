<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head runat="server">
<title>预付卡对账文件下载</title>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/js/jquery.autocomplete.min.js"></script>
<link rel="Stylesheet" href="${ctx}/js/jquery/css/jquery.autocomplete.css" />
<script type="text/javascript">
	<c:if test="${error !=null}" >
		alert('${error}');
	</c:if>
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


		
	function download(){
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
			  	var aDate, aDate1, oDate1, oDate2, iDays 
				aDate = beginDate.split("-"); 
				oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]); 
				aDate1 = endDate.split("-"); 
				oDate2 = new Date(aDate1[1] + '-' + aDate1[2] + '-' + aDate1[0]); 
				iDays = parseInt(Math.abs(oDate2 - oDate1) / 1000 / 60 / 60 /24); 
			  	if((iDays)>=31){
			  		alert("查询时间间隔大于31天！");
			  		return;
			  	}
				//var par = $("#searchFormBean").serialize();
				var url = "${ctx}/if_poss_query/reconciliationDownLoad.do?method=download&beginDate="+beginDate+"&endDate="+endDate;
				
				window.location.href=url;
				
			  	//$('#infoLoadingDiv').dialog('open');
				//var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
				//$.ajax({
					//type: "POST",
					//url: "${ctx}/if_poss_query/subjectBalanceQuery.do?method=query",
					//data: pars,
					//success: function(result) {
						//$('#infoLoadingDiv').dialog('close');
						//$('#paginationResult').html(result);
					//}
				//});
	        }
			        
	</script>

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
						<font class="titletext">预付卡对账文件下载</font>
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
<td  ><table width="100%"  border="0">
   <tr>
   <td nowrap="nowrap">交易时间</td>    
    <td><input class="Wdate" type="text" id="beginDate" name="beginDate" value='${before}'  onClick="WdatePicker()"></td>
    <td nowrap="nowrap">~</td>
    <td><input class="Wdate" type="text" id="endDate" name="endDate"  value='${today}' onClick="WdatePicker()"></td>
    <td><input type="button" name="query" id="query"  value="下载" onClick="download()"/></td>
  </tr>

</table>
  </td>
</table>
</p>
</form>
<p>&nbsp;</p>
</body>
</html>

