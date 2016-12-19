<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<h2 class="panel_title">拒付登记详情</h2>
<form action="" method="post" name="auditForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户会员号：</td>
	      <td class="border_top_right4">
	      	${result.partnerId}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >渠道订单号：</td>
	      <td class="border_top_right4">
	      	${result.channelOrderNo}&nbsp;
	      </td>
	    </tr> 
	    <tr class="trForContent1">
	    <td align="right" class="border_top_right4" >档案号：</td>
	      <td class="border_top_right4">
	      	${result.refNo}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >交易日期：</td>
	      <td class="border_top_right4">
	      	<date:date value='${result.tranDate}' />
	      </td>
	     </tr>
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >批次号：</td>
	      <td class="border_top_right4">
	      	${result.batchNo}&nbsp;
	      </td>
	     <td align="right" class="border_top_right4" >银行卡：</td>
	      <td class="border_top_right4">
	      	${result.cardholderCardno}&nbsp;
	      </td>
	     </tr> 
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >授权码：</td>
	      <td class="border_top_right4">
	      	${result.authorisation}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >渠道号：</td>
	      <td class="border_top_right4">
	      	${result.orgCode}&nbsp;
	      </td>
	     </tr> 
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >拒付时间：</td>
	      <td class="border_top_right4">
	      	${result.cpdDate}
	      </td>
	      <td align="right" class="border_top_right4" >可拒付金额：</td>
	      <td class="border_top_right4">
	      	${result.canBouncedAmount}
	      </td>
	     </tr> 
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >拒付币种：</td>
	      <td class="border_top_right4">
	      	${result.bankCurrencyCode}
	      </td>
	      <td align="right" class="border_top_right4" >拒付金额：</td>
	      <td class="border_top_right4">
	       <fmt:formatNumber value="${result.bankAmount/1000}" pattern="#,##0.00" />
	      </td>
	     </tr> 
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >拒付原因：</td>
	      <td class="border_top_right4">
	      	${result.reasonCode}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >显示原因：</td>
	      <td class="border_top_right4">
	      	<c:choose>
		      		<c:when test="${result.visableCode=='1'}">1——需要交易凭证</c:when>
		      		<c:when test="${result.visableCode=='2'}">2——未收到货物或服务</c:when>
		      		<c:when test="${result.visableCode=='3'}">3——未授权交易</c:when>
		      		<c:when test="${result.visableCode=='4'}">4——重复处理</c:when>
		      		<c:when test="${result.visableCode=='5'}">5——欺诈伪冒</c:when>
		      		<c:when test="${result.visableCode=='6'}">6——货不对版</c:when>
		      		<c:when test="${result.visableCode=='7'}">7——不承认</c:when>
		      		<c:when test="${result.visableCode=='8'}">8——要求个人纪录</c:when>
		      		<c:when test="${result.visableCode=='9'}">9——商品已退回未退款</c:when>
		      		<c:when test="${result.visableCode=='10'}">10——未收到退款</c:when>
		      		<c:when test="${result.visableCode=='11'}">11——金额不符</c:when>
		      		<c:when test="${result.visableCode=='12'}">12——未提供单据</c:when>
		      		<c:when test="${result.visableCode=='13'}">13——重复扣款</c:when>
		     </c:choose> 		
	      </td>
	     </tr> 
	     <tr class="trForContent1">
	     <td align="right" class="border_top_right4" >最晚回复时间：</td>
	      <td class="border_top_right4">
	      	<date:date value='${result.lastDate}' />
	      </td>
	      <td align="right" class="border_top_right4" >拒付类型：</td>
			<td class="border_top_right4"><c:choose>
					<c:when test="${result.bouncedType=='0'}">拒付</c:when>
					<c:when test="${result.bouncedType=='1'}">银行调单</c:when>
					<c:when test="${result.bouncedType=='2'}">内部调单</c:when>
				</c:choose>
			</td>
	     </tr>
	  	<tr class='trForContent1' colspan='4'>
				<td class=" border_top_right4" colspan='4' align="center">  操作备注：<textarea rows="5" name="remark" 
		id="remark" title="最多只能输入50个汉字或100个字符">${result.remark}</textarea></td>
		</tr>				   
	   
	</tr>	
	</table>
	<center>
    <input type="button" name="btnRollback" onclick="rollbackAuditQuery();" class="button2" value="返  回"/>
    </center>
</form>
 <script type="text/javascript">

	function rollbackAuditQuery(){
		var url = 'bounced-register.do?method=batchRegister';
		document.auditForm.action = url;
		document.auditForm.submit();
	}
</script>
 