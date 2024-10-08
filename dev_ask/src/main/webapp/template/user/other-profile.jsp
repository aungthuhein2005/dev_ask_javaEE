<%@page import="com.devask.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="com.devask.utilis.DateTimeUtils"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>

<c:import url="/template/common/header.jsp"></c:import>
<style>
.dropdown-item:focus, .dropdown-item:hover {
	background-color: #fad054;
}

.nav-tabs {
	border-bottom: 0;
}

.nav-tabs .nav-link {
	background-color: transparent !important;
	border: 0 !important;
	border-bottom: transparent 3px solid !important;
	color: #fff;
}

.nav-tabs .nav-link.active {
	background-color: transparent !important;
	border-bottom: #fff 3px solid !important;
	color: #fff;
}
</style>
</head>
<body style="background: #171527;">
	<!-- Import common navigation content -->
	<c:import url="/template/common/nav.jsp"></c:import>
	<section class="container my-5 text-light" style="min-height:350px">
		<div class="d-flex mb-4"
			style="border-bottom: 1px solid #2B293C !important;">
			 <div style="width:75px;height:75px;border:1px solid #ccc;border-radius:50%;display:flex;align-items:center;justify-content:center;">
    <span style="color: #fff; font-size: 20px;">${fn:toUpperCase(fn:substring(user.username, 0, 1))}</span>
</div>
			<div
				class="ms-4 d-flex align-items-end justify-content-between w-100">
				<div>
					<h4 class="font-monospace">${user.username }</h4>
					<c:if test="${user.dob != null }">
					<p class="mb-2 text-secondary" style="font-size: 13px">
						<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
							fill="currentColor" class="bi bi-cake-fill" viewBox="0 0 16 16">
                <path
								d="m7.399.804.595-.792.598.79A.747.747 0 0 1 8.5 1.806V4H11a2 2 0 0 1 2 2v3h1a2 2 0 0 1 2 2v4a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1v-4a2 2 0 0 1 2-2h1V6a2 2 0 0 1 2-2h2.5V1.813a.747.747 0 0 1-.101-1.01ZM12 6.414a.9.9 0 0 1-.646-.268 1.914 1.914 0 0 0-2.708 0 .914.914 0 0 1-1.292 0 1.914 1.914 0 0 0-2.708 0A.9.9 0 0 1 4 6.414v1c.49 0 .98-.187 1.354-.56a.914.914 0 0 1 1.292 0c.748.747 1.96.747 2.708 0a.914.914 0 0 1 1.292 0c.374.373.864.56 1.354.56zm2.646 5.732a.914.914 0 0 1-1.293 0 1.914 1.914 0 0 0-2.707 0 .914.914 0 0 1-1.292 0 1.914 1.914 0 0 0-2.708 0 .914.914 0 0 1-1.292 0 1.914 1.914 0 0 0-2.708 0 .914.914 0 0 1-1.292 0L1 11.793v1.34c.737.452 1.715.36 2.354-.28a.914.914 0 0 1 1.292 0c.748.748 1.96.748 2.708 0a.914.914 0 0 1 1.292 0c.748.748 1.96.748 2.707 0a.914.914 0 0 1 1.293 0 1.915 1.915 0 0 0 2.354.28v-1.34z" />
              </svg>
						Birthday: 18.6.2005
					</p>
					</c:if>
					<p class="mb-2 text-secondary" style="font-size: 13px">
						<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18"
							fill="currentColor" class="bi bi-bar-chart-line-fill"
							viewBox="0 0 16 16">
                <path
								d="M11 2a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3h1V7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7h1z" />
              </svg>
						Reputation - ${user.reputation}
					</p>
					<h6 class="text-decoration-none">
						<c:if test="${user.socialList['facebook'] != null}">
							<a href="${user.socialList['facebook']}" class="h5 me-1 text-decoration-none" target="_blank">
								<i class="bi bi-facebook"></i>
							</a>
						</c:if>

						<c:if test="${user.socialList['instagram'] != null}">
							<a href="${user.socialList['instagram']}" class="h5 me-1 text-decoration-none"
								target="_blank"> <i class="bi bi-instagram"></i>
							</a>
						</c:if>


						<c:if test="${user.socialList['github'] != null}">
							<a href="${user.socialList['github'] }" class="h5 me-1 text-decoration-none" target="_blank">
								<i class="bi bi-github"></i>
							</a>
						</c:if>

						<c:if test="${user.socialList.linkedIn != null}">
							<a href="${user.socialList.linkedIn}" class="h5 me-1 text-decoration-none" target="_blank">
								<i class="bi bi-linkedin"></i>
							</a>
						</c:if>
						<span class="h5 " style="cursor:pointer;" onclick="copyToClipboard()" class=" h5 me-1"><i class="bi bi-link-45deg"></i></span>
					</h6>
				</div>
			</div>
		</div>
		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<button class="nav-link active" id="nav-home-tab"
					data-bs-toggle="tab" data-bs-target="#nav-home" type="button"
					role="tab" aria-controls="nav-home" aria-selected="true">Profile</button>
				<button class="nav-link" id="nav-profile-tab" data-bs-toggle="tab"
					data-bs-target="#nav-profile" type="button" role="tab"
					aria-controls="nav-profile" aria-selected="false">Posts</button>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<div class="tab-pane fade show active" id="nav-home" role="tabpanel"
				aria-labelledby="nav-home-tab">
				<div>
					<div class="my-4">
						<c:if test="${not empty user.about}">
							<h6>About</h6>
							<p style="font-size: 15px">${user.about }</p>
						</c:if>
					</div>
					<div class="my-4">
						<h6>Tags</h6>
						<div>
							<c:forEach var="tag" items="${interestTags }">
								<a href="#" class="badge bg-secondary m-1">${tag.tag_name }</a>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane fade" id="nav-profile" role="tabpanel"
				aria-labelledby="nav-profile-tab">
				<div class="my-4">
					<c:forEach var="question" items="${postedQuestions }">
						<div class="border p-2 mb-2 card text-light"
							style="background-color: #201D31; border: 3px solid #2B293C !important;">
							<div class="h6">
								<c:url var="detailLink" value="questions">
									<c:param name="mode" value="DETAILS" />
									<c:param name="questionId" value="${question.questionId }" />
								</c:url>
								<a href="${detailLink }"
									class="text-decoration-none text-secondary">${question.title }</a>
								<span style="font-size: 12px; font-weight: 400">${ DateTimeUtils.formatTimeAgo(question.createdAt) }</span>
							</div>
							<p style="font-size: 14px">${question.description }</p>
							<div class="row">
								<div class="col-6">
									<c:forEach var="tag" items="${question.tags }">
										<span class="badge bg-primary">${tag.tag_name }</span>
									</c:forEach>
								</div>
								<div class="col-6 text-end" style="font-size: 13px">
									<span class="badge bg-secondary">${question.answerCount }
										${questoin.answerCount > 1 ? "Answers" : "Answer" }</span> <span
										class="badge bg-secondary">${question.vote }
										${question.vote > 1 ? "Votes" : "Vote" }</span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

	</section>
	<c:import url="/template/common/footer.jsp"></c:import>
	 <script>
        function copyToClipboard() {
        	navigator.clipboard.writeText(window.location.href)
            .then(() => {
                alert("copied to clipboard")
            })
            .catch(err => {
                console.error('Failed to copy: ', err);
            });
           
        }
    </script>
</body>
</html>