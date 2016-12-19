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
function refundPersonalBillsQuery(pageNo,totalCount) {
/*	var paramaters = $("#command").serialize().split('&');
	var count = 0 ;
	for(var i=0;i<paramaters.length;i++){
		
	    if(paramaters[i].split('=')[1]==""){
		    count +=1;
		}      	
     }
    if(paramaters.length == count){
        alert('请选择一个条件');
    	return false;
       
    }*/
	$('#infoLoadingDiv').dialog('open');
	var datas = $("#command").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refoundPersonal.do?method=refundPersonalSearch",
		data: datas,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#refundPersonalBillsList').html(result);
		}
	});
}
function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	 
	
	}
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
                       <table cellSpacing="0" cellPadding="2" border="0" align="center">
                         <tbody>
                              <tr >
                                 <td class="td1">订单流水号</td>
                                 <td><input class="textbox" name="orderId" id="orderId"/> </td>
                                 <td class="td1">子订单流水号</td>
                                 <td><input class="textbox" name="itemId" id="itemId"/> </td>
                                 <td class="td1">出款帐户</td>
                                 <td><input class="textbox" name="loginName" id="loginName"/> </td>
                                 <td class="td1">收款账户</td>
                                 <td><input class="textbox" name="payeeAcctNo" id="payeeAcctNo"/> </td>
                                
							 </tr>
							 <tr>
							     <td class="td1">交易名称</td>
                                 <td><input class="textbox"  id="billName" name="billName"/></td>
                                 <td class="td1">交易日期</td>
                                 <td> <li:dateTime id="createDate" width="120" onfocusId="complateDate"/></td>
                                 <td class="td1">-</td>
                                 <td> <li:dateTime id="complateDate" width="120" /></td>
                                 <td class="td1">入款流水号</td>
                                 <td> <input class="textbox" name="gatewayTradeNo" id="gatewayTradeNo"/></td>
                              </tr> 
                              <tr>
                                <td class="td1">退款状态</td>
                                 <td>
                                  <select  id="status" name="status" style="width: 120px;">
                                     <option value="0">已退款</option>
                                     <option value="1">退款</option>
                                  </select>
                                 </td>
                                 <td class="td1">退款流水号</td>
                                 <td><input class="textbox" name="eiroId" id="eiroId"/></td>
                                  <td align="center" colspan="2">
                                        <input class="button"  type="button" value="查询 " onclick="refundPersonalBillsQuery();"/>
                                        <input class="button"  type="button" value="清空 " onclick="resetForm();" />
                                  </td>
                                  <td>&nbsp;</td>
                                  <td>&nbsp;</td>
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
           <div id="refundPersonalBillsList"> 
                   
           </div>
           </td>
           </tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>
