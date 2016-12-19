<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<h2 class="panel_title">拒付详情</h2>
<form action="refund.manage.do?method=handerRefundAuditSingle" method="post" name="auditForm">
	<div align="center" style="width: 100%">
		<font style="text-align: left;" color="blue"><b>基本信息</b></font>
	</div>

	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户会员号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberCode}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >拒付订单号：</td>
	      <td class="border_top_right4">
	      	${mDto.orderId}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >商户订单号：</td>
	      <td class="border_top_right4">
	      	${mDto.orderNo}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >渠道订单号：</td>
	      <td class="border_top_right4">
	      	${mDto.channelOrderId}&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >档案号：</td>
	      <td class="border_top_right4">
	      	${mDto.refNo}&nbsp;
	      </td>
	       <td align="right" class="border_top_right4" >批次号：</td>
	      <td class="border_top_right4">
	      	${mDto.batchNo}&nbsp;
	      </td>
	       <td align="right" class="border_top_right4" >渠道号：</td>
	      <td class="border_top_right4">
	      	${mDto.orgCode}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >交易日期：</td>
	      <td class="border_top_right4">
	      	<date:date value='${mDto.tradeDate}' />
	      </td>
	    </tr>
	      <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >拒付类型：</td>
			<td class="border_top_right4"><c:choose>
					<c:when test="${mDto.cpType=='0'}">拒付</c:when>
					<c:when test="${mDto.cpType=='1'}">银行调单</c:when>
					<c:when test="${mDto.cpType=='2'}">内部调单</c:when>
				</c:choose>
			</td>
			<td align="right" class="border_top_right4" >登记时间：</td>
	      <td class="border_top_right4">
	      	<date:date value='${mDto.createDate}'/>
	      </td>
	       <td align="right" class="border_top_right4" >拒付状态：</td>
	       <td class="border_top_right4">
					<c:choose>
					<c:when test="${mDto.status=='0'}">未处理</c:when>
					<c:when test="${mDto.status=='1'}">已上传资料</c:when>
					<c:when test="${mDto.status=='2'}">已递交银行</c:when>
					<c:when test="${mDto.status=='3'}">申诉失败待复核</c:when>
					<c:when test="${mDto.status=='4'}">申诉成功待复核</c:when>
					<c:when test="${mDto.status=='5'}">申诉失败</c:when>
					<c:when test="${mDto.status=='6'}">申诉成功</c:when>
					<c:when test="${mDto.status=='7'}">不申诉</c:when>
					</c:choose>
				</td>
	      <td align="right" class="border_top_right4" >最晚回复时间：</td>
	      <td class="border_top_right4">
	      	<date:date value='${mDto.latestAnswerDate}' />
	      </td>
	    </tr>
	    </tr>
	      <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >备注：</td>
	      <td class="border_top_right4">
				${mDto.remark}&nbsp;
		 </td>		
		 <td align="right" class="border_top_right4" >拒付原因：</td>
	      <td class="border_top_right4">
	      	${mDto.reasonCode}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >显示原因：</td>
	      <td class="border_top_right4">
	      	<c:choose>
		      		<c:when test="${mDto.visableCode=='1'}">1——需要交易凭证</c:when>
		      		<c:when test="${mDto.visableCode=='2'}">2——未收到货物或服务</c:when>
		      		<c:when test="${mDto.visableCode=='3'}">3——未授权交易</c:when>
		      		<c:when test="${mDto.visableCode=='4'}">4——重复处理</c:when>
		      		<c:when test="${mDto.visableCode=='5'}">5——欺诈伪冒</c:when>
		      		<c:when test="${mDto.visableCode=='6'}">6——货不对版</c:when>
		      		<c:when test="${mDto.visableCode=='7'}">7——不承认</c:when>
		      		<c:when test="${mDto.visableCode=='8'}">8——要求个人纪录</c:when>
		      		<c:when test="${mDto.visableCode=='9'}">9——商品已退回未退款</c:when>
		      		<c:when test="${mDto.visableCode=='10'}">10——未收到退款</c:when>
		      		<c:when test="${mDto.visableCode=='11'}">11——金额不符</c:when>
		      		<c:when test="${mDto.visableCode=='12'}">12——未提供单据</c:when>
		      		<c:when test="${mDto.visableCode=='13'}">13——重复扣款</c:when>
		     </c:choose> 		
	      </td>
	      <td align="right" class="border_top_right4" >拒付中金额：</td>
	       <td class="border_top_right4">
	       <fmt:formatNumber value="${mDto.doingBouncedAmount/1000}" pattern="#,##0.00" />
			</td>
	    </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >交易金额：</td>
	      <td class="border_top_right4">
	      <fmt:formatNumber value="${mDto.tradeAmount/1000}" pattern="#,##0.00" />
			</td>
			<td align="right" class="border_top_right4" >交易币种：</td>
	      <td class="border_top_right4">
	      ${mDto.tranCurrencyCode} &nbsp;
	      </td>
	       <td align="right" class="border_top_right4" >拒付金额：</td>
	       <td class="border_top_right4">
	       <fmt:formatNumber value="${mDto.bouncedAmount/1000}" pattern="#,##0.00" />
			</td>
	      <td align="right" class="border_top_right4" >拒付币种：</td>
	      <td class="border_top_right4">
	      	${mDto.cpCurrencyCode}&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent1">
	    <td align="right" class="border_top_right4" >已退金额：</td>
	       <td class="border_top_right4">
	       <fmt:formatNumber value="${mDto.overRefundAmount/1000}" pattern="#,##0.00" />
			</td>
	    <td align="right" class="border_top_right4" >已退金额币种：</td>
	       <td class="border_top_right4">
	       ${mDto.tranCurrencyCode} &nbsp;
			</td>
	    <td align="right" class="border_top_right4" >已拒付金额：</td>
	       <td class="border_top_right4">
	       <fmt:formatNumber value="${mDto.overBouncedAmount/1000}" pattern="#,##0.00" />
			</td>
	    <td align="right" class="border_top_right4" >已拒付金额币种：</td>
	       <td class="border_top_right4">
	       ${mDto.tranCurrencyCode} &nbsp;
			</td>
	    </tr>
	</table>
	<br/>
	<div align="center" style="width: 100%">
		<font style="text-align: center;" color="blue"><b>操作记录</b></font>
		 
		<c:if test="${not empty bouncedFlows}">
			<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
			  <thead> 
				<tr>
					<th >操作员</th>
					<th >操作类型</th>
					<th> 操作时间</th>
					<th >操作备注</th>
				</tr>
			  </thead>
			  <tbody> 
			  	<c:forEach items="${bouncedFlows}" var="dDto" varStatus="status">
						<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
							<td>${dDto.operator}</td>
							<td><c:choose>
									<c:when test="${dDto.status=='0'}">未处理</c:when>
									<c:when test="${dDto.status=='1'}">已上传资料</c:when>
									<c:when test="${dDto.status=='2'}">已递交银行</c:when>
									<c:when test="${dDto.status=='3'}">申诉失败待复核</c:when>
									<c:when test="${dDto.status=='4'}">申诉成功待复核</c:when>
									<c:when test="${dDto.status=='5'}">申诉失败</c:when>
									<c:when test="${dDto.status=='6'}">申诉成功</c:when>
									<c:when test="${dDto.status=='7'}">不申诉</c:when>
								</c:choose></td>
							<td><date:date value='${dDto.createDate}' /></td>
							<td>${dDto.remark}</td>
						</tr>
					</c:forEach>
			  </tbody>
			</table>
		</c:if>
		
		
