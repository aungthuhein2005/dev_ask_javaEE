<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page import="com.devask.utilis.DateTimeUtils"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="com.devask.model.User"%>
<div class="p-4">
    <%
        String answerId = request.getParameter("answerId");
        User currentUser = (User) session.getAttribute("user");
    %>
    <form id="commentForm">
        <input type="hidden" name="mode" value="CREATE" />
        <input type="hidden" data-answer-id="<%= answerId %>" id="answerId" name="answerId" value="<%= answerId %>" />
        <label class="form-label text-light" for="commentContent">Comment</label>
        <div class="d-flex align-items-end">
            <textarea name="body" class="form-control text-light mt-2 me-2" id="commentContent"
                      style="background-color: #201d31; border: 2px solid #2b293c;"></textarea>
            <div class="text-end">
                <button class="btn btn-sm btn-primary" id="submitComment">Comment</button>
            </div>
        </div>
    </form>
</div>
<div id="commentDiv-<%= answerId %>">
    <!-- Comments will be loaded here -->
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function(){
    var answerId = "<%= answerId %>";
    var currentUser = {
        id: "<%= currentUser != null ? currentUser.getUser_id() : "" %>",
        username: "<%= currentUser != null ? currentUser.getusername() : "" %>"
    };

    // Load comments when the page loads
    $.ajax({
        url: 'comment?mode=LOADCOMMENTS&answerId=' + encodeURIComponent(answerId),
        method: 'GET',
        success: function(response){
            $('#commentDiv-' + answerId).html(response);
        },
        error: function(xhr, status, error) {
            console.error('Failed to load comments:', status, error);
        }
    });

    // Handle comment submission
    $('#submitComment').click(function(event){
        event.preventDefault();
        if(currentUser.id == '') {
            window.location.href = 'http://localhost:8585/dev_ask/login'; // Redirect to login if not logged in
        }
        $.ajax({
            url: 'comment',
            method: 'POST',
            data: {
                mode: 'CREATE',
                answerId: answerId,
                body: $('#commentContent').val()
            },
            success: function(response){
                $.ajax({
                    url: 'comment?mode=LOADCOMMENTS&answerId=' + encodeURIComponent(answerId),
                    method: 'GET',
                    success: function(response){
                        $('#commentDiv-' + answerId).html(response);
                    }
                });
                $('#commentContent').val('');  // Clear the textarea after comment is posted
            },
            error: function(xhr, status, error) {
                console.error('Failed to post comment:', status, error);
                alert('Failed to post comment.');
            }
        });
    });
});
</script>
