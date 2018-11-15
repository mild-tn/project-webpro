<%-- 
    Document   : ProductList
    Created on : Nov 3, 2018, 8:19:11 PM
    Author     : Mild-TN
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
        #h5{
            color: #333;
            text-decoration: none;
        }
        a{
            color: #333;            
        }
        p{
            color: #333;
            text-decoration: none;
        }
    </style>
    <body>
        <jsp:include page="include/NavBarBackColor.jsp"/>
        <h1>Product List</h1>
        <div class="container">
            <div class="row">
                <c:forEach items="${products}" var="p">
                    <div class="col-6 col-md-4">
                        <br>
                        <a  style="text-decoration: none" href="ProductDetail?productCode=${p.productcode}">
                            <div class="card-group">
                                <div class="card">
                                    <img class="card-img-top" src="include/img/product/${p.productcode}.jpg" width="100px" alt="Card image cap">
                                    <div class="card-body">
                                        <h5 class="card-title" id="h5">${p.productname}</h5>
                                        <p> Color : <input style="height: 20px;width: 20px;background-color: ${p.productcolor};border: 1px solid black;border-radius: 10px;" disabled/></p>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
