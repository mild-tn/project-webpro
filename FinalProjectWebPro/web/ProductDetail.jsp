<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Detail</title>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.2/css/all.css"/>
        <link rel="icon" type="image/png" sizes="64x64" href="images/oie_transparent.png">
    </head>
    <body style="background-color: #fffffe">
        <jsp:include page="include/NavBarBackColor.jsp"/>
        <c:set value="${productDetail}" var="p"></c:set>
            <div style="margin-top: 40px;" class="container">
                <div class="row">
                    <div class="col">
                        <img src="include/img/product/${p.productcode}.jpg" width="300px;"/>
                </div>
                <div class="col">
                    <h3>ชื่อสินค้า : ${p.productname}</h3>
                    ชนิดของสาย : ${p.producttype}<br>
                    รายละเอียดสินค้า : ${p.productdescription}<br>
                    ประกัน : ${p.warrenty == '' ? '-':p.warrenty}<br>
                    เพศ : ${p.sex == "F" ? "หญิง":"ชาย"}<br>
                    ราคา/1 ชิ้น : ${p.buyprice}<br>
                    Color : <input style="height: 20px;width: 20px;background-color: ${p.productcolor};border: 1px solid black;border-radius: 10px;" disabled/><br>
                    <div class="row justify-content-center">
                        <form action="AddToCart" style="margin-top: 40px">
                            <input type="hidden" value="${p.productcode}" name="addProductCode"/>
                            <button type="submit" class="btn btn-outline-secondary">Add To Cart</button>
                        </form>      
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="include/footer.jsp"/>
    </body>
</html>
