<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">资金渠道管理</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" name="mainfrom" id="mainfromId">
  			<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
    			<tr class="trForContent1">
      				<td align="right" class="border_top_right4">ID：</td>
      				<td class="border_top_right4">
      					<input  type="text" name="id"  id="id"  value='${id}' >
      				</td>
      				
    			</tr>
    			<tr class="trForContent1">
      				<td align="right" class="border_top_right4">别名：</td>
      				<td class="border_top_right4">
      					<input type="text" name="alias"  id="alias"  value='${alias}' >
      				</td>
      				
    			</tr>
    			<tr class="trForContent1">
      				<td align="right" class="border_top_right4">机构代码：</td>
      				<td class="border_top_right4">
      					<input  type="text" name="orgcode"  id="orgcode"  value='${orgcode}' >
        				
      				    &nbsp;&nbsp;<input type="button" onclick="queryList();" name="submitBtn" value="查 询" class="button2">
      				</td>
      				
    			</tr>
    			<c:if test="${message != null }">
					<tr>
					<td colspan="11" align="center"> 
						${message}
					</td>
					</tr>
				</c:if>
  			</table>
 		</form>
	  
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
<script type="text/javascript">

		$(document).ready(function(){
			queryList ();
		}); 
		var re = /^\d{1,11}$/;
 		function queryList () {
 			var id =  $('#id').val() ;
 			if(id.length>0){
 		 		if(!re.test(id)) {
 	 				alert('id：只能输入1-11位的数字');
 	 				return false;
 	 	 		}
 		 	 }
 			$('#infoLoadingDiv').dialog('open');
 			var pars = $("#mainfromId").serialize();
 				$.ajax({
 					type: "POST",
 					url: "channelconfig.htm?method=channelManageSubmit",
 					data: pars,
 					success: function(result) {
 						$('#infoLoadingDiv').dialog('close');
 						$('#resultListDiv').html(result);
 					}
 				});
 		}
 		
 		
	
	</script>