<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">操作备注：</td>
		<td><textarea rows="5" cols="60" name="remark" id="remark" title="最多只能输入50个汉字或100个字符"></textarea></td>
		</tr>
		
	</table>
	</div>
	<br/>
	<p/>
	<center>
     <input type="button" onclick="handerAuditBatch(2,'${mDto.status}','${mDto.orderId}');" class="button2" value="已递交银行"/>
   	<input type="button" onclick="handerAuditBatch(4,'${mDto.status}','${mDto.orderId}');" class="button2" value="申诉成功"/>
   	<input type="button" onclick="handerAuditBatch(3,'${mDto.status}','${mDto.orderId}');" class="button2" value="申诉失败"/>
    <input type="button" name="btnRollback" onclick="rollbackAuditQuery();" class="button2" value="返  回"/>
    </center>
</form>
 <script type="text/javascript">

 function handerAuditBatch(status,statusBegin,orderId){
		var flag = true;
		if(2 == status){
			if('1' != statusBegin){
				alert("资料未上传不能递交银行!");
				flag = false;
				return false;
			}
		}
		if(4 == status){
			if('2' != statusBegin){
				alert("资料未递交银行!");
				flag = false;
				return false;
			}
		}
		if(3 == status){
			if('2' != statusBegin){
				alert("资料未递交银行!");
				flag = false;
				return false;
			}
		}
		if(!flag){
			return false;
		}
		if(confirm("您是否确定提交?")){
			//组装参数
			var remark = $("#remark").val();
			$('#infoLoadingDiv').dialog('open');
			var pars = "&status=" + status  
						+ "&orderId=" + orderId
						+"&remark=" + remark;
			$.ajax({
				type: "POST",
				url: "bounced-register.do?method=auditBatch",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			}); 
		}else{
			return false;
		}
	}

	function rollbackAuditQuery(){
		var url = 'bounced-register.do?method=bouncedQuery';
		document.auditForm.action = url;
		document.auditForm.submit();
	}
</script>
 