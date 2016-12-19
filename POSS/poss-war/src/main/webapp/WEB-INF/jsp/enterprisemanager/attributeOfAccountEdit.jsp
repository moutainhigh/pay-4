<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
function closePage(){
	parent.closePage('attributeOfAccountEdit.do?accountCode=${accountCode}&type=${type}');
}
function submitForm(){
	if(document.getElementById('frozen').checked){
		document.getElementById('allowIn').checked='true';		
		document.getElementById('allowOut').checked='true';
	}
	document.attributeFormBean.submit();
}

</script>
</head>
<body>


<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">会 员 账 户 属 性</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">会员账户属性</h2>
<c:if test="${sign!=null}">
	<center>
		<font color="red">${sign}</font>
	</center>
</c:if>

<form id="attributeFormBean" name="attributeFormBean" action="attributeOfAccountEdit.do" method="post">
<input type="hidden" id="acctCode" name="acctCode" value="${accountCode}"></input>
<input type="hidden" id="type" name="type" value="${type}"></input>
<table class="border_all2" width="100%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
			<td class="border_top_right4" align="right" >冻结：</td>
			<td class="border_top_right4" align="left" >	
				<input type="checkbox" id="frozen" name="frozen" 
					<c:if test="${accAttribute.frozen == 0}"> 
						checked 
					</c:if>
					<c:if test="${type != 'edit'}"> 
									disabled
					</c:if>
				value="1" />
			</td>					
			<td class="border_top_right4" align="right" >止入：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowIn" name="allowIn" 
					<c:if test="${accAttribute.allowIn == 0}"> 
						checked
					</c:if> 
					<c:if test="${type != 'edit'}"> 
									disabled
					</c:if>
				value="1"/>
			</td>
			<td class="border_top_right4" align="right" >止出：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowOut" name="allowOut" 
					<c:if test="${accAttribute.allowOut == 0}"> 
						checked
					</c:if>
					<c:if test="${type != 'edit'}"> 
									disabled
					</c:if>
				value="1"/>
			</td>
		</tr>		
		<!-- <tr class="trForContent1">
			<td class="border_top_right4" align="right" >充值：</td>
			<td class="border_top_right4" align="left" >	
				<input type="checkbox" id="allowDeposit" name="allowDeposit" 
					<c:if test="${accAttribute.allowDeposit == 1}"> 
						checked
					</c:if> 
				value="1"/>
			</td>								
			<td class="border_top_right4" align="right" >提现：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowWithdrawal" name="allowWithdrawal" 
					<c:if test="${accAttribute.allowWithdrawal == 1}"> 
						checked
					</c:if> 
				value="1"/>
			</td>
			<td class="border_top_right4" align="right" >转账入：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowTransferIn" name="allowTransferIn" 
					<c:if test="${accAttribute.allowTransferIn == 1}"> 
						checked
					</c:if> 
				value="1"/>
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >转账出：</td>
			<td class="border_top_right4" align="left" >	
				<input type="checkbox" id="allowTransferOut" name="allowTransferOut" 
					<c:if test="${accAttribute.allowTransferOut == 1}"> 
						checked
					</c:if> 
				value="1"/>
			</td>	
			 						
			<td class="border_top_right4" align="right" >止入：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowIn" name="allowIn" 
					<c:if test="${accAttribute.allowIn == 1}"> 
						checked
					</c:if> 
				value="1"/>
			</td>
			<td class="border_top_right4" align="right" >止出：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="allowOut" name="allowOut" 
					<c:if test="${accAttribute.allowOut == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >冻结：</td>
			<td class="border_top_right4" align="left" >	
				<input type="checkbox" id="frozen" name="frozen" 
					<c:if test="${accAttribute.frozen == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>								
			<td class="border_top_right4" align="right" >计息：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="bearInterest" name="bearInterest" 
					<c:if test="${accAttribute.bearInterest == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>
			<td class="border_top_right4" align="right" >允许支付：</td>
			<td class="border_top_right4" align="left" >				
				<input type="checkbox" id="payAble" name="payAble" 
					<c:if test="${accAttribute.payAble == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>
		</tr>		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >透支：</td>
			<td class="border_top_right4" align="left" >	
				<input type="checkbox" id="allowOverdraft" name="allowOverdraft" 
					<c:if test="${accAttribute.allowOverdraft == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>								
			<td class="border_top_right4" align="right" >密码保护：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="needProtect" name="needProtect" 
					<c:if test="${accAttribute.needProtect == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>
			<td class="border_top_right4" align="right" >会员管理：</td>
			<td class="border_top_right4" align="left" >
				<input type="checkbox" id="managerable" name="managerable" 
					<c:if test="${accAttribute.managerable == 1}"> 
						checked
					</c:if>
				value="1"/>
			</td>
		</tr>
			-->	
			
			<tr class="trForContent1">
		<td  align="center" colspan="10" class="border_top_right4" >	
		<c:if test="${type == 'edit'}"> 	
			<input type="button" value="保存" onclick="javascript:submitForm();">				
		 </c:if>		
		 	<input type = "button"  onclick="javascript:closePage();" value="关闭">		
		</td>
	</tr>		
</table>

</form>


</body>

