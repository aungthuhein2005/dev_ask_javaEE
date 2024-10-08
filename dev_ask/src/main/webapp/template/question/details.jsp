<%@page import="java.util.stream.Collectors"%>
<%@page import="com.devask.model.Vote"%>
<%@page import="java.util.List"%>
<%@page import="com.devask.model.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page import="com.devask.utilis.DateTimeUtils"%>
<%@ page import="java.time.LocalDateTime"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/template/common/header.jsp"></c:import>
<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css"
	rel="stylesheet">
</head>
<%
User currentUser = (User) session.getAttribute("user");
int currentUserId = (currentUser != null) ? currentUser.getUser_id() : -1;
List<Vote> questionVotes = (List<Vote>) request.getAttribute("questionVotes");
boolean hasUpVote = questionVotes != null && currentUser != null && questionVotes.stream()
.anyMatch(vote -> vote.getUserId() == currentUser.getUser_id() && "upvote".equals(vote.getVoteType()));

boolean hasDownVote = questionVotes != null && currentUser != null && questionVotes.stream()
.anyMatch(vote -> vote.getUserId() == currentUser.getUser_id() && "downvote".equals(vote.getVoteType()));

long upvoteCount = questionVotes.stream().filter(vote -> "upvote".equals(vote.getVoteType())).count();
long downvoteCount = questionVotes.stream().filter(vote -> "downvote".equals(vote.getVoteType())).count();
%>
<body style="background: #171527;">
	<c:import url="/template/common/nav.jsp"></c:import>
	<div class="container my-5 text-light">
		<div class="row">

			<div class="col-lg-9 col-md-8 col-sm-12 border-end">
				<div class="mb-4">
					<h5>${question.title }</h5>
					<p>${question.description }</p>
					<p>${question.body }</p>
					<div>
						<c:forEach var="tag" items="${question.tags }">
							<a href="#" class="badge bg-secondary m-1">${tag.tag_name }</a>
						</c:forEach>
					</div>
					<div style="font-size: 14px; text-align: end"
						class="border-bottom pb-4 mb-4">
						<span class="me-2">${question.answers.size() }
							${question.answers.size() > 1 ? "Answers" : "Answer" } <i
							class="bi bi-chat-fill"></i>
						</span> <span id="upvote" class="me-2" style="cursor: pointer;"> <%=upvoteCount%>
							<i
							class="bi bi-hand-thumbs-up-fill  <%=hasUpVote ? "text-warning" : ""%>"></i>
						</span> <span id="downvote" style="cursor: pointer;"> <%=downvoteCount%>
							<i
							class="bi bi-hand-thumbs-down-fill <%=hasDownVote ? "text-warning" : ""%>"></i>
						</span>
					</div>
					<div>
						<h6>Your Answer</h6>
						<!-- Create the editor container -->
						<form action="questions" method="post">
							<div id="editor"></div>
							<input type="hidden" name="mode" value="ANSWER"> <input
								type="hidden" name="questionId" value="${question.questionId}">
							<input type="hidden" name="answerContent" id="answerContent">
							<div class="text-end">
								<button class="btn btn-success text-end mt-2">
									Post Answer
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
										fill="currentColor" class="bi bi-send-fill"
										viewBox="0 0 16 16">
                <path
											d="M15.964.686a.5.5 0 0 0-.65-.65L.767 5.855H.766l-.452.18a.5.5 0 0 0-.082.887l.41.26.001.002 4.995 3.178 3.178 4.995.002.002.26.41a.5.5 0 0 0 .886-.083zm-1.833 1.89L6.637 10.07l-.215-.338a.5.5 0 0 0-.154-.154l-.338-.215 7.494-7.494 1.178-.471z" />
            </svg>
								</button>
							</div>
						</form>

					</div>
				</div>
				<div class="my-4">
					<h6>Answers</h6>
					
					<c:forEach var="answer" items="${question.answers}">
    <div class="border-top border-bottom d-flex py-2">
        <h3 class="text-success me-2 opacity-0">
            <i class="bi bi-check-circle-fill"></i>
        </h3>
        <div class="w-100">
            <div class="ps-2 border-start border-5">${answer.body}</div>
            <div class="d-flex align-items-center justify-content-between">
                <button class="show-comments btn btn-sm text-secondary border-0" data-answer-id="${answer.answerId}">
                    Show Comments
                </button>
                <div class="text-end mb-2" style="font-size: 13px;">
                    <span class="me-2">
                        ${answer.commentCount} 
                        ${answer.commentCount > 1 ? "Comments" : "Comment"}
                        <i class="bi bi-chat-fill"></i>
                    </span>

                    <!-- Loop through answer votes and check if the user has voted -->
                    <c:set var="hasVoted" value="false"/>
                    <c:set var="currentUserId" value="<%= currentUserId %>"/>
                    <c:forEach var="vote" items="${answer.votes}">
                        <c:if test="${vote.userId == currentUserId }">
                            <c:set var="hasVoted" value="true"/>
                        </c:if>
                    </c:forEach>
                    	<span class="answer-vote" data-answer-id="${answer.answerId}">
                        ${answer.votes.size()} ${answer.votes.size() > 1 ? "Votes" : "Vote"}
                        <i class="bi bi-star-fill ${hasVoted ? 'text-warning' : ''}"></i>
                        
                    </span>
                </div>
            </div>

            <div class="comments" id="comments-${answer.answerId}" style="display: none;">
                <!-- Comments will be loaded here via AJAX -->
            </div>
        </div>
    </div>
