<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
 // <!--
	$(function(){
		$("[name='inBlance']").each(function(){
				var idName = $(this).attr("id");
				var memberCode = $(this).attr("dm");
				var amount = $(this).attr("da");
				var memberAccType = $(this).attr("dmat");
				queryBlance(idName,memberCode,amount,memberAccType);
			});
});

	function queryBlance(idName,memberCode,amount,memberAccType) {
		
		//var urls = "${ctx}/fundout-withdraw-generatebatch.do?method=getInbalanceAjax&memberCode="+memberCode+"&amount="amount+"&memberAccType="+memberAccType;
		var urls = "${ctx}/fundout-withdraw-generatebatch.do?method=getInbalanceAjax&memberCode="+memberCode+"&amount="+amount+"&memberAccType="+memberAccType;
		$.post(urls,function(d){
			$("#"+idName+"").html(d);
		});	
	}
  //-->
  </script>

 <form action="" method="post"  name="nextSetpFrom">
 	<input type="hidden"  name="sequenceList" />
 	<input type="hidden"  name="busiType" value="${busiType}"/>
 </form>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >选择<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" /> </th>
			<th >序号</th>
			<th >交易号</th>
			<th >会员号</th>
			<th >会员名称</th>
			<th >结算周期</th>
			<th >是否垫资</th>
			<th >银行名称</th>
			<th >开户行名称</th>
			<th >银行账户</th>
			<th >汇款金额（元）</th>
			<th >入款金额（元）</th>
			<th >收款人</th>
			<th >交易备注</th>
			<th >状态</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${page.result}" var="dto" varStatus="status">
		<c:set var="preStr" value="K${dto.sequenceId}"></c:set>
		<input type="hidden" value="${dto.amount}" id="chooseAmount${status.count}" />
			<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><input type="checkbox" value="${dto.workOrderky}" data-a="${dto.amount}" name="choose" onclick="selectAllcheckBoxunchecked(this);" /></td> 
				<td ><c:out value="${status.count}" /></td>
				<td >${dto.sequenceId}</td>
				<td>${dto.memberCode}</td>
				<td>${dto.memberName}</td>
				<td><li:code2name itemList="${accountModeList}" selected="${dto.accountMode}"/></td>
				<td>
					<c:choose>
					   <c:when test="${dto.isLoaning==1}">
					   		是
					   </c:when>
					   <c:otherwise>
					   		否
					   </c:otherwise>
					</c:choose>
				</td>
				<td><%-- <li:code2name itemList="${fundoutChannelList}" selected="${dto.bankKy}"/> --%>
					<c:forEach items="${targetBankList}" var="bank">
							<c:if test="${bank.value==dto.bankKy}">
								${bank.text} 
							</c:if>
					</c:forEach>	
				</td>
				<td > ${dto.bankBranch}</td>
				<td > ${dto.bankAcct}</td>
				<td ><fmt:formatNumber value="${dto.amount/1000}" pattern="#,##0.00"  />&nbsp;</td>
				<td id="I${status.count}" name="inBlance" da="${dto.amount}" dm="${dto.memberCode}" dmat="${dto.memberAccType}"></td>
				<td > ${dto.accountName}</td>
				<td > ${dto.orderRemarks}</td>
				<td >
					复核通过
				</td>
			</tr>
		</c:forEach>
	</tbody>
	<c:if test="${page.totalCount > 0}">
		<tr class="trForContent1" align="center">
		<td colspan="15">选中金额<span id="chooseAmount">0</span>元,共<span id="chooseCount">0</span>笔</td>
		</tr>
		<tr class="trForContent1" align="center">
		<td colspan="15"><input id="download" type="button" onclick="nextStep();" value="下一步" class="button2" /></td>
		</tr>
	</c:if>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>



<script type="text/javascript" language="javascript">
	function nextStep(){
		var num=0;
		var sequenceId = "";
		$("input[name='choose']").each(function(){
			if(this.checked == true){
				num++;
				sequenceId+=this.value+",";
			}
		});
		if(num<=0){
			alert("请选择数据");
			return false;
		}
		$("input[name='sequenceList']").val(sequenceId);
		document.nextSetpFrom.action='fundout-withdraw-generatebatch.do?method=nextStep';
		document.nextSetpFrom.submit();
	}
	function setDesc() {
		var count = 0;
		var amount = 0;
		$("input[type=checkbox][name='choose']:checked").each(function(i){
			count = i+1;
			amount += parseFloat($(this).attr("data-a"));
		});
		$("#chooseAmount").text(fmoney(amount/1000,2));
		$("#chooseCount").text(count);
	}
	
	function fmoney(s, n)   
	{   
	   n = n > 0 && n <= 20 ? n : 2;   
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = s.split(".")[0].split("").reverse(),   
	   r = s.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ )   
	   {   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
	} 

	//id的全选或全部取消
	function selectAll() {
		if($("#checkAll").attr("checked")){
			$("input[name='choose']").attr("checked",true);
		} else {
			$("input[name='choose']").attr("checked",false);
		}
		setDesc();
	}
	//取消一个选择单位，去掉全选框的勾
	function selectAllcheckBoxunchecked(obj){
	  if(!obj.checked){
		  $("#checkAll").attr("checked",false);
		  }
		setDesc();
	}
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	1: {sorter: false},
			 	13: {sorter: false}
			 }});
	});	
</script>