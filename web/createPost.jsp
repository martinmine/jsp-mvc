<%--
  Created by IntelliJ IDEA.
  User: marti_000
  Date: 09.08.2015
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create a new post</title>
</head>
<body>
<form method="post" action="create">
  Title:<br>
  <input type="text" name="title"/>
  <br>
  Author:<br>
  <input type="text" name="author"/>
  <br>
  Header:<br>
  <textarea name="header" cols="40" rows="5"></textarea>
  <br>
  Content:<br>
  <textarea name="content" cols="40" rows="5"></textarea>
  <br><br>
  <input type="submit" value="Submit"/>
</form>
</body>
</html>
