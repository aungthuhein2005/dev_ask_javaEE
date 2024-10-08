<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<c:import url="/template/common/header.jsp"></c:import>
</head>
<body style="background-color: #171527;">
 <div class="container text-light vh-100 d-flex align-items-center justify-content-center" >
        <div class="row w-100">
            <div class="col-4 mx-auto">
            <c:if test="${not success and not empty success }">
             <div class="alert alert-danger text-center" role="alert">
                Email or password is incorrect
                  </div>
             </c:if>
                <h4 class="text-center" style="color: #FFC107;">Login</h4>
                <form action="login" method="post" class="mt-4">
                  <input type="hidden"  name="mode" value="LOGIN">
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control text-light" id="email" name="email" placeholder="example@gmail.com" required  style="background-color: #201D31;border: 2px solid #2B293C;">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control text-light" id="password" name="password" required placeholder="password"   style="background-color: #201D31;border: 2px solid #2B293C;">
                    </div>
                    <div class="text-end mb-2">
                        <a href="" style="font-size: 13px;">Forget password?</a>
                        <a href="user" style="font-size: 13px;">Register</a>
                    </div>
                    <button class="btn btn-danger btn-sm w-100">Login</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>