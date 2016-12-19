<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	
	 $(document).ready(function(){
		 var flag = $("input[name='flag']").val();
		 if(flag==1){
			  $('#divone').show();
		 }else{
			 $('#divtwo').show();
		 }
	 }); 
	function search() {
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		;
			$.ajax({
				type: "POST",
				url: "batchCompare.htm?method=search",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	function goPass(){
			document.mainfrom.action = "batchExecutor.htm?method=onSubmit";
		    document.mainfrom.submit();
	}
	function goBack(){
			//document.mainfrom.action = "batchExecutor.htm?method=";
		    //document.mainfrom.submit();
	}
</script>




<div id="divone" style="display:none">
<form action="" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	确认审核通过?
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	 <input type="button" onclick="goPass();"  class="button2" value="YES "/>&nbsp;&nbsp;&nbsp;
			 <input type="button" onclick="goBack();"  class="button2" value="NO "/>
	      </td>
	    </tr>
	  </table>
	  
</form>
</div>
	
<div id="divtwo" style="display:none">
<table style="" width="25%" align="center" border="0" cellpadding="0" cellspacing="0" height="21">
	<tbody>
		<tr>
			<td bgcolor="#000000" height="1"></td>
		</tr>
		<tr>
			<td height="18"><div align="center"><font class="titletext">拒绝审核</font></div></td>
		</tr>
		<tr>
			<td bgcolor="#000000" height="1"></td>
		</tr>
	</tbody>
</table>
<form method="post" name="gatewayUploadForm" id="gatewayUploadForm" action="" enctype="multipart/form-data">
	<table class="border_all2" width="80%" align="center" border="0" cellpadding="1" cellspacing="0">
		<tbody>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right">拒绝理由：</td>
				<td class="border_top_right4"><textarea cols="30" rows="5"></textarea></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4">&nbsp;</td>
				<td class="border_top_right4"  align="left"><input type="button" value="确认拒绝">&nbsp;&nbsp;&nbsp;
					<input type="button" class="nyroModalClose button01" id="closeBtn" value="取 消" /></td>
			</tr>
		</tbody>
	</table>
</form>
</div>
