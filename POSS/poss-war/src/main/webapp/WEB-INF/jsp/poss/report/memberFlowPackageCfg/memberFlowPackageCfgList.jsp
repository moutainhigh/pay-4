<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<script type="text/javascript">
		$(document).ready(function(){
			 $("#memberFlowPackageCfgTable").tablesorter({
				 headers: {
				 	6: {sorter: false},
				 	7: {sorter: false}
				 }}); 
	
			 $(".tablesorter tbody tr").mouseover(function(){$(this).find("td").css({"background":"#cec"});})
			 .mouseout(function(){$(this).find("td").css({"background":"#fff"});} ) ;         
		});

		function invalid(id){
			if(!confirm("确认是否要作废？")){
				return false;
			}
			window.location.href="${ctx}/report/memberFlowPackageCfg.do?method=invalid&id="+id+"&status="+"9";
		}

		function processBack(){
			location.href = "memberFlowPackageCfg.do";
		}

		function invalid(id){
			
			$('#unBindDiv').dialog({ 
						width:500,
						height:260,
						modal:true, 	
						title:'删除包量会员配置信息', 
						overlay: {opacity: 0.5, background: "black" ,overflow:'auto' },
						buttons:{ 
							'确定':function(){ 
							 var remark = $('#unBindDiv').find("textarea[name=remark]").val();
									$.post("${ctx}/report/memberFlowPackageCfg.do?method=invalid",{id:id,status:"9",remark:remark}
										,function (msg){
												if(msg=="S"){
													$('#unBindDiv').dialog("close");
													processBack();
												}
												else{
													alert(msg);
												}
										}
									);
							}, 
							'取消':function(){ 
								$("#unBindDiv").dialog("close");
							} 
							} 
					}); 
		}
	</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	
	<div class="tableList">
	<table id="memberFlowPackageCfgTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	
	   <tr class="tabT">
	    <th class="tabTitle" align="center" scope="col">会员编号</th>
	    <th class="tabTitle" align="center" scope="col">会员名称</th>
	    <th class="tabTitle" align="center" scope="col">预付金额</th>
	    <th class="tabTitle" align="center" scope="col">预付流量</th>
	    <th class="tabTitle" align="center" scope="col">预付日期</th>
	    <th class="tabTitle" align="center" scope="col">包量起始日期</th>
	    <th class="tabTitle" align="center" scope="col">平均费率</th>
	    <th class="tabTitle" align="center" scope="col">预警流量</th>
	    <th class="tabTitle" align="center" scope="col">预警联系人</th>
	    <th class="tabTitle" align="center" scope="col">关停流量</th>
	    <th class="tabTitle" align="center" scope="col">状态</th>
	    <th class="tabTitle" align="center" scope="col">操作备注</th>
	    <th class="tabTitle" align="center" scope="col">操作</th>
	    
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	    <td>&nbsp;
	    	${dto.memberCode}
	    </td>
	     <td>&nbsp;
	    	${dto.memberName}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.prePayAmount/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.prePayFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatDate value="${dto.prePayDate}" type="date" pattern="yyyy-MM-dd"/>
	    </td>
	    <td>&nbsp;
	    	<fmt:formatDate value="${dto.beginDate}" type="date" pattern="yyyy-MM-dd"/>
	    </td>
	    <td>&nbsp;
	    	${dto.averageRate}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.warnFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	${dto.warnLinkman}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.shutDownFlow/1000}" pattern="#,##0.00"/>
	    </td>
	    <td>&nbsp;
	    	${dto.statusStr}
	    </td>
	    <td>&nbsp;
	    	${dto.remark}
	    </td>
	    <td>&nbsp;
	    	<c:if test="${dto.status=='0' || dto.status=='1'}">
		    	<a href="${ctx}/report/memberFlowPackageCfg.do?method=renewInit&id=${dto.sequenceId}">续费</a>
		    	<a href="${ctx}/report/memberFlowPackageCfg.do?method=toUpdate&id=${dto.sequenceId}">修改</a>
		    	<a href="javascript:invalid(${dto.sequenceId});">作废</a>
	    	</c:if>
	    </td>
	  	</tr>
	  </c:forEach>
	</table>
		<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
	
	<div id="unBindDiv" style="display: none">
		<span style="font-size:  15pt">确定要作废此记录吗？</span>
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
			<tr class="trForContent1">
			      <td class=border_top_right4 align="right" >备注：</td>
			      <td class="border_top_right4" >
			        	 	<textarea id="remark" name="remark" rows="5" cols="20"></textarea>
			      </td>
		     </tr>
	     </table>
	</div>
</body>
</html>
