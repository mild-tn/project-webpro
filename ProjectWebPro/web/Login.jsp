<%-- 
    Document   : Login
    Created on : Nov 10, 2018, 1:59:20 PM
    Author     : maysmiler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
         <link rel="stylesheet" href="include/css/Login.style.css"/>
    </head>
    <body>
        <form action="Login" method="post">
     <div id="overlay"></div>
    <div class="loginbox">
        <h1>Sign in</h1><br>
        
            <input type="text" name="email" placeholder="Username"><br>
            <input type="password" name="pass" placeholder="Password">
            <input type="submit" value="LOGIN">
        </form>
      
        <a href="#">Forgot Password?</a><br>
        <span id="text-account">Don't have an account?</span><a id="create-account" href="#"> Create here.</a>
        
    </div>
    </body>
</html>
