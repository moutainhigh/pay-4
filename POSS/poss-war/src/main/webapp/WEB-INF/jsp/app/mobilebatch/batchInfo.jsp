<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<body>

		
	<form action="" method="post" id="form" name="form">
		<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" style="margin-top: 20px;">
			<tr>
				<td>文件名：</td>
				<td><input type="text" style="width: 150px;" /></td>
				<td>提交日期：</td>
				<td >
				        <li:dateTime id="beginDate" width="120" onfocusId="endDate"/>
                         -<li:dateTime width="120" id="endDate" />
                   </td>
				 <td>
				<a href="javascript:void(0)" class="dialog_link ui-state-default ui-corner-all" >
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
			     </a>
				</td>
				 <td>
				<a href="javascript:void(0)" class="dialog_link ui-state-default ui-corner-all" >
						<span class="ui-icon ui-icon-search"></span>&nbsp;上次批量文件&nbsp;
						
			     </a>
				</td>
			</tr>
		</table>
    </form>
    <div style="height: 10px; text-align: left; padding-left: 5px;margin-left: 5px;" >
       <a href="#">范例文件下载</a>
    </div>
	<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" style="position: fixed;padding: 5px;margin: 5px;" >
      <thead>
       <tr>
           <th>文件名</th>
           <th>提交实际时间</th>
           <th>审核时间</th>
           <th>操作</th>
         </tr>
     </thead>
     <tbody>
          <tr>
              <td>20101003_100_10.xlsx</td>
              <td>2011-11-11 11:11:11</td>
              <td>2011-11-11 11:11:11</td>
              <td>
                 <a href="#">下载</a>&nbsp;
                 <a href="${ctx}/batchFile_mobile_check.do?method=testView">审核</a>&nbsp;
                 <a href="${ctx}/batchFile_mobile_operate.do?method=checkBatchFile&batchId=3571" target="">删除</a>&nbsp;
              </td> 
          </tr>
     </tbody>
    </table>
	
</body>
</html>
