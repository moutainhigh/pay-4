<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" id="listForm"  name="resultForm">
 </form>
<p id="showmsg" style="display: none"></p>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >序号</th>
			<th >批次号</th>
			<th >批次规则名称</th>
			<th >产生时间</th>
			<th >批次状态</th>
			<th >操作</th>
		</tr>
	</thead>
	<tbody> 
			<c:forEach items="${page.result}" var="dto" varStatus="status">
				<tr>
					<td ><c:out value="${status.count}" /></td>
					<td >${dto.batchNum}</td>
					<td >${dto.ruleName}</td>
					<td >
					<fmt:formatDate value="${dto.updateTime}" type="both"/>
					</td>
					<td id="batchStatus" >${dto.statusDesc}</td>
					<td>
						<c:if test="${dto.batchFileStatus > 0 && dto.status ne 3 && dto.status ne 6}">
							<input type="button" id="invalidButton" value="废除" class="button2" onclick="invalidBatchFile('${dto.batchNum }');" />
						</c:if>
						<c:if test="${5 eq dto.status}">
							<input type="button" value="生  成" class="button2" onclick="generateBatchFile('${dto.batchNum }');"  />
						</c:if>
						<c:if test="${3 eq dto.status}">
							<input type="button" value="重支付平台成" class="button2" onclick="regenerateBatchFile('${dto.batchNum}','${dto.ruleName}');"  />
						</c:if>
					</td>
				</tr>
			</c:forEach>
	</tbody>
	
</table>

<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>

<script type="text/javascript">
	
	//废除批次
	function invalidBatchFile(batchNum){
	//	document.getElementById("invalidButton").disabled ='true';
		if(confirm("确定要废除批次?")){
			var pars = $("#listForm").serialize()+ "&batchNum=" + batchNum ;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-querybatchfile.do?method=invalidBatchFile",
				data: pars,
				success: function(transport) {
			          var result = eval('('+transport+')');
			          $("#showmsg").html('<li style="color: red ">'+result.infos+' </li>');
			          $("#showmsg").show();
			          
			          window.setTimeout("query()",2000);
			          }       
			            // alert("网络延时! 加载数据失败!");
				});
		}
		
	}
	//生成文件
	function generateBatchFile(batchNum){
		var pars = $("#listForm").serialize()+ "&batchNum=" + batchNum ;
		$.ajax({
			type: "POST",
			url: "fundout-withdraw-querybatchfile.do?method=generateBatchFile",
			data: pars,
			success: function(transport) {
		          var result = eval('('+transport+')');
		          $("#showmsg").html('<li style="color: red ">'+result.infos+' </li>');
		          $("#showmsg").show();
		          window.setTimeout("query()", 2000);
		          }
			});
	}
	//重支付平台成批次
	function regenerateBatchFile(batchNum,batchName){
		if(!confirm("确定要重支付平台成批次?")){
			return;
		}
		var pars = $("#listForm").serialize()+ "&batchNum=" + batchNum + "&batchName=" + batchName;
		$.ajax({
			type: "POST",
			url: "fundout-withdraw-querybatchfile.do?method=regenerateBatchFile",
			data: pars,
			success: function(transport) {
		          var result = eval('('+transport+')');
		          $("#showmsg").html('<li style="color: red ">'+result.infos+' </li>');
		          $("#showmsg").show();
		          window.setTimeout("query()", 2000);
		          }
			});
	}
	
</script>
