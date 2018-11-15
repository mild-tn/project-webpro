<%-- 
    Document   : Payment
    Created on : Nov 12, 2018, 11:47:37 PM
    Author     : kao-tu
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
        <form action="Payment" method="post">
     <div id="overlay"></div>
    <div class="loginbox">
        <h1>Payment!</h1><br>
            <input type="text" name="cardholder" placeholder="Card Holder">
            <input type="text" name="cardno" placeholder="Card Number"><br>
            <p2>EXP (Month Year)</p2>
            <input type="month" name="exp" placeholder="Exp YY">
            <input type="text" name="cvv" placeholder="CVV">
            <input type="submit" value="Pay NOW">
        </form>
      
       
        
    </div>
    </body>
</html>
