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

function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	}

function create(){
	var datas = $("#command").serialize();
	$.ajax({
		type: "POST",
		url: "${ctx}/mobileChannel.do?method=create",
		data: datas,
		success: function(result) {
			alert("渠道添加成功！");
		}
	});
}

function closeTab(){
	parent.closePage("${ctx}/mobileChannel.do?method=create");
	
}


/*单个移动*/
function moveOptions(objPush1,objGet1,id) { 

	objPush = document.getElementById(objPush1);
	objGet = document.getElementById(objGet1);
    for (var i = 0;i < objPush.length;i++) { 
    	
        if (objPush.options[i].selected) {   
            	
              var pushOpt = objPush.options[i];   
              objGet.options.add(new Option(pushOpt.innerText, pushOpt.value));
              document.getElementById(id).value = document.getElementById(id).value + pushOpt.value+",";
              alert(document.getElementById(id).value);
              objPush.remove(i);   
              i = i - 1;   
        }   
    }   
}
 
/*多个移动*/
function moveAllOptions(objPush,objGet) { 
	
    for (var i = 0;i < objPush.length;i++) {   
          var pushOpt = objPush.options[i];   
          objGet.options.add(new Option(pushOpt.innerText, pushOpt.value));   
          objPush.remove(i);   
          i = i - 1;         
    }   
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
                     <input type="hidden" id="amounts" name="amounts" value="">
                     <input type="hidden" id="mobileProvinces" name="mobileProvinces" value="">
                       <table cellSpacing="0" cellPadding="2" border="0" align="center">
                         <tbody>
                              <tr >
                                 <td width="68" class="td1">渠道编号</td>
                                 <td colspan="3"><input class="textbox" name="code" id="code"/> </td>
                                 <td width="68" class="td1">渠道名称</td>
                                 <td colspan="5"><input class="textbox" name="name" id="name"/> </td>
                                 <td width="68" class="td1">渠道折扣</td>
                                 <td width="170"><input class="textbox" name="discount" id="discount"/> </td>
                                 <td width="102" class="td1">系统科目账户</td>
                                 <td width="170"><input class="textbox" name="memberAccountCode" id="memberAccountCode"/> </td>
							 </tr>
							 <tr>
							 <td rowspan="4" class="td1">支持金额</td>
							 		<td width="87" rowspan="4" class="td1"><select id="amounts2" name="amounts2" ondblclick="moveOptions('amounts2','amounts1','amounts');" size=10>
							 			<option value="30">30</option>
										<option value="50">50</option>
										<option value="100">100</option>
										<option value="300">300</option>
										<option value="500">500</option>
										<option value="800">800</option>
										<option value="1000">1000</option>
										</select></td>
							 		<td width="39" class="td1"></td>
							 		<td width="41" rowspan="4" class="td1"><select id="amounts1" name="amounts1" size=10 ondblclick="moveOptions('amounts1','amounts2','amounts');">
							 		 
							 	   </select></td>
	                             <td rowspan="4" class="td1">渠道状态</td>
                                 <td width="62" rowspan="4"> <select id="status" name="status">
										<option value="1">打开</option>
										<option value="0">关闭</option>
									</select></td>
                                 <td  rowspan="4" class="td1">支持地区</td>
                                 <td  rowspan="4"><select multiple size="10" name="mobileProvincesB" id="mobileProvincesB" ondblclick="moveOptions('mobileProvincesB','mobileProvincesA','mobileProvinces');">
                                  <c:forEach items="${lsProvince}" var="channelProv">
                                  <option value="${channelProv.code}">${channelProv.name}</option>
                                  </c:forEach>
                                  </select>
                                 </td>
                                 <td></td>
                                 <td  rowspan="4"><select multiple size="10" name="mobileProvincesA" id="mobileProvincesA" ondblclick="moveOptions('mobileProvincesA','mobileProvincesB','mobileProvinces');"></select></td>
                                 <td rowspan="4" class="td1">充值类型</td>
                                 <td rowspan="4"> 
                                 <select id="chargeType" name="chargeType">
                                 		<option value="1">快充</option>
                                 		<option value="0">慢充</option>
								 </select></td>
                                 <td rowspan="4" class="td1">运营商</td>
                               <td rowspan="4"> <select id="bossType" name="bossType">
                                 		<option value="0">移动</option>
										<option value="1">联通</option>
										<option value="2">电信</option>
									</select></td>
									</tr>
							 <tr>
							   <td class="td1"><input type="button" size="50" id="seloneAmount" name="seloneAmount" onclick="moveOptions('amounts2','amounts1','amounts');" value="添加&gt;"></td>
							   <td><input type="button" size="50" name="seloneProv"  id="seloneProv" value="添加&gt;" onclick="moveOptions('mobileProvincesB','mobileProvincesA','mobileProvinces');"></td>
					       </tr>
							 <tr>
							   <td class="td1"><input type="button" name="removeoneAmount" id="removeoneAmount" value="&lt;移除" nclick="moveOptions('amounts1','amounts2','amounts');" style="size:50"></td>
							   <td><input type="button" size="50" name="removeoneProv" id="removeoneProv" value="&lt;移除" onclick="moveOptions('mobileProvincesA','mobileProvincesB','mobileProvinces');"></td>
					       </tr>
							 <tr>
							   <td class="td1">&nbsp;</td>
							   <td>&nbsp;</td>
					       </tr>
									<tr>
                                 <td align="center">
                                        <input class="button"  type="button" value="提交 " onclick="create()"/>
                                   </td>
                                   <td colspan="3" align="center">
                                        <input class="button"  type="reset" value="清空 " onclick="resetForm();" />
                                  </td>
                                  <td align="center">
                                  <input class="button"  type="reset" value="关闭 " onclick="closeTab();" />
                                  </td>
                                  
                              </tr> 
                                   </tbody>
                                   </table>
                           </form>
                   </td></tr>
          <tr>
          <td>
           </td>
           </tr>
     </tbody>
     </table>
     </tbody>
   </table>
</body>
</html>
