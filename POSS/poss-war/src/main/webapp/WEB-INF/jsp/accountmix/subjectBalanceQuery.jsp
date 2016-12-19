<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head runat="server">
<title>科目余额查询</title>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery/js/jquery.autocomplete.min.js"></script>
<link rel="Stylesheet" href="${ctx}/js/jquery/css/jquery.autocomplete.css" />
<script type="text/javascript">
	$().ready(function() {	
		
		$('#acctCode').autocomplete(eval('${json}'), {
			max : 1000, //列表里的条目数 
			minChars : 0, //自动完成激活之前填入的最小字符 
			width : 400, //提示的宽度，溢出隐藏 
			scrollHeight : 300, //提示的高度，溢出显示滚动条 
			matchContains : true, //包含匹配，就是data参数里的数据，是否只要包含文本框里的数据就显示 
			autoFill : false, //自动填充 
			formatItem : function(row, i, max) {
				return row.acctName + '[' + row.acctCode + ']';
			},
			formatMatch : function(row, i, max) {
				return row.acctName + row.acctCode;
			},
			formatResult : function(row) {
				return row.acctCode;
			}
		});
	});
	
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
				//if(isEmpty(acctCode)){
				//	alert("科目不能为空.");
				//	return false;
				//}
				
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
					url: "${ctx}/if_poss_query/subjectBalanceQuery.do?method=query",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#paginationResult').html(result);
					}
				});
	        }
			        
	</script>

</script>
</head>
<form id="process" action="">
<input type = 'hidden'  name="orderSeqId" value="" />
<input type = 'hidden'  name="rsIds" id="rsIds" value="" />
</form>

<body>
<h2 class="panel_title">科目余额查询</h2>

<form method="POST" id="form1" name="form1" >
<input type="hidden" name="code" value="">
<input type="hidden" name="isRequestDate" value="1"/></td>
<td  >
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
   <tr class="trForContent1" >
   <td align="right" class="border_top_right4" nowrap="nowrap">科目代码：</td>
  	<td class="border_top_right4" nowrap="nowrap"><input type="text" name="acctCode" id="acctCode" value=""/></td>
       <td align="right" class="border_top_right4" nowrap="nowrap">科目级别</td>
     <td class="border_top_right4"  align="left" >
			<select id="subjectLevel" name="subjectLevel">
				<option value="1" <c:if test="${subjectLevel=='1'}">select</c:if> >一级科目</option>	
				<option value="2" <c:if test="${subjectLevel=='2'}">select</c:if> >二级科目</option>
				<option value="3" <c:if test="${subjectLevel=='3'}">select</c:if> >三级科目</option>	
				<option value="4" <c:if test="${subjectLevel=='4'}">select</c:if> >四级科目</option>	
				<option value="5" <c:if test="${subjectLevel=='5'}">select</c:if> >五级科目</option>							
		   </select>
	</td>
  
    <td align="right" class="border_top_right4" nowrap="nowrap">记账时间</td>    
    <td class="border_top_right4"><input class="Wdate" type="text" id="beginDate" name="beginDate" value='${before}'  onClick="WdatePicker()"></td>
    <td align="center" class="border_top_right4" nowrap="nowrap">到</td>
    <td class="border_top_right4"><input class="Wdate" type="text" id="endDate" name="endDate"  value='${today}' onClick="WdatePicker()"></td>
    <td class="border_top_right4"><input type="button" name="query" id="query"  value="查询" onClick="userQuery()"/></td>
  </tr>

</table>

</form>


<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>

