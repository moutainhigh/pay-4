<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">手工补单查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
 <form action="" method="post" id="mainfromId">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
			cellpadding="1" align="center">	
		    <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >协议流水号：</td>
		      <td class="border_top_right4">&nbsp;
		      	<input type="text" name="depositProtocolNo" id="depositProtocolNo"  value="" />&nbsp;
		      	<input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
		      </td>
		     </tr>
		  
	   </table>
	
 </form>
 
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
  <script type="text/javascript">
	
	function search() {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&depositProtocolNo=" + $("#depositProtocolNo").val();
		;
			$.ajax({
				type: "POST",
				url: "supplement.htm?method=search",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}


	
  </script>