<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
	 $(document).ready(function(){
		$("input[type=checkbox]").attr("checked",true);
		//alert($("input[name='allbatchInfos']:checked").size());
	 }); 
	
	function suppsubmit(){
		var countNum = $("input[name='countNum']").val();
		if(countNum!='' && countNum!=0){
		    document.supplementCompareForm.action = "batchCompare.htm?method=supplementPrepare";
		    document.supplementCompareForm.submit();
		}
	}
	function selectAll(obj){
        var choosenum = 0;
		var amount = 0;
		if($("#checkAll").attr("checked")){
			$("input[name='choose']").each(function(index){
				this.checked = true;
				choosenum = index;
				amount += parseFloat($(this).val().split(',')[4]);
				$("#countNum").val(choosenum +1);
			});
	
		} else {
			$("input[name='choose']").each(function() {
				this.checked = false;
				$("#countNum").val("");
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
				amount += parseFloat(objs[i].value.split(',')[4]);
			}
		}
		amount=Number(amount).toFixed(2);
		$("#countNum").val(choosenum);
		$("#countamount").val(amount);
	}
	function suppBack(){
			document.supplementCompareForm.action = "batchCompare.htm?method=index";
		    document.supplementCompareForm.submit();
	}
	function pageSubmit(pageNo,totalCount,pageSize){
		var url = "batchCompare.htm?method=onSubmit";
		url+="&batchNo=${batchNo}&countNum=${countNum}&countAmount=${countAmount}&batchsupplementId=${batchsupplementId}"
		url+="&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
		document.supplementCompareForm.action = url;
		document.supplementCompareForm.submit();
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">需补单条目详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form method="post" name="supplementCompareForm" id="supplementCompareForm" action="">
  <input type="hidden" id="batchsupplementIdQuery" name="batchsupplementIdQuery" value="${batchsupplementId}" />
  <input type="hidden" id="batchNoQuery" name="batchNoQuery" value="${batchNo}" />
 <table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
					    <th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);"/>选择全批</th>
						<th>银行名称</th>
						<th>批次号</th>
						<th>订单号</th>
						<th>银行订单号</th>
						<th>金额</th>
						<th>交易状态</th>
						<th>日期</th>
					</tr>
				</thead>
				<tbody> 
					 <c:forEach items="${supplementCompareResDTOList}" var="supp">    
					<tr>
					    <td >
							<input type="checkbox" name="choose"
								value="${supp.bankName},${batchNo},${supp.orderNo},${supp.bankOrderNo},<fmt:formatNumber value="${supp.amount*0.001}" pattern="#0.00"  />,${supp.stat},<fmt:formatDate value="${supp.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>,${supp.batchsupplementInfoId}" onclick="selectAllcheckBoxunchecked(this);"/>
						</td>
						<td>
							${supp.bankName}
						</td>
						<td>
							${batchNo}
						</td>
						<td>
							${supp.orderNo}
						</td>
						<td>
							${supp.bankOrderNo}
						</td>
						<td>
							<fmt:formatNumber value="${supp.amount*0.001}" pattern="#0.00"  />
						</td>
						<td>
							 <c:choose>
								<c:when test="${supp.stat.code eq '0'}">
									处理中
								</c:when>
								<c:when test="${supp.stat.code eq '1'}">
									成功
								</c:when>
								<c:when test="${supp.stat.code eq '2'}">
									失败
								</c:when>
							
							</c:choose>
						</td>
				        <td>
							<fmt:formatDate value="${supp.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						
				</tr>
				</c:forEach>

		</tbody>
	</table>
 <tr>
			   <td>
			     <li:pagination methodName="pageSubmit" pageBean="${page}" sytleName="black2"/>
			    <tr class="trForContent1">
      <td class="border_top_right4" align="right">已选择总比数：</td>
      <td class="border_top_right4">
        <input type="text" id="countNum" name="countNum" value="${countNum}" size=4 readonly="true"/>
		已选择总金额：
		<input type="text" id="countamount" name="countamount" value="<fmt:formatNumber value="${countAmount*0.001}" pattern="#0.00"  />" size=14 readonly="true"/>
	    <input type="button"   class="button2" value="提交申请" onclick="javascript:suppsubmit();">
	    <input type="button"    class="button2" value="取  消" onclick="javascript:suppBack();">
      </td>
	    
    </tr>

			   </td>
			   </tr>

</form>
