<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<html>
<head><title>Регистрация</title></head>
<body class="container">
<h2 class="mb-4">Регистрация</h2>
<form method="post" action="${pageContext.request.contextPath}/register">
    <div class="mb-3">
        <label class="form-label">Логин:</label>
        <input type="text" class="form-control" name="username" required>
    </div>
    <div class="mb-3">
        <label class="form-label">Пароль:</label>
        <input type="password" class="form-control" name="password" required>
    </div>
    <div class="mb-3">
        <label class="form-label">E-mail:</label>
        <input type="email" class="form-control" name="email">
    </div>
    <div class="mb-3">
        <label class="form-label">ФИО:</label>
        <input type="text" class="form-control" name="fullName">
    </div>
    <button type="submit" class="btn btn-success">Зарегистрироваться</button>
</form>
<c:if test="${not empty error}">
    <div class="alert alert-danger mt-3">${error}</div>
</c:if>

<p class="mt-3">
    Уже есть аккаунт? <a href="${pageContext.request.contextPath}/login">Войти</a>
</p>
</body>
</html>
