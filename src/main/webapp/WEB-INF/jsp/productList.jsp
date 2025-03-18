<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<html>
<head><title>Список товаров</title></head>
<body class="container">
<h2 class="mb-4">Список товаров</h2>
<table class="table table-bordered">
    <thead class="table-light">
    <tr>
        <th>ID</th>
        <th>Название</th>
        <th>Описание</th>
        <th>Цена</th>
        <th>Количество</th>
        <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${products}">
        <tr>
            <td>${p.id}</td>
            <td>${p.name}</td>
            <td>${p.description}</td>
            <td>${p.price}</td>
            <td>${p.quantity}</td>
            <td>
                <a class="btn btn-sm btn-warning mb-1" href="${pageContext.request.contextPath}/product-edit?id=${p.id}">Редактировать</a>
                <form class="d-inline" action="${pageContext.request.contextPath}/operation" method="post">
                    <input type="hidden" name="action" value="receive" />
                    <input type="hidden" name="productId" value="${p.id}" />
                    <input type="number" name="amount" min="1" value="1" class="form-control d-inline w-25" />
                    <input type="text" name="comment" placeholder="Комментарий" class="form-control d-inline w-50" />
                    <button type="submit" class="btn btn-sm btn-success">Приход</button>
                </form>
                <form class="d-inline mt-1" action="${pageContext.request.contextPath}/operation" method="post">
                    <input type="hidden" name="action" value="ship" />
                    <input type="hidden" name="productId" value="${p.id}" />
                    <input type="number" name="amount" min="1" value="1" class="form-control d-inline w-25" />
                    <input type="text" name="comment" placeholder="Комментарий" class="form-control d-inline w-50" />
                    <button type="submit" class="btn btn-sm btn-danger">Отгрузка</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
