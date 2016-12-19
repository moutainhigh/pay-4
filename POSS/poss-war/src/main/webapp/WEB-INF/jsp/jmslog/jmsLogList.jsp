<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>补发消息列表</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#choiceAll").click(function(){
					if($(this).attr("checked")){
						$("#tb input[type='checkbox']").attr("checked",true);
					} else {
						$("#tb input[type='checkbox']").attr("checked",false);
					}
				});				
			});

			function send(seqId){
				//if($("#choice"+seqId).attr("checked")){
					window.location="${ctx}/jmsLogController.htm?method=reSend&ids="+seqId;
					$('#msgSendingDiv').dialog('open');
				//} else {
				//	alert("请选中当前行");
				//}
			}

			function batchSend(){
				var ids="";
				$("#tb input[type='checkbox']").each(function(){
					if($(this).attr("checked")){
						ids+=$(this).val()+",";
					}
				});
				if(ids==""){
					alert("请至少选择一条");
				} else {
					window.location="${ctx}/jmsLogController.htm?method=reSend&ids="+ids;
					$('#msgSendingDiv').dialog('open');
				}
			}
		</script>
	</head>
	
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>
						<input type="checkbox" id="choiceAll"></input>
						全选
					</th>
					<th>序号</th>
					<th>消息名称</th>
					<th>业务详细信息</th>
					<th>补单次数</th>
					<th>创建日期</th>
					<th>更新日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="tb">
				<c:forEach var="jmsLog" items="${page.result}" varStatus="v"> 
					<tr>  
						<td>
							<input type="checkbox" id="choice${jmsLog.sequenceId}" value="${jmsLog.sequenceId}"/>
						</td>
						<td>${(page.pageNo-1)*page.pageSize+(v.index+1)}</td>
						<td>${(jmsLog.queueName==null)?'':jmsLog.queueName}</td>
						<td>${(jmsLog.busiInfo==null)?'':jmsLog.busiInfo}</td>
						<td>${jmsLog.retryTimes}</td>
						<td>
							<fmt:formatDate value="${jmsLog.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td> 
						<td>
							<fmt:formatDate value="${jmsLog.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>  
						<td>
							<input class="button2" type="button" value="重发" onclick="send('${jmsLog.sequenceId}');">
						</td>  
					</tr>                   
				</c:forEach>                   
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
		<input type="button" class="button2" value="批量重发" onclick="batchSend();">
		<div id="msgSendingDiv" title="提示信息"
			style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;消息发送中,
			请稍候...
		</div>
	</body>
</html>