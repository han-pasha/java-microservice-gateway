<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head></head>
<body>
<h2>Please Login</h2>

<c:if test="${param.error ne null}">
    <p>Invalid username and password</p>
</c:if>

<c:if test="${param.logout ne null}">
    <p>You have been logged out</p>
</c:if>

<form method="POST" action="/login">
    <input name="username" type="text"  placeholder="Username"/>
    <input name="password" type="password" placeholder="Password"/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

    <button type="submit">Log In</button>
</form>
</body>
</html>