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
           <link rel="stylesheet" href="include/css/resetpass.style.css"/>
    </head>
    <body>
         <style>
        body{
            color: #fff;
        }
        h2{
            text-align: center;
        }
    </style>
          <jsp:include page="include/NavBar.jsp"/>
        <form action="ResetPass" method="post">
     <div id="overlay"></div>
    <div class="resetbox">
        <h2>Forgot Password?</h2><br>
        
            <input type="text" name="emailre" placeholder="Email"><br>
            <br>
            
            <input type="submit">
        </form>
        <!--<a href="Login">Back</a><br>-->
        <!--<span id="text-account">Don't have an account?</span><a id="create-account" href="#"> Create here.</a>-->
        
    </div>
    </body>
</html>
