<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">异常卡情况</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">异常卡情况</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="99%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号</td>
	      <td class="border_top_right4"><input type="text"  name="memberCode" id="memberCode"></td>
          <td align="right" class="border_top_right4" >时间：</td>
	      <td class="border_top_right4">
	      	  <input class="Wdate" type="text" id="time" name="time"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="javascript:search();">
	      <input type="button"  name="butSubmit" value="结果下载" class="button2" onclick="javascript:download();">
	      <input type="button"  name="butSubmit" value="明细下载" class="button2" onclick="javascript:downloadDetail();">
	      <!-- <input type="button"  name="butSubmit" value="测试报警" class="button2" onclick="javascript:testWeixin();"> -->
	      
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>
<c:if test="${not empty responseCode && responseCode != '0000'}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   	 
<script type="text/javascript">  
    /* $(function(){
    	search();
    }); */
	
	function search(pageNo,totalCount,pageSize) {
    	var memberCode = $("#memberCode").val() ;
    	if(null == memberCode || "" == memberCode){
    		alert("请输入会员号")
    		return false ;
    	}
    	var time = $("#time").val() ;
    	if(null == time || "" == time){
    		alert("请选择时间") ;
    		return false ;
    	}
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/exceptionCardMgr.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function download() {
    	var memberCode = $("#memberCode").val() ;
    	if(null == memberCode || "" == memberCode){
    		alert("请输入会员号")
    		return false ;
    	}
    	var time = $("#time").val() ;
    	if(null == time || "" == time){
    		alert("请选择时间") ;
    		return false ;
    	}
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		window.location.href = 	"${ctx}/exceptionCardMgr.do?method=downLoadList&"+pars;
	}
	function downloadDetail(){
		var memberCode = $("#memberCode").val() ;
    	if(null == memberCode || "" == memberCode){
    		alert("请输入会员号")
    		return false ;
    	}
    	var time = $("#time").val() ;
    	if(null == time || "" == time){
    		alert("请选择时间") ;
    		return false ;
    	}
		//$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		window.location.href = 	"${ctx}/exceptionCardMgr.do?method=downDetail&"+pars;
	}
	function testWeixin(){
		window.location.href = 	"${ctx}/exceptionCardMgr.do?method=testAlert";
	}
  </script>