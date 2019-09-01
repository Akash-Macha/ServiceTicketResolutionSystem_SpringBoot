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
	text-align:center
	}
/* ----START: Table------ */
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 90%;
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

/*
* Basic styles
*/
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
    text-decoration: none;;
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

<h3>Raise Ticket:</h3><br>

<form action="RaiseTicket" method="POST">

<!-- START: getListOfDepartments - make request to rest!  -->
Issue Category:
<select name="IssueCategory"> 
<%
/*
		Get the ArrayList from the sessoin
*/
		List<Department> listOfDepartments = (List<Department>) session.getAttribute("listOfDepartments");
		System.out.println("In JSP----> : " + listOfDepartments);
		for(Department department : listOfDepartments) {
			//ArrayList eachDepartment = (ArrayList)eachObject;
%>
  			<option value="<%= department.getName() %>"><%= department.getName() %></option>
<%
		}
%>
</select><br>
<!-- END: getListOfDepartments - make request to rest!  -->

<div>
Message:
<textarea name="message" placeholder="Type your message here" rows="4" cols="30"></textarea><br>
</div>

<!-- START: getAvailablePriorities - make request to rest!  -->
Priority:
<select name="priority"> 
<%
		List<Priority> listOfPriorities = (List<Priority>) session.getAttribute("listOfPriorities");
		for(Priority priority : listOfPriorities) {
			//ArrayList eachPriority = (ArrayList)eachObject;
%>
			<option value="<%= priority.getValue() %>"><%= priority.getValue() %></option>
<%
		}
%>
</select><br>
<!-- END: getAvailablePriorities - make request to rest!  -->

Start Date: <input type="date" value="<%= LocalDate.now() %>" name="start_date" id="StartDate" readonly ><br>
Requested End Date: <input type="date" value="<%= LocalDate.now() %>" name="requested_end_date" id="EndDate" onchange="validateDate();" ><br>

<input type="submit"><br>

</form>
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