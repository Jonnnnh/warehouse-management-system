<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<html>
<head><title>Редактировать товар</title></head>
<body class="container">
<h2 class="mb-4">Редактирование товара</h2>
<form method="post" action="${pageContext.request.contextPath}/product-edit">
  <input type="hidden" name="id" value="${product.id}">
  <div class="mb-3">
    <label class="form-label">Название:</label>
    <input type="text" class="form-control" name="name" value="${product.name}">
  </div>
  <div class="mb-3">
    <label class="form-label">Описание:</label>
    <textarea class="form-control" name="description">${product.description}</textarea>
  </div>
  <div class="mb-3">
    <label class="form-label">Цена:</label>
    <input type="number" step="0.01" class="form-control" name="price" value="${product.price}">
  </div>
  <div class="mb-3">
    <label class="form-label">Количество:</label>
    <input type="number" class="form-control" name="quantity" value="${product.quantity}">
  </div>
  <button type="submit" class="btn btn-primary">Сохранить</button>
</form>
</body>
</html>
