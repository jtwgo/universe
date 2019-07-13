<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD//XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>登录</head>
<br/>
<body>
<form action="/loginToken" method="get">
    用户名：<input type="text" name="username"></br>
    密码：<input type="password" name="password"></br>
    <input type="submit" value="登录">
</form>

</body>

</html>