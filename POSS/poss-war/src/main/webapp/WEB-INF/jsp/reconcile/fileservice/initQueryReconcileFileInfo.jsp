<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查看历史银行对账文件</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="" method="post" id="mainfromId" name="mainfrom"
	onkeydown="javascript:if(event.keyCode == 13){document.all.submitBtn.focus();document.all.submitBtn.click();}">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td class=border_top_right4 align="right">选择银行：</td>
      <td class="border_top_right4">
        	<li:codetable fieldName="orgCode" style="select" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
      </td>
      <td class=border_top_right4 align="right">文件名称：</td>
      <td class="border_top_right4">
        	<input type="text" name="fileName" width="130px;"/>
      </td>
    </tr>
    <tr class="trForContent1">
      <td align="right" class="border_top_right4">业务日期：</td>
      <td class="border_top_right4">
      	<input class="Wdate" type="text" id="startDate"  name="startDate" 
      		value='<fmt:formatDate value="<%=new Date() %>" type="date"/>'  onClick="WdatePicker({maxDate:'#F{$dp.$D(\'startDate\',{d:0});}'})">
        	～
		<input class="Wdate" type="text" id="endDate" name="endDate" 
			value='<fmt:formatDate value="<%=new Date() %>" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'startDate\',{d:0});}'})">
      </td>
      <td class=border_top_right4 align="right">状态：</td>
      <td class="border_top_right4">
        	<li:select name="busiStatus" itemList="${uploadStatus}"/>
      </td>
    </tr>  
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="submitByHref();" name="submitBtn" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<script type="text/javascript">
$(document).ready(function(){submitByHref();});
	function submitByHref(pageNo,totalCount,pageSize) {
		var strDate1 = $("#startDate").val();
		var strDate2 = $("#endDate").val();

		if("" == strDate1 && "" == strDate2){
			alert("请您选择业务日期!");
			return false;
		}

		if(!validDate(strDate1,strDate2)){
			alert("开始日期不能大于结束日期!");
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "fo_rc_queryreconcile.do?method=queryUploadFile",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}


	//验证日期
	function validDate(strDate1,strDate2){
		if(null != strDate1 && null != strDate2 &&
				0 != strDate1.length && 0 != strDate2.length){
			var date1 = new Date(strDate1.replace("-","/"));
			var date2 = new Date(strDate2.replace("-","/"));
			if(date1 > date2){
				return false;
			}
		}
		return true;
	}
</script>