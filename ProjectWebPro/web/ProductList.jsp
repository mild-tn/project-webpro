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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <table>
            <tr>
                <th>#</th>
                <th>Product Code</th>
                <th>Product Name</th>
                <th>Product Description</th>
            </tr>
            <c:forEach items="${products}" var="p" varStatus="vc">
                <tr>
                    <th>${vc.count}</th>
                    <th>${p.productcode}</th>
                    <th>${p.productname}</th>
                    <th>#</th>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
