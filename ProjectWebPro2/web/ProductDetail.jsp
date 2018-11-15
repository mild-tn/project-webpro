<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
    </head>
    <body style="background-color: #fffff0">
        <jsp:include page="include/NavBarBackColor.jsp"/>
        <!--<h1>Product Detail</h1>-->
        <div style="margin-top: 40px;" class="container">
            <div class="row">
                <div class="col-8">
                    <c:set value="${productDetail}" var="p"></c:set>
                        <div class="container">
                            <div class="row">
                                <div class="col">
                                    <img src="include/img/product/${p.productcode}.jpg" width="300px;"/>
                            </div>
                            <div class="col">
                                <h3>ชื่อสินค้า : ${p.productname}</h3>
                                ชนิดของสาย : ${p.producttype}<br>
                                ราบละเอียดสินค้า : ${p.productdescription}<br>
                                ประกัน : ${p.sex}<br>
                                เพศ : ${p.sex == "F" ? "หญิง":"ชาย"}<br>
                                Color : <input style="height: 20px;width: 20px;background-color: ${p.productcolor};border: 1px solid black;border-radius: 10px;" disabled/><br>
                                <form action="AddToCart" method="">
                                    <input type="hidden" value="${p.productcode}" name="addProductCode"/>
                                    <input type="submit" value="Add to cart"/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4">
                    <div class="card">
                        <div class="card-header">
                            Your Cart :<a href=""><i class="fas fa-cart-plus" style="color: #000">(${shoppingCart.totalQuantity})</i></a>
                        </div>
                        <div class="card-body">
                            <c:forEach items="${shoppingCart.lineItems}" var="s">
                                <img src="include/img/product/${p.productcode}.jpg" width="50px;"/>
                                ${s.product.productname}
                                <form action="RemoveToCart">
                                    <input type="hidden" value="${s.product.productcode}" name="removeProductCode"/>
                                    <button type="submit" class="btn btn-primary" >remove</button>
                                </form>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </body>
</html>
