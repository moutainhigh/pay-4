<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<script type="text/javascript">
function checkProd(){
	if($("#selectAll").attr("checked")){
		$("input[name=prodItem]").attr("checked","checked");
	}else{
		$("input[name=prodItem]").attr("checked","");
	}
}
function batchOperate(tag){
	//$("#command").submit();
	//alert($("input:checked").attr("value"));
	var chk_val=[];
	$('input[name="prodItem"]:checked').each(function(){
		chk_val.push($(this).val());
	});
	$('#infoLoadingDiv').dialog('open');
	var datas = "items="+chk_val +"&tag="+tag;
	$.ajax({
		type: "POST",
		url: "${ctx}/ch19payProd.do?method=mobileProdBatchOperate",
		data: datas,
		success: function() {
			$('#infoLoadingDiv').dialog('close');
			refundPersonalBillsQuery();
		}
	});
	
}
function prodOperate(sequenceId,delTag){
	$('#infoLoadingDiv').dialog('open');
	var datas = "sequenceId="+sequenceId +"&delTag="+delTag;
	$.ajax({
		type: "POST",
		url: "${ctx}/ch19payProd.do?method=updStatus",
		data: datas,
		success: function() {
			$('#infoLoadingDiv').dialog('close');
			refundPersonalBillsQuery();
		}
	});
}
</script>
</head>
<input type="button" class="button" value="批量有效" onclick="batchOperate(0);"/><input type="button" class="button" value="批量无效" onclick="batchOperate(1);"/>
   <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
              	<td><input type="checkbox" name="selectAll" id="selectAll" onclick="checkProd();"/>全选</td>
                <td>产品编号</td>
                <td>产品面额</td>
                <td>渠道名称</td>
                <td>所属省</td>
                <td>所属市</td>
                <td>运营商</td>
                <td>最后修改时间</td>
                <td>状态</td>
                <td>操作</td>
               </tr>
              <c:forEach items="${page.result}" var="ch19payprod">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                   <td><input type="checkbox" name="prodItem" value="${ch19payprod.prodId}" id="prodItems"/></td>	
                   <td>${ch19payprod.prodId}</td>
                   <td>${ch19payprod.prodContent}</td>
                   <td>${ch19payprod.prodChannel}</td>
                   <td>${ch19payprod.provName}</td>
                   <td>${ch19payprod.cityName}</td>
                   <td>
                 	<c:if test="${ch19payprod.prodIsp eq 0}">移动</c:if>
                   	<c:if test="${ch19payprod.prodIsp eq 1}">联通</c:if>
                   	<c:if test="${ch19payprod.prodIsp eq 2}">电信</c:if>
                   </td>
                   <td>${ch19payprod.lastUpdDt}</td>
                   <td>
                    <c:if test="${ch19payprod.delTag eq 0}">有效</c:if>
                   	<c:if test="${ch19payprod.delTag eq 1}">无效</c:if>
                   </td>
                   <td>
                   <c:if test="${ch19payprod.delTag eq 0}"><a href="#" onclick="prodOperate('${ch19payprod.sequenceId}',1)">设为无效</a></c:if>
                   <c:if test="${ch19payprod.delTag eq 1}"><a href="#" onclick="prodOperate('${ch19payprod.sequenceId}',0)">设为有效</a></c:if>
                   </td>
			     </tr>
              </c:forEach>
               <c:if test="${empty page}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="16" align="center">没有记录</td>
              </tr>
              </c:if>
           </tbody>
           </table>
             <li:pagination methodName="refundPersonalBillsQuery" pageBean="${page}" sytleName="black2"/>
