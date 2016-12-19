<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
$(document).ready(function() {
	<c:if test="${empty daylist &&  not empty isQuery}">
	alert("${fileMonth }月没有对应的文件。");
	</c:if>
	
});
function download(day){
	form1.fileDate.value =	day;
	form1.submit();
}
</script>
<body>
	<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">水电煤，通讯账单对账文件下载</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="${ctx}/app/billDownload.do" method="post" id="form1" name="form1" target="hideIfr" >
		
		<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center">
			<tr>
				<td class="textRight">月份：</td>
				<td class="textLeft"><input type="text" name="fileMonth" id="fileMonth" style="width: 150px;"  value="${fileMonth }" readonly onclick="WdatePicker({dateFmt:'yyyyMM'})"/>
				<a href="javascript:void(0)" class="dialog_link ui-state-default ui-corner-all" onclick="window.location='${ctx}/app/billList.do?isQuery=query&fileMonth='+form1.fileMonth.value">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>
				</td>
				
				
			</tr>
		</table>
	
	<p></p>
	<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" style="width:500px">
      <thead>
       <tr>
           <th>文件日期</th>
           <th>下载</th>
         </tr>
     </thead>
      <tbody>
      	
       <c:forEach items="${daylist}" var="day" >
          <tr>
              <td>${day}</td>
              <td><a href="javascript:download('${day}')">下载</a> 
          </tr>
        </c:forEach>   
      </tbody>
    </table>
    <input type="hidden" name="fileDate" />
	</form>
   <iframe style="display:none" id="hideIfr" name="hideIfr" src=""></iframe>
</body>
</html>
