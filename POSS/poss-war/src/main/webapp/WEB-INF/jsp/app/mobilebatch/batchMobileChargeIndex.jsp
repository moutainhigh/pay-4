<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
		<title>选择文件</title>
		<script type="text/javascript">
		
			$(document).ready(function(){
				/*
				$("#btn").click(function(){
					if(checkInfo()){
						if(confirm("确定上传文件吗？"))	{
							$("#frm").submit();
						}
					}
				});
				$("#downloadbtn").click(function(){
					$("#frm3").submit();
				});
				*/
				$("#resetbtn").click(function(){
					resetcondition();
				});
				$("#querybtn").click(function(){
					var startDate = $("#startDate").val();
					var endDate = $("#endDate").val();
					
					if(startDate!=""&&startDate==""){
						alert("请选择结束时间");	
						return false;
					}
					if(startDate==""&&startDate!=""){
						alert("请选择开始时间");	
						return false;
					}
					var fromdate = new Date(startDate.replace(/-/g, "/"));
					var todate = new Date(endDate.replace(/-/g,"/"));
					if(Date.parse(fromdate)-Date.parse(todate)>0){
						alert("结束时间不能早于开始时间");
						return false;
					}
					$("#frm2").submit();
				});
				$("#uploadbtn").click(function(){
					window.open("${ctx}/app/batchmobilecharge.do?method=uploadExcel",'newwindow', 'height=600, width=800, top=100, left=200, toolbar=no,menubar=no, scrollbars=yes, resizable=no,location=no, status=no');
				});
			});

			function checkInfo(){
				var uploadFile=$("#uploadFile").val();
				if(uploadFile.length>0){
					var fileName=uploadFile.substring(uploadFile.lastIndexOf("\\")+1);
					//alert(fileName);
					if(fileName.length>0 && fileName.length<5){
						alert("文件格式不正确");
						return false;
					}				
				} else {
					alert("请选择上传的文件");
					return false;
				}
				
				return true;
			}

			function refuseBatch(id){
				if(confirm("确定撤销该订单吗？"))	{
					$("#sequenceId").val(id);
					//$("#frm2").attr("action","${ctx}/app/refuseOrder.do");
					$("#frm2").attr("action","${ctx}/app/batchmobilecharge.do?method=refuseOrder");
					$("#frm2").submit();
					
				}else{
					return;
				}
				//return false;
				
			}
			function auditBatch(id){
				if(confirm("确定审核该订单吗？"))	{
					$("#sequenceId").val(id);
					//$("#frm2").attr("action","${ctx}/app/refuseOrder.do");
					$("#frm2").attr("action","${ctx}/batchFile_mobile_operate.do?method=checkBatchFile&batchId="+id);
					$("#frm2").submit();
					
				}else{
					return;
				}
				//return false;
				
			}
			function resetcondition(){
				$("#batchFileName").val("");
				$("#batchId").val("");
				$("#status").val("");
				$("#startDate").val("");
				$("#endDate").val("");
				
			}
			<c:if test="${not empty retmsg}">
			alert('<c:out value="${retmsg}"/>');
			</c:if>
		</script>
	</head>
	
	<body>
	<!-- 
		<form action="${ctx}/app/batchmobilechargeupload.do" method="post" enctype="multipart/form-data" id="frm">
			<table class="searchTermTable">
				<tr>
					<td class="textRight">请选择上传文件：</td>
					<td class="textLeft"><input type="file" name="uploadFile" id="uploadFile"/><input type="button" value="上传文件" id="btn"></input></td>
				</tr>
			</table>
			
		</form>
		<form action="${ctx}/app/querybatchmobileInfos.do" method="post"  id="frm2">
		 -->
		  <div style="height: 10px; text-align: left; padding-left: 5px;margin-left: 5px; margin-top: 10px;" >
		 <a href="<c:url value='/app/batchmobilecharge.do?method=downloadMobileExampleFile'/>">范例文件下载</a>
		 </div>
		<form action="${ctx}/app/batchmobilecharge.do?method=queryBatchMobileInfos" method="post"  id="frm2">
		<input type="hidden" name="sequenceId" id="sequenceId" value="tst"></input>
		<input type="hidden" name="resmsg" id="resmsg" value="${resmsg}"></input>
		<table class="searchTermTable">
			<tr>
				<td class="textRight">文件名：</td>
				<td class="textLeft">
				 <input type="text" name="batchFileName" id="batchFileName" value="${batchFileName}"/></td>
				<td class="textRight">批次号：</td>
				<td class="textLeft">
				 <input type="text" name="batchId" id="batchId" value="${batchId}"/></td>
				<!-- 
				 <input type="text" name="sequenceId" id="sequenceId"/></td>
				 -->
				<td class="textRight">状态：</td>
				<td class="textLeft"><select id="status" name="status">
                                 		<option value="" <c:if test="${ empty status  }">selected</c:if>>请选择状态</option>
                                 		<option value="0" <c:if test="${not empty status and status ==0 }">selected</c:if>>未审核</option>
										<option value="1" <c:if test="${not empty status and status ==1 }">selected</c:if>>已审核</option>
										<option value="2" <c:if test="${not empty status and status ==2 }">selected</c:if>>撤销</option>
									</select></td>
				<td class="textRight">提交日期：</td>
				<td class="textLeft">
				<input type="text" name="startDate" id="startDate" style="width: 80px;"  value="${startDate}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				-<input type="text" name="endDate" id="endDate" style="width: 80px;"  value="${endDate}" readonly onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				</td>
				<td class="textRight">
					<input type="button" value="查询" id="querybtn"></input>&nbsp;<input type="button" value="重置" id="resetbtn"></input>&nbsp;<input type="button" value="批量上传" id="uploadbtn"></input>
				</td>
			</tr>
		</table>
		<table id="bankTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead>
	   <tr>
	       <th>批次号</th>
	       <th>文件名</th>
	       <th>总金额</th>
	       <th>提交时间</th>
	       <th>审核时间</th>
	       <th>状态</th>
	       <th>充值结果</th>
	       <th>操作</th>
	     </tr>
	 </thead>
	  <tbody>
	   <c:forEach items="${retList}" var="info">
	      <tr>
	          <td><a href="<c:url value='/app/batchmobilecharge.do?method=downloadMobileChargeInfos&sequenceId=${info.sequenceId}'/>"> ${info.batchId}</a></td>
	          <td>${info.batchFileName}</td>
	          <td>${info.sumAmount}</td>
	          <td><fmt:formatDate value="${info.createdDate}" type="date"/> </td>
	          <td><fmt:formatDate value="${info.updatedDate}" type="date"/></td>
	          <td><c:if test="${info.status == 0}">未审核</c:if><c:if test="${info.status == 1}">已审核</c:if><c:if test="${info.status == 2}">撤单</c:if></td>
	          <!-- 
	          <td><a href="<c:url value='/app/downloadmobilechargeinfos.do?sequenceId=${info.sequenceId}'/>"> ${info.batchId}</a></td>
	          <td><c:if test="${not empty info.payMentId}"><a href="<c:url value='/app/downLoadBatchCharges.do?sequenceId=${info.sequenceId}'/>">下载</a></c:if></td>
	           -->
	          <td><c:if test="${not empty info.payMentId}"><a href="<c:url value='/app/batchmobilecharge.do?method=downLoadMobileChargeresult&sequenceId=${info.sequenceId}'/>">下载</a></c:if></td>
	          <td> <c:if test="${info.status == 0}"><a href="#" onclick="auditBatch('${info.sequenceId}');">审核</a></c:if>&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${info.status == 0}"><a href="#" onclick="refuseBatch('${info.sequenceId}');"/>撤单</a></c:if></td>
	      </tr>
	    </c:forEach>   
	  </tbody>
	</table>
		</form>
		<!-- 
		<form action="${ctx}/app/downLoadBatchCharges.do" method="post" enctype="multipart/form-data" id="frm3">
		<div style="text-align: left;position: absolute;left:50px;top:270px;">
			<span style="width:100px;">查询条件：</span>
			<input type="text" name="sequenceId" id="sequenceId" style="width:215px;height:20px"/><br><br>
			<hr color="gray" noshade="noshade"><br>
			<input type="button" value="查询" id="downloadbtn"></input>
		</div>
		<table>
		</table>
		</form>
		 <table>
		 <c:forEach items="${errorlist}" var="errormsg">
	      <tr>
	          <td>${errormsg}</td>
	      </tr>
	    </c:forEach>  
		</table>
		 -->
		 
	</body>
</html>