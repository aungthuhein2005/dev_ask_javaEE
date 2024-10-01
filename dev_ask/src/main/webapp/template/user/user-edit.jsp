<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page import="java.time.LocalDateTime"%>


<!DOCTYPE html>
<html>
<head>

<c:import url="/template/common/header.jsp"></c:import> 
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css"
    />
</head>
<style>
    /* Targeting the Select2 container */

.select2-container--bootstrap-5 .select2-selection {
    background-color: #201D31 !important;
    border: 2px solid #2B293C !important;
    color: #fff !important; /* For text color */
}

/* Dropdown options background */
.select2-container--bootstrap-5 .select2-results__option {
    background-color: #201D31 !important;
    color: #fff !important; /* For text color */
}

/* Dropdown options hover state */
.select2-container--bootstrap-5 .select2-results__option--highlighted {
    background-color: #2B293C !important;
    color: #fff !important; 
}

.select2-container--bootstrap-5 .select2-selection__choice {
    color: #fff !important; /* Text color for selected items */
    font-size: 12px !important;
}

</style>
<body style="background: #171527;">
	<!-- Import common navigation content -->
	<c:import url="/template/common/nav.jsp"></c:import>
	    <div class="container my-5 text-light">
      <div class="row">
        <form action="user" method="post" class="col-12 col-md-9 col-lg-6 mx-auto">
         <c:choose>
    <c:when test="${not success and not empty success}">
        <div class="alert alert-danger text-center" role="alert">
            User edit failed!
        </div>
    </c:when>
 <c:when test="${ success and not empty success}">
        <div class="alert alert-success text-center" role="alert">
            User edit success
        </div>
    </c:when>
</c:choose>

         <input type="hidden" name="mode" value="EDIT_USER" />
           <input type="hidden" name="userId" value="${ user.user_id}" />
          <h4>About You</h4>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>Profile pricture</h6>
              <button
                class="btn text-primary p-0 m-0"
                type="button"
                id="profile-img-edit"
              >
                Edit
              </button>
              <input
                type="file"
                class="d-none"
                name="profileImg"
                id="profile-img-choose"
              />
            </div>
            <div class="text-center">
              <img
                src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcg4Y51XjQ-zSf87X4nUPTQzsF83eFdZswTg&s"
                width="100px"
                height="100px"
                class="rounded-circle"
                style="object-fit: cover"
                id="profile-img"
                alt=""
              />
            </div>
          </div>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>Name</h6>
            </div>
            <input
              type="text"
              name="username"
              class="form-control text-light"
              id=""
              value="${ user.username}"
              style="background-color: #201d31; border: 2px solid #2b293c"
            />
          </div>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>About</h6>
            </div>
            <textarea
              name="about"
              class="form-control text-light"
              id=""
              style="background-color: #201d31; border: 2px solid #2b293c"
            > ${ user.about}</textarea>
          </div>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>Date of birth</h6>
            </div>
            <input
              type="date"
              name="dob"
              class="form-control text-light"
              id=""
               value="${ user.dob}"
              style="background-color: #201d31; border: 2px solid #2b293c"
            />
          </div>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>Private Profile</h6>
            </div>
            <input type="checkbox" name="private_profile" 
    <c:if test="${user.privateProfile}">
        checked
    </c:if> 
    class="form-check-input" />

          </div>
          <div class="border border-start-0 border-end-0 py-4">
            <div class="d-flex items-center justify-content-between mb-2">
              <h6>Tags</h6>
            </div>
            <select
              name="tags"
              class="form-select text-light"
              id="multiple-select-clear-field"
              style="
                background-color: #201d31 !important;
                border: 2px solid #2b293c;
              "
              data-placeholder="Choose anything"
              multiple
            >
               <c:forEach var="tag" items="${tags }">
                            
                            <option value="${tag.tagId }"><c:if test="${fn:contains(interestTags, tag.tagId)}">selected</c:if>
      ${tag.tag_name}</option>
                            </c:forEach>
            </select>
          </div>
          <button class="btn btn-success w-100 my-2" type="submit">Save</button>
        </form>
      </div>
    </div>
	
	<c:import url="/template/common/footer.jsp"></c:import>
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <script>
      $(document).ready(function () {
        $("#multiple-select-clear-field").select2({
          theme: "bootstrap-5",
          placeholder: "Choose anything",
        });
        $("#profile-img-edit").click(() => {
          $("#profile-img-choose").click();
        });
        $("#profile-img-choose").change(function() {
    const file = this.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            $("#profile-img").attr("src", e.target.result).show();
        }

        reader.readAsDataURL(file);
    }
});
      });
    </script>
</body>
</html>