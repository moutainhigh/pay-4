<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>风控交易监控预警日报表查询</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
		
			var businessType = document.getElementById("businessType").value;
			if(isEmpty(startDate)){
				alert("时间不能为空");
				return false;
			}
			
			
			if(isEmpty(businessType)){
				alert("请选择触发规则");
				return false;
			}
			return true;
		}
		
		function dataQuery(){
		  	var startDate = document.getElementById("startDate").value;
		  	var businessType = document.getElementById("businessType").value;
		  	var pars =  "&startDate=" + startDate + "&businessType=" + businessType;
			$.ajax({
				type: "POST",
				url: "${ctx}/orderrisk/orderriskmonitor.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});	       
        }
		
		function downLoad(){
		 	var startDate = document.getElementById("startDate").value;
		  	var businessType = document.getElementById("businessType").value;
		  	var pars =  "&startDate=" + startDate + "&businessType=" + businessType;
			window.location="${ctx}/orderrisk/orderriskmonitor.do?method=download"+pars; 
		}


		function setrule(pageNo,totalCount,pageSize){
		window.location="${ctx}/orderrisk/orderriskmonitor.do?method=setrule"; 
        }
</script>
</head>

<body>
<h2 class="panel_title">风控交易监控预警日报表</h2>


<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${dateStr}" type="date" pattern="yyyy-MM-dd" />'  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	         <%-- <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})"> --%>
      	</td>
      	
      	<td class=border_top_right4 align="right" >触发规则：</td>
      	<td class="border_top_right4" >
        	<select id="businessType" name="businessType" onChange="redirec(document.form1.businessType.options.selectedIndex)">
        			<option selected="0" value="">全部</option>
        			<option value="1">规则1_时段内失败交易数超限</option>
        			<option value="2">规则2_同一Email累计交易次数超限</option>
        			<option value="3">规则4_账单国与收货国不符</option>
        			
        	</select>
      	</td>
    </tr>
   
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
       <input type="button" onclick="setrule();" name="submitB" value="规则设置" class="button2">
      <input type="button" onclick="dataQuery();" name="submitBtn" value="查  询" class="button2">
       <input type="button" onclick="downLoad();" name="submitB" value="下载报表" class="button2">
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

<script type="text/javascript">
</script>
</html>
