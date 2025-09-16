<%@ page contentType="text/html; charset=UTF-8" %>
	<!DOCTYPE html>
	<html lang="zh-Hant">

	<head>
		<meta charset="UTF-8" />
		<title>註冊帳號</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />

		<script>
			function toggleForm(type) {
				const userFields = document.querySelectorAll("#userForm input, #userForm select");
				const charityFields = document.querySelectorAll("#charityForm input, #charityForm select");

				if (type === "user") {
					document.getElementById("userForm").style.display = "block";
					document.getElementById("charityForm").style.display = "none";

					userFields.forEach(el => {
						el.disabled = false;
						el.required = el.hasAttribute("data-required") || el.required;
					});
					charityFields.forEach(el => {
						el.disabled = true;
						el.required = false;
					});
				} else if (type === "charity") {
					document.getElementById("userForm").style.display = "none";
					document.getElementById("charityForm").style.display = "block";

					charityFields.forEach(el => {
						el.disabled = false;
						el.required = el.hasAttribute("data-required") || el.required;
					});
					userFields.forEach(el => {
						el.disabled = true;
						el.required = false;
					});

					const fileInput = document.getElementById('charityFile');
					const preview = document.getElementById('filePreview');

					fileInput.addEventListener('change', function () {
						const file = this.files[0];
						if (file && file.type.startsWith('image/')) {
							const reader = new FileReader();
							reader.onload = function (e) {
								preview.src = e.target.result;
								preview.style.display = 'block';
							};
							reader.readAsDataURL(file);
						} else {
							preview.style.display = 'none';
							alert('非圖片檔案無法預覽，請上傳圖片類型的檔案。');
						}
					}, { once: true });
				}
			}

			window.onload = () => {
				const typeSelect = document.querySelector("select[name='accountType']");
				toggleForm(typeSelect.value);
			};
		</script>

		<style>
			body {
				font-family: "Microsoft JhengHei", sans-serif;
				background-color: #f8f9fa;
				margin: 0;
				padding: 20px;
			}

			h2 {
				text-align: center;
				color: #333;
			}

			form {
				max-width: 600px;
				margin: 0 auto;
				background-color: #fff;
				padding: 30px;
				border-radius: 10px;
				box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			}

			img#filePreview {
				display: block;
				max-width: 100%;
				margin-top: 10px;
				border: 1px solid #ccc;
				padding: 5px;
				border-radius: 5px;
			}
		</style>
	</head>

	<body>

		<div class="container">
			<h2>註冊帳號</h2>

			<c:if test="${not empty errorMessage}">
				<div class="alert alert-danger" role="alert">${errorMessage}</div>
			</c:if>

			<form action="RegisterServlet" method="post" enctype="multipart/form-data">

				<div class="mb-3">
					<label for="accountType" class="form-label">選擇身分</label>
					<select name="accountType" id="accountType" class="form-select" onchange="toggleForm(this.value)"
						required>
						<option value="user">一般會員</option>
						<option value="charity">慈善團體</option>
					</select>
				</div>

				<!-- 一般會員 -->
				<div id="userForm" class="form-section">
					<h3>一般會員資訊</h3>

					<div class="mb-3">
						<label for="userAccount" class="form-label">帳號</label>
						<input type="text" id="userAccount" name="userAccount" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="password" class="form-label">密碼</label>
						<input type="password" id="password" name="password" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="fullName" class="form-label">姓名</label>
						<input type="text" id="fullName" name="fullName" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="email" class="form-label">Email</label>
						<input type="email" id="email" name="email" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="phone" class="form-label">電話</label>
						<input type="text" id="phone" name="phone" class="form-control" />
					</div>

					<div class="mb-3">
						<label for="address" class="form-label">地址</label>
						<input type="text" id="address" name="address" class="form-control" />
					</div>

					<div class="mb-3">
						<label for="birthday" class="form-label">生日</label>
						<input type="date" id="birthday" name="birthday" class="form-control" />
					</div>

					<div class="mb-3">
						<label for="gender" class="form-label">性別</label>
						<select name="gender" id="gender" class="form-select">
							<option value="M">男</option>
							<option value="F">女</option>
						</select>
					</div>
				</div>

				<!-- 慈善團體 -->
				<div id="charityForm" class="form-section" style="display: none;">
					<h3>慈善團體資訊</h3>

					<div class="mb-3">
						<label for="charityAccount" class="form-label">團體帳號</label>
						<input type="text" id="charityAccount" name="charityAccount" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="charityPassword" class="form-label">密碼</label>
						<input type="password" id="charityPassword" name="charityPassword" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="charityAddress" class="form-label">地址</label>
						<input type="text" id="charityAddress" name="charityAddress" class="form-control" />
					</div>

					<div class="mb-3">
						<label for="charityPhone" class="form-label">電話</label>
						<input type="text" id="charityPhone" name="charityPhone" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="charityNo" class="form-label">統編</label>
						<input type="text" id="charityNo" name="charityNo" class="form-control" required
							data-required="true" />
					</div>

					<div class="mb-3">
						<label for="charityFile" class="form-label">證明文件</label>
						<input type="file" id="charityFile" name="charityFile" class="form-control" accept="image/*"
							required data-required="true" />
						<img id="filePreview" alt="檔案預覽" style="max-width: 300px; margin-top: 10px; display: none;" />
					</div>
				</div>

				<div class="text-center">
					<button type="submit" class="btn btn-success">送出註冊</button>
				</div>

				<div class="text-center mt-3">
					<a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">回到首頁</a>
				</div>
			</form>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

	</body>

	</html>