<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta charset="utf-8">
	<title>出错啦</title>
	<link rel="stylesheet" href="error.css"/>
	<script language="JavaScript" src="error.js"></script>
  </head>
  
  <body>
    
	<div id="errorDiv">
		<div id="errorHint">
			<p id="errorInfo">${info}</p>
			<p><span id="leaveTime">10</span>秒后自动返回到登录界面</p>
			<p>不能跳转，请<a href="login.html">点击这儿</a></p>
			
		</div>
		
	</div>
	
  </body>
</html>
