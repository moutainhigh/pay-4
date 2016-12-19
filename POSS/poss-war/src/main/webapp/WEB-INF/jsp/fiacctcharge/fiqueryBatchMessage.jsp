<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%> 
<form action="" method="post" name="mainfromQuery" id="mainfromQuery">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	       ${messageInfo}
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
		document.mainfromQuery.action="batchQuery.htm?method=index";
		document.mainfromQuery.submit();
	}


	
  </script>