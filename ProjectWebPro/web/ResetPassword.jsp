<%-- 
    Document   : ResetPassword
    Created on : Nov 11, 2018, 10:44:08 AM
    Author     : maysmiler
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password Page</title>
    </head>
    <body>
           <link rel="stylesheet" href="include/css/ResetPass.style.css"/>
    </head>
    <body>
        <form action="ResetPassword" method="post">
     <div id="overlay"></div>
    <div class="resetbox">
        <h1>Forgot Password?</h1><br>
        
            <input type="text" name="emailre" placeholder="Email"><br>
            <input type="password" name="passre" placeholder="Current Password"><br>
            <input type="password" name="newpass" placeholder="New Password"><br>
            <input type="password" name="newpasscf" placeholder="Confirm New Password"><br>
        <!--<a href="Login">Back?</a><br>-->
            <input type="submit">
        </form>
      
        <!--<span id="text-account">Don't have an account?</span><a id="create-account" href="#"> Create here.</a>-->
        
    </div>
    </body>
</html>
