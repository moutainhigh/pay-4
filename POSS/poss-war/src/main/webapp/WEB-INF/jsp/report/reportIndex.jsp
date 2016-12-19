<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>报表查询统计</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			if(isEmpty(startDate)){
				alert("开始时间不能为空");
				return false;
			}
			if(isEmpty(endDate)){
				alert("结束时间不能为空");
				return false;
			}
			if(startDate > endDate){
				alert("开始时间必须小于结束时间");
				return false;
			}
	
			var tempDay =compareDate(startDate,endDate)
			if(tempDay > 30){
				alert('查询日志不能大于30天')
				return false;
			}else if(tempDay > 6){
				if(!confirm("由于查询数据量较大，建意查询天数小于一周,确认是否要查询？")){
					return false;
				}
			}
			return true;
		}


		function compareDate(s1,s2){
		    var t1=s1.substring(0,4)+"/"+s1.substring(5,7)+"/"+s1.substring(8,10);
		    var t2=s2.substring(0,4)+"/"+s2.substring(5,7)+"/"+s2.substring(8,10);
			var date1 = new Date(t1);
		  	var date2 = new Date(t2);
			var days= date2.getTime() - date1.getTime(); 
			var time = parseInt(days / (1000 * 60 * 60 * 24));
			return time;
		}
		
		function userQuery(){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/if_poss_query/sumaryReport.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }
        
</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">报表查询统计</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value="${dateStr}"  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value="${dateStr}" onClick="WdatePicker()">
      	</td>
      	<td class=border_top_right4 align="center" colspan="2">
      		 <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
      	</td>
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
