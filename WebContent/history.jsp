<%@page import="java.util.Iterator"%>
<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="com.mongodb.MongoClientOptions"%>
<%@page import="com.mongodb.ServerAddress"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.MongoClient"%>
<%@page import="com.mongodb.MongoCredential"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Al verstuurde berichten!</title>
</head>
<body>
<p>Alle verstuurde berichten</p>
<table border="1">
<thead>
	<tr>
	<td>Aan:</td>
	<td>Van:</td>
	<td>Onderwerp:</td>
	<td>Text:</td>
	<td>HTML:</td>
	</tr>
</thead>
<tbody>
<% 

String userName = "spammer";
String password = "hamspam";
String database = "friendspammer";

MongoCredential credential = MongoCredential.createCredential(userName, database, password.toCharArray());

MongoClient mongoClient = new MongoClient(new ServerAddress("ds227939.mlab.com", 27939), credential, MongoClientOptions.builder().build());
	
	MongoDatabase db = mongoClient.getDatabase( database );
	
	MongoCollection<Document> c = db.getCollection("email");
	
	Iterator<Document> it = c.find().iterator();
	
	while(it.hasNext())
	{
		Document email = it.next();
		
		%>
			<tr>
				<td><%=email.get("to") %></td>
				<td><%=email.get("from") %></td>
				<td><%=email.get("subject") %></td>
				<td><%=email.get("text") %></td>
				<td><%=email.get("asHtml") %></td>
				
			</tr>
		
		
		
		
		<%
		
		
	}


	mongoClient.close();




%>

</tbody>
</table>


</body>
</html>