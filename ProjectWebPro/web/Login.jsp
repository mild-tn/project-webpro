<%-- 
    Document   : Login
    Created on : Nov 2, 2018, 11:15:29 AM
    Author     : maysmiler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <form action="/Login" method="post">
            Email  <br>
            <input type="text" name="user" placeholder="Please enter your Email" size="32" required />
            <br><br>
            Password   <br>
            <input type="password" name="pass" placeholder="Password" size="32" required/>
            <br><br>
            <input type="submit" value="Login"/>
            <a href="ResetPass.jsp">Forgot Password?</a>
                
        </form>

    </body>
</html>
