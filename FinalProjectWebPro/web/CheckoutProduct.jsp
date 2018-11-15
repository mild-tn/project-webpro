<%-- 
    Document   : CheckoutProduct
    Created on : Nov 15, 2018, 11:30:32 PM
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
        <jsp:include page="include/NavBarBackColor.jsp"/>
        <h1>Check Out</h1>
        <div class="container">
            <div class="row">
                <table class="table">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">Product Code</th>
                            <th scope="col">Last</th>
                            <th scope="col">Handle</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${All.lineItems}" var="s" varStatus="vc">
                        <tr>
                            <th scope="row">${vc.count}</th>
                            <td>${s.product.productcode}</td>
                            <td>${s.product.productname}</td>
                            <td></td>
                        </tr>
                         </c:forEach>
                    </tbody>
                </table>
                ${All.totalquantity}
            </div>
        </div>


        ${shoppingCart.lineItems}
        <c:forEach items="${All.lineItems}" var="s">
            ${s.product.productcode}<br>
            ${s.product.productname}<br>
        </c:forEach>
    </body>
</html>
