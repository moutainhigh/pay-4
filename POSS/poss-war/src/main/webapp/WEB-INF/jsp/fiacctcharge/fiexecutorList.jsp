<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
	table.tablesorter tbody .tr_bg1 td{ background-color:#fff;}
	table.tablesorter tbody .tr_bg2 td{ background-color:#DDECFB;}
</style>

<div id="titleDiv">
<form action="" method="post" id="mainfromAudit" name="mainfromAudit">
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
					    <th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);" />全选</th>
						<th>银行名称</th>
						<th>批次号</th>
						<th>订单号</th>
						<th>银行订单号</th>
						<th>金额</th>
						<th>交易状态</th>
						<th>日期</th>
						<th>申请人</th>
						<th>申请时间</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody> 

					   <c:forEach items="${requestScope.paramsList}" var="parammap">    
						<tr class="tr_bg1">
					    <td >
						   <c:choose>
								<c:when test="${parammap.status eq '0'}">
									<input type="checkbox"  name="choose" 
								value="${parammap.batchNo},${parammap.orderNo},${parammap.bankOrderNo},${parammap.proposer},${parammap.followno},${parammap.auditid},<fmt:formatNumber value="${parammap.amount*0.001}" pattern="#0.00"  />,${parammap.billType}" onclick="selectAllcheckBoxunchecked(this);"/>
								</c:when>
							</c:choose>    
		              	</td>
						<td>
						     <c:choose>
								<c:when test="${parammap.bankName eq parammap.itemname}">
										${parammap.bankName}
								</c:when>
								<c:otherwise>
                                        <font color="red">${parammap.bankName}</font>  
                                </c:otherwise>
							</c:choose>
						
						</td>
						<td>
							${parammap.batchNo}
						</td>
						<td>
							${parammap.orderNo}
						</td>
						<td>
							${parammap.bankOrderNo}
						</td>
						<td>
							<fmt:formatNumber value="${parammap.amount*0.001}" pattern="#0.00"  />
						</td>
						<td>
						     成功
						</td>
				        <td>
							<fmt:formatDate value="${parammap.applydate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
                        <td>
							${parammap.proposer}
						</td>
						<td>
							<fmt:formatDate value="${parammap.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<c:choose>
								<c:when test="${parammap.status eq '0'}">
									未审核
								</c:when>
								<c:when test="${parammap.status eq '1'}">
									成功
								</c:when>
								<c:when test="${parammap.status eq '2'}">
									失败
								</c:when>
							</c:choose>
						</td>
						
				</tr>

			
			    <tr class="tr_bg2">
					<td></td>
					<td> ${parammap.itemname} </td>
					<td>  </td>
					<td> ${parammap.depositprotocolno} </td>
					<td> 
					      <c:if test="${not (parammap.returnNo eq 'null')}">
									 ${parammap.returnNo}
						  </c:if>
					 </td>
					<td> <fmt:formatNumber value="${parammap.depositamount*0.001}" pattern="#0.00"  /></td>
					

					<td>  <c:choose>
								<c:when test="${parammap.depositstatus eq '0'}">
									处理中
								</c:when>
								<c:when test="${parammap.depositstatus eq '1'}">
									成功
								</c:when>
								<c:when test="${parammap.depositstatus eq '2'}">
									失败
								</c:when>
							
							</c:choose> </td>
					<td>  <fmt:formatDate value="${parammap.depositcreatedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td colspan="5"></td>
				</tr>

			</c:forEach>
		</tbody>
		
	</table>
	<tr class="trForContent1">
				<td class="border_top_right4" align="right">备   注：</td>
				<td class="border_top_right4"><textarea cols="40" rows="5" name="comments" id="comments"></textarea></td>
				&nbsp;&nbsp;<font color="red"><p class="g_tips red" id="remkinfo"></p></font>
	</tr>
	<div > 已选择总比数：
			<input type="text" id="countNum" name="countNum" value="${countNum}" size=4 readonly="true"/>
			已选择总金额：
			<input type="text" id="countamount" name="countamount" value="<fmt:formatNumber value="${countamount*0.001}" pattern="#0.00"  />" size=14 readonly="true"/>　　　　
			<li:pagination methodName="executorsearch" pageBean="${page}" sytleName="black2"/>
			<p style="margin-top:15px;">
				<input type="button" name="audityes" class="button2" value="通 过" onclick="auditsubmit('1');"/>&nbsp;&nbsp;&nbsp;
				<input type="button" name="auditno"  class="button2" value="拒 绝" onclick="auditsubmit('0');"/>
			</p>
		</div>
	</form>  
 </div>
 <script type="text/javascript">
	
	//id的全选或全部取消
	function selectAll() {
		
		var choosenum = 0;
		var amount = 0;
		if($("#checkAll").attr("checked")){
			$("input[name='choose']").each(function(index){
				this.checked = true;
				choosenum = index;
				amount += parseFloat($(this).val().split(',')[6]);
				$("#countNum").val(choosenum +1);
			});
	
		} else {
			$("input[name='choose']").each(function() {
				this.checked = false;
				$("#countNum").val("0");
			$("#countamount").val("");
			});
			
		}

		//var len=$("input[type=checkbox][name=choose][checked]").length;
		var objs=$("input[type=checkbox][name=choose][checked]");
		
		
		amount=Number(amount).toFixed(2);
		
		$("#countamount").val(amount);
	
	}
	//取消一个选择单位，去掉全选框的勾
	function selectAllcheckBoxunchecked(obj){
		var choosenum = 0;
	    if(!obj.checked){
		  $("#checkAll").attr("checked",false);
		 }
		var objs= document.getElementsByName('choose'); 
		var amount = 0;
		for(var i = 0; i<objs.length;i++){
			if(objs[i].checked){
				choosenum++;
				amount += parseFloat(objs[i].value.split(',')[6]);
			}
		}
		amount=Number(amount).toFixed(2);
		$("#countNum").val(choosenum);
		$("#countamount").val(amount);
	}


    function auditsubmit(flag){
		
		var num=0;
		var sequenceId = "";
		var len=$("input[type=checkbox][name=choose][checked]").length;
		var choose = $("input[name='choose']").val();
		$("input[name='choose']").each(function(){
			if(this.checked == true){
				num++;
				sequenceId+=this.value+",";
			}
		});
		if(num<=0){
			alert("请选择数据");
			return false;
		}
		var comments =  $.trim($('#comments').val()) ;
		if(flag==0){
			//拒绝
			 if(comments.length==0){
					 $('#remkinfo').html('拒绝时请输入：拒绝理由');
					  return false;
			    }
			    if(comments.length>512){
					  $('#remkinfo').html('审核说明不能大于512个汉字');
					  return false;
				}
			if(confirm("确认审核拒绝?")){
                auditchoose();
				document.mainfromAudit.action="batchExecutor.htm?method=onSubmit&flag="+flag;
		        document.mainfromAudit.submit();
			}
		}else{
			//通过
			if(confirm("确认审核通过?")){
				if(num>100){
				  alert('批量审核通过时，一次最多100笔');
				  return false;
				}
				auditchoose();
				document.mainfromAudit.action="batchExecutor.htm?method=onSubmit&flag="+flag;
				document.mainfromAudit.submit();
			}
		}
	}
	function auditchoose() {
		
			$('#infoLoadingDiv').dialog('open');
			$("#audityes").attr({"disabled":"disabled"});
			$("#auditno").attr({"disabled":"disabled"});

	}
</script>