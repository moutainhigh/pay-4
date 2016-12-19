<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">卡片信息查询</h2>
<form action="manualTransSub.do" method="post" id="mainfromId" name="mainfrom" enctype="multipart/form-data">
	<input type="hidden" name="method" value="uploadCardInfo">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
		<td class=border_top_right4 align="right" >卡号：</td>
      		<td class="border_top_right4" >
        	<input type="text" name="cardCode"/>
      	</td>
      	<td align="right" class="border_top_right4">上传日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startTime"  name="startTime"  onClick="WdatePicker({minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'endTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="endTime" name="endTime"   onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})">
   	</td>
      	<td class=border_top_right4 align="right" >卡组织：</td>
      		<td class="border_top_right4" >
        	<select  name="cardOrg" id="interestType" style="width:132px; height:20px;">
	      				<option value="">--请选择--</option>
						<option value="Visa">Visa</option>
						<option value="MasterCard">MasterCard</option>
						<option value="JCB">JCB</option>
	      	</select>
      	</td>
      	<td class=border_top_right4 align="right" >状态：</td>
      		<td class="border_top_right4" >
        	<select  name="status" style="width:132px; height:20px;">
	      			<option value="">--请选择--</option>
	      			<option value="1">启动</option>
	      			<option value="2">禁用</option>
	      		</select>
      	</td>
    </tr>
		<tr class="trForContent1">
      		<td align="center" class="border_top_right4" colspan="8">
      			<input type="button" onclick="search();" name="submitBtn" value="查  询" class="button2" >&nbsp;&nbsp;
      			      <input type="file" name="orginalFile" id="orginalFile" size="50" class="button2"/>
      			      选择卡号批次<input type="radio" name="pici" value="P1" checked="checked" class="button2">P1<input type="radio" name="pici" value="P2" class="button2">P2<input type="radio" name="pici" value="P3" class="button2">P3
					<input type="button" onclick="uploadCardInfo();" name="submitBtn" value="卡片信息上传" class="button2" >&nbsp;
				<a href="#" onclick="downloadTemp();">卡片信息模版下载</a>&nbsp;
			
				
				
      		</td>
      	</tr>
      	<tr class="trForContent1">
      		<td align="center" class="border_top_right4" colspan="8" align="right" >
      			
				<input type="button" onclick="back();"  value="返  回" class="button2" >&nbsp;&nbsp;
				<input type="button" onclick="updateBatchStatus('1');"  value="启  用 " class="button2" >
				&nbsp;&nbsp;&nbsp;
				<input type="button" onclick="updateBatchStatus('2');"  value="暂不使用" class="button2" >
				
      		</td>
      	</tr>
      	
  </table>
 </form>
<input name="pageNo" type="hidden" id="pageNo" value="${pageNo}" >
 <div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
 <script type="text/javascript">
 	function back(){
 		window.location.href = 	"${ctx}/manualTransSub.do?method=index"; 		
 	}
 	function updateBatchStatus(obj){
 		var ids="";
 		$(":checkbox:checked").each(function(){
		  ids+=$(this).val()+",";
		});
 		if(ids==''){
 			alert("请选择卡片！！！");
 			return false;
 		}
 		  var pageNo=$("#pageNo").val();
 		window.location.href = 	"${ctx}/manualTransSub.do?method=updateStatus&status="+obj+"&ids="+ids+"&pageNo="+pageNo;
 	}
 
	function uploadCardInfo() {
		var orginalFile=$("#orginalFile").val();
		if(orginalFile==""){
			alert("请选择需要复核的文件！！！");
			return;
		}
		 if(orginalFile.indexOf('.xls')!=-1){
				$("#mainfromId").submit();	
	       }else{
	    	   alert("文件格式不正确，请选择正确的Excel文件(后缀名.xls)！")
				return false ;	    	   
	       }
	} 	
	$(document).ready(function() {
		var paegNo=$("#pageNo").val();
		//alert(paegNo);
		search(paegNo);
	});
	function search(pageNo,totalCount,pageSize) {
		var pars = $("#mainfromId").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "${ctx}/manualTransSub.do?method=queryCardInfo",
			data: pars,
			success: function(result) {
				$('#resultListDiv').html(result);
			}
		});
	}
	function downloadTemp(){
		window.location.href = 	"${ctx}/manualTransSub.do?method=templateDownload";
	}
 </script>