<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">订单阈值配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">订单阈值配置</h2>
<form action="orderthreholdRule.do?method=save" method="post" id="mainfromId">
	<table class="border_all2" width="99%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
	      <td width="50%" align="right" class="border_top_right4" >操作描述</td>
	      <td class="border_top_right4"><input type="text"  name="description" value="${description}"></td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >时间间隔（秒）</td>
	      <td class="border_top_right4"><input type="text"  name="duration" value="${duration}"></td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >次数阈值</td>
	      <td class="border_top_right4"><input type="text"  name="threhold" value="${threhold}"></td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="保存修改" class="button2" onclick="save();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseDesc}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   	 
<script type="text/javascript">      
	function save() {
		if(!confirm("您确认要阈值修改吗？")) {
			return ;
		}
		
		$("#mainfromId").submit();
	} 
  </script>