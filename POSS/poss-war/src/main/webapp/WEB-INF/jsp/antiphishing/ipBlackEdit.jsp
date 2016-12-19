<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function(){
	$("#btn").click(function(){
		if(checkInfo()){
			$("#frm").submit();
		}
	});
});

	function checkInfo(){
		var ipaddr=$("#ipaddr").val();
		var desc=$("#desc").val();
		if(ipaddr.length<1){
			alert("请输入IP地址");
			return false;
		}
		if(desc.length<1){
			alert("请输入描述");
			return false;
		}
		return true;
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">修改IP黑名单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/ipBlackList.do?method=editIpBlack" method="post" id="frm">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		
			   
	   <tr class="trForContent1">
		      <td align="right" class="border_top_right4" >IP地址：</td>
		      <td class="border_top_right4">
		      	<input id="ipaddr" type="text"  name="ipaddr" value="${dto.ipAddress}" readonly="readonly" />
		     	<input id="ipBlackNo" type="hidden"  name="ipBlackNo" value="${dto.ipBlacklistNo}" />
		     	<input id="flag" type="hidden"  name="flag" value="1" />
		      </td>
	     </tr>
	     
	    <tr class="trForContent1">
		      <td class=border_top_right4 align="right" >描述：</td>
		      <td class="border_top_right4" >
		      <textarea name="desc" id ="desc" cols="40" rows="4">${dto.remark}</textarea>
		      </td>
	    </tr>
	     <tr class="trForContent1">
	      <td align="center" colspan="2">
	      	<input id="btn" type="button" class="button2" value="提交 ">
	      </td>
	    </tr>
	    <c:if test="${not empty info}">
			<li style="color: red;">${info }</li>
		</c:if>
	  </table>
</form>