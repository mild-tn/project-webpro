<%-- 
    Document   : NavBar
    Created on : Aug 10, 2018, 3:32:13 PM
    Author     : INT303
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title </title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
        <link rel="stylesheet" href="include/css/style-page.css"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css"/>
    </head>
    <style>
        #navbar-ProductList{
            background-color: #333;
        }
        .dropdown-menu{
            width: 30px;
        }
        .dropdown-toggle{
            width: 50px;
            border: 0px;
        }
        
        a button{
            cursor: pointer;
        }
    </style>
    <body>
        <script>
            var prevScrollpos = window.pageYOffset;
            window.onscroll = function () {
                var currentScrollPos = window.pageYOffset;
                if (prevScrollpos < currentScrollPos) {
                    document.getElementById("navbar").style.top = "0";
                    document.getElementById("navbar").style.backgroundColor = "#333";
                    document.getElementById("navbar").style.color = "#000";
                } else {
                    document.getElementById("navbar").style.top = "-30px";
                    document.getElementById("navbar").style.backgroundColor = "transparent";
                }
                prevScrollpos = currentScrollPos;
            }
        </script>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" ></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
        <nav class="navbar navbar-expand-lg sticky-top" id="navbar">
            <a class="navbar-brand" href="HomePage.jsp"><img src="include/img/forPage/TIME4U.png" width="100px"/></a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="HomePage.jsp">Home<span class="sr-only"></span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="ShowProductServlet">Product</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="Contact.jsp">Contact</a>
                    </li>
                </ul>
                <form action="ProductList" method="post" class="form-inline my-2 my-lg-0">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search" name="productName" required>
                    <button class="btn btn-outline-light my-2 my-sm-0" type="submit"><i class="fas fa-search"></i></button>
                </form>
                <ul class="navbar-nav">
                    <li>
                        <a href="Checkout" class="nav-link">                         
                            <button style="background-color: transparent;border: 0px;">
                                <i class="fas fa-cart-plus" style="color: #fff;">(${shoppingCart.totalQuantity})</i>
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <c:choose>
                            <c:when test="${message == 'Login'}">   
                                <div class="dropdown " style="margin-left: 10px;margin-right: 5px ;">
                                    <button style="background-color: transparent;border: 0px;" class="btn btn-secondary "  data-toggle="dropdown" >
                                        <img src="include/img/forPage/1024px-Circle-icons-profile.png" width="30px;">
                                    </button>
                                    <div class="dropdown-menu dropdown-menu-right">
                                        <a class="dropdown-item" href="Profile">Profile</a>
                                        <a class="dropdown-item" href="LogoutServlet">Logout</a>
                                    </div>
                                </div>
                            </c:when>
                            <c:when test="${message == null}">
                                <a class="nav-link" href="Login.jsp">Login</a>
                            </c:when>
                        </c:choose>
                    </li>     
                </ul>
            </div>
        </nav>
    </body>
</html>
