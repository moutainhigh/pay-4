<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>

<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				　0: {sorter: false}
				 }});      
		});
		function userQuery(pageNo,totalCount,pageSize) {
			var dateFrom = document.getElementById("dateFrom").value;
		  	if (!isRightDateFormat(dateFrom)) {
		  		alert("开始日期格式错误！应为YYYY-MM-DD.");
		  		return ;
		  	}
		  	
		  	var dateTo = document.getElementById("dateTo").value;
		  	if (!isRightDateFormat(dateTo)) {
		  		alert("结束日期格式错误！应为YYYY-MM-DD.");
		  		return ;
		  	}
		  	
		  	if (dateTo.length!=0&&dateFrom.length!=0&&isRightDateFormat(dateFrom)&&isRightDateFormat(dateTo)) {
		  		if(dateFrom>dateTo){
					alert("起始时间不应晚于结束时间");
					return;
				}
		  	}

	  	     var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     
		  	  var amountFrom = document.getElementById("amountFrom").value;
	  	     if(amountFrom.length!=0){
		  	     if (!re.test(amountFrom))   
			  	    {   
			  	        alert("起始金额请输入数字(例:0.02)");   
			  	        return ;   
			  	     }  
	  	     }

			 var amountTo = document.getElementById("amountTo").value;
			  	  	if(amountTo.length!=0){
				  	     if (!re.test(amountTo))   
					  	    {   
					  	        alert("最大金额请输入数字(例:0.02)");   
					  	        return ;   
					  	     }   
			  	  	}			
		  	
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/manualbooking/misController.do?method=queryMis",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
</script>
</head>
<body>
<form id="vouchViewForm" name="vouchViewForm" action="" method="POST">
	<input id="vouchDataId" name="vouchDataId" type="hidden" value="" />
	<input name="vouchSeq" type="hidden" value="${vouchSearchCriteria.vouchSeq}"/>
	<input name="vouchCode" type="hidden" value="${vouchSearchCriteria.vouchCode}"/>
	<input name="vouchStatus" type="hidden" value="${vouchSearchCriteria.vouchStatus}"/>
	<input name="dateFrom" type="hidden" value="${vouchSearchCriteria.dateFromString}"/>
	<input name="dateTo" type="hidden" value="${vouchSearchCriteria.dateToString}"/>
	<input name="page" type="hidden" value="${vouchSearchCriteria.page}"/>
	<input name="batchVouchDataId" id="batchVouchDataId"  type="hidden" value=""/>	
</form>

<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>    
			　<th><input id="checkAllBox" name="checkAllBox" type="checkbox" value="" onclick="checkAll()"　width="10"/></th>
				<th>摘要</th>
			 	<th>记账申请号</th>
				<th>凭证号</th>
				<th>经办人</th>
				<th>金额</th>
				<th>申请日期</th>
				<th>记账日期</th>
				<th>复核日期</th>
				<th>状态</th>
				
				<th colspan="3">操作</th>
			</tr> 
		</thead> 
		<tbody> 
		
		<c:forEach items="${page.result}" var="vouch" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>   
			<td><input name="dataId" type="checkbox" value="${vouch.vouchDataId}"/></td>
			
								<td>${vouch.remark}</td> 
							  	<td>${vouch.applicationCode}</td>  
							    <td>${vouch.vouchDataId}</td>  
							    <td>${vouch.creator}</td> 
							   	<td>${vouch.amount}</td> 
							    <td> <fmt:formatDate value="${vouch.createDate}" type="both"/></td>  
							    <td> <fmt:formatDate value="${vouch.accountingDate}" type="both"/></td>     
							    <td> <fmt:formatDate value="${vouch.auditDate}" type="both"/></td>
								<td>
									<c:choose>
										<c:when test="${'0' eq vouch.status}">
											未复核
										</c:when>
										<c:when test="${'1' eq vouch.status}">
											审核通过
										</c:when>
										<c:when test="${'2' eq vouch.status}">
											审核不通过
										</c:when>
										<c:otherwise>
											作废
										</c:otherwise>
				  	               </c:choose>
								</td>						   
								<td style="display:none">
							    </td>	
							    
							    <td><a  href="#" onclick="javascript:viewVouch('${vouch.vouchDataId}')"  >查看</a></td>
							     							
							</tr>
					</c:forEach>
		</tbody> 
	</table>
		<tr>
			<td colspan="12" align="center"><li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/></td>
		</tr>
	<tr>
	<td><a href="#" class="" onclick="javascript:verifyChoice()">
						<input class="button2" type="button" value="批量审核">
					</a>&nbsp;<i>注：审核人与申请人不能为同一人，否则不予以处理！</i></td>
	</tr>
</body>
</html>