<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="com.devask.utilis.GenerateColor" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url="/template/common/header.jsp"></c:import>
</head>
<body style="background: #171527;">
    <!-- Import common navigation content -->
    <c:import url="/template/common/nav.jsp"></c:import>
    <section class="container my-5 text-light" style="min-height:350px">
        <div class="row">
            <div class="col-lg-3 col-md-4 col-sm-12 mb-4 mb-md-0">
                <h5 class="pb-3" style="border-bottom: 1px solid #201D31;">Top Contributors</h5>
                <c:forEach var="user" items="${topReputation}">
                    <div class="mb-2 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid #2B293C;">
                        <img
                          src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dXNlcnxlbnwwfHwwfHx8MA%3D%3D"
                          class="rounded-circle m-auto"
                          alt="User"
                          width="80"
                          height="80"
                        />
                        <a class="my-3 h5" href="users?mode=PROFILE&id=${user.user_id }" style="color: #FFC107;">${user.username}</a>
                        <p>Reputation: ${user.reputation }</p>
                    </div>
                </c:forEach>
            </div>
            <div class="col-lg-9 col-md-8 col-sm-12 border-start">
                <div class="d-flex justify-content-between align-items-center mb-3" style="border-bottom: 1px solid #201D31;">
                    <h4>Users</h4>
                    <form action="users" class="me-3">
                        <div class="input-group input-group-sm mb-3 me-3 text-light">
                            <input type="hidden" name="mode" value="SEARCH" />
                            <input type="text" name="key" class="form-control text-light"
                                aria-label="Sizing example input"
                                aria-describedby="inputGroup-sizing-sm"
                                style="background-color: #201D31; border: 2px solid #2B293C;"
                                placeholder="Search..." />
                            <span class="input-group-text text-light" id="inputGroup-sizing-sm"
                                style="background-color: #201D31; border: 3px solid #2B293C;">
                                <i class="bi bi-search"></i>
                            </span>
                        </div>
                    </form>
                </div>

                <div class="container my-4">
                    <div class="row gy-4">  
                        <c:forEach var="user" items="${users }">
                            <div class="col-lg-4 col-md-6 col-sm-12">
                                <div class="card border-0 border-start p-2 text-light border-3 rounded-0 border-secondary" style="background-color: #2B293C;">
                                    <div class="row">
                                      <div class="col-3">
                                      
   <div style="width:50px;height:50px;border:1px solid #ccc;border-radius:50%;display:flex;align-items:center;justify-content:center;">
    <span style="color: #fff; font-size: 20px;">${fn:toUpperCase(fn:substring(user.username, 0, 1))}</span>
</div>

</div>


                                        <div class="col-9">
                                            <a href="users?mode=PROFILE&id=${user.user_id }"><h6>${user.username }</h6></a> 
                                            <span class="text-secondary" style="font-size: 13px;">Reputation - ${user.reputation}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <nav aria-label="Page navigation">
                    <c:if test="${totalPages > 1}">
                        <ul class="pagination pagination-sm">
                            <li class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
                                <a class="page-link" href="?page=${currentPage - 1}"
                                style="background-color: #2B293C; border: 1px solid #201D31;">Previous</a>
                            </li>
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
                                    <a class="page-link" href="?page=${i}"
                                    style="background-color: #171527; border: 1px solid #2B293C">${i}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
                                <a class="page-link" href="?page=${currentPage + 1}"
                                style="background-color: #2B293C; border: 1px solid #201D31;">Next</a>
                            </li>
                        </ul>
                    </c:if>
                </nav>
            </div>
        </div>
    </section>
    <c:import url="/template/common/footer.jsp"></c:import>
</body>
</html>
