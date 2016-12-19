<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>风控交易监控预警日报表规则配置</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0)||isNaN(fData) )
		 
		}

		 function setrule(){
		 var memberduration =$('#memberduration').val();
		  var menbertimes =$('#menbertimes').val();
		   var emailduration =$('#emailduration').val();
		  var emailtimes =$('#emailtimes').val();
		 if(isEmpty(memberduration)) {alert("分钟请输入数字");return false;}
			 if(isEmpty(menbertimes)) {alert("笔数请输入数字");return false;}
			  if(isEmpty(emailduration)) {alert("小时请输入数字");return false;}
			   if(isEmpty(emailtimes)) {alert("次数请输入数字");return false;}
		window.location="${ctx}/orderrisk/orderriskmonitor.do?method=modyrule&memberduration="+memberduration+"&menbertimes="+menbertimes+"&emailduration="+emailduration+"&emailtimes="+emailtimes; 
			    
        }
	    
</script>
</head>

<body>
	


<h2 class="panel_title">风控交易监控预警日报表规则配置</h2>
<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<br/>
    <tr class="trForContent1">
    	<td align="center" class="border_top_right4">规则1：同一会员号在
      
	      	<input  type="text" id="memberduration" name="memberduration" size="4" value="${ memberduration}">分钟内，失败交易超过<input type="text"  name="menbertimes"  id="menbertimes" size="4" value="${ menbertimes}"/>笔，则预警
      	</td>
    </tr>
     <tr class="trForContent1">
    	<td align="center" class="border_top_right4">规则2：同一email地址
      
	      	<input type="text" id="emailduration" name="emailduration" size="4" value="${ emailduration}"/>小时内，累计交易次数达<input type="text"  name="emailtimes"  id="emailtimes" size="4" value="${ emailtimes}"/>次，则预警
      	</td>
    </tr>
    <tr class="trForContent1">
    	<td align="center" class="border_top_right4">规则3：收货国家与账单国家不符，则预警<font color="red">（该规则仅针对实物类商户）</font>
      	</td>
    </tr>
   
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
       <input type="button" onclick="setrule();" name="submitB" value="确认修改" class="button2">
      <input type="button" onclick="history.go(-1);" name="submitBtn" value="返回" class="button2">
      
      </td>
    </tr>
  </table>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>

<script type="text/javascript">
	//获取一级菜单长度     
	var select1_len = document.form1.businessType.options.length;     
	var select2 = new Array(select1_len);     
	//把一级菜单都设为数组     
	for (i=0; i < select1_len; i++)     
	{ 
		select2[i] = new Array();
	}     
	//定义基本选项     
	select2[0][0] = new Option("选择所有", "");  
	
	select2[1][0] = new Option("选择所有", "");  
	select2[1][1] = new Option("初始状态", "100");     
	select2[1][2] = new Option("处理中", "101");
	select2[1][3] = new Option("申请失败", "102");
	select2[1][4] = new Option("处理成功", "111");
	select2[1][5] = new Option("处理失败", "112");   
	select2[1][6] = new Option("已经退票", "113");

	select2[2][0] = new Option("选择所有", "");
	select2[2][1] = new Option("退款处理中", "101");     
	select2[2][2] = new Option("退款成功", "111");     
	select2[2][3] = new Option("退款失败", "112");     

	select2[3][0] = new Option("选择所有", "");
	select2[3][1] = new Option("处理中", "0");     
	select2[3][2] = new Option("成功", "1");     
	select2[3][3] = new Option("失败", "2");    
	
	select2[4][0] = new Option("选择所有", "");
	select2[4][1] = new Option("退帐户处理中", "1");     
	select2[4][2] = new Option("退帐户成功", "2");
	select2[4][3] = new Option("退帐户失败", "3");
	select2[4][4] = new Option("退帐户成功,充退处理中", "4");
	select2[4][5] = new Option("退帐户成功,充退成功", "5");
	select2[4][6] = new Option("退帐户成功,充退失败", "6");
	select2[4][7] = new Option("处理失败", "7");

	var channel = new Array(3); 
	channel[0] = new Array();
	channel[1] = new Array();
	channel[2] = new Array();
	channel[0][0] = new Option("选择所有", "");
    
    channel[1][0] = new Option("选择所有", "");
    channel[2][0] = new Option("选择所有", "");
    
    var i=1;
	<c:forEach items="${fundOutChennal}" var="dto">
		channel[1][i] = new Option('${dto.bankName}', '${dto.bankCode}');
		i++;
	</c:forEach>

	var j = 1;
	<c:forEach items="${fundInChennal}" var="dto">
		channel[2][j] = new Option('${dto.bankName}', '${dto.bankCode}');
		j++;
	</c:forEach>
	//联动函数     
	function redirec(x)
	{
		var temp = document.form1.orderStatus;
		for(m=temp.options.length-1;m >= select2[x].length;m--){
			 temp.options[m]=null;
		}
		for (i=0;i<select2[x].length;i++){
			temp.options[i]=new Option(select2[x][i].text,select2[x][i].value);
		}
		temp.options[0].selected=true;  

		
		var tempChannel = document.form1.channel;
		if(x > 2){
			for(a = tempChannel.options.length-1;a >= channel[2].length;a--){
				tempChannel.options[a] = null;
			}
			for (i=0;i<channel[2].length;i++){
				tempChannel.options[i] = new Option(channel[2][i].text,channel[2][i].value);
			}
		}else{
			for(a = tempChannel.options.length-1;a >= channel[1].length;a--){
				tempChannel.options[a] = null;
			}
			for (i=0;i<channel[1].length;i++){
				tempChannel.options[i] = new Option(channel[1][i].text,channel[1][i].value);
			}
		}
		tempChannel.options[0].selected = true; 
			   
	}

</script>
</html>
