<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
function validateForm1(){
	var orderDate = $("#orderDate").val();
	if(orderDate.length<=1){
		alert("日期不能为空,格式为yyyy-mm-dd");
		return false;
	}
	return true;
	
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
					<font class="titletext">水电煤，通讯账单消账/退款处理</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	
	
<form method="post" name="form2"  action="${ctx}/app/sdmtxOrderConfirm.do" enctype="multipart/form-data" onsubmit="return validateForm1()">

	<table class="inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
    	<tr >
      		<th><span class="must">*</span>消账日期：</th>
      		<td>
        		<input type="text" name="orderDate" id="orderDate" style="width: 150px;"  value="${orderDate }" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
      		</td>
    	</tr>
   
    	<tr >
      		<th>需要退款条形码文件：</th>
      		<td >
        		<input type="file" name="failedFile" style="width:215px;height:20px" />
      		</td>
    	</tr>
    	
    	<tr >
      		<td colspan="2" style="text-align: center">
          		<input type="submit" name="submit" value="提交"   />
      		</td>
    	</tr>
  </table>
  
  

</form>
		
	<!--<form action="${ctx}/app/sdmtxOrderConfirm.do" method="POST" name="form1"  enctype="multipart/form-data" >
		
		<table class="inputTable" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
			<tr>
				<th >消账日期：</th>
				<td >
				<input type="text" name="orderDate" id="orderDate" style="width: 150px;"  value="" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			</tr>
			<tr>
				<th >失败条形码文件：</th>
				<td >
				<input type="file"　name="failedFile" style="width:215px;height:20px" />
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
				<input type="submit" name="submit" value="提交"   />
				</td>
			</tr>
		</table>
   
	</form>-->
</body>
</html>
