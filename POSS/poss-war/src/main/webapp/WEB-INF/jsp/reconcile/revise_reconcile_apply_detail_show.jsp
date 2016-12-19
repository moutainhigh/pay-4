<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr> 
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查看调账申请页面</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr align="center" class="trForContent1"  valign="middle">
      <td class="border_top_right4" align="right">银行名称：</td>
      <td class="border_top_right4" align="left">
      	${ reconcileAcceptMain.bankCode}
      </td>
    </tr>
  
     <tr align="center" class="trForContent1"  valign="middle">
      <td class="border_top_right4" align="right">服务代码：</td>
      <td class="border_top_right4" align="left">
        	${reconcileAcceptMain.providerCode}
      </td>
      </tr>
      
      <tr align="center" class="trForContent1"  valign="middle">
      <td class="border_top_right4" align="right">交易日期：</td>
      <td class="border_top_right4" align="left">
        <fmt:formatDate value="${reconcileAcceptMain.busiTime}" type="date"/>
      </td>
    </tr>
    
    <tr align="center" class="trForContent1"  valign="middle">
      <td class="border_top_right4"align="right">
      		<c:if test="${'200' eq reconcileAcceptMain.adjustType   }">银行订单号：</c:if>
  			<c:if test="${'210' eq reconcileAcceptMain.adjustType   }">系统订单号：</c:if>    
      </td>
      <td class="border_top_right4" align="left">
        	  <c:if test="${'200' eq reconcileAcceptMain.adjustType   }">${reconcileAcceptMain.bankSeq }</c:if>
        	  <c:if test="${'210' eq reconcileAcceptMain.adjustType   }">${reconcileAcceptMain.accountSeq }</c:if>
      </td>
    </tr>  
    <tr align="center" class="trForContent1"  valign="middle">
    
      <td class="border_top_right4" align="right">交易金额：</td>
      <td class="border_top_right4" align="left">
      		 <c:if test="${'200' eq reconcileAcceptMain.adjustType   }">
				<fmt:formatNumber value="${reconcileAcceptMain.bankAmount}" pattern="#,##0.00"/>
			</c:if>
        	<c:if test="${'210' eq reconcileAcceptMain.adjustType   }">
				<fmt:formatNumber value="${reconcileAcceptMain.accountAmount}" pattern="#,##0.00"/>
			</c:if> 
      </td>
    </tr>
    <tr align="center" class="trForContent1"  valign="middle">
        <td class="border_top_right4" align="right" >错账方：</td>
      <td class="border_top_right4" align="left">
        <c:if test="${'200' eq reconcileAcceptMain.adjustType   }">银行</c:if>
			<c:if test="${'210' eq reconcileAcceptMain.adjustType }">系统</c:if>
			&nbsp;
      </td>
      </tr>
    <tr align="center" class="trForContent1"  valign="middle">
      <td class=border_top_right4 align="right" >调账理由：</td>
      <td class="border_top_right4" align="left">
        	 	<textarea rows="5" cols="20" disabled="disabled">${reconcileAcceptMain.applyReason }</textarea>
      </td>
     </tr>
  </table>
  <div align="center">
	 <a class="s03" href="#" onclick="javascript:history.go(-1);"><img
			src="./images/goback.jpg" border="0"> </a>
      </div>
  
 