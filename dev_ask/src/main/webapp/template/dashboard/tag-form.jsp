<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<c:import url="/template/dashboard/side-bar.jsp"></c:import>
<div class="container">
	<div class="row">
		<div class="col-4 mx-auto mt-4">
<a href="javascript:history.back()" class="float-end">Back</a>

			<form action="admin" class="mt-2 ">
			<input type="hidden" name="mode" value="TAGADD" />
				 <div class="form-group mb-3">
    <label for="tag_name">Name</label>
    <input type="text" class="form-control" id="tag_name" name="name" placeholder="Enter tag name">
  </div>
  <div class="mb-3">
  <label for="tag_description" class="form-label">Description</label>
  <textarea class="form-control" id="tag_description" rows="3" name="description"></textarea>
</div>
<div>
	<button type="submit" class="text-white bg-primary border-0 rounded px-1 py-2 float-end">Create</button>
</div>
			</form>
		</div>
	</div>	
</div>