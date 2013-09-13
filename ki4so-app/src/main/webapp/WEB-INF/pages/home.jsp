<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Ki4so单点登录系统集成示例</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
	function logout(){
		//获取参数。
		jQuery.ajax({
			url:"http://localhost:8080/ki4so-web/getAppList.do",
			success:function(data){
				alert(data);
			}
		});
		//注销服务器。
		jQuery.ajax({
			url:"http://localhost:8080/ki4so-web/logout.do",
			success:function(data){
			}
		});
	}
</script>
</head>
<body>
<p>这是ki4so集成单点登录系统的示例一个用，演示了如何集成单点登录系统ki4so.</p>
<p style="color: red;">
	单点登录成功，当前登录的用户是：${user.userId}.登录的应用ID是：${user.appId}
</p>

<p>
<a href="javascript:logout();">统一登录</a>
</p>
</body>
</html>