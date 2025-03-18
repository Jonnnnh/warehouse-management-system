<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Журнал операций</title>
</head>
<body>

<div class="container">
  <h1 class="mb-4">Журнал операций</h1>

  <form method="get" action="${pageContext.request.contextPath}/operation-history" class="mb-4">
    <div class="row g-3">
      <div class="col-md-4">
        <label for="productId" class="form-label">Фильтр по товару (ID):</label>
        <input type="text" class="form-control" name="productId" id="productId" value="${param.productId}">
      </div>
      <div class="col-md-4">
        <label for="startDate" class="form-label">Дата с:</label>
        <input type="date" class="form-control" name="startDate" id="startDate" value="${param.startDate}">
      </div>
      <div class="col-md-4">
        <label for="endDate" class="form-label">По:</label>
        <input type="date" class="form-control" name="endDate" id="endDate" value="${param.endDate}">
      </div>
    </div>
    <button type="submit" class="btn btn-primary mt-3">Применить</button>
  </form>

  <table class="table table-bordered">
    <thead class="table-light">
    <tr>
      <th>ID операции</th>
      <th>ID товара</th>
      <th>Дата и время</th>
      <th>Пользователь (ID)</th>
      <th>Количество</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${operations}" var="op">
      <tr>
        <td>${op.id}</td>
        <td>${op.productId}</td>
        <td>${op.operationTimestamp}</td>
        <td>${op.userId}</td>
        <td>${op.quantity}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
