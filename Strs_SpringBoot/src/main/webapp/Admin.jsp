<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%@ page import="com.comakeit.strs.entites.*" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Service Ticket Management System</title>
	<style type="text/css">
	*{
	text-align:center
	}

.alignColumns {
  /* Standard syntax */
  column-count: 1;
  column-gap: 40px;
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
	</style>
	<script type="text/javascript">
		function CheckIsEmpty(){
				let name = document.getElementById("name").value;
				let value = document.getElementById("value").value;
				let code = document.getElementById("code").value;
				
				alert(name);
				alert(code);

				if((value !== '' && code !== '') || (name !== '' && code !== ''))
					//alert("inside if")Ṣ
					return true;
				}
				
				alert("Name or Code field cannot be empty!");
				return false;
			}

		function CheckIsEmptyUser(){
			let name = document.getElementById("name").value;
			let user_name = document.getElementById("user_name").value;
			let code = document.getElementById("code").value;
			alert(name);
			alert(code);

			if(name ==='' || user_name === '' || code === ''){
				alert("Name or Code field cannot be empty!");
				return false;
			}
			
			return true;
		}
	</script>
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

	if(request.getParameter("warning") != null){
		String warning = request.getParameter("warning"); 
		if(warning.equals("changeUserName")){
			%>
			<h2>Please change your user name!</h2>
			<%
		}
	}
	
	if(request.getParameter("status") != null){
		String status = request.getParameter("status");
		
		if(status.equals("addedNewDepartment")){
			%>
			<h2>Department Added Successfully!</h2>
			<%
		}else if(status.equals("notAddedNewDepartment")){
			%>
			<h2>Please check your Department Name / Code !</h2>
			<%
		} else if(status.equals("addedNewStatus")){
			%>
			<h2>Status Added Successfully!</h2>
			<%
		}else if(status.equals("notAddedNewStatus")){
			%>
			<h2>Please check your Status Name / Code !</h2>
			<%
		}else if(status.equals("notAddedNewRole")){
			%>
			<h2>Please check your Role Name / Code !</h2>
			<%
		}
	}
%>
<!-- END: If person tries to Come back after loggin OUT --> 
<h1>Welcome <%= session.getAttribute("user_name") %></h1>
<div class="alignColumns">
<p>Users</p>
<a href="admin-Show_All_User">Show All Users</a><br>
<a href="Admin.jsp?operation=Add_user">Add a User</a><br>
<a href="Admin.jsp?operation=Add_Service_Engineer">Add a Service Engineer</a><br>

<a href="admin-ShowDepartmentsAndAddDepartment">View Departments or Add a Department</a><br>

<a href="admin-ShowStatusesAndAddStatuses">View Statuses or Add a Status</a><br>
<a href="admin-ShowRolesAndAddRole">View Role's or Add a Role</a><br>
 <!--
<a href="admin-ShowPrioritiesAndAddPriority">View Tickets Priorities or Add a priority</a><br> -->
</div>

<form action="Logout" method="POST">
	<input type="submit" class="button button2" value="Logout">
</form>


<%

