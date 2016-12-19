<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<form action="" method="post" name="mainfrom" id="mainfrom">
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead>
					<tr>
					    <th>选择</th>
						<th>批次号</th>
						<th>操作员</th>
						<th>入库时间</th>
						<th>导入文件名</th>
						<th>基准时间</th>
						<th>导入总比数</th>
						<th>导入总金额</th>
						<th>文件大小(k)</th>
					</tr>
				</thead>
				<tbody> 
				     <c:forEach items="${supplementBankDocList}" var="supp">    
					<tr>
					    <td >
							<input type="radio" id="suppInfos" name="suppInfos" 
					           value="${supp.batchRepairId},${supp.batchNo},${supp.countNum},${supp.countAmount},${supp.dateTime}"/>
		              	</td>
						<td>
							${supp.batchNo}
						</td>
						<td>
							${supp.operator}
						</td>
						<td>
							
							<fmt:formatDate value="${supp.systemdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${supp.fileName}
						</td>
						<td>
							
							<fmt:formatDate value="${supp.dateTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${supp.countNum}
						</td>
				        <td>	
							<fmt:formatNumber value="${supp.countAmount*0.001}" pattern="#0.00"  />
						</td>
                        <td>
							${supp.fileSize}
						</td>
						
				</tr>
				</c:forEach>
		</tbody>
	</table>
	 <li:pagination methodName="search" pageBean="${page}" sytleName="black2"/>
	 <tr>
			   <td>
			   <input type="button"   id="back" class="button2" value="确认比对" onclick="javascript:compare();">
			   </td>
			   </tr>
	</form>  

 <script type="text/javascript">
	function compare(){	 
		if($("input[name='suppInfos']:checked").size() == 0){
			alert("请您先选择记录再确认比对!");
			return false;
		}
		var radiovlue = $("input[@type=radio][name=suppInfos][checked]").val();
		array = radiovlue.split(",");
		var batchsupplementId=array[0];
		var batchNo=array[1];
		var countNum=array[2];
		var countAmount=array[3];
		var dateTime=array[4];
		if(confirm("您是否确认比对?")){
			document.mainfrom.action="batchCompare.htm?method=onSubmit&batchsupplementId="+batchsupplementId+"&batchNo="+batchNo+"&countNum="+countNum+"&countAmount="+countAmount+"&dateTime="+dateTime;
			document.mainfrom.submit();
		}
	}
	function pageSubmit(pageNo,totalCount,pageSize){
		var url = "batchCompare.htm?method=search";
		url+="&querybatchNo=${batchNo}&countNum=${countNum}&countAmount=${countAmount}&startTime=${startTime}&endTime=${endTime}&queryoperator=${operator}"
		url+="&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
		document.mainfrom.action = url;
		document.mainfrom.submit();
	}
	
</script>