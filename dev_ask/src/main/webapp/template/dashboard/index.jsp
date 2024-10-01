<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Dashboard</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        .height-100 {
            height: 100vh;
        }
    </style>
</head>
<c:import url="/template/dashboard/side-bar.jsp"></c:import>
<body>
    <div class="height-100">
        <div class="container pt-4">
            <div class="row">
                <div class="col">
                    <div class="card rounded-sm">
                        <div class="card-body">
                            <h5 class="font-bold">Users</h5>
                            <h3>${userCount }</h3>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card rounded-sm">
                        <div class="card-body">
                            <h5 class="font-bold">Tags</h5>
                            <h3>${tagCount }</h3>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card rounded-sm">
                        <div class="card-body">
                            <h5 class="font-bold">Questions</h5>
                            <h3>${questionCount }</h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container mt-4">
            <div class="row">
                <!-- Bar Chart for User Growth -->
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-header">User Growth</div>
                        <div class="card-body">
                            <canvas id="userGrowthChart" style="height: 300px;"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Line Chart for Question Trends -->
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-header">Question Trends</div>
                        <div class="card-body">
                            <canvas id="questionTrendsChart" style="height: 300px;"></canvas>
                        </div>
                    </div>
                </div>

                <!-- Pie Chart for Answer Distribution -->
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

    <script>
        // Dummy data for User Growth chart
        const userGrowthCtx = document.getElementById('userGrowthChart').getContext('2d');
        const userGrowthChart = new Chart(userGrowthCtx, {
            type: 'bar',
            data: {
                labels: ['January', 'February', 'March', 'April', 'May', 'June'],
                datasets: [{
                    label: 'Users',
                    data: [50, 100, 150, 200, 250, 300],
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        // Dummy data for Question Trends chart
        const questionTrendsCtx = document.getElementById('questionTrendsChart').getContext('2d');
        const questionTrendsChart = new Chart(questionTrendsCtx, {
            type: 'line',
            data: {
                labels: ['Week 1', 'Week 2', 'Week 3', 'Week 4'],
                datasets: [{
                    label: 'Questions Asked',
                    data: [10, 25, 30, 45],
                    fill: false,
                    borderColor: 'rgba(153, 102, 255, 1)',
                    tension: 0.1
                }]
            }
        });

        // Dummy data for Answer Distribution chart
        const answerDistributionCtx = document.getElementById('answerDistributionChart').getContext('2d');
        const answerDistributionChart = new Chart(answerDistributionCtx, {
            type: 'pie',
            data: {
                labels: ['Correct', 'Incorrect', 'Pending'],
                datasets: [{
                    label: 'Answer Distribution',
                    data: [40, 30, 30],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255, 99, 132, 1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)'
                    ],
                    borderWidth: 1
                }]
            }
        });
    </script>
	<c:import url="/template/dashboard/footer.jsp"></c:import>
