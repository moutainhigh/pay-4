<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<HTML>
<head>
<style type="text/css">
body{font-size: 12px; font-family: 宋体}
.textbox {border-right: #cccccc 1px solid; border-top: #cccccc 1px solid; border-left: #cccccc 1px solid; color: #333333; border-bottom: #cccccc 1px solid; height: 20px;widows:120px; background-color: #fdfdfd}
.button {border-right: #888888 1px solid; border-top: #f4f4f4 1px solid; border-left: #f4f4f4 1px solid; COLOR: #000000; padding-top: 2px; border-bottom: #888888 1px solid}
td{height: 20px;}
.td1{font-weight: bold;text-align: center;}
</style>
<script type="text/javascript">
function mobileBillsQuery(pageNo,totalCount) {
      
	var paramaters = $("#command").serialize().split('&');
	var count = 0 ;
	for(var i=0;i<paramaters.length;i++){
		
		if(i == 0 && paramaters[i].split('=')[1]==-1){
			  count +=1;
		}
	    if(paramaters[i].split('=')[1]==""){
		    count +=1;
		}      	
     }
    if(paramaters.length == count){
        alert('请选择一个条件');
    	return false;
       
    }
    $('#infoLoadingDiv').dialog('open');
	var datas = $("#command").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalBill.do?method=loadMobileBillsSearch",
		data: datas,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#mobileBillsList').html(result);
		}
	});
}
function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	 
	 $("#status").get(0).selectedIndex=0;

	 $("#createDate").attr("disabled", true);
	 $("#endDate").attr("disabled", true);
	}

function setTime(){
	if($("#orderId").val() != "" || $("#mobileNo").val() != ""){
		$("#createDate").attr("disabled", false);
		 $("#endDate").attr("disabled", false);
	}else{
		$("#createDate").attr("disabled", true);
		 $("#endDate").attr("disabled", true);
	}
}

$().ready(function(){
	$("#createDate").attr("disabled", true);
	 $("#endDate").attr("disabled", true);

});
</script>
</head>
<body>
     
<table cellSpacing=0 cellPadding=0 width="98%" border=0 style="margin-top: 20px;">
   <tbody>
     <tr>
       <td vAlign=top width="100%" bgColor=#ffffff>
       
     
          <table borderColor="#cccccc" cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
            <tbody>
                 <tr>
                     <td height=25>
                     <form action="" name="command" id="command">
                       <table cellSpacing="0" cellPadding="2" border="0" align="center" >
                         <tbody>
                              <tr >
                                 <td class="td1">充值状态</td>
                                 <td> <select id="status"  name="status" style="width: 120px;">
                                          <option value="-1">全部</option>
						                  <option value="0" >初始</option>
						                  <option value="1" >充值中</option>
						                  <option value="2" >充值成功</option>
						                  <option value="3" >充值部分完成</option>
						                  <option value="4" >充值失败</option>
                                 </select>
                                 </td>
                                 <td class="td1">起止时间</td>
                                 <td><li:dateTime id="createDate" width="120" onfocusId="endDate"/></td>
                                 <td>-<li:dateTime width="120" id="endDate" /></td>
                                 <td >&nbsp;</td>
							 </tr>
							 <tr>
                                 <td class="td1">充值订单号</td>
                                 <td><input class="textbox" id="orderId"  name="orderId" onchange="setTime()"/></td>
                                 <td class="td1">充值号码</td>
                                 <td><input class="textbox" id="mobileNo"  name="mobileNo" onchange="setTime()"/></td>
                                 <td class="td1">WY 订单号</td>
                                 <td ><input class="textbox" id="channelSeqId"  name="channelSeqId"/></td>
                              </tr>
                              <tr>
                              <td align="center" colspan="5">
                                  <input class="button"  type="button" value="查询 " onclick="mobileBillsQuery()"/>
                                  <input class="button"  type="button" value="清空" onclick="resetForm();"/>
                                 </td>
                              </tr> 
                                   </tbody>
                                   </table>
                           </form>
                   </td></tr>
          <tr>
          <td>
          <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			  <img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		 </div>
           <div id="mobileBillsList"> 
                  
           </div>
           </td>
           </tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>
