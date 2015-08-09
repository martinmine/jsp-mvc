<%--
  Created by IntelliJ IDEA.
  User: marti_000
  Date: 08.08.2015
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
<c:forEach items="${posts}" var="post">
  <h1>${post.title}</h1>
  <h2>${post.header}</h2>
  <p><a href="article?id=${post.id}">Read more</a></p>
</c:forEach>

<div>
  <a href="create">Create a new post</a>
</div>
</body>
</html>
