<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="post" class="net.martinmine.jsp.model.BlogPost" scope="request"/>
<html>
<head>
    <title>Article: ${post.title}</title>
</head>
<body>
<h1>${post.title}</h1>
<div>${post.author}</div>
<h2>${post.header}</h2>
<div>${post.datePublished}</div>
<p>${post.content}</p>
</body>
</html>