</c:forEach>

				</div>
				<div>
					<h6>Related Questions</h6>
					<div class="list-group"
						style="background-color: #171527 !important;">
						<c:forEach var="relatedQuestion" items="${relatedQuestions}">
							<c:url var="detailLink" value="questions">
								<c:param name="mode" value="DETAILS" />
								<c:param name="questionId"
									value="${relatedQuestion.questionId }" />
							</c:url>
							<a href="${detailLink }"
								class="list-group-item list-group-item-action text-light"
								style="background-color: #201D31 !important; border: 1px solid #2B293C;">
								${relatedQuestion.title } <c:forEach var="tag"
									items="${relatedQuestion.tags }">
									<span class="badge bg-primary">${tag.tag_name }</span>
								</c:forEach> <span class="badge bg-secondary">${relatedQuestion.answerCount }
									${relatedQuestion.answerCount > 1 ? "Answers" : "Answer" }</span> <span
								class="text-secondary float-end"> ${ DateTimeUtils.formatTimeAgo(relatedQuestion.createdAt) }
							</span>
							</a>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="col-lg-3 col-md-4 col-sm-12 mt-4 mt-md-0">
				<div>
					<h6>Today's Topic Tag</h6>
					<div class="card p-2 mb-2 text-light"
						style="background-color: #201D31; border: 3px solid #2B293C !important;">
						<h6>
							<a href="#">Bootstrap</a>
						</h6>
						<p class="p-0 m-0">42 posts</p>
					</div>
					<div class="card p-2 mb-2 text-light"
						style="background-color: #201D31; border: 3px solid #2B293C !important;">
						<h6>
							<a href="#">CSS</a>
						</h6>
						<p class="p-0 m-0">38 posts</p>
					</div>
					<div class="card p-2 mb-2 text-light"
						style="background-color: #201D31; border: 3px solid #2B293C !important;">
						<h6>
							<a href="#">Java</a>
						</h6>
						<p class="p-0 m-0">27 posts</p>
					</div>
					<div class="card p-2 mb-2 text-light"
						style="background-color: #201D31; border: 3px solid #2B293C !important;">
						<h6>
							<a href="#">API</a>
						</h6>
						<p class="p-0 m-0">19 posts</p>
					</div>
				</div>
			</div>
		</div>

	</div>
	<c:import url="/template/common/footer.jsp"></c:import>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.min.js"></script>
	<script src="https://cdn.quilljs.com/1.3.6/quill.js"></script>
	<c:set var="user" value="${sessionScope.user}" />
	<script>
   var currentUser = {
        id: "<%=currentUser != null ? currentUser.getUser_id() : ""%>",
        username: "<%=currentUser != null ? currentUser.getusername() : ""%>"
    };

    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{ 'header': [1, 2, false] }],
                ['bold', 'italic', 'underline'],
                ['link'],
                [{ 'list': 'ordered' }, { 'list': 'bullet' }],
                ['blockquote', 'code-block'], // Add code block to toolbar
                [{ 'align': [] }],
                ['image']
            ]
        }
    });

    $(document).ready(function() {
    	var questionId = ${question.questionId};
        // Upvote click handler
        $("#upvote").click(function() {
            if (currentUser.id === '') {
                window.location.href = 'http://localhost:8585/dev_ask/login';
            } else {
            	 $(this).prop('disabled', true);
                $.ajax({
                    url: 'questions?mode=VOTE&voteType=upvote',
                    method: 'POST',
                    data: {
                        questionId: questionId,
                    },
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert("Failed to register vote. Please login to vote.");
                        $(this).prop('disabled', false);
                    }
                });
            }
        });

        // Downvote click handler
        $("#downvote").click(function() {
            if (currentUser.id === '') {
                window.location.href = 'http://localhost:8585/dev_ask/login';
            } else {
            	$(this).prop('disabled', true);
                $.ajax({
                    url: 'questions?mode=VOTE&voteType=downvote',
                    method: 'POST',
                    data: {
                        questionId: questionId,
                    },
                    success: function(response) {
                        location.reload();
                    },
                    error: function() {
                        alert("Failed to register vote. Please login to vote.");
                        $(this).prop('disabled', false);
                    }
                });
            }
        });
        

        $('.answer-vote').on('click', function() {
            var answerId = $(this).data('answer-id');
            console.log(answerId);
            answerVote(answerId,questionId);
        });

        
        // Handle show comments button click
        $('.show-comments').on('click', function() {
            var button = $(this);
            var answerId = button.data('answer-id');
            var commentDiv = $('#comments-' + answerId);

            console.log("Answer ID at request: " + answerId);

            if ($.trim(commentDiv.text()) === '') {
                $.ajax({
                    url: 'questions?mode=LOADCOMMENTS&answerId=' + answerId,
                    method: 'GET',
                    data: {
                        answerId: answerId
                    },
                    success: function(data) {
                        commentDiv.html(data);
                        commentDiv.slideDown();
                        button.text("Hide comments");
                    },
                    error: function() {
                        alert('Failed to load comments.');
                    }
                });
            } else {
                commentDiv.slideToggle(function() {
                    if (commentDiv.is(':visible')) {
                        button.text("Hide comments");
                    } else {
                        button.text("Show comments");
                    }
                });
            }
        });

    });

    // Handle form submit for answer content
    document.querySelector('form').onsubmit = function() {
        document.querySelector('#answerContent').value = quill.root.innerHTML;
    };
    
    function answerVote(answerId,questionId){
    	if (currentUser.id === '') {
            window.location.href = 'http://localhost:8585/dev_ask/login';
        } else {
        	console.log("answerid",answerId);
        	  $.ajax({
                  url: 'questions?mode=ANSWERVOTE',
                  method: 'POST',
                  data: {
                      answerId: answerId,
                  },
                  success: function(response) {
                      location.reload();
                  },
                  error: function() {
                      alert("Failed to answer vote. Please login to vote.");
                      $(this).prop('disabled', false);
                  }
              });
        }
    }
</script>

</body>
</html>