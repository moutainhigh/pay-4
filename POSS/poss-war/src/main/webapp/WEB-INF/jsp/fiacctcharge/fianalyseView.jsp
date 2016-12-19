<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	  $(document).ready(function(){
		 var errorInfo = $("input[name='errorInfo']").val();
		 if(errorInfo ==''){
			  $('#divsuccess').show();
			  $('#diverror').hide();
		 }else{
		      $('#divsuccess').hide();
			  $('#diverror').show();
		 }
	 }); 

	function checkBack(){
		document.mainfromrq.action="batchAnalyse.htm?method=batchInit";
		document.mainfromrq.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">入库文件信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<div id="divsuccess">
<form action="" method="post" name="mainfromrq" id="mainfromrq">
    <input type="hidden" id="errorInfo" name="errorInfo" value="${Error}"/>
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>批次号</th>
						<th>操作员</th>
						<th>入库时间</th>
						<th>导入文件名</th>
						<th>导入时间</th>
						<th>导入总比数</th>
						<th>导入总金额</th>
						<th>文件大小(k)</th>
					</tr>
				</thead>
				<tbody> 
				     <c:forEach items="${supplementBankDocList}" var="supp">    
					<tr>
					   
						<td>
							${supp.batchNo}
						</td>
						<td>
							${supp.operator}
						</td>
						<td>
							<fmt:formatDate value="${supp.systemdate}" pattern="yyyy-MM-dd HH:mm:ss"/>		
						</td>
						<td>
							${supp.fileName}
						</td>
						<td>
							<fmt:formatDate value="${supp.dateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			
						</td>
						<td>
							${supp.countNum}
						</td>
				        <td>
                            <fmt:formatNumber value="${supp.countAmount*0.001}" pattern="#0.00"  />
						</td>
                        <td>
							${supp.fileSize}
						</td>
						
				</tr>
				</c:forEach>
		</tbody>
	</table>
	 <tr>
			   <td>
			  <input type="button" onclick="javascript:checkBack();"  class="button2" value="返  回">
	      </td>
			   </td>
			   </tr>
	</form>  
</div>
	<div id="diverror" style="display:none">
	<form action="" method="post" name="mainfromtwo">
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="center">
	      	${Error}
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