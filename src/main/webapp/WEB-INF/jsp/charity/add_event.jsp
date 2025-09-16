<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>新增公益活動</title>
			<!-- Bootstrap 5 CDN -->
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
		</head>

		<body class="bg-light">

			<div class="container mt-5">
				<div class="card shadow-lg">
					<div class="card-header bg-primary text-white">
						<h3 class="mb-0">新增公益活動</h3>
					</div>
					<div class="card-body">

						<!-- 顯示錯誤訊息 -->
						<c:if test="${not empty errorMsg}">
							<div class="alert alert-danger">${errorMsg}</div>
						</c:if>

						<form action="${pageContext.request.contextPath}/charity/AddEventServlet" method="post">

							<div class="mb-3">
								<label for="eventName" class="form-label">活動名稱</label>
								<input type="text" class="form-control" id="eventName" name="eventName" required
									value="${param.eventName != null ? param.eventName : ''}">
							</div>

							<div class="mb-3">
								<label for="eventDescription" class="form-label">活動說明</label>
								<textarea class="form-control" id="eventDescription" name="eventDescription" rows="3"
									required>${param.eventDescription != null ? param.eventDescription : ''}</textarea>
							</div>

							<div class="mb-3">
								<label for="eventLocation" class="form-label">活動地點</label>
								<input type="text" class="form-control" id="eventLocation" name="eventLocation" required
									value="${param.eventLocation != null ? param.eventLocation : ''}">
							</div>

							<div class="mb-3">
								<label for="eventDate" class="form-label">活動日期</label>
								<input type="date" class="form-control" id="eventDate" name="eventDate" required
									value="${param.eventDate != null ? param.eventDate : ''}">
							</div>

							<div class="mb-3">
								<label for="eventStart" class="form-label">活動開始時間</label>
								<input type="time" class="form-control" id="eventStart" name="eventStart" required
									value="${param.eventStart != null ? param.eventStart : ''}">
							</div>

							<div class="mb-3">
								<label for="eventEnd" class="form-label">活動結束時間</label>
								<input type="time" class="form-control" id="eventEnd" name="eventEnd" required
									value="${param.eventEnd != null ? param.eventEnd : ''}">
							</div>

							<div class="d-flex justify-content-between">
								<a href="${pageContext.request.contextPath}/charity/events"
									class="btn btn-secondary">返回活動列表</a>
								<button type="submit" class="btn btn-success">新增活動</button>
							</div>
						</form>

					</div>
				</div>
			</div>

			<!-- Bootstrap JS (optional) -->
			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

		</body>

		</html>