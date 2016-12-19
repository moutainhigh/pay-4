<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr> 
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">调账日志详情<!-- 系统 --></font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
 <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4" >银行名称：</td>
      <td class="border_top_right4">
      	${ reconcileAcceptMain.bankCode}
      </td>
    </tr>
  
     <tr class="trForContent1">
      <td class=border_top_right4 align="right" width="50%">服务代码：</td>
      <td class="border_top_right4" width="50%">
        	${reconcileAcceptMain.providerCode}
      </td>
      </tr>
      
      <tr class="trForContent1">
      <td class=border_top_right4 align="right" width="50%">交易日期：</td>
      <td class="border_top_right4" width="50%">
        <fmt:formatDate value="${reconcileAcceptMain.busiTime}" type="both"/>
      </td>
    </tr>
    
    <tr class="trForContent1">
      <td class=border_top_right4 align="right" width="50%">系统订单号：</td>
      <td class="border_top_right4" width="50%">
        	 ${reconcileAcceptMain.accountSeq }
      </td>
    </tr>  
    <tr class="trForContent1">
    
      <td class=border_top_right4 align="right" width="50%">系统交易金额：</td>
      <td class="border_top_right4" width="50%">
        	 <fmt:formatNumber value="${reconcileAcceptMain.accountAmount}" pattern="#,##0.00"/>
      </td>
      
    </tr>
    <tr class="trForContent1">
        <td class=border_top_right4 align="right" >错账方：</td>
      <td class="border_top_right4" align="left" >
        	系统
      </td>
      </tr><!--
    <tr>
      <td class=border_top_right4 align="right" width="50%">调账理由：</td>
      <td class="border_top_right4" width="50%">
        	 	<textarea rows="5" cols="20" disabled="disabled">${reconcileAcceptMain.applyReason }</textarea>
      </td>
     </tr>
     -->
     	<c:if test="${not empty fundsList}">
	  <table id="" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
					<th >序号</th>
					<th >操作时间</th>
					<th >操作人</th>
					<th >操作行为</th>
					<th >操作理由</th>
			</tr>
	     </thead>
	     <tbody>	
	     		<c:forEach items="${fundsList}" var="reconcileFlowtFunds" varStatus="status">
					<tr >
						<td ><c:out value="${status.count}" /></td>
						<td ><fmt:formatDate value="${reconcileFlowtFunds.processTime}" type="both"/></td>
						<td >${reconcileFlowtFunds.opertor}</td>
						<td >
							${reconcileFlowtFunds.nodeKyDesc}
						</td>
						<td >
							${reconcileFlowtFunds.processRemarks}
						</td>
					</tr>
				</c:forEach>
			</tbody>
	     	</table>
     	</c:if>  </table>
	     <div align="center" >  
	         <input type="button" class="button2" name="submitBack" onclick="javascript:history.go(-1);" value="返回">
	    </div>
  

 