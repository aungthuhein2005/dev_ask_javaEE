<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <c:import url="/template/common/header.jsp"></c:import>
    
       <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/select2-bootstrap-5-theme@1.3.0/dist/select2-bootstrap-5-theme.min.css" />
    <link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">
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
    <c:import url="/template/common/nav.jsp"></c:import>
   
    <section class="container my-5 text-light">
        <div class="row">
        <c:if test="${not empty success and not success}">
        <div class="alert alert-danger d-flex align-items-center alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <span>Creaetion failed</span>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
            <div class="card p-4" style="background-color: #201D31;border: 3px solid #2B293C !important;">
                <!-- Form element starts here -->
                <form action="questions" method="post">
                    <input type="hidden" name="mode" value="CREATE" />
                     <div class="mb-4">
                    <label class="form-label text-light">Title</label>
                    <input type="text" name="title" class="form-control text-light" id=""
                        style="background-color: #201D31;border: 2px solid #2B293C;">
                </div>
                  <div class="mb-4">
                    <label for="description" class="form-label text-light">Description</label>
                <textarea class="form-control text-light" id="description" name="description" rows="3"  style="background-color: #201D31;border: 2px solid #2B293C;"></textarea>
                </div>
                <div class="mb-4">
                    <label class="form-label text-light">Tags</label>
                        <select class="form-select text-light" multiple name="tag" id="multiple-select-clear-field" aria-label="Default select example"
                                style="background-color: #201D31;border: 3px solid #2B293C !important;">
                            <c:forEach var="tag" items="${tags }">
                            
                            <option value="${tag.tagId }">${tag.tag_name }</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-4">
<label class="text-light form-label">Question</label>
                        <!-- Quill editor -->
                        <div id="editor" class="text-light" style="min-height:140px;"></div>
                        <!-- Hidden input to store the Quill editor content -->
                        <input type="hidden" name="questionContent" id="questionContent">
                    </div>
                    <div>
                        <button type="submit" class="btn btn-sm float-end px-4 mt-4" style="background-color: #FFC107;">Ask</button>
                    </div>
                </form>
                <!-- Form element ends here -->
            </div>
        </div>
    </section>
    <c:import url="/template/common/footer.jsp"></c:import>
    
       <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>
    <!-- Quill Editor JavaScript -->
    <script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
	<script>
	$(document).ready(function() {
        $('#multiple-select-clear-field').select2({
            theme: 'bootstrap-5',
            placeholder: 'Choose anything',
        });
    });

	var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'header': [1, 2, false] }],
                ['bold', 'italic', 'underline'],
                ['link'],
                [{ 'list': 'ordered'}, { 'list': 'bullet' }],
                ['blockquote', 'code-block'], // Add code block to toolbar
                [{ 'align': [] }],
                ['image']
            ]
        }
    });
    
    document.querySelector('form').onsubmit = function() {
        // Populate the hidden input with the editor content
        document.querySelector('#questionContent').value = quill.root.innerHTML;
    };
	</script>
</body>
</html>
