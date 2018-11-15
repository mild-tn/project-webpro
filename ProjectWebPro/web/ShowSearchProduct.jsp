<%-- 
    Document   : ShoeSearchProduct
    Created on : Nov 15, 2018, 1:07:54 AM
    Author     : maysmiler
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Search Page</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" />
        <link rel="stylesheet" href="include/css/style-page.css"/>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css"/>
    </head>
    <body>
        <jsp:include page="include/NavBar.jsp"/>
        <h1>Product List</h1>
        <div class="container">
        <c:forEach items="${products}" var="p"></c:forEach>
            <div class="row">

                    <div class="col-6 col-md-4">
                    <br>
                        <div class="card-group">
                            <div class="card">
                            <img class="card-img-top" src="include/img/product/${products.productcode}.jpg" width="100px" alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title"><a href="ProductDetail?productCode=${products.productcode}">${products.productname}</a></h5>
                                    <p> Color : <input style="height: 20px;width: 20px;background-color: ${products.productcolor};border: 1px solid black;border-radius: 10px;" disabled/></p>
                                </div>
                            </div>
                        </div>
                    </div>

            </div>
        </div>
    </body>
</html>
