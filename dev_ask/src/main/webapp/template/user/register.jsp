<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
	<c:import url="/template/common/header.jsp"></c:import>
</head>
<style>
 .form-control::placeholder{
        color: #808080;
        
    }
</style>
<body style="background-color: #171527;">
 <div class="container text-light vh-100 d-flex align-items-center justify-content-center">
        <div class="row w-100">
            <div class="col-4 mx-auto">
             <c:if test="${not success and not empty success }">
             <div class="alert alert-danger text-center" role="alert">
                   Account creation fail
                  </div>
             </c:if>
                <h4 class="text-center" style="color: #ffc107;">Register</h4>
                <form action="user" method="post" class="mt-4" oninput='confirmpassword.setCustomValidity(confirmpassword.value != password.value ? "Passwords do not match." : "")'>
                 <input type="hidden"  name="mode" value="REGISTER">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control text-light" id="username" name="username" style="background-color: #201D31;border: 2px solid #2B293C;" placeholder="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control text-light" id="email" name="email" style="background-color: #201D31;border: 2px solid #2B293C;" placeholder="example@gmail.com" required>
                    </div>
                    <div class="mb-3">
                        <label for="dob" class="form-label">Date of birth</label>
                        <input type="date" class="form-control text-light" id="dob" name="dob" style="background-color: #201D31;border: 2px solid #2B293C;" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control text-light" minlength="8" id="password" name="password" style="background-color: #201D31;border: 2px solid #2B293C;" placeholder="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirmpassword" class="form-label">Confirm password</label>
                        <input type="password" class="form-control text-light" name="confirmPassword" minlength="8" id="confirmpassword" style="background-color: #201D31;border: 2px solid #2B293C;" placeholder="re-enter password" required>
                    </div>
                    <div class="text-end mb-2">
                        <a href="login" style="font-size: 13px;">Login</a>
                    </div>
                    <button class="btn btn-success btn-sm w-100">Register</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>