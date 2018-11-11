<%-- 
    Document   : ActivateAccount
    Created on : Nov 11, 2018, 1:11:53 PM
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
        <form action="ActivateKey" method="get">
     <div id="overlay"></div>
    <div class="registerbox">
        <h1>Activate</h1><br>
        <h3 style="color: whitesmoke">Email : ${email}</h3>
            <input type="text" name="activatekey" placeholder="Activate Key">
            <input type="submit" value="SUBMIT">
        </form>
      
        
    </div>
    </body>
</html>