if(request.getParameter("operation") != null){
	String operation = request.getParameter("operation");
	System.out.println("request.getParameter('operation');" + request.getParameter("operation") + "\n");
	if(operation.equals("addedUser")){
		%>
		<h2>Added User!</h2>
		<%
	}
	else if(operation.equals("addedServiceEngineer")){
		%>
		<h2>Added ServiceEngineer!</h2>
		<%
	}
	else if(operation.equals("Show_All_User")){
%>

<h3>All Users:</h3><br>

	<table>
	<!-- Table heading -->
  <tr>
    <th>User Id</th>
    <th>Name</th>
    <th>User Name</th>
    <th>Password</th>
    <th>Role</th>
    <!-- <th>Action</th> -->
  </tr>
  <%
  	ArrayList<User> listOfUsers = (ArrayList<User>) session.getAttribute("listOfUsers");
    	System.out.println("--> all users : " + listOfUsers);
      			for(User user : listOfUsers){
  %>
		   <tr>
		    <td> <%= user.getId()  %> </td>
		    <td> <%= user.getName() %> </td>
		    <td> <%= user.getUser_name() %> </td>
		    <td> <%= user.getPassword() %> </td>
		    <td> <%= user.getRole().getName() %> </td>
		    <!-- <td>
		         <form action="AdminOperations?operation=DeleteUser&UserId=<%= user.getId() %>" method="POST">
		    		<input type="submit" class="close_button" value="Delete User">
				</form> 
		    </td> -->
		  </tr>
			<%
  			}
		}else if(operation.equals("Add_user")){
			%>
	</table>
			<br>
			<br>
			<form action="Add_User" method="POST"  onsubmit="CheckIsEmptyUser();">
			<label>Name: </label> Name: <input type="text" id = "name" name="name" ><br>
			<label>User Name: </label> User Name: <input type="text" id = "user_name" name="user_name" ><br>
			<label>Password: </label> Password: <input type="password" id = "password" name="password"><br>
								
				<input type="submit" value="submit">
			</form>
			
			<%
		}else if(operation.equals("Add_Service_Engineer")){
			%>
			
			<form action="Add_Service_Engineer" method="POST" onsubmit="CheckIsEmptyUser();">
			<label>Name: </label><input type="text" id = "name" name="name" ><br>
			<label>User Name: </label><input type="text" id = "user_name" name="user_name" ><br>
			<label>Password: </label><input type="password" id = "password" name="password"><br>

				<label for="departmentName">Department: </label> 
				<select id="departmentName" name="departmentName"> 
<%
					List<Department> listOfDepartments = (List<Department>) session.getAttribute("listOfDepartments");
					System.out.println("In JSP----> : " + listOfDepartments);
					for(Department department : listOfDepartments) {
%>
  						<option value="<%= department.getName() %>"><%= department.getName() %></option>
<%
					}
%>
</select><br>
				<input type="submit" value="submit">
			</form>
			
			<%
		}else if(operation.equals("ShowDepartmentsAndAddDepartment")){
			%>
			<form action="admin-addNewDepartment" method="POST" onsubmit="CheckIsEmpty();">
			<table>
			  <tr>
			    <th>Id</th>
			    <th>Name</th>
			    <th>Code</th>
			  </tr>
			  
<%
				/* Get the ArrayList from the sessoin */
				List<Department> listOfDepartments = (List<Department>) session.getAttribute("listOfDepartments");
				System.out.println("In JSP----> : " + listOfDepartments);
				for(Department department : listOfDepartments) {
%>
					<tr>
	  					<td><%= department.getId() %></td>
	  					<td><%= department.getName() %></td>
	  					<td><%= department.getCode() %></td>
  					</tr>
<%
				}
%>
			</table>
			
			<label>Add New Department</label><br>
			<label>Department Name: </label><input type="text" id="name" name="newDepartmentName" ><br>
			<label>Department Code: </label><input type="text" id="code" name="newDepartmentCode" ><br>
			
			<input type="submit" name="submitNewDepartment">
			</form>
			<%
		}else if(operation.equals("ShowStatusesAndAddStatuses")){
			%>
			<form action="admin-addNewStatus"  method="POST" onsubmit="CheckIsEmpty();">
				<table>
				  <tr>
				    <th>Id</th>
				    <th>Name</th>
				    <th>Code</th>
				  </tr>		  
	<%
					/* Get the ArrayList from the sessoin */
					List<Status> listOfStatuses = (List<Status>) session.getAttribute("listOfStatuses");
					System.out.println("In JSP----> : " + listOfStatuses);
					for(Status statusValues : listOfStatuses) {
	%>
						<tr>
		  					<td><%= statusValues.getId() %></td>
		  					<td><%= statusValues.getValue() %></td>
		  					<td><%= statusValues.getCode() %></td>
	  					</tr>
	<%
					}
	%>
				</table>
				
				<label>Add New Status</label><br>
				<label>Status Name: </label><input type="text" id="name" name="newStatusValue" ><br>
				<label>Status Code: </label><input type="text" id="code" name="newStatusCode" ><br>
				
				<input type="submit" name="submitNewStatus">
			</form>
			<%
		}else if(operation.equals("ShowRolesAndAddRole")){
			%>
			<form action="admin-addNewRole" method="POST" onsubmit="CheckIsEmpty();">
				<table>
				  <tr>
				    <th>Id</th>
				    <th>Name</th>
				    <th>Code</th>
				  </tr>		  
	<%
					/* Get the ArrayList from the sessoin */
					List<Role> listOfRoles = (List<Role>) session.getAttribute("listOfRoles");
					System.out.println("In JSP----> : " + listOfRoles);
					for(Role role : listOfRoles) {
	%>
						<tr>
		  					<td><%= role.getId() %></td>
		  					<td><%= role.getName() %></td>
		  					<td><%= role.getCode() %></td>
	  					</tr>
	<%
					}
	%>
				</table>
				
				<label>Add New Role</label><br>
				<label>Status Name: </label><input type="text" id="name" name="newRoleName" ><br>
				<label>Status Code: </label><input type="text" id="code"  name="newRoleCode" ><br>
				
				<input type="submit" name="submitNewRole">
			</form>
			<%
		}
	}
%>


</body>
</html>