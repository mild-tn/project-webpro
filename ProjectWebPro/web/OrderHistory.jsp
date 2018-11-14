<%-- 
    Document   : OrderHistory
    Created on : Nov 14, 2018, 4:48:03 PM
    Author     : kao-tu
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
        <h1>History!</h1>
        <form action="History" method="get">
            <table class="tablehistory">
                <tr>
                    <th>Count</th>
                    <th>Date</th>
                    <th>Amount</th>
                    <th>Balance</th>
                </tr>
                
                <tr>
                    <c:forEach items="${history}" var="h" varStatus="hc">
                        <td>${hc.count}</td>
                        <td>${h.createdate}</td>
                        <td>${h.amount}</td>
                        <td>${h.balance}</td>
                    </c:forEach>
                </tr>
            </table>
        </form>
    </body>
</html>
