<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>综合报表查询统计</title>
<script type="text/javascript">
		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) );
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
			return true;
		}
		
		function memberDailyQuery(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/memberDailyReport.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		function exportExcel(totalCount) {
			if(totalCount <= 0){
				alert("无结果集,不能下载！");
			}else if(totalCount > 20000){
				alert("结果集大于20000,不能下载！");
			}else{
				if(!validateQuery()){
					return false;
				}
				var pars = $("#form1").serialize();
				window.location="${ctx}/report/memberDailyReport.do?method=query&export=1&"+pars; 
			}
        }
	    
</script>
</head>

<body>
<h2 class="panel_title">综合报表查询统计</h2>


<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4" colspan="3">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd"/>' onClick="WdatePicker()">
      	</td>
      	
    </tr>
    <tr class="trForContent1">
    	<td class=border_top_right4 align="right" >会员类型：</td>
      	<td class="border_top_right4" >
        	<select name="memberType"">
        			<option selected="selected" value="">全部</option>
        			<option value="1">个人</option>
        			<option value="2">企业</option>
        	</select>
      	</td>
      	<td class=border_top_right4 align="right" >会员编号：</td>
      	<td class="border_top_right4" >
        	<input type="text" name="memberCode"> 
      	</td>
    </tr>
    
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="memberDailyQuery();" name="submitBtn" value="查  询" class="button2">
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
