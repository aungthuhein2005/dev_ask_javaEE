<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core"  prefix="c"%>
<%@ page import="com.devask.utilis.DateTimeUtils" %>
<%@ page import="java.time.LocalDateTime" %>
<!DOCTYPE html>
<html>
<head>
	<c:import url="/template/common/header.jsp"></c:import>
</head>
  <body style="background: #171527;">
	<c:import url="/template/common/nav.jsp" ></c:import>
	      <section class="container my-5 text-light">
      <div class="row">
        <!-- <div class="col-3"></div> -->
        <div class="col-9">
          <div class="d-flex justify-content-between align-items-center mb-3" style="border-bottom: 1px solid #201D31;">
            <h4>All Questions</h4>
            <div class="d-flex">              
              <form action="questions" class="me-3">
              <div class="input-group input-group-sm mb-3 me-3 text-light">
              <input type="hidden" name="mode" value="SEARCH"/>
                <input
                  type="text"
                  name="key"
                  class="form-control text-light"
                  aria-label="Sizing example input"
                  aria-describedby="inputGroup-sizing-sm"
                  style="background-color: #201D31;border: 2px solid #2B293C;"
                  placeholder="Search..."
                />
                <span class="input-group-text text-light" id="inputGroup-sizing-sm" type="submit"  style="background-color: #201D31;border: 3px solid #2B293C;"
                  ><i class="bi bi-search"></i
                ></span>
              </div>
              </form>
              <%--<div class="nav-item dropdown">
                <button
                  type="button"
                  class="btn btn-outline-success btn-sm dropdown-toggle"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  style="background-color: #FFC107;color: #171527;"
                >
                  <i class="bi bi-filter"></i> Filter
                </button>

                <ul class="dropdown-menu text-light" style="background-color: #FFC107;">
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Action</a></li>
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Another action</a></li>
                  <li>
                    <a class="dropdown-item" style="color: #201D31;" href="#">Something else here</a>
                  </li>
                  <li><hr class="dropdown-divider" /></li>
                  <li><a class="dropdown-item" style="color: #201D31;" href="#">Separated link</a></li>
                </ul>
              </div>--%>
            </div>
          </div>

          <div class="mb-5">
         	<c:forEach var="question" items="${questions }">
         		 <div class="border p-2 mb-2 card text-light" style="background-color: #201D31;border: 3px solid #2B293C !important;">
              <div class="h6">
								<c:url var="detailLink" value="questions">
									<c:param name="mode" value="DETAILS"/>
									<c:param name="questionId" value="${question.questionId }"/>
								</c:url>
                <a href="${detailLink }" class="text-decoration-none me-2"
                  >${question.title }</a
                ><span class="text-secondary" style="font-size: 12px; font-weight: 400"
                  > ${ DateTimeUtils.formatTimeAgo(question.createdAt) }</span>
                <p>${question.description }</p>
                
              </div>
              <p style="font-size: 14px">
              </p>
              <div class="row">
                <div class="col-6">
                <c:forEach var="t" items="${question.tags }">
                  <span class="badge bg-primary">${t.tag_name }</span>
                  </c:forEach>
                </div>
                <div class="col-6 text-end" style="font-size: 13px">
                  <span class="badge bg-secondary">${question.answerCount } ${questoin.answerCount > 1 ? "Answers" : "Answer" }</span>
                  <span class="badge bg-secondary">${question.vote } ${question.vote > 1 ? "Votes" : "Vote" }</span>
                </div>
              </div>
            </div>
         	</c:forEach>
          </div>
          <nav aria-label="Page navigation">
					<c:if test="${totalPages > 1 }">
						<ul class="pagination pagination-sm">
						<li
							class="page-item <c:if test='${currentPage == 1}'>disabled</c:if>'">
							<a class="page-link" href="?page=${currentPage - 1}"
							style="background-color: #2B293C; border: 1px solid #201D31;">Previous</a>
						</li>
						<c:forEach var="i" begin="1" end="${totalPages}">
							<li
								class="page-item <c:if test='${currentPage == i}'>active</c:if>'">
								<a class="page-link" href="?page=${i}"
								style="background-color: #171527; border: 1px solid #2B293C">${i}</a>
							</li>
						</c:forEach>
						<li
							class="page-item <c:if test='${currentPage == totalPages}'>disabled</c:if>'">
							<a class="page-link" href="?page=${currentPage + 1}"
							style="background-color: #2B293C; border: 1px solid #201D31;">Next</a>
						</li>
					</ul>
					</c:if>
				</nav>
        </div>
        <div class="col-3">
        <c:forEach var="question" items="${bestQuestions }">
        <div class="card mb-2 text-light"  style="background-color: #201D31;border: 3px solid #2B293C !important;">
            <div class="card-header" style="border-bottom: 1px solid #2B293C !important;">Today Best Question</div>
            <div class="card-body">
              <div class="h6">
              <c:url var="detailLink" value="questions">
									<c:param name="mode" value="DETAILS"/>
									<c:param name="questionId" value="${question.questionId }"/>
								</c:url>
                <a href="${detailLink }" class="text-decoration-none text-secondary"
                  >${question.title}</a
                >
                <span style="font-size: 12px; font-weight: 400"
                  > ${ DateTimeUtils.formatTimeAgo(question.createdAt) }</span
                >
              </div>
              <p style="font-size: 14px">
               	${question.description }
              </p>
              <div class="row">
                <div class="col-6">
                   <c:forEach var="t" items="${question.tags }">
                  <span class="badge bg-primary">${t.tag_name }</span>
                  </c:forEach>
                </div>
                <div class="col-6 text-end" style="font-size: 13px">
                  <span class="badge bg-secondary">${question.answerCount } ${questoin.answerCount > 1 ? "Answers" : "Answer" }</span>
                  <span class="badge bg-secondary">${question.vote } ${question.vote > 1 ? "Votes" : "Vote" }</span>
                </div>
              </div>
            </div>
          </div>
        </c:forEach>
        </div>
      </div>
    </section>
	<c:import url="/template/common/footer.jsp"></c:import>
</body>
</html>