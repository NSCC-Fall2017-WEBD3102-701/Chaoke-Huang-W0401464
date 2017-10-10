<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: inet2005
  Date: 10/9/17
  Time: 8:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Details</title>
</head>
<body>
<form action="" name="userDetailForm" method="post">
    <p>First Name: <input type="text" name="First Name" id="firstName" required /></p>
    <p>Last Name: <input type="text" name="Last Name" id="lastName" required /></p>
    <p>Favourite Colour: <input type="color" name="Favourite Color" id="favouriteColor" required /></p>
    <p>Email address: <input type="email" name="Email" id="Email" required /></p>
    <p>Website: <input type="url" name="website" id="website" required /></p>
    <p>Date of Birth: <input type="datetime-local" name="Datetime" id="dob" placeholder="mm/dd/yyyy, --:-- -.-" required /></p>
    <p><input type="submit" name="submit" value="Submit" id="submit" /></p>
</form>
<%
    Enumeration<String> e = request.getParameterNames();
    String parameterName, parameterValue;
    while(e.hasMoreElements())
    {
        parameterName = e.nextElement();
        parameterValue = request.getParameter(parameterName);
      if(!parameterValue.equals("")&&!parameterName.equals("submit")){%>
        <p><%= parameterName %> was <%= parameterValue %> </p>


<%

      }
    }
%>
</body>
</html>