<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<html>
<body>
<h2>Chaoke Huang</h2>
<% SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); %>
<h3>The Date and Time is: <%= df.format(new Date())%> </h3>
</body>
</html>
