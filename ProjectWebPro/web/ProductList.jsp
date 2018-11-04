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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product</title>
    </head>
    <body>
        <jsp:include page="include/NavBar.jsp"/>
        <h1>Product List</h1>
        <div class="container">
            <div class="row">
                <c:forEach items="${products}" var="p">
                    <div class="col-6 col-md-4">
                    <br>
                        <div class="card-group">
                            <div class="card">
                                <img class="card-img-top" src="..." alt="Card image cap">
                                <div class="card-body">
                                    <h5 class="card-title">${p.productname}</h5>
                                    <p>Color : <div style="height: 20px;width: 20px;background-color: ${p.productcolor};border: 1px solid black;border-radius: 10px;"></div></p>
                                </div>
                                <div class="card-footer">
                                    <small class="text-muted">Last updated 3 mins ago</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </body>
</html>
