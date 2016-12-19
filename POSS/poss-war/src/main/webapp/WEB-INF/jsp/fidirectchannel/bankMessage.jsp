<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%> 
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">管理非即时网关配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr> 
</table>
<form action="" method="post" name="mainfrom">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	${message}
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  
</form>
  <script type="text/javascript">
	
	function goBack() {
		document.mainfrom.action="bankchannelconfig.htm?method=bankInit";
		document.mainfrom.submit();
	}


	
  </script>