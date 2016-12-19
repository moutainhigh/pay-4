<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%> 
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
<!--		<c:if test="${isPreCard !='isPreCard'}">-->
<!--			<div align="center"><font class="titletext">资金渠道新增</font></div>-->
<!--		</c:if>-->
<!--		<c:if test="${isPreCard=='isPreCard'}">-->
<!--			<div align="center"><font class="titletext">预付卡渠道新增</font></div>-->
<!--		</c:if>-->
		<c:choose>
			<c:when test="${isPreCard == 'isPreCard'}">
				<div align="center"><font class="titletext">预付卡渠道新增</font></div>
			</c:when>
			<c:when test="${isRecCard == 'isRecCard'}">
				<div align="center"><font class="titletext">充值卡渠道新增</font></div>
			</c:when>
			<c:otherwise>
				<div align="center"><font class="titletext">资金渠道新增</font></div>
			</c:otherwise>
		</c:choose>
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
	 <input name="isPreCard" id="isPreCard" type="hidden" value="${isPreCard}" /> 
	 <input name="isRecCard" id="isRecCard" type="hidden" value="${isRecCard}" /> 
</form>
  <script type="text/javascript">
	
	function goBack() {
		var isPreCard =  $('#isPreCard').val() ;
		var isRecCard =  $('#isRecCard').val() ;
		if("isPreCard" == isPreCard){
			document.mainfrom.action="channelconfig.htm?method=preInit";
		}else if("isRecCard" == isRecCard){
			document.mainfrom.action="channelconfig.htm?method=recInit";
		}else{
			document.mainfrom.action="channelconfig.htm?method=channelInit";
		}
		document.mainfrom.submit();
	}


	
  </script>