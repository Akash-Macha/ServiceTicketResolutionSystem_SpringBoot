<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>


<%@ page import="com.comakeit.strs.entites.*" %>


<!DOCTYPE html>
<html>
<head>
<!-- START: styling for Table [ tickets ] displaying -->
<style>
*{
	text-align: center
}
/* ----START: Table------ */
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 95%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
/*----END: Table --------*/
/* START- align Div in center : class="center" */
.center {
  margin: auto;
  width: 95%;
  /*border: 3px solid #73AD21;*/
  padding: 10px;
}
/* END- align Div in center : class="center" */
/* Basic styles */
body {
  margin: 0;
  font-family: Cambria, Cochin, Georgia, Times, 'Times New Roman', serif;
  font-size: 1.15em;
  line-height: 1.5;
}

a {
  color: var(--c-interactive);
}

h1 {
  margin-top: 0;
}

code {
  font-family: 'Fira Mono', Consolas, 'Liberation Mono', Menlo, Courier, monospace;
}

.wrapper {
  max-width: 600px;
}

/* to remove underline from achor tags */
a, u {
    text-decoration: none;
}

/*-- START: styling for Button-- */
.button {
  background-color: #4CAF50; /* Green */
  border: none;
  color: white;
  padding: 10px 20px; /* padding: 15px 32px; */
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 2px 1px;
  cursor: pointer;
  -webkit-transition-duration: 0.4s; /* Safari */
  transition-duration: 0.4s;
}

.button2:hover {
  box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
}
/*-- END: styling for Button-- */

/* -- START: Form styling */
* {box-sizing: border-box;}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  margin-top: 6px;
  margin-bottom: 16px;
  resize: vertical;
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
  width: 50%;
  text-align: center;
  display: inline-block;
}

/* -- END: Form styling */

#StartDate{
    margin-bottom: 8px;
}
</style>

<script type="text/javascript">
function validateDate(){
    var startDate = document.getElementById("StartDate").value;
    var endDate = document.getElementById("EndDate").value;

    if ((Date.parse(endDate) <= Date.parse(startDate))) {
        alert("End date should be greater than Start date");
        document.getElementById("EndDate").value = startDate;
    }
    // console.log("called");
}
</script>

<title>Service Ticket Resolution System</title>
</head>
<body>

<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setHeader("Expires", "0"); // Proxies.
%>

<!-- START:  If person tries to Come back after loggin OUT : redirect him/her to login page -->
<%
	if(session.getAttribute("user_name") == null){
		System.out.println("session.getAttribute(user_name) == null");
		response.sendRedirect("index.jsp?warning=UnAuthorizedLogin");
		return;
	}
%>
<!-- END: If person tries to Come back after loggin OUT -->
<!-- <center>  -->
<h1>Welcome <%= session.getAttribute("user_name") %></h1>


<!-- <form action="" method="">
	<input type="submit" name="ShowAllTickets" value="Show All Tickets">
</form>  -->
<a href="ShowAllTickets">Show All Tickets</a> |
<a href="ShowFormRaiseTicket">Raise A Ticket</a><br><br>

<form action="Logout" method="POST">
	<input type="submit" class="button button2" value="Logout">
</form>
<!-- START: Raise Ticket Form -->
<%

