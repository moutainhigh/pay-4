<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<head>
<title>每日应收未收金额统计</title>
<script type="text/javascript">
	function processBack(){
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		window.location.href='${ctx}/report/systemReserveDiary.do?startDate='+startDate+'&endDate='+endDate;
		//window.history.back(); 
	}

</script>
</head>

<body>
	<table width="320" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">每日应收未收金额统计</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
	</table>
	<br>

	<form id="form1" name="form1">
	<input id="startDate" type="hidden" value="${startDate}">
	<input id="endDate" type="hidden" value="${endDate}">
	  <br>
      <table id="policyItemTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">	   
		<thead>
		   <tr class="tabT">
		   		<th class="tabTitle" align="center" scope="col">序号</th>
			    <th class="tabTitle" align="center" scope="col">统计日</th>
			    <th class="tabTitle" align="center" scope="col">网关渠道名称</th>
			    <th class="tabTitle" align="center" scope="col">统计起始日期</th>
			    <th class="tabTitle" align="center" scope="col">统计起始时间</th>
			    <th class="tabTitle" align="center" scope="col">统计截止日期</th>
			    <th class="tabTitle" align="center" scope="col">统计截止时间</th>
			    <th class="tabTitle" align="center" scope="col">金额(元)</th>
		  </tr>
		</thead> 
		<tbody>
		  <c:forEach items="${uncollectedAmtDiaryDTOList}" var="dto" varStatus="s">
			  <tr>
			  	<td align="right">&nbsp;
					${s.index+1}
			    </td>
			    <td align="right">&nbsp;
			    	${dto.countDateStr}
			    </td>
			    <td align="right">&nbsp;
					${dto.channelOrgName}
			    </td>
			    <td align="right">&nbsp;
					${dto.countBeginDate}
			    </td>
			    <td align="right">&nbsp;
					${dto.countBeginTime}
			    </td>
			    <td align="right">&nbsp;
					${dto.countEndDate}
			    </td>
			    <td align="right">&nbsp;
					${dto.countEndTime}
			    </td>
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.amount == null ? '' : dto.amount / 1000}"  type="currency" />
			    </td>
			  </tr>
		  </c:forEach>
		</tbody>
	  </table>			         
 	  <br>
	  <input type="button" onclick="processBack()" name="Submit2" value=" 返 回 ">
	</form>
</body>
