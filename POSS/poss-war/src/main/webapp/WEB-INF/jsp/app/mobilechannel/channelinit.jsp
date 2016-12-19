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
	var paramaters = $("#command").serialize().split('&');
	var count = 0 ;
	for(var i=0;i<paramaters.length;i++){
		
	    if(paramaters[i].split('=')[1]==""){
		    count +=1;
		}      	
     }
    if(paramaters.length == count){
        alert('请输入查询条件');
    	return false;
       
    }
	$('#infoLoadingDiv').dialog('open');
	var datas = $("#command").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/mobileChannel.do?method=getChannelList",
		data: datas,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#mobilechannelList').html(result);
		}
	});
}
function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	}
function showDetail(sqId){
	var url = "${ctx}/mobileChannel.do?method=showDetail&id=" + sqId;
	parent.addMenu("渠道详细",url);
}

function create(){
	parent.addMenu("渠道新增","${ctx}/mobileChannel.do?method=create");
	
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
                                 <td class="td1">渠道编号</td>
                                 <td><input class="textbox" name="code" id="code"/> </td>
                                 <td class="td1">渠道名称</td>
                                 <td><input class="textbox" name="name" id="name"/> </td>
                                 <td class="td1">渠道折扣</td>
                                 <td><input class="textbox" name="discount" id="discount"/> </td>
                                 <td class="td1">系统科目账户</td>
                                 <td><input class="textbox" name="memberAccountCode" id="memberAccountCode"/> </td>
							 </tr>
							 <tr>
							 <td class="td1">支持金额</td>
							 		<td class="td1"><select id="amounts" name="amounts">
							 			<option value="">&nbsp;</option>
										<option value="30">30</option>
										<option value="50">50</option>
										<option value="100">100</option>
										<option value="300">300</option>
										<option value="500">500</option>
										<option value="800">800</option>
										<option value="1000">1000</option>
										</select></td>
	                             <td class="td1">渠道状态</td>
                                 <td> <select id="status" name="status">
                                 		<option value="">&nbsp;</option>
										<option value="0">关闭</option>
										<option value="1">打开</option>
									</select></td>
                                 <td class="td1">充值类型</td>
                                 <td> <select id="chargeType" name="chargeType">
                                 		<option value="">&nbsp;</option>
										<option value="0">慢充</option>
										<option value="1">快充</option>
										</select></td>
                                 <td class="td1">运营商</td>
                                 <td> <select id="bossType" name="bossType">
                                 		<option value="">&nbsp;</option>
										<option value="0">移动</option>
										<option value="1">联通</option>
										<option value="2">电信</option>
									</select></td>
									</tr>
									<tr>
                                 <td align="center">
                                        <input class="button"  type="button" value="查询 " onclick="refundPersonalBillsQuery()"/>
                                   </td>
                                   <td align="center">
                                        <input class="button"  type="button" value="清空 " onclick="resetForm();" />
                                  </td align="center">
                                  <td>
                                   <input class="button"  type="button" value="新增 " onclick="create();" />
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
           <div id="mobilechannelList"> 
                   
           </div>
           </td>
           </tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>
