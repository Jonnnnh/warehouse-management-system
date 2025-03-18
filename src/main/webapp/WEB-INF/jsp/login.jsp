<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<html>
<head><title>Авторизация</title></head>
<body class="container">
<h2 class="mb-4">Вход</h2>
<form method="post" action="${pageContext.request.contextPath}/login" class="mb-3">
    <div class="mb-3">
        <label for="username" class="form-label">Логин:</label>
        <input type="text" class="form-control" name="username" id="username" required>
    </div>
    <div class="mb-3">
        <label for="password" class="form-label">Пароль:</label>
        <input type="password" class="form-control" name="password" id="password" required>
    </div>
    <button type="submit" class="btn btn-primary">Войти</button>
</form>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>

<p class="mt-3">
    Нет аккаунта? <a href="${pageContext.request.contextPath}/register">Зарегистрироваться</a>
</p>
</body>
</html>
