<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
    </head>
    <body>
        <jsp:include page="include/NavBarBackColor.jsp"/>
        <h1>Product Detail</h1>
        <div class="container">
            <div class="row">
                <div class="col-8">
                    <c:set value="${productDetail}" var="p"></c:set>
                        <div class="container">
                            <img src="include/img/product/${p.productcode}.jpg"/>
                        Product Name : ${p.productname}
                        <form action="AddToCart" method="post">
                            <%--<input type="hidden" value="${p.productCode}" name="productCode"/>--%>
                            <input type="hidden" value="${p.productcode}" name="productcode"/>
                            <input type="submit" value="Add to cart"/>
                        </form>
                    </div>
                </div>
                <div class="col-4">
                    <div class="card">
                        <div class="card-header">
                          Your Cart :   <a href=""><i class="fas fa-cart-plus" >(${cart.totalQuantity})</i></a>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title">Special title treatment</h5>
                            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                            <a href="#" class="btn btn-primary">remove</a>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
