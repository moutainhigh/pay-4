<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<html>
<head>
<title>IP黑名单查询</title>
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
				url: "${ctx}/ipBlackList.do?method=search",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }
		$(document).ready(function(){
			userQuery();
		}); 
	  </script>
</head>

<body>
<!-- 	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">IP黑名单查询</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table> -->
	<h2 class="panel_title">IP黑名单查询</h2>
<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">
    		起始时间：</td>
   		<td align="left" class="border_top_right4">
    		<input class="Wdate" type="text" id="startDate" name="startDate" value='${startDate}'  onClick="WdatePicker()">
    	</td>
      	<td align="right" class="border_top_right4">
	    	  结束时间：</td>
   		<td align="left" class="border_top_right4">
	    	<input class="Wdate" type="text" id="endDate" name="endDate"  value='${endDate}' onClick="WdatePicker()">
      	</td>
    </tr>
    
    <tr class="trForContent1">
      	<td align="right" class="border_top_right4">
	      	<label for ="r1">
				IP地址：
			</label></td>
   		<td align="left" class="border_top_right4">
		    <input type="text" name="ipaddr" id="ipaddr" maxlength="15" size="16"/>
      	</td>
    	<td align="right" class="border_top_right4">
	      	<label for ="r1">
				状态：
			</label></td>
   		<td align="left" class="border_top_right4">
		     <select name="status" id="status">
      			<option selected="selected" value="">全部</option>
      			<option value="1">激活</option>
      			<option value="2">禁用</option>
       		</select>
      	</td>
    </tr>
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
	     <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
	 	 <input type="button" onclick="javascript:window.location.href='${ctx}/ipBlackList.do?method=createIpBlack';" name="addBtn" value="新增黑名单" class="button2">
      </td>
    </tr>
  </table>
 </form>
 
   <c:if test="${not empty message}">
 	<div>
		<li style="color: red;">${message}</li>
	</div>
 </c:if>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>

</body>


</html>
