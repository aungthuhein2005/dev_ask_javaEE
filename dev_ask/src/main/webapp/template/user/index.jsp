
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>



<!DOCTYPE html>
<html>
<head>
	<c:import url="/template/common/header.jsp"></c:import>
</head>
  <body style="background: #171527;">
	<!-- Import common navigation content -->
	<c:import url="/template/common/nav.jsp"></c:import>
    <section class="container my-5 text-light">
        <div class="row">
          <div class="col-lg-3 col-md-4 col-sm-12 mb-4 mb-md-0">
            <h5 class="pb-3" style="border-bottom: 1px solid #201D31;">Top Contributors</h4>
              <div class="mb-2 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
                <img
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8dXNlcnxlbnwwfHwwfHx8MA%3D%3D"
                  class="rounded-circle m-auto"
                  alt="User"
                  width="80"
                  height="80"
                />
                <h5 class="my-3" style="color: #FFC107;">Jane Doe</h5>
                <p>Reputation: 1500</p>
              </div>
              <div class="mb-2 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
                <img
                  src="data:image/jpeg;base64,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"
                  class="rounded-circle m-auto"
                  alt="User"
                  width="80"
                  height="80"
                />
                <h5 class="my-3" style="color: #FFC107;">Max</h5>
                <p>Reputation: 1500</p>
              </div>
              <div class="mb-2 card text-center p-2 text-light" style="background-color: #201D31;border: 1px solid#2B293C;">
                <img
                  src="data:image/jpeg;base64,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"
                  class="rounded-circle m-auto"
                  alt="User"
                  width="80"
                  height="80"
                />
                <h5 class="my-3" style="color: #FFC107;">Jane Doe</h5>
                <p>Michel</p>
              </div>
          </div>
          <div class="col-lg-9 col-md-8 col-sm-12 border-start">
            <div class="d-flex justify-content-between align-items-center mb-3" style="border-bottom: 1px solid #201D31;">
              <h4>Users</h4>
              <div class="d-flex">
                <div class="input-group input-group-sm mb-3 me-3 text-light">
                  <input
                    type="text"
                    class="form-control text-light"
                    aria-label="Sizing example input"
                    aria-describedby="inputGroup-sizing-sm"
                    style="background-color: #201D31;border: 2px solid #2B293C;"
                    placeholder="Search..."
                  />
                  <span class="input-group-text text-light" id="inputGroup-sizing-sm"  style="background-color: #201D31;border: 3px solid #2B293C;"
                    ><i class="bi bi-search"></i
                  ></span>
                </div>
                <div class="nav-item dropdown">
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
                </div>
              </div>
            </div>
      
            <div class="container my-4">
              <div class="row gy-4">  
                <c:forEach var="user" items="${users }">
                 <div class="col-lg-4 col-md-6 col-sm-12">
                  <div class="card border-0 border-start p-2 text-light border-3 rounded-0 border-secondary" style="background-color: #2B293C;">
                    <div class="row">
                      <div class="col-3">
                        <img
                          src="https://img.freepik.com/free-photo/young-bearded-man-with-striped-shirt_273609-5677.jpg?t=st=1723297145~exp=1723300745~hmac=1be37d3f8488f98bd6d5af920c5538fbc40944fb9da2484bc060e1b5ee9867c7&w=996"
                          width="50px"
                          height="50px"
                          style="object-fit: cover;"
                          alt=""
                        />
                      </div>
                      <div class="col-9">
                        <h6>${user.username }</h6>
                        <span class="text-secondary" style="font-size: 13px;">Reputation - ${user.reputation}</span>
                        
                      </div>
                    </div>
                  </div>
                </div>
                </c:forEach>
                <!-- Duplicate and modify the above col-lg-4 block for additional users -->
              </div>
            </div>
      
            <nav aria-label="Page navigation ">
              <ul class="pagination pagination-sm" >
                <li class="page-item">
                  <a class="page-link" href="#"  style="background-color: #2B293C;border: 1px solid #201D31;">Previous</a>
                </li>
                <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">1</a></li>
                <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">2</a></li>
                <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">3</a></li>
                <li class="page-item"><a class="page-link"  style="background-color: #171527;border: 1px solid #2B293C" href="#">Next</a></li>
              </ul>
            </nav>
          </div>
        </div>
      </section>
		<c:import url="/template/common/footer.jsp"></c:import>
</body>
</html>