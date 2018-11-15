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
        <jsp:include page="include/NavBar.jsp"/>
        <form action="ActivateKey" method="get">
            <div id="overlay"></div>
            <div class="registerbox" style="text-align: center;color: whitesmoke">
                <h1>Activate</h1>
                <h4 style="color: whitesmoke">Your Email </h4> 
                <h6 style="color: whitesmoke">${emailRe}</h6>
                <input type="text" name="email" placeholder="Email">
                <input type="text" name="activatekey" placeholder="Activate Key">
                <input type="submit" value="SUBMIT">
                </form>
                <p style="color: red;text-align: center">${messageActivate}</p>
            </div>
    </body>
</html>
