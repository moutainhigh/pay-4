<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 
	<!-- 调账审核系统 -->
	<form action="" method="post" name="fromaudit">
		<input type="hidden"  name="id" value="${reconcileAcceptMain.id}"  />
		<input type="hidden"  name="acceptKy" value="${reconcileAcceptMain.acceptKy}"  />
 		<input type="hidden" name="mark"  value="" />
 		<input type="hidden"  value="${reconcileAcceptMain.accountSeq }" name="accountSeq"/>
 		<input type="hidden"  value="" name="remark"/>
  		<input type="hidden"  value='<fmt:formatNumber value="${reconcileAcceptMain.applyAmount}" pattern="#,##0.00"/>' name="applyAmount"/>
 
		 <table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
		 	<tr  align="center" class="trForContent1"  valign="middle">
				<td align="right" class="border_top_right4" >银行名称：</td>
		      	<td class="border_top_right4" align="left">
		      		${ reconcileAcceptMain.bankCode}
		      	</td>
		    </tr>
		    <tr  align="center" class="trForContent1"  valign="middle">
		      	<td class=border_top_right4 align="right" >服务代码：</td>
		      	<td class="border_top_right4" align="left">
		        	${reconcileAcceptMain.providerCode}
		      	</td>
		     </tr>
		      <tr  align="center" class="trForContent1"  valign="middle">
		      <td class=border_top_right4 align="right" >交易日期：</td>
		      <td class="border_top_right4" align="left">
		        <fmt:formatDate value="${reconcileAcceptMain.busiTime}" type="date"/>
		      </td>
		    </tr>
		    
		       <tr  align="center" class="trForContent1"  valign="middle">
		        <td class=border_top_right4 align="right" >错账方：</td>
		      <td class="border_top_right4" align="left" >
		      <input type="hidden"  value="${reconcileAcceptMain.busiFlag }" name="busiFlag"/>
		    		系统
		      </td>
		      </tr>
		    
		    <tr  align="center" class="trForContent1"  valign="middle">
		      <td class=border_top_right4 align="right" >系统订单号：</td>
		      <td class="border_top_right4" align="left">
		        	<input type="text"  value="${reconcileAcceptMain.accountSeq }"  disabled="disabled"/>
		      </td>
		    </tr>  
		    <tr  align="center" class="trForContent1"  valign="middle">
		      <td class=border_top_right4 align="right" >系统交易金额：</td>
		      <td class="border_top_right4" align="left">
		        	 <input type="text" value='<fmt:formatNumber value="${reconcileAcceptMain.accountAmount}" pattern="#,##0.00"/>'  disabled="disabled"/>
		      </td>
		    </tr>
		    <tr  align="center" class="trForContent1"  valign="middle">
		      <td class=border_top_right4 align="right" >调账原因：</td>
		      <td class="border_top_right4" align="left">
		        	${reconcileAcceptMain.applyCause}&nbsp;
		      </td>
		     </tr>		
		    <tr  align="center" class="trForContent1"  valign="middle">
		      <td class=border_top_right4 align="right" >调账理由：</td>
		      <td class="border_top_right4" align="left">
		        	 	<textarea rows="5" cols="20" disabled="disabled">${reconcileAcceptMain.applyReason }</textarea>
		      </td>
		     </tr>
		     <tr  align="center" >
		      <td align="center" colspan="3">
		      <c:if test="${reconcileAcceptMain.adjustStatus == '2'}">
		      	<input type="button" onclick="haderReconcileAudit('success');" class="button4" id="submitQuery" value="审核通过">
		      	<input type="button" onclick="haderReconcileAudit('failure');" class="button4" name="submitQuery" value="审核失败">
		      </c:if>
		      
		      <input type="button" onclick="javascript:history.go(-1);" class="button2" name="submitQuery" value="返  回">
		      </td>
		    </tr>
		  </table>
	
	
	
	<div id="dialog-form" title="审核拒绝原因:">
		<textarea id="reason" rows="5" cols="40" class="text ui-widget-content ui-corner-all"  ></textarea>
	</div>
	
 </form>

 <script type="text/javascript">
	 function haderReconcileAudit(obj){
		$("input[name='mark']").val(obj);
		var truthBeTold = window.confirm("确定要提交吗?");
       	if (truthBeTold) {
           	if(obj == 'failure'){
       			$('#dialog-form').dialog('open');
           	}else{
           		submitAudit();
            	}
			
       	}else{
           	return;
       	}
	}

	function submitAudit(){
		document.fromaudit.action="reconcile.reviseAudit.do?method=handerReviseAudit";
		document.fromaudit.submit();
	}
		

	 $("#dialog-form").dialog({
			autoOpen: false,
			height: 300,
			width: 350,
			modal: true,
			buttons: {
				'提交': function() {
						$(this).dialog('close');
						$("input[name='remark']").val($("#reason").val());
						submitAudit();
				},
				'取消': function() {
					$("textarea[name='reason']").val('');
					$(this).dialog('close');
				}
			}
			
		});


		
 </script>