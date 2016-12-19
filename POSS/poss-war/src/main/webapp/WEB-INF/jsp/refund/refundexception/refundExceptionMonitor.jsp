<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">退款异常监控</h2>
<form method="post" id="mainfromId" name="tempUploadForm" action="${ctx}/refundOrder.do?method=refundExceptionStatusUpdateBatch" method="post" enctype="multipart/form-data">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      <td align="right" class="border_top_right4" >退款订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="refundOrderNo" name="refundOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="merchantOrderNo" name="merchantOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >网关订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="tradeOrderNo" name="tradeOrderNo">
	      </td>
	     </tr>
	     
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >渠道订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="channelOrderNo" name="channelOrderNo">
	      </td>
	      <td align="right" class="border_top_right4" >退款状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value="0,1,3,4,5" selected="selected">全部</option>
	      		<option value='0,1'>创建退款中</option>
	      		<option value='1'>退款中</option>
	      		<option value='4'>机构退款超时</option>
				<option value='3'>机构退款失败</option>
				<option value='5'>人工处理</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >退款创建时间</td>
	      <td class="border_top_right4" colspan="3">
	      <input class="Wdate" type="text" id="startTime" value="" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
						<input class="Wdate" type="text" id="endTime" name="endTime" value="" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')}'})">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >渠道：</td>
	      <td class="border_top_right4" align="left"  colspan="7">
			<select id="orgCode" name="orgCode">
				<option value="" selected>---请选择---</option>
				<c:forEach var="channel" items="${paymentChannels}" varStatus="v">
					<option value="${channel.code}">${channel.desc}</option>
				</c:forEach>
			</select>	
		  </td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    
	      <%-- <form name="tempUploadForm" id="tempUploadForm" action="${ctx}/refundOrder.do?method=refundExceptionStatusUpdateBatch" method="post" enctype="multipart/form-data"> --%>
	      	<input type="file" name="file" id="file" /><input type="submit" value="批量处理导入" onclick="javascript:return upFormCheck() ;" />
	      <!-- </form> -->
	      &nbsp;&nbsp;&nbsp;&nbsp;
	      <a href="${ctx }/refundOrder.do?method=refundTemplateDown&filename=refundTemplate.xls" title="模板下载">模板下载</a>
	      &nbsp;&nbsp;&nbsp;&nbsp;
	      <input type="button" onclick="download();" name="submitBtn" value="下  载" class="button2" />
	      </td>
	    </tr>
   </table>
</form>
<div id="setRefundToFail" style="display: none">
<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" colspan="2" align="left" nowrap>
				&nbsp;确认该笔退款为<font color="red">退款失败</font>状态？
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="left" colspan="2" nowrap>
				&nbsp;<font color="red">*</font>失败原因：<input id="failReason" name="failReason"/>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" colspan="2" align="left" nowrap>
				&nbsp;<input type="button" onclick="submitSetRefundFail()" value="提交"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="取消" onclick="failClose()"/>
				<input type="hidden" id="refundOrderNoForFail">
			</td>
		</tr>
	</table>
</div>

<div id="setRefundToSuccess" style="display: none">
<table class="border_all2" width="95%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" colspan="2" align="left" nowrap>
				&nbsp;确认该笔退款为<font color="green">退款成功</font>状态？
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" align="left" colspan="2" nowrap>
				&nbsp;<font color="red">*</font>退款机构端流水号：<input id="refundChannelSeqNo" name="refundChannelSeqNo"/>
			</td>
		</tr>
		<tr class="trForContent1" align="center" valign="middle">
			<td class="border_top_right4" colspan="2" align="left" nowrap>
				&nbsp;<input type="button" onclick="submitSetRefundSuccess()" value="提交"/>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" value="取消" onclick="successClose()"/>
				<input type="hidden" id="refundOrderNoForSuccess">
			</td>
		</tr>
	</table>
</div>
<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">
	$(function(){
		<c:if test="${not empty fileEmpty}">
			alert("${fileEmpty}") ;
		</c:if>
	}) ;
	$(document).ready(function(){
		search();
	});  
	function successClose(){
		$("#setRefundToSuccess").dialog("close");
	}
	function failClose(){
		$("#setRefundToFail").dialog("close");
	}
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/refundOrder.do?method=refundExceptionMonitorList",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	function upFormCheck(){
		var flag = true ;
		var filename = $("#file").val() ;
		if(null != filename && "" != filename){
			var extName = filename.substring(filename.lastIndexOf(".")) ;
			if(extName != ".xls"){
				alert("文件格式不正确！") ;
				flag = false ;
			}
		}else{
			flag = false ;
			alert("请选择批量文件！")
		}
		return flag ;
	}
	function download(){
		var refundOrderNos="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					refundOrderNos+=$(this).val()+",";							
				}
			};
		 })
		 if(refundOrderNos==''){
			alert("请选择订单！！！");
			return ;	
		}
		
		var pars = "refundOrderNos="+refundOrderNos;
		window.location.href = 	"refundOrder.do?method=refundExceptionMonitorDownload&"+pars;
	}
  </script>