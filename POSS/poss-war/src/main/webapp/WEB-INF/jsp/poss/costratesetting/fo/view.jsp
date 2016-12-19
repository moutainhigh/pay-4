<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<head>
<title>成本费率配置</title>
<script type="text/javascript">
	function processBack(){
		//window.location.href='${ctx}/report/costRateSetting.do';
		window.history.back(); 
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
						<font class="titletext">出款银行成本费率配置 — 查看</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
	</table>
	<br>

	<form id="form1" name="form1">
	  <table id="policyTable" border="0" cellpadding="5" cellspacing="0" width="66%">
	    <tr>
	    	<td align="right">出款银行名称：</td>
	    	<td align="left">
		    	<c:forEach items="${pciList}" var="pci">
		    		${pci.orgCode == calPolicy.channelNo ? pci.desciption : ""}
		    	</c:forEach>
	    	</td>
	    	<td align="right">对公/对私：</td>    	
	    	<td align="left">
			    ${calPolicy.isPrivate == 0 ? "对私" : ""}
			   	${calPolicy.isPrivate == 1 ? "对公" : ""}
	    	</td>
		</tr>
	    <tr>
	    	<td align="right">同行/跨行：</td>    	
	    	<td align="left">
		    	${calPolicy.inOut == 1 ? "同行" : ""}
		   		${calPolicy.inOut == 2 ? "跨行" : ""}
	    	</td>
	    	<td align="right">同城/异地：</td>    	
	    	<td align="left">
		    	${calPolicy.address == 1 ? "同城" : ""}
		   		${calPolicy.address == 2 ? "异地" : ""}
	    	</td>
		</tr>
	    <tr>
	    	<td align="right">缓急程度：</td>    	
	    	<td align="left">
		    	${calPolicy.isUrgency == 0 ? "普通" : ""}
		   		${calPolicy.isUrgency == 1 ? "加急" : ""}
	    	</td>
	    	<td align="right">出款类型：</td>    	
	    	<td align="left">
		    	${calPolicy.outType == 1 ? "代发" : ""}
		   		${calPolicy.outType == 2 ? "转账" : ""}
	    	</td>
		</tr>	    	
		<tr>
	      	<td align="right">费率配置类型：</td>
	      	<td align="left">
		    	${calPolicy.policyType == 1 ? "固定费用" : ""}
				${calPolicy.policyType == 2 ? "费率" : ""}
				${calPolicy.policyType == 3 ? "费率及下限" : ""}
				${calPolicy.policyType == 4 ? "费率及上限" : ""}
				${calPolicy.policyType == 5 ? "费率及上下限" : ""}
				${calPolicy.policyType == 6 ? "固定费用_费率" : ""}
				${calPolicy.policyType == 7 ? "固定费用_费率及下限" : ""}
				${calPolicy.policyType == 8 ? "固定费用_费率及上限" : ""}
				${calPolicy.policyType == 9 ? "固定费用_费率及上下限" : ""}				
	      	</td>
	      	<td align="right">计算方式：</td>
	      	<td align="left">
		   		${calPolicy.countType == 1 ? "单笔交易" : ""}
				${calPolicy.countType == 2 ? "按月累计流量" : ""}
				${calPolicy.countType == 3 ? "按年累计流量" : ""}
				${calPolicy.countType == 4 ? "按季度累计流量" : ""}	
	      	</td>
	    </tr>
		<tr>
	      	<td align="right">启用日期：</td>
	      	<td align="left">
	      		<fmt:formatDate value="${calPolicy.startDate}" type="date" pattern="yyyy-MM-dd"/>
	      	</td>
	      	<td align="right">停用日期：</td>
	      	<td align="left">
	      		<fmt:formatDate value="${calPolicy.endDate}" type="date" pattern="yyyy-MM-dd"/>
	      	</td>
	    </tr>	    
	  </table>
	  <br>
      <table id="policyItemTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">	   
		<thead>
		   <tr class="tabT">
			    <th class="tabTitle" align="center" scope="col">固定费用（元）</th>
			    <th class="tabTitle" align="center" scope="col">费率（十万分之一）</th>
			    <th class="tabTitle" align="center" scope="col">起始金额（元）</th>
			    <th class="tabTitle" align="center" scope="col">终止金额（元）</th>
			    <th class="tabTitle" align="center" scope="col">下限（元）</th>
			    <th class="tabTitle" align="center" scope="col">上限（元）</th>
		  </tr>
		</thead> 
		<tbody>
		  <c:forEach items="${calPolicyItems}" var="dto">
			  <tr>
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.fixAmount == null ? '' : dto.fixAmount / 1000}"  type="currency" />
			    </td>
			    <td align="right">&nbsp;
					${dto.rateAmount}
			    </td>
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.startAmount == null ? '' : dto.startAmount / 1000}"  type="currency" />
			    </td>
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.endAmount == null ? '' : dto.endAmount / 1000}"  type="currency" />
			    </td>
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.minCharge == null ? '' : dto.minCharge / 1000}"  type="currency" />
			    </td>			    			    
			    <td align="right">&nbsp;
			    	<fmt:formatNumber value="${dto.maxCharge == null ? '' : dto.maxCharge / 1000}"  type="currency" />
			    </td>
			  </tr>
		  </c:forEach>
		</tbody>
	  </table>			         
 	  <br>
	  <br>
	  <input type="button" onclick="processBack()" name="Submit2" value=" 返 回 ">
	</form>
</body>
