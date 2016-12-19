<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="" method="post" name="detailForm"><input
	type="hidden" name="refundSeq" /></form>
总笔数：
<font color="red"><b>${count}&nbsp;</b></font>
笔 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 总金额：
<font color="red"><b><fmt:formatNumber value="${sumAmount}"
	pattern="#,##0.00" />&nbsp;</b></font>
元
<form name="assignTaskForm" method="post"
	action="refund.manage.do?method=handerAssignTask">
<table id="detailTable" class="tablesorter" border="0" cellpadding="0"
	cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="allRefundInfos"
				id="allRefundInfos" onclick="checkAll(this);" />选择</th>
			<th>会员号</th>
			<th>会员姓名</th>
			<th>账户类型</th>
			<th>充退流水号</th>
			<th>充退金额（元）</th>
			<th>充退时间</th>
			<th>状态</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="mDto" varStatus="status">
			<tr>
				<td><input type="checkbox" name="refundInfos"
					value="${mDto.refundWorkorder.workorderKy},${mDto.refundWorkorder.workflowKy},${mDto.refundWorkorder.status}"
					onclick="checkRefundInfo(this,'${mDto.refundWorkorder.status}');" />
				</td>
				<td>${mDto.refundOrderM.memberCode}</td>
				<td>${mDto.refundOrderM.memberName}</td>
				<td><c:choose>
					<c:when test="${'10' eq mDto.refundOrderM.memberAccType}">
						人民币
					</c:when>
					<c:otherwise>
						${mDto.refundOrderM.memberAccType}
					</c:otherwise>
				</c:choose></td>
				<td>${mDto.refundOrderM.orderKy}</td>
				<td><fmt:formatNumber value="${mDto.refundOrderM.applyAmount}"
					pattern="#,##0.00" />&nbsp;</td>
				<td><fmt:formatDate value="${mDto.refundOrderM.applyTime}"
					type="date" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><c:choose>
					<c:when test="${'0' eq mDto.refundWorkorder.status}">
						初始
					</c:when>
					<c:when test="${'1' eq mDto.refundWorkorder.status}">
						审核通过
					</c:when>
					<c:when test="${'2' eq mDto.refundWorkorder.status}">
						审核拒绝
					</c:when>
					<c:when test="${'3' eq mDto.refundWorkorder.status}">
						审核滞留
					</c:when>
					<c:when test="${'4' eq mDto.refundWorkorder.status}">
						复核同意
					</c:when>
					<c:when test="${'5' eq mDto.refundWorkorder.status}">
						复核拒绝
					</c:when>
					<c:when test="${'6' eq mDto.refundWorkorder.status}">
						清算拒绝
					</c:when>
					<c:when test="${'7' eq mDto.refundWorkorder.status}">
						已完成
					</c:when>
					<c:otherwise>
						${mDto.refundWorkorder.status}
					</c:otherwise>
				</c:choose></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</form>

<li:pagination methodName="submitByHref" pageBean="${page}"
	sytleName="black2" />

<br />
<br />
<center>选择操作员:<select id="assigner" name="assigner"
	style="width: 120px;"></select> <br />
	<br />
	<input type="button" onclick="transmitTask();" class="button4" value="转派任务" />
</center>

<script type="text/javascript">
	var queryUserFlag = false;
	
	

	function transmitTask(pageNo,totalCount,pageSize){
		if($("input[name='refundInfos']:checked").size() == 0){
			alert("请您先选择充退记录再进行分配!");
			return false;
		}
		var array;
		var tempCh = "";
		var initStatus = "0";
		var tempStatus = "3";
		var flag = true;
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if(0 == index){
				tempCh = array[2];
			}else{
				if(tempCh == initStatus || tempStatus == tempCh){
					if(initStatus != array[2] && tempStatus != array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}else{
					if(initStatus == array[2] || tempStatus == array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}
			}
			
		});
		if(!flag){
			return false;
		}
		
		var assigner = $("#assigner").val();
		if("" == assigner){
			alert("请您先选择操作员再进行分配!");
			return false;
		}

		if(confirm("您是否确定【转派任务】提交?")){			
			//组装参数
			var params = "";
			var size = $("input[name='refundInfos']:checked").size();
			$("input[name='refundInfos']:checked").each(function(index){
					if(index < (size - 1)){
						params += this.value + "##";
					}else{
						params += this.value;
					}
				});
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
						"&handleDatas=" + params + "&assigner=" + assigner + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "refund.manage.do?method=handerTransmitTask",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
	}

	function checkAll(obj){
		$("input[name='refundInfos']").each(function(){
            $(this).attr('checked',obj.checked);
        });

		if(!obj.checked){
			document.getElementById("assigner").options.length = 0;
			queryUserFlag = false;
			return false;
		}
		
		var array;
		var tempCh = "";
		var initStatus = "0";
		var tempStatus = "3";
		var flag = true;
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if(0 == index){
				tempCh = array[2];
			}else{
				if(tempCh == initStatus || tempStatus == tempCh){
					if(initStatus != array[2] && tempStatus != array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}else{
					if(initStatus == array[2] || tempStatus == array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}
			}
			
		});
		if(!flag){
			return false;
		}

		if(!queryUserFlag){
			var checkVal = $("input[name='refundInfos']:checked").eq(0).val();
			var array = checkVal.split(",");
			tempCh = array[2];
			if(tempCh == initStatus || tempStatus == tempCh){
				tempCh = initStatus;
			}else if("1" == tempCh || "2" == tempCh){
				tempCh = "1";
			}
			queryUserInfo(tempCh);
		}
	}

	function queryUserInfo(status){		

		var params = "status=" + status;
		$.ajax({
			type: "POST",
			url: "refund.manage.do?method=queryUserInfo",
			data: params,
			success: function(result) {
				if(null != result && "" != result){
					var data = eval('(' + result + ')');
					var userInfo = data.userInfo;
					if(undefined != userInfo){
						var size = userInfo.length;
						var options = "";
						for(var i = 0; i < size; i++){
							options += "<option value=" + userInfo[i] + ">" + userInfo[i] + "</option>";
						}
						$("#assigner").append(options);
						queryUserFlag = true;
					}else{
						queryUserFlag = false;
					}
				}else{
					queryUserFlag = false;
				}
			}
		});
	}

	function checkRefundInfo(obj,status){
		if(!obj.checked){
			if($("input[name='refundInfos']:checked").size() > 0){
				return false;
			}else{
				document.getElementById("assigner").options.length = 0;
				queryUserFlag = false;
				return false;
			}
		}
		
		var array;
		var tempCh = "";
		var initStatus = "0";
		var tempStatus = "3";
		var flag = true;
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if(0 == index){
				tempCh = array[2];
			}else{
				if(tempCh == initStatus || tempStatus == tempCh){
					if(initStatus != array[2] && tempStatus != array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}else{
					if(initStatus == array[2] || tempStatus == array[2]){
						alert("请您先选择[状态相同]的充退记录再进行分配!");
						flag = false;
						return flag;
					}
				}
			}
			
		});
		if(!flag){
			return false;
		}

		if(!queryUserFlag){
			if(status == initStatus || tempStatus == status){
				status = initStatus;
			}else if("1" == status || "2" == status){
				status = "1";
			}
			queryUserInfo(status);
		}
	}
</script>
