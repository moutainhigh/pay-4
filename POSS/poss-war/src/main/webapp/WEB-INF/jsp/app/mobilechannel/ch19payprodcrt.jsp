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
function createProd() {
	  if($("#prodId").val() == ""){
        alert('请输入产品编号');
    	return false;
      }else if($("#prodContent").val() == ""){
    	  alert("请输入产品面额");
          return false;
      }else if($("#provinceCode").val() == ""){
          alert("请选择产品支持地区");
          return false;
      }else{
    	  var datas = $("#command").serialize();
    	  $.ajax({
    			type: "POST",
    			url: "${ctx}/ch19payProd.do?method=create",
    			data: datas,
    			
    			success: function() {
    				alert("产品添加成功");
    				resetForm();
    			}
			});    	
        }
}

$(function(){
	$("#provinceCode").blur(function(){
		var datas = $("#command").serialize();
	  $.ajax({
			type: "POST",
			url: "${ctx}/ch19payProd.do?method=getCityList",
			data: datas,
			dataType : "html",
			success: function(result) {
				$("#cityCode").html("<option value=''>&nbsp;</option>" + result);
				
			}
		});
	});
});

function closeTab(){
	parent.closePage("${ctx}/ch19payProd.do?method=create");
	
}

function resetForm(){
	//清空文本框
	 $('input[type=text]').each(function(){
		 $(this).val(""); 
	  });
	 
	
	}
$(document).ready(function(){
	  $.getJSON(
			  "${ctx}/ch19payProd.do?method=channelList",
			  function(data_p){
				  for(var i=0;i<data_p.length;i++){
						$("#prodChannel").append('<option value='+data_p[i].code+'>'+data_p[i].name+'</option>');
					}
			}
		);
	
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
                       <table cellSpacing="0" cellPadding="2" border="0" align="center">
                         <tbody>
                              <tr >
                                 <td class="td1">产品编号</td>
                                 <td><input class="textbox" name="prodId" id="prodId"/> </td>
                                 <td class="td1">产品面额</td>
                                 <td><input class="textbox" name="prodContent" id="prodContent"/></td>
                                 <td class="td1">渠道名称</td>
                                 <td style="width: 18%">
                                 <select name="prodChannel" id="prodChannel">
                                 </select> </td>
                                 </tr>
                                 <tr>
                                 <td class="td1">支持地区</td>
                                 <td><select name="provinceCode" id="provinceCode"/>
                                 	<option value="">&nbsp;</option>
                                 	<c:forEach items="${provinceMP}" var="ch19payprod">
                                 	<option value="${ch19payprod.code}"><c:out value="${ch19payprod.name}" /></option>
                                 	</c:forEach>
                                 </select></td>
                                  <td><select name="cityCode" id="cityCode" style="size:270"/>
                                 
                                 </select></td>
                                 <td class="td1">运营商</td>
                                 <td> <select id="prodIsp" name="prodIsp">
                                 		<option value="0">移动</option>
										<option value="1">联通</option>
										<option value="2">电信</option>
									</select></td>
								 <td class="td1">产品类型</td>
                                 <td> <select id="prodType" name="prodType">
                                 		<option value="0">移动电话</option>
										<option value="1">固定电话</option>
										<option value="2">小灵通</option>
									</select></td>
                                 <td class="td1">有效性</td>
                                 <td><select id="delTag" name="delTag">
                                 		<option value="0">有效</option>
										<option value="1">无效</option>
									</select></td>
							 </tr>
							 <tr>
							       <td align="center">
                                        <input class="button"  type="button" value="确定 " onclick="createProd();"/>
                                   </td>
                                   <td align="center">
                                        <input class="button"  type="button" value="清空 " onclick="resetForm();" />
                                  </td>
                                  <td align="center">
                                        <input class="button"  type="button" value="关闭 " onclick="closeTab();" />
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