if(request.getParameter("operation") != null){
	System.out.println("Inside: if(request.getParameter('operation') != null){");
	System.out.println("Value = " + request.getParameter("operation"));

	String operation = (String) request.getParameter("operation");
	if(operation.equals("RaiseTicket")){
%>

<h3>Raise Ticket:</h3>
<div class="container">
<form action="RaiseTicket" method="POST">

<!-- START: getListOfDepartments - make request to rest!  -->
<label for="IssueCategory">Issue Category</label>
<select id="IssueCategory" name="IssueCategory"> 
<%
/*
		Get the ArrayList from the sessoin
*/
		List<Department> listOfDepartments = (List<Department>) session.getAttribute("listOfDepartments");
		System.out.println("In JSP----> : " + listOfDepartments);
		for(Department department : listOfDepartments) {
%>
  			<option value="<%= department.getName() %>"><%= department.getName() %></option>
<%
		}
%>
</select>
<!-- END: getListOfDepartments - make request to rest!  -->

<label for="message">Message</label>
<textarea id="message" name="message" placeholder="Type your message here!" style="height:120px"></textarea>

<!-- START: getAvailablePriorities - make request to rest!  -->
<label for="priority">Priority</label>
<select id="priority" name="priority"> 
<%
		List<Priority> listOfPriorities = (List<Priority>) session.getAttribute("listOfPriorities");
		for(Priority priority : listOfPriorities) {
%>
			<option value="<%= priority.getValue() %>"><%= priority.getValue() %></option>
<%
		}
%>
</select>
<!-- END: getAvailablePriorities - make request to rest!  -->

<label for="StartDate">Start date:</label>
<input type="date" value="<%= LocalDate.now() %>" id="StartDate" name="start_date" id="StartDate" readonly >
<br>

<label for="RequestedEndDate">Requested End Date:</label>
<input type="date" value="<%= LocalDate.now() %>" name="requested_end_date" id="RequestedEndDate" onchange="validateDate();" >
<br><br>

<input type="submit" value="Submit">

</form>
</div>
<%
	}
%>
<!-- END: Raise Ticket Form -->

<!-- START: Ticket Submitted -->

<%
	if(operation.equals("TicketGenerated")){
%>

<h2>Ticket has been successfully raised</h2><br>
<h2>You can view the status of the Ticket in the <b>All Tickets<br>section.</h2><br><br>


<%
	}
%>
<!-- END: Ticket Submitted -->

<!--  START: Show All Tickets -->

<%
	if(operation.equals("ShowAllTickets")){
		
%>

<!-- Ticket Id	Issue Category	start_date	requested_end_date	service_engineer_id		priority	status -->

<%		System.out.println("\n\nInside: if(operation.equals('ShowAllTickets')){\n\n");
		System.out.println("(ArrayList<Ticket>)session.getAttribute('listOfTickets') " + (ArrayList<Ticket>)session.getAttribute("listOfTickets"));
		ArrayList<Ticket> listOfTickets = (ArrayList<Ticket>)session.getAttribute("listOfTickets");
		
		System.out.println("listOfTickets = " + listOfTickets);
		
		if(listOfTickets == null){// && listOfTickets.size() == 0){

%>
<h2>You have not raised any Ticket yet! :)</h2>
<%
		}
		else{

%>
<div class="center">
<h2>All Tickets</h2>
<table>
	<!-- Table heading -->
  <tr>
    <th>Ticket Id</th>
    <th>Issue Category</th>
    
    <th>Message</th>
    
    <th>Start Date</th>
    <th>Requested End Date</th>
    
    <th>Service Engineer</th>
    <th>Priority</th>
    <th>Status</th>
  </tr>

<!-- Table rows -->
<%
			System.out.println("\nBefore for loop\n\n");
			for(int i=0 ; i < listOfTickets.size() ; ++i){
%>
    <tr>
    <td> <%= listOfTickets.get(i).getId()  %> </td>
    <td> <%= listOfTickets.get(i).getCategory().getName() %> </td>
    
    <td> <%= listOfTickets.get(i).getMessage()  %> </td>
    
    <td> <%= listOfTickets.get(i).getStart_date()  %> </td>
    <td> <%= listOfTickets.get(i).getRequested_end_date()  %> </td>
    <td> <%= listOfTickets.get(i).getAssigned_to().getName()  %> </td>
    <td> <%= listOfTickets.get(i).getPriority().getValue()  %> </td>
    <td> <%= listOfTickets.get(i).getStatus().getValue()  %> </td>
  </tr>

<%
			}
		}
%>

</table>
</div>

<!-- <a href="EndUser.jsp">Home</a>  -->
<%
	}
%>
<!--  END: Show All Tickets -->


<%
} // initial null conditino if brace
%>

<!-- </center>  -->
</body>
</html>