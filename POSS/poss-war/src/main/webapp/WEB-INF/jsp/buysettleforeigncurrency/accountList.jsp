<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th><input type="checkbox" onclick="cheked()" id="check"></th>
					<th>会员号</th>
					<th>会员名称</th>
					<th>申请操作员</th>
					<th>申请时间</th>
					<th>申请开通账户</th>
					<th>申请账户用途</th>
					<th>申请状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="acc" items="${accApplyChecks}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>
						<c:if test="${acc.status=='0'}">
										<input type="checkbox"  value="${acc.id}" > 
										<input type="hidden"  value="${acc.partnerId}" > 
										<input type="hidden"  value="${acc.accCurrencyCode}" > 
						</c:if>
						</td>
						<td>${acc.partnerId}</td>
						<td>
							${acc.partnerName}
						</td>
						<td>
							${acc.operator}
						</td>
						<td>
						<fmt:formatDate value="${acc.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
						<c:choose>
							<c:when test="${acc.accCurrencyCode=='CNY'}">人民币基本结算账户和人民币保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='USD'}">美元基本结算账户和美元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='EUR'}">欧元基本结算账户和欧元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='GBP'}">英镑基本结算账户和英镑保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='HKD'}">港元基本结算账户和港元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='AUD'}">澳元基本结算账户和澳元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='CAD'}">加元基本结算账户和加元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='JPY'}">日元基本结算账户和日元保证金账户</c:when>
							<c:when test="${acc.accCurrencyCode=='SGD'}">新加坡元基本结算账户和新加坡元保证金账户</c:when>
						</c:choose>	
						</td>
						<td>
							${acc.reason}
						</td>
						<td>
						<c:choose>
						<c:when test="${acc.status=='0'}">待处理</c:when>
						<c:when test="${acc.status=='1'}">申请通过</c:when>
						<c:when test="${acc.status=='2'}">申请拒绝</c:when>
						</c:choose>
						</td>
						<td>
							<c:if test="${acc.status=='0'}">
										<a href="#"  onclick="check('${acc.id}','1','${acc.partnerId}','${acc.accCurrencyCode}');">通过</a>
										<a href="#"  onclick="check('${acc.id}','2');">拒绝</a>
							</c:if>
							<c:if test="${acc.status!='0'}">
										-
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
<script type="text/javascript">
		function bacthReviewed(status){
			var checkId="";
			var partnerIds = "";
			var currencyCodes = "";
			 $("input[type='checkbox']").each(function(){
				 if(this.checked){
					if($(this).val()!='on'){
						checkId+=$(this).val()+",";							
						partnerIds+=$(this).next().val()+",";
						currencyCodes+=$(this).next().next().val()+","
					}
				};
			 })
			if(checkId==''){
				alert("请选择需要审核的账户！");
				return;
			}
			 window.location.href="${ctx}/accountDreCheck.do?method=check&id="+checkId+"&status="+status+"&partnerId="+partnerIds+"&currencyCode="+currencyCodes;
		}
		
	  function check(id,status,partnerId,currencyCode){
			window.location.href="${ctx}/accountDreCheck.do?method=check&status="+status+"&id="+id+"&partnerId="+partnerId+"&currencyCode="+currencyCode;
	  }
	function del(id,status){
		if (!confirm("确认删除？")) {
			return;
		 }
	}
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./accountDreCheck.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function cheked(){
		 if($("#check").attr("checked")==true){
			 $("input[type='checkbox']").each(function(){
				 this.checked=true;
			 })
		 }else{
			 $("input[type='checkbox']").each(function(){
				 this.checked=false;
			 })
		 }
	}
</script>