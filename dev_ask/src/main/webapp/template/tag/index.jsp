<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/template/common/header.jsp"></c:import>
</head>
<body style="background: #171527;">
	<c:import url="/template/common/nav.jsp"></c:import>
	<section class="container my-5">
		<div class="row">
			<div class="col-2">
				<div
					class="d-flex align-items-center justify-content-between  w-100"
					style="border-bottom: 1px solid #201D31;">
					<h6 class="text-light">${tags.size()}Tags</h6>
					<%-- <button class="btn btn-sm mb-2 text-light"
						style="background-color: #201D31; border: 1px solid #2B293C;"
						style="font-size: 13px" type="button" data-bs-toggle="modal"
						data-bs-target="#staticBackdrop">
						<i class="bi bi-plus-circle-fill"></i> New Tag
					</button>--%>
				</div>
			</div>
			<div class="col-10 border-start">
				<div class="d-flex justify-content-between align-items-center mb-3"
					style="border-bottom: 1px solid #201D31;">
					<h4 class="text-light">Tags</h4>
					<div class="d-flex">
						<form action="tags" class="me-3">
							<div class="input-group input-group-sm mb-3 me-3 text-light">
								<input type="hidden" name="mode" value="SEARCH" /> <input
									type="text" name="key" class="form-control text-light"
									aria-label="Sizing example input"
									aria-describedby="inputGroup-sizing-sm"
									style="background-color: #201D31; border: 2px solid #2B293C;"
									placeholder="Search..." /> <span
									class="input-group-text text-light" id="inputGroup-sizing-sm"
									type="submit"
									style="background-color: #201D31; border: 3px solid #2B293C;"><i
									class="bi bi-search"></i></span>
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

				<!-- Modal -->
				<div class="modal fade" id="staticBackdrop"
					data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
					aria-labelledby="staticBackdropLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<h1 class="modal-title fs-5" id="staticBackdropLabel">New
									tag</h1>
								<button type="button" class="btn-close" data-bs-dismiss="modal"
									aria-label="Close"></button>
							</div>
							<div class="modal-body">
								<form action="">
									<div class="mb-2">
										<label for="" class="form-label">Tag Name</label><br> <input
											type="text" class="form-control" placeholder="tagname">
									</div>
									<div class="mb-2">
										<label for="" class="form-label">Description</label><br>
										<textarea name="" id="" class="form-control" rows="5"
											placeholder="tag descritpion"></textarea>
									</div>
									<div class="text-end">
										<button class="btn btn-danger btn-sm">Cancel</button>
										<button class="btn btn-success btn-sm">Save</button>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

				<div class="container my-2" style="min-height: 250px">
					<div class="row">
						<c:forEach var="tag" items="${tags }">
							<div class="col-3">
								<div class="card p-2 mb-2 text-light"
									style="background-color: #2B293C;">
									<h6>
										<a href="#">${tag.tag_name }</a>
									</h6>
									<p class="p-2" style="font-size: 13px">${tag.description }
									</p>
								</div>
							</div>
						</c:forEach>
					</div>
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
		</div>
	</section>
	<c:import url="/template/common/footer.jsp"></c:import>
</body>
</html>