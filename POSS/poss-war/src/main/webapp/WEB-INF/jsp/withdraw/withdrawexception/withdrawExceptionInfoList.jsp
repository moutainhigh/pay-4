<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form id="frm" name="manyForm" method="post" action="${ctx}/withdrawException.htm?method=relation">
<input type="hidden" name="ids" id="ids" value=""/>
<input type="hidden" name="uuid" value="${sessionScope.uuid}"/>
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="choiceAll" id="choiceAll"/>选择</th>
			<th>会员号</th>
			<th>会员名称</th>
			<th>交易类型</th>
			<th>交易流水号</th>
			<th>银行名称</th>
			<th>收款人</th>			
			<th>银行账号</th>
			<th>汇款金额(元)</th>
			<th>交易时间</th>
			<th>异常原因</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody id="tb"> 
	<c:forEach items="${page.result}" var="info">
		<tr>
			<td><input type="checkbox" value="${info.sequenceId}_${info.workOrderKy==null?'0':'1'}" id="choice" name="choice"/></td>
			<td>${info.memberCode}</td>
			<td>${info.memberName}</td>
			<td>${info.busiTypeDesc}</td>
			<td>${info.sequenceId}</td>
			<td>${info.bankName}</td>
			<td>${info.accountName}</td>
			<td>${info.bankAcct}</td>
			<td>${info.amountStr}</td>
			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.createTime}" type="both"/></td> 
			<td>${info.workOrderKy==null?'未生成':'未关联'}</td>
			<td>
				${info.status==null||info.status==0?"初始":""}
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<input type="button" value="批量关联工作流" id="btn" class="button4"/>
</form>
<div id="infoLoadingDiv" title="处理信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息处理中, 请稍候...
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#choiceAll").click(function(){
			if($(this).attr("checked")==true){
				$("#tb input").each(function(index){
					$(this).attr("checked",true);
				});
			}else{
				$("#tb input").each(function(index){
					$(this).attr("checked",false);
				});
			}
		});

		$("#btn").click(function(){
			if(checkChoice()){
				$("#btn").attr("disabled","disabled");
				$("#frm").submit();
				$('#infoLoadingDiv').dialog('open');
			}
		});
	});

	function checkChoice(){
		var ids="";
		$("#tb input").each(function(index){
			if($(this).attr("type")=="checkbox" && $(this).attr("checked")==true){
				ids+=$(this).val()+",";
			}
		});
		if(ids==""){
			alert("请至少选择一条");
			return false;
		}
		$("#ids").val(ids);
		return true;
	}
</script>
