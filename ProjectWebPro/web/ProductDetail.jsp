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
        <h1>Product Detail</h1>
        <c:set value="${productDetail}" var="p"></c:set>
        <div class="container-fulid">
        <img src="include/img/product/${p.productcode}.jpg"/>
        Product Name : ${p.productname}
            
        </div>
    </body>
</html>
