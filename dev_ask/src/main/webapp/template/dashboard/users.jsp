<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="https://cdn.datatables.net/2.1.4/css/dataTables.bootstrap5.css
				https://cdn.datatables.net/buttons/3.1.1/css/buttons.bootstrap5.css">
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://cdn.datatables.net/2.1.4/js/dataTables.js"></script>
	<script src="https://cdn.datatables.net/2.1.4/js/dataTables.bootstrap5.js"></script>
<c:import url="/template/dashboard/side-bar.jsp"></c:import>
<div class="height-100 pt-4">
<c:choose>
	<c:when test="${status == 'DELETE_SUCCESS' }">
	<div class="alert alert-success alert-dismissible fade show mx-auto w-50 d-flex items-center justify-content-between" role="alert">
  User Delete Success
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
	
	</c:when>
	<c:when test="${status == 'DELETE_FAIL' }">
	<div class="alert alert-danger alert-dismissible fade show mx-auto w-50 d-flex items-center justify-content-between" role="alert">
  User Delete Fail
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
	
	</c:when>
</c:choose>
        <table id="user" class="table table-striped" style="width:100%">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Reputation</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users }">
            <tr>
                    <td>${user.user_id }</td>
                    <td>${user.username }</td>
                    <td>${user.email }</td>
                    <td>${user.reputation }</td>
                    <td><a href="admin?mode=USERDELETE&userId=${user.user_id }" class="text-white bg-danger px-2 py-1 rounded"><i class='bx bx-trash' ></i></a></td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th></th>
                </tr>
            </tfoot>
        </table>
    </div>
     <script
      src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
      integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
      crossorigin="anonymous"
    ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/boxicons@2.1.4/dist/boxicons.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script type="text/javascript">
		new DataTable('#user');
	</script>
   <c:import url="/template/dashboard/footer.jsp"></c:import>
   
    