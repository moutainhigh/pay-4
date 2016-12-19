<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/fitaglibs.jsp"%>

<script type="text/javascript" src="js/bankChange.js"></script> 

<script type="text/javascript">

	//上传文件的验证格式
	var suffixArray = new Array ( 
									new Array("xml","xls"),//银联
									"txt",//建设银行
									"txt",	//华夏银行                
									new Array("","txt"),	//兴业银行       
									new Array("xlsx","xls"), //骏网         
									"txt",	//农业银行                
									"txt",	//深发展银行              
									"txt",	//中信银行                
									"txt",	//工商银行                
									"txt",	//民生银行                
									"txt",	//光大银行                
									"txt",	//北京农村商业银行        
									"txt",	//邮政储蓄银行            
									"txt",	//交通银行                
									"txt",	//浦发银行                
									"txt",	//北京银行                
									"xls",	//东亚银行                
									"txt") ;//中国银行   
	function checkFileUpload(){
		
		var fileName = $("input[name='orginalFile']").val();
		if ( fileName == '') {
			alert('请上传一个文件.');
			return false;
		} else {
			  var i1=fileName.lastIndexOf("."); 
		      var suffix=fileName.substring(i1+1).toLowerCase();
			  var selectVal = $("select[name='bankCode']").val();
			  if ( selectVal.length== 0 ) {
					alert ( "请选择银行" ) ;
					return false ;
			  }
			  for ( var cnt=0 ; cnt<bankCodeArray.length ; cnt++ ){
				var bc = bankCodeArray[cnt] ;
				if ( bc == selectVal ) {
					if ( i1 == -1 ) {
						submitUpload () ;
						return true ;
					}
					var sx = suffixArray[cnt] ;
					if ( typeof ( sx ) != "string" ) {
						for ( var xy=0 ; xy<sx.length ; xy++ ){
							if  ( sx[xy]== suffix ) {
								submitUpload () ;
								return true ;
							}
						}
						break ;
					}
					else {
						if ( sx == suffix ) {
							submitUpload () ;
							return true ;
						}
					}
					
				}
			}
			alert("导入的文件格式不正确");      
		 	$("input[name='orginalFile']").focus(); 
			return false ;
		}
	}

	function submitUpload(){
		var divB = document.getElementById("divUpload") ;
		if ( divB ) {
			divB.innerHTML = "" ;
		}
		$('#infoLoadingDiv').dialog('open');
		document.upload.action = "reconcile.upload.do?method=handerUpload";
	    document.upload.submit();
	}

</script>

<body onload="bankSelectInit('bankCode')">
	<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21" style="">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">导入银行对账文件</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table> -->
	<h2 class="panel_title">导入银行对账文件</h2>
	<form method="post" name="upload"  action=""  enctype="multipart/form-data">
	  
	  <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td class="border_top_right4"  style="width:120px;" >选择银行</td>
	      <td class="border_top_right4">
	  		<table>
	  			<tr>
	  				<td>
	  					<select name="bankCode" style="width:150px;" id="bankCode" onchange="changeBankInfo2('bankProvider','divCust',this);">
	           
	        			</select>
	  				</td>
	  				<td><div id="bankProvider"></div></td>
	  				<td><div id="divCust"></div></td>
	  			</tr>
	  		</table>
	      </td>
	      
	    </tr>
	    <tr class="trForContent1">
	      <td class="border_top_right4" >上传对账文件</td>
	      <td class="border_top_right4" colspan="2">
	        <input type="file" name="orginalFile" size="50" />
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td class="border_top_right4" align="center" colspan="3" >
	          <a class="s03" href="#" onclick="checkFileUpload();"><input class="button2" type="button" value="确定"></a>
	      </td>
	    </tr>
	  </table>
	  
   	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;数据上传中, 请稍候...
	</div>  
	  
	  <div id="divUpload">
	  	<c:if test="${not empty msg}"> 
	  			<li style="color: red"><c:out value="${msg}" /> </li>
	  		</c:if>
	  
	  <c:if test="${not empty page}">
	  	
	  	  	
	  <c:forEach items="${page.result}" var="reconcileRsultSummary" varStatus="status">
		<table class="tablesorter" style="width:80%" border="0" cellpadding="0" cellspacing="1" align="center">	
			<thead>
				<tr >
					<th>序号</th>
					<th>挂账主体</th>
					<th>总笔数</th>
					<th>总金额</th>
					<th>挂账笔数</th>
					<th>挂账金额</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td >1</td>
					<td >${bankCode}</td>
					<td >${reconcileRsultSummary.allBankCount}</td>
					<td ><fmt:formatNumber value="${reconcileRsultSummary.allBankAmount }" pattern="#,##0.00"  /></td>
					<td >${reconcileRsultSummary.bankManyCount}</td>
					<td ><fmt:formatNumber value="${reconcileRsultSummary.bankAmout}" pattern="#,##0.00"  /></td>			
				</tr>
				<tr>
					<td >2</td>
					<td >系统系统</td>
					<td >${reconcileRsultSummary.allAccountCount}</td>
					<td ><fmt:formatNumber value="${reconcileRsultSummary.allAccountAmount }" pattern="#,##0.00"  /></td>
					<td >${reconcileRsultSummary.sysManyCount}</td>
					<td ><fmt:formatNumber value="${reconcileRsultSummary.accountAmout}" pattern="#,##0.00"  /></td>			
				</tr>		
			</tbody>
		</table>
		</c:forEach>
		</c:if>
		</div>
	</form>
</body>