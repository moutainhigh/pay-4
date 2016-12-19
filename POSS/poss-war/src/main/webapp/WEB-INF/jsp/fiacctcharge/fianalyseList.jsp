<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<div id="divonetitle">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">网关文件导入</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
</div>
<div id="divone">
<form action="" method="post" name="mainfrom" id="mainfrom">
				<input type="hidden" name="fileSize" id="fileSize" value="${fileSize}" />
				<input type="hidden" name="fileName" id="fileName" value="${fileName}" />
				<input type="hidden" name="drDateTime" id="drDateTime" value="${drDateTime}" />
				<input type="hidden" name="countNum" id="countNum" value="${countNum}" />
				<input type="hidden" name="countAmount" id="countAmount" value="${countAmount}" />
				<input type="hidden" id="flag" name="flag" value="${info}"/>

			
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>文件名</th>
						<th>基准时间</th>
						<th>导入总比数</th>
						<th>导入总金额</th>
						<th>文件大小(k)</th>
						<th>导入状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody> 
					<c:if test="${supplementBankDocDto != null }">
					<tr>
						<td>
							${fileName}
						</td>
						<td>
					
							<fmt:formatDate value="${supplementBankDocDto.dateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${supplementBankDocDto.countNum}
						</td>
						<td>
							<fmt:formatNumber value="${supplementBankDocDto.countAmount*0.001}" pattern="#0.00"  />
						</td>
						<td>
							${fileSize}
						</td>
							<td>
							<c:choose>
								<c:when test="${supplementBankDocDto.stat.code eq '1'}">
									成功
								</c:when>
								<c:when test="${supplementBankDocDto.stat.code eq '0'}">
									失败
								</c:when>
							
							</c:choose>
						</td>
				
                  <td>
						<input type="button"   id="insertbatch" class="button2" value="入 库" onclick="javascript:insertfile();">&nbsp;
				        <input type="button"   id="del" class="button2" value="删 除" onclick="javascript:shanchu();">
						</td>
						
				</tr>
				
			  
			</c:if>
		</tbody>
	</table>
	 <tr>
			   <td>
			   <input type="button"   id="back" class="button2" value="返  回" onclick="javascript:checkBack();">
			   </td>
			   </tr>
	</form>
	</div>
	<div id="divtwo" style="display:none">
	<form action="" method="post" name="mainfromtwo">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	${info}
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	 <input type="button" onclick="javascript:checkBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  
</form>
	</div>
	<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;数据处理中, 请稍候...
	</div> 
 <script type="text/javascript">
	function insertfile() {
		 if (confirm("确定入库该文件?")){
			$('#infoLoadingDiv').dialog('open');
			$("#insertbatch").attr({"disabled":"disabled"});
			$("#del").attr({"disabled":"disabled"});
			var pars = $("#mainfrom").serialize();
			var fileSize = $("input[name='fileSize']").val();
			var fileName = $("input[name='fileName']").val();
			var drDateTime = $("input[name='drDateTime']").val();
			var countNum = $("input[name='countNum']").val();
			var countAmount = $("input[name='countAmount']").val();
			var url = "batchAnalyse.htm?method=onSubmit";
			url+="&fileSize="+fileSize+"&fileName="+fileName+"&drDateTime="+drDateTime+"&countNum="+countNum+"&countAmount="+countAmount;
			;
				$.ajax({
					type: "POST",
					url: url,
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
                        $('#divone').hide();
						$('#divtwo').hide();
						$('#divonetitle').hide();
					}
                    
				});
		 }
	}
    $(document).ready(function(){
		 var flag = $("input[name='flag']").val();
	
		 if(!flag==''){
			  $('#divone').hide();
			  $('#divtwo').show();
		 }
	 }); 

	function checkBack(){
		document.mainfrom.action="batchAnalyse.htm?method=batchInit";
		document.mainfrom.submit();
	}
	function shanchu(){
		
		if (confirm("确定删除该文件?")){
			document.mainfrom.action="batchAnalyse.htm?method=batchInit";
		  document.mainfrom.submit();
		}
	}
	function insertfileback(){

	  if (confirm("确定入库该文件?")){
		
			document.mainfrom.action="batchAnalyse.htm?method=onSubmit";
		  document.mainfrom.submit();
		}
	}
</script>