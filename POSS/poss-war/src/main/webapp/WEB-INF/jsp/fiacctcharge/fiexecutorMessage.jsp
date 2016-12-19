<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%> 

<div id="divone">
<form action="" method="post" name="mainfrom" id="mainfrom">
     <input type="hidden" id="flag" name="flag" value="${info}"/>
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	成功总比数: ${successNum},失败总比数: ${errorNum}
	      </td>
	    </tr>
	   <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  
</form>
</div>
	<div id="divtwo" style="display:none">
	<form action="" method="post" name="mainfromtwo">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	       拒绝操作成功
	      </td>
	    </tr>
	  <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  
</form>
	</div>
  <script type="text/javascript">
	
	 $(document).ready(function(){
		 var flag = $("input[name='flag']").val();
		 if(flag==0){
			  $('#divone').hide();
			  $('#divtwo').show();
		 }else{
		      $('#divone').show();
			  $('#divtwo').hide();
		 }
	 }); 
	function goBack() {
		document.mainfrom.action="batchExecutor.htm?method=index";
		document.mainfrom.submit();
	}


	
  </script>