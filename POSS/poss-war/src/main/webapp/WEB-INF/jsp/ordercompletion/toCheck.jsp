<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	a:hover{text-decoration:underline;}
</style>
<h2 class="panel_title">补单审核</h2>
<form action="${ctx }/orderCompletionCheck.do?method=doCheck" method="post" id="mainfromId" name="mainfromId" enctype="multipart/form-data">
	<input type="hidden" id="jArray" name=jArray />
	<input type="hidden" id="reqBatchNo" name="reqBatchNo" value="${reqBatchNo }"/>
	<input type="hidden" id="orgCode" name="orgCode"/>
	
	<div style="text-align:center;">
		<p>银行机构：&nbsp;&nbsp;&nbsp;&nbsp;
			<c:forEach var="channel" items="${paymentChannelItems }" varStatus="v">
				<label id="${channel.orgCode }" style="display:none;">${channel.name }</label>
			</c:forEach>
		</p>
		<p>申请笔数：&nbsp;&nbsp;${fn:length(fillRecordInfos) }笔</p>
	</div>
	
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th>渠道订单号</th>
				<th>机构端流水号</th>
				<th>支付金额</th>
				<th>支付币种</th>
				<th>授权码</th>
			</tr>
		</thead>
		<tbody>
			
			<c:forEach var="recordInfo" items="${fillRecordInfos }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
					<td><span></span>${recordInfo.channelOrderNo }<span></span></td>
					<td>
						${recordInfo.returnNo }
					</td>
					<td>${recordInfo.amount }</td>
					<td>${recordInfo.currencyCode }</td>
					<td>${recordInfo.authorization }</td>
				</tr>
			</c:forEach>
		</tbody>
		
	</table>
	
	上传补单审核文件：<input type="file" name="file" id="file" /><br/><br/>
	
	<input type="submit"  name="butSubmit" value="上传" class="button2" onclick="javascript:return retCheck();">
	<input type="button"  name="butSubmit" value="返回" alt="返回" class="button2" onclick="window.history.go(-1)">
	<br/></br/>
	<font style="font-weight:bold;color:red;">${recordInfoNull }</font>
	<font style="font-weight:bold;color:red;">${checkFileNull }</font>
</form>

<script type="text/javascript">

	$(function(){
		$("#jArray").val(encodeURIComponent('${jArray}'));
		$("#" + '${orgCode}').removeAttr("style") ;
		$("#orgCode").val('${orgCode}') ;
	}) ;
	
	function retCheck(){
		var fileName = document.mainfromId.file.value ;
		if(null != fileName && "" != fileName){
			var suffixName = fileName.substring(fileName.lastIndexOf(".")) ;
			if(suffixName != ".xls"){
				alert("文件格式不正确，请选择.xls格式文件上传！") ;
				return false ;
			}
		}else{
			alert("请选择文件！") ;
			return false ;
		}
		return true ;
	}
</script>
