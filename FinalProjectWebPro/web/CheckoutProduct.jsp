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
                            <th scope="col">Product Name</th>
                            <th scope="col">Price</th>
                            <th scope="col">Add To Cart</th>
                            <th scope="col">Remove</th>
                            <th scope="col">Price</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${All.lineItems}" var="s" varStatus="vc">
                            <tr>
                                <th scope="row">${vc.count}</th>
                                <td>${s.product.productcode}</td>
                                <td>${s.product.productname}</td>
                                <td>${All.totalQuantity}</td>
                                <th>
                                    <form action="AddToCart">
                                        <input type="hidden" value="${s.product.productcode}" name="addProductCode"/>
                                        <button type="submit" class="btn btn-success">Add</button>
                                    </form>
                                </th>
                                <th>
                                    <form action="RemoveToCart">
                                        <input type="hidden" value="${s.product.productcode}" name="removeProductCode"/>
                                        <button type="submit" class="btn btn-danger" >Remove</button>
                                    </form>
                                </th>
                                <th>
                                    ${s.product.buyprice}/ 1 Piece
                                </th>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                ${All.totalPrice}
            </div>
        </div>
    </body>
</html>
