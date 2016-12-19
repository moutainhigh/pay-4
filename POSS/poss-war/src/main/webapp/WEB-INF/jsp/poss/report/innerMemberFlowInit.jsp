<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>分子公司日交易查询统计</title>
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
			return true;
		}
		
		function userQuery(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/innerMemberFlow.do?method=query",
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
				window.location="${ctx}/report/innerMemberFlow.do?method=query&export=1&"+pars; 
			}
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
						<font class="titletext">分子公司日交易查询统计</font>
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
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${startDate}" type="date"/>'  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date"/>' onClick="WdatePicker()">
      	</td>
      	
      	<td class=border_top_right4 align="right" >分子公司名称：</td>
      	<td class="border_top_right4" >
        	<select name="memberCode">
       			<option selected="selected" value="">全部</option>
       			<c:forEach items="${list}" var="item">
					<option value="${item.memberCode}">
						${item.memberName}
					</option>
				</c:forEach>
        	</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      	<td class=border_top_right4 align="right" >操作类型：</td>
      	<td class="border_top_right4" >
          <label>
				<input type="radio" id="r1"  name="optType" checked="checked" value="1" /></label>
		  <label for="r1">
			分子公司总额查询
		   </label>
		   <label>
				<input type="radio" id="r2"  name="optType" value="2" /></label>
		    <label for="r2">
			分子公司商户明细
			</label>
      	</td>
        <td class=border_top_right4 align="right" ></td>
       	<td class="border_top_right4" ></td>
    </tr>
    
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
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
