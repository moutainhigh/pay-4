<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>
<html>
<head>
<title>成本收入查询</title>
<script type="text/javascript">
		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}
		
		function selectOptType(optType){	
			if(1== optType){
				document.getElementById('subMemberCode').disabled=false;
				document.getElementById('innerMemberCode').disabled=true;
			}else{
				document.getElementById('subMemberCode').disabled=true;
				document.getElementById('innerMemberCode').disabled=false;
			}
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			if(isEmpty(startDate)){
				alert("开始时间不能为空");
				return false;
			}
			if(isEmpty(endDate)){
				alert("结束时间不能为空");
				return false;
			}
			if(startDate > endDate){
				alert("开始时间必须小于结束时间");
				return false;
			}
			return true;
		}

		function setTargetVal(source,target){
			var valueList ="";
			$('#'+source+' option:selected').each(function(){
				if($(this).val() != "" && $(this).val() !=null)
					valueList += $(this).val()+",";
				});
				if(valueList != "" && valueList!=null)
					valueList = valueList.substring(0,valueList.length-1);
			$('#'+target).val(valueList);
		}
		
		function userQuery(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
			setTargetVal("innerMemberCode","innerMemberCodeVal");
			setTargetVal("fiBankList","fiBankListVal");
			setTargetVal("foBankList","foBankListVal");
			setTargetVal("prdType","prdTypeVal");
			
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/constingSerarch.do?method=search",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		function changeItem(obj){
			if(obj[0].value=="" && obj.options[0].selected == true){
				obj.remove(0);
				selectAll(obj);
				return ;
			}
			else if(obj[0].value!="") {
				var isTrue =true;
				for(var i =0; i<obj.length;i++){
					if(obj.options[i].selected==false){
						isTrue=false;
					}
				}
				if(!isTrue){
						var option = new Option();
						option.text = "全部";
						option.value="";
						obj.add(option,0);
				}
			}
		}
		function selectAll(obj){
			for(var i =0; i<obj.length;i++){
					obj.options[i].selected=true;
			}
		}
	
	  </script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">成本收入查询</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>
<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="left" class="border_top_right4">
    		交易日期：
    		<input class="Wdate" type="text" id="startDate" name="startDate" value='${startDate}'  onClick="WdatePicker()">
	        	--
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='${endDate}' onClick="WdatePicker()">
    	</td>
      	<td class="border_top_right4">
	    	  用户类型：
	    	 <select name="memberType" id="memberType">
      			<option selected="selected" value="">全部</option>
      			<option value="1">个人</option>
      			<option value="2">企业</option>
       		</select>
      	</td>
    </tr>
    
    <tr class="trForContent1">
      	<td align="left" class="border_top_right4">
    		<label>
				<input type="radio" id="r1"  name="optType" checked="checked" value="1" onclick="selectOptType(1)" />
			</label>
	      	<label for ="r1">
				会员编号：
			</label>
		    <input type="text" name="subMemberCode" id="subMemberCode" maxlength="11" size="16"/>
      	</td>
    	<td class="border_top_right4">
	    	  <label>
			<input type="radio" id="r2"  name="optType" value="2" onclick="selectOptType(2)" />
			</label>
			<label for="r2">
				分子公司：
			</label>
				<select name="innerMemberCode" id="innerMemberCode"  multiple="multiple" disabled="disabled" size="5" style="width:250px" >
        		<option selected="selected" value="">全部</option>
       			<c:forEach items="${memberList}" var="item">
					<option value="${item.memberCode}">
						${item.memberName}
					</option>
				</c:forEach>
        	</select>
        	<input type="hidden" id="innerMemberCodeVal" name="innerMemberCodeVal" />
      	</td>
    </tr>
     <tr class="trForContent1">
    	<td align="left" class="border_top_right4">
			<label for="r2">
				入款银行渠道：
			</label>
			<select name="fiBankList" id="fiBankList"  multiple="multiple"  size="5"  style="width:250px" >
        		<option selected="selected" value="">全部</option>
       			<c:forEach items="${fiBankList}" var="item">
					<option value="${item.id}">
						${item.description}
					</option>
				</c:forEach>
        	</select>
        	<input type="hidden" id="fiBankListVal" name="fiBankListVal" />
		</td>
      	<td class="border_top_right4" >
        	<label for="r2">
			&nbsp;出款银行渠道：
			</label>
			<select name="foBankList"  id="foBankList" multiple="multiple" size="5"  style="width:250px" >
        		<option selected="selected" value="">全部</option>
       			<c:forEach items="${foBankList}" var="item">
					<option value="${item.orgCode}">
						${item.displayName}
					</option>
				</c:forEach>
        	</select>
        	<input type="hidden" id="foBankListVal"  name="foBankListVal" />
      	</td>
    </tr>
        
     <tr class="trForContent1">
    	<td align="left" class="border_top_right4" colspan="2">
			<label for="r2">
				业务类型：&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<select name="prdType" id="prdType" multiple="multiple" size="5"  style="width:250px">
       			<option selected="selected" value="">全部</option>
				<option value="1">银行卡支付平台账户充值</option>
				<option value="2">银行卡支付</option>
				<option value="3">提现</option>
				<option value="4">委托提现</option>
				<option value="5">单笔付款到银行</option>
				<option value="6">批量付款到银行</option>
				<option value="7">提现退票</option>
				<option value="8">直付到银行的退票</option>
				<option value="9">批量直付到银行退票</option>
				<option value="10">网关退款</option>
				<option value="11">充值退款</option>
        	</select>
        	<input type="hidden" id="prdTypeVal" name="prdTypeVal" />
		</td>
    </tr>

    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>

<script type="text/javascript">
 	//changeItem(document.getElementById("fiBankList"));
	//changeItem(document.getElementById("foBankList"));
</script>
</body>
</html>
