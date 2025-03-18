<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<html>
<head><title>Добавить товар</title></head>
<body class="container">
<h2 class="mb-4">Добавить товар</h2>
<form method="post" action="${pageContext.request.contextPath}/product-create">
  <div class="mb-3">
    <label class="form-label">Название:</label>
    <input type="text" class="form-control" name="name">
  </div>
  <div class="mb-3">
    <label class="form-label">Описание:</label>
    <textarea class="form-control" name="description"></textarea>
  </div>
  <div class="mb-3">
    <label class="form-label">Цена:</label>
    <input type="number" step="0.01" class="form-control" name="price" value="0.00">
  </div>
  <div class="mb-3">
    <label class="form-label">Количество:</label>
    <input type="number" class="form-control" name="quantity" value="0">
  </div>
  <button type="submit" class="btn btn-primary">Сохранить</button>
</form>
</body>
</html>
