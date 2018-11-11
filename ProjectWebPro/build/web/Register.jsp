<%-- 
    Document   : Register
    Created on : Nov 10, 2018, 3:57:43 PM
    Author     : kao-tu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
         <link rel="stylesheet" href="include/css/Register.style.css"/>
    </head>
    <body>
        <form action="Register" method="get">
     <div id="overlay"></div>
    <div class="registerbox">
        <h1>Register</h1><br>
        
            <input type="text" name="email" placeholder="Email"><br>
            <input type="password" name="pass" placeholder="Password">
            <input type="password" name="confirmpass" placeholder="Password Confirmation">
            <input type="submit" value="SUBMIT">
        </form>
      
         <p class="message">Already registered? <a href="Login.jsp">Sign In</a></p>
        
    </div>
    </body>
</html>
