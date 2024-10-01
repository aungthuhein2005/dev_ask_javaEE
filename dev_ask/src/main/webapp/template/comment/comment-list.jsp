<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="com.devask.utilis.DateTimeUtils"%>
<%@ page import="java.time.LocalDateTime"%>
<c:forEach var="comment" items="${comments}">
	<div class="comment border-bottom border-top py-2 ps-2"
		style="font-size: 13px;">
		${comment.body }
		<div class="d-flex align-items-center justify-content-between">
			<div>
				<a href="#">${comment.username }</a> <span class="text-secondary"
					style="font-sie: 11px;">${ DateTimeUtils.formatTimeAgo(comment.createdAt) }</span>
			</div>
		</div>
	</div>
</c:forEach>