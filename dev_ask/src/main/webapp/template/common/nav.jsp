<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<nav
	class="navbar navbar-expand-lg bg-body-tertiary sticky-top border-bottom border-dark"
	style="background-color: #171527 !important;">
	<div class="container-fluid text-light"
		style="background-color: #171527;">
		<a class="navbar-brand text-light" href="home"><span
			style="color: #FFC107">Dev</span>Ask</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation"
			style="background-color: #201D31 !important; border: 1px solid #2B293C !important;">
			<i class="bi bi-list text-light"></i>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item "><a class="nav-link text-light active"
					aria-current="page" href="home">Home</a></li>
				<li class="nav-item "><a class="nav-link text-light"
					href="tags">Tags</a></li>
				<li class="nav-item "><a class="nav-link text-light"
					href="users">Users</a></li>
				<li class="nav-item "><a class="nav-link text-light"
					href="questions?mode=FORM">Ask Question</a></li>
				<li class="nav-item "><a class="nav-link text-light"
					href="questions">Questions</a></li>
			</ul>
			<c:choose>
				<c:when test="${not empty sessionScope.user}">  
       <a href="user?mode=PROFILE" class="btn btn-sm text-light me-2" style="background-color: #201D31;border: 1px solid #2B293C;">
       <span class="fw-bold ">${sessionScope.user.username }</span></a>
    </c:when>
				<c:otherwise>
					<a href="login" class="btn btn-sm text-light me-2" style="background-color: #201D31;border: 1px solid #2B293C;">Login</a>
					<a href="register" class="btn btn-sm text-light"
						style="background-color: #2B293C; border: 1px solid #2B293C;">Sign
						Up</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</nav>